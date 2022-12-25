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
import com.one.tomato.thirdpart.video.manager.PapaVideoViewManager;
import com.one.tomato.thirdpart.video.widget.PapaTextureView;
import com.one.tomato.utils.LogUtil;

/* loaded from: classes3.dex */
public class PapaVideoView extends BaseIjkVideoView {
    protected boolean isFullScreen;
    private boolean isLand;
    protected int mCurrentScreenScale;
    protected SurfaceTexture mSurfaceTexture;
    protected ResizeSurfaceView mSurfaceView;
    protected PapaTextureView mTextureView;
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

    public PapaVideoView(@NonNull Context context) {
        this(context, null);
    }

    public PapaVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PapaVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
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

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public boolean isInPlaybackState() {
        return super.isInPlaybackState();
    }

    public void setIsLand(boolean z) {
        this.isLand = z;
        LogUtil.m3785e("PapaVideoView", "isLand = " + z);
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void setPlayerState(int i) {
        this.mCurrentPlayerState = i;
        BaseVideoController baseVideoController = this.mVideoController;
        if (baseVideoController != null) {
            baseVideoController.setPlayerState(i);
        }
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void startPlay() {
        if (this.mAddToVideoViewManager) {
            PapaVideoViewManager.instance().releaseVideoPlayer();
            PapaVideoViewManager.instance().setCurrentVideoPlayer(this);
        }
        if (!checkNetwork()) {
            super.startPlay();
        }
    }

    private void addSurfaceView() {
        this.playerContainer.removeView(this.mSurfaceView);
        this.mSurfaceView = new ResizeSurfaceView(getContext());
        SurfaceHolder holder = this.mSurfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() { // from class: com.one.tomato.thirdpart.video.player.PapaVideoView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                if (((BaseIjkVideoView) PapaVideoView.this).mMediaPlayer != null) {
                    ((BaseIjkVideoView) PapaVideoView.this).mMediaPlayer.setDisplay(surfaceHolder);
                }
            }
        });
        holder.setFormat(1);
        this.playerContainer.addView(this.mSurfaceView, 0, new FrameLayout.LayoutParams(-1, -1, 17));
    }

    private void addTextureView() {
        this.playerContainer.removeView(this.mTextureView);
        this.mSurfaceTexture = null;
        this.mTextureView = new PapaTextureView(getContext());
        this.mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() { // from class: com.one.tomato.thirdpart.video.player.PapaVideoView.2
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                PapaVideoView papaVideoView = PapaVideoView.this;
                SurfaceTexture surfaceTexture2 = papaVideoView.mSurfaceTexture;
                if (surfaceTexture2 != null) {
                    papaVideoView.mTextureView.setSurfaceTexture(surfaceTexture2);
                    return;
                }
                papaVideoView.mSurfaceTexture = surfaceTexture;
                ((BaseIjkVideoView) papaVideoView).mMediaPlayer.setSurface(new Surface(surfaceTexture));
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return PapaVideoView.this.mSurfaceTexture == null;
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
        Activity scanForActivity;
        BaseVideoController baseVideoController = this.mVideoController;
        if (baseVideoController == null || (scanForActivity = WindowUtil.scanForActivity(baseVideoController.getContext())) == null || this.isFullScreen) {
            return;
        }
        WindowUtil.hideSystemBar(this.mVideoController.getContext());
        removeView(this.playerContainer);
        ((ViewGroup) scanForActivity.findViewById(16908290)).addView(this.playerContainer, new FrameLayout.LayoutParams(-1, -1));
        this.mOrientationEventListener.enable();
        this.isFullScreen = true;
        setPlayerState(11);
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void stopFullScreen() {
        Activity scanForActivity;
        BaseVideoController baseVideoController = this.mVideoController;
        if (baseVideoController == null || (scanForActivity = WindowUtil.scanForActivity(baseVideoController.getContext())) == null || !this.isFullScreen) {
            return;
        }
        if (!this.mAutoRotate) {
            this.mOrientationEventListener.disable();
        }
        WindowUtil.showSystemBar(this.mVideoController.getContext());
        ((ViewGroup) scanForActivity.findViewById(16908290)).removeView(this.playerContainer);
        addView(this.playerContainer, new FrameLayout.LayoutParams(-1, -1));
        requestFocus();
        this.isFullScreen = false;
        setPlayerState(10);
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public boolean isFullScreen() {
        return this.isFullScreen;
    }

    @Override // com.dueeeke.videoplayer.listener.PlayerEventListener
    public void onVideoSizeChanged(int i, int i2) {
        if (this.mUsingSurfaceView) {
            this.mSurfaceView.setScreenScale(this.mCurrentScreenScale);
            this.mSurfaceView.setVideoSize(i, i2);
            return;
        }
        if (this.isLand) {
            this.mTextureView.setRotation(90.0f);
        } else {
            this.mTextureView.setRotation(0.0f);
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
        PapaTextureView papaTextureView = this.mTextureView;
        if (papaTextureView == null) {
            return;
        }
        papaTextureView.setScreenScale(i);
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void replay(boolean z) {
        if (z) {
            this.mCurrentPosition = 0L;
        }
        addDisplay();
        startPrepare(true);
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void setMirrorRotation(boolean z) {
        PapaTextureView papaTextureView = this.mTextureView;
        if (papaTextureView != null) {
            papaTextureView.setScaleX(z ? -1.0f : 1.0f);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public Bitmap doScreenShot() {
        PapaTextureView papaTextureView = this.mTextureView;
        if (papaTextureView != null) {
            return papaTextureView.getBitmap();
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
