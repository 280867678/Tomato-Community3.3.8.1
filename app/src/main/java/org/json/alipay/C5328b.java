package org.json.alipay;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* renamed from: org.json.alipay.b */
/* loaded from: classes4.dex */
public class C5328b {

    /* renamed from: a */
    public static final Object f6045a = new C5329a((byte) 0);

    /* renamed from: b */
    private Map f6046b;

    /* renamed from: org.json.alipay.b$a */
    /* loaded from: classes4.dex */
    private static final class C5329a {
        private C5329a() {
        }

        /* synthetic */ C5329a(byte b) {
            this();
        }

        protected final Object clone() {
            return this;
        }

        public final boolean equals(Object obj) {
            return obj == null || obj == this;
        }

        public final String toString() {
            return "null";
        }
    }

    public C5328b() {
        this.f6046b = new HashMap();
    }

    public C5328b(String str) {
        this(new C5330c(str));
    }

    public C5328b(Map map) {
        this.f6046b = map == null ? new HashMap() : map;
    }

    public C5328b(C5330c c5330c) {
        this();
        if (c5330c.m55c() == '{') {
            while (true) {
                char m55c = c5330c.m55c();
                if (m55c == 0) {
                    throw c5330c.m57a("A JSONObject text must end with '}'");
                }
                if (m55c == '}') {
                    return;
                }
                c5330c.m59a();
                String obj = c5330c.m54d().toString();
                char m55c2 = c5330c.m55c();
                if (m55c2 == '=') {
                    if (c5330c.m56b() != '>') {
                        c5330c.m59a();
                    }
                } else if (m55c2 != ':') {
                    throw c5330c.m57a("Expected a ':' after a key");
                }
                Object m54d = c5330c.m54d();
                if (obj == null) {
                    throw new JSONException("Null key.");
                }
                if (m54d != null) {
                    m62b(m54d);
                    this.f6046b.put(obj, m54d);
                } else {
                    this.f6046b.remove(obj);
                }
                char m55c3 = c5330c.m55c();
                if (m55c3 != ',' && m55c3 != ';') {
                    if (m55c3 != '}') {
                        throw c5330c.m57a("Expected a ',' or '}'");
                    }
                    return;
                } else if (c5330c.m55c() == '}') {
                    return;
                } else {
                    c5330c.m59a();
                }
            }
        } else {
            throw c5330c.m57a("A JSONObject text must begin with '{'");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public static String m64a(Object obj) {
        if (obj == null || obj.equals(null)) {
            return "null";
        }
        if (!(obj instanceof Number)) {
            return ((obj instanceof Boolean) || (obj instanceof C5328b) || (obj instanceof C5327a)) ? obj.toString() : obj instanceof Map ? new C5328b((Map) obj).toString() : obj instanceof Collection ? new C5327a((Collection) obj).toString() : obj.getClass().isArray() ? new C5327a(obj).toString() : m60c(obj.toString());
        }
        Number number = (Number) obj;
        if (number == null) {
            throw new JSONException("Null pointer");
        }
        m62b(number);
        String obj2 = number.toString();
        if (obj2.indexOf(46) <= 0 || obj2.indexOf(101) >= 0 || obj2.indexOf(69) >= 0) {
            return obj2;
        }
        while (obj2.endsWith("0")) {
            obj2 = obj2.substring(0, obj2.length() - 1);
        }
        return obj2.endsWith(".") ? obj2.substring(0, obj2.length() - 1) : obj2;
    }

    /* renamed from: b */
    private static void m62b(Object obj) {
        if (obj != null) {
            if (obj instanceof Double) {
                Double d = (Double) obj;
                if (!d.isInfinite() && !d.isNaN()) {
                    return;
                }
                throw new JSONException("JSON does not allow non-finite numbers.");
            } else if (!(obj instanceof Float)) {
            } else {
                Float f = (Float) obj;
                if (!f.isInfinite() && !f.isNaN()) {
                    return;
                }
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0084, code lost:
        if (r4 == '<') goto L37;
     */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String m60c(String str) {
        String str2;
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length + 4);
        stringBuffer.append('\"');
        int i = 0;
        char c = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt == '\f') {
                str2 = "\\f";
            } else if (charAt != '\r') {
                if (charAt != '\"') {
                    if (charAt != '/') {
                        if (charAt != '\\') {
                            switch (charAt) {
                                case '\b':
                                    str2 = "\\b";
                                    break;
                                case '\t':
                                    str2 = "\\t";
                                    break;
                                case '\n':
                                    str2 = "\\n";
                                    break;
                                default:
                                    if (charAt < ' ' || ((charAt >= 128 && charAt < 160) || (charAt >= 8192 && charAt < 8448))) {
                                        String str3 = "000" + Integer.toHexString(charAt);
                                        str2 = "\\u" + str3.substring(str3.length() - 4);
                                        break;
                                    }
                                    stringBuffer.append(charAt);
                                    i++;
                                    c = charAt;
                                    break;
                            }
                        }
                    }
                }
                stringBuffer.append('\\');
                stringBuffer.append(charAt);
                i++;
                c = charAt;
            } else {
                str2 = "\\r";
            }
            stringBuffer.append(str2);
            i++;
            c = charAt;
        }
        stringBuffer.append('\"');
        return stringBuffer.toString();
    }

    /* renamed from: a */
    public final Object m63a(String str) {
        Object obj = str == null ? null : this.f6046b.get(str);
        if (obj != null) {
            return obj;
        }
        throw new JSONException("JSONObject[" + m60c(str) + "] not found.");
    }

    /* renamed from: a */
    public final Iterator m65a() {
        return this.f6046b.keySet().iterator();
    }

    /* renamed from: b */
    public final boolean m61b(String str) {
        return this.f6046b.containsKey(str);
    }

    public String toString() {
        try {
            Iterator m65a = m65a();
            StringBuffer stringBuffer = new StringBuffer("{");
            while (m65a.hasNext()) {
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(',');
                }
                Object next = m65a.next();
                stringBuffer.append(m60c(next.toString()));
                stringBuffer.append(':');
                stringBuffer.append(m64a(this.f6046b.get(next)));
            }
            stringBuffer.append('}');
            return stringBuffer.toString();
        } catch (Exception unused) {
            return null;
        }
    }
}
