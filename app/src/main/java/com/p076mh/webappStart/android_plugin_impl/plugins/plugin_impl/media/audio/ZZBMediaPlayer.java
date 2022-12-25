package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.callback.JsCallBackKeys;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity.BaseUnity;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.WxAPIConfig;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls;
import java.io.IOException;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer */
/* loaded from: classes3.dex */
public class ZZBMediaPlayer extends CZMediaPlayer implements InnerAudioContextCalls {
    private static final String TAG = "ZZBMediaPlayer";
    private boolean autoplay;
    private int buffered;
    private String coverImgUrl;
    private String epname;
    private boolean loop;
    private final CZMediaPlayer.OnCZAudioFocusChangeListener onCZAudioFocusChangeListener;
    private CZMediaPlayer.PlayStateListener playStateListener;
    private String protocol;
    private String singer;
    private String src;
    private int startTime;
    private String title;
    private BaseUnity unity;
    private float volume;
    private String webUrl;
    protected IWebFragmentController webViewFragment;
    private boolean wx_onCanplay;
    private boolean wx_onEnded;
    private boolean wx_onError;
    private boolean wx_onPause;
    private boolean wx_onPlay;
    private boolean wx_onSeeked;
    private boolean wx_onSeeking;
    private boolean wx_onStop;
    private boolean wx_onTimeUpdate;
    private boolean wx_onWaiting;

    public ZZBMediaPlayer() {
        this.src = null;
        this.startTime = 0;
        this.autoplay = false;
        this.loop = false;
        this.volume = 1.0f;
        this.title = null;
        this.epname = "";
        this.singer = "";
        this.coverImgUrl = "";
        this.webUrl = "";
        this.protocol = "http";
        this.wx_onCanplay = false;
        this.wx_onPlay = false;
        this.wx_onPause = false;
        this.wx_onStop = false;
        this.wx_onEnded = false;
        this.wx_onTimeUpdate = false;
        this.wx_onError = false;
        this.wx_onWaiting = false;
        this.wx_onSeeking = false;
        this.wx_onSeeked = false;
        this.buffered = 0;
        this.playStateListener = new CZMediaPlayer.PlayStateListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer.1
            @Override // android.media.MediaPlayer.OnInfoListener
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                return false;
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
            public void onPrepare() {
            }

            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                Logger.m4112i(ZZBMediaPlayer.TAG, "onCommonError: what = " + i + ",extra = " + i2);
                if (ZZBMediaPlayer.this.wx_onError) {
                    ZZBMediaPlayer.this.runTask(null, JsCallBackKeys.ON_ERR_AUDIO);
                    return true;
                }
                return true;
            }

            @Override // android.media.MediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(MediaPlayer mediaPlayer) {
                if (ZZBMediaPlayer.this.wx_onSeeked) {
                    ZZBMediaPlayer.this.runTask(null, JsCallBackKeys.ON_SEEKED_AUDIO);
                }
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
            public void onMpStart() {
                Log.e(ZZBMediaPlayer.TAG, "onMpStart: " + ZZBMediaPlayer.this.wx_onPlay);
                if (ZZBMediaPlayer.this.wx_onPlay) {
                    ZZBMediaPlayer.this.runTask(null, JsCallBackKeys.ON_PLAY_AUDIO);
                }
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
            public void onMpStop() {
                Log.e(ZZBMediaPlayer.TAG, "onMpStart: " + ZZBMediaPlayer.this.wx_onStop);
                if (ZZBMediaPlayer.this.wx_onStop) {
                    ZZBMediaPlayer.this.runTask(null, JsCallBackKeys.ON_STOP_AUDIO);
                }
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
            public void onMpPause() {
                Log.e(ZZBMediaPlayer.TAG, "onMpPause: " + ZZBMediaPlayer.this.wx_onPause);
                if (ZZBMediaPlayer.this.wx_onPause) {
                    ZZBMediaPlayer.this.runTask(null, JsCallBackKeys.ON_PAUSE_AUDIO);
                }
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
            public void onSeeking() {
                if (ZZBMediaPlayer.this.wx_onSeeking) {
                    ZZBMediaPlayer.this.runTask(null, JsCallBackKeys.ON_SEEKING_AUDIO);
                }
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
            public void onPlaying(int i, int i2) {
                if (ZZBMediaPlayer.this.wx_onTimeUpdate) {
                    ZZBMediaPlayer.this.runTask(null, JsCallBackKeys.ON_UPDATE_AUDIO);
                }
                if (!ZZBMediaPlayer.this.wx_onWaiting || i <= ZZBMediaPlayer.this.buffered) {
                    return;
                }
                ZZBMediaPlayer.this.runTask(null, JsCallBackKeys.ON_WAIT_AUDIO);
            }

            @Override // android.media.MediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                ZZBMediaPlayer zZBMediaPlayer = ZZBMediaPlayer.this;
                zZBMediaPlayer.buffered = (int) ((i / 100.0f) * zZBMediaPlayer.getDuration());
            }

            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (ZZBMediaPlayer.this.wx_onEnded) {
                    ZZBMediaPlayer.this.runTask(null, JsCallBackKeys.ON_END_AUDIO);
                }
            }
        };
        this.onCZAudioFocusChangeListener = new CZMediaPlayer.OnCZAudioFocusChangeListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer.2
            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.OnCZAudioFocusChangeListener
            public void audiofocus_gain(AudioManager audioManager, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.OnCZAudioFocusChangeListener
            public void audiofocus_loss_transient_can_duck(AudioManager audioManager, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.OnCZAudioFocusChangeListener
            public void audiofocus_loss(AudioManager audioManager, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
                if (!WxAPIConfig.InnerAudioOption.mixWithOther) {
                    ZZBMediaPlayer.this.pause();
                }
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.OnCZAudioFocusChangeListener
            public void audiofocus_loss_transient(AudioManager audioManager, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
                ZZBMediaPlayer.this.pause();
            }
        };
        Logger.m4112i(TAG, "ZZBMediaPlayer 构造方法Call了");
        this.startTime = 0;
        this.autoplay = false;
        this.loop = false;
        this.volume = 1.0f;
        addPlayStateListener(this.playStateListener);
        setOnCZAudioFocusChangeListener(this.onCZAudioFocusChangeListener);
    }

    public ZZBMediaPlayer(BaseUnity baseUnity) {
        this();
        this.unity = baseUnity;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer
    protected void myStart() {
        int i;
        Log.e(TAG, "myStart222 " + this.mState);
        if (this.mState != CZMediaPlayer.State.PAUSED && (i = this.startTime) > 0) {
            seekTo(i);
        }
        super.myStart();
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer, android.media.MediaPlayer
    public void seekTo(int i) throws IllegalStateException {
        Log.e(TAG, "seekTo: " + i + "秒");
        super.seekTo(i * 1000);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_play() {
        if (!WxAPIConfig.InnerAudioOption.mixWithOther) {
            requestAudioFocus();
        }
        start();
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_pause() {
        pause();
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_stop() {
        this.startTime = 0;
        stop();
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_seek(int i) {
        seekTo(i);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_destroy() {
        release();
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public int wx_getDuration() {
        return (int) (getDuration() / 1000.0f);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public int wx_getCurrent() {
        return (int) (getCurrentPosition() / 1000.0f);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public int wx_getBuffered() {
        return this.buffered;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onCanplay() {
        this.wx_onCanplay = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offCanplay() {
        this.wx_onCanplay = false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer, android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        super.onPrepared(mediaPlayer);
        if (this.wx_onCanplay) {
            runTask(null, JsCallBackKeys.ON_CAN_PLAY_AUDIO);
        }
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onPlay() {
        this.wx_onPlay = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offPlay() {
        this.wx_onPlay = false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onPause() {
        Log.e(TAG, "wx_onPause: ");
        this.wx_onPause = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offPause() {
        Log.e(TAG, "wx_offPause: ");
        this.wx_onPause = false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onStop() {
        this.wx_onStop = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offStop() {
        this.wx_onStop = false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onEnded() {
        this.wx_onEnded = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offEnded() {
        this.wx_onEnded = false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onTimeUpdate() {
        this.wx_onTimeUpdate = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offTimeUpdate() {
        this.wx_onTimeUpdate = false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onError() {
        this.wx_onError = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offError() {
        this.wx_onError = false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onWaiting() {
        this.wx_onWaiting = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offWaiting() {
        this.wx_onWaiting = false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onSeeking() {
        this.wx_onSeeking = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offSeeking() {
        this.wx_onSeeking = false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_onSeeked() {
        this.wx_onSeeked = true;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls
    public void wx_offSeeked() {
        this.wx_onSeeked = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runTask(Runnable runnable, String str) {
        if (runnable != null) {
            runnable.run();
        }
        BaseUnity baseUnity = this.unity;
        if (baseUnity != null) {
            baseUnity.responseFunctionCallBackSuccess(str);
        } else {
            Log.e(TAG, "runTask but unity == null");
        }
    }

    public void setSrc(String str) {
        try {
            this.src = str;
            setDataSource(str);
            if (!this.autoplay) {
                return;
            }
            start();
        } catch (IOException e) {
            e.printStackTrace();
            onCommonError();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCommonError() {
        Log.e(TAG, "onCommonError: " + this.wx_onError);
        if (this.wx_onError) {
            runTask(null, JsCallBackKeys.ON_ERR_AUDIO);
        }
    }

    public String getSrc() {
        return this.src;
    }

    public void setStartTime(int i) {
        Log.e(TAG, "setStartTime: " + i);
        this.startTime = i;
    }

    public void setAutoplay(boolean z) {
        this.autoplay = z;
        if (!z || TextUtils.isEmpty(this.src)) {
            return;
        }
        start();
    }

    public void setWxLoop(boolean z) {
        this.loop = z;
        setLooping(z);
    }

    public void setVolume(float f) {
        this.volume = f;
        Logger.m4112i(TAG, "setVolume " + f);
        if (f >= 0.0f && f <= 1.0f) {
            setVolume(f, f);
            return;
        }
        Logger.m4112i(TAG, "Illegal Volume: " + f);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        Log.e(TAG, "setTitle: " + str);
        this.title = str;
    }

    public String getEpname() {
        return this.epname;
    }

    public void setEpname(String str) {
        Log.e(TAG, "setEpname: " + str);
        this.epname = str;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String str) {
        Log.e(TAG, "setSinger: " + str);
        this.singer = str;
    }

    public String getCoverImgUrl() {
        return this.coverImgUrl;
    }

    public void setCoverImgUrl(String str) {
        Log.e(TAG, "setCoverImgUrl: " + str);
        this.coverImgUrl = str;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String str) {
        Log.e(TAG, "setWebUrl: " + str);
        this.webUrl = str;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String str) {
        this.protocol = str;
    }

    public void setWebViewFragment(IWebFragmentController iWebFragmentController) {
        this.webViewFragment = iWebFragmentController;
    }

    public IWebFragmentController getWebViewFragment() {
        return this.webViewFragment;
    }

    public void setUnity(BaseUnity baseUnity) {
        this.unity = baseUnity;
    }
}
