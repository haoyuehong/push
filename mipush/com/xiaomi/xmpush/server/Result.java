// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import org.json.simple.JSONObject;
import com.xiaomi.push.sdk.ErrorCode;
import java.io.Serializable;

public final class Result implements Serializable
{
    private static final long serialVersionUID = 8030699726843716781L;
    private final String messageId;
    private final String traceId;
    private final ErrorCode errorCode;
    private final JSONObject data;
    private final String reason;
    
    private Result(final Builder builder) {
        this.messageId = builder.messageId;
        this.data = builder.data;
        this.errorCode = builder.errorCode;
        this.reason = builder.reason;
        this.traceId = builder.traceId;
    }
    
    public String getMessageId() {
        return this.messageId;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
    
    public JSONObject getData() {
        return this.data;
    }
    
    public String getData(final String key) {
        if (this.data == null) {
            return null;
        }
        if (this.data.containsKey((Object)key)) {
            return (String)this.data.get((Object)key);
        }
        return null;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("[");
        if (this.messageId != null) {
            builder.append(" messageId=").append(this.messageId);
        }
        if (this.errorCode != null) {
            builder.append(" errorCode=").append(this.errorCode.getValue());
        }
        if (this.reason != null) {
            builder.append(" reason=").append(this.reason);
        }
        if (this.data != null) {
            builder.append(" data=").append(this.data.toString());
        }
        if (this.traceId != null) {
            builder.append(" trace_id=").append(this.traceId);
        }
        return builder.append(" ]").toString();
    }
    
    public static final class Builder
    {
        private String messageId;
        private String traceId;
        private JSONObject data;
        private ErrorCode errorCode;
        private String reason;
        
        public Builder() {
            this.traceId = null;
        }
        
        public Result fromJson(final JSONObject json) {
            if (json.containsKey((Object)"data")) {
                this.data = (JSONObject)json.get((Object)"data");
            }
            this.reason = (String)json.get((Object)"reason");
            final Integer code = ((Long)json.get((Object)"code")).intValue();
            this.errorCode = ErrorCode.valueOf(code, this.reason);
            this.messageId = ((this.data == null) ? null : ((String)this.data.get((Object)"id")));
            if (json.containsKey((Object)"trace_id")) {
                this.traceId = (String)json.get((Object)"trace_id");
            }
            if ((this.messageId == null || this.messageId.length() == 0) && this.traceId != null) {
                this.messageId = (String)json.get((Object)"trace_id");
            }
            return this.build();
        }
        
        public Builder messageId(final String msgId) {
            this.messageId = msgId;
            return this;
        }
        
        public Builder errorCode(final ErrorCode value) {
            this.errorCode = value;
            return this;
        }
        
        public Result build() {
            return new Result(this, null);
        }
    }
}
