package com.ame.shiro;


import com.ame.core.exception.PlatformException;

public class StopAuthenticatingException extends PlatformException {

    /**
     *
     */
    private static final long serialVersionUID = 5125577562665651601L;

    public StopAuthenticatingException(Exception ex){
        super(ex);
    }

    public StopAuthenticatingException(String errorMessage) {
        super(errorMessage);
    }

    public StopAuthenticatingException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
