//package com.ame.util.net.http;
//
//import com.ame.meperframework.core.constant.ErrorCodeConstants;
//import com.ame.meperframework.core.exception.PlatformException;
//import okhttp3.OkHttpClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.security.cert.X509Certificate;
//import java.util.concurrent.TimeUnit;
//
//public class OkHttpClientHelper {
//
//    private static Logger logger = LoggerFactory.getLogger(OkHttpClientHelper.class);
//
//    public static OkHttpClient createClient() {
//        return new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS)
//            .readTimeout(20, TimeUnit.SECONDS).build();
//    }
//
//    public static OkHttpClient createHttpsUsedClient() {
//        try {
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            X509TrustManager x509TrustManager = new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}
//
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return new X509Certificate[0];
//                }
//            };
//            sslContext.init(null, new TrustManager[] {x509TrustManager}, null);
//            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            return new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS).hostnameVerifier((s, sslSession) -> true)
//                .sslSocketFactory(sslSocketFactory, x509TrustManager).build();
//        } catch (Exception e) {
//            logger.error("", e);
//            throw new PlatformException(ErrorCodeConstants.UNKNOWN_ERROR, "UnKnown Error");
//        }
//    }
//
//}
