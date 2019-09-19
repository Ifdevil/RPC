package com.rpcfly.remoting;

import java.net.InetSocketAddress;
import java.util.Collection;

public interface Server extends Endpoint{

    boolean isBound();

    /**
     * 服务持有的所有channel
     * @return
     */
    Collection<Channel> getChannels();

    Channel getChannel(InetSocketAddress remoteAddress);
}
