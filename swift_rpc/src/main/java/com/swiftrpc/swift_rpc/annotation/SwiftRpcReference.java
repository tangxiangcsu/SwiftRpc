package com.swiftrpc.swift_rpc.annotation;

import com.swiftrpc.swift_rpc.constant.RpcConstant;
import com.swiftrpc.swift_rpc.fault.retry.RetryStrategyKeys;
import com.swiftrpc.swift_rpc.fault.tolerant.TolerantStrategyKeys;
import com.swiftrpc.swift_rpc.loadbalancer.LoadBalancerKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.annotation
 * @NAME: SwiftRpcReference
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION: 消费者调用接口，需要制定相关属性
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SwiftRpcReference {

    /**
     * 服务接口类
     * */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     * */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡器
     * */
    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     * */
    String retryStrategy() default RetryStrategyKeys.NO;

    /**
     * 容错策略
     * */
    String tolerantStrategy() default TolerantStrategyKeys.FAIL_FAST;

    /**
     * 模拟调用
     * */
    boolean mock() default false;
}
