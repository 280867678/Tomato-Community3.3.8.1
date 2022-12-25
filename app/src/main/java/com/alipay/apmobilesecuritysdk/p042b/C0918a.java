package com.alipay.apmobilesecuritysdk.p042b;

import com.alipay.security.mobile.module.http.C1059d;
import com.alipay.security.mobile.module.p047a.C1037a;

/* renamed from: com.alipay.apmobilesecuritysdk.b.a */
/* loaded from: classes2.dex */
public final class C0918a {

    /* renamed from: b */
    private static C0918a f845b = new C0918a();

    /* renamed from: a */
    private int f846a = 0;

    /* renamed from: a */
    public static C0918a m4786a() {
        return f845b;
    }

    /* renamed from: a */
    public final void m4785a(int i) {
        this.f846a = i;
    }

    /* renamed from: b */
    public final int m4784b() {
        return this.f846a;
    }

    /* renamed from: c */
    public final String m4783c() {
        String m4196a = C1059d.m4196a();
        if (C1037a.m4299b(m4196a)) {
            return m4196a;
        }
        int i = this.f846a;
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "https://mobilegw.alipay.com/mgw.htm" : "http://mobilegw.aaa.alipay.net/mgw.htm" : "http://mobilegw-1-64.test.alipay.net/mgw.htm" : "https://mobilegw.alipay.com/mgw.htm" : "http://mobilegw.stable.alipay.net/mgw.htm";
    }
}
