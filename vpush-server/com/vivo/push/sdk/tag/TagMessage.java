// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.tag;

import java.util.Set;

public class TagMessage
{
    private final String name;
    private final String oldName;
    private final String newName;
    private final String desc;
    private final String group;
    private final int type;
    private final Set<String> ids;
    
    protected TagMessage(final Builder builder) {
        this.name = builder.name;
        this.oldName = builder.oldName;
        this.newName = builder.newName;
        this.desc = builder.desc;
        this.group = builder.group;
        this.type = builder.type;
        this.ids = builder.ids;
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
    
    public String getDesc() {
        return this.desc;
    }
    
    public String getGroup() {
        return this.group;
    }
    
    public int getType() {
        return this.type;
    }
    
    public Set<String> getIds() {
        return this.ids;
    }
    
    public static final class Builder
    {
        private String name;
        private String oldName;
        private String newName;
        private String desc;
        private String group;
        private int type;
        private Set<String> ids;
        
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
        
        public Builder desc(final String desc) {
            this.desc = desc;
            return this;
        }
        
        public Builder group(final String group) {
            this.group = group;
            return this;
        }
        
        public Builder type(final int type) {
            this.type = type;
            return this;
        }
        
        public Builder ids(final Set<String> ids) {
            this.ids = ids;
            return this;
        }
        
        public TagMessage build() {
            final TagMessage TagMessage = new TagMessage(this);
            return TagMessage;
        }
    }
}
