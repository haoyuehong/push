// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.server;

import com.alibaba.fastjson.JSON;
import com.vivo.push.sdk.notofication.Result;
import com.vivo.push.sdk.tag.TagSegMessage;
import com.vivo.push.sdk.common.HttpUtil;

public class TagSegment extends HttpUtil
{
    public TagSegment(final String secret) throws Exception {
        super(Validation.nonNull(secret));
        init();
    }
    
    public void initPool(final int connection, final int route) throws Exception {
        HttpUtil.init(connection, route);
    }
    
    public Result addTagSegment(final TagSegMessage tagSegMessage) throws Exception {
        Result result = null;
        Validation.validateTagName(tagSegMessage.getName());
        Validation.validateTagExpression(tagSegMessage.getExpression());
        result = HttpUtil.sendMessage(this.doPost(JSON.toJSONString((Object)tagSegMessage), "/tagSegment/add"));
        return result;
    }
    
    public Result updateTagSegment(final TagSegMessage updateMessage) throws Exception {
        Result result = null;
        Validation.validateTagName(updateMessage.getOldName());
        Validation.validateTagName(updateMessage.getNewName());
        if (updateMessage.getExpression() != null) {
            Validation.validateTagExpression(updateMessage.getExpression());
        }
        result = HttpUtil.sendMessage(this.doPost(JSON.toJSONString((Object)updateMessage), "/tagSegment/update"));
        return result;
    }
}
