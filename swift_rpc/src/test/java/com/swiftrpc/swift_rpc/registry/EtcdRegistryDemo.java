package com.swiftrpc.swift_rpc.registry;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.registry
 * @NAME: EtcdRegistry
 * @USER: tangxiang
 * @DATE: 2024/7/14
 * @DESCRIPTION:
 **/
public class EtcdRegistryDemo {
    @Test
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Client client = Client.builder().endpoints("http://localhost:2379").build();
        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());
        kvClient.put(key,value).get();
        CompletableFuture<GetResponse> getFuture = kvClient.get(key);
        kvClient.delete(key).get();
    }
}
