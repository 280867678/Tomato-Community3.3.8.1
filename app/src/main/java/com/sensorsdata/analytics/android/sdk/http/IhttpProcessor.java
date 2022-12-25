package com.sensorsdata.analytics.android.sdk.http;

import java.util.Map;

/* loaded from: classes3.dex */
public interface IhttpProcessor {
    void get(String str, Map<String, Object> map, ICallBack iCallBack);

    void post(String str, String str2, ICallBack iCallBack);

    void post(String str, Map<String, Object> map, ICallBack iCallBack);
}
