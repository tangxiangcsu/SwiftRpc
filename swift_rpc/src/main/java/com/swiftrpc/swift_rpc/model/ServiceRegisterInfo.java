package com.swiftrpc.swift_rpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.model
 * @NAME: ServiceRegisterInfo
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION: 服务提供者的封装类
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRegisterInfo<T> {
    /**
     * 服务名称
     * */
    private String serviceName;

    /**
     * 实现类
     * */
    private Class<? extends T> implClass;
}
