// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.examples;

import com.huawei.push.exception.HuaweiMesssagingException;
import com.huawei.push.reponse.SendResponse;
import com.huawei.push.messaging.HuaweiApp;
import com.huawei.push.message.Message;
import com.huawei.push.message.ApnsConfig;
import com.huawei.push.apns.ApnsHmsOptions;
import com.huawei.push.apns.Aps;
import com.huawei.push.apns.Alert;
import com.huawei.push.apns.ApnsHeaders;
import com.huawei.push.messaging.HuaweiMessaging;
import com.huawei.push.util.InitAppUtils;

public class SendApnsMessage
{
    public void sendApnsMessage() throws HuaweiMesssagingException {
        final HuaweiApp app = InitAppUtils.initializeApp();
        final HuaweiMessaging huaweiMessaging = HuaweiMessaging.getInstance(app);
        final ApnsHeaders apnsHeaders = ApnsHeaders.builder().setApnsId("123e4567-e89b-12d3-a456-426655440000").build();
        final Alert altert = Alert.builder().setTitle("titile").setBody("body").setLaunchImage("image").build();
        final Aps aps = Aps.builder().setAlert(altert).setBadge(1).setSound("wtewt.mp4").setContentAvailable(1).setCategory("category").setThreadId("id").build();
        final ApnsHmsOptions apnsHmsOptions = ApnsHmsOptions.builder().setTargetUserType(1).build();
        final ApnsConfig apns = ApnsConfig.builder().setApnsHeaders(apnsHeaders).addPayloadAps(aps).addPayload("acme_account", "jane.appleseed@apple.com").addPayload("acme_message", "message123456").setHmsOptions(apnsHmsOptions).build();
        final Message message = Message.builder().setApns(apns).addToken("9FDA406A04BDE017A2F53EB9831846FBF5308567DE9A4E986D96512136F72C3D").build();
        final SendResponse response = huaweiMessaging.sendMessage(message);
    }
}
