package com.rpc.config;


import com.rpc.registry.Registry;

public class ServiceConfig<T> extends AbstractConfig {

    private Registry registry;
    private Class<T> interfaceClass;
    private String interfaceName;

    public ServiceConfig(Registry registry){
        this.registry = registry;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
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
