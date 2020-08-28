// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.common;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.nio.charset.Charset;

public class SignUtils
{
    public static String getSign(final int appId, final String appKey, final Long time, final String appSecret) {
        final String s = appId + appKey + time + appSecret;
        return sign(s);
    }
    
    private static String sign(final String waitSignStr) {
        String sign = "";
        try {
            sign = toHexValue(encryptMD5(waitSignStr.getBytes(Charset.forName("utf-8"))));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("md5 error");
        }
        return sign;
    }
    
    private static byte[] encryptMD5(final byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw e;
        }
        md5.update(data);
        return md5.digest();
    }
    
    private static String toHexValue(final byte[] messageDigest) {
        if (messageDigest == null) {
            return "";
        }
        final StringBuilder hexValue = new StringBuilder();
        for (final byte aMessageDigest : messageDigest) {
            final int val = 0xFF & aMessageDigest;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
