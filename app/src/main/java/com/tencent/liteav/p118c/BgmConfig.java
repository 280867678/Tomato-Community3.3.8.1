package com.tencent.liteav.p118c;

import android.text.TextUtils;

/* renamed from: com.tencent.liteav.c.b */
/* loaded from: classes3.dex */
public class BgmConfig {

    /* renamed from: m */
    private static BgmConfig f3326m;

    /* renamed from: a */
    public String f3327a;

    /* renamed from: b */
    public long f3328b;

    /* renamed from: c */
    public long f3329c;

    /* renamed from: d */
    public long f3330d;

    /* renamed from: e */
    public boolean f3331e;

    /* renamed from: f */
    public float f3332f;

    /* renamed from: g */
    public float f3333g;

    /* renamed from: h */
    public boolean f3334h;

    /* renamed from: i */
    public long f3335i;

    /* renamed from: j */
    public boolean f3336j;

    /* renamed from: k */
    public long f3337k;

    /* renamed from: l */
    public long f3338l;

    /* renamed from: a */
    public static BgmConfig m2505a() {
        if (f3326m == null) {
            f3326m = new BgmConfig();
        }
        return f3326m;
    }

    private BgmConfig() {
        m2503b();
    }

    /* renamed from: a */
    public void m2504a(String str) {
        if (TextUtils.isEmpty(str)) {
            m2503b();
            return;
        }
        String str2 = this.f3327a;
        if (str2 != null && str2.equals(str)) {
            return;
        }
        m2502b(str);
    }

    /* renamed from: b */
    private void m2502b(String str) {
        this.f3327a = str;
    }

    /* renamed from: b */
    public void m2503b() {
        this.f3327a = null;
        this.f3328b = -1L;
        this.f3329c = -1L;
        this.f3334h = false;
        this.f3332f = 1.0f;
        this.f3331e = false;
        this.f3336j = false;
        this.f3337k = 0L;
        this.f3338l = 0L;
        this.f3335i = 0L;
    }
}
