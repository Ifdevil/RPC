package com.rpc.config;

import com.rpc.common.util.StringUtils;

/**
 * 当前应用配置
 */
public class ApplicationConfig {
    //应用名称
    private String name;

    // ======================== constructor ========================

    public ApplicationConfig(){}
    public ApplicationConfig(String name){
        setName(name);
    }


    // ====================  method  ===============================


    public boolean isValid() {
        return !StringUtils.isEmpty(name);
    }



    // ======================getter and setter =======================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
