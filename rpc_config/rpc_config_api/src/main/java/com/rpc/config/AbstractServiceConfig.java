package com.rpc.config;

import com.rpc.common.Constants;
import com.rpc.common.URL;
import com.rpc.common.util.ConfigUtils;
import com.rpc.common.util.UrlUtils;
import com.rpc.registry.RegistryService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务配置抽象类
 */
public abstract class AbstractServiceConfig extends AbstractConfig{

    //应用配置
    protected ApplicationConfig applicationConfig;
    //注册中心配置
    protected RegistryConfig registryConfig;
    //传输协议
    protected ProtocolConfig protocolConfig;


    static void appendRuntimeParameters(Map<String, String> map) {
        map.put(Constants.RPC_FLY_VERSION_KEY,Constants.RPC_FLY_VERSION_VALUE);
        map.put(Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        if (ConfigUtils.getPid() > 0) {
            map.put(Constants.PID_KEY, String.valueOf(ConfigUtils.getPid()));
        }
    }

    static String[] getMehods(Class<?> c){
        String[] methodstr = {};
        Method[] methods = c.getMethods();
        for (int i = 0;i<methods.length;i++){
            methodstr[i] = methods[i].getName();
        }
        return methodstr;
    }



    protected List<URL> loadRegistries(boolean provider) {
        List<URL> registryList = new ArrayList<URL>();
        if(registryConfig!=null){
            String address = registryConfig.getAddress();
            Map<String, String> map = new HashMap<String, String>();
            appendParameters(map, applicationConfig);
            appendParameters(map, registryConfig);
            map.put(Constants.PATH_KEY, RegistryService.class.getName());
            if (!map.containsKey(Constants.PROTOCOL_KEY)) {
                map.put(Constants.PROTOCOL_KEY, Constants.RPC_FLY);
            }
            List<URL> urls = UrlUtils.parseURLs(address, map);
        }

        return registryList;
    }










    // ======================getter and setter =======================

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public ProtocolConfig getProtocolConfig() {
        return protocolConfig;
    }

    public void setProtocolConfig(ProtocolConfig protocolConfig) {
        this.protocolConfig = protocolConfig;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }
}
