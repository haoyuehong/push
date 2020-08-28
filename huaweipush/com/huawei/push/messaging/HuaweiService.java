// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

public abstract class HuaweiService<T>
{
    private String id;
    protected T instance;
    
    protected HuaweiService(final String id, final T instance) {
        this.id = id;
        this.instance = instance;
    }
    
    public String getId() {
        return this.id;
    }
    
    public T getInstance() {
        return this.instance;
    }
    
    public abstract void destroy();
}
