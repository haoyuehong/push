// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.notofication;

import com.vivo.push.sdk.server.Validation;
import java.util.Set;
import java.io.Serializable;

public class TargetMessage implements Serializable
{
    private final Set<String> regIds;
    private final Set<String> aliases;
    private final String taskId;
    private final String requestId;
    private final int pushMode;
    
    protected TargetMessage(final Builder builder) {
        this.regIds = builder.regIds;
        this.aliases = builder.aliases;
        this.taskId = builder.taskId;
        this.requestId = builder.requestId;
        this.pushMode = builder.pushMode;
    }
    
    public Set<String> getRegIds() {
        return this.regIds;
    }
    
    public Set<String> getAliases() {
        return this.aliases;
    }
    
    public String getTaskId() {
        return this.taskId;
    }
    
    public String getRequestId() {
        return this.requestId;
    }
    
    public int getPushMode() {
        return this.pushMode;
    }
    
    public static final class Builder
    {
        private Set<String> regIds;
        private Set<String> aliases;
        private String taskId;
        private String requestId;
        private int pushMode;
        
        public Builder regIds(final Set<String> regIds) {
            this.regIds = regIds;
            return this;
        }
        
        public Builder aliases(final Set<String> aliases) {
            this.aliases = aliases;
            return this;
        }
        
        public Builder taskId(final String taskId) {
            this.taskId = taskId;
            return this;
        }
        
        public Builder requestId(final String requestId) {
            this.requestId = requestId;
            return this;
        }
        
        public Builder pushMode(final int pushMode) {
            this.pushMode = pushMode;
            return this;
        }
        
        public TargetMessage build() {
            final TargetMessage targetMessage = new TargetMessage(this);
            Validation.validateTargetMessage(targetMessage);
            return targetMessage;
        }
    }
}
