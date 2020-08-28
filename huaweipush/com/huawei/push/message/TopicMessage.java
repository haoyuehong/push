// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.message;

import java.util.Collection;
import com.huawei.push.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class TopicMessage
{
    @JSONField(name = "topic")
    private String topic;
    @JSONField(name = "tokenArray")
    private List<String> tokenArray;
    @JSONField(name = "token")
    private String token;
    
    public String getTopic() {
        return this.topic;
    }
    
    public List<String> getTokenArray() {
        return this.tokenArray;
    }
    
    public String getToken() {
        return this.token;
    }
    
    private TopicMessage(final Builder builder) {
        this.tokenArray = new ArrayList<String>();
        this.topic = builder.topic;
        if (!CollectionUtils.isEmpty(builder.tokenArray)) {
            this.tokenArray.addAll(builder.tokenArray);
        }
        else {
            this.tokenArray = null;
        }
        this.token = builder.token;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private String topic;
        private List<String> tokenArray;
        private String token;
        
        public Builder() {
            this.tokenArray = new ArrayList<String>();
        }
        
        public Builder setTopic(final String topic) {
            this.topic = topic;
            return this;
        }
        
        public Builder addToken(final String token) {
            this.tokenArray.add(token);
            return this;
        }
        
        public Builder addAllToken(final List<String> tokenArray) {
            this.tokenArray.addAll(tokenArray);
            return this;
        }
        
        public Builder setToken(final String token) {
            this.token = token;
            return this;
        }
        
        public TopicMessage build() {
            return new TopicMessage(this, null);
        }
    }
}
