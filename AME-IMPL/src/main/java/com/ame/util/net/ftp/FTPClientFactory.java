//package com.ame.util.net.ftp;
//
//
//
//public class FTPClientFactory extends BasePooledObjectFactory<FTPClient> {
//
//    private static String LOCAL_CHARSET = "GBK";
//    private String url;
//    private int port = -1;
//    private String name;
//    private String password;
//
//    public FTPClientFactory(String url, String name, String password) {
//        this.url = url;
//        this.name = name;
//        this.password = password;
//    }
//
//    public FTPClientFactory(String url, int port, String name, String password) {
//        this.url = url;
//        this.port = port;
//        this.name = name;
//        this.password = password;
//    }
//
//    @Override
//    public FTPClient create() throws Exception {
//        FTPClient ftpClient = new FTPClient();
//        if (port < 0) {
//            ftpClient.connect(url);
//        } else {
//            ftpClient.connect(url, port);
//        }
//        int reply = ftpClient.getReplyCode();
//        if (!FTPReply.isPositiveCompletion(reply)) {
//            ftpClient.disconnect();
//            throw new PlatformException(ErrorCodeConstants.FTP_SERVER_REFUSED_CONNECTION,
//                "FTP server refused connection");
//        }
//        if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
//            LOCAL_CHARSET = "UTF-8";
//        }
//        boolean loginSuccess = ftpClient.login(name, password);
//        ftpClient.setControlEncoding(LOCAL_CHARSET);
//        ftpClient.enterLocalPassiveMode();
//        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//        if (!loginSuccess) {
//            throw new PlatformException(ErrorCodeConstants.FTP_SERVER_LOGIN_FAILURE, "Can not login FTP Server");
//        }
//        return ftpClient;
//    }
//
//    @Override
//    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
//        return new DefaultPooledObject<FTPClient>(ftpClient);
//    }
//
//    @Override
//    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
//        FTPClient ftpClient = p.getObject();
//        if (ftpClient.isConnected()) {
//            ftpClient.disconnect();
//        }
//    }
//
//    @Override
//    public boolean validateObject(PooledObject<FTPClient> p) {
//        FTPClient ftpClient = p.getObject();
//        return ftpClient.isAvailable();
//    }
//
//    @Override
//    public void activateObject(PooledObject<FTPClient> p) throws Exception {
//        FTPClient ftpClient = p.getObject();
//        ftpClient.changeWorkingDirectory("/");
//    }
//
//}
