package com.tomatolive.library.http.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.tomatolive.library.utils.AppUtils;
import java.io.IOException;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class BaseUrlManagerInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        Request.Builder newBuilder = request.newBuilder();
        HttpUrl parse = HttpUrl.parse(AppUtils.getApiURl());
        if (parse == null) {
            return chain.proceed(chain.request());
        }
        List<String> headers = request.headers("urlName");
        if (headers != null && headers.size() > 0) {
            newBuilder.removeHeader("urlName");
            if (TextUtils.equals("upload", headers.get(0))) {
                parse = HttpUrl.parse(AppUtils.getImgUploadUrl());
            }
        }
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
