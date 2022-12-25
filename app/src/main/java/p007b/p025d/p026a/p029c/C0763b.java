package p007b.p025d.p026a.p029c;

import com.zzz.ipfssdk.LogUtil;
import p007b.p025d.p026a.p028b.C0759a;
import p007b.p025d.p026a.p032e.C0781a;

/* renamed from: b.d.a.c.b */
/* loaded from: classes2.dex */
public class C0763b {

    /* renamed from: a */
    public static C0763b f511a = new C0763b();

    /* renamed from: b */
    public int f512b = C0759a.f499a;

    /* renamed from: b */
    public static final C0763b m5163b() {
        return f511a;
    }

    /* renamed from: a */
    public void m5164a() {
        boolean m5038a = C0781a.m5038a(this.f512b);
        StringBuilder sb = new StringBuilder();
        sb.append("端口");
        sb.append(this.f512b);
        sb.append(":");
        sb.append(m5038a ? "可用" : "不可用");
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
        while (!m5038a) {
            this.f512b++;
            m5038a = C0781a.m5038a(this.f512b);
        }
        C0759a.f499a = this.f512b;
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "当前分配的代理端口:" + C0759a.f499a);
    }
}
