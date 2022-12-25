package com.tencent.liteav.audio;

import android.content.Context;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.audio.impl.TXCTraeJNI;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class TXCLiveBGMPlayer implements Runnable {
    private static final int PLAY_ERR_AUDIO_TRACK = -3;
    private static final int PLAY_ERR_AUDIO_TRACK_PLAY = -4;
    private static final int PLAY_ERR_FILE_NOTFOUND = -2;
    private static final int PLAY_ERR_OPEN = -1;
    private static final int PLAY_SUCCESS = 0;
    private static final String TAG = "AudioCenter:" + TXCLiveBGMPlayer.class.getSimpleName();
    private static TXCLiveBGMPlayer instance;
    private String mFilePath = null;
    private boolean mIsRunning = false;
    private Thread mThread = null;
    private boolean mIsPause = false;
    private WeakReference<TXIBGMOnPlayListener> mWeakListener = null;
    private int mAECType = TXEAudioDef.TXE_AEC_NONE;
    private float mPitch = 0.0f;
    private Context mContext = null;

    private native int nativeGetBitsPerChannel();

    private native int nativeGetChannels();

    private native long nativeGetCurDurationMS();

    private native long nativeGetCurPtsMS();

    private native long nativeGetDurationMS(String str);

    private native int nativeGetSampleRate();

    private native void nativePause();

    private native int nativeRead(byte[] bArr, int i);

    private native void nativeResume();

    private native void nativeSetPitch(float f);

    private native void nativeSetVolume(float f);

    private native boolean nativeStartPlay(String str, TXCLiveBGMPlayer tXCLiveBGMPlayer);

    private native void nativeStopPlay();

    static {
        TXCSystemUtil.m2873e();
    }

    public static TXCLiveBGMPlayer getInstance() {
        if (instance == null) {
            synchronized (TXCLiveBGMPlayer.class) {
                if (instance == null) {
                    instance = new TXCLiveBGMPlayer();
                }
            }
        }
        return instance;
    }

    private TXCLiveBGMPlayer() {
    }

    public synchronized void setOnPlayListener(TXIBGMOnPlayListener tXIBGMOnPlayListener) {
        if (tXIBGMOnPlayListener == null) {
            this.mWeakListener = null;
        }
        this.mWeakListener = new WeakReference<>(tXIBGMOnPlayListener);
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public boolean startPlay(String str, int i) {
        if (str == null || str.isEmpty()) {
            TXCLog.m2914e(TAG, "start live bgm failed! invalid params!");
            return false;
        }
        stopPlay();
        this.mAECType = i;
        this.mFilePath = str;
        this.mIsRunning = true;
        onPlayStart();
        if (!nativeStartPlay(this.mFilePath, this)) {
            onPlayEnd(-1);
            return false;
        }
        if (this.mAECType == TXEAudioDef.TXE_AEC_TRAE) {
            TXCTraeJNI.traeStartPlay(this.mContext);
        } else if (this.mThread == null) {
            this.mThread = new Thread(this, "BGMPlayer");
            this.mThread.start();
        }
        String str2 = TAG;
        TXCLog.m2913i(str2, "startPlay filePath = " + str);
        return true;
    }

    public void stopPlay() {
        this.mIsRunning = false;
        long currentTimeMillis = System.currentTimeMillis();
        Thread thread = this.mThread;
        if (thread != null && thread.isAlive() && Thread.currentThread().getId() != this.mThread.getId()) {
            try {
                this.mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.mThread = null;
        nativeStopPlay();
        TXCTraeJNI.traeStopPlay();
        this.mIsPause = false;
        String str = TAG;
        TXCLog.m2913i(str, "stopBGMPlay cost(MS): " + (System.currentTimeMillis() - currentTimeMillis));
    }

    public boolean isRunning() {
        return this.mIsRunning && !this.mIsPause;
    }

    public boolean isPlaying() {
        return this.mIsRunning;
    }

    public void switchAecType(int i) {
        if (!this.mIsRunning) {
            TXCLog.m2911w(TAG, "未开始播放BGM，不能切换AEC Type");
        } else if (this.mAECType == i) {
            String str = TAG;
            TXCLog.m2913i(str, "无需切换AEC Type. aecType = " + this.mAECType);
        } else {
            String str2 = TAG;
            TXCLog.m2913i(str2, "切换AEC Type为 " + i);
            this.mAECType = i;
            if (this.mAECType == TXEAudioDef.TXE_AEC_TRAE) {
                Thread thread = this.mThread;
                if (thread != null && thread.isAlive() && Thread.currentThread().getId() != this.mThread.getId()) {
                    try {
                        this.mThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.mThread = null;
            } else if (this.mThread != null) {
            } else {
                this.mThread = new Thread(this, "BGMPlayer");
                this.mThread.start();
            }
        }
    }

    public void pause() {
        TXCLog.m2913i(TAG, "pause");
        this.mIsPause = true;
        nativePause();
    }

    public void resume() {
        TXCLog.m2913i(TAG, "resume");
        this.mIsPause = false;
        nativeResume();
    }

    public void setVolume(float f) {
        nativeSetVolume(f);
    }

    public long getMusicDuration(String str) {
        return nativeGetDurationMS(str);
    }

    public void setPitch(float f) {
        this.mPitch = f;
        nativeSetPitch(f);
    }

    private void onPlayStart() {
        final TXIBGMOnPlayListener tXIBGMOnPlayListener;
        synchronized (this) {
            tXIBGMOnPlayListener = this.mWeakListener != null ? this.mWeakListener.get() : null;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.audio.TXCLiveBGMPlayer.1
            @Override // java.lang.Runnable
            public void run() {
                TXIBGMOnPlayListener tXIBGMOnPlayListener2 = tXIBGMOnPlayListener;
                if (tXIBGMOnPlayListener2 != null) {
                    tXIBGMOnPlayListener2.mo275a();
                }
            }
        });
    }

    private void onPlayEnd(final int i) {
        final TXIBGMOnPlayListener tXIBGMOnPlayListener;
        synchronized (this) {
            tXIBGMOnPlayListener = this.mWeakListener != null ? this.mWeakListener.get() : null;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.audio.TXCLiveBGMPlayer.2
            @Override // java.lang.Runnable
            public void run() {
                TXIBGMOnPlayListener tXIBGMOnPlayListener2 = tXIBGMOnPlayListener;
                if (tXIBGMOnPlayListener2 != null) {
                    tXIBGMOnPlayListener2.mo274a(i);
                }
            }
        });
    }

    private void onPlayProgress(long j, long j2) {
        TXIBGMOnPlayListener tXIBGMOnPlayListener;
        synchronized (this) {
            tXIBGMOnPlayListener = this.mWeakListener != null ? this.mWeakListener.get() : null;
        }
        if (tXIBGMOnPlayListener != null) {
            tXIBGMOnPlayListener.mo273a(j, j2);
        }
    }

    private void nativeOPlayProgress(long j, long j2) {
        onPlayProgress(j, j2);
        if (j == j2) {
            onPlayEnd(0);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        String str = this.mFilePath;
        int i = 0;
        AudioTrack audioTrack = null;
        if (str == null || str.isEmpty()) {
            i = -2;
        } else {
            int nativeGetSampleRate = nativeGetSampleRate();
            int nativeGetChannels = nativeGetChannels();
            int nativeGetBitsPerChannel = nativeGetBitsPerChannel();
            int i2 = 3;
            int i3 = nativeGetChannels == 1 ? 2 : 3;
            if (nativeGetBitsPerChannel != 8) {
                i2 = 2;
            }
            try {
                AudioTrack audioTrack2 = new AudioTrack(3, nativeGetSampleRate, i3, i2, AudioTrack.getMinBufferSize(nativeGetSampleRate, i3, i2), 1);
                try {
                    audioTrack2.play();
                    int i4 = nativeGetChannels * 2048;
                    byte[] bArr = new byte[i4];
                    while (true) {
                        if (!this.mIsRunning || Thread.interrupted() || this.mAECType == TXEAudioDef.TXE_AEC_TRAE) {
                            break;
                        }
                        int nativeRead = nativeRead(bArr, i4);
                        if (nativeRead < 0) {
                            onPlayProgress(nativeGetCurDurationMS(), nativeGetCurDurationMS());
                            break;
                        } else if (nativeRead == 0) {
                            if (this.mIsPause) {
                                try {
                                    Thread.sleep(100L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            audioTrack2.write(bArr, 0, nativeRead);
                            onPlayProgress(nativeGetCurPtsMS(), nativeGetCurDurationMS());
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    String str2 = TAG;
                    TXCLog.m2914e(str2, "AudioTrack play Exception: " + e2.getMessage());
                    i = -4;
                }
                audioTrack = audioTrack2;
            } catch (Exception e3) {
                e3.printStackTrace();
                String str3 = TAG;
                TXCLog.m2914e(str3, "new AudioTrack Exception: " + e3.getMessage());
                i = -3;
            }
        }
        if (audioTrack != null) {
            try {
                audioTrack.pause();
                audioTrack.flush();
                audioTrack.stop();
                audioTrack.release();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        String str4 = TAG;
        TXCLog.m2913i(str4, "Live BGM player play time: " + (System.currentTimeMillis() - currentTimeMillis));
        if (this.mIsRunning) {
            onPlayEnd(i);
        }
    }
}
