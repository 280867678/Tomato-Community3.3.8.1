package com.p065io.liquidlink;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import com.p065io.liquidlink.p067b.C2128b;
import com.p065io.liquidlink.p069d.C2135a;
import com.p065io.liquidlink.p070e.AbstractHandlerC2152n;
import com.p065io.liquidlink.p070e.HandlerC2139a;
import com.p089pm.liquidlink.listener.AppInstallListener;
import com.p089pm.liquidlink.listener.AppWakeUpListener;
import com.p089pm.liquidlink.p092c.C3053a;
import com.p089pm.liquidlink.p092c.C3055c;
import java.util.Set;

/* renamed from: com.io.liquidlink.b */
/* loaded from: classes3.dex */
public class C2126b {

    /* renamed from: a */
    private static volatile C2126b f1378a;

    /* renamed from: b */
    private AbstractHandlerC2152n f1379b;

    /* renamed from: c */
    private C2135a f1380c;

    private C2126b(Context context) {
        C2122a c2122a = new C2122a();
        C2128b c2128b = new C2128b();
        C2129c c2129c = new C2129c(new C2134d().m4035a(context, "FM_config", null));
        HandlerThread handlerThread = new HandlerThread("CoreHandler-Thread");
        handlerThread.start();
        this.f1379b = new HandlerC2139a(context, handlerThread.getLooper(), c2122a, c2129c, c2128b);
        this.f1380c = new C2135a(context, c2122a, c2129c, c2128b);
    }

    /* renamed from: a */
    public static C2126b m4081a(Context context) {
        if (f1378a == null) {
            synchronized (C2126b.class) {
                if (f1378a == null) {
                    f1378a = new C2126b(context);
                }
            }
        }
        return f1378a;
    }

    /* renamed from: a */
    private void m4080a(Uri uri, AppWakeUpListener appWakeUpListener) {
        if (C3055c.f1826a) {
            C3055c.m3734b("decodeWakeUp", new Object[0]);
        }
        this.f1379b.m4004a(uri, appWakeUpListener);
    }

    /* renamed from: a */
    public void m4082a(long j, AppInstallListener appInstallListener) {
        this.f1379b.m4006a(j, appInstallListener);
    }

    /* renamed from: a */
    public void m4079a(AppWakeUpListener appWakeUpListener) {
        m4080a((Uri) null, appWakeUpListener);
    }

    /* renamed from: a */
    public void m4078a(String str) {
        this.f1379b.m4002b(str);
        this.f1380c.m4031a(str);
        this.f1379b.m4000e();
    }

    /* renamed from: b */
    public boolean m4077b(Intent intent) {
        if (intent == null) {
            return false;
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.getBoolean("liquidlink_intent", false)) {
                return false;
            }
            String string = extras.getString(C3053a.f1821a);
            if (C3053a.f1822b.equalsIgnoreCase(string) || C3053a.f1823c.equalsIgnoreCase(string)) {
                return true;
            }
        }
        String action = intent.getAction();
        Set<String> categories = intent.getCategories();
        return action == null || categories == null || !action.equals("android.intent.action.MAIN") || !categories.contains("android.intent.category.LAUNCHER");
    }
}
