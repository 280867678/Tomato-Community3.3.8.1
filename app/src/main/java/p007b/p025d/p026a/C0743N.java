package p007b.p025d.p026a;

import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: b.d.a.N */
/* loaded from: classes2.dex */
public class C0743N implements AbstractC0808t {

    /* renamed from: a */
    public C0744O f429a;

    /* renamed from: b */
    public long f430b;

    /* renamed from: c */
    public final /* synthetic */ long f431c;

    /* renamed from: d */
    public final /* synthetic */ C0744O f432d;

    public C0743N(C0744O c0744o, long j) {
        this.f432d = c0744o;
        this.f431c = j;
        this.f429a = this.f432d;
        this.f430b = this.f431c;
    }

    @Override // p007b.p025d.p026a.AbstractC0808t
    /* renamed from: a */
    public void mo4925a(long j) {
    }

    @Override // p007b.p025d.p026a.AbstractC0808t
    /* renamed from: a */
    public void mo4924a(long j, String str, String str2, byte[] bArr, int i) {
        long j2;
        j2 = this.f429a.f435c;
        if (j == j2) {
            this.f429a.m5233b(this.f430b);
        }
    }

    @Override // p007b.p025d.p026a.AbstractC0808t
    /* renamed from: a */
    public void mo4923a(long j, byte[] bArr, int i) {
        Object obj;
        long j2;
        long j3;
        int i2;
        int i3;
        int i4;
        AtomicInteger atomicInteger;
        obj = this.f429a.f436d;
        synchronized (obj) {
            StringBuilder sb = new StringBuilder();
            sb.append("Get data from node!!!!!!!!!--->");
            sb.append(j);
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(i);
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
            j2 = this.f429a.f435c;
            if (j == j2) {
                this.f429a.m5240a(this.f430b, bArr, i);
                C0744O c0744o = this.f429a;
                i2 = c0744o.f438f;
                c0744o.f438f = i2 + i;
                i3 = this.f429a.f438f;
                i4 = this.f429a.f437e;
                if (i3 == i4) {
                    this.f429a.m5242a(this.f430b);
                }
                atomicInteger = this.f429a.f445m;
                atomicInteger.set(0);
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Error taskId!--->");
                sb2.append(j);
                sb2.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                j3 = this.f429a.f435c;
                sb2.append(j3);
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb2.toString());
            }
        }
    }
}
