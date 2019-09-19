package com.rpcfly.remoting.exchange;

import com.rpcfly.remoting.Channel;
import com.rpcfly.remoting.RemotingException;

/**
 * 交换信息通道
 */
public interface ExchangeChannel extends Channel {

    ResponseFuture request(Object reqeust) throws RemotingException;

    ResponseFuture request(Object request,int timeout) throws RemotingException;

    ExchangeHandler getExchangeHandler();

    @Override
    void close(int timeout);

}
