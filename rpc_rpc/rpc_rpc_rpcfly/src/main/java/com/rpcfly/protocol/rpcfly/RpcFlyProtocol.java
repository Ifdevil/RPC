package com.rpcfly.protocol.rpcfly;

import com.rpc.common.Constants;
import com.rpc.common.URL;
import com.rpc.common.URLBuilder;
import com.rpc.rpc.*;
import com.rpc.rpc.protocol.AbstractProtocol;
import com.rpc.rpc.support.ProtocolUtils;
import com.rpcfly.remoting.Channel;
import com.rpcfly.remoting.RemotingException;
import com.rpcfly.remoting.exchange.ExchangeChannel;
import com.rpcfly.remoting.exchange.ExchangeHandler;
import com.rpcfly.remoting.exchange.ExchangeServer;
import com.rpcfly.remoting.exchange.Exchangers;
import com.rpcfly.remoting.exchange.support.header.ExchangeHandlerAdapter;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class RpcFlyProtocol extends AbstractProtocol {

    public static final String NAME = "rpcfly";
    public static final int DEFAULT_PORT = 20880;

    /**
     * 通信服务器集合
     * <host:port,Exchanger>  key: 服务器地址。格式为：host:port
     */
    private final Map<String, ExchangeServer> serverMap = new ConcurrentHashMap<>();

    /**
     * Exchange层请求处理
     */
    private ExchangeHandler requestHandler = new ExchangeHandlerAdapter(){
        //重写reply方法处理request
        @Override
        public CompletableFuture<Object> reply(ExchangeChannel channel, Object message) throws RemotingException{

            if (!(message instanceof Invocation)) {
                throw new RemotingException(channel, "Unsupported request: "
                        + (message == null ? null : (message.getClass().getName() + ": " + message))
                        + ", channel: consumer: " + channel.getRemoteAddress() + " --> provider: " + channel.getLocalAddress());
            }
            Invocation inv = (Invocation) message;
            Invoker<?> invoker = getInvoker(channel,inv);

            Result result = invoker.invoke(inv);
            return CompletableFuture.completedFuture(result);
        }
    };

    /**
     * 根据参数和channel 获取对应的服务exporter,返回invoker
     * @param channel
     * @param inv
     * @return
     */
    Invoker<?> getInvoker(Channel channel,Invocation inv){

        int port = channel.getLocalAddress().getPort();
        String path = inv.getAttachments().get(Constants.PATH_KEY);
        String serviceKey = serviceKey(port, path);
        RpcFlyExporter exporter = (RpcFlyExporter) serverMap.get(serviceKey);

        return exporter.getInvoker();
    }

    /**
     * 获取服务key
     * @param port
     * @param serviceName
     * @return
     */
    protected static String serviceKey(int port, String serviceName){
        return ProtocolUtils.serviceKey(port,serviceName,null);
    }


    @Override
    public int getDefaultPort() {
        return DEFAULT_PORT;
    }

    /**
     * 暴露服务
     * @param invoker
     * @param <T>
     * @return
     * @throws RpcException
     */
    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        URL url = invoker.getUrl();
        // 服务器关键字 eg: com.npz.test.TestInterface2:20880
        String key = serviceKey(url);

        RpcFlyExporter<T> exporter = new RpcFlyExporter<>(invoker,key,exporterMap);
        exporterMap.put(key,exporter);

        return null;
    }

    // 开启服务
    private void openServe(URL url){
        //find serve
        String key = url.getAddress();
        ExchangeServer server = serverMap.get(key);
        if(server==null){
            synchronized (this){
                server = serverMap.get(key);
                if(server==null){
                    // 创建服务器实例
                    serverMap.put(key, createServer(url));
                }
            }
        }
    }

    // 创建服务实例
    private ExchangeServer createServer(URL url) {
        url = URLBuilder.from(url)
                .addParameterIfAbsent(Constants.HEARTBEAT_KEY, String.valueOf(Constants.DEFAULT_HEARTBEAT))
                .addParameterIfAbsent(Constants.CODEC_KEY,RpcFlyCodec.NAME)
                .build();
        // 获取 server 参数，默认为 netty
        String str = url.getParameter(Constants.SERVER_KEY, Constants.DEFAULT_REMOTING_SERVER);

        ExchangeServer server;
        // todo
        try {
            //创建服务实例并启动
            server = Exchangers.bind(url, requestHandler);
        } catch (RemotingException e) {
            throw new RpcException("Fail to start server(url: " + url + ") " + e.getMessage(), e);
        }
        return server;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    @Override
    public void destroy() {

    }
}
