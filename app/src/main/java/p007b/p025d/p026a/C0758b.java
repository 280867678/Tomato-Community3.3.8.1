package p007b.p025d.p026a;

import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: b.d.a.b */
/* loaded from: classes2.dex */
public class C0758b implements AbstractC0805q {

    /* renamed from: a */
    public String f491a;

    /* renamed from: b */
    public String f492b;

    /* renamed from: c */
    public int f493c;

    /* renamed from: e */
    public RunnableC0801m f495e;

    /* renamed from: g */
    public RunnableC0792g f497g;

    /* renamed from: d */
    public AtomicBoolean f494d = new AtomicBoolean(false);

    /* renamed from: h */
    public Map<String, List<String>> f498h = null;

    /* renamed from: f */
    public Object f496f = new Object();

    public C0758b(RunnableC0792g runnableC0792g) {
        this.f497g = runnableC0792g;
    }

    /* renamed from: a */
    public Map<String, List<String>> m5178a(long j, String str, int i, int i2, boolean z) {
        Map<String, List<String>> map;
        this.f494d.set(true);
        String str2 = this.f491a + "/" + str;
        this.f492b = str;
        try {
            URL url = new URL(str2);
            HashMap hashMap = new HashMap();
            String format = i2 != -1 ? String.format("bytes=%d-%d", Integer.valueOf(i), Integer.valueOf(i2)) : String.format("bytes=%d-", Integer.valueOf(i));
            hashMap.put("Range", format);
            hashMap.put("accept", "*/*");
            hashMap.put("Host", url.getHost());
            hashMap.put("Cache-Control", "no-cache");
            StringBuilder sb = new StringBuilder();
            sb.append("new Runnable!!!!!!!!!!!!!!!!!! range: ");
            sb.append(str2);
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(format);
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
            synchronized (this.f496f) {
                this.f495e = C0804p.m4938a().m4936a(1, str2, "GET", hashMap, (String) null, new C0755a(this, j));
            }
            if (!z) {
                return null;
            }
            synchronized (this) {
                try {
                    if (this.f498h == null) {
                        wait(4000L);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map = this.f498h;
                this.f498h = null;
            }
            return map;
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    public void m5180a() {
        synchronized (this.f496f) {
            if (this.f495e != null) {
                C0804p.m4938a().m4937a(1, this.f495e);
                this.f495e.m4939d();
                this.f495e = null;
            }
        }
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "CDN fetcher end currentRequest!");
    }

    /* renamed from: a */
    public void m5179a(long j) {
        synchronized (this.f496f) {
            if (this.f495e != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("CDN fetch done--->");
                sb.append(this.f492b);
                sb.append(" fetch done!");
                sb.append(this.f493c);
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
                this.f497g.m5014a(j);
                this.f494d.set(false);
            }
        }
    }

    /* renamed from: a */
    public void m5177a(long j, byte[] bArr, int i) {
        synchronized (this.f496f) {
            if (this.f495e != null) {
                this.f493c += i;
                this.f497g.m5013a(j, bArr, i);
            }
        }
    }

    /* renamed from: a */
    public void m5175a(String str) {
        this.f492b = str;
        this.f493c = 0;
        this.f498h = null;
        m5180a();
    }

    /* renamed from: a */
    public void m5174a(String str, String str2) {
        this.f491a = str;
    }

    /* renamed from: b */
    public void m5173b() {
        this.f493c = 0;
        this.f492b = null;
        this.f498h = null;
        m5180a();
    }
}
