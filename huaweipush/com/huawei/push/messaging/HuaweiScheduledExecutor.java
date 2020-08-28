// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class HuaweiScheduledExecutor extends ScheduledThreadPoolExecutor
{
    public HuaweiScheduledExecutor(final ThreadFactory threadFactory, final String name) {
        this(threadFactory, name, null);
    }
    
    public HuaweiScheduledExecutor(final ThreadFactory threadFactory, final String name, final Thread.UncaughtExceptionHandler handler) {
        super(1, decorateThreadFactory(threadFactory, name, handler));
        this.setRemoveOnCancelPolicy(true);
    }
    
    static ThreadFactory getThreadFactoryWithName(final ThreadFactory threadFactory, final String name) {
        return decorateThreadFactory(threadFactory, name, null);
    }
    
    private static ThreadFactory decorateThreadFactory(final ThreadFactory threadFactory, final String name, final Thread.UncaughtExceptionHandler handler) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
        final ThreadFactoryBuilder builder = new ThreadFactoryBuilder().setThreadFactory(threadFactory).setNameFormat(name).setDaemon(true);
        if (handler != null) {
            builder.setUncaughtExceptionHandler(handler);
        }
        return builder.build();
    }
}
