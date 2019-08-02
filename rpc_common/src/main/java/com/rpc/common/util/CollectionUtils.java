package com.rpc.common.util;

import java.util.Collection;
import java.util.HashMap;
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

    /**
     * 字符转map
     * @param pairs
     * @return
     */
    public static Map<String, String> toStringMap(String... pairs) {
        Map<String, String> parameters = new HashMap<>();
        if (pairs.length > 0) {
            if (pairs.length % 2 != 0) {
                throw new IllegalArgumentException("pairs must be even.");
            }
            for (int i = 0; i < pairs.length; i = i + 2) {
                parameters.put(pairs[i], pairs[i + 1]);
            }
        }
        return parameters;
    }
}
