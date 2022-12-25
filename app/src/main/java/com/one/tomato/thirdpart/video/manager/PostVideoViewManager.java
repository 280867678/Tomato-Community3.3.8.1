package com.one.tomato.thirdpart.video.manager;

import com.one.tomato.thirdpart.video.player.PostVideoView;

/* loaded from: classes3.dex */
public class PostVideoViewManager {
    private static PostVideoViewManager sInstance;
    private PostVideoView mPlayer;

    private PostVideoViewManager() {
    }

    public static PostVideoViewManager instance() {
        if (sInstance == null) {
            synchronized (PostVideoViewManager.class) {
                if (sInstance == null) {
                    sInstance = new PostVideoViewManager();
                }
            }
        }
        return sInstance;
    }

    public void setCurrentVideoPlayer(PostVideoView postVideoView) {
        this.mPlayer = postVideoView;
    }

    public void releaseVideoPlayer() {
        PostVideoView postVideoView = this.mPlayer;
        if (postVideoView != null) {
            postVideoView.release();
            this.mPlayer = null;
        }
    }
}
