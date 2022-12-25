package com.zzhoujay.richtext.ext;

import com.zzhoujay.richtext.RichText;

/* loaded from: classes4.dex */
public class Debug {
    /* renamed from: e */
    public static void m122e(Throwable th) {
        if (RichText.debugMode) {
            th.printStackTrace();
        }
    }
}
