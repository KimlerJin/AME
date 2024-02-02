//package com.ame.util.net.ftp;
//
//import com.ame.meperframework.core.constant.ErrorCodeConstants;
//import com.ame.meperframework.core.exception.PlatformException;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.pool2.ObjectPool;
//import org.apache.commons.pool2.impl.AbandonedConfig;
//import org.apache.commons.pool2.impl.GenericObjectPool;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class FTPClientManager {
//
//    private static Map<String, ObjectPool<FTPClient>> cacheClientPool = new HashMap<>();
//
//    public static ObjectPool<FTPClient> getClientPool(String url, String username, String password) {
//        String key = url + username + password;
//        if (cacheClientPool.containsKey(key)) {
//            return cacheClientPool.get(key);
//        } else {
//            FTPClientFactory ftpClientFactory = null;
//            String[] urlStrs = url.split(":");
//            if (urlStrs.length == 1) {
//                ftpClientFactory = new FTPClientFactory(urlStrs[0], username, password);
//            } else if (urlStrs.length == 2) {
//                ftpClientFactory = new FTPClientFactory(urlStrs[0], Integer.valueOf(urlStrs[1]), username, password);
//            } else {
//                throw new PlatformException(ErrorCodeConstants.FTP_SERVER_URL_INCORRECT, "Ftp server url is incorrect");
//            }
//            GenericObjectPoolConfig<FTPClient> objectPoolConfig = new GenericObjectPoolConfig<>();
//            objectPoolConfig.setMaxTotal(10);
//            objectPoolConfig.setMaxIdle(10);
//            objectPoolConfig.setMinIdle(0);
//            objectPoolConfig.setMaxWaitMillis(5000);
//            objectPoolConfig.setBlockWhenExhausted(true);
//            objectPoolConfig.setTestOnBorrow(true);
//
//            AbandonedConfig abandonedConfig = new AbandonedConfig();
//            abandonedConfig.setRemoveAbandonedTimeout(60);
//            GenericObjectPool<FTPClient> objectPool =
//                new GenericObjectPool<>(ftpClientFactory, objectPoolConfig, abandonedConfig);
//            cacheClientPool.put(key, objectPool);
//            return objectPool;
//        }
//    }
//
//}
