package com.p065io.liquidlink.p070e;

import android.net.Uri;
import android.text.TextUtils;
import com.p065io.liquidlink.p073h.AbstractC2170a;
import com.p089pm.liquidlink.listener.AppWakeUpListener;
import com.p089pm.liquidlink.model.AppData;
import com.p089pm.liquidlink.model.Error;
import com.p089pm.liquidlink.p090a.C3044b;
import com.p089pm.liquidlink.p091b.C3049b;
import com.p089pm.liquidlink.p091b.EnumC3050c;
import com.p089pm.liquidlink.p092c.C3055c;
import org.json.JSONException;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.io.liquidlink.e.k */
/* loaded from: classes3.dex */
public class C2149k implements AbstractC2170a {

    /* renamed from: a */
    final /* synthetic */ AppWakeUpListener f1417a;

    /* renamed from: b */
    final /* synthetic */ Uri f1418b;

    /* renamed from: c */
    final /* synthetic */ HandlerC2139a f1419c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C2149k(HandlerC2139a handlerC2139a, AppWakeUpListener appWakeUpListener, Uri uri) {
        this.f1419c = handlerC2139a;
        this.f1417a = appWakeUpListener;
        this.f1418b = uri;
    }

    @Override // com.p065io.liquidlink.p073h.AbstractC2170a
    /* renamed from: a */
    public void mo3924a(C3049b c3049b) {
        if (c3049b.m3748a() != EnumC3050c.SUCCESS) {
            if (C3055c.f1826a) {
                C3055c.m3732d("decodeWakeUp fail : %s", c3049b.m3742c());
            }
            AppWakeUpListener appWakeUpListener = this.f1417a;
            if (appWakeUpListener == null) {
                return;
            }
            appWakeUpListener.onWakeUpFinish(null, new Error(c3049b.m3744b(), c3049b.m3742c()));
            return;
        }
        if (C3055c.f1826a) {
            C3055c.m3734b("decodeWakeUp success : %s", c3049b.m3740d());
        }
        if (!TextUtils.isEmpty(c3049b.m3742c()) && C3055c.f1826a) {
            C3055c.m3733c("decodeWakeUp warning : %s", c3049b.m3742c());
        }
        try {
            AppData appData = new AppData();
            if (c3049b.m3744b() == 1) {
                appData = this.f1419c.m4012f(c3049b.m3740d());
            } else {
                C3044b m3755d = C3044b.m3755d(c3049b.m3740d());
                appData.setChannel(m3755d.m3760a());
                appData.setData(m3755d.m3758b());
            }
            if (this.f1417a != null) {
                this.f1417a.onWakeUpFinish(appData, null);
            }
            if (appData == null || appData.isEmpty()) {
                return;
            }
            this.f1419c.m4005a(this.f1418b);
        } catch (JSONException e) {
            if (C3055c.f1826a) {
                C3055c.m3732d("decodeWakeUp error : %s", e.toString());
            }
            AppWakeUpListener appWakeUpListener2 = this.f1417a;
            if (appWakeUpListener2 == null) {
                return;
            }
            appWakeUpListener2.onWakeUpFinish(null, null);
        }
    }
}
