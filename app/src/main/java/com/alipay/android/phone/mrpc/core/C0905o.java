package com.alipay.android.phone.mrpc.core;

import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

/* renamed from: com.alipay.android.phone.mrpc.core.o */
/* loaded from: classes2.dex */
public final class C0905o extends AbstractC0910t {

    /* renamed from: b */
    private String f798b;

    /* renamed from: c */
    private byte[] f799c;

    /* renamed from: g */
    private boolean f803g;

    /* renamed from: e */
    private ArrayList<Header> f801e = new ArrayList<>();

    /* renamed from: f */
    private Map<String, String> f802f = new HashMap();

    /* renamed from: d */
    private String f800d = "application/x-www-form-urlencoded";

    public C0905o(String str) {
        this.f798b = str;
    }

    /* renamed from: a */
    public final String m4835a() {
        return this.f798b;
    }

    /* renamed from: a */
    public final void m4834a(String str) {
        this.f800d = str;
    }

    /* renamed from: a */
    public final void m4833a(String str, String str2) {
        if (this.f802f == null) {
            this.f802f = new HashMap();
        }
        this.f802f.put(str, str2);
    }

    /* renamed from: a */
    public final void m4832a(Header header) {
        this.f801e.add(header);
    }

    /* renamed from: a */
    public final void m4831a(boolean z) {
        this.f803g = z;
    }

    /* renamed from: a */
    public final void m4830a(byte[] bArr) {
        this.f799c = bArr;
    }

    /* renamed from: b */
    public final String m4828b(String str) {
        Map<String, String> map = this.f802f;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    /* renamed from: b */
    public final byte[] m4829b() {
        return this.f799c;
    }

    /* renamed from: c */
    public final String m4827c() {
        return this.f800d;
    }

    /* renamed from: d */
    public final ArrayList<Header> m4826d() {
        return this.f801e;
    }

    /* renamed from: e */
    public final boolean m4825e() {
        return this.f803g;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C0905o.class != obj.getClass()) {
            return false;
        }
        C0905o c0905o = (C0905o) obj;
        byte[] bArr = this.f799c;
        if (bArr == null) {
            if (c0905o.f799c != null) {
                return false;
            }
        } else if (!bArr.equals(c0905o.f799c)) {
            return false;
        }
        String str = this.f798b;
        String str2 = c0905o.f798b;
        if (str == null) {
            if (str2 != null) {
                return false;
            }
        } else if (!str.equals(str2)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        Map<String, String> map = this.f802f;
        int hashCode = ((map == null || !map.containsKey(DatabaseFieldConfigLoader.FIELD_NAME_ID)) ? 1 : this.f802f.get(DatabaseFieldConfigLoader.FIELD_NAME_ID).hashCode() + 31) * 31;
        String str = this.f798b;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return String.format("Url : %s,HttpHeader: %s", this.f798b, this.f801e);
    }
}
