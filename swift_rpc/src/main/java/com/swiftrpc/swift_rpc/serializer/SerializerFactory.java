package com.swiftrpc.swift_rpc.serializer;

import com.swiftrpc.swift_rpc.spi.SpiLoader;

/**
 * @PACKAGE_NAME: Serializer.serializer
 * @NAME: SerializerFactory
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 序列化工厂，用以获取序列化对象
 **/
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    public static Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class,key);
    }
}
