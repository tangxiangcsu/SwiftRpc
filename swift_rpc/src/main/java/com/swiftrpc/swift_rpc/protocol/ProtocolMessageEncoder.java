package com.swiftrpc.swift_rpc.protocol;


import com.swiftrpc.swift_rpc.serializer.Serializer;
import com.swiftrpc.swift_rpc.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.protocol
 * @NAME: ProtocolMessageEncoder
 * @USER: tangxiang
 * @DATE: 2024/7/15
 * @DESCRIPTION: 向Buffer缓冲区中写入消息对象，存在粘包半包的问题
 **/
public class ProtocolMessageEncoder {
    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws IOException {
        if(protocolMessage == null || protocolMessage.getHeader() == null){
            return Buffer.buffer();
        }
        ProtocolMessage.Header header = protocolMessage.getHeader();
        // 依次向缓冲区写入字节
        Buffer buffer = Buffer.buffer();
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());

        // 获取序列化器
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if(serializerEnum == null){
            throw new RuntimeException("序列化协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());
        // 写入body长度和数据
        buffer.appendInt(bodyBytes.length);
        buffer.appendBytes(bodyBytes);
        return buffer;
    }

}
