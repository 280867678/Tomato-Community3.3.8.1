package com.tencent.liteav.network.p128a;

/* renamed from: com.tencent.liteav.network.a.d */
/* loaded from: classes3.dex */
public final class NetworkInfo {

    /* renamed from: a */
    public static final NetworkInfo f4806a = new NetworkInfo(EnumC3572a.NO_NETWORK, 0);

    /* renamed from: b */
    public static final NetworkInfo f4807b = new NetworkInfo(EnumC3572a.WIFI, 0);

    /* renamed from: c */
    public final int f4808c;

    /* renamed from: d */
    public final EnumC3572a f4809d;

    /* compiled from: NetworkInfo.java */
    /* renamed from: com.tencent.liteav.network.a.d$a */
    /* loaded from: classes3.dex */
    public enum EnumC3572a {
        NO_NETWORK,
        WIFI,
        MOBILE
    }

    public NetworkInfo(EnumC3572a enumC3572a, int i) {
        this.f4809d = enumC3572a;
        this.f4808c = i;
    }
}
