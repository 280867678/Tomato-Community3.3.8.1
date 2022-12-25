package com.alipay.android.phone.mrpc.core.p040a;

import com.alipay.android.phone.mrpc.core.RpcException;
import com.alipay.p037a.p038a.C0867e;
import java.lang.reflect.Type;
import org.json.JSONObject;

/* renamed from: com.alipay.android.phone.mrpc.core.a.d */
/* loaded from: classes2.dex */
public final class C0882d extends AbstractC0879a {
    public C0882d(Type type, byte[] bArr) {
        super(type, bArr);
    }

    @Override // com.alipay.android.phone.mrpc.core.p040a.AbstractC0881c
    /* renamed from: a */
    public final Object mo4872a() {
        try {
            String str = new String(this.f755b);
            StringBuilder sb = new StringBuilder("threadid = ");
            sb.append(Thread.currentThread().getId());
            sb.append("; rpc response:  ");
            sb.append(str);
            JSONObject jSONObject = new JSONObject(str);
            int i = jSONObject.getInt("resultStatus");
            if (i == 1000) {
                return this.f754a == String.class ? jSONObject.optString("result") : C0867e.m4882a(jSONObject.optString("result"), this.f754a);
            }
            throw new RpcException(Integer.valueOf(i), jSONObject.optString("tips"));
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("response  =");
            sb2.append(new String(this.f755b));
            sb2.append(":");
            sb2.append(e);
            throw new RpcException((Integer) 10, sb2.toString() == null ? "" : e.getMessage());
        }
    }
}
