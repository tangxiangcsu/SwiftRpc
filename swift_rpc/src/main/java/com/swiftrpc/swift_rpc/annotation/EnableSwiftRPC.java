package com.swiftrpc.swift_rpc.annotation;

import com.swiftrpc.swift_rpc.boorstrap.ConsumerBootstrap;
import com.swiftrpc.swift_rpc.boorstrap.ProviderBootstrap;
import com.swiftrpc.swift_rpc.boorstrap.RpcInitBootStrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.annotation
 * @NAME: EnableSwiftRPC
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
// 注册我们自定义的启动类
@Import({RpcInitBootStrap.class, ProviderBootstrap.class, ConsumerBootstrap.class})
public @interface EnableSwiftRPC {

    /**
     * 启动Server服务
     * */
    boolean needServer() default true;

}
