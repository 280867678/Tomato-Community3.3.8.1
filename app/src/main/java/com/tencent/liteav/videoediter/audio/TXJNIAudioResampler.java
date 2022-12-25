package com.tencent.liteav.videoediter.audio;

import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes3.dex */
public class TXJNIAudioResampler {
    private static final String TAG = "TXJNIAudioResampler";
    private long handle = init();
    private volatile boolean isInitSuccess;

    private native short[] flushBuffer(long j);

    private native int init();

    private native short[] resample(long j, short[] sArr);

    private native void setChannelCount(long j, int i);

    private native void setSampleRate(long j, int i, int i2);

    private native void setSpeed(long j, float f);

    private native void uninit(long j);

    public TXJNIAudioResampler() {
        if (this.handle != -1) {
            this.isInitSuccess = true;
        }
    }

    public synchronized void destroy() {
        if (this.isInitSuccess) {
            this.isInitSuccess = false;
            uninit(this.handle);
            this.handle = -1L;
        }
    }

    public synchronized void setSpeed(float f) {
        if (this.isInitSuccess) {
            setSpeed(this.handle, f);
        } else {
            TXCLog.m2914e(TAG, "you must init first!!!");
        }
    }

    public synchronized void setSampleRate(int i, int i2) {
        if (this.isInitSuccess) {
            setSampleRate(this.handle, i, i2);
        } else {
            TXCLog.m2914e(TAG, "you must init first!!!");
        }
    }

    public synchronized void setChannelCount(int i) {
        if (this.isInitSuccess) {
            setChannelCount(this.handle, i);
        } else {
            TXCLog.m2914e(TAG, "you must init first!!!");
        }
    }

    public synchronized short[] resample(short[] sArr) {
        if (this.isInitSuccess) {
            return resample(this.handle, sArr);
        }
        TXCLog.m2914e(TAG, "you must init first!!!");
        return sArr;
    }

    public synchronized short[] flushBuffer() {
        if (this.isInitSuccess) {
            return flushBuffer(this.handle);
        }
        TXCLog.m2914e(TAG, "you must init first!!!");
        return null;
    }
}
