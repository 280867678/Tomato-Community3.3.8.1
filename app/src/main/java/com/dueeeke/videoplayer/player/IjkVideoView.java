package com.dueeeke.videoplayer.player;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.util.PlayerUtils;
import com.dueeeke.videoplayer.widget.ResizeSurfaceView;
import com.dueeeke.videoplayer.widget.ResizeTextureView;

/* loaded from: classes2.dex */
public class IjkVideoView extends BaseIjkVideoView {
    protected static final int FULLSCREEN_FLAGS = 4098;
    public static final int SCREEN_SCALE_16_9 = 1;
    public static final int SCREEN_SCALE_4_3 = 2;
    public static final int SCREEN_SCALE_CENTER_CROP = 5;
    public static final int SCREEN_SCALE_DEFAULT = 0;
    public static final int SCREEN_SCALE_MATCH_PARENT = 3;
    public static final int SCREEN_SCALE_ORIGINAL = 4;
    protected int mCurrentScreenScale;
    protected View mHideNavBarView;
    protected boolean mIsFullScreen;
    protected boolean mIsTinyScreen;
    protected FrameLayout mPlayerContainer;
    protected SurfaceTexture mSurfaceTexture;
    protected ResizeSurfaceView mSurfaceView;
    protected ResizeTextureView mTextureView;
    protected int[] mTinyScreenSize;
    protected int[] mVideoSize;

    public IjkVideoView(@NonNull Context context) {
        this(context, null);
    }

    public IjkVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public IjkVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentScreenScale = 0;
        this.mVideoSize = new int[]{0, 0};
        this.mTinyScreenSize = new int[]{0, 0};
        initView();
    }

    protected void initView() {
        this.mPlayerContainer = new FrameLayout(getContext());
        this.mPlayerContainer.setBackgroundColor(0);
        addView(this.mPlayerContainer, new FrameLayout.LayoutParams(-1, -1));
        this.mHideNavBarView = new View(getContext());
        this.mHideNavBarView.setSystemUiVisibility(4098);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void initPlayer() {
        super.initPlayer();
        addDisplay();
    }

    protected void addDisplay() {
        if (this.mUsingSurfaceView || Build.VERSION.SDK_INT <= 19) {
            addSurfaceView();
        } else {
            addTextureView();
        }
    }

    private void addSurfaceView() {
        this.mPlayerContainer.removeView(this.mSurfaceView);
        this.mSurfaceView = new ResizeSurfaceView(getContext());
        SurfaceHolder holder = this.mSurfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() { // from class: com.dueeeke.videoplayer.player.IjkVideoView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                AbstractPlayer abstractPlayer = IjkVideoView.this.mMediaPlayer;
                if (abstractPlayer != null) {
                    abstractPlayer.setDisplay(surfaceHolder);
                }
            }
        });
        holder.setFormat(1);
        this.mPlayerContainer.addView(this.mSurfaceView, 0, new FrameLayout.LayoutParams(-1, -1, 17));
    }

    public void addTextureView() {
        this.mPlayerContainer.removeView(this.mTextureView);
        this.mSurfaceTexture = null;
        this.mTextureView = new ResizeTextureView(getContext());
        this.mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() { // from class: com.dueeeke.videoplayer.player.IjkVideoView.2
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                IjkVideoView ijkVideoView = IjkVideoView.this;
                SurfaceTexture surfaceTexture2 = ijkVideoView.mSurfaceTexture;
                if (surfaceTexture2 != null) {
                    ijkVideoView.mTextureView.setSurfaceTexture(surfaceTexture2);
                    return;
                }
                ijkVideoView.mSurfaceTexture = surfaceTexture;
                ijkVideoView.mMediaPlayer.setSurface(new Surface(surfaceTexture));
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return IjkVideoView.this.mSurfaceTexture == null;
            }
        });
        this.mPlayerContainer.addView(this.mTextureView, 0, new FrameLayout.LayoutParams(-1, -1, 17));
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void release() {
        super.release();
        this.mPlayerContainer.removeView(this.mTextureView);
        this.mPlayerContainer.removeView(this.mSurfaceView);
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.release();
            this.mSurfaceTexture = null;
        }
        this.mCurrentScreenScale = 0;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void startFullScreen() {
        BaseVideoController baseVideoController;
        Activity scanForActivity;
        if (this.mIsFullScreen || (baseVideoController = this.mVideoController) == null || (scanForActivity = PlayerUtils.scanForActivity(baseVideoController.getContext())) == null) {
            return;
        }
        PlayerUtils.hideActionBar(scanForActivity);
        addView(this.mHideNavBarView);
        scanForActivity.getWindow().setFlags(1024, 1024);
        removeView(this.mPlayerContainer);
        ((ViewGroup) scanForActivity.findViewById(16908290)).addView(this.mPlayerContainer, new FrameLayout.LayoutParams(-1, -1));
        this.mIsFullScreen = true;
        setPlayerState(11);
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void stopFullScreen() {
        BaseVideoController baseVideoController;
        Activity scanForActivity;
        if (!this.mIsFullScreen || (baseVideoController = this.mVideoController) == null || (scanForActivity = PlayerUtils.scanForActivity(baseVideoController.getContext())) == null) {
            return;
        }
        PlayerUtils.showActionBar(scanForActivity);
        removeView(this.mHideNavBarView);
        scanForActivity.getWindow().clearFlags(1024);
        ((ViewGroup) scanForActivity.findViewById(16908290)).removeView(this.mPlayerContainer);
        addView(this.mPlayerContainer, new FrameLayout.LayoutParams(-1, -1));
        this.mIsFullScreen = false;
        setPlayerState(10);
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public boolean isFullScreen() {
        return this.mIsFullScreen;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void startTinyScreen() {
        Activity scanForActivity;
        if (!this.mIsTinyScreen && (scanForActivity = PlayerUtils.scanForActivity(getContext())) != null) {
            this.mOrientationEventListener.disable();
            removeView(this.mPlayerContainer);
            ViewGroup viewGroup = (ViewGroup) scanForActivity.findViewById(16908290);
            int i = this.mTinyScreenSize[0];
            if (i <= 0) {
                i = PlayerUtils.getScreenWidth(scanForActivity, false) / 2;
            }
            int i2 = this.mTinyScreenSize[1];
            if (i2 <= 0) {
                i2 = (i * 9) / 16;
            }
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i2);
            layoutParams.gravity = 8388693;
            viewGroup.addView(this.mPlayerContainer, layoutParams);
            this.mIsTinyScreen = true;
            setPlayerState(12);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void stopTinyScreen() {
        Activity scanForActivity;
        if (this.mIsTinyScreen && (scanForActivity = PlayerUtils.scanForActivity(getContext())) != null) {
            ((ViewGroup) scanForActivity.findViewById(16908290)).removeView(this.mPlayerContainer);
            addView(this.mPlayerContainer, new FrameLayout.LayoutParams(-1, -1));
            if (this.mAutoRotate) {
                this.mOrientationEventListener.enable();
            }
            this.mIsTinyScreen = false;
            setPlayerState(10);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public boolean isTinyScreen() {
        return this.mIsTinyScreen;
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView, com.dueeeke.videoplayer.listener.PlayerEventListener
    public void onInfo(int i, int i2) {
        ResizeTextureView resizeTextureView;
        super.onInfo(i, i2);
        if (i == 10001 && (resizeTextureView = this.mTextureView) != null) {
            resizeTextureView.setRotation(i2);
        }
    }

    @Override // com.dueeeke.videoplayer.listener.PlayerEventListener
    public void onVideoSizeChanged(int i, int i2) {
        int[] iArr = this.mVideoSize;
        iArr[0] = i;
        iArr[1] = i2;
        if (this.mUsingSurfaceView || Build.VERSION.SDK_INT <= 19) {
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
        if (z) {
            this.mHideNavBarView.setSystemUiVisibility(4098);
        }
        if (isInPlaybackState()) {
            if (!this.mAutoRotate && !this.mIsFullScreen) {
                return;
            }
            if (z) {
                postDelayed(new Runnable() { // from class: com.dueeeke.videoplayer.player.IjkVideoView.3
                    @Override // java.lang.Runnable
                    public void run() {
                        IjkVideoView.this.mOrientationEventListener.disable();
                    }
                }, 800L);
            } else {
                this.mOrientationEventListener.disable();
            }
        }
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void replay(boolean z) {
        if (z) {
            this.mCurrentPosition = 0L;
        }
        addDisplay();
        startPrepare(true);
    }

    public void setVideoController(@Nullable BaseVideoController baseVideoController) {
        this.mPlayerContainer.removeView(this.mVideoController);
        this.mVideoController = baseVideoController;
        if (baseVideoController != null) {
            baseVideoController.setMediaPlayer(this);
            this.mPlayerContainer.addView(this.mVideoController, new FrameLayout.LayoutParams(-1, -1));
        }
    }

    public void removerBaseVideoController() {
        this.mPlayerContainer.removeView(this.mVideoController);
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

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public int[] getVideoSize() {
        return this.mVideoSize;
    }

    @Override // android.view.View, com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void setRotation(float f) {
        ResizeTextureView resizeTextureView = this.mTextureView;
        if (resizeTextureView != null) {
            resizeTextureView.setRotation(f);
            this.mTextureView.requestLayout();
        }
        ResizeSurfaceView resizeSurfaceView = this.mSurfaceView;
        if (resizeSurfaceView != null) {
            resizeSurfaceView.setRotation(f);
            this.mSurfaceView.requestLayout();
        }
    }

    public void setTinyScreenSize(int[] iArr) {
        this.mTinyScreenSize = iArr;
    }
}
