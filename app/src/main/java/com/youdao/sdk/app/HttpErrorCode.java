package com.youdao.sdk.app;

/* loaded from: classes4.dex */
public enum HttpErrorCode {
    EMPTY_RESPONSE("Server returned empty response."),
    UNSPECIFICERROR("unspecified error occured."),
    REQUEST_ERROR("http request error.");
    
    private final int code;
    private final String message;

    public int getCode() {
        return this.code;
    }

    HttpErrorCode(String str) {
        this.message = str;
        this.code = 0;
    }

    HttpErrorCode(String str, int i) {
        this.message = str;
        this.code = i;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.message;
    }
}
