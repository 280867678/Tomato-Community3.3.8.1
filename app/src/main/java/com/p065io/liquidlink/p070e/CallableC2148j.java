package com.p065io.liquidlink.p070e;

import android.net.Uri;
import com.p065io.liquidlink.p066a.C2125c;
import com.p065io.liquidlink.p074i.C2173a;
import com.p089pm.liquidlink.p091b.C3048a;
import com.p089pm.liquidlink.p091b.C3049b;
import com.p089pm.liquidlink.p091b.EnumC3050c;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.io.liquidlink.e.j */
/* loaded from: classes3.dex */
public class CallableC2148j implements Callable {

    /* renamed from: a */
    final /* synthetic */ Uri f1415a;

    /* renamed from: b */
    final /* synthetic */ HandlerC2139a f1416b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CallableC2148j(HandlerC2139a handlerC2139a, Uri uri) {
        this.f1416b = handlerC2139a;
        this.f1415a = uri;
    }

    @Override // java.util.concurrent.Callable
    /* renamed from: a */
    public C3049b call() {
        C3049b c3049b;
        String m4018a;
        C3049b m3752a;
        String m4018a2;
        if (!this.f1416b.f1427d.m4106a()) {
            String m4050c = this.f1416b.f1431h.m4050c();
            C3049b c3049b2 = new C3049b(EnumC3050c.ERROR, -12);
            c3049b2.m3743b("初始化时错误：" + m4050c);
            return c3049b2;
        }
        Uri uri = this.f1415a;
        if (uri != null) {
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments == null || pathSegments.size() <= 0) {
                c3049b = new C3049b(EnumC3050c.SUCCESS, 1);
            } else if (pathSegments.get(0).equalsIgnoreCase("c")) {
                if (pathSegments.size() <= 1) {
                    C3049b c3049b3 = new C3049b(EnumC3050c.SUCCESS, 1);
                    c3049b3.m3741c("");
                    return c3049b3;
                }
                String m3920a = C2173a.m3920a(pathSegments.get(1), 8);
                C3049b c3049b4 = new C3049b(EnumC3050c.SUCCESS, 1);
                c3049b4.m3741c(m3920a);
                return c3049b4;
            } else if (pathSegments.get(0).equalsIgnoreCase("h")) {
                HashMap hashMap = new HashMap();
                hashMap.put("wakeupUrl", this.f1415a.toString());
                C3048a m3749a = C3048a.m3749a(true);
                m4018a = this.f1416b.m4018a(false, "decode-wakeup-url");
                m3752a = m3749a.m3752a(m4018a, this.f1416b.m3997i(), hashMap);
            } else {
                c3049b = new C3049b(EnumC3050c.SUCCESS, 1);
            }
            c3049b.m3743b("The wakeup parameter is invalid");
            return c3049b;
        }
        C2125c m4100a = this.f1416b.f1433j.m4100a();
        this.f1416b.f1433j.m4094b();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        identityHashMap.put("model", this.f1416b.f1432i.m3965f());
        identityHashMap.put("buildId", this.f1416b.f1432i.m3964g());
        identityHashMap.put("buildDisplay", this.f1416b.f1432i.m3963h());
        identityHashMap.put("brand", this.f1416b.f1432i.m3962i());
        identityHashMap.putAll(this.f1416b.f1432i.m3957n());
        if (m4100a != null) {
            if (m4100a.m4084c(2)) {
                identityHashMap.put("pbHtml", m4100a.m4088b());
            }
            if (m4100a.m4084c(1)) {
                identityHashMap.put("pbText", m4100a.m4092a());
            }
        }
        C3048a m3749a2 = C3048a.m3749a(true);
        m4018a2 = this.f1416b.m4018a(false, "decode-wakeup-url");
        m3752a = m3749a2.m3752a(m4018a2, this.f1416b.m3997i(), identityHashMap);
        this.f1416b.m4001d(m3752a.m3738e());
        return m3752a;
    }
}
