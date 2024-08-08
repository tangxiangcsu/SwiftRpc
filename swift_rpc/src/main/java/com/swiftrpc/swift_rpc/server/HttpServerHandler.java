package com.swiftrpc.swift_rpc.server;

import com.swiftrpc.swift_rpc.config.RpcApplication;
import com.swiftrpc.swift_rpc.model.RpcRequest;
import com.swiftrpc.swift_rpc.model.RpcResponse;
import com.swiftrpc.swift_rpc.registry.LocalRegistry;
import com.swiftrpc.swift_rpc.serializer.Serializer;
import com.swiftrpc.swift_rpc.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 1. 反序列化请求为对象，并从中获取参数
 * 2. 根据服务名称从本地注册器中获取对应的服务实现类
 * 3. 通过反射机制，得到返回结果
 * 4. 将返回结果进行封装和序列化，并写入响应中
 * 请求处理器，服务提供者
 * */

// 请求处理器，讲从消费者-序列化-web服务器-请求处理器，
public class HttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 异步处理HTTP请求，并反序列化请求
        request.bodyHandler(body->{
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try{
                rpcRequest = serializer.deserialize(bytes,RpcRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            RpcResponse rpcResponse = new RpcResponse();
            // 如果请求为null，直接返回
            if(request==null){
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(request,rpcResponse,serializer);
                return;
            }

            try {
                // 根据服务名称从本地注册器中获得对应的服务实现类
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());


                /**
                 * 通过反射获得返回结果
                 * （1）先根据方法名和参数类型获得对应的方法
                 * （2）通过反射机制执行
                 * */
                Method method = implClass.getMethod(rpcRequest.getMethodName(),rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(),rpcRequest.getArgs());

                // 封装结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("OK");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            // 响应
            doResponse(request,rpcResponse,serializer);

        });



    }

    private void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response().putHeader("content-type", "application/json");
        try {
            byte[] bytes = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(bytes));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }

}
