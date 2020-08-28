// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.logging.Level;

public class EMQFetcher extends HttpBase
{
    public EMQFetcher(final String security) {
        super(security);
    }
    
    public EMQFetcher(final String security, final Region region) {
        super(security, region);
    }
    
    public String fetchAckInfo(final String packageName, final int retries) throws IOException {
        int attempt = 0;
        String result = null;
        int backoff = 1000;
        boolean tryAgain;
        do {
            ++attempt;
            if (EMQFetcher.logger.isLoggable(Level.FINE)) {
                EMQFetcher.logger.fine("Attempt #" + attempt + " to fetch ack info ");
            }
            result = this.fetchAckInfoNoRetry("package_name=" + packageName);
            tryAgain = (result == null && attempt <= retries);
            if (tryAgain) {
                final int sleepTime = backoff / 2 + this.random.nextInt(backoff);
                this.sleep(sleepTime);
                if (2 * backoff >= 1024000) {
                    continue;
                }
                backoff *= 2;
            }
        } while (tryAgain);
        if (result == null) {
            throw this.exception(attempt);
        }
        return result;
    }
    
    public String fetchClickInfo(final String packageName, final int retries) throws IOException {
        int attempt = 0;
        String result = null;
        int backoff = 1000;
        boolean tryAgain;
        do {
            ++attempt;
            if (EMQFetcher.logger.isLoggable(Level.FINE)) {
                EMQFetcher.logger.fine("Attempt #" + attempt + " to fetch click info ");
            }
            result = this.fetchClickInfoNoRetry("package_name=" + packageName);
            tryAgain = (result == null && attempt <= retries);
            if (tryAgain) {
                final int sleepTime = backoff / 2 + this.random.nextInt(backoff);
                this.sleep(sleepTime);
                if (2 * backoff >= 1024000) {
                    continue;
                }
                backoff *= 2;
            }
        } while (tryAgain);
        if (result == null) {
            throw this.exception(attempt);
        }
        return result;
    }
    
    public String fetchInvalidRegId(final String packageName, final int retries) throws IOException {
        int attempt = 0;
        String result = null;
        int backoff = 1000;
        boolean tryAgain;
        do {
            ++attempt;
            if (EMQFetcher.logger.isLoggable(Level.FINE)) {
                EMQFetcher.logger.fine("Attempt #" + attempt + " to fetch invalid regid ");
            }
            result = this.fetchInvalidRegIdNoRetry("package_name=" + packageName);
            tryAgain = (result == null && attempt <= retries);
            if (tryAgain) {
                final int sleepTime = backoff / 2 + this.random.nextInt(backoff);
                this.sleep(sleepTime);
                if (2 * backoff >= 1024000) {
                    continue;
                }
                backoff *= 2;
            }
        } while (tryAgain);
        if (result == null) {
            throw this.exception(attempt);
        }
        return result;
    }
    
    private String fetchAckInfoNoRetry(final String packageName) throws IOException {
        HttpURLConnection conn;
        int status;
        try {
            conn = this.doGet(Constants.XmPushRequestPath.V1_EMQ_ACK_INFO, packageName);
            status = conn.getResponseCode();
        }
        catch (IOException e) {
            EMQFetcher.logger.log(Level.WARNING, "IOException while fetch ack info: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e);
            this.lastException = e;
            return null;
        }
        if (status / 100 == 5) {
            EMQFetcher.logger.fine("Service is unavailable (status " + status + "): remote server " + this.remoteHost + "(" + this.remoteIp + ")");
            return null;
        }
        if (status != 200) {
            String responseBody;
            try {
                responseBody = HttpBase.getAndClose(conn.getErrorStream());
                EMQFetcher.logger.finest("Plain get error response: " + responseBody);
            }
            catch (IOException e2) {
                responseBody = "N/A";
                EMQFetcher.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            }
            throw new InvalidRequestException(status, responseBody);
        }
        String responseBody;
        try {
            responseBody = HttpBase.getAndClose(conn.getInputStream());
        }
        catch (IOException e2) {
            EMQFetcher.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            return null;
        }
        return responseBody;
    }
    
    private String fetchClickInfoNoRetry(final String packageName) throws IOException {
        HttpURLConnection conn;
        int status;
        try {
            conn = this.doGet(Constants.XmPushRequestPath.V1_EMQ_CLICK_INFO, packageName);
            status = conn.getResponseCode();
        }
        catch (IOException e) {
            EMQFetcher.logger.log(Level.WARNING, "IOException while fetch click info: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e);
            this.lastException = e;
            return null;
        }
        if (status / 100 == 5) {
            EMQFetcher.logger.fine("Service is unavailable (status " + status + "): remote server " + this.remoteHost + "(" + this.remoteIp + ")");
            return null;
        }
        if (status != 200) {
            String responseBody;
            try {
                responseBody = HttpBase.getAndClose(conn.getErrorStream());
                EMQFetcher.logger.finest("Plain get error response: " + responseBody);
            }
            catch (IOException e2) {
                responseBody = "N/A";
                EMQFetcher.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            }
            throw new InvalidRequestException(status, responseBody);
        }
        String responseBody;
        try {
            responseBody = HttpBase.getAndClose(conn.getInputStream());
        }
        catch (IOException e2) {
            EMQFetcher.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            return null;
        }
        return responseBody;
    }
    
    private String fetchInvalidRegIdNoRetry(final String packageName) throws IOException {
        HttpURLConnection conn;
        int status;
        try {
            conn = this.doGet(Constants.XmPushRequestPath.V1_EMQ_INVALID_REGID, packageName);
            status = conn.getResponseCode();
        }
        catch (IOException e) {
            EMQFetcher.logger.log(Level.WARNING, "IOException while fetch invalid regId: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e);
            this.lastException = e;
            return null;
        }
        if (status / 100 == 5) {
            EMQFetcher.logger.fine("Service is unavailable (status " + status + "): remote server " + this.remoteHost + "(" + this.remoteIp + ")");
            return null;
        }
        if (status != 200) {
            String responseBody;
            try {
                responseBody = HttpBase.getAndClose(conn.getErrorStream());
                EMQFetcher.logger.finest("Plain get error response: " + responseBody);
            }
            catch (IOException e2) {
                responseBody = "N/A";
                EMQFetcher.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            }
            throw new InvalidRequestException(status, responseBody);
        }
        String responseBody;
        try {
            responseBody = HttpBase.getAndClose(conn.getInputStream());
        }
        catch (IOException e2) {
            EMQFetcher.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            return null;
        }
        return responseBody;
    }
}
