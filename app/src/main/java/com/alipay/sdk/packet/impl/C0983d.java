package com.alipay.sdk.packet.impl;

import android.content.Context;
import com.alipay.sdk.packet.AbstractC0980e;
import com.alipay.sdk.packet.C0977b;
import com.alipay.sdk.sys.C0988a;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.packet.impl.d */
/* loaded from: classes2.dex */
public class C0983d extends AbstractC0980e {
    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: a */
    protected String mo4505a(C0988a c0988a, String str, JSONObject jSONObject) {
        return str;
    }

    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: a */
    protected JSONObject mo4503a() throws JSONException {
        return null;
    }

    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: a */
    protected Map<String, String> mo4504a(boolean z, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("msp-gzip", String.valueOf(z));
        hashMap.put("content-type", "application/octet-stream");
        hashMap.put("des-mode", "CBC");
        return hashMap;
    }

    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: c */
    protected String mo4507c() throws JSONException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("api_name", "/sdk/log");
        hashMap.put("api_version", "1.0.0");
        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("log_v", "1.0");
        return m4509a(hashMap, hashMap2);
    }

    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: a */
    public C0977b mo4506a(C0988a c0988a, Context context, String str) throws Throwable {
        return m4512a(c0988a, context, str, "https://mcgw.alipay.com/sdklog.do", true);
    }
}
