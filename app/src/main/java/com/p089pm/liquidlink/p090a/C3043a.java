package com.p089pm.liquidlink.p090a;

/* renamed from: com.pm.liquidlink.a.a */
/* loaded from: classes3.dex */
public class C3043a {

    /* renamed from: a */
    public static final C3043a f1801a = new C3043a(1, "未初始化");

    /* renamed from: b */
    public static final C3043a f1802b = new C3043a(2, "正在初始化");

    /* renamed from: c */
    public static final C3043a f1803c = new C3043a(0, "初始化成功");

    /* renamed from: d */
    public static final C3043a f1804d = new C3043a(-1, "初始化失败");

    /* renamed from: e */
    public static final C3043a f1805e = new C3043a(-2, "初始化错误");

    /* renamed from: f */
    private int f1806f;

    C3043a(int i, String str) {
        this.f1806f = i;
    }

    /* renamed from: a */
    public static C3043a m3761a(int i) {
        return i != -2 ? i != -1 ? i != 0 ? (i == 1 || i != 2) ? f1801a : f1802b : f1803c : f1804d : f1805e;
    }

    /* renamed from: a */
    public int m3762a() {
        return this.f1806f;
    }
}
