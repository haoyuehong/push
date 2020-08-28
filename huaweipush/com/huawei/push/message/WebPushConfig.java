// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.message;

import com.huawei.push.webpush.WebHmsOptions;
import com.huawei.push.webpush.WebNotification;
import com.alibaba.fastjson.annotation.JSONField;
import com.huawei.push.webpush.WebpushHeaders;

public class WebPushConfig
{
    @JSONField(name = "headers")
    private WebpushHeaders headers;
    @JSONField(name = "data")
    private String data;
    @JSONField(name = "notification")
    private WebNotification notification;
    @JSONField(name = "hms_options")
    private WebHmsOptions webHmsOptions;
    
    public WebpushHeaders getHeaders() {
        return this.headers;
    }
    
    public String getData() {
        return this.data;
    }
    
    public WebNotification getNotification() {
        return this.notification;
    }
    
    public WebHmsOptions getWebHmsOptions() {
        return this.webHmsOptions;
    }
    
    public WebPushConfig(final Builder builder) {
        this.headers = builder.headers;
        this.data = builder.data;
        this.notification = builder.notification;
        this.webHmsOptions = builder.webHmsOptions;
    }
    
    public void check() {
        if (this.headers != null) {
            this.headers.check();
        }
        if (this.notification != null) {
            this.notification.check();
        }
        if (this.webHmsOptions != null) {
            this.webHmsOptions.check();
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private WebpushHeaders headers;
        private String data;
        private WebNotification notification;
        private WebHmsOptions webHmsOptions;
        
        public Builder setHeaders(final WebpushHeaders headers) {
            this.headers = headers;
            return this;
        }
        
        public Builder setData(final String data) {
            this.data = data;
            return this;
        }
        
        public Builder setNotification(final WebNotification notification) {
            this.notification = notification;
            return this;
        }
        
        public Builder setWebHmsOptions(final WebHmsOptions webHmsOptions) {
            this.webHmsOptions = webHmsOptions;
            return this;
        }
        
        public WebPushConfig build() {
            return new WebPushConfig(this);
        }
    }
}
