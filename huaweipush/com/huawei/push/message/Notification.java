// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.message;

import com.huawei.push.util.ValidatorUtils;
import java.util.Locale;
import com.alibaba.fastjson.annotation.JSONField;

public class Notification
{
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "body")
    private String body;
    @JSONField(name = "image")
    private String image;
    
    public Notification() {
    }
    
    public Notification(final String title, final String body) {
        this.title = title;
        this.body = body;
    }
    
    public Notification(final String title, final String body, final String image) {
        this.title = title;
        this.body = body;
        this.image = image;
    }
    
    public Notification(final Builder builder) {
        this.title = builder.title;
        this.body = builder.body;
        this.image = builder.image;
    }
    
    public void check() {
        if (this.image != null) {
            ValidatorUtils.checkArgument(this.image.toLowerCase(Locale.getDefault()).trim().startsWith("https"), "image's url should start with HTTPS");
        }
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getBody() {
        return this.body;
    }
    
    public void setBody(final String body) {
        this.body = body;
    }
    
    public String getImage() {
        return this.image;
    }
    
    public void setImage(final String image) {
        this.image = image;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private String title;
        private String body;
        private String image;
        
        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }
        
        public Builder setBody(final String body) {
            this.body = body;
            return this;
        }
        
        public Builder setImage(final String image) {
            this.image = image;
            return this;
        }
        
        public Notification build() {
            return new Notification(this);
        }
    }
}
