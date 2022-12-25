package com.danikula.videocache.net;

import com.danikula.videocache.log.LogUtil;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class RepResultIntercepter implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response proceed = chain.proceed(request);
        String httpUrl = request.url().toString();
        int code = proceed.code();
        boolean isSuccessful = proceed.isSuccessful();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(httpUrl);
        stringBuffer.append(",");
        stringBuffer.append(isSuccessful);
        stringBuffer.append(",");
        stringBuffer.append(code);
        LogUtil.m4184i("RepR", stringBuffer.toString());
        return proceed;
    }
}
