// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.examples;

import com.huawei.push.exception.HuaweiMesssagingException;
import com.huawei.push.reponse.SendResponse;
import com.huawei.push.messaging.HuaweiApp;
import com.huawei.push.message.Message;
import com.huawei.push.message.AndroidConfig;
import com.alibaba.fastjson.JSONObject;
import com.huawei.push.messaging.HuaweiMessaging;
import com.huawei.push.util.InitAppUtils;

public class SendInstanceAppMessage
{
    public void sendInstanceAppMessage() throws HuaweiMesssagingException {
        final HuaweiApp app = InitAppUtils.initializeApp();
        final HuaweiMessaging huaweiMessaging = HuaweiMessaging.getInstance(app);
        final String token = "AI838_-IxzMqKqeIoIqFgL9D5N8YunVqZXFU3jCohcmEkb1RMquoT7uxQkv3cXCv7IXwXjTsH0WK35DRrnLI6RBOWxqjnRqkbp6W5CFQj0zw09FG5sTuyZd2NHtxgVzUUg";
        final JSONObject params = new JSONObject();
        params.put("key1", (Object)"test1");
        params.put("key2", (Object)"test2");
        final JSONObject ringtone = new JSONObject();
        ringtone.put("vibration", (Object)"true");
        ringtone.put("breathLight", (Object)"true");
        final JSONObject msgBody = new JSONObject();
        msgBody.put("title", (Object)"Welcome to use Huawei HMS Push Kit");
        msgBody.put("description", (Object)"One of the best push platform on the planet!!!");
        msgBody.put("page", (Object)"");
        msgBody.put("params", (Object)params);
        msgBody.put("ringtone", (Object)ringtone);
        final JSONObject msg = new JSONObject();
        msg.put("pushtype", (Object)0);
        msg.put("pushbody", (Object)msgBody);
        final String data = msg.toJSONString();
        final AndroidConfig androidConfig = AndroidConfig.builder().setFastAppTargetType(1).build();
        final Message message = Message.builder().setData(data).setAndroidConfig(androidConfig).addToken(token).build();
        final SendResponse response = huaweiMessaging.sendMessage(message);
    }
}
