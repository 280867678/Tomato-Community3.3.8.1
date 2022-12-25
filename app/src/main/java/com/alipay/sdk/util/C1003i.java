package com.alipay.sdk.util;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;

/* renamed from: com.alipay.sdk.util.i */
/* loaded from: classes2.dex */
public class C1003i {
    /* renamed from: a */
    public static void m4411a(C0988a c0988a, Context context, String str) {
        try {
            String m4410a = m4410a(str);
            C0996c.m4438a("mspl", "trade token: " + m4410a);
            if (TextUtils.isEmpty(m4410a)) {
                return;
            }
            C1004j.m4408a(c0988a, context, "pref_trade_token", m4410a);
        } catch (Throwable th) {
            C0954a.m4632a(c0988a, "biz", "SaveTradeTokenError", th);
            C0996c.m4436a(th);
        }
    }

    /* renamed from: a */
    public static String m4410a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(";");
        String str2 = null;
        for (int i = 0; i < split.length; i++) {
            if (split[i].startsWith("result={") && split[i].endsWith("}")) {
                String[] split2 = split[i].substring(8, split[i].length() - 1).split("&");
                int i2 = 0;
                while (true) {
                    if (i2 >= split2.length) {
                        break;
                    } else if (split2[i2].startsWith("trade_token=\"") && split2[i2].endsWith("\"")) {
                        str2 = split2[i2].substring(13, split2[i2].length() - 1);
                        break;
                    } else if (split2[i2].startsWith("trade_token=")) {
                        str2 = split2[i2].substring(12);
                        break;
                    } else {
                        i2++;
                    }
                }
            }
        }
        return str2;
    }

    /* renamed from: a */
    public static String m4412a(C0988a c0988a, Context context) {
        String m4407b = C1004j.m4407b(c0988a, context, "pref_trade_token", "");
        C0996c.m4438a("mspl", "get trade token: " + m4407b);
        return m4407b;
    }
}
