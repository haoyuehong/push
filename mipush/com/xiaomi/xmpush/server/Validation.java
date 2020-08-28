// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.List;
import java.util.logging.Logger;

public class Validation extends PushSender<Validation>
{
    protected static final Logger logger;
    
    public Validation(final String security) {
        super(security);
    }
    
    public Validation(final String security, final Region region) {
        super(security, region);
    }
    
    public String validateRegistrationIds(final List<String> regIds, final int retries) throws IOException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("registration_ids", regIds.toArray());
        String result;
        try {
            result = this.post(Constants.XmPushRequestPath.V1_VALIDATE_REGID, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Validation.logger.isLoggable(Level.FINE)) {
                    Validation.logger.fine("Attempt #" + executionCount + " to validate regids.");
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
    
    public String validateRegistrationIdsNoRetry(final List<String> regIds) throws IOException {
        return this.validateRegistrationIds(regIds, 1);
    }
    
    static {
        logger = Logger.getLogger(Validation.class.getName());
    }
}
