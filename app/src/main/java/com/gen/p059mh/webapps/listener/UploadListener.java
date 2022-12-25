package com.gen.p059mh.webapps.listener;

import okhttp3.Response;

/* renamed from: com.gen.mh.webapps.listener.UploadListener */
/* loaded from: classes2.dex */
public interface UploadListener {
    void onProgress(long j, long j2);

    void onResponse(Response response);
}
