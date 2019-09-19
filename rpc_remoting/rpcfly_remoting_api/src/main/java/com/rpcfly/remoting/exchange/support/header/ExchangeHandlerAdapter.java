package com.rpcfly.remoting.exchange.support.header;

import com.rpcfly.remoting.RemotingException;
import com.rpcfly.remoting.exchange.ExchangeChannel;
import com.rpcfly.remoting.exchange.ExchangeHandler;

import java.util.concurrent.CompletableFuture;

public abstract class ExchangeHandlerAdapter implements ExchangeHandler {

    @Override
    public CompletableFuture<Object> reply(ExchangeChannel exchangeChannel, Object request) throws RemotingException{
        return null;
    }
}
