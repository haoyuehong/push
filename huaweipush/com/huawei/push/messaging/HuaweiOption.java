// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import com.huawei.push.util.IgnoreSSLUtils;
import org.slf4j.LoggerFactory;
import com.huawei.push.util.ValidatorUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;

public class HuaweiOption
{
    private static final Logger logger;
    private final HuaweiCredential credential;
    private final CloseableHttpClient httpClient;
    private final ThreadManager threadManager;
    
    private HuaweiOption(final Builder builder) {
        ValidatorUtils.checkArgument(builder.credential != null, "HuaweiOption must be initialized with setCredential()");
        this.credential = builder.credential;
        ValidatorUtils.checkArgument(builder.httpClient != null, "HuaweiOption must be initialized with a non-null httpClient");
        this.httpClient = builder.httpClient;
        ValidatorUtils.checkArgument(builder.threadManager != null, "HuaweiOption must be initialized with a non-null threadManager");
        this.threadManager = builder.threadManager;
    }
    
    public HuaweiCredential getCredential() {
        return this.credential;
    }
    
    public CloseableHttpClient getHttpClient() {
        return this.httpClient;
    }
    
    public ThreadManager getThreadManager() {
        return this.threadManager;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)HuaweiOption.class);
    }
    
    public static final class Builder
    {
        private HuaweiCredential credential;
        private CloseableHttpClient httpClient;
        private ThreadManager threadManager;
        
        public Builder() {
            try {
                this.httpClient = IgnoreSSLUtils.createClient();
            }
            catch (KeyManagementException | NoSuchAlgorithmException ex2) {
                final GeneralSecurityException ex;
                final GeneralSecurityException e = ex;
                HuaweiOption.logger.debug("Fail to create httpClient for sending message", (Throwable)e);
            }
            this.threadManager = HuaweiThreadManager.DEFAULT_THREAD_MANAGER;
        }
        
        public Builder setCredential(final HuaweiCredential credential) {
            this.credential = credential;
            return this;
        }
        
        public Builder setHttpClient(final CloseableHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }
        
        public Builder setThreadManager(final ThreadManager threadManager) {
            this.threadManager = threadManager;
            return this;
        }
        
        public HuaweiOption build() {
            return new HuaweiOption(this, null);
        }
    }
}
