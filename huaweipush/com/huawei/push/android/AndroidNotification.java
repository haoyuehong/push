// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.android;

import java.util.Iterator;
import com.huawei.push.model.Visibility;
import java.util.Locale;
import com.huawei.push.model.Importance;
import com.huawei.push.util.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import com.huawei.push.message.Notification;
import java.util.Collection;
import com.huawei.push.util.CollectionUtils;
import java.util.ArrayList;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class AndroidNotification
{
    private static final String COLOR_PATTERN = "^#[0-9a-fA-F]{6}$";
    private static final String URL_PATTERN = "^https.*";
    private static final String VIBRATE_PATTERN = "[0-9]+|[0-9]+[sS]|[0-9]+[.][0-9]{1,9}|[0-9]+[.][0-9]{1,9}[sS]";
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "body")
    private String body;
    @JSONField(name = "icon")
    private String icon;
    @JSONField(name = "color")
    private String color;
    @JSONField(name = "sound")
    private String sound;
    @JSONField(name = "default_sound")
    private boolean defaultSound;
    @JSONField(name = "tag")
    private String tag;
    @JSONField(name = "click_action")
    private ClickAction clickAction;
    @JSONField(name = "body_loc_key")
    private String bodyLocKey;
    @JSONField(name = "body_loc_args")
    private List<String> bodyLocArgs;
    @JSONField(name = "title_loc_key")
    private String titleLocKey;
    @JSONField(name = "title_loc_args")
    private List<String> titleLocArgs;
    @JSONField(name = "multi_lang_key")
    private JSONObject multiLangKey;
    @JSONField(name = "channel_id")
    private String channelId;
    @JSONField(name = "notify_summary")
    private String notifySummary;
    @JSONField(name = "image")
    private String image;
    @JSONField(name = "style")
    private Integer style;
    @JSONField(name = "big_title")
    private String bigTitle;
    @JSONField(name = "big_body")
    private String bigBody;
    @JSONField(name = "auto_clear")
    private Integer autoClear;
    @JSONField(name = "notify_id")
    private Integer notifyId;
    @JSONField(name = "group")
    private String group;
    @JSONField(name = "badge")
    private BadgeNotification badge;
    @JSONField(name = "ticker")
    private String ticker;
    @JSONField(name = "auto_cancel")
    private boolean autoCancel;
    @JSONField(name = "when")
    private String when;
    @JSONField(name = "local_only")
    private Boolean localOnly;
    @JSONField(name = "importance")
    private String importance;
    @JSONField(name = "use_default_vibrate")
    private boolean useDefaultVibrate;
    @JSONField(name = "use_default_light")
    private boolean useDefaultLight;
    @JSONField(name = "vibrate_config")
    private List<String> vibrateConfig;
    @JSONField(name = "visibility")
    private String visibility;
    @JSONField(name = "light_settings")
    private LightSettings lightSettings;
    @JSONField(name = "foreground_show")
    private boolean foregroundShow;
    @JSONField(name = "inbox_content")
    private List<String> inboxContent;
    @JSONField(name = "buttons")
    private List<Button> buttons;
    
    private AndroidNotification(final Builder builder) {
        this.bodyLocArgs = new ArrayList<String>();
        this.titleLocArgs = new ArrayList<String>();
        this.vibrateConfig = new ArrayList<String>();
        this.title = builder.title;
        this.body = builder.body;
        this.icon = builder.icon;
        this.color = builder.color;
        this.sound = builder.sound;
        this.defaultSound = builder.defaultSound;
        this.tag = builder.tag;
        this.clickAction = builder.clickAction;
        this.bodyLocKey = builder.bodyLocKey;
        if (!CollectionUtils.isEmpty(builder.bodyLocArgs)) {
            this.bodyLocArgs.addAll(builder.bodyLocArgs);
        }
        else {
            this.bodyLocArgs = null;
        }
        this.titleLocKey = builder.titleLocKey;
        if (!CollectionUtils.isEmpty(builder.titleLocArgs)) {
            this.titleLocArgs.addAll(builder.titleLocArgs);
        }
        else {
            this.titleLocArgs = null;
        }
        if (builder.multiLangkey != null) {
            this.multiLangKey = builder.multiLangkey;
        }
        else {
            this.multiLangKey = null;
        }
        this.channelId = builder.channelId;
        this.notifySummary = builder.notifySummary;
        this.image = builder.image;
        this.style = builder.style;
        this.bigTitle = builder.bigTitle;
        this.bigBody = builder.bigBody;
        this.autoClear = builder.autoClear;
        this.notifyId = builder.notifyId;
        this.group = builder.group;
        if (null != builder.badge) {
            this.badge = builder.badge;
        }
        else {
            this.badge = null;
        }
        this.ticker = builder.ticker;
        this.autoCancel = builder.autoCancel;
        this.when = builder.when;
        this.importance = builder.importance;
        this.useDefaultVibrate = builder.useDefaultVibrate;
        this.useDefaultLight = builder.useDefaultLight;
        if (!CollectionUtils.isEmpty(builder.vibrateConfig)) {
            this.vibrateConfig = builder.vibrateConfig;
        }
        else {
            this.vibrateConfig = null;
        }
        this.visibility = builder.visibility;
        this.lightSettings = builder.lightSettings;
        this.foregroundShow = builder.foregroundShow;
        if (!CollectionUtils.isEmpty(builder.inboxContent)) {
            this.inboxContent = builder.inboxContent;
        }
        else {
            this.inboxContent = null;
        }
        if (!CollectionUtils.isEmpty(builder.buttons)) {
            this.buttons = builder.buttons;
        }
        else {
            this.buttons = null;
        }
    }
    
    public void check(final Notification notification) {
        if (null != notification) {
            ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)notification.getTitle()) || StringUtils.isNotEmpty((CharSequence)this.title), "title should be set");
            ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)notification.getBody()) || StringUtils.isNotEmpty((CharSequence)this.body), "body should be set");
        }
        if (StringUtils.isNotEmpty((CharSequence)this.color)) {
            ValidatorUtils.checkArgument(this.color.matches("^#[0-9a-fA-F]{6}$"), "Wrong color format, color must be in the form #RRGGBB");
        }
        if (this.clickAction != null) {
            this.clickAction.check();
        }
        if (!CollectionUtils.isEmpty(this.bodyLocArgs)) {
            ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)this.bodyLocKey), "bodyLocKey is required when specifying bodyLocArgs");
        }
        if (!CollectionUtils.isEmpty(this.titleLocArgs)) {
            ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)this.titleLocKey), "titleLocKey is required when specifying titleLocArgs");
        }
        if (StringUtils.isNotEmpty((CharSequence)this.image)) {
            ValidatorUtils.checkArgument(this.image.matches("^https.*"), "notifyIcon must start with https");
        }
        if (this.style != null) {
            final boolean isTrue = this.style == 0 || this.style == 1 || this.style == 2 || this.style == 3;
            ValidatorUtils.checkArgument(isTrue, "style should be one of 0:default, 1: big text, 2: big picture");
            if (this.style == 1) {
                ValidatorUtils.checkArgument(StringUtils.isNotEmpty((CharSequence)this.bigTitle) && StringUtils.isNotEmpty((CharSequence)this.bigBody), "title and body are required when style = 1");
            }
            else if (this.style == 3) {
                ValidatorUtils.checkArgument(!CollectionUtils.isEmpty(this.inboxContent) && this.inboxContent.size() <= 5, "inboxContent is required when style = 3 and at most 5 inbox content is needed");
            }
        }
        if (this.autoClear != null) {
            ValidatorUtils.checkArgument(this.autoClear > 0, "auto clear should positive value");
        }
        if (this.badge != null) {
            this.badge.check();
        }
        if (this.importance != null) {
            ValidatorUtils.checkArgument(StringUtils.equals((CharSequence)this.importance, (CharSequence)Importance.LOW.getValue()) || StringUtils.equals((CharSequence)this.importance, (CharSequence)Importance.NORMAL.getValue()) || StringUtils.equals((CharSequence)this.importance, (CharSequence)Importance.HIGH.getValue()), "importance shouid be [HIGH, NORMAL, LOW]");
        }
        if (!CollectionUtils.isEmpty(this.vibrateConfig)) {
            ValidatorUtils.checkArgument(this.vibrateConfig.size() <= 10, "vibrate_config array size cannot be more than 10");
            for (final String vibrateTiming : this.vibrateConfig) {
                ValidatorUtils.checkArgument(vibrateTiming.matches("[0-9]+|[0-9]+[sS]|[0-9]+[.][0-9]{1,9}|[0-9]+[.][0-9]{1,9}[sS]"), "Wrong vibrate timing format");
                final long vibrateTimingValue = (long)(1000.0 * Double.valueOf(StringUtils.substringBefore(vibrateTiming.toLowerCase(Locale.getDefault()), "s")));
                ValidatorUtils.checkArgument(vibrateTimingValue > 0L && vibrateTimingValue < 60L, "Vibrate timing duration must be greater than 0 and less than 60s");
            }
        }
        if (this.visibility != null) {
            ValidatorUtils.checkArgument(StringUtils.equals((CharSequence)this.visibility, (CharSequence)Visibility.VISIBILITY_UNSPECIFIED.getValue()) || StringUtils.equals((CharSequence)this.visibility, (CharSequence)Visibility.PRIVATE.getValue()) || StringUtils.equals((CharSequence)this.visibility, (CharSequence)Visibility.PUBLIC.getValue()) || StringUtils.equals((CharSequence)this.visibility, (CharSequence)Visibility.SECRET.getValue()), "visibility shouid be [VISIBILITY_UNSPECIFIED, PRIVATE, PUBLIC, SECRET]");
        }
        if (null != this.multiLangKey) {
            for (final String key : this.multiLangKey.keySet()) {
                ValidatorUtils.checkArgument(this.multiLangKey.get((Object)key) instanceof JSONObject, "multiLangKey value should be JSONObject");
                final JSONObject contentsObj = this.multiLangKey.getJSONObject(key);
                if (contentsObj != null) {
                    ValidatorUtils.checkArgument(contentsObj.keySet().size() <= 3, "Only three lang property can carry");
                }
            }
        }
        if (this.lightSettings != null) {
            this.lightSettings.check();
        }
        if (!CollectionUtils.isEmpty(this.buttons)) {
            ValidatorUtils.checkArgument(this.buttons.size() <= 3, "Only three buttons can carry");
            for (final Button button : this.buttons) {
                button.check();
            }
        }
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getBody() {
        return this.body;
    }
    
    public String getIcon() {
        return this.icon;
    }
    
    public String getColor() {
        return this.color;
    }
    
    public String getSound() {
        return this.sound;
    }
    
    public Boolean isDefaultSound() {
        return this.defaultSound;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public ClickAction getClickAction() {
        return this.clickAction;
    }
    
    public String getBodyLocKey() {
        return this.bodyLocKey;
    }
    
    public List<String> getBodyLocArgs() {
        return this.bodyLocArgs;
    }
    
    public String getTitleLocKey() {
        return this.titleLocKey;
    }
    
    public List<String> getTitleLocArgs() {
        return this.titleLocArgs;
    }
    
    public JSONObject getMultiLangKey() {
        return this.multiLangKey;
    }
    
    public String getChannelId() {
        return this.channelId;
    }
    
    public String getNotifySummary() {
        return this.notifySummary;
    }
    
    public String getImage() {
        return this.image;
    }
    
    public Integer getStyle() {
        return this.style;
    }
    
    public String getBigTitle() {
        return this.bigTitle;
    }
    
    public String getBigBody() {
        return this.bigBody;
    }
    
    public Integer getAutoClear() {
        return this.autoClear;
    }
    
    public Integer getNotifyId() {
        return this.notifyId;
    }
    
    public String getGroup() {
        return this.group;
    }
    
    public BadgeNotification getBadge() {
        return this.badge;
    }
    
    public String getTicker() {
        return this.ticker;
    }
    
    public String getWhen() {
        return this.when;
    }
    
    public String getImportance() {
        return this.importance;
    }
    
    public List<String> getVibrateConfig() {
        return this.vibrateConfig;
    }
    
    public String getVisibility() {
        return this.visibility;
    }
    
    public LightSettings getLightSettings() {
        return this.lightSettings;
    }
    
    public boolean isAutoCancel() {
        return this.autoCancel;
    }
    
    public Boolean getLocalOnly() {
        return this.localOnly;
    }
    
    public boolean isUseDefaultVibrate() {
        return this.useDefaultVibrate;
    }
    
    public boolean isUseDefaultLight() {
        return this.useDefaultLight;
    }
    
    public boolean isForegroundShow() {
        return this.foregroundShow;
    }
    
    public List<String> getInboxContent() {
        return this.inboxContent;
    }
    
    public List<Button> getButtons() {
        return this.buttons;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private String title;
        private String body;
        private String icon;
        private String color;
        private String sound;
        private boolean defaultSound;
        private String tag;
        private ClickAction clickAction;
        private String bodyLocKey;
        private List<String> bodyLocArgs;
        private String titleLocKey;
        private List<String> titleLocArgs;
        private JSONObject multiLangkey;
        private String channelId;
        private String notifySummary;
        private String image;
        private Integer style;
        private String bigTitle;
        private String bigBody;
        private Integer autoClear;
        private Integer notifyId;
        private String group;
        private BadgeNotification badge;
        private String ticker;
        private boolean autoCancel;
        private String when;
        private String importance;
        private boolean useDefaultVibrate;
        private boolean useDefaultLight;
        private List<String> vibrateConfig;
        private String visibility;
        private LightSettings lightSettings;
        private boolean foregroundShow;
        private List<String> inboxContent;
        private List<Button> buttons;
        
        private Builder() {
            this.bodyLocArgs = new ArrayList<String>();
            this.titleLocArgs = new ArrayList<String>();
            this.autoCancel = true;
            this.vibrateConfig = new ArrayList<String>();
            this.inboxContent = new ArrayList<String>();
            this.buttons = new ArrayList<Button>();
        }
        
        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }
        
        public Builder setBody(final String body) {
            this.body = body;
            return this;
        }
        
        public Builder setIcon(final String icon) {
            this.icon = icon;
            return this;
        }
        
        public Builder setColor(final String color) {
            this.color = color;
            return this;
        }
        
        public Builder setSound(final String sound) {
            this.sound = sound;
            return this;
        }
        
        public Builder setDefaultSound(final boolean defaultSound) {
            this.defaultSound = defaultSound;
            return this;
        }
        
        public Builder setTag(final String tag) {
            this.tag = tag;
            return this;
        }
        
        public Builder setClickAction(final ClickAction clickAction) {
            this.clickAction = clickAction;
            return this;
        }
        
        public Builder setBodyLocKey(final String bodyLocKey) {
            this.bodyLocKey = bodyLocKey;
            return this;
        }
        
        public Builder addBodyLocArgs(final String arg) {
            this.bodyLocArgs.add(arg);
            return this;
        }
        
        public Builder addAllBodyLocArgs(final List<String> args) {
            this.bodyLocArgs.addAll(args);
            return this;
        }
        
        public Builder setTitleLocKey(final String titleLocKey) {
            this.titleLocKey = titleLocKey;
            return this;
        }
        
        public Builder addTitleLocArgs(final String arg) {
            this.titleLocArgs.add(arg);
            return this;
        }
        
        public Builder addAllTitleLocArgs(final List<String> args) {
            this.titleLocArgs.addAll(args);
            return this;
        }
        
        public Builder setMultiLangkey(final JSONObject multiLangkey) {
            this.multiLangkey = multiLangkey;
            return this;
        }
        
        public Builder setChannelId(final String channelId) {
            this.channelId = channelId;
            return this;
        }
        
        public Builder setNotifySummary(final String notifySummary) {
            this.notifySummary = notifySummary;
            return this;
        }
        
        public Builder setImage(final String image) {
            this.image = image;
            return this;
        }
        
        public Builder setStyle(final Integer style) {
            this.style = style;
            return this;
        }
        
        public Builder setBigTitle(final String bigTitle) {
            this.bigTitle = bigTitle;
            return this;
        }
        
        public Builder setBigBody(final String bigBody) {
            this.bigBody = bigBody;
            return this;
        }
        
        public Builder setAutoClear(final Integer autoClear) {
            this.autoClear = autoClear;
            return this;
        }
        
        public Builder setNotifyId(final Integer notifyId) {
            this.notifyId = notifyId;
            return this;
        }
        
        public Builder setGroup(final String group) {
            this.group = group;
            return this;
        }
        
        public Builder setBadge(final BadgeNotification badge) {
            this.badge = badge;
            return this;
        }
        
        public Builder setTicker(final String ticker) {
            this.ticker = ticker;
            return this;
        }
        
        public Builder setAutoCancel(final boolean autoCancel) {
            this.autoCancel = autoCancel;
            return this;
        }
        
        public Builder setWhen(final String when) {
            this.when = when;
            return this;
        }
        
        public Builder setImportance(final String importance) {
            this.importance = importance;
            return this;
        }
        
        public Builder setUseDefaultVibrate(final boolean useDefaultVibrate) {
            this.useDefaultVibrate = useDefaultVibrate;
            return this;
        }
        
        public Builder setUseDefaultLight(final boolean useDefaultLight) {
            this.useDefaultLight = useDefaultLight;
            return this;
        }
        
        public Builder addVibrateConfig(final String vibrateTiming) {
            this.vibrateConfig.add(vibrateTiming);
            return this;
        }
        
        public Builder addAllVibrateConfig(final List<String> vibrateTimings) {
            this.vibrateConfig.addAll(vibrateTimings);
            return this;
        }
        
        public Builder setVisibility(final String visibility) {
            this.visibility = visibility;
            return this;
        }
        
        public Builder setLightSettings(final LightSettings lightSettings) {
            this.lightSettings = lightSettings;
            return this;
        }
        
        public Builder setForegroundShow(final boolean foregroundShow) {
            this.foregroundShow = foregroundShow;
            return this;
        }
        
        public Builder addInboxContent(final String inboxContent) {
            this.inboxContent.add(inboxContent);
            return this;
        }
        
        public Builder addAllInboxContent(final List<String> inboxContents) {
            this.inboxContent.addAll(inboxContents);
            return this;
        }
        
        public Builder addButton(final Button button) {
            this.buttons.add(button);
            return this;
        }
        
        public Builder addAllButtons(final List<Button> buttons) {
            this.buttons.addAll(buttons);
            return this;
        }
        
        public AndroidNotification build() {
            return new AndroidNotification(this, null);
        }
    }
}
