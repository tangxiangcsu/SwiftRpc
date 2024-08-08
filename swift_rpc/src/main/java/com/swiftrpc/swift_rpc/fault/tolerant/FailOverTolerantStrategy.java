package com.swiftrpc.swift_rpc.fault.tolerant;

import com.swiftrpc.swift_rpc.model.RpcResponse;

import java.util.Map;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.fault.tolerant
 * @NAME: FailOverTolerantStrategy
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 转移到其他服务节点 - 容错策略
 **/
public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 可自行扩展，获取其他服务节点并调用
        return null;
    }
}
