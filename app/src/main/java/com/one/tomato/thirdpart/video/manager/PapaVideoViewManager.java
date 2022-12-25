package com.one.tomato.thirdpart.video.manager;

import com.one.tomato.thirdpart.video.player.PapaVideoView;

/* loaded from: classes3.dex */
public class PapaVideoViewManager {
    private static PapaVideoViewManager sInstance;
    private PapaVideoView mPlayer;

    private PapaVideoViewManager() {
    }

    public static PapaVideoViewManager instance() {
        if (sInstance == null) {
            synchronized (PapaVideoViewManager.class) {
                if (sInstance == null) {
                    sInstance = new PapaVideoViewManager();
                }
            }
        }
        return sInstance;
    }

    public void setCurrentVideoPlayer(PapaVideoView papaVideoView) {
        this.mPlayer = papaVideoView;
    }

    public void releaseVideoPlayer() {
        PapaVideoView papaVideoView = this.mPlayer;
        if (papaVideoView != null) {
            papaVideoView.release();
            this.mPlayer = null;
        }
    }
}
