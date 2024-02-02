package com.ame.util.concurrent;


import com.ame.core.exception.PlatformException;

import java.io.Serializable;

public class BatchJobResult {

    protected Object[] messageI18Nparams;
    private boolean isSuccess;
    private String message;
    private String messageI18NKey;
    private PlatformException platformException;
    private Serializable object;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageI18NKey() {
        return messageI18NKey;
    }

    public void setMessageI18NKey(String messageI18NKey) {
        this.messageI18NKey = messageI18NKey;
    }

    public Object[] getMessageI18Nparams() {
        return messageI18Nparams;
    }

    public void setMessageI18Nparams(Object[] messageI18Nparams) {
        this.messageI18Nparams = messageI18Nparams;
    }

    public PlatformException getPlatformException() {
        return platformException;
    }

    public void setPlatformException(PlatformException platformException) {
        this.platformException = platformException;
    }

    public Serializable getObject() {
        return object;
    }

    public void setObject(Serializable object) {
        this.object = object;
    }
}
