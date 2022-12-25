package p007b.p025d.p026a;

import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.P2PUdpUtil;
import com.zzz.ipfssdk.callback.exception.CodeState;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p007b.p014c.p015a.p018c.C0649b;
import p007b.p025d.p026a.p027a.C0756a;
import p007b.p025d.p026a.p030d.C0770d;
import p007b.p025d.p026a.p030d.C0772f;
import p007b.p025d.p026a.p030d.C0774h;
import p007b.p025d.p026a.p030d.C0775i;

/* renamed from: b.d.a.g */
/* loaded from: classes2.dex */
public class RunnableC0792g implements Runnable {

    /* renamed from: I */
    public volatile long f633I;

    /* renamed from: J */
    public long f634J;

    /* renamed from: K */
    public long f635K;

    /* renamed from: L */
    public long f636L;

    /* renamed from: M */
    public long f637M;

    /* renamed from: N */
    public long f638N;

    /* renamed from: O */
    public long f639O;

    /* renamed from: U */
    public C0752X f645U;

    /* renamed from: f */
    public int f652f;

    /* renamed from: g */
    public String f653g;

    /* renamed from: h */
    public String f654h;

    /* renamed from: i */
    public String f655i;

    /* renamed from: j */
    public ArrayList<String> f656j;

    /* renamed from: k */
    public ArrayList<C0742M> f657k;

    /* renamed from: l */
    public C0742M f658l;

    /* renamed from: m */
    public String f659m;

    /* renamed from: n */
    public int f660n;

    /* renamed from: p */
    public String f661p;

    /* renamed from: q */
    public String f662q;

    /* renamed from: r */
    public String f663r;

    /* renamed from: s */
    public int f664s;

    /* renamed from: t */
    public C0764ca f665t;

    /* renamed from: e */
    public BlockingQueue<C0798j> f651e = new LinkedBlockingDeque();

    /* renamed from: b */
    public AtomicBoolean f648b = new AtomicBoolean(false);

    /* renamed from: a */
    public AtomicBoolean f647a = new AtomicBoolean(false);

    /* renamed from: c */
    public AtomicBoolean f649c = new AtomicBoolean(false);

    /* renamed from: d */
    public boolean f650d = true;

    /* renamed from: y */
    public C0758b f670y = new C0758b(this);

    /* renamed from: x */
    public C0744O f669x = new C0744O(this);

    /* renamed from: u */
    public C0750V f666u = new C0750V();

    /* renamed from: v */
    public C0750V f667v = new C0750V();

    /* renamed from: w */
    public C0750V f668w = new C0750V();

    /* renamed from: z */
    public long f671z = -1;

    /* renamed from: A */
    public long f626A = -1;

    /* renamed from: B */
    public C0745P f627B = new C0745P();

    /* renamed from: C */
    public C0761c f628C = new C0761c();

    /* renamed from: D */
    public C0793ga f629D = new C0793ga(5);

    /* renamed from: E */
    public C0793ga f630E = new C0793ga(5);

    /* renamed from: F */
    public C0793ga f631F = new C0793ga(50);

    /* renamed from: H */
    public C0741L f632H = null;

    /* renamed from: Q */
    public long f641Q = -1;

    /* renamed from: P */
    public long f640P = -1;

    /* renamed from: R */
    public long f642R = -1;

    /* renamed from: S */
    public long f643S = -1;

    /* renamed from: T */
    public byte[] f644T = new byte[65536];

    /* renamed from: V */
    public C0775i f646V = null;

    public RunnableC0792g(C0752X c0752x) {
        this.f645U = c0752x;
    }

    /* renamed from: a */
    public int m4998a(byte[] bArr, int i) {
        synchronized (this) {
            if (this.f647a.get()) {
                return -1;
            }
            if (this.f665t != null && this.f665t.f513a < this.f665t.f514b + 1) {
                int i2 = (this.f665t.f514b - this.f665t.f513a) + 1;
                if (i > i2) {
                    i = i2;
                }
                int m5222a = this.f666u.m5222a(bArr, i);
                this.f631F.m4961a(m5222a);
                this.f665t.f513a += m5222a;
                this.f633I += m5222a;
                if (this.f634J == -1) {
                    this.f634J = new Date().getTime();
                }
                if (this.f635K == 0 || new Date().getTime() - this.f635K > 180000) {
                    this.f635K = new Date().getTime();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Read Statistics:");
                    sb.append(m5222a);
                    sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                    sb.append(this.f635K);
                    C0740K.m5251c(sb.toString());
                }
                return m5222a;
            }
            return -1;
        }
    }

    /* renamed from: a */
    public int m4997a(byte[] bArr, int i, int i2) {
        int m5219b;
        String str;
        if (i2 == 0) {
            m5219b = this.f667v.m5219b(bArr, i);
            if (!this.f667v.m5224a()) {
                return m5219b;
            }
            str = "CDN buf is full!!!!!!!!!!!";
        } else if (i2 != 1) {
            return 0;
        } else {
            m5219b = this.f668w.m5219b(bArr, i);
            if (!this.f668w.m5224a()) {
                return m5219b;
            }
            str = "P2P buf is full!!!!!!!!!!!";
        }
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, str);
        m5017a(i2);
        return m5219b;
    }

    /* renamed from: a */
    public C0649b m5016a(int i, int i2) {
        C0741L c0741l;
        int i3 = this.f652f;
        if (i3 != 0) {
            if (i3 == 1 && (c0741l = this.f632H) != null) {
                return c0741l.m5248a(this.f662q, i, i2);
            }
            return null;
        }
        C0649b c0649b = new C0649b();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.CHINA);
        if (i2 == -1) {
            i2 = this.f660n - 1;
        }
        String format = String.format("bytes %d-%d/%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.f660n));
        c0649b.m5439a("Content-Length", String.valueOf((i2 - i) + 1));
        c0649b.m5439a("Content-Range", format);
        c0649b.m5439a("Accept-Ranges", "bytes");
        c0649b.m5439a("Date", simpleDateFormat.format(new Date()));
        return c0649b;
    }

    /* renamed from: a */
    public C0649b m5004a(String str, int i, int i2, boolean z) {
        String str2;
        if (!this.f650d || (str2 = this.f655i) == null || str2.length() == 0) {
            return null;
        }
        this.f646V.m5067f().m5134c(0);
        m4982d(str);
        this.f626A = C0757aa.m5181a(0L, Long.MAX_VALUE);
        Map<String, List<String>> m5178a = this.f670y.m5178a(this.f626A, str, i, i2, z);
        if (m5178a != null) {
            return new C0649b(m5178a);
        }
        return null;
    }

    /* renamed from: a */
    public void m5018a() {
        String str;
        this.f641Q = new Date().getTime();
        if (this.f632H == null || this.f658l == null || this.f662q == null) {
            return;
        }
        int i = C0779da.f602a;
        int i2 = i / 2;
        int i3 = i / 10;
        int m5217d = this.f668w.m5217d();
        int m5217d2 = this.f667v.m5217d();
        int m5217d3 = this.f666u.m5217d();
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "CheckDataBuffer--->" + m5217d + ConstantUtils.PLACEHOLDER_STR_ONE + m5217d2 + ConstantUtils.PLACEHOLDER_STR_ONE + m5217d3 + ConstantUtils.PLACEHOLDER_STR_ONE + this.f666u.m5218c() + ConstantUtils.PLACEHOLDER_STR_ONE + this.f668w.m5221b() + ConstantUtils.PLACEHOLDER_STR_ONE + this.f667v.m5221b() + ConstantUtils.PLACEHOLDER_STR_ONE + this.f627B.f446a + ConstantUtils.PLACEHOLDER_STR_ONE + this.f628C.f505a);
        if (this.f666u.m5218c() != this.f668w.m5221b() && this.f666u.m5218c() != this.f667v.m5221b()) {
            LogUtil.m120e(LogUtil.TAG_IPFS_SDK, "=====================================================>ERROR!!!!!!!!!!!!!!");
            C0756a.m5182a().onException(new CodeState(8000, CodeState.MSGS.MSG_PACKAGE_ERROR, null));
        }
        if (this.f664s > 0 && this.f668w.m5218c() >= this.f664s) {
            m4964s();
            m4963t();
            this.f627B.m5226a(6);
            this.f628C.m5168a(0);
        } else if (m5217d > i2 && this.f628C.f505a != 0) {
            if (this.f628C.f505a == 1) {
                m4964s();
            }
            this.f628C.m5168a(0);
            this.f627B.m5226a(2);
        } else {
            if (m5217d3 < i2) {
                if (this.f628C.f505a == 2) {
                    m4995b(0);
                }
                if (this.f627B.f446a == 3) {
                    m4995b(1);
                }
            }
            if (m5217d3 >= i3 || this.f627B.f446a != 2 || this.f628C.f505a == 1 || (str = this.f655i) == null || str.length() == 0) {
                return;
            }
            double m4962a = this.f629D.m4962a();
            long time = new Date().getTime();
            long j = this.f634J;
            double d = (((this.f633I * 1.0d) / 1024.0d) / (time == j ? 0L : time - j)) * 1000.0d;
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, String.format("p2pLength under lowWater, check Speed: %.2fKB/s %.2fKB/s", Double.valueOf(m4962a), Double.valueOf(d)));
            if (m4962a >= d) {
                return;
            }
            int m5218c = (this.f668w.m5221b() == this.f666u.m5218c() ? this.f668w : this.f667v).m5218c();
            C0797ia m5245b = this.f632H.m5245b(this.f663r, m5218c);
            if (m5245b == null) {
                return;
            }
            int m4948b = m5218c - m5245b.m4948b();
            int m4947c = m5245b.m4947c() - 1;
            this.f667v.m5223a(m5218c);
            m5004a(m5245b.m4945e(), m4948b, m4947c, false);
            C0761c c0761c = this.f628C;
            c0761c.f506b = m4948b;
            c0761c.f507c = m4947c;
            c0761c.f508d = m5245b;
            this.f627B.m5226a(1);
            this.f628C.m5168a(1);
            this.f646V.m5068e();
        }
    }

    /* renamed from: a */
    public void m5017a(int i) {
        if (i == 0) {
            m4964s();
            this.f628C.m5168a(2);
        } else if (i != 1) {
        } else {
            m4969n();
            this.f627B.m5226a(3);
        }
    }

    /* renamed from: a */
    public void m5015a(int i, String str, String str2, String str3, int i2, ArrayList<C0742M> arrayList, String str4, String str5, String str6, C0775i c0775i) {
        m4991b(new C0798j(2, new C0794h(i, str, str2, str3, i2, arrayList, str4, str5, str6, c0775i)));
    }

    /* renamed from: a */
    public void m5014a(long j) {
        m4991b(new C0798j(14, Long.valueOf(j)));
    }

    /* renamed from: a */
    public void m5013a(long j, byte[] bArr, int i) {
        m4991b(new C0798j(12, new C0796i(j, bArr, i)));
    }

    /* renamed from: a */
    public void m5011a(C0794h c0794h) {
        this.f652f = c0794h.f675a;
        this.f655i = c0794h.f676b;
        this.f659m = c0794h.f678d;
        this.f660n = c0794h.f679e;
        String str = c0794h.f682h;
        this.f661p = c0794h.f683i;
        this.f654h = c0794h.f677c;
        this.f653g = c0794h.f681g;
        this.f646V = c0794h.f684j;
        this.f657k = c0794h.f680f;
        this.f656j = new ArrayList<>();
        for (int i = 0; i < this.f657k.size(); i++) {
            C0742M c0742m = this.f657k.get(i);
            this.f656j.add(c0742m.f428g.substring(c0742m.f428g.lastIndexOf("_")));
        }
        this.f670y.m5174a(this.f655i, c0794h.f682h);
        this.f669x.m5236a(c0794h.f677c, this.f652f, this.f659m, c0794h.f681g, c0794h.f682h);
        if (this.f632H == null) {
            m4981e();
        }
        m4970m();
        this.f646V.m5067f().m5127f(System.currentTimeMillis());
    }

    /* renamed from: a */
    public void m5010a(C0796i c0796i) {
        String str;
        this.f646V.m5079a(c0796i.f691c);
        if (this.f626A != c0796i.f689a) {
            str = "Error cdn fetch ID:" + this.f626A + ConstantUtils.PLACEHOLDER_STR_ONE + c0796i.f689a;
        } else if (this.f628C.f505a == 1) {
            int m4997a = m4997a(c0796i.f690b, c0796i.f691c, 0);
            this.f628C.f506b += c0796i.f691c;
            this.f638N += m4997a;
            Date date = new Date();
            if (this.f639O == 0 || date.getTime() - this.f639O > 180000) {
                this.f639O = date.getTime();
                C0740K.m5251c("CDN Fetch total size:" + this.f638N);
            }
            this.f630E.m4961a(m4997a);
            synchronized (this) {
                m4978f();
                m5018a();
                m4984d();
            }
            return;
        } else {
            str = "CDN not Running, Return directly!--------->" + this.f628C.f505a;
        }
        LogUtil.m119i(LogUtil.TAG_IPFS_SDK, str);
    }

    /* renamed from: a */
    public void m5009a(C0798j c0798j) {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "handleFetcherCmd-------------->" + c0798j.f697a + ConstantUtils.PLACEHOLDER_STR_ONE + c0798j.f698b + ConstantUtils.PLACEHOLDER_STR_ONE + this.f627B.f446a + ConstantUtils.PLACEHOLDER_STR_ONE + this.f628C.f505a);
        if (c0798j.f698b) {
            return;
        }
        switch (c0798j.f697a) {
            case 0:
                m4973j();
                return;
            case 1:
                m4974i();
                return;
            case 2:
                m5011a((C0794h) c0798j.f699c);
                return;
            case 3:
                m4988b((ArrayList) c0798j.f699c);
                return;
            case 4:
                m4990b((String) c0798j.f699c);
                return;
            case 5:
                m5007a((C0800l) c0798j.f699c);
                return;
            case 6:
                m4972k();
                return;
            case 7:
            case 8:
            case 9:
            default:
                return;
            case 10:
                m5005a((String) c0798j.f699c);
                return;
            case 11:
                m5008a((C0799k) c0798j.f699c);
                return;
            case 12:
                m5010a((C0796i) c0798j.f699c);
                return;
            case 13:
                m4992b((C0796i) c0798j.f699c);
                return;
            case 14:
                m4994b(((Long) c0798j.f699c).longValue());
                return;
            case 15:
                m4986c(((Long) c0798j.f699c).longValue());
                return;
            case 16:
                m4975h();
                return;
            case 17:
                m4983d(((Long) c0798j.f699c).longValue());
                return;
            case 18:
                m5006a((Boolean) c0798j.f699c);
                return;
        }
    }

    /* renamed from: a */
    public void m5008a(C0799k c0799k) {
        C0745P c0745p;
        synchronized (this) {
            if (!this.f650d) {
                return;
            }
            if (!this.f645U.m5200b(c0799k.f700a)) {
                return;
            }
            if (this.f652f == 1 && this.f632H == null) {
                return;
            }
            this.f665t = null;
            m5001a(c0799k.f701b);
            if (this.f665t == null) {
                this.f645U.m5206a((String) null, (C0649b) null);
                return;
            }
            this.f662q = c0799k.f700a;
            this.f663r = this.f632H.m5244c(this.f662q).m4946d();
            this.f664s = this.f632H.m5246b(this.f663r).m4953b();
            StringBuilder sb = new StringBuilder();
            sb.append("resourceName = ");
            sb.append(this.f662q);
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(this.f665t.toString());
            LogUtil.m119i(LogUtil.TAG_IPFS_SDK, sb.toString());
            C0649b m5016a = m5016a(this.f665t.f513a, this.f665t.f514b);
            if (m5016a == null) {
                this.f645U.m5206a(c0799k.f700a, (C0649b) null);
            }
            this.f645U.m5206a(c0799k.f700a, m5016a);
            String m5436b = m5016a.m5436b("Content-Range");
            int parseInt = Integer.parseInt(m5436b.substring(m5436b.indexOf(47) + 1));
            if (this.f665t.f514b == -1) {
                this.f665t.f514b = parseInt - 1;
            }
            C0797ia m5244c = this.f632H.m5244c(this.f662q);
            if (m5244c == null) {
                return;
            }
            int m4948b = m5244c.m4948b() + this.f665t.f513a;
            int m4948b2 = this.f665t.f514b == -1 ? (m5244c.m4948b() + m5244c.m4947c()) - 1 : this.f665t.f514b + m5244c.m4948b();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("request Info: ");
            sb2.append(parseInt);
            sb2.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb2.append(m4948b);
            sb2.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb2.append(m4948b2);
            sb2.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb2.append(this.f666u.m5221b());
            sb2.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb2.append(this.f666u.m5218c());
            LogUtil.m119i(LogUtil.TAG_IPFS_SDK, sb2.toString());
            m5018a();
            int i = 2;
            if (this.f666u.m5217d() == 0 && this.f666u.m5221b() == m4948b && (this.f627B.f446a == 2 || this.f628C.f505a == 1)) {
                return;
            }
            m4978f();
            if (this.f666u.m5220b(m4948b)) {
                return;
            }
            this.f666u.m5223a(m4948b);
            m4978f();
            if (this.f666u.m5220b(m4948b)) {
                return;
            }
            this.f666u.m5223a(m4948b);
            this.f667v.m5223a(m4948b);
            this.f668w.m5223a(m4948b);
            C0797ia m5244c2 = this.f632H.m5244c(this.f662q);
            if (m5244c2 == null) {
                return;
            }
            int m4948b3 = m4948b - m5244c2.m4948b();
            int m4948b4 = m4948b2 - m5244c2.m4948b();
            if (this.f655i != null && this.f655i.length() != 0) {
                if (this.f627B.f446a == 0) {
                    m5004a(this.f662q, m4948b3, m4948b4, false);
                    this.f628C.f506b = m4948b3;
                    this.f628C.f507c = m4948b4;
                    this.f628C.f508d = m5244c2;
                    this.f628C.m5168a(1);
                    if (this.f658l != null) {
                        m4989b(this.f662q, m4948b, m4948b2, false);
                        c0745p = this.f627B;
                        i = 1;
                        c0745p.m5226a(i);
                    }
                    return;
                }
                if (this.f627B.f446a != 1) {
                    if (this.f627B.f446a != 4 && this.f627B.f446a != 5) {
                        m4989b(this.f662q, m4948b, m4948b2, false);
                        c0745p = this.f627B;
                        c0745p.m5226a(i);
                    }
                    if (this.f628C.f505a == 1) {
                        m5004a(this.f662q, m4948b3, m4948b4, false);
                        this.f628C.f506b = m4948b3;
                        this.f628C.f507c = m4948b4;
                        this.f628C.f508d = m5244c2;
                        this.f628C.m5168a(1);
                    }
                } else if (this.f628C.f505a == 1) {
                    m5004a(this.f662q, m4948b3, m4948b4, false);
                    this.f628C.f506b = m4948b3;
                    this.f628C.f507c = m4948b4;
                    this.f628C.f508d = m5244c2;
                    this.f628C.m5168a(1);
                    m4989b(this.f662q, m4948b, m4948b2, false);
                }
                return;
            }
            m4989b(this.f662q, m4948b, m4948b2, false);
            this.f627B.m5226a(2);
        }
    }

    /* renamed from: a */
    public void m5007a(C0800l c0800l) {
        if (!c0800l.f702a) {
            C0770d m5067f = this.f646V.m5067f();
            if (m5067f != null) {
                m5067f.m5127f(System.currentTimeMillis());
                m5067f.m5128f(0);
                m5067f.m5132d(1);
                m5067f.m5130e(4);
                this.f646V.m5063j();
            }
            C0774h m5066g = this.f646V.m5066g();
            if (m5066g == null) {
                return;
            }
            m5066g.m5092a(2, System.currentTimeMillis());
            m5066g.m5093a(2, 1);
            this.f646V.m5062k();
            return;
        }
        C0774h m5066g2 = this.f646V.m5066g();
        if (m5066g2 != null) {
            m5066g2.m5092a(2, System.currentTimeMillis());
            m5066g2.m5093a(2, 0);
        }
        if (this.f652f != 1) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(c0800l.f703b.toString());
            String string = jSONObject.getString(IjkMediaMeta.IJKM_KEY_M3U8);
            String string2 = jSONObject.getString("m3u8Content");
            C0741L c0741l = new C0741L(string, string2, string2);
            JSONArray jSONArray = jSONObject.getJSONArray("files");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                String string3 = jSONObject2.getString("subm3u8");
                C0795ha c0795ha = new C0795ha(string3, jSONObject2.getString("subm3u8Content"));
                JSONArray jSONArray2 = jSONObject2.getJSONArray("Fragments");
                C0797ia c0797ia = null;
                int i2 = 0;
                while (i2 < jSONArray2.length()) {
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                    C0797ia c0797ia2 = new C0797ia(i2, jSONObject3.getString("name"), string3, jSONObject3.getInt("offset"), jSONObject3.getInt("size"));
                    c0795ha.m4955a(c0797ia2);
                    i2++;
                    c0797ia = c0797ia2;
                }
                c0795ha.m4952b(c0797ia.m4948b() + c0797ia.m4947c());
                c0741l.m5247a(string3, c0795ha);
            }
            synchronized (this) {
                this.f632H = c0741l;
            }
        } catch (JSONException e) {
            LogUtil.m119i(LogUtil.TAG_IPFS_SDK, "------------------------------->Wrong HlsList!!!!!!!");
            LogUtil.m119i(LogUtil.TAG_IPFS_SDK, c0800l.f703b.toString());
            C0740K.m5251c("Wrong hlslist!!!!!!!");
            e.printStackTrace();
        }
        C0740K.m5251c("Get Hlslist Done!");
        LogUtil.m119i(LogUtil.TAG_IPFS_SDK, "Get Hlslist Done!!!!!!!!");
    }

    /* renamed from: a */
    public void m5006a(Boolean bool) {
        this.f650d = bool.booleanValue();
        if (!this.f650d) {
            if (this.f627B.f446a == 2 || this.f627B.f446a == 1) {
                m4963t();
                this.f627B.m5226a(0);
            }
            if (this.f628C.f505a == 1) {
                m4964s();
                this.f628C.m5168a(0);
            }
            this.f646V.m5063j();
            this.f646V.m5062k();
        }
    }

    /* renamed from: a */
    public void m5005a(String str) {
        if (!this.f650d) {
            this.f645U.m5205a(str, "");
        }
        C0741L c0741l = this.f632H;
        if (c0741l != null) {
            this.f645U.m5205a(str, c0741l.m5250a(str));
            this.f646V.m5067f().m5127f(System.currentTimeMillis());
            return;
        }
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "HlsList is null");
        m4985c(str);
    }

    /* renamed from: a */
    public void m5003a(String str, Map<String, List<String>> map) {
        m4991b(new C0798j(11, new C0799k(str, map)));
    }

    /* renamed from: a */
    public void m5002a(ArrayList<C0742M> arrayList) {
        m4991b(new C0798j(3, arrayList));
    }

    /* renamed from: a */
    public void m5001a(Map<String, List<String>> map) {
        List<String> list = map.get("range");
        List<String> list2 = map.get("Range");
        if (list != null) {
            list2 = list;
        }
        if (list2 == null || list2.size() == 0) {
            this.f665t = new C0764ca(0, -1);
            return;
        }
        String[] split = list2.get(0).substring(6).trim().split(",");
        C0764ca c0764ca = null;
        if (split.length > 0) {
            String[] split2 = split[0].split("-");
            c0764ca = new C0764ca();
            if (split2.length == 2) {
                c0764ca.f513a = Integer.parseInt(split2[0]);
                c0764ca.f514b = Integer.parseInt(split2[1]);
            } else {
                c0764ca.f513a = Integer.parseInt(split2[0]);
                c0764ca.f514b = -1;
            }
        }
        this.f665t = c0764ca;
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "currentRdRange = " + this.f665t);
    }

    /* renamed from: a */
    public void m5000a(boolean z) {
        m4991b(new C0798j(18, Boolean.valueOf(z)));
    }

    /* renamed from: a */
    public void m4999a(boolean z, StringBuffer stringBuffer) {
        m4991b(new C0798j(5, new C0800l(z, stringBuffer)));
    }

    /* renamed from: b */
    public void m4996b() {
        long time = new Date().getTime();
        ArrayList<C0742M> arrayList = this.f657k;
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        if (this.f658l == null && this.f627B.f451f >= 20) {
            C0774h m5066g = this.f646V.m5066g();
            if (m5066g != null) {
                m5066g.m5092a(1, System.currentTimeMillis());
                m5066g.m5093a(1, 1);
                this.f646V.m5062k();
            }
            m4968o();
            this.f627B.f451f = 0L;
        }
        if (this.f658l != null || time - this.f627B.f452g <= 2000) {
            return;
        }
        m4970m();
        this.f627B.f451f++;
    }

    /* renamed from: b */
    public void m4995b(int i) {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "Resume!!!!!!!!!!!----->" + i);
        if (i == 0) {
            int m5218c = this.f667v.m5218c();
            C0761c c0761c = this.f628C;
            if (c0761c.f506b < c0761c.f507c) {
                C0797ia c0797ia = c0761c.f508d;
                int m4948b = m5218c - c0797ia.m4948b();
                m5004a(this.f662q, m4948b, m4948b, false);
                C0761c c0761c2 = this.f628C;
                c0761c2.f506b = m4948b;
                c0761c2.f507c = c0797ia.m4947c() - 1;
                c0761c2.f508d = c0797ia;
            } else {
                C0797ia m5249a = this.f632H.m5249a(this.f663r, c0761c.f508d.m4949a() + 1);
                m5004a(m5249a.m4945e(), 0, m5249a.m4947c() - 1, false);
                C0761c c0761c3 = this.f628C;
                c0761c3.f508d = m5249a;
                c0761c3.f506b = 0;
                c0761c3.f507c = m5249a.m4947c() - 1;
            }
            this.f628C.m5168a(1);
        }
        if (i == 1) {
            m4989b(this.f662q, this.f668w.m5218c(), this.f664s, false);
            this.f627B.m5226a(2);
        }
    }

    /* renamed from: b */
    public void m4994b(long j) {
        synchronized (this) {
            if (j != this.f626A) {
                return;
            }
            if (this.f628C.f506b < this.f628C.f507c + 1) {
                m5004a(this.f662q, this.f628C.f506b, this.f628C.f507c, false);
            }
            C0797ia c0797ia = this.f628C.f508d;
            if (this.f628C.f506b >= this.f628C.f507c + 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("This is cdn, Get Next TS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!------>");
                sb.append(this.f628C.f508d.m4945e());
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
                C0797ia m5245b = this.f667v.m5218c() < this.f668w.m5218c() ? this.f632H.m5245b(this.f663r, this.f668w.m5218c()) : this.f632H.m5249a(this.f663r, c0797ia.m4949a() + 1);
                if (m5245b != null) {
                    m5004a(m5245b.m4945e(), 0, m5245b.m4947c() - 1, false);
                    this.f628C.f508d = m5245b;
                    this.f628C.f506b = 0;
                    this.f628C.f507c = m5245b.m4947c() - 1;
                }
                if (m5245b == null && c0797ia.m4949a() + 1 == this.f632H.m5246b(this.f663r).m4958a()) {
                    m4963t();
                    this.f627B.m5226a(0);
                }
            }
        }
    }

    /* renamed from: b */
    public void m4993b(long j, byte[] bArr, int i) {
        m4991b(new C0798j(13, new C0796i(j, bArr, i)));
    }

    /* renamed from: b */
    public void m4992b(C0796i c0796i) {
        this.f627B.f450e = new Date().getTime();
        C0774h m5066g = this.f646V.m5066g();
        if (m5066g != null) {
            m5066g.m5092a(3, System.currentTimeMillis());
            m5066g.m5093a(3, 0);
            this.f646V.m5062k();
        }
        this.f646V.m5074b(c0796i.f691c);
        if (c0796i.f689a != this.f671z) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "Error p2p fetch ID:" + this.f671z + ConstantUtils.PLACEHOLDER_STR_ONE + c0796i.f689a);
        } else if (this.f627B.f446a != 2 && this.f627B.f446a != 1) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "P2P Not Running, Return directly!------------>" + this.f627B.f446a);
        } else if (this.f627B.f446a == 3) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "P2P paused, Return directly!");
        } else if (this.f627B.f446a == 5) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "P2P lost, Return directly!");
        } else {
            int m4997a = m4997a(c0796i.f690b, c0796i.f691c, 1);
            this.f636L += m4997a;
            Date date = new Date();
            if (this.f637M == 0 || date.getTime() - this.f637M > 180000) {
                this.f637M = date.getTime();
                C0740K.m5251c("P2P Fetch total size:" + this.f636L);
            }
            this.f629D.m4961a(m4997a);
            synchronized (this) {
                m4978f();
                m5018a();
                m4984d();
            }
        }
    }

    /* renamed from: b */
    public void m4991b(C0798j c0798j) {
        try {
            this.f651e.put(c0798j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00ed A[Catch: all -> 0x010a, TryCatch #0 {, blocks: (B:15:0x0033, B:17:0x003b, B:19:0x004d, B:23:0x0052, B:25:0x0054, B:27:0x0083, B:29:0x0099, B:31:0x00a1, B:33:0x00b8, B:36:0x00bc, B:37:0x00c2, B:38:0x00e1, B:39:0x00e4, B:41:0x00ed, B:42:0x0105, B:43:0x0108, B:50:0x00fe, B:51:0x00c5, B:53:0x00d3, B:54:0x00da), top: B:14:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f6  */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m4990b(String str) {
        int m5218c;
        C0745P c0745p;
        ArrayList<C0742M> arrayList = this.f657k;
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.f646V.m5077a(str, currentTimeMillis);
        if (this.f627B.f452g == -1) {
            return;
        }
        C0774h m5066g = this.f646V.m5066g();
        if (m5066g != null) {
            m5066g.m5092a(1, currentTimeMillis);
            m5066g.m5093a(1, 0);
        }
        synchronized (this) {
            C0742M c0742m = null;
            int i = 0;
            while (true) {
                if (i >= this.f657k.size()) {
                    break;
                }
                C0742M c0742m2 = this.f657k.get(i);
                if (c0742m2.f422a.compareTo(str) == 0) {
                    c0742m = c0742m2;
                    break;
                }
                i++;
            }
            if (c0742m == null) {
                return;
            }
            int i2 = this.f627B.f446a;
            m4963t();
            this.f658l = c0742m;
            this.f669x.m5239a(this.f658l);
            this.f627B.f452g = -1L;
            this.f627B.f450e = new Date().getTime();
            this.f627B.f451f = 0L;
            StringBuilder sb = new StringBuilder();
            sb.append("Nodehole Over!!!!");
            sb.append(this.f658l.toString());
            C0740K.m5251c(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Nodehole Over!!!!");
            sb2.append(this.f658l.toString());
            LogUtil.m117w(LogUtil.TAG_IPFS_SDK, sb2.toString());
            if (this.f632H == null) {
                return;
            }
            if (i2 == 0) {
                m5218c = this.f667v.m5218c();
            } else if (this.f668w.m5221b() == this.f666u.m5218c()) {
                m5218c = this.f668w.m5218c();
                int i3 = this.f664s;
                int i4 = 2;
                if (this.f628C.f505a == 1) {
                    if (i2 == 5 || i2 == 2 || i2 == 3) {
                        m4989b(this.f662q, m5218c, i3, false);
                        c0745p = this.f627B;
                    }
                }
                m4989b(this.f662q, m5218c, i3, false);
                c0745p = this.f627B;
                i4 = 1;
                c0745p.m5226a(i4);
            } else {
                m5218c = this.f667v.m5218c();
            }
            this.f668w.m5223a(m5218c);
            int i32 = this.f664s;
            int i42 = 2;
            if (this.f628C.f505a == 1) {
            }
            c0745p.m5226a(i42);
        }
    }

    /* renamed from: b */
    public void m4989b(String str, int i, int i2, boolean z) {
        if (!this.f650d) {
            return;
        }
        if (this.f658l == null) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "P2P No targetNode!!");
            return;
        }
        m4963t();
        this.f671z = C0757aa.m5181a(0L, Long.MAX_VALUE);
        this.f669x.m5241a(this.f671z, str, i, i2, z);
        this.f627B.f450e = new Date().getTime();
        C0774h m5066g = this.f646V.m5066g();
        if (m5066g == null) {
            return;
        }
        m5066g.m5091b(3, System.currentTimeMillis());
    }

    /* renamed from: b */
    public void m4988b(ArrayList<C0742M> arrayList) {
        String str = "";
        String str2 = str;
        String str3 = str2;
        String str4 = str3;
        String str5 = str4;
        for (int i = 0; i < this.f657k.size(); i++) {
            C0772f m5078a = this.f646V.m5078a(this.f657k.get(i).f422a);
            if (m5078a != null) {
                str = m5078a.m5152f();
                str2 = m5078a.m5150h();
                str3 = m5078a.m5160b();
                str4 = m5078a.m5151g();
                str5 = m5078a.m5162a();
            }
        }
        this.f657k = arrayList;
        this.f646V.m5071c();
        if (this.f657k.size() == 0) {
            m4963t();
            this.f627B.m5226a(0);
            return;
        }
        this.f656j = new ArrayList<>();
        for (int i2 = 0; i2 < this.f657k.size(); i2++) {
            C0742M c0742m = this.f657k.get(i2);
            this.f656j.add(c0742m.f428g.substring(c0742m.f428g.lastIndexOf("_") + 1));
            C0772f c0772f = new C0772f();
            c0772f.m5157c(str);
            c0772f.m5153e(str2);
            c0772f.m5159b(str3);
            c0772f.m5155d(str4);
            c0772f.m5161a(str5);
            c0772f.m5101f(c0742m.f428g);
            c0772f.m5100g(c0742m.f427f);
            this.f646V.m5076a(c0742m.f422a, c0772f);
        }
        m4970m();
    }

    /* renamed from: c */
    public void m4987c() {
        ArrayList<C0742M> arrayList;
        C0745P c0745p;
        long j;
        long time = new Date().getTime();
        this.f640P = time;
        if (this.f632H == null || (arrayList = this.f657k) == null || arrayList.size() == 0 || this.f658l == null || this.f662q == null || this.f627B.f446a == 6) {
            return;
        }
        C0745P c0745p2 = this.f627B;
        if (time - c0745p2.f450e > 4000 && (c0745p2.f446a == 2 || this.f627B.f446a == 3 || this.f627B.f446a == 1)) {
            if (this.f668w.m5218c() < this.f664s) {
                this.f627B.m5226a(5);
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "P2P Lost Active!!!!!!!!!!!!!");
                C0770d m5067f = this.f646V.m5067f();
                m5067f.m5132d(m5067f.m5118p() + 1);
                m5067f.m5128f(1);
                m5067f.m5130e(5);
                C0774h m5066g = this.f646V.m5066g();
                if (m5066g != null) {
                    m5066g.m5092a(3, System.currentTimeMillis());
                    m5066g.m5093a(3, 1);
                    this.f646V.m5062k();
                }
            } else {
                this.f627B.m5226a(6);
            }
        }
        C0745P c0745p3 = this.f627B;
        if (c0745p3.f451f < 10 || c0745p3.f446a != 5) {
            C0745P c0745p4 = this.f627B;
            if (time - c0745p4.f452g <= 2000 || c0745p4.f446a != 5) {
                return;
            }
            m4970m();
            c0745p = this.f627B;
            j = c0745p.f451f + 1;
        } else {
            m4968o();
            c0745p = this.f627B;
            j = 0;
        }
        c0745p.f451f = j;
    }

    /* renamed from: c */
    public void m4986c(long j) {
        synchronized (this) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "FetchDone!!!!!!!!!");
        }
    }

    /* renamed from: c */
    public void m4985c(String str) {
        m4991b(new C0798j(10, str));
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0084, code lost:
        if (r14.f668w.m5218c() <= r14.f667v.m5218c()) goto L21;
     */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m4984d() {
        if (this.f658l == null) {
            return;
        }
        long time = new Date().getTime();
        this.f642R = time;
        C0745P c0745p = this.f627B;
        long j = time - c0745p.f449d;
        if (c0745p.f446a == 1 && j > 5000) {
            double m4962a = this.f631F.m4962a();
            double m4962a2 = this.f629D.m4962a();
            double m4962a3 = this.f630E.m4962a();
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, String.format("P2PProbe-----------> s = %.2fKB/s, p = %.2fKB/s, c = %.2fKB/s", Double.valueOf(m4962a), Double.valueOf(m4962a2), Double.valueOf(m4962a3)));
            if (m4962a2 >= m4962a || m4962a2 >= m4962a3) {
                m4964s();
                if (this.f666u.m5218c() == this.f667v.m5221b() && this.f668w.m5218c() <= this.f667v.m5218c()) {
                    int m5218c = this.f667v.m5218c();
                    this.f668w.m5223a(m5218c);
                    m4989b(this.f662q, m5218c, this.f664s, false);
                }
                this.f628C.m5168a(0);
                this.f627B.m5226a(2);
                this.f627B.f448c = 0L;
                this.f646V.m5069d();
                this.f646V.m5065h();
                this.f646V.m5067f().m5134c(1);
                return;
            }
            int i = (m4962a2 > m4962a3 ? 1 : (m4962a2 == m4962a3 ? 0 : -1));
            if (i < 0 && m4962a3 >= m4962a) {
                if (this.f666u.m5218c() == this.f667v.m5221b()) {
                }
                this.f627B.m5226a(1);
            } else if (i >= 0 || m4962a3 >= m4962a) {
                return;
            } else {
                m4963t();
                this.f627B.m5226a(4);
                this.f627B.f448c++;
                return;
            }
        } else if (this.f627B.f446a != 4) {
            return;
        } else {
            C0745P c0745p2 = this.f627B;
            if (j <= c0745p2.f447b * ((c0745p2.f448c / 2) + 1)) {
                return;
            }
        }
        int m5218c2 = this.f667v.m5218c();
        this.f668w.m5223a(m5218c2);
        m4989b(this.f662q, m5218c2, this.f664s, false);
        this.f627B.m5226a(1);
    }

    /* renamed from: d */
    public void m4983d(long j) {
        if (this.f671z == j) {
            long time = new Date().getTime();
            this.f627B.f450e = time;
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "Recv Heart!--->" + this.f671z + ConstantUtils.PLACEHOLDER_STR_ONE + j + ConstantUtils.PLACEHOLDER_STR_ONE + time + ConstantUtils.PLACEHOLDER_STR_ONE + this.f627B.f450e);
            if (time - this.f643S <= 5000) {
                return;
            }
            this.f669x.m5243a();
            this.f643S = time;
        }
    }

    /* renamed from: d */
    public void m4982d(String str) {
        this.f626A = -1L;
        this.f670y.m5175a(str);
        for (C0798j c0798j : this.f651e) {
            int i = c0798j.f697a;
            if (i == 12 || i == 14) {
                c0798j.f698b = true;
            }
        }
    }

    /* renamed from: e */
    public void m4981e() {
        if (this.f632H != null) {
            return;
        }
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "downloadHlsList");
        C0774h m5066g = this.f646V.m5066g();
        if (m5066g != null) {
            m5066g.m5091b(2, System.currentTimeMillis());
        }
        try {
            URL url = new URL(this.f661p);
            HashMap hashMap = new HashMap();
            hashMap.put("accept", "*/*");
            hashMap.put("Host", url.getHost());
            hashMap.put("Cache-Control", "no-cache");
            C0804p.m4938a().m4936a(0, this.f661p, "GET", hashMap, (String) null, new C0790f(this));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: e */
    public void m4980e(long j) {
        m4991b(new C0798j(15, Long.valueOf(j)));
    }

    /* renamed from: e */
    public void m4979e(String str) {
        m4991b(new C0798j(4, str));
    }

    /* renamed from: f */
    public void m4978f() {
        boolean z;
        if (!this.f668w.m5220b(this.f666u.m5218c())) {
            if (this.f668w.m5221b() < this.f666u.m5218c()) {
                C0750V c0750v = this.f668w;
                c0750v.m5223a(c0750v.m5218c());
            }
            z = false;
        } else {
            z = true;
        }
        while (true) {
            int m5216e = this.f644T.length > this.f666u.m5216e() ? this.f666u.m5216e() : this.f644T.length;
            if (m5216e != 0) {
                int m5222a = z ? this.f668w.m5222a(this.f644T, m5216e) : 0;
                if (m5222a != 0) {
                    this.f666u.m5219b(this.f644T, m5222a);
                } else {
                    if (!this.f667v.m5220b(this.f666u.m5218c())) {
                        if (this.f667v.m5221b() >= this.f666u.m5218c()) {
                            return;
                        }
                        C0750V c0750v2 = this.f667v;
                        c0750v2.m5223a(c0750v2.m5218c());
                        return;
                    }
                    this.f666u.m5219b(this.f644T, this.f667v.m5222a(this.f644T, m5216e));
                }
            } else {
                return;
            }
        }
    }

    /* renamed from: f */
    public void m4977f(long j) {
        m4991b(new C0798j(17, Long.valueOf(j)));
    }

    /* renamed from: g */
    public int m4976g() {
        if (this.f628C.f505a == 1 && (this.f627B.f446a == 2 || this.f627B.f446a == 1 || this.f627B.f446a == 3 || this.f627B.f446a == 5)) {
            return 2;
        }
        if (this.f628C.f505a == 1) {
            return 0;
        }
        return (this.f627B.f446a == 2 || this.f627B.f446a == 3 || this.f627B.f446a == 5) ? 1 : 3;
    }

    /* renamed from: h */
    public void m4975h() {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "NetworkChange!!!!!!!!!!!!!!");
        m4968o();
    }

    /* renamed from: i */
    public void m4974i() {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "handleQueryResource2!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        C0742M m101b = P2PUdpUtil.m116a().m101b();
        if (m101b == null) {
            LogUtil.m119i(LogUtil.TAG_IPFS_SDK, "P2P not ready!!!!!!!!!!");
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m4991b(new C0798j(1, null));
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("url", this.f654h);
        linkedHashMap.put("token", this.f653g);
        linkedHashMap.put("p2pID", m101b.f422a);
        linkedHashMap.put("localIP", m101b.f423b);
        linkedHashMap.put("localPort", Integer.valueOf(m101b.f424c));
        linkedHashMap.put("natIP", m101b.f425d);
        linkedHashMap.put("natPort", Integer.valueOf(m101b.f426e));
        linkedHashMap.put("filterNodes", this.f656j);
        String jSONObject = new JSONObject(linkedHashMap).toString();
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "QueryResource: " + jSONObject);
        C0740K.m5251c("QueryResource: " + jSONObject);
        HashMap hashMap = new HashMap();
        hashMap.put("Content-Type", "application/json; charset=UTF-8");
        hashMap.put("Connection", MainFragment.CLOSE_EVENT);
        C0804p.m4938a().m4936a(0, C0738I.f406c, "POST", hashMap, jSONObject, new C0765d(this));
    }

    /* renamed from: j */
    public void m4973j() {
        this.f627B.m5227a();
        this.f628C.m5169a();
        this.f667v.m5223a(0);
        this.f668w.m5223a(0);
        this.f666u.m5223a(0);
        this.f629D.m4959b();
        this.f630E.m4959b();
        this.f631F.m4959b();
        this.f632H = null;
        this.f661p = null;
        this.f656j = null;
        this.f657k = null;
        this.f658l = null;
        this.f633I = 0L;
        this.f634J = -1L;
        this.f635K = 0L;
        this.f636L = 0L;
        this.f637M = 0L;
        this.f638N = 0L;
        this.f639O = 0L;
    }

    /* renamed from: k */
    public void m4972k() {
        synchronized (this) {
            this.f647a.set(true);
            if (this.f628C.f505a != 0) {
                m4964s();
            }
            if (this.f627B.f446a != 0) {
                m4963t();
            }
            if (this.f646V != null) {
                this.f646V.m5063j();
                this.f646V.m5062k();
                this.f646V.m5064i();
            }
            m4973j();
            StringBuilder sb = new StringBuilder();
            sb.append("Play stop!!!");
            sb.append(toString());
            C0740K.m5251c(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Play stop!!!");
            sb2.append(toString());
            sb2.append(",this.needExit = ");
            sb2.append(this.f647a);
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, sb2.toString());
            this.f649c.set(false);
        }
    }

    /* renamed from: l */
    public void m4971l() {
        m4991b(new C0798j(16, null));
    }

    /* renamed from: m */
    public void m4970m() {
        ArrayList<C0742M> arrayList = this.f657k;
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        C0774h m5066g = this.f646V.m5066g();
        if (m5066g != null) {
            m5066g.m5091b(1, currentTimeMillis);
        }
        LogUtil.m117w(LogUtil.TAG_IPFS_SDK, "Nodehole Begin!!!!!");
        for (int i = 0; i < this.f657k.size(); i++) {
            C0742M c0742m = this.f657k.get(i);
            P2PUdpUtil.m116a().m99b(c0742m.f422a, new C0780e(this));
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("type", 0);
            linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, P2PUdpUtil.m116a().m98c());
            linkedHashMap.put("verify", "12345678");
            linkedHashMap.put(AopConstants.APP_PROPERTIES_KEY, "12345678");
            linkedHashMap.put("targetId", c0742m.f422a);
            String jSONObject = new JSONObject(linkedHashMap).toString();
            this.f627B.f452g = new Date().getTime();
            this.f646V.m5072b(c0742m.f422a, currentTimeMillis);
            P2PUdpUtil.m116a().m103a(jSONObject.getBytes(), c0742m);
            P2PUdpUtil.m116a().m103a(jSONObject.getBytes(), c0742m);
            P2PUdpUtil.m116a().m103a(jSONObject.getBytes(), c0742m);
            P2PUdpUtil.m116a().m103a(jSONObject.getBytes(), c0742m);
            P2PUdpUtil.m116a().m103a(jSONObject.getBytes(), c0742m);
        }
    }

    /* renamed from: n */
    public void m4969n() {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "puaseP2PFetchter");
        this.f669x.m5234b();
    }

    /* renamed from: o */
    public void m4968o() {
        m4991b(new C0798j(1, null));
    }

    /* renamed from: p */
    public void m4967p() {
        m4991b(new C0798j(0, null));
    }

    /* renamed from: q */
    public void m4966q() {
        while (this.f649c.get()) {
            try {
                Thread.sleep(20L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.f651e.clear();
        new Thread(this).start();
        LogUtil.m119i(LogUtil.TAG_IPFS_SDK, "DataFetcherWapper start!!!!!!!!!!!");
        C0740K.m5251c("DataFetcherWapper start!!!!");
    }

    /* renamed from: r */
    public void m4965r() {
        this.f649c.set(true);
        this.f651e.clear();
        m4991b(new C0798j(6, null));
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f640P = new Date().getTime();
        this.f641Q = new Date().getTime();
        while (!this.f647a.get()) {
            if (this.f648b.get()) {
                m4973j();
                this.f648b.set(false);
            }
            C0775i c0775i = this.f646V;
            if (c0775i != null && this.f650d) {
                c0775i.m5075b();
            }
            m4996b();
            C0798j c0798j = null;
            try {
                c0798j = this.f651e.poll(100L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (c0798j != null) {
                m5009a(c0798j);
            }
            synchronized (this) {
                long time = new Date().getTime();
                if (time - this.f640P > 100) {
                    m4987c();
                }
                if (time - this.f641Q > 100) {
                    m4978f();
                    m5018a();
                }
                if (time - this.f642R > 50) {
                    m4984d();
                }
            }
        }
        this.f647a.set(false);
        LogUtil.m119i(LogUtil.TAG_IPFS_SDK, "DataFetcherWapper exit!!!!!!!!!");
        C0740K.m5251c("DataFetcherWapper exit!!!!");
    }

    /* renamed from: s */
    public void m4964s() {
        this.f626A = -1L;
        this.f670y.m5173b();
        for (C0798j c0798j : this.f651e) {
            int i = c0798j.f697a;
            if (i == 12 || i == 14) {
                c0798j.f698b = true;
            }
        }
    }

    /* renamed from: t */
    public void m4963t() {
        this.f671z = -1L;
        this.f669x.m5231c();
        for (C0798j c0798j : this.f651e) {
            int i = c0798j.f697a;
            if (i == 13 || i == 15) {
                c0798j.f698b = true;
            }
        }
    }
}
