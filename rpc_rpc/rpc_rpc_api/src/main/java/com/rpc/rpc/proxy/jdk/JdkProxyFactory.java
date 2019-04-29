package com.rpc.rpc.proxy.jdk;

import com.rpc.common.URL;
import com.rpc.rpc.Invoker;
import com.rpc.rpc.RpcException;
import com.rpc.rpc.proxy.AbstractProxyFactory;
import com.rpc.rpc.proxy.InvokerInvocationHandler;

import java.lang.reflect.Proxy;

public class JdkProxyFactory extends AbstractProxyFactory {


    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] types) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),types,new InvokerInvocationHandler(invoker));
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException {
        return null;
    }
}
