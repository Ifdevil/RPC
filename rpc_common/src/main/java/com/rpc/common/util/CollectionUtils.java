package com.rpc.common.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {


    public static boolean isEmptyMap(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNotEmptyMap(Map map) {
        return !isEmptyMap(map);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
