package com.rpc.rpc;

public interface Exporter<T> {

    Invoker<T> getInvoker();
}
