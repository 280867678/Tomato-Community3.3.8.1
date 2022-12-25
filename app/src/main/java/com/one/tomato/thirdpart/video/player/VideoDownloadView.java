package com.one.tomato.thirdpart.video.player;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.AbstractPlayer;
import com.dueeeke.videoplayer.player.BaseIjkVideoView;
import com.dueeeke.videoplayer.util.WindowUtil;
import com.dueeeke.videoplayer.widget.ResizeSurfaceView;
import com.dueeeke.videoplayer.widget.ResizeTextureView;
import com.one.tomato.thirdpart.video.manager.VideoDownloadViewManager;

/* loaded from: classes3.dex */
public class VideoDownloadView extends BaseIjkVideoView {
    protected boolean isFullScreen;
    protected int mCurrentScreenScale;
    protected SurfaceTexture mSurfaceTexture;
    protected ResizeSurfaceView mSurfaceView;
    protected ResizeTextureView mTextureView;
    protected FrameLayout playerContainer;

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    protected boolean checkNetwork() {
        return false;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public int[] getVideoSize() {
        return new int[0];
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public boolean isTinyScreen() {
        return false;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void startTinyScreen() {
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void stopTinyScreen() {
    }

    public VideoDownloadView(@NonNull Context context) {
        this(context, null);
    }

    public VideoDownloadView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VideoDownloadView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentScreenScale = 0;
        initView();
    }

    protected void initView() {
        this.playerContainer = new FrameLayout(getContext());
        this.playerContainer.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        addView(this.playerContainer, new FrameLayout.LayoutParams(-1, -1));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void initPlayer() {
        super.initPlayer();
        addDisplay();
    }

    protected void addDisplay() {
        if (this.mUsingSurfaceView) {
            addSurfaceView();
        } else {
            addTextureView();
        }
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void setPlayState(int i) {
        this.mCurrentPlayState = i;
        BaseVideoController baseVideoController = this.mVideoController;
        if (baseVideoController != null) {
            baseVideoController.setPlayState(i);
        }
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void setPlayerState(int i) {
        this.mCurrentPlayerState = i;
        BaseVideoController baseVideoController = this.mVideoController;
        if (baseVideoController != null) {
            baseVideoController.setPlayerState(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void startPlay() {
        if (this.mAddToVideoViewManager) {
            VideoDownloadViewManager.instance().releaseVideoPlayer();
            VideoDownloadViewManager.instance().setCurrentVideoPlayer(this);
        }
        if (checkNetwork()) {
            return;
        }
        super.startPlay();
    }

    private void addSurfaceView() {
        this.playerContainer.removeView(this.mSurfaceView);
        this.mSurfaceView = new ResizeSurfaceView(getContext());
        SurfaceHolder holder = this.mSurfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() { // from class: com.one.tomato.thirdpart.video.player.VideoDownloadView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                if (((BaseIjkVideoView) VideoDownloadView.this).mMediaPlayer != null) {
                    ((BaseIjkVideoView) VideoDownloadView.this).mMediaPlayer.setDisplay(surfaceHolder);
                }
            }
        });
        holder.setFormat(1);
        this.playerContainer.addView(this.mSurfaceView, 0, new FrameLayout.LayoutParams(-1, -1, 17));
    }

    private void addTextureView() {
        this.playerContainer.removeView(this.mTextureView);
        this.mSurfaceTexture = null;
        this.mTextureView = new ResizeTextureView(getContext());
        this.mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() { // from class: com.one.tomato.thirdpart.video.player.VideoDownloadView.2
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                VideoDownloadView videoDownloadView = VideoDownloadView.this;
                SurfaceTexture surfaceTexture2 = videoDownloadView.mSurfaceTexture;
                if (surfaceTexture2 != null) {
                    videoDownloadView.mTextureView.setSurfaceTexture(surfaceTexture2);
                    return;
                }
                videoDownloadView.mSurfaceTexture = surfaceTexture;
                ((BaseIjkVideoView) videoDownloadView).mMediaPlayer.setSurface(new Surface(surfaceTexture));
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return VideoDownloadView.this.mSurfaceTexture == null;
            }
        });
        this.playerContainer.addView(this.mTextureView, 0, new FrameLayout.LayoutParams(-1, -1, 17));
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void release() {
        super.release();
        this.playerContainer.removeView(this.mTextureView);
        this.playerContainer.removeView(this.mSurfaceView);
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.release();
            this.mSurfaceTexture = null;
        }
        this.mCurrentScreenScale = 0;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void startFullScreen() {
        Activity scanForActivity = WindowUtil.scanForActivity(getContext());
        if (scanForActivity != null && !this.isFullScreen) {
            removeView(this.playerContainer);
            ((ViewGroup) scanForActivity.getWindow().getDecorView()).addView(this.playerContainer, new FrameLayout.LayoutParams(-1, -1));
            this.isFullScreen = true;
            setPlayerState(11);
            WindowUtil.hideSystemBar(getContext());
        }
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void stopFullScreen() {
        Activity scanForActivity = WindowUtil.scanForActivity(getContext());
        if (scanForActivity != null && this.isFullScreen) {
            BaseVideoController baseVideoController = this.mVideoController;
            if (baseVideoController != null) {
                baseVideoController.hide();
            }
            WindowUtil.showSystemBar(getContext());
            ((ViewGroup) scanForActivity.getWindow().getDecorView()).removeView(this.playerContainer);
            addView(this.playerContainer, new FrameLayout.LayoutParams(-1, -1));
            this.isFullScreen = false;
            setPlayerState(10);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public boolean isFullScreen() {
        return this.isFullScreen;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void replay(boolean z) {
        addDisplay();
        startPrepare(true);
    }

    @Override // com.dueeeke.videoplayer.listener.PlayerEventListener
    public void onVideoSizeChanged(int i, int i2) {
        if (this.mUsingSurfaceView) {
            this.mSurfaceView.setScreenScale(this.mCurrentScreenScale);
            this.mSurfaceView.setVideoSize(i, i2);
            return;
        }
        this.mTextureView.setScreenScale(this.mCurrentScreenScale);
        this.mTextureView.setVideoSize(i, i2);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (!z || !this.isFullScreen) {
            return;
        }
        WindowUtil.hideSystemBar(getContext());
    }

    public void setVideoController(@Nullable BaseVideoController baseVideoController) {
        this.playerContainer.removeView(this.mVideoController);
        this.mVideoController = baseVideoController;
        if (baseVideoController != null) {
            baseVideoController.setMediaPlayer(this);
            this.playerContainer.addView(this.mVideoController, new FrameLayout.LayoutParams(-1, -1));
        }
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public boolean onBackPressed() {
        BaseVideoController baseVideoController = this.mVideoController;
        return baseVideoController != null && baseVideoController.onBackPressed();
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void setScreenScale(int i) {
        this.mCurrentScreenScale = i;
        ResizeSurfaceView resizeSurfaceView = this.mSurfaceView;
        if (resizeSurfaceView != null) {
            resizeSurfaceView.setScreenScale(i);
            return;
        }
        ResizeTextureView resizeTextureView = this.mTextureView;
        if (resizeTextureView == null) {
            return;
        }
        resizeTextureView.setScreenScale(i);
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void setMirrorRotation(boolean z) {
        ResizeTextureView resizeTextureView = this.mTextureView;
        if (resizeTextureView != null) {
            resizeTextureView.setScaleX(z ? -1.0f : 1.0f);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public Bitmap doScreenShot() {
        ResizeTextureView resizeTextureView = this.mTextureView;
        if (resizeTextureView != null) {
            return resizeTextureView.getBitmap();
        }
        return null;
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView, com.dueeeke.videoplayer.controller.MediaPlayerControl
    public long getDuration() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getDuration();
        }
        return 0L;
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView, com.dueeeke.videoplayer.controller.MediaPlayerControl
    public long getCurrentPosition() {
        if (isInPlaybackState()) {
            this.mCurrentPosition = this.mMediaPlayer.getCurrentPosition();
            return this.mCurrentPosition;
        }
        return 0L;
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public int getBufferPercentage() {
        AbstractPlayer abstractPlayer = this.mMediaPlayer;
        if (abstractPlayer != null) {
            return abstractPlayer.getBufferedPercentage();
        }
        return 0;
    }
}
