package com.swiftrpc.swiftrpc_comsumer;

import com.swiftrpc.swift_common.model.User;
import com.swiftrpc.swift_common.service.UserServie;
import com.swiftrpc.swift_rpc.annotation.HuaWeiRpcReference;
import org.springframework.stereotype.Service;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_comsumer
 * @NAME: UserServiceImpl
 * @USER: tangxiang
 * @DATE: 2024/7/22
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/

@Service
public class UserServiceImpl {

    @HuaWeiRpcReference
    private UserServie userServie;

    public void test(){
        User user = new User();
        user.setName("tx");
        User result = userServie.getuser(user);
        System.out.println(result.getName());
    }
}
