package p007b.p014c.p015a;

import java.io.Closeable;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* renamed from: b.c.a.G */
/* loaded from: classes2.dex */
public class C0604G implements Closeable {

    /* renamed from: a */
    public Selector f197a;

    /* renamed from: b */
    public boolean f198b;

    /* renamed from: c */
    public Semaphore f199c = new Semaphore(0);

    public C0604G(Selector selector) {
        this.f197a = selector;
    }

    /* renamed from: a */
    public Selector m5487a() {
        return this.f197a;
    }

    /* renamed from: a */
    public void m5486a(long j) {
        try {
            this.f199c.drainPermits();
            this.f197a.select(j);
        } finally {
            this.f199c.release(Integer.MAX_VALUE);
        }
    }

    /* renamed from: b */
    public Set<SelectionKey> m5485b() {
        return this.f197a.keys();
    }

    /* renamed from: c */
    public int m5484c() {
        return this.f197a.selectNow();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.f197a.close();
    }

    /* renamed from: d */
    public Set<SelectionKey> m5483d() {
        return this.f197a.selectedKeys();
    }

    /* renamed from: e */
    public void m5482e() {
        boolean z = !this.f199c.tryAcquire();
        this.f197a.wakeup();
        if (z) {
            return;
        }
        synchronized (this) {
            if (this.f198b) {
                return;
            }
            this.f198b = true;
            for (int i = 0; i < 100; i++) {
                try {
                    try {
                        if (this.f199c.tryAcquire(10L, TimeUnit.MILLISECONDS)) {
                            synchronized (this) {
                                this.f198b = false;
                            }
                            return;
                        }
                    } catch (InterruptedException unused) {
                    }
                    this.f197a.wakeup();
                } catch (Throwable th) {
                    synchronized (this) {
                        this.f198b = false;
                        throw th;
                    }
                }
            }
            synchronized (this) {
                this.f198b = false;
            }
        }
    }

    public boolean isOpen() {
        return this.f197a.isOpen();
    }
}
