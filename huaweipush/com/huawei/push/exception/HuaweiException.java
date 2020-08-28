// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.exception;

import com.huawei.push.util.ValidatorUtils;
import com.google.common.base.Strings;

public class HuaweiException extends Exception
{
    public HuaweiException(final String detailMessage) {
        super(detailMessage);
        ValidatorUtils.checkArgument(!Strings.isNullOrEmpty(detailMessage), "Detail message must not be empty");
    }
    
    public HuaweiException(final String detailMessage, final Throwable cause) {
        super(detailMessage, cause);
        ValidatorUtils.checkArgument(!Strings.isNullOrEmpty(detailMessage), "Detail message must not be empty");
    }
}
