package com.one.tomato.utils.encrypt;

import android.text.TextUtils;
import android.util.Base64;

/* loaded from: classes3.dex */
public class Base64Util {
    public static String base64EncodeStr(String str) {
        return TextUtils.isEmpty(str) ? "" : Base64.encodeToString(str.getBytes(), 2);
    }

    public static String base64EncodeStr(String str, int i) {
        return TextUtils.isEmpty(str) ? "" : Base64.encodeToString(str.getBytes(), i);
    }

    public static String base64EncodeData(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }

    public static byte[] getDataBase64DecodedStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return Base64.decode(str, 2);
    }
}
