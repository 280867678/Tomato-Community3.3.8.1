package p007b.p012b.p013a;

import android.os.Looper;
import android.text.TextUtils;
import com.dianping.logan.LoganModel;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentLinkedQueue;

/* renamed from: b.b.a.d */
/* loaded from: classes2.dex */
public class C0585d {

    /* renamed from: a */
    public static C0585d f141a;

    /* renamed from: b */
    public ConcurrentLinkedQueue<LoganModel> f142b = new ConcurrentLinkedQueue<>();

    /* renamed from: c */
    public String f143c;

    /* renamed from: d */
    public String f144d;

    /* renamed from: e */
    public long f145e;

    /* renamed from: f */
    public long f146f;

    /* renamed from: g */
    public long f147g;

    /* renamed from: h */
    public long f148h;

    /* renamed from: i */
    public String f149i;

    /* renamed from: j */
    public String f150j;

    /* renamed from: k */
    public C0589h f151k;

    public C0585d(C0583c c0583c) {
        new SimpleDateFormat("yyyy-MM-dd");
        if (c0583c.m5528a()) {
            this.f144d = c0583c.f127b;
            this.f143c = c0583c.f126a;
            this.f145e = c0583c.f129d;
            this.f147g = c0583c.f131f;
            this.f146f = c0583c.f128c;
            this.f148h = c0583c.f130e;
            this.f149i = new String(c0583c.f132g);
            this.f150j = new String(c0583c.f133h);
            m5508a();
            return;
        }
        throw new NullPointerException("config's param is invalid");
    }

    /* renamed from: a */
    public static C0585d m5507a(C0583c c0583c) {
        if (f141a == null) {
            synchronized (C0585d.class) {
                if (f141a == null) {
                    f141a = new C0585d(c0583c);
                }
            }
        }
        return f141a;
    }

    /* renamed from: a */
    public final void m5508a() {
        if (this.f151k == null) {
            this.f151k = new C0589h(this.f142b, this.f143c, this.f144d, this.f145e, this.f146f, this.f147g, this.f149i, this.f150j);
            this.f151k.setName("logan-thread");
            this.f151k.start();
        }
    }

    /* renamed from: a */
    public void m5506a(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        LoganModel loganModel = new LoganModel();
        loganModel.f1235a = LoganModel.Action.WRITE;
        C0593l c0593l = new C0593l();
        String name = Thread.currentThread().getName();
        long id = Thread.currentThread().getId();
        boolean z = false;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            z = true;
        }
        c0593l.f177a = str;
        c0593l.f181e = System.currentTimeMillis();
        c0593l.f182f = i;
        c0593l.f178b = z;
        c0593l.f179c = id;
        c0593l.f180d = name;
        loganModel.f1236b = c0593l;
        if (this.f142b.size() >= this.f148h) {
            return;
        }
        this.f142b.add(loganModel);
        C0589h c0589h = this.f151k;
        if (c0589h == null) {
            return;
        }
        c0589h.m5497d();
    }
}
