package com.swiftrpc.swift_rpc.auth;

import com.swiftrpc.swift_rpc.model.RpcRequest;
import com.swiftrpc.swift_rpc.protocol.ProtocolMessage;
import io.vertx.core.Handler;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.auth
 * @NAME: Filter
 * @USER: tangxiang
 * @DATE: 2024/7/27
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 鉴权过滤器接口
 **/
public interface Filter {
    void doFilter(ProtocolMessage<RpcRequest> request, Handler<ProtocolMessage<RpcRequest>> next);
}

