package com.rpc.rpc;

import com.rpc.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RpcResult implements Result {
    protected Map<String, String> attachments = new HashMap<String, String>();

    protected Object result;

    public RpcResult(){}
    public RpcResult(Object result){
        this.result = result;
    }
    @Override
    public Object getValue() {
        return result;
    }

    public void setValue(Object result){
        this.result = result;
    }

    @Override
    public String getAttachment(String key, String defaultValue) {
        String result = attachments.get(key);
        if(StringUtils.isEmpty(result)){
            result = defaultValue;
        }
        return result;
    }

    @Override
    public void setAttachment(String key, String value) {
        attachments.put(key, value);
    }
}
