// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

public class ReturnCode
{
    private int code;
    private String message;
    
    public int getCode() {
        return this.code;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    static ReturnCode valueOf(final int code, final String message) {
        final ErrorCode errorCode = getErrorCode(code, message);
        if (errorCode != null) {
            return new ReturnCode(errorCode.getCode(), errorCode.getMessage());
        }
        return new ReturnCode(code, message);
    }
    
    private ReturnCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "ReturnCode{code=" + this.code + ", message='" + this.message + '\'' + '}';
    }
    
    public enum ErrorCode
    {
        SUCCESS(0, "Success"), 
        SERVICE_ERROR(-1, "Service Currently Unavailable"), 
        SERVICE_FLOW_CONTROL(-2, "Service in Flow Control"), 
        INVALID_AUTH_TOKEN(11, "Invalid Auth Token"), 
        HTTP_METHOD_ERROR(12, "Http Action Not Allowed"), 
        CALL_LIMIT_ERROR(13, "App Call Limited"), 
        APP_KEY_INVALID_ERROR(14, "Invalid App Key"), 
        APP_KEY_MISSING_ERROR(15, "Missing App Key"), 
        SIGNATURE_INVALID_ERROR(16, "Invalid Signature"), 
        SIGNATURE_MISSING_ERROR(17, "Missing Signature"), 
        TIMESTAMP_MISSING_ERROR(18, "Missing Timestamp"), 
        TIMESTAMP_INVALID_ERROR(19, "Invalid Timestamp"), 
        METHOD_INVALID_ERROR(20, "Invalid Method"), 
        METHOD_MISSING_ERROR(21, "Missing Method"), 
        VERSION_MISSING_ERROR(22, "Missing Version"), 
        VERSION_INVALID_ERROR(23, "Invalid Version"), 
        VERSION_UNSUPPORTED_ERROR(24, "Unsupported Version"), 
        ENCODING_ERROR(25, "Invalid encoding"), 
        IP_BLACK_ERROR(26, "IP Black List"), 
        ACCESS_DENIED(27, "Access Denied"), 
        APP_DISABLED(28, "App Disabled"), 
        MISSING_AUTH_TOKEN(29, "Missing Auth Token"), 
        API_PERMISSION_DENIED(30, "API Permission Denied"), 
        DATA_NOT_EXISTS(31, "Data Not Exists"), 
        DATA_DUPLICATE(32, "Data Duplicate Conflict"), 
        EXCEED_DAILY_LIMIT(33, "The number of messages exceeds the daily limit"), 
        EXCEED_UPLOAD_DAILY_LIMIT(33, "The number of upload pictures exceeds the daily limit"), 
        PARAMETER_MISSING_ERROR(40, "Missing Required Arguments"), 
        PARAMETER_INVALID_ERROR(41, "Invalid Arguments"), 
        INVALID_PICTURE(51, "Invalid Picture"), 
        RESPONSE_PARSE_ERROR(-2, "Response Parse Error");
        
        private int code;
        private String message;
        
        int getCode() {
            return this.code;
        }
        
        String getMessage() {
            return this.message;
        }
        
        private ErrorCode(final int code, final String message) {
            this.code = code;
            this.message = message;
        }
        
        private static ErrorCode getErrorCode(final int code, final String message) {
            for (final ErrorCode errorCode : values()) {
                if (errorCode.getCode() == code && errorCode.getMessage().equals(message)) {
                    return errorCode;
                }
            }
            return null;
        }
    }
}
