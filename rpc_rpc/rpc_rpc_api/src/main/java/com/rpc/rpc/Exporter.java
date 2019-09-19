package com.rpc.rpc;

/**
 * 导出服务接口
 * @param <T>
 */
public interface Exporter<T> {

    Invoker<T> getInvoker();

    void unexport();
}
