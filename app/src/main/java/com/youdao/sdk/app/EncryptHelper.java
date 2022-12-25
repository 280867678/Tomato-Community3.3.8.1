package com.youdao.sdk.app;

import android.util.Base64;
import com.youdao.sdk.app.other.C5167i;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydtranslate.TranslateSdk;

/* loaded from: classes4.dex */
public class EncryptHelper {
    public static String[] generateEncryptV1(String str) {
        try {
            return new String[]{getBase64(C5167i.m186a(getKey(), str)), "0"};
        } catch (Exception unused) {
            return new String[]{getBase64(str.getBytes()), "1"};
        }
    }

    public static String generateDecryptV1(String str) {
        try {
            return C5167i.m184b(getKey(), str);
        } catch (Exception e) {
            YouDaoLog.m166e("decrypt error", e);
            return null;
        }
    }

    public static String generateEncrypt(String str) {
        try {
            return getBase64(C5167i.m186a(getKey(), str));
        } catch (Exception e) {
            YouDaoLog.m166e("encrypt error", e);
            return null;
        }
    }

    public static String getKey() {
        return new TranslateSdk().signKey();
    }

    public static String getBase64(byte[] bArr) {
        return new StringBuffer(Base64.encodeToString(bArr, 2)).toString();
    }
}
