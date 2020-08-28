// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.model;

public class PushMsgVo
{
    private Integer pushType;
    private String title;
    private String content;
    private String actionParameters;
    private Integer clickActionType;
    
    public Integer getPushType() {
        return this.pushType;
    }
    
    public void setPushType(final Integer pushType) {
        this.pushType = pushType;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(final String content) {
        this.content = content;
    }
    
    public String getActionParameters() {
        return this.actionParameters;
    }
    
    public void setActionParameters(final String actionParameters) {
        this.actionParameters = actionParameters;
    }
    
    public Integer getClickActionType() {
        return this.clickActionType;
    }
    
    public void setClickActionType(final Integer clickActionType) {
        this.clickActionType = clickActionType;
    }
}
