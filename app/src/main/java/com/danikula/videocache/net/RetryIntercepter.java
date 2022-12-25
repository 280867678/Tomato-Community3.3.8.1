package com.danikula.videocache.net;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/* loaded from: classes2.dex */
public class RetryIntercepter implements Interceptor {
    public int maxRetry;
    private int retryNum = 0;

    public RetryIntercepter(int i) {
        this.maxRetry = i;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        int i;
        Request request = chain.request();
        Response proceed = chain.proceed(request);
        while (!proceed.isSuccessful() && (i = this.retryNum) < this.maxRetry) {
            this.retryNum = i + 1;
            Timber.Tree tag = Timber.tag("TTT");
            tag.mo18i("retryNum=" + this.retryNum, new Object[0]);
            proceed = chain.proceed(request);
        }
        return proceed;
    }

    public void setRetryNum(int i) {
        this.retryNum = i;
    }
}
