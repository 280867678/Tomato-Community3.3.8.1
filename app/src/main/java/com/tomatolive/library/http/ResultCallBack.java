package com.tomatolive.library.http;

/* loaded from: classes3.dex */
public interface ResultCallBack<T> {
    void onError(int i, String str);

    void onSuccess(T t);
}
