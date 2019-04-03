package com.rpc.config;

import com.rpc.common.Constants;
import com.rpc.common.util.ConfigUtils;
import com.rpc.registry.Registry;

import java.util.Map;

/**
 * 服务配置抽象类
 */
public abstract class AbstractServiceConfig extends AbstractConfig{

    static void appendRuntimeParameters(Map<String, String> map) {
        map.put(Constants.RPC_FLY_VERSION_KEY,Constants.RPC_FLY_VERSION_VALUE);
        map.put(Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        if (ConfigUtils.getPid() > 0) {
            map.put(Constants.PID_KEY, String.valueOf(ConfigUtils.getPid()));
        }
    }
}
