package com.rpc.rpc;

import com.rpc.common.URL;

public interface Protocol {

    int getDefaultPort();

    <T> Exporter<T> export(Invoker<T> invoker) throws RpcException;

    <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException;

    void destroy();

}
