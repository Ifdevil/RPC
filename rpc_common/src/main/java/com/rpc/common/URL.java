package com.rpc.common;

import com.rpc.common.util.ArrayUtils;
import com.rpc.common.util.CollectionUtils;
import com.rpc.common.util.NetUtils;
import com.rpc.common.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * URL统一数据模型
 */
public class URL implements Serializable {

    //协议
    private final String protocol;
    //地址
    private final String host;
    //端口
    private final int port;
    //名称
    private final String path;
    //参数
    private final Map<String, String> parameters;

    private volatile transient String ip;

    public URL (String protocol, String host, int port, String path, Map<String,String> parameters){
        this.protocol = protocol;
        this.host = host;
        this.port = (port < 0 ? 0 : port);
        while (path != null && path.startsWith("/")) {
            path = path.substring(1);
        }
        this.path = path;
        //返回有序映射--只读
        this.parameters = Collections.unmodifiableMap(parameters);;
    }

    @Override
    public String toString(){
        return buildString(true,true,true);
    }
    private String buildString(boolean appendParameter, boolean useIP, boolean useService, String... parameters){
        StringBuilder buf = new StringBuilder();
        if (StringUtils.isNotEmpty(protocol)) {
            buf.append(protocol);
            buf.append("://");
        }
        String host;
        if(useIP){
            host = getIp();
        }else{
            host = getHost();
        }
        if(host!=null && host.length()>0){
            buf.append(host);
            if(port>0){
                buf.append(":");
                buf.append(port);
            }
        }
        String path;
        if (useService) {
            path = getServiceKey();
        } else {
            path = getPath();
        }
        if (path != null && path.length() > 0) {
            buf.append("/");
            buf.append(path);
        }
        if (appendParameter) {
            buildParameters(buf, true, parameters);
        }
        return buf.toString();
    }
    //拼接参数
    private void buildParameters(StringBuilder buf, boolean concat, String[] parameters) {
        if (CollectionUtils.isNotEmptyMap(getParameters())) {
            List<String> includes = (ArrayUtils.isEmpty(parameters) ? null : Arrays.asList(parameters));
            boolean first = true;
            for (Map.Entry<String, String> entry : new TreeMap<>(getParameters()).entrySet()) {
                if (entry.getKey() != null && entry.getKey().length() > 0
                        && (includes == null || includes.contains(entry.getKey()))) {
                    if (first) {
                        if (concat) {
                            buf.append("?");
                        }
                        first = false;
                    } else {
                        buf.append("&");
                    }
                    buf.append(entry.getKey());
                    buf.append("=");
                    buf.append(entry.getValue() == null ? "" : entry.getValue().trim());
                }
            }
        }
    }

    public String getIp() {
        if (ip == null) {
            ip = NetUtils.getIpByHost(host);
        }
        return ip;
    }
    public String getServiceKey() {
        String inf = getServiceInterface();
        return inf;
    }
    public String getServiceInterface(){
        return getParameter(Constants.INTERFACE_KEY,path);
    }
    public String getParameter(String key, String defaultValue) {
        String value = getParameter(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }
    public String getParameter(String key) {
        String value = parameters.get(key);
        return value;
    }


    public String getPath() {
        return path;
    }
    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
