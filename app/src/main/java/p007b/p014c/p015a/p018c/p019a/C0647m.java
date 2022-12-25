package p007b.p014c.p015a.p018c.p019a;

import com.koushikdutta.async.http.Multimap;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.p016a.AbstractC0610a;

/* renamed from: b.c.a.c.a.m */
/* loaded from: classes2.dex */
public class C0647m implements AbstractC0610a {

    /* renamed from: a */
    public final /* synthetic */ C0714n f245a;

    /* renamed from: b */
    public final /* synthetic */ AbstractC0610a f246b;

    /* renamed from: c */
    public final /* synthetic */ C0648n f247c;

    public C0647m(C0648n c0648n, C0714n c0714n, AbstractC0610a abstractC0610a) {
        this.f247c = c0648n;
        this.f245a = c0714n;
        this.f246b = abstractC0610a;
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0610a
    /* renamed from: a */
    public void mo5196a(Exception exc) {
        try {
            if (exc != null) {
                throw exc;
            }
            this.f247c.f248a = Multimap.m3865d(this.f245a.m5305k());
            this.f246b.mo5196a(null);
        } catch (Exception e) {
            this.f246b.mo5196a(e);
        }
    }
}
