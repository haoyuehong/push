// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import org.json.simple.JSONArray;
import com.xiaomi.push.sdk.ErrorCode;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;
import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import org.json.simple.JSONObject;

public class Sender extends PushSender<Sender>
{
    private static final int BROADCAST_TOPIC_MAX = 5;
    private static final String TOPIC_SPLITTER = ";$;";
    private static final String COMMA = ",";
    
    public Sender(final String security) {
        super(security);
    }
    
    public Sender(final String security, final boolean isVip) {
        super(security, isVip);
    }
    
    public Sender(final String security, final Region region) {
        super(security, region);
    }
    
    public Sender(final String security, final boolean isVip, final Region region) {
        super(security, isVip, region);
    }
    
    public Sender(final String security, final boolean isVip, final String token, final Region region) {
        super(security, isVip, token, region);
    }
    
    public Sender(final String security, final String token) {
        super(security, token);
    }
    
    public Sender(final String security, final String token, final boolean isVip) {
        super(security, token, isVip);
    }
    
    public Sender(final String security, final String token, final Region region) {
        super(security, token, region);
    }
    
    protected static void tryAddJson(final JSONObject json, final String parameterName, final Object value) {
        if (!XMStringUtils.isEmpty(parameterName) && value != null) {
            json.put((Object)parameterName, value);
        }
    }
    
    private static boolean isMultiPackageName(final Message message) {
        final String[] packageNames = message.getRestrictedPackageNames();
        return packageNames == null || packageNames.length == 0 || message.getRestrictedPackageNames().length >= 2;
    }
    
    public Result send(final Message message, final String registrationId, final int retries) throws IOException {
        final NameValuePairs body = this.buildFormDataFromMessage(message).nameAndValue("registration_id", registrationId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V3_REGID_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to send message " + message + " to regIds " + registrationId);
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(retries, null);
        }
        return this.parseResult(response);
    }
    
    public Result unionSend(final Message message, final List<String> registrationIds, final int retries) throws IOException {
        final NameValuePairs body = this.buildFormDataFromMessage(message).nameAndValue("registration_id", String.join(",", registrationIds));
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V4_REGID_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to send message " + message + " to regIds " + registrationIds);
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(retries, null);
        }
        return this.parseResult(response);
    }
    
    private Result sendHybridMessageByRegId(final Message message, final String registrationId, final boolean isDebug, final int retries) throws IOException, ParseException {
        message.getExtra().put("push_server_action", "hybrid_message");
        if (isDebug) {
            message.getExtra().put("hybrid_debug", "1");
        }
        final NameValuePairs body = this.buildFormDataFromMessage(message).nameAndValue("registration_id", registrationId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V2_REGID_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to send message " + message + " to regIds " + registrationId);
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(retries, null);
        }
        return this.parseResult(response);
    }
    
    public Result broadcastHybridAll(final Message message, final int retries) throws IOException, ParseException {
        return this.broadcastHybridAll(message, false, retries);
    }
    
    public Result broadcastHybridAll(final Message message, final boolean isDebug, final int retries) throws IOException, ParseException {
        message.getExtra().put("push_server_action", "hybrid_message");
        if (isDebug) {
            message.getExtra().put("hybrid_debug", "1");
        }
        return this.broadcastAll(message, retries);
    }
    
    public Result broadcast(final Message message, final String topic, final int retries) throws IOException, ParseException {
        final NameValuePairs body = this.buildFormDataFromMessage(message).nameAndValue("topic", topic);
        Result result;
        try {
            final String response = this.post(isMultiPackageName(message) ? Constants.XmPushRequestPath.V3_BROADCAST : Constants.XmPushRequestPath.V2_BROADCAST, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to broadcast message " + message + " to topic: " + topic);
                }
                return;
            }));
            if (XMStringUtils.isBlank(response)) {
                throw this.exception(retries, null);
            }
            result = this.parseResult(response);
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        return result;
    }
    
    public Result multiTopicBroadcast(final Message message, final List<String> topics, final BROADCAST_TOPIC_OP topicOp, final int retries) throws IOException, ParseException, IllegalArgumentException {
        if (topics == null || topics.size() <= 0 || topics.size() > 5) {
            throw new IllegalArgumentException("topics size invalid");
        }
        if (topics.size() == 1) {
            return this.broadcast(message, topics.get(0), retries);
        }
        final NameValuePairs body = this.buildFormDataFromMessage(message).nameAndValue("topic_op", topicOp.toString()).nameAndValue("topics", String.join(";$;", topics));
        try {
            final String response = this.post(isMultiPackageName(message) ? Constants.XmPushRequestPath.V3_MULTI_TOPIC_BROADCAST : Constants.XmPushRequestPath.V2_MULTI_TOPIC_BROADCAST, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to broadcast message " + message + " to topic: " + String.join(";$;", topics) + " op=" + topicOp.toString());
                }
                return;
            }));
            if (XMStringUtils.isBlank(response)) {
                throw this.exception(retries, null);
            }
            final Result result = this.parseResult(response);
            return result;
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
    }
    
    public Result broadcastAll(final Message message, final int retries) throws IOException, ParseException {
        final NameValuePairs body = this.buildFormDataFromMessage(message);
        final NameValuePairs headers = new NameValuePairs().nameAndValue("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        Result result;
        try {
            final String response = this.post(isMultiPackageName(message) ? Constants.XmPushRequestPath.V3_BROADCAST_TO_ALL : Constants.XmPushRequestPath.V2_BROADCAST_TO_ALL, body.toQueryOrFormData(), headers, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + retries + " to broadcast message " + message + " to all.");
                }
                return;
            }));
            if (XMStringUtils.isBlank(response)) {
                throw this.exception(retries, null);
            }
            result = this.parseResult(response);
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        return result;
    }
    
    public Result sendToAlias(final Message message, final String alias, final int retries) throws IOException, ParseException {
        final List<String> aliases = new ArrayList<String>();
        aliases.add(alias);
        return this.sendToAlias(message, aliases, retries);
    }
    
    public Result sendToAlias(final Message message, final List<String> aliases, final int retries) throws IOException, ParseException {
        final NameValuePairs body = this.buildFormDataFromMessage(message).nameAndValue("alias", aliases.toArray());
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V3_ALIAS_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to send message " + message + " to alias [" + String.join(",", aliases) + "].");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(retries, null);
        }
        return this.parseResult(response);
    }
    
    public Result sendToRegion(final Message message, final List<String> regions, final int retries) throws IOException, ParseException {
        final NameValuePairs body = this.buildFormDataFromMessage(message).nameAndValue("region", regions.toArray());
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V2_REGION_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to send message " + message + " to regions [" + String.join(",", regions) + "]");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(retries, null);
        }
        return this.parseResult(response);
    }
    
    public Result sendHybridMessageByRegId(final Message message, final List<String> regIds, final int retries) throws IOException, ParseException {
        return this.sendHybridMessageByRegId(message, regIds, false, retries);
    }
    
    public Result sendHybridMessageByRegId(final Message message, final List<String> regIds, final boolean isDebug, final int retries) throws IOException, ParseException {
        final StringBuilder sb = new StringBuilder(regIds.get(0));
        for (int i = 1; i < regIds.size(); ++i) {
            sb.append(",").append(regIds.get(i));
        }
        return this.sendHybridMessageByRegId(message, sb.toString(), isDebug, retries);
    }
    
    public Result sendToUserAccount(final Message message, final String userAccount, final int retries) throws IOException, ParseException {
        final List<String> userAccounts = new ArrayList<String>();
        userAccounts.add(userAccount);
        return this.sendToUserAccount(message, userAccounts, retries);
    }
    
    public Result sendToUserAccount(final Message message, final List<String> userAccounts, final int retries) throws IOException, ParseException {
        final NameValuePairs body = this.buildFormDataFromMessage(message).nameAndValue("user_account", userAccounts.toArray());
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V2_USERACCOUNT_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to send message " + message + " to user account [" + String.join(",", userAccounts) + "].");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(retries, null);
        }
        return this.parseResult(response);
    }
    
    private NameValuePairs buildFormDataFromMessage(final Message message) {
        final NameValuePairs formData = new NameValuePairs().nameAndValue("restricted_package_name", isMultiPackageName(message) ? ((message.getRestrictedPackageNames() == null) ? null : String.join(",", (CharSequence[])message.getRestrictedPackageNames())) : message.getRestrictedPackageName()).nameAndValue("collapse_key", message.getCollapseKey()).nameAndValue("time_to_live", message.getTimeToLive()).nameAndValue("payload", message.getPayload()).nameAndValue("title", message.getTitle()).nameAndValue("description", message.getDescription()).nameAndValue("notify_type", message.getNotifyType()).nameAndValue("pass_through", message.getPassThrough()).nameAndValue("notify_id", message.getNotifyId()).nameAndValue("time_to_send", message.getTimeToSend());
        final Map<String, String> extraInfo = message.getExtra();
        if (extraInfo != null && !extraInfo.isEmpty()) {
            for (final Map.Entry<String, String> entry : extraInfo.entrySet()) {
                formData.nameAndValue("extra." + entry.getKey(), entry.getValue());
            }
        }
        final Map<String, String> apsProperFieldsInfo = message.getApsProperFields();
        if (apsProperFieldsInfo != null && !apsProperFieldsInfo.isEmpty()) {
            for (final Map.Entry<String, String> entry2 : apsProperFieldsInfo.entrySet()) {
                formData.nameAndValue("aps_proper_fields." + entry2.getKey(), entry2.getValue());
            }
        }
        return formData;
    }
    
    public Result revokeAliasMessage(final List<String> aliases, final int notifyId, final String packageName) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("aliases", String.join(",", aliases)).nameAndValue("restricted_package_name", packageName).nameAndValue("notify_id", notifyId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V1_REVOKE_ALIAS_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke alias message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    public Result revokeRegIdMessage(final List<String> registrationIds, final int notifyId, final String packageName) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("registration_ids", String.join(",", registrationIds)).nameAndValue("restricted_package_name", packageName).nameAndValue("notify_id", notifyId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V1_REVOKE_REGID_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke regid message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(0, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(0, null);
        }
        return this.parseResult(response);
    }
    
    public Result revokeUserAccountMessage(final List<String> userAccounts, final int notifyId, final String packageName) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("user_accounts", String.join(",", userAccounts)).nameAndValue("restricted_package_name", packageName).nameAndValue("notify_id", notifyId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V1_REVOKE_USERACCOUNT_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke user account message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(0, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(0, null);
        }
        return this.parseResult(response);
    }
    
    public Result revokeMiidMessage(final List<String> miids, final int notifyId, final String packageName) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("miids", String.join(",", miids)).nameAndValue("restricted_package_name", packageName).nameAndValue("notify_id", notifyId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V1_REVOKE_MIID_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke miid message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(0, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(0, null);
        }
        return this.parseResult(response);
    }
    
    public Result revokeImeimd5Message(final List<String> imeimd5s, final int notifyId, final String packageName) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("imeimd5s", String.join(",", imeimd5s)).nameAndValue("restricted_package_name", packageName).nameAndValue("notify_id", notifyId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V1_REVOKE_IMEIMD5_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke imeimd5 message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    public Result revokeTopicMessage(final String topic, final int notifyId, final String packageName) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("topic", topic).nameAndValue("restricted_package_name", packageName).nameAndValue("notify_id", notifyId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V1_REVOKE_TOPIC_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke topic message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    public Result revokeMultiTopicMessage(final List<String> topics, final String topicOp, final int notifyId, final String packageName) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("topics", (Object[])topics.toArray(new String[0])).nameAndValue("topic_op", topicOp).nameAndValue("restricted_package_name", packageName).nameAndValue("notify_id", notifyId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V1_REVOKE_MULTITOPIC_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke multi topic message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    public Result revokeMessage(final String packageName, final String msgId, final String jobKey) throws IOException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("msg_id", msgId).nameAndValue("restricted_package_name", packageName).nameAndValue("job_key", jobKey);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V2_REVOKE_MESSAGE, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    public Result stopMessageById(final String packageName, final List<String> msgIds) throws IOException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("msg_ids", (Object[])msgIds.toArray(new String[0])).nameAndValue("restricted_package_name", packageName);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V1_STOP_MESSAGE_BY_ID, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke multi topic message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    public Result stopMessageByJobKey(final String packageName, final List<String> jobKeys) throws IOException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("job_keys", (Object[])jobKeys.toArray(new String[0])).nameAndValue("restricted_package_name", packageName);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V1_STOP_MESSAGE_BY_JOBKEY, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(0, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to revoke multi topic message!");
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    public Result send(final Message message, final List<String> regIds, final int retries) throws IOException, ParseException {
        final StringBuilder sb = new StringBuilder(regIds.get(0));
        for (int i = 1; i < regIds.size(); ++i) {
            sb.append(",").append(regIds.get(i));
        }
        return this.send(message, sb.toString(), retries);
    }
    
    public Result send(final List<TargetedMessage> messages, final int retries) throws IOException, ParseException {
        return this.send(messages, retries, 0L);
    }
    
    public Result send(final List<TargetedMessage> messages, final int retries, final long timeToSend) throws IOException, ParseException {
        if (messages.isEmpty()) {
            Sender.LOGGER.log(Level.WARNING, "Empty message, returned. Remote server " + this.remoteHost + "(" + this.remoteIp + ")");
            return new Result.Builder().errorCode(ErrorCode.Success).build();
        }
        Constants.XmPushRequestPath requestPath;
        if (messages.get(0).getTargetType() == 2) {
            requestPath = Constants.XmPushRequestPath.V2_SEND_MULTI_MESSAGE_WITH_ALIAS;
        }
        else if (messages.get(0).getTargetType() == 3) {
            requestPath = Constants.XmPushRequestPath.V2_SEND_MULTI_MESSAGE_WITH_ACCOUNT;
        }
        else {
            requestPath = Constants.XmPushRequestPath.V2_SEND_MULTI_MESSAGE_WITH_REGID;
        }
        final NameValuePairs body = new NameValuePairs().nameAndValue("messages", this.toString(messages)).nameAndValue("time_to_send", timeToSend);
        String response;
        try {
            response = this.post(requestPath, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Sender.LOGGER.isLoggable(Level.FINE)) {
                    Sender.LOGGER.fine("Attempt #" + executionCount + " to send messages " + messages.size());
                }
                return;
            }));
        }
        catch (Exception e) {
            throw this.exception(retries, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(retries, null);
        }
        return this.parseResult(response);
    }
    
    public Result deleteScheduleJob(final String jobId) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("job_id", jobId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V2_DELETE_SCHEDULE_JOB, body.toQueryOrFormData(), null, null, null);
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    public Result deleteScheduleJobByJobKey(final String jobKey) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("jobkey", jobKey);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V3_DELETE_SCHEDULE_JOB, body.toQueryOrFormData(), null, null, null);
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    public Result checkScheduleJobExist(final String jobId) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("job_id", jobId);
        String response;
        try {
            response = this.post(Constants.XmPushRequestPath.V2_CHECK_SCHEDULE_JOB_EXIST, body.toQueryOrFormData(), null, null, null);
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        if (XMStringUtils.isBlank(response)) {
            throw this.exception(1, null);
        }
        return this.parseResult(response);
    }
    
    private String toString(final List<TargetedMessage> messages) {
        final JSONArray jsonArray = new JSONArray();
        for (final TargetedMessage message : messages) {
            final JSONObject jsonMessage = new JSONObject();
            final JSONObject msg = this.toJson(message.getMessage());
            tryAddJson(jsonMessage, "target", message.getTarget());
            tryAddJson(jsonMessage, "message", msg);
            jsonArray.add((Object)jsonMessage);
        }
        return jsonArray.toString();
    }
    
    @Override
    protected void before(final SenderContext context) {
        super.after(context);
        if (Constants.INCLUDE_LAST_METRICS && this.lastResult != null && this.lastResult.containsKey((Object)"trace_id")) {
            final String traceId = (String)this.lastResult.get((Object)"trace_id");
            context.headers().nameAndValue("X-PUSH-LAST-REQUEST-ID", traceId);
        }
    }
    
    private JSONObject toJson(final Message msg) {
        final JSONObject json = new JSONObject();
        tryAddJson(json, "payload", msg.getPayload());
        tryAddJson(json, "title", msg.getTitle());
        tryAddJson(json, "description", msg.getDescription());
        tryAddJson(json, "notify_type", msg.getNotifyType());
        tryAddJson(json, "notify_id", msg.getNotifyId());
        tryAddJson(json, "pass_through", msg.getPassThrough());
        tryAddJson(json, "restricted_package_name", msg.getRestrictedPackageName());
        tryAddJson(json, "time_to_live", msg.getTimeToLive());
        tryAddJson(json, "time_to_send", msg.getTimeToSend());
        tryAddJson(json, "collapse_key", msg.getCollapseKey());
        final Map<String, String> extraInfo = msg.getExtra();
        if (extraInfo != null && !extraInfo.isEmpty()) {
            final JSONObject extraJson = new JSONObject();
            for (final Map.Entry<String, String> entry : extraInfo.entrySet()) {
                tryAddJson(extraJson, entry.getKey(), entry.getValue());
            }
            tryAddJson(json, "extra", extraJson);
        }
        final Map<String, String> apsInfo = msg.getApsProperFields();
        if (apsInfo != null && !apsInfo.isEmpty()) {
            final JSONObject apsJson = new JSONObject();
            for (final Map.Entry<String, String> entry2 : apsInfo.entrySet()) {
                tryAddJson(apsJson, entry2.getKey(), entry2.getValue());
            }
            tryAddJson(json, "aps_proper_fields", apsJson);
        }
        return json;
    }
    
    public enum BROADCAST_TOPIC_OP
    {
        UNION, 
        INTERSECTION, 
        EXCEPT;
    }
}
