package p007b.p014c.p015a.p018c.p021c;

import android.annotation.TargetApi;
import android.util.Log;
import com.koushikdutta.async.AsyncServer;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import p007b.p014c.p015a.AbstractC0711k;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p016a.AbstractC0615d;
import p007b.p014c.p015a.p018c.C0649b;
import p007b.p014c.p015a.p018c.C0686d;
import p007b.p014c.p015a.p018c.p019a.AbstractC0634a;

@TargetApi(5)
/* renamed from: b.c.a.c.c.i */
/* loaded from: classes2.dex */
public class C0669i extends C0677q {

    /* renamed from: f */
    public static Hashtable<Integer, String> f286f = new Hashtable<>();

    /* renamed from: g */
    public ArrayList<AbstractC0711k> f287g = new ArrayList<>();

    /* renamed from: h */
    public AbstractC0615d f288h = new C0668h(this);

    /* renamed from: i */
    public AbstractC0610a f289i;

    static {
        f286f.put(200, "OK");
        f286f.put(202, "Accepted");
        f286f.put(206, "Partial Content");
        f286f.put(101, "Switching Protocols");
        f286f.put(301, "Moved Permanently");
        f286f.put(302, "Found");
        f286f.put(304, "Not Modified");
        f286f.put(Integer.valueOf((int) LotteryDialog.MAX_VALUE), "Bad Request");
        f286f.put(404, "Not Found");
        f286f.put(500, "Internal Server Error");
    }

    /* renamed from: a */
    public static String m5414a(int i) {
        String str = f286f.get(Integer.valueOf(i));
        return str == null ? "Unknown" : str;
    }

    /* renamed from: a */
    public AbstractC0634a m5412a(C0649b c0649b) {
        return new C0685u(c0649b.m5436b("Content-Type"));
    }

    /* renamed from: a */
    public AbstractC0711k m5407a(AsyncServer asyncServer, int i) {
        return asyncServer.m3889a((InetAddress) null, i, this.f288h);
    }

    /* renamed from: a */
    public void m5413a(AbstractC0610a abstractC0610a) {
        this.f289i = abstractC0610a;
    }

    /* renamed from: a */
    public void m5408a(AbstractC0683s abstractC0683s, AbstractC0670j abstractC0670j, AbstractC0674n abstractC0674n) {
        if (abstractC0683s != null) {
            try {
                abstractC0683s.mo5212a(abstractC0670j, abstractC0674n);
            } catch (Exception e) {
                Log.e("AsyncHttpServer", "request callback raised uncaught exception. Catching versus crashing process", e);
                abstractC0674n.mo5388a(500);
                abstractC0674n.end();
            }
        }
    }

    /* renamed from: a */
    public final void m5406a(Exception exc) {
        AbstractC0610a abstractC0610a = this.f289i;
        if (abstractC0610a != null) {
            abstractC0610a.mo5196a(exc);
        }
    }

    /* renamed from: a */
    public boolean m5410a(AbstractC0670j abstractC0670j, AbstractC0674n abstractC0674n) {
        return C0686d.m5362a(abstractC0674n.mo5382b(), abstractC0670j.mo5394i());
    }

    /* renamed from: a */
    public boolean m5409a(AbstractC0674n abstractC0674n) {
        return abstractC0674n.mo5389a() == 101;
    }

    /* renamed from: b */
    public AbstractC0711k m5405b(int i) {
        return m5407a(AsyncServer.m3883c(), i);
    }

    /* renamed from: b */
    public boolean m5404b(AbstractC0670j abstractC0670j, AbstractC0674n abstractC0674n) {
        return false;
    }

    /* renamed from: c */
    public void m5403c(AbstractC0670j abstractC0670j, AbstractC0674n abstractC0674n) {
    }
}
