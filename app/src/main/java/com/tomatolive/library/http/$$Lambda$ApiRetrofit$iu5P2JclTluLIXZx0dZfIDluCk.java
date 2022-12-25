package com.tomatolive.library.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.http.-$$Lambda$ApiRetrofit$iu-5P2JclTluLIXZx0dZfIDluCk  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ApiRetrofit$iu5P2JclTluLIXZx0dZfIDluCk implements HostnameVerifier {
    public static final /* synthetic */ $$Lambda$ApiRetrofit$iu5P2JclTluLIXZx0dZfIDluCk INSTANCE = new $$Lambda$ApiRetrofit$iu5P2JclTluLIXZx0dZfIDluCk();

    private /* synthetic */ $$Lambda$ApiRetrofit$iu5P2JclTluLIXZx0dZfIDluCk() {
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        return ApiRetrofit.lambda$new$0(str, sSLSession);
    }
}
