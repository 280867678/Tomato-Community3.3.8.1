package p007b.p014c.p015a.p018c.p020b;

import com.koushikdutta.async.http.filter.DataRemainingException;
import java.nio.ByteBuffer;
import java.util.zip.Inflater;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0608K;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.C0723u;

/* renamed from: b.c.a.c.b.j */
/* loaded from: classes2.dex */
public class C0659j extends C0723u {

    /* renamed from: h */
    public static final /* synthetic */ boolean f266h = !C0659j.class.desiredAssertionStatus();

    /* renamed from: i */
    public Inflater f267i;

    /* renamed from: j */
    public C0714n f268j;

    public C0659j() {
        this(new Inflater());
    }

    public C0659j(Inflater inflater) {
        this.f268j = new C0714n();
        this.f267i = inflater;
    }

    @Override // p007b.p014c.p015a.C0723u, p007b.p014c.p015a.p016a.AbstractC0613c
    /* renamed from: a */
    public void mo3861a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        try {
            ByteBuffer m5319b = C0714n.m5319b(c0714n.m5303m() * 2);
            while (c0714n.m5301o() > 0) {
                ByteBuffer m5302n = c0714n.m5302n();
                if (m5302n.hasRemaining()) {
                    int remaining = m5302n.remaining();
                    this.f267i.setInput(m5302n.array(), m5302n.arrayOffset() + m5302n.position(), m5302n.remaining());
                    do {
                        m5319b.position(m5319b.position() + this.f267i.inflate(m5319b.array(), m5319b.arrayOffset() + m5319b.position(), m5319b.remaining()));
                        if (!m5319b.hasRemaining()) {
                            m5319b.flip();
                            this.f268j.m5326a(m5319b);
                            if (!f266h && remaining == 0) {
                                throw new AssertionError();
                            }
                            m5319b = C0714n.m5319b(m5319b.capacity() * 2);
                        }
                        if (!this.f267i.needsInput()) {
                        }
                    } while (!this.f267i.finished());
                }
                C0714n.m5314c(m5302n);
            }
            m5319b.flip();
            this.f268j.m5326a(m5319b);
            C0608K.m5480a(this, this.f268j);
        } catch (Exception e) {
            mo3859b(e);
        }
    }

    @Override // p007b.p014c.p015a.AbstractC0718q
    /* renamed from: b */
    public void mo3859b(Exception exc) {
        this.f267i.end();
        if (exc != null && this.f267i.getRemaining() > 0) {
            exc = new DataRemainingException("data still remaining in inflater", exc);
        }
        super.mo3859b(exc);
    }
}
