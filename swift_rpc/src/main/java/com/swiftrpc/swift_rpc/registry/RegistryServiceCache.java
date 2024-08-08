package com.swiftrpc.swift_rpc.registry;

import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.registry
 * @NAME: RegistryServiceCache
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 消费端的服务本地缓存，主要提供给消费者使用，当消费者请求到服务列表后，在本地进行缓存，提高性能
 **/
public class RegistryServiceCache {

    /**
     * 服务缓存
     */
    List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     *
     * @param newServiceCache
     * @return
     */
    void writeCache(List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     *
     * @return
     */
    List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    void clearCache() {
        this.serviceCache = null;
    }
}
