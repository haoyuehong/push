// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Feedback extends PushSender<Feedback>
{
    private static final Logger logger;
    private static final String REG_ID_SPLITTER = ",";
    
    public Feedback(final String security) {
        super(security);
    }
    
    public Feedback(final String security, final Region region) {
        super(security, region);
    }
    
    @Deprecated
    public String getInvalidRegIds(final int retries) throws IOException {
        String result;
        try {
            Feedback.logger.fine("get from: " + Constants.XmPushRequestPath.V1_FEEDBACK_INVALID_REGID);
            result = this.get(Constants.XmPushRequestPath.V1_FEEDBACK_INVALID_REGID, null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Feedback.logger.isLoggable(Level.FINE)) {
                    Feedback.logger.fine("Attempt #" + executionCount + " to get invalid registration ids");
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
    
    @Deprecated
    public String getInvalidAliases(final int retries) throws IOException {
        String result;
        try {
            Feedback.logger.fine("get from: " + Constants.XmPushRequestPath.V1_FEEDBACK_INVALID_ALIAS);
            result = this.get(Constants.XmPushRequestPath.V1_FEEDBACK_INVALID_ALIAS, null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Feedback.logger.isLoggable(Level.FINE)) {
                    Feedback.logger.fine("Attempt #" + executionCount + " to get invalid registration ids");
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
    
    public String getRegionByRegIds(final List<String> regIds, final int retries) throws IOException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("regIds", String.join(",", regIds));
        String result;
        try {
            Feedback.logger.fine("get from: " + Constants.XmPushRequestPath.V1_FEEDBACK_REGID_REGION);
            result = this.post(Constants.XmPushRequestPath.V1_FEEDBACK_REGID_REGION, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Feedback.logger.isLoggable(Level.FINE)) {
                    Feedback.logger.fine("Attempt #" + executionCount + " to get region by regid");
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
    
    @Deprecated
    public String getInvalidRegIdsNoRetry() throws IOException {
        return this.getInvalidRegIds(1);
    }
    
    @Deprecated
    public String getInvalidAliasesNoRetry() throws IOException {
        return this.getInvalidAliases(1);
    }
    
    public String getRegionByRegIdsNoRetry(final List<String> regIds) throws IOException {
        return this.getRegionByRegIds(regIds, 1);
    }
    
    static {
        logger = Logger.getLogger(Feedback.class.getName());
    }
}
