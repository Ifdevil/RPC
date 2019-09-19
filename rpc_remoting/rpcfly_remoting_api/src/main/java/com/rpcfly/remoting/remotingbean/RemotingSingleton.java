package com.rpcfly.remoting.remotingbean;

import com.rpcfly.remoting.exchange.Exchanger;
import com.rpcfly.remoting.exchange.support.header.HeaderExchanger;

public class RemotingSingleton {

    private volatile static Exchanger exchanger;

    /**
     * 返回一个数据交换者
     * @return
     */
    public static Exchanger getExchanger(){
        if(exchanger == null){
            synchronized (RemotingSingleton.class){
                if(exchanger == null){
                    exchanger = new HeaderExchanger();
                }
            }
        }
        return exchanger;
    }
}
