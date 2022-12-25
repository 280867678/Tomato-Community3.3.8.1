package com.one.tomato.mvp.base.okhttp.interceptor;

import com.one.tomato.mvp.base.okhttp.download.ProgressResponseBody;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class ProgressInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder newBuilder = chain.request().newBuilder();
        newBuilder.addHeader("Accept-Encoding", "identity");
        Response proceed = chain.proceed(newBuilder.build());
        Response.Builder newBuilder2 = proceed.newBuilder();
        newBuilder2.body(new ProgressResponseBody(proceed.body()));
        return newBuilder2.build();
    }
}
