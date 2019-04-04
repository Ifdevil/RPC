package com.rpc.config;

import com.rpc.common.util.ClassHelper;
import com.rpc.common.util.StringUtils;
import com.rpc.config.supports.Parameter;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Map;

public abstract class AbstractConfig {

    protected static final Logger logger = Logger.getLogger(AbstractConfig.class);


    protected static void appendParameters(Map<String, String> parameters, Object config) {
        appendParameters(parameters, config, null);
    }

    /**
     * 放置参数到map中
     * @param parameters
     * @param config
     * @param prefix
     */
    protected static void appendParameters(Map<String, String> parameters, Object config, String prefix) {
        if (config == null) {
            return;
        }
        Method[] methods = config.getClass().getMethods();
        for (Method method : methods) {
            try {
                String name = method.getName();
                if (ClassHelper.isGetter(method)) {
                    Parameter parameter = method.getAnnotation(Parameter.class);
                    String key;
                    if(parameter != null && parameter.key().length()>0){
                        key = parameter.key();
                    }else {
                        key = calculatePropertyFromGetter(name);
                    }
                    Object value = method.invoke(config);
                    String str = String.valueOf(value).trim();
                    if (value != null && str.length() > 0) {
                        if (prefix != null && prefix.length() > 0) {
                            key = prefix + "." + key;
                        }
                        parameters.put(key, str);
                    }
                }
            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }
    private static String calculatePropertyFromGetter(String name) {
        int i = name.startsWith("get") ? 3 : 2;
        return StringUtils.camelToSplitName(name.substring(i, i + 1).toLowerCase() + name.substring(i + 1), ".");
    }
}
