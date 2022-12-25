package com.zzhoujay.richtext.exceptions;

/* loaded from: classes4.dex */
public class HttpResponseCodeException extends RuntimeException {
    public HttpResponseCodeException(int i) {
        super("Http Response Code is :" + i);
    }
}
