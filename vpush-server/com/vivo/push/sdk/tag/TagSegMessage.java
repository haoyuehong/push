// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagSegMessage
{
    private final String name;
    private final String oldName;
    private final String newName;
    private final Map<String, List<String>> expression;
    
    protected TagSegMessage(final Builder builder) {
        this.name = builder.name;
        this.oldName = builder.oldName;
        this.newName = builder.newName;
        final Map<String, List<String>> map = new HashMap<String, List<String>>();
        if (builder.orTags != null) {
            map.put("orTags", builder.orTags);
        }
        else {
            map.put("orTags", new ArrayList<String>());
        }
        if (builder.andTags != null) {
            map.put("andTags", builder.andTags);
        }
        else {
            map.put("andTags", new ArrayList<String>());
        }
        if (builder.notTags != null) {
            map.put("notTags", builder.notTags);
        }
        else {
            map.put("notTags", new ArrayList<String>());
        }
        this.expression = map;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getOldName() {
        return this.oldName;
    }
    
    public String getNewName() {
        return this.newName;
    }
    
    public Map<String, List<String>> getExpression() {
        return this.expression;
    }
    
    public static final class Builder
    {
        private String name;
        private String oldName;
        private String newName;
        private List<String> orTags;
        private List<String> andTags;
        private List<String> notTags;
        
        public Builder name(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder oldName(final String oldName) {
            this.oldName = oldName;
            return this;
        }
        
        public Builder newName(final String newName) {
            this.newName = newName;
            return this;
        }
        
        public Builder orTags(final List<String> orTags) {
            this.orTags = orTags;
            return this;
        }
        
        public Builder andTags(final List<String> andTags) {
            this.andTags = andTags;
            return this;
        }
        
        public Builder notTags(final List<String> notTags) {
            this.notTags = notTags;
            return this;
        }
        
        public TagSegMessage build() {
            final TagSegMessage TagSegMessage = new TagSegMessage(this);
            return TagSegMessage;
        }
    }
}
