package com.swiftrpc.swift_rpc.loadbalancer;

import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.loadbalancer
 * @NAME: LoadBalancer
 * @USER: tangxiang
 * @DATE: 2024/7/19
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/
public interface LoadBalancer {

    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
