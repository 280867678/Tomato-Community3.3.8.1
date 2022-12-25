package com.alipay.sdk.data;

import android.content.Context;
import com.alipay.sdk.packet.C0977b;
import com.alipay.sdk.packet.impl.C0982b;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C0996c;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.data.b */
/* loaded from: classes2.dex */
public class RunnableC0964b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C0988a f976a;

    /* renamed from: b */
    final /* synthetic */ Context f977b;

    /* renamed from: c */
    final /* synthetic */ C0962a f978c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC0964b(C0962a c0962a, C0988a c0988a, Context context) {
        this.f978c = c0962a;
        this.f976a = c0988a;
        this.f977b = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            C0977b m4514a = new C0982b().m4514a(this.f976a, this.f977b);
            if (m4514a == null) {
                return;
            }
            this.f978c.m4589b(m4514a.m4528b());
            this.f978c.m4594a(C0988a.m4495a());
        } catch (Throwable th) {
            C0996c.m4436a(th);
        }
    }
}
