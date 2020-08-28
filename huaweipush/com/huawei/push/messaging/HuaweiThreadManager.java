// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.Executors;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.Set;

public class HuaweiThreadManager
{
    public static final ThreadManager DEFAULT_THREAD_MANAGER;
    
    static {
        DEFAULT_THREAD_MANAGER = new DefaultThreadManager();
    }
    
    abstract static class GlobalThreadManager extends ThreadManager
    {
        private final Object lock;
        private final Set<String> apps;
        private ExecutorService executorService;
        
        GlobalThreadManager() {
            this.lock = new Object();
            this.apps = new HashSet<String>();
        }
        
        @Override
        protected ExecutorService getExecutor(final HuaweiApp app) {
            synchronized (this.lock) {
                if (this.executorService == null) {
                    this.executorService = this.doInit();
                }
                this.apps.add(app.getOption().getCredential().getAppId());
                return this.executorService;
            }
        }
        
        @Override
        protected void releaseExecutor(final HuaweiApp app, final ExecutorService executor) {
            synchronized (this.lock) {
                final String appId = app.getOption().getCredential().getAppId();
                if (this.apps.remove(appId) && this.apps.isEmpty()) {
                    this.doCleanup(this.executorService);
                    this.executorService = null;
                }
            }
        }
        
        protected abstract ExecutorService doInit();
        
        protected abstract void doCleanup(final ExecutorService p0);
    }
    
    private static class DefaultThreadManager extends GlobalThreadManager
    {
        @Override
        protected ExecutorService doInit() {
            final ThreadFactory threadFactory = HuaweiScheduledExecutor.getThreadFactoryWithName(this.getThreadFactory(), "huawei-default-%d");
            return Executors.newCachedThreadPool(threadFactory);
        }
        
        @Override
        protected void doCleanup(final ExecutorService executorService) {
            executorService.shutdown();
        }
        
        @Override
        protected ThreadFactory getThreadFactory() {
            return Executors.defaultThreadFactory();
        }
    }
}
