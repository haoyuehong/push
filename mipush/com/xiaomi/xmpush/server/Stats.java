// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Stats extends PushSender<Stats>
{
    protected static final Logger logger;
    
    public Stats(final String security) {
        super(security);
    }
    
    public Stats(final String security, final Region region) {
        super(security, region);
    }
    
    public String getStats(final String startDate, final String endDate, final String packageName, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("start_date", startDate).nameAndValue("end_date", endDate).nameAndValue("restricted_package_name", packageName);
        String result;
        try {
            Stats.logger.fine("get from: " + Constants.XmPushRequestPath.V1_GET_MESSAGE_COUNTERS);
            result = this.get(Constants.XmPushRequestPath.V1_GET_MESSAGE_COUNTERS, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Stats.logger.isLoggable(Level.FINE)) {
                    Stats.logger.fine("Attempt #" + executionCount + " to get realtime stats data between " + startDate + " and " + endDate);
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
    
    protected String getStatsNoRetry(final String startDate, final String endDate, final String packageName) throws IOException {
        return this.getStats(startDate, endDate, packageName, 1);
    }
    
    static {
        logger = Logger.getLogger(Stats.class.getName());
    }
}
