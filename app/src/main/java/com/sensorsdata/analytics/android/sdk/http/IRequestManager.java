package com.sensorsdata.analytics.android.sdk.http;

/* loaded from: classes3.dex */
public interface IRequestManager {
    void delete(String str, String str2, IRequestCallback iRequestCallback);

    void get(String str, IRequestCallback iRequestCallback);

    void post(String str, String str2, IRequestCallback iRequestCallback);

    void put(String str, String str2, IRequestCallback iRequestCallback);
}
