package com.zzhoujay.richtext.ext;

import android.text.TextUtils;

/* loaded from: classes4.dex */
public class TextKit {
    public static boolean isLocalPath(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith("/");
    }

    public static boolean isAssetPath(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith("file:///android_asset/");
    }
}
