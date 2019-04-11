package com.rpc.common.util;

import com.rpc.common.Constants;
import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Optional;

public class NetUtils {

    private static final Logger logger = Logger.getLogger(NetUtils.class);

    // valid port range is (0, 65535]
    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 65535;

    private volatile static InetAddress LOCAL_ADDRESS = null;

    /**
     * 获取IP地址
     * @return
     */
    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    /**
     * 获取IP地址
     * @return
     */
    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? Constants.LOCALHOST_VALUE : address.getHostAddress();
    }

    /**
     * 获取InetAddress( InetAddress是Java对IP地址的封装 )
     * @return
     */
    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            Optional<InetAddress> addressOptional = toValidAddress(localAddress);
            if(addressOptional.isPresent()){
                return addressOptional.get();
            }
        } catch (UnknownHostException e) {
            logger.warn(e);
        }
        //NetworkInterface可以通过getNetworkInterfaces方法来枚举本机所有的网络接口
        //利用getNetworkInterfaces得到的网络接口来枚举本机的所有IP地址
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if(null == interfaces){
                return localAddress;
            }
            while (interfaces.hasMoreElements()){
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    Optional<InetAddress> addressop = toValidAddress(addresses.nextElement());
                    if(addressop.isPresent()){
                        return addressop.get();
                    }
                }
            }
        } catch (SocketException e) {
            logger.warn(e);
        }
        return localAddress;
    }

    public static String getIpByHost(String hostName){
        try {
            return InetAddress.getByName(hostName).getHostAddress();
        } catch (UnknownHostException e) {
            return hostName;
        }
    }
    /**
     * 检验InetAddress
     * @param inetAddress
     * @return
     */
    private static Optional<InetAddress> toValidAddress(InetAddress inetAddress){
        if(isValidV4Address(inetAddress)){
            return Optional.of(inetAddress);
        }
        return  Optional.empty();
    }

    /**
     * 检验IP是否是V4
     * @param inetAddress
     * @return
     */
    static boolean isValidV4Address(InetAddress inetAddress){
        String name = inetAddress.getHostAddress();
        return name != null
                && !Constants.ANYHOST_VALUE.equals(name)
                && !Constants.LOCALHOST_VALUE.equals(name);
    }

    public static boolean isInvalidPort(int port) {
        return port <= MIN_PORT || port > MAX_PORT;
    }
}
