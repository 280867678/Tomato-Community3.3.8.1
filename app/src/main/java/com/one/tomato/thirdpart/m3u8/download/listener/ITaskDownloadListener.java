package com.one.tomato.thirdpart.m3u8.download.listener;

import com.one.tomato.thirdpart.m3u8.download.entity.M3U8;

/* loaded from: classes3.dex */
public interface ITaskDownloadListener {
    void onError(M3U8 m3u8, Throwable th);

    void onProgress(M3U8 m3u8, long j, int i, int i2);

    void onStartDownload(M3U8 m3u8, long j, int i, int i2);

    void onSuccess(M3U8 m3u8);
}
