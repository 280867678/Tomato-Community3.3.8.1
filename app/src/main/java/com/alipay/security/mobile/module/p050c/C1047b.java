package com.alipay.security.mobile.module.p050c;

import com.alipay.security.mobile.module.p047a.C1037a;
import java.io.File;

/* renamed from: com.alipay.security.mobile.module.c.b */
/* loaded from: classes2.dex */
public final class C1047b {
    /* renamed from: a */
    public static String m4217a(String str) {
        String str2;
        try {
            str2 = C1051f.m4211a(str);
        } catch (Throwable unused) {
            str2 = "";
        }
        if (C1037a.m4303a(str2)) {
            return C1048c.m4215a(".SystemConfig" + File.separator + str);
        }
        return str2;
    }
}
