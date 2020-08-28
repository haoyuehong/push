// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.io.File;

public abstract class AbstractClient
{
    protected boolean useProxy;
    protected boolean needAuth;
    protected String proxyHost;
    protected int proxyPort;
    protected String user;
    protected String password;
    protected int connectTimeout;
    protected int readTimeout;
    protected int writeTimeout;
    
    public AbstractClient(final boolean useProxy, final boolean needAuth, final String proxyHost, final int proxyPort, final String user, final String password) {
        this.useProxy = false;
        this.needAuth = false;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        this.writeTimeout = 3000;
        this.useProxy = useProxy;
        this.needAuth = needAuth;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.user = user;
        this.password = password;
    }
    
    public AbstractClient(final boolean useProxy, final boolean needAuth, final String proxyHost, final int proxyPort, final String user, final String password, final int connectTimeout, final int readTimeout, final int writeTimeout) {
        this(useProxy, needAuth, proxyHost, proxyPort, user, password);
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
    }
    
    public ResponseWrapper post(final String url, final byte[] body, final NameValuePairs headers, final RetryHandler retryHandler) throws Exception {
        return this.execute(() -> this.post(url, body, headers), retryHandler);
    }
    
    public ResponseWrapper get(final String url, final NameValuePairs headers, final RetryHandler retryHandler) throws Exception {
        return this.execute(() -> this.get(url, headers), retryHandler);
    }
    
    public ResponseWrapper upload(final String url, final File file, final NameValuePairs headers, final RetryHandler retryHandler) throws Exception {
        return this.execute(() -> this.upload(url, file, headers), retryHandler);
    }
    
    private ResponseWrapper execute(final Action action, final RetryHandler retryHandler) throws Exception {
        int executionCount = 0;
        ResponseWrapper res = null;
        Exception currentException = null;
        do {
            try {
                res = action.action();
            }
            catch (Exception e) {
                currentException = e;
            }
        } while (retryHandler != null && retryHandler.retryHandle(res, currentException, ++executionCount));
        if (currentException != null) {
            throw currentException;
        }
        return res;
    }
    
    public abstract ResponseWrapper post(final String p0, final byte[] p1, final NameValuePairs p2) throws Exception;
    
    public abstract ResponseWrapper get(final String p0, final NameValuePairs p1) throws Exception;
    
    public abstract ResponseWrapper upload(final String p0, final File p1, final NameValuePairs p2) throws Exception;
    
    public interface ResponseWrapper
    {
        int status();
        
        byte[] content();
        
        String header(final String p0);
        
        default String header(final String name, final String defaultValue) {
            final String value = this.header(name);
            return XMStringUtils.isBlank(value) ? defaultValue : value;
        }
    }
    
    public interface Action
    {
        ResponseWrapper action() throws Exception;
    }
}
