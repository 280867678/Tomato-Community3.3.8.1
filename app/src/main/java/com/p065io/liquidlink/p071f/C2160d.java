package com.p065io.liquidlink.p071f;

import android.content.Context;

/* renamed from: com.io.liquidlink.f.d */
/* loaded from: classes3.dex */
public class C2160d {

    /* renamed from: c */
    private static C2160d f1447c;

    /* renamed from: d */
    private static final Object f1448d = new Object();

    /* renamed from: a */
    private AbstractC2161e f1449a;

    /* renamed from: b */
    private AbstractC2161e f1450b;

    private C2160d(Context context) {
        this.f1449a = new C2158b(context);
        this.f1450b = new C2159c(context);
    }

    /* renamed from: a */
    public static C2160d m3978a(Context context) {
        synchronized (f1448d) {
            if (f1447c == null) {
                f1447c = new C2160d(context.getApplicationContext());
            }
        }
        return f1447c;
    }

    /* renamed from: a */
    public String m3977a(String str) {
        String mo3974a = this.f1449a.mo3974a(str);
        if (mo3974a != null) {
            this.f1450b.mo3973a(str, mo3974a);
            this.f1449a.mo3972b(str);
        }
        return this.f1450b.mo3974a(str);
    }

    /* renamed from: a */
    public void m3976a(String str, String str2) {
        this.f1450b.mo3973a(str, str2);
    }

    /* renamed from: b */
    public void m3975b(String str, String str2) {
        String m3977a = m3977a(str);
        if (m3977a == null || !m3977a.equals(str2)) {
            m3976a(str, str2);
        }
    }
}
