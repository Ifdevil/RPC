package com.rpcfly.remoting.exchange;

import com.rpcfly.remoting.Server;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * 交换层服务器
 */
public interface ExchangeServer extends Server {

    /**
     * 获取交换信息channel
     * @return
     */
    Collection<ExchangeChannel> getExchangeChannels();

    /**
     * 根据remoteAddress获取ExchangeChannel
     * @param remoteAddress
     * @return
     */
    ExchangeChannel getExchangeChannel(InetSocketAddress remoteAddress);
}
