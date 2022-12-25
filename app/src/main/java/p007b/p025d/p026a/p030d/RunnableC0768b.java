package p007b.p025d.p026a.p030d;

import android.support.p002v4.app.NotificationCompat;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.zzz.ipfssdk.LogUtil;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;
import p007b.p025d.p026a.C0804p;

/* renamed from: b.d.a.d.b */
/* loaded from: classes2.dex */
public class RunnableC0768b implements Runnable {

    /* renamed from: a */
    public C0769c f528a;

    /* renamed from: b */
    public final /* synthetic */ String f529b;

    /* renamed from: c */
    public final /* synthetic */ C0769c f530c;

    public RunnableC0768b(C0769c c0769c, String str) {
        this.f530c = c0769c;
        this.f529b = str;
        this.f528a = this.f530c;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str;
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("Content-Type", "application/json");
            hashMap.put("Connection", MainFragment.CLOSE_EVENT);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("playId", this.f529b);
            String jSONObject = new JSONObject(linkedHashMap).toString();
            StringBuilder sb = new StringBuilder();
            str = this.f530c.f532b;
            sb.append(str);
            sb.append("getkey");
            String m4933a = C0804p.m4938a().m4933a(sb.toString(), "POST", hashMap, jSONObject, 5000, 5000);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("getKey结果反馈");
            sb2.append(m4933a);
            LogUtil.m121d("EncodeKeyManager", sb2.toString());
            if (m4933a != null) {
                JSONObject jSONObject2 = new JSONObject(m4933a);
                if (jSONObject2.getInt(NotificationCompat.CATEGORY_STATUS) == 0) {
                    String string = jSONObject2.getString("key");
                    this.f528a.m5143a(this.f529b, string);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("find a key:");
                    sb3.append(string);
                    sb3.append(" for playid ");
                    sb3.append(this.f529b);
                    sb3.append(" from net ");
                    LogUtil.m121d("EncodeKeyManager", sb3.toString());
                    return;
                }
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append("获取key失败 playId = ");
            sb4.append(this.f529b);
            LogUtil.m120e("EncodeKeyManager", sb4.toString());
            Thread.sleep(500L);
            RunnableC0778l.m5061a().m5054a(this);
        } catch (Exception e) {
            RunnableC0778l.m5061a().m5054a(this);
            e.printStackTrace();
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }
}
