package com.rpc.rpc.proxy;

import com.rpc.rpc.Invoker;
import com.rpc.rpc.ProxyFactory;

public abstract class AbstractProxyFactory implements ProxyFactory {

    public abstract <T> T getProxy(Invoker<T> invoker,Class<?>[] types);

}
