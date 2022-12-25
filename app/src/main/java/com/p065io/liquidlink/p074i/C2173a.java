package com.p065io.liquidlink.p074i;

import android.util.Base64;
import java.nio.charset.Charset;

/* renamed from: com.io.liquidlink.i.a */
/* loaded from: classes3.dex */
public class C2173a {
    /* renamed from: a */
    public static String m3921a(String str) {
        return m3920a(str, 0);
    }

    /* renamed from: a */
    public static String m3920a(String str, int i) {
        try {
            return new String(Base64.decode(str, i), Charset.forName("UTF-8"));
        } catch (Exception unused) {
            return str;
        }
    }
}
