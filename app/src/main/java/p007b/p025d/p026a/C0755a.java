package p007b.p025d.p026a;

import com.zzz.ipfssdk.LogUtil;
import java.util.List;
import java.util.Map;

/* renamed from: b.d.a.a */
/* loaded from: classes2.dex */
public class C0755a implements AbstractC0806r {

    /* renamed from: a */
    public long f486a;

    /* renamed from: b */
    public final /* synthetic */ long f487b;

    /* renamed from: c */
    public final /* synthetic */ C0758b f488c;

    public C0755a(C0758b c0758b, long j) {
        this.f488c = c0758b;
        this.f487b = j;
        this.f486a = this.f487b;
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4931a(int i, Map<String, List<String>> map) {
        synchronized (this.f488c) {
            this.f488c.f498h = map;
            this.f488c.notify();
        }
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4930a(Exception exc) {
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4929a(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        this.f488c.m5177a(this.f486a, bArr2, i);
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    public void end(int i) {
        if (i == 200 || i == 206) {
            synchronized (this.f488c) {
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "HTTP Runable end!");
                this.f488c.m5179a(this.f486a);
                this.f488c.notify();
            }
        }
    }
}
