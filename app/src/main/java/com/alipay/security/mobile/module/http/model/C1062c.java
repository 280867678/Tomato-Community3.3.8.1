package com.alipay.security.mobile.module.http.model;

import com.alipay.security.mobile.module.p047a.C1037a;

/* renamed from: com.alipay.security.mobile.module.http.model.c */
/* loaded from: classes2.dex */
public class C1062c extends C1060a {

    /* renamed from: h */
    public String f1146h;

    /* renamed from: i */
    public String f1147i;

    /* renamed from: j */
    public String f1148j;

    /* renamed from: k */
    public String f1149k;

    /* renamed from: l */
    public String f1150l;

    /* renamed from: m */
    public String f1151m;

    /* renamed from: n */
    public String f1152n;

    /* renamed from: o */
    public String f1153o;

    /* renamed from: p */
    public String f1154p = "";

    /* renamed from: a */
    public int m4191a() {
        return this.f1144a ? C1037a.m4303a(this.f1146h) ? 2 : 1 : "APPKEY_ERROR".equals(this.f1145b) ? 3 : 2;
    }

    /* renamed from: b */
    public boolean m4190b() {
        return "1".equals(this.f1148j);
    }

    /* renamed from: c */
    public String m4189c() {
        String str = this.f1149k;
        return str == null ? "0" : str;
    }
}
