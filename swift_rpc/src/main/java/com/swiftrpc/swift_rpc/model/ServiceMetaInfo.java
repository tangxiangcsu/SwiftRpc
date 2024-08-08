package com.swiftrpc.swift_rpc.model;

import cn.hutool.core.util.StrUtil;
import com.swiftrpc.swift_rpc.constant.RpcConstant;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.model
 * @NAME: ServiceMetaInfo
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/
@Data
public class ServiceMetaInfo {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 服务域名
     */
    private String serviceHost;

    /**
     * 服务端口号
     */
    private Integer servicePort;

    /**
     * 服务分组（暂未实现）
     */
    private String serviceGroup = "default";

    /**
     * 获取服务键名
     *
     * @return
     */
    public String getServiceKey() {
        // 后续可扩展服务分组
//        return String.format("%s:%s:%s", serviceName, serviceVersion, serviceGroup);
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册节点键名
     *
     * @return
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }

    /**
     * 获取完整服务地址
     * 消费者、服务者都需要从注册中心获得节点信息，再得到调用地址，并执行
     * @return
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }
}
