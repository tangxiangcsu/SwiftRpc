package com.swiftrpc.swift_rpc.fault.tolerant;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.fault.tolerant
 * @NAME: FailFastTolerantStrategy
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/

import com.swiftrpc.swift_rpc.model.RpcResponse;

import java.util.Map;

/**
 * 快速失败 - 容错策略（立刻通知外层调用方）
 */
public class FailFastTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }

}
