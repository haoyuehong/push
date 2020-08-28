// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

public enum PushJobType
{
    Invalid(0), 
    Topic(1), 
    Common(2), 
    Alias(3), 
    BatchAlias(4), 
    BatchRegId(5), 
    ALL(6), 
    UserAccount(7), 
    BatchUserAccount(8), 
    DeviceId(9), 
    ImeiMd5(10), 
    PublicWelfare(11), 
    Miid(12), 
    BatchMiid(13);
    
    private final byte value;
    private static PushJobType[] VALID_JOB_TYPES;
    
    private PushJobType(final int value) {
        this((byte)value);
    }
    
    private PushJobType(final byte value) {
        this.value = value;
    }
    
    public byte value() {
        return this.value;
    }
    
    public static PushJobType from(final byte value) {
        for (final PushJobType type : PushJobType.VALID_JOB_TYPES) {
            if (type.value == value) {
                return type;
            }
        }
        return PushJobType.Invalid;
    }
    
    static {
        PushJobType.VALID_JOB_TYPES = new PushJobType[] { PushJobType.Topic, PushJobType.Common, PushJobType.Alias, PushJobType.UserAccount, PushJobType.ImeiMd5, PushJobType.Miid };
    }
}
