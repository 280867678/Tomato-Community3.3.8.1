package p007b.p014c.p015a.p018c.p021c;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: b.c.a.c.c.q */
/* loaded from: classes2.dex */
public class C0677q implements AbstractC0684t {

    /* renamed from: a */
    public static Hashtable<String, String> f312a = new Hashtable<>();

    /* renamed from: d */
    public final ArrayList<C0680c> f313d = new ArrayList<>();

    /* renamed from: b.c.a.c.c.q$a */
    /* loaded from: classes2.dex */
    abstract class AbstractC0678a extends AbstractC0673m {
        public AbstractC0678a(C0677q c0677q) {
        }
    }

    /* renamed from: b.c.a.c.c.q$b */
    /* loaded from: classes2.dex */
    class C0679b implements AbstractC0683s, AbstractC0684t {
        public C0679b() {
        }

        @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0684t
        /* renamed from: a */
        public C0681d mo5368a(String str, String str2) {
            return C0677q.this.mo5368a(str, str2);
        }

        @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0683s
        /* renamed from: a */
        public void mo5212a(AbstractC0670j abstractC0670j, AbstractC0674n abstractC0674n) {
            C0681d mo5368a = mo5368a(abstractC0670j.getMethod(), abstractC0670j.getPath());
            if (mo5368a != null) {
                mo5368a.f320d.mo5212a(abstractC0670j, abstractC0674n);
                return;
            }
            abstractC0674n.mo5388a(404);
            abstractC0674n.end();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b.c.a.c.c.q$c */
    /* loaded from: classes2.dex */
    public static class C0680c {

        /* renamed from: a */
        public String f315a;

        /* renamed from: b */
        public Pattern f316b;

        /* renamed from: c */
        public AbstractC0683s f317c;

        /* renamed from: d */
        public AbstractC0661a f318d;

        public C0680c() {
        }

        public /* synthetic */ C0680c(C0676p c0676p) {
            this();
        }
    }

    /* renamed from: b.c.a.c.c.q$d */
    /* loaded from: classes2.dex */
    public static class C0681d {

        /* renamed from: c */
        public final Matcher f319c;

        /* renamed from: d */
        public final AbstractC0683s f320d;

        /* renamed from: e */
        public final AbstractC0661a f321e;

        public C0681d(String str, String str2, Matcher matcher, AbstractC0683s abstractC0683s, AbstractC0661a abstractC0661a) {
            this.f319c = matcher;
            this.f320d = abstractC0683s;
            this.f321e = abstractC0661a;
        }

        public /* synthetic */ C0681d(String str, String str2, Matcher matcher, AbstractC0683s abstractC0683s, AbstractC0661a abstractC0661a, C0676p c0676p) {
            this(str, str2, matcher, abstractC0683s, abstractC0661a);
        }
    }

    static {
        C0677q.class.desiredAssertionStatus();
        new Hashtable();
    }

    public C0677q() {
        f312a.put("js", "application/javascript");
        f312a.put("json", "application/json");
        f312a.put("png", "image/png");
        f312a.put("jpg", "image/jpeg");
        f312a.put("jpeg", "image/jpeg");
        f312a.put("html", "text/html");
        f312a.put("css", "text/css");
        f312a.put("mp4", "video/mp4");
        f312a.put("mov", "video/quicktime");
        f312a.put("wmv", "video/x-ms-wmv");
        f312a.put("txt", "text/plain");
        new C0679b();
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0684t
    /* renamed from: a */
    public C0681d mo5368a(String str, String str2) {
        synchronized (this.f313d) {
            Iterator<C0680c> it2 = this.f313d.iterator();
            while (it2.hasNext()) {
                C0680c next = it2.next();
                if (TextUtils.equals(str, next.f315a) || next.f315a == null) {
                    Matcher matcher = next.f316b.matcher(str2);
                    if (matcher.matches()) {
                        if (!(next.f317c instanceof AbstractC0684t)) {
                            return new C0681d(str, str2, matcher, next.f317c, next.f318d, null);
                        }
                        return ((AbstractC0684t) next.f317c).mo5368a(str, matcher.group(1));
                    }
                }
            }
            return null;
        }
    }

    /* renamed from: a */
    public void m5374a(String str, AbstractC0683s abstractC0683s) {
        m5373a("GET", str, abstractC0683s);
    }

    /* renamed from: a */
    public void m5373a(String str, String str2, AbstractC0683s abstractC0683s) {
        m5372a(str, str2, abstractC0683s, null);
    }

    /* renamed from: a */
    public void m5372a(String str, String str2, AbstractC0683s abstractC0683s, AbstractC0661a abstractC0661a) {
        C0680c c0680c = new C0680c(null);
        c0680c.f316b = Pattern.compile("^" + str2);
        c0680c.f317c = abstractC0683s;
        c0680c.f315a = str;
        c0680c.f318d = abstractC0661a;
        synchronized (this.f313d) {
            this.f313d.add(c0680c);
        }
    }
}
