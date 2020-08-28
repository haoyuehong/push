// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.codec.binary.Base64;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.io.FileInputStream;
import java.util.UUID;
import java.io.OutputStream;
import java.io.File;
import java.net.SocketTimeoutException;
import java.net.HttpURLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.io.Closeable;
import java.net.InetAddress;
import java.util.Random;
import java.util.logging.Logger;

public class HttpBase
{
    protected static final String UTF8 = "UTF-8";
    protected static final int BACKOFF_INITIAL_DELAY = 1000;
    protected static final int MAX_BACKOFF_DELAY = 1024000;
    protected static final Logger logger;
    private static final String JDK_VERSION;
    private static final String OS;
    private static final SSLHandler sslVerifier;
    private static String LOCAL_IP;
    private static final String LOCAL_HOST_NAME;
    private static boolean useProxy;
    private static boolean needAuth;
    private static String proxyHost;
    private static int proxyPort;
    private static String user;
    private static String password;
    protected final Random random;
    protected final String security;
    protected final String token;
    protected final Region region;
    protected final boolean isVip;
    protected long lastRequestCostTime;
    protected ERROR lastRequestError;
    protected String lastRequestUrl;
    protected String lastRequestHost;
    protected String remoteHost;
    protected String remoteIp;
    protected Exception lastException;
    
    public HttpBase(final String security) {
        this.random = new Random();
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.security = security;
        this.token = null;
        this.region = Region.China;
        this.isVip = false;
    }
    
    public HttpBase(final String security, final boolean isVip) {
        this.random = new Random();
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.security = security;
        this.token = null;
        this.region = Region.China;
        this.isVip = isVip;
    }
    
    public HttpBase(final String security, final String token) {
        this.random = new Random();
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.security = security;
        this.token = token;
        this.region = Region.China;
        this.isVip = false;
    }
    
    public HttpBase(final String security, final String token, final boolean isVip) {
        this.random = new Random();
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.security = security;
        this.token = token;
        this.region = Region.China;
        this.isVip = isVip;
    }
    
    public HttpBase(final String security, final Region region) {
        this.random = new Random();
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.security = security;
        this.token = null;
        this.region = region;
        this.isVip = false;
    }
    
    public HttpBase(final String security, final String token, final Region region) {
        this.random = new Random();
        this.lastRequestCostTime = 0L;
        this.lastRequestError = ERROR.SUCCESS;
        this.lastRequestUrl = null;
        this.lastRequestHost = null;
        this.remoteHost = "";
        this.remoteIp = "";
        this.lastException = null;
        this.security = security;
        this.token = token;
        this.region = region;
        this.isVip = false;
    }
    
    private static String getLocalHostName() {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostName();
            HttpBase.LOCAL_IP = InetAddress.getLocalHost().getHostAddress();
        }
        catch (Exception ex) {}
        return host;
    }
    
    protected static StringBuilder newBody(final String name, final String value) {
        return new StringBuilder(nonNull(name)).append('=').append(nonNull(value));
    }
    
    private static void close(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (IOException e) {
                HttpBase.logger.log(Level.FINEST, "IOException closing stream", e);
            }
        }
    }
    
    protected static StringBuilder newBodyWithArrayParameters(final String name, final List<String> parameters) throws UnsupportedEncodingException {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parameters.size(); ++i) {
            if (i == 0) {
                sb.append(nonNull(name)).append("=").append(URLEncoder.encode(nonNull(parameters.get(i)), "UTF-8"));
            }
            else {
                nonNull(sb).append('&').append(nonNull(name)).append('=').append(URLEncoder.encode(nonNull(parameters.get(i)), "UTF-8"));
            }
        }
        if (parameters.size() == 0) {
            sb.append(name).append("=").append("");
        }
        return sb;
    }
    
    protected static void addParameter(final StringBuilder body, final String name, final String value) {
        nonNull(body).append('&').append(nonNull(name)).append('=').append(nonNull(value));
    }
    
    protected static String getString(final InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        final StringBuilder content = new StringBuilder();
        String newLine;
        do {
            newLine = reader.readLine();
            if (newLine != null) {
                content.append(newLine).append('\n');
            }
        } while (newLine != null);
        if (content.length() > 0) {
            content.setLength(content.length() - 1);
        }
        return content.toString();
    }
    
    protected static String getAndClose(final InputStream stream) throws IOException {
        try {
            return getString(stream);
        }
        finally {
            if (stream != null) {
                close(stream);
            }
        }
    }
    
    static <T> T nonNull(final T argument) {
        if (argument == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        return argument;
    }
    
    public static void setProxy(final String host, final int port) {
        setProxy(host, port, null, null);
    }
    
    public static void setProxy(final String host, final int port, final String authUser, final String authPassword) {
        if (XMStringUtils.isBlank(host) || port <= 0) {
            throw new IllegalArgumentException("proxy host or port invalid.");
        }
        HttpBase.useProxy = true;
        HttpBase.needAuth = (!XMStringUtils.isBlank(authUser) && !XMStringUtils.isBlank(authPassword));
        HttpBase.proxyHost = host;
        HttpBase.proxyPort = port;
        HttpBase.user = authUser;
        HttpBase.password = authPassword;
    }
    
    public static void unsetProxy() {
        HttpBase.useProxy = false;
        HttpBase.needAuth = false;
    }
    
    protected IOException exception(final int attemptNum) {
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
        return new IOException(msg);
    }
    
    private HttpURLConnection httpRequest(final HttpAction action, final Constants.RequestPath requestPath) throws IOException {
        final ServerSwitch.Server server = ServerSwitch.getInstance().selectServer(requestPath, this.region, this.isVip);
        final long start = System.currentTimeMillis();
        boolean succ = false;
        try {
            final HttpURLConnection result = action.action(server);
            succ = true;
            this.lastRequestError = ERROR.SUCCESS;
            Constants.autoSwitchHost = (result.getHeaderField("X-PUSH-DISABLE-AUTO-SELECT-DOMAIN") == null);
            Constants.accessTimeOut = result.getHeaderFieldInt("X-PUSH-CLIENT-TIMEOUT-MS", 5000);
            final String hosts = result.getHeaderField("X-PUSH-HOST-LIST");
            if (hosts != null) {
                ServerSwitch.getInstance().initialize(hosts);
            }
            return result;
        }
        catch (SocketTimeoutException e) {
            this.lastRequestError = ERROR.SocketTimeoutException;
            throw e;
        }
        catch (IOException e2) {
            this.lastRequestError = ERROR.IOException;
            throw e2;
        }
        finally {
            this.lastRequestCostTime = System.currentTimeMillis() - start;
            if (this.lastRequestCostTime > Constants.accessTimeOut) {
                this.lastRequestError = ERROR.CostTooMuchTime;
                server.decrPriority();
            }
            else if (succ) {
                server.incrPriority();
            }
            else {
                server.decrPriority();
            }
            this.lastRequestUrl = requestPath.getPath();
            this.lastRequestHost = server.getHost();
        }
    }
    
    protected String getResponseBody(final HttpURLConnection conn) throws IOException {
        final int status = conn.getResponseCode();
        if (status / 100 == 5) {
            HttpBase.logger.fine("Service is unavailable (status " + status + "): remote server " + this.remoteHost + "(" + this.remoteIp + ")");
            return null;
        }
        if (status != 200) {
            String responseBody;
            try {
                responseBody = getAndClose(conn.getErrorStream());
                HttpBase.logger.finest("Plain get error response: " + responseBody);
            }
            catch (IOException e) {
                responseBody = "N/A";
                HttpBase.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e);
            }
            throw new InvalidRequestException(status, responseBody);
        }
        String responseBody;
        try {
            responseBody = getAndClose(conn.getInputStream());
        }
        catch (IOException e) {
            HttpBase.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e);
            return null;
        }
        return responseBody;
    }
    
    protected HttpURLConnection doPost(final Constants.RequestPath requestPath, final String body) throws IOException {
        return this.httpRequest(new HttpAction() {
            @Override
            public HttpURLConnection action(final ServerSwitch.Server server) throws IOException {
                return HttpBase.this.doPost(server, requestPath, "application/x-www-form-urlencoded;charset=UTF-8", body);
            }
        }, requestPath);
    }
    
    protected HttpURLConnection doGet(final Constants.RequestPath requestPath, final String parameter) throws IOException {
        return this.httpRequest(new HttpAction() {
            @Override
            public HttpURLConnection action(final ServerSwitch.Server server) throws IOException {
                return HttpBase.this.doGet(server, requestPath, "application/x-www-form-urlencoded;charset=UTF-8", parameter);
            }
        }, requestPath);
    }
    
    protected HttpURLConnection doUpload(final Constants.RequestPath requestPath, final File file, final String parameter) throws IOException {
        return this.httpRequest(new HttpAction() {
            @Override
            public HttpURLConnection action(final ServerSwitch.Server server) throws IOException {
                return HttpBase.this.doUpload(server, requestPath, file, parameter);
            }
        }, requestPath);
    }
    
    protected HttpURLConnection doPost(final ServerSwitch.Server server, final Constants.RequestPath requestPath, final String contentType, final String body) throws IOException {
        if (requestPath == null || body == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        HttpBase.logger.fine("Sending post to " + server.getHost() + " " + requestPath.getPath());
        HttpBase.logger.finest("post body: " + body);
        final HttpURLConnection conn = this.getConnection(server, requestPath);
        this.prepareConnection(conn);
        final byte[] bytes = body.getBytes();
        conn.setConnectTimeout(20000);
        conn.setReadTimeout(20000);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setFixedLengthStreamingMode(bytes.length);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Authorization", "key=" + this.security);
        if (this.token != null) {
            conn.setRequestProperty("X-PUSH-AUDIT-TOKEN", this.token);
        }
        final OutputStream out = conn.getOutputStream();
        try {
            out.write(bytes);
        }
        finally {
            close(out);
        }
        return conn;
    }
    
    public HttpURLConnection doUpload(final ServerSwitch.Server server, final Constants.RequestPath requestPath, final File file, final String parameter) throws IOException {
        if (requestPath == null || file == null || !file.exists()) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        HttpBase.logger.fine("Upload to " + server.getHost() + " " + requestPath.getPath());
        HttpBase.logger.finest("post file name : " + file);
        final String BOUNDARY = "------WKFB" + UUID.randomUUID().toString();
        final String TWO_HYPHENS = "--";
        final String LINE_END = "\r\n";
        final HttpURLConnection conn = this.getConnection(server, requestPath, parameter);
        this.prepareConnection(conn);
        conn.setConnectTimeout(20000);
        conn.setReadTimeout(20000);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        conn.setRequestProperty("Authorization", "key=" + this.security);
        if (this.token != null) {
            conn.setRequestProperty("X-PUSH-AUDIT-TOKEN", this.token);
        }
        final StringBuffer sb = new StringBuffer();
        sb.append(LINE_END);
        sb.append(TWO_HYPHENS);
        sb.append(BOUNDARY);
        sb.append(LINE_END);
        sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"");
        sb.append(LINE_END);
        sb.append("Content-Type: image/*");
        sb.append(LINE_END);
        sb.append("Content-Lenght: " + file.length());
        sb.append(LINE_END);
        sb.append(LINE_END);
        final FileInputStream in = new FileInputStream(file);
        final OutputStream out = conn.getOutputStream();
        try {
            out.write(sb.toString().getBytes("UTF-8"));
            int bytes = 0;
            final byte[] buffer = new byte[1024];
            while ((bytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
            final byte[] endData = (LINE_END + TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_END).getBytes();
            out.write(endData);
            out.flush();
        }
        finally {
            close(out);
        }
        return conn;
    }
    
    protected HttpURLConnection doGet(final ServerSwitch.Server server, final Constants.RequestPath requestPath, final String contentType, final String parameter) throws IOException {
        if (requestPath == null || parameter == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        HttpBase.logger.fine("Sending get to " + server.getHost() + " " + requestPath.getPath());
        HttpBase.logger.finest("get parameter: " + parameter);
        final HttpURLConnection conn = this.getConnection(server, requestPath, parameter);
        this.prepareConnection(conn);
        conn.setConnectTimeout(20000);
        conn.setReadTimeout(20000);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Authorization", "key=" + this.security);
        conn.getInputStream();
        return conn;
    }
    
    protected void prepareConnection(final HttpURLConnection conn) {
        conn.setRequestProperty("X-PUSH-SDK-VERSION", "JAVA_SDK_V1.0.3");
        conn.setRequestProperty("X-PUSH-JDK-VERSION", HttpBase.JDK_VERSION);
        conn.setRequestProperty("X-PUSH-OS", HttpBase.OS);
        conn.setRequestProperty("X-PUSH-REMOTEIP", this.remoteIp);
        if (HttpBase.LOCAL_HOST_NAME != null) {
            conn.setRequestProperty("X-PUSH-CLIENT-HOST", HttpBase.LOCAL_HOST_NAME);
        }
        if (HttpBase.LOCAL_IP != null) {
            conn.setRequestProperty("X-PUSH-CLIENT-IP", HttpBase.LOCAL_IP);
        }
        if (Constants.INCLUDE_LAST_METRICS) {
            if (this.lastRequestCostTime > 0L) {
                conn.setRequestProperty("X-PUSH-LAST-REQUEST-DURATION", this.lastRequestCostTime + "");
                this.lastRequestCostTime = 0L;
            }
            if (this.lastRequestUrl != null) {
                conn.setRequestProperty("X-PUSH-LAST-REQUEST-URL", this.lastRequestUrl);
                this.lastRequestUrl = null;
            }
            if (this.lastRequestHost != null) {
                conn.setRequestProperty("X-PUSH-LAST-REQUEST-HOST", this.lastRequestHost);
                this.lastRequestHost = null;
            }
            conn.setRequestProperty("X-PUSH-LAST-ERROR", this.lastRequestError.name());
            this.lastRequestError = ERROR.SUCCESS;
        }
        if (Constants.autoSwitchHost && ServerSwitch.getInstance().needRefreshHostList()) {
            conn.setRequestProperty("X-PUSH-HOST-LIST", "true");
        }
    }
    
    protected HttpURLConnection getConnection(final ServerSwitch.Server server, final Constants.RequestPath requestPath) throws IOException {
        return this.getConnection(server, requestPath, null);
    }
    
    protected HttpURLConnection getConnection(final Constants.RequestPath requestPath) throws IOException {
        return this.getConnection(null, requestPath, null);
    }
    
    protected HttpURLConnection getConnection(ServerSwitch.Server server, final Constants.RequestPath requestPath, final String parameter) throws IOException {
        if (server == null) {
            server = ServerSwitch.getInstance().selectServer(requestPath, this.region, this.isVip);
        }
        String urlSpec = ServerSwitch.buildFullRequestURL(server, requestPath);
        if (parameter != null) {
            urlSpec = urlSpec + "?" + parameter;
        }
        final URL url = new URL(urlSpec);
        if (HttpBase.useProxy) {
            return this.setProxy(url);
        }
        this.remoteHost = "";
        this.remoteIp = "";
        try {
            this.remoteHost = url.getHost();
            final InetAddress address = InetAddress.getByName(this.remoteHost);
            this.remoteIp = address.getHostAddress();
        }
        catch (Exception e) {
            this.lastException = e;
            HttpBase.logger.log(Level.WARNING, "Get remote ip failed for " + this.remoteHost, e);
        }
        if (Constants.USE_HTTPS) {
            final HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(final String s, final SSLSession sslSession) {
                    return true;
                }
            });
            return conn;
        }
        return (HttpURLConnection)url.openConnection();
    }
    
    private HttpsURLConnection setProxy(final URL url) throws IOException {
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        try {
            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { HttpBase.sslVerifier }, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(HttpBase.sslVerifier);
        }
        catch (Exception e) {
            HttpBase.logger.fine("https config ssl failure: " + e);
            this.lastException = e;
        }
        final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(HttpBase.proxyHost, HttpBase.proxyPort));
        final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection(proxy);
        if (HttpBase.needAuth) {
            final String encoded = new String(Base64.encodeBase64((HttpBase.user + ":" + HttpBase.password).getBytes()));
            httpsURLConnection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
        }
        return httpsURLConnection;
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
    
    static {
        logger = Logger.getLogger(HttpBase.class.getName());
        JDK_VERSION = System.getProperty("java.version", "UNKNOWN");
        OS = System.getProperty("os.name").toLowerCase();
        sslVerifier = new SSLHandler();
        LOCAL_HOST_NAME = getLocalHostName();
        HttpBase.useProxy = false;
        HttpBase.needAuth = false;
    }
    
    enum ERROR
    {
        SUCCESS, 
        SocketTimeoutException, 
        IOException, 
        CostTooMuchTime;
    }
    
    private static class SSLHandler implements X509TrustManager, HostnameVerifier
    {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
        }
        
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
        }
        
        @Override
        public boolean verify(final String paramString, final SSLSession paramSSLSession) {
            return true;
        }
    }
    
    interface HttpAction
    {
        HttpURLConnection action(final ServerSwitch.Server p0) throws IOException;
    }
}
