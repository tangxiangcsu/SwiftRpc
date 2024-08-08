package com.swiftrpc.swift_rpc.protocol;

import lombok.Getter;
/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.protocol
 * @NAME: ProtocolMessageTypeEnum
 * @USER: tangxiang
 * @DATE: 2024/7/14
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 协议消息类型枚举，包括请求、响应、心跳、其他。代码如下：
 **/
@Getter
public enum ProtocolMessageTypeEnum {

    REQUEST(0),
    RESPONSE(1),
    HEART_BEAT(2),
    OTHERS(3);

    private final int key;

    ProtocolMessageTypeEnum(int key){
        this.key = key;
    }

    /**
     * 根据key获取枚举
     * */
    public static ProtocolMessageTypeEnum getEnumByKey(int key){
        for(ProtocolMessageTypeEnum anEnum : ProtocolMessageTypeEnum.values()){
            if(anEnum.key == key){
                return anEnum;
            }
        }
        return null;
    }
}
