package com.rpc.rpc;

public class RpcException extends RuntimeException {


    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }
}
