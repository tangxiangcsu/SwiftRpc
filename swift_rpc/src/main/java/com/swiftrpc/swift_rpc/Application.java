package com.swiftrpc.swift_rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc
 * @NAME: Application
 * @USER: tangxiang
 * @DATE: 2024/8/4
 * @DESCRIPTION:
 **/
@SpringBootApplication
@CrossOrigin
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
