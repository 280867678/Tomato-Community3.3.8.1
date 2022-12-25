package p007b.p025d.p026a;

import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.P2PUdpUtil;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/* renamed from: b.d.a.O */
/* loaded from: classes2.dex */
public class C0744O implements AbstractC0805q {

    /* renamed from: a */
    public RunnableC0792g f433a;

    /* renamed from: b */
    public C0742M f434b;

    /* renamed from: e */
    public int f437e;

    /* renamed from: f */
    public int f438f;

    /* renamed from: g */
    public String f439g;

    /* renamed from: h */
    public int f440h;

    /* renamed from: i */
    public String f441i;

    /* renamed from: j */
    public String f442j;

    /* renamed from: k */
    public String f443k;

    /* renamed from: c */
    public long f435c = -1;

    /* renamed from: m */
    public AtomicInteger f445m = new AtomicInteger(1);

    /* renamed from: d */
    public Object f436d = new Object();

    /* renamed from: l */
    public int f444l = 0;

    public C0744O(RunnableC0792g runnableC0792g) {
        this.f433a = runnableC0792g;
    }

    /* renamed from: a */
    public String m5235a(String str, String str2) {
        int i;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        HashMap hashMap = new HashMap();
        int i2 = this.f440h;
        if (i2 == 0) {
            hashMap.put("hash", this.f441i);
            hashMap.put("token", this.f442j);
            hashMap.put("urlname", this.f439g);
            hashMap.put("playid", str2);
            i = 1;
        } else if (i2 != 1) {
            i = 0;
        } else {
            i = 4;
            hashMap.put("subtype", 1);
            hashMap.put("hash", this.f441i);
            hashMap.put("file", str);
            hashMap.put("token", this.f442j);
            hashMap.put("urlname", this.f439g);
            hashMap.put("playid", str2);
            hashMap.put("firstflag", Integer.valueOf(this.f445m.get()));
            hashMap.put("sdktype", 0);
        }
        linkedHashMap.put("type", Integer.valueOf(i));
        linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, P2PUdpUtil.m116a().m98c());
        linkedHashMap.put("verify", "xxxxxxxxxxxxx");
        linkedHashMap.put(AopConstants.APP_PROPERTIES_KEY, hashMap);
        String jSONObject = new JSONObject(linkedHashMap).toString();
        this.f444l++;
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "GenRequestInfo: " + jSONObject + ConstantUtils.PLACEHOLDER_STR_ONE + this.f444l);
        return jSONObject;
    }

    /* renamed from: a */
    public Map<String, List<String>> m5241a(long j, String str, int i, int i2, boolean z) {
        if (this.f434b == null) {
            LogUtil.m120e(LogUtil.TAG_IPFS_SDK, "p2p fetchData but targetNode is null!!!");
        }
        String m5235a = m5235a(str, this.f443k);
        long m5181a = C0757aa.m5181a(1L, 2147483647L);
        P2PUdpUtil.m116a().m113a(m5181a, this.f434b, i, 2147483647L, m5235a.getBytes(), new C0743N(this, j));
        synchronized (this.f436d) {
            this.f435c = m5181a;
        }
        this.f437e = (i2 - i) + 1;
        this.f438f = 0;
        return null;
    }

    /* renamed from: a */
    public void m5243a() {
        if (this.f435c != -1) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("type", 6);
            linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, P2PUdpUtil.m116a().m98c());
            linkedHashMap.put("verify", "xxxxxxxxxxxxx");
            linkedHashMap.put(AopConstants.APP_PROPERTIES_KEY, "");
            String jSONObject = new JSONObject(linkedHashMap).toString();
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "GenP2PTaskHeartInfo: " + jSONObject);
            P2PUdpUtil.m116a().m112a(this.f435c, jSONObject.getBytes());
        }
    }

    /* renamed from: a */
    public void m5242a(long j) {
        if (this.f435c != -1) {
            this.f433a.m4980e(j);
        }
    }

    /* renamed from: a */
    public void m5240a(long j, byte[] bArr, int i) {
        if (this.f435c != -1) {
            this.f433a.m4993b(j, bArr, i);
        }
    }

    /* renamed from: a */
    public void m5239a(C0742M c0742m) {
        this.f434b = c0742m;
    }

    /* renamed from: a */
    public void m5236a(String str, int i, String str2, String str3, String str4) {
        this.f439g = str;
        this.f440h = i;
        this.f441i = str2;
        this.f442j = str3;
        this.f443k = str4;
        this.f445m.set(1);
        this.f444l = 0;
    }

    /* renamed from: b */
    public void m5234b() {
        if (this.f435c != -1) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("type", 7);
            linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, P2PUdpUtil.m116a().m98c());
            linkedHashMap.put("verify", "xxxxxxxxxxxxx");
            linkedHashMap.put(AopConstants.APP_PROPERTIES_KEY, 0);
            String jSONObject = new JSONObject(linkedHashMap).toString();
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "GenP2PTaskPauseInfo: " + jSONObject);
            P2PUdpUtil.m116a().m112a(this.f435c, jSONObject.getBytes());
        }
    }

    /* renamed from: b */
    public void m5233b(long j) {
        if (this.f435c != -1) {
            this.f433a.m4977f(j);
        }
    }

    /* renamed from: c */
    public void m5231c() {
        synchronized (this.f436d) {
            if (this.f435c != -1) {
                P2PUdpUtil.m116a().m114a(this.f435c);
                this.f435c = -1L;
                this.f437e = 0;
                this.f438f = 0;
            }
        }
    }
}
