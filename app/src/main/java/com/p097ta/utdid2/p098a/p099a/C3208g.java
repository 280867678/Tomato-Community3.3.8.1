package com.p097ta.utdid2.p098a.p099a;

import java.util.regex.Pattern;

/* renamed from: com.ta.utdid2.a.a.g */
/* loaded from: classes3.dex */
public class C3208g {

    /* renamed from: a */
    private static final Pattern f1905a = Pattern.compile("([\t\r\n])+");

    /* renamed from: a */
    public static boolean m3647a(String str) {
        return str == null || str.length() <= 0;
    }

    /* renamed from: a */
    public static int m3648a(String str) {
        if (str.length() > 0) {
            int i = 0;
            for (char c : str.toCharArray()) {
                i = (i * 31) + c;
            }
            return i;
        }
        return 0;
    }
}
