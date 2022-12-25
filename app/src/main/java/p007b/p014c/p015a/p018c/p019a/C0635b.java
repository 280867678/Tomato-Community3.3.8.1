package p007b.p014c.p015a.p018c.p019a;

import org.json.JSONObject;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p017b.AbstractC0623e;

/* renamed from: b.c.a.c.a.b */
/* loaded from: classes2.dex */
public class C0635b implements AbstractC0623e<JSONObject> {

    /* renamed from: a */
    public final /* synthetic */ AbstractC0610a f226a;

    /* renamed from: b */
    public final /* synthetic */ C0636c f227b;

    public C0635b(C0636c c0636c, AbstractC0610a abstractC0610a) {
        this.f227b = c0636c;
        this.f226a = abstractC0610a;
    }

    @Override // p007b.p014c.p015a.p017b.AbstractC0623e
    /* renamed from: a  reason: avoid collision after fix types in other method */
    public void mo5444a(Exception exc, JSONObject jSONObject) {
        this.f227b.f228a = jSONObject;
        this.f226a.mo5196a(exc);
    }
}
