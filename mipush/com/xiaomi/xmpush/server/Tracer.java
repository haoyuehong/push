// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tracer extends PushSender<Tracer>
{
    protected static final Logger logger;
    
    public Tracer(final String security) {
        super(security);
    }
    
    public Tracer(final String security, final Region region) {
        super(security, region);
    }
    
    public String getMessageGroupStatus(final String jobKey, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("job_key", jobKey);
        String result;
        try {
            result = this.get(Constants.XmPushRequestPath.V1_MESSAGE_STATUS, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Tracer.logger.isLoggable(Level.FINE)) {
                    Tracer.logger.fine("Attempt #" + executionCount + " to get status of message group " + jobKey);
                }
                return;
            }));
            if (XMStringUtils.isBlank(result)) {
                throw this.exception(retries, null);
            }
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        return result;
    }
    
    public String getMessageStatus(final String msgId, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("msg_id", msgId);
        String result;
        try {
            result = this.get(Constants.XmPushRequestPath.V1_MESSAGE_STATUS, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Tracer.logger.isLoggable(Level.FINE)) {
                    Tracer.logger.fine("Attempt #" + executionCount + " to get status of message " + msgId);
                }
                return;
            }));
            if (XMStringUtils.isBlank(result)) {
                throw this.exception(retries, null);
            }
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        return result;
    }
    
    public String getMessageStatus(final long beginTime, final long endTime, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("begin_time", beginTime).nameAndValue("end_time", endTime);
        String result;
        try {
            result = this.get(Constants.XmPushRequestPath.V1_MESSAGES_STATUS, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Tracer.logger.isLoggable(Level.FINE)) {
                    Tracer.logger.fine("Attempt #" + executionCount + " to get messages status between " + beginTime + " and " + endTime);
                }
                return;
            }));
            if (XMStringUtils.isBlank(result)) {
                throw this.exception(retries, null);
            }
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        return result;
    }
    
    public String getMessageGroupStatusNoRetry(final String jobKey) throws IOException {
        return this.getMessageGroupStatus(jobKey, 1);
    }
    
    public String getMessageStatusNoRetry(final String msgId) throws IOException {
        return this.getMessageStatus(msgId, 1);
    }
    
    public String getMessageStatusNoRetry(final long beginTime, final long endTime) throws IOException {
        return this.getMessageStatus(beginTime, endTime, 1);
    }
    
    static {
        logger = Logger.getLogger(Tracer.class.getName());
    }
}
