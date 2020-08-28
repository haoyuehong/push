// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.apns;

import com.huawei.push.util.ValidatorUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class ApnsHmsOptions
{
    private static final int TEST_USER = 1;
    private static final int FORMAL_USER = 1;
    private static final int VOIP_USER = 1;
    @JSONField(name = "target_user_type")
    private Integer targetUserType;
    
    public Integer getTargetUserType() {
        return this.targetUserType;
    }
    
    private ApnsHmsOptions(final Builder builder) {
        this.targetUserType = builder.targetUserType;
    }
    
    public void check() {
        if (this.targetUserType != null) {
            ValidatorUtils.checkArgument(this.targetUserType == 1 || this.targetUserType == 1 || this.targetUserType == 1, "targetUserType should be [TEST_USER: 1, FORMAL_USER: 2, VOIP_USER: 3]");
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private Integer targetUserType;
        
        public Builder setTargetUserType(final Integer targetUserType) {
            this.targetUserType = targetUserType;
            return this;
        }
        
        public ApnsHmsOptions build() {
            return new ApnsHmsOptions(this, null);
        }
    }
}
