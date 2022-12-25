package com.tomatolive.library.http;

import com.tomatolive.library.http.interceptor.AddHeaderInterceptor;
import com.tomatolive.library.http.interceptor.BaseUrlManagerInterceptor;
import com.tomatolive.library.http.interceptor.SignRequestInterceptor;
import com.tomatolive.library.http.utils.CustomGsonConverterFactory;
import com.tomatolive.library.http.utils.HttpsUtils;
import com.tomatolive.library.utils.AppUtils;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/* loaded from: classes3.dex */
public class ApiRetrofit {
    private ApiService mApiService;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$new$0(String str, SSLSession sSLSession) {
        return true;
    }

    private ApiRetrofit() {
        this.mApiService = null;
        try {
            HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory(null, null, null);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new BaseUrlManagerInterceptor());
            builder.addInterceptor(new AddHeaderInterceptor());
            builder.addInterceptor(new SignRequestInterceptor());
            builder.connectTimeout(6L, TimeUnit.SECONDS);
            builder.readTimeout(30L, TimeUnit.SECONDS);
            builder.writeTimeout(30L, TimeUnit.SECONDS);
            builder.sslSocketFactory(sslSocketFactory.sSLSocketFactory, sslSocketFactory.trustManager);
            builder.hostnameVerifier($$Lambda$ApiRetrofit$iu5P2JclTluLIXZx0dZfIDluCk.INSTANCE);
            OkHttpClient build = builder.build();
            Retrofit.Builder builder2 = new Retrofit.Builder();
            builder2.baseUrl(AppUtils.getApiURl());
            builder2.addConverterFactory(CustomGsonConverterFactory.create());
            builder2.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            builder2.client(build);
            this.mApiService = (ApiService) builder2.build().create(ApiService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static final ApiRetrofit INSTANCE = new ApiRetrofit();

        private SingletonHolder() {
        }
    }

    public static ApiRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ApiService getApiService() {
        return this.mApiService;
    }

    public boolean isApiService() {
        return this.mApiService != null;
    }
}
