package com.tomatolive.library.http;

import com.tomatolive.library.http.utils.CustomGsonConverterFactory;
import com.tomatolive.library.http.utils.HttpsUtils;
import com.tomatolive.library.utils.AppUtils;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/* loaded from: classes3.dex */
public class EventConfigRetrofit {
    private ApiService mApiService;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$new$0(String str, SSLSession sSLSession) {
        return true;
    }

    private EventConfigRetrofit() {
        this.mApiService = null;
        try {
            HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory(null, null, null);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(6L, TimeUnit.SECONDS);
            builder.readTimeout(30L, TimeUnit.SECONDS);
            builder.writeTimeout(30L, TimeUnit.SECONDS);
            builder.sslSocketFactory(sslSocketFactory.sSLSocketFactory, sslSocketFactory.trustManager);
            builder.hostnameVerifier($$Lambda$EventConfigRetrofit$kyqQe6KDavX2fi_o2g7hzvs44A.INSTANCE);
            OkHttpClient build = builder.build();
            Retrofit.Builder builder2 = new Retrofit.Builder();
            builder2.baseUrl(getBaseUrl());
            builder2.addConverterFactory(CustomGsonConverterFactory.create());
            builder2.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            builder2.client(build);
            this.mApiService = (ApiService) builder2.build().create(ApiService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* loaded from: classes3.dex */
    private static class SingletonHolder {
        private static final EventConfigRetrofit INSTANCE = new EventConfigRetrofit();

        private SingletonHolder() {
        }
    }

    public static EventConfigRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ApiService getApiService() {
        return this.mApiService;
    }

    public boolean isApiService() {
        return this.mApiService != null;
    }

    public String getBaseUrl() {
        return AppUtils.getDataReportConfigUrl();
    }
}
