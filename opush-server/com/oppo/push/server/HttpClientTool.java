// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

import org.apache.http.Consts;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.InputStream;
import java.io.FileInputStream;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import java.io.File;
import java.util.Iterator;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import com.alibaba.fastjson.JSON;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import javax.net.ssl.SSLContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import java.nio.charset.Charset;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

class HttpClientTool
{
    private static PoolingHttpClientConnectionManager manager;
    private static Charset charset;
    
    static void init() throws Exception {
        if (HttpClientTool.manager == null) {
            synchronized (PoolingHttpClientConnectionManager.class) {
                if (HttpClientTool.manager == null) {
                    final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial((KeyStore)null, (TrustStrategy)new TrustStrategy() {
                        public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
                    final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                        @Override
                        public boolean verify(final String hostname, final SSLSession session) {
                            return true;
                        }
                    };
                    final LayeredConnectionSocketFactory ssl = (LayeredConnectionSocketFactory)new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
                    final Registry<ConnectionSocketFactory> socketFactoryRegistry = (Registry<ConnectionSocketFactory>)RegistryBuilder.create().register(Constants.HTTP_PROTOCOL, (Object)ssl).build();
                    (HttpClientTool.manager = new PoolingHttpClientConnectionManager((Registry)socketFactoryRegistry)).setMaxTotal(Constants.HTTP_MAX_CONNECTION);
                    HttpClientTool.manager.setDefaultMaxPerRoute(Constants.HTTP_MAX_ROUTE);
                }
            }
        }
    }
    
    private static JSONObject getResponse(final CloseableHttpResponse response) {
        String result = null;
        try {
            final HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, HttpClientTool.charset);
            if (result == null || result.trim().length() == 0) {
                return null;
            }
            return JSON.parseObject(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", (Object)result);
            jsonObject.put("code", (Object)ReturnCode.ErrorCode.RESPONSE_PARSE_ERROR.getCode());
            return jsonObject;
        }
        finally {
            try {
                if (response != null) {
                    response.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    private static List<NameValuePair> buildNameValuePairsParameters(final Map<String, Object> params) {
        final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (final Map.Entry<String, Object> param : params.entrySet()) {
            nameValuePairs.add((NameValuePair)new BasicNameValuePair((String)param.getKey(), param.getValue() + ""));
        }
        return nameValuePairs;
    }
    
    static Result httpPostWithToken(final RequestPath path, final Map<String, Object> parameters, final Sender sender) throws Exception {
        Result result = httpPost(path, parameters);
        if (result != null && result.getReturnCode() != null && result.getReturnCode().getCode() == ReturnCode.ErrorCode.INVALID_AUTH_TOKEN.getCode()) {
            final Result tryGetToken = Auth.getAuthResult(sender.getAppKey(), sender.getMasterSecret());
            if (tryGetToken != null && !Validate.isEmpty(tryGetToken.getToken())) {
                sender.setToken(tryGetToken.getToken());
                sender.setCreateTime(tryGetToken.getCreateTime());
                parameters.put("auth_token", tryGetToken.getToken());
                result = httpPost(path, parameters);
            }
        }
        return result;
    }
    
    static Result multipartHttpPostWithToken(final RequestPath path, final Map<String, Object> parameters, final File uploadFile, final Sender sender) throws Exception {
        Result result = multipartHttpPost(path, parameters, uploadFile);
        if (result != null && result.getReturnCode() != null && result.getReturnCode().getCode() == ReturnCode.ErrorCode.INVALID_AUTH_TOKEN.getCode()) {
            final Result tryGetToken = Auth.getAuthResult(sender.getAppKey(), sender.getMasterSecret());
            if (tryGetToken != null && !Validate.isEmpty(tryGetToken.getToken())) {
                sender.setToken(tryGetToken.getToken());
                sender.setCreateTime(tryGetToken.getCreateTime());
                result = multipartHttpPost(path, parameters, uploadFile);
            }
        }
        return result;
    }
    
    static Result httpPost(final RequestPath path, final Map<String, Object> parameters) throws Exception {
        final HttpPost httpPost = new HttpPost(getUrl(path));
        parameters.put("sdk_version", "JAVA_SDK_V1.0.0");
        httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity((Iterable)buildNameValuePairsParameters(parameters), HttpClientTool.charset));
        final CloseableHttpResponse response = execute((HttpUriRequest)httpPost);
        final Result result = new Result();
        if (response.getStatusLine() != null) {
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setReason(response.getStatusLine().getReasonPhrase());
        }
        final JSONObject responseBody = getResponse(response);
        result.setResult(path, responseBody);
        return result;
    }
    
    static Result multipartHttpPost(final RequestPath path, final Map<String, Object> parameters, final File file) throws Exception {
        final HttpPost httpPost = new HttpPost(getMediaUrl(path));
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(HttpClientTool.charset);
        builder.addTextBody("auth_token", String.valueOf(parameters.get("auth_token")), ContentType.TEXT_PLAIN);
        builder.addTextBody("picture_ttl", String.valueOf(parameters.get("picture_ttl")), ContentType.TEXT_PLAIN);
        builder.addBinaryBody("file", (InputStream)new FileInputStream(file), ContentType.MULTIPART_FORM_DATA, file.getName());
        httpPost.setEntity(builder.build());
        final CloseableHttpResponse response = execute((HttpUriRequest)httpPost);
        final Result result = new Result();
        if (response.getStatusLine() != null) {
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setReason(response.getStatusLine().getReasonPhrase());
        }
        final JSONObject responseBody = getResponse(response);
        result.setResult(path, responseBody);
        return result;
    }
    
    private static CloseableHttpResponse execute(final HttpUriRequest request) throws Exception {
        CloseableHttpResponse response;
        try {
            response = getSSLClient().execute(request);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return response;
    }
    
    private static CloseableHttpClient getSSLClient() {
        return HttpClients.custom().setConnectionManager((HttpClientConnectionManager)HttpClientTool.manager).build();
    }
    
    private static String getUrl(final RequestPath path) {
        return String.format("https://%s%s%s%s", Constants.HOST_PRODUCTION_CHINA, RequestPath.BASE_PATH.getPath(), RequestPath.VERSION_PATH.getPath(), path.getPath());
    }
    
    private static String getMediaUrl(final RequestPath path) {
        return String.format("https://%s%s%s%s", Constants.MEDIA_HOST_PRODUCTION_CHINA, RequestPath.BASE_PATH.getPath(), RequestPath.VERSION_PATH.getPath(), path.getPath());
    }
    
    static {
        HttpClientTool.manager = null;
        HttpClientTool.charset = Consts.UTF_8;
    }
}
