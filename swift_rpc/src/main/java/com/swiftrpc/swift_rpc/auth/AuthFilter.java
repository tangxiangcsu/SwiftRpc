package com.swiftrpc.swift_rpc.auth;

import com.swiftrpc.swift_common.model.AuthDto;
import com.swiftrpc.swift_common.service.AuthRequestService;
import com.swiftrpc.swift_rpc.annotation.SwiftRpcReference;
import com.swiftrpc.swift_rpc.boorstrap.ProviderBootstrap;
import com.swiftrpc.swift_rpc.model.RpcRequest;
import com.swiftrpc.swift_rpc.protocol.ProtocolMessage;
import io.vertx.core.Handler;
import lombok.extern.slf4j.Slf4j;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.auth
 * @NAME: AuthFilter
 * @USER: tangxiang
 * @DATE: 2024/7/27
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 鉴权过滤器实现
 **/
@Slf4j
public class AuthFilter implements Filter {
    @SwiftRpcReference
    AuthRequestService authRequestService;

    @Override
    public void doFilter(ProtocolMessage<RpcRequest> request, Handler<ProtocolMessage<RpcRequest>> next) {
        try {
            if(ProviderBootstrap.isAuthRequired(request.getBody().getServiceName())){
                log.info("开始鉴权");
                System.out.println(authRequestService);
                AuthDto authDto = authRequestService.getAuthRequestByAppId(request.getBody().getAppId());
                log.info("查询鉴权中心得到AK/SK" + authDto.toString());
                request.getBody().setSecretKey(authDto.getSk());
                boolean isAuthenticated = AkSkAuthCheck.AuthCheck(request);
                if (!isAuthenticated) {
                    throw new RuntimeException("无权限");
                }
                log.info("鉴权通过");
            }
            next.handle(request);
        } catch (Exception e) {
            log.info(request.getBody().toString());
            throw new RuntimeException("无权限",e);
            /*// 处理鉴权失败情况
            ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>();
            ProtocolMessage.Header header = request.getHeader();
            header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
            header.setStatus((byte) ProtocolMessageStatusEnum.ERROR.getValue());
            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.setMessage("Authentication failed: " + e.getMessage());
            responseProtocolMessage.setHeader(header);
            responseProtocolMessage.setBody(rpcResponse);
            try {
                Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
                request.getSocket().write(encode);
            } catch (IOException ioException) {
                throw new RuntimeException("Failed to encode response", ioException);
            }*/
        }
    }
}

