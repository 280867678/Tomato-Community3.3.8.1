package p007b.p025d.p026a;

import com.tomatolive.library.http.utils.EncryptUtil;
import com.zzz.ipfssdk.LogUtil;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/* renamed from: b.d.a.f */
/* loaded from: classes2.dex */
public class C0790f implements AbstractC0806r {

    /* renamed from: b */
    public int f621b = 0;

    /* renamed from: d */
    public StringBuffer f622d = new StringBuffer();

    /* renamed from: e */
    public final /* synthetic */ RunnableC0792g f623e;

    public C0790f(RunnableC0792g runnableC0792g) {
        this.f623e = runnableC0792g;
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4931a(int i, Map<String, List<String>> map) {
        Integer.parseInt(map.get("Content-Length").get(0));
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4930a(Exception exc) {
        exc.printStackTrace();
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    /* renamed from: a */
    public void mo4929a(byte[] bArr, int i) {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "hlsListDLUrl return data len =  " + i);
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        this.f621b = this.f621b + i;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("HLS List recv len = ");
            sb.append(this.f621b);
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
            this.f622d.append(new String(bArr2, EncryptUtil.CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override // p007b.p025d.p026a.AbstractC0806r
    public void end(int i) {
        RunnableC0792g runnableC0792g;
        StringBuffer stringBuffer;
        boolean z;
        if (i == 200 || i == 206) {
            runnableC0792g = this.f623e;
            stringBuffer = this.f622d;
            z = true;
        } else {
            runnableC0792g = this.f623e;
            stringBuffer = this.f622d;
            z = false;
        }
        runnableC0792g.m4999a(z, stringBuffer);
    }
}
