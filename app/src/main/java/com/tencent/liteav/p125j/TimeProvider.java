package com.tencent.liteav.p125j;

import com.tencent.liteav.p118c.ReverseConfig;
import com.tencent.liteav.p119d.Frame;
import java.util.concurrent.atomic.AtomicLong;

/* renamed from: com.tencent.liteav.j.e */
/* loaded from: classes3.dex */
public class TimeProvider {

    /* renamed from: a */
    private static TimeProvider f4433a;

    /* renamed from: b */
    private AtomicLong f4434b = new AtomicLong(0);

    /* renamed from: c */
    private AtomicLong f4435c = new AtomicLong(0);

    /* renamed from: d */
    private AtomicLong f4436d = new AtomicLong(0);

    /* renamed from: e */
    private AtomicLong f4437e = new AtomicLong(0);

    /* renamed from: f */
    private AtomicLong f4438f = new AtomicLong(0);

    /* renamed from: g */
    private AtomicLong f4439g = new AtomicLong(0);

    /* renamed from: a */
    public static TimeProvider m1429a() {
        if (f4433a == null) {
            f4433a = new TimeProvider();
        }
        return f4433a;
    }

    private TimeProvider() {
    }

    /* renamed from: a */
    public static long m1427a(Frame frame) {
        if (ReverseConfig.m2478a().m2476b()) {
            return frame.m2304u();
        }
        return frame.m2305t();
    }

    /* renamed from: a */
    public void m1428a(long j) {
        this.f4434b.set(j);
    }

    /* renamed from: b */
    public void m1425b(long j) {
        this.f4435c.set(j);
    }

    /* renamed from: c */
    public void m1424c(long j) {
        this.f4436d.set(j);
    }

    /* renamed from: d */
    public void m1423d(long j) {
        this.f4437e.set(j);
    }

    /* renamed from: e */
    public void m1422e(long j) {
        this.f4438f.set(j);
    }

    /* renamed from: f */
    public void m1421f(long j) {
        this.f4439g.set(j);
    }

    /* renamed from: b */
    public void m1426b() {
        this.f4434b.set(0L);
        this.f4435c.set(0L);
        this.f4436d.set(0L);
        this.f4437e.set(0L);
        this.f4438f.set(0L);
        this.f4439g.set(0L);
    }
}
