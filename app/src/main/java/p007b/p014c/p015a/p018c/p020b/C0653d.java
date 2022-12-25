package p007b.p014c.p015a.p018c.p020b;

import p007b.p014c.p015a.C0599F;

/* renamed from: b.c.a.c.b.d */
/* loaded from: classes2.dex */
public class C0653d implements C0599F.AbstractC0601b<byte[]> {

    /* renamed from: a */
    public final /* synthetic */ C0654e f255a;

    public C0653d(C0654e c0654e) {
        this.f255a = c0654e;
    }

    @Override // p007b.p014c.p015a.C0599F.AbstractC0601b
    /* renamed from: a  reason: avoid collision after fix types in other method */
    public void mo5426a(byte[] bArr) {
        C0657h c0657h = this.f255a.f256a;
        if (c0657h.f260b) {
            c0657h.f263e.f265l.update(bArr, 0, bArr.length);
        }
        C0657h.m5427a(this.f255a.f256a);
    }
}
