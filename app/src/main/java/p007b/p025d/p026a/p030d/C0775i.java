package p007b.p025d.p026a.p030d;

import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: b.d.a.d.i */
/* loaded from: classes2.dex */
public class C0775i {

    /* renamed from: a */
    public String f573a;

    /* renamed from: e */
    public int f577e = 0;

    /* renamed from: f */
    public int f578f = 0;

    /* renamed from: g */
    public int f579g = 0;

    /* renamed from: h */
    public int f580h = 0;

    /* renamed from: b */
    public C0770d f574b = new C0770d();

    /* renamed from: c */
    public Map<String, C0772f> f575c = new HashMap();

    /* renamed from: d */
    public C0774h f576d = new C0774h();

    /* renamed from: a */
    public C0772f m5078a(String str) {
        return this.f575c.get(str);
    }

    /* renamed from: a */
    public void m5082a() {
        for (Map.Entry<String, C0772f> entry : this.f575c.entrySet()) {
            C0772f value = entry.getValue();
            long currentTimeMillis = System.currentTimeMillis();
            if (value.m5099j() == 1 && currentTimeMillis - value.m5095n() > 5000 && value.m5094o() != 1) {
                value.m5105b(1);
                value.m5102d(currentTimeMillis / 1000);
                value.m5107a(3);
                m5070c(entry.getKey());
            }
        }
    }

    /* renamed from: a */
    public void m5081a(int i) {
        this.f574b.m5127f(System.currentTimeMillis());
        this.f574b.m5128f(0);
        this.f574b.m5132d(1);
        this.f574b.m5130e(i);
        C0774h c0774h = this.f576d;
        if (c0774h != null) {
            c0774h.m5092a(0, System.currentTimeMillis());
            this.f576d.m5093a(0, 1);
        }
        m5063j();
        m5062k();
    }

    /* renamed from: a */
    public void m5080a(int i, String str) {
        String str2 = this.f573a;
        if (str2 == null || str2.length() == 0) {
            return;
        }
        String m5144a = C0769c.m5146a().m5144a(this.f573a);
        if (m5144a == null) {
            RunnableC0778l.m5061a().m5052a(this.f573a, i, str);
        } else {
            RunnableC0778l.m5061a().m5051a(this.f573a, i, str, m5144a);
        }
    }

    /* renamed from: a */
    public void m5079a(long j) {
        if (this.f574b.m5122l() == 0) {
            C0770d c0770d = this.f574b;
            c0770d.m5129e(c0770d.m5112v() + j);
            this.f578f = (int) (this.f578f + j);
            return;
        }
        C0770d c0770d2 = this.f574b;
        c0770d2.m5139a(c0770d2.m5124j() + j);
        this.f579g = (int) (this.f579g + j);
    }

    /* renamed from: a */
    public void m5077a(String str, long j) {
        C0772f c0772f = this.f575c.get(str);
        if (c0772f == null || c0772f.m5099j() != 1) {
            return;
        }
        c0772f.m5105b(0);
        c0772f.m5102d(j / 1000);
        c0772f.m5107a(2);
        c0772f.m5104b(j);
        c0772f.m5106a(j - c0772f.m5095n());
        m5070c(str);
    }

    /* renamed from: a */
    public void m5076a(String str, C0772f c0772f) {
        this.f575c.put(str, c0772f);
    }

    /* renamed from: b */
    public void m5075b() {
        if (System.currentTimeMillis() - this.f574b.m5109y() > 300000) {
            m5063j();
        }
        m5082a();
    }

    /* renamed from: b */
    public void m5074b(long j) {
        C0770d c0770d = this.f574b;
        c0770d.m5131d(c0770d.m5114t() + j);
        this.f577e = (int) (this.f577e + j);
    }

    /* renamed from: b */
    public void m5073b(String str) {
        this.f573a = str;
    }

    /* renamed from: b */
    public void m5072b(String str, long j) {
        C0772f c0772f = this.f575c.get(str);
        if (c0772f == null || c0772f.m5099j() == 1) {
            return;
        }
        c0772f.m5107a(1);
        c0772f.m5103c(j);
    }

    /* renamed from: c */
    public void m5071c() {
        this.f575c.clear();
    }

    /* renamed from: c */
    public void m5070c(String str) {
        String m5152f;
        C0772f c0772f = this.f575c.get(str);
        if (c0772f == null || (m5152f = c0772f.m5152f()) == null || m5152f.length() == 0) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, c0772f.m5149i());
        linkedHashMap.put("msgId", Integer.valueOf(c0772f.m5158c()));
        linkedHashMap.put("taskId", c0772f.m5152f());
        linkedHashMap.put("chanId", c0772f.m5162a());
        linkedHashMap.put("domain", c0772f.m5160b());
        linkedHashMap.put("uri", c0772f.m5151g());
        linkedHashMap.put("urlName", c0772f.m5150h());
        linkedHashMap.put("SDKType", Integer.valueOf(c0772f.m5156d()));
        linkedHashMap.put("SDKVersion", c0772f.m5154e());
        linkedHashMap.put("nodeId", c0772f.m5097l());
        linkedHashMap.put("nodeInfo", c0772f.m5096m());
        linkedHashMap.put("state", Integer.valueOf(c0772f.m5094o()));
        linkedHashMap.put("consume", Long.valueOf(c0772f.m5098k()));
        linkedHashMap.put("timeStamp", Long.valueOf(new Date().getTime() / 1000));
        String replace = new JSONObject(linkedHashMap).toString().replace("\\/", "/");
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "uploadP2PConnReportItem--->" + replace);
        m5080a(c0772f.m5158c(), replace);
    }

    /* renamed from: d */
    public void m5069d() {
        C0770d c0770d = this.f574b;
        c0770d.m5140a(c0770d.m5123k() + 1);
    }

    /* renamed from: e */
    public void m5068e() {
        C0770d c0770d = this.f574b;
        c0770d.m5126g(c0770d.m5113u() + 1);
    }

    /* renamed from: f */
    public C0770d m5067f() {
        return this.f574b;
    }

    /* renamed from: g */
    public C0774h m5066g() {
        return this.f576d;
    }

    /* renamed from: h */
    public void m5065h() {
        this.f574b.m5137b(1);
    }

    /* renamed from: i */
    public void m5064i() {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "FlowData: " + this.f577e + ConstantUtils.PLACEHOLDER_STR_ONE + this.f578f + ConstantUtils.PLACEHOLDER_STR_ONE + this.f579g + ConstantUtils.PLACEHOLDER_STR_ONE + this.f580h);
    }

    /* renamed from: j */
    public void m5063j() {
        String m5152f = this.f574b.m5152f();
        if (m5152f == null || m5152f.length() == 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (this.f574b.m5109y() != 0 && currentTimeMillis - this.f574b.m5109y() > 360000) {
            currentTimeMillis = this.f574b.m5109y() + 360000;
        }
        this.f574b.m5136b(currentTimeMillis);
        this.f574b.m5133c(currentTimeMillis);
        this.f574b.m5125h(RunnableC0778l.m5061a().m5047c());
        this.f574b.m5135b(RunnableC0778l.m5061a().m5049b());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, this.f574b.m5149i());
        linkedHashMap.put("msgId", Integer.valueOf(this.f574b.m5158c()));
        linkedHashMap.put("taskId", m5152f);
        linkedHashMap.put("chanId", this.f574b.m5162a());
        linkedHashMap.put("domain", this.f574b.m5160b());
        linkedHashMap.put("uri", this.f574b.m5151g());
        linkedHashMap.put("urlName", this.f574b.m5150h());
        linkedHashMap.put("SDKType", Integer.valueOf(this.f574b.m5156d()));
        linkedHashMap.put("SDKVersion", this.f574b.m5154e());
        linkedHashMap.put("pureCDNFlow", Long.valueOf(this.f574b.m5112v()));
        linkedHashMap.put("CDNFlow", Long.valueOf(this.f574b.m5124j()));
        linkedHashMap.put("P2PFlow", Long.valueOf(this.f574b.m5114t()));
        linkedHashMap.put("nodeList", this.f574b.m5115s());
        linkedHashMap.put("P2PSwitchCount", Integer.valueOf(this.f574b.m5113u()));
        linkedHashMap.put("CDNSwitchCount", Integer.valueOf(this.f574b.m5123k()));
        linkedHashMap.put("SDKIPList", this.f574b.m5111w());
        linkedHashMap.put("SDKNetType", Integer.valueOf(this.f574b.m5110x()));
        linkedHashMap.put("curAccelState", Integer.valueOf(this.f574b.m5121m()));
        linkedHashMap.put("curTimeStamp", Long.valueOf(this.f574b.m5120n() / 1000));
        linkedHashMap.put("startTimeStamp", Long.valueOf(this.f574b.m5109y() / 1000));
        linkedHashMap.put("endTimeStamp", Long.valueOf(this.f574b.m5119o() / 1000));
        linkedHashMap.put("exceptionCount", Integer.valueOf(this.f574b.m5118p()));
        linkedHashMap.put("exceptionType", Integer.valueOf(this.f574b.m5116r()));
        linkedHashMap.put("exceptionStatus", Integer.valueOf(this.f574b.m5117q()));
        String replace = new JSONObject(linkedHashMap).toString().replace("\\/", "/");
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "uploadFlowReportItem--->" + replace);
        m5080a(this.f574b.m5158c(), replace);
        this.f580h = this.f580h + 1;
        this.f574b.m5108z();
    }

    /* renamed from: k */
    public void m5062k() {
        String m5152f;
        C0774h c0774h = this.f576d;
        if (c0774h == null || (m5152f = c0774h.m5152f()) == null || m5152f.length() == 0) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, this.f576d.m5149i());
        linkedHashMap.put("msgId", Integer.valueOf(this.f576d.m5158c()));
        linkedHashMap.put("taskId", this.f576d.m5152f());
        linkedHashMap.put("chanId", this.f576d.m5162a());
        linkedHashMap.put("domain", this.f576d.m5160b());
        linkedHashMap.put("uri", this.f576d.m5151g());
        linkedHashMap.put("urlName", this.f576d.m5150h());
        linkedHashMap.put("SDKType", Integer.valueOf(this.f576d.m5156d()));
        linkedHashMap.put("SDKVersion", this.f576d.m5154e());
        C0773g m5087m = this.f576d.m5087m();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.put("state", Integer.valueOf(m5087m.f560a));
        linkedHashMap2.put("consume", Long.valueOf(m5087m.f563d));
        C0773g m5089k = this.f576d.m5089k();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        linkedHashMap3.put("state", Integer.valueOf(m5089k.f560a));
        linkedHashMap3.put("consume", Long.valueOf(m5089k.f563d));
        C0773g m5088l = this.f576d.m5088l();
        LinkedHashMap linkedHashMap4 = new LinkedHashMap();
        linkedHashMap4.put("state", Integer.valueOf(m5088l.f560a));
        linkedHashMap4.put("consume", Long.valueOf(m5088l.f563d));
        C0773g m5086n = this.f576d.m5086n();
        LinkedHashMap linkedHashMap5 = new LinkedHashMap();
        linkedHashMap5.put("state", Integer.valueOf(m5086n.f560a));
        linkedHashMap5.put("consume", Long.valueOf(m5086n.f563d));
        linkedHashMap.put("phaseQuery", linkedHashMap2);
        linkedHashMap.put("phaseDLHlsList", linkedHashMap3);
        linkedHashMap.put("phaseHole", linkedHashMap4);
        linkedHashMap.put("phaseRequest", linkedHashMap5);
        linkedHashMap.put("state", Integer.valueOf(this.f576d.m5084p()));
        linkedHashMap.put("totalConsume", Integer.valueOf(this.f576d.m5083q()));
        linkedHashMap.put("startTime", Long.valueOf(this.f576d.m5085o()));
        linkedHashMap.put("endTime", Long.valueOf(this.f576d.m5090j()));
        linkedHashMap.put("timeStamp", Long.valueOf(new Date().getTime() / 1000));
        String replace = new JSONObject(linkedHashMap).toString().replace("\\/", "/");
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "uploadPreRequestReportItem--->" + replace);
        m5080a(this.f576d.m5158c(), replace);
        this.f576d = null;
    }
}
