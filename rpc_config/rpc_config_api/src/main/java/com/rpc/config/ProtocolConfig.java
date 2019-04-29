package com.rpc.config;

import com.rpc.common.util.StringUtils;

public class ProtocolConfig {

    //协议名称
    private String name;
    //协议端口
    private String port;

    // ======================== constructor ========================
    public ProtocolConfig(){};

    public ProtocolConfig(String name,String port){
        setName(name);
        setPort(port);
    }

    // ==================== method ===============================


    public boolean isValid() {
        return !StringUtils.isEmpty(name);
    }




    // ====================== getter and setter =======================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
