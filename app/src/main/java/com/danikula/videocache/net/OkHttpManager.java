package com.danikula.videocache.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class OkHttpManager {
    private OkHttpClient.Builder builder;
    private RetryIntercepter retryIntercepter;

    private OkHttpManager() {
        this.retryIntercepter = new RetryIntercepter(3);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30L, TimeUnit.SECONDS);
        builder.writeTimeout(30L, TimeUnit.SECONDS);
        builder.readTimeout(30L, TimeUnit.SECONDS);
        builder.addInterceptor(this.retryIntercepter);
        builder.addInterceptor(new RepResultIntercepter());
        builder.retryOnConnectionFailure(true);
        this.builder = builder;
    }

    public static OkHttpManager getInstance() {
        return OkHttpHolder.instance;
    }

    /* loaded from: classes2.dex */
    private static class OkHttpHolder {
        private static final OkHttpManager instance = new OkHttpManager();
    }

    public Call initRequest(String str, long j) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        builder.header("Range", "bytes=" + j + "-");
        Request build = builder.build();
        this.retryIntercepter.setRetryNum(0);
        return this.builder.build().newCall(build);
    }

    public Response requestHeader(String str) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        Request build = builder.build();
        this.retryIntercepter.setRetryNum(0);
        return this.builder.build().newCall(build).execute();
    }
}
