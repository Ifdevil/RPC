package com.rpc.rpc;

public interface Invoker<T> {

    Class<T> getInterface();
}
