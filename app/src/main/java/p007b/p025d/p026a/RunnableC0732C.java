package p007b.p025d.p026a;

import com.zzz.ipfssdk.callback.OnStateChangeListenner;
import com.zzz.ipfssdk.callback.exception.CodeState;

/* renamed from: b.d.a.C */
/* loaded from: classes2.dex */
public class RunnableC0732C implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ CodeState f394a;

    /* renamed from: b */
    public final /* synthetic */ C0733D f395b;

    public RunnableC0732C(C0733D c0733d, CodeState codeState) {
        this.f395b = c0733d;
        this.f394a = codeState;
    }

    @Override // java.lang.Runnable
    public void run() {
        OnStateChangeListenner onStateChangeListenner = this.f395b.f396a;
        if (onStateChangeListenner != null) {
            onStateChangeListenner.onException(this.f394a);
        }
    }
}
