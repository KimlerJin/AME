package com.ame.base;

import java.io.Serializable;

public class ConfirmResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -851586290551914102L;
    private Result result = Result.OK;
    private Object obj;

    public ConfirmResult() {
        super();
    }

    public ConfirmResult(Result result) {
        super();
        this.result = result;
    }

    public ConfirmResult(Result result, Object object) {
        super();
        this.result = result;
        this.obj = object;
    }

    public ConfirmResult(Object obj) {
        super();
        this.obj = obj;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isOK() {
        return result.equals(Result.OK);
    }

    public boolean isCanceled() {
        return result.equals(Result.CANCEL);
    }

    public enum Result {
        OK, CANCEL, ERROR,SAVE_AND_CONTINUE
    }
}
