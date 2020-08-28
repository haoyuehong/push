// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

import com.alibaba.fastjson.JSONArray;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import com.alibaba.fastjson.JSONObject;
import java.util.List;

public class Result
{
    private int statusCode;
    private String reason;
    private ReturnCode returnCode;
    private String messageId;
    private String taskId;
    private String token;
    private Long createTime;
    private List<UnicastBatchResult> unicastBatchResults;
    private List<BroadcastErrorResult> broadcastErrorResults;
    private String bigPictureId;
    private String smallPictureId;
    
    public int getStatusCode() {
        return this.statusCode;
    }
    
    void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    void setReason(final String reason) {
        this.reason = reason;
    }
    
    public ReturnCode getReturnCode() {
        return this.returnCode;
    }
    
    private void setReturnCode(final ReturnCode returnCode) {
        this.returnCode = returnCode;
    }
    
    public String getMessageId() {
        return this.messageId;
    }
    
    private void setMessageId(final String messageId) {
        this.messageId = messageId;
    }
    
    public String getTaskId() {
        return this.taskId;
    }
    
    private void setTaskId(final String taskId) {
        this.taskId = taskId;
    }
    
    public String getToken() {
        return this.token;
    }
    
    private void setToken(final String token) {
        this.token = token;
    }
    
    public Long getCreateTime() {
        return this.createTime;
    }
    
    private void setCreateTime(final Long createTime) {
        this.createTime = createTime;
    }
    
    public List<UnicastBatchResult> getUnicastBatchResults() {
        return this.unicastBatchResults;
    }
    
    private void setUnicastBatchResults(final List<UnicastBatchResult> unicastBatchResults) {
        this.unicastBatchResults = unicastBatchResults;
    }
    
    public List<BroadcastErrorResult> getBroadcastErrorResults() {
        return this.broadcastErrorResults;
    }
    
    private void setBroadcastErrorResults(final List<BroadcastErrorResult> broadcastErrorResults) {
        this.broadcastErrorResults = broadcastErrorResults;
    }
    
    private BroadcastErrorResult newBroadCastErrorResult() {
        return new BroadcastErrorResult();
    }
    
    private UnicastBatchResult newUnicastBatchResult() {
        return new UnicastBatchResult();
    }
    
    public String getBigPictureId() {
        return this.bigPictureId;
    }
    
    private void setBigPictureId(final String bigPictureId) {
        this.bigPictureId = bigPictureId;
    }
    
    public String getSmallPictureId() {
        return this.smallPictureId;
    }
    
    private void setSmallPictureId(final String smallPictureId) {
        this.smallPictureId = smallPictureId;
    }
    
    private void setAuthResult(final JSONObject data) {
        if (data != null) {
            this.setToken(data.getString("auth_token"));
            this.setCreateTime(data.getLong("create_time"));
        }
    }
    
    private void setUnicastResult(final JSONObject data) {
        if (data != null) {
            this.setMessageId(data.getString("messageId"));
        }
    }
    
    private void setSaveMessageResult(final JSONObject data) {
        if (data != null) {
            this.setMessageId(data.getString("message_id"));
        }
    }
    
    private void setUploadBigPicResult(final JSONObject data) {
        if (data != null) {
            this.setBigPictureId(data.getString("big_picture_id"));
        }
    }
    
    private void setUploadSmallPicResult(final JSONObject data) {
        if (data != null) {
            this.setSmallPictureId(data.getString("small_picture_id"));
        }
    }
    
    private void setBroadcastResult(final JSONObject data) {
        if (data == null || data.size() == 0) {
            return;
        }
        final List<BroadcastErrorResult> errorResults = new ArrayList<BroadcastErrorResult>();
        for (final Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getKey().equals("messageId") || entry.getKey().equals("message_id")) {
                this.setMessageId(entry.getValue());
            }
            else if (entry.getKey().equals("task_id")) {
                this.setTaskId(entry.getValue());
            }
            else {
                if (!Validate.validateErrorCode(entry.getKey())) {
                    continue;
                }
                final BroadcastErrorResult errorResult = this.newBroadCastErrorResult();
                errorResult.setErrorCode(entry.getKey());
                errorResult.setTargetValue(entry.getValue().toString());
                errorResults.add(errorResult);
            }
        }
        this.setBroadcastErrorResults(errorResults);
    }
    
    private void setUnicastBatchResults(final JSONObject responseBody) {
        if (responseBody.get((Object)"data") == null || responseBody.get((Object)"data") instanceof JSONObject) {
            return;
        }
        final JSONArray dataArray = responseBody.getJSONArray("data");
        final List<UnicastBatchResult> unicastBatchResults = new ArrayList<UnicastBatchResult>();
        if (dataArray != null && dataArray.size() > 0) {
            for (int i = 0; i < dataArray.size(); ++i) {
                final JSONObject jsonObject = dataArray.getJSONObject(i);
                if (jsonObject != null) {
                    final UnicastBatchResult unicastBatchResult = this.newUnicastBatchResult();
                    unicastBatchResult.setMessageId(jsonObject.getString("messageId"));
                    unicastBatchResult.setTargetValue(jsonObject.getString("registrationId"));
                    unicastBatchResult.setErrorCode(jsonObject.getInteger("errorCode"));
                    unicastBatchResult.setErrorMessage(jsonObject.getString("errorMessage"));
                    unicastBatchResults.add(unicastBatchResult);
                }
            }
            this.setUnicastBatchResults(unicastBatchResults);
        }
    }
    
    void setResult(final RequestPath path, final JSONObject responseBody) {
        final Integer code = responseBody.getInteger("code");
        final String message = responseBody.getString("message");
        if (code != null) {
            this.setReturnCode(ReturnCode.valueOf(code, message));
        }
        switch (path) {
            case AUTH: {
                this.setAuthResult(responseBody.getJSONObject("data"));
                break;
            }
            case NOTIFICATION_UNICAST: {
                this.setUnicastResult(responseBody.getJSONObject("data"));
                break;
            }
            case NOTIFICATION_SAVE_MESSAGE: {
                this.setSaveMessageResult(responseBody.getJSONObject("data"));
                break;
            }
            case NOTIFICATION_BROADCAST: {
                this.setBroadcastResult(responseBody.getJSONObject("data"));
                break;
            }
            case NOTIFICATION_UNICAST_BATCH: {
                this.setUnicastBatchResults(responseBody);
                break;
            }
            case UPLOAD_SMALL_PICTURE: {
                this.setUploadSmallPicResult(responseBody.getJSONObject("data"));
                break;
            }
            case UPLOAD_BIG_PICTURE: {
                this.setUploadBigPicResult(responseBody.getJSONObject("data"));
                break;
            }
        }
    }
    
    @Override
    public String toString() {
        return "Result{statusCode=" + this.statusCode + ", reason='" + this.reason + '\'' + ", returnCode=" + this.returnCode + ", messageId='" + this.messageId + '\'' + ", taskId='" + this.taskId + '\'' + ", token='" + this.token + '\'' + ", createTime=" + this.createTime + ", unicastBatchResults=" + this.unicastBatchResults + ", broadcastErrorResults=" + this.broadcastErrorResults + ", bigPictureId='" + this.bigPictureId + '\'' + ", smallPictureId='" + this.smallPictureId + '\'' + '}';
    }
    
    public class BroadcastErrorResult
    {
        private String errorCode;
        private String targetValue;
        
        public String getErrorCode() {
            return this.errorCode;
        }
        
        private void setErrorCode(final String errorCode) {
            this.errorCode = errorCode;
        }
        
        public String getTargetValue() {
            return this.targetValue;
        }
        
        private void setTargetValue(final String targetValue) {
            this.targetValue = targetValue;
        }
        
        @Override
        public String toString() {
            return "BroadcastErrorResult{errorCode='" + this.errorCode + '\'' + ", targetValue='" + this.targetValue + '\'' + '}';
        }
    }
    
    public class UnicastBatchResult
    {
        private String messageId;
        private String targetValue;
        private Integer errorCode;
        private String errorMessage;
        
        public String getMessageId() {
            return this.messageId;
        }
        
        private void setMessageId(final String messageId) {
            this.messageId = messageId;
        }
        
        public String getTargetValue() {
            return this.targetValue;
        }
        
        private void setTargetValue(final String targetValue) {
            this.targetValue = targetValue;
        }
        
        public Integer getErrorCode() {
            return this.errorCode;
        }
        
        private void setErrorCode(final Integer errorCode) {
            this.errorCode = errorCode;
        }
        
        public String getErrorMessage() {
            return this.errorMessage;
        }
        
        private void setErrorMessage(final String errorMessage) {
            this.errorMessage = errorMessage;
        }
        
        @Override
        public String toString() {
            return "UnicastBatchResult{messageId='" + this.messageId + '\'' + ", targetValue='" + this.targetValue + '\'' + ", errorCode=" + this.errorCode + ", errorMessage='" + this.errorMessage + '\'' + '}';
        }
    }
}
