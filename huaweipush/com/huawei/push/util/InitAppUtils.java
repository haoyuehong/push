// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.util;

import com.huawei.push.messaging.HuaweiOption;
import com.huawei.push.messaging.HuaweiCredential;
import com.huawei.push.messaging.HuaweiApp;

public class InitAppUtils
{
    private static final String appId = "102707909";
    private static final String appSecret = "c9894ce9d5059399cb9389d242c58632b3f50e0f5b283986fe4e7c46a7f40807";
    
    public static HuaweiApp initializeApp() {
        return initializeApp("102707909", "c9894ce9d5059399cb9389d242c58632b3f50e0f5b283986fe4e7c46a7f40807");
    }
    
    private static HuaweiApp initializeApp(final String appId, final String appSecret) {
        final HuaweiCredential credential = HuaweiCredential.builder().setAppId(appId).setAppSecret(appSecret).build();
        final HuaweiOption option = HuaweiOption.builder().setCredential(credential).build();
        return HuaweiApp.getInstance(option);
    }
}
