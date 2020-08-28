// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import org.slf4j.LoggerFactory;
import com.huawei.push.util.ValidatorUtils;
import com.huawei.push.model.TopicOperation;
import com.huawei.push.message.TopicMessage;
import com.huawei.push.exception.HuaweiMesssagingException;
import com.huawei.push.reponse.SendResponse;
import com.huawei.push.message.Message;
import com.google.common.base.Suppliers;
import com.google.common.base.Supplier;
import org.slf4j.Logger;

public class HuaweiMessaging
{
    private static final Logger logger;
    static final String INTERNAL_ERROR = "internal error";
    static final String UNKNOWN_ERROR = "unknown error";
    static final String KNOWN_ERROR = "known error";
    private final HuaweiApp app;
    private final Supplier<? extends HuaweiMessageClient> messagingClient;
    private static final String SERVICE_ID;
    
    private HuaweiMessaging(final Builder builder) {
        this.app = builder.app;
        this.messagingClient = (Supplier<? extends HuaweiMessageClient>)Suppliers.memoize(builder.messagingClient);
    }
    
    public static synchronized HuaweiMessaging getInstance(final HuaweiApp app) {
        HuaweiMessagingService service = ImplHuaweiTrampolines.getService(app, HuaweiMessaging.SERVICE_ID, HuaweiMessagingService.class);
        if (service == null) {
            service = ImplHuaweiTrampolines.addService(app, new HuaweiMessagingService(app));
        }
        return service.getInstance();
    }
    
    private static HuaweiMessaging fromApp(final HuaweiApp app) {
        return builder().setApp(app).setMessagingClient((Supplier<? extends HuaweiMessageClient>)(() -> HuaweiMessageClientImpl.fromApp(app))).build();
    }
    
    HuaweiMessageClient getMessagingClient() {
        return (HuaweiMessageClient)this.messagingClient.get();
    }
    
    public SendResponse sendMessage(final Message message) throws HuaweiMesssagingException {
        return this.sendMessage(message, false);
    }
    
    public SendResponse subscribeTopic(final TopicMessage topicMessage) throws HuaweiMesssagingException {
        final HuaweiMessageClient messagingClient = this.getMessagingClient();
        return messagingClient.send(topicMessage, TopicOperation.SUBSCRIBE.getValue(), ImplHuaweiTrampolines.getAccessToken(this.app));
    }
    
    public SendResponse unsubscribeTopic(final TopicMessage topicMessage) throws HuaweiMesssagingException {
        final HuaweiMessageClient messagingClient = this.getMessagingClient();
        return messagingClient.send(topicMessage, TopicOperation.UNSUBSCRIBE.getValue(), ImplHuaweiTrampolines.getAccessToken(this.app));
    }
    
    public SendResponse listTopic(final TopicMessage topicMessage) throws HuaweiMesssagingException {
        final HuaweiMessageClient messagingClient = this.getMessagingClient();
        return messagingClient.send(topicMessage, TopicOperation.LIST.getValue(), ImplHuaweiTrampolines.getAccessToken(this.app));
    }
    
    public SendResponse sendMessage(final Message message, final boolean validateOnly) throws HuaweiMesssagingException {
        ValidatorUtils.checkArgument(message != null, "message must not be null");
        final HuaweiMessageClient messagingClient = this.getMessagingClient();
        return messagingClient.send(message, validateOnly, ImplHuaweiTrampolines.getAccessToken(this.app));
    }
    
    static Builder builder() {
        return new Builder();
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)HuaweiMessaging.class);
        SERVICE_ID = HuaweiMessaging.class.getName();
    }
    
    private static class HuaweiMessagingService extends HuaweiService<HuaweiMessaging>
    {
        HuaweiMessagingService(final HuaweiApp app) {
            super(HuaweiMessaging.SERVICE_ID, fromApp(app));
        }
        
        @Override
        public void destroy() {
        }
    }
    
    static class Builder
    {
        private HuaweiApp app;
        private Supplier<? extends HuaweiMessageClient> messagingClient;
        
        private Builder() {
        }
        
        public Builder setApp(final HuaweiApp app) {
            this.app = app;
            return this;
        }
        
        public Builder setMessagingClient(final Supplier<? extends HuaweiMessageClient> messagingClient) {
            this.messagingClient = messagingClient;
            return this;
        }
        
        public HuaweiMessaging build() {
            return new HuaweiMessaging(this, null);
        }
    }
}
