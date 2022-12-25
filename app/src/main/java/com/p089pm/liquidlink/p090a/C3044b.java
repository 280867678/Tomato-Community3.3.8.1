package com.p089pm.liquidlink.p090a;

import android.text.TextUtils;
import java.io.Serializable;
import org.json.JSONObject;

/* renamed from: com.pm.liquidlink.a.b */
/* loaded from: classes3.dex */
public class C3044b implements Serializable {

    /* renamed from: a */
    private String f1807a = "";

    /* renamed from: b */
    private String f1808b = "";

    /* renamed from: c */
    private String f1809c = "";

    /* renamed from: d */
    public static C3044b m3755d(String str) {
        C3044b c3044b = new C3044b();
        if (TextUtils.isEmpty(str)) {
            return c3044b;
        }
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has("market")) {
            c3044b.m3759a(jSONObject.optString("market"));
        }
        if (jSONObject.has("channelCode")) {
            c3044b.m3757b(jSONObject.optString("channelCode"));
        }
        if (jSONObject.has("bind")) {
            c3044b.m3756c(jSONObject.optString("bind"));
        }
        return c3044b;
    }

    /* renamed from: a */
    public String m3760a() {
        return this.f1808b;
    }

    /* renamed from: a */
    public void m3759a(String str) {
        this.f1807a = str;
    }

    /* renamed from: b */
    public String m3758b() {
        return this.f1809c;
    }

    /* renamed from: b */
    public void m3757b(String str) {
        this.f1808b = str;
    }

    /* renamed from: c */
    public void m3756c(String str) {
        this.f1809c = str;
    }
}
