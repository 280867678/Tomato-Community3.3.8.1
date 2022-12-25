package com.one.tomato.utils.recharge;

import android.text.TextUtils;
import org.slf4j.Marker;

/* loaded from: classes3.dex */
public class AlipayUtils {
    private static final AlipayUtils sInstance = new AlipayUtils();

    private AlipayUtils() {
    }

    public static AlipayUtils getInstance() {
        return sInstance;
    }

    public String hideAlipayName(String str) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (str.length() == 2) {
            sb.append(str.charAt(0));
            sb.append(Marker.ANY_MARKER);
        } else if (str.length() > 2) {
            for (int i = 0; i < str.length(); i++) {
                if (i == 0 || i == str.length() - 1) {
                    sb.append(str.charAt(i));
                } else {
                    sb.append(Marker.ANY_MARKER);
                }
            }
        } else {
            sb.append(str);
        }
        return sb.toString();
    }

    public String hideAlipayAccount(String str) {
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (TextUtils.isEmpty(str)) {
            str = str2;
        }
        boolean z = true;
        if (str.split("\\@").length >= 2) {
            str2 = str.split("\\@")[str.split("\\@").length - 1];
        } else {
            z = false;
        }
        String str3 = str.split("\\@")[0];
        int length = (str3.length() - 4) / 2;
        int i = length + 4;
        int i2 = i + 1;
        if (str3.length() >= i2) {
            sb.append(str3.substring(0, length));
            sb.append("****");
        } else {
            sb.append(str3);
        }
        if (str3.length() >= i2) {
            sb.append(str3.substring(i, str3.length()));
        }
        if (z) {
            sb.append("@");
            sb.append(str2);
        }
        return sb.toString();
    }
}
