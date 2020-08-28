// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DevTools extends PushSender<DevTools>
{
    private static final Logger logger;
    private static final String REG_ID_SPLITTER = ",";
    private final Map<String, Region> LOCAL_REGION_MAP;
    
    public DevTools(final String security) {
        super(security);
        this.LOCAL_REGION_MAP = (Map<String, Region>)new HashMap() {
            {
                this.put("CN", Region.China);
                this.put("IN", Region.India);
                this.put("RU", Region.Russia);
                this.put("ES", Region.Europe);
                this.put("PL", Region.Europe);
                this.put("GB", Region.Europe);
                this.put("GR", Region.Europe);
                this.put("CZ", Region.Europe);
                this.put("DE", Region.Europe);
                this.put("IT", Region.Europe);
                this.put("FR", Region.Europe);
                this.put("HU", Region.Europe);
                this.put("SK", Region.Europe);
                this.put("RO", Region.Europe);
                this.put("PT", Region.Europe);
                this.put("BG", Region.Europe);
                this.put("LT", Region.Europe);
                this.put("EE", Region.Europe);
                this.put("UK", Region.Europe);
                this.put("LV", Region.Europe);
                this.put("BE", Region.Europe);
                this.put("NL", Region.Europe);
                this.put("AT", Region.Europe);
                this.put("SI", Region.Europe);
                this.put("EU", Region.Europe);
                this.put("SE", Region.Europe);
                this.put("FI", Region.Europe);
                this.put("DK", Region.Europe);
                this.put("MT", Region.Europe);
                this.put("IE", Region.Europe);
                this.put("CY", Region.Europe);
                this.put("LU", Region.Europe);
                this.put("ID", Region.Other);
                this.put("UA", Region.Other);
                this.put("MY", Region.Other);
                this.put("TW", Region.Other);
                this.put("HK", Region.Other);
                this.put("US", Region.Other);
                this.put("VN", Region.Other);
                this.put("BD", Region.Other);
                this.put("AL", Region.Other);
                this.put("SG", Region.Other);
                this.put("DZ", Region.Other);
                this.put("KZ", Region.Other);
                this.put("BR", Region.Other);
                this.put("BY", Region.Other);
                this.put("IL", Region.Other);
                this.put("MM", Region.Other);
                this.put("UZ", Region.Other);
                this.put("TR", Region.Other);
                this.put("EG", Region.Other);
                this.put("PH", Region.Other);
                this.put("AF", Region.Other);
                this.put("TH", Region.Other);
                this.put("MX", Region.Other);
                this.put("AO", Region.Other);
                this.put("IR", Region.Other);
                this.put("AR", Region.Other);
                this.put("NP", Region.Other);
                this.put("CO", Region.Other);
                this.put("PK", Region.Other);
                this.put("AE", Region.Other);
                this.put("AG", Region.Other);
                this.put("UY", Region.Other);
                this.put("KR", Region.Other);
                this.put("BH", Region.Other);
                this.put("BS", Region.Other);
                this.put("AZ", Region.Other);
                this.put("SA", Region.Other);
                this.put("CL", Region.Other);
                this.put("BO", Region.Other);
                this.put("QA", Region.Other);
                this.put("ZA", Region.Other);
                this.put("MA", Region.Other);
                this.put("KG", Region.Other);
                this.put("MD", Region.Other);
                this.put("AM", Region.Other);
                this.put("AS", Region.Other);
                this.put("AD", Region.Other);
                this.put("PE", Region.Other);
                this.put("BB", Region.Other);
                this.put("IQ", Region.Other);
                this.put("AI", Region.Other);
                this.put("CR", Region.Other);
                this.put("EC", Region.Other);
                this.put("PS", Region.Other);
                this.put("HR", Region.Other);
                this.put("RS", Region.Other);
                this.put("GQ", Region.Other);
                this.put("BW", Region.Other);
                this.put("LK", Region.Other);
                this.put("BZ", Region.Other);
                this.put("AU", Region.Other);
                this.put("CM", Region.Other);
                this.put("JO", Region.Other);
                this.put("CI", Region.Other);
                this.put("GE", Region.Other);
                this.put("ZW", Region.Other);
                this.put("NG", Region.Other);
                this.put("KH", Region.Other);
                this.put("CD", Region.Other);
                this.put("OM", Region.Other);
                this.put("YE", Region.Other);
                this.put("BA", Region.Other);
                this.put("KW", Region.Other);
                this.put("ET", Region.Other);
                this.put("TM", Region.Other);
                this.put("KE", Region.Other);
                this.put("GH", Region.Other);
                this.put("VE", Region.Other);
                this.put("CU", Region.Other);
                this.put("GF", Region.Other);
                this.put("ZM", Region.Other);
                this.put("CH", Region.Other);
                this.put("GD", Region.Other);
                this.put("JP", Region.Other);
                this.put("TJ", Region.Other);
                this.put("DO", Region.Other);
                this.put("GT", Region.Other);
                this.put("SV", Region.Other);
                this.put("MK", Region.Other);
                this.put("CA", Region.Other);
                this.put("PY", Region.Other);
                this.put("EN", Region.Other);
                this.put("MW", Region.Other);
                this.put("KP", Region.Other);
                this.put("MU", Region.Other);
                this.put("FK", Region.Other);
                this.put("SD", Region.Other);
                this.put("UG", Region.Other);
                this.put("SY", Region.Other);
                this.put("HT", Region.Other);
                this.put("HN", Region.Other);
                this.put("MO", Region.Other);
                this.put("TN", Region.Other);
                this.put("AW", Region.Other);
                this.put("TZ", Region.Other);
                this.put("ME", Region.Other);
                this.put("SZ", Region.Other);
                this.put("MZ", Region.Other);
                this.put("LB", Region.Other);
                this.put("SN", Region.Other);
                this.put("DM", Region.Other);
                this.put("MV", Region.Other);
                this.put("JM", Region.Other);
                this.put("NI", Region.Other);
                this.put("RW", Region.Other);
                this.put("LY", Region.Other);
                this.put("BN", Region.Other);
                this.put("NA", Region.Other);
                this.put("GS", Region.Other);
                this.put("KHM", Region.Other);
                this.put("PA", Region.Other);
                this.put("GY", Region.Other);
                this.put("TT", Region.Other);
                this.put("LS", Region.Other);
                this.put("PNG", Region.Other);
                this.put("NO", Region.Other);
                this.put("VC", Region.Other);
                this.put("NZ", Region.Other);
                this.put("MR", Region.Other);
                this.put("BT", Region.Other);
                this.put("SR", Region.Other);
                this.put("IS", Region.Other);
                this.put("SO", Region.Other);
                this.put("MN", Region.Other);
                this.put("LC", Region.Other);
                this.put("ML", Region.Other);
                this.put("VG", Region.Other);
                this.put("BJ", Region.Other);
                this.put("ER", Region.Other);
                this.put("CV", Region.Other);
                this.put("KN", Region.Other);
                this.put("IM", Region.Other);
                this.put("TD", Region.Other);
                this.put("BI", Region.Other);
                this.put("FJ", Region.Other);
                this.put("LA", Region.Other);
                this.put("VU", Region.Other);
                this.put("PF", Region.Other);
                this.put("BF", Region.Other);
                this.put("LI", Region.Other);
                this.put("GA", Region.Other);
                this.put("BQ", Region.Other);
                this.put("KM", Region.Other);
                this.put("CK", Region.Other);
                this.put("MG", Region.Other);
                this.put("GI", Region.Other);
                this.put("GN", Region.Other);
                this.put("DJ", Region.Other);
                this.put("KY", Region.Other);
                this.put("YT", Region.Other);
                this.put("TL", Region.Other);
                this.put("GU", Region.Other);
                this.put("GM", Region.Other);
                this.put("TG", Region.Other);
                this.put("GL", Region.Other);
                this.put("ZH", Region.Other);
                this.put("NC", Region.Other);
                this.put("SS", Region.Other);
                this.put("NU", Region.Other);
                this.put("WS", Region.Other);
                this.put("FO", Region.Other);
                this.put("NR", Region.Other);
                this.put("PW", Region.Other);
                this.put("MP", Region.Other);
                this.put("FM", Region.Other);
                this.put("GG", Region.Other);
                this.put("LR", Region.Other);
                this.put("PG", Region.Other);
                this.put("KI", Region.Other);
                this.put("MS", Region.Other);
                this.put("TV", Region.Other);
                this.put("GW", Region.Other);
                this.put("SH", Region.Other);
                this.put("SC", Region.Other);
                this.put("JE", Region.Other);
                this.put("SL", Region.Other);
                this.put("PR", Region.Other);
                this.put("TO", Region.Other);
                this.put("SM", Region.Other);
                this.put("SB", Region.Other);
                this.put("PM", Region.Other);
                this.put("MH", Region.Other);
                this.put("NF", Region.Other);
                this.put("ST", Region.Other);
                this.put("TK", Region.Other);
                this.put("LG", Region.Other);
                this.put("TC", Region.Other);
            }
        };
    }
    
    public DevTools(final String security, final Region region) {
        super(security, region);
        this.LOCAL_REGION_MAP = (Map<String, Region>)new HashMap() {
            {
                this.put("CN", Region.China);
                this.put("IN", Region.India);
                this.put("RU", Region.Russia);
                this.put("ES", Region.Europe);
                this.put("PL", Region.Europe);
                this.put("GB", Region.Europe);
                this.put("GR", Region.Europe);
                this.put("CZ", Region.Europe);
                this.put("DE", Region.Europe);
                this.put("IT", Region.Europe);
                this.put("FR", Region.Europe);
                this.put("HU", Region.Europe);
                this.put("SK", Region.Europe);
                this.put("RO", Region.Europe);
                this.put("PT", Region.Europe);
                this.put("BG", Region.Europe);
                this.put("LT", Region.Europe);
                this.put("EE", Region.Europe);
                this.put("UK", Region.Europe);
                this.put("LV", Region.Europe);
                this.put("BE", Region.Europe);
                this.put("NL", Region.Europe);
                this.put("AT", Region.Europe);
                this.put("SI", Region.Europe);
                this.put("EU", Region.Europe);
                this.put("SE", Region.Europe);
                this.put("FI", Region.Europe);
                this.put("DK", Region.Europe);
                this.put("MT", Region.Europe);
                this.put("IE", Region.Europe);
                this.put("CY", Region.Europe);
                this.put("LU", Region.Europe);
                this.put("ID", Region.Other);
                this.put("UA", Region.Other);
                this.put("MY", Region.Other);
                this.put("TW", Region.Other);
                this.put("HK", Region.Other);
                this.put("US", Region.Other);
                this.put("VN", Region.Other);
                this.put("BD", Region.Other);
                this.put("AL", Region.Other);
                this.put("SG", Region.Other);
                this.put("DZ", Region.Other);
                this.put("KZ", Region.Other);
                this.put("BR", Region.Other);
                this.put("BY", Region.Other);
                this.put("IL", Region.Other);
                this.put("MM", Region.Other);
                this.put("UZ", Region.Other);
                this.put("TR", Region.Other);
                this.put("EG", Region.Other);
                this.put("PH", Region.Other);
                this.put("AF", Region.Other);
                this.put("TH", Region.Other);
                this.put("MX", Region.Other);
                this.put("AO", Region.Other);
                this.put("IR", Region.Other);
                this.put("AR", Region.Other);
                this.put("NP", Region.Other);
                this.put("CO", Region.Other);
                this.put("PK", Region.Other);
                this.put("AE", Region.Other);
                this.put("AG", Region.Other);
                this.put("UY", Region.Other);
                this.put("KR", Region.Other);
                this.put("BH", Region.Other);
                this.put("BS", Region.Other);
                this.put("AZ", Region.Other);
                this.put("SA", Region.Other);
                this.put("CL", Region.Other);
                this.put("BO", Region.Other);
                this.put("QA", Region.Other);
                this.put("ZA", Region.Other);
                this.put("MA", Region.Other);
                this.put("KG", Region.Other);
                this.put("MD", Region.Other);
                this.put("AM", Region.Other);
                this.put("AS", Region.Other);
                this.put("AD", Region.Other);
                this.put("PE", Region.Other);
                this.put("BB", Region.Other);
                this.put("IQ", Region.Other);
                this.put("AI", Region.Other);
                this.put("CR", Region.Other);
                this.put("EC", Region.Other);
                this.put("PS", Region.Other);
                this.put("HR", Region.Other);
                this.put("RS", Region.Other);
                this.put("GQ", Region.Other);
                this.put("BW", Region.Other);
                this.put("LK", Region.Other);
                this.put("BZ", Region.Other);
                this.put("AU", Region.Other);
                this.put("CM", Region.Other);
                this.put("JO", Region.Other);
                this.put("CI", Region.Other);
                this.put("GE", Region.Other);
                this.put("ZW", Region.Other);
                this.put("NG", Region.Other);
                this.put("KH", Region.Other);
                this.put("CD", Region.Other);
                this.put("OM", Region.Other);
                this.put("YE", Region.Other);
                this.put("BA", Region.Other);
                this.put("KW", Region.Other);
                this.put("ET", Region.Other);
                this.put("TM", Region.Other);
                this.put("KE", Region.Other);
                this.put("GH", Region.Other);
                this.put("VE", Region.Other);
                this.put("CU", Region.Other);
                this.put("GF", Region.Other);
                this.put("ZM", Region.Other);
                this.put("CH", Region.Other);
                this.put("GD", Region.Other);
                this.put("JP", Region.Other);
                this.put("TJ", Region.Other);
                this.put("DO", Region.Other);
                this.put("GT", Region.Other);
                this.put("SV", Region.Other);
                this.put("MK", Region.Other);
                this.put("CA", Region.Other);
                this.put("PY", Region.Other);
                this.put("EN", Region.Other);
                this.put("MW", Region.Other);
                this.put("KP", Region.Other);
                this.put("MU", Region.Other);
                this.put("FK", Region.Other);
                this.put("SD", Region.Other);
                this.put("UG", Region.Other);
                this.put("SY", Region.Other);
                this.put("HT", Region.Other);
                this.put("HN", Region.Other);
                this.put("MO", Region.Other);
                this.put("TN", Region.Other);
                this.put("AW", Region.Other);
                this.put("TZ", Region.Other);
                this.put("ME", Region.Other);
                this.put("SZ", Region.Other);
                this.put("MZ", Region.Other);
                this.put("LB", Region.Other);
                this.put("SN", Region.Other);
                this.put("DM", Region.Other);
                this.put("MV", Region.Other);
                this.put("JM", Region.Other);
                this.put("NI", Region.Other);
                this.put("RW", Region.Other);
                this.put("LY", Region.Other);
                this.put("BN", Region.Other);
                this.put("NA", Region.Other);
                this.put("GS", Region.Other);
                this.put("KHM", Region.Other);
                this.put("PA", Region.Other);
                this.put("GY", Region.Other);
                this.put("TT", Region.Other);
                this.put("LS", Region.Other);
                this.put("PNG", Region.Other);
                this.put("NO", Region.Other);
                this.put("VC", Region.Other);
                this.put("NZ", Region.Other);
                this.put("MR", Region.Other);
                this.put("BT", Region.Other);
                this.put("SR", Region.Other);
                this.put("IS", Region.Other);
                this.put("SO", Region.Other);
                this.put("MN", Region.Other);
                this.put("LC", Region.Other);
                this.put("ML", Region.Other);
                this.put("VG", Region.Other);
                this.put("BJ", Region.Other);
                this.put("ER", Region.Other);
                this.put("CV", Region.Other);
                this.put("KN", Region.Other);
                this.put("IM", Region.Other);
                this.put("TD", Region.Other);
                this.put("BI", Region.Other);
                this.put("FJ", Region.Other);
                this.put("LA", Region.Other);
                this.put("VU", Region.Other);
                this.put("PF", Region.Other);
                this.put("BF", Region.Other);
                this.put("LI", Region.Other);
                this.put("GA", Region.Other);
                this.put("BQ", Region.Other);
                this.put("KM", Region.Other);
                this.put("CK", Region.Other);
                this.put("MG", Region.Other);
                this.put("GI", Region.Other);
                this.put("GN", Region.Other);
                this.put("DJ", Region.Other);
                this.put("KY", Region.Other);
                this.put("YT", Region.Other);
                this.put("TL", Region.Other);
                this.put("GU", Region.Other);
                this.put("GM", Region.Other);
                this.put("TG", Region.Other);
                this.put("GL", Region.Other);
                this.put("ZH", Region.Other);
                this.put("NC", Region.Other);
                this.put("SS", Region.Other);
                this.put("NU", Region.Other);
                this.put("WS", Region.Other);
                this.put("FO", Region.Other);
                this.put("NR", Region.Other);
                this.put("PW", Region.Other);
                this.put("MP", Region.Other);
                this.put("FM", Region.Other);
                this.put("GG", Region.Other);
                this.put("LR", Region.Other);
                this.put("PG", Region.Other);
                this.put("KI", Region.Other);
                this.put("MS", Region.Other);
                this.put("TV", Region.Other);
                this.put("GW", Region.Other);
                this.put("SH", Region.Other);
                this.put("SC", Region.Other);
                this.put("JE", Region.Other);
                this.put("SL", Region.Other);
                this.put("PR", Region.Other);
                this.put("TO", Region.Other);
                this.put("SM", Region.Other);
                this.put("SB", Region.Other);
                this.put("PM", Region.Other);
                this.put("MH", Region.Other);
                this.put("NF", Region.Other);
                this.put("ST", Region.Other);
                this.put("TK", Region.Other);
                this.put("LG", Region.Other);
                this.put("TC", Region.Other);
            }
        };
    }
    
    public String getAliasesOf(final String packageName, final String regId, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("restricted_package_name", packageName).nameAndValue("registration_id", regId);
        String result;
        try {
            DevTools.logger.fine("get from: " + Constants.XmPushRequestPath.V1_GET_ALL_ALIAS);
            result = this.get(Constants.XmPushRequestPath.V1_GET_ALL_ALIAS, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (DevTools.logger.isLoggable(Level.FINE)) {
                    DevTools.logger.fine("Attempt #" + executionCount + " to get all aliases of the device.");
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
    
    public String getTopicsOf(final String packageName, final String regId, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("restricted_package_name", packageName).nameAndValue("registration_id", regId);
        String result;
        try {
            DevTools.logger.fine("get from: " + Constants.XmPushRequestPath.V1_GET_ALL_TOPIC);
            result = this.get(Constants.XmPushRequestPath.V1_GET_ALL_TOPIC, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (DevTools.logger.isLoggable(Level.FINE)) {
                    DevTools.logger.fine("Attempt #" + executionCount + " to get all topics of the device.");
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
    
    public String getAccountsOf(final String packageName, final String regId, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("restricted_package_name", packageName).nameAndValue("registration_id", regId);
        String result;
        try {
            DevTools.logger.fine("get from: " + Constants.XmPushRequestPath.V1_GET_ALL_ACCOUNT);
            result = this.get(Constants.XmPushRequestPath.V1_GET_ALL_ACCOUNT, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (DevTools.logger.isLoggable(Level.FINE)) {
                    DevTools.logger.fine("Attempt #" + executionCount + " to get all user account of the device.");
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
    
    public String getPresence(final String packageName, final String regId, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("restricted_package_name", packageName).nameAndValue("registration_id", regId);
        String result;
        try {
            final Constants.RequestPath requestPath = regId.contains(",") ? Constants.XmPushRequestPath.V2_REGID_PRESENCE : Constants.XmPushRequestPath.V1_REGID_PRESENCE;
            DevTools.logger.fine("get from: " + requestPath);
            result = this.get(requestPath, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (DevTools.logger.isLoggable(Level.FINE)) {
                    DevTools.logger.fine("Attempt #" + executionCount + " to get presence of the device.");
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
    
    public String getPresence(final String packageName, final List<String> regIds, final int retries) throws IOException {
        return this.getPresence(packageName, String.join(",", regIds), retries);
    }
    
    public String getPresenceV3(final String packageName, final List<String> regIds, final int retries) throws IOException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("restricted_package_name", packageName).nameAndValue("registration_id", String.join(",", regIds));
        String result;
        try {
            final Constants.RequestPath requestPath = Constants.XmPushRequestPath.V3_REGID_PRESENCE;
            DevTools.logger.fine("get from: " + requestPath);
            result = this.post(requestPath, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (DevTools.logger.isLoggable(Level.FINE)) {
                    DevTools.logger.fine("Attempt #" + executionCount + " to get presence of the device.");
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
    
    public Region localeToRegion(final String locale) {
        return this.LOCAL_REGION_MAP.get(locale);
    }
    
    static {
        logger = Logger.getLogger(DevTools.class.getName());
    }
}
