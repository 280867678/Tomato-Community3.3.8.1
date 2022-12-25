package p007b.p012b.p013a;

import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import com.dianping.logan.LoganModel;
import com.tomatolive.library.utils.DateUtils;
import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

/* renamed from: b.b.a.h */
/* loaded from: classes2.dex */
public class C0589h extends Thread {

    /* renamed from: d */
    public long f159d;

    /* renamed from: e */
    public boolean f160e;

    /* renamed from: f */
    public File f161f;

    /* renamed from: g */
    public boolean f162g;

    /* renamed from: h */
    public long f163h;

    /* renamed from: i */
    public C0586e f164i;

    /* renamed from: j */
    public ConcurrentLinkedQueue<LoganModel> f165j;

    /* renamed from: k */
    public String f166k;

    /* renamed from: l */
    public String f167l;

    /* renamed from: m */
    public long f168m;

    /* renamed from: n */
    public long f169n;

    /* renamed from: o */
    public long f170o;

    /* renamed from: p */
    public String f171p;

    /* renamed from: q */
    public String f172q;

    /* renamed from: r */
    public int f173r;

    /* renamed from: a */
    public final Object f156a = new Object();

    /* renamed from: b */
    public final Object f157b = new Object();

    /* renamed from: c */
    public volatile boolean f158c = true;

    /* renamed from: s */
    public ConcurrentLinkedQueue<LoganModel> f174s = new ConcurrentLinkedQueue<>();

    public C0589h(ConcurrentLinkedQueue<LoganModel> concurrentLinkedQueue, String str, String str2, long j, long j2, long j3, String str3, String str4) {
        this.f165j = concurrentLinkedQueue;
        this.f166k = str;
        this.f167l = str2;
        this.f168m = j;
        this.f169n = j2;
        this.f170o = j3;
        this.f171p = str3;
        this.f172q = str4;
    }

    /* renamed from: a */
    public final void m5504a() {
        if (C0581a.f125c) {
            Log.d("LoganThread", "Logan flush start");
        }
        C0586e c0586e = this.f164i;
        if (c0586e != null) {
            c0586e.mo4182a();
        }
    }

    /* renamed from: a */
    public final void m5503a(long j) {
        String[] list;
        File file = new File(this.f167l);
        if (!file.isDirectory() || (list = file.list()) == null) {
            return;
        }
        for (String str : list) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    String[] split = str.split("\\.");
                    if (split.length > 0 && Long.valueOf(split[0]).longValue() <= j && split.length == 1) {
                        new File(this.f167l, str).delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    public final void m5502a(C0591j c0591j) {
        if (C0581a.f125c) {
            Log.d("LoganThread", "Logan send start");
        }
        if (TextUtils.isEmpty(this.f167l) || c0591j == null) {
            return;
        }
        c0591j.m5496a();
        throw null;
    }

    /* renamed from: a */
    public final void m5501a(C0593l c0593l) {
        if (C0581a.f125c) {
            Log.d("LoganThread", "Logan write start");
        }
        if (this.f161f == null) {
            this.f161f = new File(this.f167l);
        }
        if (!m5498c()) {
            long m5495a = C0592k.m5495a();
            m5503a(m5495a - this.f168m);
            this.f159d = m5495a;
            this.f164i.mo4179a(String.valueOf(this.f159d));
        }
        if (System.currentTimeMillis() - this.f163h > DateUtils.ONE_MINUTE_MILLIONS) {
            this.f162g = m5499b();
        }
        this.f163h = System.currentTimeMillis();
        if (!this.f162g) {
            return;
        }
        this.f164i.mo4181a(c0593l.f182f, c0593l.f177a, c0593l.f181e, c0593l.f180d, c0593l.f179c, c0593l.f178b);
    }

    /* renamed from: a */
    public final void m5500a(LoganModel loganModel) {
        if (loganModel == null || !loganModel.m4173a()) {
            return;
        }
        if (this.f164i == null) {
            this.f164i = C0586e.m5505b();
            this.f164i.mo4180a(new C0588g(this));
            this.f164i.mo4177a(this.f166k, this.f167l, (int) this.f169n, this.f171p, this.f172q);
            this.f164i.mo4176a(C0581a.f125c);
        }
        LoganModel.Action action = loganModel.f1235a;
        if (action == LoganModel.Action.WRITE) {
            m5501a(loganModel.f1236b);
        } else if (action != LoganModel.Action.SEND) {
            if (action != LoganModel.Action.FLUSH) {
                return;
            }
            m5504a();
        } else if (loganModel.f1237c.f175a == null) {
        } else {
            synchronized (this.f157b) {
                if (this.f173r == 10001) {
                    this.f174s.add(loganModel);
                } else {
                    m5502a(loganModel.f1237c);
                }
            }
        }
    }

    /* renamed from: b */
    public final boolean m5499b() {
        StatFs statFs;
        try {
            statFs = new StatFs(this.f167l);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize()) > this.f170o;
    }

    /* renamed from: c */
    public final boolean m5498c() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.f159d;
        return j < currentTimeMillis && j + DateUtils.ONE_DAY_MILLIONS > currentTimeMillis;
    }

    /* renamed from: d */
    public void m5497d() {
        if (!this.f160e) {
            synchronized (this.f156a) {
                this.f156a.notify();
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        super.run();
        while (this.f158c) {
            synchronized (this.f156a) {
                this.f160e = true;
                try {
                    LoganModel poll = this.f165j.poll();
                    if (poll == null) {
                        this.f160e = false;
                        this.f156a.wait();
                        this.f160e = true;
                    } else {
                        m5500a(poll);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.f160e = false;
                }
            }
        }
    }
}
