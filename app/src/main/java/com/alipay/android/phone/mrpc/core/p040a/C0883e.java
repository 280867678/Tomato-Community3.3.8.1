package com.alipay.android.phone.mrpc.core.p040a;

import com.alipay.android.phone.mrpc.core.RpcException;
import com.alipay.p037a.p038a.C0868f;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.util.ArrayList;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/* renamed from: com.alipay.android.phone.mrpc.core.a.e */
/* loaded from: classes2.dex */
public final class C0883e extends AbstractC0880b {

    /* renamed from: c */
    private int f758c;

    /* renamed from: d */
    private Object f759d;

    public C0883e(int i, String str, Object obj) {
        super(str, obj);
        this.f758c = i;
    }

    @Override // com.alipay.android.phone.mrpc.core.p040a.AbstractC0884f
    /* renamed from: a */
    public final void mo4870a(Object obj) {
        this.f759d = obj;
    }

    @Override // com.alipay.android.phone.mrpc.core.p040a.AbstractC0884f
    /* renamed from: a */
    public final byte[] mo4871a() {
        try {
            ArrayList arrayList = new ArrayList();
            if (this.f759d != null) {
                arrayList.add(new BasicNameValuePair("extParam", C0868f.m4881a(this.f759d)));
            }
            arrayList.add(new BasicNameValuePair("operationType", this.f756a));
            StringBuilder sb = new StringBuilder();
            sb.append(this.f758c);
            arrayList.add(new BasicNameValuePair(DatabaseFieldConfigLoader.FIELD_NAME_ID, sb.toString()));
            new StringBuilder("mParams is:").append(this.f757b);
            arrayList.add(new BasicNameValuePair("requestData", this.f757b == null ? "[]" : C0868f.m4881a(this.f757b)));
            return URLEncodedUtils.format(arrayList, EncryptUtil.CHARSET).getBytes();
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("request  =");
            sb2.append(this.f757b);
            sb2.append(":");
            sb2.append(e);
            throw new RpcException(9, sb2.toString() == null ? "" : e.getMessage(), e);
        }
    }
}
