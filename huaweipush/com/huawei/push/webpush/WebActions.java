// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.webpush;

import com.alibaba.fastjson.annotation.JSONField;

public class WebActions
{
    @JSONField(name = "action")
    private String action;
    @JSONField(name = "icon")
    private String icon;
    @JSONField(name = "title")
    private String title;
    
    public String getAction() {
        return this.action;
    }
    
    public String getIcon() {
        return this.icon;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void check() {
    }
    
    public WebActions(final Builder builder) {
        this.action = builder.action;
        this.icon = builder.icon;
        this.title = builder.title;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private String action;
        private String icon;
        private String title;
        
        public Builder setAction(final String action) {
            this.action = action;
            return this;
        }
        
        public Builder setIcon(final String icon) {
            this.icon = icon;
            return this;
        }
        
        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }
        
        public WebActions build() {
            return new WebActions(this);
        }
    }
}
