package com.one.tomato.mvp.base.okhttp;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class ResponseThrowable extends Exception implements Serializable {
    public int code;
    public Object data;
    public String message;

    public ResponseThrowable(Throwable th, int i) {
        super(th);
        this.code = i;
    }

    public ResponseThrowable(String str, int i) {
        this.code = i;
        this.message = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getThrowableMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object obj) {
        this.data = obj;
    }
}
