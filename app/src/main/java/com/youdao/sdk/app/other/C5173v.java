package com.youdao.sdk.app.other;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/* renamed from: com.youdao.sdk.app.other.v */
/* loaded from: classes4.dex */
public class C5173v {

    /* renamed from: a */
    protected static C5173v f5932a = new C5173v();

    /* renamed from: a */
    public static TimeZone m177a() {
        return f5932a.m174d();
    }

    /* renamed from: b */
    public static Date m176b() {
        return f5932a.m173e();
    }

    /* renamed from: c */
    public static String m175c() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Z");
        simpleDateFormat.setTimeZone(m177a());
        return simpleDateFormat.format(m176b());
    }

    /* renamed from: d */
    public TimeZone m174d() {
        return TimeZone.getDefault();
    }

    /* renamed from: e */
    public Date m173e() {
        return new Date();
    }
}
