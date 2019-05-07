package com.rpc.rpc;

public class RpcInvocation implements Invocation {

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    public RpcInvocation(String methodName,Class<?>[] parameterTypes,Object[] arguments){
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }
    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }
}
