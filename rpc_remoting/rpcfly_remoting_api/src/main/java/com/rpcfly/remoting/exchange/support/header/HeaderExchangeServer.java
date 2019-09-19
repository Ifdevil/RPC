package com.rpcfly.remoting.exchange.support.header;

import com.rpc.common.URL;
import com.rpcfly.remoting.Channel;
import com.rpcfly.remoting.ChannelHandler;
import com.rpcfly.remoting.exchange.ExchangeChannel;
import com.rpcfly.remoting.exchange.ExchangeServer;

import java.net.InetSocketAddress;
import java.util.Collection;

public class HeaderExchangeServer implements ExchangeServer {



    public HeaderExchangeServer(){

    }
    @Override
    public Collection<ExchangeChannel> getExchangeChannels() {
        return null;
    }

    @Override
    public ExchangeChannel getExchangeChannel(InetSocketAddress remoteAddress) {
        return null;
    }

    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public Collection<Channel> getChannels() {
        return null;
    }

    @Override
    public Channel getChannel(InetSocketAddress remoteAddress) {
        return null;
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public ChannelHandler getChannelHandler() {
        return null;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void close(int timeout) {

    }
}
