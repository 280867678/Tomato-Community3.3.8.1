package com.netease.nis.captcha;

import android.content.Context;
import java.io.File;

/* renamed from: com.netease.nis.captcha.a */
/* loaded from: classes3.dex */
public class C2404a {

    /* renamed from: a */
    private static C2404a f1606a;

    /* renamed from: b */
    private Context f1607b;

    /* renamed from: c */
    private final String f1608c;

    /* renamed from: d */
    private final String f1609d = "mobile.v2.13.5.html";

    /* renamed from: e */
    private final String f1610e;

    private C2404a(Context context) {
        this.f1607b = context.getApplicationContext();
        this.f1608c = this.f1607b.getFilesDir().getAbsolutePath();
        this.f1610e = this.f1608c + File.separator + "captcha.html";
    }

    /* renamed from: a */
    public static C2404a m3829a(Context context) {
        if (f1606a == null) {
            synchronized (C2404a.class) {
                if (f1606a == null) {
                    f1606a = new C2404a(context);
                }
            }
        }
        return f1606a;
    }

    /* renamed from: a */
    public String m3830a() {
        return "mobile.v2.13.5.html";
    }
}
