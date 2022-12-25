package p007b.p025d.p026a;

import com.zzz.ipfssdk.crypt.DoneCallBack;

/* renamed from: b.d.a.y */
/* loaded from: classes2.dex */
public class RunnableC0813y implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ RunnableC0814z f728a;

    public RunnableC0813y(RunnableC0814z runnableC0814z) {
        this.f728a = runnableC0814z;
    }

    @Override // java.lang.Runnable
    public void run() {
        DoneCallBack doneCallBack = this.f728a.f729a;
        if (doneCallBack != null) {
            doneCallBack.onFinish();
        }
    }
}
