package p007b.p025d.p026a.p030d;

import android.content.Context;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.crypt.SDKCryptUtil;
import com.zzz.ipfssdk.sdkReport.Dao.ReportItemJsonWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import p007b.p008a.p009a.p010a.C0570a;
import p007b.p008a.p009a.p011b.C0576f;
import p007b.p008a.p009a.p011b.C0577g;
import p007b.p025d.p026a.C0738I;
import p007b.p025d.p026a.p030d.p031a.C0767a;

/* renamed from: b.d.a.d.l */
/* loaded from: classes2.dex */
public class RunnableC0778l implements Runnable {

    /* renamed from: a */
    public static RunnableC0778l f593a;

    /* renamed from: b */
    public String f594b;

    /* renamed from: c */
    public C0577g<ReportItemJsonWrapper> f595c;

    /* renamed from: h */
    public ArrayList<String> f600h;

    /* renamed from: i */
    public long f601i = 0;

    /* renamed from: g */
    public volatile int f599g = 0;

    /* renamed from: f */
    public AtomicBoolean f598f = new AtomicBoolean(false);

    /* renamed from: d */
    public ConcurrentHashMap<String, ConcurrentLinkedQueue<ReportItemJsonWrapper>> f596d = new ConcurrentHashMap<>();

    /* renamed from: e */
    public LinkedBlockingDeque<Runnable> f597e = new LinkedBlockingDeque<>();

    /* renamed from: a */
    public static RunnableC0778l m5061a() {
        if (f593a == null) {
            synchronized (RunnableC0778l.class) {
                f593a = new RunnableC0778l();
            }
        }
        return f593a;
    }

    /* renamed from: a */
    public final String m5060a(int i) {
        StringBuilder sb;
        String str;
        if (i == 100) {
            sb = new StringBuilder();
            sb.append(this.f594b);
            str = "flowreport";
        } else if (i == 101) {
            sb = new StringBuilder();
            sb.append(this.f594b);
            str = "prereqreport";
        } else if (i != 102) {
            return "";
        } else {
            sb = new StringBuilder();
            sb.append(this.f594b);
            str = "p2pconnreport";
        }
        sb.append(str);
        return sb.toString();
    }

    /* renamed from: a */
    public void m5059a(Context context) {
        C0570a.m5580a().m5579a(context);
        C0570a.m5580a().m5578a(true);
        C0767a.m5147b().m5573a(new C0576f("ormlite_default.db", 4));
        this.f595c = C0767a.m5147b().m5574a(C0738I.m5275a().m5258d(), ReportItemJsonWrapper.class);
        for (ReportItemJsonWrapper reportItemJsonWrapper : this.f595c.m5542a()) {
            m5056a(reportItemJsonWrapper);
        }
        new Thread(this).start();
    }

    /* renamed from: a */
    public void m5056a(ReportItemJsonWrapper reportItemJsonWrapper) {
        String playId = reportItemJsonWrapper.getPlayId();
        if (playId == null || playId.length() == 0) {
            return;
        }
        ConcurrentLinkedQueue<ReportItemJsonWrapper> concurrentLinkedQueue = this.f596d.get(playId);
        if (concurrentLinkedQueue == null) {
            concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
            this.f596d.put(playId, concurrentLinkedQueue);
        }
        concurrentLinkedQueue.add(reportItemJsonWrapper);
    }

    /* renamed from: a */
    public void m5055a(ReportItemJsonWrapper reportItemJsonWrapper, String str) {
        String tempDecode = SDKCryptUtil.tempDecode(reportItemJsonWrapper.getTempEncodeData());
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "从数据库解密出原始json:" + tempDecode);
        m5054a(new RunnableC0777k(this, reportItemJsonWrapper, SDKCryptUtil.nativeEncode(str, tempDecode)));
    }

    /* renamed from: a */
    public void m5054a(Runnable runnable) {
        this.f597e.push(runnable);
    }

    /* renamed from: a */
    public void m5053a(String str) {
        this.f594b = str;
        C0769c.m5146a().m5141c(str);
    }

    /* renamed from: a */
    public void m5052a(String str, int i, String str2) {
        String tempEncode = SDKCryptUtil.tempEncode(str2);
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.f601i;
        this.f601i = j + 1;
        ReportItemJsonWrapper reportItemJsonWrapper = new ReportItemJsonWrapper(str, i, currentTimeMillis + j, tempEncode);
        this.f595c.m5541a((C0577g<ReportItemJsonWrapper>) reportItemJsonWrapper);
        ConcurrentLinkedQueue<ReportItemJsonWrapper> concurrentLinkedQueue = this.f596d.get(str);
        if (concurrentLinkedQueue == null) {
            concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
            this.f596d.put(str, concurrentLinkedQueue);
        }
        concurrentLinkedQueue.add(reportItemJsonWrapper);
    }

    /* renamed from: a */
    public void m5051a(String str, int i, String str2, String str3) {
        String tempEncode = SDKCryptUtil.tempEncode(str2);
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.f601i;
        this.f601i = j + 1;
        ReportItemJsonWrapper reportItemJsonWrapper = new ReportItemJsonWrapper(str, i, currentTimeMillis + j, tempEncode);
        if (this.f595c.m5541a((C0577g<ReportItemJsonWrapper>) reportItemJsonWrapper)) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "UploadReportItem save report successfully!");
        }
        m5054a(new RunnableC0776j(this, reportItemJsonWrapper, SDKCryptUtil.nativeEncode(str3, str2)));
    }

    /* renamed from: a */
    public synchronized void m5050a(ArrayList<String> arrayList) {
        this.f600h = arrayList;
    }

    /* renamed from: b */
    public synchronized ArrayList<String> m5049b() {
        return this.f600h;
    }

    /* renamed from: b */
    public synchronized void m5048b(int i) {
        this.f599g = i;
    }

    /* renamed from: c */
    public synchronized int m5047c() {
        return this.f599g;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0043, code lost:
        if (r1.isEmpty() != false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0045, code lost:
        r2 = r1.poll();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004b, code lost:
        if (r2 == null) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004d, code lost:
        m5055a(r2, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0051, code lost:
        r0.remove();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0054, code lost:
        return;
     */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m5046d() {
        String str = this.f594b;
        if (str == null || str.length() == 0) {
            return;
        }
        Iterator<Map.Entry<String, ConcurrentLinkedQueue<ReportItemJsonWrapper>>> it2 = this.f596d.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, ConcurrentLinkedQueue<ReportItemJsonWrapper>> next = it2.next();
            String key = next.getKey();
            ConcurrentLinkedQueue<ReportItemJsonWrapper> value = next.getValue();
            String m5144a = C0769c.m5146a().m5144a(key);
            if (m5144a == null) {
                C0769c.m5146a().m5142b(key);
            }
        }
    }

    /* renamed from: e */
    public void m5045e() {
        this.f598f.set(true);
    }

    @Override // java.lang.Runnable
    public void run() {
        while (!this.f598f.get()) {
            m5046d();
            try {
                Runnable poll = this.f597e.poll(1L, TimeUnit.SECONDS);
                if (poll != null) {
                    poll.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
