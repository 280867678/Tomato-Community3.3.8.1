package com.tomatolive.library.http.interceptor;

import android.support.annotation.NonNull;
import com.blankj.utilcode.util.DeviceUtils;
import com.eclipsesource.p056v8.Platform;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.utils.LogConstants;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class AddHeaderInterceptor implements Interceptor {
    public static final String DEVICE_ID = "deviceId";
    public static final String RANDOM_STR = "randomStr";
    public static final String TIME_STAMP_STR = "timeStampStr";

    @Override // okhttp3.Interceptor
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request.Builder newBuilder = chain.request().newBuilder();
        newBuilder.header("deviceType", Platform.ANDROID);
        newBuilder.header(LogConstants.APP_ID, UserInfoManager.getInstance().getAppId());
        newBuilder.header("appKey", TomatoLiveSDK.getSingleton().APP_KEY);
        newBuilder.header("sdkVersion", "3.1.1");
        newBuilder.header("osVersion", DeviceUtils.getSDKVersionName());
        newBuilder.header("deviceName", DeviceUtils.getModel());
        newBuilder.header(TIME_STAMP_STR, String.valueOf(System.currentTimeMillis() / 1000));
        newBuilder.header(RANDOM_STR, StringUtils.getRandomString(16));
        newBuilder.header("deviceId", DeviceUtils.getAndroidID());
        newBuilder.header(LogConstants.OPEN_ID, UserInfoManager.getInstance().getAppOpenId());
        newBuilder.header("token", UserInfoManager.getInstance().getToken());
        newBuilder.header("userId", UserInfoManager.getInstance().getUserId());
        return chain.proceed(newBuilder.build());
    }
}
