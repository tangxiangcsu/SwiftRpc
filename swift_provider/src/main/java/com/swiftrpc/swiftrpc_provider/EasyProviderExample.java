package com.swiftrpc.swiftrpc_provider;


import com.swiftrpc.swift_common.service.UserServie;
import com.swiftrpc.swiftrpc_provider.Impl.UserServiceImpl;
import com.swiftrpc.swift_rpc.boorstrap.ProviderBootstrap;
import com.swiftrpc.swift_rpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

public class EasyProviderExample {
    public static void main(String[] args) {
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserServie.class.getName(),UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
