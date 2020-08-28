// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.io.IOException;
import java.util.logging.Level;
import org.json.simple.JSONObject;
import java.util.logging.Logger;

public class ChannelHelper extends PushSender<ChannelHelper>
{
    protected static final Logger logger;
    protected JSONObject lastResult;
    
    public ChannelHelper(final String security) {
        super(security);
    }
    
    public ChannelHelper(final String security, final Region region) {
        super(security, region);
    }
    
    public Result addNewChannel(final ChannelInfo channelInfo, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("channel_id", channelInfo.getChannelId()).nameAndValue("channel_name", channelInfo.getChannelName()).nameAndValue("channel_description", channelInfo.getChannelDesc()).nameAndValue("sound_url", channelInfo.getSoundUrl()).nameAndValue("notify_type", channelInfo.getNotifyType());
        Result result;
        try {
            ChannelHelper.logger.fine("get from: " + Constants.XmPushRequestPath.V1_ADD_NEW_CHANNEL);
            final String response = this.get(Constants.XmPushRequestPath.V1_ADD_NEW_CHANNEL, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (ChannelHelper.logger.isLoggable(Level.FINE)) {
                    ChannelHelper.logger.fine("Attempt #" + executionCount + " to add channel " + channelInfo.toString());
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
    
    public Result discardChannel(final String channelId, final int retries) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("channel_id", channelId);
        Result result;
        try {
            ChannelHelper.logger.fine("get from: " + Constants.XmPushRequestPath.V1_DISCARD_CHANNEL);
            final String response = this.get(Constants.XmPushRequestPath.V1_DISCARD_CHANNEL, null, queryParams, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (ChannelHelper.logger.isLoggable(Level.FINE)) {
                    ChannelHelper.logger.fine("Attempt #" + executionCount + " to discard channel " + channelId);
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
    
    public Result getChannelList(final int retries) throws IOException {
        Result result;
        try {
            ChannelHelper.logger.fine("get from: " + Constants.XmPushRequestPath.V1_GET_CHANNEL_LIST);
            final String response = this.get(Constants.XmPushRequestPath.V1_GET_CHANNEL_LIST, null, null, new DefaultPushRetryHandler(retries, 1000, executionCount -> {
                if (ChannelHelper.logger.isLoggable(Level.FINE)) {
                    ChannelHelper.logger.fine("Attempt #" + executionCount + " to get channel list");
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
    
    public Result addNewChannelNoRetry(final ChannelInfo channelInfo) throws IOException {
        return this.addNewChannel(channelInfo, 1);
    }
    
    public Result discardChannelNoRetry(final String channelId) throws IOException {
        return this.discardChannel(channelId, 1);
    }
    
    public Result getChannelListNoRetry() throws IOException {
        return this.getChannelList(1);
    }
    
    static {
        logger = Logger.getLogger(ChannelHelper.class.getName());
    }
}
