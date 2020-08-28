// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.server;

import java.util.Map;
import com.vivo.push.sdk.common.SignUtils;
import java.util.HashMap;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import com.vivo.push.sdk.notofication.TargetMessage;
import com.vivo.push.sdk.notofication.Message;
import com.vivo.push.sdk.notofication.Result;
import com.vivo.push.sdk.common.HttpUtil;

public class Sender extends HttpUtil
{
    public Sender(final String secret) throws Exception {
        super(Validation.nonNull(secret));
        init();
    }
    
    public void initPool(final int connection, final int route) throws Exception {
        HttpUtil.init(connection, route);
    }
    
    public Result getToken(final int appId, final String appKey) throws Exception {
        Result result = null;
        result = this.setTokenBody(appId, appKey);
        return result;
    }
    
    public Result sendSingle(final Message singleMessage) throws Exception {
        Validation.validateSingleMessage(singleMessage);
        Result result = null;
        result = HttpUtil.sendMessage(this.doPost(this.toJsonString(singleMessage), "/message/send"));
        return result;
    }
    
    public Result saveListPayLoad(final Message listPayLoad) throws Exception {
        Validation.validateLiveTime(listPayLoad);
        Result result = null;
        result = HttpUtil.sendMessage(this.doPost(this.toJsonString(listPayLoad), "/message/saveListPayload"));
        return result;
    }
    
    public Result sendToList(final TargetMessage targetMessage) throws Exception {
        Result result = null;
        final String jsonBody = JSON.toJSONString((Object)targetMessage);
        result = HttpUtil.sendMessage(this.doPost(jsonBody, "/message/pushToList"));
        return result;
    }
    
    public Result sendToAll(final Message allMessage) throws Exception {
        Validation.validateLiveTime(allMessage);
        Result result = null;
        result = HttpUtil.sendMessage(this.doPost(this.toJsonString(allMessage), "/message/all"));
        return result;
    }
    
    public Result sendToTag(final Message tagMessage) throws Exception {
        Validation.validateTagExpression(tagMessage.getTagExpression());
        Result result = null;
        result = HttpUtil.sendMessage(this.doPost(this.toJsonString(tagMessage), "/message/tagPush"));
        return result;
    }
    
    public Result getStatistics(final Set<String> taskIds) throws Exception {
        Validation.validateTaskIds(taskIds);
        Result result = null;
        result = HttpUtil.sendMessage(this.doGet(this.toUrl("/report/getStatistics", taskIds)));
        return result;
    }
    
    private String toUrl(final String requestPath, final Set<String> taskId) throws IOException {
        final StringBuilder builder = new StringBuilder(requestPath).append("?taskIds=");
        final Iterator<String> iterator = taskId.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
    
    private String toJsonString(final Message message) throws IOException {
        final String jsonBody = JSON.toJSONString((Object)message);
        return jsonBody;
    }
    
    private Result setTokenBody(final int appId, final String appKey) throws Exception {
        final Map<String, Object> body = new HashMap<String, Object>(4);
        final long timestamp = System.currentTimeMillis();
        body.put("appId", appId);
        body.put("appKey", appKey);
        body.put("timestamp", timestamp);
        body.put("sign", SignUtils.getSign(appId, appKey, timestamp, this.secret));
        final String jsonBody = JSON.toJSONString((Object)body);
        final Result result = HttpUtil.sendMessage(this.doPost(jsonBody, "/message/auth"));
        return result;
    }
}
