package com.gen.p059mh.webapp_extensions.listener;

import com.gen.p059mh.webapps.listener.AppResponse;
import com.gen.p059mh.webapps.listener.IErrorInfo;

/* renamed from: com.gen.mh.webapp_extensions.listener.AppControlListener */
/* loaded from: classes2.dex */
public interface AppControlListener {
    void onFail(IErrorInfo iErrorInfo);

    void onProgress(long j, long j2);

    void onReady();

    void onReceiveInfo(AppResponse appResponse);

    void onStart();

    void onUpdate();
}
