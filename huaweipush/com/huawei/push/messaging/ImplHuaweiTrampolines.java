// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.messaging;

import org.apache.http.client.HttpClient;

public final class ImplHuaweiTrampolines
{
    private ImplHuaweiTrampolines() {
    }
    
    public static HuaweiCredential getCredential(final HuaweiApp app) {
        return app.getOption().getCredential();
    }
    
    public static String getAccessToken(final HuaweiApp app) {
        return app.getOption().getCredential().getAccessToken();
    }
    
    public static String getAppId(final HuaweiApp app) {
        return app.getOption().getCredential().getAppId();
    }
    
    public static HttpClient getHttpClient(final HuaweiApp app) {
        return (HttpClient)app.getOption().getHttpClient();
    }
    
    public static <T extends HuaweiService> T getService(final HuaweiApp app, final String id, final Class<T> type) {
        return type.cast(app.getService(id));
    }
    
    public static <T extends HuaweiService> T addService(final HuaweiApp app, final T service) {
        app.addService(service);
        return service;
    }
}
