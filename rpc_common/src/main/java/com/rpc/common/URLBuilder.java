package com.rpc.common;

import com.rpc.common.util.CollectionUtils;
import com.rpc.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class URLBuilder {

    private String protocol;

    // by default, host to registry
    private String host;

    // by default, port to registry
    private int port;

    private String path;

    private Map<String, String> parameters;

    public URLBuilder() {
        protocol = null;
        host = null;
        port = 0;
        path = null;
        parameters = new HashMap<>();
    }

    public URLBuilder(String protocol, String host, int port) {
        this(protocol, host, port, null, "");
    }


    public URLBuilder(String protocol, String host, int port, String path) {
        this(protocol, host, port, path, "");
    }

    public URLBuilder(String protocol, String host, int port, String[] pairs) {
        this(protocol, host, port, null, CollectionUtils.toStringMap(pairs));
    }

    public URLBuilder(String protocol, String host, int port, Map<String, String> parameters) {
        this(protocol, host, port, null, parameters);
    }

    public URLBuilder(String protocol, String host, int port, String path, String... pairs) {
        this(protocol, host, port, path, CollectionUtils.toStringMap(pairs));
    }

    public URLBuilder(String protocol, String host, int port, String path, Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters != null ? parameters : new HashMap<>();
    }


    public static URLBuilder from(URL url) {
        String protocol = url.getProtocol();
        String host = url.getHost();
        int port = url.getPort();
        String path = url.getPath();
        Map<String, String> parameters = new HashMap<>(url.getParameters());
        return new URLBuilder(
                protocol,
                host,
                port,
                path,
                parameters);
    }

    public URLBuilder addParameterIfAbsent(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (hasParameter(key)) {
            return this;
        }
        parameters.put(key, value);
        return this;
    }

    public boolean hasParameter(String key) {
        String value = getParameter(key);
        return value != null && value.length() > 0;
    }

    public String getParameter(String key) {
        String value = parameters.get(key);
        if (StringUtils.isEmpty(value)) {
            value = parameters.get(Constants.DEFAULT_KEY_PREFIX + key);
        }
        return value;
    }


    public URL build() {
        port = port < 0 ? 0 : port;
        return new URL(protocol,host, port, path, parameters);
    }

}
