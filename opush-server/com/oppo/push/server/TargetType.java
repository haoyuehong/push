// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

public enum TargetType
{
    ALL(1), 
    REGISTRATION_ID(2);
    
    private int value;
    
    int getValue() {
        return this.value;
    }
    
    private TargetType(final int value) {
        this.value = value;
    }
}
