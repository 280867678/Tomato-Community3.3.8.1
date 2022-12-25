package com.tomatolive.library.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.http.-$$Lambda$StatisticsApiRetrofit$t5GqMCVCsVzyqcEvlSFC0oBv6xc  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$StatisticsApiRetrofit$t5GqMCVCsVzyqcEvlSFC0oBv6xc implements HostnameVerifier {
    public static final /* synthetic */ $$Lambda$StatisticsApiRetrofit$t5GqMCVCsVzyqcEvlSFC0oBv6xc INSTANCE = new $$Lambda$StatisticsApiRetrofit$t5GqMCVCsVzyqcEvlSFC0oBv6xc();

    private /* synthetic */ $$Lambda$StatisticsApiRetrofit$t5GqMCVCsVzyqcEvlSFC0oBv6xc() {
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        return StatisticsApiRetrofit.lambda$new$0(str, sSLSession);
    }
}
