package com.tomatolive.library.download;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.download.-$$Lambda$DownLoadRetrofit$zxOJ7ZXEdWEJptYCXR9uSPYv7Rw  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$DownLoadRetrofit$zxOJ7ZXEdWEJptYCXR9uSPYv7Rw implements HostnameVerifier {
    public static final /* synthetic */ $$Lambda$DownLoadRetrofit$zxOJ7ZXEdWEJptYCXR9uSPYv7Rw INSTANCE = new $$Lambda$DownLoadRetrofit$zxOJ7ZXEdWEJptYCXR9uSPYv7Rw();

    private /* synthetic */ $$Lambda$DownLoadRetrofit$zxOJ7ZXEdWEJptYCXR9uSPYv7Rw() {
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        return DownLoadRetrofit.lambda$new$0(str, sSLSession);
    }
}
