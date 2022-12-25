package com.security.sdk.p095c;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* renamed from: com.security.sdk.c.c */
/* loaded from: classes3.dex */
public class C3086c {

    /* renamed from: a */
    private SharedPreferences f1877a;

    /* renamed from: b */
    private SharedPreferences.Editor f1878b;

    public C3086c(Context context) {
        this.f1877a = context.getSharedPreferences("security_info", 0);
        this.f1878b = this.f1877a.edit();
    }

    public C3086c(Context context, String str) {
        this.f1877a = context.getSharedPreferences(str, 0);
        this.f1878b = this.f1877a.edit();
    }

    /* renamed from: a */
    public void m3684a() {
        this.f1878b.clear();
        this.f1878b.commit();
    }

    /* renamed from: a */
    public void m3683a(String str) {
        this.f1878b.remove(str);
        this.f1878b.commit();
    }

    /* renamed from: a */
    public void m3682a(String str, Object obj) {
        SharedPreferences.Editor editor;
        String obj2;
        if (!(obj instanceof String)) {
            if (obj instanceof Integer) {
                this.f1878b.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Boolean) {
                this.f1878b.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Float) {
                this.f1878b.putFloat(str, ((Float) obj).floatValue());
            } else if (obj instanceof Long) {
                this.f1878b.putLong(str, ((Long) obj).longValue());
            } else {
                editor = this.f1878b;
                obj2 = obj.toString();
            }
            this.f1878b.commit();
        }
        editor = this.f1878b;
        obj2 = (String) obj;
        editor.putString(str, obj2);
        this.f1878b.commit();
    }

    /* renamed from: b */
    public Boolean m3680b(String str) {
        return Boolean.valueOf(this.f1877a.contains(str));
    }

    /* renamed from: b */
    public Object m3679b(String str, Object obj) {
        return obj instanceof String ? this.f1877a.getString(str, (String) obj) : obj instanceof Integer ? Integer.valueOf(this.f1877a.getInt(str, ((Integer) obj).intValue())) : obj instanceof Boolean ? Boolean.valueOf(this.f1877a.getBoolean(str, ((Boolean) obj).booleanValue())) : obj instanceof Float ? Float.valueOf(this.f1877a.getFloat(str, ((Float) obj).floatValue())) : obj instanceof Long ? Long.valueOf(this.f1877a.getLong(str, ((Long) obj).longValue())) : this.f1877a.getString(str, null);
    }

    /* renamed from: b */
    public Map<String, ?> m3681b() {
        return this.f1877a.getAll();
    }
}
