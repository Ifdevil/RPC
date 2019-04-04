package com.rpc.common.util;

import com.rpc.common.Constants;

import java.net.InetAddress;

public class NetUtils {

    // 和dubbo相比，缺少volatile
    private static InetAddress LOCAL_ADDRESS = null;

    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? Constants.LOCALHOST_VALUE : address.getHostAddress();
    }

    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        return localAddress;
    }
}
