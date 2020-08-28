// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.webpush;

import java.util.Arrays;
import com.huawei.push.util.ValidatorUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class WebpushHeaders
{
    private static String TTL_PATTERN;
    private static String[] URGENCY_VALUE;
    @JSONField(name = "ttl")
    private String ttl;
    @JSONField(name = "topic")
    private String topic;
    @JSONField(name = "urgency")
    private String urgency;
    
    public String getTtl() {
        return this.ttl;
    }
    
    public String getTopic() {
        return this.topic;
    }
    
    public String getUrgency() {
        return this.urgency;
    }
    
    public WebpushHeaders(final Builder builder) {
        this.ttl = builder.ttl;
        this.topic = builder.topic;
        this.urgency = builder.urgency;
    }
    
    public void check() {
        if (this.ttl != null) {
            ValidatorUtils.checkArgument(this.ttl.matches(WebpushHeaders.TTL_PATTERN), "Invalid ttl format");
        }
        if (this.urgency != null) {
            ValidatorUtils.checkArgument(Arrays.stream(WebpushHeaders.URGENCY_VALUE).anyMatch(value -> value.equals(this.urgency)), "Invalid urgency");
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    static {
        WebpushHeaders.TTL_PATTERN = "[0-9]+|[0-9]+[sS]";
        WebpushHeaders.URGENCY_VALUE = new String[] { "very-low", "low", "normal", "high" };
    }
    
    public static class Builder
    {
        private String ttl;
        private String topic;
        private String urgency;
        
        public Builder setTtl(final String ttl) {
            this.ttl = ttl;
            return this;
        }
        
        public Builder setTopic(final String topic) {
            this.topic = topic;
            return this;
        }
        
        public Builder setUrgency(final String urgency) {
            this.urgency = urgency;
            return this;
        }
        
        public WebpushHeaders build() {
            return new WebpushHeaders(this);
        }
    }
}
