package com.alipay.sdk.util;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.alipay.android.app.IAlixPay;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.util.f */
/* loaded from: classes2.dex */
public class ServiceConnectionC1000f implements ServiceConnection {

    /* renamed from: a */
    final /* synthetic */ C0998e f1063a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ServiceConnectionC1000f(C0998e c0998e) {
        this.f1063a = c0998e;
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        C0988a c0988a;
        c0988a = this.f1063a.f1059h;
        C0954a.m4634a(c0988a, "biz", "srvDis");
        this.f1063a.f1055d = null;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        C0988a c0988a;
        Object obj;
        Object obj2;
        c0988a = this.f1063a.f1059h;
        C0954a.m4634a(c0988a, "biz", "srvCon");
        obj = this.f1063a.f1056e;
        synchronized (obj) {
            this.f1063a.f1055d = IAlixPay.Stub.asInterface(iBinder);
            obj2 = this.f1063a.f1056e;
            obj2.notify();
        }
    }
}
