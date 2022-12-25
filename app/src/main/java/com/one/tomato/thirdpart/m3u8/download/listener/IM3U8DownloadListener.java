package com.one.tomato.thirdpart.m3u8.download.listener;

import com.one.tomato.thirdpart.m3u8.download.entity.M3U8Task;

/* loaded from: classes3.dex */
public interface IM3U8DownloadListener {
    void onDownloadError(M3U8Task m3U8Task, Throwable th);

    void onDownloadPause(M3U8Task m3U8Task);

    void onDownloadPending(M3U8Task m3U8Task);

    void onDownloadPrepare(M3U8Task m3U8Task);

    void onDownloadProgress(M3U8Task m3U8Task);

    void onDownloadSuccess(M3U8Task m3U8Task);
}
