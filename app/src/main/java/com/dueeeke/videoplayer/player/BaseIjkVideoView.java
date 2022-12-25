package com.dueeeke.videoplayer.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.OrientationEventListener;
import android.widget.FrameLayout;
import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.dueeeke.videoplayer.C1228R;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.controller.MediaPlayerControl;
import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.listener.PlayerEventListener;
import com.dueeeke.videoplayer.m3u8.ProxyCacheManager;
import com.dueeeke.videoplayer.util.PlayerUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class BaseIjkVideoView extends FrameLayout implements MediaPlayerControl, PlayerEventListener {
    public static boolean IS_PLAY_ON_MOBILE_NETWORK = false;
    protected static final int LANDSCAPE = 2;
    public static final int PLAYER_FULL_SCREEN = 11;
    public static final int PLAYER_NORMAL = 10;
    public static final int PLAYER_TINY_SCREEN = 12;
    protected static final int PORTRAIT = 1;
    protected static final int REVERSE_LANDSCAPE = 3;
    public static final int STATE_BUFFERED = 7;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_ERROR = -1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_PLAYBACK_COMPLETED = 5;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_PREPARED = 2;
    public static final int STATE_PREPARING = 1;
    protected int bufferPercentage;
    private CacheListener cacheListener;
    protected String cdnUrl;
    private String decryptKey;
    protected String ipfsUrl;
    protected boolean mAddToVideoViewManager;
    protected AssetFileDescriptor mAssetFileDescriptor;
    @Nullable
    protected AudioFocusHelper mAudioFocusHelper;
    protected AudioManager mAudioManager;
    protected boolean mAutoRotate;
    private HttpProxyCacheServer mCacheServer;
    protected int mCurrentOrientation;
    protected int mCurrentPlayState;
    protected int mCurrentPlayerState;
    protected long mCurrentPosition;
    protected String mCurrentUrl;
    protected boolean mEnableAudioFocus;
    protected boolean mEnableMediaCodec;
    protected Map<String, String> mHeaders;
    protected boolean mIsLockFullScreen;
    protected boolean mIsLooping;
    protected boolean mIsMute;
    protected AbstractPlayer mMediaPlayer;
    protected List<OnVideoViewStateChangeListener> mOnVideoViewStateChangeListeners;
    protected OrientationEventListener mOrientationEventListener;
    @Nullable
    protected ProgressManager mProgressManager;
    protected AbstractPlayer mTempMediaPlayer;
    protected boolean mUsingSurfaceView;
    @Nullable
    protected BaseVideoController mVideoController;
    protected boolean offline;

    protected void onOrientationPortrait(Activity activity) {
        int i;
        if (this.mIsLockFullScreen || !this.mAutoRotate || (i = this.mCurrentOrientation) == 1) {
            return;
        }
        if ((i == 2 || i == 3) && !isFullScreen()) {
            this.mCurrentOrientation = 1;
            return;
        }
        this.mCurrentOrientation = 1;
        activity.setRequestedOrientation(1);
        stopFullScreen();
    }

    protected void onOrientationLandscape(Activity activity) {
        int i = this.mCurrentOrientation;
        if (i == 2) {
            return;
        }
        if (i == 1 && activity.getRequestedOrientation() != 8 && isFullScreen()) {
            this.mCurrentOrientation = 2;
            return;
        }
        this.mCurrentOrientation = 2;
        if (!isFullScreen()) {
            startFullScreen();
        }
        activity.setRequestedOrientation(0);
    }

    protected void onOrientationReverseLandscape(Activity activity) {
        int i = this.mCurrentOrientation;
        if (i == 3) {
            return;
        }
        if (i == 1 && activity.getRequestedOrientation() != 0 && isFullScreen()) {
            this.mCurrentOrientation = 3;
            return;
        }
        this.mCurrentOrientation = 3;
        if (!isFullScreen()) {
            startFullScreen();
        }
        activity.setRequestedOrientation(8);
    }

    public BaseIjkVideoView(@NonNull Context context) {
        this(context, null);
    }

    public BaseIjkVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseIjkVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentPlayState = 0;
        this.mCurrentPlayerState = 10;
        this.mCurrentOrientation = 0;
        this.mOrientationEventListener = new OrientationEventListener(getContext()) { // from class: com.dueeeke.videoplayer.player.BaseIjkVideoView.1
            private long mLastTime;

            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int i2) {
                BaseVideoController baseVideoController;
                Activity scanForActivity;
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.mLastTime < 300 || (baseVideoController = BaseIjkVideoView.this.mVideoController) == null || (scanForActivity = PlayerUtils.scanForActivity(baseVideoController.getContext())) == null) {
                    return;
                }
                if (i2 >= 340) {
                    BaseIjkVideoView.this.onOrientationPortrait(scanForActivity);
                } else if (i2 >= 260 && i2 <= 280) {
                    BaseIjkVideoView.this.onOrientationLandscape(scanForActivity);
                } else if (i2 >= 70 && i2 <= 90) {
                    BaseIjkVideoView.this.onOrientationReverseLandscape(scanForActivity);
                }
                this.mLastTime = currentTimeMillis;
            }
        };
        this.cacheListener = new CacheListener() { // from class: com.dueeeke.videoplayer.player.BaseIjkVideoView.2
            @Override // com.danikula.videocache.CacheListener
            public void onFileLoadIsWrong() {
            }

            @Override // com.danikula.videocache.CacheListener
            public void onCacheAvailable(File file, String str, int i2) {
                BaseIjkVideoView.this.bufferPercentage = i2;
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1228R.styleable.BaseIjkVideoView);
        this.mAutoRotate = obtainStyledAttributes.getBoolean(C1228R.styleable.BaseIjkVideoView_autoRotate, false);
        this.mUsingSurfaceView = obtainStyledAttributes.getBoolean(C1228R.styleable.BaseIjkVideoView_usingSurfaceView, false);
        this.mIsLooping = obtainStyledAttributes.getBoolean(C1228R.styleable.BaseIjkVideoView_looping, false);
        this.mEnableAudioFocus = obtainStyledAttributes.getBoolean(C1228R.styleable.BaseIjkVideoView_enableAudioFocus, true);
        this.mEnableMediaCodec = obtainStyledAttributes.getBoolean(C1228R.styleable.BaseIjkVideoView_enableMediaCodec, false);
        obtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initPlayer() {
        AbstractPlayer abstractPlayer = this.mTempMediaPlayer;
        if (abstractPlayer != null) {
            this.mMediaPlayer = abstractPlayer;
        } else {
            this.mMediaPlayer = new IjkPlayer(getContext());
        }
        this.mMediaPlayer.bindVideoView(this);
        this.mMediaPlayer.initPlayer(this.decryptKey);
        this.mMediaPlayer.setEnableMediaCodec(this.mEnableMediaCodec);
        this.mMediaPlayer.setLooping(this.mIsLooping);
    }

    public void setDecryptKey(String str) {
        this.decryptKey = str;
    }

    public void setPlayState(int i) {
        this.mCurrentPlayState = i;
        BaseVideoController baseVideoController = this.mVideoController;
        if (baseVideoController != null) {
            baseVideoController.setPlayState(i);
        }
        List<OnVideoViewStateChangeListener> list = this.mOnVideoViewStateChangeListeners;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnVideoViewStateChangeListener onVideoViewStateChangeListener = this.mOnVideoViewStateChangeListeners.get(i2);
                if (onVideoViewStateChangeListener != null) {
                    onVideoViewStateChangeListener.onPlayStateChanged(i);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setPlayerState(int i) {
        this.mCurrentPlayerState = i;
        BaseVideoController baseVideoController = this.mVideoController;
        if (baseVideoController != null) {
            baseVideoController.setPlayerState(i);
        }
        List<OnVideoViewStateChangeListener> list = this.mOnVideoViewStateChangeListeners;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnVideoViewStateChangeListener onVideoViewStateChangeListener = this.mOnVideoViewStateChangeListeners.get(i2);
                if (onVideoViewStateChangeListener != null) {
                    onVideoViewStateChangeListener.onPlayerStateChanged(i);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void startPrepare(boolean z) {
        if (!TextUtils.isEmpty(this.mCurrentUrl) || this.mAssetFileDescriptor != null) {
            if (z) {
                this.mMediaPlayer.reset();
            }
            AssetFileDescriptor assetFileDescriptor = this.mAssetFileDescriptor;
            if (assetFileDescriptor != null) {
                this.mMediaPlayer.setDataSource(assetFileDescriptor);
            } else if (isS3Url()) {
                this.mCacheServer = ProxyCacheManager.instance().getProxy(getContext().getApplicationContext(), this.offline, this.mCurrentUrl, this.ipfsUrl);
                if (!TextUtils.isEmpty(this.ipfsUrl)) {
                    this.mCurrentUrl = this.ipfsUrl;
                }
                this.mCacheServer.registerCacheListener(this.cacheListener, this.mCurrentUrl);
                this.mMediaPlayer.setDataSource(this.mCacheServer.getProxyUrl(this.mCurrentUrl), this.mHeaders);
            } else {
                this.mMediaPlayer.setDataSource(this.mCurrentUrl, this.mHeaders);
            }
            this.mMediaPlayer.prepareAsync();
            setPlayState(1);
            setPlayerState(isFullScreen() ? 11 : isTinyScreen() ? 12 : 10);
        }
    }

    public boolean isS3Url() {
        return !TextUtils.isEmpty(this.cdnUrl) && Arrays.asList(this.cdnUrl.split("/")).contains("_s3");
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void start() {
        if (this.mCurrentPlayState == 0) {
            startPlay();
        } else if (isInPlaybackState()) {
            startInPlaybackState();
        }
        setKeepScreenOn(true);
        AudioFocusHelper audioFocusHelper = this.mAudioFocusHelper;
        if (audioFocusHelper != null) {
            audioFocusHelper.requestFocus();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void startPlay() {
        if (this.mAddToVideoViewManager) {
            VideoViewManager.instance().releaseVideoPlayer();
            VideoViewManager.instance().setCurrentVideoPlayer(this);
        }
        if (checkNetwork()) {
            return;
        }
        if (this.mEnableAudioFocus) {
            this.mAudioManager = (AudioManager) getContext().getApplicationContext().getSystemService("audio");
            this.mAudioFocusHelper = new AudioFocusHelper();
        }
        ProgressManager progressManager = this.mProgressManager;
        if (progressManager != null) {
            this.mCurrentPosition = progressManager.getSavedProgress(this.mCurrentUrl);
        }
        if (this.mAutoRotate) {
            this.mOrientationEventListener.enable();
        }
        initPlayer();
        startPrepare(false);
    }

    protected boolean checkNetwork() {
        if (!this.offline && PlayerUtils.getNetworkType(getContext()) == 4 && !IS_PLAY_ON_MOBILE_NETWORK) {
            BaseVideoController baseVideoController = this.mVideoController;
            if (baseVideoController == null) {
                return true;
            }
            baseVideoController.showStatusView();
            return true;
        }
        return false;
    }

    public int getBufferPercentage() {
        AbstractPlayer abstractPlayer = this.mMediaPlayer;
        if (abstractPlayer != null) {
            return abstractPlayer.getBufferedPercentage();
        }
        return 0;
    }

    protected void startInPlaybackState() {
        this.mMediaPlayer.start();
        setPlayState(3);
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void pause() {
        if (isPlaying()) {
            this.mMediaPlayer.pause();
            setPlayState(4);
            setKeepScreenOn(false);
            AudioFocusHelper audioFocusHelper = this.mAudioFocusHelper;
            if (audioFocusHelper == null) {
                return;
            }
            audioFocusHelper.abandonFocus();
        }
    }

    public void resume() {
        if (!isInPlaybackState() || this.mMediaPlayer.isPlaying()) {
            return;
        }
        this.mMediaPlayer.start();
        setPlayState(3);
        AudioFocusHelper audioFocusHelper = this.mAudioFocusHelper;
        if (audioFocusHelper != null) {
            audioFocusHelper.requestFocus();
        }
        setKeepScreenOn(true);
    }

    public void stopPlayback() {
        if (this.mProgressManager != null && isInPlaybackState()) {
            this.mProgressManager.saveProgress(this.mCurrentUrl, this.mCurrentPosition);
        }
        AbstractPlayer abstractPlayer = this.mMediaPlayer;
        if (abstractPlayer != null) {
            abstractPlayer.stop();
            setPlayState(0);
            AudioFocusHelper audioFocusHelper = this.mAudioFocusHelper;
            if (audioFocusHelper != null) {
                audioFocusHelper.abandonFocus();
            }
            setKeepScreenOn(false);
        }
        onPlayStopped();
    }

    public void release() {
        if (this.mProgressManager != null && isInPlaybackState()) {
            this.mProgressManager.saveProgress(this.mCurrentUrl, this.mCurrentPosition);
        }
        AbstractPlayer abstractPlayer = this.mMediaPlayer;
        if (abstractPlayer != null) {
            abstractPlayer.release();
            this.mMediaPlayer = null;
            setPlayState(0);
            AudioFocusHelper audioFocusHelper = this.mAudioFocusHelper;
            if (audioFocusHelper != null) {
                audioFocusHelper.abandonFocus();
            }
            setKeepScreenOn(false);
        }
        onPlayStopped();
        this.offline = false;
        this.cdnUrl = null;
        this.ipfsUrl = null;
    }

    public void onPlayStopped() {
        BaseVideoController baseVideoController = this.mVideoController;
        if (baseVideoController != null) {
            baseVideoController.hideStatusView();
        }
        this.mOrientationEventListener.disable();
        HttpProxyCacheServer httpProxyCacheServer = this.mCacheServer;
        if (httpProxyCacheServer != null) {
            httpProxyCacheServer.unregisterCacheListener(this.cacheListener);
        }
        this.mIsLockFullScreen = false;
        this.mCurrentPosition = 0L;
    }

    public void addOnVideoViewStateChangeListener(@NonNull OnVideoViewStateChangeListener onVideoViewStateChangeListener) {
        if (this.mOnVideoViewStateChangeListeners == null) {
            this.mOnVideoViewStateChangeListeners = new ArrayList();
        }
        this.mOnVideoViewStateChangeListeners.add(onVideoViewStateChangeListener);
    }

    public void removeOnVideoViewStateChangeListener(@NonNull OnVideoViewStateChangeListener onVideoViewStateChangeListener) {
        List<OnVideoViewStateChangeListener> list = this.mOnVideoViewStateChangeListeners;
        if (list != null) {
            list.remove(onVideoViewStateChangeListener);
        }
    }

    public void clearOnVideoViewStateChangeListeners() {
        List<OnVideoViewStateChangeListener> list = this.mOnVideoViewStateChangeListeners;
        if (list != null) {
            list.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isInPlaybackState() {
        int i;
        return (this.mMediaPlayer == null || (i = this.mCurrentPlayState) == -1 || i == 0 || i == 1 || i == 5) ? false : true;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public long getDuration() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getDuration();
        }
        return 0L;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public long getCurrentPosition() {
        if (isInPlaybackState()) {
            this.mCurrentPosition = this.mMediaPlayer.getCurrentPosition();
            return this.mCurrentPosition;
        }
        return 0L;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void seekTo(long j) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(j);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public int getBufferedPercentage() {
        AbstractPlayer abstractPlayer = this.mMediaPlayer;
        if (abstractPlayer != null) {
            return abstractPlayer.getBufferedPercentage();
        }
        return 0;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void setMute(boolean z) {
        if (this.mMediaPlayer != null) {
            this.mIsMute = z;
            float f = z ? 0.0f : 1.0f;
            this.mMediaPlayer.setVolume(f, f);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public boolean isMute() {
        return this.mIsMute;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void setLock(boolean z) {
        this.mIsLockFullScreen = z;
    }

    @Override // com.dueeeke.videoplayer.listener.PlayerEventListener
    public void onError(String str) {
        setPlayState(-1);
    }

    @Override // com.dueeeke.videoplayer.listener.PlayerEventListener
    public void onCompletion() {
        setPlayState(5);
        setKeepScreenOn(false);
        this.mCurrentPosition = 0L;
    }

    @Override // com.dueeeke.videoplayer.listener.PlayerEventListener
    public void onInfo(int i, int i2) {
        if (i == 3) {
            setPlayState(3);
            if (getWindowVisibility() == 0) {
                return;
            }
            pause();
        } else if (i == 701) {
            setPlayState(6);
        } else if (i != 702) {
        } else {
            setPlayState(7);
        }
    }

    @Override // com.dueeeke.videoplayer.listener.PlayerEventListener
    public void onPrepared() {
        setPlayState(2);
        long j = this.mCurrentPosition;
        if (j > 0) {
            seekTo(j);
        }
    }

    public int getCurrentPlayerState() {
        return this.mCurrentPlayerState;
    }

    public int getCurrentPlayState() {
        return this.mCurrentPlayState;
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public long getTcpSpeed() {
        return this.mMediaPlayer.getTcpSpeed();
    }

    @Override // com.dueeeke.videoplayer.controller.MediaPlayerControl
    public void setSpeed(float f) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.setSpeed(f);
        }
    }

    public void setOffline(boolean z) {
        this.offline = z;
    }

    public void setUrl(String str) {
        this.mCurrentUrl = str;
        this.cdnUrl = str;
    }

    public String getUrl() {
        return this.mCurrentUrl;
    }

    public void setUrl(String str, Map<String, String> map) {
        this.mCurrentUrl = str;
        this.mHeaders = map;
    }

    public void setAssetFileDescriptor(AssetFileDescriptor assetFileDescriptor) {
        this.mAssetFileDescriptor = assetFileDescriptor;
    }

    public void skipPositionWhenPlay(long j) {
        this.mCurrentPosition = j;
    }

    public void setVolume(float f, float f2) {
        AbstractPlayer abstractPlayer = this.mMediaPlayer;
        if (abstractPlayer != null) {
            abstractPlayer.setVolume(f, f2);
        }
    }

    public void setProgressManager(@Nullable ProgressManager progressManager) {
        this.mProgressManager = progressManager;
    }

    public void setLooping(boolean z) {
        this.mIsLooping = z;
        AbstractPlayer abstractPlayer = this.mMediaPlayer;
        if (abstractPlayer != null) {
            abstractPlayer.setLooping(z);
        }
    }

    public void setAutoRotate(boolean z) {
        this.mAutoRotate = z;
    }

    public void setUsingSurfaceView(boolean z) {
        this.mUsingSurfaceView = z;
    }

    public void setEnableAudioFocus(boolean z) {
        this.mEnableAudioFocus = z;
    }

    public void setEnableMediaCodec(boolean z) {
        this.mEnableMediaCodec = z;
    }

    public void addToVideoViewManager() {
        this.mAddToVideoViewManager = true;
    }

    public void setCustomMediaPlayer(@NonNull AbstractPlayer abstractPlayer) {
        this.mTempMediaPlayer = abstractPlayer;
    }

    public void setPlayOnMobileNetwork(boolean z) {
        IS_PLAY_ON_MOBILE_NETWORK = z;
    }

    public void setIpfsUrl(String str) {
        this.ipfsUrl = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class AudioFocusHelper implements AudioManager.OnAudioFocusChangeListener {
        private int currentFocus;
        private boolean pausedForLoss;
        private boolean startRequested;

        private AudioFocusHelper() {
            this.startRequested = false;
            this.pausedForLoss = false;
            this.currentFocus = 0;
        }

        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            if (this.currentFocus == i) {
                return;
            }
            this.currentFocus = i;
            if (i == -3) {
                BaseIjkVideoView baseIjkVideoView = BaseIjkVideoView.this;
                if (baseIjkVideoView.mMediaPlayer == null || !baseIjkVideoView.isPlaying()) {
                    return;
                }
                BaseIjkVideoView baseIjkVideoView2 = BaseIjkVideoView.this;
                if (baseIjkVideoView2.mIsMute) {
                    return;
                }
                baseIjkVideoView2.mMediaPlayer.setVolume(0.1f, 0.1f);
            } else if (i == -2 || i == -1) {
                if (!BaseIjkVideoView.this.isPlaying()) {
                    return;
                }
                this.pausedForLoss = true;
                BaseIjkVideoView.this.pause();
            } else if (i != 1 && i != 2) {
            } else {
                if (this.startRequested || this.pausedForLoss) {
                    BaseIjkVideoView.this.start();
                    this.startRequested = false;
                    this.pausedForLoss = false;
                }
                BaseIjkVideoView baseIjkVideoView3 = BaseIjkVideoView.this;
                AbstractPlayer abstractPlayer = baseIjkVideoView3.mMediaPlayer;
                if (abstractPlayer == null || baseIjkVideoView3.mIsMute) {
                    return;
                }
                abstractPlayer.setVolume(1.0f, 1.0f);
            }
        }

        void requestFocus() {
            AudioManager audioManager;
            if (this.currentFocus == 1 || (audioManager = BaseIjkVideoView.this.mAudioManager) == null) {
                return;
            }
            if (1 == audioManager.requestAudioFocus(this, 3, 1)) {
                this.currentFocus = 1;
            } else {
                this.startRequested = true;
            }
        }

        void abandonFocus() {
            AudioManager audioManager = BaseIjkVideoView.this.mAudioManager;
            if (audioManager == null) {
                return;
            }
            this.startRequested = false;
            audioManager.abandonAudioFocus(this);
        }
    }

    public boolean onBackPressed() {
        BaseVideoController baseVideoController = this.mVideoController;
        return baseVideoController != null && baseVideoController.onBackPressed();
    }
}
