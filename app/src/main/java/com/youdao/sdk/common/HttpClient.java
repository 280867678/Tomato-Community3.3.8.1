package com.youdao.sdk.common;

import android.net.http.AndroidHttpClient;
import com.youdao.sdk.app.other.C5174w;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* loaded from: classes4.dex */
public class HttpClient {
    public static AndroidHttpClient getHttpClient(int i) {
        AndroidHttpClient newInstance = AndroidHttpClient.newInstance(C5174w.m172a());
        HttpParams params = newInstance.getParams();
        HttpConnectionParams.setConnectionTimeout(params, i);
        HttpConnectionParams.setSoTimeout(params, i);
        HttpClientParams.setRedirecting(params, true);
        return newInstance;
    }
}
