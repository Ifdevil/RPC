package com.rpcfly.remoting;

import java.net.InetSocketAddress;

public interface Channel extends Endpoint {

    InetSocketAddress getRemoteAddress();
}
