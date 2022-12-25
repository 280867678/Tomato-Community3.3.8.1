package com.gen.p059mh.webapps.listener;

/* renamed from: com.gen.mh.webapps.listener.ImageCallBack */
/* loaded from: classes2.dex */
public interface ImageCallBack<T> {
    void onFailure(Exception exc);

    void onResult(T t);
}
