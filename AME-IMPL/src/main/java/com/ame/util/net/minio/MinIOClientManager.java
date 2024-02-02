//package com.ame.util.net.minio;
//
//import io.minio.MinioClient;
//import org.apache.commons.pool2.ObjectPool;
//import org.apache.commons.pool2.impl.AbandonedConfig;
//import org.apache.commons.pool2.impl.GenericObjectPool;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MinIOClientManager {
//
//    private static Map<String, ObjectPool<MinioClient>> cacheClientPool = new HashMap<>();
//
//    public static ObjectPool<MinioClient> getClientPool(String endpoint, String bucket, String accessKey, String secretKey) {
//        String key = endpoint + bucket + accessKey + secretKey;
//        if (cacheClientPool.containsKey(key)) {
//            return cacheClientPool.get(key);
//        } else {
//            MinIOClientFactory minIOClientFactory = new MinIOClientFactory(endpoint, bucket, accessKey, secretKey);
//            GenericObjectPoolConfig<MinioClient> objectPoolConfig = new GenericObjectPoolConfig<>();
//            objectPoolConfig.setMaxTotal(10);
//            objectPoolConfig.setMaxIdle(10);
//            objectPoolConfig.setMinIdle(0);
//            objectPoolConfig.setMaxWaitMillis(5000);
//            objectPoolConfig.setBlockWhenExhausted(true);
//            objectPoolConfig.setTestOnBorrow(true);
//
//            AbandonedConfig abandonedConfig = new AbandonedConfig();
//            abandonedConfig.setRemoveAbandonedTimeout(60);
//            GenericObjectPool<MinioClient> objectPool =
//                    new GenericObjectPool<>(minIOClientFactory, objectPoolConfig, abandonedConfig);
//            cacheClientPool.put(key, objectPool);
//            return objectPool;
//        }
//    }
//}
