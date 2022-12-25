package p007b.p014c.p015a.p018c.p021c;

import com.koushikdutta.async.http.Protocol;
import java.io.IOException;
import p007b.p014c.p015a.AbstractC0712l;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0725w;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p016a.AbstractC0613c;
import p007b.p014c.p015a.p018c.C0649b;
import p007b.p014c.p015a.p018c.C0686d;
import p007b.p014c.p015a.p018c.p019a.AbstractC0634a;

/* renamed from: b.c.a.c.c.l */
/* loaded from: classes2.dex */
public class C0672l implements C0725w.AbstractC0726a {

    /* renamed from: a */
    public final /* synthetic */ AbstractC0673m f291a;

    public C0672l(AbstractC0673m abstractC0673m) {
        this.f291a = abstractC0673m;
    }

    @Override // p007b.p014c.p015a.C0725w.AbstractC0726a
    /* renamed from: a */
    public void mo5277a(String str) {
        String str2;
        C0649b c0649b;
        C0649b c0649b2;
        AbstractC0610a abstractC0610a;
        AbstractC0610a abstractC0610a2;
        C0649b c0649b3;
        C0649b c0649b4;
        C0649b c0649b5;
        C0649b c0649b6;
        String str3;
        str2 = this.f291a.f292h;
        if (str2 == null) {
            this.f291a.f292h = str;
            str3 = this.f291a.f292h;
            if (str3.contains("HTTP/")) {
                return;
            }
            this.f291a.m5390n();
            this.f291a.f294j.mo5292a(new AbstractC0613c.C0614a());
            this.f291a.mo3859b(new IOException("data/header received was not not http"));
        } else if (!"\r".equals(str)) {
            c0649b6 = this.f291a.f293i;
            c0649b6.m5440a(str);
        } else {
            AbstractC0673m abstractC0673m = this.f291a;
            AbstractC0712l abstractC0712l = abstractC0673m.f294j;
            Protocol protocol = Protocol.HTTP_1_1;
            c0649b = abstractC0673m.f293i;
            AbstractC0717p m5364a = C0686d.m5364a(abstractC0712l, protocol, c0649b, true);
            AbstractC0673m abstractC0673m2 = this.f291a;
            c0649b2 = abstractC0673m2.f293i;
            abstractC0673m2.f298o = abstractC0673m2.mo5402a(c0649b2);
            AbstractC0673m abstractC0673m3 = this.f291a;
            if (abstractC0673m3.f298o == null) {
                abstractC0610a2 = abstractC0673m3.f295l;
                c0649b3 = this.f291a.f293i;
                abstractC0673m3.f298o = C0686d.m5365a(m5364a, abstractC0610a2, c0649b3);
                AbstractC0673m abstractC0673m4 = this.f291a;
                if (abstractC0673m4.f298o == null) {
                    c0649b4 = abstractC0673m4.f293i;
                    abstractC0673m4.f298o = abstractC0673m4.mo5398b(c0649b4);
                    AbstractC0673m abstractC0673m5 = this.f291a;
                    if (abstractC0673m5.f298o == null) {
                        c0649b5 = abstractC0673m5.f293i;
                        abstractC0673m5.f298o = new C0685u(c0649b5.m5436b("Content-Type"));
                    }
                }
            }
            AbstractC0673m abstractC0673m6 = this.f291a;
            AbstractC0634a abstractC0634a = abstractC0673m6.f298o;
            abstractC0610a = abstractC0673m6.f295l;
            abstractC0634a.mo5367a(m5364a, abstractC0610a);
            this.f291a.mo5391m();
        }
    }
}
