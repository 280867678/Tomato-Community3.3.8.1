package com.sensorsdata.analytics.android.sdk.http;

import com.google.gson.Gson;
import java.lang.reflect.ParameterizedType;

/* loaded from: classes3.dex */
public abstract class HttpCallback<Result> implements ICallBack {
    public abstract void onSuccess(Result result);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.sensorsdata.analytics.android.sdk.http.ICallBack
    public void onSuccess(String str) {
        onSuccess((HttpCallback<Result>) new Gson().fromJson(str, (Class<Object>) analysisClazzInfo(this)));
    }

    public static Class<?> analysisClazzInfo(Object obj) {
        return (Class) ((ParameterizedType) obj.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
