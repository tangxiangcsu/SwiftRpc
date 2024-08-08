package com.swiftrpc.swift_rpc.fault.retry;

import com.swiftrpc.swift_rpc.spi.SpiLoader;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.fault.retry
 * @NAME: RetryStrategyFactory
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    public static RetryStrategy getInstance(String key){
        return SpiLoader.getInstance(RetryStrategy.class,key);
    }
}
