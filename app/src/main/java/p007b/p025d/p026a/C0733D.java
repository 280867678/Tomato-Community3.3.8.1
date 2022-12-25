package p007b.p025d.p026a;

import com.zzz.ipfssdk.IpfsSDK;
import com.zzz.ipfssdk.callback.OnStateChangeListenner;
import com.zzz.ipfssdk.callback.exception.CodeState;
import p007b.p025d.p026a.p032e.p035c.HandlerC0788a;

/* renamed from: b.d.a.D */
/* loaded from: classes2.dex */
public class C0733D implements OnStateChangeListenner {

    /* renamed from: a */
    public final /* synthetic */ OnStateChangeListenner f396a;

    public C0733D(IpfsSDK ipfsSDK, OnStateChangeListenner onStateChangeListenner) {
        this.f396a = onStateChangeListenner;
    }

    @Override // com.zzz.ipfssdk.callback.OnStateChangeListenner
    public void onException(CodeState codeState) {
        HandlerC0788a.m5019a().post(new RunnableC0732C(this, codeState));
    }

    @Override // com.zzz.ipfssdk.callback.OnStateChangeListenner
    public void onIniting() {
        HandlerC0788a.m5019a().post(new RunnableC0730A(this));
    }

    @Override // com.zzz.ipfssdk.callback.OnStateChangeListenner
    public void onInitted() {
        HandlerC0788a.m5019a().post(new RunnableC0731B(this));
    }
}
