package com.alipay.security.mobile.module.p051d;

import com.alipay.security.mobile.module.p047a.C1037a;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* renamed from: com.alipay.security.mobile.module.d.a */
/* loaded from: classes2.dex */
public final class C1052a {

    /* renamed from: a */
    private String f1124a;

    /* renamed from: b */
    private String f1125b;

    /* renamed from: c */
    private String f1126c;

    /* renamed from: d */
    private String f1127d;

    /* renamed from: e */
    private String f1128e;

    /* renamed from: f */
    private String f1129f;

    /* renamed from: g */
    private String f1130g;

    public C1052a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.f1124a = str;
        this.f1125b = str2;
        this.f1126c = str3;
        this.f1127d = str4;
        this.f1128e = str5;
        this.f1129f = str6;
        this.f1130g = str7;
    }

    public final String toString() {
        StringBuilder sb;
        String str;
        StringBuilder sb2;
        String str2;
        StringBuilder sb3;
        String str3;
        StringBuffer stringBuffer = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime()));
        stringBuffer.append("," + this.f1124a);
        stringBuffer.append("," + this.f1125b);
        stringBuffer.append("," + this.f1126c);
        stringBuffer.append("," + this.f1127d);
        if (C1037a.m4303a(this.f1128e) || this.f1128e.length() < 20) {
            sb = new StringBuilder(",");
            str = this.f1128e;
        } else {
            sb = new StringBuilder(",");
            str = this.f1128e.substring(0, 20);
        }
        sb.append(str);
        stringBuffer.append(sb.toString());
        if (C1037a.m4303a(this.f1129f) || this.f1129f.length() < 20) {
            sb2 = new StringBuilder(",");
            str2 = this.f1129f;
        } else {
            sb2 = new StringBuilder(",");
            str2 = this.f1129f.substring(0, 20);
        }
        sb2.append(str2);
        stringBuffer.append(sb2.toString());
        if (C1037a.m4303a(this.f1130g) || this.f1130g.length() < 20) {
            sb3 = new StringBuilder(",");
            str3 = this.f1130g;
        } else {
            sb3 = new StringBuilder(",");
            str3 = this.f1130g.substring(0, 20);
        }
        sb3.append(str3);
        stringBuffer.append(sb3.toString());
        return stringBuffer.toString();
    }
}
