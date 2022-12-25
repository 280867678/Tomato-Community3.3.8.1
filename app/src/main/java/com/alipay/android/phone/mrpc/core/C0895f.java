package com.alipay.android.phone.mrpc.core;

import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

/* renamed from: com.alipay.android.phone.mrpc.core.f */
/* loaded from: classes2.dex */
final class C0895f implements ConnectionKeepAliveStrategy {

    /* renamed from: a */
    final /* synthetic */ C0893d f776a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C0895f(C0893d c0893d) {
        this.f776a = c0893d;
    }

    public final long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        return 180000L;
    }
}
