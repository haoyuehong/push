// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSession;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import javax.net.ssl.SSLContext;
import java.net.URL;
import java.io.FileInputStream;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.function.Predicate;
import java.util.Objects;
import java.util.Arrays;
import java.util.logging.Logger;

public class URLConnectionClient extends AbstractClient
{
    private static final SSLHandler sslVerifier;
    private static final Logger LOGGER;
    
    public URLConnectionClient(final boolean useProxy, final boolean needAuth, final String proxyHost, final int proxyPort, final String user, final String password) {
        super(useProxy, needAuth, proxyHost, proxyPort, user, password);
    }
    
    public URLConnectionClient(final boolean useProxy, final boolean needAuth, final String proxyHost, final int proxyPort, final String user, final String password, final int connectTimeout, final int readTimeout, final int writeTimeout) {
        super(useProxy, needAuth, proxyHost, proxyPort, user, password, connectTimeout, readTimeout, writeTimeout);
    }
    
    @Override
    public ResponseWrapper post(final String url, final byte[] body, final NameValuePairs headers) throws Exception {
        final HttpURLConnection connection = this.getConnection(url);
        connection.setConnectTimeout(this.connectTimeout);
        connection.setReadTimeout(this.readTimeout);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setFixedLengthStreamingMode(body.length);
        connection.setRequestMethod("POST");
        if (headers != null && !headers.isEmpty()) {
            final URLConnection urlConnection;
            headers.getPairs().forEach(header -> {
                if (header.getValues() != null) {
                    Arrays.stream(header.getValues()).filter(Objects::nonNull).forEach(value -> urlConnection.setRequestProperty(header.getName(), value.toString()));
                }
                return;
            });
        }
        InputStream is = null;
        try (final OutputStream os = connection.getOutputStream()) {
            os.write(body);
            os.flush();
            final int status = connection.getResponseCode();
            if (status >= 500) {
                URLConnectionClient.LOGGER.fine("XmPush service is unavailable (status " + status + ")");
            }
            is = ((status == 200) ? connection.getInputStream() : connection.getErrorStream());
            final byte[] content = this.readFixedLengthBytes(is, connection.getContentLength());
            return new ResponseWrapper() {
                @Override
                public int status() {
                    return status;
                }
                
                @Override
                public byte[] content() {
                    return content;
                }
                
                @Override
                public String header(final String name) {
                    return connection.getHeaderField(name);
                }
            };
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    private byte[] readFixedLengthBytes(final InputStream is, final int length) throws IOException {
        final byte[] buffer = new byte[length];
        is.read(buffer);
        return buffer;
    }
    
    @Override
    public ResponseWrapper get(final String url, final NameValuePairs headers) throws Exception {
        final HttpURLConnection connection = this.getConnection(url);
        connection.setConnectTimeout(this.connectTimeout);
        connection.setReadTimeout(this.readTimeout);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("GET");
        if (headers != null && !headers.isEmpty()) {
            final URLConnection urlConnection;
            headers.getPairs().forEach(header -> {
                if (header.getValues() != null) {
                    Arrays.stream(header.getValues()).filter(Objects::nonNull).forEach(value -> urlConnection.setRequestProperty(header.getName(), value.toString()));
                }
                return;
            });
        }
        InputStream is = null;
        try {
            final int status = connection.getResponseCode();
            if (status >= 500) {
                URLConnectionClient.LOGGER.fine("XmPush service is unavailable (status " + status + ")");
            }
            is = ((status == 200) ? connection.getInputStream() : connection.getErrorStream());
            final byte[] content = this.readFixedLengthBytes(is, connection.getContentLength());
            return new ResponseWrapper() {
                @Override
                public int status() {
                    return status;
                }
                
                @Override
                public byte[] content() {
                    return content;
                }
                
                @Override
                public String header(final String name) {
                    return connection.getHeaderField(name);
                }
            };
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    @Override
    public ResponseWrapper upload(final String url, final File file, final NameValuePairs headers) throws Exception {
        final HttpURLConnection connection = this.getConnection(url);
        final String BOUNDARY = "------WKFB" + UUID.randomUUID().toString();
        final String TWO_HYPHENS = "--";
        final String LINE_END = "\r\n";
        connection.setConnectTimeout(this.connectTimeout);
        connection.setReadTimeout(this.readTimeout);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Charsert", "UTF-8");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        if (headers != null && !headers.isEmpty()) {
            final URLConnection urlConnection;
            headers.getPairs().forEach(header -> {
                if (header.getValues() != null) {
                    Arrays.stream(header.getValues()).filter(Objects::nonNull).forEach(value -> urlConnection.setRequestProperty(header.getName(), value.toString()));
                }
                return;
            });
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
        InputStream is = null;
        try (final OutputStream out = connection.getOutputStream()) {
            out.write(sb.toString().getBytes("UTF-8"));
            int bytes = 0;
            final byte[] buffer = new byte[1024];
            while ((bytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
            final byte[] endData = (LINE_END + TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_END).getBytes();
            out.write(endData);
            out.flush();
            final int status = connection.getResponseCode();
            if (status >= 500) {
                URLConnectionClient.LOGGER.fine("XmPush service is unavailable (status " + status + ")");
                return null;
            }
            is = ((status == 200) ? connection.getInputStream() : connection.getErrorStream());
            final byte[] content = this.readFixedLengthBytes(is, connection.getContentLength());
            return new ResponseWrapper() {
                @Override
                public int status() {
                    return status;
                }
                
                @Override
                public byte[] content() {
                    return content;
                }
                
                @Override
                public String header(final String name) {
                    return connection.getHeaderField(name);
                }
            };
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    private HttpURLConnection getConnection(final String url) throws Exception {
        final URL u = new URL(url);
        HttpURLConnection connection;
        if (this.useProxy) {
            System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
            try {
                final SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, new TrustManager[] { URLConnectionClient.sslVerifier }, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(URLConnectionClient.sslVerifier);
            }
            catch (Exception e) {
                URLConnectionClient.LOGGER.fine("https config ssl failure: " + e);
                throw e;
            }
            final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.proxyHost, this.proxyPort));
            connection = (HttpsURLConnection)u.openConnection(proxy);
        }
        else if (Constants.USE_HTTPS) {
            connection = (HttpsURLConnection)u.openConnection();
            ((HttpsURLConnection)connection).setHostnameVerifier((s, sslSession) -> true);
        }
        else {
            connection = (HttpURLConnection)u.openConnection();
        }
        return connection;
    }
    
    static {
        sslVerifier = new SSLHandler();
        LOGGER = Logger.getLogger(URLConnectionClient.class.getName());
    }
    
    private static class SSLHandler implements X509TrustManager, HostnameVerifier
    {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
        }
        
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
        }
        
        @Override
        public boolean verify(final String paramString, final SSLSession paramSSLSession) {
            return true;
        }
    }
}
