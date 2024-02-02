package com.ame.core.exception;

public class PlatformException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected Object[] params;
    private String errorCode;

    public PlatformException(Exception ex) {
        super(ex.getMessage(),ex);
        if(ex instanceof  PlatformException){
            setErrorCode(((PlatformException) ex).getErrorCode());
            setParams(((PlatformException) ex).getParams());
        }
    }

    public PlatformException(String errorMessage) {
        super(errorMessage);
    }

    public PlatformException(Throwable cause) {
        super(cause);
    }

    public PlatformException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public PlatformException(String errorCode, String errorMessage, Object... params) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.params = params;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

}
