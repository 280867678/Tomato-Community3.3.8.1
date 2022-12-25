package p007b.p014c.p015a.p018c.p021c;

import com.tomatolive.library.utils.ConstantUtils;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import p007b.p014c.p015a.AbstractC0712l;
import p007b.p014c.p015a.C0608K;
import p007b.p014c.p015a.p018c.C0649b;
import p007b.p014c.p015a.p018c.p019a.AbstractC0634a;
import p007b.p014c.p015a.p018c.p021c.C0677q;

/* renamed from: b.c.a.c.c.g */
/* loaded from: classes2.dex */
public class C0667g extends C0677q.AbstractC0678a {

    /* renamed from: C */
    public final /* synthetic */ AbstractC0712l f274C;

    /* renamed from: D */
    public final /* synthetic */ C0668h f275D;

    /* renamed from: r */
    public C0677q.AbstractC0678a f276r = this;

    /* renamed from: s */
    public AbstractC0683s f277s;

    /* renamed from: t */
    public String f278t;

    /* renamed from: u */
    public String f279u;

    /* renamed from: v */
    public boolean f280v;

    /* renamed from: w */
    public boolean f281w;

    /* renamed from: x */
    public C0675o f282x;

    /* renamed from: y */
    public boolean f283y;

    /* renamed from: z */
    public boolean f284z;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0667g(C0668h c0668h, AbstractC0712l abstractC0712l) {
        super(c0668h.f285a);
        this.f275D = c0668h;
        this.f274C = abstractC0712l;
        new RunnableC0662b(this);
        new C0663c(this);
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0673m
    /* renamed from: a */
    public AbstractC0634a mo5402a(C0649b c0649b) {
        String[] split = m5392l().split(ConstantUtils.PLACEHOLDER_STR_ONE);
        this.f278t = split[1];
        this.f279u = URLDecoder.decode(this.f278t.split("\\?")[0]);
        this.f297n = split[0];
        C0677q.C0681d mo5368a = this.f275D.f285a.mo5368a(this.f297n, this.f279u);
        if (mo5368a == null) {
            return null;
        }
        Matcher matcher = mo5368a.f319c;
        this.f277s = mo5368a.f320d;
        AbstractC0661a abstractC0661a = mo5368a.f321e;
        if (abstractC0661a != null) {
            return abstractC0661a.m5421a(c0649b);
        }
        return null;
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0673m, p007b.p014c.p015a.p016a.AbstractC0610a
    /* renamed from: a */
    public void mo5196a(Exception exc) {
        this.f281w = true;
        super.mo5196a(exc);
        this.f294j.mo5292a(new C0666f(this));
        if (exc != null) {
            this.f294j.close();
            return;
        }
        m5418o();
        if (!m5393k().mo5366h() || this.f284z) {
            return;
        }
        m5417p();
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0673m
    /* renamed from: b */
    public AbstractC0634a mo5398b(C0649b c0649b) {
        return this.f275D.f285a.m5412a(c0649b);
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0670j
    public String getPath() {
        return this.f279u;
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0673m
    /* renamed from: m */
    public void mo5391m() {
        C0649b mo5394i = mo5394i();
        if (!this.f283y && "100-continue".equals(mo5394i.m5436b("Expect"))) {
            pause();
            C0608K.m5478a(this.f294j, "HTTP/1.1 100 Continue\r\n\r\n".getBytes(), new C0664d(this));
            return;
        }
        this.f282x = new C0665e(this, this.f274C, this);
        this.f284z = this.f275D.f285a.m5404b(this, this.f282x);
        if (this.f284z) {
            return;
        }
        if (this.f277s == null) {
            this.f282x.mo5388a(404);
            this.f282x.end();
        } else if (m5393k().mo5366h() && !this.f281w) {
        } else {
            m5417p();
        }
    }

    /* renamed from: o */
    public final void m5418o() {
        if (!this.f281w || !this.f280v || this.f275D.f285a.m5409a((AbstractC0674n) this.f282x)) {
            return;
        }
        if (this.f275D.f285a.m5410a(this.f276r, this.f282x)) {
            this.f275D.mo5415a(this.f274C);
        } else {
            this.f274C.close();
        }
    }

    /* renamed from: p */
    public void m5417p() {
        this.f275D.f285a.m5408a(this.f277s, this, this.f282x);
    }
}
