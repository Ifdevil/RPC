package com.rpc.config;

import com.rpc.common.util.StringUtils;

/**
 * 注册中心配置
 */
public class RegistryConfig {

    private String address;

    // ======================== constructor ========================

    public RegistryConfig(){

    };
    public RegistryConfig(String address){
        setAddress(address);
    }


    // ====================  method  ===============================

    public boolean isValid() {
        // empty protocol will default to 'dubbo'
        return !StringUtils.isEmpty(address);
    }




    // ======================getter and setter =======================

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
