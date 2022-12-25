package com.tomatolive.library.http.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.utils.SignRequestUtils;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/* loaded from: classes3.dex */
public class SignRequestInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        if (TextUtils.equals(method, "GET") || TextUtils.equals(method, "PUT")) {
            HttpUrl url = request.url();
            Set<String> queryParameterNames = url.queryParameterNames();
            TreeMap treeMap = new TreeMap();
            for (String str : queryParameterNames) {
                treeMap.put(str, url.queryParameter(str));
            }
            String signRequest = SignRequestUtils.signRequest(request, treeMap, EncryptUtil.CHARSET);
            HttpUrl.Builder newBuilder = url.newBuilder();
            newBuilder.addQueryParameter("sign", signRequest);
            HttpUrl build = newBuilder.build();
            Request.Builder newBuilder2 = request.newBuilder();
            newBuilder2.url(build);
            return chain.proceed(newBuilder2.build());
        } else if (TextUtils.equals(method, "POST")) {
            RequestBody body = request.body();
            if (body instanceof MultipartBody) {
                return chain.proceed(request);
            }
            Gson gson = new Gson();
            Buffer buffer = new Buffer();
            ((RequestBody) Objects.requireNonNull(body)).writeTo(buffer);
            String signRequest2 = SignRequestUtils.signRequest(request, (TreeMap) gson.fromJson(buffer.readUtf8(), (Class<Object>) TreeMap.class), EncryptUtil.CHARSET);
            Request.Builder newBuilder3 = request.newBuilder();
            newBuilder3.header("sign", signRequest2);
            return chain.proceed(newBuilder3.build());
        } else {
            return chain.proceed(request);
        }
    }
}
