package com.alipay.sdk.util;

import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.util.b */
/* loaded from: classes2.dex */
public class C0995b {
    /* renamed from: a */
    public static JSONObject m4440a(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONObject[] jSONObjectArr;
        JSONObject jSONObject3 = new JSONObject();
        try {
            for (JSONObject jSONObject4 : new JSONObject[]{jSONObject, jSONObject2}) {
                if (jSONObject4 != null) {
                    Iterator<String> keys = jSONObject4.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        jSONObject3.put(next, jSONObject4.get(next));
                    }
                }
            }
        } catch (JSONException e) {
            C0996c.m4436a(e);
        }
        return jSONObject3;
    }
}
