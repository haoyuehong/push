// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import okhttp3.Protocol;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import okhttp3.Response;
import java.util.function.Predicate;
import java.util.Objects;
import java.util.Arrays;
import okhttp3.MultipartBody;
import java.io.File;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import java.util.logging.Logger;

public class OkHttp3Client extends AbstractClient
{
    private static final Logger LOGGER;
    private static final Object lock;
    private static OkHttpClient client;
    
    public OkHttp3Client(final boolean useProxy, final boolean needAuth, final String proxyHost, final int proxyPort, final String user, final String password) {
        super(useProxy, needAuth, proxyHost, proxyPort, user, password);
    }
    
    public OkHttp3Client(final boolean useProxy, final boolean needAuth, final String proxyHost, final int proxyPort, final String user, final String password, final int connectTimeout, final int readTimeout, final int writeTimeout) {
        super(useProxy, needAuth, proxyHost, proxyPort, user, password, connectTimeout, readTimeout, writeTimeout);
    }
    
    @Override
    public ResponseWrapper post(final String url, final byte[] body, final NameValuePairs headers) throws Exception {
        final Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.post(RequestBody.create(MediaType.get("application/x-www-form-urlencoded;charset=UTF-8"), body));
        return this.execute(requestBuilder, headers);
    }
    
    @Override
    public ResponseWrapper get(final String url, final NameValuePairs headers) throws Exception {
        final Request.Builder requestBuilder = new Request.Builder().url(url).get();
        return this.execute(requestBuilder, headers);
    }
    
    @Override
    public ResponseWrapper upload(final String url, final File file, final NameValuePairs headers) throws Exception {
        final Request.Builder requestBuilder = new Request.Builder().url(url);
        final RequestBody image = RequestBody.create(MediaType.get("image/*"), file);
        requestBuilder.post((RequestBody)new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getPath(), image).build());
        return this.execute(requestBuilder, headers);
    }
    
    private ResponseWrapper execute(final Request.Builder requestBuilder, final NameValuePairs headers) throws IOException {
        if (headers != null && !headers.isEmpty()) {
            headers.getPairs().forEach(header -> {
                if (header.getValues() != null) {
                    Arrays.stream(header.getValues()).filter(Objects::nonNull).forEach(value -> requestBuilder.header(header.getName(), value.toString()));
                }
                return;
            });
        }
        try (final Response response = this.getClient().newCall(requestBuilder.build()).execute()) {
            if (response.code() >= 500) {
                OkHttp3Client.LOGGER.fine("XmPush service is unavailable (status " + response.code() + ")");
            }
            return new ResponseWrapper() {
                byte[] content = response.body().bytes();
                
                @Override
                public int status() {
                    return response.code();
                }
                
                @Override
                public byte[] content() {
                    return this.content;
                }
                
                @Override
                public String header(final String name) {
                    return response.header(name);
                }
            };
        }
    }
    
    private OkHttpClient getClient() {
        if (OkHttp3Client.client == null) {
            synchronized (OkHttp3Client.lock) {
                if (OkHttp3Client.client == null) {
                    final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                    try {
                        final Method setProtocolMethod = SSLParameters.class.getMethod("setApplicationProtocols", String[].class);
                        SSLSocket.class.getMethod("getApplicationProtocol", (Class<?>[])new Class[0]);
                    }
                    catch (NoSuchMethodException e) {
                        try {
                            Class.forName("org.conscrypt.Conscrypt");
                            System.setProperty("okhttp.platform", "conscrypt");
                        }
                        catch (ClassNotFoundException ex) {}
                    }
                    clientBuilder.connectTimeout((long)this.connectTimeout, TimeUnit.MILLISECONDS).readTimeout((long)this.readTimeout, TimeUnit.MILLISECONDS).writeTimeout((long)this.writeTimeout, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).protocols((List)Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1));
                    if (this.useProxy) {
                        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
                        clientBuilder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.proxyHost, this.proxyPort)));
                    }
                    OkHttp3Client.client = clientBuilder.build();
                }
            }
        }
        return OkHttp3Client.client;
    }
    
    static {
        LOGGER = Logger.getLogger(OkHttp3Client.class.getName());
        lock = new Object();
    }
}
