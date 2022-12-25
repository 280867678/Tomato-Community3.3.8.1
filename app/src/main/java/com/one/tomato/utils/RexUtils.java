package com.one.tomato.utils;

import android.text.TextUtils;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class RexUtils {
    public static boolean isNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.matches("^[0-9]*$", str);
    }
}
