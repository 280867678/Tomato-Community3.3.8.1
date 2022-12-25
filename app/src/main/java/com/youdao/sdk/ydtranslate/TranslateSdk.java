package com.youdao.sdk.ydtranslate;

import android.content.Context;

/* loaded from: classes4.dex */
public class TranslateSdk {
    public static native boolean offlineInit(Context context);

    public static native boolean offlineInitzhangyue(Context context);

    public native String sign(Context context, String str, String str2, String str3);

    public native String signKey();
}
