// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.logging.Level;
import java.net.URLEncoder;

public class MessageRevoker extends HttpBase
{
    public MessageRevoker(final String security) {
        super(HttpBase.nonNull(security));
    }
    
    public MessageRevoker(final String security, final Region region) {
        super(HttpBase.nonNull(security), region);
    }
    
    public String revokeMessage(final String packageName, final String title, final String description, final int notifyId, final String[] topics, final String msgId, final int retries) throws IOException {
        final StringBuilder body = new StringBuilder("");
        if (!XMStringUtils.isEmpty(packageName)) {
            HttpBase.addParameter(body, "restricted_package_name", URLEncoder.encode(packageName, "UTF-8"));
        }
        if (!XMStringUtils.isEmpty(title)) {
            HttpBase.addParameter(body, "title", URLEncoder.encode(title, "UTF-8"));
        }
        if (!XMStringUtils.isEmpty(description)) {
            HttpBase.addParameter(body, "description", URLEncoder.encode(description, "UTF-8"));
        }
        if (!XMStringUtils.isEmpty(Integer.toString(notifyId))) {
            HttpBase.addParameter(body, "notify_id", URLEncoder.encode(Integer.toString(notifyId), "UTF-8"));
        }
        if (topics != null && topics.length != 0) {
            for (final String topic : topics) {
                HttpBase.addParameter(body, "topics", URLEncoder.encode(topic, "UTF-8"));
            }
        }
        if (!XMStringUtils.isEmpty(msgId)) {
            HttpBase.addParameter(body, "msg_id", URLEncoder.encode(msgId, "UTF-8"));
        }
        final String strBody = body.toString();
        int attempt = 0;
        String result = null;
        int backoff = 1000;
        boolean tryAgain;
        do {
            ++attempt;
            if (MessageRevoker.logger.isLoggable(Level.FINE)) {
                MessageRevoker.logger.fine("Attempt #" + attempt + " to revoke message.");
            }
            result = this.revokeMessageNoRetry(strBody);
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
    
    private String revokeMessageNoRetry(final String params) throws IOException {
        HttpURLConnection conn;
        int status;
        try {
            conn = this.doPost(Constants.XmPushRequestPath.V1_REVOKE_MESSAGE, params);
            status = conn.getResponseCode();
        }
        catch (IOException e) {
            MessageRevoker.logger.log(Level.WARNING, "IOException while fetch revoke message: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e);
            this.lastException = e;
            return null;
        }
        if (status / 100 == 5) {
            MessageRevoker.logger.fine("Service is unavailable (status " + status + "): remote server " + this.remoteHost + "(" + this.remoteIp + ")");
            return null;
        }
        if (status != 200) {
            String responseBody;
            try {
                responseBody = HttpBase.getAndClose(conn.getErrorStream());
                MessageRevoker.logger.finest("Plain get error response: " + responseBody);
            }
            catch (IOException e2) {
                responseBody = "N/A";
                MessageRevoker.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            }
            throw new InvalidRequestException(status, responseBody);
        }
        String responseBody;
        try {
            responseBody = HttpBase.getAndClose(conn.getInputStream());
        }
        catch (IOException e2) {
            MessageRevoker.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            return null;
        }
        return responseBody;
    }
}
