package com.one.tomato.mvp.base.okhttp.interceptor;

import android.text.TextUtils;
import com.one.tomato.mvp.base.okhttp.CallbackUtil;
import com.one.tomato.thirdpart.domain.DomainCallback;
import com.one.tomato.utils.AppInitUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.SaltUtils;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class ResponseHeaderInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response proceed = chain.proceed(request);
        String header = proceed.header("code");
        if ("301".equals(header)) {
            LogUtil.m3785e("TomatoCallback", "报301的请求地址：" + request.url().toString());
            CallbackUtil.loginOut(false);
        } else if ("302".equals(header)) {
            LogUtil.m3785e("TomatoCallback", "报302的请求地址：" + request.url().toString());
            CallbackUtil.loginOut(true);
        }
        DomainCallback.getInstance().setDomainVersion(proceed.header("domainVersion"));
        String header2 = proceed.header("keyApp");
        if (!TextUtils.isEmpty(header2)) {
            SaltUtils.INSTANCE.parseResponseKey(header2);
        }
        String header3 = proceed.header("refreshSalt");
        if (!TextUtils.isEmpty(header3) && header3.equals("1")) {
            SaltUtils.INSTANCE.clearData();
            AppInitUtil.initAppInfoFromServerForSalt();
        }
        return proceed.newBuilder().build();
    }
}
