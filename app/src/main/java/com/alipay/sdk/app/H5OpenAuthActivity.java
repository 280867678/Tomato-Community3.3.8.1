package com.alipay.sdk.app;

import android.content.Intent;
import android.net.Uri;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;

/* loaded from: classes2.dex */
public class H5OpenAuthActivity extends H5PayActivity {

    /* renamed from: a */
    private boolean f890a = false;

    @Override // com.alipay.sdk.app.H5PayActivity
    /* renamed from: a */
    public void mo4684a() {
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        try {
            C0988a m4482a = C0988a.C0989a.m4482a(intent);
            if (m4482a == null) {
                finish();
                return;
            }
            try {
                super.startActivity(intent);
                Uri data = intent != null ? intent.getData() : null;
                if (data == null || !data.toString().startsWith("alipays://platformapi/startapp")) {
                    return;
                }
                finish();
            } catch (Throwable th) {
                C0954a.m4631a(m4482a, "biz", "StartActivityEx", th, (intent == null || intent.getData() == null) ? "null" : intent.getData().toString());
                this.f890a = true;
                throw th;
            }
        } catch (Throwable unused) {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alipay.sdk.app.H5PayActivity, android.app.Activity
    public void onDestroy() {
        if (this.f890a) {
            try {
                C0988a m4482a = C0988a.C0989a.m4482a(getIntent());
                if (m4482a != null) {
                    C0954a.m4629b(this, m4482a, "", m4482a.f1013p);
                }
            } catch (Throwable unused) {
            }
        }
        super.onDestroy();
    }
}
