package com.rpc.rpc.proxy.jdk;

import com.rpc.common.URL;
import com.rpc.rpc.Invoker;
import com.rpc.rpc.RpcException;
import com.rpc.rpc.proxy.AbstractProxyFactory;
import com.rpc.rpc.proxy.AbstractProxyInvoker;
import com.rpc.rpc.proxy.InvokerInvocationHandler;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 */
public class JdkProxyFactory extends AbstractProxyFactory {

    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] types) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),types,new InvokerInvocationHandler(invoker));
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException {
        return new AbstractProxyInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(Object proxy, String methodName,
                                      Class[] parameterTypes,
                                      Object[] arguments) throws Throwable {
                Method method = proxy.getClass().getMethod(methodName, parameterTypes);
                return method.invoke(proxy, arguments);
            }
        };
    }
}
