package com.tomatolive.library.http.exception;

/* loaded from: classes3.dex */
public class ApiException extends Exception {
    private int code;
    private String msg;

    public ApiException(Throwable th, int i) {
        super(th);
        this.code = i;
    }

    public ApiException(int i, String str) {
        this.code = i;
        this.msg = str;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }
}
