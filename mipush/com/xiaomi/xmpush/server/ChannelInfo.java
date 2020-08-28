// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.io.Serializable;

public class ChannelInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String channelId;
    private String channelName;
    private String channelDesc;
    private String soundUrl;
    private Integer notifyType;
    
    protected ChannelInfo(final Builder builder) {
        this.channelId = builder.channelId;
        this.channelName = builder.channelName;
        this.channelDesc = builder.channelDesc;
        this.soundUrl = builder.soundUrl;
        this.notifyType = builder.notifyType;
    }
    
    public String getChannelId() {
        return this.channelId;
    }
    
    public String getChannelName() {
        return this.channelName;
    }
    
    public String getChannelDesc() {
        return this.channelDesc;
    }
    
    public String getSoundUrl() {
        return this.soundUrl;
    }
    
    public Integer getNotifyType() {
        return this.notifyType;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("ChannelInfo(");
        if (!XMStringUtils.isEmpty(this.channelId)) {
            builder.append("channelId=").append(this.channelId).append(", ");
        }
        if (!XMStringUtils.isEmpty(this.channelName)) {
            builder.append("channelName=").append(this.channelName).append(", ");
        }
        if (!XMStringUtils.isEmpty(this.channelDesc)) {
            builder.append("channelDesc=").append(this.channelDesc).append(", ");
        }
        if (!XMStringUtils.isEmpty(this.soundUrl)) {
            builder.append("soundUrl=").append(this.soundUrl).append(", ");
        }
        if (this.notifyType != null) {
            builder.append("notifyType=").append(this.notifyType);
        }
        builder.append(")");
        return builder.toString();
    }
    
    public static class Builder
    {
        private String channelId;
        private String channelName;
        private String channelDesc;
        private String soundUrl;
        private Integer notifyType;
        
        public Builder channelId(final String channelId) {
            this.channelId = channelId;
            return this;
        }
        
        public Builder channelName(final String channelName) {
            this.channelName = channelName;
            return this;
        }
        
        public Builder channelDesc(final String channelDesc) {
            this.channelDesc = channelDesc;
            return this;
        }
        
        public Builder soundUrl(final String soundUrl) {
            this.soundUrl = soundUrl;
            return this;
        }
        
        public Builder notifyType(final Integer notifyType) {
            this.notifyType = notifyType;
            return this;
        }
        
        public ChannelInfo build() {
            return new ChannelInfo(this);
        }
    }
}
