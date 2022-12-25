package com.gen.p059mh.webapp_extensions.listener;

import com.gen.p059mh.webapps.listener.IErrorInfo;
import com.gen.p059mh.webapps.listener.OnAppInfoResponse;

/* renamed from: com.gen.mh.webapp_extensions.listener.DownloadListener */
/* loaded from: classes2.dex */
public interface DownloadListener {
    void onDownloadFail(IErrorInfo iErrorInfo);

    void onRequestAppInfo(String str, OnAppInfoResponse onAppInfoResponse);
}
