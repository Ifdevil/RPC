package com.rpcfly.remoting.exchange;

import com.rpcfly.remoting.RemotingException;

public interface ResponseFuture {

    /**
     * 获取结果
     * @return
     * @throws RemotingException
     */
    Object get() throws RemotingException;

    /**
     * 定时获取结果
     * @param timeoutInMillis
     * @return
     * @throws RemotingException
     */
    Object get(int timeoutInMillis) throws RemotingException;

    /**
     * 设置回调
     * @param callback
     */
    void setCallback(ResponseCallback callback);
}
