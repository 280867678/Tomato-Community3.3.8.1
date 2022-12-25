package com.tomatolive.library.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.http.-$$Lambda$EventConfigRetrofit$kyqQ-e6KDavX2fi_o2g7hzvs44A  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$EventConfigRetrofit$kyqQe6KDavX2fi_o2g7hzvs44A implements HostnameVerifier {
    public static final /* synthetic */ $$Lambda$EventConfigRetrofit$kyqQe6KDavX2fi_o2g7hzvs44A INSTANCE = new $$Lambda$EventConfigRetrofit$kyqQe6KDavX2fi_o2g7hzvs44A();

    private /* synthetic */ $$Lambda$EventConfigRetrofit$kyqQe6KDavX2fi_o2g7hzvs44A() {
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        return EventConfigRetrofit.lambda$new$0(str, sSLSession);
    }
}
