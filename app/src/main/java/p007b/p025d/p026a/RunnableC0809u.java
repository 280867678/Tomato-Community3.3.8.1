package p007b.p025d.p026a;

import android.util.Pair;
import com.zzz.ipfssdk.crypt.ResultCallBack;

/* renamed from: b.d.a.u */
/* loaded from: classes2.dex */
public class RunnableC0809u implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ Pair f720a;

    /* renamed from: b */
    public final /* synthetic */ RunnableC0810v f721b;

    public RunnableC0809u(RunnableC0810v runnableC0810v, Pair pair) {
        this.f721b = runnableC0810v;
        this.f720a = pair;
    }

    @Override // java.lang.Runnable
    public void run() {
        ResultCallBack resultCallBack = this.f721b.f724c;
        if (resultCallBack != null) {
            resultCallBack.onResult(this.f720a);
        }
    }
}
