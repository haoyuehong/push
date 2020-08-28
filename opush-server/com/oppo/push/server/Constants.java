// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

class Constants
{
    static final String PARAM_SDK_VERSION = "sdk_version";
    static final String SDK_VERSION = "JAVA_SDK_V1.0.0";
    static String HTTP_PROTOCOL;
    static int HTTP_MAX_CONNECTION;
    static int HTTP_MAX_ROUTE;
    static int HTTP_RETRY_TIME;
    static String HOST_PRODUCTION_CHINA;
    static String MEDIA_HOST_PRODUCTION_CHINA;
    static final String TARGET_VALUE_SPLIT_CHAR = ";";
    static final String PARAM_TOKEN = "auth_token";
    static final String PARAM_APP_KEY = "app_key";
    static final String PARAM_SIGN = "sign";
    static final String PARAM_TIMESTAMP = "timestamp";
    static final String PARAM_MESSAGE_ID = "message_id";
    static final String PARAM_TARGET_TYPE = "target_type";
    static final String PARAM_TARGET_VALUE = "target_value";
    static final String PARAM_NOTIFICATION = "notification";
    static final String PARAM_MESSAGE = "message";
    static final String PARAM_MESSAGES = "messages";
    static final String PARAM_CHANNEL_ID = "channel_id";
    static final String PARAM_CHANNEL_GROUP_ID = "channel_group_id";
    static final String PARAM_TITLE = "title";
    static final String PARAM_APP_MESSAGE_ID = "app_message_id";
    static final String PARAM_SUB_TITLE = "sub_title";
    static final String PARAM_CONTENT = "content";
    static final String PARAM_CLICK_ACTION_TYPE = "click_action_type";
    static final String PARAM_CLICK_ACTION_ACTIVITY = "click_action_activity";
    static final String PARAM_CLICK_ACTION_URL = "click_action_url";
    static final String PARAM_ACTION_PARAMETERS = "action_parameters";
    static final String PARAM_SHOW_TIME_TYPE = "show_time_type";
    static final String PARAM_SHOW_START_TIME = "show_start_time";
    static final String PARAM_SHOW_END_TIME = "show_end_time";
    static final String PARAM_OFF_LINE = "off_line";
    static final String PARAM_OFF_LINE_TTL = "off_line_ttl";
    static final String PARAM_PUSH_TIME_TYPE = "push_time_type";
    static final String PARAM_PUSH_START_TIME = "push_start_time";
    static final String PARAM_TIME_ZONE = "time_zone";
    static final String PARAM_FIX_SPEED = "fix_speed";
    static final String PARAM_FIX_SPEED_RATE = "fix_speed_rate";
    static final String PARAM_NETWORK_TYPE = "network_type";
    static final String PARAM_CALL_BACK_URL = "call_back_url";
    static final String PARAM_CALL_BACK_PARAMETER = "call_back_parameter";
    static final String PARAM_STYLE = "style";
    static final String PARAM_BIG_PICTURE_ID = "big_picture_id";
    static final String PARAM_SMALL_PICTURE_ID = "small_picture_id";
    static final String PARAM_PICTURE_TTL = "picture_ttl";
    static final String RETURN_CODE = "code";
    static final String RETURN_MESSAGE = "message";
    static final String RETURN_DATA = "data";
    static final String RETURN_TOKEN = "auth_token";
    static final String RETURN_CREATE_TIME = "create_time";
    static final String RETURN_MESSAGEID = "messageId";
    static final String RETURN_MESSAGE_ID = "message_id";
    static final String RETURN_TASK_ID = "task_id";
    static final String RETURN_BIG_PICTURE_ID = "big_picture_id";
    static final String RETURN_SMALL_PICTURE_ID = "small_picture_id";
    static final String RETURN_REGISTRATION_ID = "registrationId";
    static final String RETURN_ERROR_CODE = "errorCode";
    static final String RETURN_ERROR_MESSAGE = "errorMessage";
    static final int TITLE_LENGTH_LIMIT = 50;
    static final int CONTENT_LENGTH_LIMIT = 200;
    static final int OFFLINE_TTL_LIMIT = 864000;
    static final int SUBTITLE_LENGTH_LIMIT = 10;
    static final int CLICK_ACTION_ACTIVITY_LENGTH_LIMIT = 500;
    static final int CLICK_ACTION_URL_LENGTH_LIMIT = 500;
    static final long MAX_BIG_PICTURE_SIZE = 1048576L;
    static final long MAX_SMALL_PICTURE_SIZE = 51200L;
    static final int CALL_BACK_PARAMETER_LENGTH_LIMIT = 50;
    
    static {
        Constants.HTTP_PROTOCOL = "https";
        Constants.HTTP_MAX_CONNECTION = 10;
        Constants.HTTP_MAX_ROUTE = 5;
        Constants.HTTP_RETRY_TIME = 3;
        Constants.HOST_PRODUCTION_CHINA = "api.push.oppomobile.com";
        Constants.MEDIA_HOST_PRODUCTION_CHINA = "api-media.push.heytapmobi.com";
    }
}
