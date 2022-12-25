package com.alipay.android.phone.mrpc.core;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

/* renamed from: com.alipay.android.phone.mrpc.core.e */
/* loaded from: classes2.dex */
final class C0894e extends DefaultRedirectHandler {

    /* renamed from: a */
    int f774a;

    /* renamed from: b */
    final /* synthetic */ C0893d f775b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C0894e(C0893d c0893d) {
        this.f775b = c0893d;
    }

    public final boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
        int statusCode;
        this.f774a++;
        boolean isRedirectRequested = super.isRedirectRequested(httpResponse, httpContext);
        if (isRedirectRequested || this.f774a >= 5 || !((statusCode = httpResponse.getStatusLine().getStatusCode()) == 301 || statusCode == 302)) {
            return isRedirectRequested;
        }
        return true;
    }
}
