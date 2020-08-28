// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.android;

import com.huawei.push.util.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class Button
{
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "action_type")
    private Integer actionType;
    @JSONField(name = "intent_type")
    private Integer intentType;
    @JSONField(name = "intent")
    private String intent;
    @JSONField(name = "data")
    private String data;
    
    public String getName() {
        return this.name;
    }
    
    public Integer getActionType() {
        return this.actionType;
    }
    
    public Integer getIntentType() {
        return this.intentType;
    }
    
    public String getIntent() {
        return this.intent;
    }
    
    public String getData() {
        return this.data;
    }
    
    public Button(final Builder builder) {
        this.name = builder.name;
        this.actionType = builder.actionType;
        this.intentType = builder.intentType;
        this.intent = builder.intent;
        this.data = builder.data;
    }
    
    public void check() {
        if (this.actionType != null && this.actionType == 4) {
            ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)this.data), "data is needed when actionType is 4");
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "actionType")
        private Integer actionType;
        @JSONField(name = "intentType")
        private Integer intentType;
        @JSONField(name = "intent")
        private String intent;
        @JSONField(name = "data")
        private String data;
        
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder setActionType(final Integer actionType) {
            this.actionType = actionType;
            return this;
        }
        
        public Builder setIntentType(final Integer intentType) {
            this.intentType = intentType;
            return this;
        }
        
        public Builder setIntent(final String intent) {
            this.intent = intent;
            return this;
        }
        
        public Builder setData(final String data) {
            this.data = data;
            return this;
        }
        
        public Button build() {
            return new Button(this);
        }
    }
}
