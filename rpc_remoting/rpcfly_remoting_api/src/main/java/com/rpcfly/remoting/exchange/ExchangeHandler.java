package com.rpcfly.remoting.exchange;

import com.rpcfly.remoting.RemotingException;

import java.util.concurrent.CompletableFuture;

/**
 * 交换层处理请求的handler
 */
public interface ExchangeHandler {

    /**
     * 回复
     * @param exchangeChannel
     * @param request
     * @return
     * @throws RemotingException
     */
    CompletableFuture<Object> reply(ExchangeChannel exchangeChannel,Object request) throws RemotingException;

}
