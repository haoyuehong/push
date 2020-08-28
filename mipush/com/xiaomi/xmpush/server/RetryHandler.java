// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

public interface RetryHandler
{
    boolean retryHandle(final AbstractClient.ResponseWrapper p0, final Exception p1, final int p2);
}
