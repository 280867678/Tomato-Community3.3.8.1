package com.alipay.sdk.app.statistic;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.C0954a;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.app.statistic.b */
/* loaded from: classes2.dex */
public final class RunnableC0959b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f947a;

    /* renamed from: b */
    final /* synthetic */ Context f948b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC0959b(String str, Context context) {
        this.f947a = str;
        this.f948b = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean m4618b;
        boolean m4618b2;
        if (!TextUtils.isEmpty(this.f947a)) {
            m4618b2 = C0954a.C0957b.m4618b(this.f948b, this.f947a);
            if (!m4618b2) {
                return;
            }
        }
        for (int i = 0; i < 4; i++) {
            String m4627a = C0954a.C0955a.m4627a(this.f948b);
            if (TextUtils.isEmpty(m4627a)) {
                return;
            }
            m4618b = C0954a.C0957b.m4618b(this.f948b, m4627a);
            if (!m4618b) {
                return;
            }
        }
    }
}
