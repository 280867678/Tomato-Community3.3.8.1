package p007b.p025d.p026a;

import android.content.Context;
import android.util.Log;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.P2PUdpUtil;
import java.util.concurrent.atomic.AtomicBoolean;
import p007b.p025d.p026a.p028b.C0759a;
import p007b.p025d.p026a.p029c.C0763b;
import p007b.p025d.p026a.p030d.RunnableC0778l;
import p007b.p025d.p026a.p032e.C0783b;
import p007b.p025d.p026a.p032e.p034b.C0785b;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: b.d.a.G */
/* loaded from: classes2.dex */
public class RunnableC0736G implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ Context f399a;

    /* renamed from: b */
    public final /* synthetic */ C0738I f400b;

    public RunnableC0736G(C0738I c0738i, Context context) {
        this.f400b = c0738i;
        this.f399a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        AtomicBoolean atomicBoolean;
        C0785b.AbstractC0787b abstractC0787b;
        C0738I c0738i = this.f400b;
        LogUtil.m119i("IpfsSDKOld", "IpfsSDKOld init!");
        C0740K.m5251c("IpfsSDKOld begin init!");
        atomicBoolean = c0738i.f410h;
        if (atomicBoolean.compareAndSet(false, true)) {
            RunnableC0778l.m5061a().m5059a(this.f399a);
            String m5030d = C0783b.m5030d(this.f399a);
            Log.d("IpfsSDKOld", "uniqueID: " + m5030d);
            C0740K.m5252b(m5030d);
            C0785b m5025a = C0785b.m5025a();
            Context context = this.f399a;
            abstractC0787b = this.f400b.f417o;
            m5025a.m5024a(context, abstractC0787b);
            C0763b.m5163b().m5164a();
            C0754Z.m5186d().m5194a(C0759a.f499a);
            P2PUdpUtil.m116a().m104a(String.valueOf(C0757aa.m5181a(0L, Long.MAX_VALUE)), "wlan0", 10015);
        }
    }
}
