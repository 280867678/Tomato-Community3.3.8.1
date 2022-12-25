package p007b.p025d.p026a;

import com.zzz.ipfssdk.crypt.DoneCallBack;

/* renamed from: b.d.a.w */
/* loaded from: classes2.dex */
public class RunnableC0811w implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ RunnableC0812x f725a;

    public RunnableC0811w(RunnableC0812x runnableC0812x) {
        this.f725a = runnableC0812x;
    }

    @Override // java.lang.Runnable
    public void run() {
        DoneCallBack doneCallBack = this.f725a.f727b;
        if (doneCallBack != null) {
            doneCallBack.onFinish();
        }
    }
}
