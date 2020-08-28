// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.util.logging.Level;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Subscription extends PushSender<Subscription>
{
    public Subscription(final String security) {
        super(security);
    }
    
    public Subscription(final String security, final Region region) {
        super(security, region);
    }
    
    protected static String joinString(final List<String> stringList, final char sep) {
        final StringBuffer sb = new StringBuffer();
        for (final String s : stringList) {
            sb.append(sep).append(s);
        }
        return sb.substring(1);
    }
    
    public Result subscribeTopic(final List<String> regIds, final String topic, final String category, final int retries) throws IOException, ParseException {
        return this.topicSubscribeBase(regIds, topic, category, retries, true);
    }
    
    public Result subscribeTopic(final List<String> regIds, final String topic, final String packageName, final String category, final int retries) throws IOException, ParseException {
        return this.topicSubscribeBase(regIds, topic, packageName, category, retries, true);
    }
    
    public Result subscribeTopic(final List<String> regIds, final String topic, final String category) throws IOException, ParseException {
        return this.subscribeTopic(regIds, topic, category, 1);
    }
    
    public Result subscribeTopic(final String regId, final String topic, final String category, final int retries) throws IOException, ParseException {
        return this.subscribeTopic(Arrays.asList(regId), topic, category, retries);
    }
    
    public Result subscribeTopic(final String regId, final String topic, final String packageName, final String category, final int retries) throws IOException, ParseException {
        final List<String> regIds = new ArrayList<String>();
        regIds.add(regId);
        return this.subscribeTopic(regIds, topic, packageName, category, retries);
    }
    
    public Result subscribeTopic(final String regId, final String topic, final String category) throws IOException, ParseException {
        return this.subscribeTopic(regId, topic, category, 1);
    }
    
    public Result subscribeTopic(final String regId, final String topic, final String packageName, final String category) throws IOException, ParseException {
        return this.subscribeTopic(regId, topic, packageName, category, 1);
    }
    
    public Result unsubscribeTopic(final List<String> regIds, final String topic, final String category, final int retries) throws IOException, ParseException {
        return this.topicSubscribeBase(regIds, topic, category, retries, false);
    }
    
    public Result unsubscribeTopic(final List<String> regIds, final String topic, final String category) throws IOException, ParseException {
        return this.unsubscribeTopic(regIds, topic, category, 1);
    }
    
    public Result unsubscribeTopic(final List<String> regIds, final String topic, final String packageName, final String category, final int retries) throws IOException, ParseException {
        return this.topicSubscribeBase(regIds, topic, packageName, category, retries, false);
    }
    
    public Result unsubscribeTopic(final String regId, final String topic, final String category, final int retries) throws IOException, ParseException {
        final List<String> regIds = new ArrayList<String>();
        regIds.add(regId);
        return this.unsubscribeTopic(regIds, topic, category, retries);
    }
    
    public Result unsubscribeTopic(final String regId, final String topic, final String packageName, final String category, final int retries) throws IOException, ParseException {
        return this.unsubscribeTopic(Arrays.asList(regId), topic, packageName, category, retries);
    }
    
    public Result unsubscribeTopic(final String regId, final String topic, final String category) throws IOException, ParseException {
        return this.unsubscribeTopic(regId, topic, category, 1);
    }
    
    public Result unsubscribeTopic(final String regId, final String topic, final String packageName, final String category) throws IOException, ParseException {
        return this.unsubscribeTopic(regId, topic, packageName, category, 1);
    }
    
    public Result subscribeTopicByAlias(final String topic, final List<String> aliases, final String category, final int retries) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("aliases", String.join(",", aliases)).nameAndValue("topic", topic);
        if (category != null) {
            body.nameAndValue("category", category);
        }
        Result result;
        try {
            final String response = this.post(Constants.XmPushRequestPath.V2_SUBSCRIBE_TOPIC_BY_ALIAS, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Subscription.LOGGER.isLoggable(Level.FINE)) {
                    Subscription.LOGGER.fine("Attempt #" + executionCount + " to subscribe topic " + topic + " for aliases " + aliases);
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
    
    public Result subscribeTopicByAlias(final String topic, final List<String> aliases, final String packageName, final String category, final int retries) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("aliases", String.join(",", aliases)).nameAndValue("topic", topic);
        if (!XMStringUtils.isBlank(packageName)) {
            body.nameAndValue("restricted_package_name", packageName);
        }
        if (category != null) {
            body.nameAndValue("category", category);
        }
        Result result;
        try {
            final String response = this.post(Constants.XmPushRequestPath.V2_SUBSCRIBE_TOPIC_BY_ALIAS, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Subscription.LOGGER.isLoggable(Level.FINE)) {
                    Subscription.LOGGER.fine("Attempt #" + executionCount + " to subscribe topic " + topic + " for aliases " + aliases);
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
    
    public Result unsubscribeTopicByAlias(final String topic, final List<String> aliases, final String category, final int retries) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("aliases", String.join(",", aliases)).nameAndValue("topic", topic);
        if (category != null) {
            body.nameAndValue("category", category);
        }
        Result result;
        try {
            final String response = this.post(Constants.XmPushRequestPath.V2_UNSUBSCRIBE_TOPIC_BY_ALIAS, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Subscription.LOGGER.isLoggable(Level.FINE)) {
                    Subscription.LOGGER.fine("Attempt #" + executionCount + " to unsubscribe topic " + topic + " for aliases " + aliases);
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
    
    public Result unsubscribeTopicByAlias(final String topic, final List<String> aliases, final String packageName, final String category, final int retries) throws IOException, ParseException {
        final NameValuePairs body = new NameValuePairs().nameAndValue("aliases", String.join(",", aliases)).nameAndValue("topic", topic);
        if (!XMStringUtils.isBlank(packageName)) {
            body.nameAndValue("restricted_package_name", packageName);
        }
        if (category != null) {
            body.nameAndValue("category", category);
        }
        Result result;
        try {
            final String response = this.post(Constants.XmPushRequestPath.V2_UNSUBSCRIBE_TOPIC_BY_ALIAS, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Subscription.LOGGER.isLoggable(Level.FINE)) {
                    Subscription.LOGGER.fine("Attempt #" + executionCount + " to unsubscribe topic " + topic + " for aliases " + aliases);
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
    
    protected Result topicSubscribeBase(final List<String> regIds, final String topic, final String category, final int retries, final boolean isSubscribe) throws IOException, ParseException {
        final String regIdsStr = String.join(",", regIds);
        final NameValuePairs body = new NameValuePairs().nameAndValue("registration_id", regIdsStr).nameAndValue("topic", topic);
        if (category != null) {
            body.nameAndValue("category", category);
        }
        Result result;
        try {
            final String type = isSubscribe ? "subscribe" : "unsubscribe";
            final Constants.XmPushRequestPath requestPath = isSubscribe ? Constants.XmPushRequestPath.V2_SUBSCRIBE_TOPIC : Constants.XmPushRequestPath.V2_UNSUBSCRIBE_TOPIC;
            final String str;
            final String str2;
            final String response = this.post(requestPath, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Subscription.LOGGER.isLoggable(Level.FINE)) {
                    Subscription.LOGGER.fine("Attempt #" + executionCount + " to send " + str + " topic " + topic + " to regIds " + str2);
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
    
    protected Result topicSubscribeBase(final List<String> regIds, final String topic, final String packageName, final String category, final int retries, final boolean isSubscribe) throws IOException, ParseException {
        final String regIdsStr = String.join(",", regIds);
        final NameValuePairs body = new NameValuePairs().nameAndValue("registration_id", regIdsStr).nameAndValue("topic", topic);
        if (!XMStringUtils.isBlank(packageName)) {
            body.nameAndValue("restricted_package_name", packageName);
        }
        if (category != null) {
            body.nameAndValue("category", category);
        }
        Result result;
        try {
            final String type = isSubscribe ? "subscribe" : "unsubscribe";
            final Constants.RequestPath requestPath = isSubscribe ? Constants.XmPushRequestPath.V2_SUBSCRIBE_TOPIC : Constants.XmPushRequestPath.V2_UNSUBSCRIBE_TOPIC;
            final String str;
            final String str2;
            final String response = this.post(requestPath, body.toQueryOrFormData(), null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (Subscription.LOGGER.isLoggable(Level.FINE)) {
                    Subscription.LOGGER.fine("Attempt #" + executionCount + " to send " + str + " topic " + topic + " to regIds " + str2);
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
}
