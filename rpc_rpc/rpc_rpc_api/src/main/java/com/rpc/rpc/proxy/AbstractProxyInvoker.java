package com.rpc.rpc.proxy;

import com.rpc.common.URL;
import com.rpc.rpc.*;

/**
 * abstract invoker
 * @param <T>
 */
public abstract class AbstractProxyInvoker<T> implements Invoker<T> {

    // 被代理的服务引用
    private final T proxy;
    // 被代理的服务类
    private final Class<T> type;
    // URL
    private final URL url;

    public AbstractProxyInvoker(T proxy,Class<T> type,URL url){

        if (proxy == null) {
            throw new IllegalArgumentException("proxy == null");
        }
        if (type == null) {
            throw new IllegalArgumentException("interface == null");
        }
        if (!type.isInstance(proxy)) {
            throw new IllegalArgumentException(proxy.getClass().getName() + " not implement interface " + type);
        }

        this.proxy = proxy;
        this.type = type;
        this.url = url;
    }
    @Override
    public Class getInterface() {
        return type;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void destroy() {

    }

    // 调用方法
    @Override
    public Result invoke(Invocation invocation){
        try {
            Object obj = doInvoke(proxy,invocation.getMethodName(),invocation.getParameterTypes(),invocation.getArguments());
            return new RpcResult(obj);
        } catch (Throwable e) {
            throw new RpcException("Failed to invoke remote proxy method " + invocation.getMethodName() + " to " + getUrl() + ", cause: " + e.getMessage(), e);
        }
    }

    protected abstract Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable;
}
