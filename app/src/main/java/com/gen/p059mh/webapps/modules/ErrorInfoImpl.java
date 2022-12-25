package com.gen.p059mh.webapps.modules;

import com.gen.p059mh.webapps.listener.IErrorInfo;

/* renamed from: com.gen.mh.webapps.modules.ErrorInfoImpl */
/* loaded from: classes2.dex */
public class ErrorInfoImpl implements IErrorInfo {
    private String appId;
    private int code;
    private String message;

    private ErrorInfoImpl(int i, String str) {
        this.code = i;
        this.message = str;
    }

    public static ErrorInfoImpl newInstance(int i, String str) {
        return new ErrorInfoImpl(i, str);
    }

    @Override // com.gen.p059mh.webapps.listener.IErrorInfo
    public int getCode() {
        return this.code;
    }

    @Override // com.gen.p059mh.webapps.listener.IErrorInfo
    public String getMessage() {
        return this.message;
    }

    @Override // com.gen.p059mh.webapps.listener.IErrorInfo
    public String getAppId() {
        return this.appId;
    }

    @Override // com.gen.p059mh.webapps.listener.IErrorInfo
    public void setAppInfo(String str) {
        this.appId = str;
    }

    @Override // com.gen.p059mh.webapps.listener.IErrorInfo
    public String toString() {
        return this.appId + " | " + this.code + " | " + this.message;
    }
}
