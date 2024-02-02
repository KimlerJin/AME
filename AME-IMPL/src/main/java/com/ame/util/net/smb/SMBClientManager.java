//package com.ame.util.net.smb;
//
//import com.ame.meperframework.core.exception.PlatformException;
//import com.hierynomus.msdtyp.AccessMask;
//import com.hierynomus.msfscc.FileAttributes;
//import com.hierynomus.mssmb2.SMB2CreateDisposition;
//import com.hierynomus.mssmb2.SMB2CreateOptions;
//import com.hierynomus.mssmb2.SMB2ShareAccess;
//import com.hierynomus.smbj.SMBClient;
//import com.hierynomus.smbj.auth.AuthenticationContext;
//import com.hierynomus.smbj.connection.Connection;
//import com.hierynomus.smbj.session.Session;
//import com.hierynomus.smbj.share.DiskShare;
//import org.apache.commons.pool2.ObjectPool;
//import org.apache.commons.pool2.impl.AbandonedConfig;
//import org.apache.commons.pool2.impl.GenericObjectPool;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//
//import java.io.*;
//import java.util.EnumSet;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SMBClientManager {
//
//    private static Map<String, ObjectPool<SMBClient>> cacheClientPool = new HashMap<>();
//
//    public static ObjectPool<SMBClient> getClientPool(String ip, String domain, String userName, String userPwd) {
//        String key = domain + ip + userName + userPwd;
//        if (cacheClientPool.containsKey(key)) {
//            return cacheClientPool.get(key);
//        } else {
//            SMBClientFactory smbClientFactory = new SMBClientFactory(domain, ip, userName, userPwd);
//            GenericObjectPoolConfig<SMBClient> objectPoolConfig = new GenericObjectPoolConfig<>();
//            objectPoolConfig.setMaxTotal(10);
//            objectPoolConfig.setMaxIdle(10);
//            objectPoolConfig.setMinIdle(0);
//            objectPoolConfig.setMaxWaitMillis(5000);
//            objectPoolConfig.setBlockWhenExhausted(true);
//            objectPoolConfig.setTestOnBorrow(true);
//            AbandonedConfig abandonedConfig = new AbandonedConfig();
//            abandonedConfig.setRemoveAbandonedTimeout(60);
//            GenericObjectPool<SMBClient> objectPool =
//                    new GenericObjectPool<>(smbClientFactory, objectPoolConfig, abandonedConfig);
//            cacheClientPool.put(key, objectPool);
//            return objectPool;
//        }
//    }
//
//    public static void upload(String ip, String rootPath, String domain, String userName, String userPwd, String filename, byte[] fileBytes) throws Exception {
//        Connection connection = null;
//        Session session = null;
//        DiskShare diskShare = null;
//        OutputStream shareOs = null;
//        try {
//            ObjectPool<SMBClient> clientPool = getClientPool(ip, domain, userName, userPwd);
//            SMBClient client = clientPool.borrowObject();
//            if (client == null) {
//                throw new PlatformException("share file client is null");
//            }
//            connection = client.connect(ip);
//            // 创建连接会话.
//            AuthenticationContext authenticationContext = new AuthenticationContext(userName, userPwd.toCharArray(), domain);
//            session = connection.authenticate(authenticationContext);
//            // 操作共享文件.
//            String suffixPath = "";
//            if (rootPath.contains("/") || rootPath.contains("\\")) {
//                rootPath = rootPath.replaceAll("\\\\", "/");
//                diskShare = (DiskShare) session.connectShare(rootPath.substring(0, rootPath.indexOf("/")));
//                suffixPath = rootPath.substring(rootPath.indexOf("/") + 1);
//                filename = suffixPath + "/" + filename;
//            } else {
//                diskShare = (DiskShare) session.connectShare(rootPath);
//            }
//            if (filename.contains("/") || filename.contains("\\")) {
//                filename = filename.replaceAll("\\\\", "/");
//                String[] filepathArr = filename.split("/");
//                String vfilepath = "";
//                for (int index = 0; index < filepathArr.length - 1; index++) {
//                    vfilepath += filepathArr[index] + "/";
//                    if (!diskShare.folderExists(vfilepath)) {
//                        diskShare.mkdir(vfilepath);
//                    }
//                }
//            }
//            // 获取文件流.上传文件.
//            File shareFile = diskShare.openFile(filename, EnumSet.of(AccessMask.GENERIC_WRITE),
//                    EnumSet.of(FileAttributes.FILE_ATTRIBUTE_NORMAL), SMB2ShareAccess.ALL,
//                    SMB2CreateDisposition.FILE_OPEN_IF, EnumSet.noneOf(SMB2CreateOptions.class));
//            shareOs = shareFile.getOutputStream();
//            shareOs.write(fileBytes);
//            clientPool.returnObject(client);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (shareOs != null) {
//                shareOs.close();
//            }
//            if (diskShare != null) {
//                diskShare.close();
//            }
//            if (session != null) {
//                session.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//
//
//    public static InputStream getInputStream(String ip, String rootPath, String domain, String userName, String userPwd, String filename) throws Exception {
//        Connection connection = null;
//        Session session = null;
//        DiskShare diskShare = null;
//        ObjectPool<SMBClient> clientPool = null;
//        SMBClient client = null;
//        ByteArrayOutputStream bos = null;
//        InputStream ins = null;
//        try {
//            clientPool = getClientPool(ip, domain, userName, userPwd);
//            client = clientPool.borrowObject();
//            if (client == null) {
//                throw new PlatformException("share file client is null");
//            }
//            connection = client.connect(ip);
//            // 创建连接会话.
//            AuthenticationContext authenticationContext = new AuthenticationContext(userName, userPwd.toCharArray(), domain);
//            session = connection.authenticate(authenticationContext);
//            // 操作共享文件.
//            String suffixPath = "";
//            if (rootPath.contains("/") || rootPath.contains("\\")) {
//                rootPath = rootPath.replaceAll("\\\\", "/");
//                diskShare = (DiskShare) session.connectShare(rootPath.substring(0, rootPath.indexOf("/")));
//                suffixPath = rootPath.substring(rootPath.indexOf("/") + 1);
//                filename = suffixPath + "/" + filename;
//            } else {
//                diskShare = (DiskShare) session.connectShare(rootPath);
//            }
//            // 获取文件流.上传文件.
//            File shareFile = diskShare.openFile(filename, EnumSet.of(AccessMask.GENERIC_READ),
//                    EnumSet.of(FileAttributes.FILE_ATTRIBUTE_NORMAL), SMB2ShareAccess.ALL,
//                    SMB2CreateDisposition.FILE_OPEN_IF, EnumSet.noneOf(SMB2CreateOptions.class));
//            ins = shareFile.getInputStream();
//            //将共享文件的流用另一个流去接收，然后就可以执行finally关闭连接等操作，如果直接返回共享文件的流会报错
//            bos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[4096];
//            int len;
//            while ((len = ins.read(buffer, 0, buffer.length)) != -1) {
//                bos.write(buffer, 0, len);
//            }
//            return new ByteArrayInputStream(bos.toByteArray());
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (clientPool != null) {
//                clientPool.returnObject(client);
//            }
//            if (diskShare != null) {
//                diskShare.close();
//            }
//            if (session != null) {
//                session.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//            if (bos != null) {
//                bos.close();
//            }
//            if (ins != null) {
//                ins.close();
//            }
//        }
//    }
//
//
//    public static void rename(String ip, String rootPath, String domain, String userName, String userPwd, String oldFileUrl, String newFileName) throws Exception {
//        Connection connection = null;
//        Session session = null;
//        DiskShare diskShare = null;
//        try {
//            ObjectPool<SMBClient> clientPool = getClientPool(ip, domain, userName, userPwd);
//            SMBClient client = clientPool.borrowObject();
//            if (client == null) {
//                throw new PlatformException("share file client is null");
//            }
//            connection = client.connect(ip);
//            // 创建连接会话.
//            AuthenticationContext authenticationContext = new AuthenticationContext(userName, userPwd.toCharArray(), domain);
//            session = connection.authenticate(authenticationContext);
//            // 操作共享文件.
//            String suffixPath = "";
//            if (rootPath.contains("/") || rootPath.contains("\\")) {
//                rootPath = rootPath.replaceAll("\\\\", "/");
//                diskShare = (DiskShare) session.connectShare(rootPath.substring(0, rootPath.indexOf("/")));
//                suffixPath = rootPath.substring(rootPath.indexOf("/") + 1) + "/";
//            } else {
//                diskShare = (DiskShare) session.connectShare(rootPath);
//            }
//            if (oldFileUrl.contains("/") || oldFileUrl.contains("\\")) {
//                oldFileUrl = suffixPath + oldFileUrl.replaceAll("\\\\", "/");
//            }
//            if (newFileName.contains("/") || newFileName.contains("\\")) {
//                newFileName = suffixPath + newFileName.replaceAll("\\\\", "/");
//            }
//            // 获取文件流.上传文件.
//            File shareFile = diskShare.openFile(oldFileUrl, EnumSet.of(AccessMask.GENERIC_WRITE),
//                    EnumSet.of(FileAttributes.FILE_ATTRIBUTE_NORMAL), SMB2ShareAccess.ALL,
//                    SMB2CreateDisposition.FILE_OPEN_IF, EnumSet.noneOf(SMB2CreateOptions.class));
//            shareFile.rename(newFileName);
//            clientPool.returnObject(client);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (diskShare != null) {
//                diskShare.close();
//            }
//            if (session != null) {
//                session.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//
//    public static void delete(String ip, String rootPath, String domain, String userName, String userPwd, String filename) throws Exception {
//        Connection connection = null;
//        Session session = null;
//        DiskShare diskShare = null;
//        try {
//            ObjectPool<SMBClient> clientPool = getClientPool(ip, domain, userName, userPwd);
//            SMBClient client = clientPool.borrowObject();
//            if (client == null) {
//                throw new PlatformException("share file client is null");
//            }
//            connection = client.connect(ip);
//            // 创建连接会话.
//            AuthenticationContext authenticationContext = new AuthenticationContext(userName, userPwd.toCharArray(), domain);
//            session = connection.authenticate(authenticationContext);
//            // 操作共享文件.
//            String suffixPath = "";
//            if (rootPath.contains("/") || rootPath.contains("\\")) {
//                rootPath = rootPath.replaceAll("\\\\", "/");
//                diskShare = (DiskShare) session.connectShare(rootPath.substring(0, rootPath.indexOf("/")));
//                suffixPath = rootPath.substring(rootPath.indexOf("/") + 1);
//                filename = suffixPath + "/" + filename;
//            } else {
//                diskShare = (DiskShare) session.connectShare(rootPath);
//            }
//            // 获取文件流.上传文件.
//            diskShare.rm(filename);
//            clientPool.returnObject(client);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (diskShare != null) {
//                diskShare.close();
//            }
//            if (session != null) {
//                session.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//
//
//
//
//
//    public static void testLink(String ip, String domain, String userName, String userPwd) throws Exception {
//        Connection connection = null;
//        Session session = null;
//        DiskShare diskShare = null;
//        try {
//            ObjectPool<SMBClient> clientPool = getClientPool(ip, domain, userName, userPwd);
//            SMBClient client = clientPool.borrowObject();
//            if (client == null) {
//                throw new PlatformException("share file client is null");
//            }
//            connection = client.connect(ip);
//            // 创建连接会话.
//            AuthenticationContext authenticationContext = new AuthenticationContext(userName, userPwd.toCharArray(), domain);
//            session = connection.authenticate(authenticationContext);
//
//        } catch (Exception e) {
//            throw new PlatformException("fail connected:" + e.getMessage());
//        } finally {
//            if (diskShare != null) {
//                diskShare.close();
//            }
//            if (session != null) {
//                session.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//}
