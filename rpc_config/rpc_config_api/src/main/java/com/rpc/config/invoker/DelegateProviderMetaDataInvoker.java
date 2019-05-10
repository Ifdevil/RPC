package com.rpc.config.invoker;

import com.rpc.common.URL;
import com.rpc.config.ServiceConfig;
import com.rpc.rpc.Invocation;
import com.rpc.rpc.Invoker;
import com.rpc.rpc.Result;

public class DelegateProviderMetaDataInvoker<T> implements Invoker<T> {

    protected final Invoker<T> invoker;
    private ServiceConfig metadata;

    public DelegateProviderMetaDataInvoker(Invoker<T> invoker, ServiceConfig metadata) {
        this.invoker = invoker;
        this.metadata = metadata;
    }

    @Override
    public Class<T> getInterface() {
        return invoker.getInterface();
    }

    @Override
    public URL getUrl() {
        return invoker.getUrl();
    }

    @Override
    public boolean isAvailable() {
        return invoker.isAvailable();
    }

    @Override
    public Result invoke(Invocation invocation){
        return invoker.invoke(invocation);
    }

    @Override
    public void destroy() {
        invoker.destroy();
    }

    public ServiceConfig getMetadata() {
        return metadata;
    }

}
