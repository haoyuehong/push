// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.examples;

import com.huawei.push.exception.HuaweiMesssagingException;
import com.huawei.push.reponse.SendResponse;
import com.huawei.push.messaging.HuaweiApp;
import com.huawei.push.message.Message;
import com.huawei.push.message.WebPushConfig;
import com.huawei.push.webpush.WebHmsOptions;
import com.huawei.push.webpush.WebActions;
import com.huawei.push.webpush.WebNotification;
import com.huawei.push.webpush.WebpushHeaders;
import com.huawei.push.message.Notification;
import com.huawei.push.messaging.HuaweiMessaging;
import com.huawei.push.util.InitAppUtils;

public class SendWebpushMessage
{
    public void sendWebpushMessage() throws HuaweiMesssagingException {
        final HuaweiApp app = InitAppUtils.initializeApp();
        final HuaweiMessaging huaweiMessaging = HuaweiMessaging.getInstance(app);
        final Notification notification = Notification.builder().setTitle("Big News").setBody("This is a Big apple news1202").build();
        final WebpushHeaders webpushHeaders = WebpushHeaders.builder().setTtl("990").setUrgency("low").setTopic("12313").build();
        final WebNotification webNotification = WebNotification.builder().setTitle("Web Push Title").setBody("Web Push body").setIcon("https://developer-portalres-drcn.dbankcdn.com/system/modules/org.opencms.portal.template.core/\resources/images/icon_Promotion.png").addAction(WebActions.builder().setAction("click").setIcon("").setTitle("title").build()).setBadge("badge").setDir("auto").setImage("image url").setLang("en").setRenotify(false).setRequireInteraction(false).setSilent(true).setTag("tag").setTimestamp(32323L).addVibrate(1).addVibrate(2).addVibrate(3).build();
        final WebHmsOptions webHmsOptions = WebHmsOptions.builder().setLink("http://www.xx.com").build();
        final WebPushConfig webpush = WebPushConfig.builder().setHeaders(webpushHeaders).setNotification(webNotification).setWebHmsOptions(webHmsOptions).build();
        final String token = "cTW+APk7SomjRb2dOB7UIfyn_6q-hdNR8TfbkEcRus7fR2DrfXqS6EwINiuy1dhceiPXgE9t6rYkVNuRrcFcCPsCfNAIVR4N54Whfhow4r51hY05MB43r7461pls0qj9nhF4gQ";
        final Message message = Message.builder().setNotification(notification).setData("nb!").setWebpush(webpush).addToken(token).build();
        final SendResponse response = huaweiMessaging.sendMessage(message);
    }
}
