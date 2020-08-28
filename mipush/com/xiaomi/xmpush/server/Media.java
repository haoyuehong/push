// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.io.IOException;
import java.io.File;

public class Media extends PushSender<Media>
{
    public Media(final String security) {
        super(security);
    }
    
    public Media(final String security, final Region region) {
        super(security, region);
    }
    
    public Result upload(final File file, final boolean isIcon, final boolean isGlobal) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("is_global", String.valueOf(isGlobal)).nameAndValue("is_icon", String.valueOf(isIcon));
        Result result;
        try {
            final String response = this.upload(Constants.XmPushRequestPath.MEDIA_UPLOAD_IMAGE, file, null, queryParams, null);
            if (XMStringUtils.isBlank(response)) {
                throw this.exception(1, null);
            }
            result = this.parseResult(response);
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        return result;
    }
    
    public Result uploadSmallIcon(final File file) throws IOException {
        Result result;
        try {
            final String response = this.upload(Constants.XmPushRequestPath.MEDIA_UPLOAD_SMALLICON, file, null, null, null);
            if (XMStringUtils.isBlank(response)) {
                throw this.exception(1, null);
            }
            result = this.parseResult(response);
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        return result;
    }
    
    public Result querySmallIconInfoByAppId(final long appId) throws IOException {
        final NameValuePairs queryParams = new NameValuePairs().nameAndValue("app_id", String.valueOf(appId));
        Result result;
        try {
            final String response = this.get(Constants.XmPushRequestPath.MEDIA_UPLOAD_SMALLICON_QUERY, null, queryParams, null);
            if (XMStringUtils.isBlank(response)) {
                throw this.exception(1, null);
            }
            result = this.parseResult(response);
        }
        catch (Exception e) {
            throw this.exception(1, e);
        }
        return result;
    }
}
