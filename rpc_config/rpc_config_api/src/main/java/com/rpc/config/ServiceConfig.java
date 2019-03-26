package com.rpc.config;


import com.rpc.registry.Registry;

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
    private Class<T> interfaceClass;
    //服务接口名称
    private String interfaceName;
    //服务实现引用
    private T ref;


    public ServiceConfig(T ref){
        this.ref = ref;
    }

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

    public Class<T> getInterfaceClass() {
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
