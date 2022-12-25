package com.dueeeke.videoplayer.player;

/* loaded from: classes2.dex */
public class VideoViewManager {
    private static VideoViewManager sInstance;
    private BaseIjkVideoView mPlayer;

    private VideoViewManager() {
    }

    public static VideoViewManager instance() {
        if (sInstance == null) {
            synchronized (VideoViewManager.class) {
                if (sInstance == null) {
                    sInstance = new VideoViewManager();
                }
            }
        }
        return sInstance;
    }

    public void setCurrentVideoPlayer(BaseIjkVideoView baseIjkVideoView) {
        this.mPlayer = baseIjkVideoView;
    }

    public BaseIjkVideoView getCurrentVideoPlayer() {
        return this.mPlayer;
    }

    public void releaseVideoPlayer() {
        BaseIjkVideoView baseIjkVideoView = this.mPlayer;
        if (baseIjkVideoView != null) {
            baseIjkVideoView.release();
            this.mPlayer = null;
        }
    }

    public void stopPlayback() {
        BaseIjkVideoView baseIjkVideoView = this.mPlayer;
        if (baseIjkVideoView != null) {
            baseIjkVideoView.stopPlayback();
        }
    }

    public boolean onBackPressed() {
        BaseIjkVideoView baseIjkVideoView = this.mPlayer;
        return baseIjkVideoView != null && baseIjkVideoView.onBackPressed();
    }
}
