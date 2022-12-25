package com.p065io.liquidlink.p070e;

import android.text.TextUtils;
import com.p089pm.liquidlink.p091b.C3048a;
import com.p089pm.liquidlink.p091b.C3049b;
import com.p089pm.liquidlink.p091b.EnumC3050c;
import com.p089pm.liquidlink.p092c.C3055c;
import java.util.HashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.io.liquidlink.e.c */
/* loaded from: classes3.dex */
public class RunnableC2141c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f1407a;

    /* renamed from: b */
    final /* synthetic */ HandlerC2139a f1408b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC2141c(HandlerC2139a handlerC2139a, String str) {
        this.f1408b = handlerC2139a;
        this.f1407a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        String m4018a;
        if (!this.f1408b.f1427d.m4106a()) {
            String m4050c = this.f1408b.f1431h.m4050c();
            if (!C3055c.f1826a) {
                return;
            }
            C3055c.m3734b("初始化时错误：" + m4050c, new Object[0]);
        } else if (!this.f1408b.f1428e.m4070b()) {
            if (!C3055c.f1826a) {
                return;
            }
            C3055c.m3734b("wakeupStatsEnabled is disable", new Object[0]);
        } else {
            HashMap hashMap = new HashMap();
            if (!TextUtils.isEmpty(this.f1407a)) {
                hashMap.put("url", this.f1407a);
            }
            C3048a m3749a = C3048a.m3749a(true);
            m4018a = this.f1408b.m4018a(true, "stats/wakeup");
            C3049b m3752a = m3749a.m3752a(m4018a, this.f1408b.m3997i(), hashMap);
            this.f1408b.m4001d(m3752a.m3738e());
            if (m3752a.m3748a() != EnumC3050c.SUCCESS) {
                if (!C3055c.f1826a) {
                    return;
                }
                C3055c.m3732d("statWakeup fail : %s", m3752a.m3742c());
                return;
            }
            if (C3055c.f1826a) {
                C3055c.m3734b("statWakeup success : %s", m3752a.m3740d());
            }
            if (TextUtils.isEmpty(m3752a.m3742c()) || !C3055c.f1826a) {
                return;
            }
            C3055c.m3733c("statWakeup warning : %s", m3752a.m3742c());
        }
    }
}
