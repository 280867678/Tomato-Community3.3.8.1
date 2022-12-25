package com.youdao.sdk.app.other;

import android.content.Context;
import android.content.SharedPreferences;

/* renamed from: com.youdao.sdk.app.other.s */
/* loaded from: classes4.dex */
public final class C5170s {
    /* renamed from: a */
    public static SharedPreferences m179a(Context context, String str) {
        return context.getSharedPreferences(str, 0);
    }

    /* renamed from: b */
    public static void m178b(Context context, String str, String str2) {
        SharedPreferences.Editor edit = m179a(context, "youdaoSdkCache").edit();
        edit.putString(str, str2);
        edit.commit();
    }
}
