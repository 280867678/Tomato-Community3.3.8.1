package p007b.p025d.p026a;

import java.util.concurrent.BlockingQueue;

/* renamed from: b.d.a.o */
/* loaded from: classes2.dex */
public class RunnableC0803o implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ C0804p f714a;

    public RunnableC0803o(C0804p c0804p) {
        this.f714a = c0804p;
    }

    @Override // java.lang.Runnable
    public void run() {
        BlockingQueue blockingQueue;
        while (true) {
            try {
                blockingQueue = this.f714a.f719f;
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
