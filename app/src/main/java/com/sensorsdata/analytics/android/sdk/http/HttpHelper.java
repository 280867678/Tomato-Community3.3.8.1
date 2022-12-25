package com.sensorsdata.analytics.android.sdk.http;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class HttpHelper implements IhttpProcessor {
    private static HttpHelper _instance;
    private static IhttpProcessor mIhttpProcessor;
    private Map<String, Object> mParams = new HashMap();

    private HttpHelper() {
    }

    public static HttpHelper obtain() {
        synchronized (HttpHelper.class) {
            if (_instance == null) {
                _instance = new HttpHelper();
            }
        }
        return _instance;
    }

    public static void init(IhttpProcessor ihttpProcessor) {
        mIhttpProcessor = ihttpProcessor;
    }

    @Override // com.sensorsdata.analytics.android.sdk.http.IhttpProcessor
    public void get(String str, Map<String, Object> map, ICallBack iCallBack) {
        mIhttpProcessor.get(str, map, iCallBack);
    }

    @Override // com.sensorsdata.analytics.android.sdk.http.IhttpProcessor
    public void post(String str, Map<String, Object> map, ICallBack iCallBack) {
        mIhttpProcessor.post(str, map, iCallBack);
    }

    @Override // com.sensorsdata.analytics.android.sdk.http.IhttpProcessor
    public void post(String str, String str2, ICallBack iCallBack) {
        mIhttpProcessor.post(str, str2, iCallBack);
    }
}
