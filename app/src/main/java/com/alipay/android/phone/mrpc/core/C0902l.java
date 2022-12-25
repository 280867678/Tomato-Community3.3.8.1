package com.alipay.android.phone.mrpc.core;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.eclipsesource.p056v8.Platform;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.alipay.android.phone.mrpc.core.l */
/* loaded from: classes2.dex */
public final class C0902l implements AbstractC0886ab {

    /* renamed from: b */
    private static C0902l f786b;

    /* renamed from: i */
    private static final ThreadFactory f787i = new ThreadFactoryC0904n();

    /* renamed from: a */
    Context f788a;

    /* renamed from: e */
    private long f791e;

    /* renamed from: f */
    private long f792f;

    /* renamed from: g */
    private long f793g;

    /* renamed from: h */
    private int f794h;

    /* renamed from: d */
    private C0889b f790d = C0889b.m4863a(Platform.ANDROID);

    /* renamed from: c */
    private ThreadPoolExecutor f789c = new ThreadPoolExecutor(10, 11, 3, TimeUnit.SECONDS, new ArrayBlockingQueue(20), f787i, new ThreadPoolExecutor.CallerRunsPolicy());

    private C0902l(Context context) {
        this.f788a = context;
        try {
            this.f789c.allowCoreThreadTimeOut(true);
        } catch (Exception unused) {
        }
        CookieSyncManager.createInstance(this.f788a);
        CookieManager.getInstance().setAcceptCookie(true);
    }

    /* renamed from: a */
    public static final C0902l m4840a(Context context) {
        C0902l c0902l = f786b;
        return c0902l != null ? c0902l : m4837b(context);
    }

    /* renamed from: b */
    private static final synchronized C0902l m4837b(Context context) {
        synchronized (C0902l.class) {
            if (f786b != null) {
                return f786b;
            }
            C0902l c0902l = new C0902l(context);
            f786b = c0902l;
            return c0902l;
        }
    }

    /* renamed from: a */
    public final C0889b m4842a() {
        return this.f790d;
    }

    @Override // com.alipay.android.phone.mrpc.core.AbstractC0886ab
    /* renamed from: a */
    public final Future<C0911u> mo4839a(AbstractC0910t abstractC0910t) {
        if (C0909s.m4804a(this.f788a)) {
            String str = "HttpManager" + hashCode() + ": Active Task = %d, Completed Task = %d, All Task = %d,Avarage Speed = %d KB/S, Connetct Time = %d ms, All data size = %d bytes, All enqueueConnect time = %d ms, All socket time = %d ms, All request times = %d times";
            Object[] objArr = new Object[9];
            objArr[0] = Integer.valueOf(this.f789c.getActiveCount());
            objArr[1] = Long.valueOf(this.f789c.getCompletedTaskCount());
            objArr[2] = Long.valueOf(this.f789c.getTaskCount());
            long j = this.f793g;
            long j2 = 0;
            objArr[3] = Long.valueOf(j == 0 ? 0L : ((this.f791e * 1000) / j) >> 10);
            int i = this.f794h;
            if (i != 0) {
                j2 = this.f792f / i;
            }
            objArr[4] = Long.valueOf(j2);
            objArr[5] = Long.valueOf(this.f791e);
            objArr[6] = Long.valueOf(this.f792f);
            objArr[7] = Long.valueOf(this.f793g);
            objArr[8] = Integer.valueOf(this.f794h);
            String.format(str, objArr);
        }
        CallableC0907q callableC0907q = new CallableC0907q(this, (C0905o) abstractC0910t);
        C0903m c0903m = new C0903m(this, callableC0907q, callableC0907q);
        this.f789c.execute(c0903m);
        return c0903m;
    }

    /* renamed from: a */
    public final void m4841a(long j) {
        this.f791e += j;
    }

    /* renamed from: b */
    public final void m4838b(long j) {
        this.f792f += j;
        this.f794h++;
    }

    /* renamed from: c */
    public final void m4836c(long j) {
        this.f793g += j;
    }
}
