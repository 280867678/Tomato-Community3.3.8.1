package com.dueeeke.videoplayer.listener;

/* loaded from: classes2.dex */
public interface PlayerEventListener {
    void onCompletion();

    void onError(String str);

    void onInfo(int i, int i2);

    void onPrepared();

    void onVideoSizeChanged(int i, int i2);
}
