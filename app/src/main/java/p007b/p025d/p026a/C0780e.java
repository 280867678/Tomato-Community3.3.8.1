package p007b.p025d.p026a;

import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.P2PUdpUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: b.d.a.e */
/* loaded from: classes2.dex */
public class C0780e implements AbstractC0807s {

    /* renamed from: a */
    public final /* synthetic */ RunnableC0792g f608a;

    public C0780e(RunnableC0792g runnableC0792g) {
        this.f608a = runnableC0792g;
    }

    @Override // p007b.p025d.p026a.AbstractC0807s
    /* renamed from: a */
    public void mo4928a(String str, String str2, int i, String str3, int i2, String str4) {
    }

    @Override // p007b.p025d.p026a.AbstractC0807s
    /* renamed from: a */
    public void mo4927a(String str, byte[] bArr, String str2, int i) {
    }

    @Override // p007b.p025d.p026a.AbstractC0807s
    /* renamed from: b */
    public void mo4926b(String str, String str2, int i, String str3, int i2, String str4) {
        LogUtil.m119i(LogUtil.TAG_IPFS_SDK, "RegisterResCalback:" + str + ConstantUtils.PLACEHOLDER_STR_ONE + str2 + ConstantUtils.PLACEHOLDER_STR_ONE + str3 + ConstantUtils.PLACEHOLDER_STR_ONE + str4);
        RunnableC0792g runnableC0792g = this.f608a;
        if (str.compareTo(P2PUdpUtil.m116a().m98c()) == 0) {
            try {
                runnableC0792g.m4979e(new JSONObject(str4).getString("targetId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
