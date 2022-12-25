package p007b.p025d.p026a;

import com.zzz.ipfssdk.IpfsSDK;
import com.zzz.ipfssdk.crypt.DoneCallBack;
import p007b.p025d.p026a.p032e.p035c.HandlerC0788a;

/* renamed from: b.d.a.x */
/* loaded from: classes2.dex */
public class RunnableC0812x implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ String f726a;

    /* renamed from: b */
    public final /* synthetic */ DoneCallBack f727b;

    public RunnableC0812x(IpfsSDK ipfsSDK, String str, DoneCallBack doneCallBack) {
        this.f726a = str;
        this.f727b = doneCallBack;
    }

    @Override // java.lang.Runnable
    public void run() {
        C0738I.m5275a().m5259c(this.f726a);
        HandlerC0788a.m5019a().post(new RunnableC0811w(this));
    }
}
