package p007b.p025d.p026a.p027a;

import com.zzz.ipfssdk.callback.OnStateChangeListenner;
import com.zzz.ipfssdk.callback.exception.CodeState;
import p007b.p025d.p026a.C0738I;

/* renamed from: b.d.a.a.a */
/* loaded from: classes2.dex */
public class C0756a implements OnStateChangeListenner {

    /* renamed from: a */
    public static C0756a f489a = new C0756a();

    /* renamed from: b */
    public CodeState f490b;

    /* renamed from: a */
    public static C0756a m5182a() {
        return f489a;
    }

    @Override // com.zzz.ipfssdk.callback.OnStateChangeListenner
    public void onException(CodeState codeState) {
        if (codeState == null) {
            return;
        }
        CodeState codeState2 = this.f490b;
        if (codeState2 != null && codeState2.equal(codeState)) {
            return;
        }
        if (C0738I.m5275a().m5265b() != null && codeState != null) {
            C0738I.m5275a().m5265b().onException(codeState);
        }
        this.f490b = codeState;
    }

    @Override // com.zzz.ipfssdk.callback.OnStateChangeListenner
    public void onIniting() {
        this.f490b = null;
        if (C0738I.m5275a().m5265b() != null) {
            C0738I.m5275a().m5265b().onIniting();
        }
    }

    @Override // com.zzz.ipfssdk.callback.OnStateChangeListenner
    public void onInitted() {
        if (C0738I.m5275a().m5265b() != null) {
            C0738I.m5275a().m5265b().onInitted();
        }
    }
}
