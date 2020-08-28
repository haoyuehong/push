// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.android;

import com.huawei.push.util.ValidatorUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class LightSettings
{
    private static final String LIGTH_DURATION_PATTERN = "\\d+|\\d+[sS]|\\d+.\\d{1,9}|\\d+.\\d{1,9}[sS]";
    @JSONField(name = "color")
    private Color color;
    @JSONField(name = "light_on_duration")
    private String lightOnDuration;
    @JSONField(name = "light_off_duration")
    private String lightOffDuration;
    
    public LightSettings(final Builder builder) {
        this.color = builder.color;
        this.lightOnDuration = builder.lightOnDuration;
        this.lightOffDuration = builder.lightOffDuration;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public String getLightOnDuration() {
        return this.lightOnDuration;
    }
    
    public String getLightOffDuration() {
        return this.lightOffDuration;
    }
    
    public void check() {
        ValidatorUtils.checkArgument(this.color != null, "color must be selected when light_settings is set");
        if (this.color != null) {
            this.color.check();
        }
        ValidatorUtils.checkArgument(this.lightOnDuration != null, "light_on_duration must be selected when light_settings is set");
        ValidatorUtils.checkArgument(this.lightOffDuration != null, "light_off_duration must be selected when light_settings is set");
        ValidatorUtils.checkArgument(this.lightOnDuration.matches("\\d+|\\d+[sS]|\\d+.\\d{1,9}|\\d+.\\d{1,9}[sS]"), "light_on_duration format is wrong");
        ValidatorUtils.checkArgument(this.lightOffDuration.matches("\\d+|\\d+[sS]|\\d+.\\d{1,9}|\\d+.\\d{1,9}[sS]"), "light_off_duration format is wrong");
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private Color color;
        private String lightOnDuration;
        private String lightOffDuration;
        
        public Builder setColor(final Color color) {
            this.color = color;
            return this;
        }
        
        public Builder setLightOnDuration(final String lightOnDuration) {
            this.lightOnDuration = lightOnDuration;
            return this;
        }
        
        public Builder setLightOffDuration(final String lightOffDuration) {
            this.lightOffDuration = lightOffDuration;
            return this;
        }
        
        public LightSettings build() {
            return new LightSettings(this);
        }
    }
}
