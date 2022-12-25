package com.alipay.apmobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.otherid.UtdidWrapper;
import com.alipay.apmobilesecuritysdk.p041a.C0917a;
import com.alipay.apmobilesecuritysdk.p046f.C0937b;
import com.alipay.security.mobile.module.p047a.C1037a;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class TMNTokenClient {

    /* renamed from: a */
    private static TMNTokenClient f880a;

    /* renamed from: b */
    private Context f881b;

    /* loaded from: classes2.dex */
    public interface InitResultListener {
        void onResult(String str, int i);
    }

    private TMNTokenClient(Context context) {
        this.f881b = null;
        if (context != null) {
            this.f881b = context;
            return;
        }
        throw new IllegalArgumentException("TMNTokenClient initialization error: context is null.");
    }

    public static TMNTokenClient getInstance(Context context) {
        if (f880a == null) {
            synchronized (TMNTokenClient.class) {
                if (f880a == null) {
                    f880a = new TMNTokenClient(context);
                }
            }
        }
        return f880a;
    }

    public void intiToken(final String str, String str2, String str3, final InitResultListener initResultListener) {
        if (C1037a.m4303a(str) && initResultListener != null) {
            initResultListener.onResult("", 2);
        }
        if (C1037a.m4303a(str2) && initResultListener != null) {
            initResultListener.onResult("", 3);
        }
        final HashMap hashMap = new HashMap();
        hashMap.put("utdid", UtdidWrapper.getUtdid(this.f881b));
        hashMap.put("tid", "");
        hashMap.put("userId", "");
        hashMap.put("appName", str);
        hashMap.put("appKeyClient", str2);
        hashMap.put("appchannel", "openapi");
        hashMap.put("sessionId", str3);
        hashMap.put("rpcVersion", "8");
        C0937b.m4698a().m4696a(new Runnable() { // from class: com.alipay.apmobilesecuritysdk.face.TMNTokenClient.1
            @Override // java.lang.Runnable
            public void run() {
                int m4790a = new C0917a(TMNTokenClient.this.f881b).m4790a(hashMap);
                InitResultListener initResultListener2 = initResultListener;
                if (initResultListener2 == null) {
                    return;
                }
                if (m4790a != 0) {
                    initResultListener2.onResult("", m4790a);
                    return;
                }
                initResultListener.onResult(C0917a.m4791a(TMNTokenClient.this.f881b, str), 0);
            }
        });
    }
}
