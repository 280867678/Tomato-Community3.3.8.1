package p007b.p025d.p026a;

import com.zzz.ipfssdk.P2PUdpUtil;
import com.zzz.ipfssdk.util.net.NetworkType;
import p007b.p025d.p026a.p030d.RunnableC0778l;
import p007b.p025d.p026a.p032e.p034b.C0784a;

/* renamed from: b.d.a.E */
/* loaded from: classes2.dex */
public class RunnableC0734E implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ C0735F f397a;

    public RunnableC0734E(C0735F c0735f) {
        this.f397a = c0735f;
    }

    @Override // java.lang.Runnable
    public void run() {
        RunnableC0778l m5061a;
        int i;
        P2PUdpUtil.m116a().m96d();
        C0754Z.m5186d().m5185e();
        if (NetworkType.NETWORK_WIFI == C0784a.m5027a(this.f397a.f398a.m5258d())) {
            m5061a = RunnableC0778l.m5061a();
            i = 0;
        } else {
            m5061a = RunnableC0778l.m5061a();
            i = 1;
        }
        m5061a.m5048b(i);
    }
}
