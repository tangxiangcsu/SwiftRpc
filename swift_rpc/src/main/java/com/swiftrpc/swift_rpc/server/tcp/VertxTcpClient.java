package com.swiftrpc.swift_rpc.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.swiftrpc.swift_rpc.config.RpcApplication;
import com.swiftrpc.swift_rpc.model.RpcRequest;
import com.swiftrpc.swift_rpc.model.RpcResponse;
import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;
import com.swiftrpc.swift_rpc.protocol.*;
import com.swiftrpc.swift_rpc.util.SignUtils;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.server.tcp
 * @NAME: VertxTcpClient
 * @USER: tangxiang
 * @DATE: 2024/7/14
 * @DESCRIPTION: 所有请求响应的逻辑提取出来进行封装
 **/

@Slf4j
@Component
public class VertxTcpClient {

    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceMetaInfo serviceMetaInfo) throws ExecutionException, InterruptedException {
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> future = new CompletableFuture<>();
        netClient.connect(serviceMetaInfo.getServicePort(),serviceMetaInfo.getServiceHost(),result->{
            if(!result.succeeded()){
                log.info("Failed to connect TCP server");
                return;
            }
            NetSocket socket = result.result();

            // 发送数据
            ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
            ProtocolMessage.Header header = new ProtocolMessage.Header();
            header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
            header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
            header.setSerializer((byte) ProtocolMessageSerializerEnum.getEnumByValue(RpcApplication.getRpcConfig().getSerializer()).getKey());
            header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
            // 生成全局的请求ID
            header.setRequestId(IdUtil.getSnowflakeNextId());
            protocolMessage.setBody(rpcRequest);
            protocolMessage.setHeader(header);
            /**
             * 设置签名
             */
            rpcRequest.setSign(SignUtils.genSign1(protocolMessage,rpcRequest.getSecretKey()));
            /**
             * 清除密钥
             */
            rpcRequest.setSecretKey(null);
            // 编码请求
            try{
                Buffer buffer = ProtocolMessageEncoder.encode(protocolMessage);
                socket.write(buffer);
            }catch (Exception e){
                throw new RuntimeException("协议消息编码错误");
            }
            // 接受响应
            TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(responseBuffer->{
                try{
                    ProtocolMessage<RpcResponse> responseProtocolMessage = (ProtocolMessage<RpcResponse>)ProtocolMessageDecoder.decode(responseBuffer);
                    future.complete(responseProtocolMessage.getBody());
                }catch (Exception e){
                    throw new RuntimeException("协议消息解码错误");
                }
            });
            socket.handler(bufferHandlerWrapper);
       });
        RpcResponse rpcResponse = future.get();
        netClient.close();
        return rpcResponse;
    }
}
