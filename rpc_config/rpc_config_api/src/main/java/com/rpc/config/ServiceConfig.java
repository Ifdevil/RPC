package com.rpc.config;


import com.rpc.registry.Registry;

/**
 * 服务配置入口
 * @param <T>
 */
public class ServiceConfig<T> extends AbstractServiceConfig {

    private ApplicationConfig applicationConfig;
    private RegistryConfig registryConfig;
    private ProtocolConfig protocolConfig;
    private Class<T> interfaceClass;
    private String interfaceName;
    private T ref;

    public ServiceConfig(T ref){
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
