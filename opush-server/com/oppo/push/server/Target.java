// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

public class Target
{
    private TargetType targetType;
    private String targetValue;
    
    public Target() {
        this.targetType = TargetType.REGISTRATION_ID;
    }
    
    public TargetType getTargetType() {
        return this.targetType;
    }
    
    public void setTargetType(final TargetType targetType) {
        this.targetType = targetType;
    }
    
    public String getTargetValue() {
        return this.targetValue;
    }
    
    public void setTargetValue(final String targetValue) {
        this.targetValue = targetValue;
    }
    
    public static Target build(final String targetValue) {
        final Target target = new Target();
        target.setTargetValue(targetValue);
        return target;
    }
    
    void validate() {
        if (this.targetType != TargetType.ALL && Validate.isEmpty(this.targetValue)) {
            throw new IllegalArgumentException("target value is null");
        }
        if (this.targetType == TargetType.REGISTRATION_ID) {
            final String[] split;
            final String[] registrationIds = split = this.targetValue.split(";");
            for (final String registrationId : split) {
                if (!Validate.validateRegistrationId(registrationId) && registrationIds.length == 1) {
                    throw new IllegalArgumentException("registration_id format error");
                }
                if (!Validate.validateRegistrationId(registrationId) && registrationIds.length > 1) {
                    throw new IllegalArgumentException("registration_id format error, two values registration_id separated by commas");
                }
            }
        }
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
