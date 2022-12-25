package p007b.p025d.p026a.p029c;

import com.gen.p059mh.webapps.listener.WebAppInformation;
import com.zzz.ipfssdk.LogUtil;
import p007b.p025d.p026a.p028b.C0759a;

/* renamed from: b.d.a.c.a */
/* loaded from: classes2.dex */
public class C0762a {

    /* renamed from: a */
    public static C0762a f509a = new C0762a();

    /* renamed from: b */
    public int f510b = 5242880;

    /* renamed from: b */
    public static final C0762a m5166b() {
        return f509a;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0036, code lost:
        if (r10.f510b <= 0) goto L8;
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m5167a() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        if (C0759a.f501c + j + (C0759a.f500b * WebAppInformation.maxCacheSize) >= maxMemory) {
            this.f510b = (int) ((((maxMemory - j) - C0759a.f501c) * 1.0d) / (C0759a.f500b * 2));
        }
        this.f510b = 5242880;
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "maxMemory = " + ((maxMemory / 1024.0d) / 1024.0d) + "M");
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "totalMemory = " + ((((double) j) / 1024.0d) / 1024.0d) + "M");
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "app_reserved_memory = " + ((((double) C0759a.f501c) / 1024.0d) / 1024.0d) + "M");
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "ringBufferMemorySize = " + ((((double) this.f510b) / 1024.0d) / 1024.0d) + "M");
    }

    /* renamed from: c */
    public int m5165c() {
        return this.f510b;
    }
}
