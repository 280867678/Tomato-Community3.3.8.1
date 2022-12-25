package com.tencent.liteav.p118c;

import com.tencent.liteav.p119d.BeautyFilter;
import com.tencent.liteav.p119d.ComStaticFilter;
import com.tencent.liteav.p119d.StaticFilter;
import com.tencent.liteav.p119d.WaterMark;

/* renamed from: com.tencent.liteav.c.j */
/* loaded from: classes3.dex */
public class VideoPreProcessConfig {

    /* renamed from: b */
    private static VideoPreProcessConfig f3386b;

    /* renamed from: a */
    public float f3387a;

    /* renamed from: c */
    private WaterMark f3388c;

    /* renamed from: d */
    private StaticFilter f3389d;

    /* renamed from: e */
    private BeautyFilter f3390e;

    /* renamed from: f */
    private ComStaticFilter f3391f;

    /* renamed from: g */
    private int f3392g;

    /* renamed from: a */
    public static VideoPreProcessConfig m2427a() {
        if (f3386b == null) {
            f3386b = new VideoPreProcessConfig();
        }
        return f3386b;
    }

    private VideoPreProcessConfig() {
        m2417g();
    }

    /* renamed from: g */
    private void m2417g() {
        m2418f();
    }

    /* renamed from: a */
    public void m2423a(WaterMark waterMark) {
        this.f3388c = waterMark;
    }

    /* renamed from: b */
    public WaterMark m2422b() {
        return this.f3388c;
    }

    /* renamed from: a */
    public void m2425a(BeautyFilter beautyFilter) {
        this.f3390e = beautyFilter;
    }

    /* renamed from: c */
    public BeautyFilter m2421c() {
        return this.f3390e;
    }

    /* renamed from: d */
    public ComStaticFilter m2420d() {
        return this.f3391f;
    }

    /* renamed from: a */
    public void m2424a(ComStaticFilter comStaticFilter) {
        this.f3391f = comStaticFilter;
    }

    /* renamed from: e */
    public int m2419e() {
        return this.f3392g;
    }

    /* renamed from: a */
    public void m2426a(int i) {
        this.f3392g = i;
    }

    /* renamed from: f */
    public void m2418f() {
        this.f3387a = 1.0f;
        WaterMark waterMark = this.f3388c;
        if (waterMark != null) {
            waterMark.mo2297b();
        }
        this.f3388c = null;
        StaticFilter staticFilter = this.f3389d;
        if (staticFilter != null) {
            staticFilter.m2299a();
        }
        ComStaticFilter comStaticFilter = this.f3391f;
        if (comStaticFilter != null) {
            comStaticFilter.m2354a();
        }
        this.f3389d = null;
        this.f3390e = null;
        this.f3392g = 0;
    }
}
