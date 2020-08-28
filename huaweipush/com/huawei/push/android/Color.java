// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.android;

import com.huawei.push.util.ValidatorUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class Color
{
    private final float zero = -1.0E-6f;
    private final float one = 1.000001f;
    @JSONField(name = "alpha")
    private Float alpha;
    @JSONField(name = "red")
    private Float red;
    @JSONField(name = "green")
    private Float green;
    @JSONField(name = "blue")
    private Float blue;
    
    public Color(final Builder builder) {
        this.alpha = new Float(1.0);
        this.red = new Float(0.0);
        this.green = new Float(0.0);
        this.blue = new Float(0.0);
        this.alpha = builder.alpha;
        this.red = builder.red;
        this.green = builder.green;
        this.blue = builder.blue;
    }
    
    public double getAlpha() {
        return this.alpha;
    }
    
    public Float getRed() {
        return this.red;
    }
    
    public Float getGreen() {
        return this.green;
    }
    
    public Float getBlue() {
        return this.blue;
    }
    
    public void check() {
        ValidatorUtils.checkArgument(this.alpha > -1.0E-6f && this.alpha < 1.000001f, "Alpha shoube locate between [0,1]");
        ValidatorUtils.checkArgument(this.red > -1.0E-6f && this.red < 1.000001f, "Red shoube locate between [0,1]");
        ValidatorUtils.checkArgument(this.green > -1.0E-6f && this.green < 1.000001f, "Green shoube locate between [0,1]");
        ValidatorUtils.checkArgument(this.blue > -1.0E-6f && this.blue < 1.000001f, "Blue shoube locate between [0,1]");
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private Float alpha;
        private Float red;
        private Float green;
        private Float blue;
        
        public Builder() {
            this.alpha = new Float(1.0);
            this.red = new Float(0.0);
            this.green = new Float(0.0);
            this.blue = new Float(0.0);
        }
        
        public Builder setAlpha(final Float alpha) {
            this.alpha = alpha;
            return this;
        }
        
        public Builder setRed(final Float red) {
            this.red = red;
            return this;
        }
        
        public Builder setGreen(final Float green) {
            this.green = green;
            return this;
        }
        
        public Builder setBlue(final Float blue) {
            this.blue = blue;
            return this;
        }
        
        public Color build() {
            return new Color(this);
        }
    }
}
