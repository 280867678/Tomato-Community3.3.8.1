package com.tomatolive.library.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.http.-$$Lambda$EventReportRetrofit$3F2SM5e_EpoL_2LEJCOXC8L6zL8  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$EventReportRetrofit$3F2SM5e_EpoL_2LEJCOXC8L6zL8 implements HostnameVerifier {
    public static final /* synthetic */ $$Lambda$EventReportRetrofit$3F2SM5e_EpoL_2LEJCOXC8L6zL8 INSTANCE = new $$Lambda$EventReportRetrofit$3F2SM5e_EpoL_2LEJCOXC8L6zL8();

    private /* synthetic */ $$Lambda$EventReportRetrofit$3F2SM5e_EpoL_2LEJCOXC8L6zL8() {
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        return EventReportRetrofit.lambda$new$0(str, sSLSession);
    }
}
