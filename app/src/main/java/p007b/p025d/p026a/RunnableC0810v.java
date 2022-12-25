package p007b.p025d.p026a;

import com.zzz.ipfssdk.IpfsSDK;
import com.zzz.ipfssdk.crypt.ResultCallBack;
import p007b.p025d.p026a.p032e.p035c.HandlerC0788a;

/* renamed from: b.d.a.v */
/* loaded from: classes2.dex */
public class RunnableC0810v implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ String f722a;

    /* renamed from: b */
    public final /* synthetic */ String f723b;

    /* renamed from: c */
    public final /* synthetic */ ResultCallBack f724c;

    public RunnableC0810v(IpfsSDK ipfsSDK, String str, String str2, ResultCallBack resultCallBack) {
        this.f722a = str;
        this.f723b = str2;
        this.f724c = resultCallBack;
    }

    @Override // java.lang.Runnable
    public void run() {
        HandlerC0788a.m5019a().post(new RunnableC0809u(this, C0738I.m5275a().m5269a(this.f722a, this.f723b)));
    }
}
