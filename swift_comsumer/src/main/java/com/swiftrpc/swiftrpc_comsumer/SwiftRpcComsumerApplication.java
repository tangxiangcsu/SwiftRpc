package com.swiftrpc.swiftrpc_comsumer;

import com.swiftrpc.swift_rpc.annotation.EnableHuaWeiRPC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableHuaWeiRPC(needServer = false)
public class SwiftRpcComsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwiftRpcComsumerApplication.class, args);
    }

}
