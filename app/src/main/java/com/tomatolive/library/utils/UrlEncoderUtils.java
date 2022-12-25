package com.tomatolive.library.utils;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class UrlEncoderUtils {
    private UrlEncoderUtils() {
    }

    public static String encode(String str) {
        return TextUtils.isEmpty(str) ? "" : URLEncoder.encode(str).replace(Marker.ANY_NON_NULL_MARKER, "%20");
    }

    public static String encode(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, str2).replace(Marker.ANY_NON_NULL_MARKER, "%20");
        } catch (UnsupportedEncodingException unused) {
            return encode(str);
        }
    }

    public static String escape(String str) {
        StringBuilder sb = new StringBuilder(20);
        sb.ensureCapacity(str.length() * 6);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isDigit(charAt) || Character.isLowerCase(charAt) || Character.isUpperCase(charAt)) {
                sb.append(charAt);
            } else if (charAt < 256) {
                sb.append("%");
                if (charAt < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toString(charAt, 16));
            } else {
                sb.append("%u");
                sb.append(Integer.toString(charAt, 16));
            }
        }
        return sb.toString();
    }
}
