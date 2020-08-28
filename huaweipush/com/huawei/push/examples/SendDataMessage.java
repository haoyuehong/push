// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.examples;

import com.huawei.push.exception.HuaweiMesssagingException;
import com.huawei.push.reponse.SendResponse;
import com.huawei.push.messaging.HuaweiApp;
import com.huawei.push.message.Message;
import com.huawei.push.model.Urgency;
import com.huawei.push.message.AndroidConfig;
import com.huawei.push.messaging.HuaweiMessaging;
import com.huawei.push.util.InitAppUtils;

public class SendDataMessage
{
    public void sendTransparent() throws HuaweiMesssagingException {
        final HuaweiApp app = InitAppUtils.initializeApp();
        final HuaweiMessaging huaweiMessaging = HuaweiMessaging.getInstance(app);
        final AndroidConfig androidConfig = AndroidConfig.builder().setCollapseKey(-1).setUrgency(Urgency.HIGH.getValue()).setTtl("10000s").setBiTag("the_sample_bi_tag_for_receipt_service").build();
        final String token = "AND8rUp4etqJvbakK7qQoCVgFHnROXzH8o7B8fTl9rMP5VRFN83zU3Nvmabm3xw7e3gZjyBbp_wfO1jP-UyDQcZN_CtjBpoa7nx1WaVFe_3mqXMJ6nXJNUZcDyO_-k3sSw";
        final Message message = Message.builder().setData("{'k1':'v1', 'k2':'v2'}").setAndroidConfig(androidConfig).addToken(token).build();
        final SendResponse response = huaweiMessaging.sendMessage(message);
    }
}
