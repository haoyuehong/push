// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.message;

import com.alibaba.fastjson.annotation.JSONField;

public class TokenMessage
{
    @JSONField(name = "token")
    private String token;
    
    public String getToken() {
        return this.token;
    }
    
    public TokenMessage(final Builder builder) {
        this.token = builder.token;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private String token;
        
        public Builder setToken(final String token) {
            this.token = token;
            return this;
        }
        
        public TokenMessage build() {
            return new TokenMessage(this);
        }
    }
}
