package p007b.p014c.p015a.p018c.p020b;

import java.nio.ByteBuffer;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.p016a.AbstractC0613c;

/* renamed from: b.c.a.c.b.f */
/* loaded from: classes2.dex */
public class C0655f implements AbstractC0613c {

    /* renamed from: a */
    public final /* synthetic */ C0657h f257a;

    public C0655f(C0657h c0657h) {
        this.f257a = c0657h;
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0613c
    /* renamed from: a */
    public void mo3861a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        if (this.f257a.f260b) {
            while (c0714n.m5301o() > 0) {
                ByteBuffer m5302n = c0714n.m5302n();
                this.f257a.f263e.f265l.update(m5302n.array(), m5302n.arrayOffset() + m5302n.position(), m5302n.remaining());
                C0714n.m5314c(m5302n);
            }
        }
        c0714n.m5304l();
        this.f257a.m5428a();
    }
}
