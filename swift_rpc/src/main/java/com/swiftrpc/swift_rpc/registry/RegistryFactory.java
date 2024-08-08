package com.swiftrpc.swift_rpc.registry;

import com.swiftrpc.swift_rpc.spi.SpiLoader;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.registry
 * @NAME: RegistryFactory
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 支持多种注册中心，ETCD, ZK
 **/
public class RegistryFactory {

    // SPI 动态加载
    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }

}
