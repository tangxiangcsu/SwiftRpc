package com.swiftrpc.swift_rpc.registry;

import com.swiftrpc.swift_rpc.config.RegistryConfig;
import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.registry
 * @NAME: RedisRegister
 * @USER: tangxiang
 * @DATE: 2024/7/14
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/
public class RedisRegister implements Registry{
    @Override
    public void init(RegistryConfig registryConfig) {

    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {

    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {

    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        return null;
    }

    @Override
    public void heartBeat() {

    }

    @Override
    public void watch(String serviceNodeKey) {

    }

    @Override
    public void destroy() {

    }
}
