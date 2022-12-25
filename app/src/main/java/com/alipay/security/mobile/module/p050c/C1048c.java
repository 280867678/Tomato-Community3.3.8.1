package com.alipay.security.mobile.module.p050c;

import android.os.Environment;
import java.io.File;

/* renamed from: com.alipay.security.mobile.module.c.c */
/* loaded from: classes2.dex */
public final class C1048c {
    /* renamed from: a */
    public static String m4215a(String str) {
        try {
            if (!m4216a()) {
                return null;
            }
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), str);
            if (!file.exists()) {
                return null;
            }
            file.delete();
            return "";
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public static boolean m4216a() {
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState == null || externalStorageState.length() <= 0) {
            return false;
        }
        return (externalStorageState.equals("mounted") || externalStorageState.equals("mounted_ro")) && Environment.getExternalStorageDirectory() != null;
    }
}
