// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.model;

public enum TopicOperation
{
    SUBSCRIBE("subscribe"), 
    UNSUBSCRIBE("unsubscribe"), 
    LIST("list");
    
    private String value;
    
    private TopicOperation(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
