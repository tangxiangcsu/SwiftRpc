package com.swiftrpc.swift_rpc.serializer;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftrpc.swift_rpc.model.RpcRequest;
import com.swiftrpc.swift_rpc.model.RpcResponse;

/**
 * @PACKAGE_NAME: Serializer.serializer
 * @NAME: JsonSerializer
 * @USER: tangxiang
 * @DATE: 2024/7/13
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION: 实现Json序列化机制，需要解决Object在序列化以后会丢失数据类型
 **/
public class JsonSerializer implements Serializer{

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes,type);
        if(obj instanceof RpcRequest){// 反序列化为 RpcRequest
            return handleRequest((RpcRequest) obj, type);
        }
        if(obj instanceof RpcResponse){// 反序列化为 RpcResponse
            return handleResponse((RpcResponse) obj, type);
        }
        return obj;
    }

    private <T>T handleRequest(RpcRequest request, Class<T> type) throws IOException{
        Class<?>[] parametertypes = request.getParameterTypes();
        Object[] args = request.getArgs();
        for(int i=0;i<parametertypes.length;i++){
            Class<?> clazz = parametertypes[i];
            if(!clazz.isAssignableFrom(args[i].getClass())){//如果类型不一致，需要重新处理一下类型
                byte[] argsByte = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argsByte,clazz);
            }
        }
        return type.cast(request);
    }

    private <T> T handleResponse(RpcResponse response, Class<T> type) throws IOException {
        byte[] dataByte = OBJECT_MAPPER.writeValueAsBytes(response.getData());
        response.setData(OBJECT_MAPPER.readValue(dataByte,response.getDataType()));
        return type.cast(response);
    }
}
