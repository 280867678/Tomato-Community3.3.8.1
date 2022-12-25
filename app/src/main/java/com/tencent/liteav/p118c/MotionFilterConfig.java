package com.tencent.liteav.p118c;

import com.tencent.liteav.p119d.Motion;
import java.util.LinkedList;
import java.util.List;

/* renamed from: com.tencent.liteav.c.d */
/* loaded from: classes3.dex */
public class MotionFilterConfig {

    /* renamed from: a */
    private static MotionFilterConfig f3345a;

    /* renamed from: b */
    private final LinkedList<Motion> f3346b = new LinkedList<>();

    /* renamed from: c */
    private Motion f3347c;

    /* renamed from: a */
    public static MotionFilterConfig m2491a() {
        if (f3345a == null) {
            f3345a = new MotionFilterConfig();
        }
        return f3345a;
    }

    private MotionFilterConfig() {
    }

    /* renamed from: a */
    public void m2490a(Motion motion) {
        this.f3347c = motion;
        this.f3346b.add(motion);
    }

    /* renamed from: b */
    public Motion m2489b() {
        return this.f3347c;
    }

    /* renamed from: c */
    public void m2488c() {
        if (this.f3346b.size() == 0) {
            return;
        }
        this.f3346b.removeLast();
    }

    /* renamed from: d */
    public List<Motion> m2487d() {
        return this.f3346b;
    }

    /* renamed from: e */
    public void m2486e() {
        this.f3346b.clear();
    }
}
