package com.rpc.rpc.proxy;

import com.rpc.rpc.*;

public abstract class AbstractProxyFactory implements ProxyFactory {

    public <T> T getProxy(Invoker<T> invoker) throws RpcException{
        Class<?>[] interfaces = new Class[1];
        interfaces[0] = invoker.getInterface();
        return getProxy(invoker,interfaces);
    }

    public abstract <T> T getProxy(Invoker<T> invoker,Class<?>[] types);

}
