package com.p065io.liquidlink.p070e;

import android.text.TextUtils;
import com.p089pm.liquidlink.p091b.C3048a;
import com.p089pm.liquidlink.p091b.C3049b;
import com.p089pm.liquidlink.p091b.EnumC3050c;
import com.p089pm.liquidlink.p092c.C3055c;
import java.util.Collections;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.io.liquidlink.e.d */
/* loaded from: classes3.dex */
public class RunnableC2142d implements Runnable {

    /* renamed from: a */
    final /* synthetic */ HandlerC2139a f1409a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC2142d(HandlerC2139a handlerC2139a) {
        this.f1409a = handlerC2139a;
    }

    @Override // java.lang.Runnable
    public void run() {
        String m4018a;
        if (!this.f1409a.f1427d.m4106a()) {
            String m4050c = this.f1409a.f1431h.m4050c();
            if (!C3055c.f1826a) {
                return;
            }
            C3055c.m3734b("初始化时错误：" + m4050c, new Object[0]);
        } else if (!this.f1409a.f1428e.m4062f()) {
            if (!C3055c.f1826a) {
                return;
            }
            C3055c.m3734b("registerStatsEnabled is disable", new Object[0]);
        } else {
            C3048a m3749a = C3048a.m3749a(true);
            m4018a = this.f1409a.m4018a(true, "stats/register");
            C3049b m3752a = m3749a.m3752a(m4018a, this.f1409a.m3997i(), Collections.emptyMap());
            this.f1409a.m4001d(m3752a.m3738e());
            if (m3752a.m3748a() != EnumC3050c.SUCCESS) {
                if (!C3055c.f1826a) {
                    return;
                }
                C3055c.m3732d("statRegister fail : %s", m3752a.m3742c());
                return;
            }
            if (C3055c.f1826a) {
                C3055c.m3734b("statRegister success : %s", m3752a.m3740d());
            }
            if (TextUtils.isEmpty(m3752a.m3742c()) || !C3055c.f1826a) {
                return;
            }
            C3055c.m3733c("statRegister warning : %s", m3752a.m3742c());
        }
    }
}
