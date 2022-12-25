package com.alipay.sdk.util;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.encrypt.C0972f;
import com.alipay.sdk.sys.C0988a;

/* renamed from: com.alipay.sdk.util.j */
/* loaded from: classes2.dex */
public class C1004j {

    /* renamed from: a */
    private static String f1067a;

    /* renamed from: a */
    public static void m4408a(C0988a c0988a, Context context, String str, String str2) {
        try {
            String m4544a = C0972f.m4544a(m4409a(context), str2, str);
            if (!TextUtils.isEmpty(str2) && TextUtils.isEmpty(m4544a)) {
                C0954a.m4633a(c0988a, "cp", "TriDesDecryptError", String.format("%s,%s", str, str2));
            }
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(str, m4544a).apply();
        } catch (Throwable th) {
            C0996c.m4436a(th);
        }
    }

    /* renamed from: b */
    public static String m4407b(C0988a c0988a, Context context, String str, String str2) {
        String str3 = null;
        try {
            String string = PreferenceManager.getDefaultSharedPreferences(context).getString(str, str2);
            if (!TextUtils.isEmpty(string)) {
                str3 = C0972f.m4542b(m4409a(context), string, str);
            }
            if (!TextUtils.isEmpty(string) && TextUtils.isEmpty(str3)) {
                C0954a.m4633a(c0988a, "cp", "TriDesEncryptError", String.format("%s,%s", str, string));
            }
        } catch (Exception e) {
            C0996c.m4436a(e);
        }
        return str3;
    }

    /* renamed from: a */
    private static String m4409a(Context context) {
        String str;
        if (TextUtils.isEmpty(f1067a)) {
            try {
                str = context.getApplicationContext().getPackageName();
            } catch (Throwable th) {
                C0996c.m4436a(th);
                str = "";
            }
            f1067a = (str + "0000000000000000000000000000").substring(0, 24);
        }
        return f1067a;
    }
}
