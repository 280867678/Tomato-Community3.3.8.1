package com.p065io.liquidlink.p067b;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.io.liquidlink.b.b */
/* loaded from: classes3.dex */
public class C2128b {

    /* renamed from: a */
    private Boolean f1381a;

    /* renamed from: b */
    private Boolean f1382b;

    /* renamed from: c */
    private Boolean f1383c;

    /* renamed from: d */
    private Boolean f1384d;

    /* renamed from: e */
    private Long f1385e;

    /* renamed from: f */
    private String f1386f;

    /* renamed from: g */
    private List f1387g = new ArrayList();

    /* renamed from: b */
    public static C2128b m4068b(String str) {
        C2128b c2128b = new C2128b();
        if (TextUtils.isEmpty(str)) {
            return c2128b;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("wakeupStatsEnabled")) {
                c2128b.m4073a(Boolean.valueOf(jSONObject.optBoolean("wakeupStatsEnabled", true)));
            }
            if (jSONObject.has("aliveStatsEnabled")) {
                c2128b.m4066c(Boolean.valueOf(jSONObject.optBoolean("aliveStatsEnabled", true)));
            }
            if (jSONObject.has("registerStatsEnabled")) {
                c2128b.m4069b(Boolean.valueOf(jSONObject.optBoolean("registerStatsEnabled", true)));
            }
            if (jSONObject.has("eventStatsEnabled")) {
                c2128b.m4066c(Boolean.valueOf(jSONObject.optBoolean("eventStatsEnabled", true)));
            }
            if (jSONObject.has("reportPeriod")) {
                c2128b.m4072a(Long.valueOf(jSONObject.optLong("reportPeriod")));
            }
            if (jSONObject.has("installId")) {
                c2128b.m4071a(jSONObject.optString("installId"));
            }
        } catch (JSONException unused) {
        }
        return c2128b;
    }

    /* renamed from: d */
    private boolean m4064d(Boolean bool) {
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    /* renamed from: a */
    public Boolean m4076a() {
        return this.f1381a;
    }

    /* renamed from: a */
    public void m4075a(AbstractC2127a abstractC2127a) {
        this.f1387g.add(abstractC2127a);
    }

    /* renamed from: a */
    public void m4074a(C2128b c2128b) {
        this.f1381a = c2128b.m4076a();
        this.f1382b = c2128b.m4065d();
        this.f1383c = c2128b.m4067c();
        this.f1384d = c2128b.m4065d();
        this.f1385e = c2128b.m4061g();
        this.f1386f = c2128b.m4060h();
    }

    /* renamed from: a */
    public void m4073a(Boolean bool) {
        this.f1381a = bool;
    }

    /* renamed from: a */
    public void m4072a(Long l) {
        this.f1385e = l;
    }

    /* renamed from: a */
    public void m4071a(String str) {
        this.f1386f = str;
    }

    /* renamed from: b */
    public void m4069b(Boolean bool) {
        this.f1383c = bool;
    }

    /* renamed from: b */
    public boolean m4070b() {
        return m4064d(this.f1381a);
    }

    /* renamed from: c */
    public Boolean m4067c() {
        return this.f1383c;
    }

    /* renamed from: c */
    public void m4066c(Boolean bool) {
        this.f1384d = bool;
    }

    /* renamed from: d */
    public Boolean m4065d() {
        return this.f1384d;
    }

    /* renamed from: e */
    public boolean m4063e() {
        return m4064d(this.f1384d);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2128b.class != obj.getClass()) {
            return false;
        }
        C2128b c2128b = (C2128b) obj;
        Boolean bool = this.f1381a;
        if (bool == null ? c2128b.f1381a != null : !bool.equals(c2128b.f1381a)) {
            return false;
        }
        Boolean bool2 = this.f1382b;
        if (bool2 == null ? c2128b.f1382b != null : !bool2.equals(c2128b.f1382b)) {
            return false;
        }
        Boolean bool3 = this.f1383c;
        if (bool3 == null ? c2128b.f1383c != null : !bool3.equals(c2128b.f1383c)) {
            return false;
        }
        Boolean bool4 = this.f1384d;
        if (bool4 == null ? c2128b.f1384d != null : !bool4.equals(c2128b.f1384d)) {
            return false;
        }
        Long l = this.f1385e;
        if (l == null ? c2128b.f1385e != null : !l.equals(c2128b.f1385e)) {
            return false;
        }
        String str = this.f1386f;
        String str2 = c2128b.f1386f;
        return str != null ? str.equals(str2) : str2 == null;
    }

    /* renamed from: f */
    public boolean m4062f() {
        return m4064d(this.f1383c);
    }

    /* renamed from: g */
    public Long m4061g() {
        return this.f1385e;
    }

    /* renamed from: h */
    public String m4060h() {
        return this.f1386f;
    }

    public int hashCode() {
        Boolean bool = this.f1381a;
        int i = 0;
        int hashCode = (bool != null ? bool.hashCode() : 0) * 31;
        Boolean bool2 = this.f1382b;
        int hashCode2 = (hashCode + (bool2 != null ? bool2.hashCode() : 0)) * 31;
        Boolean bool3 = this.f1383c;
        int hashCode3 = (hashCode2 + (bool3 != null ? bool3.hashCode() : 0)) * 31;
        Boolean bool4 = this.f1384d;
        int hashCode4 = (hashCode3 + (bool4 != null ? bool4.hashCode() : 0)) * 31;
        Long l = this.f1385e;
        int hashCode5 = (hashCode4 + (l != null ? l.hashCode() : 0)) * 31;
        String str = this.f1386f;
        if (str != null) {
            i = str.hashCode();
        }
        return hashCode5 + i;
    }

    /* renamed from: i */
    public void m4059i() {
        for (AbstractC2127a abstractC2127a : this.f1387g) {
            abstractC2127a.mo3985a(this);
        }
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("wakeupStatsEnabled", this.f1381a);
            jSONObject.put("registerStatsEnabled", this.f1383c);
            jSONObject.put("eventStatsEnabled", this.f1384d);
            jSONObject.put("reportPeriod", this.f1385e);
            jSONObject.put("installId", this.f1386f);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }
}
