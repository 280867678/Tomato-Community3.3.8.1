package com.blankj.utilcode.util;

import android.support.p002v4.util.SimpleArrayMap;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class RegexUtils {
    static {
        new SimpleArrayMap();
    }

    public static boolean isURL(CharSequence charSequence) {
        return isMatch("[a-zA-z]+://[^\\s]*", charSequence);
    }

    public static boolean isMatch(String str, CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0 && Pattern.matches(str, charSequence);
    }
}
