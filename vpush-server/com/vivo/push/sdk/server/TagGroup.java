// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.server;

import com.alibaba.fastjson.JSON;
import com.vivo.push.sdk.notofication.Result;
import com.vivo.push.sdk.tag.TagGroupMessage;
import com.vivo.push.sdk.common.HttpUtil;

public class TagGroup extends HttpUtil
{
    public TagGroup(final String secret) throws Exception {
        super(Validation.nonNull(secret));
        init();
    }
    
    public void initPool(final int connection, final int route) throws Exception {
        HttpUtil.init(connection, route);
    }
    
    public Result addTagGroup(final TagGroupMessage groupMessage) throws Exception {
        Result result = null;
        Validation.validateTagName(groupMessage.getName());
        Validation.validateTagGoupType(groupMessage.getType());
        result = HttpUtil.sendMessage(this.doPost(JSON.toJSONString((Object)groupMessage), "/tagGroup/add"));
        return result;
    }
    
    public Result updateTagGroup(final TagGroupMessage updateMessage) throws Exception {
        Result result = null;
        Validation.validateTagName(updateMessage.getOldName());
        Validation.validateTagName(updateMessage.getNewName());
        result = HttpUtil.sendMessage(this.doPost(JSON.toJSONString((Object)updateMessage), "/tagGroup/update"));
        return result;
    }
    
    public Result addTagToGroup(final TagGroupMessage tagsToGroup) throws Exception {
        Result result = null;
        Validation.validateTagName(tagsToGroup.getName());
        result = HttpUtil.sendMessage(this.doPost(JSON.toJSONString((Object)tagsToGroup), "/tagGroup/addToGroup"));
        return result;
    }
}
