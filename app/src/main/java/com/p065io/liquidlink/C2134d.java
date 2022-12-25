package com.p065io.liquidlink;

import android.content.Context;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* renamed from: com.io.liquidlink.d */
/* loaded from: classes3.dex */
public class C2134d {

    /* renamed from: a */
    private final Executor f1392a = Executors.newSingleThreadExecutor();

    /* renamed from: a */
    public Future m4035a(Context context, String str, AbstractC2156f abstractC2156f) {
        FutureTask futureTask = new FutureTask(new CallableC2138e(context, str, abstractC2156f));
        this.f1392a.execute(futureTask);
        return futureTask;
    }
}
