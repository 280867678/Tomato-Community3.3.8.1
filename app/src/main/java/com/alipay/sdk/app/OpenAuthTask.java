package com.alipay.sdk.app;

import android.os.Bundle;
import com.alipay.sdk.util.C0996c;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class OpenAuthTask {

    /* renamed from: a */
    private static final Map<String, Callback> f898a = new ConcurrentHashMap();

    /* loaded from: classes2.dex */
    public interface Callback {
        void onResult(int i, String str, Bundle bundle);
    }

    /* loaded from: classes2.dex */
    public enum BizType {
        Invoice("20000920"),
        AccountAuth("20000067"),
        Deduct("60000157");
        
        private String appId;

        BizType(String str) {
            this.appId = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public static void m4682a(String str, int i, String str2, Bundle bundle) {
        Callback remove = f898a.remove(str);
        if (remove != null) {
            try {
                remove.onResult(i, str2, bundle);
            } catch (Throwable th) {
                C0996c.m4436a(th);
            }
        }
    }
}
