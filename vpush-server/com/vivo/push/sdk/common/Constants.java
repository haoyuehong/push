// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.common;

import org.apache.http.Consts;
import java.nio.charset.Charset;

public class Constants
{
    public static final String HOST_PRODUCTION = "api-push.vivo.com.cn";
    public static final String SINGLE_SEND = "/message/send";
    public static final String SAVE_LIST_PAYLOAD = "/message/saveListPayload";
    public static final String GET_TOKEN = "/message/auth";
    public static final String SEND_TO_LIST = "/message/pushToList";
    public static final String SEND_TO_ALL = "/message/all";
    public static final String SEND_TO_TAG = "/message/tagPush";
    public static final String GET_STATISTICS = "/report/getStatistics";
    public static final String ADD_TAG = "/tag/add";
    public static final String UPDATE_TAG = "/tag/update";
    public static final String ADD_MEMBERS = "/tag/addMembers";
    public static final String REMOVE_MEMBERS = "/tag/removeMembers";
    public static final String ADD_TAG_GROUP = "/tagGroup/add";
    public static final String UPDATE_TAG_GROUP = "/tagGroup/update";
    public static final String ADD_TAG_TO_GROUP = "/tagGroup/addToGroup";
    public static final String ADD_TAG_SEGMENT = "/tagSegment/add";
    public static final String UPDATE_TAG_SEGMENT = "/tagSegment/update";
    static Charset CHARSET;
    static int HTTP_MAX_CONNECTION;
    static int HTTP_MAX_ROUTE;
    static int CONNECTION_REQUEST_TIMEOUT;
    static int CONNECTION_TIMEOUT;
    static int SOCKET_TIMEOUT;
    public static final Integer MAX_TIME_TO_LIVE;
    public static final Integer SINGLE_MIN_TIME_TO_LIVE;
    public static final Integer MIN_TIME_TO_LIVE;
    
    protected Constants() {
        throw new UnsupportedOperationException();
    }
    
    static {
        Constants.CHARSET = Consts.UTF_8;
        Constants.HTTP_MAX_CONNECTION = 10;
        Constants.HTTP_MAX_ROUTE = 5;
        Constants.CONNECTION_REQUEST_TIMEOUT = 1000;
        Constants.CONNECTION_TIMEOUT = 5000;
        Constants.SOCKET_TIMEOUT = 5000;
        MAX_TIME_TO_LIVE = 604800;
        SINGLE_MIN_TIME_TO_LIVE = 60;
        MIN_TIME_TO_LIVE = 900;
    }
}
