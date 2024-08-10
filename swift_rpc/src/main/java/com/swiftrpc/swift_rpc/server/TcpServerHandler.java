package com.swiftrpc.swift_rpc.server;

import com.swiftrpc.swift_rpc.auth.AuthFilter;
import com.swiftrpc.swift_rpc.auth.Filter;
import com.swiftrpc.swift_rpc.model.RpcRequest;
import com.swiftrpc.swift_rpc.model.RpcResponse;
import com.swiftrpc.swift_rpc.protocol.*;
import com.swiftrpc.swift_rpc.registry.LocalRegistry;
import com.swiftrpc.swift_rpc.server.tcp.TcpBufferHandlerWrapper;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.server
 * @NAME: TcpServerHandler
 * @USER: tangxiang
 * @DATE: 2024/7/15
 * @DESCRIPTION: 请求处理器（服务提供者）
 **/
public class TcpServerHandler implements Handler<NetSocket> {
    @Override
    public void handle(NetSocket event) {
        TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
            // 接受请求，解码
            ProtocolMessage<RpcRequest> protocolMessage;
            try {
                protocolMessage = (ProtocolMessage<RpcRequest>) ProtocolMessageDecoder.decode(buffer);
            } catch (IOException e) {
                throw new RuntimeException("协议解码异常");
            }
            // 鉴权过滤器
            Filter authFilter = new AuthFilter();
            authFilter.doFilter(protocolMessage, request -> {
                // 获得请求体
                RpcRequest rpcRequest = protocolMessage.getBody();
                RpcResponse rpcResponse = new RpcResponse();
                try {
                    // 通过反射调到相应方法
                    Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                    Method method = implClass.getMethod(rpcRequest.getMethodName(),rpcRequest.getParameterTypes());
                    Object result = method.invoke(implClass.newInstance(),rpcRequest.getArgs());

                    // 封装返回结果
                    rpcResponse.setData(result);
                    rpcResponse.setDataType(method.getReturnType());
                    rpcResponse.setMessage("ok");
                } catch (Exception e) {
                    e.printStackTrace();
                    rpcResponse.setMessage(e.getMessage());
                    rpcResponse.setException(e);
                }
                // 发送响应，编码返回
                ProtocolMessage.Header header = protocolMessage.getHeader();
                header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
                header.setStatus((byte) ProtocolMessageStatusEnum.OK.getValue());
                ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);
                try {
                    Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
                    event.write(encode);
                } catch (IOException e) {
                    throw new RuntimeException("协议消息编码错误");
                }
            });
        });
        event.handler(bufferHandlerWrapper);
    }
}
