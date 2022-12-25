package com.tencent.liteav.beauty;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCModule;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.CropRect;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import com.tencent.liteav.basic.p111g.TXSVFrame;
import com.tomatolive.library.utils.ConstantUtils;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/* renamed from: com.tencent.liteav.beauty.d */
/* loaded from: classes3.dex */
public class TXCVideoPreprocessor extends TXCModule implements TXIVideoPreprocessorListener {

    /* renamed from: a */
    protected Context f3212a;

    /* renamed from: b */
    protected boolean f3213b;

    /* renamed from: h */
    protected TXCFilterDrawer f3219h;

    /* renamed from: k */
    TXIVideoPreprocessorListenerEx f3222k;

    /* renamed from: c */
    protected boolean f3214c = false;

    /* renamed from: d */
    protected int f3215d = 0;

    /* renamed from: e */
    protected int f3216e = 0;

    /* renamed from: f */
    protected int f3217f = 0;

    /* renamed from: g */
    protected CropRect f3218g = null;

    /* renamed from: i */
    protected C3395b f3220i = new C3395b();

    /* renamed from: j */
    protected C3396c f3221j = null;

    /* renamed from: l */
    private boolean f3223l = false;

    /* renamed from: m */
    private long f3224m = 0;

    /* renamed from: n */
    private long f3225n = 0;

    /* renamed from: o */
    private long f3226o = 0;

    /* renamed from: p */
    private C3394a f3227p = new C3394a(this);

    /* compiled from: TXCVideoPreprocessor.java */
    /* renamed from: com.tencent.liteav.beauty.d$d */
    /* loaded from: classes3.dex */
    public static class C3397d {

        /* renamed from: a */
        public Bitmap f3256a;

        /* renamed from: b */
        public float f3257b;

        /* renamed from: c */
        public float f3258c;

        /* renamed from: d */
        public float f3259d;

        /* renamed from: e */
        public int f3260e;

        /* renamed from: f */
        public int f3261f;

        /* renamed from: g */
        public int f3262g;
    }

    /* renamed from: m */
    private int m2598m(int i) {
        if (i != 1) {
            if (i == 2) {
                return 180;
            }
            if (i == 3) {
                return 270;
            }
            return i;
        }
        return 90;
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
    public int willAddWatermark(int i, int i2, int i3) {
        boolean z = false;
        if (this.f3222k != null) {
            TXSVFrame tXSVFrame = new TXSVFrame();
            tXSVFrame.f2717d = i2;
            tXSVFrame.f2718e = i3;
            C3396c c3396c = this.f3221j;
            tXSVFrame.f2722i = c3396c != null ? c3396c.f3252j : 0;
            C3396c c3396c2 = this.f3221j;
            if (c3396c2 != null) {
                z = c3396c2.f3249g;
            }
            tXSVFrame.f2721h = z;
            tXSVFrame.f2714a = i;
            return this.f3222k.mo2580a(tXSVFrame);
        }
        return 0;
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
    public void didProcessFrame(int i, int i2, int i3, long j) {
        m2612b();
        if (this.f3222k != null) {
            TXSVFrame tXSVFrame = new TXSVFrame();
            tXSVFrame.f2717d = i2;
            tXSVFrame.f2718e = i3;
            C3396c c3396c = this.f3221j;
            boolean z = false;
            tXSVFrame.f2722i = c3396c != null ? c3396c.f3252j : 0;
            C3396c c3396c2 = this.f3221j;
            if (c3396c2 != null) {
                z = c3396c2.f3249g;
            }
            tXSVFrame.f2721h = z;
            tXSVFrame.f2714a = i;
            this.f3222k.mo2579a(tXSVFrame, j);
        }
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
    public void didProcessFrame(byte[] bArr, int i, int i2, int i3, long j) {
        TXIVideoPreprocessorListenerEx tXIVideoPreprocessorListenerEx = this.f3222k;
        if (tXIVideoPreprocessorListenerEx != null) {
            tXIVideoPreprocessorListenerEx.mo2563a(bArr, i, i2, i3, j);
        }
    }

    /* renamed from: b */
    private void m2612b() {
        long j;
        if (this.f3224m != 0) {
            setStatusValue(3002, Long.valueOf(System.currentTimeMillis() - this.f3224m));
        }
        this.f3225n++;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > 2000 + this.f3226o) {
            setStatusValue(3003, Double.valueOf((this.f3225n * 1000.0d) / (currentTimeMillis - j)));
            this.f3225n = 0L;
            this.f3226o = currentTimeMillis;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCVideoPreprocessor.java */
    /* renamed from: com.tencent.liteav.beauty.d$c */
    /* loaded from: classes3.dex */
    public static class C3396c {

        /* renamed from: a */
        public boolean f3243a;

        /* renamed from: b */
        public int f3244b;

        /* renamed from: c */
        public int f3245c;

        /* renamed from: d */
        public int f3246d;

        /* renamed from: e */
        public int f3247e;

        /* renamed from: f */
        public int f3248f;

        /* renamed from: g */
        public boolean f3249g;

        /* renamed from: h */
        public int f3250h;

        /* renamed from: i */
        public int f3251i;

        /* renamed from: j */
        public int f3252j;

        /* renamed from: k */
        public int f3253k;

        /* renamed from: l */
        public int f3254l;

        /* renamed from: m */
        public CropRect f3255m;

        private C3396c() {
            this.f3249g = false;
            this.f3253k = 5;
            this.f3254l = 0;
            this.f3255m = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TXCVideoPreprocessor.java */
    /* renamed from: com.tencent.liteav.beauty.d$b */
    /* loaded from: classes3.dex */
    public static class C3395b {

        /* renamed from: a */
        int f3230a;

        /* renamed from: b */
        int f3231b;

        /* renamed from: c */
        int f3232c;

        /* renamed from: d */
        int f3233d;

        /* renamed from: e */
        int f3234e;

        /* renamed from: f */
        int f3235f;

        /* renamed from: g */
        int f3236g;

        /* renamed from: h */
        int f3237h;

        /* renamed from: i */
        boolean f3238i;

        /* renamed from: j */
        boolean f3239j;

        /* renamed from: k */
        public int f3240k = 5;

        /* renamed from: l */
        public int f3241l = 0;

        /* renamed from: m */
        CropRect f3242m = null;

        C3395b() {
        }
    }

    public TXCVideoPreprocessor(Context context, boolean z) {
        this.f3213b = true;
        TXCLog.m2913i("TXCVideoPreprocessor", "TXCVideoPreprocessor version: VideoPreprocessor-v1.1");
        ConfigurationInfo deviceConfigurationInfo = ((ActivityManager) context.getSystemService("activity")).getDeviceConfigurationInfo();
        if (deviceConfigurationInfo != null) {
            TXCLog.m2913i("TXCVideoPreprocessor", "opengl es version " + deviceConfigurationInfo.reqGlEsVersion);
            TXCLog.m2913i("TXCVideoPreprocessor", "set GLContext " + z);
            if (deviceConfigurationInfo.reqGlEsVersion > 131072) {
                TXCLog.m2913i("TXCVideoPreprocessor", "This devices is OpenGlUtils.OPENGL_ES_3");
                TXCOpenGlUtils.m3008a(3);
            } else {
                TXCLog.m2913i("TXCVideoPreprocessor", "This devices is OpenGlUtils.OPENGL_ES_2");
                TXCOpenGlUtils.m3008a(2);
            }
        } else {
            TXCLog.m2914e("TXCVideoPreprocessor", "getDeviceConfigurationInfo opengl Info failed!");
        }
        this.f3212a = context;
        this.f3213b = z;
        this.f3219h = new TXCFilterDrawer(this.f3212a, this.f3213b);
        ReportDuaManage.m2863a().m2862a(context);
    }

    /* renamed from: a */
    public void m2613a(float[] fArr) {
        TXCFilterDrawer tXCFilterDrawer = this.f3219h;
        if (tXCFilterDrawer != null) {
            tXCFilterDrawer.m2806a(fArr);
        }
    }

    /* renamed from: a */
    public synchronized int m2614a(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        m2628a(i, i2, m2598m(i3), i4, i5);
        this.f3219h.m2797b(this.f3220i);
        return this.f3219h.m2808a(bArr, i4);
    }

    /* renamed from: a */
    public synchronized int m2627a(int i, int i2, int i3, int i4, int i5, int i6) {
        m2628a(i2, i3, m2598m(i4), i5, i6);
        this.f3219h.m2797b(this.f3220i);
        return this.f3219h.m2842a(i, i5);
    }

    /* renamed from: a */
    public synchronized int m2622a(TXSVFrame tXSVFrame, int i, int i2) {
        this.f3224m = System.currentTimeMillis();
        m2623a(tXSVFrame.f2724k);
        m2629a(tXSVFrame.f2719f, tXSVFrame.f2720g);
        m2615a(tXSVFrame.f2721h);
        m2630a(tXSVFrame.f2722i);
        m2613a(tXSVFrame.f2716c);
        return m2627a(tXSVFrame.f2714a, tXSVFrame.f2717d, tXSVFrame.f2718e, tXSVFrame.f2722i, i, i2);
    }

    /* renamed from: a */
    public synchronized void m2623a(CropRect cropRect) {
        this.f3218g = cropRect;
    }

    /* renamed from: a */
    public synchronized void m2629a(int i, int i2) {
        this.f3215d = i;
        this.f3216e = i2;
    }

    /* renamed from: a */
    public synchronized void m2630a(int i) {
        this.f3217f = i;
    }

    /* renamed from: a */
    public synchronized void m2625a(Bitmap bitmap, float f, float f2, float f3) {
        if (f < 0.0f || f2 < 0.0f || f3 < 0.0d) {
            TXCLog.m2914e("TXCVideoPreprocessor", "WaterMark param is Error!");
            return;
        }
        if (this.f3219h != null) {
            this.f3219h.m2837a(bitmap, f, f2, f3);
        }
    }

    /* renamed from: a */
    public synchronized void m2616a(List<C3397d> list) {
        if (this.f3219h != null) {
            ReportDuaManage.m2863a().m2858e();
            this.f3219h.m2812a(list);
        }
    }

    /* renamed from: a */
    public synchronized void m2615a(boolean z) {
        this.f3214c = z;
    }

    /* renamed from: a */
    public synchronized void m2620a(TXIVideoPreprocessorListener tXIVideoPreprocessorListener) {
        if (this.f3219h == null) {
            TXCLog.m2914e("TXCVideoPreprocessor", "setListener mDrawer is null!");
        } else {
            this.f3219h.m2816a(tXIVideoPreprocessorListener);
        }
    }

    /* renamed from: a */
    public synchronized void m2619a(TXIVideoPreprocessorListenerEx tXIVideoPreprocessorListenerEx) {
        if (this.f3219h == null) {
            TXCLog.m2914e("TXCVideoPreprocessor", "setListener mDrawer is null!");
            return;
        }
        this.f3222k = tXIVideoPreprocessorListenerEx;
        if (tXIVideoPreprocessorListenerEx == null) {
            this.f3219h.m2816a((TXIVideoPreprocessorListener) null);
        } else {
            this.f3219h.m2816a(this);
        }
    }

    /* renamed from: a */
    public synchronized void m2624a(TXINotifyListener tXINotifyListener) {
        if (this.f3219h == null) {
            TXCLog.m2914e("TXCVideoPreprocessor", "setListener mDrawer is null!");
        } else {
            this.f3219h.m2836a(tXINotifyListener);
        }
    }

    /* renamed from: a */
    private boolean m2628a(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        CropRect cropRect;
        int i9;
        int i10;
        int i11;
        int i12;
        CropRect cropRect2;
        CropRect cropRect3;
        CropRect cropRect4;
        CropRect cropRect5;
        if (this.f3221j == null) {
            this.f3221j = new C3396c();
            this.f3225n = 0L;
            this.f3226o = System.currentTimeMillis();
        }
        C3396c c3396c = this.f3221j;
        if (i == c3396c.f3244b && i2 == c3396c.f3245c && i3 == c3396c.f3248f && (((i6 = this.f3215d) <= 0 || i6 == c3396c.f3250h) && (((i7 = this.f3216e) <= 0 || i7 == this.f3221j.f3251i) && (((i8 = this.f3217f) <= 0 || i8 == this.f3221j.f3252j) && ((cropRect = this.f3218g) == null || (((i9 = cropRect.f2535c) <= 0 || ((cropRect5 = this.f3221j.f3255m) != null && i9 == cropRect5.f2535c)) && (((i10 = this.f3218g.f2536d) <= 0 || ((cropRect4 = this.f3221j.f3255m) != null && i10 == cropRect4.f2536d)) && (((i11 = this.f3218g.f2533a) < 0 || ((cropRect3 = this.f3221j.f3255m) != null && i11 == cropRect3.f2533a)) && ((i12 = this.f3218g.f2534b) < 0 || ((cropRect2 = this.f3221j.f3255m) != null && i12 == cropRect2.f2534b)))))))))) {
            boolean z = this.f3214c;
            C3396c c3396c2 = this.f3221j;
            if (z == c3396c2.f3249g) {
                if (i4 != c3396c2.f3253k || i5 != c3396c2.f3254l) {
                    C3396c c3396c3 = this.f3221j;
                    c3396c3.f3253k = i4;
                    C3395b c3395b = this.f3220i;
                    c3395b.f3240k = i4;
                    c3396c3.f3254l = i5;
                    c3395b.f3241l = i5;
                    this.f3219h.m2843a(i5);
                }
                return true;
            }
        }
        TXCLog.m2913i("TXCVideoPreprocessor", "Init sdk");
        TXCLog.m2913i("TXCVideoPreprocessor", "Input widht " + i + " height " + i2);
        C3396c c3396c4 = this.f3221j;
        c3396c4.f3244b = i;
        c3396c4.f3245c = i2;
        CropRect cropRect6 = this.f3218g;
        if (cropRect6 != null && cropRect6.f2533a >= 0 && cropRect6.f2534b >= 0 && cropRect6.f2535c > 0 && cropRect6.f2536d > 0) {
            TXCLog.m2913i("TXCVideoPreprocessor", "set Crop Rect; init ");
            CropRect cropRect7 = this.f3218g;
            int i13 = cropRect7.f2533a;
            int i14 = i - i13;
            int i15 = cropRect7.f2535c;
            if (i14 <= i15) {
                i15 = i - i13;
            }
            CropRect cropRect8 = this.f3218g;
            int i16 = cropRect8.f2534b;
            int i17 = i2 - i16;
            int i18 = cropRect8.f2536d;
            if (i17 <= i18) {
                i18 = i2 - i16;
            }
            CropRect cropRect9 = this.f3218g;
            cropRect9.f2535c = i15;
            cropRect9.f2536d = i18;
            i = cropRect9.f2535c;
            int i19 = cropRect9.f2536d;
            this.f3221j.f3255m = cropRect9;
            i2 = i19;
        }
        C3396c c3396c5 = this.f3221j;
        c3396c5.f3248f = i3;
        c3396c5.f3243a = this.f3213b;
        c3396c5.f3253k = i4;
        c3396c5.f3254l = i5;
        if (true == this.f3223l) {
            c3396c5.f3250h = this.f3215d;
            c3396c5.f3251i = this.f3216e;
        } else {
            c3396c5.f3250h = 0;
            c3396c5.f3251i = 0;
        }
        C3396c c3396c6 = this.f3221j;
        c3396c6.f3252j = this.f3217f;
        if (c3396c6.f3252j <= 0) {
            c3396c6.f3252j = 0;
        }
        C3396c c3396c7 = this.f3221j;
        if (c3396c7.f3250h <= 0 || c3396c7.f3251i <= 0) {
            C3396c c3396c8 = this.f3221j;
            int i20 = c3396c8.f3252j;
            if (90 == i20 || 270 == i20) {
                C3396c c3396c9 = this.f3221j;
                c3396c9.f3250h = i2;
                c3396c9.f3251i = i;
            } else {
                c3396c8.f3250h = i;
                c3396c8.f3251i = i2;
            }
        }
        C3396c c3396c10 = this.f3221j;
        int i21 = c3396c10.f3252j;
        if (90 == i21 || 270 == i21) {
            C3396c c3396c11 = this.f3221j;
            c3396c11.f3246d = c3396c11.f3251i;
            c3396c11.f3247e = c3396c11.f3250h;
        } else {
            c3396c10.f3246d = c3396c10.f3250h;
            c3396c10.f3247e = c3396c10.f3251i;
        }
        if (true != this.f3223l) {
            C3396c c3396c12 = this.f3221j;
            c3396c12.f3250h = this.f3215d;
            c3396c12.f3251i = this.f3216e;
            if (c3396c12.f3250h <= 0 || c3396c12.f3251i <= 0) {
                C3396c c3396c13 = this.f3221j;
                int i22 = c3396c13.f3252j;
                if (90 == i22 || 270 == i22) {
                    C3396c c3396c14 = this.f3221j;
                    c3396c14.f3250h = i2;
                    c3396c14.f3251i = i;
                } else {
                    c3396c13.f3250h = i;
                    c3396c13.f3251i = i2;
                }
            }
        }
        C3396c c3396c15 = this.f3221j;
        c3396c15.f3249g = this.f3214c;
        if (!m2621a(c3396c15)) {
            TXCLog.m2914e("TXCVideoPreprocessor", "init failed!");
            return false;
        }
        return true;
    }

    /* renamed from: a */
    private boolean m2621a(C3396c c3396c) {
        C3395b c3395b = this.f3220i;
        c3395b.f3233d = c3396c.f3244b;
        c3395b.f3234e = c3396c.f3245c;
        c3395b.f3242m = c3396c.f3255m;
        c3395b.f3236g = c3396c.f3246d;
        c3395b.f3235f = c3396c.f3247e;
        c3395b.f3237h = (c3396c.f3248f + 360) % 360;
        c3395b.f3231b = c3396c.f3250h;
        c3395b.f3232c = c3396c.f3251i;
        c3395b.f3230a = c3396c.f3252j;
        boolean z = c3396c.f3243a;
        c3395b.f3239j = z;
        c3395b.f3238i = c3396c.f3249g;
        c3395b.f3240k = c3396c.f3253k;
        c3395b.f3241l = c3396c.f3254l;
        if (this.f3219h == null) {
            this.f3219h = new TXCFilterDrawer(this.f3212a, z);
        }
        return this.f3219h.m2817a(this.f3220i);
    }

    /* renamed from: a */
    public synchronized void m2633a() {
        if (this.f3219h != null) {
            this.f3219h.m2846a();
        }
        this.f3221j = null;
    }

    /* renamed from: b */
    public synchronized void m2610b(int i) {
        if (this.f3219h != null) {
            this.f3219h.m2794c(i);
        }
        this.f3227p.m2596a("beautyStyle", i);
    }

    /* renamed from: c */
    public synchronized void m2608c(int i) {
        try {
            if (i > 9) {
                TXCLog.m2914e("TXCVideoPreprocessor", "Beauty value too large! set max value 9");
                i = 9;
            } else if (i < 0) {
                TXCLog.m2914e("TXCVideoPreprocessor", "Beauty < 0; set 0");
                i = 0;
            }
            if (this.f3219h != null) {
                this.f3219h.m2802b(i);
            }
            this.f3227p.m2596a("beautyLevel", i);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: d */
    public synchronized void m2607d(int i) {
        try {
            if (i > 9) {
                TXCLog.m2914e("TXCVideoPreprocessor", "Brightness value too large! set max value 9");
                i = 9;
            } else if (i < 0) {
                TXCLog.m2914e("TXCVideoPreprocessor", "Brightness < 0; set 0");
                i = 0;
            }
            if (this.f3219h != null) {
                this.f3219h.m2790d(i);
            }
            this.f3227p.m2596a("whiteLevel", i);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: e */
    public synchronized void m2606e(int i) {
        try {
            if (i > 9) {
                TXCLog.m2914e("TXCVideoPreprocessor", "Ruddy value too large! set max value 9");
                i = 9;
            } else if (i < 0) {
                TXCLog.m2914e("TXCVideoPreprocessor", "Ruddy < 0; set 0");
                i = 0;
            }
            if (this.f3219h != null) {
                this.f3219h.m2785f(i);
            }
            this.f3227p.m2596a("ruddyLevel", i);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: f */
    public void m2605f(int i) {
        if (i > 9) {
            TXCLog.m2914e("TXCVideoPreprocessor", "Brightness value too large! set max value 9");
            i = 9;
        } else if (i < 0) {
            TXCLog.m2914e("TXCVideoPreprocessor", "Brightness < 0; set 0");
            i = 0;
        }
        TXCFilterDrawer tXCFilterDrawer = this.f3219h;
        if (tXCFilterDrawer != null) {
            tXCFilterDrawer.m2787e(i);
        }
    }

    /* renamed from: a */
    public synchronized void m2618a(String str) {
        if (this.f3219h != null) {
            this.f3219h.m2814a(str);
        }
    }

    /* renamed from: b */
    public synchronized void m2609b(boolean z) {
        if (this.f3219h != null) {
            this.f3219h.m2810a(z);
        }
    }

    @TargetApi(18)
    /* renamed from: a */
    public boolean m2617a(String str, boolean z) {
        if (Build.VERSION.SDK_INT < 18) {
            return false;
        }
        TXCFilterDrawer tXCFilterDrawer = this.f3219h;
        if (tXCFilterDrawer == null) {
            return true;
        }
        tXCFilterDrawer.m2813a(str, z);
        return true;
    }

    /* renamed from: g */
    public synchronized void m2604g(int i) {
        if (this.f3219h != null) {
            this.f3219h.m2783g(i);
        }
        this.f3227p.m2596a("eyeBigScale", i);
    }

    /* renamed from: h */
    public synchronized void m2603h(int i) {
        if (this.f3219h != null) {
            this.f3219h.m2781h(i);
        }
        this.f3227p.m2596a("faceSlimLevel", i);
    }

    /* renamed from: i */
    public void m2602i(int i) {
        TXCFilterDrawer tXCFilterDrawer = this.f3219h;
        if (tXCFilterDrawer != null) {
            tXCFilterDrawer.m2779i(i);
        }
        this.f3227p.m2596a("faceVLevel", i);
    }

    /* renamed from: j */
    public void m2601j(int i) {
        TXCFilterDrawer tXCFilterDrawer = this.f3219h;
        if (tXCFilterDrawer != null) {
            tXCFilterDrawer.m2777j(i);
        }
        this.f3227p.m2596a("faceShortLevel", i);
    }

    /* renamed from: k */
    public void m2600k(int i) {
        TXCFilterDrawer tXCFilterDrawer = this.f3219h;
        if (tXCFilterDrawer != null) {
            tXCFilterDrawer.m2775k(i);
        }
        this.f3227p.m2596a("chinLevel", i);
    }

    /* renamed from: l */
    public void m2599l(int i) {
        TXCFilterDrawer tXCFilterDrawer = this.f3219h;
        if (tXCFilterDrawer != null) {
            tXCFilterDrawer.m2773l(i);
        }
        this.f3227p.m2596a("noseSlimLevel", i);
    }

    /* renamed from: a */
    public synchronized void m2632a(float f) {
        if (this.f3219h != null) {
            this.f3219h.m2845a(f);
        }
    }

    /* renamed from: a */
    public synchronized void m2626a(Bitmap bitmap) {
        if (this.f3219h != null) {
            this.f3219h.m2838a(bitmap);
        }
    }

    /* renamed from: a */
    public void m2631a(float f, Bitmap bitmap, float f2, Bitmap bitmap2, float f3) {
        TXCFilterDrawer tXCFilterDrawer = this.f3219h;
        if (tXCFilterDrawer != null) {
            tXCFilterDrawer.m2844a(f, bitmap, f2, bitmap2, f3);
        }
    }

    /* renamed from: b */
    public synchronized void m2611b(float f) {
        if (this.f3219h != null) {
            this.f3219h.m2803b(f);
        }
    }

    @Override // com.tencent.liteav.basic.module.TXCModule
    public void setID(String str) {
        super.setID(str);
        setStatusValue(3001, this.f3227p.m2597a());
    }

    /* compiled from: TXCVideoPreprocessor.java */
    /* renamed from: com.tencent.liteav.beauty.d$a */
    /* loaded from: classes3.dex */
    protected static class C3394a {

        /* renamed from: a */
        WeakReference<TXCVideoPreprocessor> f3228a;

        /* renamed from: b */
        private HashMap<String, String> f3229b = new HashMap<>();

        public C3394a(TXCVideoPreprocessor tXCVideoPreprocessor) {
            this.f3228a = new WeakReference<>(tXCVideoPreprocessor);
        }

        /* renamed from: a */
        public void m2596a(String str, int i) {
            String id;
            this.f3229b.put(str, String.valueOf(i));
            TXCVideoPreprocessor tXCVideoPreprocessor = this.f3228a.get();
            if (tXCVideoPreprocessor == null || (id = tXCVideoPreprocessor.getID()) == null || id.length() <= 0) {
                return;
            }
            tXCVideoPreprocessor.setStatusValue(3001, m2597a());
        }

        /* renamed from: a */
        public String m2597a() {
            String str = "";
            for (String str2 : this.f3229b.keySet()) {
                str = str + str2 + ":" + this.f3229b.get(str2) + ConstantUtils.PLACEHOLDER_STR_ONE;
            }
            return "{" + str + "}";
        }
    }
}
