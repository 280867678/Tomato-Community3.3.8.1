package p007b.p014c.p015a.p018c.p020b;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Locale;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0599F;
import p007b.p014c.p015a.p016a.AbstractC0613c;

/* renamed from: b.c.a.c.b.h */
/* loaded from: classes2.dex */
public class C0657h implements C0599F.AbstractC0601b<byte[]> {

    /* renamed from: a */
    public int f259a;

    /* renamed from: b */
    public boolean f260b;

    /* renamed from: c */
    public final /* synthetic */ AbstractC0717p f261c;

    /* renamed from: d */
    public final /* synthetic */ C0599F f262d;

    /* renamed from: e */
    public final /* synthetic */ C0658i f263e;

    public C0657h(C0658i c0658i, AbstractC0717p abstractC0717p, C0599F c0599f) {
        this.f263e = c0658i;
        this.f261c = abstractC0717p;
        this.f262d = c0599f;
    }

    /* renamed from: a */
    public static /* synthetic */ void m5427a(C0657h c0657h) {
        c0657h.m5424b();
    }

    /* renamed from: a */
    public final void m5428a() {
        if (this.f260b) {
            this.f262d.m5489a(2, new C0656g(this));
            return;
        }
        C0658i c0658i = this.f263e;
        c0658i.f264k = false;
        c0658i.m5284a(this.f261c);
    }

    @Override // p007b.p014c.p015a.C0599F.AbstractC0601b
    /* renamed from: a */
    public void mo5426a(byte[] bArr) {
        short m5422a = C0658i.m5422a(bArr, 0, ByteOrder.LITTLE_ENDIAN);
        boolean z = true;
        if (m5422a != -29921) {
            this.f263e.mo3859b(new IOException(String.format(Locale.ENGLISH, "unknown format (magic number %x)", Short.valueOf(m5422a))));
            this.f261c.mo5292a(new AbstractC0613c.C0614a());
            return;
        }
        this.f259a = bArr[3];
        if ((this.f259a & 2) == 0) {
            z = false;
        }
        this.f260b = z;
        if (this.f260b) {
            this.f263e.f265l.update(bArr, 0, bArr.length);
        }
        if ((this.f259a & 4) != 0) {
            this.f262d.m5489a(2, new C0654e(this));
        } else {
            m5424b();
        }
    }

    /* renamed from: b */
    public final void m5424b() {
        C0599F c0599f = new C0599F(this.f261c);
        C0655f c0655f = new C0655f(this);
        int i = this.f259a;
        if ((i & 8) != 0) {
            c0599f.m5490a((byte) 0, c0655f);
        } else if ((i & 16) != 0) {
            c0599f.m5490a((byte) 0, c0655f);
        } else {
            m5428a();
        }
    }
}
