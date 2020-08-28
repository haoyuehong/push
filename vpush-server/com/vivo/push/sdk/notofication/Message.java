// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.notofication;

import com.vivo.push.sdk.server.Validation;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class Message implements Serializable
{
    private final String regId;
    private final String alias;
    private final int notifyType;
    private final String title;
    private final String content;
    private final int timeToLive;
    private final int skipType;
    private final String skipContent;
    private final int networkType;
    private Map<String, String> clientCustomMap;
    private final Map<String, String> extra;
    private final String requestId;
    private final Map<String, List<String>> tagExpression;
    private final int classification;
    private final int pushMode;
    
    protected Message(final Builder builder) {
        this.regId = builder.regId;
        this.alias = builder.alias;
        this.notifyType = builder.notifyType;
        this.title = builder.title;
        this.content = builder.content;
        this.timeToLive = builder.timeToLive;
        this.skipType = builder.skipType;
        this.skipContent = builder.skipContent;
        this.networkType = builder.networkType;
        this.extra = builder.extra;
        this.clientCustomMap = builder.clientCustomMap;
        this.requestId = builder.requestId;
        this.classification = builder.classification;
        this.pushMode = builder.pushMode;
        final Map<String, List<String>> map = new HashMap<String, List<String>>();
        if (builder.orTags != null) {
            map.put("orTags", builder.orTags);
        }
        else {
            map.put("orTags", new ArrayList<String>());
        }
        if (builder.andTags != null) {
            map.put("andTags", builder.andTags);
        }
        else {
            map.put("andTags", new ArrayList<String>());
        }
        if (builder.notTags != null) {
            map.put("notTags", builder.notTags);
        }
        else {
            map.put("notTags", new ArrayList<String>());
        }
        this.tagExpression = map;
    }
    
    public String getRegId() {
        return this.regId;
    }
    
    public String getAlias() {
        return this.alias;
    }
    
    public int getNotifyType() {
        return this.notifyType;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public int getTimeToLive() {
        return this.timeToLive;
    }
    
    public int getSkipType() {
        return this.skipType;
    }
    
    public String getSkipContent() {
        return this.skipContent;
    }
    
    public int getNetworkType() {
        return this.networkType;
    }
    
    public Map<String, String> getClientCustomMap() {
        return this.clientCustomMap;
    }
    
    public Map<String, String> getExtra() {
        return this.extra;
    }
    
    public String getRequestId() {
        return this.requestId;
    }
    
    public Map<String, List<String>> getTagExpression() {
        return this.tagExpression;
    }
    
    public int getClassification() {
        return this.classification;
    }
    
    public int getPushMode() {
        return this.pushMode;
    }
    
    public static final class Builder
    {
        private String regId;
        private String alias;
        private int notifyType;
        private String title;
        private String content;
        private int timeToLive;
        private int skipType;
        private String skipContent;
        private int networkType;
        private Map<String, String> clientCustomMap;
        private Map<String, String> extra;
        private String requestId;
        private List<String> orTags;
        private List<String> andTags;
        private List<String> notTags;
        private int classification;
        private int pushMode;
        
        public Builder() {
            this.networkType = -1;
            this.clientCustomMap = new LinkedHashMap<String, String>();
            this.extra = new LinkedHashMap<String, String>();
        }
        
        public Builder regId(final String value) {
            this.regId = value;
            return this;
        }
        
        public Builder alias(final String value) {
            this.alias = value;
            return this;
        }
        
        public Builder notifyType(final int value) {
            this.notifyType = value;
            return this;
        }
        
        public Builder title(String value) {
            value = value.trim();
            this.title = value;
            return this;
        }
        
        public Builder content(String value) {
            value = value.trim();
            this.content = value;
            return this;
        }
        
        public Builder timeToLive(final int value) {
            this.timeToLive = value;
            return this;
        }
        
        public Builder skipType(final int value) {
            this.skipType = value;
            return this;
        }
        
        public Builder skipContent(String value) {
            value = value.trim();
            this.skipContent = value;
            return this;
        }
        
        public Builder networkType(final int value) {
            this.networkType = value;
            return this;
        }
        
        public Builder clientCustomMap(final Map<String, String> map) {
            this.clientCustomMap = map;
            return this;
        }
        
        public Builder extra(final String callback, final String param) {
            this.extra.put("callback", callback);
            this.extra.put("callback.param", param);
            return this;
        }
        
        public Builder requestId(String value) {
            value = value.trim();
            this.requestId = value;
            return this;
        }
        
        public Builder orTags(final List<String> orTags) {
            this.orTags = orTags;
            return this;
        }
        
        public Builder andTags(final List<String> andTags) {
            this.andTags = andTags;
            return this;
        }
        
        public Builder notTags(final List<String> notTags) {
            this.notTags = notTags;
            return this;
        }
        
        public Builder classification(final int classification) {
            this.classification = classification;
            return this;
        }
        
        public Builder pushMode(final int pushMode) {
            this.pushMode = pushMode;
            return this;
        }
        
        public Message build() {
            final Message message = new Message(this);
            Validation.validatePublicMessage(message);
            return message;
        }
    }
}
