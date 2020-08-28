// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.message;

import org.apache.commons.lang3.StringUtils;
import com.huawei.push.util.ValidatorUtils;
import com.google.common.primitives.Booleans;
import com.google.common.base.Strings;
import java.util.Collection;
import com.huawei.push.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class Message
{
    @JSONField(name = "data")
    private String data;
    @JSONField(name = "notification")
    private Notification notification;
    @JSONField(name = "android")
    private AndroidConfig androidConfig;
    @JSONField(name = "apns")
    private ApnsConfig apns;
    @JSONField(name = "webpush")
    private WebPushConfig webpush;
    @JSONField(name = "token")
    private List<String> token;
    @JSONField(name = "topic")
    private String topic;
    @JSONField(name = "condition")
    private String condition;
    
    private Message(final Builder builder) {
        this.token = new ArrayList<String>();
        this.data = builder.data;
        this.notification = builder.notification;
        this.androidConfig = builder.androidConfig;
        this.apns = builder.apns;
        this.webpush = builder.webpush;
        if (!CollectionUtils.isEmpty(builder.token)) {
            this.token.addAll(builder.token);
        }
        else {
            this.token = null;
        }
        this.topic = builder.topic;
        this.condition = builder.condition;
        this.check();
    }
    
    public void check() {
        final int count = Booleans.countTrue(new boolean[] { !CollectionUtils.isEmpty(this.token), !Strings.isNullOrEmpty(this.topic), !Strings.isNullOrEmpty(this.condition) });
        ValidatorUtils.checkArgument(count == 1, "Exactly one of token, topic or condition must be specified");
        final boolean isEmptyData = StringUtils.isEmpty((CharSequence)this.data);
        if (this.notification != null) {
            this.notification.check();
        }
        if (null != this.androidConfig) {
            this.androidConfig.check(this.notification);
        }
        if (this.apns != null) {
            this.apns.check();
        }
        if (this.webpush != null) {
            this.webpush.check();
        }
    }
    
    public String getData() {
        return this.data;
    }
    
    public Notification getNotification() {
        return this.notification;
    }
    
    public AndroidConfig getAndroidConfig() {
        return this.androidConfig;
    }
    
    public ApnsConfig getApns() {
        return this.apns;
    }
    
    public WebPushConfig getWebpush() {
        return this.webpush;
    }
    
    public List<String> getToken() {
        return this.token;
    }
    
    public String getTopic() {
        return this.topic;
    }
    
    public String getCondition() {
        return this.condition;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private String data;
        private Notification notification;
        private AndroidConfig androidConfig;
        private ApnsConfig apns;
        private WebPushConfig webpush;
        private List<String> token;
        private String topic;
        private String condition;
        
        private Builder() {
            this.token = new ArrayList<String>();
        }
        
        public Builder setData(final String data) {
            this.data = data;
            return this;
        }
        
        public Builder setNotification(final Notification notification) {
            this.notification = notification;
            return this;
        }
        
        public Builder setAndroidConfig(final AndroidConfig androidConfig) {
            this.androidConfig = androidConfig;
            return this;
        }
        
        public Builder setApns(final ApnsConfig apns) {
            this.apns = apns;
            return this;
        }
        
        public Builder setWebpush(final WebPushConfig webpush) {
            this.webpush = webpush;
            return this;
        }
        
        public Builder addToken(final String token) {
            this.token.add(token);
            return this;
        }
        
        public Builder addAllToken(final List<String> tokens) {
            this.token.addAll(tokens);
            return this;
        }
        
        public Builder setTopic(final String topic) {
            this.topic = topic;
            return this;
        }
        
        public Builder setCondition(final String condition) {
            this.condition = condition;
            return this;
        }
        
        public Message build() {
            return new Message(this, null);
        }
    }
}
