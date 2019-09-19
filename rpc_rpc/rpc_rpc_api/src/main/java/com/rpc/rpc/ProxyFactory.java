package com.rpc.rpc;

import com.rpc.common.URL;

/**
 * 动态代理工厂
 */
public interface ProxyFactory {

    <T> T getProxy(Invoker<T> invoker) throws RpcException;

    <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException;

}
