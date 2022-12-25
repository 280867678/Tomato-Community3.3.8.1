package com.alipay.android.phone.mrpc.core;

import android.text.format.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.alipay.android.phone.mrpc.core.k */
/* loaded from: classes2.dex */
public final class C0900k {

    /* renamed from: a */
    private static final Pattern f781a = Pattern.compile("([0-9]{1,2})[- ]([A-Za-z]{3,9})[- ]([0-9]{2,4})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])");

    /* renamed from: b */
    private static final Pattern f782b = Pattern.compile("[ ]([A-Za-z]{3,9})[ ]+([0-9]{1,2})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])[ ]([0-9]{2,4})");

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.alipay.android.phone.mrpc.core.k$a */
    /* loaded from: classes2.dex */
    public static class C0901a {

        /* renamed from: a */
        int f783a;

        /* renamed from: b */
        int f784b;

        /* renamed from: c */
        int f785c;

        C0901a(int i, int i2, int i3) {
            this.f783a = i;
            this.f784b = i2;
            this.f785c = i3;
        }
    }

    /* renamed from: a */
    public static long m4847a(String str) {
        int m4844d;
        int i;
        int i2;
        C0901a c0901a;
        int i3;
        int i4;
        int i5;
        Matcher matcher = f781a.matcher(str);
        if (matcher.find()) {
            int m4846b = m4846b(matcher.group(1));
            int m4845c = m4845c(matcher.group(2));
            int m4844d2 = m4844d(matcher.group(3));
            c0901a = m4843e(matcher.group(4));
            i = m4845c;
            i2 = m4846b;
            m4844d = m4844d2;
        } else {
            Matcher matcher2 = f782b.matcher(str);
            if (!matcher2.find()) {
                throw new IllegalArgumentException();
            }
            int m4845c2 = m4845c(matcher2.group(1));
            int m4846b2 = m4846b(matcher2.group(2));
            C0901a m4843e = m4843e(matcher2.group(3));
            m4844d = m4844d(matcher2.group(4));
            i = m4845c2;
            i2 = m4846b2;
            c0901a = m4843e;
        }
        if (m4844d >= 2038) {
            i4 = 1;
            i5 = 0;
            i3 = 2038;
        } else {
            i3 = m4844d;
            i4 = i2;
            i5 = i;
        }
        Time time = new Time("UTC");
        time.set(c0901a.f785c, c0901a.f784b, c0901a.f783a, i4, i5, i3);
        return time.toMillis(false);
    }

    /* renamed from: b */
    private static int m4846b(String str) {
        return str.length() == 2 ? ((str.charAt(0) - '0') * 10) + (str.charAt(1) - '0') : str.charAt(0) - '0';
    }

    /* renamed from: c */
    private static int m4845c(String str) {
        int lowerCase = ((Character.toLowerCase(str.charAt(0)) + Character.toLowerCase(str.charAt(1))) + Character.toLowerCase(str.charAt(2))) - 291;
        if (lowerCase != 9) {
            if (lowerCase == 10) {
                return 1;
            }
            if (lowerCase == 22) {
                return 0;
            }
            if (lowerCase == 26) {
                return 7;
            }
            if (lowerCase == 29) {
                return 2;
            }
            if (lowerCase == 32) {
                return 3;
            }
            if (lowerCase == 40) {
                return 6;
            }
            if (lowerCase == 42) {
                return 5;
            }
            if (lowerCase == 48) {
                return 10;
            }
            switch (lowerCase) {
                case 35:
                    return 9;
                case 36:
                    return 4;
                case 37:
                    return 8;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return 11;
    }

    /* renamed from: d */
    private static int m4844d(String str) {
        if (str.length() == 2) {
            int charAt = ((str.charAt(0) - '0') * 10) + (str.charAt(1) - '0');
            return charAt >= 70 ? charAt + 1900 : charAt + 2000;
        } else if (str.length() == 3) {
            return ((str.charAt(0) - '0') * 100) + ((str.charAt(1) - '0') * 10) + (str.charAt(2) - '0') + 1900;
        } else {
            if (str.length() != 4) {
                return 1970;
            }
            return ((str.charAt(0) - '0') * 1000) + ((str.charAt(1) - '0') * 100) + ((str.charAt(2) - '0') * 10) + (str.charAt(3) - '0');
        }
    }

    /* renamed from: e */
    private static C0901a m4843e(String str) {
        int i;
        int i2;
        int i3;
        int charAt = str.charAt(0) - '0';
        if (str.charAt(1) != ':') {
            i = 2;
            charAt = (charAt * 10) + (str.charAt(1) - '0');
        } else {
            i = 1;
        }
        int i4 = i + 1 + 1 + 1 + 1;
        return new C0901a(charAt, ((str.charAt(i2) - '0') * 10) + (str.charAt(i3) - '0'), ((str.charAt(i4) - '0') * 10) + (str.charAt(i4 + 1) - '0'));
    }
}
