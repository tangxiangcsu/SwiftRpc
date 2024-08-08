package com.swiftrpc.swift_rpc.annotation;

import com.swiftrpc.swift_rpc.constant.RpcConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.annotation
 * @NAME: HuaWeiRpcService
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 在Provider中使用，用于声明RPC服务，需要在注册和提供的服务类上使用
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HuaWeiRpcService {
    /**
     * 服务接口类
     * */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     * */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 要求鉴权
     * */
    boolean auth() default false;
}
