package com.swiftrpc.swift_rpc.config;

import com.swiftrpc.swift_rpc.registry.RegistryKeys;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.config
 * @NAME: RegistryConfig
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @DESCRIPTION:
 **/
@Data
public class RegistryConfig {

    /**
     * 注册中心类别
     */
    private String registry = RegistryKeys.ETCD;

    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2379";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;
}