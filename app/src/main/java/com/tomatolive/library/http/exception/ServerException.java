package com.tomatolive.library.http.exception;

/* loaded from: classes3.dex */
public class ServerException extends RuntimeException {
    private int code;
    private String msg;

    public ServerException(int i, String str) {
        this.code = i;
        this.msg = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
