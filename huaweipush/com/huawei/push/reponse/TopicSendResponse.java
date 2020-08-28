// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.reponse;

import com.alibaba.fastjson.JSONArray;

public final class TopicSendResponse extends SendResponse
{
    private final Integer failureCount;
    private final Integer successCount;
    private final JSONArray errors;
    
    public Integer getFailureCount() {
        return this.failureCount;
    }
    
    public Integer getSuccessCount() {
        return this.successCount;
    }
    
    public JSONArray getErrors() {
        return this.errors;
    }
    
    private TopicSendResponse(final String code, final String msg, final String requestId, final Integer failureCount, final Integer successCount, final JSONArray errors) {
        super(code, msg, requestId);
        this.failureCount = failureCount;
        this.successCount = successCount;
        this.errors = errors;
    }
    
    public static TopicSendResponse fromCode(final String code, final String msg, final String requestId, final Integer failureCount, final Integer successCount, final JSONArray errors) {
        return new TopicSendResponse(code, msg, requestId, failureCount, successCount, errors);
    }
}
