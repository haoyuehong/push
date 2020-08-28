// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.util;

import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.config.Registry;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLContext;

public class IgnoreSSLUtils
{
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        final SSLContext sc = SSLContext.getInstance("SSLv3");
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(final X509Certificate[] paramArrayOfX509Certificate, final String paramString) throws CertificateException {
            }
            
            @Override
            public void checkServerTrusted(final X509Certificate[] paramArrayOfX509Certificate, final String paramString) throws CertificateException {
            }
            
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }
    
    public static CloseableHttpClient createClient() throws KeyManagementException, NoSuchAlgorithmException {
        final SSLContext sslcontext = createIgnoreVerifySSL();
        final Registry<ConnectionSocketFactory> socketFactoryRegistry = (Registry<ConnectionSocketFactory>)RegistryBuilder.create().register("http", (Object)PlainConnectionSocketFactory.INSTANCE).register("https", (Object)new SSLConnectionSocketFactory(sslcontext)).build();
        final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager((Registry)socketFactoryRegistry);
        HttpClients.custom().setConnectionManager((HttpClientConnectionManager)connManager);
        return HttpClients.custom().setConnectionManager((HttpClientConnectionManager)connManager).build();
    }
}
