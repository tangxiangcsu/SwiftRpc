package com.swiftrpc.swiftrpc_provider;

import com.swiftrpc.swift_rpc.annotation.EnableSwiftRPC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwiftRPC
public class SwiftRpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwiftRpcProviderApplication.class, args);
    }

}
