// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import com.alibaba.fastjson.JSON;
import java.util.Map;
import java.util.HashMap;

class Auth
{
    private static final String SHA_256 = "SHA-256";
    
    static Result getAuthResult(final String appKey, final String masterSecret) throws Exception {
        final Map<String, Object> body = new HashMap<String, Object>(3);
        final long timestamp = System.currentTimeMillis();
        body.put("app_key", appKey);
        body.put("timestamp", timestamp);
        body.put("sign", getSign(appKey, timestamp, masterSecret));
        return getAuthResultWithRetry(RequestPath.AUTH, body);
    }
    
    private static Result getAuthResultWithRetry(final RequestPath path, final Map<String, Object> body) throws Exception {
        Result result = null;
        int tryTime = 0;
        while (tryTime < Constants.HTTP_RETRY_TIME) {
            try {
                result = HttpClientTool.httpPost(path, body);
                if (result != null && !Validate.isEmpty(result.getToken())) {
                    return result;
                }
                ++tryTime;
            }
            catch (Exception e) {
                e.printStackTrace();
                if (tryTime >= Constants.HTTP_RETRY_TIME - 1) {
                    throw e;
                }
                ++tryTime;
            }
        }
        throw new RuntimeException(String.format("get token error: %s", (result == null) ? "get token result is null" : JSON.toJSONString((Object)result)));
    }
    
    private static String getSign(final String appKey, final long timestamp, final String masterSecret) throws Exception {
        final String plaintext = String.format("%s%s%s", appKey, timestamp, masterSecret);
        return encrypt(plaintext, "SHA-256");
    }
    
    private static String encrypt(final String plaintext, final String encryptType) throws Exception {
        String cipherText = null;
        if (plaintext != null && plaintext.length() > 0) {
            try {
                final MessageDigest messageDigest = MessageDigest.getInstance(encryptType);
                messageDigest.update(plaintext.getBytes());
                final byte[] byteBuffer = messageDigest.digest();
                final StringBuilder strHexString = new StringBuilder(byteBuffer.length * 2);
                for (int i = 0; i < byteBuffer.length; ++i) {
                    final String hex = Integer.toHexString(0xFF & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                cipherText = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return cipherText;
    }
}
