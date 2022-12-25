package com.tomatolive.library.http.interceptor;

import android.support.annotation.NonNull;
import com.eclipsesource.p056v8.Platform;
import com.tomatolive.library.utils.LogConstants;
import com.tomatolive.library.utils.UserInfoManager;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class StatisticsHeaderInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request.Builder newBuilder = chain.request().newBuilder();
        newBuilder.header("deviceType", Platform.ANDROID);
        newBuilder.header(LogConstants.APP_ID, UserInfoManager.getInstance().getAppId());
        return chain.proceed(newBuilder.build());
    }
}
