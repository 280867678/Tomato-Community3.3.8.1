package p007b.p014c.p015a.p018c.p020b;

import java.nio.ByteOrder;
import p007b.p014c.p015a.C0599F;

/* renamed from: b.c.a.c.b.e */
/* loaded from: classes2.dex */
public class C0654e implements C0599F.AbstractC0601b<byte[]> {

    /* renamed from: a */
    public final /* synthetic */ C0657h f256a;

    public C0654e(C0657h c0657h) {
        this.f256a = c0657h;
    }

    @Override // p007b.p014c.p015a.C0599F.AbstractC0601b
    /* renamed from: a  reason: avoid collision after fix types in other method */
    public void mo5426a(byte[] bArr) {
        C0657h c0657h = this.f256a;
        if (c0657h.f260b) {
            c0657h.f263e.f265l.update(bArr, 0, 2);
        }
        this.f256a.f262d.m5489a(C0658i.m5422a(bArr, 0, ByteOrder.LITTLE_ENDIAN) & 65535, new C0653d(this));
    }
}
