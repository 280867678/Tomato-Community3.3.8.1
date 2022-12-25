package com.alipay.sdk.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.SystemClock;
import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;
import com.alipay.sdk.app.C0951i;
import com.alipay.sdk.app.C0952j;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.data.C0962a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C1008n;
import java.util.HashMap;
import java.util.List;

/* renamed from: com.alipay.sdk.util.e */
/* loaded from: classes2.dex */
public class C0998e {

    /* renamed from: c */
    private Activity f1054c;

    /* renamed from: d */
    private volatile IAlixPay f1055d;

    /* renamed from: f */
    private boolean f1057f;

    /* renamed from: g */
    private AbstractC0999a f1058g;

    /* renamed from: h */
    private final C0988a f1059h;

    /* renamed from: e */
    private final Object f1056e = IAlixPay.class;

    /* renamed from: i */
    private ServiceConnection f1060i = new ServiceConnectionC1000f(this);

    /* renamed from: j */
    private String f1061j = null;

    /* renamed from: k */
    private IRemoteServiceCallback f1062k = new BinderC1002h(this);

    /* renamed from: com.alipay.sdk.util.e$a */
    /* loaded from: classes2.dex */
    public interface AbstractC0999a {
        /* renamed from: a */
        void mo4415a();

        /* renamed from: b */
        void mo4414b();
    }

    public C0998e(Activity activity, C0988a c0988a, AbstractC0999a abstractC0999a) {
        this.f1054c = activity;
        this.f1059h = c0988a;
        this.f1058g = abstractC0999a;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005c A[Catch: all -> 0x0063, TryCatch #0 {all -> 0x0063, blocks: (B:3:0x0004, B:6:0x0018, B:9:0x0025, B:11:0x002d, B:14:0x0034, B:18:0x003d, B:20:0x0041, B:23:0x004f, B:24:0x0058, B:26:0x005c, B:27:0x005e, B:31:0x0054, B:33:0x0016), top: B:2:0x0004 }] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String m4422a(String str) {
        C1008n.C1009a m4389a;
        String str2 = "";
        PackageInfo packageInfo = null;
        try {
            List<C0962a.C0963a> m4582i = C0962a.m4581j().m4582i();
            if (!C0962a.m4581j().f968a || m4582i == null) {
                m4582i = C0951i.f930a;
            }
            m4389a = C1008n.m4389a(this.f1059h, this.f1054c, m4582i);
        } catch (Throwable th) {
            C0954a.m4632a(this.f1059h, "biz", "CheckClientSignEx", th);
        }
        if (m4389a == null || m4389a.m4362a(this.f1059h) || m4389a.m4363a() || C1008n.m4394a(m4389a.f1069a)) {
            return "failed";
        }
        if (m4389a.f1069a != null && !"com.eg.android.AlipayGphone".equals(m4389a.f1069a.packageName)) {
            str2 = m4389a.f1069a.packageName;
            if (m4389a.f1069a != null) {
                packageInfo = m4389a.f1069a;
            }
            m4423a(m4389a);
            return m4420a(str, str2, packageInfo);
        }
        str2 = C1008n.m4398a();
        if (m4389a.f1069a != null) {
        }
        m4423a(m4389a);
        return m4420a(str, str2, packageInfo);
    }

    /* renamed from: a */
    private void m4423a(C1008n.C1009a c1009a) throws InterruptedException {
        PackageInfo packageInfo;
        if (c1009a == null || (packageInfo = c1009a.f1069a) == null) {
            return;
        }
        String str = packageInfo.packageName;
        Intent intent = new Intent();
        intent.setClassName(str, "com.alipay.android.app.TransProcessPayActivity");
        try {
            this.f1054c.startActivity(intent);
        } catch (Throwable th) {
            C0954a.m4632a(this.f1059h, "biz", "StartLaunchAppTransEx", th);
        }
        Thread.sleep(200L);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:49:0x02af
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:82)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:40)
        */
    /* renamed from: a */
    private java.lang.String m4420a(java.lang.String r20, java.lang.String r21, android.content.pm.PackageInfo r22) {
        /*
            Method dump skipped, instructions count: 775
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.util.C0998e.m4420a(java.lang.String, java.lang.String, android.content.pm.PackageInfo):java.lang.String");
    }

    /* renamed from: a */
    private static boolean m4421a(String str, Context context, C0988a c0988a) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.setClassName(str, "com.alipay.android.msp.ui.views.MspContainerActivity");
            if (intent.resolveActivityInfo(context.getPackageManager(), 0) != null) {
                return true;
            }
            C0954a.m4634a(c0988a, "biz", "BSPDetectFail");
            return false;
        } catch (Throwable th) {
            C0954a.m4632a(c0988a, "biz", "BSPDetectFail", th);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x022b, code lost:
        if (r13 != null) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x022d, code lost:
        r13.setRequestedOrientation(0);
        r12.f1057f = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0291, code lost:
        if (r13 != null) goto L60;
     */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.alipay.android.app.IAlixPay, android.content.ServiceConnection, com.alipay.sdk.util.e$a, com.alipay.android.app.IRemoteServiceCallback] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String m4419a(String str, String str2, C0988a c0988a) {
        Activity activity;
        String m4643c;
        Activity activity2;
        Intent intent = new Intent();
        intent.setPackage(str2);
        intent.setAction(C1008n.m4384a(str2));
        String m4395a = C1008n.m4395a(this.f1054c, str2);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(elapsedRealtime);
        sb.append("|");
        sb.append(str != null ? str.length() : 0);
        C0954a.m4628b(c0988a, "biz", "PgBindStarting", sb.toString());
        C0954a.m4635a(this.f1054c, c0988a, str, c0988a.f1013p);
        try {
            if (!C0962a.m4581j().m4583h()) {
                ComponentName startService = this.f1054c.getApplication().startService(intent);
                C0954a.m4628b(c0988a, "biz", "stSrv", startService != null ? startService.getPackageName() : "null");
            } else {
                C0954a.m4628b(c0988a, "biz", "stSrv", "skipped");
            }
            if (!this.f1054c.getApplicationContext().bindService(intent, this.f1060i, 1)) {
                throw new Throwable("bindService fail");
            }
            synchronized (this.f1056e) {
                if (this.f1055d == null) {
                    try {
                        this.f1056e.wait(C0962a.m4581j().m4597a());
                    } catch (InterruptedException e) {
                        C0954a.m4632a(c0988a, "biz", "BindWaitTimeoutEx", e);
                    }
                }
            }
            try {
            } catch (Throwable th) {
                try {
                    C0954a.m4632a(c0988a, "biz", "ClientBindException", th);
                    m4643c = C0952j.m4643c();
                    try {
                        this.f1055d.unregisterCallback(this.f1062k);
                    } catch (Throwable th2) {
                        C0996c.m4436a(th2);
                    }
                    try {
                        this.f1054c.getApplicationContext().unbindService(this.f1060i);
                    } catch (Throwable th3) {
                        C0996c.m4436a(th3);
                    }
                    C0954a.m4628b(c0988a, "biz", "PgBindEnd", "" + SystemClock.elapsedRealtime());
                    C0954a.m4635a(this.f1054c, c0988a, str, c0988a.f1013p);
                    this.f1058g = null;
                    this.f1062k = null;
                    this.f1060i = null;
                    this.f1055d = null;
                    if (this.f1057f) {
                        activity2 = this.f1054c;
                    }
                } finally {
                    try {
                        this.f1055d.unregisterCallback(this.f1062k);
                    } catch (Throwable th4) {
                        C0996c.m4436a(th4);
                    }
                    try {
                        this.f1054c.getApplicationContext().unbindService(this.f1060i);
                    } catch (Throwable th5) {
                        C0996c.m4436a(th5);
                    }
                    C0954a.m4628b(c0988a, "biz", "PgBindEnd", "" + SystemClock.elapsedRealtime());
                    C0954a.m4635a(this.f1054c, c0988a, str, c0988a.f1013p);
                    this.f1058g = null;
                    this.f1062k = null;
                    this.f1060i = null;
                    this.f1055d = null;
                    if (this.f1057f && (activity = this.f1054c) != null) {
                        activity.setRequestedOrientation(0);
                        this.f1057f = false;
                    }
                }
            }
            if (this.f1055d == null) {
                String m4395a2 = C1008n.m4395a(this.f1054c, str2);
                C0954a.m4633a(c0988a, "biz", "ClientBindFailed", m4395a + "|" + m4395a2);
                return "failed";
            }
            long elapsedRealtime2 = SystemClock.elapsedRealtime();
            C0954a.m4628b(c0988a, "biz", "PgBinded", "" + elapsedRealtime2);
            if (this.f1058g != null) {
                this.f1058g.mo4415a();
            }
            if (this.f1054c.getRequestedOrientation() == 0) {
                this.f1054c.setRequestedOrientation(1);
                this.f1057f = true;
            }
            int version = this.f1055d.getVersion();
            this.f1055d.registerCallback(this.f1062k);
            long elapsedRealtime3 = SystemClock.elapsedRealtime();
            C0954a.m4628b(c0988a, "biz", "PgBindPay", "" + elapsedRealtime3);
            if (version >= 2) {
                HashMap<String, String> m4494a = C0988a.m4494a(c0988a);
                m4494a.put("ts_bind", String.valueOf(elapsedRealtime));
                m4494a.put("ts_bend", String.valueOf(elapsedRealtime2));
                m4494a.put("ts_pay", String.valueOf(elapsedRealtime3));
                m4643c = this.f1055d.pay02(str, m4494a);
            } else {
                m4643c = this.f1055d.Pay(str);
            }
            try {
                this.f1055d.unregisterCallback(this.f1062k);
            } catch (Throwable th6) {
                C0996c.m4436a(th6);
            }
            try {
                this.f1054c.getApplicationContext().unbindService(this.f1060i);
            } catch (Throwable th7) {
                C0996c.m4436a(th7);
            }
            C0954a.m4628b(c0988a, "biz", "PgBindEnd", "" + SystemClock.elapsedRealtime());
            C0954a.m4635a(this.f1054c, c0988a, str, c0988a.f1013p);
            this.f1058g = null;
            this.f1062k = null;
            this.f1060i = null;
            this.f1055d = null;
            if (this.f1057f) {
                activity2 = this.f1054c;
            }
            return m4643c;
        }
    }

    /* renamed from: a */
    public void m4427a() {
        this.f1054c = null;
    }
}
