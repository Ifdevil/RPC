package com.rpcfly.remoting.exchange;

import com.rpc.common.URL;
import com.rpcfly.remoting.RemotingException;

public interface Exchanger {

    ExchangeServer bind(URL url,ExchangeHandler exchangeHandler) throws RemotingException;
}
