package p007b.p025d.p026a;

import com.zzz.ipfssdk.IpfsSDK;
import com.zzz.ipfssdk.crypt.DoneCallBack;
import p007b.p025d.p026a.p032e.p035c.HandlerC0788a;

/* renamed from: b.d.a.z */
/* loaded from: classes2.dex */
public class RunnableC0814z implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ DoneCallBack f729a;

    public RunnableC0814z(IpfsSDK ipfsSDK, DoneCallBack doneCallBack) {
        this.f729a = doneCallBack;
    }

    @Override // java.lang.Runnable
    public void run() {
        C0738I.m5275a().m5255g();
        HandlerC0788a.m5019a().post(new RunnableC0813y(this));
    }
}
