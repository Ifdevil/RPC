package com.rpc.rpc.protocol;

import com.rpc.rpc.Exporter;
import com.rpc.rpc.Invoker;
import org.apache.log4j.Logger;

/**
 * 导出服务实体抽象类
 * AbstractExporter
 * @param <T>
 */
public abstract class AbstractExporter<T> implements Exporter<T> {

    protected static final Logger logger = Logger.getLogger(AbstractExporter.class);

    private final Invoker<T> invoker;

    private volatile boolean unexported = false;

    public AbstractExporter(Invoker<T> invoker) {
        if (invoker == null) {
            throw new IllegalStateException("service invoker == null");
        }
        if (invoker.getInterface() == null) {
            throw new IllegalStateException("service type == null");
        }
        if (invoker.getUrl() == null) {
            throw new IllegalStateException("service url == null");
        }
        this.invoker = invoker;
    }

    @Override
    public Invoker<T> getInvoker() {
        return invoker;
    }

    /**
     * 根据unexported判断，销毁暴露的服务，销毁invoker
     */
    @Override
    public void unexport() {
        if (unexported) {
            return;
        }
        unexported = true;
        getInvoker().destroy();
    }
}
