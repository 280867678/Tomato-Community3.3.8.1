package org.json.alipay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/* renamed from: org.json.alipay.c */
/* loaded from: classes4.dex */
public final class C5330c {

    /* renamed from: a */
    private int f6047a;

    /* renamed from: b */
    private Reader f6048b;

    /* renamed from: c */
    private char f6049c;

    /* renamed from: d */
    private boolean f6050d;

    private C5330c(Reader reader) {
        this.f6048b = !reader.markSupported() ? new BufferedReader(reader) : reader;
        this.f6050d = false;
        this.f6047a = 0;
    }

    public C5330c(String str) {
        this(new StringReader(str));
    }

    /* renamed from: a */
    private String m58a(int i) {
        if (i == 0) {
            return "";
        }
        char[] cArr = new char[i];
        int i2 = 0;
        if (this.f6050d) {
            this.f6050d = false;
            cArr[0] = this.f6049c;
            i2 = 1;
        }
        while (i2 < i) {
            try {
                int read = this.f6048b.read(cArr, i2, i - i2);
                if (read == -1) {
                    break;
                }
                i2 += read;
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        this.f6047a += i2;
        if (i2 < i) {
            throw m57a("Substring bounds error");
        }
        this.f6049c = cArr[i - 1];
        return new String(cArr);
    }

    /* renamed from: a */
    public final JSONException m57a(String str) {
        return new JSONException(str + toString());
    }

    /* renamed from: a */
    public final void m59a() {
        int i;
        if (this.f6050d || (i = this.f6047a) <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.f6047a = i - 1;
        this.f6050d = true;
    }

    /* renamed from: b */
    public final char m56b() {
        if (this.f6050d) {
            this.f6050d = false;
            if (this.f6049c != 0) {
                this.f6047a++;
            }
            return this.f6049c;
        }
        try {
            int read = this.f6048b.read();
            if (read <= 0) {
                this.f6049c = (char) 0;
                return (char) 0;
            }
            this.f6047a++;
            this.f6049c = (char) read;
            return this.f6049c;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0053, code lost:
        return r0;
     */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final char m55c() {
        char m56b;
        char m56b2;
        while (true) {
            char m56b3 = m56b();
            if (m56b3 == '/') {
                char m56b4 = m56b();
                if (m56b4 == '*') {
                    while (true) {
                        char m56b5 = m56b();
                        if (m56b5 == 0) {
                            throw m57a("Unclosed comment");
                        }
                        if (m56b5 == '*') {
                            if (m56b() != '/') {
                                m59a();
                            }
                        }
                    }
                } else if (m56b4 != '/') {
                    m59a();
                    return '/';
                } else {
                    do {
                        m56b = m56b();
                        if (m56b != '\n' && m56b != '\r') {
                        }
                    } while (m56b != 0);
                }
            } else if (m56b3 == '#') {
                do {
                    m56b2 = m56b();
                    if (m56b2 != '\n' && m56b2 != '\r') {
                    }
                } while (m56b2 != 0);
            } else if (m56b3 == 0 || m56b3 > ' ') {
                break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:125:0x0147, code lost:
        throw m57a("Unterminated string");
     */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m54d() {
        String m58a;
        char m55c = m55c();
        if (m55c != '\"') {
            if (m55c != '[') {
                if (m55c == '{') {
                    m59a();
                    return new C5328b(this);
                } else if (m55c != '\'') {
                    if (m55c != '(') {
                        StringBuffer stringBuffer = new StringBuffer();
                        char c = m55c;
                        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
                            stringBuffer.append(c);
                            c = m56b();
                        }
                        m59a();
                        String trim = stringBuffer.toString().trim();
                        if (trim.equals("")) {
                            throw m57a("Missing value");
                        }
                        if (trim.equalsIgnoreCase("true")) {
                            return Boolean.TRUE;
                        }
                        if (trim.equalsIgnoreCase("false")) {
                            return Boolean.FALSE;
                        }
                        if (trim.equalsIgnoreCase("null")) {
                            return C5328b.f6045a;
                        }
                        if ((m55c < '0' || m55c > '9') && m55c != '.' && m55c != '-' && m55c != '+') {
                            return trim;
                        }
                        if (m55c == '0') {
                            try {
                                return (trim.length() <= 2 || !(trim.charAt(1) == 'x' || trim.charAt(1) == 'X')) ? new Integer(Integer.parseInt(trim, 8)) : new Integer(Integer.parseInt(trim.substring(2), 16));
                            } catch (Exception unused) {
                            }
                        }
                        try {
                            try {
                                try {
                                    return new Integer(trim);
                                } catch (Exception unused2) {
                                    return new Double(trim);
                                }
                            } catch (Exception unused3) {
                                return new Long(trim);
                            }
                        } catch (Exception unused4) {
                            return trim;
                        }
                    }
                }
            }
            m59a();
            return new C5327a(this);
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        while (true) {
            char m56b = m56b();
            if (m56b == 0 || m56b == '\n' || m56b == '\r') {
                break;
            }
            if (m56b == '\\') {
                m56b = m56b();
                if (m56b == 'b') {
                    stringBuffer2.append('\b');
                } else if (m56b == 'f') {
                    m56b = '\f';
                } else if (m56b == 'n') {
                    stringBuffer2.append('\n');
                } else if (m56b != 'r') {
                    if (m56b == 'x') {
                        m58a = m58a(2);
                    } else if (m56b == 't') {
                        m56b = '\t';
                    } else if (m56b == 'u') {
                        m58a = m58a(4);
                    }
                    m56b = (char) Integer.parseInt(m58a, 16);
                } else {
                    stringBuffer2.append('\r');
                }
            } else if (m56b == m55c) {
                return stringBuffer2.toString();
            }
            stringBuffer2.append(m56b);
        }
    }

    public final String toString() {
        return " at character " + this.f6047a;
    }
}
