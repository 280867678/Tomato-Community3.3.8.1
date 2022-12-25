package p007b.p014c.p015a.p018c.p020b;

import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0599F;
import p007b.p014c.p015a.C0714n;

/* renamed from: b.c.a.c.b.i */
/* loaded from: classes2.dex */
public class C0658i extends C0659j {

    /* renamed from: k */
    public boolean f264k = true;

    /* renamed from: l */
    public CRC32 f265l = new CRC32();

    public C0658i() {
        super(new Inflater(true));
    }

    /* renamed from: a */
    public static short m5422a(byte[] bArr, int i, ByteOrder byteOrder) {
        int i2;
        byte b;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            i2 = bArr[i] << 8;
            b = bArr[i + 1];
        } else {
            i2 = bArr[i + 1] << 8;
            b = bArr[i];
        }
        return (short) ((b & 255) | i2);
    }

    @Override // p007b.p014c.p015a.p018c.p020b.C0659j, p007b.p014c.p015a.C0723u, p007b.p014c.p015a.p016a.AbstractC0613c
    /* renamed from: a */
    public void mo3861a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        if (!this.f264k) {
            super.mo3861a(abstractC0717p, c0714n);
            return;
        }
        C0599F c0599f = new C0599F(abstractC0717p);
        c0599f.m5489a(10, new C0657h(this, abstractC0717p, c0599f));
    }
}
