// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.apns;

import com.huawei.push.util.ValidatorUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class ApnsHeaders
{
    private static final String AUTHORIZATION_PATTERN = "^bearer*";
    private static final String APN_ID_PATTERN = "[0-9a-z]{8}(-[0-9a-z]{4}){3}-[0-9a-z]{12}";
    private static final int SEND_IMMEDIATELY = 10;
    private static final int SEND_BY_GROUP = 5;
    @JSONField(name = "authorization")
    private String authorization;
    @JSONField(name = "apns-id")
    private String apnsId;
    @JSONField(name = "apns-expiration")
    private Long apnsExpiration;
    @JSONField(name = "apns-priority")
    private String apnsPriority;
    @JSONField(name = "apns-topic")
    private String apnsTopic;
    @JSONField(name = "apns-collapse-id")
    private String apnsCollapseId;
    
    public String getAuthorization() {
        return this.authorization;
    }
    
    public String getApnsId() {
        return this.apnsId;
    }
    
    public Long getApnsExpiration() {
        return this.apnsExpiration;
    }
    
    public String getApnsPriority() {
        return this.apnsPriority;
    }
    
    public String getApnsTopic() {
        return this.apnsTopic;
    }
    
    public String getApnsCollapseId() {
        return this.apnsCollapseId;
    }
    
    public void check() {
        if (this.authorization != null) {
            ValidatorUtils.checkArgument(this.authorization.matches("^bearer*"), "authorization must start with bearer");
        }
        if (this.apnsId != null) {
            ValidatorUtils.checkArgument(this.apnsId.matches("[0-9a-z]{8}(-[0-9a-z]{4}){3}-[0-9a-z]{12}"), "apns-id format error");
        }
        if (this.apnsPriority != null) {
            ValidatorUtils.checkArgument(Integer.parseInt(this.apnsPriority) == 5 || Integer.parseInt(this.apnsPriority) == 10, "apns-priority should be SEND_BY_GROUP:5  or SEND_IMMEDIATELY:10");
        }
        if (this.apnsCollapseId != null) {
            ValidatorUtils.checkArgument(this.apnsCollapseId.getBytes().length < 64, "Number of apnsCollapseId bytes should be less than 64");
        }
    }
    
    private ApnsHeaders(final Builder builder) {
        this.authorization = builder.authorization;
        this.apnsId = builder.apnsId;
        this.apnsExpiration = builder.apnsExpiration;
        this.apnsPriority = builder.apnsPriority;
        this.apnsTopic = builder.apnsTopic;
        this.apnsCollapseId = builder.apnsCollapseId;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private String authorization;
        private String apnsId;
        private Long apnsExpiration;
        private String apnsPriority;
        private String apnsTopic;
        private String apnsCollapseId;
        
        public Builder setAuthorization(final String authorization) {
            this.authorization = authorization;
            return this;
        }
        
        public Builder setApnsId(final String apnsId) {
            this.apnsId = apnsId;
            return this;
        }
        
        public Builder setApnsExpiration(final Long apnsExpiration) {
            this.apnsExpiration = apnsExpiration;
            return this;
        }
        
        public Builder setApnsPriority(final String apnsPriority) {
            this.apnsPriority = apnsPriority;
            return this;
        }
        
        public Builder setApnsTopic(final String apnsTopic) {
            this.apnsTopic = apnsTopic;
            return this;
        }
        
        public Builder setApnsCollapseId(final String apnsCollapseId) {
            this.apnsCollapseId = apnsCollapseId;
            return this;
        }
        
        public ApnsHeaders build() {
            return new ApnsHeaders(this, null);
        }
    }
}
