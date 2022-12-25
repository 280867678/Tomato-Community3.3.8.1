package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AlipayResultActivity extends Activity {

    /* renamed from: a */
    public static final ConcurrentHashMap<String, AbstractC0941a> f886a = new ConcurrentHashMap<>();

    /* renamed from: com.alipay.sdk.app.AlipayResultActivity$a */
    /* loaded from: classes2.dex */
    public interface AbstractC0941a {
        /* renamed from: a */
        void mo4413a(int i, String str, String str2);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Intent intent = getIntent();
            String stringExtra = intent.getStringExtra("session");
            Bundle bundleExtra = intent.getBundleExtra("result");
            String stringExtra2 = intent.getStringExtra(AopConstants.SCENE);
            C0988a m4479a = C0988a.C0989a.m4479a(stringExtra);
            if (m4479a == null) {
                finish();
                return;
            }
            C0954a.m4628b(m4479a, "biz", "BSPSession", stringExtra + "|" + SystemClock.elapsedRealtime());
            if (TextUtils.equals("mqpSchemePay", stringExtra2)) {
                m4692a(stringExtra, bundleExtra);
                return;
            }
            if ((TextUtils.isEmpty(stringExtra) || bundleExtra == null) && intent.getData() != null) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(Base64.decode(intent.getData().getQuery(), 2), "UTF-8"));
                    JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                    stringExtra = jSONObject.getString("session");
                    C0954a.m4628b(m4479a, "biz", "BSPUriSession", stringExtra);
                    Bundle bundle2 = new Bundle();
                    try {
                        Iterator<String> keys = jSONObject2.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            bundle2.putString(next, jSONObject2.getString(next));
                        }
                        bundleExtra = bundle2;
                    } catch (Throwable th) {
                        th = th;
                        bundleExtra = bundle2;
                        C0954a.m4632a(m4479a, "biz", "BSPResEx", th);
                        C0954a.m4632a(m4479a, "biz", "ParseSchemeQueryError", th);
                        if (!TextUtils.isEmpty(stringExtra)) {
                        }
                        C0954a.m4629b(this, m4479a, "", m4479a.f1013p);
                        finish();
                        return;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            if (!TextUtils.isEmpty(stringExtra) || bundleExtra == null) {
                C0954a.m4629b(this, m4479a, "", m4479a.f1013p);
                finish();
                return;
            }
            C0954a.m4628b(m4479a, "biz", "PgReturn", "" + SystemClock.elapsedRealtime());
            OpenAuthTask.m4682a(stringExtra, 9000, "OK", bundleExtra);
            C0954a.m4629b(this, m4479a, "", m4479a.f1013p);
            finish();
        } catch (Throwable unused) {
            finish();
        }
    }

    /* renamed from: a */
    private void m4692a(String str, Bundle bundle) {
        AbstractC0941a remove = f886a.remove(str);
        if (remove == null) {
            return;
        }
        try {
            remove.mo4413a(bundle.getInt("endCode"), bundle.getString("memo"), bundle.getString("result"));
        } finally {
            finish();
        }
    }
}
