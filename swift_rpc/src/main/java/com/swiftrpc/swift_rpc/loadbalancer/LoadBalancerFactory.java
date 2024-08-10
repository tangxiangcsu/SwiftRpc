package com.swiftrpc.swift_rpc.loadbalancer;

import com.swiftrpc.swift_rpc.spi.SpiLoader;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.loadbalancer
 * @NAME: LoadBalancerFactory
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION:
 **/
public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     * */
    private static final LoadBalancer DEFAUT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     * */
    public static LoadBalancer getInstance(String key){
        return SpiLoader.getInstance(LoadBalancer.class,key);
    }
}
