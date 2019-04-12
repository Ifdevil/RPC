package com.rpc.common.util;

import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern INT_PATTERN = Pattern.compile(("\\d+$"));

    public static boolean isInteger(String str) {
        return isNotEmpty(str) && INT_PATTERN.matcher(str).matches();
    }

    public static int parseInteger(String str) {
        return isInteger(str) ? Integer.parseInt(str) : 0;
    }


    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    /**
     * 测空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str==null || str.length()==0;
    }

    /**
     * 切割名称 StringUtils---> string.utils
     * @param camelName
     * @param split
     * @return
     */
    public static String camelToSplitName(String camelName, String split) {
        if (isEmpty(camelName)) {
            return camelName;
        }
        StringBuilder buf = null;
        for (int i = 0; i < camelName.length(); i++) {
            char ch = camelName.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                if (buf == null) {
                    buf = new StringBuilder();
                    if (i > 0) {
                        buf.append(camelName.substring(0, i));
                    }
                }
                if (i > 0) {
                    buf.append(split);
                }
                buf.append(Character.toLowerCase(ch));
            } else if (buf != null) {
                buf.append(ch);
            }
        }
        return buf == null ? camelName : buf.toString();
    }
}
