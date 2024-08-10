package com.swiftrpc.swift_rpc.fault.tolerant;

import com.swiftrpc.swift_rpc.model.RpcResponse;

import java.util.Map;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.fault.tolerant
 * @NAME: TolerantStrategy
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION:
 **/
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
