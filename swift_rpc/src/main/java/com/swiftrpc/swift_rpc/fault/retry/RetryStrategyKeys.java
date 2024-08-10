package com.swiftrpc.swift_rpc.fault.retry;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.fault.retry
 * @NAME: RetryStrategyKeys
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION:
 **/
public interface RetryStrategyKeys {
    String NO= "no";

    /**
     * 固定时间间隔重试
     * */
    String FIXED_INTERVAL = "fixedInterval";
}
