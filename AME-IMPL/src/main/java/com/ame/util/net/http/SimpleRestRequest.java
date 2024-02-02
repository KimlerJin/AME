//package com.ame.util.net.http;
//
//import com.ame.meperframework.core.constant.ErrorCodeConstants;
//import com.ame.meperframework.core.exception.PlatformException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.base.Strings;
//import okhttp3.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//
//public class SimpleRestRequest {
//
//    private static Logger logger = LoggerFactory.getLogger(SimpleRestRequest.class);
//
//    private static OkHttpClient okHttpClient = OkHttpClientHelper.createHttpsUsedClient();
//    private static ObjectMapper mapper = new ObjectMapper();
//    private Request.Builder requestBuilder = new Request.Builder();
//
//    private SimpleRestRequest() {}
//
//    public void call() {
//        try {
//            Call call = okHttpClient.newCall(requestBuilder.build());
//            Response response = call.execute();
//            if (response.isSuccessful()) {
//                return;
//            } else {
//            }
//        } catch (IOException e) {
//        }
//    }
//
//    public <T> T call(Class<T> clazz) {
//        try {
//            Call call = okHttpClient.newCall(requestBuilder.build());
//            Response response = call.execute();
//            if (response.isSuccessful()) {
//                String responseBody = response.body().string();
//                return Strings.isNullOrEmpty(responseBody) ? null : mapper.readValue(responseBody, clazz);
//            } else {
//                logger.error("okhttp3 request error!", response.message());
//                throw new PlatformException(ErrorCodeConstants.HTTP_REQUEST_ERROR, "Http Request Error");
//            }
//        } catch (IOException e) {
//            logger.error("", e);
//            throw new PlatformException(ErrorCodeConstants.UNKNOWN_ERROR, "UnKnown Error");
//        }
//    }
//
//    // public static void main(String[] args) {
//    // HttpUrl url = new HttpUrl.Builder()
//    // .scheme("https")
//    // .host("www.google.com")
//    // .addPathSegment("search1")
//    // .addPathSegment("search2")
//    // .addQueryParameter("q", "polar bears")
//    // .build();
//    // System.out.println(url);
//
//    // SimpleRestRequest build = new Builder().https()
//    // .host("www.jianshu.com")
//    // .addPathSegments("notes/2c07fbb52b45/mark_viewed.json")
//    // .postJson("{}")
//    // .build();
//    // }
//
//    public static class Builder {
//
//        SimpleRestRequest simpleRestRequest = new SimpleRestRequest();
//
//        private HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder();
//
//        public Builder() {
//            httpUrlBuilder.scheme("http");
//        }
//
//        public Builder https() {
//            httpUrlBuilder.scheme("https");
//            return this;
//        }
//
//        public Builder host(String host) {
//            httpUrlBuilder.host(host);
//            return this;
//        }
//
//        public Builder port(int port) {
//            httpUrlBuilder.port(port);
//            return this;
//        }
//
//        public Builder addPathSegment(String... pathSegment) {
//            for (int i = 0; i < pathSegment.length; i++) {
//                httpUrlBuilder.addPathSegment(pathSegment[i]);
//            }
//            return this;
//        }
//
//        public Builder addPathSegments(String pathSegments) {
//            httpUrlBuilder.addPathSegments(pathSegments);
//            return this;
//        }
//
//        public Builder addQueryParameter(String key, String value) {
//            httpUrlBuilder.addQueryParameter(key, value);
//            return this;
//        }
//
//        public Builder header(String name, String value) {
//            simpleRestRequest.requestBuilder.header(name, value);
//            return this;
//        }
//
//        public Builder get() {
//            simpleRestRequest.requestBuilder.get();
//            return this;
//        }
//
//        public Builder postJson(String json) {
//            simpleRestRequest.requestBuilder
//                .post(RequestBody.create(MediaType.parse(com.google.common.net.MediaType.JSON_UTF_8.toString()), json));
//            return this;
//        }
//
//        public Builder putJson(String json) {
//            simpleRestRequest.requestBuilder
//                .put(RequestBody.create(MediaType.parse(com.google.common.net.MediaType.JSON_UTF_8.toString()), json));
//            return this;
//        }
//
//        public Builder delete() {
//            simpleRestRequest.requestBuilder.delete();
//            return this;
//        }
//
//        public SimpleRestRequest build() {
//            simpleRestRequest.requestBuilder.url(httpUrlBuilder.toString());
//            return simpleRestRequest;
//        }
//
//    }
//}
