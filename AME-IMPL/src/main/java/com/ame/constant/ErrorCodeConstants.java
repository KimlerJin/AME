package com.ame.constant;

public interface ErrorCodeConstants {

    /**
     * 未知错误
     */
    String UNKNOWN_ERROR = "common.error.unknow.error";
    /**
     * FTP服务器拒绝连接
     */
    String FTP_SERVER_REFUSED_CONNECTION = "common.error.ftp.server.refused.connection";
    /**
     * 无法登录FTP服务器
     */
    String FTP_SERVER_LOGIN_FAILURE = "common.error.ftp.server.login.failure";

    /**
     * FTP服务器
     */
    String FTP_SERVER_URL_INCORRECT = "common.error.ftp.server.url.incorrect";

    /**
     * Can not get the local ip/mac address
     */
    String GET_LOCAL_MAC_IP_ADDRESS_ERROR = "common.error.get.local.mac.ip.address";

    String HTTP_REQUEST_ERROR = "common.error.http.request.error";
}
