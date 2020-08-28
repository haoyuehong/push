// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.model;

public enum Importance
{
    LOW("LOW"), 
    NORMAL("NORMAL"), 
    HIGH("HIGH");
    
    private String value;
    
    private Importance(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
