package com.rpc.common;

import java.io.Serializable;

/**
 * URL统一数据模型
 */
public class URL implements Serializable {

    String protocol;
    String host;
    String port;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
