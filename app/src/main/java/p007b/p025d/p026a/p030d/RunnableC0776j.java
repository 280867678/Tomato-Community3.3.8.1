package p007b.p025d.p026a.p030d;

import android.support.p002v4.app.NotificationCompat;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.sdkReport.Dao.ReportItemJsonWrapper;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;
import p007b.p008a.p009a.p011b.C0577g;
import p007b.p025d.p026a.C0804p;

/* renamed from: b.d.a.d.j */
/* loaded from: classes2.dex */
public class RunnableC0776j implements Runnable {

    /* renamed from: a */
    public ReportItemJsonWrapper f581a;

    /* renamed from: b */
    public String f582b;

    /* renamed from: c */
    public RunnableC0778l f583c;

    /* renamed from: d */
    public final /* synthetic */ ReportItemJsonWrapper f584d;

    /* renamed from: e */
    public final /* synthetic */ String f585e;

    /* renamed from: f */
    public final /* synthetic */ RunnableC0778l f586f;

    public RunnableC0776j(RunnableC0778l runnableC0778l, ReportItemJsonWrapper reportItemJsonWrapper, String str) {
        this.f586f = runnableC0778l;
        this.f584d = reportItemJsonWrapper;
        this.f585e = str;
        this.f581a = this.f584d;
        this.f582b = this.f585e;
        this.f583c = this.f586f;
    }

    @Override // java.lang.Runnable
    public void run() {
        String m5060a;
        C0577g c0577g;
        long curTimeStamp;
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("Content-Type", "application/json");
            hashMap.put("Connection", MainFragment.CLOSE_EVENT);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("playId", this.f581a.getPlayId());
            linkedHashMap.put(AopConstants.APP_PROPERTIES_KEY, this.f582b);
            String jSONObject = new JSONObject(linkedHashMap).toString();
            m5060a = this.f583c.m5060a(this.f581a.getType());
            if (m5060a == "") {
                return;
            }
            String m4933a = C0804p.m4938a().m4933a(m5060a, "POST", hashMap, jSONObject, 5000, 5000);
            StringBuilder sb = new StringBuilder();
            sb.append("REPORT_SERVER_IP：");
            sb.append(m5060a);
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(m4933a);
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
            int i = new JSONObject(m4933a).getInt(NotificationCompat.CATEGORY_STATUS);
            if (i == 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("上报成功, 从数据库移除 ");
                sb2.append(this.f581a);
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb2.toString());
                c0577g = this.f583c.f595c;
                curTimeStamp = this.f581a.getCurTimeStamp();
            } else if (-5 == i) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("WRONG_KEY, 重新走上报流程。 ");
                sb3.append(this.f581a);
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb3.toString());
                C0769c.m5146a().m5142b(this.f581a.getPlayId());
                this.f583c.m5056a(this.f581a);
                return;
            } else if (-3 == i) {
                Thread.sleep(500L);
                this.f583c.m5054a(this);
                return;
            } else {
                c0577g = this.f583c.f595c;
                curTimeStamp = this.f581a.getCurTimeStamp();
            }
            c0577g.m5540a("curTimeStamp", Long.valueOf(curTimeStamp));
        } catch (Exception e) {
            e.printStackTrace();
            this.f583c.m5054a(this);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }
}
