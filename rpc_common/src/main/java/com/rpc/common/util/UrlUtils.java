package com.rpc.common.util;

import com.rpc.common.Constants;
import com.rpc.common.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlUtils {


    public static List<URL> parseURLs(String address, Map<String, String> defaults) {
        if (address == null || address.length() == 0) {
            return null;
        }
        String[] addresses = Constants.REGISTRY_SPLIT_PATTERN.split(address);
        if (addresses == null || addresses.length == 0) {
            return null; //here won't be empty
        }
        List<URL> registries = new ArrayList<URL>();
        for (String addr : addresses) {
            registries.add(parseURL(addr, defaults));
        }
        return registries;
    }

    public static URL parseURL(String address, Map<String, String> defaults) {
        if(address == null || address.length()==0){
            return null;
        }
        String url = address;
        String defaultProtocol = defaults == null ? null : defaults.get(Constants.PROTOCOL_KEY);
        if (defaultProtocol == null || defaultProtocol.length() == 0) {
            defaultProtocol = Constants.RPC_FLY_PROTOCOL;
        }
        String defaultPath = defaults == null ? null : defaults.get(Constants.PATH_KEY);
        int defaultPort = StringUtils.parseInteger(defaults == null ? null : defaults.get(Constants.PORT_KEY));
        Map<String, String> defaultParameters = defaults == null ? null : new HashMap<String, String>(defaults);

        URL u = URL.valueOf(url);
        boolean changed = false;
        String protocol = u.getProtocol();
        String host = u.getHost();
        int port = u.getPort();
        String path = u.getPath();
        Map<String, String> parameters = new HashMap<String, String>(u.getParameters());
        if ((protocol == null || protocol.length() == 0) && defaultProtocol != null && defaultProtocol.length() > 0) {
            changed = true;
            protocol = defaultProtocol;
        }
        if (port <= 0) {
            if (defaultPort > 0) {
                changed = true;
                port = defaultPort;
            } else {
                changed = true;
                port = 9090;
            }
        }
        if (path == null || path.length() == 0) {
            if (defaultPath != null && defaultPath.length() > 0) {
                changed = true;
                path = defaultPath;
            }
        }
        if (defaultParameters != null && defaultParameters.size() > 0) {
            for (Map.Entry<String, String> entry : defaultParameters.entrySet()) {
                String key = entry.getKey();
                String defaultValue = entry.getValue();
                if (defaultValue != null && defaultValue.length() > 0) {
                    String value = parameters.get(key);
                    if (StringUtils.isEmpty(value)) {
                        changed = true;
                        parameters.put(key, defaultValue);
                    }
                }
            }
        }
        if (changed) {
            u = new URL(protocol, host, port, path, parameters);
        }
        return u;
    }
}
