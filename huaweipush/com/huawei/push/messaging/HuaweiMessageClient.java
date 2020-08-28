// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import com.huawei.push.message.TopicMessage;
import com.huawei.push.exception.HuaweiMesssagingException;
import com.huawei.push.reponse.SendResponse;
import com.huawei.push.message.Message;

public interface HuaweiMessageClient
{
    SendResponse send(final Message p0, final boolean p1, final String p2) throws HuaweiMesssagingException;
    
    SendResponse send(final TopicMessage p0, final String p1, final String p2) throws HuaweiMesssagingException;
}
