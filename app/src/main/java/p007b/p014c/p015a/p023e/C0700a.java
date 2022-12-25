package p007b.p014c.p015a.p023e;

import java.nio.ByteBuffer;
import p007b.p014c.p015a.C0714n;

/* renamed from: b.c.a.e.a */
/* loaded from: classes2.dex */
public class C0700a {

    /* renamed from: b */
    public int f346b = 0;

    /* renamed from: c */
    public int f347c = 4096;

    /* renamed from: a */
    public final int f345a = C0714n.f367c;

    /* renamed from: a */
    public ByteBuffer m5339a() {
        return m5338a(this.f346b);
    }

    /* renamed from: a */
    public ByteBuffer m5338a(int i) {
        return C0714n.m5319b(Math.min(Math.max(i, this.f347c), this.f345a));
    }

    /* renamed from: a */
    public void m5337a(long j) {
        this.f346b = ((int) j) * 2;
    }
}
