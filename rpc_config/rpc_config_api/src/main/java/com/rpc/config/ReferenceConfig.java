package com.rpc.config;

public class ReferenceConfig<T> {
    //应用配置
    private ApplicationConfig application;
    //服务接口名称
    private String interfaceName;
    //服务接口类
    private Class<?> interfaceClass;

    protected String id;

    public void setInterface(String interfaceName) {
        this.interfaceName = interfaceName;
        if (id == null || id.length() == 0) {
            id = interfaceName;
        }
    }
    public void setInterface(Class<?> interfaceClass) {
        if (interfaceClass != null && !interfaceClass.isInterface()) {
            throw new IllegalStateException("The interface class " + interfaceClass + " is not a interface!");
        }
        this.interfaceClass = interfaceClass;
        setInterface(interfaceClass == null ? null : interfaceClass.getName());
    }
}
