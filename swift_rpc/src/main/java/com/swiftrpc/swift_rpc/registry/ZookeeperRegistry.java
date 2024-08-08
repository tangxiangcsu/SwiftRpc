package com.swiftrpc.swift_rpc.registry;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.swiftrpc.swift_rpc.config.RegistryConfig;
import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.registry
 * @NAME: ZookeeperRegistry
 * @USER: tangxiang
 * @DATE: 2024/7/14
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/

@Slf4j
public class ZookeeperRegistry implements Registry{

    private CuratorFramework client;

    private ServiceDiscovery<ServiceMetaInfo> serviceDiscovery;

    /**
     * 根节点
     */
    private static final String ZK_ROOT_PATH = "/rpc/zk/";
    /**
     * 本机注册的节点 key 集合（用于维护续期）
     */
    private final Set<String> localRegisterNodeKeySet = new HashSet<>();

    /**
     * 注册中心服务缓存
     */
    private final RegistryServiceCache registryServiceCache = new RegistryServiceCache();

    /**
     * 正在监听的 key 集合
     */
    private final Set<String> watchingKeySet = new ConcurrentHashSet<>();


    @Override
    public void init(RegistryConfig registryConfig) {
        client = CuratorFrameworkFactory
                .builder()
                .connectString(registryConfig.getAddress())
                .retryPolicy(new ExponentialBackoffRetry(Math.toIntExact(registryConfig.getTimeout()),3))
                .build();
        // 构造服务发现示例
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceMetaInfo.class)
                .client(client)
                .basePath(ZK_ROOT_PATH)
                .serializer(new JsonInstanceSerializer<>(ServiceMetaInfo.class))
                .build();
        try{
            client.start();
            serviceDiscovery.start();
        }catch (Exception e){
            log.error("加载ZK客户端和服务发现示例出现一场");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        // 将服务注册到ZK里
        serviceDiscovery.registerService(builderServiceInstance(serviceMetaInfo));

        // 添加节点信息到本地缓存
        String registryKey = ZK_ROOT_PATH+serviceMetaInfo.getServiceNodeKey();
        localRegisterNodeKeySet.add(registryKey);
    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {

        try{
            serviceDiscovery.unregisterService(builderServiceInstance(serviceMetaInfo));
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        // 添加节点信息到本地缓存
        String registryKey = ZK_ROOT_PATH+serviceMetaInfo.getServiceNodeKey();
        localRegisterNodeKeySet.remove(registryKey);
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        // 优先从缓存中获取服务信息
        List<ServiceMetaInfo> cachedServiceMetaInfo = registryServiceCache.readCache();
        if(cachedServiceMetaInfo!=null){
            return cachedServiceMetaInfo;
        }
        try{
            Collection<ServiceInstance<ServiceMetaInfo>> serviceInstances =  serviceDiscovery.queryForInstances(serviceKey);

            // 解析服务
            List<ServiceMetaInfo> serviceMetaInfoList = serviceInstances.stream()
                    .map(ServiceInstance::getPayload)
                    .collect(Collectors.toList());

            // 写入本地缓存
            registryServiceCache.writeCache(serviceMetaInfoList);
            return serviceMetaInfoList;
        }catch (Exception e){
            log.error("获取服务列表失败",e);
            throw new RuntimeException("获取服务列表失败",e);
        }
    }

    @Override
    public void heartBeat() {
        // 不需要心跳机制
    }

    /**
     * 监听（消费端）
     *
     * */
    @Override
    public void watch(String serviceNodeKey) {
        String watchKey = ZK_ROOT_PATH+serviceNodeKey;
        boolean newWatch = watchingKeySet.add(watchKey);
        if(newWatch){
            CuratorCache curatorCache = CuratorCache.build(client,watchKey);
            curatorCache.start();
            curatorCache.listenable().addListener(
                    CuratorCacheListener.builder()
                    .forDeletes(childData -> registryServiceCache.clearCache())
                    .forChanges(((oldNode, node)->registryServiceCache.clearCache()))
                    .build()
            );
        }
    }

    @Override
    public void destroy() {
        // 这步可以不写，因为ZK中都是零时节点，如果服务下线，自然而然就被删掉了
        for(String key: localRegisterNodeKeySet){
            try{
                client.delete().guaranteed().forPath(key);
            }catch (Exception e){
                throw new RuntimeException(key+"节点下线失败");
            }
        }
        // 释放资源
        if(client!=null){
            client.close();
        }
    }

    public ServiceInstance builderServiceInstance(ServiceMetaInfo serviceMetaInfo){
        String serviceAddress = serviceMetaInfo.getServiceHost() + ":" + serviceMetaInfo.getServicePort();
        try{
            return ServiceInstance.
                    <ServiceMetaInfo>builder()
                    .address(serviceAddress)
                    .id(serviceAddress)
                    .name(serviceMetaInfo.getServiceKey())
                    .payload(serviceMetaInfo)
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
