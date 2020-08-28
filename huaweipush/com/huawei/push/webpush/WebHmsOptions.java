// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.webpush;

import java.net.MalformedURLException;
import com.huawei.push.util.ValidatorUtils;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class WebHmsOptions
{
    @JSONField(name = "link")
    private String link;
    
    public String getLink() {
        return this.link;
    }
    
    public WebHmsOptions(final Builder builder) {
        this.link = builder.link;
    }
    
    public void check() {
        if (!StringUtils.isEmpty((CharSequence)this.link)) {
            try {
                new URL(this.link);
            }
            catch (MalformedURLException e) {
                ValidatorUtils.checkArgument(false, "Invalid link");
            }
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private String link;
        
        public Builder setLink(final String link) {
            this.link = link;
            return this;
        }
        
        public WebHmsOptions build() {
            return new WebHmsOptions(this);
        }
    }
}
