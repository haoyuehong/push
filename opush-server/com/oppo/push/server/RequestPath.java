// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

enum RequestPath
{
    BASE_PATH("/server"), 
    VERSION_PATH("/v1"), 
    AUTH("/auth"), 
    NOTIFICATION_UNICAST("/message/notification/unicast"), 
    NOTIFICATION_UNICAST_BATCH("/message/notification/unicast_batch"), 
    NOTIFICATION_SAVE_MESSAGE("/message/notification/save_message_content"), 
    NOTIFICATION_BROADCAST("/message/notification/broadcast"), 
    UPLOAD_BIG_PICTURE("/media/upload/big_picture"), 
    UPLOAD_SMALL_PICTURE("/media/upload/small_picture");
    
    private String path;
    
    private RequestPath(final String path) {
        this.path = path;
    }
    
    String getPath() {
        return this.path;
    }
}
