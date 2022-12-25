package org.xutils.p149ex;

/* renamed from: org.xutils.ex.HttpRedirectException */
/* loaded from: classes4.dex */
public class HttpRedirectException extends HttpException {
    private static final long serialVersionUID = 1;

    public HttpRedirectException(int i, String str, String str2) {
        super(i, str);
        setResult(str2);
    }
}
