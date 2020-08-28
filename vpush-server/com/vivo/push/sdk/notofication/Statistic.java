// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.notofication;

public class Statistic
{
    private String taskId;
    private long target;
    private long valid;
    private long send;
    private long receive;
    private long display;
    private long click;
    private long targetInvalid;
    private long targetUnSub;
    private long targetInActive;
    private long covered;
    private long controlled;
    private long targetOffline;
    
    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(final String taskId) {
        this.taskId = taskId;
    }
    
    public long getTarget() {
        return this.target;
    }
    
    public void setTarget(final long target) {
        this.target = target;
    }
    
    public long getValid() {
        return this.valid;
    }
    
    public void setValid(final long valid) {
        this.valid = valid;
    }
    
    public long getSend() {
        return this.send;
    }
    
    public void setSend(final long send) {
        this.send = send;
    }
    
    public long getReceive() {
        return this.receive;
    }
    
    public void setReceive(final long receive) {
        this.receive = receive;
    }
    
    public long getDisplay() {
        return this.display;
    }
    
    public void setDisplay(final long display) {
        this.display = display;
    }
    
    public long getClick() {
        return this.click;
    }
    
    public void setClick(final long click) {
        this.click = click;
    }
    
    public long getTargetInvalid() {
        return this.targetInvalid;
    }
    
    public void setTargetInvalid(final long targetInvalid) {
        this.targetInvalid = targetInvalid;
    }
    
    public long getTargetUnSub() {
        return this.targetUnSub;
    }
    
    public void setTargetUnSub(final long targetUnSub) {
        this.targetUnSub = targetUnSub;
    }
    
    public long getTargetInActive() {
        return this.targetInActive;
    }
    
    public void setTargetInActive(final long targetInActive) {
        this.targetInActive = targetInActive;
    }
    
    public long getCovered() {
        return this.covered;
    }
    
    public void setCovered(final long covered) {
        this.covered = covered;
    }
    
    public long getControlled() {
        return this.controlled;
    }
    
    public void setControlled(final long controlled) {
        this.controlled = controlled;
    }
    
    public long getTargetOffline() {
        return this.targetOffline;
    }
    
    public void setTargetOffline(final long targetOffline) {
        this.targetOffline = targetOffline;
    }
}
