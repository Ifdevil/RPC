package com.rpc.registry.integration;

import com.rpc.common.Constants;
import com.rpc.common.URL;
import com.rpc.rpc.Exporter;
import com.rpc.rpc.Invoker;
import com.rpc.rpc.Protocol;
import com.rpc.rpc.RpcException;
import com.rpc.rpc.protocol.InvokerWrapper;
import com.rpcfly.protocol.rpcfly.RpcFlyProtocol;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RegistryProtocol implements Protocol {


    //providerurl <--> exporter
    private final ConcurrentMap<String, ExporterChangeableWrapper<?>> bounds = new ConcurrentHashMap<>();

    private Protocol protocol = new RpcFlyProtocol();

    @Override
    public int getDefaultPort() {
        return 0;
    }

    /**
     * 流程
     *      1.根据Invoker获取服务注册中心url,服务地址url
     *      2.设置该服务的订阅监听
     *      3.暴露服务（根据不同的协议暴露）
     *      4.根据不同注册中心，注册服务
     *      5.向注册中心进行订阅 override 数据
     *      6.创建并返回 DestroyableExporter
     *
     * @param originInvoker
     * @param <T>
     * @return
     * @throws RpcException
     */
    @Override
    public <T> Exporter<T> export(Invoker<T> originInvoker) throws RpcException {
        URL registryUrl = getRegistryUrl(originInvoker);
        // url to export locally
        URL providerUrl = getProviderUrl(originInvoker);

        // 本地注册表

        // 暴露服务，启动服务
        final ExporterChangeableWrapper<T> exporter = dolocalExport(originInvoker,providerUrl);
        return null;
    }

    /**
     * 获取需要注册的URL
     * @param originInvoker
     * @return
     */
    private URL getRegistryUrl(Invoker<?> originInvoker) {
        URL registryUrl = originInvoker.getUrl();
        if(Constants.REGISTRY_PROTOCOL.equals(registryUrl.getProtocol())){
            //设置具体协议名称
            String protocol = registryUrl.getParameter(Constants.REGISTRY_PROTOCOL,Constants.DEFAULT_PROTOCOL);
            registryUrl = registryUrl.setProtocol(protocol).removeParameter(Constants.REGISTRY_KEY);
        }
        return registryUrl;
    }

    /**
     * 获取要导出的服务的URL
     * @param originInvoker
     * @return
     */
    private URL getProviderUrl(final Invoker<?> originInvoker) {
        String export = originInvoker.getUrl().getParameterAndDecoded(Constants.EXPORT_KEY);
        if (export == null || export.length() == 0) {
            throw new IllegalArgumentException("The registry export url is null! registry: " + originInvoker.getUrl());
        }
        return URL.valueOf(export);
    }

    /**
     * 暴露服务，启动服务
     * @param originInvoker
     * @param providerUrl
     * @param <T>
     * @return
     */
    private <T> ExporterChangeableWrapper<T> dolocalExport(final Invoker<T> originInvoker,URL providerUrl){
        String key = getCacheKey(originInvoker);
        return ( ExporterChangeableWrapper<T> ) bounds.computeIfAbsent(key,s -> {
            Invoker<T> invokerDelegate = new InvokerDelegate<>(originInvoker,providerUrl);
            return new ExporterChangeableWrapper<>((Exporter<T>)protocol.export(invokerDelegate),originInvoker);
        });
    }

    /**
     * 获取provider url 的字符串
     * @param originInvoker
     * @return
     */
    private String getCacheKey(final Invoker<?> originInvoker) {
        URL providerUrl = getProviderUrl(originInvoker);
        String key = providerUrl.toFullString();
        return key;
    }
    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    @Override
    public void destroy() {

    }


    private class ExporterChangeableWrapper<T> implements Exporter<T> {

        private final Invoker<T> originInvoker;
        private Exporter<T> exporter;

        public ExporterChangeableWrapper(Exporter<T> exporter, Invoker<T> originInvoker) {
            this.exporter = exporter;
            this.originInvoker = originInvoker;
        }

        @Override
        public Invoker<T> getInvoker() {
            return null;
        }

        @Override
        public void unexport() {

        }
    }

    public static class InvokerDelegate<T> extends InvokerWrapper<T> {
        private final Invoker<T> invoker;

        /**
         * @param invoker
         * @param url     invoker.getUrl return this value
         */
        public InvokerDelegate(Invoker<T> invoker, URL url) {
            super(invoker, url);
            this.invoker = invoker;
        }

        public Invoker<T> getInvoker() {
            if (invoker instanceof InvokerDelegate) {
                return ((InvokerDelegate<T>) invoker).getInvoker();
            } else {
                return invoker;
            }
        }
    }
}
