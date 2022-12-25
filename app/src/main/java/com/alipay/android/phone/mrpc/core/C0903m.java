package com.alipay.android.phone.mrpc.core;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* renamed from: com.alipay.android.phone.mrpc.core.m */
/* loaded from: classes2.dex */
final class C0903m extends FutureTask<C0911u> {

    /* renamed from: a */
    final /* synthetic */ CallableC0907q f795a;

    /* renamed from: b */
    final /* synthetic */ C0902l f796b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0903m(C0902l c0902l, Callable callable, CallableC0907q callableC0907q) {
        super(callable);
        this.f796b = c0902l;
        this.f795a = callableC0907q;
    }

    @Override // java.util.concurrent.FutureTask
    protected final void done() {
        C0905o m4820a = this.f795a.m4820a();
        if (m4820a.m4803f() == null) {
            super.done();
            return;
        }
        try {
            get();
            if (!isCancelled() && !m4820a.m4801h()) {
                return;
            }
            m4820a.m4802g();
            if (isCancelled() && isDone()) {
                return;
            }
            cancel(false);
        } catch (InterruptedException e) {
            new StringBuilder().append(e);
        } catch (CancellationException unused) {
            m4820a.m4802g();
        } catch (ExecutionException e2) {
            if (e2.getCause() == null || !(e2.getCause() instanceof HttpException)) {
                new StringBuilder().append(e2);
                return;
            }
            HttpException httpException = (HttpException) e2.getCause();
            httpException.getCode();
            httpException.getMsg();
        } catch (Throwable th) {
            throw new RuntimeException("An error occured while executing http request", th);
        }
    }
}
