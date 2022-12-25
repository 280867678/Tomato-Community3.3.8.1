package com.gen.p059mh.webapp_extensions.utils;

import android.util.Base64;

/* renamed from: com.gen.mh.webapp_extensions.utils.EncodeUtils */
/* loaded from: classes2.dex */
public final class EncodeUtils {
    public static String base64Encode2String(byte[] bArr) {
        return (bArr == null || bArr.length == 0) ? "" : Base64.encodeToString(bArr, 2);
    }
}
