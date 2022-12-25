package com.alipay.sdk.util;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.cons.C0961a;

/* renamed from: com.alipay.sdk.util.m */
/* loaded from: classes2.dex */
public class C1007m {
    /* renamed from: a */
    public static String m4399a(Context context) {
        if (EnvUtils.isSandBox()) {
            return "https://mobilegw.alipaydev.com/mgw.htm";
        }
        if (context == null) {
            return C0961a.f959a;
        }
        String str = C0961a.f959a;
        return TextUtils.isEmpty(str) ? C0961a.f959a : str;
    }
}
