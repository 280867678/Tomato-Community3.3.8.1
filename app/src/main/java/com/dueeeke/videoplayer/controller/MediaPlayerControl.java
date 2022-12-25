package com.dueeeke.videoplayer.controller;

import android.graphics.Bitmap;

/* loaded from: classes2.dex */
public interface MediaPlayerControl {
    Bitmap doScreenShot();

    int getBufferedPercentage();

    long getCurrentPosition();

    long getDuration();

    long getTcpSpeed();

    int[] getVideoSize();

    boolean isFullScreen();

    boolean isMute();

    boolean isPlaying();

    boolean isTinyScreen();

    void pause();

    void replay(boolean z);

    void seekTo(long j);

    void setLock(boolean z);

    void setMirrorRotation(boolean z);

    void setMute(boolean z);

    void setRotation(float f);

    void setScreenScale(int i);

    void setSpeed(float f);

    void start();

    void startFullScreen();

    void startTinyScreen();

    void stopFullScreen();

    void stopTinyScreen();
}
