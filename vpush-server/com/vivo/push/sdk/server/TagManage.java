// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.server;

import com.alibaba.fastjson.JSON;
import com.vivo.push.sdk.notofication.Result;
import com.vivo.push.sdk.tag.TagMessage;
import com.vivo.push.sdk.common.HttpUtil;

public class TagManage extends HttpUtil
{
    public TagManage(final String secret) throws Exception {
        super(Validation.nonNull(secret));
        init();
    }
    
    public void initPool(final int connection, final int route) throws Exception {
        HttpUtil.init(connection, route);
    }
    
    public Result addTag(final TagMessage tagMessage) throws Exception {
        Result result = null;
        Validation.validateTagName(tagMessage.getName());
        result = HttpUtil.sendMessage(this.doPost(JSON.toJSONString((Object)tagMessage), "/tag/add"));
        return result;
    }
    
    public Result updateTag(final TagMessage updateMessage) throws Exception {
        Result result = null;
        Validation.validateTagName(updateMessage.getOldName());
        Validation.validateTagName(updateMessage.getNewName());
        result = HttpUtil.sendMessage(this.doPost(JSON.toJSONString((Object)updateMessage), "/tag/update"));
        return result;
    }
    
    public Result addMembers(final TagMessage membersMessage) throws Exception {
        Result result = null;
        Validation.validateTagMember(membersMessage);
        result = HttpUtil.sendMessage(this.doPost(JSON.toJSONString((Object)membersMessage), "/tag/addMembers"));
        return result;
    }
    
    public Result removeMembers(final TagMessage membersMessage) throws Exception {
        Result result = null;
        Validation.validateTagMember(membersMessage);
        result = HttpUtil.sendMessage(this.doPost(JSON.toJSONString((Object)membersMessage), "/tag/removeMembers"));
        return result;
    }
}
