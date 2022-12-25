package com.alipay.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import com.alipay.android.app.IRemoteServiceCallback;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C0998e;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.util.h */
/* loaded from: classes2.dex */
public class BinderC1002h extends IRemoteServiceCallback.Stub {

    /* renamed from: a */
    final /* synthetic */ C0998e f1066a;

    @Override // com.alipay.android.app.IRemoteServiceCallback
    public boolean isHideLoadingScreen() throws RemoteException {
        return false;
    }

    @Override // com.alipay.android.app.IRemoteServiceCallback
    public void payEnd(boolean z, String str) throws RemoteException {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BinderC1002h(C0998e c0998e) {
        this.f1066a = c0998e;
    }

    @Override // com.alipay.android.app.IRemoteServiceCallback
    public void startActivity(String str, String str2, int i, Bundle bundle) throws RemoteException {
        C0988a c0988a;
        C0988a c0988a2;
        Activity activity;
        C0988a c0988a3;
        C0988a c0988a4;
        C0998e.AbstractC0999a abstractC0999a;
        Activity activity2;
        C0988a c0988a5;
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        if (bundle == null) {
            bundle = new Bundle();
        }
        try {
            bundle.putInt("CallingPid", i);
            intent.putExtras(bundle);
        } catch (Exception e) {
            c0988a = this.f1066a.f1059h;
            C0954a.m4632a(c0988a, "biz", "ErrIntentEx", e);
        }
        intent.setClassName(str, str2);
        try {
            activity = this.f1066a.f1054c;
            if (activity == null) {
                c0988a3 = this.f1066a.f1059h;
                C0954a.m4633a(c0988a3, "biz", "ErrActNull", "");
                c0988a4 = this.f1066a.f1059h;
                Context m4489b = c0988a4.m4489b();
                if (m4489b != null) {
                    m4489b.startActivity(intent);
                }
            } else {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                activity2 = this.f1066a.f1054c;
                activity2.startActivity(intent);
                c0988a5 = this.f1066a.f1059h;
                C0954a.m4628b(c0988a5, "biz", "stAct2", "" + (SystemClock.elapsedRealtime() - elapsedRealtime));
            }
            abstractC0999a = this.f1066a.f1058g;
            abstractC0999a.mo4414b();
        } catch (Throwable th) {
            c0988a2 = this.f1066a.f1059h;
            C0954a.m4632a(c0988a2, "biz", "ErrActNull", th);
            throw th;
        }
    }
}
