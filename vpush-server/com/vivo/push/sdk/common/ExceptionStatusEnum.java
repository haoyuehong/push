// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.common;

public enum ExceptionStatusEnum
{
    ALIAS_AND_REGID_EMPTY(10050, "alias\u548cregId \u4e0d\u80fd\u90fd\u4e3a\u7a7a"), 
    NOTIFY_TYPE_INVALID(10054, "notifyType \u4e0d\u5408\u6cd5"), 
    TITLE_EMPTY(10055, "title \u4e0d\u80fd\u4e3a\u7a7a"), 
    TITLE_LENGTH_INVALID(10056, "title \u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc740\u4e2a\u5b57\u7b26"), 
    CONTENT_EMPTY(10057, "content \u4e0d\u80fd\u4e3a\u7a7a"), 
    CONTENT_LENGTH_INVALID(10058, "content \u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7100\u4e2a\u5b57\u7b26"), 
    TIME_TO_LIVE_INVALID(10059, "timeToLive \u4e0d\u5408\u6cd5"), 
    SKIP_TYPE_INVALID(10060, "skipType \u4e0d\u5408\u6cd5"), 
    SKIP_TYPE_2_SKIP_CONTENT_EMPTY(10061, "skipType = 2, skipContent \u4e0d\u80fd\u4e3a\u7a7a"), 
    SKIP_TYPE_2_SKIP_CONTENT_LENGTH_INVALID(10062, "skipType = 2, skipContent \u4e0d\u80fd\u8d85\u8fc71000\u4e2a\u5b57\u7b26"), 
    SKIP_TYPE_3_SKIP_CONTENT_EMPTY(10063, "skipType = 3, skipContent \u4e0d\u80fd\u4e3a\u7a7a"), 
    SKIP_TYPE_3_SKIP_CONTENT_LENGTH_INVALID(10064, "skipType = 3, skipContent \u4e0d\u80fd\u8d85\u8fc71024\u4e2a\u5b57\u7b26"), 
    NETWORK_TYPE_INVALID(10065, "networkType \u4e0d\u5408\u6cd5"), 
    CLIENT_CUSTOM_MAP_SIZE_INVALID(10066, "\u81ea\u5b9a\u4e49key\u548cValue\u952e\u503c\u5bf9\u4e2a\u6570\u4e0d\u80fd\u8d85\u8fc710\u4e2a"), 
    CLIENT_CUSTOM_MAP_INVALID(10067, "\u81ea\u5b9a\u4e49key\u548cvalue\u952e\u503c\u5bf9\u4e0d\u5408\u6cd5"), 
    SKIP_TYPE_4_SKIP_CONTENT_EMPTY(10068, "skipType = 4, skipContent \u4e0d\u80fd\u4e3a\u7a7a"), 
    SKIP_TYPE_4_SKIP_CONTENT_LENGTH_INVALID(10069, "skipType = 4, skipContent \u4e0d\u80fd\u8d85\u8fc71024\u4e2a\u5b57\u7b26"), 
    ALIASES_AND_REGIDS_EMPTY(10150, "aliases \u548c regIds \u4e0d\u80fd\u90fd\u4e3a\u7a7a"), 
    TASK_ID_EMPTY(10151, "taskId \u4e0d\u80fd\u4e3a\u7a7a"), 
    REGIDS_SIZE_INVALID(10153, "regIds \u4e2a\u6570\u4e0d\u5728\u6307\u5b9a\u8303\u56f4[2-1000]"), 
    ALIASES_SIZE_INVALID(10154, "aliases \u4e2a\u6570\u4e0d\u5728\u6307\u5b9a\u8303\u56f4[2-1000]"), 
    ALIAS_LENGTH_INVALID(10301, "alias \u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc740\u4e2a\u5b57\u7b26"), 
    REGID_INVALID(10302, "regId \u4e0d\u5408\u6cd5"), 
    EXTRA_CALLBACK_LENGTH_INVALID(10305, "extra callback\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7128\u4e2a\u5b57\u7b26"), 
    EXTRA_CALLBACKPARAM_LENGTH_INVALID(10306, "extra callback.param\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc764\u4e2a\u5b57\u7b26"), 
    REQUEST_ID_EMPTY(10352, "requestId \u4e0d\u80fd\u4e3a\u7a7a"), 
    REQUEST_ID_LENGTH_INVALID(10353, "requestId \u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc764\u4e2a\u5b57\u7b26"), 
    TASK_IDS_SIZE_INVALID(10471, "taskIds \u6570\u91cf\u4e0d\u80fd\u8d85\u8fc7100\u4e2a"), 
    TASK_IDS_EMPTY(10473, "taskIds \u4e3a\u7a7a"), 
    TAG_NAME_EMPTY(10601, "name\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a"), 
    IDS_EMPTY(10609, "ids\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a"), 
    IDS_OVER_LIMIT(10614, "ids\u6570\u91cf\u4e0d\u80fd\u8d85\u8fc71000"), 
    TYPE_INVALID(10616, "type\u53c2\u6570\u4e0d\u5408\u6cd5"), 
    CLASSIFICATION_ERROR(11000, "\u6d88\u606f\u7c7b\u578b\u9519\u8bef");
    
    private int code;
    private String message;
    
    private ExceptionStatusEnum(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(final int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}
