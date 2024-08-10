package com.swiftrpc.swift_rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.protocol
 * @NAME: ProtocalMessage
 * @USER: tangxiang
 * @DATE: 2024/7/14
 * @DESCRIPTION:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolMessage<T> {

    private Header header;

    /**
     * 消息体（请求或响应对象）
     * */
    private T body;

    /**
     * 协议消息头
     * */
    @Data
    public static class Header{

        private byte magic;

        private byte version;

        private byte type;

        private byte status;

        private byte serializer;

        private long requestId;

        private int bodyLength;

    }
}
