package com.zzhoujay.markdown.util;

/* loaded from: classes4.dex */
public class NumberKit {
    private static final String[] digit = {"", "i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix"};
    private static final String[] ten = {"", "x", "xx", "xxx", "xl", "l", "lx", "lxx", "lxxx", "xc"};
    private static final String[] hundreds = {"", "c", "cc", "ccc", "cd", "d", "dc", "dcc", "dccc", "cm"};
    private static final String[] thousand = {"", "m", "mm", "mmm"};

    public static String toRomanNumerals(int i) {
        while (i > 4996) {
            i -= 4996;
        }
        String str = thousand[i / 1000];
        int i2 = i % 1000;
        String str2 = hundreds[i2 / 100];
        int i3 = i2 % 100;
        return String.format("%s%s%s%s", str, str2, ten[i3 / 10], digit[i3 % 10]);
    }

    public static String toABC(int i) {
        int i2 = i / 26;
        int i3 = i % 26;
        StringBuilder sb = new StringBuilder();
        if (i2 > 26) {
            sb.append(toABC(i2 - 1));
            sb.append((char) (i3 + 97));
        } else if (i2 == 0) {
            sb.append((char) (i3 + 97));
        } else {
            sb.append((char) (i2 + 97));
            sb.append((char) (i3 + 97));
        }
        return sb.toString();
    }
}
