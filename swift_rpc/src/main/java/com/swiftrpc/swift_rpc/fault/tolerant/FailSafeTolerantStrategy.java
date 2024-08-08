package com.swiftrpc.swift_rpc.fault.tolerant;

import com.swiftrpc.swift_rpc.model.RpcResponse;

import java.util.Map;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.fault.tolerant
 * @NAME: FailSafeTolerantStrategy
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 当重试失败，则静默处理，返回一个新的response,并用日志记录
 **/
public class FailSafeTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        System.out.println(String.format("静默处理异常", e));
        return new RpcResponse();
    }
}