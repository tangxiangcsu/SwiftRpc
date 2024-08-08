package com.swiftrpc.swiftrpc_comsumer;

import com.swiftrpc.swift_rpc.config.RpcConfig;
import com.swiftrpc.swift_rpc.util.ConfigUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SwiftRpcComsumerApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void testConfigUtilsLoaderConfig(){
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class,"prc");
        System.out.println(rpcConfig.toString());
    }

}
