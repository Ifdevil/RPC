package com.rpc.config;

import com.rpc.common.Constants;
import com.rpc.common.util.StringUtils;
import com.rpc.config.supports.Parameter;

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
    @Parameter(key = Constants.APPLICATION_KEY)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
