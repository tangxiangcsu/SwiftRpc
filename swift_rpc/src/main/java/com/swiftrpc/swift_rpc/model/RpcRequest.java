package com.swiftrpc.swift_rpc.model;

import com.swiftrpc.swift_rpc.constant.RpcConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RPC 请求
 *
 * */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型列表
     */
    private Class<?>[] parameterTypes;

    /**
     * 服务版本号
     * */
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 参数列表
     */
    private Object[] args;

    /**
     * appId
     */
    private String appId;
    /**
     * 公钥
     */
    private String accessKey;
    /**
     * 密钥，在传输时应制空
     */
    private String secretKey;
    /**
     *
     */
    private String nonce;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 签名
     */
    private String sign;

}
