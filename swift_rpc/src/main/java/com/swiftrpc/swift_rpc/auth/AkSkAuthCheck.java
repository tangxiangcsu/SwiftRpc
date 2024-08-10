package com.swiftrpc.swift_rpc.auth;

import com.swiftrpc.swift_rpc.constant.AuthConstant;
import com.swiftrpc.swift_rpc.model.RpcRequest;
import com.swiftrpc.swift_rpc.protocol.ProtocolMessage;
import com.swiftrpc.swift_rpc.util.SignUtils;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.auth
 * @NAME: AkSkAuthFilter
 * @USER: tangxiang
 * @DATE: 2024/7/27
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 请求处理器鉴权
 **/
public class AkSkAuthCheck {

    private static final long ALLOWED_TIME_DIFFERENCE = 300;

    public static boolean AuthCheck(ProtocolMessage<RpcRequest> protocolMessage){
        RpcRequest request = protocolMessage.getBody();
        String accessKey = request.getAccessKey();
        String nonce = request.getNonce();
        String timestamp = request.getTimestamp();
        Long currentTimestamp = System.currentTimeMillis()/1000;
        String sign = request.getSign();
        // todo 实际情况应该是去数据库中查是否已分配给用户
        if (!accessKey.equals(request.getAccessKey())) {
            throw new RuntimeException("无权限");
        }
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }
        // todo 时间和当前时间不能超过 5 分钟
        if (Math.abs(Long.parseLong(timestamp) - currentTimestamp) > ALLOWED_TIME_DIFFERENCE) {
            throw new RuntimeException("超时");
        }
        // todo 实际情况中是从数据库中查出 secretKey
        String serverSign = SignUtils.genSign1(protocolMessage, request.getSecretKey());
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("无权限");
        }
        return true;
    }
}
