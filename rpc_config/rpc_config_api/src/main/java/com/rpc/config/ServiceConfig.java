package com.rpc.config;

public class ServiceConfig<T> extends AbstractConfig {


    private String registry;
    private Class<T> interfaceClass;
    private String interfaceName;
}
