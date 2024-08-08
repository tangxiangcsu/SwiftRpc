package com.swiftrpc.swift_rpc.loadbalancer;

import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.loadbalancer
 * @NAME: RoundRobinLoadBalancer
 * @USER: tangxiang
 * @DATE: 2024/7/19
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 轮询负载均衡
 **/
public class RoundRobinLoadBalancer implements LoadBalancer{
    private final AtomicInteger currentIndex = new AtomicInteger(0);
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if(serviceMetaInfoList.isEmpty()){
            throw new RuntimeException("服务列表为空");
        }
        int size = serviceMetaInfoList.size();
        if(size == 1){
            return serviceMetaInfoList.get(0);
        }
        int index = currentIndex.getAndIncrement()%size;
        return serviceMetaInfoList.get(index);
    }
}
