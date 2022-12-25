package com.tencent.liteav.p118c;

import com.tencent.liteav.basic.log.TXCLog;

/* renamed from: com.tencent.liteav.c.c */
/* loaded from: classes3.dex */
public class CutTimeConfig {

    /* renamed from: b */
    private static CutTimeConfig f3339b;

    /* renamed from: a */
    private final String f3340a = "CutTimeConfig";

    /* renamed from: c */
    private long f3341c = -1;

    /* renamed from: d */
    private long f3342d = -1;

    /* renamed from: e */
    private long f3343e = -1;

    /* renamed from: f */
    private long f3344f = -1;

    /* renamed from: a */
    public static CutTimeConfig m2501a() {
        if (f3339b == null) {
            synchronized (CutTimeConfig.class) {
                if (f3339b == null) {
                    f3339b = new CutTimeConfig();
                }
            }
        }
        return f3339b;
    }

    /* renamed from: a */
    public void m2500a(long j, long j2) {
        if (j < 0 || j2 < 0) {
            TXCLog.m2914e("CutTimeConfig", "setCutTimeUs, startTimeUs or endTimeUs < 0");
        } else if (j >= j2) {
            TXCLog.m2914e("CutTimeConfig", "setCutTimeUs, start time >= end time, ignore");
        } else {
            this.f3341c = j;
            this.f3342d = j2;
        }
    }

    /* renamed from: b */
    public void m2498b(long j, long j2) {
        if (j < 0 || j2 < 0) {
            TXCLog.m2914e("CutTimeConfig", "setPlayTimeUs, startTimeUs or endTimeUs < 0");
        } else if (j >= j2) {
            TXCLog.m2914e("CutTimeConfig", "setPlayTimeUs, start time >= end time, ignore");
        } else {
            this.f3343e = j;
            this.f3344f = j2;
        }
    }

    /* renamed from: b */
    public long m2499b() {
        return this.f3341c;
    }

    /* renamed from: c */
    public long m2497c() {
        return this.f3342d;
    }

    /* renamed from: d */
    public long m2496d() {
        return this.f3343e;
    }

    /* renamed from: e */
    public long m2495e() {
        return this.f3344f;
    }

    /* renamed from: f */
    public long m2494f() {
        if (this.f3341c < 0) {
            this.f3341c = 0L;
        }
        return this.f3341c;
    }

    /* renamed from: g */
    public long m2493g() {
        if (this.f3342d < 0) {
            this.f3342d = 0L;
        }
        return this.f3342d;
    }

    /* renamed from: h */
    public void m2492h() {
        this.f3341c = -1L;
        this.f3342d = -1L;
        this.f3343e = -1L;
        this.f3344f = -1L;
    }
}
