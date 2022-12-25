package com.p089pm.liquidlink.p092c;

/* renamed from: com.pm.liquidlink.c.d */
/* loaded from: classes3.dex */
public class C3056d {
    private C3056d(String str) {
    }

    /* renamed from: a */
    public static C3056d m3731a(Class cls) {
        String simpleName = cls.getSimpleName();
        if (simpleName.length() > 23) {
            simpleName = simpleName.substring(0, 23);
        }
        return new C3056d(simpleName);
    }
}
