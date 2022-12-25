package com.p097ta.utdid2.p098a.p099a;

import android.os.Build;

/* renamed from: com.ta.utdid2.a.a.c */
/* loaded from: classes3.dex */
public class C3202c {
    /* renamed from: a */
    public static boolean m3658a() {
        if (Build.VERSION.SDK_INT < 29) {
            return Build.VERSION.CODENAME.length() == 1 && Build.VERSION.CODENAME.charAt(0) >= 'Q' && Build.VERSION.CODENAME.charAt(0) <= 'Z';
        }
        return true;
    }
}
