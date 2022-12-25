package com.p065io.liquidlink.p069d;

import android.app.Application;
import android.content.Context;
import android.os.HandlerThread;
import com.p065io.liquidlink.C2122a;
import com.p065io.liquidlink.C2129c;
import com.p065io.liquidlink.p067b.C2128b;
import com.p065io.liquidlink.p070e.HandlerC2154p;
import com.p089pm.liquidlink.p092c.C3056d;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.io.liquidlink.d.a */
/* loaded from: classes3.dex */
public class C2135a {

    /* renamed from: a */
    private LinkedBlockingQueue f1393a = new LinkedBlockingQueue(1);

    /* renamed from: c */
    private volatile boolean f1394c = true;

    /* renamed from: d */
    private Thread f1395d = new Thread(new RunnableC2136b(this));

    /* renamed from: e */
    private Application f1396e;

    /* renamed from: f */
    private int f1397f;

    /* renamed from: g */
    private Application.ActivityLifecycleCallbacks f1398g;

    /* renamed from: h */
    private HandlerC2154p f1399h;

    public C2135a(Context context, C2122a c2122a, C2129c c2129c, C2128b c2128b) {
        C3056d.m3731a(C2135a.class);
        this.f1396e = (Application) context.getApplicationContext();
        HandlerThread handlerThread = new HandlerThread("EventsHandler-Thread");
        handlerThread.start();
        this.f1399h = new HandlerC2154p(context, handlerThread.getLooper(), c2122a, c2129c, c2128b);
        m4034a();
    }

    /* renamed from: a */
    private void m4034a() {
        this.f1394c = true;
        this.f1395d.start();
        m4030b();
    }

    /* renamed from: b */
    private void m4030b() {
        this.f1397f = 0;
        this.f1398g = new C2137c(this);
        this.f1396e.registerActivityLifecycleCallbacks(this.f1398g);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: e */
    public static /* synthetic */ int m4026e(C2135a c2135a) {
        int i = c2135a.f1397f;
        c2135a.f1397f = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: f */
    public static /* synthetic */ int m4025f(C2135a c2135a) {
        int i = c2135a.f1397f;
        c2135a.f1397f = i - 1;
        return i;
    }

    /* renamed from: a */
    public void m4033a(long j) {
        if (j > 1) {
            long currentTimeMillis = System.currentTimeMillis();
            this.f1399h.m4003a(currentTimeMillis + "," + j + ";");
        }
    }

    /* renamed from: a */
    public void m4031a(String str) {
        this.f1399h.m4002b(str);
    }
}
