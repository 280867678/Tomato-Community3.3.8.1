package p007b.p014c.p015a.p018c.p020b;

import java.io.IOException;
import java.nio.ByteOrder;
import p007b.p014c.p015a.C0599F;

/* renamed from: b.c.a.c.b.g */
/* loaded from: classes2.dex */
public class C0656g implements C0599F.AbstractC0601b<byte[]> {

    /* renamed from: a */
    public final /* synthetic */ C0657h f258a;

    public C0656g(C0657h c0657h) {
        this.f258a = c0657h;
    }

    @Override // p007b.p014c.p015a.C0599F.AbstractC0601b
    /* renamed from: a  reason: avoid collision after fix types in other method */
    public void mo5426a(byte[] bArr) {
        if (((short) this.f258a.f263e.f265l.getValue()) != C0658i.m5422a(bArr, 0, ByteOrder.LITTLE_ENDIAN)) {
            this.f258a.f263e.mo3859b(new IOException("CRC mismatch"));
            return;
        }
        this.f258a.f263e.f265l.reset();
        C0657h c0657h = this.f258a;
        C0658i c0658i = c0657h.f263e;
        c0658i.f264k = false;
        c0658i.m5284a(c0657h.f261c);
    }
}
