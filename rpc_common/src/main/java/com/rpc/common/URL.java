package com.rpc.common;

import com.rpc.common.util.ArrayUtils;
import com.rpc.common.util.CollectionUtils;
import com.rpc.common.util.NetUtils;
import com.rpc.common.util.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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


    //-----------cache---------------
    private volatile transient Map<String ,Number> numbers;
    private volatile transient String full;




    //-----------method--------------

    public URL (String protocol, String host, int port, String path, Map<String,String> parameters){
        this.protocol = protocol;
        this.host = host;
        this.port = (port < 0 ? 0 : port);
        while (path != null && path.startsWith("/")) {
            path = path.substring(1);
        }
        this.path = path;
        if (parameters == null) {
            parameters = new HashMap<>();
        } else {
            parameters = new HashMap<>(parameters);
        }
        //返回有序映射--只读
        this.parameters = Collections.unmodifiableMap(parameters);;
    }

    @Override
    public String toString(){
        return buildString(true,true,true);
    }

    public String toFullString() {
        if (full != null) {
            return full;
        }
        return full = buildString(true, true);
    }
    private String buildString(boolean appendUser, boolean appendParameter, String... parameters) {
        return buildString(appendParameter, false, false, parameters);
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

    /**
     * 将String url 转化为 URL对象
     * @param url
     * @return
     */
    public static URL valueOf(String url) {
        if (url == null || (url = url.trim()).length() == 0) {
            throw new IllegalArgumentException("url == null");
        }
        String protocol = null;
        String host = null;
        int port = 0;
        String path = null;
        Map<String, String> parameters = null;
        int i = url.indexOf("?"); // separator between body and parameters
        if (i >= 0) {
            String[] parts = url.substring(i + 1).split("&");
            parameters = new HashMap<>();
            for (String part : parts) {
                part = part.trim();
                if (part.length() > 0) {
                    int j = part.indexOf('=');
                    if (j >= 0) {
                        parameters.put(part.substring(0, j), part.substring(j + 1));
                    } else {
                        parameters.put(part, part);
                    }
                }
            }
            url = url.substring(0, i);
        }
        i = url.indexOf("://");
        if (i >= 0) {
            if (i == 0) {
                throw new IllegalStateException("url missing protocol: \"" + url + "\"");
            }
            protocol = url.substring(0, i);
            url = url.substring(i + 3);
        } else {
            // case: file:/path/to/file.txt
            i = url.indexOf(":/");
            if (i >= 0) {
                if (i == 0) {
                    throw new IllegalStateException("url missing protocol: \"" + url + "\"");
                }
                protocol = url.substring(0, i);
                url = url.substring(i + 1);
            }
        }

        i = url.indexOf("/");
        if (i >= 0) {
            path = url.substring(i + 1);
            url = url.substring(0, i);
        }
        i = url.lastIndexOf(":");
        if (i >= 0 && i < url.length() - 1) {
            if (url.lastIndexOf("%") > i) {
                // ipv6 address with scope id
                // e.g. fe80:0:0:0:894:aeec:f37d:23e1%en0
                // see https://howdoesinternetwork.com/2013/ipv6-zone-id
                // ignore
            } else {
                port = Integer.parseInt(url.substring(i + 1));
                url = url.substring(0, i);
            }
        }
        if (url.length() > 0) {
            host = url;
        }
        return new URL(protocol, host, port, path, parameters);
    }

    /**
     * 添加参数
     * @param key
     * @param value
     * @return
     */
    public URL addParameterAndEncoded(String key, String value) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }
        return addParameter(key, encode(value));
    }
    public URL addParameter(String key, String value) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(value)) {
            return this;
        }
        // if value doesn't change, return immediately
        if (value.equals(getParameters().get(key))) { // value != null
            return this;
        }

        Map<String, String> map = new HashMap<>(getParameters());
        map.put(key, value);
        return new URL(protocol,host, port, path, map);
    }
    /**
     * UTF-8编码
     * @param value
     * @return
     */
    public static String encode(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    //=========== getter and setter ================

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

    public int getParameter(String key,int defaultValue){
        Number n = getNumbers().get(key);
        if(n!=null){
            return n.intValue();
        }
        String value = getParameter(key);
        if(StringUtils.isEmpty(value)){
            return defaultValue;
        }
        int i = Integer.parseInt(value);
        getNumbers().put(key,i);
        return i;
    }

    public String getParameter(String key) {
        String value = parameters.get(key);
        return value;
    }

    private Map<String,Number> getNumbers(){
        if(numbers ==null){
            numbers = new ConcurrentHashMap<>();
        }
        return numbers;
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
