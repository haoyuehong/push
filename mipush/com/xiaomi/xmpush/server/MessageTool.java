// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.net.HttpURLConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.logging.Level;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.URLEncoder;

public class MessageTool extends HttpBase
{
    public MessageTool(final String security) {
        super(HttpBase.nonNull(security));
    }
    
    public MessageTool(final String security, final Region region) {
        super(HttpBase.nonNull(security), region);
    }
    
    public Result deleteTopic(final String jobId, final int retries) throws IOException, ParseException {
        final StringBuilder body = HttpBase.newBody("id", URLEncoder.encode(jobId, "UTF-8"));
        return this.sendMessage(Constants.XmPushRequestPath.V2_DELETE_BROADCAST_MESSAGE, body, retries);
    }
    
    public Result deleteTopic(final String jobId) throws IOException, ParseException {
        return this.deleteTopic(jobId, 1);
    }
    
    protected Result sendMessage(final Constants.RequestPath requestPath, final StringBuilder body, final int retries) throws IOException, ParseException {
        int attempt = 0;
        Result result = null;
        int backoff = 1000;
        boolean tryAgain = false;
        do {
            ++attempt;
            if (MessageTool.logger.isLoggable(Level.FINE)) {
                MessageTool.logger.fine("Attempt #" + attempt + " to send " + (Object)body + " to url " + requestPath.getPath());
            }
            String bodyStr = body.toString();
            if (bodyStr.charAt(0) == '&') {
                bodyStr = body.toString().substring(1);
            }
            result = this.sendMessage(requestPath, bodyStr);
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
    
    protected Result sendMessage(final Constants.RequestPath requestPath, final String message) throws IOException, ParseException {
        HttpURLConnection conn;
        int status;
        try {
            MessageTool.logger.fine("post to: " + requestPath.getPath());
            conn = this.doPost(requestPath, message);
            status = conn.getResponseCode();
        }
        catch (IOException e) {
            this.lastException = e;
            MessageTool.logger.log(Level.WARNING, "IOException send message: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e);
            return null;
        }
        if (status / 100 == 5) {
            MessageTool.logger.fine("XmPush service is unavailable (status " + status + "): remote server " + this.remoteHost + "(" + this.remoteIp + ")");
            return null;
        }
        if (status != 200) {
            String responseBody;
            try {
                responseBody = HttpBase.getAndClose(conn.getErrorStream());
                MessageTool.logger.finest("Plain post error response: " + responseBody);
            }
            catch (IOException e2) {
                responseBody = "N/A";
                this.lastException = e2;
                MessageTool.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            }
            throw new InvalidRequestException(status, responseBody);
        }
        String responseBody;
        try {
            responseBody = HttpBase.getAndClose(conn.getInputStream());
        }
        catch (IOException e2) {
            MessageTool.logger.log(Level.WARNING, "Exception reading response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", e2);
            return null;
        }
        try {
            final JSONParser parser = new JSONParser();
            final JSONObject json = (JSONObject)parser.parse(responseBody);
            return new Result.Builder().fromJson(json);
        }
        catch (ParseException e3) {
            MessageTool.logger.log(Level.WARNING, "Exception parsing response: remote server " + this.remoteHost + "(" + this.remoteIp + ")", (Throwable)e3);
            throw new IOException("Invalid response from XmPush: " + responseBody);
        }
    }
}
