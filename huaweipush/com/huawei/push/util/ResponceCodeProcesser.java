// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.util;

import java.util.HashMap;
import java.util.Map;

public class ResponceCodeProcesser
{
    private static final Map<Integer, String> codeMap;
    
    public static String process(final Integer code) {
        return ResponceCodeProcesser.codeMap.getOrDefault(code, "Unknown code");
    }
    
    static {
        codeMap = new HashMap<Integer, String>() {
            {
                this.put(80000000, "Success");
                this.put(80100000, "Some tokens are right, the others are illegal");
                this.put(80100001, "Parameters check error");
                this.put(80100002, "Token number should be one when send sys message");
                this.put(80100003, "Message structure error");
                this.put(80100004, "TTL is less than current time, please check");
                this.put(80100013, "Collapse_key is illegal, please check");
                this.put(80100016, "Message contians sensitive information, please check");
                this.put(80200001, "Oauth authentication error");
                this.put(80200003, "Oauth Token expired");
                this.put(80300002, "APP is forbidden to send");
                this.put(80300007, "Invalid Token");
                this.put(80300008, "The message body size exceeds the default value set by the system (4K)");
                this.put(80300010, "Tokens exceed the default value");
                this.put(81000001, "System inner error");
                this.put(82000001, "GroupKey or groupName error");
                this.put(82000002, "GroupKey and groupName do not match");
                this.put(82000003, "Token array is null");
                this.put(82000004, "Group do not exist");
                this.put(82000005, "Group do not belond to this app");
                this.put(82000006, "Token array or group number is transfinited");
                this.put(82000007, "Invalid topic");
                this.put(82000008, "Token array null or transfinited");
                this.put(82000009, "Topic num transfinited, at most 2000");
                this.put(82000010, "Some token is wrong");
                this.put(82000011, "Token is null");
                this.put(82000012, "Data storage location is not selected");
                this.put(82000013, "Data storage location does not match the actual data");
            }
        };
    }
}
