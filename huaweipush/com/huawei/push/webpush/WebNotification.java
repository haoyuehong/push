// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.webpush;

import java.util.Collection;
import com.huawei.push.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Iterator;
import com.huawei.push.util.ValidatorUtils;
import java.util.Arrays;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class WebNotification
{
    private static String[] DIR_VALUE;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "body")
    private String body;
    @JSONField(name = "icon")
    private String icon;
    @JSONField(name = "image")
    private String image;
    @JSONField(name = "lang")
    private String lang;
    @JSONField(name = "tag")
    private String tag;
    @JSONField(name = "badge")
    private String badge;
    @JSONField(name = "dir")
    private String dir;
    @JSONField(name = "vibrate")
    private List<Object> vibrate;
    @JSONField(name = "renotify")
    private boolean renotify;
    @JSONField(name = "require_interaction")
    private boolean requireInteraction;
    @JSONField(name = "silent")
    private boolean silent;
    @JSONField(name = "timestamp")
    private Long timestamp;
    @JSONField(name = "actions")
    private List<WebActions> actions;
    
    public String getTitle() {
        return this.title;
    }
    
    public String getBody() {
        return this.body;
    }
    
    public String getIcon() {
        return this.icon;
    }
    
    public String getImage() {
        return this.image;
    }
    
    public String getLang() {
        return this.lang;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public String getBadge() {
        return this.badge;
    }
    
    public String getDir() {
        return this.dir;
    }
    
    public List<Object> getVibrate() {
        return this.vibrate;
    }
    
    public boolean isRenotify() {
        return this.renotify;
    }
    
    public boolean isRequireInteraction() {
        return this.requireInteraction;
    }
    
    public boolean isSilent() {
        return this.silent;
    }
    
    public Long getTimestamp() {
        return this.timestamp;
    }
    
    public List<WebActions> getActions() {
        return this.actions;
    }
    
    public void check() {
        if (this.dir != null) {
            ValidatorUtils.checkArgument(Arrays.stream(WebNotification.DIR_VALUE).anyMatch(value -> value.equals(this.dir)), "Invalid dir");
        }
        if (this.vibrate != null) {
            for (final Object obj : this.vibrate) {
                ValidatorUtils.checkArgument(obj instanceof Double || obj instanceof Integer, "Invalid vibrate");
            }
        }
    }
    
    public WebNotification(final Builder builder) {
        this.vibrate = new ArrayList<Object>();
        this.actions = new ArrayList<WebActions>();
        this.title = builder.title;
        this.body = builder.body;
        this.icon = builder.icon;
        this.image = builder.image;
        this.lang = builder.lang;
        this.tag = builder.tag;
        this.badge = builder.badge;
        this.dir = builder.dir;
        if (!CollectionUtils.isEmpty(builder.vibrate)) {
            this.vibrate.addAll(builder.vibrate);
        }
        else {
            this.vibrate = null;
        }
        this.renotify = builder.renotify;
        this.requireInteraction = builder.requireInteraction;
        this.silent = builder.silent;
        this.timestamp = builder.timestamp;
        if (!CollectionUtils.isEmpty(builder.actions)) {
            this.actions.addAll(builder.actions);
        }
        else {
            this.actions = null;
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    static {
        WebNotification.DIR_VALUE = new String[] { "auto", "ltr", "rtl" };
    }
    
    public static class Builder
    {
        private String title;
        private String body;
        private String icon;
        private String image;
        private String lang;
        private String tag;
        private String badge;
        private String dir;
        private List<Object> vibrate;
        private boolean renotify;
        private boolean requireInteraction;
        private boolean silent;
        private Long timestamp;
        private List<WebActions> actions;
        
        public Builder() {
            this.vibrate = new ArrayList<Object>();
            this.actions = new ArrayList<WebActions>();
        }
        
        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }
        
        public Builder setBody(final String body) {
            this.body = body;
            return this;
        }
        
        public Builder setIcon(final String icon) {
            this.icon = icon;
            return this;
        }
        
        public Builder setImage(final String image) {
            this.image = image;
            return this;
        }
        
        public Builder setLang(final String lang) {
            this.lang = lang;
            return this;
        }
        
        public Builder setTag(final String tag) {
            this.tag = tag;
            return this;
        }
        
        public Builder setBadge(final String badge) {
            this.badge = badge;
            return this;
        }
        
        public Builder setDir(final String dir) {
            this.dir = dir;
            return this;
        }
        
        public Builder addAllVibrate(final List<Object> vibrate) {
            this.vibrate.addAll(vibrate);
            return this;
        }
        
        public Builder addVibrate(final Object vibrate) {
            this.vibrate.add(vibrate);
            return this;
        }
        
        public Builder setRenotify(final boolean renotify) {
            this.renotify = renotify;
            return this;
        }
        
        public Builder setRequireInteraction(final boolean requireInteraction) {
            this.requireInteraction = requireInteraction;
            return this;
        }
        
        public Builder setSilent(final boolean silent) {
            this.silent = silent;
            return this;
        }
        
        public Builder setTimestamp(final Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Builder addAllActions(final List<WebActions> actions) {
            this.actions.addAll(actions);
            return this;
        }
        
        public Builder addAction(final WebActions action) {
            this.actions.add(action);
            return this;
        }
        
        public WebNotification build() {
            return new WebNotification(this);
        }
    }
}
