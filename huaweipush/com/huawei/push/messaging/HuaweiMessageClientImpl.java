// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.client.methods.CloseableHttpResponse;
import com.huawei.push.reponse.TopicSendResponse;
import com.huawei.push.reponse.TopicListResponse;
import com.huawei.push.util.ResponceCodeProcesser;
import com.huawei.push.model.TopicOperation;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpResponseException;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.HttpPost;
import com.huawei.push.message.TopicMessage;
import java.io.IOException;
import com.huawei.push.exception.HuaweiMesssagingException;
import com.huawei.push.reponse.SendResponse;
import com.huawei.push.message.Message;
import com.huawei.push.util.ValidatorUtils;
import java.text.MessageFormat;
import org.apache.http.impl.client.CloseableHttpClient;

public class HuaweiMessageClientImpl implements HuaweiMessageClient
{
    private static final String PUSH_URL = "https://push-api.cloud.huawei.com";
    private final String HcmPushUrl;
    private String hcmTopicUrl;
    private String hcmGroupUrl;
    private String hcmTokenUrl;
    private final CloseableHttpClient httpClient;
    
    private HuaweiMessageClientImpl(final Builder builder) {
        this.HcmPushUrl = MessageFormat.format("https://push-api.cloud.huawei.com/v1/{0}/messages:send", builder.appId);
        this.hcmTopicUrl = MessageFormat.format("https://push-api.cloud.huawei.com/v1/{0}/topic:{1}", builder.appId);
        ValidatorUtils.checkArgument(builder.httpClient != null, "requestFactory must not be null");
        this.httpClient = builder.httpClient;
    }
    
    public String getHcmSendUrl() {
        return this.HcmPushUrl;
    }
    
    public CloseableHttpClient getHttpClient() {
        return this.httpClient;
    }
    
    @Override
    public SendResponse send(final Message message, final boolean validateOnly, final String accessToken) throws HuaweiMesssagingException {
        try {
            return this.sendRequest(message, validateOnly, accessToken);
        }
        catch (IOException e) {
            throw new HuaweiMesssagingException("internal error", "Error while calling HCM backend service", e);
        }
    }
    
    @Override
    public SendResponse send(final TopicMessage message, final String operation, final String accessToken) throws HuaweiMesssagingException {
        try {
            return this.sendRequest(message, operation, accessToken);
        }
        catch (IOException e) {
            throw new HuaweiMesssagingException("internal error", "Error while calling HCM backend service", e);
        }
    }
    
    private SendResponse sendRequest(final TopicMessage message, final String operation, final String accessToken) throws IOException, HuaweiMesssagingException {
        this.hcmTopicUrl = MessageFormat.format(this.hcmTopicUrl, "", operation);
        final HttpPost httpPost = new HttpPost(this.hcmTopicUrl);
        final StringEntity entity = new StringEntity(JSON.toJSONString((Object)message), "UTF-8");
        httpPost.setHeader("Authorization", "Bearer " + accessToken);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setEntity((HttpEntity)entity);
        final CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)httpPost);
        final String rpsContent = EntityUtils.toString(response.getEntity());
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            final HttpResponseException exception = new HttpResponseException(statusCode, rpsContent);
            throw this.createExceptionFromResponse(exception);
        }
        final JSONObject jsonObject = JSONObject.parseObject(rpsContent);
        final String code = jsonObject.getString("code");
        final String msg = jsonObject.getString("msg");
        final String requestId = jsonObject.getString("requestId");
        if (StringUtils.equals((CharSequence)code, (CharSequence)"80000000")) {
            SendResponse sendResponse;
            if (StringUtils.equals((CharSequence)operation, (CharSequence)TopicOperation.LIST.getValue())) {
                final JSONArray topics = jsonObject.getJSONArray("topics");
                sendResponse = TopicListResponse.fromCode(code, ResponceCodeProcesser.process(Integer.valueOf(code)), requestId, topics);
            }
            else {
                final Integer failureCount = jsonObject.getInteger("failureCount");
                final Integer successCount = jsonObject.getInteger("successCount");
                final JSONArray errors = jsonObject.getJSONArray("errors");
                sendResponse = TopicSendResponse.fromCode(code, ResponceCodeProcesser.process(Integer.valueOf(code)), requestId, failureCount, successCount, errors);
            }
            return sendResponse;
        }
        final String errorMsg = MessageFormat.format("error code : {0}, error message : {1}", String.valueOf(code), ResponceCodeProcesser.process(Integer.valueOf(code)));
        throw new HuaweiMesssagingException("known error", errorMsg);
    }
    
    private SendResponse sendRequest(final Message message, final boolean validateOnly, final String accessToken) throws IOException, HuaweiMesssagingException {
        final Map<String, Object> map = this.createRequestMap(message, validateOnly);
        final HttpPost httpPost = new HttpPost(this.HcmPushUrl);
        final StringEntity entity = new StringEntity(JSON.toJSONString((Object)map), "UTF-8");
        httpPost.setHeader("Authorization", "Bearer " + accessToken);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setEntity((HttpEntity)entity);
        final CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)httpPost);
        final String rpsContent = EntityUtils.toString(response.getEntity());
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            final HttpResponseException exception = new HttpResponseException(statusCode, rpsContent);
            throw this.createExceptionFromResponse(exception);
        }
        final JSONObject jsonObject = JSONObject.parseObject(rpsContent);
        final String code = jsonObject.getString("code");
        final String msg = jsonObject.getString("msg");
        final String requestId = jsonObject.getString("requestId");
        if (StringUtils.equals((CharSequence)code, (CharSequence)"80000000")) {
            return SendResponse.fromCode(code, ResponceCodeProcesser.process(Integer.valueOf(code)), requestId);
        }
        final String errorMsg = MessageFormat.format("error code : {0}, error message : {1}", String.valueOf(code), ResponceCodeProcesser.process(Integer.valueOf(code)));
        throw new HuaweiMesssagingException("known error", errorMsg);
    }
    
    private Map<String, Object> createRequestMap(final Message message, final boolean validateOnly) {
        return new HashMap<String, Object>() {
            {
                ((HashMap<String, Boolean>)this).put("validate_only", validateOnly);
                ((HashMap<String, Message>)this).put("message", message);
            }
        };
    }
    
    private HuaweiMesssagingException createExceptionFromResponse(final HttpResponseException e) {
        final String msg = MessageFormat.format("Unexpected HTTP response with status : {0}, body : {1}", e.getStatusCode(), e.getMessage());
        return new HuaweiMesssagingException("unknown error", msg, (Throwable)e);
    }
    
    static HuaweiMessageClientImpl fromApp(final HuaweiApp app) {
        final String appId = ImplHuaweiTrampolines.getAppId(app);
        return builder().setAppId(appId).setHttpClient(app.getOption().getHttpClient()).build();
    }
    
    static Builder builder() {
        return new Builder();
    }
    
    static final class Builder
    {
        private String appId;
        private CloseableHttpClient httpClient;
        
        private Builder() {
        }
        
        public Builder setAppId(final String appId) {
            this.appId = appId;
            return this;
        }
        
        public Builder setHttpClient(final CloseableHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }
        
        public HuaweiMessageClientImpl build() {
            return new HuaweiMessageClientImpl(this, null);
        }
    }
}
