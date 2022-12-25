package p007b.p025d.p026a;

import java.util.concurrent.BlockingQueue;

/* renamed from: b.d.a.n */
/* loaded from: classes2.dex */
public class RunnableC0802n implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ C0804p f713a;

    public RunnableC0802n(C0804p c0804p) {
        this.f713a = c0804p;
    }

    @Override // java.lang.Runnable
    public void run() {
        BlockingQueue blockingQueue;
        while (true) {
            try {
                blockingQueue = this.f713a.f718e;
                RunnableC0801m runnableC0801m = (RunnableC0801m) blockingQueue.take();
                if (runnableC0801m.m4944a()) {
                    runnableC0801m.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
