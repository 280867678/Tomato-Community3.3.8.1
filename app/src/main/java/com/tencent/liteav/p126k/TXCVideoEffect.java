package com.tencent.liteav.p126k;

import android.content.Context;
import com.tencent.liteav.TXCGPUIllusionFilter;
import com.tencent.liteav.TXCGPUMirrorFilter;
import com.tencent.liteav.basic.log.TXCLog;
import java.util.LinkedList;
import java.util.Queue;

/* renamed from: com.tencent.liteav.k.n */
/* loaded from: classes3.dex */
public class TXCVideoEffect {

    /* renamed from: A */
    private Context f4548A;

    /* renamed from: e */
    private TXCGPULinearShadowFilter f4553e;

    /* renamed from: a */
    private TXCGPUSpiritOutFilter f4549a = null;

    /* renamed from: b */
    private TXCGPUSplitScreenFilter f4550b = null;

    /* renamed from: c */
    private TXCGPUDongGanFilter f4551c = null;

    /* renamed from: d */
    private TXCGPUAnHeiFilter f4552d = null;

    /* renamed from: f */
    private TXCGPUGhostShadowFilter f4554f = null;

    /* renamed from: g */
    private TXCGPUPhontomFilter f4555g = null;

    /* renamed from: h */
    private TXCGPUGhostFilter f4556h = null;

    /* renamed from: i */
    private TXCGPULightingFilter f4557i = null;

    /* renamed from: j */
    private TXCGPUDiffuseFilter f4558j = null;

    /* renamed from: k */
    private TXCGPUIllusionFilter f4559k = null;

    /* renamed from: l */
    private TXCGPUMirrorFilter f4560l = null;

    /* renamed from: m */
    private C3545l f4561m = null;

    /* renamed from: n */
    private C3546m f4562n = null;

    /* renamed from: o */
    private C3537d f4563o = null;

    /* renamed from: p */
    private C3533a f4564p = null;

    /* renamed from: q */
    private C3542i f4565q = null;

    /* renamed from: r */
    private C3539f f4566r = null;

    /* renamed from: s */
    private C3544k f4567s = null;

    /* renamed from: t */
    private C3538e f4568t = null;

    /* renamed from: u */
    private C3541h f4569u = null;

    /* renamed from: v */
    private C3535c f4570v = null;

    /* renamed from: w */
    private C3540g f4571w = null;

    /* renamed from: x */
    private C3543j f4572x = null;

    /* renamed from: y */
    private final Queue<Runnable> f4573y = new LinkedList();

    /* renamed from: z */
    private final String f4574z = "VideoEffect";

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$a */
    /* loaded from: classes3.dex */
    public static class C3533a extends C3547n {
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$b */
    /* loaded from: classes3.dex */
    public static class C3534b {

        /* renamed from: a */
        public int f4578a;

        /* renamed from: b */
        public int f4579b;

        /* renamed from: c */
        public int f4580c;
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$d */
    /* loaded from: classes3.dex */
    public static class C3537d extends C3547n {

        /* renamed from: a */
        public float f4596a = 0.0f;

        /* renamed from: b */
        public float f4597b = 0.4f;

        /* renamed from: c */
        public float[] f4598c = {0.5f, 0.5f};

        /* renamed from: d */
        public float f4599d = 0.0f;

        /* renamed from: e */
        public float f4600e = 10.0f;

        /* renamed from: f */
        public float[] f4601f = {0.0f, 0.0f};

        /* renamed from: g */
        public float[] f4602g = {0.0f, 0.0f};

        /* renamed from: h */
        public float[] f4603h = {0.0f, 0.0f};
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$e */
    /* loaded from: classes3.dex */
    public static class C3538e extends C3547n {

        /* renamed from: a */
        public float f4604a = 0.0f;

        /* renamed from: b */
        public float f4605b = 0.0f;

        /* renamed from: c */
        public float f4606c = 0.0f;
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$f */
    /* loaded from: classes3.dex */
    public static class C3539f extends C3547n {

        /* renamed from: a */
        public int f4607a = 5;

        /* renamed from: b */
        public int f4608b = 1;

        /* renamed from: c */
        public float f4609c = 0.5f;
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$g */
    /* loaded from: classes3.dex */
    public static class C3540g extends C3547n {
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$h */
    /* loaded from: classes3.dex */
    public static class C3541h extends C3547n {

        /* renamed from: a */
        public float f4610a = 0.0f;
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$i */
    /* loaded from: classes3.dex */
    public static class C3542i extends C3547n {

        /* renamed from: a */
        public float f4611a = 0.0f;

        /* renamed from: b */
        public float f4612b = 0.0f;

        /* renamed from: c */
        public float f4613c = 0.0f;

        /* renamed from: d */
        public float f4614d = 0.0f;

        /* renamed from: e */
        public float f4615e = 0.05f;
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$j */
    /* loaded from: classes3.dex */
    public static class C3543j extends C3547n {

        /* renamed from: a */
        public float f4616a = 0.0f;
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$k */
    /* loaded from: classes3.dex */
    public static class C3544k extends C3545l {

        /* renamed from: a */
        public float[] f4617a = {0.0f, 0.0f};

        /* renamed from: b */
        public float[] f4618b = {0.0f, 0.1f};

        /* renamed from: c */
        public float[] f4619c = {0.0f, 0.0f};
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$l */
    /* loaded from: classes3.dex */
    public static class C3545l extends C3547n {

        /* renamed from: d */
        public float f4620d = 0.5f;

        /* renamed from: e */
        public float f4621e = 0.5f;

        /* renamed from: f */
        public int f4622f = 1;

        /* renamed from: g */
        public int f4623g = 1;

        /* renamed from: h */
        public float f4624h = 0.5f;
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$m */
    /* loaded from: classes3.dex */
    public static class C3546m extends C3547n {

        /* renamed from: a */
        public int f4625a;
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$n */
    /* loaded from: classes3.dex */
    public static class C3547n {
    }

    /* compiled from: TXCVideoEffect.java */
    /* renamed from: com.tencent.liteav.k.n$c */
    /* loaded from: classes3.dex */
    public static class C3535c extends C3547n {

        /* renamed from: a */
        public float f4581a = 0.01f;

        /* renamed from: b */
        public float f4582b = 0.02f;

        /* renamed from: c */
        public float f4583c = 0.05f;

        /* renamed from: d */
        public float f4584d = 30.0f;

        /* renamed from: e */
        public float f4585e = 0.6f;

        /* renamed from: f */
        public float f4586f = 0.0f;

        /* renamed from: g */
        public EnumC3536a f4587g = EnumC3536a.MODE_ZOOM_IN;

        /* renamed from: h */
        public float f4588h = 0.3f;

        /* renamed from: i */
        public float f4589i = 0.5f;

        /* renamed from: j */
        public float f4590j = 1.5f;

        /* renamed from: k */
        public boolean f4591k = false;

        /* compiled from: TXCVideoEffect.java */
        /* renamed from: com.tencent.liteav.k.n$c$a */
        /* loaded from: classes3.dex */
        public enum EnumC3536a {
            MODE_NONE(-1),
            MODE_ZOOM_IN(0),
            MODE_ZOOM_OUT(1);
            
            public int value;

            EnumC3536a(int i) {
                this.value = i;
            }
        }
    }

    public TXCVideoEffect(Context context) {
        this.f4548A = null;
        this.f4548A = context;
    }

    /* renamed from: a */
    public void m1317a(final int i, final C3547n c3547n) {
        m1303a(new Runnable() { // from class: com.tencent.liteav.k.n.1
            @Override // java.lang.Runnable
            public void run() {
                switch (i) {
                    case 0:
                        TXCVideoEffect.this.f4564p = (C3533a) c3547n;
                        return;
                    case 1:
                        TXCVideoEffect.this.f4563o = (C3537d) c3547n;
                        return;
                    case 2:
                        TXCVideoEffect.this.f4561m = (C3545l) c3547n;
                        return;
                    case 3:
                        TXCVideoEffect.this.f4562n = (C3546m) c3547n;
                        return;
                    case 4:
                        TXCVideoEffect.this.f4565q = (C3542i) c3547n;
                        return;
                    case 5:
                        TXCVideoEffect.this.f4566r = (C3539f) c3547n;
                        return;
                    case 6:
                        TXCVideoEffect.this.f4567s = (C3544k) c3547n;
                        return;
                    case 7:
                        TXCVideoEffect.this.f4568t = (C3538e) c3547n;
                        return;
                    case 8:
                        TXCVideoEffect.this.f4569u = (C3541h) c3547n;
                        return;
                    case 9:
                        TXCVideoEffect.this.f4570v = (C3535c) c3547n;
                        return;
                    case 10:
                        TXCVideoEffect.this.f4571w = (C3540g) c3547n;
                        return;
                    case 11:
                        TXCVideoEffect.this.f4572x = (C3543j) c3547n;
                        return;
                    default:
                        return;
                }
            }
        });
    }

    /* renamed from: a */
    public int m1316a(C3534b c3534b) {
        m1302a(this.f4573y);
        int i = c3534b.f4578a;
        if (this.f4564p != null) {
            m1297d(c3534b.f4579b, c3534b.f4580c);
            TXCGPUAnHeiFilter tXCGPUAnHeiFilter = this.f4552d;
            if (tXCGPUAnHeiFilter != null) {
                tXCGPUAnHeiFilter.m1372a(this.f4564p);
                i = this.f4552d.mo2294a(i);
            }
        }
        if (this.f4563o != null) {
            m1298c(c3534b.f4579b, c3534b.f4580c);
            TXCGPUDongGanFilter tXCGPUDongGanFilter = this.f4551c;
            if (tXCGPUDongGanFilter != null) {
                tXCGPUDongGanFilter.m1360a(this.f4563o);
                i = this.f4551c.mo2294a(i);
            }
        }
        if (this.f4561m != null) {
            m1318a(c3534b.f4579b, c3534b.f4580c);
            TXCGPUSpiritOutFilter tXCGPUSpiritOutFilter = this.f4549a;
            if (tXCGPUSpiritOutFilter != null) {
                tXCGPUSpiritOutFilter.m1328a(this.f4561m);
                i = this.f4549a.mo1330a(i);
            }
        }
        if (this.f4562n != null) {
            m1300b(c3534b.f4579b, c3534b.f4580c);
            TXCGPUSplitScreenFilter tXCGPUSplitScreenFilter = this.f4550b;
            if (tXCGPUSplitScreenFilter != null) {
                tXCGPUSplitScreenFilter.m1323a(this.f4562n);
                i = this.f4550b.mo2294a(i);
            }
        }
        if (this.f4565q != null) {
            m1296e(c3534b.f4579b, c3534b.f4580c);
            TXCGPULinearShadowFilter tXCGPULinearShadowFilter = this.f4553e;
            if (tXCGPULinearShadowFilter != null) {
                tXCGPULinearShadowFilter.m1332a(this.f4565q);
                i = this.f4553e.mo2294a(i);
            }
        }
        if (this.f4566r != null) {
            m1295f(c3534b.f4579b, c3534b.f4580c);
            TXCGPUGhostShadowFilter tXCGPUGhostShadowFilter = this.f4554f;
            if (tXCGPUGhostShadowFilter != null) {
                tXCGPUGhostShadowFilter.m1347a(this.f4566r);
                i = this.f4554f.m1349a(i);
            }
        }
        if (this.f4567s != null) {
            m1294g(c3534b.f4579b, c3534b.f4580c);
            TXCGPUPhontomFilter tXCGPUPhontomFilter = this.f4555g;
            if (tXCGPUPhontomFilter != null) {
                tXCGPUPhontomFilter.m1328a(this.f4567s);
                i = this.f4555g.mo1330a(i);
            }
        }
        if (this.f4568t != null) {
            m1293h(c3534b.f4579b, c3534b.f4580c);
            TXCGPUGhostFilter tXCGPUGhostFilter = this.f4556h;
            if (tXCGPUGhostFilter != null) {
                tXCGPUGhostFilter.m1354a(this.f4568t);
                i = this.f4556h.mo2294a(i);
            }
        }
        if (this.f4569u != null) {
            m1292i(c3534b.f4579b, c3534b.f4580c);
            TXCGPULightingFilter tXCGPULightingFilter = this.f4557i;
            if (tXCGPULightingFilter != null) {
                tXCGPULightingFilter.m1338a(this.f4569u);
                i = this.f4557i.m1340a(i);
            }
        }
        if (this.f4570v != null) {
            m1291j(c3534b.f4579b, c3534b.f4580c);
            TXCGPUDiffuseFilter tXCGPUDiffuseFilter = this.f4558j;
            if (tXCGPUDiffuseFilter != null) {
                tXCGPUDiffuseFilter.m1367a(this.f4570v);
                i = this.f4558j.m1369a(i);
            }
        }
        if (this.f4571w != null) {
            m1290k(c3534b.f4579b, c3534b.f4580c);
            TXCGPUIllusionFilter tXCGPUIllusionFilter = this.f4559k;
            if (tXCGPUIllusionFilter != null) {
                i = tXCGPUIllusionFilter.mo2294a(i);
            }
        }
        if (this.f4572x != null) {
            m1289l(c3534b.f4579b, c3534b.f4580c);
            TXCGPUMirrorFilter tXCGPUMirrorFilter = this.f4560l;
            if (tXCGPUMirrorFilter != null) {
                tXCGPUMirrorFilter.m1936a(this.f4572x);
                i = this.f4560l.mo2294a(i);
            }
        }
        m1301b();
        return i;
    }

    /* renamed from: a */
    private void m1318a(int i, int i2) {
        if (this.f4549a == null) {
            this.f4549a = new TXCGPUSpiritOutFilter();
            if (!this.f4549a.m1329a(i, i2)) {
                TXCLog.m2914e("VideoEffect", "mSpiritOutFilter.init failed");
                return;
            }
        }
        this.f4549a.m1326b(i, i2);
    }

    /* renamed from: b */
    private void m1300b(int i, int i2) {
        if (this.f4550b == null) {
            this.f4550b = new TXCGPUSplitScreenFilter();
            this.f4550b.mo1353a(true);
            if (!this.f4550b.mo2653c()) {
                TXCLog.m2914e("VideoEffect", "mSpiritOutFilter.init failed");
                return;
            }
        }
        this.f4550b.mo1333a(i, i2);
    }

    /* renamed from: c */
    private void m1298c(int i, int i2) {
        if (this.f4551c == null) {
            this.f4551c = new TXCGPUDongGanFilter();
            this.f4551c.mo1353a(true);
            if (!this.f4551c.mo2653c()) {
                TXCLog.m2914e("VideoEffect", "mSpiritOutFilter.init failed");
                return;
            }
        }
        this.f4551c.mo1333a(i, i2);
    }

    /* renamed from: d */
    private void m1297d(int i, int i2) {
        if (this.f4552d == null) {
            this.f4552d = new TXCGPUAnHeiFilter();
            this.f4552d.mo1353a(true);
            if (!this.f4552d.mo2653c()) {
                TXCLog.m2914e("VideoEffect", "mAnHeiFilter.init failed");
                return;
            }
        }
        this.f4552d.mo1333a(i, i2);
    }

    /* renamed from: e */
    private void m1296e(int i, int i2) {
        if (this.f4553e == null) {
            this.f4553e = new TXCGPULinearShadowFilter();
            this.f4553e.mo1353a(true);
            if (!this.f4553e.mo2653c()) {
                TXCLog.m2914e("VideoEffect", "mShadowFilter.init failed");
                return;
            }
        }
        this.f4553e.mo1333a(i, i2);
    }

    /* renamed from: f */
    private void m1295f(int i, int i2) {
        if (this.f4554f == null) {
            this.f4554f = new TXCGPUGhostShadowFilter();
            if (!this.f4554f.m1348a(i, i2)) {
                TXCLog.m2914e("VideoEffect", "mGhostShadowFilter.init failed");
                return;
            }
        }
        this.f4554f.m1344b(i, i2);
    }

    /* renamed from: g */
    private void m1294g(int i, int i2) {
        if (this.f4555g == null) {
            this.f4555g = new TXCGPUPhontomFilter();
            if (!this.f4555g.m1329a(i, i2)) {
                TXCLog.m2914e("VideoEffect", "createPhontomFilter.init failed");
                return;
            }
        }
        this.f4555g.m1326b(i, i2);
    }

    /* renamed from: h */
    private void m1293h(int i, int i2) {
        if (this.f4556h == null) {
            this.f4556h = new TXCGPUGhostFilter();
            this.f4556h.mo1353a(true);
            if (!this.f4556h.mo2653c()) {
                TXCLog.m2914e("VideoEffect", "createGhostFilter.init failed");
                return;
            }
        }
        this.f4556h.mo1333a(i, i2);
    }

    /* renamed from: i */
    private void m1292i(int i, int i2) {
        if (this.f4557i == null) {
            this.f4557i = new TXCGPULightingFilter(this.f4548A);
            if (!this.f4557i.m1339a(i, i2)) {
                TXCLog.m2914e("VideoEffect", "createGhostFilter.init failed");
                return;
            }
        }
        this.f4557i.m1336b(i, i2);
    }

    /* renamed from: j */
    private void m1291j(int i, int i2) {
        if (this.f4558j == null) {
            this.f4558j = new TXCGPUDiffuseFilter();
            if (!this.f4558j.m1368a(i, i2)) {
                TXCLog.m2914e("VideoEffect", "mDiffuseFilter.init failed");
                return;
            }
        }
        this.f4558j.m1365b(i, i2);
    }

    /* renamed from: k */
    private void m1290k(int i, int i2) {
        if (this.f4559k == null) {
            this.f4559k = new TXCGPUIllusionFilter();
            this.f4559k.mo1353a(true);
            if (!this.f4559k.mo2653c()) {
                TXCLog.m2914e("VideoEffect", "mDiffuseFilter.init failed");
                return;
            }
        }
        this.f4559k.mo1333a(i, i2);
    }

    /* renamed from: l */
    private void m1289l(int i, int i2) {
        if (this.f4560l == null) {
            this.f4560l = new TXCGPUMirrorFilter();
            this.f4560l.mo1353a(true);
            if (!this.f4560l.mo2653c()) {
                TXCLog.m2914e("VideoEffect", "mDiffuseFilter.init failed");
                return;
            }
        }
        this.f4560l.mo1333a(i, i2);
    }

    /* renamed from: b */
    private void m1301b() {
        this.f4564p = null;
        this.f4563o = null;
        this.f4561m = null;
        this.f4562n = null;
        this.f4565q = null;
        this.f4566r = null;
        this.f4567s = null;
        this.f4568t = null;
        this.f4569u = null;
        this.f4570v = null;
        this.f4571w = null;
        this.f4572x = null;
    }

    /* renamed from: a */
    private void m1303a(Runnable runnable) {
        synchronized (this.f4573y) {
            this.f4573y.add(runnable);
        }
    }

    /* renamed from: a */
    private void m1302a(Queue<Runnable> queue) {
        while (true) {
            Runnable runnable = null;
            synchronized (queue) {
                if (!queue.isEmpty()) {
                    runnable = queue.poll();
                }
            }
            if (runnable == null) {
                return;
            }
            runnable.run();
        }
    }

    /* renamed from: a */
    public void m1319a() {
        m1299c();
        m1301b();
    }

    /* renamed from: c */
    private void m1299c() {
        TXCGPUSpiritOutFilter tXCGPUSpiritOutFilter = this.f4549a;
        if (tXCGPUSpiritOutFilter != null) {
            tXCGPUSpiritOutFilter.m1331a();
            this.f4549a = null;
        }
        TXCGPUSplitScreenFilter tXCGPUSplitScreenFilter = this.f4550b;
        if (tXCGPUSplitScreenFilter != null) {
            tXCGPUSplitScreenFilter.mo1351e();
            this.f4550b = null;
        }
        TXCGPUDongGanFilter tXCGPUDongGanFilter = this.f4551c;
        if (tXCGPUDongGanFilter != null) {
            tXCGPUDongGanFilter.mo1351e();
            this.f4551c = null;
        }
        TXCGPUAnHeiFilter tXCGPUAnHeiFilter = this.f4552d;
        if (tXCGPUAnHeiFilter != null) {
            tXCGPUAnHeiFilter.mo1351e();
            this.f4552d = null;
        }
        TXCGPULinearShadowFilter tXCGPULinearShadowFilter = this.f4553e;
        if (tXCGPULinearShadowFilter != null) {
            tXCGPULinearShadowFilter.mo1351e();
            this.f4553e = null;
        }
        TXCGPUGhostShadowFilter tXCGPUGhostShadowFilter = this.f4554f;
        if (tXCGPUGhostShadowFilter != null) {
            tXCGPUGhostShadowFilter.m1350a();
            this.f4554f = null;
        }
        TXCGPUPhontomFilter tXCGPUPhontomFilter = this.f4555g;
        if (tXCGPUPhontomFilter != null) {
            tXCGPUPhontomFilter.m1331a();
            this.f4555g = null;
        }
        TXCGPUGhostFilter tXCGPUGhostFilter = this.f4556h;
        if (tXCGPUGhostFilter != null) {
            tXCGPUGhostFilter.mo1351e();
            this.f4556h = null;
        }
        TXCGPULightingFilter tXCGPULightingFilter = this.f4557i;
        if (tXCGPULightingFilter != null) {
            tXCGPULightingFilter.m1337b();
            this.f4557i = null;
        }
        TXCGPUDiffuseFilter tXCGPUDiffuseFilter = this.f4558j;
        if (tXCGPUDiffuseFilter != null) {
            tXCGPUDiffuseFilter.m1370a();
            this.f4558j = null;
        }
        TXCGPUIllusionFilter tXCGPUIllusionFilter = this.f4559k;
        if (tXCGPUIllusionFilter != null) {
            tXCGPUIllusionFilter.mo1351e();
            this.f4559k = null;
        }
        TXCGPUMirrorFilter tXCGPUMirrorFilter = this.f4560l;
        if (tXCGPUMirrorFilter != null) {
            tXCGPUMirrorFilter.mo1351e();
            this.f4560l = null;
        }
    }
}
