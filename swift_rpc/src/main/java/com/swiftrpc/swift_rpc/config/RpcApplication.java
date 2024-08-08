package com.swiftrpc.swift_rpc.config;

import com.swiftrpc.swift_rpc.constant.RpcConstant;
import com.swiftrpc.swift_rpc.registry.Registry;
import com.swiftrpc.swift_rpc.registry.RegistryFactory;
import com.swiftrpc.swift_rpc.util.ConfigUtils;

/**
 * @PACKAGE_NAME: Serializer.config
 * @NAME: RpcApplication
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 维护一个全局个单列配置信息实例，当第一次调用就全局维护，减少配置对象的创建
 **/
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    public static void init(RpcConfig newRpcConfig){
        rpcConfig = newRpcConfig;
        System.out.println(String.format("Swift RPC INIT, CONFIG = {}",newRpcConfig.toString()));
        // 初始化注册中心
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        System.out.println(String.format("registry init, config = {}",registryConfig.toString()));

        // 创建并注册ShutDown Hook, JVM退出时执行
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    public static void init(){
        RpcConfig newRpcConfig;
        try{
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULUT_RPC_PREFIX);
        }catch (Exception e){
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    public static RpcConfig getRpcConfig(){
        if(rpcConfig==null){
            synchronized (RpcApplication.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
