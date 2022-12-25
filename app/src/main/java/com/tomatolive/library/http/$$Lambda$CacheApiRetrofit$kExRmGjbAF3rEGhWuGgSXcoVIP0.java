package com.tomatolive.library.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.http.-$$Lambda$CacheApiRetrofit$kExRmGjbAF3rEGhWuGgSXcoVIP0  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$CacheApiRetrofit$kExRmGjbAF3rEGhWuGgSXcoVIP0 implements HostnameVerifier {
    public static final /* synthetic */ $$Lambda$CacheApiRetrofit$kExRmGjbAF3rEGhWuGgSXcoVIP0 INSTANCE = new $$Lambda$CacheApiRetrofit$kExRmGjbAF3rEGhWuGgSXcoVIP0();

    private /* synthetic */ $$Lambda$CacheApiRetrofit$kExRmGjbAF3rEGhWuGgSXcoVIP0() {
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        return CacheApiRetrofit.lambda$new$0(str, sSLSession);
    }
}
