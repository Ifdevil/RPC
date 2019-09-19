package com.rpcfly.remoting.exchange;

import com.rpc.common.Constants;
import com.rpc.common.URL;
import com.rpcfly.remoting.RemotingException;
import com.rpcfly.remoting.remotingbean.RemotingSingleton;

/**
 * 数据交换层门面类
 */
public class Exchangers {

    private Exchangers(){}

    /**
     * 返回 ExchangeServer
     * @param url
     * @param handler
     * @return
     */
    public static ExchangeServer bind(URL url,ExchangeHandler handler) throws RemotingException {
        if(url == null){
            throw new IllegalArgumentException("url == null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        url = url.addParameterIfAbsent(Constants.CODEC_KEY, "exchange");
        return getExchanger().bind(url,handler);
    }

    /**
     * 返回 Exchanger
     * @return
     */
    public static Exchanger getExchanger(){
        return RemotingSingleton.getExchanger();
    }
}
