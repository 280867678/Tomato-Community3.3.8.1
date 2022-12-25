package com.tencent.liteav.p121f;

import android.content.Context;
import com.tencent.liteav.p118c.MotionFilterConfig;
import com.tencent.liteav.p118c.ReverseConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Motion;
import com.tencent.liteav.p126k.TXCVideoEffect;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.List;

/* renamed from: com.tencent.liteav.f.e */
/* loaded from: classes3.dex */
public class MotionFilterChain {

    /* renamed from: c */
    private TXCVideoEffect f3914c;

    /* renamed from: d */
    private Motion f3915d;

    /* renamed from: e */
    private boolean f3916e;

    /* renamed from: n */
    private TXCVideoEffect.C3545l f3925n;

    /* renamed from: o */
    private TXCVideoEffect.C3546m f3926o;

    /* renamed from: p */
    private TXCVideoEffect.C3533a f3927p;

    /* renamed from: q */
    private TXCVideoEffect.C3537d f3928q;

    /* renamed from: r */
    private TXCVideoEffect.C3542i f3929r;

    /* renamed from: s */
    private TXCVideoEffect.C3539f f3930s;

    /* renamed from: t */
    private TXCVideoEffect.C3544k f3931t;

    /* renamed from: u */
    private TXCVideoEffect.C3538e f3932u;

    /* renamed from: v */
    private TXCVideoEffect.C3541h f3933v;

    /* renamed from: w */
    private TXCVideoEffect.C3540g f3934w;

    /* renamed from: x */
    private TXCVideoEffect.C3543j f3935x;

    /* renamed from: f */
    private long f3917f = -1;

    /* renamed from: g */
    private long f3918g = -1;

    /* renamed from: h */
    private long f3919h = -1;

    /* renamed from: i */
    private long f3920i = -1;

    /* renamed from: j */
    private long f3921j = -1;

    /* renamed from: k */
    private long f3922k = -1;

    /* renamed from: l */
    private long f3923l = -1;

    /* renamed from: m */
    private long f3924m = -1;

    /* renamed from: y */
    private final int f3936y = 120000;

    /* renamed from: z */
    private final int f3937z = 230000;

    /* renamed from: A */
    private final int f3852A = 274000;

    /* renamed from: B */
    private final int f3853B = 318000;

    /* renamed from: C */
    private final int f3854C = 362000;

    /* renamed from: D */
    private final int f3855D = 406000;

    /* renamed from: E */
    private final int f3856E = 450000;

    /* renamed from: F */
    private final int f3857F = 494000;

    /* renamed from: G */
    private final int f3858G = 538000;

    /* renamed from: H */
    private final int f3859H = 582000;

    /* renamed from: I */
    private final int f3860I = 560000;

    /* renamed from: J */
    private final int f3861J = 1120000;

    /* renamed from: K */
    private final int f3862K = 1000000;

    /* renamed from: L */
    private final int f3863L = 120000;

    /* renamed from: M */
    private final int f3864M = 70000;

    /* renamed from: N */
    private final int f3865N = 300000;

    /* renamed from: O */
    private final int f3866O = 350000;

    /* renamed from: P */
    private final int f3867P = 400000;

    /* renamed from: Q */
    private final int f3868Q = 500000;

    /* renamed from: R */
    private final int f3869R = 600000;

    /* renamed from: S */
    private final int f3870S = 650000;

    /* renamed from: T */
    private final int f3871T = 700000;

    /* renamed from: U */
    private final int f3872U = 800000;

    /* renamed from: V */
    private final int f3873V = 900000;

    /* renamed from: W */
    private final int f3874W = 1000000;

    /* renamed from: X */
    private final int f3875X = 1050000;

    /* renamed from: Y */
    private final int f3876Y = ConstantUtils.VERSION_LOW_ERROR_CODE;

    /* renamed from: Z */
    private final int f3877Z = 1200000;

    /* renamed from: aa */
    private final int f3887aa = 1500000;

    /* renamed from: ab */
    private final int f3888ab = 2500000;

    /* renamed from: ac */
    private final int f3889ac = 120000;

    /* renamed from: ad */
    private final int f3890ad = 240000;

    /* renamed from: ae */
    private final int f3891ae = 360000;

    /* renamed from: af */
    private final int f3892af = 480000;

    /* renamed from: ag */
    private final int f3893ag = 600000;

    /* renamed from: ah */
    private final int f3894ah = 720000;

    /* renamed from: ai */
    private final int f3895ai = 840000;

    /* renamed from: aj */
    private final int f3896aj = 960000;

    /* renamed from: ak */
    private final int f3897ak = 1080000;

    /* renamed from: al */
    private final int f3898al = 1200000;

    /* renamed from: am */
    private final int f3899am = 1320000;

    /* renamed from: an */
    private final int f3900an = 1440000;

    /* renamed from: ao */
    private final int f3901ao = 1560000;

    /* renamed from: ap */
    private final int f3902ap = 1680000;

    /* renamed from: aq */
    private final int f3903aq = 1800000;

    /* renamed from: ar */
    private final int f3904ar = 100000;

    /* renamed from: as */
    private final int f3905as = 200000;

    /* renamed from: at */
    private final int f3906at = 300000;

    /* renamed from: au */
    private final int f3907au = 400000;

    /* renamed from: av */
    private final int f3908av = 500000;

    /* renamed from: aw */
    private final int f3909aw = 600000;

    /* renamed from: ax */
    private final int f3910ax = 700000;

    /* renamed from: ay */
    private final int f3911ay = 800000;

    /* renamed from: az */
    private final int f3912az = 850000;

    /* renamed from: aA */
    private final int f3879aA = 50000;

    /* renamed from: aB */
    private final int f3880aB = 150000;

    /* renamed from: aC */
    private final int f3881aC = 250000;

    /* renamed from: aD */
    private final int f3882aD = 300000;

    /* renamed from: aE */
    private final int f3883aE = 400000;

    /* renamed from: aF */
    private final int f3884aF = 580000;

    /* renamed from: aG */
    private final int f3885aG = 1000000;

    /* renamed from: aH */
    private final int f3886aH = 2000000;

    /* renamed from: b */
    private MotionFilterConfig f3913b = MotionFilterConfig.m2491a();

    /* renamed from: a */
    private ReverseConfig f3878a = ReverseConfig.m2478a();

    public MotionFilterChain(Context context) {
        this.f3914c = new TXCVideoEffect(context);
    }

    /* renamed from: a */
    public void m1871a(Frame frame) {
        int m1872a;
        long m2329e = frame.m2329e();
        this.f3915d = null;
        if (m1869b() && (m1872a = m1872a(m2329e)) != -1 && this.f3915d != null && m1868b(m2329e)) {
            m1873a(m1872a, m2329e);
        }
    }

    /* renamed from: a */
    public int m1870a(Frame frame, int i) {
        if (this.f3915d == null) {
            return i;
        }
        TXCVideoEffect.C3534b c3534b = new TXCVideoEffect.C3534b();
        c3534b.f4578a = i;
        if (frame.m2323h() == 90 || frame.m2323h() == 270) {
            c3534b.f4579b = frame.m2311n();
            c3534b.f4580c = frame.m2313m();
        } else {
            c3534b.f4579b = frame.m2313m();
            c3534b.f4580c = frame.m2311n();
        }
        switch (this.f3915d.f3464a) {
            case 0:
                this.f3914c.m1317a(0, this.f3927p);
                break;
            case 1:
                this.f3914c.m1317a(1, this.f3928q);
                break;
            case 2:
                this.f3914c.m1317a(2, this.f3925n);
                break;
            case 3:
                this.f3914c.m1317a(3, this.f3926o);
                break;
            case 4:
                this.f3914c.m1317a(4, this.f3929r);
                break;
            case 5:
                this.f3914c.m1317a(5, this.f3930s);
                break;
            case 6:
                this.f3914c.m1317a(6, this.f3931t);
                break;
            case 7:
                this.f3914c.m1317a(7, this.f3932u);
                break;
            case 8:
                this.f3914c.m1317a(8, this.f3933v);
                break;
            case 10:
                this.f3914c.m1317a(10, this.f3934w);
                break;
            case 11:
                this.f3914c.m1317a(11, this.f3935x);
                break;
        }
        return this.f3914c.m1316a(c3534b);
    }

    /* renamed from: b */
    private boolean m1869b() {
        List<Motion> m2487d = this.f3913b.m2487d();
        return (m2487d == null || m2487d.size() == 0) ? false : true;
    }

    /* renamed from: a */
    private int m1872a(long j) {
        List<Motion> m2487d = this.f3913b.m2487d();
        int i = -1;
        if (m2487d == null || m2487d.size() == 0) {
            return -1;
        }
        int size = m2487d.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            Motion motion = m2487d.get(size);
            if (j >= motion.f3465b && j <= motion.f3466c) {
                i = motion.f3464a;
                this.f3915d = motion;
                break;
            }
            size--;
        }
        Motion m2489b = this.f3913b.m2489b();
        long j2 = m2489b.f3466c;
        if (j2 != -1 && j2 != m2489b.f3465b) {
            return i;
        }
        int i2 = m2489b.f3464a;
        this.f3915d = m2489b;
        return i2;
    }

    /* renamed from: b */
    private boolean m1868b(long j) {
        Motion motion = this.f3915d;
        long j2 = motion.f3465b;
        long j3 = motion.f3466c;
        int i = (j2 > (-1L) ? 1 : (j2 == (-1L) ? 0 : -1));
        boolean z = i != 0 && j3 != -1 && j > j2 && j < j3;
        if (i != 0 && j > j2) {
            z = true;
        }
        if (j3 == -1 || j >= j3) {
            return z;
        }
        return true;
    }

    /* renamed from: a */
    private void m1873a(int i, long j) {
        switch (i) {
            case 0:
                if (this.f3927p == null) {
                    this.f3927p = new TXCVideoEffect.C3533a();
                }
                m1867c();
                return;
            case 1:
                m1859j(j);
                return;
            case 2:
                m1857l(j);
                return;
            case 3:
                m1858k(j);
                return;
            case 4:
                m1860i(j);
                return;
            case 5:
                m1861h(j);
                return;
            case 6:
                m1862g(j);
                return;
            case 7:
                m1863f(j);
                return;
            case 8:
                m1864e(j);
                return;
            case 9:
            default:
                return;
            case 10:
                m1865d(j);
                return;
            case 11:
                m1866c(j);
                return;
        }
    }

    /* renamed from: c */
    private void m1866c(long j) {
        if (this.f3935x == null) {
            this.f3935x = new TXCVideoEffect.C3543j();
        }
        if (this.f3924m == -1) {
            this.f3924m = j;
        }
        long abs = Math.abs(j - this.f3924m);
        if (abs < 1000000) {
            this.f3935x.f4616a = 0.0f;
        } else if (abs < 2000000) {
            this.f3935x.f4616a = 1.0f;
        } else {
            this.f3924m = -1L;
        }
    }

    /* renamed from: d */
    private void m1865d(long j) {
        if (this.f3934w == null) {
            this.f3934w = new TXCVideoEffect.C3540g();
        }
    }

    /* renamed from: e */
    private void m1864e(long j) {
        if (this.f3923l == -1) {
            this.f3923l = j;
        }
        if (this.f3933v == null) {
            this.f3933v = new TXCVideoEffect.C3541h();
            this.f3933v.f4610a = 0.0f;
        }
        long abs = Math.abs(j - this.f3923l);
        if (abs < 50000) {
            this.f3933v.f4610a = 0.7f;
        } else if (abs < 150000) {
            this.f3933v.f4610a = 0.5f;
        } else if (abs < 250000) {
            this.f3933v.f4610a = 0.4f;
        } else if (abs < 300000) {
            this.f3933v.f4610a = 1.0f;
        } else if (abs < 400000) {
            this.f3933v.f4610a = 0.3f;
        } else if (abs < 580000) {
            this.f3933v.f4610a = 0.0f;
        } else {
            this.f3923l = -1L;
        }
    }

    /* renamed from: f */
    private void m1863f(long j) {
        if (this.f3922k == -1) {
            this.f3922k = j;
        }
        if (this.f3932u == null) {
            this.f3932u = new TXCVideoEffect.C3538e();
            TXCVideoEffect.C3538e c3538e = this.f3932u;
            c3538e.f4605b = 0.0f;
            c3538e.f4604a = 0.0f;
            c3538e.f4606c = 0.0f;
        }
        long abs = Math.abs(j - this.f3922k);
        if (abs < 100000) {
            TXCVideoEffect.C3538e c3538e2 = this.f3932u;
            c3538e2.f4605b = 10.0f;
            c3538e2.f4604a = 0.01f;
            c3538e2.f4606c = 0.0f;
        } else if (abs < 200000) {
            TXCVideoEffect.C3538e c3538e3 = this.f3932u;
            c3538e3.f4605b = 20.0f;
            c3538e3.f4604a = -0.02f;
            c3538e3.f4606c = 0.0f;
        } else if (abs < 300000) {
            TXCVideoEffect.C3538e c3538e4 = this.f3932u;
            c3538e4.f4605b = 30.0f;
            c3538e4.f4604a = 0.02f;
            c3538e4.f4606c = 0.0f;
        } else if (abs < 400000) {
            TXCVideoEffect.C3538e c3538e5 = this.f3932u;
            c3538e5.f4605b = 20.0f;
            c3538e5.f4604a = -0.03f;
            c3538e5.f4606c = 0.0f;
        } else if (abs < 500000) {
            TXCVideoEffect.C3538e c3538e6 = this.f3932u;
            c3538e6.f4605b = 10.0f;
            c3538e6.f4604a = 0.01f;
            c3538e6.f4606c = 0.0f;
        } else if (abs < 600000) {
            TXCVideoEffect.C3538e c3538e7 = this.f3932u;
            c3538e7.f4605b = 20.0f;
            c3538e7.f4604a = -0.02f;
            c3538e7.f4606c = 0.0f;
        } else if (abs < 700000) {
            TXCVideoEffect.C3538e c3538e8 = this.f3932u;
            c3538e8.f4605b = 30.0f;
            c3538e8.f4604a = -0.03f;
            c3538e8.f4606c = 0.0f;
        } else if (abs < 800000) {
            TXCVideoEffect.C3538e c3538e9 = this.f3932u;
            c3538e9.f4605b = 20.0f;
            c3538e9.f4604a = 0.02f;
            c3538e9.f4606c = 0.0f;
        } else if (abs < 850000) {
            TXCVideoEffect.C3538e c3538e10 = this.f3932u;
            c3538e10.f4605b = 0.0f;
            c3538e10.f4604a = 0.0f;
            c3538e10.f4606c = 0.0f;
        } else {
            this.f3922k = -1L;
        }
    }

    /* renamed from: g */
    private void m1862g(long j) {
        if (this.f3921j == -1) {
            this.f3921j = j;
        }
        if (this.f3931t == null) {
            this.f3931t = new TXCVideoEffect.C3544k();
            TXCVideoEffect.C3544k c3544k = this.f3931t;
            c3544k.f4622f = 1;
            c3544k.f4624h = 0.3f;
        }
        TXCVideoEffect.C3544k c3544k2 = this.f3931t;
        c3544k2.f4617a = new float[]{0.0f, 0.0f};
        c3544k2.f4618b = new float[]{0.0f, 0.1f};
        long abs = Math.abs(j - this.f3921j);
        int i = (abs > 120000L ? 1 : (abs == 120000L ? 0 : -1));
        if (i < 0) {
            TXCVideoEffect.C3544k c3544k3 = this.f3931t;
            c3544k3.f4623g = 0;
            c3544k3.f4617a = new float[]{0.0f, 0.0f};
            c3544k3.f4618b = new float[]{0.0f, 0.0f};
        } else if (i < 0) {
            this.f3931t.f4623g = 1;
        } else if (abs < 240000) {
            this.f3931t.f4623g = 2;
        } else if (abs < 360000) {
            this.f3931t.f4623g = 3;
        } else if (abs < 480000) {
            this.f3931t.f4623g = 4;
        } else if (abs < 600000) {
            this.f3931t.f4623g = 5;
        } else if (abs < 720000) {
            this.f3931t.f4623g = 6;
        } else if (abs < 840000) {
            this.f3931t.f4623g = 7;
        } else if (abs < 960000) {
            this.f3931t.f4623g = 8;
        } else if (abs < 1080000) {
            this.f3931t.f4623g = 9;
        } else if (abs < 1200000) {
            this.f3931t.f4623g = 10;
        } else if (abs < 1320000) {
            this.f3931t.f4623g = 11;
        } else if (abs < 1440000) {
            this.f3931t.f4623g = 12;
        } else if (abs < 1560000) {
            this.f3931t.f4623g = 13;
        } else if (abs < 1680000) {
            this.f3931t.f4623g = 14;
        } else if (abs < 1800000) {
            this.f3931t.f4623g = 15;
        } else {
            this.f3921j = -1L;
        }
    }

    /* renamed from: h */
    private void m1861h(long j) {
        if (this.f3930s == null) {
            this.f3930s = new TXCVideoEffect.C3539f();
        }
        TXCVideoEffect.C3539f c3539f = this.f3930s;
        c3539f.f4607a = 5;
        c3539f.f4608b = 1;
        c3539f.f4609c = 0.5f;
    }

    /* renamed from: i */
    private void m1860i(long j) {
        if (this.f3920i == -1) {
            if (this.f3916e) {
                this.f3920i = this.f3915d.f3465b;
            } else {
                this.f3920i = j;
            }
        }
        if (this.f3929r == null) {
            this.f3929r = new TXCVideoEffect.C3542i();
        }
        long abs = Math.abs(j - this.f3920i);
        if (abs < 300000) {
            TXCVideoEffect.C3542i c3542i = this.f3929r;
            c3542i.f4612b = 0.0f;
            c3542i.f4615e = 0.03f;
            c3542i.f4614d = 0.003f;
        } else if (abs < 350000) {
            TXCVideoEffect.C3542i c3542i2 = this.f3929r;
            c3542i2.f4612b = 0.0f;
            c3542i2.f4615e = 0.03f;
            c3542i2.f4614d = 0.015f;
        } else if (abs < 400000) {
            TXCVideoEffect.C3542i c3542i3 = this.f3929r;
            c3542i3.f4612b = 0.0f;
            c3542i3.f4615e = 0.03f;
            c3542i3.f4614d = 0.024f;
        } else if (abs < 500000) {
            TXCVideoEffect.C3542i c3542i4 = this.f3929r;
            c3542i4.f4612b = 0.0f;
            c3542i4.f4615e = 0.03f;
            c3542i4.f4614d = 0.015f;
        } else if (abs < 600000) {
            TXCVideoEffect.C3542i c3542i5 = this.f3929r;
            c3542i5.f4612b = 0.0f;
            c3542i5.f4615e = 0.03f;
            c3542i5.f4614d = 0.003f;
        } else if (abs < 650000) {
            TXCVideoEffect.C3542i c3542i6 = this.f3929r;
            c3542i6.f4612b = 0.0f;
            c3542i6.f4615e = 0.03f;
            c3542i6.f4614d = 0.03f;
        } else if (abs < 700000) {
            TXCVideoEffect.C3542i c3542i7 = this.f3929r;
            c3542i7.f4612b = 0.0f;
            c3542i7.f4615e = 0.03f;
            c3542i7.f4614d = 0.015f;
        } else if (abs < 800000) {
            TXCVideoEffect.C3542i c3542i8 = this.f3929r;
            c3542i8.f4612b = 0.0f;
            c3542i8.f4615e = 0.03f;
            c3542i8.f4614d = 0.024f;
        } else if (abs < 900000) {
            this.f3929r.f4612b = 1.0f;
        } else if (abs < 1000000) {
            TXCVideoEffect.C3542i c3542i9 = this.f3929r;
            c3542i9.f4612b = 0.0f;
            c3542i9.f4615e = 0.03f;
            c3542i9.f4614d = 0.015f;
        } else if (abs < 1050000) {
            TXCVideoEffect.C3542i c3542i10 = this.f3929r;
            c3542i10.f4612b = 0.0f;
            c3542i10.f4615e = 0.03f;
            c3542i10.f4614d = 0.024f;
        } else if (abs < 1100000) {
            TXCVideoEffect.C3542i c3542i11 = this.f3929r;
            c3542i11.f4612b = 0.0f;
            c3542i11.f4615e = 0.03f;
            c3542i11.f4614d = 0.015f;
        } else if (abs < 1200000) {
            TXCVideoEffect.C3542i c3542i12 = this.f3929r;
            c3542i12.f4612b = 0.0f;
            c3542i12.f4615e = 0.03f;
            c3542i12.f4614d = 0.009f;
        } else if (abs < 1500000) {
            TXCVideoEffect.C3542i c3542i13 = this.f3929r;
            c3542i13.f4612b = 0.0f;
            c3542i13.f4615e = 0.03f;
            c3542i13.f4614d = 0.003f;
        } else if (abs < 2500000) {
            this.f3929r.f4612b = 1.0f;
        } else {
            this.f3920i = -1L;
        }
    }

    /* renamed from: j */
    private void m1859j(long j) {
        if (this.f3919h == -1) {
            if (this.f3916e) {
                this.f3919h = this.f3915d.f3465b;
            } else {
                this.f3919h = j;
            }
        }
        if (this.f3928q == null) {
            this.f3928q = new TXCVideoEffect.C3537d();
            TXCVideoEffect.C3537d c3537d = this.f3928q;
            c3537d.f4600e = 8.0f;
            c3537d.f4598c = new float[]{0.5f, 0.5f};
            c3537d.f4596a = 0.0f;
            c3537d.f4597b = 0.2f;
        }
        long abs = Math.abs(j - this.f3919h);
        if (abs < 120000) {
            TXCVideoEffect.C3537d c3537d2 = this.f3928q;
            c3537d2.f4599d = 0.0f;
            c3537d2.f4600e = 8.0f;
            c3537d2.f4598c = new float[]{0.0f, 0.0f};
            c3537d2.f4596a = 0.0f;
            c3537d2.f4597b = 0.0f;
            c3537d2.f4601f = new float[]{0.0f, 0.0f};
            c3537d2.f4602g = new float[]{0.0f, 0.0f};
            return;
        }
        int i = 1;
        while (true) {
            if (i > 8) {
                break;
            } else if (abs < (70000 * i) + 120000) {
                TXCVideoEffect.C3537d c3537d3 = this.f3928q;
                c3537d3.f4599d = i;
                c3537d3.f4600e = 8.0f;
                c3537d3.f4598c = new float[]{0.5f, 0.5f};
                c3537d3.f4596a = 0.0f;
                c3537d3.f4597b = 0.3f;
                if (i >= 3) {
                    c3537d3.f4601f = new float[]{-0.1f, 0.0f};
                    c3537d3.f4602g = new float[]{0.0f, 0.1f};
                } else {
                    c3537d3.f4601f = new float[]{0.0f, 0.0f};
                    c3537d3.f4602g = new float[]{0.0f, 0.0f};
                }
            } else {
                i++;
            }
        }
        if (abs <= 680000) {
            return;
        }
        if (abs <= 1080000) {
            TXCVideoEffect.C3537d c3537d4 = this.f3928q;
            c3537d4.f4599d = 0.0f;
            c3537d4.f4600e = 8.0f;
            c3537d4.f4598c = new float[]{0.0f, 0.0f};
            c3537d4.f4596a = 0.0f;
            c3537d4.f4597b = 0.0f;
            c3537d4.f4601f = new float[]{0.0f, 0.0f};
            c3537d4.f4602g = new float[]{0.0f, 0.0f};
            return;
        }
        this.f3919h = -1L;
    }

    /* renamed from: c */
    private void m1867c() {
        if (this.f3927p == null) {
            this.f3927p = new TXCVideoEffect.C3533a();
        }
    }

    /* renamed from: k */
    private void m1858k(long j) {
        if (this.f3918g == -1) {
            if (this.f3916e) {
                this.f3918g = this.f3915d.f3465b;
            } else {
                this.f3918g = j;
            }
        }
        if (this.f3926o == null) {
            this.f3926o = new TXCVideoEffect.C3546m();
        }
        long abs = Math.abs(j - this.f3918g);
        if (abs <= 1000000) {
            this.f3926o.f4625a = 4;
        } else if (abs <= 2000000) {
            this.f3926o.f4625a = 9;
        } else {
            this.f3918g = -1L;
        }
    }

    /* renamed from: l */
    private void m1857l(long j) {
        if (this.f3917f == -1) {
            if (this.f3916e) {
                this.f3917f = this.f3915d.f3465b;
            } else {
                this.f3917f = j;
            }
        }
        if (this.f3925n == null) {
            this.f3925n = new TXCVideoEffect.C3545l();
            TXCVideoEffect.C3545l c3545l = this.f3925n;
            c3545l.f4622f = 1;
            c3545l.f4624h = 0.3f;
        }
        long abs = Math.abs(j - this.f3917f);
        if (abs < 120000) {
            this.f3925n.f4623g = 0;
        } else if (abs < 230000) {
            this.f3925n.f4623g = 1;
        } else if (abs < 274000) {
            this.f3925n.f4623g = 2;
        } else if (abs < 318000) {
            this.f3925n.f4623g = 3;
        } else if (abs < 362000) {
            this.f3925n.f4623g = 4;
        } else if (abs < 406000) {
            this.f3925n.f4623g = 5;
        } else if (abs < 450000) {
            this.f3925n.f4623g = 6;
        } else if (abs < 494000) {
            this.f3925n.f4623g = 7;
        } else if (abs < 538000) {
            this.f3925n.f4623g = 8;
        } else if (abs < 582000) {
            this.f3925n.f4623g = 9;
        } else if (abs < 1120000) {
            this.f3925n.f4623g = 0;
        } else {
            this.f3917f = -1L;
        }
    }

    /* renamed from: a */
    public void m1874a() {
        TXCVideoEffect tXCVideoEffect = this.f3914c;
        if (tXCVideoEffect != null) {
            tXCVideoEffect.m1319a();
        }
    }
}
