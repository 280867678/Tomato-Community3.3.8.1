package p007b.p014c.p015a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import p007b.p014c.p015a.p016a.AbstractC0613c;

/* renamed from: b.c.a.F */
/* loaded from: classes2.dex */
public class C0599F implements AbstractC0613c {

    /* renamed from: j */
    public AbstractC0717p f188j;

    /* renamed from: k */
    public LinkedList<AbstractC0603d> f189k = new LinkedList<>();

    /* renamed from: l */
    public ArrayList<Object> f190l = new ArrayList<>();

    /* renamed from: m */
    public ByteOrder f191m = ByteOrder.BIG_ENDIAN;

    /* renamed from: n */
    public C0714n f192n = new C0714n();

    /* renamed from: b.c.a.F$a */
    /* loaded from: classes2.dex */
    static class C0600a extends AbstractC0603d {

        /* renamed from: b */
        public AbstractC0601b<byte[]> f193b;

        public C0600a(int i, AbstractC0601b<byte[]> abstractC0601b) {
            super(i);
            if (i > 0) {
                this.f193b = abstractC0601b;
                return;
            }
            throw new IllegalArgumentException("length should be > 0");
        }

        @Override // p007b.p014c.p015a.C0599F.AbstractC0603d
        /* renamed from: a */
        public AbstractC0603d mo5276a(AbstractC0717p abstractC0717p, C0714n c0714n) {
            byte[] bArr = new byte[this.f196a];
            c0714n.m5323a(bArr);
            this.f193b.mo5426a(bArr);
            return null;
        }
    }

    /* renamed from: b.c.a.F$b */
    /* loaded from: classes2.dex */
    public interface AbstractC0601b<T> {
        /* renamed from: a */
        void mo5426a(T t);
    }

    /* renamed from: b.c.a.F$c */
    /* loaded from: classes2.dex */
    static class C0602c extends AbstractC0603d {

        /* renamed from: b */
        public byte f194b;

        /* renamed from: c */
        public AbstractC0613c f195c;

        public C0602c(byte b, AbstractC0613c abstractC0613c) {
            super(1);
            this.f194b = b;
            this.f195c = abstractC0613c;
        }

        @Override // p007b.p014c.p015a.C0599F.AbstractC0603d
        /* renamed from: a */
        public AbstractC0603d mo5276a(AbstractC0717p abstractC0717p, C0714n c0714n) {
            C0714n c0714n2 = new C0714n();
            boolean z = true;
            while (true) {
                if (c0714n.m5301o() <= 0) {
                    break;
                }
                ByteBuffer m5302n = c0714n.m5302n();
                m5302n.mark();
                int i = 0;
                while (m5302n.remaining() > 0) {
                    z = m5302n.get() == this.f194b;
                    if (z) {
                        break;
                    }
                    i++;
                }
                m5302n.reset();
                if (z) {
                    c0714n.m5318b(m5302n);
                    c0714n.m5327a(c0714n2, i);
                    c0714n.m5330a();
                    break;
                }
                c0714n2.m5326a(m5302n);
            }
            this.f195c.mo3861a(abstractC0717p, c0714n2);
            if (z) {
                return null;
            }
            return this;
        }
    }

    /* renamed from: b.c.a.F$d */
    /* loaded from: classes2.dex */
    static abstract class AbstractC0603d {

        /* renamed from: a */
        public int f196a;

        public AbstractC0603d(int i) {
            this.f196a = i;
        }

        /* renamed from: a */
        public abstract AbstractC0603d mo5276a(AbstractC0717p abstractC0717p, C0714n c0714n);
    }

    static {
        new Hashtable();
    }

    public C0599F(AbstractC0717p abstractC0717p) {
        new C0727x(this, 0);
        new C0728y(this, 1);
        new C0729z(this, 2);
        new C0594A(this, 4);
        new C0595B(this, 8);
        new C0596C(this);
        new C0597D(this);
        new C0598E(this);
        this.f188j = abstractC0717p;
        this.f188j.mo5292a(this);
    }

    /* renamed from: a */
    public C0599F m5490a(byte b, AbstractC0613c abstractC0613c) {
        this.f189k.add(new C0602c(b, abstractC0613c));
        return this;
    }

    /* renamed from: a */
    public C0599F m5489a(int i, AbstractC0601b<byte[]> abstractC0601b) {
        this.f189k.add(new C0600a(i, abstractC0601b));
        return this;
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0613c
    /* renamed from: a */
    public void mo3861a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        c0714n.m5328a(this.f192n);
        while (this.f189k.size() > 0 && this.f192n.m5303m() >= this.f189k.peek().f196a) {
            this.f192n.m5325a(this.f191m);
            AbstractC0603d mo5276a = this.f189k.poll().mo5276a(abstractC0717p, this.f192n);
            if (mo5276a != null) {
                this.f189k.addFirst(mo5276a);
            }
        }
        if (this.f189k.size() == 0) {
            this.f192n.m5328a(c0714n);
        }
    }
}
