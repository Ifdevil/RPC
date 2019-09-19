package com.rpc.rpc.support;

public class ProtocolUtils {


    public static String serviceKey(int port, String serviceName, String serviceVersion){
        StringBuilder buf = new StringBuilder();
        buf.append(serviceName);
        if (serviceVersion != null && serviceVersion.length() > 0 && !"0.0.0".equals(serviceVersion)) {
            buf.append(":");
            buf.append(serviceVersion);
        }
        buf.append(":");
        buf.append(port);
        return buf.toString();
    }
}
