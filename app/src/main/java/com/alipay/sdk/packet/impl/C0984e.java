package com.alipay.sdk.packet.impl;

import android.content.Context;
import com.alipay.sdk.encrypt.C0969c;
import com.alipay.sdk.net.C0973a;
import com.alipay.sdk.packet.AbstractC0980e;
import com.alipay.sdk.packet.C0977b;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.sys.C0990b;
import com.alipay.sdk.util.C0996c;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.packet.impl.e */
/* loaded from: classes2.dex */
public class C0984e extends AbstractC0980e {
    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: a */
    protected String mo4505a(C0988a c0988a, String str, JSONObject jSONObject) {
        return str;
    }

    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: a */
    protected JSONObject mo4503a() {
        return null;
    }

    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: a */
    protected Map<String, String> mo4504a(boolean z, String str) {
        return new HashMap();
    }

    @Override // com.alipay.sdk.packet.AbstractC0980e
    /* renamed from: a */
    public C0977b mo4506a(C0988a c0988a, Context context, String str) throws Throwable {
        C0996c.m4435b("mspl", "mdap post");
        byte[] m4549a = C0969c.m4549a(str.getBytes(Charset.forName("UTF-8")));
        HashMap hashMap = new HashMap();
        hashMap.put("utdId", C0990b.m4478a().m4473e());
        hashMap.put("logHeader", "RAW");
        hashMap.put("bizCode", "alipaysdk");
        hashMap.put("productId", "alipaysdk_android");
        hashMap.put("Content-Encoding", "Gzip");
        hashMap.put("productVersion", "15.7.4");
        C0973a.C0975b m4539a = C0973a.m4539a(context, new C0973a.C0974a("https://loggw-exsdk.alipay.com/loggw/logUpload.do", hashMap, m4549a));
        C0996c.m4435b("mspl", "mdap got " + m4539a);
        if (m4539a == null) {
            throw new RuntimeException("Response is null");
        }
        boolean m4516a = AbstractC0980e.m4516a(m4539a);
        try {
            byte[] bArr = m4539a.f994c;
            if (m4516a) {
                bArr = C0969c.m4548b(bArr);
            }
            return new C0977b("", new String(bArr, Charset.forName("UTF-8")));
        } catch (Exception e) {
            C0996c.m4436a(e);
            return null;
        }
    }
}
