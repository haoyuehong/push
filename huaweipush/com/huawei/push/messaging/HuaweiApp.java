// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import org.slf4j.LoggerFactory;
import com.google.common.collect.UnmodifiableIterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.text.MessageFormat;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import com.google.common.collect.ImmutableList;
import java.util.List;
import com.huawei.push.util.ValidatorUtils;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ScheduledExecutorService;
import java.util.Map;
import org.slf4j.Logger;

public class HuaweiApp
{
    private static final Logger logger;
    private String appId;
    private HuaweiOption option;
    private static final Object appsLock;
    private static final Map<String, HuaweiApp> instances;
    private final Map<String, HuaweiService> services;
    private TokenRefresher tokenRefresher;
    private volatile ScheduledExecutorService scheduledExecutor;
    private ThreadManager threadManager;
    private ThreadManager.HuaweiExecutors executors;
    private final AtomicBoolean deleted;
    private final Object lock;
    
    private HuaweiApp(final HuaweiOption option) {
        this.services = new HashMap<String, HuaweiService>();
        this.deleted = new AtomicBoolean();
        this.lock = new Object();
        ValidatorUtils.checkArgument(option != null, "HuaweiOption must not be null");
        this.option = option;
        this.appId = option.getCredential().getAppId();
        this.tokenRefresher = new TokenRefresher(this);
        this.threadManager = option.getThreadManager();
        this.executors = this.threadManager.getHuaweiExecutors(this);
    }
    
    public HuaweiOption getOption() {
        return this.option;
    }
    
    public String getAppId() {
        return this.appId;
    }
    
    public static HuaweiApp getInstance(final HuaweiOption option) {
        final String appId = option.getCredential().getAppId();
        synchronized (HuaweiApp.appsLock) {
            final HuaweiApp app = HuaweiApp.instances.get(appId);
            if (app != null) {
                return app;
            }
            return initializeApp(option);
        }
    }
    
    public static HuaweiApp initializeApp(final HuaweiOption option) {
        final String appId = option.getCredential().getAppId();
        HuaweiApp app;
        synchronized (HuaweiApp.appsLock) {
            if (!HuaweiApp.instances.containsKey(appId)) {
                ValidatorUtils.checkState(!HuaweiApp.instances.containsKey(appId), "HuaweiApp with id " + appId + " already exists!");
                app = new HuaweiApp(option);
                HuaweiApp.instances.put(appId, app);
                app.startTokenRefresher();
            }
            else {
                app = getInstance(option);
            }
        }
        return app;
    }
    
    public static List<HuaweiApp> getApps() {
        synchronized (HuaweiApp.appsLock) {
            return (List<HuaweiApp>)ImmutableList.copyOf((Collection)HuaweiApp.instances.values());
        }
    }
    
    public static List<String> getAllAppIds() {
        final Set<String> allAppIds = new HashSet<String>();
        synchronized (HuaweiApp.appsLock) {
            for (final HuaweiApp app : HuaweiApp.instances.values()) {
                allAppIds.add(app.getAppId());
            }
        }
        final List<String> sortedIdList = new ArrayList<String>(allAppIds);
        Collections.sort(sortedIdList);
        return sortedIdList;
    }
    
    public void delete() {
        synchronized (this.lock) {
            final boolean valueChanged = this.deleted.compareAndSet(false, true);
            if (!valueChanged) {
                return;
            }
            try {
                this.getOption().getHttpClient().close();
                this.getOption().getCredential().getHttpClient().close();
            }
            catch (IOException e) {
                HuaweiApp.logger.debug("Fail to close httpClient");
            }
            for (final HuaweiService service : this.services.values()) {
                service.destroy();
            }
            this.services.clear();
            this.tokenRefresher.stop();
            this.threadManager.releaseHuaweiExecutors(this, this.executors);
            if (this.scheduledExecutor != null) {
                this.scheduledExecutor.shutdown();
                this.scheduledExecutor = null;
            }
        }
        synchronized (HuaweiApp.appsLock) {
            HuaweiApp.instances.remove(this.getAppId());
        }
    }
    
    private void checkNotDeleted() {
        final String errorMessage = MessageFormat.format("HuaweiApp with id {0} was deleted", this.getAppId());
        ValidatorUtils.checkState(!this.deleted.get(), errorMessage);
    }
    
    private ScheduledExecutorService singleScheduledExecutorService() {
        if (this.scheduledExecutor == null) {
            synchronized (this.lock) {
                this.checkNotDeleted();
                if (this.scheduledExecutor == null) {
                    this.scheduledExecutor = new HuaweiScheduledExecutor(this.getThreadFactory(), "huawei-scheduled-worker");
                }
            }
        }
        return this.scheduledExecutor;
    }
    
    public ThreadFactory getThreadFactory() {
        return this.threadManager.getThreadFactory();
    }
    
    private ScheduledExecutorService getScheduledExecutorService() {
        return this.singleScheduledExecutorService();
    }
    
    ScheduledFuture<?> schedule(final Runnable runnable, final long initialDelay, final long period) {
        return this.getScheduledExecutorService().scheduleWithFixedDelay(runnable, initialDelay, period, TimeUnit.MILLISECONDS);
    }
    
    void addService(final HuaweiService service) {
        synchronized (this.lock) {
            this.checkNotDeleted();
            ValidatorUtils.checkArgument(!this.services.containsKey(service.getId()), "service already exists");
            this.services.put(service.getId(), service);
        }
    }
    
    HuaweiService getService(final String id) {
        synchronized (this.lock) {
            return this.services.get(id);
        }
    }
    
    public void startTokenRefresher() {
        synchronized (this.lock) {
            this.checkNotDeleted();
            this.tokenRefresher.start();
        }
    }
    
    public static void clearInstancesForTest() {
        synchronized (HuaweiApp.appsLock) {
            for (final HuaweiApp app : ImmutableList.copyOf((Collection)HuaweiApp.instances.values())) {
                app.delete();
            }
            HuaweiApp.instances.clear();
        }
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)HuaweiApp.class);
        appsLock = new Object();
        instances = new HashMap<String, HuaweiApp>();
    }
}
