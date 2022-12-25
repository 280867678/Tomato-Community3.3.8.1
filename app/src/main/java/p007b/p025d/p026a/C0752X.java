package p007b.p025d.p026a;

import android.os.Environment;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import p007b.p014c.p015a.AbstractC0712l;
import p007b.p014c.p015a.C0608K;
import p007b.p014c.p015a.C0691d;
import p007b.p014c.p015a.p018c.C0649b;
import p007b.p014c.p015a.p018c.p021c.AbstractC0670j;
import p007b.p014c.p015a.p018c.p021c.AbstractC0674n;
import p007b.p014c.p015a.p018c.p021c.AbstractC0683s;
import p007b.p014c.p015a.p018c.p021c.C0669i;
import p007b.p025d.p026a.p030d.C0775i;

/* renamed from: b.d.a.X */
/* loaded from: classes2.dex */
public class C0752X implements AbstractC0683s {

    /* renamed from: e */
    public String f472e;

    /* renamed from: f */
    public Pattern f473f;

    /* renamed from: k */
    public String f478k;

    /* renamed from: l */
    public int f479l = 0;

    /* renamed from: g */
    public RunnableC0792g f474g = new RunnableC0792g(this);

    /* renamed from: h */
    public AtomicBoolean f475h = new AtomicBoolean(true);

    /* renamed from: i */
    public String f476i = null;

    /* renamed from: j */
    public C0649b f477j = null;

    /* renamed from: b */
    public AbstractC0712l f470b = null;

    /* renamed from: c */
    public Object f471c = new Object();

    /* renamed from: a */
    public static /* synthetic */ void m5208a(Exception exc) {
    }

    /* renamed from: b */
    public static /* synthetic */ void m5201b(Exception exc) {
    }

    /* renamed from: a */
    public int m5214a() {
        return this.f474g.m4976g();
    }

    /* renamed from: a */
    public void m5213a(int i, String str, String str2, String str3, String str4, int i2, ArrayList<C0742M> arrayList, String str5, String str6, String str7, C0775i c0775i) {
        this.f472e = str2;
        this.f473f = Pattern.compile("^" + str2);
        this.f474g.m4966q();
        this.f474g.m4967p();
        this.f474g.m5015a(i, str3, str, str4, i2, arrayList, str5, str6, str7, c0775i);
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0683s
    /* renamed from: a */
    public void mo5212a(AbstractC0670j abstractC0670j, AbstractC0674n abstractC0674n) {
        String path = abstractC0670j.getPath();
        if (!m5207a(path)) {
            abstractC0674n.mo5388a(404);
        } else if (!this.f475h.get()) {
            abstractC0674n.mo5388a(500);
        } else {
            synchronized (this.f471c) {
                AbstractC0712l socket = abstractC0674n.getSocket();
                if (socket != this.f470b) {
                    this.f470b = socket;
                }
            }
            String substring = path.substring(this.f472e.length() - 2);
            if (!substring.toLowerCase().endsWith(".m3u8")) {
                String str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dump/" + substring;
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "new file request------->fileName = " + substring + " \nheaders = " + abstractC0670j.mo5394i().toString() + " \nthreadid = " + Thread.currentThread().getId());
                synchronized (this) {
                    this.f478k = substring;
                    this.f474g.m5003a(substring, abstractC0670j.mo5394i().m5441a());
                    if (this.f477j == null) {
                        try {
                            wait(5000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (this.f477j == null) {
                        this.f478k = null;
                        abstractC0674n.mo5388a(500);
                        return;
                    }
                    this.f478k = null;
                    m5211a(abstractC0674n, 206, this.f477j);
                    LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "send header done!!!!!!!!!!!!!!");
                    this.f477j = null;
                    abstractC0674n.getSocket().mo5289a(new C0751W(this));
                    ((C0691d) abstractC0674n.getSocket()).m5353b(4);
                    return;
                }
            }
            synchronized (this) {
                this.f478k = substring;
                StringBuilder sb = new StringBuilder();
                sb.append("new m3u8 request------->");
                sb.append(abstractC0670j.mo5394i().toString());
                sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                sb.append(Thread.currentThread().getId());
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("new m3u8 request--->");
                sb2.append(Thread.currentThread().getId());
                C0740K.m5251c(sb2.toString());
                this.f474g.m4985c(substring);
                if (this.f476i == null) {
                    try {
                        wait(5000L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                if (this.f476i == null) {
                    this.f478k = null;
                    abstractC0674n.mo5388a(500);
                    return;
                }
                this.f478k = null;
                int length = this.f476i.getBytes().length;
                C0649b c0649b = new C0649b();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                String format = String.format("bytes %d-%d/%d", 0, Integer.valueOf(length - 1), Integer.valueOf(length));
                c0649b.m5439a("Content-Length", String.valueOf(length));
                c0649b.m5439a("Content-Range", format);
                c0649b.m5439a("Accept-Ranges", "bytes");
                c0649b.m5439a("Date", simpleDateFormat.format(new Date()));
                m5211a(abstractC0674n, 206, c0649b);
                C0608K.m5478a(abstractC0674n.getSocket(), this.f476i.getBytes(), $$Lambda$S5l6VXdRtQEKVE8Ik36R258yhU.INSTANCE);
                this.f476i = null;
            }
        }
    }

    /* renamed from: a */
    public void m5211a(AbstractC0674n abstractC0674n, int i, C0649b c0649b) {
        String m5432e = c0649b.m5432e(String.format(Locale.ENGLISH, "%s %s %s", "HTTP/1.1", Integer.valueOf(i), C0669i.m5414a(206)));
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "------------------------------------------------------------------->New header: " + m5432e);
        C0608K.m5478a(abstractC0674n.getSocket(), m5432e.getBytes(), $$Lambda$1APiXYksQrEkQroVMPaQcvbKXQ.INSTANCE);
    }

    /* renamed from: a */
    public void m5206a(String str, C0649b c0649b) {
        synchronized (this) {
            if (str != null) {
                if (this.f478k != null && this.f478k.compareTo(str) == 0) {
                    this.f477j = c0649b;
                    LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "Wake up relayHeaders");
                    notify();
                }
            }
        }
    }

    /* renamed from: a */
    public void m5205a(String str, String str2) {
        synchronized (this) {
            if (str != null) {
                if (this.f478k != null && this.f478k.compareTo(str) == 0) {
                    this.f476i = str2;
                    LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "RelayM3U8File done!");
                    notify();
                }
            }
        }
    }

    /* renamed from: a */
    public void m5204a(boolean z) {
        this.f475h.set(z);
        this.f474g.m5000a(z);
    }

    /* renamed from: a */
    public boolean m5207a(String str) {
        Pattern pattern = this.f473f;
        return pattern != null && pattern.matcher(str).matches();
    }

    /* renamed from: b */
    public void m5203b() {
        this.f474g.m4971l();
    }

    /* renamed from: b */
    public boolean m5200b(String str) {
        synchronized (this) {
            if (str != null) {
                try {
                    if (this.f478k != null && this.f478k.compareTo(str) == 0) {
                        return true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return false;
        }
    }

    /* renamed from: c */
    public void m5199c() {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "proxy handler begin stop");
        synchronized (this.f471c) {
            this.f473f = null;
            this.f470b = null;
            this.f474g.m4965r();
        }
    }
}
