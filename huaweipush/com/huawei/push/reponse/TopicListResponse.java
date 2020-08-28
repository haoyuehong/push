// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.reponse;

import com.alibaba.fastjson.JSONArray;

public class TopicListResponse extends SendResponse
{
    private final JSONArray topics;
    
    public JSONArray getTopics() {
        return this.topics;
    }
    
    private TopicListResponse(final String code, final String msg, final String requestId, final JSONArray topics) {
        super(code, msg, requestId);
        this.topics = topics;
    }
    
    public static TopicListResponse fromCode(final String code, final String msg, final String requestId, final JSONArray topics) {
        return new TopicListResponse(code, msg, requestId, topics);
    }
}
