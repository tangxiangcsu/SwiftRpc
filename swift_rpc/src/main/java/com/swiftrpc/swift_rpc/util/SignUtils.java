package com.swiftrpc.swift_rpc.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.swiftrpc.swift_rpc.protocol.ProtocolMessage;

import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.util
 * @NAME: SignUtils
 * @USER: tangxiang
 * @DATE: 2024/7/27
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/
public class SignUtils {

    /**
     * 生成签名
     * @param protocolMessage 包含需要签名的协议请求数据
     * @param secretKey 密钥
     * @return 生成的签名字符串
     */
    public static String genSign1(ProtocolMessage protocolMessage , String secretKey) {
        // 使用SHA256算法的Digester
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        // 构建签名内容，将哈希映射转换为字符串并拼接密钥
        String content = protocolMessage.toString() + "." + secretKey;
        // 计算签名的摘要并返回摘要的十六进制表示形式
        return md5.digestHex(content.getBytes(StandardCharsets.UTF_8));
    }

}
