package com.rpc.rpc.proxy;

import com.rpc.rpc.Invoker;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvokerInvocationHandler implements InvocationHandler {

    private static final Logger logger = Logger.getLogger(InvokerInvocationHandler.class);

    private final Invoker<?> invoker;

    public InvokerInvocationHandler(Invoker<?> handler) {
        this.invoker = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(invoker, args);
    }
}
