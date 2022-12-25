package p007b.p014c.p015a.p018c;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.BodyDecoderException;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.filter.ChunkedInputFilter;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0723u;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p018c.p019a.AbstractC0634a;
import p007b.p014c.p015a.p018c.p019a.C0636c;
import p007b.p014c.p015a.p018c.p019a.C0639f;
import p007b.p014c.p015a.p018c.p019a.C0644j;
import p007b.p014c.p015a.p018c.p019a.C0648n;
import p007b.p014c.p015a.p018c.p020b.C0652c;
import p007b.p014c.p015a.p018c.p020b.C0658i;
import p007b.p014c.p015a.p018c.p020b.C0659j;

/* renamed from: b.c.a.c.d */
/* loaded from: classes2.dex */
public class C0686d {

    /* renamed from: b.c.a.c.d$a */
    /* loaded from: classes2.dex */
    static class C0687a extends C0723u {
        /* renamed from: a */
        public static C0687a m5360a(AsyncServer asyncServer, Exception exc) {
            C0687a c0687a = new C0687a();
            asyncServer.m3893a(new RunnableC0660c(c0687a, exc));
            return c0687a;
        }
    }

    /* renamed from: a */
    public static AbstractC0634a m5365a(AbstractC0717p abstractC0717p, AbstractC0610a abstractC0610a, C0649b c0649b) {
        String m5436b = c0649b.m5436b("Content-Type");
        if (m5436b != null) {
            String[] split = m5436b.split(";");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].trim();
            }
            for (String str : split) {
                if ("application/x-www-form-urlencoded".equals(str)) {
                    return new C0648n();
                }
                if ("application/json".equals(str)) {
                    return new C0636c();
                }
                if ("text/plain".equals(str)) {
                    return new C0644j();
                }
                if (str != null && str.startsWith("multipart/")) {
                    return new C0639f(m5436b);
                }
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0016  */
    /* JADX WARN: Type inference failed for: r6v9, types: [b.c.a.c.b.c] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static AbstractC0717p m5364a(AbstractC0717p abstractC0717p, Protocol protocol, C0649b c0649b, boolean z) {
        long j;
        ChunkedInputFilter chunkedInputFilter;
        C0658i c0658i;
        ChunkedInputFilter chunkedInputFilter2;
        C0687a m5360a;
        String m5436b;
        try {
            m5436b = c0649b.m5436b("Content-Length");
        } catch (NumberFormatException unused) {
        }
        if (m5436b != null) {
            j = Long.parseLong(m5436b);
            if (-1 != j) {
                if ("chunked".equalsIgnoreCase(c0649b.m5436b("Transfer-Encoding"))) {
                    chunkedInputFilter2 = new ChunkedInputFilter();
                    chunkedInputFilter2.m5284a(abstractC0717p);
                    chunkedInputFilter = chunkedInputFilter2;
                    if ("gzip".equals(c0649b.m5436b("Content-Encoding"))) {
                    }
                    c0658i.m5284a(chunkedInputFilter);
                    return c0658i;
                }
                if (!z) {
                    chunkedInputFilter = abstractC0717p;
                    if ("gzip".equals(c0649b.m5436b("Content-Encoding"))) {
                    }
                    c0658i.m5284a(chunkedInputFilter);
                    return c0658i;
                }
                m5360a = C0687a.m5360a(abstractC0717p.mo5283c(), (Exception) null);
                m5360a.m5284a(abstractC0717p);
                return m5360a;
            }
            int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
            if (i < 0) {
                m5360a = C0687a.m5360a(abstractC0717p.mo5283c(), new BodyDecoderException("not using chunked encoding, and no content-length found."));
                m5360a.m5284a(abstractC0717p);
                return m5360a;
            }
            if (i != 0) {
                chunkedInputFilter2 = new C0652c(j);
                chunkedInputFilter2.m5284a(abstractC0717p);
                chunkedInputFilter = chunkedInputFilter2;
                if ("gzip".equals(c0649b.m5436b("Content-Encoding"))) {
                    c0658i = new C0658i();
                } else if (!"deflate".equals(c0649b.m5436b("Content-Encoding"))) {
                    return chunkedInputFilter;
                } else {
                    c0658i = new C0659j();
                }
                c0658i.m5284a(chunkedInputFilter);
                return c0658i;
            }
            m5360a = C0687a.m5360a(abstractC0717p.mo5283c(), (Exception) null);
            m5360a.m5284a(abstractC0717p);
            return m5360a;
        }
        j = -1;
        if (-1 != j) {
        }
    }

    /* renamed from: a */
    public static boolean m5363a(Protocol protocol, C0649b c0649b) {
        String m5436b = c0649b.m5436b("Connection");
        return m5436b == null ? protocol == Protocol.HTTP_1_1 : "keep-alive".equalsIgnoreCase(m5436b);
    }

    /* renamed from: a */
    public static boolean m5362a(String str, C0649b c0649b) {
        String m5436b = c0649b.m5436b("Connection");
        return m5436b == null ? Protocol.m3864a(str) == Protocol.HTTP_1_1 : "keep-alive".equalsIgnoreCase(m5436b);
    }
}
