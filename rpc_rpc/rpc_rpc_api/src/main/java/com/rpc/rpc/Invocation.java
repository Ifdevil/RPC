package com.rpc.rpc;

import java.util.Map;

public interface Invocation {

    String getMethodName();

    /**
     * get parameter types.
     *
     * @return parameter types.
     * @serial
     */
    Class<?>[] getParameterTypes();

    /**
     * get arguments.
     *
     * @return arguments.
     * @serial
     */
    Object[] getArguments();

    Map<String, String> getAttachments();
}
