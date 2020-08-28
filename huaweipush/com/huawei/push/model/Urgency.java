// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.model;

public enum Urgency
{
    HIGH("HIGH"), 
    NORMAL("NORMAL");
    
    private String value;
    
    private Urgency(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
