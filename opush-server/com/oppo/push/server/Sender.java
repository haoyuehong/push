// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Map;
import com.alibaba.fastjson.JSON;

public class Sender
{
    private String appKey;
    private String masterSecret;
    private String token;
    private Long createTime;
    
    public Sender(final String appKey, final String masterSecret) throws Exception {
        this.appKey = appKey;
        this.masterSecret = masterSecret;
        try {
            HttpClientTool.init();
            this.setToken(this.appKey, this.masterSecret);
        }
        catch (Exception e) {
            throw e;
        }
    }
    
    private void setToken(final String appKey, final String masterSecret) throws Exception {
        final Result result = Auth.getAuthResult(appKey, masterSecret);
        if (result != null && !Validate.isEmpty(result.getToken())) {
            this.token = result.getToken();
            this.createTime = result.getCreateTime();
        }
    }
    
    public Result unicastNotification(final Notification notification, final Target target) throws Exception {
        notification.validate();
        final Map<String, Object> message = MessageTool.newMessage(target);
        message.put("notification", MessageTool.getNotification(notification));
        final Map<String, Object> body = MessageTool.newBody(this.token);
        body.put("message", JSON.toJSONString((Object)message));
        return HttpClientTool.httpPostWithToken(RequestPath.NOTIFICATION_UNICAST, body, this);
    }
    
    public Result broadcastNotification(final String messageId, final Target target) throws Exception {
        final Map<String, Object> body = MessageTool.newBody(target, this.token);
        MessageTool.setMessageId(body, messageId);
        return HttpClientTool.httpPostWithToken(RequestPath.NOTIFICATION_BROADCAST, body, this);
    }
    
    public Result uploadBigPicture(final Integer pictureTtl, final File file) throws Exception {
        final Map<String, Object> body = MessageTool.newBody(this.token);
        MessageTool.setPictureTtl(body, pictureTtl);
        Validate.validatePictureType(file);
        Validate.validateFileMaxSize(file, 1048576L);
        return HttpClientTool.multipartHttpPostWithToken(RequestPath.UPLOAD_BIG_PICTURE, body, file, this);
    }
    
    public Result uploadSmallPicture(final Integer pictureTtl, final File file) throws Exception {
        final Map<String, Object> body = MessageTool.newBody(this.token);
        MessageTool.setPictureTtl(body, pictureTtl);
        Validate.validatePictureType(file);
        Validate.validateFileMaxSize(file, 51200L);
        return HttpClientTool.multipartHttpPostWithToken(RequestPath.UPLOAD_SMALL_PICTURE, body, file, this);
    }
    
    public Result saveNotification(final Notification notification) throws Exception {
        notification.validate();
        final Map<String, Object> body = MessageTool.newBody(this.token);
        body.putAll(MessageTool.getNotification(notification));
        return HttpClientTool.httpPostWithToken(RequestPath.NOTIFICATION_SAVE_MESSAGE, body, this);
    }
    
    public Result unicastBatchNotification(final Map<Target, Notification> notificationMessages) throws Exception {
        final Map<String, Object> body = MessageTool.newBody(this.token);
        final List<Map<String, Object>> messages = new ArrayList<Map<String, Object>>();
        for (final Map.Entry<Target, Notification> entry : notificationMessages.entrySet()) {
            final Target target = entry.getKey();
            final Notification notification = entry.getValue();
            try {
                notification.validate();
                final Map<String, Object> message = MessageTool.newMessage(target);
                message.put("notification", MessageTool.getNotification(notification));
                messages.add(message);
            }
            catch (IllegalArgumentException e) {
                final String string = String.format("%s    target:%s  notification:%s ", e.getMessage(), JSON.toJSON((Object)target), notification);
                final IllegalArgumentException illegalArgumentException = new IllegalArgumentException(string, e);
                throw illegalArgumentException;
            }
        }
        body.put("messages", JSON.toJSONString((Object)messages));
        return HttpClientTool.httpPostWithToken(RequestPath.NOTIFICATION_UNICAST_BATCH, body, this);
    }
    
    String getAppKey() {
        return this.appKey;
    }
    
    String getMasterSecret() {
        return this.masterSecret;
    }
    
    void setToken(final String token) {
        this.token = token;
    }
    
    void setCreateTime(final Long createTime) {
        this.createTime = createTime;
    }
}
