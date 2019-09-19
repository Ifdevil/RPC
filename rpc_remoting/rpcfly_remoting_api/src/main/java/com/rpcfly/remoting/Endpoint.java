package com.rpcfly.remoting;

import com.rpc.common.URL;

import java.net.InetSocketAddress;

public interface Endpoint {

    /**
     * 服务URL
     * @return
     */
    URL getUrl();

    /**
     * 服务处理器 handler
     * @return
     */
    ChannelHandler getChannelHandler();

    /**
     * 获取本地地址
     * @return
     */
    InetSocketAddress getLocalAddress();

    /**
     * close the channel.
     */
    void close();

    /**
     * Graceful close the channel.
     */
    void close(int timeout);
}
