package com.tomatolive.library.http;

import com.tomatolive.library.http.interceptor.BaseUrlManagerInterceptor;
import com.tomatolive.library.http.utils.HttpsUtils;
import com.tomatolive.library.utils.AppUtils;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes3.dex */
public class CacheApiRetrofit {
    private ApiService mApiService;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$new$0(String str, SSLSession sSLSession) {
        return true;
    }

    private CacheApiRetrofit() {
        try {
            HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory(null, null, null);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new BaseUrlManagerInterceptor());
            builder.connectTimeout(10L, TimeUnit.SECONDS);
            builder.readTimeout(30L, TimeUnit.SECONDS);
            builder.writeTimeout(30L, TimeUnit.SECONDS);
            builder.sslSocketFactory(sslSocketFactory.sSLSocketFactory, sslSocketFactory.trustManager);
            builder.hostnameVerifier($$Lambda$CacheApiRetrofit$kExRmGjbAF3rEGhWuGgSXcoVIP0.INSTANCE);
            OkHttpClient build = builder.build();
            Retrofit.Builder builder2 = new Retrofit.Builder();
            builder2.baseUrl(AppUtils.getApiURl());
            builder2.addConverterFactory(GsonConverterFactory.create());
            builder2.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            builder2.client(build);
            this.mApiService = (ApiService) builder2.build().create(ApiService.class);
        } catch (Exception unused) {
        }
    }

    /* loaded from: classes3.dex */
    private static class SingletonHolder {
        private static final CacheApiRetrofit INSTANCE = new CacheApiRetrofit();

        private SingletonHolder() {
        }
    }

    public static CacheApiRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ApiService getApiService() {
        return this.mApiService;
    }

    public boolean isApiService() {
        return this.mApiService != null;
    }
}
