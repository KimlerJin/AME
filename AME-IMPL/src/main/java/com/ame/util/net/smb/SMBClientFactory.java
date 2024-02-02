//package com.ame.util.net.smb;
//
//import com.hierynomus.smbj.SMBClient;
//import org.apache.commons.pool2.BasePooledObjectFactory;
//import org.apache.commons.pool2.PooledObject;
//import org.apache.commons.pool2.impl.DefaultPooledObject;
//
//public class SMBClientFactory extends BasePooledObjectFactory<SMBClient> {
//    private String domain;
//    private String ip;
//    private String userName;
//    private String userPwd;
//
//    public SMBClientFactory(String domain, String ip, String userName, String userPwd) {
//        this.domain = domain;
//        this.ip = ip;
//        this.userName = userName;
//        this.userPwd = userPwd;
//    }
//
//    @Override
//    public SMBClient create() throws Exception {
//        SMBClient client = new SMBClient();
//        // 操作共享文件.
//        return client;
//    }
//
//    @Override
//    public PooledObject<SMBClient> wrap(SMBClient obj) {
//        return new DefaultPooledObject<SMBClient>(obj);
//    }
//
//    @Override
//    public boolean validateObject(PooledObject<SMBClient> p) {
//        return super.validateObject(p);
//    }
//
//    @Override
//    public void destroyObject(PooledObject<SMBClient> p) throws Exception {
//        try {
//            SMBClient client = p.getObject();
//            client.close();
//        } catch (Exception e) {
//
//        }
//    }
//
//
//}
