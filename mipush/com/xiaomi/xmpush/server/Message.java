// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.Serializable;

public class Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final String collapseKey;
    private final String payload;
    private final String title;
    private final String description;
    private final Integer notifyType;
    private final Long timeToLive;
    private final Integer passThrough;
    private final Integer notifyId;
    private final String[] restrictedPackageNames;
    private final Map<String, String> extra;
    private Map<String, String> apsProperFields;
    private final Long timeToSend;
    public static final int PASS_THROUGH_PASS = 1;
    public static final int PASS_THROUGH_NOTIFICATION = 0;
    public static final int NOTIFY_TYPE_ALL = -1;
    public static final int NOTIFY_TYPE_SOUND = 1;
    public static final int NOTIFY_TYPE_VIBRATE = 2;
    public static final int NOTIFY_TYPE_LIGHTS = 4;
    
    protected Message(final IOSBuilder builder) {
        this.collapseKey = null;
        this.payload = null;
        this.title = null;
        this.description = builder.description;
        this.notifyType = null;
        this.timeToLive = builder.timeToLive;
        this.restrictedPackageNames = null;
        this.passThrough = null;
        this.notifyId = null;
        this.extra = builder.extra;
        this.apsProperFields = builder.apsProperFields;
        this.timeToSend = builder.timeToSend;
    }
    
    protected Message(final Builder builder) {
        this.collapseKey = builder.collapseKey;
        this.payload = builder.payload;
        this.title = builder.title;
        this.description = builder.description;
        this.notifyType = builder.notifyType;
        this.timeToLive = builder.timeToLive;
        this.restrictedPackageNames = builder.restrictedPackageNames;
        this.passThrough = builder.passThrough;
        this.notifyId = builder.notifyId;
        this.extra = builder.extra;
        this.apsProperFields = null;
        this.timeToSend = builder.timeToSend;
    }
    
    protected String getCollapseKey() {
        return this.collapseKey;
    }
    
    public String getPayload() {
        return this.payload;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Integer getNotifyType() {
        return this.notifyType;
    }
    
    public Integer getNotifyId() {
        return this.notifyId;
    }
    
    public Long getTimeToLive() {
        return this.timeToLive;
    }
    
    public String getRestrictedPackageName() {
        if (this.restrictedPackageNames != null && this.restrictedPackageNames.length != 0) {
            return this.restrictedPackageNames[0];
        }
        return null;
    }
    
    public String[] getRestrictedPackageNames() {
        return this.restrictedPackageNames;
    }
    
    public Integer getPassThrough() {
        return this.passThrough;
    }
    
    public Map<String, String> getExtra() {
        return this.extra;
    }
    
    public Map<String, String> getApsProperFields() {
        return this.apsProperFields;
    }
    
    public Long getTimeToSend() {
        return this.timeToSend;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Message(");
        if (!XMStringUtils.isEmpty(this.collapseKey)) {
            builder.append("collapseKey=").append(this.collapseKey).append(", ");
        }
        if (!XMStringUtils.isEmpty(this.payload)) {
            builder.append("payload=").append(this.payload).append(", ");
        }
        if (!XMStringUtils.isEmpty(this.title)) {
            builder.append("title=").append(this.title).append(", ");
        }
        if (!XMStringUtils.isEmpty(this.description)) {
            builder.append("description=").append(this.description).append(", ");
        }
        if (this.timeToLive != null) {
            builder.append("timeToLive=").append(this.timeToLive).append(", ");
        }
        if (this.restrictedPackageNames != null && this.restrictedPackageNames.length != 0) {
            builder.append("restrictedPackageNames=").append("[").append(this.arrayToString(this.restrictedPackageNames)).append("]").append(", ");
        }
        if (this.notifyType != null) {
            builder.append("notifyType=").append(this.notifyType).append(", ");
        }
        if (this.notifyId != null) {
            builder.append("notifyId=").append(this.notifyId).append(", ");
        }
        if (!this.extra.isEmpty()) {
            for (final Map.Entry<String, String> entry : this.extra.entrySet()) {
                builder.append("extra.").append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        if (this.apsProperFields != null && !this.apsProperFields.isEmpty()) {
            for (final Map.Entry<String, String> entry : this.apsProperFields.entrySet()) {
                builder.append("aps_proper_fields.").append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        if (builder.charAt(builder.length() - 1) == ' ') {
            builder.delete(builder.length() - 2, builder.length());
        }
        builder.append(")");
        return builder.toString();
    }
    
    private String arrayToString(final String[] array) {
        final StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < array.length - 1; ++i) {
            sb.append(array[i]).append(",");
        }
        sb.append(array[i]);
        return sb.toString();
    }
    
    public static class ExtraBuilder
    {
        protected Map<String, String> extra;
        
        public ExtraBuilder() {
            this.extra = new HashMap<String, String>();
        }
        
        public ExtraBuilder extras(final Map<String, String> extras) {
            this.extra.putAll(extras);
            return this;
        }
        
        public Map<String, String> build() {
            return this.extra;
        }
    }
    
    private enum ButtonIndex
    {
        left, 
        mid, 
        right;
    }
    
    public static class ButtonBuilder extends ExtraBuilder implements Constants.NotificationStyleExtra
    {
        private String name;
        private String effect;
        private String intentUri;
        private String className;
        private String webUrl;
        private ButtonIndex index;
        private NotificationStyleBuilder superBuiler;
        
        public ButtonBuilder(final ButtonIndex index, final NotificationStyleBuilder superBuiler) {
            this.index = index;
            this.superBuiler = superBuiler;
        }
        
        public ButtonBuilder name(final String name) {
            this.name = name;
            return this;
        }
        
        public ButtonBuilder launcher() {
            this.effect = "1";
            return this;
        }
        
        public ButtonBuilder activity(final String intentUri) {
            this.effect = "2";
            this.intentUri = intentUri;
            return this;
        }
        
        public ButtonBuilder activity() {
            this.effect = "2";
            return this;
        }
        
        public ButtonBuilder intentUri(final String intentUri) {
            this.intentUri = intentUri;
            return this;
        }
        
        public ButtonBuilder className(final String className) {
            this.className = className;
            return this;
        }
        
        public ButtonBuilder web(final String webUrl) {
            this.effect = "3";
            this.webUrl = webUrl;
            return this;
        }
        
        @Override
        public Map<String, String> build() {
            switch (this.index) {
                case left: {
                    this.extra.put("notification_style_button_left_name", this.name);
                    this.extra.put("notification_style_button_left_notify_effect", this.effect);
                    break;
                }
                case mid: {
                    this.extra.put("notification_style_button_mid_name", this.name);
                    this.extra.put("notification_style_button_mid_notify_effect", this.effect);
                    break;
                }
                case right: {
                    this.extra.put("notification_style_button_right_name", this.name);
                    this.extra.put("notification_style_button_right_notify_effect", this.effect);
                    break;
                }
            }
            if (!"1".equals(this.effect)) {
                if ("2".equals(this.effect)) {
                    switch (this.index) {
                        case left: {
                            if (!XMStringUtils.isBlank(this.intentUri)) {
                                this.extra.put("notification_style_button_left_intent_uri", this.intentUri);
                            }
                            if (!XMStringUtils.isBlank(this.className)) {
                                this.extra.put("notification_style_button_left_intent_class", this.className);
                                break;
                            }
                            break;
                        }
                        case mid: {
                            if (!XMStringUtils.isBlank(this.intentUri)) {
                                this.extra.put("notification_style_button_mid_intent_uri", this.intentUri);
                            }
                            if (!XMStringUtils.isBlank(this.className)) {
                                this.extra.put("notification_style_button_mid_intent_class", this.className);
                                break;
                            }
                            break;
                        }
                        case right: {
                            if (!XMStringUtils.isBlank(this.intentUri)) {
                                this.extra.put("notification_style_button_right_intent_uri", this.intentUri);
                            }
                            if (!XMStringUtils.isBlank(this.className)) {
                                this.extra.put("notification_style_button_right_intent_class", this.className);
                                break;
                            }
                            break;
                        }
                    }
                }
                else if ("3".equals(this.effect)) {
                    switch (this.index) {
                        case left: {
                            this.extra.put("notification_style_button_left_web_uri", this.webUrl);
                            break;
                        }
                        case mid: {
                            this.extra.put("notification_style_button_mid_web_uri", this.webUrl);
                            break;
                        }
                        case right: {
                            this.extra.put("notification_style_button_right_web_uri", this.webUrl);
                            break;
                        }
                    }
                }
            }
            return this.extra;
        }
        
        public ExtraBuilder start() {
            return this;
        }
        
        public NotificationStyleBuilder end() {
            this.build();
            this.superBuiler.extras(this.extra);
            return this.superBuiler;
        }
    }
    
    public static class NotificationStyleBuilder extends ExtraBuilder implements Constants.NotificationStyleExtra
    {
        private String type;
        private String largeIconUri;
        private String bigPicUri;
        private ButtonBuilder left;
        private ButtonBuilder mid;
        private ButtonBuilder right;
        
        public NotificationStyleBuilder bigTextStyle() {
            this.type = "1";
            return this;
        }
        
        public NotificationStyleBuilder bigPictureStyle() {
            this.type = "2";
            return this;
        }
        
        public NotificationStyleBuilder largeIconUri(final String uri) {
            this.largeIconUri = uri;
            return this;
        }
        
        public NotificationStyleBuilder bigPicUri(final String uri) {
            this.bigPicUri = uri;
            return this;
        }
        
        public ButtonBuilder leftBtn() {
            return this.left = new ButtonBuilder(ButtonIndex.left, this);
        }
        
        public ButtonBuilder midBtn() {
            return this.mid = new ButtonBuilder(ButtonIndex.mid, this);
        }
        
        public ButtonBuilder rightBtn() {
            return this.right = new ButtonBuilder(ButtonIndex.right, this);
        }
        
        @Override
        public Map<String, String> build() {
            this.extra.put("notification_style_type", this.type);
            this.extra.put("notification_large_icon_uri", this.largeIconUri);
            this.extra.put("notification_bigPic_uri", this.bigPicUri);
            if (this.left != null) {
                this.extra.putAll(this.left.build());
            }
            if (this.mid != null) {
                this.extra.putAll(this.mid.build());
            }
            if (this.right != null) {
                this.extra.putAll(this.right.build());
            }
            return this.extra;
        }
    }
    
    public static final class Builder
    {
        private String collapseKey;
        private String payload;
        private String title;
        private String description;
        private Integer notifyType;
        private Long timeToLive;
        private String[] restrictedPackageNames;
        private Integer passThrough;
        private Integer notifyId;
        private Map<String, String> extra;
        private Long timeToSend;
        
        public Builder() {
            this.passThrough = 0;
            this.notifyId = 0;
            this.extra = new LinkedHashMap<String, String>();
        }
        
        protected Builder collapseKey(final String value) {
            this.collapseKey = value;
            return this;
        }
        
        public Builder payload(final String value) {
            this.payload = value;
            return this;
        }
        
        public Builder title(final String value) {
            this.title = value;
            return this;
        }
        
        public Builder description(final String value) {
            this.description = value;
            return this;
        }
        
        public Builder notifyType(final Integer value) {
            this.notifyType = value;
            return this;
        }
        
        public Builder notifyId(final Integer value) {
            this.notifyId = value;
            return this;
        }
        
        public Builder timeToLive(final long value) {
            this.timeToLive = value;
            return this;
        }
        
        public Builder restrictedPackageName(final String value) {
            (this.restrictedPackageNames = new String[1])[0] = value;
            return this;
        }
        
        public Builder restrictedPackageNames(final String[] value) {
            this.restrictedPackageNames = value;
            return this;
        }
        
        public Builder passThrough(final int passThrough) {
            this.passThrough = passThrough;
            return this;
        }
        
        public Builder extra(final String key, final String value) {
            this.extra.put(key, value);
            return this;
        }
        
        public Builder extra(final Map<String, String> extra) {
            this.extra.putAll(extra);
            return this;
        }
        
        public Builder timeToSend(final long timeToSend) {
            this.timeToSend = timeToSend;
            return this;
        }
        
        public Builder instantNotify(final boolean isInstantNotify) {
            if (isInstantNotify) {
                this.extra.put("instant_notify", "1");
            }
            else {
                this.extra.remove("instant_notify");
            }
            return this;
        }
        
        public Builder enableFlowControl(final boolean needFlowControl) {
            if (needFlowControl) {
                this.extra.put("flow_control", "1");
            }
            else {
                this.extra.remove("flow_control");
            }
            return this;
        }
        
        public Builder hybridPath(final String hybridPath) {
            this.extra("hybrid_pn", hybridPath);
            return this;
        }
        
        public Message build() {
            return new Message(this);
        }
        
        public void setExtra(final Map<String, String> extra) {
            this.extra = extra;
        }
    }
    
    public static final class IOSBuilder
    {
        private String description;
        private Long timeToLive;
        private Map<String, String> extra;
        private Map<String, String> apsProperFields;
        private Long timeToSend;
        
        public IOSBuilder() {
            this.extra = new LinkedHashMap<String, String>();
            this.apsProperFields = new LinkedHashMap<String, String>();
        }
        
        public IOSBuilder description(final String value) {
            this.description = value;
            return this;
        }
        
        public IOSBuilder timeToLive(final long value) {
            this.timeToLive = value;
            return this;
        }
        
        public IOSBuilder extra(final String key, final String value) {
            this.extra.put(key, value);
            return this;
        }
        
        public IOSBuilder timeToSend(final long timeToSend) {
            this.timeToSend = timeToSend;
            return this;
        }
        
        public IOSBuilder title(final String value) {
            this.apsProperFields.put("title", value);
            return this;
        }
        
        public IOSBuilder subtitle(final String value) {
            this.apsProperFields.put("subtitle", value);
            return this;
        }
        
        public IOSBuilder body(final String value) {
            this.apsProperFields.put("body", value);
            return this;
        }
        
        public IOSBuilder mutableContent(final String value) {
            this.apsProperFields.put("mutable-content", value);
            return this;
        }
        
        public IOSBuilder apsProperties(final String key, final String value) {
            this.apsProperFields.put(key, value);
            return this;
        }
        
        public Message build() {
            return new Message(this);
        }
        
        public IOSBuilder badge(final int badge) {
            this.extra.put("badge", String.valueOf(badge));
            return this;
        }
        
        public IOSBuilder category(final String category) {
            this.extra.put("category", category);
            return this;
        }
        
        public IOSBuilder soundURL(final String url) {
            this.extra.put("sound_url", url);
            return this;
        }
        
        public IOSBuilder apnsOnly() {
            this.extra.put("ios_msg_channel", "1");
            return this;
        }
        
        public IOSBuilder connectionOnly() {
            this.extra.put("ios_msg_channel", "2");
            return this;
        }
        
        public IOSBuilder contentAvailble(final String value) {
            this.extra.put("content-available", value);
            return this;
        }
        
        public IOSBuilder showContent() {
            this.extra.put("show-content", "1");
            return this;
        }
        
        public void setExtra(final Map<String, String> extra) {
            this.extra = extra;
        }
        
        public void setApsProperFields(final Map<String, String> apsProperFields) {
            this.apsProperFields = apsProperFields;
        }
    }
}
