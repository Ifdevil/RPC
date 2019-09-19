package com.rpc.config;

import com.rpc.common.Constants;
import com.rpc.common.URL;
import com.rpc.common.util.CollectionUtils;
import com.rpc.common.util.StringUtils;
import com.rpc.config.invoker.DelegateProviderMetaDataInvoker;
import com.rpc.registry.integration.RegistryProtocol;
import com.rpc.rpc.Exporter;
import com.rpc.rpc.Invoker;
import com.rpc.rpc.Protocol;
import com.rpc.rpc.ProxyFactory;
import com.rpc.rpc.proxy.jdk.JdkProxyFactory;
import com.rpcfly.protocol.rpcfly.RpcFlyProtocol;

import java.util.*;

import static com.rpc.common.util.NetUtils.isInvalidPort;
import static com.rpc.common.util.NetUtils.getLocalHost;

/**
 * 服务配置入口
 * @param <T>
 */
public class ServiceConfig<T> extends AbstractServiceConfig {


    private static final ProxyFactory proxyFactory = new JdkProxyFactory();

    private static final Protocol protocol = new RegistryProtocol();

    /**
     * The exported services
     */
    private final List<Exporter<?>> exporters = new ArrayList<Exporter<?>>();

    //服务接口
    private Class<?> interfaceClass;
    //服务接口名称
    private String interfaceName;
    //服务名称
    private String path;
    //服务实现引用
    private T ref;
    /**
     * The config id
     */
    protected String id;



    // ======================== constructor ========================

    public ServiceConfig(){}
    public ServiceConfig(T ref){
        this.ref = ref;
    }




    // ====================  method  ===============================

    /**
     * 暴露服务
     */
    public void export(){
        //完善配置信息,使用默认信息
        completeCompoundConfigs();
        //校验配置
        checkAndUpdateConfigs();
        //暴露服务
        doExportUrls();
    }
    private void completeCompoundConfigs(){

    }
    //校验
    private void checkAndUpdateConfigs(){
        checkApplication();
        checkProtocol();
        checkRegistry();
        if (StringUtils.isEmpty(interfaceName)) {
            throw new IllegalStateException("serviceConfig interface not allow null!");
        }
        try {
            interfaceClass = Class.forName(interfaceName,true,Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
    //校验Application
    private void checkApplication(){
        if(!applicationConfig.isValid()){
            throw new IllegalStateException("No application config found");
        }
    }
    //校验Protocol
    private void checkProtocol(){
        if(!protocolConfig.isValid()){
            throw new IllegalStateException("No protocol config found or it's not a valid config! " +
                    "The protocol config is: " + protocolConfig);
        }
    }
    //校验Registry
    private void checkRegistry(){
        if (!registryConfig.isValid()) {
            throw new IllegalStateException("No registry config found or it's not a valid config! " +
                    "The registry config is: " + registryConfig);
        }
    }
    //暴露服务
    private void doExportUrls(){
        //加载所有定义的注册中心
        //格式：multicast://127.0.0.1:1234/com.rpc.registry.RegistryService?address=multicast://127.0.0.1:1234&application=Rpc-demo-test&path=com.rpc.registry.RegistryService&protocol=rpc_fly
        List<URL> registryURLs = loadRegistries(true);
        if (StringUtils.isEmpty(path)) {
            path = interfaceName;
        }
        //根据不同的协议暴露服务
        doExportUrlsFor1Protocol(protocolConfig, registryURLs);
    }

    /**
     * //根据不同的协议暴露服务
     * @param protocolConfig  协议配置
     * @param registryURLs  注册中心地址
     */
    private void doExportUrlsFor1Protocol(ProtocolConfig protocolConfig,List<URL> registryURLs){
        String name = protocolConfig.getName();
        if(StringUtils.isEmpty(name)){
            name = Constants.RPC_FLY;
        }
        Map<String, String> map = new HashMap<String, String>();
        appendRuntimeParameters(map);
        appendParameters(map, applicationConfig);
        String[] mehtods = getMehods(interfaceClass);
        if(mehtods.length == 0){
            logger.warn("No method found in service interface " + interfaceClass.getName());
            map.put(Constants.METHODS_KEY, Constants.ANY_VALUE);
        }

        // export service
        String host = this.findConfigedHosts(protocolConfig,map);// 获取host
        Integer port = this.findConfigedPorts(protocolConfig, map);// 获取端口

        URL url = new URL(name,host,port,getContextPath().map(p -> p +"/"+path).orElse(path),map);

        if(logger.isInfoEnabled()){
            logger.info("Export rpcfly service " + interfaceClass.getName() + " to url " + url);
        }
        if(CollectionUtils.isNotEmpty(registryURLs)){
            for (URL registryURL : registryURLs) {
                if (logger.isInfoEnabled()) {
                    logger.info("Register rpcfly service " + interfaceClass.getName() + " url " + url + " to registry " + registryURL);
                }
                System.out.println(registryURL.toString());
                // 为服务提供类(ref)生成 Invoker------------这里添加EXPORT_KEY为什么要URL编解码
                Invoker invoker = proxyFactory.getInvoker(ref,(Class)interfaceClass,registryURL.addParameterAndEncoded(Constants.EXPORT_KEY,url.toFullString()));
                DelegateProviderMetaDataInvoker wapperInvoker = new DelegateProviderMetaDataInvoker(invoker,this);
                // 导出服务，并生成 Exporter
                Exporter<?> exporter = protocol.export(wapperInvoker);
                exporters.add(exporter);
            }

        }




    }

    //寻找配置的IP
    private String findConfigedHosts(ProtocolConfig protocolConfig,Map<String, String> map){
        boolean anyhost = true;
        String hostToBind = getLocalHost();
        map.put(Constants.BIND_IP_KEY,hostToBind);
        map.put(Constants.ANYHOST_KEY, String.valueOf(anyhost));
        return hostToBind;
    }
    //寻找配置的端口
    private Integer findConfigedPorts(ProtocolConfig protocolConfig,Map<String,String> map){
        Integer portToBind = null;
        String port = protocolConfig.getPort();
        portToBind = parsePort(port);
        map.put(Constants.BIND_PORT_KEY, String.valueOf(portToBind));
        return portToBind;
    }
    //解析端口
    private Integer parsePort(String configPort) {
        Integer port = null;
        if (configPort != null && configPort.length() > 0) {
            try {
                Integer intPort = Integer.parseInt(configPort);
                if (isInvalidPort(intPort)) {
                    throw new IllegalArgumentException("Specified invalid port from env value:" + configPort);
                }
                port = intPort;
            } catch (Exception e) {
                throw new IllegalArgumentException("Specified invalid port from env value:" + configPort);
            }
        }
        return port;
    }
    //获取服务路径
    private Optional<String> getContextPath(){
        // path
        Optional<String> opt = Optional.ofNullable(null);
        return opt;
    }













    // ======================getter and setter =======================


    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        if(interfaceClass != null && !interfaceClass.isInterface()){
            throw new IllegalStateException("The interface class " + interfaceClass + " is not a interface!");
        }
        this.interfaceClass = interfaceClass;
        setInterface(interfaceClass == null ? null : interfaceClass.getName());

    }
    public void setInterface(String interfaceName) {
        this.interfaceName = interfaceName;
        if (StringUtils.isEmpty(id)) {
            id = interfaceName;
        }
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
