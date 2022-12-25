package com.p065io.liquidlink.p070e;

import com.p065io.liquidlink.p072g.C2164b;
import com.p089pm.liquidlink.listener.GetUpdateApkListener;
import java.io.File;
import java.io.IOException;

/* renamed from: com.io.liquidlink.e.e */
/* loaded from: classes3.dex */
class RunnableC2143e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f1410a;

    /* renamed from: b */
    final /* synthetic */ String f1411b;

    /* renamed from: c */
    final /* synthetic */ GetUpdateApkListener f1412c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC2143e(HandlerC2139a handlerC2139a, String str, String str2, GetUpdateApkListener getUpdateApkListener) {
        this.f1410a = str;
        this.f1411b = str2;
        this.f1412c = getUpdateApkListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            File file = new File(this.f1410a);
            File file2 = new File(this.f1411b);
            C2164b.m3947a((byte[]) null, file, file2);
            this.f1412c.onGetFinish(file2);
        } catch (IOException e) {
            e.printStackTrace();
            this.f1412c.onGetFinish(null);
        }
    }
}
