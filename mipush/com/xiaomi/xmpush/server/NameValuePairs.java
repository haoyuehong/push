// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class NameValuePairs
{
    private List<NameValuePair> pairs;
    
    NameValuePairs() {
        this.pairs = new ArrayList<NameValuePair>();
    }
    
    public List<NameValuePair> getPairs() {
        return this.pairs;
    }
    
    NameValuePairs nameAndValue(final String name, final Object... values) {
        if (values != null && values.length > 0) {
            this.pairs.add(new NameValuePair(name, values));
        }
        return this;
    }
    
    public boolean isEmpty() {
        return this.pairs == null || this.pairs.size() == 0;
    }
    
    public String toQueryOrFormData() throws UnsupportedEncodingException {
        final StringBuilder data = new StringBuilder();
        if (this.pairs != null && this.pairs.size() > 0) {
            final Iterator<NameValuePair> iter = this.pairs.iterator();
            if (iter.hasNext()) {
                NameValuePair pair = iter.next();
                for (final Object value : pair.values) {
                    if (value != null) {
                        data.append(pair.name).append("=").append(URLEncoder.encode(value.toString(), "UTF-8"));
                    }
                }
                while (iter.hasNext()) {
                    pair = iter.next();
                    for (final Object value : pair.values) {
                        if (value != null) {
                            data.append("&").append(pair.name).append("=").append(URLEncoder.encode(value.toString(), "UTF-8"));
                        }
                    }
                }
            }
        }
        return data.toString();
    }
    
    public static class NameValuePair
    {
        private String name;
        private Object[] values;
        
        public NameValuePair(final String name, final Object[] values) {
            this.name = name;
            this.values = values;
        }
        
        public String getName() {
            return this.name;
        }
        
        public Object[] getValues() {
            return this.values;
        }
    }
}
