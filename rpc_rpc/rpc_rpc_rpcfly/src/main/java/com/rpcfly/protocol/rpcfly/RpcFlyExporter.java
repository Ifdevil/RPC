package com.rpcfly.protocol.rpcfly;

import com.rpc.rpc.Exporter;
import com.rpc.rpc.Invoker;
import com.rpc.rpc.protocol.AbstractExporter;

import java.util.Map;

public class RpcFlyExporter<T> extends AbstractExporter {

    private final String key;

    private final Map<String, Exporter<?>> exporterMap;

    public RpcFlyExporter(Invoker<T> invoker, String key, Map<String,Exporter<?>> exporterMap){
        super(invoker);
        this.key = key;
        this.exporterMap = exporterMap;
    }

    /**
     * 删除暴露的服务
     */
    @Override
    public void unexport() {
        super.unexport();
        exporterMap.remove(key);
    }
}
