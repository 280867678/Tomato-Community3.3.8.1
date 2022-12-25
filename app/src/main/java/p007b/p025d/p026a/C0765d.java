package p007b.p025d.p026a;

import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.callback.exception.CodeState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p007b.p025d.p026a.p027a.C0756a;
import p007b.p025d.p026a.p030d.C0775i;

/* renamed from: b.d.a.d */
/* loaded from: classes2.dex */
public class C0765d implements AbstractC0806r {

    /* renamed from: a */
    public RunnableC0792g f515a;

    /* renamed from: b */
    public final /* synthetic */ RunnableC0792g f516b;

    public C0765d(RunnableC0792g runnableC0792g) {
        this.f516b = runnableC0792g;
        this.f515a = this.f516b;
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4931a(int i, Map<String, List<String>> map) {
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4930a(Exception exc) {
        C0756a.m5182a().onException(new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_HTTP_REQUEST_ERR, CodeState.MSGS.MSG_QUERY_RESOURCE_HTTP_REQUEST_ERR, exc));
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4929a(byte[] bArr, int i) {
        C0775i c0775i;
        C0775i c0775i2;
        C0775i c0775i3;
        if (i == 0) {
            return;
        }
        String str = new String(bArr);
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "QueryResource resp: " + str);
        C0740K.m5251c("QueryResource resp: " + str);
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject(AopConstants.APP_PROPERTIES_KEY);
            int i2 = jSONObject.getInt("status_code");
            int i3 = 2;
            int i4 = 3;
            CodeState codeState = i2 == 2 ? new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_P2P_DISABLE, CodeState.MSGS.MSG_P2P_DISABLE, null) : i2 == 3 ? new CodeState(3004, CodeState.MSGS.MSG_NODE_NOT_EXIST, null) : null;
            JSONArray jSONArray = jSONObject.getJSONArray("cacheNodes");
            if (jSONArray.length() == 0) {
                LogUtil.m119i(LogUtil.TAG_IPFS_SDK, "No cacheNodes!");
                return;
            }
            ArrayList<C0742M> arrayList = new ArrayList<>();
            ArrayList arrayList2 = new ArrayList();
            int i5 = 0;
            while (i5 < jSONArray.length()) {
                JSONArray jSONArray2 = jSONArray.getJSONArray(i5);
                String string = jSONArray2.getString(5);
                arrayList.add(new C0742M(jSONArray2.getString(0), jSONArray2.getString(1), (short) jSONArray2.getInt(i3), jSONArray2.getString(3), (short) jSONArray2.getInt(4), string, jSONArray2.getString(6)));
                arrayList2.add(string);
                i5++;
                i3 = 2;
            }
            c0775i = this.f515a.f646V;
            c0775i.m5067f().m5138a(arrayList2);
            if (codeState != null) {
                if (3004 == codeState.getCode()) {
                    c0775i2 = this.f515a.f646V;
                } else {
                    if (3014 == codeState.getCode()) {
                        c0775i2 = this.f515a.f646V;
                        i4 = 6;
                    }
                    c0775i3 = this.f515a.f646V;
                    c0775i3.m5063j();
                }
                c0775i2.m5081a(i4);
                c0775i3 = this.f515a.f646V;
                c0775i3.m5063j();
            }
            this.f515a.m5002a(arrayList);
        } catch (JSONException e) {
            e.printStackTrace();
            C0756a.m5182a().onException(new CodeState(3000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e));
        }
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    public void end(int i) {
    }
}
