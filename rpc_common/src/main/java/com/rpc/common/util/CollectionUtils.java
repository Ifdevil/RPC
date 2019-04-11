package com.rpc.common.util;

import java.util.Map;

public class CollectionUtils {


    public static boolean isEmptyMap(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNotEmptyMap(Map map) {
        return !isEmptyMap(map);
    }
}
