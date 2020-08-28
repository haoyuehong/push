// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import com.huawei.push.util.ValidatorUtils;
import java.util.concurrent.Future;
import org.slf4j.Logger;

public class TokenRefresher
{
    private static final Logger logger;
    private HuaweiApp app;
    private HuaweiCredential credential;
    private Future future;
    
    public TokenRefresher(final HuaweiApp app) {
        ValidatorUtils.checkArgument(app != null, "app must not be null");
        this.app = app;
        this.credential = app.getOption().getCredential();
    }
    
    protected void scheduleNext(final Runnable task, final long initialDelay, final long period) {
        TokenRefresher.logger.debug("Scheduling next token refresh in {} milliseconds", (Object)period);
        try {
            this.future = this.app.schedule(task, initialDelay, period);
        }
        catch (UnsupportedOperationException e) {
            TokenRefresher.logger.debug("Failed to schedule token refresh event", (Throwable)e);
        }
    }
    
    public void scheduleRefresh(final long period) {
        this.cancelPrevious();
        this.scheduleNext(() -> this.credential.refreshToken(), period, period);
    }
    
    private void cancelPrevious() {
        if (this.future != null) {
            this.future.cancel(true);
        }
    }
    
    final synchronized void start() {
        TokenRefresher.logger.debug("Starting the proactive token refresher");
        final String accessToken = this.credential.getAccessToken();
        if (accessToken == null || StringUtils.isEmpty((CharSequence)accessToken)) {
            this.credential.refreshToken();
        }
        final long refreshDelay = this.credential.getExpireIn();
        this.scheduleRefresh(refreshDelay);
    }
    
    final synchronized void stop() {
        this.cancelPrevious();
        TokenRefresher.logger.debug("Stopped the proactive token refresher");
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)TokenRefresher.class);
    }
}
