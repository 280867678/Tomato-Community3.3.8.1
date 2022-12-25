package p007b.p014c.p015a;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p016a.AbstractC0613c;

/* renamed from: b.c.a.K */
/* loaded from: classes2.dex */
public class C0608K {

    /* renamed from: b */
    public static final /* synthetic */ boolean f207b = !C0608K.class.desiredAssertionStatus();

    /* renamed from: a */
    public static boolean f206a = false;

    /* renamed from: a */
    public static void m5480a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        int m5303m;
        AbstractC0613c abstractC0613c = null;
        while (!abstractC0717p.mo5281f() && (abstractC0613c = abstractC0717p.mo5291g()) != null && (m5303m = c0714n.m5303m()) > 0) {
            abstractC0613c.mo3861a(abstractC0717p, c0714n);
            if (m5303m == c0714n.m5303m() && abstractC0613c == abstractC0717p.mo5291g() && !abstractC0717p.mo5281f()) {
                PrintStream printStream = System.out;
                printStream.println("handler: " + abstractC0613c);
                c0714n.m5304l();
                if (f206a) {
                    return;
                }
                if (f207b) {
                    throw new RuntimeException("mDataHandler failed to consume data, yet remains the mDataHandler.");
                }
                throw new AssertionError();
            }
        }
        if (c0714n.m5303m() == 0 || abstractC0717p.mo5281f()) {
            return;
        }
        PrintStream printStream2 = System.out;
        printStream2.println("handler: " + abstractC0613c);
        PrintStream printStream3 = System.out;
        printStream3.println("emitter: " + abstractC0717p);
        c0714n.m5304l();
        if (f206a) {
        }
    }

    /* renamed from: a */
    public static void m5479a(AbstractC0719r abstractC0719r, C0714n c0714n, AbstractC0610a abstractC0610a) {
        C0607J c0607j = new C0607J(abstractC0719r, c0714n, abstractC0610a);
        abstractC0719r.mo5289a(c0607j);
        c0607j.mo5215a();
    }

    /* renamed from: a */
    public static void m5478a(AbstractC0719r abstractC0719r, byte[] bArr, AbstractC0610a abstractC0610a) {
        ByteBuffer m5319b = C0714n.m5319b(bArr.length);
        m5319b.put(bArr);
        m5319b.flip();
        C0714n c0714n = new C0714n();
        c0714n.m5326a(m5319b);
        m5479a(abstractC0719r, c0714n, abstractC0610a);
    }
}
