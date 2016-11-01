package com.iware.webshiro.enums;

/**
 * Created by wuhao1 on 2016/10/31.
 */
public class ResponeVo<T> {

    private String errcode;

    private String errmsg;

    private T result;


    public ResponeVo(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public ResponeVo(String errcode, String errmsg, T result) {
        this(errcode,errmsg);
        this.result = result;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
