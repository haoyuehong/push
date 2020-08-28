// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.notofication;

public class InvalidUser
{
    private int status;
    private String userid;
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(final String userid) {
        this.userid = userid;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("{ ");
        builder.append("\n  invalidUser\uff1a{");
        builder.append("\n    status\uff1a").append(this.status);
        builder.append("\n    userid\uff1a").append(this.userid);
        builder.append("\n   }");
        return builder.append("\n}").toString();
    }
}
