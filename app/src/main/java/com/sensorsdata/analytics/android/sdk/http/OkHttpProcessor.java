package com.sensorsdata.analytics.android.sdk.http;

import android.os.Handler;
import android.os.Looper;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class OkHttpProcessor implements IhttpProcessor {
    public static final String TAG = "OkHttpProcessor";
    private static OkHttpClient mOkHttpClient;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override // com.sensorsdata.analytics.android.sdk.http.IhttpProcessor
    public void post(String str, String str2, ICallBack iCallBack) {
    }

    public OkHttpProcessor() {
        mOkHttpClient = new OkHttpClient();
    }

    @Override // com.sensorsdata.analytics.android.sdk.http.IhttpProcessor
    public void get(String str, Map<String, Object> map, final ICallBack iCallBack) {
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        mOkHttpClient.newCall(builder.build()).enqueue(new Callback() { // from class: com.sensorsdata.analytics.android.sdk.http.OkHttpProcessor.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                iCallBack.onFailed(iOException.toString());
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                OkHttpProcessor.this.postParams(iCallBack, response.isSuccessful(), response);
            }
        });
    }

    @Override // com.sensorsdata.analytics.android.sdk.http.IhttpProcessor
    public void post(String str, Map<String, Object> map, final ICallBack iCallBack) {
        RequestBody appendBody = appendBody(map);
        Request.Builder builder = new Request.Builder();
        builder.post(appendBody);
        builder.url(str);
        mOkHttpClient.newCall(builder.build()).enqueue(new Callback() { // from class: com.sensorsdata.analytics.android.sdk.http.OkHttpProcessor.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                iCallBack.onFailed(iOException.toString());
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                OkHttpProcessor.this.postParams(iCallBack, response.isSuccessful(), response);
            }
        });
    }

    private RequestBody appendBody(Map<String, Object> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map == null || map.isEmpty()) {
            return builder.build();
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue().toString());
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postParams(final ICallBack iCallBack, final boolean z, final Response response) {
        final String[] strArr = {""};
        this.mHandler.post(new Runnable() { // from class: com.sensorsdata.analytics.android.sdk.http.OkHttpProcessor.3
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    strArr[0] = response.body().toString();
                    iCallBack.onSuccess(strArr[0]);
                    return;
                }
                strArr[0] = response.message().toString();
                iCallBack.onFailed(strArr[0]);
            }
        });
    }
}
