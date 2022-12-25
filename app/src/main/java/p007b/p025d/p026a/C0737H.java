package p007b.p025d.p026a;

import com.zzz.ipfssdk.LogUtil;
import java.util.List;
import java.util.Map;

/* renamed from: b.d.a.H */
/* loaded from: classes2.dex */
public class C0737H implements AbstractC0806r {

    /* renamed from: a */
    public C0738I f401a;

    /* renamed from: b */
    public int f402b = 0;

    /* renamed from: c */
    public final /* synthetic */ C0738I f403c;

    public C0737H(C0738I c0738i) {
        this.f403c = c0738i;
        this.f401a = this.f403c;
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4931a(int i, Map<String, List<String>> map) {
        this.f402b = i;
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4930a(Exception exc) {
        this.f401a.m5274a(0, (String) null);
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4929a(byte[] bArr, int i) {
        String str = new String(bArr);
        LogUtil.m119i("IpfsSDKOld", "QueryResource resp: " + str);
        C0740K.m5251c("QueryResource resp: " + str);
        this.f401a.m5274a(this.f402b, str);
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    public void end(int i) {
        if (i == 200 || i == 206 || i == 0) {
            return;
        }
        this.f401a.m5274a(i, (String) null);
    }
}
