// 
// Decompiled by Procyon v0.5.36
// 

package com.oppo.push.server;

import java.io.File;
import java.util.regex.Pattern;

class Validate
{
    private static final Pattern PATTERN_OBJECT_ID;
    private static final Pattern PATTERN_REGISTRATION_ID;
    private static final Pattern PATTERN_ERROR_CODE;
    private static final Pattern FILE_SUFFIX_IMAGE;
    
    static <T> T nonNull(final T source, final String field) {
        if (source == null) {
            throw new IllegalArgumentException(String.format("%s%s", field, " cannot be null"));
        }
        return source;
    }
    
    static Integer validatePositiveInteger(final Integer value, final String field) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException(String.format("%s%s", field, " must be positive "));
        }
        return value;
    }
    
    static void validateLength(final String source, final String field, final int limit) {
        if (source.length() > limit) {
            throw new IllegalArgumentException(String.format("%s%s%s", field, " length limit is ", limit));
        }
    }
    
    static void validateSize(final int value, final String field, final int limit) {
        if (value < 0 || value > limit) {
            throw new IllegalArgumentException(String.format("%s%s", field, " value is invalid"));
        }
    }
    
    static boolean isEmpty(final CharSequence source) {
        return source == null || source.length() == 0;
    }
    
    static boolean validateRegistrationId(final String registrationId) {
        return !isEmpty(registrationId) && (Validate.PATTERN_REGISTRATION_ID.matcher(registrationId).matches() || Validate.PATTERN_OBJECT_ID.matcher(registrationId).matches());
    }
    
    static boolean validateErrorCode(final String errorCode) {
        return !isEmpty(errorCode) && Validate.PATTERN_ERROR_CODE.matcher(errorCode).matches();
    }
    
    static void validatePictureType(final File file) {
        if (file == null || !Validate.FILE_SUFFIX_IMAGE.matcher(file.getName().toUpperCase()).matches()) {
            throw new IllegalArgumentException("picture format must be PNG/JPEG/JPG");
        }
    }
    
    static void validateFileMaxSize(final File file, final long maxSize) {
        if (file == null || file.length() > maxSize) {
            throw new IllegalArgumentException(String.format("%s%s%s", " file Size limit is", maxSize / 1204L, "KB"));
        }
    }
    
    static void validateStyle(final Integer style) {
        if (style < 1 || style > 3) {
            throw new IllegalArgumentException(String.format("%s%s", " notification style can not choose ", style));
        }
    }
    
    static {
        PATTERN_OBJECT_ID = Pattern.compile("[0-9a-fA-F]{24}");
        PATTERN_REGISTRATION_ID = Pattern.compile("((([0-9A-Za-z]{2,10})_)?([A-Z]{2}|EUEX)_)?[0-9a-fA-F]{32}");
        PATTERN_ERROR_CODE = Pattern.compile("[0-9]+");
        FILE_SUFFIX_IMAGE = Pattern.compile("[^*]+\\.(JPEG|JPG|PNG|)$");
    }
}
