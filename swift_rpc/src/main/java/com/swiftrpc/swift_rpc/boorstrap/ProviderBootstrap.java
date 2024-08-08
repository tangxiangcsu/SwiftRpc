package com.swiftrpc.swift_rpc.boorstrap;

import com.swiftrpc.swift_rpc.annotation.SwiftRpcService;
import com.swiftrpc.swift_rpc.config.RegistryConfig;
import com.swiftrpc.swift_rpc.config.RpcApplication;
import com.swiftrpc.swift_rpc.config.RpcConfig;
import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;
import com.swiftrpc.swift_rpc.model.ServiceRegisterInfo;
import com.swiftrpc.swift_rpc.registry.LocalRegistry;
import com.swiftrpc.swift_rpc.registry.Registry;
import com.swiftrpc.swift_rpc.registry.RegistryFactory;
import com.swiftrpc.swift_rpc.server.tcp.VertxTcpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.boorstrap
 * @NAME: ProviderBootstrap
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 服务提供者的启动类，利用Bean的特性去监听Bean的加载，直接获取服务提供者的Bean对象
 **/
@Slf4j
public class ProviderBootstrap implements BeanPostProcessor {


    private static final Map<String, Boolean> SERVICE_AUTH_MAP = new ConcurrentHashMap<>();

    /**
     * 初始化
     * */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList){
        // RPC框架的初始化（配置和注册中心）
        RpcApplication.init();
        // f
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        for(ServiceRegisterInfo<?> registerInfo : serviceRegisterInfoList){
            String servieName = registerInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(servieName,registerInfo.getImplClass());
            // 注册服务搭配注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(servieName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getPort());
            try{
                registry.register(serviceMetaInfo);
            }catch (Exception e){
                throw new RuntimeException(servieName + " 服务注册失败, " + e);
            }
            // 启动服务器
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getPort());
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 在服务提供者Bean初始化后，执行注册服务的操作
        Class<?> beanClass = bean.getClass();
        SwiftRpcService swiftRpcService = beanClass.getAnnotation(SwiftRpcService.class);
        if(swiftRpcService != null){
            /**
             * 1. 获取服务基本信息
             */
            log.info("coming");
            Class<?> interfaceClass = swiftRpcService.interfaceClass();
            // 默认值处理
            if(interfaceClass == void.class){
                interfaceClass = beanClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = swiftRpcService.serviceVersion();
            boolean auth = swiftRpcService.auth();
            if(auth){
                SERVICE_AUTH_MAP.put(serviceName, auth);
                log.info(serviceName + "need to authrization");
            }
            /**
             * 3. 注册服务
             */
            // 本地注册
            LocalRegistry.register(serviceName,beanClass);
            // 注册服务搭配注册中心
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getPort());
            try{
                registry.register(serviceMetaInfo);
            }catch (Exception e){
                throw new RuntimeException(serviceName + " 服务注册失败, " + e);
            }

        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean,beanName);
    }
    public static boolean isAuthRequired(String serviceName){
        return SERVICE_AUTH_MAP.getOrDefault(serviceName, false);
    }

}
