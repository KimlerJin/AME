package com.ame.dto;

import java.io.Serializable;


public class RestResponse<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4150800751579236573L;

//    @Schema(description = "响应的代码, 1-表示调用成功,0-表示调用失败")
    private int code;

//    @Schema(description = "响应的状态, true-表示调用成功,false-表示调用失败")
    private boolean success;

//    @Schema(description = "调用返回的消息，已经完成了国际化")
    private String message;

//    @Schema(description = "调用返回的业务数据")
    private T data;

//    @Schema(description = "调用的路径, url")
    private String path;

//    @Schema(description = "调用时的异常信息")
    private String exception;

    public RestResponse() {

    }

    public RestResponse(int code, boolean success, String message, T data) {
        super();
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public RestResponse(int code, boolean success, String message, T data, String path, String exception) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
        this.path = path;
        this.exception = exception;
    }

    public static <T> RestResponseBuilder<T> builder() {
        return new RestResponseBuilder<>();
    }

    public static <T> RestResponseBuilder<T> builderDefault(T data){
        return (RestResponseBuilder<T>) RestResponse.builder().success(true).data(data).code(RestResponseCode.OK);
    }

    public int getCode() {
        return code;
    }

    public RestResponse<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public RestResponse<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public RestResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getPath() {
        return path;
    }

    public RestResponse<T> setPath(String path) {
        this.path = path;
        return this;
    }

    public String getException() {
        return exception;
    }

    public RestResponse<T> setException(String exception) {
        this.exception = exception;
        return this;
    }

    public static class RestResponseBuilder<T> {
        private int code;
        private boolean success;
        private String message;
        private T data;
        private String path;
        private String exception;

        public RestResponseBuilder() {}

        public RestResponseBuilder<T> code(int code) {
            this.code = code;
            return this;
        }

        public RestResponseBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public RestResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public RestResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public RestResponseBuilder<T> path(String path) {
            this.path = path;
            return this;
        }

        public RestResponseBuilder<T> exception(String exception) {
            this.exception = exception;
            return this;
        }

        public RestResponse<T> build() {
            return new RestResponse<T>(this.code, this.success, this.message, this.data, this.path, this.exception);
        }

    }
}
