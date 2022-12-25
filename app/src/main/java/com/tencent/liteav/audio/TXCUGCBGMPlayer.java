package com.tencent.liteav.audio;

import android.media.AudioTrack;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class TXCUGCBGMPlayer implements Runnable {
    private static final int PLAY_ERR_AUDIO_TRACK = -3;
    private static final int PLAY_ERR_AUDIO_TRACK_PLAY = -4;
    private static final int PLAY_ERR_FILE_NOTFOUND = -2;
    private static final int PLAY_ERR_OPEN = -1;
    private static final int PLAY_SUCCESS = 0;
    private static final String TAG = "AudioCenter:TXCUGCBGMPlayer";
    private static TXCUGCBGMPlayer instance;
    private String mFilePath = null;
    private boolean mIsRunning = false;
    private Thread mThread = null;
    private boolean mIsPause = false;
    private WeakReference<TXIBGMOnPlayListener> mWeakListener = null;
    private float mVolume = 1.0f;
    private float mSpeedRate = 1.0f;
    private long mStartTimeMS = 0;
    private long mEndTimeMS = 0;
    private long mSeekBytes = 0;

    private native int nativeGetBitsPerChannel();

    private native int nativeGetChannels();

    private native long nativeGetCurDurationMS();

    private native long nativeGetCurPosition();

    private native long nativeGetCurPtsMS();

    private static native long nativeGetDurationMS(String str);

    private native int nativeGetSampleRate();

    private native void nativePause();

    private native void nativePlayFromTime(long j, long j2);

    private native int nativeRead(byte[] bArr, int i);

    private native void nativeResume();

    private native void nativeSeekBytes(long j);

    private native void nativeSetSpeedRate(float f);

    private native void nativeSetVolume(float f);

    private native boolean nativeStartPlay(String str);

    private native void nativeStopPlay();

    static {
        TXCSystemUtil.m2873e();
    }

    public static TXCUGCBGMPlayer getInstance() {
        if (instance == null) {
            synchronized (TXCUGCBGMPlayer.class) {
                if (instance == null) {
                    instance = new TXCUGCBGMPlayer();
                }
            }
        }
        return instance;
    }

    private TXCUGCBGMPlayer() {
    }

    public synchronized void setOnPlayListener(TXIBGMOnPlayListener tXIBGMOnPlayListener) {
        if (tXIBGMOnPlayListener == null) {
            this.mWeakListener = null;
        }
        this.mWeakListener = new WeakReference<>(tXIBGMOnPlayListener);
    }

    public void startPlay(String str) {
        TXCLog.m2913i(TAG, "startPlay:" + str);
        if (str == null || str.isEmpty()) {
            return;
        }
        if (this.mIsRunning) {
            TXCLog.m2911w(TAG, "BGM正在播放中，将重新启动");
            stopPlay();
        }
        this.mIsPause = false;
        this.mSeekBytes = 0L;
        this.mFilePath = str;
        this.mIsRunning = true;
        this.mThread = new Thread(this, "UGCBGMPlayer");
        this.mThread.start();
    }

    public void stopPlay() {
        TXCLog.m2913i(TAG, "stopPlay");
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
        synchronized (this) {
            nativeStopPlay();
        }
        TXCLog.m2913i(TAG, "stopBGMPlay cost(MS): " + (System.currentTimeMillis() - currentTimeMillis));
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
        this.mVolume = f;
        nativeSetVolume(f);
    }

    public void setSpeedRate(float f) {
        TXCLog.m2913i(TAG, "setSpeedRate:" + f);
        this.mSpeedRate = f;
        nativeSetSpeedRate(f);
    }

    public void playFromTime(long j, long j2) {
        TXCLog.m2913i(TAG, "playFromTime:" + j + ", " + j2);
        this.mStartTimeMS = j;
        this.mEndTimeMS = j2;
        nativePlayFromTime(j, j2);
    }

    public void seekBytes(long j) {
        TXCLog.m2913i(TAG, "seekBytes:" + j);
        if (j < 0) {
            TXCLog.m2914e(TAG, "seek bytes can not be negative. change to 0");
            j = 0;
        }
        this.mSeekBytes = j;
        nativeSeekBytes(j);
    }

    public long getCurPosition() {
        long nativeGetCurPosition = nativeGetCurPosition();
        TXCLog.m2913i(TAG, "getCurPosition:" + nativeGetCurPosition);
        return nativeGetCurPosition;
    }

    public static long getDurationMS(String str) {
        return nativeGetDurationMS(str);
    }

    private void onPlayStart() {
        TXIBGMOnPlayListener tXIBGMOnPlayListener;
        synchronized (this) {
            tXIBGMOnPlayListener = this.mWeakListener != null ? this.mWeakListener.get() : null;
        }
        if (tXIBGMOnPlayListener != null) {
            tXIBGMOnPlayListener.mo275a();
        }
    }

    private void onPlayEnd(int i) {
        TXIBGMOnPlayListener tXIBGMOnPlayListener;
        synchronized (this) {
            tXIBGMOnPlayListener = this.mWeakListener != null ? this.mWeakListener.get() : null;
        }
        if (tXIBGMOnPlayListener != null) {
            tXIBGMOnPlayListener.mo274a(i);
        }
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

    @Override // java.lang.Runnable
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        onPlayStart();
        String str = this.mFilePath;
        int i = 0;
        AudioTrack audioTrack = null;
        if (str == null || str.isEmpty()) {
            TXCLog.m2914e(TAG, "file path = " + this.mFilePath);
            i = -2;
        } else {
            nativeSetVolume(this.mVolume);
            nativeSetSpeedRate(this.mSpeedRate);
            nativePlayFromTime(this.mStartTimeMS, this.mEndTimeMS);
            nativeSeekBytes(this.mSeekBytes);
            if (!nativeStartPlay(this.mFilePath)) {
                i = -1;
            } else {
                if (this.mIsPause) {
                    nativePause();
                } else {
                    nativeResume();
                }
                TXCLog.m2913i(TAG, "start play bgm: path = " + this.mFilePath + "volume = " + this.mVolume + ", speedRate = " + this.mSpeedRate + ", startTime = " + this.mStartTimeMS + ", endTime = " + this.mEndTimeMS + ", seekBytes = " + this.mSeekBytes + ", pause = " + this.mIsPause);
                int nativeGetSampleRate = nativeGetSampleRate();
                int nativeGetChannels = nativeGetChannels();
                int nativeGetBitsPerChannel = nativeGetBitsPerChannel();
                int i2 = nativeGetChannels == 1 ? 2 : 3;
                int i3 = nativeGetBitsPerChannel == 8 ? 3 : 2;
                try {
                    AudioTrack audioTrack2 = new AudioTrack(3, nativeGetSampleRate, i2, i3, AudioTrack.getMinBufferSize(nativeGetSampleRate, i2, i3), 1);
                    try {
                        audioTrack2.play();
                        byte[] bArr = new byte[nativeGetChannels * 2048 * 2];
                        while (true) {
                            if (!this.mIsRunning || Thread.interrupted()) {
                                break;
                            }
                            int nativeRead = nativeRead(bArr, bArr.length);
                            if (nativeRead < 0) {
                                TXCLog.m2913i(TAG, "UGC BGM播放结束");
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
                        TXCLog.m2914e(TAG, "AudioTrack play Exception: " + e2.getMessage());
                        i = -4;
                    }
                    audioTrack = audioTrack2;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    TXCLog.m2914e(TAG, "new AudioTrack Exception: " + e3.getMessage());
                    i = -3;
                }
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
        TXCLog.m2913i(TAG, "UGC BGM player play time: " + (System.currentTimeMillis() - currentTimeMillis));
        if (this.mIsRunning) {
            onPlayEnd(i);
        }
    }
}
