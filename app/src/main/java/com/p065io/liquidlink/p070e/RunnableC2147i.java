package com.p065io.liquidlink.p070e;

import com.p065io.liquidlink.C2122a;
import com.p065io.liquidlink.p066a.C2125c;
import com.p065io.liquidlink.p068c.C2133d;
import com.p089pm.liquidlink.p090a.C3043a;
import com.p089pm.liquidlink.p091b.C3048a;
import com.p089pm.liquidlink.p091b.C3049b;
import com.p089pm.liquidlink.p091b.EnumC3050c;
import com.p089pm.liquidlink.p092c.C3055c;
import java.util.IdentityHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.io.liquidlink.e.i */
/* loaded from: classes3.dex */
public class RunnableC2147i implements Runnable {

    /* renamed from: a */
    final /* synthetic */ HandlerC2139a f1414a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC2147i(HandlerC2139a handlerC2139a) {
        this.f1414a = handlerC2139a;
    }

    /* renamed from: a */
    private long m4009a(int i) {
        if (i < 3) {
            return 1L;
        }
        return i < 8 ? 5L : 10L;
    }

    @Override // java.lang.Runnable
    public void run() {
        C2125c m4022a;
        String m4018a;
        C3049b m3752a;
        C2122a c2122a;
        C3043a c3043a;
        C3043a m4103b = this.f1414a.f1427d.m4103b();
        if (m4103b == null) {
            HandlerC2139a handlerC2139a = this.f1414a;
            m4103b = handlerC2139a.f1431h.m4055a(handlerC2139a.f1429f);
            if (m4103b == C3043a.f1801a) {
                m4103b = this.f1414a.f1431h.m4058a();
                HandlerC2139a handlerC2139a2 = this.f1414a;
                handlerC2139a2.f1431h.m4054a(handlerC2139a2.f1429f, m4103b);
            }
        }
        if (m4103b == C3043a.f1801a) {
            this.f1414a.f1431h.m4045g();
        }
        if (m4103b != C3043a.f1801a && m4103b != C3043a.f1804d && m4103b != C3043a.f1805e) {
            this.f1414a.f1428e.m4074a(this.f1414a.f1431h.m4048d());
            this.f1414a.f1427d.m4104a(m4103b);
            this.f1414a.f1428e.m4059i();
            return;
        }
        this.f1414a.f1427d.m4104a(C3043a.f1802b);
        C2125c m4100a = this.f1414a.f1433j.m4100a();
        this.f1414a.f1433j.m4094b();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        identityHashMap.put("model", this.f1414a.f1432i.m3965f());
        identityHashMap.put("buildId", this.f1414a.f1432i.m3964g());
        identityHashMap.put("buildDisplay", this.f1414a.f1432i.m3963h());
        identityHashMap.put("brand", this.f1414a.f1432i.m3962i());
        identityHashMap.putAll(this.f1414a.f1432i.m3957n());
        boolean m4038a = C2133d.m4039a().m4038a(this.f1414a.f1426c);
        if (m4038a && C3055c.f1826a) {
            C3055c.m3734b("check device is emulator", new Object[0]);
        }
        identityHashMap.put("simulator", String.valueOf(m4038a));
        identityHashMap.put("apkInfo", this.f1414a.f1432i.m3971a());
        m4022a = this.f1414a.m4022a(m4100a);
        if (m4022a != null) {
            if (m4022a.m4084c(2)) {
                identityHashMap.put("pbHtml", m4022a.m4088b());
            }
            if (m4022a.m4084c(1)) {
                identityHashMap.put("pbText", m4022a.m4092a());
            }
        }
        int i = 0;
        do {
            C3048a m3749a = C3048a.m3749a(false);
            m4018a = this.f1414a.m4018a(false, "init");
            m3752a = m3749a.m3752a(m4018a, this.f1414a.m3997i(), identityHashMap);
            try {
                if (this.f1414a.f1427d.m4102b(m4009a(i)) != null) {
                    i = 0;
                }
            } catch (InterruptedException unused) {
            }
            i++;
        } while (m3752a.m3748a() == EnumC3050c.FAIL);
        this.f1414a.m4001d(m3752a.m3738e());
        if (m3752a.m3748a() == EnumC3050c.SUCCESS) {
            this.f1414a.f1431h.m4051b(m3752a.m3740d());
            c2122a = this.f1414a.f1427d;
            c3043a = C3043a.f1803c;
        } else if (m3752a.m3748a() != EnumC3050c.ERROR) {
            if (m3752a.m3748a() == EnumC3050c.FAIL) {
                this.f1414a.f1431h.m4049c(m3752a.m3742c());
                c2122a = this.f1414a.f1427d;
                c3043a = C3043a.f1804d;
            }
            this.f1414a.f1427d.m4101c();
            HandlerC2139a handlerC2139a3 = this.f1414a;
            handlerC2139a3.f1431h.m4054a(handlerC2139a3.f1429f, handlerC2139a3.f1427d.m4103b());
        } else {
            this.f1414a.f1431h.m4049c(m3752a.m3742c());
            c2122a = this.f1414a.f1427d;
            c3043a = C3043a.f1805e;
        }
        c2122a.m4104a(c3043a);
        this.f1414a.f1427d.m4101c();
        HandlerC2139a handlerC2139a32 = this.f1414a;
        handlerC2139a32.f1431h.m4054a(handlerC2139a32.f1429f, handlerC2139a32.f1427d.m4103b());
    }
}
