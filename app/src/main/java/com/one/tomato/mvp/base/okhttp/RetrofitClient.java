package com.one.tomato.mvp.base.okhttp;

import android.content.Context;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.HttpsUtils;
import com.one.tomato.mvp.base.okhttp.cookie.CookieJarImpl;
import com.one.tomato.mvp.base.okhttp.cookie.store.PersistentCookieStore;
import com.one.tomato.mvp.base.okhttp.interceptor.RequestHeaderInterceptor;
import com.one.tomato.mvp.base.okhttp.interceptor.ResponseHeaderInterceptor;
import com.one.tomato.mvp.base.okhttp.interceptor.logging.Level;
import com.one.tomato.mvp.base.okhttp.interceptor.logging.LoggingInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes3.dex */
public class RetrofitClient {
    public static String baseUrl;
    private static Context mContext;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient() {
        mContext = BaseApplication.getApplication();
        baseUrl = "http://www.google.com";
        HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)));
        builder.addInterceptor(new RequestHeaderInterceptor());
        builder.sslSocketFactory(sslSocketFactory.sSLSocketFactory, sslSocketFactory.trustManager);
        builder.addInterceptor(new ResponseHeaderInterceptor());
        LoggingInterceptor.Builder builder2 = new LoggingInterceptor.Builder();
        builder2.loggable(false);
        builder2.setLevel(Level.BASIC);
        builder2.log(4);
        builder2.request("Request");
        builder2.response("Response");
        builder2.addHeader("log-header", "I am the log request header.");
        builder2.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        builder.addInterceptor(builder2.build());
        builder.connectTimeout(20L, TimeUnit.SECONDS);
        builder.writeTimeout(20L, TimeUnit.SECONDS);
        builder.connectionPool(new ConnectionPool(8, 15L, TimeUnit.SECONDS));
        okHttpClient = builder.build();
        Retrofit.Builder builder3 = new Retrofit.Builder();
        builder3.client(okHttpClient);
        builder3.addConverterFactory(GsonConverterFactory.create());
        builder3.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder3.baseUrl(baseUrl);
        retrofit = builder3.build();
    }

    public <T> T create(Class<T> cls) {
        if (cls == null) {
            throw new RuntimeException("Api service is null!");
        }
        return (T) retrofit.create(cls);
    }
}
