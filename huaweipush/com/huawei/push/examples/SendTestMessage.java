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
import com.huawei.push.model.Visibility;
import com.huawei.push.android.BadgeNotification;
import com.huawei.push.model.Importance;
import com.huawei.push.android.ClickAction;
import com.huawei.push.android.AndroidNotification;
import com.huawei.push.android.Color;
import com.huawei.push.android.LightSettings;
import com.alibaba.fastjson.JSONObject;
import com.huawei.push.message.Notification;
import com.huawei.push.messaging.HuaweiMessaging;
import com.huawei.push.util.InitAppUtils;

public class SendTestMessage
{
    public void sendTestMessage() throws HuaweiMesssagingException {
        final HuaweiApp app = InitAppUtils.initializeApp();
        final HuaweiMessaging huaweiMessaging = HuaweiMessaging.getInstance(app);
        final Notification notification = Notification.builder().setTitle("sample title").setBody("sample message body").build();
        final JSONObject multiLangKey = new JSONObject();
        final JSONObject titleKey = new JSONObject();
        titleKey.put("en", (Object)"\u597d\u53cb\u8bf7\u6c42");
        final JSONObject bodyKey = new JSONObject();
        titleKey.put("en", (Object)"My name is %s, I am from %s.");
        multiLangKey.put("key1", (Object)titleKey);
        multiLangKey.put("key2", (Object)bodyKey);
        final LightSettings lightSettings = LightSettings.builder().setColor(Color.builder().setAlpha(0.0f).setRed(0.0f).setBlue(1.0f).setGreen(1.0f).build()).setLightOnDuration("3.5").setLightOffDuration("5S").build();
        final AndroidNotification androidNotification = AndroidNotification.builder().setIcon("/raw/ic_launcher2").setColor("#AACCDD").setSound("/raw/shake").setDefaultSound(true).setTag("tagBoom").setClickAction(ClickAction.builder().setType(2).setUrl("https://www.huawei.com").build()).setBodyLocKey("M.String.body").addBodyLocArgs("boy").addBodyLocArgs("dog").setTitleLocKey("M.String.title").addTitleLocArgs("Girl").addTitleLocArgs("Cat").setChannelId("Your Channel ID").setNotifySummary("some summary").setMultiLangkey(multiLangKey).setStyle(1).setBigTitle("Big Boom Title").setBigBody("Big Boom Body").setAutoClear(86400000).setNotifyId(486).setGroup("Group1").setImportance(Importance.LOW.getValue()).setLightSettings(lightSettings).setBadge(BadgeNotification.builder().setAddNum(1).setBadgeClass("Classic").build()).setVisibility(Visibility.PUBLIC.getValue()).setForegroundShow(true).build();
        final AndroidConfig androidConfig = AndroidConfig.builder().setCollapseKey(-1).setUrgency(Urgency.HIGH.getValue()).setTtl("10000s").setBiTag("the_sample_bi_tag_for_receipt_service").setNotification(androidNotification).build();
        final Message message = Message.builder().setNotification(notification).setAndroidConfig(androidConfig).addToken("AND8rUp4etqJvbakK7qQoCVgFHnROXzH8o7B8fTl9rMP5VRFN83zU3Nvmabm3xw7e3gZjyBbp_wfO1jP-UyDQcZN_CtjBpoa7nx1WaVFe_3mqXMJ6nXJNUZcDyO_-k3sSw").build();
        final SendResponse response = huaweiMessaging.sendMessage(message, true);
    }
}
