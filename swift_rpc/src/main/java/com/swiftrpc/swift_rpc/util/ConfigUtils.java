package com.swiftrpc.swift_rpc.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @PACKAGE_NAME: Serializer.util
 * @NAME: ConfigUtils
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @DESCRIPTION: 读取 application.properties 配置文件并返回配置对象，简化开发
 **/
public class ConfigUtils {
    public static <T> T loadConfig(Class<T> tClass, String prefix){
        return loadConfig(tClass,prefix,"");
    }

    /**
     * 加载配置对象，并将其注入到bean中。
     * 支持区分环境
     *
     * */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment){
        // prefix 指的时配置文件的spring.redis.address这样的前缀
        StringBuilder configFileBuilder = new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        return props.toBean(tClass,prefix);//支持将配置文件转换为bean
    }
}
