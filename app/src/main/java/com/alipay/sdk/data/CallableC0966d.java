package com.alipay.sdk.data;

import android.content.Context;
import com.alipay.sdk.sys.C0988a;
import java.util.HashMap;
import java.util.concurrent.Callable;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.data.d */
/* loaded from: classes2.dex */
public final class CallableC0966d implements Callable<String> {

    /* renamed from: a */
    final /* synthetic */ C0988a f983a;

    /* renamed from: b */
    final /* synthetic */ Context f984b;

    /* renamed from: c */
    final /* synthetic */ HashMap f985c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CallableC0966d(C0988a c0988a, Context context, HashMap hashMap) {
        this.f983a = c0988a;
        this.f984b = context;
        this.f985c = hashMap;
    }

    @Override // java.util.concurrent.Callable
    /* renamed from: a */
    public String call() throws Exception {
        String m4568b;
        m4568b = C0965c.m4568b(this.f983a, this.f984b, this.f985c);
        return m4568b;
    }
}
