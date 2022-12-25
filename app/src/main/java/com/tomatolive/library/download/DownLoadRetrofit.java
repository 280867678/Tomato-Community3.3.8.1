package com.tomatolive.library.download;

import com.tomatolive.library.http.ApiService;
import com.tomatolive.library.http.interceptor.AddHeaderInterceptor;
import com.tomatolive.library.http.utils.HttpsUtils;
import com.tomatolive.library.utils.AppUtils;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes3.dex */
public class DownLoadRetrofit {
    private ApiService mApiService;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$new$0(String str, SSLSession sSLSession) {
        return true;
    }

    private DownLoadRetrofit() {
        this.mApiService = null;
        try {
            HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory(null, null, null);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new DownBaseUrlManagerInterceptor());
            builder.addInterceptor(new AddHeaderInterceptor());
            builder.connectTimeout(10L, TimeUnit.SECONDS);
            builder.readTimeout(30L, TimeUnit.SECONDS);
            builder.writeTimeout(30L, TimeUnit.SECONDS);
            builder.sslSocketFactory(sslSocketFactory.sSLSocketFactory, sslSocketFactory.trustManager);
            builder.hostnameVerifier($$Lambda$DownLoadRetrofit$zxOJ7ZXEdWEJptYCXR9uSPYv7Rw.INSTANCE);
            OkHttpClient build = builder.build();
            Retrofit.Builder builder2 = new Retrofit.Builder();
            builder2.baseUrl(AppUtils.getImgDownloadURl());
            builder2.addConverterFactory(GsonConverterFactory.create());
            builder2.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            builder2.client(build);
            this.mApiService = (ApiService) builder2.build().create(ApiService.class);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static final DownLoadRetrofit INSTANCE = new DownLoadRetrofit();

        private SingletonHolder() {
        }
    }

    public static DownLoadRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ApiService getApiService() {
        return this.mApiService;
    }

    public boolean isApiService() {
        return this.mApiService != null;
    }
}
