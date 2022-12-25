package com.one.tomato.thirdpart.video.manager;

import com.one.tomato.thirdpart.video.player.VideoDownloadView;

/* loaded from: classes3.dex */
public class VideoDownloadViewManager {
    private static VideoDownloadViewManager sInstance;
    private VideoDownloadView mPlayer;

    private VideoDownloadViewManager() {
    }

    public static VideoDownloadViewManager instance() {
        if (sInstance == null) {
            synchronized (VideoDownloadViewManager.class) {
                if (sInstance == null) {
                    sInstance = new VideoDownloadViewManager();
                }
            }
        }
        return sInstance;
    }

    public void setCurrentVideoPlayer(VideoDownloadView videoDownloadView) {
        this.mPlayer = videoDownloadView;
    }

    public void releaseVideoPlayer() {
        VideoDownloadView videoDownloadView = this.mPlayer;
        if (videoDownloadView != null) {
            videoDownloadView.release();
            this.mPlayer = null;
        }
    }
}
