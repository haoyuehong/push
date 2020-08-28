// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.reponse;

public class SendResponse
{
    private final String code;
    private final String msg;
    private final String requestId;
    
    protected SendResponse(final String coede, final String msg, final String requestId) {
        this.code = coede;
        this.msg = msg;
        this.requestId = requestId;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public String getRequestId() {
        return this.requestId;
    }
    
    public static SendResponse fromCode(final String coede, final String msg, final String requestId) {
        return new SendResponse(coede, msg, requestId);
    }
}
