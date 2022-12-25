package p007b.p014c.p015a;

import android.util.Log;
import com.koushikdutta.async.AsyncServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p016a.AbstractC0613c;
import p007b.p014c.p015a.p016a.AbstractC0617f;
import p007b.p014c.p015a.p023e.C0700a;

/* renamed from: b.c.a.d */
/* loaded from: classes2.dex */
public class C0691d implements AbstractC0712l {

    /* renamed from: a */
    public static final /* synthetic */ boolean f325a = !C0691d.class.desiredAssertionStatus();

    /* renamed from: c */
    public AbstractC0716o f326c;

    /* renamed from: d */
    public SelectionKey f327d;

    /* renamed from: e */
    public AsyncServer f328e;

    /* renamed from: g */
    public C0700a f330g;

    /* renamed from: h */
    public boolean f331h;

    /* renamed from: i */
    public AbstractC0617f f332i;

    /* renamed from: j */
    public AbstractC0613c f333j;

    /* renamed from: k */
    public AbstractC0610a f334k;

    /* renamed from: l */
    public boolean f335l;

    /* renamed from: m */
    public Exception f336m;

    /* renamed from: n */
    public AbstractC0610a f337n;

    /* renamed from: f */
    public C0714n f329f = new C0714n();

    /* renamed from: o */
    public boolean f338o = false;

    /* renamed from: a */
    public final void m5359a() {
        this.f327d.cancel();
        try {
            this.f326c.close();
        } catch (IOException unused) {
        }
    }

    /* renamed from: a */
    public final void m5358a(int i) {
        SelectionKey selectionKey;
        int interestOps;
        if (this.f327d.isValid()) {
            if (i <= 0) {
                selectionKey = this.f327d;
                interestOps = selectionKey.interestOps() & (-5);
            } else if (!f325a && this.f326c.m5298a()) {
                throw new AssertionError();
            } else {
                selectionKey = this.f327d;
                interestOps = selectionKey.interestOps() | 4;
            }
            selectionKey.interestOps(interestOps);
            return;
        }
        throw new IOException(new CancelledKeyException());
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: a */
    public void mo5293a(AbstractC0610a abstractC0610a) {
        this.f337n = abstractC0610a;
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: a */
    public void mo5292a(AbstractC0613c abstractC0613c) {
        this.f333j = abstractC0613c;
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: a */
    public void mo5289a(AbstractC0617f abstractC0617f) {
        this.f332i = abstractC0617f;
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: a */
    public void mo5288a(C0714n c0714n) {
        if (this.f328e.m3888b() != Thread.currentThread()) {
            this.f328e.m3884b(new RunnableC0609a(this, c0714n));
        } else if (!this.f326c.mo5296b()) {
            m5356a((Exception) null);
            if (!f325a && this.f326c.m5298a()) {
                throw new AssertionError();
            }
        } else {
            try {
                int m5303m = c0714n.m5303m();
                ByteBuffer[] m5320b = c0714n.m5320b();
                this.f326c.mo5297a(m5320b);
                c0714n.m5321a(m5320b);
                m5358a(c0714n.m5303m());
                this.f328e.m3887b(m5303m - c0714n.m5303m());
            } catch (IOException unused) {
                m5359a();
            }
        }
    }

    /* renamed from: a */
    public void m5357a(AsyncServer asyncServer, SelectionKey selectionKey) {
        this.f328e = asyncServer;
        this.f327d = selectionKey;
    }

    /* renamed from: a */
    public void m5356a(Exception exc) {
        if (this.f331h) {
            return;
        }
        this.f331h = true;
        AbstractC0610a abstractC0610a = this.f334k;
        if (abstractC0610a == null) {
            return;
        }
        abstractC0610a.mo5196a(exc);
        this.f334k = null;
    }

    /* renamed from: a */
    public void m5355a(SocketChannel socketChannel, InetSocketAddress inetSocketAddress) {
        this.f330g = new C0700a();
        this.f326c = new C0606I(socketChannel);
    }

    /* renamed from: b */
    public void m5353b(int i) {
        try {
            this.f327d.interestOps(i | this.f327d.interestOps());
        } catch (CancelledKeyException e) {
            e.printStackTrace();
        }
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: b */
    public void mo5287b(AbstractC0610a abstractC0610a) {
        this.f334k = abstractC0610a;
    }

    /* renamed from: b */
    public void m5352b(Exception exc) {
        if (this.f335l) {
            return;
        }
        this.f335l = true;
        AbstractC0610a abstractC0610a = this.f337n;
        if (abstractC0610a != null) {
            abstractC0610a.mo5196a(exc);
        } else if (exc == null) {
        } else {
            Log.e("NIO", "Unhandled exception", exc);
        }
    }

    /* renamed from: b */
    public boolean m5354b() {
        return this.f326c.mo5296b() && this.f327d.isValid();
    }

    @Override // p007b.p014c.p015a.AbstractC0712l, p007b.p014c.p015a.AbstractC0717p, p007b.p014c.p015a.AbstractC0719r
    /* renamed from: c */
    public AsyncServer mo5283c() {
        return this.f328e;
    }

    /* renamed from: c */
    public void m5351c(Exception exc) {
        if (this.f329f.m5308h()) {
            this.f336m = exc;
        } else {
            m5352b(exc);
        }
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    public void close() {
        m5359a();
        m5356a((Exception) null);
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: d */
    public void mo5294d() {
        if (this.f328e.m3888b() != Thread.currentThread()) {
            this.f328e.m3884b(new RunnableC0632c(this));
        } else if (!this.f338o) {
        } else {
            this.f338o = false;
            try {
                this.f327d.interestOps(this.f327d.interestOps() | 1);
            } catch (Exception unused) {
            }
            m5348k();
            if (m5354b()) {
                return;
            }
            m5351c(this.f336m);
        }
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: e */
    public String mo5282e() {
        return null;
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    public void end() {
        this.f326c.mo5295c();
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: f */
    public boolean mo5281f() {
        return this.f338o;
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: g */
    public AbstractC0613c mo5291g() {
        return this.f333j;
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: h */
    public AbstractC0617f mo5286h() {
        return this.f332i;
    }

    /* renamed from: i */
    public void m5350i() {
        if (!this.f326c.m5298a()) {
            SelectionKey selectionKey = this.f327d;
            selectionKey.interestOps(selectionKey.interestOps() & (-5));
        }
        AbstractC0617f abstractC0617f = this.f332i;
        if (abstractC0617f != null) {
            abstractC0617f.mo5215a();
        }
    }

    /* renamed from: j */
    public int m5349j() {
        long j;
        int i;
        m5348k();
        boolean z = false;
        if (this.f338o) {
            return 0;
        }
        ByteBuffer m5339a = this.f330g.m5339a();
        try {
            j = this.f326c.read(m5339a);
        } catch (Exception e) {
            m5359a();
            m5351c(e);
            m5356a(e);
            j = -1;
        }
        int i2 = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i2 < 0) {
            m5359a();
            z = true;
            i = 0;
        } else {
            i = (int) (0 + j);
        }
        if (i2 > 0) {
            this.f330g.m5337a(j);
            m5339a.flip();
            this.f329f.m5326a(m5339a);
            C0608K.m5480a(this, this.f329f);
        } else {
            C0714n.m5314c(m5339a);
        }
        if (z) {
            m5351c(null);
            m5356a((Exception) null);
        }
        return i;
    }

    /* renamed from: k */
    public final void m5348k() {
        if (this.f329f.m5308h()) {
            C0608K.m5480a(this, this.f329f);
        }
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    public void pause() {
        if (this.f328e.m3888b() != Thread.currentThread()) {
            this.f328e.m3884b(new RunnableC0618b(this));
        } else if (this.f338o) {
        } else {
            this.f338o = true;
            try {
                this.f327d.interestOps(this.f327d.interestOps() & (-2));
            } catch (Exception unused) {
            }
        }
    }
}
