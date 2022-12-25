package com.p089pm.liquidlink;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.p065io.liquidlink.C2126b;
import com.p089pm.liquidlink.listener.AppInstallListener;
import com.p089pm.liquidlink.listener.AppWakeUpListener;
import com.p089pm.liquidlink.p092c.C3055c;

/* renamed from: com.pm.liquidlink.LiquidLink */
/* loaded from: classes3.dex */
public class LiquidLink {

    /* renamed from: a */
    private static C2126b f1799a;

    /* renamed from: b */
    private static volatile boolean f1800b;

    private LiquidLink() {
    }

    /* renamed from: a */
    private static String m3763a(Context context) {
        Bundle bundle;
        Object obj = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                obj = bundle.get("com.pm.liquidlink.APP_KEY");
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (obj != null) {
            try {
                return String.valueOf(obj);
            } catch (Exception unused2) {
                return "";
            }
        }
        return "";
    }

    /* renamed from: a */
    private static boolean m3764a() {
        if (!f1800b) {
            if (C3055c.f1826a) {
                C3055c.m3732d("请先调用 init(Context) 初始化", new Object[0]);
            }
            return false;
        }
        return true;
    }

    public static boolean checkYYB(Intent intent) {
        return f1799a.m4077b(intent);
    }

    public static void getInstall(AppInstallListener appInstallListener) {
        getInstall(appInstallListener, 0);
    }

    public static void getInstall(AppInstallListener appInstallListener, int i) {
        if (!m3764a()) {
            appInstallListener.onInstallFinish(null, null);
        } else {
            f1799a.m4082a(i, appInstallListener);
        }
    }

    public static boolean getWakeUpYYB(Intent intent, AppWakeUpListener appWakeUpListener) {
        if (m3764a() && checkYYB(intent)) {
            f1799a.m4079a(appWakeUpListener);
            return true;
        }
        return false;
    }

    public static void init(Context context) {
        init(context, m3763a(context));
    }

    public static void init(Context context, String str) {
        if (context != null) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("请在AndroidManifest.xml中配置LiquidLink提供的AppKey");
            }
            if (C3055c.f1826a) {
                C3055c.m3734b("SDK VERSION : %s", "2.5.0");
            }
            synchronized (LiquidLink.class) {
                if (!f1800b) {
                    if (f1799a == null) {
                        f1799a = C2126b.m4081a(context);
                        f1799a.m4078a(str);
                    }
                    f1800b = true;
                }
            }
            return;
        }
        throw new IllegalArgumentException("context不能为空");
    }
}
