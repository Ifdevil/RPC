package com.rpc.rpc;

import com.rpc.common.Node;

public interface Invoker<T> extends Node {

    Class<T> getInterface();

    Result invoke(Invocation invocation);
}
