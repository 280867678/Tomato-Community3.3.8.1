package com.alipay.sdk.sys;

import android.content.Context;
import com.alipay.sdk.data.C0965c;
import com.alipay.sdk.util.C0996c;
import com.p097ta.utdid2.device.UTDevice;
import java.io.File;

/* renamed from: com.alipay.sdk.sys.b */
/* loaded from: classes2.dex */
public class C0990b {

    /* renamed from: a */
    private static C0990b f1022a;

    /* renamed from: b */
    private Context f1023b;

    private C0990b() {
    }

    /* renamed from: a */
    public static C0990b m4478a() {
        if (f1022a == null) {
            f1022a = new C0990b();
        }
        return f1022a;
    }

    /* renamed from: b */
    public Context m4476b() {
        return this.f1023b;
    }

    /* renamed from: a */
    public void m4477a(Context context) {
        C0965c.m4570b();
        this.f1023b = context.getApplicationContext();
    }

    /* renamed from: c */
    public C0965c m4475c() {
        return C0965c.m4570b();
    }

    /* renamed from: d */
    public static boolean m4474d() {
        for (String str : new String[]{"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"}) {
            if (new File(str).exists()) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: e */
    public String m4473e() {
        try {
            return UTDevice.getUtdid(this.f1023b);
        } catch (Throwable th) {
            C0996c.m4436a(th);
            return "getUtdidEx";
        }
    }
}
