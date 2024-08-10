package com.swiftrpc.swift_rpc.protocol;

import lombok.Getter;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.protocol
 * @NAME: ProtocolMessageStatusEnum
 * @USER: tangxiang
 * @DATE: 2024/7/14
 * @DESCRIPTION:
 **/
@Getter
public enum ProtocolMessageStatusEnum {

    OK("ok",200),
    BAD_REQUEST("badRequest",400),
    BAD_RESPONSE("badResponse",500);

    private final String text;

    private final int value;

    ProtocolMessageStatusEnum(String text, int value){
        this.text = text;
        this.value = value;
    }

    /**
     * 根据value获取状态
     * */
    public static ProtocolMessageStatusEnum getEnumByValue(int value){
        for(ProtocolMessageStatusEnum anEnum : ProtocolMessageStatusEnum.values()){
            if(anEnum.value == value) return anEnum;
        }
        return null;
    }
}

