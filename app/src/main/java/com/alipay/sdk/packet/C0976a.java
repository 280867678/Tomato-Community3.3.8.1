package com.alipay.sdk.packet;

import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;

/* renamed from: com.alipay.sdk.packet.a */
/* loaded from: classes2.dex */
public class C0976a {
    /* renamed from: a */
    public static String m4535a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String[] split = str.split("&");
        if (split.length == 0) {
            return "";
        }
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        for (String str6 : split) {
            if (TextUtils.isEmpty(str2)) {
                str2 = m4534b(str6);
            }
            if (TextUtils.isEmpty(str3)) {
                str3 = m4533c(str6);
            }
            if (TextUtils.isEmpty(str4)) {
                str4 = m4532d(str6);
            }
            if (TextUtils.isEmpty(str5)) {
                str5 = m4530f(str6);
            }
        }
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str2)) {
            sb.append("biz_type=" + str2 + ";");
        }
        if (!TextUtils.isEmpty(str3)) {
            sb.append("biz_no=" + str3 + ";");
        }
        if (!TextUtils.isEmpty(str4)) {
            sb.append("trade_no=" + str4 + ";");
        }
        if (!TextUtils.isEmpty(str5)) {
            sb.append("app_userid=" + str5 + ";");
        }
        String sb2 = sb.toString();
        return sb2.endsWith(";") ? sb2.substring(0, sb2.length() - 1) : sb2;
    }

    /* renamed from: b */
    private static String m4534b(String str) {
        if (!str.contains("biz_type")) {
            return null;
        }
        return m4531e(str);
    }

    /* renamed from: c */
    private static String m4533c(String str) {
        if (!str.contains("biz_no")) {
            return null;
        }
        return m4531e(str);
    }

    /* renamed from: d */
    private static String m4532d(String str) {
        if (!str.contains("trade_no") || str.startsWith("out_trade_no")) {
            return null;
        }
        return m4531e(str);
    }

    /* renamed from: e */
    private static String m4531e(String str) {
        String[] split = str.split(SimpleComparison.EQUAL_TO_OPERATION);
        if (split.length > 1) {
            String str2 = split[1];
            return str2.contains("\"") ? str2.replaceAll("\"", "") : str2;
        }
        return null;
    }

    /* renamed from: f */
    private static String m4530f(String str) {
        if (!str.contains("app_userid")) {
            return null;
        }
        return m4531e(str);
    }
}
