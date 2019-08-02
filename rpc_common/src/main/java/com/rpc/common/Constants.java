package com.rpc.common;

import java.util.regex.Pattern;

public class Constants {

    public static final String RPC_FLY = "rpc_fly";

    public static final String RPC_FLY_VERSION_KEY = "rpc_fly_version";

    public static final String RPC_FLY_VERSION_VALUE= "1.0";

    public static final String TIMESTAMP_KEY= "timestamp";

    public static final String PID_KEY= "pid";

    public static final String APPLICATION_KEY = "application";

    public static final String METHODS_KEY = "methods";

    public static final String ANY_VALUE = "*";

    public static final String LOCALHOST_VALUE = "127.0.0.1";

    public static final String ANYHOST_VALUE = "0.0.0.0";

    public static final String BIND_IP_KEY = "bind.ip";

    public static final String ANYHOST_KEY = "anyhost";

    public static final String BIND_PORT_KEY = "bind.port";

    public static final String INTERFACE_KEY = "interface";

    public static final String PATH_KEY = "path";

    public static final String PROTOCOL_KEY = "protocol";

    public static final String RPC_FLY_PROTOCOL = RPC_FLY;

    public static final String HOST_KEY = "host";

    public static final String PORT_KEY = "port";

    public static final String VERSION_KEY = "version";

    public static final String DEFAULT_KEY_PREFIX = "default.";



    public static final String REGISTRY_PROTOCOL = "registry";

    public static final String DEFAULT_PROTOCOL = "rpc_fly";



    public static final String REGISTRY_KEY = "registry";
    /**
     * The key name for export URL in register center
     */
    public static final String EXPORT_KEY = "export";




    /**
     * key for router type, for e.g., "script"/"file",  corresponding to ScriptRouterFactory.NAME, FileRouterFactory.NAME
     */

    public static final String HEARTBEAT_KEY = "heartbeat";




    /**
     * By default, a consumer JVM instance and a provider JVM instance share a long TCP connection (except when connections are set),
     * which can set the number of long TCP connections shared to avoid the bottleneck of sharing a single long TCP connection.
     */

    public static final int DEFAULT_HEARTBEAT = 60 * 1000;

    //===========正则校验================================


    public static final Pattern REGISTRY_SPLIT_PATTERN = Pattern
            .compile("\\s*[|;]+\\s*");
}
