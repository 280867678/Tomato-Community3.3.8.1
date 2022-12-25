package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.utils.Logger;
import com.one.tomato.entity.C2516Ad;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.WxAPIConfig;
import com.p076mh.webappStart.util.TimeCounter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer */
/* loaded from: classes3.dex */
public class CZMediaPlayer extends MediaPlayer implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, TimeCounter.TimeCallBack {
    private static final String TAG = "CZMediaPlayer";
    private AudioManager mAudioManager;
    private OnCZAudioFocusChangeListener onCZAudioFocusChangeListener;
    private List<PlayStateListener> playStateListenerList;
    private TimeCounter timeCounter;
    protected State mState = State.IDLE;
    private boolean isNetMode = false;
    private int duration = -1;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.1
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            if (i == -3) {
                Logger.m4116d(CZMediaPlayer.TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                if (CZMediaPlayer.this.onCZAudioFocusChangeListener == null) {
                    return;
                }
                CZMediaPlayer.this.onCZAudioFocusChangeListener.audiofocus_loss_transient_can_duck(CZMediaPlayer.this.mAudioManager, CZMediaPlayer.this.mAudioFocusChange);
            } else if (i == -2) {
                Logger.m4116d(CZMediaPlayer.TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                if (CZMediaPlayer.this.onCZAudioFocusChangeListener != null) {
                    CZMediaPlayer.this.onCZAudioFocusChangeListener.audiofocus_loss_transient(CZMediaPlayer.this.mAudioManager, CZMediaPlayer.this.mAudioFocusChange);
                } else {
                    CZMediaPlayer.this.pause();
                }
            } else if (i == -1) {
                Logger.m4116d(CZMediaPlayer.TAG, "AUDIOFOCUS_LOSS");
                if (CZMediaPlayer.this.onCZAudioFocusChangeListener != null) {
                    CZMediaPlayer.this.onCZAudioFocusChangeListener.audiofocus_loss(CZMediaPlayer.this.mAudioManager, CZMediaPlayer.this.mAudioFocusChange);
                } else {
                    CZMediaPlayer.this.pause();
                }
            } else if (i != 1) {
            } else {
                Log.d(CZMediaPlayer.TAG, "AUDIOFOCUS_GAIN");
                if (CZMediaPlayer.this.onCZAudioFocusChangeListener != null) {
                    CZMediaPlayer.this.onCZAudioFocusChangeListener.audiofocus_gain(CZMediaPlayer.this.mAudioManager, CZMediaPlayer.this.mAudioFocusChange);
                } else {
                    CZMediaPlayer.this.start();
                }
            }
        }
    };

    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer$OnCZAudioFocusChangeListener */
    /* loaded from: classes3.dex */
    public interface OnCZAudioFocusChangeListener {
        void audiofocus_gain(AudioManager audioManager, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener);

        void audiofocus_loss(AudioManager audioManager, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener);

        void audiofocus_loss_transient(AudioManager audioManager, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener);

        void audiofocus_loss_transient_can_duck(AudioManager audioManager, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener);
    }

    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer$PlayStateListener */
    /* loaded from: classes3.dex */
    public interface PlayStateListener extends MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnSeekCompleteListener {
        void onMpPause();

        void onMpStart();

        void onMpStop();

        void onPlaying(int i, int i2);

        void onPrepare();

        void onSeeking();
    }

    public CZMediaPlayer() {
        Logger.m4112i(TAG, "CZMediaPlayer 构造方法Call了");
        init();
    }

    private void init() {
        this.playStateListenerList = new ArrayList();
        this.mState = State.IDLE;
        this.isNetMode = false;
        if (!WxAPIConfig.InnerAudioOption.mixWithOther) {
            requestAudioFocus();
        }
        setOnCompletionListener(this);
        setOnBufferingUpdateListener(this);
        setOnSeekCompleteListener(this);
        setOnErrorListener(this);
        setOnInfoListener(this);
        this.timeCounter = new TimeCounter();
        this.timeCounter.setDelayTime(350L);
        this.timeCounter.setTimeCallBack(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void requestAudioFocus() {
        this.mAudioManager = (AudioManager) WebApplication.getInstance().getApplication().getSystemService("audio");
        this.mAudioManager.requestAudioFocus(this.mAudioFocusChange, 3, 1);
    }

    public void addPlayStateListener(PlayStateListener playStateListener) {
        Logger.m4112i(TAG, "addPlayStateListener: " + playStateListener);
        this.playStateListenerList.add(playStateListener);
    }

    public void setOnCZAudioFocusChangeListener(OnCZAudioFocusChangeListener onCZAudioFocusChangeListener) {
        this.onCZAudioFocusChangeListener = onCZAudioFocusChangeListener;
    }

    @Override // android.media.MediaPlayer
    public void setDataSource(String str) throws IOException, IllegalArgumentException, IllegalStateException, SecurityException {
        Logger.m4112i(TAG, "setDataSource: " + str);
        if (State.IDLE != this.mState) {
            reset();
        }
        super.setDataSource(str);
        this.mState = State.INITIALIZED;
        this.isNetMode = true;
    }

    @Override // android.media.MediaPlayer
    public void reset() {
        Logger.m4112i(TAG, "reset: ");
        super.reset();
        this.mState = State.IDLE;
        this.isNetMode = false;
    }

    @Override // android.media.MediaPlayer
    public int getDuration() {
        if (this.mState.index < State.PREPARED.index || this.mState.index > State.PLAYBACKCOMPLETED.index) {
            return 0;
        }
        this.duration = super.getDuration();
        return this.duration;
    }

    @Override // android.media.MediaPlayer
    public void start() throws IllegalStateException {
        if (isPlaying()) {
            Logger.m4113i("is playing now,don't call start again");
            return;
        }
        Log.e(TAG, "start: mState = " + this.mState);
        State state = State.PREPARED;
        State state2 = this.mState;
        if (state == state2) {
            myStart();
        } else if (State.INITIALIZED == state2) {
            setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.2
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer) {
                    CZMediaPlayer cZMediaPlayer = CZMediaPlayer.this;
                    cZMediaPlayer.mState = State.PREPARED;
                    cZMediaPlayer.myStart();
                }
            });
            prepareAsync();
        } else if (State.PREPARING == state2) {
            Logger.m4113i("is preparing now,please wait...");
        } else if (State.STOP == state2) {
            setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.3
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer) {
                    CZMediaPlayer cZMediaPlayer = CZMediaPlayer.this;
                    cZMediaPlayer.mState = State.PREPARED;
                    cZMediaPlayer.myStart();
                }
            });
            prepareAsync();
        } else {
            myStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void myStart() {
        Log.e(TAG, "myStart111" + this.mState);
        super.start();
        this.mState = State.STARTED;
        Logger.m4112i(TAG, C2516Ad.TYPE_START);
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onMpStart();
        }
        this.timeCounter.startCount();
    }

    @Override // android.media.MediaPlayer
    public void pause() throws IllegalStateException {
        if (State.STARTED == this.mState) {
            myPause();
        } else {
            Logger.m4112i(TAG, "pause can only called after start");
        }
    }

    private void myPause() {
        super.pause();
        this.mState = State.PAUSED;
        Logger.m4112i(TAG, "pause");
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onMpPause();
        }
        this.timeCounter.stop();
    }

    @Override // android.media.MediaPlayer
    public void stop() throws IllegalStateException {
        State state = State.STARTED;
        State state2 = this.mState;
        if (state == state2 || State.PAUSED == state2 || State.PLAYBACKCOMPLETED == state2) {
            myStop();
        } else {
            Logger.m4112i(TAG, "pause can only called after on state STARTED,PAUSED,PLAYBACKCOMPLETED");
        }
    }

    private void myStop() {
        super.stop();
        this.mState = State.STOP;
        Logger.m4112i(TAG, "stop");
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onMpStop();
        }
        this.timeCounter.stop();
    }

    @Override // android.media.MediaPlayer
    public void prepareAsync() throws IllegalStateException {
        Logger.m4112i(TAG, "prepareAsync: ");
        this.mState = State.PREPARING;
        super.prepareAsync();
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onPrepare();
        }
    }

    @Override // android.media.MediaPlayer
    public void prepare() throws IOException, IllegalStateException {
        this.mState = State.PREPARING;
        super.prepare();
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onPrepare();
        }
    }

    @Override // android.media.MediaPlayer
    public void seekTo(int i) throws IllegalStateException {
        Logger.m4112i(TAG, "seekTo: " + i);
        State state = State.PREPARED;
        State state2 = this.mState;
        if (state == state2 || State.STARTED == state2 || State.PAUSED == state2 || State.PLAYBACKCOMPLETED == state2) {
            super.seekTo(i);
            for (PlayStateListener playStateListener : this.playStateListenerList) {
                playStateListener.onSeeking();
            }
            return;
        }
        Log.e(TAG, "call seekTo on Illegal state");
    }

    @Override // android.media.MediaPlayer
    public void release() {
        super.release();
        this.mState = State.END;
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            audioManager.abandonAudioFocus(this.mAudioFocusChange);
        }
        TimeCounter timeCounter = this.timeCounter;
        if (timeCounter != null) {
            timeCounter.stop();
            this.timeCounter = null;
        }
        this.playStateListenerList.clear();
        this.onCZAudioFocusChangeListener = null;
        this.timeCounter = null;
        this.mAudioManager = null;
        this.isNetMode = false;
    }

    public static MediaPlayer create(Context context, MediaPlayer mediaPlayer, int i) {
        return create(context, mediaPlayer, i, (AudioAttributes) null, 0);
    }

    public static MediaPlayer create(Context context, MediaPlayer mediaPlayer, int i, AudioAttributes audioAttributes, int i2) {
        try {
            AssetFileDescriptor openRawResourceFd = context.getResources().openRawResourceFd(i);
            if (openRawResourceFd == null) {
                return null;
            }
            if (audioAttributes == null) {
                audioAttributes = new AudioAttributes.Builder().build();
            }
            mediaPlayer.setAudioAttributes(audioAttributes);
            mediaPlayer.setAudioSessionId(i2);
            mediaPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
            openRawResourceFd.close();
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (IOException e) {
            Log.d(TAG, "create failed:", e);
            return null;
        } catch (IllegalArgumentException e2) {
            Log.d(TAG, "create failed:", e2);
            return null;
        } catch (SecurityException e3) {
            Log.d(TAG, "create failed:", e3);
            return null;
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        this.mState = State.PLAYBACKCOMPLETED;
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onCompletion(mediaPlayer);
        }
        this.timeCounter.stop();
    }

    public State getmState() {
        return this.mState;
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onBufferingUpdate(mediaPlayer, i);
        }
    }

    @Override // com.p076mh.webappStart.util.TimeCounter.TimeCallBack
    public void onTime(int i, long j) {
        if (this.timeCounter != null) {
            for (PlayStateListener playStateListener : this.playStateListenerList) {
                if (this.duration < 0) {
                    this.duration = getDuration();
                }
                playStateListener.onPlaying(getCurrentPosition(), this.duration);
            }
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.mState = State.PREPARED;
        Logger.m4112i(TAG, "inner call onPrepared: ");
    }

    @Override // android.media.MediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onSeekComplete(mediaPlayer);
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        TimeCounter timeCounter = this.timeCounter;
        if (timeCounter != null) {
            timeCounter.startCount();
        }
        this.mState = State.ERROR;
        Logger.m4112i(TAG, "onError: what = " + i + ",extra = " + i2);
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onError(mediaPlayer, i, i2);
        }
        return true;
    }

    @Override // android.media.MediaPlayer.OnInfoListener
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        for (PlayStateListener playStateListener : this.playStateListenerList) {
            playStateListener.onInfo(mediaPlayer, i, i2);
        }
        return true;
    }

    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer$State */
    /* loaded from: classes3.dex */
    public enum State {
        IDLE(0),
        INITIALIZED(1),
        PREPARING(2),
        PREPARED(3),
        STARTED(4),
        PAUSED(5),
        STOP(6),
        PLAYBACKCOMPLETED(7),
        END(8),
        ERROR(9);
        
        private int index;

        State(int i) {
            this.index = -1;
            this.index = i;
        }
    }
}
