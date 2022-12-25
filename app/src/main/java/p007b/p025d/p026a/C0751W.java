package p007b.p025d.p026a;

import com.zzz.ipfssdk.LogUtil;
import java.nio.ByteBuffer;
import p007b.p014c.p015a.AbstractC0712l;
import p007b.p014c.p015a.C0691d;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.p016a.AbstractC0617f;

/* renamed from: b.d.a.W */
/* loaded from: classes2.dex */
public class C0751W implements AbstractC0617f {

    /* renamed from: a */
    public C0752X f463a;

    /* renamed from: b */
    public C0691d f464b;

    /* renamed from: c */
    public boolean f465c = true;

    /* renamed from: d */
    public boolean f466d = true;

    /* renamed from: e */
    public C0714n f467e = new C0714n();

    /* renamed from: f */
    public byte[] f468f = new byte[10240];

    /* renamed from: g */
    public final /* synthetic */ C0752X f469g;

    public C0751W(C0752X c0752x) {
        AbstractC0712l abstractC0712l;
        this.f469g = c0752x;
        this.f463a = this.f469g;
        abstractC0712l = this.f463a.f470b;
        this.f464b = (C0691d) abstractC0712l;
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0617f
    /* renamed from: a */
    public void mo5215a() {
        Object obj;
        AbstractC0712l abstractC0712l;
        AbstractC0712l abstractC0712l2;
        RunnableC0792g runnableC0792g;
        int i;
        obj = this.f463a.f471c;
        synchronized (obj) {
            abstractC0712l = this.f463a.f470b;
            if (abstractC0712l == null) {
                return;
            }
            C0691d c0691d = this.f464b;
            abstractC0712l2 = this.f463a.f470b;
            if (c0691d != abstractC0712l2) {
                return;
            }
            runnableC0792g = this.f463a.f474g;
            int m4998a = runnableC0792g.m4998a(this.f468f, this.f468f.length);
            StringBuilder sb = new StringBuilder();
            sb.append("----------------------------------------------->ProxyHttpRequestHandler ReadLen = ");
            sb.append(m4998a);
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
            if (m4998a > 0 && this.f466d) {
                if (this.f468f[0] != 71) {
                    byte b = this.f468f[1];
                }
                this.f466d = false;
            }
            if (m4998a == -1) {
                this.f465c = false;
            } else if (m4998a == 0) {
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                ByteBuffer allocate = ByteBuffer.allocate(m4998a);
                allocate.put(this.f468f, 0, m4998a);
                allocate.flip();
                this.f467e.m5326a(allocate);
            }
            C0752X c0752x = this.f469g;
            i = this.f469g.f479l;
            c0752x.f479l = i + this.f467e.m5303m();
            this.f464b.mo5288a(this.f467e);
            if (this.f465c) {
                this.f464b.m5353b(4);
            }
        }
    }
}
