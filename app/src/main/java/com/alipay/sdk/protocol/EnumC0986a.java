package com.alipay.sdk.protocol;

import android.text.TextUtils;

/* renamed from: com.alipay.sdk.protocol.a */
/* loaded from: classes2.dex */
public enum EnumC0986a {
    None("none"),
    WapPay("js://wappay"),
    Update("js://update"),
    OpenWeb("loc:openweb"),
    SetResult("loc:setResult"),
    Exit("loc:exit");
    

    /* renamed from: g */
    private String f1010g;

    EnumC0986a(String str) {
        this.f1010g = str;
    }

    /* renamed from: a */
    public static EnumC0986a m4502a(String str) {
        EnumC0986a[] values;
        if (TextUtils.isEmpty(str)) {
            return None;
        }
        EnumC0986a enumC0986a = None;
        for (EnumC0986a enumC0986a2 : values()) {
            if (str.startsWith(enumC0986a2.f1010g)) {
                return enumC0986a2;
            }
        }
        return enumC0986a;
    }
}
