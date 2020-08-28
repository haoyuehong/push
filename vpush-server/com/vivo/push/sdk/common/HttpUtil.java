// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.common;

import com.alibaba.fastjson.JSON;
import com.vivo.push.sdk.notofication.Result;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpRequest;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.protocol.HttpClientContext;
import javax.net.ssl.SSLException;
import org.apache.http.conn.ConnectTimeoutException;
import java.net.UnknownHostException;
import java.io.InterruptedIOException;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.protocol.HttpContext;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import javax.net.ssl.SSLContext;
import org.apache.http.config.Registry;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.config.RegistryBuilder;
import javax.net.ssl.HostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.apache.http.ssl.TrustStrategy;
import java.security.KeyStore;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpUtil
{
    protected final String secret;
    private String authToken;
    private static PoolingHttpClientConnectionManager manager;
    
    public HttpUtil(final String secret) {
        this.secret = secret;
        this.authToken = null;
    }
    
    public void setAuthToken(final String authToken) {
        this.authToken = authToken;
    }
    
    protected static void init() throws Exception {
        if (HttpUtil.manager == null) {
            synchronized (PoolingHttpClientConnectionManager.class) {
                final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial((KeyStore)null, (TrustStrategy)new TrustStrategy() {
                    public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                        return true;
                    }
                }).build();
                final LayeredConnectionSocketFactory ssl = (LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(sslContext, (HostnameVerifier)NoopHostnameVerifier.INSTANCE);
                final Registry<ConnectionSocketFactory> socketFactoryRegistry = (Registry<ConnectionSocketFactory>)RegistryBuilder.create().register("https", (Object)ssl).register("http", (Object)PlainConnectionSocketFactory.INSTANCE).build();
                (HttpUtil.manager = new PoolingHttpClientConnectionManager((Registry)socketFactoryRegistry)).setMaxTotal(Constants.HTTP_MAX_CONNECTION);
                HttpUtil.manager.setDefaultMaxPerRoute(Constants.HTTP_MAX_ROUTE);
            }
        }
    }
    
    protected static void init(final int connection, final int route) throws Exception {
        HttpUtil.manager = null;
        synchronized (PoolingHttpClientConnectionManager.class) {
            final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial((KeyStore)null, (TrustStrategy)new TrustStrategy() {
                public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    return true;
                }
            }).build();
            final LayeredConnectionSocketFactory ssl = (LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(sslContext, (HostnameVerifier)NoopHostnameVerifier.INSTANCE);
            final Registry<ConnectionSocketFactory> socketFactoryRegistry = (Registry<ConnectionSocketFactory>)RegistryBuilder.create().register("https", (Object)ssl).register("http", (Object)PlainConnectionSocketFactory.INSTANCE).build();
            (HttpUtil.manager = new PoolingHttpClientConnectionManager((Registry)socketFactoryRegistry)).setMaxTotal(connection);
            HttpUtil.manager.setDefaultMaxPerRoute(route);
        }
    }
    
    protected String doPost(final String jsonBody, final String path) throws Exception {
        final HttpPost httpPost = new HttpPost(getUrl(path));
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        if (this.authToken != null) {
            httpPost.addHeader("authToken", this.authToken);
        }
        final StringEntity stringEntity = new StringEntity(jsonBody, Constants.CHARSET);
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity((HttpEntity)stringEntity);
        return execute((HttpUriRequest)httpPost);
    }
    
    private static String execute(final HttpUriRequest request) throws Exception {
        String data = null;
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient().execute(request);
            int status;
            try {
                status = response.getStatusLine().getStatusCode();
            }
            catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
            if (status != 200) {
                try {
                    data = response.getStatusLine().getReasonPhrase();
                }
                catch (Exception var4) {
                    data = "N/A";
                    var4.printStackTrace();
                }
                throw new IOException("faild :" + status + ":" + data);
            }
            data = EntityUtils.toString(response.getEntity(), Constants.CHARSET);
            return data;
        }
        catch (Exception var5) {
            var5.printStackTrace();
            throw var5;
        }
        finally {
            try {
                if (null != response) {
                    response.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    protected String doGet(final String requestPath) throws Exception {
        final HttpGet httpGet = new HttpGet(getUrl(requestPath));
        if (this.authToken != null) {
            httpGet.addHeader("authToken", this.authToken);
        }
        return execute((HttpUriRequest)httpGet);
    }
    
    private static CloseableHttpClient getHttpClient() {
        final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Constants.CONNECTION_REQUEST_TIMEOUT).setConnectTimeout(Constants.CONNECTION_TIMEOUT).setSocketTimeout(Constants.SOCKET_TIMEOUT).build();
        final HttpRequestRetryHandler retry = (HttpRequestRetryHandler)new HttpRequestRetryHandler() {
            public boolean retryRequest(final IOException exception, final int executionCount, final HttpContext context) {
                if (executionCount >= 3) {
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    return true;
                }
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                if (exception instanceof SSLException) {
                    return false;
                }
                final HttpClientContext clientContext = HttpClientContext.adapt(context);
                final HttpRequest request = clientContext.getRequest();
                return !(request instanceof HttpEntityEnclosingRequest);
            }
        };
        return HttpClients.custom().setDefaultRequestConfig(requestConfig).setRetryHandler(retry).setConnectionManager((HttpClientConnectionManager)HttpUtil.manager).build();
    }
    
    protected static Result sendMessage(final String response) throws IOException {
        try {
            return (Result)JSON.parseObject(response, (Class)Result.class);
        }
        catch (Exception var6) {
            throw new IOException("Invalid response from Push: " + response);
        }
    }
    
    private static String getUrl(final String path) {
        return String.format("https://%s%s", "api-push.vivo.com.cn", path);
    }
    
    static {
        HttpUtil.manager = null;
    }
}
