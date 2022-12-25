package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C0996c;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/* loaded from: classes2.dex */
public final class PayResultActivity extends Activity {

    /* renamed from: b */
    public static final HashMap<String, Object> f899b = new HashMap<>();

    /* renamed from: i */
    private C0988a f900i = null;

    /* renamed from: com.alipay.sdk.app.PayResultActivity$a */
    /* loaded from: classes2.dex */
    public static final class C0942a {

        /* renamed from: a */
        public static volatile String f901a;

        /* renamed from: b */
        public static volatile String f902b;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Intent intent = getIntent();
            if (!TextUtils.isEmpty(intent.getStringExtra("orderSuffix"))) {
                C0942a.f901a = intent.getStringExtra("phonecashier.pay.hash");
                String stringExtra = intent.getStringExtra("orderSuffix");
                String stringExtra2 = intent.getStringExtra("externalPkgName");
                this.f900i = C0988a.C0989a.m4482a(intent);
                if (this.f900i == null) {
                    finish();
                }
                m4680a(this, C0942a.f901a, stringExtra, stringExtra2);
                m4681a(this, 300);
                return;
            }
            if (this.f900i == null) {
                finish();
            }
            String stringExtra3 = intent.getStringExtra("phonecashier.pay.result");
            int intExtra = intent.getIntExtra("phonecashier.pay.resultOrderHash", 0);
            if (intExtra != 0 && TextUtils.equals(C0942a.f901a, String.valueOf(intExtra))) {
                if (!TextUtils.isEmpty(stringExtra3)) {
                    m4678a(stringExtra3, C0942a.f901a);
                } else {
                    m4679a(C0942a.f901a);
                }
                C0942a.f901a = "";
                m4681a(this, 300);
                return;
            }
            C0988a c0988a = this.f900i;
            C0954a.m4633a(c0988a, "biz", "SchemePayWrongHashEx", "Expected " + C0942a.f901a + ", got " + intExtra);
            m4679a(C0942a.f901a);
            m4681a(this, 300);
        } catch (Throwable unused) {
            finish();
        }
    }

    /* renamed from: a */
    private static void m4680a(Activity activity, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        Intent intent = new Intent();
        try {
            intent.setPackage("hk.alipay.wallet");
            intent.setData(Uri.parse("alipayhk://platformapi/startApp?appId=20000125&schemePaySession=" + URLEncoder.encode(str, "UTF-8") + "&orderSuffix=" + URLEncoder.encode(str2, "UTF-8") + "&packageName=" + URLEncoder.encode(str3, "UTF-8") + "&externalPkgName=" + URLEncoder.encode(str3, "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            C0996c.m4436a(e);
        }
        if (activity == null) {
            return;
        }
        try {
            activity.startActivity(intent);
        } catch (Throwable unused) {
            activity.finish();
        }
    }

    /* renamed from: a */
    private static void m4679a(String str) {
        C0942a.f902b = C0952j.m4643c();
        m4677a(f899b, str);
    }

    /* renamed from: a */
    private static void m4678a(String str, String str2) {
        C0942a.f902b = str;
        m4677a(f899b, str2);
    }

    /* renamed from: a */
    private static void m4681a(Activity activity, int i) {
        new Handler().postDelayed(new RunnableC0948f(activity), i);
    }

    /* renamed from: a */
    private static boolean m4677a(HashMap<String, Object> hashMap, String str) {
        Object obj;
        if (hashMap == null || str == null || (obj = hashMap.get(str)) == null) {
            return false;
        }
        synchronized (obj) {
            obj.notifyAll();
        }
        return true;
    }
}
