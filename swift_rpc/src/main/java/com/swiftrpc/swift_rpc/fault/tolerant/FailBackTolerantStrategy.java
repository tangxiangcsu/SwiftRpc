package com.swiftrpc.swift_rpc.fault.tolerant;

import com.swiftrpc.swift_rpc.model.RpcResponse;

import java.util.Map;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.fault.tolerant
 * @NAME: FailBackTolerantStrategy
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION: 降级到其他服务 - 容错策略
 **/
public class FailBackTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 可自行扩展，获取降级的服务并调用
        return null;
    }
}
