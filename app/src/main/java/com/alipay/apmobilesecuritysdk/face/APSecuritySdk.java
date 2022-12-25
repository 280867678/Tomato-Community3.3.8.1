package com.alipay.apmobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.otherid.UmidSdkWrapper;
import com.alipay.apmobilesecuritysdk.otherid.UtdidWrapper;
import com.alipay.apmobilesecuritysdk.p041a.C0917a;
import com.alipay.apmobilesecuritysdk.p042b.C0918a;
import com.alipay.apmobilesecuritysdk.p045e.C0927a;
import com.alipay.apmobilesecuritysdk.p045e.C0930d;
import com.alipay.apmobilesecuritysdk.p045e.C0933g;
import com.alipay.apmobilesecuritysdk.p045e.C0934h;
import com.alipay.apmobilesecuritysdk.p045e.C0935i;
import com.alipay.apmobilesecuritysdk.p046f.C0937b;
import com.alipay.security.mobile.module.p047a.C1037a;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class APSecuritySdk {

    /* renamed from: a */
    private static APSecuritySdk f873a;

    /* renamed from: c */
    private static Object f874c = new Object();

    /* renamed from: b */
    private Context f875b;

    /* loaded from: classes2.dex */
    public interface InitResultListener {
        void onResult(TokenResult tokenResult);
    }

    /* loaded from: classes2.dex */
    public class TokenResult {
        public String apdid;
        public String apdidToken;
        public String clientKey;
        public String umidToken;

        public TokenResult() {
        }
    }

    private APSecuritySdk(Context context) {
        this.f875b = context;
    }

    public static APSecuritySdk getInstance(Context context) {
        if (f873a == null) {
            synchronized (f874c) {
                if (f873a == null) {
                    f873a = new APSecuritySdk(context);
                }
            }
        }
        return f873a;
    }

    public static String getUtdid(Context context) {
        return UtdidWrapper.getUtdid(context);
    }

    public String getApdidToken() {
        String m4791a = C0917a.m4791a(this.f875b, "");
        if (C1037a.m4303a(m4791a)) {
            initToken(0, new HashMap(), null);
        }
        return m4791a;
    }

    public String getSdkName() {
        return "APPSecuritySDK-ALIPAYSDK";
    }

    public String getSdkVersion() {
        return "3.4.0.201910161639";
    }

    public synchronized TokenResult getTokenResult() {
        TokenResult tokenResult;
        tokenResult = new TokenResult();
        try {
            tokenResult.apdidToken = C0917a.m4791a(this.f875b, "");
            tokenResult.clientKey = C0934h.m4724f(this.f875b);
            tokenResult.apdid = C0917a.m4792a(this.f875b);
            tokenResult.umidToken = UmidSdkWrapper.getSecurityToken(this.f875b);
            if (C1037a.m4303a(tokenResult.apdid) || C1037a.m4303a(tokenResult.apdidToken) || C1037a.m4303a(tokenResult.clientKey)) {
                initToken(0, new HashMap(), null);
            }
        } catch (Throwable unused) {
        }
        return tokenResult;
    }

    public void initToken(int i, Map<String, String> map, final InitResultListener initResultListener) {
        C0918a.m4786a().m4785a(i);
        String m4732b = C0934h.m4732b(this.f875b);
        String m4783c = C0918a.m4786a().m4783c();
        if (C1037a.m4299b(m4732b) && !C1037a.m4302a(m4732b, m4783c)) {
            C0927a.m4765a(this.f875b);
            C0930d.m4758a(this.f875b);
            C0933g.m4740a(this.f875b);
            C0935i.m4703h();
        }
        if (!C1037a.m4302a(m4732b, m4783c)) {
            C0934h.m4729c(this.f875b, m4783c);
        }
        String m4300a = C1037a.m4300a(map, "utdid", "");
        String m4300a2 = C1037a.m4300a(map, "tid", "");
        String m4300a3 = C1037a.m4300a(map, "userId", "");
        if (C1037a.m4303a(m4300a)) {
            m4300a = UtdidWrapper.getUtdid(this.f875b);
        }
        final HashMap hashMap = new HashMap();
        hashMap.put("utdid", m4300a);
        hashMap.put("tid", m4300a2);
        hashMap.put("userId", m4300a3);
        hashMap.put("appName", "");
        hashMap.put("appKeyClient", "");
        hashMap.put("appchannel", "");
        hashMap.put("rpcVersion", "8");
        C0937b.m4698a().m4696a(new Runnable() { // from class: com.alipay.apmobilesecuritysdk.face.APSecuritySdk.1
            @Override // java.lang.Runnable
            public void run() {
                new C0917a(APSecuritySdk.this.f875b).m4790a(hashMap);
                InitResultListener initResultListener2 = initResultListener;
                if (initResultListener2 != null) {
                    initResultListener2.onResult(APSecuritySdk.this.getTokenResult());
                }
            }
        });
    }
}
