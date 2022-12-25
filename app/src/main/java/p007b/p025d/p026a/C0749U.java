package p007b.p025d.p026a;

import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.P2PUdpUtil;
import com.zzz.ipfssdk.callback.exception.CodeState;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import p007b.p025d.p026a.p027a.C0756a;
import p007b.p025d.p026a.p030d.RunnableC0778l;

/* renamed from: b.d.a.U */
/* loaded from: classes2.dex */
public class C0749U implements AbstractC0807s {

    /* renamed from: a */
    public final /* synthetic */ P2PUdpUtil f459a;

    public C0749U(P2PUdpUtil p2PUdpUtil) {
        this.f459a = p2PUdpUtil;
    }

    @Override // p007b.p025d.p026a.AbstractC0807s
    /* renamed from: a */
    public void mo4928a(String str, String str2, int i, String str3, int i2, String str4) {
    }

    @Override // p007b.p025d.p026a.AbstractC0807s
    /* renamed from: a */
    public void mo4927a(String str, byte[] bArr, String str2, int i) {
        String str3;
        int i2;
        synchronized (this.f459a) {
            str3 = this.f459a.f5965g;
            if (str3 != null) {
                i2 = this.f459a.f5966h;
                if (i2 != 0) {
                    return;
                }
            }
            String str4 = new String(bArr);
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "recvRegisgerRes: " + str4);
            C0740K.m5251c("QueryIPAddrInfo recvRegisgerRes: " + str4);
            try {
                JSONObject jSONObject = new JSONObject(str4).getJSONObject(AopConstants.APP_PROPERTIES_KEY);
                String string = jSONObject.getString("pubip");
                int i3 = jSONObject.getInt("pubport");
                String string2 = jSONObject.getString("priip");
                P2PUdpUtil.m116a().m107a(string2, jSONObject.getInt("priport"), string, i3);
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(string);
                arrayList.add(string2);
                RunnableC0778l.m5061a().m5050a(arrayList);
            } catch (JSONException e) {
                e.printStackTrace();
                C0756a.m5182a().onException(new CodeState(2000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e));
            }
        }
    }

    @Override // p007b.p025d.p026a.AbstractC0807s
    /* renamed from: b */
    public void mo4926b(String str, String str2, int i, String str3, int i2, String str4) {
    }
}
