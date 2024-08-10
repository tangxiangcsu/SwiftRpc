package com.swiftrpc.swift_rpc.config;

import com.swiftrpc.swift_rpc.fault.retry.RetryStrategyKeys;
import com.swiftrpc.swift_rpc.fault.tolerant.TolerantStrategyKeys;
import com.swiftrpc.swift_rpc.loadbalancer.LoadBalancerKeys;
import com.swiftrpc.swift_rpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * @PACKAGE_NAME: Serializer.config
 * @NAME: RpcConfig
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @DESCRIPTION: RPC配置基架
 **/
@Data
public class RpcConfig {

    private String name = "HUAWEICLOUD-RPC-PROJECT";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer port = 8080;

    private String appId = "myApp";

    private String accessKey = "***";

    private String secretKey = "***";

    /**
     * 序列化器
     * */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心info
     * */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 负载均衡器
     * */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     * */
    private String retryStrategy = RetryStrategyKeys.NO;

    /**
     * 容错策略
     */
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;
}