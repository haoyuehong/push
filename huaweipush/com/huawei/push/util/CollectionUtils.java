// 
// Decompiled by Procyon v0.5.36
// 

package com.huawei.push.util;

import java.util.Map;
import java.util.Collection;

public class CollectionUtils
{
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
