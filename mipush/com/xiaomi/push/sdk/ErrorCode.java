// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.push.sdk;

import java.util.Collection;
import java.util.HashMap;

public class ErrorCode
{
    private int value;
    private String description;
    private static HashMap<Integer, ErrorCode> intErrorCodeMap;
    public static ErrorCode UnknowError;
    public static ErrorCode Success;
    public static ErrorCode NotBlank;
    public static ErrorCode SystemError;
    public static ErrorCode ServiceUnavailable;
    public static ErrorCode RemoteServiceError;
    public static ErrorCode IpLimit;
    public static ErrorCode PermissionDenied;
    public static ErrorCode MissAppKey;
    public static ErrorCode UnsupportMimeType;
    public static ErrorCode ParameterError;
    public static ErrorCode SystemIsBusy;
    public static ErrorCode JobExpired;
    public static ErrorCode RpcError;
    public static ErrorCode IllegalRequest;
    public static ErrorCode InvalidUser;
    public static ErrorCode InsufficientPermissions;
    public static ErrorCode MissRequiredParameter;
    public static ErrorCode InvalidParameterValue;
    public static ErrorCode RequestBodyLengthTooLong;
    public static ErrorCode RequestApiNotFound;
    public static ErrorCode HttpMethodUnsupport;
    public static ErrorCode IpRequestExceedQuota;
    public static ErrorCode UserRequestExceedQuota;
    public static ErrorCode UserRequestApiExceedQuota;
    public static ErrorCode InvalidCallbackUrl;
    public static ErrorCode ApplicationInBlacklist;
    public static ErrorCode ApplicationApiCallExceedQuota;
    public static ErrorCode ApplicationTotalApiCallExceedQuota;
    public static ErrorCode InvalidDevice;
    public static ErrorCode ApplicationIllegal;
    public static ErrorCode ApplicationNotExists;
    public static ErrorCode ApplicationRevoked;
    public static ErrorCode UpdateApplicationInfoFail;
    public static ErrorCode MissApplicationInfo;
    public static ErrorCode InvalidApplicationName;
    public static ErrorCode InvalidApplicationId;
    public static ErrorCode InvalidApplicationKey;
    public static ErrorCode InvalidApplicationSecret;
    public static ErrorCode InvalidApplicationDescription;
    public static ErrorCode UserNotAuthorizeApplication;
    public static ErrorCode InvalidPackageName;
    public static ErrorCode InvalidApplicationNotificationFormat;
    public static ErrorCode TooManyApplicationNotification;
    public static ErrorCode SendApplicationNotificationFail;
    public static ErrorCode InvalidNotifyId;
    
    private static HashMap<Integer, ErrorCode> createIntegerErrorCodeMapping() {
        final HashMap<Integer, ErrorCode> result = new HashMap<Integer, ErrorCode>();
        return result;
    }
    
    public static Collection<ErrorCode> getAllErrorCodes() {
        return ErrorCode.intErrorCodeMap.values();
    }
    
    private ErrorCode(final int value) {
        this.value = value;
    }
    
    private ErrorCode(final int value, final String description) {
        this.value = value;
        this.description = description;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    public String getFullDescription() {
        return this.getName() + "," + this.value + "," + this.description;
    }
    
    public String getName() {
        return this.description;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public void setValue(final int value) {
        this.value = value;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public static ErrorCode valueOf(final int value) {
        return ErrorCode.intErrorCodeMap.get(value);
    }
    
    public static ErrorCode valueOf(final int value, final ErrorCode defaultIfMissing) {
        final ErrorCode code = ErrorCode.intErrorCodeMap.get(value);
        if (code == null) {
            return defaultIfMissing;
        }
        return code;
    }
    
    public static ErrorCode valueOf(final Integer code, final String reason) {
        ErrorCode result = ErrorCode.intErrorCodeMap.get(code);
        if (result == null) {
            result = new ErrorCode(code, reason);
            ErrorCode.intErrorCodeMap.put(code, result);
        }
        return result;
    }
    
    static {
        ErrorCode.intErrorCodeMap = createIntegerErrorCodeMapping();
        ErrorCode.UnknowError = valueOf(-1, "\u672a\u77e5\u9519\u8bef");
        ErrorCode.Success = valueOf(0, "\u6210\u529f");
        ErrorCode.NotBlank = valueOf(100, "\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        ErrorCode.SystemError = valueOf(10001, "\u7cfb\u7edf\u9519\u8bef");
        ErrorCode.ServiceUnavailable = valueOf(10002, "\u670d\u52a1\u6682\u505c");
        ErrorCode.RemoteServiceError = valueOf(10003, "\u8fdc\u7a0b\u670d\u52a1\u9519\u8bef");
        ErrorCode.IpLimit = valueOf(10004, "IP\u9650\u5236\u4e0d\u80fd\u8bf7\u6c42\u8be5\u8d44\u6e90");
        ErrorCode.PermissionDenied = valueOf(10005, "\u8be5\u8d44\u6e90\u9700\u8981appkey\u62e5\u6709\u6388\u6743");
        ErrorCode.MissAppKey = valueOf(10006, "\u7f3a\u5c11appkey\u53c2\u6570");
        ErrorCode.UnsupportMimeType = valueOf(10007, "\u4e0d\u652f\u6301\u7684 mime type");
        ErrorCode.ParameterError = valueOf(10008, "\u53c2\u6570\u9519\u8bef\uff0c\u8bf7\u53c2\u8003API\u6587\u6863");
        ErrorCode.SystemIsBusy = valueOf(10009, "\u7cfb\u7edf\u7e41\u5fd9");
        ErrorCode.JobExpired = valueOf(10010, "\u4efb\u52a1\u8d85\u65f6");
        ErrorCode.RpcError = valueOf(10011, "RPC\u9519\u8bef");
        ErrorCode.IllegalRequest = valueOf(10012, "\u975e\u6cd5\u8bf7\u6c42");
        ErrorCode.InvalidUser = valueOf(10013, "\u4e0d\u5408\u6cd5\u7684\u7528\u6237");
        ErrorCode.InsufficientPermissions = valueOf(10014, "\u5e94\u7528\u7684\u63a5\u53e3\u8bbf\u95ee\u6743\u9650\u53d7\u9650");
        ErrorCode.MissRequiredParameter = valueOf(10016, "\u7f3a\u5931\u5fc5\u9009\u53c2\u6570");
        ErrorCode.InvalidParameterValue = valueOf(10017, "\u53c2\u6570\u503c\u975e\u6cd5");
        ErrorCode.RequestBodyLengthTooLong = valueOf(10018, "\u8bf7\u6c42\u957f\u5ea6\u8d85\u8fc7\u9650\u5236");
        ErrorCode.RequestApiNotFound = valueOf(10020, "\u63a5\u53e3\u4e0d\u5b58\u5728");
        ErrorCode.HttpMethodUnsupport = valueOf(10021, "\u8bf7\u6c42\u7684HTTP\u65b9\u6cd5\u4e0d\u652f\u6301");
        ErrorCode.IpRequestExceedQuota = valueOf(10022, "IP\u8bf7\u6c42\u9891\u6b21\u8d85\u8fc7\u4e0a\u9650");
        ErrorCode.UserRequestExceedQuota = valueOf(10023, "\u7528\u6237\u8bf7\u6c42\u9891\u6b21\u8d85\u8fc7\u4e0a\u9650");
        ErrorCode.UserRequestApiExceedQuota = valueOf(10024, "\u7528\u6237\u8bf7\u6c42\u7279\u6b8a\u63a5\u53e3\u9891\u6b21\u8d85\u8fc7\u4e0a\u9650");
        ErrorCode.InvalidCallbackUrl = valueOf(10025, "Callback\u8fde\u63a5\u4e0d\u5408\u6cd5");
        ErrorCode.ApplicationInBlacklist = valueOf(10026, "\u5e94\u7528\u88ab\u52a0\u5165\u9ed1\u540d\u5355\uff0c\u4e0d\u80fd\u8c03\u7528API");
        ErrorCode.ApplicationApiCallExceedQuota = valueOf(10027, "\u5e94\u7528\u7684API\u8c03\u7528\u592a\u9891\u7e41");
        ErrorCode.ApplicationTotalApiCallExceedQuota = valueOf(10028, "\u5e94\u7528API\u8c03\u7528\u603b\u6570\u592a\u9891\u7e41");
        ErrorCode.InvalidDevice = valueOf(10029, "\u4e0d\u5408\u6cd5\u7684\u8bbe\u5907");
        ErrorCode.ApplicationIllegal = valueOf(22000, "\u975e\u6cd5\u5e94\u7528");
        ErrorCode.ApplicationNotExists = valueOf(22001, "\u5e94\u7528\u4e0d\u5b58\u5728");
        ErrorCode.ApplicationRevoked = valueOf(22002, "\u5e94\u7528\u5df2\u7ecf\u64a4\u9500");
        ErrorCode.UpdateApplicationInfoFail = valueOf(22003, "\u66f4\u65b0\u5e94\u7528\u7a0b\u5e8f\u5931\u8d25");
        ErrorCode.MissApplicationInfo = valueOf(22004, "\u7f3a\u5c11\u5e94\u7528\u7a0b\u5e8f\u4fe1\u606f");
        ErrorCode.InvalidApplicationName = valueOf(22005, "\u5e94\u7528\u7a0b\u5e8f\u540d\u5b57\u4e0d\u5408\u6cd5");
        ErrorCode.InvalidApplicationId = valueOf(22006, "\u5e94\u7528\u7a0b\u5e8fId\u4e0d\u5408\u6cd5");
        ErrorCode.InvalidApplicationKey = valueOf(22007, "\u5e94\u7528\u7a0b\u5e8fKey\u4e0d\u5408\u6cd5");
        ErrorCode.InvalidApplicationSecret = valueOf(22008, "\u5e94\u7528\u7a0b\u5e8fSecret\u4e0d\u5408\u6cd5");
        ErrorCode.InvalidApplicationDescription = valueOf(22020, "\u5e94\u7528\u7a0b\u5e8f\u63cf\u8ff0\u4fe1\u606f\u4e0d\u5408\u6cd5");
        ErrorCode.UserNotAuthorizeApplication = valueOf(22021, "\u7528\u6237\u6ca1\u6709\u6388\u6743\u7ed9\u5e94\u7528\u7a0b\u5e8f");
        ErrorCode.InvalidPackageName = valueOf(22022, "\u5e94\u7528\u7a0b\u5e8fpackage name\u4e0d\u5408\u6cd5");
        ErrorCode.InvalidApplicationNotificationFormat = valueOf(22100, "\u5e94\u7528\u901a\u77e5\u6570\u636e\u683c\u5f0f\u4e0d\u5408\u6cd5");
        ErrorCode.TooManyApplicationNotification = valueOf(22101, "\u592a\u591a\u5e94\u7528\u901a\u77e5\u6d88\u606f");
        ErrorCode.SendApplicationNotificationFail = valueOf(22102, "\u53d1\u9001\u5e94\u7528\u901a\u77e5\u6d88\u606f\u5931\u8d25");
        ErrorCode.InvalidNotifyId = valueOf(22103, "\u5e94\u7528\u901a\u77e5ID\u4e0d\u5408\u6cd5");
    }
}
