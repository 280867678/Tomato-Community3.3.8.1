package com.koushikdutta.async.http;

import java.util.Hashtable;
import java.util.Locale;

/* loaded from: classes3.dex */
public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1") { // from class: com.koushikdutta.async.http.Protocol.1
    },
    HTTP_2("h2-13") { // from class: com.koushikdutta.async.http.Protocol.2
    };
    

    /* renamed from: e */
    public static final Hashtable<String, Protocol> f1529e = new Hashtable<>();

    /* renamed from: g */
    public final String f1531g;

    static {
        f1529e.put(HTTP_1_0.toString(), HTTP_1_0);
        f1529e.put(HTTP_1_1.toString(), HTTP_1_1);
        f1529e.put(SPDY_3.toString(), SPDY_3);
        f1529e.put(HTTP_2.toString(), HTTP_2);
    }

    Protocol(String str) {
        this.f1531g = str;
    }

    /* renamed from: a */
    public static Protocol m3864a(String str) {
        if (str == null) {
            return null;
        }
        return f1529e.get(str.toLowerCase(Locale.US));
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.f1531g;
    }
}
