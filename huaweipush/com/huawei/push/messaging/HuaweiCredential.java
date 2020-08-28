// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import org.slf4j.LoggerFactory;
import java.text.MessageFormat;
import org.apache.http.client.methods.CloseableHttpResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import java.io.IOException;
import org.apache.http.impl.client.HttpClients;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.impl.client.CloseableHttpClient;
import java.util.concurrent.locks.Lock;
import org.slf4j.Logger;

public class HuaweiCredential
{
    private static final Logger logger;
    private final String PUSH_AT_URL = "https://oauth-login.cloud.huawei.com/oauth2/v2/token";
    private String appId;
    private String appSecret;
    private String accessToken;
    private long expireIn;
    private Lock lock;
    private CloseableHttpClient httpClient;
    
    private HuaweiCredential(final Builder builder) {
        this.lock = new ReentrantLock();
        this.appId = builder.appId;
        this.appSecret = builder.appSecret;
        if (builder.httpClient == null) {
            this.httpClient = HttpClients.createDefault();
        }
        else {
            this.httpClient = builder.httpClient;
        }
    }
    
    public final void refreshToken() {
        try {
            this.executeRefresh();
        }
        catch (IOException e) {
            HuaweiCredential.logger.debug("Fail to refresh token!", (Throwable)e);
        }
    }
    
    private void executeRefresh() throws IOException {
        final String requestBody = this.createRequestBody(this.appId, this.appSecret);
        final HttpPost httpPost = new HttpPost("https://oauth-login.cloud.huawei.com/oauth2/v2/token");
        final StringEntity entity = new StringEntity(requestBody);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPost.setEntity((HttpEntity)entity);
        final CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)httpPost);
        final String jsonStr = EntityUtils.toString(response.getEntity());
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            final JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            this.accessToken = jsonObject.getString("access_token");
            this.expireIn = jsonObject.getLong("expires_in") * 1000L;
        }
        else {
            HuaweiCredential.logger.debug("Fail to refresh token!");
        }
    }
    
    private String createRequestBody(final String appId, final String appSecret) {
        return MessageFormat.format("grant_type=client_credentials&client_secret={0}&client_id={1}", appSecret, appId);
    }
    
    public final String getAccessToken() {
        this.lock.lock();
        String tmp;
        try {
            tmp = this.accessToken;
        }
        finally {
            this.lock.unlock();
        }
        return tmp;
    }
    
    public final long getExpireIn() {
        this.lock.lock();
        long tmp;
        try {
            tmp = this.expireIn;
        }
        finally {
            this.lock.unlock();
        }
        return tmp;
    }
    
    protected CloseableHttpClient getHttpClient() {
        return this.httpClient;
    }
    
    public String getAppId() {
        return this.appId;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)HuaweiCredential.class);
    }
    
    public static class Builder
    {
        private String appId;
        private String appSecret;
        private CloseableHttpClient httpClient;
        
        private Builder() {
        }
        
        public Builder setAppId(final String appId) {
            this.appId = appId;
            return this;
        }
        
        public Builder setAppSecret(final String appSecret) {
            this.appSecret = appSecret;
            return this;
        }
        
        public Builder setHttpClient(final CloseableHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }
        
        public HuaweiCredential build() {
            return new HuaweiCredential(this, null);
        }
    }
}
