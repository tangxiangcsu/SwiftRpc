package com.swiftrpc.swift_rpc.server.tcp;


import com.swiftrpc.swift_rpc.server.HttpServer;
import com.swiftrpc.swift_rpc.server.TcpServerHandler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.server.tcp
 * @NAME: VertxTcpServer
 * @USER: tangxiang
 * @DATE: 2024/7/14
 * @DESCRIPTION:
 **/
public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] getBytes){
        return "Hello Word".getBytes();
    }

    @Override
    public void doStart(int port) {
        // 创建Vertx实例
        Vertx vertx = Vertx.vertx();
        // 创建TPC服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
        server.connectHandler(new TcpServerHandler());

        // 启动TCP服务器监听端口
        server.listen(port,result->{
            if(result.succeeded()) {
                System.out.println("TCP server started on port " + port);
            }else{
                System.out.println("Failed to start server: "+ result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}
