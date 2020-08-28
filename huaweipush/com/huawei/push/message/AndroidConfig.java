// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.message;

import org.apache.commons.lang3.StringUtils;
import com.huawei.push.model.Urgency;
import com.huawei.push.util.ValidatorUtils;
import com.huawei.push.android.AndroidNotification;
import com.alibaba.fastjson.annotation.JSONField;

public class AndroidConfig
{
    private static final String TTL_PATTERN = "\\d+|\\d+[sS]|\\d+.\\d{1,9}|\\d+.\\d{1,9}[sS]";
    @JSONField(name = "collapse_key")
    private Integer collapseKey;
    @JSONField(name = "urgency")
    private String urgency;
    @JSONField(name = "category")
    private String category;
    @JSONField(name = "ttl")
    private String ttl;
    @JSONField(name = "bi_tag")
    private String biTag;
    @JSONField(name = "fast_app_target")
    private Integer fastAppTargetType;
    @JSONField(name = "data")
    private String data;
    @JSONField(name = "notification")
    private AndroidNotification notification;
    
    public AndroidConfig(final Builder builder) {
        this.collapseKey = builder.collapseKey;
        this.urgency = builder.urgency;
        this.category = builder.category;
        if (null != builder.ttl) {
            this.ttl = builder.ttl;
        }
        else {
            this.ttl = null;
        }
        this.biTag = builder.biTag;
        this.fastAppTargetType = builder.fastAppTargetType;
        this.data = builder.data;
        this.notification = builder.notification;
    }
    
    public void check(final Notification notification) {
        if (this.collapseKey != null) {
            ValidatorUtils.checkArgument(this.collapseKey >= -1 && this.collapseKey <= 100, "Collapse Key should be [-1, 100]");
        }
        if (this.urgency != null) {
            ValidatorUtils.checkArgument(StringUtils.equals((CharSequence)this.urgency, (CharSequence)Urgency.HIGH.getValue()) || StringUtils.equals((CharSequence)this.urgency, (CharSequence)Urgency.NORMAL.getValue()), "urgency shouid be [HIGH, NORMAL]");
        }
        if (StringUtils.isNotEmpty((CharSequence)this.ttl)) {
            ValidatorUtils.checkArgument(this.ttl.matches("\\d+|\\d+[sS]|\\d+.\\d{1,9}|\\d+.\\d{1,9}[sS]"), "The TTL's format is wrong");
        }
        if (this.fastAppTargetType != null) {
            ValidatorUtils.checkArgument(this.fastAppTargetType == 1 || this.fastAppTargetType == 2, "Invalid fast app target type[one of 1 or 2]");
        }
        if (null != this.notification) {
            this.notification.check(notification);
        }
    }
    
    public Integer getCollapseKey() {
        return this.collapseKey;
    }
    
    public String getTtl() {
        return this.ttl;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public String getBiTag() {
        return this.biTag;
    }
    
    public Integer getFastAppTargetType() {
        return this.fastAppTargetType;
    }
    
    public AndroidNotification getNotification() {
        return this.notification;
    }
    
    public String getUrgency() {
        return this.urgency;
    }
    
    public String getData() {
        return this.data;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private Integer collapseKey;
        private String urgency;
        private String category;
        private String ttl;
        private String biTag;
        private Integer fastAppTargetType;
        private String data;
        private AndroidNotification notification;
        
        private Builder() {
        }
        
        public Builder setCollapseKey(final Integer collapseKey) {
            this.collapseKey = collapseKey;
            return this;
        }
        
        public Builder setUrgency(final String urgency) {
            this.urgency = urgency;
            return this;
        }
        
        public Builder setCategory(final String category) {
            this.category = category;
            return this;
        }
        
        public Builder setTtl(final String ttl) {
            this.ttl = ttl;
            return this;
        }
        
        public Builder setBiTag(final String biTag) {
            this.biTag = biTag;
            return this;
        }
        
        public Builder setFastAppTargetType(final Integer fastAppTargetType) {
            this.fastAppTargetType = fastAppTargetType;
            return this;
        }
        
        public Builder setData(final String data) {
            this.data = data;
            return this;
        }
        
        public Builder setNotification(final AndroidNotification notification) {
            this.notification = notification;
            return this;
        }
        
        public AndroidConfig build() {
            return new AndroidConfig(this);
        }
    }
}
