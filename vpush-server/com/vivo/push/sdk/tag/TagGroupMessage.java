// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.tag;

import java.util.List;

public class TagGroupMessage
{
    private final String name;
    private final String oldName;
    private final String newName;
    private final String desc;
    private final int type;
    private final List<String> tagList;
    
    protected TagGroupMessage(final Builder builder) {
        this.name = builder.name;
        this.oldName = builder.oldName;
        this.newName = builder.newName;
        this.desc = builder.desc;
        this.type = builder.type;
        this.tagList = builder.tagList;
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
    
    public int getType() {
        return this.type;
    }
    
    public List<String> getTagList() {
        return this.tagList;
    }
    
    public static final class Builder
    {
        private String name;
        private String oldName;
        private String newName;
        private String desc;
        private int type;
        private List<String> tagList;
        
        public Builder name(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder oldname(final String oldName) {
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
        
        public Builder type(final int type) {
            this.type = type;
            return this;
        }
        
        public Builder tagList(final List<String> tagList) {
            this.tagList = tagList;
            return this;
        }
        
        public TagGroupMessage build() {
            final TagGroupMessage GroupMessage = new TagGroupMessage(this);
            return GroupMessage;
        }
    }
}
