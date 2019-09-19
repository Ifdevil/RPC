package com.rpc.rpc.protocol;

import com.rpc.common.Constants;
import com.rpc.common.URL;
import com.rpc.rpc.Exporter;
import com.rpc.rpc.Protocol;
import com.rpc.rpc.support.ProtocolUtils;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractProtocol implements Protocol {

    protected static final Logger logger = Logger.getLogger(AbstractProtocol.class);
    // 已经暴露的服务集合
    protected final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<String, Exporter<?>>();

    /**
     * 生成service对应的key
     * @param url
     * @return
     */
    protected static String serviceKey(URL url) {
        int port = url.getParameter(Constants.BIND_PORT_KEY, url.getPort());
        return serviceKey(port, url.getPath(), url.getParameter(Constants.VERSION_KEY));
    }

    /**
     * 生成service对应的key
     * @param port
     * @param serviceName
     * @param serviceVersion
     * @return
     */
    protected static String serviceKey(int port, String serviceName, String serviceVersion) {
        return ProtocolUtils.serviceKey(port, serviceName, serviceVersion);
    }
}
