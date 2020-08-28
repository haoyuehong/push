// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.message;

import com.huawei.push.util.CollectionUtils;
import java.util.HashMap;
import com.huawei.push.apns.Aps;
import java.util.Map;
import com.huawei.push.apns.ApnsHeaders;
import com.alibaba.fastjson.annotation.JSONField;
import com.huawei.push.apns.ApnsHmsOptions;

public class ApnsConfig
{
    @JSONField(name = "hms_options")
    private ApnsHmsOptions hmsOptions;
    @JSONField(name = "headers")
    private ApnsHeaders apnsHeaders;
    @JSONField(name = "payload")
    private Map<String, Object> payload;
    
    public void check() {
        if (this.hmsOptions != null) {
            this.hmsOptions.check();
        }
        if (this.apnsHeaders != null) {
            this.apnsHeaders.check();
        }
        if (this.payload != null && this.payload.get("aps") != null) {
            final Aps aps = this.payload.get("aps");
            aps.check();
        }
    }
    
    public ApnsConfig(final Builder builder) {
        this.payload = new HashMap<String, Object>();
        this.hmsOptions = builder.hmsOptions;
        this.apnsHeaders = builder.apnsHeaders;
        if (!CollectionUtils.isEmpty(builder.payload) || builder.aps != null) {
            if (!CollectionUtils.isEmpty(builder.payload)) {
                this.payload.putAll(builder.payload);
            }
            if (builder.aps != null) {
                this.payload.put("aps", builder.aps);
            }
        }
        else {
            this.payload = null;
        }
    }
    
    public ApnsHmsOptions getHmsOptions() {
        return this.hmsOptions;
    }
    
    public Map<String, Object> getPayload() {
        return this.payload;
    }
    
    public ApnsHeaders getApnsHeaders() {
        return this.apnsHeaders;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private ApnsHmsOptions hmsOptions;
        private Map<String, Object> payload;
        private ApnsHeaders apnsHeaders;
        private Aps aps;
        
        public Builder() {
            this.payload = new HashMap<String, Object>();
        }
        
        public Builder setHmsOptions(final ApnsHmsOptions hmsOptions) {
            this.hmsOptions = hmsOptions;
            return this;
        }
        
        public Builder addPayload(final String key, final Object value) {
            this.payload.put(key, value);
            return this;
        }
        
        public Builder addAllPayload(final Map<String, Object> map) {
            this.payload.putAll(map);
            return this;
        }
        
        public Builder setApnsHeaders(final ApnsHeaders apnsHeaders) {
            this.apnsHeaders = apnsHeaders;
            return this;
        }
        
        public Builder addPayloadAps(final Aps aps) {
            this.aps = aps;
            return this;
        }
        
        public Builder addPayload(final Aps aps) {
            this.aps = aps;
            return this;
        }
        
        public ApnsConfig build() {
            return new ApnsConfig(this);
        }
    }
}
