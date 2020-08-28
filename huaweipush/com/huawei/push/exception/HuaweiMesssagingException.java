// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.exception;

import com.huawei.push.util.ValidatorUtils;
import com.google.common.base.Strings;

public class HuaweiMesssagingException extends HuaweiException
{
    private final String errorCode;
    
    public HuaweiMesssagingException(final String errorCode, final String message) {
        super(message);
        ValidatorUtils.checkArgument(!Strings.isNullOrEmpty(errorCode));
        this.errorCode = errorCode;
    }
    
    public HuaweiMesssagingException(final String errorCode, final String message, final Throwable cause) {
        super(message, cause);
        ValidatorUtils.checkArgument(!Strings.isNullOrEmpty(errorCode));
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return this.errorCode;
    }
}
