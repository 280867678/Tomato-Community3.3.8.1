package p007b.p014c.p015a;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import p007b.p014c.p015a.p016a.AbstractC0613c;

/* renamed from: b.c.a.w */
/* loaded from: classes2.dex */
public class C0725w implements AbstractC0613c {

    /* renamed from: a */
    public static final /* synthetic */ boolean f385a = !C0725w.class.desiredAssertionStatus();

    /* renamed from: b */
    public Charset f386b;

    /* renamed from: c */
    public C0714n f387c;

    /* renamed from: d */
    public AbstractC0726a f388d;

    /* renamed from: b.c.a.w$a */
    /* loaded from: classes2.dex */
    public interface AbstractC0726a {
        /* renamed from: a */
        void mo5277a(String str);
    }

    public C0725w() {
        this(null);
    }

    public C0725w(Charset charset) {
        this.f387c = new C0714n();
        this.f386b = charset;
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0613c
    /* renamed from: a */
    public void mo3861a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        ByteBuffer allocate = ByteBuffer.allocate(c0714n.m5303m());
        while (c0714n.m5303m() > 0) {
            byte m5330a = c0714n.m5330a();
            if (m5330a == 10) {
                if (!f385a && this.f388d == null) {
                    throw new AssertionError();
                }
                allocate.flip();
                this.f387c.m5326a(allocate);
                this.f388d.mo5277a(this.f387c.m5317b(this.f386b));
                this.f387c = new C0714n();
                return;
            }
            allocate.put(m5330a);
        }
        allocate.flip();
        this.f387c.m5326a(allocate);
    }

    /* renamed from: a */
    public void m5278a(AbstractC0726a abstractC0726a) {
        this.f388d = abstractC0726a;
    }
}
