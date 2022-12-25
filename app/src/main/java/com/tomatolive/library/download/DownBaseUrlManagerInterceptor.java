package com.tomatolive.library.download;

import android.support.annotation.NonNull;
import com.tomatolive.library.utils.AppUtils;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class DownBaseUrlManagerInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        Request.Builder newBuilder = request.newBuilder();
        HttpUrl parse = HttpUrl.parse(AppUtils.getImgDownloadURl());
        if (parse == null) {
            return chain.proceed(chain.request());
        }
        HttpUrl.Builder newBuilder2 = url.newBuilder();
        newBuilder2.scheme(parse.scheme());
        newBuilder2.host(parse.host());
        newBuilder2.port(parse.port());
        newBuilder.url(newBuilder2.build());
        return chain.proceed(newBuilder.build());
    }
}
