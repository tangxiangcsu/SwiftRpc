package com.swiftrpc.swiftrpc_comsumer;

public class EasyComsumerExample {
    public static void main(String[] args) {
        //使用启动器
        /*ConsumerBootstrap.init();
        UserServie userServie = ServiceProxyFactory.getProxy(UserServie.class);
        User user = new User();
        user.setName("tx");
        User newuser = userServie.getuser(user);
        System.out.println(newuser.getName());*/

        /*RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class,"rpc");
        System.out.println(rpcConfig.toString());*/

        //使用注解

    }
}
