package p007b.p025d.p026a;

import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.util.net.NetworkType;
import java.util.concurrent.ExecutorService;
import p007b.p025d.p026a.p032e.p034b.C0785b;

/* renamed from: b.d.a.F */
/* loaded from: classes2.dex */
public class C0735F implements C0785b.AbstractC0787b {

    /* renamed from: a */
    public final /* synthetic */ C0738I f398a;

    public C0735F(C0738I c0738i) {
        this.f398a = c0738i;
    }

    @Override // p007b.p025d.p026a.p032e.p034b.C0785b.AbstractC0787b
    /* renamed from: a */
    public void mo5020a(boolean z, NetworkType networkType) {
        ExecutorService executorService;
        if (z) {
            LogUtil.m121d("IpfsSDKOld", "onNetWorkChange: isNetConnected " + z + ",networkType " + networkType);
            executorService = this.f398a.f416n;
            executorService.execute(new RunnableC0734E(this));
        }
    }
}
