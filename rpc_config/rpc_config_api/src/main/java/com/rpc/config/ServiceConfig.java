package com.rpc.config;

import com.rpc.registry.Registry;

public class ServiceConfig<T> extends AbstractConfig {


    private Registry registry;
    private Class<T> interfaceClass;
    private String interfaceName;
}
