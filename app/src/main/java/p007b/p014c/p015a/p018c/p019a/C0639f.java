package p007b.p014c.p015a.p018c.p019a;

import android.text.TextUtils;
import com.koushikdutta.async.http.Multimap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.C0725w;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p018c.C0649b;
import p007b.p014c.p015a.p018c.p021c.C0682r;

/* renamed from: b.c.a.c.a.f */
/* loaded from: classes2.dex */
public class C0639f extends C0682r implements AbstractC0634a<Multimap> {

    /* renamed from: k */
    public C0725w f232k;

    /* renamed from: l */
    public C0649b f233l;

    /* renamed from: m */
    public C0714n f234m;

    /* renamed from: n */
    public C0641g f235n;

    /* renamed from: p */
    public AbstractC0640a f236p;

    /* renamed from: q */
    public ArrayList<C0641g> f237q;

    /* renamed from: b.c.a.c.a.f$a */
    /* loaded from: classes2.dex */
    public interface AbstractC0640a {
        /* renamed from: a */
        void m5446a(C0641g c0641g);
    }

    public C0639f(String str) {
        String m3868b = Multimap.m3866c(str).m3868b("boundary");
        if (m3868b == null) {
            mo3859b(new Exception("No boundary found for multipart/form-data"));
        } else {
            m5371a(m3868b);
        }
    }

    /* renamed from: a */
    public void m5449a(C0641g c0641g) {
        if (this.f237q == null) {
            this.f237q = new ArrayList<>();
        }
        this.f237q.add(c0641g);
    }

    @Override // p007b.p014c.p015a.p018c.p019a.AbstractC0634a
    /* renamed from: a */
    public void mo5367a(AbstractC0717p abstractC0717p, AbstractC0610a abstractC0610a) {
        m5284a(abstractC0717p);
        mo5293a(abstractC0610a);
    }

    @Override // p007b.p014c.p015a.p018c.p019a.AbstractC0634a
    /* renamed from: h */
    public boolean mo5366h() {
        return false;
    }

    @Override // p007b.p014c.p015a.p018c.p021c.C0682r
    /* renamed from: k */
    public void mo5370k() {
        super.mo5370k();
        m5447n();
    }

    @Override // p007b.p014c.p015a.p018c.p021c.C0682r
    /* renamed from: l */
    public void mo5369l() {
        C0649b c0649b = new C0649b();
        this.f232k = new C0725w();
        this.f232k.m5278a(new C0638e(this, c0649b));
        mo5292a(this.f232k);
    }

    /* renamed from: m */
    public List<C0641g> m5448m() {
        ArrayList<C0641g> arrayList = this.f237q;
        if (arrayList == null) {
            return null;
        }
        return new ArrayList(arrayList);
    }

    /* renamed from: n */
    public void m5447n() {
        if (this.f234m == null) {
            return;
        }
        if (this.f233l == null) {
            this.f233l = new C0649b();
        }
        String m5306j = this.f234m.m5306j();
        String m5445a = TextUtils.isEmpty(this.f235n.m5445a()) ? "unnamed" : this.f235n.m5445a();
        C0645k c0645k = new C0645k(m5445a, m5306j);
        c0645k.f238b = this.f235n.f238b;
        m5449a(c0645k);
        this.f233l.m5439a(m5445a, m5306j);
        this.f235n = null;
        this.f234m = null;
    }

    public String toString() {
        Iterator<C0641g> it2 = m5448m().iterator();
        return it2.hasNext() ? it2.next().toString() : "multipart content is empty";
    }
}
