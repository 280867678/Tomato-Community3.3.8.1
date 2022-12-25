package com.alipay.sdk.app;

/* loaded from: classes2.dex */
public class H5AuthActivity extends H5PayActivity {
    @Override // com.alipay.sdk.app.H5PayActivity
    /* renamed from: a */
    public void mo4684a() {
        Object obj = AuthTask.f887a;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception unused) {
            }
        }
    }
}
