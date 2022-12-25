package com.p076mh.webappStart.android_plugin_impl.callback;

/* renamed from: com.mh.webappStart.android_plugin_impl.callback.CommonCallBack */
/* loaded from: classes3.dex */
public interface CommonCallBack<T> {
    void onFailure(Exception exc);

    void onResult(T t);
}
