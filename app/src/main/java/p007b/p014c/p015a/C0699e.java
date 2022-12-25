package p007b.p014c.p015a;

import com.koushikdutta.async.ThreadQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* renamed from: b.c.a.e */
/* loaded from: classes2.dex */
public class C0699e {

    /* renamed from: a */
    public Semaphore f344a = new Semaphore(0);

    /* renamed from: a */
    public void m5342a() {
        ThreadQueue m3874a = ThreadQueue.m3874a(Thread.currentThread());
        C0699e c0699e = m3874a.f1520b;
        m3874a.f1520b = this;
        Semaphore semaphore = m3874a.f1521c;
        try {
            if (this.f344a.tryAcquire()) {
                return;
            }
            while (true) {
                Runnable remove = m3874a.remove();
                if (remove == null) {
                    semaphore.acquire(Math.max(1, semaphore.availablePermits()));
                    if (this.f344a.tryAcquire()) {
                        return;
                    }
                } else {
                    remove.run();
                }
            }
        } finally {
            m3874a.f1520b = c0699e;
        }
    }

    /* renamed from: a */
    public boolean m5341a(long j, TimeUnit timeUnit) {
        long convert = TimeUnit.MILLISECONDS.convert(j, timeUnit);
        ThreadQueue m3874a = ThreadQueue.m3874a(Thread.currentThread());
        C0699e c0699e = m3874a.f1520b;
        m3874a.f1520b = this;
        Semaphore semaphore = m3874a.f1521c;
        try {
            if (this.f344a.tryAcquire()) {
                return true;
            }
            long currentTimeMillis = System.currentTimeMillis();
            while (true) {
                Runnable remove = m3874a.remove();
                if (remove != null) {
                    remove.run();
                } else if (!semaphore.tryAcquire(Math.max(1, semaphore.availablePermits()), convert, TimeUnit.MILLISECONDS)) {
                    return false;
                } else {
                    if (this.f344a.tryAcquire()) {
                        return true;
                    }
                    if (System.currentTimeMillis() - currentTimeMillis >= convert) {
                        return false;
                    }
                }
            }
        } finally {
            m3874a.f1520b = c0699e;
        }
    }

    /* renamed from: b */
    public void m5340b() {
        this.f344a.release();
        ThreadQueue.m3876a(this);
    }
}
