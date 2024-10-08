package com.swiftrpc.swift_rpc.loadbalancer;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.loadbalancer
 * @NAME: LoadBalancerKeys
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION: 列举支持的所有负载均衡器键名
 **/
public interface LoadBalancerKeys {

    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";
}
