package p007b.p025d.p026a;

import com.zzz.ipfssdk.callback.OnStateChangeListenner;

/* renamed from: b.d.a.A */
/* loaded from: classes2.dex */
public class RunnableC0730A implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ C0733D f392a;

    public RunnableC0730A(C0733D c0733d) {
        this.f392a = c0733d;
    }

    @Override // java.lang.Runnable
    public void run() {
        OnStateChangeListenner onStateChangeListenner = this.f392a.f396a;
        if (onStateChangeListenner != null) {
            onStateChangeListenner.onIniting();
        }
    }
}
