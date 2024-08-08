package com.swiftrpc.swiftrpc_comsumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_comsumer
 * @NAME: AnnotationServiceTest
 * @USER: tangxiang
 * @DATE: 2024/7/22
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/
@SpringBootTest
public class AnnotationServiceTest {
    @Resource
    private UserServiceImpl service;

    @Test
    void test01(){
        service.test();
    }
}
