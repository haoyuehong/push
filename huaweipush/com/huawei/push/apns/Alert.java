// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.apns;

import com.huawei.push.util.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.Collection;
import com.huawei.push.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class Alert
{
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "body")
    private String body;
    @JSONField(name = "title-loc-key")
    private String titleLocKey;
    @JSONField(name = "title-loc-args")
    private List<String> titleLocArgs;
    @JSONField(name = "action-loc-key")
    private String actionLocKey;
    @JSONField(name = "loc-key")
    private String locKey;
    @JSONField(name = "loc-args")
    private List<String> locArgs;
    @JSONField(name = "launch-image")
    private String launchImage;
    
    public String getTitle() {
        return this.title;
    }
    
    public String getBody() {
        return this.body;
    }
    
    public String getTitleLocKey() {
        return this.titleLocKey;
    }
    
    public List<String> getTitleLocArgs() {
        return this.titleLocArgs;
    }
    
    public String getActionLocKey() {
        return this.actionLocKey;
    }
    
    public String getLocKey() {
        return this.locKey;
    }
    
    public List<String> getLocArgs() {
        return this.locArgs;
    }
    
    public String getLaunchImage() {
        return this.launchImage;
    }
    
    private Alert(final Builder builder) {
        this.titleLocArgs = new ArrayList<String>();
        this.locArgs = new ArrayList<String>();
        this.title = builder.title;
        this.body = builder.body;
        this.titleLocKey = builder.titleLocKey;
        if (!CollectionUtils.isEmpty(builder.titleLocArgs)) {
            this.titleLocArgs.addAll(builder.titleLocArgs);
        }
        else {
            this.titleLocArgs = null;
        }
        this.actionLocKey = builder.actionLocKey;
        this.locKey = builder.locKey;
        if (!CollectionUtils.isEmpty(builder.locArgs)) {
            this.locArgs.addAll(builder.locArgs);
        }
        else {
            this.locArgs = null;
        }
        this.launchImage = builder.launchImage;
    }
    
    public void check() {
        if (!CollectionUtils.isEmpty(this.locArgs)) {
            ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)this.locKey), "locKey is required when specifying locArgs");
        }
        if (!CollectionUtils.isEmpty(this.titleLocArgs)) {
            ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)this.titleLocKey), "titleLocKey is required when specifying titleLocArgs");
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private String title;
        private String body;
        private String titleLocKey;
        private List<String> titleLocArgs;
        private String actionLocKey;
        private String locKey;
        private List<String> locArgs;
        private String launchImage;
        
        public Builder() {
            this.titleLocArgs = new ArrayList<String>();
            this.locArgs = new ArrayList<String>();
        }
        
        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }
        
        public Builder setBody(final String body) {
            this.body = body;
            return this;
        }
        
        public Builder setTitleLocKey(final String titleLocKey) {
            this.titleLocKey = titleLocKey;
            return this;
        }
        
        public Builder setAddAllTitleLocArgs(final List<String> titleLocArgs) {
            this.titleLocArgs.addAll(titleLocArgs);
            return this;
        }
        
        public Builder setAddTitleLocArg(final String titleLocArg) {
            this.titleLocArgs.add(titleLocArg);
            return this;
        }
        
        public Builder setActionLocKey(final String actionLocKey) {
            this.actionLocKey = actionLocKey;
            return this;
        }
        
        public Builder setLocKey(final String locKey) {
            this.locKey = locKey;
            return this;
        }
        
        public Builder AddAllLocArgs(final List<String> locArgs) {
            this.locArgs.addAll(locArgs);
            return this;
        }
        
        public Builder AddLocArg(final String locArg) {
            this.locArgs.add(locArg);
            return this;
        }
        
        public Builder setLaunchImage(final String launchImage) {
            this.launchImage = launchImage;
            return this;
        }
        
        public Alert build() {
            return new Alert(this, null);
        }
    }
}
