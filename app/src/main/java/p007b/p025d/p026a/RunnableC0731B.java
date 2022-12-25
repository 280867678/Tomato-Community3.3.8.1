package p007b.p025d.p026a;

import com.zzz.ipfssdk.callback.OnStateChangeListenner;

/* renamed from: b.d.a.B */
/* loaded from: classes2.dex */
public class RunnableC0731B implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ C0733D f393a;

    public RunnableC0731B(C0733D c0733d) {
        this.f393a = c0733d;
    }

    @Override // java.lang.Runnable
    public void run() {
        OnStateChangeListenner onStateChangeListenner = this.f393a.f396a;
        if (onStateChangeListenner != null) {
            onStateChangeListenner.onInitted();
        }
    }
}
