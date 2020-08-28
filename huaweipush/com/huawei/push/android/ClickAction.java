// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.android;

import org.apache.commons.lang3.StringUtils;
import com.huawei.push.util.ValidatorUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class ClickAction
{
    private static final String PATTERN = "^https.*";
    @JSONField(name = "type")
    private Integer type;
    @JSONField(name = "intent")
    private String intent;
    @JSONField(name = "url")
    private String url;
    @JSONField(name = "rich_resource")
    private String richResource;
    @JSONField(name = "action")
    private String action;
    
    private ClickAction(final Builder builder) {
        this.type = builder.type;
        switch (this.type) {
            case 1: {
                this.intent = builder.intent;
                this.action = builder.action;
                break;
            }
            case 2: {
                this.url = builder.url;
                break;
            }
            case 4: {
                this.richResource = builder.richResource;
                break;
            }
        }
    }
    
    public void check() {
        final boolean isTrue = this.type == 1 || this.type == 2 || this.type == 3 || this.type == 4;
        ValidatorUtils.checkArgument(isTrue, "click type should be one of 1: customize action, 2: open url, 3: open app, 4: open rich media");
        switch (this.type) {
            case 1: {
                ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)this.intent) || StringUtils.isNotEmpty((CharSequence)this.action), "intent or action is required when click type=1");
                break;
            }
            case 2: {
                ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)this.url), "url is required when click type=2");
                ValidatorUtils.checkArgument(this.url.matches("^https.*"), "url must start with https");
                break;
            }
            case 4: {
                ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)this.richResource), "richResource is required when click type=4");
                ValidatorUtils.checkArgument(this.richResource.matches("^https.*"), "richResource must start with https");
                break;
            }
        }
    }
    
    public int getType() {
        return this.type;
    }
    
    public String getIntent() {
        return this.intent;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String getRichResource() {
        return this.richResource;
    }
    
    public String getAction() {
        return this.action;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private Integer type;
        private String intent;
        private String url;
        private String richResource;
        private String action;
        
        private Builder() {
        }
        
        public Builder setType(final Integer type) {
            this.type = type;
            return this;
        }
        
        public Builder setIntent(final String intent) {
            this.intent = intent;
            return this;
        }
        
        public Builder setUrl(final String url) {
            this.url = url;
            return this;
        }
        
        public Builder setRichResource(final String richResource) {
            this.richResource = richResource;
            return this;
        }
        
        public Builder setAction(final String action) {
            this.action = action;
            return this;
        }
        
        public ClickAction build() {
            return new ClickAction(this, null);
        }
    }
}
