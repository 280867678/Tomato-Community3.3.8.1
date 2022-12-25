package com.p065io.liquidlink;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Callable;

/* renamed from: com.io.liquidlink.e */
/* loaded from: classes3.dex */
class CallableC2138e implements Callable {

    /* renamed from: a */
    private final Context f1403a;

    /* renamed from: b */
    private final String f1404b;

    /* renamed from: c */
    private final AbstractC2156f f1405c;

    public CallableC2138e(Context context, String str, AbstractC2156f abstractC2156f) {
        this.f1403a = context;
        this.f1404b = str;
        this.f1405c = abstractC2156f;
    }

    @Override // java.util.concurrent.Callable
    /* renamed from: a */
    public SharedPreferences call() {
        SharedPreferences sharedPreferences = this.f1403a.getSharedPreferences(this.f1404b, 0);
        AbstractC2156f abstractC2156f = this.f1405c;
        if (abstractC2156f != null) {
            abstractC2156f.m3984a(sharedPreferences);
        }
        return sharedPreferences;
    }
}
