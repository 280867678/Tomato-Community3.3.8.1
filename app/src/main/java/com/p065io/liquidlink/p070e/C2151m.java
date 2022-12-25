package com.p065io.liquidlink.p070e;

import android.text.TextUtils;
import com.p065io.liquidlink.p073h.AbstractC2170a;
import com.p089pm.liquidlink.listener.AppInstallListener;
import com.p089pm.liquidlink.model.AppData;
import com.p089pm.liquidlink.model.Error;
import com.p089pm.liquidlink.p090a.C3044b;
import com.p089pm.liquidlink.p091b.C3049b;
import com.p089pm.liquidlink.p091b.EnumC3050c;
import com.p089pm.liquidlink.p092c.C3055c;
import org.json.JSONException;

/* renamed from: com.io.liquidlink.e.m */
/* loaded from: classes3.dex */
class C2151m implements AbstractC2170a {

    /* renamed from: a */
    final /* synthetic */ AppInstallListener f1422a;

    /* renamed from: b */
    final /* synthetic */ HandlerC2139a f1423b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C2151m(HandlerC2139a handlerC2139a, AppInstallListener appInstallListener) {
        this.f1423b = handlerC2139a;
        this.f1422a = appInstallListener;
    }

    @Override // com.p065io.liquidlink.p073h.AbstractC2170a
    /* renamed from: a */
    public void mo3924a(C3049b c3049b) {
        C3055c.m3735a("LLink", "init result: " + c3049b.toString());
        if (c3049b.m3748a() != EnumC3050c.SUCCESS) {
            if (C3055c.f1826a) {
                C3055c.m3732d("decodeInstall fail : %s", c3049b.m3742c());
            }
            AppInstallListener appInstallListener = this.f1422a;
            if (appInstallListener == null) {
                return;
            }
            appInstallListener.onInstallFinish(this.f1423b.m4014c(), new Error(c3049b.m3744b(), c3049b.m3742c()));
            return;
        }
        if (C3055c.f1826a) {
            C3055c.m3734b("decodeInstall success : %s", c3049b.m3740d());
        }
        if (!TextUtils.isEmpty(c3049b.m3742c()) && C3055c.f1826a) {
            C3055c.m3733c("decodeInstall warning : %s", c3049b.m3742c());
        }
        try {
            C3044b m3755d = C3044b.m3755d(c3049b.m3740d());
            AppData appData = new AppData();
            appData.setChannel(m3755d.m3760a());
            appData.setData(m3755d.m3758b());
            if (this.f1422a == null) {
                return;
            }
            this.f1422a.onInstallFinish(appData, null);
        } catch (JSONException e) {
            if (C3055c.f1826a) {
                C3055c.m3732d("decodeInstall error : %s", e.toString());
            }
            AppInstallListener appInstallListener2 = this.f1422a;
            if (appInstallListener2 == null) {
                return;
            }
            appInstallListener2.onInstallFinish(this.f1423b.m4014c(), null);
        }
    }
}
