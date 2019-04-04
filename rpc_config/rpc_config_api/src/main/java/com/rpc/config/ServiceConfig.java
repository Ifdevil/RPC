package com.rpc.config;

import com.rpc.common.Constants;
import com.rpc.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务配置入口
 * @param <T>
 */
public class ServiceConfig<T> extends AbstractServiceConfig {

    //应用配置
    private ApplicationConfig applicationConfig;
    //注册中心配置
    private RegistryConfig registryConfig;
    //传输协议
    private ProtocolConfig protocolConfig;
    //服务接口
    private Class<?> interfaceClass;
    //服务接口名称
    private String interfaceName;
    //服务实现引用
    private T ref;

    // ======================== constructor ========================
    public ServiceConfig(){}
    public ServiceConfig(T ref){
        this.ref = ref;
    }




    // ====================  method  ===============================

    /**
     * 暴露服务
     */
    public void export(){
        //完善配置信息,使用默认信息
        completeCompoundConfigs();
        //校验配置
        checkAndUpdateConfigs();
        //暴露服务
        doExportUrls();
    }
    private void completeCompoundConfigs(){

    }

    private void checkAndUpdateConfigs(){
        checkApplication();
        checkProtocol();
        checkRegistry();
        if (StringUtils.isEmpty(interfaceName)) {
            throw new IllegalStateException("serviceConfig interface not allow null!");
        }
        try {
            interfaceClass = Class.forName(interfaceName,true,Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void checkApplication(){
        if(applicationConfig.isValid()){
            throw new IllegalStateException("No application config found");
        }
    }

    private void checkProtocol(){
        if(protocolConfig.isValid()){
            throw new IllegalStateException("No protocol config found or it's not a valid config! " +
                    "The protocol config is: " + protocolConfig);
        }
    }

    private void checkRegistry(){
        if (!registryConfig.isValid()) {
            throw new IllegalStateException("No registry config found or it's not a valid config! " +
                    "The registry config is: " + registryConfig);
        }
    }

    private void doExportUrls(){
        doExportUrlsFor1Protocol(protocolConfig, registryConfig);
    }

    private void doExportUrlsFor1Protocol(ProtocolConfig protocolConfig,RegistryConfig registryConfig){
        String name = protocolConfig.getName();
        if(StringUtils.isEmpty(name)){
            name = Constants.RPC_FLY;
        }
        Map<String, String> map = new HashMap<String, String>();
        appendRuntimeParameters(map);
        appendParameters(map, applicationConfig);
        String[] mehtods = getMehods(interfaceClass);
        if(mehtods.length == 0){
            logger.warn("No method found in service interface " + interfaceClass.getName());
            map.put(Constants.METHODS_KEY, Constants.ANY_VALUE);
        }

        // export service


    }












    // ======================getter and setter =======================

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
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

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
