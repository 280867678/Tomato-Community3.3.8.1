package com.alipay.sdk.packet.impl;

import com.alipay.sdk.packet.AbstractC0980e;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.packet.impl.b */
/* loaded from: classes2.dex */
public class C0982b extends AbstractC0980e {
    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: b */
    protected String mo4508b() {
        return "5.0.0";
    }

    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: a */
    protected JSONObject mo4503a() throws JSONException {
        return AbstractC0980e.m4510a("sdkConfig", "obtain");
    }
}
