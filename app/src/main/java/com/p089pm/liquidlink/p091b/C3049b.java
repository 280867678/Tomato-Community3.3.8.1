package com.p089pm.liquidlink.p091b;

import android.support.p002v4.app.NotificationCompat;
import org.json.JSONObject;

/* renamed from: com.pm.liquidlink.b.b */
/* loaded from: classes3.dex */
public class C3049b {

    /* renamed from: a */
    private EnumC3050c f1812a;

    /* renamed from: b */
    private int f1813b;

    /* renamed from: c */
    private String f1814c;

    /* renamed from: d */
    private String f1815d;

    /* renamed from: e */
    private String f1816e;

    public C3049b() {
    }

    public C3049b(EnumC3050c enumC3050c, int i) {
        this.f1812a = enumC3050c;
        this.f1813b = i;
    }

    /* renamed from: a */
    public static C3049b m3745a(String str) {
        String str2;
        C3049b c3049b = new C3049b();
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has("config") && !jSONObject.isNull("config")) {
            c3049b.m3739d(jSONObject.optString("config"));
        }
        int optInt = jSONObject.optInt("code", -2);
        if (optInt == 0) {
            c3049b.m3746a(EnumC3050c.SUCCESS);
            c3049b.m3747a(0);
            if (jSONObject.has("body") && !jSONObject.isNull("body")) {
                c3049b.m3741c(jSONObject.optString("body"));
            }
            if (jSONObject.has(NotificationCompat.CATEGORY_MESSAGE) && !jSONObject.isNull(NotificationCompat.CATEGORY_MESSAGE)) {
                str2 = jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE);
            }
            return c3049b;
        }
        c3049b.m3746a(EnumC3050c.ERROR);
        c3049b.m3747a(-2);
        str2 = optInt + " : " + jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE);
        c3049b.m3743b(str2);
        return c3049b;
    }

    /* renamed from: a */
    public EnumC3050c m3748a() {
        return this.f1812a;
    }

    /* renamed from: a */
    public void m3747a(int i) {
        this.f1813b = i;
    }

    /* renamed from: a */
    public void m3746a(EnumC3050c enumC3050c) {
        this.f1812a = enumC3050c;
    }

    /* renamed from: b */
    public int m3744b() {
        return this.f1813b;
    }

    /* renamed from: b */
    public void m3743b(String str) {
        this.f1815d = str;
    }

    /* renamed from: c */
    public String m3742c() {
        return this.f1815d;
    }

    /* renamed from: c */
    public void m3741c(String str) {
        this.f1814c = str;
    }

    /* renamed from: d */
    public String m3740d() {
        return this.f1814c;
    }

    /* renamed from: d */
    public void m3739d(String str) {
        this.f1816e = str;
    }

    /* renamed from: e */
    public String m3738e() {
        return this.f1816e;
    }

    public String toString() {
        return "{\"type\":" + this.f1812a + ",\"code\":" + this.f1813b + ",\"body\":\"" + this.f1814c + "\",\"errMsg\":\"" + this.f1815d + "\",\"config\":\"" + this.f1816e + "\"}";
    }
}
