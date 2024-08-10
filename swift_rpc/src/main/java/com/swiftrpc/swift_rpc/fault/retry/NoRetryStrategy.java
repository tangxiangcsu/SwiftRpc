package com.swiftrpc.swift_rpc.fault.retry;

import com.swiftrpc.swift_rpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.fault.retry
 * @NAME: NoRetryStrategy
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION:
 **/
public class NoRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
