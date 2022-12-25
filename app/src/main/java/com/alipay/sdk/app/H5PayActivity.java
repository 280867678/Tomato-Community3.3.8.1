package com.alipay.sdk.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.data.C0962a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C1008n;
import com.alipay.sdk.widget.AbstractC1018g;
import com.alipay.sdk.widget.C1019h;
import com.alipay.sdk.widget.C1021j;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;

/* loaded from: classes2.dex */
public class H5PayActivity extends Activity {

    /* renamed from: a */
    private AbstractC1018g f891a;

    /* renamed from: b */
    private String f892b;

    /* renamed from: c */
    private String f893c;

    /* renamed from: d */
    private String f894d;

    /* renamed from: e */
    private String f895e;

    /* renamed from: f */
    private boolean f896f;

    /* renamed from: g */
    private String f897g;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        m4683b();
        super.onCreate(bundle);
        try {
            C0988a m4482a = C0988a.C0989a.m4482a(getIntent());
            if (m4482a == null) {
                finish();
                return;
            }
            if (!C0962a.m4581j().m4590b()) {
                setRequestedOrientation(1);
            } else {
                setRequestedOrientation(3);
            }
            try {
                Bundle extras = getIntent().getExtras();
                this.f892b = extras.getString("url", null);
                if (!C1008n.m4370d(this.f892b)) {
                    finish();
                    return;
                }
                this.f894d = extras.getString("cookie", null);
                this.f893c = extras.getString("method", null);
                this.f895e = extras.getString("title", null);
                this.f897g = extras.getString(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, "v1");
                this.f896f = extras.getBoolean("backisexit", false);
                try {
                    if ("v2".equals(this.f897g)) {
                        C1021j c1021j = new C1021j(this, m4482a);
                        setContentView(c1021j);
                        c1021j.m4341a(this.f895e, this.f893c, this.f896f);
                        c1021j.mo4343a(this.f892b);
                        this.f891a = c1021j;
                        return;
                    }
                    this.f891a = new C1019h(this, m4482a);
                    setContentView(this.f891a);
                    this.f891a.m4348a(this.f892b, this.f894d);
                    this.f891a.mo4343a(this.f892b);
                } catch (Throwable th) {
                    C0954a.m4632a(m4482a, "biz", "GetInstalledAppEx", th);
                    finish();
                }
            } catch (Exception unused) {
                finish();
            }
        } catch (Exception unused2) {
            finish();
        }
    }

    /* renamed from: b */
    private void m4683b() {
        try {
            super.requestWindowFeature(1);
        } catch (Throwable th) {
            C0996c.m4436a(th);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        AbstractC1018g abstractC1018g = this.f891a;
        if (abstractC1018g instanceof C1019h) {
            abstractC1018g.mo4339b();
            return;
        }
        if (!abstractC1018g.mo4339b()) {
            super.onBackPressed();
        }
        C0952j.m4646a(C0952j.m4643c());
        finish();
    }

    @Override // android.app.Activity
    public void finish() {
        mo4684a();
        super.finish();
    }

    /* renamed from: a */
    public void mo4684a() {
        Object obj = PayTask.f903a;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception unused) {
            }
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        AbstractC1018g abstractC1018g = this.f891a;
        if (abstractC1018g != null) {
            abstractC1018g.mo4346a();
        }
    }

    @Override // android.app.Activity
    public void setRequestedOrientation(int i) {
        try {
            super.setRequestedOrientation(i);
        } catch (Throwable th) {
            try {
                C0954a.m4632a(C0988a.C0989a.m4482a(getIntent()), "biz", "H5PayDataAnalysisError", th);
            } catch (Throwable unused) {
            }
        }
    }
}
