// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.util.function.Consumer;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.apache.commons.codec.binary.Base64;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import org.json.simple.JSONObject;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public abstract class PushSender<T extends PushSender>
{
    protected static final int BACKOFF_INITIAL_DELAY = 1000;
    protected static final int MAX_BACKOFF_DELAY = 1024000;
    protected static final Logger LOGGER;
    protected static final String JDK_VERSION;
    protected static final String OS;
    private static final String URI = "uri";
    private static final String ATTEMP = "attemp";
    private static final String REMOTE_HOST = "remoteHost";
    private static final String REMOTE_IP = "remoteIp";
    private static final String EXCEPTION = "exception";
    private static final String CONNECT_TIMEOUT = "connectTimeout";
    private static final String READ_TIMEOUT = "readTimeout";
    private static final String WRITE_TIMEOUT = "writeTimeout";
    private static final int REPORT_TIMEOUT = 10000;
    private static final String SECURITY = "security";
    private static final String REQUEST_TIME = "requestTime";
    protected static String LOCAL_IP;
    protected static final String LOCAL_HOST_NAME;
    protected static boolean useProxy;
    protected static boolean needAuth;
    protected static String proxyHost;
    protected static int proxyPort;
    protected static String user;
    private static String password;
    private static BlockingQueue<JSONObject> reportBuffer;
    private static Thread reportThread;
    protected final String security;
    protected final String token;
    protected final Region region;
    protected final boolean isVip;
    protected JSONObject lastResult;
    protected String remoteHost;
    protected String remoteIp;
    protected Exception lastException;
    private long requestTime;
    private long lastRequestCostTime;
    private ERROR lastRequestError;
    private String lastRequestUrl;
    private String lastRequestHost;
    private int connectTimeout;
    private int readTimeout;
    private int writeTimeout;
    
    public PushSender(final String security) {
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        this.writeTimeout = 3000;
        this.security = security;
        this.token = null;
        this.region = Region.China;
        this.isVip = false;
    }
    
    public PushSender(final String security, final boolean isVip) {
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        this.writeTimeout = 3000;
        this.security = security;
        this.token = null;
        this.region = Region.China;
        this.isVip = isVip;
    }
    
    public PushSender(final String security, final String token) {
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        this.writeTimeout = 3000;
        this.security = security;
        this.token = token;
        this.region = Region.China;
        this.isVip = false;
    }
    
    public PushSender(final String security, final String token, final boolean isVip) {
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        this.writeTimeout = 3000;
        this.security = security;
        this.token = token;
        this.region = Region.China;
        this.isVip = isVip;
    }
    
    public PushSender(final String security, final Region region) {
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        this.writeTimeout = 3000;
        this.security = security;
        this.token = null;
        this.region = region;
        this.isVip = false;
    }
    
    public PushSender(final String security, final boolean isVip, final Region region) {
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        this.writeTimeout = 3000;
        this.security = security;
        this.token = null;
        this.region = region;
        this.isVip = isVip;
    }
    
    public PushSender(final String security, final boolean isVip, final String token, final Region region) {
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        this.writeTimeout = 3000;
        this.security = security;
        this.token = token;
        this.region = region;
        this.isVip = isVip;
    }
    
    public PushSender(final String security, final String token, final Region region) {
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        this.writeTimeout = 3000;
        this.security = security;
        this.token = token;
        this.region = region;
        this.isVip = false;
    }
    
    private static String getLocalHostName() {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostName();
            PushSender.LOCAL_IP = InetAddress.getLocalHost().getHostAddress();
        }
        catch (Exception ex) {}
        return host;
    }
    
    public static void setProxy(final String host, final int port) {
        setProxy(host, port, null, null);
    }
    
    public static void setProxy(final String host, final int port, final String authUser, final String authPassword) {
        if (XMStringUtils.isBlank(host) || port <= 0) {
            throw new IllegalArgumentException("proxy host or port invalid.");
        }
        PushSender.useProxy = true;
        PushSender.needAuth = (!XMStringUtils.isBlank(authUser) && !XMStringUtils.isBlank(authPassword));
        PushSender.proxyHost = host;
        PushSender.proxyPort = port;
        PushSender.user = authUser;
        PushSender.password = authPassword;
    }
    
    public static void unsetProxy() {
        PushSender.useProxy = false;
        PushSender.needAuth = false;
    }
    
    private static AbstractClient getClient(final int connectTimeout, final int readTimeout, final int writeTimeout) {
        AbstractClient client;
        if (Constants.useOkHttp3) {
            try {
                Class.forName("okhttp3.OkHttpClient");
                client = new OkHttp3Client(PushSender.useProxy, PushSender.needAuth, PushSender.proxyHost, PushSender.proxyPort, PushSender.user, PushSender.password, connectTimeout, readTimeout, writeTimeout);
            }
            catch (Exception e) {
                client = new URLConnectionClient(PushSender.useProxy, PushSender.needAuth, PushSender.proxyHost, PushSender.proxyPort, PushSender.user, PushSender.password, connectTimeout, readTimeout, writeTimeout);
            }
        }
        else {
            client = new URLConnectionClient(PushSender.useProxy, PushSender.needAuth, PushSender.proxyHost, PushSender.proxyPort, PushSender.user, PushSender.password);
        }
        return client;
    }
    
    private static String buildUrl(final ServerSwitch.Server server, final Constants.RequestPath requestPath, final NameValuePairs queryParams) throws UnsupportedEncodingException {
        String url = ServerSwitch.buildFullRequestURL(server, requestPath);
        if (queryParams != null && !queryParams.isEmpty()) {
            url = url + "?" + queryParams.toQueryOrFormData();
        }
        return url;
    }
    
    public T connectTimeout(final int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return (T)this;
    }
    
    public T readTimeout(final int readTimeout) {
        this.readTimeout = readTimeout;
        return (T)this;
    }
    
    public T writeTimeout(final int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return (T)this;
    }
    
    void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            this.lastException = e;
            Thread.currentThread().interrupt();
        }
    }
    
    protected void before(final SenderContext context) {
    }
    
    protected void after(final SenderContext context) {
    }
    
    private String execute(final SenderAction<SenderContext, AbstractClient.ResponseWrapper> action, final Constants.RequestPath requestPath, NameValuePairs headers, final NameValuePairs queryParams) throws Exception {
        final long start = System.currentTimeMillis();
        this.requestTime = start;
        boolean isSuccess = false;
        final ServerSwitch.Server server = ServerSwitch.getInstance().selectServer(requestPath, this.region, this.isVip);
        try {
            headers = this.prepareRequestHeaders(headers);
            final String url = buildUrl(server, requestPath, queryParams);
            final SenderContext context = new SenderContext.Builder().server(server).headers(headers).requestPath(requestPath).url(url).build();
            try {
                final URL uri = new URL(url);
                this.remoteHost = uri.getHost();
                final InetAddress address = InetAddress.getByName(this.remoteHost);
                this.remoteIp = address.getHostAddress();
            }
            catch (Exception e) {
                this.lastException = e;
                PushSender.LOGGER.log(Level.WARNING, "Get remote ip failed for " + this.remoteHost, e);
            }
            this.before(context);
            final AbstractClient.ResponseWrapper responseWrapper = action.action(context);
            context.responseWrapper = responseWrapper;
            this.after(context);
            isSuccess = true;
            this.lastRequestError = ERROR.SUCCESS;
            Constants.autoSwitchHost = (responseWrapper.header("X-PUSH-DISABLE-AUTO-SELECT-DOMAIN") == null);
            Constants.accessTimeOut = Integer.valueOf(responseWrapper.header("X-PUSH-CLIENT-TIMEOUT-MS", "5000"));
            final String hosts = responseWrapper.header("X-PUSH-HOST-LIST");
            if (hosts != null) {
                ServerSwitch.getInstance().initialize(hosts);
            }
            return new String(responseWrapper.content());
        }
        catch (Exception e2) {
            if (e2 instanceof SocketTimeoutException) {
                this.lastRequestError = ERROR.SocketTimeoutException;
            }
            else if (e2 instanceof IOException) {
                this.lastRequestError = ERROR.IOException;
            }
            throw e2;
        }
        finally {
            this.lastRequestCostTime = System.currentTimeMillis() - start;
            if (this.lastRequestCostTime > Constants.accessTimeOut) {
                this.lastRequestError = ERROR.CostTooMuchTime;
                server.decrPriority();
            }
            else if (isSuccess) {
                server.incrPriority();
            }
            else {
                server.decrPriority();
            }
            this.lastRequestUrl = requestPath.getPath();
            this.lastRequestHost = server.getHost();
        }
    }
    
    protected String post(final Constants.RequestPath requestPath, final String body, final NameValuePairs headers, final NameValuePairs queryParams, final RetryHandler retryHandler) throws Exception {
        return this.execute(context -> getClient(this.connectTimeout, this.readTimeout, this.writeTimeout).post(context.url(), body.getBytes("UTF-8"), context.headers().nameAndValue("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"), retryHandler), requestPath, headers, queryParams);
    }
    
    protected String get(final Constants.RequestPath requestPath, final NameValuePairs headers, final NameValuePairs queryParams, final RetryHandler retryHandler) throws Exception {
        return this.execute(context -> getClient(this.connectTimeout, this.readTimeout, this.writeTimeout).get(context.url(), context.headers().nameAndValue("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"), retryHandler), requestPath, headers, queryParams);
    }
    
    protected String upload(final Constants.RequestPath requestPath, final File file, final NameValuePairs headers, final NameValuePairs queryParams, final RetryHandler retryHandler) throws Exception {
        return this.execute(context -> getClient(this.connectTimeout, this.readTimeout, this.writeTimeout).upload(context.url(), file, context.headers(), retryHandler), requestPath, headers, queryParams);
    }
    
    protected IOException exception(final int attemptNum, final Exception e) {
        if (e != null) {
            this.lastException = e;
        }
        String msg = "Failed to send http request after " + attemptNum + " attempts: remote server " + this.remoteHost + "(" + this.remoteIp + ")";
        if (this.lastException != null) {
            msg = msg + "\nException " + this.lastException.getClass().getCanonicalName() + " : " + this.lastException.getLocalizedMessage() + "\n" + this.lastException.getCause();
            final StackTraceElement[] traces = this.lastException.getStackTrace();
            if (traces != null) {
                for (final StackTraceElement trace : traces) {
                    msg = msg + "\n  " + trace.getClassName() + trace.getMethodName() + " (" + trace.getFileName() + ":" + trace.getLineNumber() + ")";
                }
            }
        }
        this.lastException = null;
        this.reportException(attemptNum, msg);
        return new IOException(msg);
    }
    
    private void reportException(final int attemp, final String exception) {
        if (Constants.enableReport) {
            final JSONObject json = new JSONObject();
            json.put((Object)"uri", (Object)this.lastRequestUrl);
            json.put((Object)"attemp", (Object)attemp);
            json.put((Object)"remoteHost", (Object)this.remoteHost);
            json.put((Object)"remoteIp", (Object)this.remoteIp);
            json.put((Object)"exception", (Object)exception);
            json.put((Object)"connectTimeout", (Object)this.connectTimeout);
            json.put((Object)"readTimeout", (Object)this.readTimeout);
            json.put((Object)"writeTimeout", (Object)this.writeTimeout);
            json.put((Object)"security", (Object)this.security);
            json.put((Object)"requestTime", (Object)this.requestTime);
            PushSender.reportBuffer.offer(json);
        }
    }
    
    private NameValuePairs prepareRequestHeaders(NameValuePairs headers) {
        if (headers == null) {
            headers = new NameValuePairs();
        }
        headers.nameAndValue("X-PUSH-SDK-VERSION", "JAVA_SDK_V1.0.3").nameAndValue("X-PUSH-JDK-VERSION", PushSender.JDK_VERSION).nameAndValue("X-PUSH-OS", PushSender.OS).nameAndValue("X-PUSH-REMOTEIP", this.remoteIp).nameAndValue("Authorization", "key=" + this.security).nameAndValue("X-PUSH-AUDIT-TOKEN", this.token).nameAndValue("X-PUSH-CLIENT-HOST", PushSender.LOCAL_HOST_NAME).nameAndValue("X-PUSH-CLIENT-IP", PushSender.LOCAL_IP).nameAndValue("X-PUSH-CLIENT-REQUEST-TIME", System.currentTimeMillis());
        if (PushSender.useProxy && PushSender.needAuth) {
            final String encoded = new String(Base64.encodeBase64((PushSender.user + ":" + PushSender.password).getBytes()));
            headers.nameAndValue("Proxy-Authorization", "Basic " + encoded);
        }
        if (Constants.INCLUDE_LAST_METRICS) {
            if (this.lastRequestCostTime > 0L) {
                headers.nameAndValue("X-PUSH-LAST-REQUEST-DURATION", String.valueOf(this.lastRequestCostTime));
                this.lastRequestCostTime = 0L;
            }
            if (this.lastResult != null && this.lastResult.containsKey((Object)"trace_id")) {
                headers.nameAndValue("X-PUSH-LAST-REQUEST-ID", this.lastResult.get((Object)"trace_id").toString());
            }
            headers.nameAndValue("X-PUSH-LAST-REQUEST-URL", this.lastRequestUrl).nameAndValue("X-PUSH-LAST-REQUEST-HOST", this.lastRequestHost).nameAndValue("X-PUSH-LAST-ERROR", this.lastRequestError.name());
            this.lastRequestUrl = null;
            this.lastRequestHost = null;
            this.lastRequestError = ERROR.SUCCESS;
        }
        if (Constants.autoSwitchHost && ServerSwitch.getInstance().needRefreshHostList()) {
            headers.nameAndValue("X-PUSH-HOST-LIST", "true");
        }
        return headers;
    }
    
    protected Result parseResult(final String content) throws IOException {
        try {
            final JSONParser parser = new JSONParser();
            final JSONObject json = (JSONObject)parser.parse(content);
            this.lastResult = json;
            return new Result.Builder().fromJson(json);
        }
        catch (ParseException e) {
            PushSender.LOGGER.log(Level.WARNING, "Exception parsing response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", (Throwable)e);
            this.lastException = (Exception)e;
            throw new IOException("Invalid response from XmPush: " + content + "\n server " + this.remoteHost + " ip " + this.remoteIp);
        }
    }
    
    static {
        LOGGER = Logger.getLogger(PushSender.class.getName());
        JDK_VERSION = System.getProperty("java.version", "UNKNOWN");
        OS = System.getProperty("os.name").toLowerCase();
        LOCAL_HOST_NAME = getLocalHostName();
        PushSender.useProxy = false;
        PushSender.needAuth = false;
        PushSender.reportBuffer = new ArrayBlockingQueue<JSONObject>(1000);
        byte[] body;
        ServerSwitch.Server server;
        JSONObject data;
        NameValuePairs headers;
        (PushSender.reportThread = new Thread(() -> {
            while (true) {
                body = null;
                server = ServerSwitch.getInstance().selectServer(Constants.XmPushRequestPath.V1_REPORT_EXCEPTION, Region.China, false);
                try {
                    data = PushSender.reportBuffer.take();
                    headers = new NameValuePairs().nameAndValue("Authorization", "key=" + data.get((Object)"security"));
                    body = new NameValuePairs().nameAndValue("report", data.toJSONString()).toQueryOrFormData().getBytes();
                    getClient(10000, 10000, 10000).post(buildUrl(server, Constants.XmPushRequestPath.V1_REPORT_EXCEPTION, null), body, headers);
                }
                catch (InterruptedException e) {
                    break;
                }
                catch (Exception e2) {
                    if (body != null) {
                        try {
                            Thread.sleep(200L);
                            getClient(10000, 10000, 10000).post(buildUrl(server, Constants.XmPushRequestPath.V1_REPORT_EXCEPTION, null), body, null);
                        }
                        catch (Exception ex) {}
                    }
                    else {
                        continue;
                    }
                }
            }
            return;
        })).setDaemon(true);
        PushSender.reportThread.start();
    }
    
    enum ERROR
    {
        SUCCESS, 
        SocketTimeoutException, 
        IOException, 
        CostTooMuchTime;
    }
    
    protected static class SenderContext
    {
        private ServerSwitch.Server server;
        private Constants.RequestPath requestPath;
        private NameValuePairs headers;
        private String url;
        private AbstractClient.ResponseWrapper responseWrapper;
        private Map<String, Object> extra;
        
        protected SenderContext() {
            this.extra = new HashMap<String, Object>();
        }
        
        protected ServerSwitch.Server server() {
            return this.server;
        }
        
        protected NameValuePairs headers() {
            return this.headers;
        }
        
        protected Constants.RequestPath requestPath() {
            return this.requestPath;
        }
        
        protected String url() {
            return this.url;
        }
        
        protected <T> T extra(final String name) {
            return (T)this.extra.get(name);
        }
        
        protected AbstractClient.ResponseWrapper responseWrapper() {
            return this.responseWrapper;
        }
        
        protected static class Builder
        {
            private SenderContext context;
            
            protected Builder() {
                this.context = new SenderContext();
            }
            
            Builder server(final ServerSwitch.Server server) {
                this.context.server = server;
                return this;
            }
            
            Builder headers(final NameValuePairs headers) {
                this.context.headers = headers;
                return this;
            }
            
            Builder requestPath(final Constants.RequestPath requestPath) {
                this.context.requestPath = requestPath;
                return this;
            }
            
            Builder responseWrapper(final AbstractClient.ResponseWrapper responseWrapper) {
                this.context.responseWrapper = responseWrapper;
                return this;
            }
            
            Builder url(final String url) {
                this.context.url = url;
                return this;
            }
            
            Builder extra(final String name, final Object value) {
                this.context.extra.put(name, value);
                return this;
            }
            
            SenderContext build() {
                return this.context;
            }
        }
    }
    
    public class DefaultPushRetryHandler implements RetryHandler
    {
        private int retries;
        private int backoff;
        private Random random;
        private Consumer<Integer> consumer;
        
        public DefaultPushRetryHandler(final int retries, final int backoff, final Consumer<Integer> consumer) {
            this.random = new Random();
            this.retries = retries;
            this.consumer = consumer;
            this.backoff = backoff;
        }
        
        @Override
        public boolean retryHandle(final AbstractClient.ResponseWrapper responseWrapper, final Exception e, final int executionCount) {
            boolean retry = false;
            if (this.retries > 1 && executionCount <= this.retries && (responseWrapper.status() >= 500 || responseWrapper.content() == null || e != null)) {
                retry = true;
            }
            if (retry) {
                if (this.backoff > 0) {
                    final int sleepTime = this.backoff / 2 + this.random.nextInt(this.backoff);
                    PushSender.this.sleep(sleepTime);
                    if (2 * this.backoff < 1024000) {
                        this.backoff *= 2;
                    }
                }
                if (this.consumer != null) {
                    this.consumer.accept(executionCount);
                }
            }
            return retry;
        }
    }
    
    private interface SenderAction<T, R>
    {
        R action(final T p0) throws Exception;
    }
}
