package com.tencent.liteav.beauty;

import android.content.Context;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;

/* renamed from: com.tencent.liteav.beauty.a */
/* loaded from: classes3.dex */
public class ReportDuaManage {

    /* renamed from: a */
    private static ReportDuaManage f2760a;

    /* renamed from: b */
    private static Context f2761b;

    /* renamed from: c */
    private static boolean f2762c;

    /* renamed from: d */
    private static boolean f2763d;

    /* renamed from: e */
    private static boolean f2764e;

    /* renamed from: f */
    private static boolean f2765f;

    /* renamed from: g */
    private static boolean f2766g;

    /* renamed from: h */
    private static boolean f2767h;

    /* renamed from: i */
    private static boolean f2768i;

    /* renamed from: j */
    private static boolean f2769j;

    /* renamed from: k */
    private static boolean f2770k;

    /* renamed from: l */
    private static boolean f2771l;

    /* renamed from: m */
    private static boolean f2772m;

    /* renamed from: n */
    private static boolean f2773n;

    /* renamed from: o */
    private static boolean f2774o;

    /* renamed from: p */
    private String f2775p = "ReportDuaManage";

    /* renamed from: a */
    public static ReportDuaManage m2863a() {
        if (f2760a == null) {
            f2760a = new ReportDuaManage();
        }
        return f2760a;
    }

    /* renamed from: a */
    public void m2862a(Context context) {
        m2857f();
        f2761b = context.getApplicationContext();
        if (!f2762c) {
            TXCLog.m2913i(this.f2775p, "reportSDKInit");
            TXCDRApi.txReportDAU(f2761b, 1201, 0, "reportSDKInit!");
        }
        f2762c = true;
    }

    /* renamed from: b */
    public void m2861b() {
        if (!f2763d) {
            TXCLog.m2913i(this.f2775p, "reportBeautyDua");
            TXCDRApi.txReportDAU(f2761b, 1202, 0, "reportBeautyDua");
        }
        f2763d = true;
    }

    /* renamed from: c */
    public void m2860c() {
        if (!f2764e) {
            TXCLog.m2913i(this.f2775p, "reportWhiteDua");
            TXCDRApi.txReportDAU(f2761b, 1203, 0, "reportWhiteDua");
        }
        f2764e = true;
    }

    /* renamed from: d */
    public void m2859d() {
        if (!f2769j) {
            TXCLog.m2913i(this.f2775p, "reportFilterImageDua");
            TXCDRApi.txReportDAU(f2761b, 1208, 0, "reportFilterImageDua");
        }
        f2769j = true;
    }

    /* renamed from: e */
    public void m2858e() {
        if (!f2773n) {
            TXCLog.m2913i(this.f2775p, "reportWarterMarkDua");
            TXCDRApi.txReportDAU(f2761b, 1212, 0, "reportWarterMarkDua");
        }
        f2773n = true;
    }

    /* renamed from: f */
    private void m2857f() {
        TXCLog.m2913i(this.f2775p, "resetReportState");
        f2762c = false;
        f2763d = false;
        f2764e = false;
        f2765f = false;
        f2766g = false;
        f2767h = false;
        f2768i = false;
        f2769j = false;
        f2770k = false;
        f2771l = false;
        f2772m = false;
        f2773n = false;
        f2774o = false;
    }
}
