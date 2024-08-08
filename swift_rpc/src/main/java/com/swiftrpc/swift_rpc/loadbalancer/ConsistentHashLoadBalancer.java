package com.swiftrpc.swift_rpc.loadbalancer;

import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.loadbalancer
 * @NAME: ConsistentHashLoadBalancer
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 一致性hash算法，使用TreeMap实现Hash环，使用该数据结构提供的ceilingEntry和firstEntry方法
 **/
public class ConsistentHashLoadBalancer implements LoadBalancer{
    /**
     * 一致性Hash环，存放虚节点和正常服务节点
     * */
    private final TreeMap<Integer, ServiceMetaInfo> virtualNodes = new TreeMap<>();

    /**
     * 虚拟节点个数
     * */
    private static final int VIRTUAL_NODE_NUM = 100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if(serviceMetaInfoList.isEmpty()){
            throw new RuntimeException("服务列表为空");
        }
        for(ServiceMetaInfo serviceMetaInfo: serviceMetaInfoList){
            for(int i=0;i<VIRTUAL_NODE_NUM;i++){
                int hash = getHash(serviceMetaInfo.getServiceAddress() + "$" + i);
                virtualNodes.put(hash, serviceMetaInfo);
            }
        }
        // 获得请求的Hash值
        int hash = getHash(requestParams);

        //使用TreeMap.ceilingEntry获得最近且大于等于hash的节点
        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        if(entry == null){// 如果没有则获得Hash环的第一个虚拟节点
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();
    }

    private int getHash(Object key){
        return key.hashCode();
    }
}
