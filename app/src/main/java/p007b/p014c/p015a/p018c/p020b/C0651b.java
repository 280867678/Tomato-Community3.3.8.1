package p007b.p014c.p015a.p018c.p020b;

import java.nio.ByteBuffer;
import p007b.p014c.p015a.AbstractC0719r;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.C0724v;

/* renamed from: b.c.a.c.b.b */
/* loaded from: classes2.dex */
public class C0651b extends C0724v {
    public C0651b(AbstractC0719r abstractC0719r) {
        super(abstractC0719r);
    }

    @Override // p007b.p014c.p015a.C0724v
    /* renamed from: c */
    public C0714n mo5279c(C0714n c0714n) {
        c0714n.m5318b(ByteBuffer.wrap((Integer.toString(c0714n.m5303m(), 16) + "\r\n").getBytes()));
        c0714n.m5326a(ByteBuffer.wrap("\r\n".getBytes()));
        return c0714n;
    }

    @Override // p007b.p014c.p015a.C0713m, p007b.p014c.p015a.AbstractC0719r
    public void end() {
        m5333b(Integer.MAX_VALUE);
        mo5288a(new C0714n());
        m5333b(0);
    }
}
