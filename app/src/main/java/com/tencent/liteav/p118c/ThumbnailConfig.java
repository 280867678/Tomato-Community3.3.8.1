package com.tencent.liteav.p118c;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.c.h */
/* loaded from: classes3.dex */
public class ThumbnailConfig {

    /* renamed from: a */
    private static ThumbnailConfig f3355a;

    /* renamed from: b */
    private ArrayList<Long> f3356b;

    /* renamed from: c */
    private int f3357c;

    /* renamed from: d */
    private TXCVideoEditConstants.C3521k f3358d;

    /* renamed from: e */
    private int f3359e;

    /* renamed from: f */
    private int f3360f;

    /* renamed from: g */
    private boolean f3361g;

    /* renamed from: a */
    public static ThumbnailConfig m2474a() {
        if (f3355a == null) {
            f3355a = new ThumbnailConfig();
        }
        return f3355a;
    }

    /* renamed from: a */
    public void m2471a(TXCVideoEditConstants.C3521k c3521k) {
        this.f3358d = c3521k;
    }

    /* renamed from: a */
    public void m2470a(ArrayList<Long> arrayList) {
        this.f3356b = arrayList;
    }

    /* renamed from: a */
    public void m2472a(long j) {
        if (j < 0) {
            return;
        }
        this.f3356b = new ArrayList<>();
        TXCVideoEditConstants.C3521k c3521k = this.f3358d;
        if (c3521k == null || c3521k.f4393a <= 0) {
            return;
        }
        CutTimeConfig m2501a = CutTimeConfig.m2501a();
        long m2494f = m2501a.m2494f();
        long m2493g = m2501a.m2493g();
        TXCLog.m2913i("ThumbnailConfig", "calculateThumbnailList startTimeUs : " + m2494f + ", endTimeUs : " + m2493g);
        long j2 = m2493g - m2494f;
        if (j2 <= 0) {
            j2 = j;
        }
        long j3 = j2 / this.f3358d.f4393a;
        for (int i = 0; i < this.f3358d.f4393a; i++) {
            long j4 = (i * j3) + m2494f;
            if (m2493g <= 0 || m2493g >= j) {
                if (j4 > j) {
                    j4 = j;
                }
            } else if (j4 > m2493g) {
                j4 = m2493g;
            }
            TXCLog.m2913i("ThumbnailConfig", "calculateThumbnailList frameTime : " + j4);
            this.f3356b.add(Long.valueOf(j4));
        }
    }

    /* renamed from: b */
    public List<Long> m2468b() {
        return this.f3356b;
    }

    /* renamed from: c */
    public int m2466c() {
        TXCVideoEditConstants.C3521k c3521k = this.f3358d;
        if (c3521k == null) {
            return 0;
        }
        return c3521k.f4393a;
    }

    /* renamed from: d */
    public Resolution m2465d() {
        int i;
        Resolution resolution = new Resolution();
        TXCVideoEditConstants.C3521k c3521k = this.f3358d;
        if (c3521k != null) {
            resolution.f3467a = c3521k.f4394b;
            resolution.f3468b = c3521k.f4395c;
        } else {
            int i2 = this.f3360f;
            if (i2 != 0 && (i = this.f3359e) != 0) {
                resolution.f3468b = i2;
                resolution.f3467a = i;
            }
        }
        return resolution;
    }

    /* renamed from: e */
    public boolean m2464e() {
        ArrayList<Long> arrayList = this.f3356b;
        return arrayList == null || arrayList.size() <= 0;
    }

    /* renamed from: f */
    public long m2463f() {
        return this.f3356b.get(0).longValue();
    }

    /* renamed from: g */
    public long m2462g() {
        this.f3357c++;
        return this.f3356b.remove(0).longValue();
    }

    /* renamed from: h */
    public int m2461h() {
        return this.f3357c;
    }

    /* renamed from: i */
    public void m2460i() {
        this.f3357c = 0;
        this.f3356b = null;
        this.f3361g = false;
    }

    /* renamed from: j */
    public void m2459j() {
        m2460i();
        this.f3358d = null;
    }

    /* renamed from: a */
    public void m2473a(int i) {
        this.f3359e = i;
        TXCVideoEditConstants.C3521k c3521k = this.f3358d;
        if (c3521k != null) {
            c3521k.f4394b = i;
        }
    }

    /* renamed from: b */
    public void m2467b(int i) {
        this.f3360f = i;
        TXCVideoEditConstants.C3521k c3521k = this.f3358d;
        if (c3521k != null) {
            c3521k.f4395c = i;
        }
    }

    /* renamed from: k */
    public boolean m2458k() {
        return this.f3361g;
    }

    /* renamed from: a */
    public void m2469a(boolean z) {
        this.f3361g = z;
    }
}
