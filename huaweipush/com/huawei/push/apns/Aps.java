// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.apns;

import com.huawei.push.util.ValidatorUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class Aps
{
    @JSONField(name = "alert")
    private Object alert;
    @JSONField(name = "badge")
    private Integer badge;
    @JSONField(name = "sound")
    private String sound;
    @JSONField(name = "content-available")
    private Integer contentAvailable;
    @JSONField(name = "category")
    private String category;
    @JSONField(name = "thread-id")
    private String threadId;
    
    public Object getAlert() {
        return this.alert;
    }
    
    public Integer getBadge() {
        return this.badge;
    }
    
    public String getSound() {
        return this.sound;
    }
    
    public Integer getContentAvailable() {
        return this.contentAvailable;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public String getThreadId() {
        return this.threadId;
    }
    
    public void check() {
        if (this.alert != null) {
            if (this.alert instanceof Alert) {
                ((Alert)this.alert).check();
            }
            else {
                ValidatorUtils.checkArgument(this.alert instanceof String, "Alter should be Dictionary or String");
            }
        }
    }
    
    private Aps(final Builder builder) {
        this.alert = builder.alert;
        this.badge = builder.badge;
        this.sound = builder.sound;
        this.contentAvailable = builder.contentAvailable;
        this.category = builder.category;
        this.threadId = builder.threadId;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private Object alert;
        private Integer badge;
        private String sound;
        private Integer contentAvailable;
        private String category;
        private String threadId;
        
        public Builder setAlert(final Object alert) {
            this.alert = alert;
            return this;
        }
        
        public Builder setBadge(final Integer badge) {
            this.badge = badge;
            return this;
        }
        
        public Builder setSound(final String sound) {
            this.sound = sound;
            return this;
        }
        
        public Builder setContentAvailable(final Integer contentAvailable) {
            this.contentAvailable = contentAvailable;
            return this;
        }
        
        public Builder setCategory(final String category) {
            this.category = category;
            return this;
        }
        
        public Builder setThreadId(final String threadId) {
            this.threadId = threadId;
            return this;
        }
        
        public Aps build() {
            return new Aps(this, null);
        }
    }
}
