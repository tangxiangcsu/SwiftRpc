package com.swiftrpc.swift_rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.swiftrpc.swift_rpc.config.RpcApplication;
import com.swiftrpc.swift_rpc.config.RpcConfig;
import com.swiftrpc.swift_rpc.constant.RpcConstant;
import com.swiftrpc.swift_rpc.fault.retry.RetryStrategy;
import com.swiftrpc.swift_rpc.fault.retry.RetryStrategyFactory;
import com.swiftrpc.swift_rpc.fault.tolerant.TolerantStrategy;
import com.swiftrpc.swift_rpc.fault.tolerant.TolerantStrategyFactory;
import com.swiftrpc.swift_rpc.loadbalancer.LoadBalancerFactory;
import com.swiftrpc.swift_rpc.model.RpcRequest;
import com.swiftrpc.swift_rpc.model.RpcResponse;
import com.swiftrpc.swift_rpc.model.ServiceMetaInfo;
import com.swiftrpc.swift_rpc.registry.Registry;
import com.swiftrpc.swift_rpc.registry.RegistryFactory;
import com.swiftrpc.swift_rpc.serializer.Serializer;
import com.swiftrpc.swift_rpc.serializer.SerializerFactory;
import com.swiftrpc.swift_rpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务动态代理，消费者在调用服务提供者的实现类时，客户端是如何发现的。这里面就需要jdk的动态代理
 * 总结：在消费者调用提供者的接口时，会改用调用invoke方法。在invoke方法当中，我们需要获得要调用
 * 的方法信息、传入的参数列表，用这些请求参数来构造请求对象就可以完成调用
 * */

public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .nonce(String.valueOf(RandomUtil.randomNumbers(100)))
                .timestamp(String.valueOf(System.currentTimeMillis()/1000))
                .accessKey(rpcConfig.getAccessKey())
                .accessKey(rpcConfig.getSecretKey())
                .appId(rpcConfig.getAppId())
                .build();
        RpcResponse rpcResponse;
        try {
            // 从注册中心获得服务提供者地址
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if(CollUtil.isEmpty(serviceMetaInfoList)){
                throw new RuntimeException("暂无服务地址");
            }
            // 负载均衡到服务提供者上
            Map<String, Object> requestParams = new HashMap<String,Object>();
            requestParams.put("methodName",rpcRequest.getMethodName());

            ServiceMetaInfo serviceMetaInfo1 = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer()).select(requestParams,serviceMetaInfoList);//负载均衡策略
            // doRequest可以封装服务重试机制
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(()->
                new VertxTcpClient().doRequest(rpcRequest,serviceMetaInfo1)
            );
        } catch(Exception e){
            // 调用容错机制
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null,e);
        }
        return rpcResponse.getData();
    }
}
