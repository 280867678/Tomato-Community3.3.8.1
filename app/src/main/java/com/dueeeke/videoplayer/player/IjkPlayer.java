package com.dueeeke.videoplayer.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.dueeeke.videoplayer.listener.PlayerEventListener;
import java.util.Map;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* loaded from: classes2.dex */
public class IjkPlayer extends AbstractPlayer {
    private String decryptKey;
    protected Context mAppContext;
    private int mBufferedPercent;
    private boolean mIsEnableMediaCodec;
    private boolean mIsLooping;
    protected IjkMediaPlayer mMediaPlayer;
    private IMediaPlayer.OnErrorListener onErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.dueeeke.videoplayer.player.IjkPlayer.2
        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
            PlayerEventListener playerEventListener = IjkPlayer.this.mPlayerEventListener;
            playerEventListener.onError("视频播放IMediaPlayer：framework_err：" + i + "   impl_errP:" + i2);
            return true;
        }
    };
    private IMediaPlayer.OnCompletionListener onCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.dueeeke.videoplayer.player.IjkPlayer.3
        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener
        public void onCompletion(IMediaPlayer iMediaPlayer) {
            IjkPlayer.this.mPlayerEventListener.onCompletion();
        }
    };
    private IMediaPlayer.OnInfoListener onInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.dueeeke.videoplayer.player.IjkPlayer.4
        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
            IjkPlayer.this.mPlayerEventListener.onInfo(i, i2);
            return true;
        }
    };
    private IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.dueeeke.videoplayer.player.IjkPlayer.5
        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
            IjkPlayer.this.mBufferedPercent = i;
        }
    };
    private IMediaPlayer.OnPreparedListener onPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.dueeeke.videoplayer.player.IjkPlayer.6
        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            IjkPlayer.this.mPlayerEventListener.onPrepared();
        }
    };
    private IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.dueeeke.videoplayer.player.IjkPlayer.7
        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener
        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
            int videoWidth = iMediaPlayer.getVideoWidth();
            int videoHeight = iMediaPlayer.getVideoHeight();
            if (videoWidth == 0 || videoHeight == 0) {
                return;
            }
            IjkPlayer.this.mPlayerEventListener.onVideoSizeChanged(videoWidth, videoHeight);
        }
    };

    public IjkPlayer(Context context) {
        this.mAppContext = context.getApplicationContext();
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void initPlayer() {
        this.mMediaPlayer = new IjkMediaPlayer();
        this.mMediaPlayer.setLogEnabled(false);
        setOptions();
        this.mMediaPlayer.setAudioStreamType(3);
        this.mMediaPlayer.setOnErrorListener(this.onErrorListener);
        this.mMediaPlayer.setOnCompletionListener(this.onCompletionListener);
        this.mMediaPlayer.setOnInfoListener(this.onInfoListener);
        this.mMediaPlayer.setOnBufferingUpdateListener(this.onBufferingUpdateListener);
        this.mMediaPlayer.setOnPreparedListener(this.onPreparedListener);
        this.mMediaPlayer.setOnVideoSizeChangedListener(this.onVideoSizeChangedListener);
        this.mMediaPlayer.setOnNativeInvokeListener(new IjkMediaPlayer.OnNativeInvokeListener() { // from class: com.dueeeke.videoplayer.player.IjkPlayer.1
            @Override // tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener
            public boolean onNativeInvoke(int i, Bundle bundle) {
                return true;
            }
        });
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void initPlayer(String str) {
        this.decryptKey = str;
        initPlayer();
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void setOptions() {
        this.mMediaPlayer.setOption(1, "dns_cache_clear", 1L);
        if (!TextUtils.isEmpty(this.decryptKey)) {
            this.mMediaPlayer.setOption(1, "httpreadbytemask", this.decryptKey);
        }
        this.mMediaPlayer.setOption(1, "fflags", "fastseek");
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void setDataSource(String str, Map<String, String> map) {
        try {
            Uri parse = Uri.parse(str);
            if ("android.resource".equals(parse.getScheme())) {
                this.mMediaPlayer.setDataSource(RawDataSourceProvider.create(this.mAppContext, parse));
            } else {
                this.mMediaPlayer.setDataSource(this.mAppContext, parse, map);
            }
        } catch (Exception e) {
            PlayerEventListener playerEventListener = this.mPlayerEventListener;
            playerEventListener.onError("setDataSource1：" + e.getMessage());
        }
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void setDataSource(AssetFileDescriptor assetFileDescriptor) {
        try {
            this.mMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor());
        } catch (Exception e) {
            PlayerEventListener playerEventListener = this.mPlayerEventListener;
            playerEventListener.onError("setDataSource2：" + e.getMessage());
        }
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void pause() {
        try {
            this.mMediaPlayer.pause();
        } catch (IllegalStateException e) {
            PlayerEventListener playerEventListener = this.mPlayerEventListener;
            playerEventListener.onError("pause：" + e.getMessage());
        }
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void start() {
        try {
            this.mMediaPlayer.start();
        } catch (IllegalStateException e) {
            PlayerEventListener playerEventListener = this.mPlayerEventListener;
            playerEventListener.onError("start：" + e.getMessage());
        }
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void stop() {
        try {
            this.mMediaPlayer.stop();
        } catch (IllegalStateException e) {
            PlayerEventListener playerEventListener = this.mPlayerEventListener;
            playerEventListener.onError("stop：" + e.getMessage());
        }
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void prepareAsync() {
        try {
            this.mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            PlayerEventListener playerEventListener = this.mPlayerEventListener;
            playerEventListener.onError("prepareAsync：" + e.getMessage());
        }
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void reset() {
        this.mMediaPlayer.reset();
        this.mMediaPlayer.setOnVideoSizeChangedListener(this.onVideoSizeChangedListener);
        this.mMediaPlayer.setLooping(this.mIsLooping);
        setOptions();
        setEnableMediaCodec(this.mIsEnableMediaCodec);
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public boolean isPlaying() {
        return this.mMediaPlayer.isPlaying();
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void seekTo(long j) {
        try {
            this.mMediaPlayer.seekTo((int) j);
        } catch (IllegalStateException e) {
            PlayerEventListener playerEventListener = this.mPlayerEventListener;
            playerEventListener.onError("seekTo：" + e.getMessage());
        }
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void release() {
        this.mMediaPlayer.release();
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public long getCurrentPosition() {
        return this.mMediaPlayer.getCurrentPosition();
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public long getDuration() {
        return this.mMediaPlayer.getDuration();
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public int getBufferedPercentage() {
        return this.mBufferedPercent;
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void setSurface(Surface surface) {
        this.mMediaPlayer.setSurface(surface);
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void setDisplay(SurfaceHolder surfaceHolder) {
        this.mMediaPlayer.setDisplay(surfaceHolder);
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void setVolume(float f, float f2) {
        this.mMediaPlayer.setVolume(f, f2);
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void setLooping(boolean z) {
        this.mIsLooping = z;
        this.mMediaPlayer.setLooping(z);
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void setEnableMediaCodec(boolean z) {
        this.mIsEnableMediaCodec = z;
        IjkMediaPlayer ijkMediaPlayer = this.mMediaPlayer;
        long j = z ? 1L : 0L;
        ijkMediaPlayer.setOption(4, "mediacodec", j);
        this.mMediaPlayer.setOption(4, "mediacodec-auto-rotate", j);
        this.mMediaPlayer.setOption(4, "mediacodec-handle-resolution-change", j);
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public void setSpeed(float f) {
        this.mMediaPlayer.setSpeed(f);
    }

    @Override // com.dueeeke.videoplayer.player.AbstractPlayer
    public long getTcpSpeed() {
        return this.mMediaPlayer.getTcpSpeed();
    }
}
