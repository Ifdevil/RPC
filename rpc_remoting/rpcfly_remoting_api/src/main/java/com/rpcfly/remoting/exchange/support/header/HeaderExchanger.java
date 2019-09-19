package com.rpcfly.remoting.exchange.support.header;

import com.rpc.common.URL;
import com.rpcfly.remoting.exchange.ExchangeHandler;
import com.rpcfly.remoting.exchange.ExchangeServer;
import com.rpcfly.remoting.exchange.Exchanger;

public class HeaderExchanger implements Exchanger {

    @Override
    public ExchangeServer bind(URL url, ExchangeHandler exchangeHandler) {
        // Transport层开始根据url和handler创建对应的服务器实例---默认使用netty

        // Todo
        return new HeaderExchangeServer();
    }
}
