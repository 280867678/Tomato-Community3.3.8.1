package p007b.p014c.p015a.p018c.p019a;

import com.koushikdutta.async.http.Multimap;
import java.util.List;
import java.util.Locale;
import p007b.p014c.p015a.p018c.AbstractC0690g;
import p007b.p014c.p015a.p018c.C0649b;

/* renamed from: b.c.a.c.a.g */
/* loaded from: classes2.dex */
public class C0641g {

    /* renamed from: b */
    public C0649b f238b;

    /* renamed from: c */
    public Multimap f239c;

    static {
        C0641g.class.desiredAssertionStatus();
    }

    public C0641g(C0649b c0649b) {
        this.f238b = c0649b;
        this.f239c = Multimap.m3866c(this.f238b.m5436b("Content-Disposition"));
    }

    public C0641g(String str, long j, List<AbstractC0690g> list) {
        this.f238b = new C0649b();
        StringBuilder sb = new StringBuilder(String.format(Locale.ENGLISH, "form-data; name=\"%s\"", str));
        if (list != null) {
            for (AbstractC0690g abstractC0690g : list) {
                sb.append(String.format(Locale.ENGLISH, "; %s=\"%s\"", abstractC0690g.getName(), abstractC0690g.getValue()));
            }
        }
        this.f238b.m5435b("Content-Disposition", sb.toString());
        this.f239c = Multimap.m3866c(this.f238b.m5436b("Content-Disposition"));
    }

    /* renamed from: a */
    public String m5445a() {
        return this.f239c.m3868b("name");
    }
}
