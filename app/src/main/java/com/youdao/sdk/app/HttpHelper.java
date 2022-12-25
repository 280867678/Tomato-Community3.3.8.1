package com.youdao.sdk.app;

import com.youdao.sdk.app.other.RunnableC5158c;
import com.youdao.sdk.common.YouDaoLog;
import java.util.Map;

/* loaded from: classes4.dex */
public class HttpHelper {

    /* loaded from: classes4.dex */
    public interface HttpJsonListener {
        void onError(HttpErrorCode httpErrorCode);

        void onResult(String str);
    }

    public static void postRequest(String str, Map<String, String> map, HttpJsonListener httpJsonListener, int i) {
        try {
            new Thread(new RunnableC5158c(map, str, i, httpJsonListener)).start();
        } catch (Exception e) {
            YouDaoLog.m166e("Unspecified error occured in request", e);
            httpJsonListener.onError(HttpErrorCode.UNSPECIFICERROR);
        }
    }
}
