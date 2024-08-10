package com.swiftrpc.swift_rpc.boorstrap;

import com.swiftrpc.swift_rpc.annotation.EnableSwiftRPC;
import com.swiftrpc.swift_rpc.config.RpcApplication;
import com.swiftrpc.swift_rpc.config.RpcConfig;
import com.swiftrpc.swift_rpc.server.tcp.VertxTcpServer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.boorstrap
 * @NAME: RpcInitBootStrap
 * @USER: tangxiang
 * @DATE: 2024/7/21
 * @DESCRIPTION: RPC注解全局启动框架，获得@EnableHuaWeiRPC注解属性，并初始化RPC框架
 **/

public class RpcInitBootStrap implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获得注解属性
        boolean neddServer = (boolean)importingClassMetadata.getAnnotationAttributes(EnableSwiftRPC.class.getName()).get("needServer");

        // 初始化RPC框架
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        if(neddServer){
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getPort());
        }else{
            System.out.println("不启动 Server");
        }
    }
}
