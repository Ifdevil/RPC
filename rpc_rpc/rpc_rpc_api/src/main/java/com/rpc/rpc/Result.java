package com.rpc.rpc;

public interface Result {

    Object getValue();

    String getAttachment(String key, String defaultValue);

    void setAttachment(String key, String value);
}
