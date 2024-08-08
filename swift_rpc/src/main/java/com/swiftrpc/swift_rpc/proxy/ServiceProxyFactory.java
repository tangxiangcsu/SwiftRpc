package com.swiftrpc.swift_rpc.proxy;

import java.lang.reflect.Proxy;

/**
 * 使用代理工厂，作用是根据类创建动态代理对象
 *
 * */
public class ServiceProxyFactory {
    /**
     * 根据服务类获取代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy()
        );
    }
}
