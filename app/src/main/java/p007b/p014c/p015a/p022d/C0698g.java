package p007b.p014c.p015a.p022d;

import java.nio.charset.Charset;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.p017b.AbstractC0630j;
import p007b.p014c.p015a.p017b.AbstractFutureC0622d;

/* renamed from: b.c.a.d.g */
/* loaded from: classes2.dex */
public class C0698g implements AbstractC0692a<String> {

    /* renamed from: a */
    public Charset f343a;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ String m5343a(String str, C0714n c0714n) {
        Charset charset = this.f343a;
        if (charset == null && str != null) {
            charset = Charset.forName(str);
        }
        return c0714n.m5317b(charset);
    }

    /* renamed from: a */
    public AbstractFutureC0622d<String> m5344a(AbstractC0717p abstractC0717p) {
        final String mo5282e = abstractC0717p.mo5282e();
        return new C0696e().m5346a(abstractC0717p).mo5465a(new AbstractC0630j() { // from class: b.c.a.d.-$$Lambda$g$pCLyPX3E7HaETo-DvY8kKWbtUxI
            @Override // p007b.p014c.p015a.p017b.AbstractC0630j
            /* renamed from: a */
            public final Object mo5347a(Object obj) {
                String m5343a;
                m5343a = C0698g.this.m5343a(mo5282e, (C0714n) obj);
                return m5343a;
            }
        });
    }
}
