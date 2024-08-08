package com.swiftrpc.swiftrpc_provider.Impl;

import com.swiftrpc.swift_common.model.User;
import com.swiftrpc.swift_common.service.UserServie;
import com.swiftrpc.swift_rpc.annotation.SwiftRpcService;
import org.springframework.stereotype.Service;

@Service
@SwiftRpcService(auth = false)
public class UserServiceImpl implements UserServie {
    @Override
    public User getuser(User user) {
        // System.out.println("我给你提供了打印名字的服务哦，你的名字是: "+user.toString());
        user.setName(user.getName()+"txboy");
        return user;
    }
}
