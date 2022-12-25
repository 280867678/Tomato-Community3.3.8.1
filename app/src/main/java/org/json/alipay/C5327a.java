package org.json.alipay;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/* renamed from: org.json.alipay.a */
/* loaded from: classes4.dex */
public class C5327a {

    /* renamed from: a */
    private ArrayList f6044a;

    public C5327a() {
        this.f6044a = new ArrayList();
    }

    public C5327a(Object obj) {
        this();
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                this.f6044a.add(Array.get(obj, i));
            }
            return;
        }
        throw new JSONException("JSONArray initial value should be a string or collection or array.");
    }

    public C5327a(String str) {
        this(new C5330c(str));
    }

    public C5327a(Collection collection) {
        this.f6044a = collection == null ? new ArrayList() : new ArrayList(collection);
    }

    public C5327a(C5330c c5330c) {
        this();
        char c;
        ArrayList arrayList;
        Object m54d;
        char m55c = c5330c.m55c();
        if (m55c == '[') {
            c = ']';
        } else if (m55c != '(') {
            throw c5330c.m57a("A JSONArray text must start with '['");
        } else {
            c = ')';
        }
        if (c5330c.m55c() == ']') {
            return;
        }
        do {
            c5330c.m59a();
            char m55c2 = c5330c.m55c();
            c5330c.m59a();
            if (m55c2 == ',') {
                arrayList = this.f6044a;
                m54d = null;
            } else {
                arrayList = this.f6044a;
                m54d = c5330c.m54d();
            }
            arrayList.add(m54d);
            char m55c3 = c5330c.m55c();
            if (m55c3 != ')') {
                if (m55c3 != ',' && m55c3 != ';') {
                    if (m55c3 != ']') {
                        throw c5330c.m57a("Expected a ',' or ']'");
                    }
                }
            }
            if (c == m55c3) {
                return;
            }
            throw c5330c.m57a("Expected a '" + new Character(c) + "'");
        } while (c5330c.m55c() != ']');
    }

    /* renamed from: a */
    private String m66a(String str) {
        int size = this.f6044a.size();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuffer.append(str);
            }
            stringBuffer.append(C5328b.m64a(this.f6044a.get(i)));
        }
        return stringBuffer.toString();
    }

    /* renamed from: a */
    public final int m68a() {
        return this.f6044a.size();
    }

    /* renamed from: a */
    public final Object m67a(int i) {
        Object obj = (i < 0 || i >= this.f6044a.size()) ? null : this.f6044a.get(i);
        if (obj != null) {
            return obj;
        }
        throw new JSONException("JSONArray[" + i + "] not found.");
    }

    public String toString() {
        try {
            return "[" + m66a(",") + ']';
        } catch (Exception unused) {
            return null;
        }
    }
}
