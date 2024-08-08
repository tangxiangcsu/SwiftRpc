package com.swiftrpc.swift_rpc.loadbalancer;

import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.loadbalancer
 * @NAME: RandomLoadBalancer
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/
public class RandomLoadBalancer implements LoadBalancer{
    private final Random random = new Random();

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        int size = serviceMetaInfoList.size();
        if(size==0){
            throw new RuntimeException("服务列表为空");
        }
        if(size==1){
            return serviceMetaInfoList.get(0);
        }
        return serviceMetaInfoList.get(random.nextInt(size));
    }
}
