package p007b.p012b.p013a;

import com.dianping.logan.CLoganProtocol;

/* renamed from: b.b.a.e */
/* loaded from: classes2.dex */
public class C0586e implements AbstractC0587f {

    /* renamed from: a */
    public static C0586e f152a;

    /* renamed from: b */
    public AbstractC0587f f153b;

    /* renamed from: c */
    public boolean f154c;

    /* renamed from: d */
    public AbstractC0590i f155d;

    /* renamed from: b */
    public static C0586e m5505b() {
        if (f152a == null) {
            synchronized (C0586e.class) {
                f152a = new C0586e();
            }
        }
        return f152a;
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4182a() {
        AbstractC0587f abstractC0587f = this.f153b;
        if (abstractC0587f != null) {
            abstractC0587f.mo4182a();
        }
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4181a(int i, String str, long j, String str2, long j2, boolean z) {
        AbstractC0587f abstractC0587f = this.f153b;
        if (abstractC0587f != null) {
            abstractC0587f.mo4181a(i, str, j, str2, j2, z);
        }
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4180a(AbstractC0590i abstractC0590i) {
        this.f155d = abstractC0590i;
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4179a(String str) {
        AbstractC0587f abstractC0587f = this.f153b;
        if (abstractC0587f != null) {
            abstractC0587f.mo4179a(str);
        }
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4177a(String str, String str2, int i, String str3, String str4) {
        if (this.f154c) {
            return;
        }
        if (!CLoganProtocol.m4175b()) {
            this.f153b = null;
            return;
        }
        this.f153b = CLoganProtocol.m4174c();
        this.f153b.mo4180a(this.f155d);
        this.f153b.mo4177a(str, str2, i, str3, str4);
        this.f154c = true;
    }

    @Override // p007b.p012b.p013a.AbstractC0587f
    /* renamed from: a */
    public void mo4176a(boolean z) {
        AbstractC0587f abstractC0587f = this.f153b;
        if (abstractC0587f != null) {
            abstractC0587f.mo4176a(z);
        }
    }
}
