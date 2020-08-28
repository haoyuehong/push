// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.notofication;

import java.util.Iterator;
import java.util.List;
import java.io.Serializable;

public final class Result implements Serializable
{
    private int result;
    private String desc;
    private String authToken;
    private String taskId;
    private List<Statistic> statistics;
    private InvalidUser invalidUser;
    private List<InvalidUser> invalidUsers;
    private List<InvalidUser> data;
    
    public Result() {
        this.result = 2;
    }
    
    public int getResult() {
        return this.result;
    }
    
    public void setResult(final int result) {
        this.result = result;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
    
    public String getAuthToken() {
        return this.authToken;
    }
    
    public void setAuthToken(final String authToken) {
        this.authToken = authToken;
    }
    
    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(final String taskId) {
        this.taskId = taskId;
    }
    
    public InvalidUser getInvalidUser() {
        return this.invalidUser;
    }
    
    public void setInvalidUser(final InvalidUser invalidUser) {
        this.invalidUser = invalidUser;
    }
    
    public List<Statistic> getStatistics() {
        return this.statistics;
    }
    
    public void setStatistics(final List<Statistic> statistics) {
        this.statistics = statistics;
    }
    
    public List<InvalidUser> getInvalidUsers() {
        return this.invalidUsers;
    }
    
    public void setInvalidUsers(final List<InvalidUser> invalidUsers) {
        this.invalidUsers = invalidUsers;
    }
    
    public List<InvalidUser> getData() {
        return this.data;
    }
    
    public void setData(final List<InvalidUser> data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("{ ");
        if (this.result != 2) {
            builder.append("resultCode\uff1a").append(this.result);
        }
        if (this.desc != null) {
            builder.append("\n  description\uff1a").append(this.desc);
        }
        if (this.authToken != null) {
            builder.append("\n  authToken\uff1a").append(this.authToken);
        }
        if (this.taskId != null) {
            builder.append("\n  taskId\uff1a").append(this.taskId);
        }
        if (this.statistics != null) {
            builder.append("\n  statistics\uff1a");
            for (final Statistic statistic : this.statistics) {
                builder.append("\n  { taskId\uff1a").append(statistic.getTaskId());
                builder.append("\n    target\uff1a").append(statistic.getTarget());
                builder.append("\n    valid\uff1a").append(statistic.getValid());
                builder.append("\n    sendCount\uff1a").append(statistic.getSend());
                builder.append("\n    receiveCount\uff1a").append(statistic.getReceive());
                builder.append("\n    displayCount\uff1a").append(statistic.getDisplay());
                builder.append("\n    clickCount\uff1a").append(statistic.getClick());
                builder.append("\n    targetInvalid\uff1a").append(statistic.getTargetInvalid());
                builder.append("\n    targetUnSub\uff1a").append(statistic.getTargetUnSub());
                builder.append("\n    targetInActive\uff1a").append(statistic.getTargetInActive());
                builder.append("\n    covered\uff1a").append(statistic.getCovered());
                builder.append("\n    controlled\uff1a").append(statistic.getControlled());
                builder.append("\n    targetOffline\uff1a").append(statistic.getTargetOffline()).append(" }");
            }
        }
        if (this.invalidUser != null) {
            builder.append("\n  invalidUser\uff1a");
            builder.append("\n  { status\uff1a").append(this.invalidUser.getStatus());
            builder.append("\n    userid\uff1a").append(this.invalidUser.getUserid()).append(" }");
        }
        if (this.invalidUsers != null) {
            builder.append("\n  invalidUsers\uff1a[");
            for (final InvalidUser invalidUser : this.invalidUsers) {
                builder.append("\n  { status\uff1a").append(invalidUser.getStatus());
                builder.append("\n    userid\uff1a").append(invalidUser.getUserid()).append(" }");
            }
            builder.append("\n  ]");
        }
        if (this.data != null) {
            builder.append("\n  data\uff1a[");
            for (final InvalidUser invalidUser : this.data) {
                builder.append("\n  { status\uff1a").append(invalidUser.getStatus());
                builder.append("\n    userid\uff1a").append(invalidUser.getUserid()).append(" }");
            }
            builder.append("\n  ]");
        }
        return builder.append("\n}").toString();
    }
}
