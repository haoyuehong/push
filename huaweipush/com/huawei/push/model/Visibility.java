// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.model;

public enum Visibility
{
    VISIBILITY_UNSPECIFIED("VISIBILITY_UNSPECIFIED"), 
    PRIVATE("PRIVATE"), 
    PUBLIC("PUBLIC"), 
    SECRET("SECRET");
    
    private String value;
    
    private Visibility(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
