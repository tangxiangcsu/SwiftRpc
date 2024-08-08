package com.swiftrpc.swift_rpc.registry;

import com.swiftrpc.swift_rpc.config.RegistryConfig;
import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.registry
 * @NAME: Registry
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 注册中心接口，方便后续对注册中心进行扩展
 **/
public interface Registry {

    /**
     * 初始化
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 心跳检测（服务端）
     */
    void heartBeat();

    /**
     * 监听（消费端）
     *
     * @param serviceNodeKey
     */
    void watch(String serviceNodeKey);

    /**
     * 服务销毁
     */
    void destroy();
}
