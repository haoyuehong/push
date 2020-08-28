// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import com.huawei.push.util.ValidatorUtils;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ExecutorService;

public abstract class ThreadManager
{
    final HuaweiExecutors getHuaweiExecutors(final HuaweiApp app) {
        return new HuaweiExecutors(this.getExecutor(app));
    }
    
    final void releaseHuaweiExecutors(final HuaweiApp app, final HuaweiExecutors executor) {
        this.releaseExecutor(app, executor.userExecutor);
    }
    
    protected abstract ExecutorService getExecutor(final HuaweiApp p0);
    
    protected abstract void releaseExecutor(final HuaweiApp p0, final ExecutorService p1);
    
    protected abstract ThreadFactory getThreadFactory();
    
    static final class HuaweiExecutors
    {
        private ExecutorService userExecutor;
        
        private HuaweiExecutors(final ExecutorService userExecutor) {
            ValidatorUtils.checkArgument(userExecutor != null, "ExecutorService must not be null");
            this.userExecutor = userExecutor;
        }
    }
}
