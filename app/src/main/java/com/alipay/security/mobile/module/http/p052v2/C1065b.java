package com.alipay.security.mobile.module.http.p052v2;

import android.content.Context;
import com.alipay.security.mobile.module.http.AbstractC1056a;
import com.alipay.security.mobile.module.http.C1059d;
import com.alipay.security.mobile.module.http.model.C1061b;
import com.alipay.security.mobile.module.http.model.C1062c;
import com.alipay.security.mobile.module.http.model.C1063d;

/* renamed from: com.alipay.security.mobile.module.http.v2.b */
/* loaded from: classes2.dex */
public class C1065b implements AbstractC1064a {

    /* renamed from: a */
    private static AbstractC1064a f1162a;

    /* renamed from: b */
    private static AbstractC1056a f1163b;

    /* renamed from: a */
    public static AbstractC1064a m4188a(Context context, String str) {
        if (context == null) {
            return null;
        }
        if (f1162a == null) {
            f1163b = C1059d.m4195a(context, str);
            f1162a = new C1065b();
        }
        return f1162a;
    }

    @Override // com.alipay.security.mobile.module.http.p052v2.AbstractC1064a
    /* renamed from: a */
    public C1062c mo4187a(C1063d c1063d) {
        return C1061b.m4192a(f1163b.mo4199a(C1061b.m4193a(c1063d)));
    }

    @Override // com.alipay.security.mobile.module.http.p052v2.AbstractC1064a
    /* renamed from: a */
    public boolean mo4186a(String str) {
        return f1163b.mo4197a(str);
    }
}
