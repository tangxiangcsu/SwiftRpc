package com.swiftrpc.swift_rpc.auth;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.auth
 * @NAME: AuthUtil
 * @USER: tangxiang
 * @DATE: 2024/7/27
 * @DESCRIPTION: 生成密钥和公钥
 **/
public class AuthUtil {

    private static final String SALT = "HuaWeiCloudRPC";

    /**
     * 随机生成密钥和公钥
     * */
    public static String getAKorSK(String IP){
        return  DigestUtil.md5Hex(SALT + IP + RandomUtil.randomNumbers(4));
    }


}
