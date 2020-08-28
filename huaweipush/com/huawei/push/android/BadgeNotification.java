// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.android;

import com.huawei.push.util.ValidatorUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class BadgeNotification
{
    @JSONField(name = "add_num")
    private Integer addNum;
    @JSONField(name = "class")
    private String badgeClass;
    @JSONField(name = "set_num")
    private Integer setNum;
    
    public Integer getAddNum() {
        return this.addNum;
    }
    
    public String getBadgeClass() {
        return this.badgeClass;
    }
    
    public Integer getSetNum() {
        return this.setNum;
    }
    
    public BadgeNotification(final Integer addNum, final String badgeClass) {
        this.addNum = builder().addNum;
        this.badgeClass = badgeClass;
    }
    
    public BadgeNotification(final Builder builder) {
        this.addNum = builder.addNum;
        this.badgeClass = builder.badgeClass;
        this.setNum = builder.setNum;
    }
    
    public void check() {
        if (this.addNum != null) {
            ValidatorUtils.checkArgument(this.addNum > 0 && this.addNum < 100, "add_num should locate between 0 and 100");
        }
        if (this.setNum != null) {
            ValidatorUtils.checkArgument(this.setNum >= 0 && this.setNum < 100, "set_num should locate between 0 and 100");
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private Integer addNum;
        private String badgeClass;
        private Integer setNum;
        
        public Builder setAddNum(final Integer addNum) {
            this.addNum = addNum;
            return this;
        }
        
        public Builder setSetNum(final Integer setNum) {
            this.setNum = setNum;
            return this;
        }
        
        public Builder setBadgeClass(final String badgeClass) {
            this.badgeClass = badgeClass;
            return this;
        }
        
        public BadgeNotification build() {
            return new BadgeNotification(this);
        }
    }
}
