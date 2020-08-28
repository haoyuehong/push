// 
// Decompiled by Procyon v0.5.36
// 

package com.vivo.push.sdk.server;

import java.util.List;
import com.vivo.push.sdk.tag.TagMessage;
import java.util.Collection;
import com.vivo.push.sdk.notofication.TargetMessage;
import com.vivo.push.sdk.common.Constants;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import com.vivo.push.sdk.common.ExceptionStatusEnum;
import com.vivo.push.sdk.notofication.Message;

public class Validation
{
    public static void validatePublicMessage(final Message publicMessage) {
        if (isBlank(publicMessage.getRequestId())) {
            errorDisplay(ExceptionStatusEnum.REQUEST_ID_EMPTY);
        }
        if (publicMessage.getRequestId().length() > 64) {
            errorDisplay(ExceptionStatusEnum.REQUEST_ID_LENGTH_INVALID);
        }
        if (isBlank(publicMessage.getTitle())) {
            errorDisplay(ExceptionStatusEnum.TITLE_EMPTY);
        }
        if (caculateCharSize(publicMessage.getTitle()) > 40) {
            errorDisplay(ExceptionStatusEnum.TITLE_LENGTH_INVALID);
        }
        if (isBlank(publicMessage.getContent())) {
            errorDisplay(ExceptionStatusEnum.CONTENT_EMPTY);
        }
        if (caculateCharSize(publicMessage.getContent()) > 100) {
            errorDisplay(ExceptionStatusEnum.CONTENT_LENGTH_INVALID);
        }
        if (publicMessage.getNotifyType() == 0 || publicMessage.getNotifyType() < 1 || publicMessage.getNotifyType() > 4) {
            errorDisplay(ExceptionStatusEnum.NOTIFY_TYPE_INVALID);
        }
        if (publicMessage.getSkipType() == 0 || publicMessage.getSkipType() < 1 || publicMessage.getSkipType() > 4) {
            errorDisplay(ExceptionStatusEnum.SKIP_TYPE_INVALID);
        }
        if (publicMessage.getSkipType() == 2) {
            if (isBlank(publicMessage.getSkipContent())) {
                errorDisplay(ExceptionStatusEnum.SKIP_TYPE_2_SKIP_CONTENT_EMPTY);
            }
            if (publicMessage.getSkipContent().length() > 1000) {
                errorDisplay(ExceptionStatusEnum.SKIP_TYPE_2_SKIP_CONTENT_LENGTH_INVALID);
            }
        }
        if (publicMessage.getSkipType() == 3) {
            if (isBlank(publicMessage.getSkipContent())) {
                errorDisplay(ExceptionStatusEnum.SKIP_TYPE_3_SKIP_CONTENT_EMPTY);
            }
            if (publicMessage.getSkipContent().length() > 1024) {
                errorDisplay(ExceptionStatusEnum.SKIP_TYPE_3_SKIP_CONTENT_LENGTH_INVALID);
            }
        }
        if (publicMessage.getSkipType() == 4) {
            if (isBlank(publicMessage.getSkipContent())) {
                errorDisplay(ExceptionStatusEnum.SKIP_TYPE_4_SKIP_CONTENT_EMPTY);
            }
            if (publicMessage.getSkipContent().length() > 1024) {
                errorDisplay(ExceptionStatusEnum.SKIP_TYPE_4_SKIP_CONTENT_LENGTH_INVALID);
            }
        }
        if (publicMessage.getNetworkType() != -1 && publicMessage.getNetworkType() != 1) {
            errorDisplay(ExceptionStatusEnum.NETWORK_TYPE_INVALID);
        }
        if (publicMessage.getClientCustomMap() != null) {
            final Set<Map.Entry<String, String>> set = publicMessage.getClientCustomMap().entrySet();
            if (set.size() > 10) {
                errorDisplay(ExceptionStatusEnum.CLIENT_CUSTOM_MAP_SIZE_INVALID);
            }
            final StringBuilder sb = new StringBuilder();
            for (final Map.Entry<String, String> entry : set) {
                if (entry.getKey() == null || entry.getKey().length() > 1024 || entry.getValue() == null || entry.getValue().length() > 1024) {
                    errorDisplay(ExceptionStatusEnum.CLIENT_CUSTOM_MAP_INVALID);
                }
                sb.append(entry.getKey()).append(entry.getValue());
            }
            if (sb != null && sb.toString().length() > 1024) {
                errorDisplay(ExceptionStatusEnum.CLIENT_CUSTOM_MAP_INVALID);
            }
        }
        if (publicMessage.getClassification() < 0 || publicMessage.getClassification() > 1) {
            errorDisplay(ExceptionStatusEnum.CLASSIFICATION_ERROR);
        }
    }
    
    protected static void validateSingleMessage(final Message singleMessage) {
        if (isBlank(singleMessage.getAlias()) && isBlank(singleMessage.getRegId())) {
            errorDisplay(ExceptionStatusEnum.ALIAS_AND_REGID_EMPTY);
        }
        if (isBlank(singleMessage.getRegId()) && !isBlank(singleMessage.getAlias()) && singleMessage.getAlias().length() > 40) {
            errorDisplay(ExceptionStatusEnum.ALIAS_LENGTH_INVALID);
        }
        if (!isBlank(singleMessage.getRegId()) && singleMessage.getRegId().length() != 23) {
            errorDisplay(ExceptionStatusEnum.REGID_INVALID);
        }
        if (!isEmpty(singleMessage.getExtra().get("callback")) && singleMessage.getExtra().get("callback").length() > 128) {
            errorDisplay(ExceptionStatusEnum.EXTRA_CALLBACK_LENGTH_INVALID);
        }
        if (!isEmpty(singleMessage.getExtra().get("callback.param")) && singleMessage.getExtra().get("callback.param").length() > 64) {
            errorDisplay(ExceptionStatusEnum.EXTRA_CALLBACKPARAM_LENGTH_INVALID);
        }
        if (singleMessage.getTimeToLive() != 0 && (singleMessage.getTimeToLive() > Constants.MAX_TIME_TO_LIVE || singleMessage.getTimeToLive() < Constants.SINGLE_MIN_TIME_TO_LIVE)) {
            errorDisplay(ExceptionStatusEnum.TIME_TO_LIVE_INVALID);
        }
    }
    
    protected static void validateLiveTime(final Message listMessage) {
        if (listMessage.getTimeToLive() != 0 && (listMessage.getTimeToLive() > Constants.MAX_TIME_TO_LIVE || listMessage.getTimeToLive() < Constants.MIN_TIME_TO_LIVE)) {
            errorDisplay(ExceptionStatusEnum.TIME_TO_LIVE_INVALID);
        }
    }
    
    public static void validateTargetMessage(final TargetMessage targetMessage) {
        if (isBlank(targetMessage.getRequestId())) {
            errorDisplay(ExceptionStatusEnum.REQUEST_ID_EMPTY);
        }
        if (targetMessage.getRequestId().length() > 64) {
            errorDisplay(ExceptionStatusEnum.REQUEST_ID_LENGTH_INVALID);
        }
        if (isBlank(targetMessage.getTaskId())) {
            errorDisplay(ExceptionStatusEnum.TASK_ID_EMPTY);
        }
        if (isEmpty(targetMessage.getRegIds()) && isEmpty(targetMessage.getAliases())) {
            errorDisplay(ExceptionStatusEnum.ALIASES_AND_REGIDS_EMPTY);
        }
        if (!isEmpty(targetMessage.getAliases())) {
            if (targetMessage.getAliases().size() > 1000 || targetMessage.getAliases().size() < 2) {
                errorDisplay(ExceptionStatusEnum.ALIASES_SIZE_INVALID);
            }
            for (final String alias : targetMessage.getAliases()) {
                if (alias.length() > 40) {
                    errorDisplay(ExceptionStatusEnum.ALIAS_LENGTH_INVALID);
                }
            }
        }
        if (!isEmpty(targetMessage.getRegIds())) {
            if (targetMessage.getRegIds().size() > 1000 || targetMessage.getRegIds().size() < 2) {
                errorDisplay(ExceptionStatusEnum.REGIDS_SIZE_INVALID);
            }
            for (final String regId : targetMessage.getRegIds()) {
                if (regId.length() != 23) {
                    errorDisplay(ExceptionStatusEnum.REGID_INVALID);
                }
            }
        }
    }
    
    protected static void validateTaskIds(final Set<String> taskIds) {
        if (isEmpty(taskIds)) {
            errorDisplay(ExceptionStatusEnum.TASK_IDS_EMPTY);
        }
        else if (taskIds.size() > 100) {
            errorDisplay(ExceptionStatusEnum.TASK_IDS_SIZE_INVALID);
        }
    }
    
    protected static void validateTagName(final String name) {
        if (isBlank(name)) {
            errorDisplay(ExceptionStatusEnum.TAG_NAME_EMPTY);
        }
    }
    
    protected static void validateTagMember(final TagMessage tagMessage) {
        if (isBlank(tagMessage.getName())) {
            errorDisplay(ExceptionStatusEnum.TAG_NAME_EMPTY);
        }
        if (tagMessage.getType() <= 0 || tagMessage.getType() > 2) {
            errorDisplay(ExceptionStatusEnum.TYPE_INVALID);
        }
        if (tagMessage.getIds() == null) {
            errorDisplay(ExceptionStatusEnum.IDS_EMPTY);
        }
        if (tagMessage.getIds().size() > 1000) {
            errorDisplay(ExceptionStatusEnum.IDS_OVER_LIMIT);
        }
    }
    
    protected static void validateTagGoupType(final int type) {
        if (type != 1 && type != 2) {
            errorDisplay(ExceptionStatusEnum.TYPE_INVALID);
        }
    }
    
    protected static void validateTagExpression(final Map<String, List<String>> tagExpression) {
        if (tagExpression.get("orTags") == null && tagExpression.get("andTags") == null) {
            throw new IllegalArgumentException("\u8868\u73b0\u7ec4\u5408\u8868\u8fbe\u5f0f\u9519\u8bef\uff0c\u5e94\u6709\u201cand\u201d\u6216\u8005\u201cor\u201d\u6807\u7b7e\u7ec4\u5408");
        }
    }
    
    private static int caculateCharSize(final String str) {
        if (str == null) {
            return 0;
        }
        int chineseCharSize = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (isChinese(str.charAt(i))) {
                ++chineseCharSize;
            }
        }
        return str.length() + chineseCharSize;
    }
    
    private static boolean isChinese(final char c) {
        final Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }
    
    static <T> T nonNull(final T argument) {
        if (argument == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        return argument;
    }
    
    private static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    
    private static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    
    private static boolean isBlank(final CharSequence cs) {
        final int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }
    
    private static void errorDisplay(final ExceptionStatusEnum statusEnum) {
        throw new IllegalArgumentException("ParameterError: " + statusEnum.getCode() + ", \u201c" + statusEnum.getMessage() + "\u201d");
    }
}
