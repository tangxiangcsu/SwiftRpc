package com.swiftrpc.swift_rpc.fault.retry;

import com.github.rholder.retry.*;
import com.swiftrpc.swift_rpc.model.RpcResponse;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.fault.retry
 * @NAME: FixedIntervalRetryStrategy
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION: 固定间隔的重试策略
 **/

public class FixedIntervalRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class)// 重试发生条件
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))//重试策略
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))// 停止策略
                .withRetryListener(new RetryListener() {// 监听重试，该做什么事情
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        System.out.println(String.format("重试次数 {}", attempt.getAttemptNumber()));
                    }
                }).build();
        return retryer.call(callable);// 异步执行重试任务
    }
}
