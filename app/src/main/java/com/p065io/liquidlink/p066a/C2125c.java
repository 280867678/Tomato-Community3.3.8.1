package com.p065io.liquidlink.p066a;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.io.liquidlink.a.c */
/* loaded from: classes3.dex */
public class C2125c {

    /* renamed from: a */
    private String f1375a;

    /* renamed from: b */
    private String f1376b;

    /* renamed from: c */
    private int f1377c;

    /* renamed from: a */
    public static String m4090a(C2125c c2125c) {
        int i;
        JSONObject jSONObject = new JSONObject();
        if (c2125c == null) {
            i = 0;
        } else {
            try {
                jSONObject.put("pbText", c2125c.f1375a);
                jSONObject.put("pbHtml", c2125c.f1376b);
                i = c2125c.f1377c;
            } catch (JSONException unused) {
            }
        }
        jSONObject.put("pbType", i);
        return jSONObject.toString();
    }

    /* renamed from: c */
    public static C2125c m4083c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        C2125c c2125c = new C2125c();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("pbText")) {
                c2125c.m4089a(jSONObject.optString("pbText"));
            }
            if (jSONObject.has("pbHtml")) {
                c2125c.m4086b(jSONObject.optString("pbHtml"));
            }
            if (jSONObject.has("pbType")) {
                c2125c.m4091a(jSONObject.optInt("pbType"));
            }
            return c2125c;
        } catch (JSONException unused) {
            return null;
        }
    }

    /* renamed from: a */
    public String m4092a() {
        return this.f1375a;
    }

    /* renamed from: a */
    public void m4091a(int i) {
        this.f1377c = i;
    }

    /* renamed from: a */
    public void m4089a(String str) {
        this.f1375a = str;
    }

    /* renamed from: b */
    public String m4088b() {
        return this.f1376b;
    }

    /* renamed from: b */
    public void m4087b(int i) {
        this.f1377c = i | this.f1377c;
    }

    /* renamed from: b */
    public void m4086b(String str) {
        this.f1376b = str;
    }

    /* renamed from: c */
    public int m4085c() {
        return this.f1377c;
    }

    /* renamed from: c */
    public boolean m4084c(int i) {
        return (i & this.f1377c) != 0;
    }
}
