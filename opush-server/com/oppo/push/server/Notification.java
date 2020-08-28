// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

public class Notification
{
    private String appMessageId;
    private Integer style;
    private String bigPictureId;
    private String smallPictureId;
    private String title;
    private String subTitle;
    private String content;
    private String channelId;
    private Integer clickActionType;
    private String clickActionActivity;
    private String clickActionUrl;
    private String actionParameters;
    private Integer showTimeType;
    private Long showStartTime;
    private Long showEndTime;
    private Boolean offLine;
    private Integer offLineTtl;
    private Integer pushTimeType;
    private Long pushStartTime;
    private String timeZone;
    private Boolean fixSpeed;
    private Long fixSpeedRate;
    private Integer networkType;
    private String callBackUrl;
    private String callBackParameter;
    
    public Notification() {
        this.pushTimeType = 0;
    }
    
    public void setAppMessageId(final String appMessageId) {
        this.appMessageId = appMessageId;
    }
    
    public void setStyle(final Integer style) {
        this.style = style;
    }
    
    public void setBigPictureId(final String bigPictureId) {
        this.bigPictureId = bigPictureId;
    }
    
    public void setSmallPictureId(final String smallPictureId) {
        this.smallPictureId = smallPictureId;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public void setSubTitle(final String subTitle) {
        this.subTitle = subTitle;
    }
    
    public void setContent(final String content) {
        this.content = content;
    }
    
    public void setChannelId(final String channelId) {
        this.channelId = channelId;
    }
    
    public void setClickActionType(final Integer clickActionType) {
        this.clickActionType = clickActionType;
    }
    
    public void setClickActionActivity(final String clickActionActivity) {
        this.clickActionActivity = clickActionActivity;
    }
    
    public void setClickActionUrl(final String clickActionUrl) {
        this.clickActionUrl = clickActionUrl;
    }
    
    public void setActionParameters(final String actionParameters) {
        this.actionParameters = actionParameters;
    }
    
    public void setShowTimeType(final Integer showTimeType) {
        this.showTimeType = showTimeType;
    }
    
    public void setShowStartTime(final Long showStartTime) {
        this.showStartTime = showStartTime;
    }
    
    public void setShowEndTime(final Long showEndTime) {
        this.showEndTime = showEndTime;
    }
    
    public void setOffLine(final Boolean offLine) {
        this.offLine = offLine;
    }
    
    public void setOffLineTtl(final Integer offLineTtl) {
        this.offLineTtl = offLineTtl;
    }
    
    private void setPushTimeType(final Integer pushTimeType) {
        this.pushTimeType = pushTimeType;
    }
    
    private void setPushStartTime(final Long pushStartTime) {
        this.pushStartTime = pushStartTime;
    }
    
    public void setTimeZone(final String timeZone) {
        this.timeZone = timeZone;
    }
    
    private void setFixSpeed(final Boolean fixSpeed) {
        this.fixSpeed = fixSpeed;
    }
    
    private void setFixSpeedRate(final Long fixSpeedRate) {
        this.fixSpeedRate = fixSpeedRate;
    }
    
    public void setNetworkType(final Integer networkType) {
        this.networkType = networkType;
    }
    
    public void setCallBackUrl(final String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }
    
    public void setCallBackParameter(final String callBackParameter) {
        this.callBackParameter = callBackParameter;
    }
    
    public String getAppMessageId() {
        return this.appMessageId;
    }
    
    public Integer getStyle() {
        return this.style;
    }
    
    public String getBigPictureId() {
        return this.bigPictureId;
    }
    
    public String getSmallPictureId() {
        return this.smallPictureId;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getSubTitle() {
        return this.subTitle;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public String getChannelId() {
        return this.channelId;
    }
    
    public Integer getClickActionType() {
        return this.clickActionType;
    }
    
    public String getClickActionActivity() {
        return this.clickActionActivity;
    }
    
    public String getClickActionUrl() {
        return this.clickActionUrl;
    }
    
    public String getActionParameters() {
        return this.actionParameters;
    }
    
    public Integer getShowTimeType() {
        return this.showTimeType;
    }
    
    public Long getShowStartTime() {
        return this.showStartTime;
    }
    
    public Long getShowEndTime() {
        return this.showEndTime;
    }
    
    public Boolean getOffLine() {
        return this.offLine;
    }
    
    public Integer getOffLineTtl() {
        return this.offLineTtl;
    }
    
    Integer getPushTimeType() {
        return this.pushTimeType;
    }
    
    private Long getPushStartTime() {
        return this.pushStartTime;
    }
    
    public String getTimeZone() {
        return this.timeZone;
    }
    
    private Boolean getFixSpeed() {
        return this.fixSpeed;
    }
    
    private Long getFixSpeedRate() {
        return this.fixSpeedRate;
    }
    
    public Integer getNetworkType() {
        return this.networkType;
    }
    
    public String getCallBackUrl() {
        return this.callBackUrl;
    }
    
    public String getCallBackParameter() {
        return this.callBackParameter;
    }
    
    @Override
    public String toString() {
        return "Notification{appMessageId='" + this.appMessageId + '\'' + ", style=" + this.style + ", bigPictureId='" + this.bigPictureId + '\'' + ", smallPictureId='" + this.smallPictureId + '\'' + ", title='" + this.title + '\'' + ", subTitle='" + this.subTitle + '\'' + ", content='" + this.content + '\'' + ", channelId='" + this.channelId + '\'' + ", clickActionType=" + this.clickActionType + ", clickActionActivity='" + this.clickActionActivity + '\'' + ", clickActionUrl='" + this.clickActionUrl + '\'' + ", actionParameters='" + this.actionParameters + '\'' + ", showTimeType=" + this.showTimeType + ", showStartTime=" + this.showStartTime + ", showEndTime=" + this.showEndTime + ", offLine=" + this.offLine + ", offLineTtl=" + this.offLineTtl + ", pushTimeType=" + this.pushTimeType + ", pushStartTime=" + this.pushStartTime + ", timeZone='" + this.timeZone + '\'' + ", fixSpeed=" + this.fixSpeed + ", fixSpeedRate=" + this.fixSpeedRate + ", networkType=" + this.networkType + ", callBackUrl='" + this.callBackUrl + '\'' + ", callBackParameter='" + this.callBackParameter + '\'' + '}';
    }
    
    void validate() {
        Validate.nonNull(this.title, "title");
        Validate.validateLength(this.title, "title", 50);
        Validate.nonNull(this.content, "content");
        Validate.validateLength(this.content, "content", 200);
        if (this.offLine != null && this.offLine && this.offLineTtl != null) {
            Validate.validateSize(this.offLineTtl, "off_line_ttl", 864000);
        }
        if (!Validate.isEmpty(this.subTitle)) {
            Validate.validateLength(this.subTitle, "sub_title", 10);
        }
        if (this.style != null) {
            Validate.validateStyle(this.style);
        }
        if (this.style != null && this.style == 3) {
            Validate.nonNull(this.bigPictureId, "big_picture_id");
        }
        if (!Validate.isEmpty(this.clickActionActivity)) {
            Validate.validateLength(this.clickActionActivity, "click_action_activity", 500);
        }
        if (!Validate.isEmpty(this.clickActionUrl)) {
            Validate.validateLength(this.clickActionUrl, "click_action_url", 500);
        }
        if (!Validate.isEmpty(this.callBackParameter)) {
            Validate.validateLength(this.callBackParameter, "call_back_parameter", 50);
        }
    }
}
