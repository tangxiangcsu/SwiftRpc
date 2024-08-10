package com.swiftrpc.swift_rpc.protocol;

import com.swiftrpc.swift_rpc.model.RpcRequest;
import com.swiftrpc.swift_rpc.model.RpcResponse;
import com.swiftrpc.swift_rpc.serializer.Serializer;
import com.swiftrpc.swift_rpc.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.protocol
 * @NAME: ProtocolMessageDecoder
 * @USER: tangxiang
 * @DATE: 2024/7/15
 * @DESCRIPTION:
 **/
public class ProtocolMessageDecoder {

    public static ProtocolMessage<?> decode(Buffer buffer) throws IOException {
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        byte magic = buffer.getByte(0);
        // 校验魔数字，确保消息传递内容时合法的
        if(magic!=ProtocolConstant.PROTOCOL_MAGIC){
            throw new RuntimeException("消息 magic 非法");
        }
        header.setMagic(magic);
        header.setVersion(buffer.getByte(1));
        header.setSerializer(buffer.getByte(2));
        header.setType(buffer.getByte(3));
        header.setStatus(buffer.getByte(4));
        header.setRequestId(buffer.getLong(5)); // +8
        header.setBodyLength(buffer.getInt(13));

        // 解决粘包问题，只读取指定长度的数据
        byte[] bodyBytes = buffer.getBytes(17,17+header.getBodyLength());
        // 解析消息体
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if(serializerEnum == null){
            throw new RuntimeException("序列化协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        ProtocolMessageTypeEnum messageTypeEnum = ProtocolMessageTypeEnum.getEnumByKey(header.getType());
        if(messageTypeEnum == null){
            throw new RuntimeException("序列化消息类型不存在");
        }
        switch(messageTypeEnum){
            case REQUEST:
                RpcRequest request = serializer.deserialize(bodyBytes,RpcRequest.class);
                return new ProtocolMessage<>(header,request);
            case RESPONSE:
                RpcResponse rpcResponse = serializer.deserialize(bodyBytes,RpcResponse.class);
                return new ProtocolMessage<>(header,rpcResponse);
            case HEART_BEAT:
            case OTHERS:
            default:
                throw new RuntimeException("暂不支持该消息类型");

        }
    }
}
