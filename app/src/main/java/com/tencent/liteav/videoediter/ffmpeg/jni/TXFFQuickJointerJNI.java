package com.tencent.liteav.videoediter.ffmpeg.jni;

import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import java.util.List;

/* loaded from: classes3.dex */
public class TXFFQuickJointerJNI {
    private static final String TAG = "TXFFQuickJointerJNI";
    private long handle;
    private boolean isInitSuccess;
    private AbstractC3671a mCallback;
    private int mTotalVideoNums;

    /* renamed from: com.tencent.liteav.videoediter.ffmpeg.jni.TXFFQuickJointerJNI$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3671a {
        /* renamed from: a */
        void mo459a(float f);
    }

    private native void destroy(long j);

    private native int getVideoHeight(long j);

    private native int getVideoWidth(long j);

    private native long init();

    private native void setDstPath(long j, String str);

    private native void setSrcPaths(long j, String[] strArr);

    private native int start(long j);

    private native int stop(long j);

    private native int verify(long j);

    public TXFFQuickJointerJNI() {
        this.handle = -1L;
        this.handle = init();
        if (this.handle != -1) {
            this.isInitSuccess = true;
        }
    }

    public synchronized void destroy() {
        if (this.isInitSuccess) {
            destroy(this.handle);
            this.isInitSuccess = false;
            this.handle = -1L;
        }
    }

    public synchronized int getVideoWidth() {
        if (this.isInitSuccess) {
            return getVideoWidth(this.handle);
        }
        return 0;
    }

    public synchronized int getVideoHeight() {
        if (this.isInitSuccess) {
            return getVideoHeight(this.handle);
        }
        return 0;
    }

    public synchronized void setSrcPaths(List<String> list) {
        if (this.isInitSuccess) {
            if (list != null && list.size() != 0) {
                this.mTotalVideoNums = list.size();
                String[] strArr = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    strArr[i] = list.get(i);
                }
                setSrcPaths(this.handle, strArr);
            }
            TXCLog.m2914e(TAG, "quick joiner path empty!!!");
        }
    }

    public synchronized void setDstPath(String str) {
        if (this.isInitSuccess) {
            setDstPath(this.handle, str);
        }
    }

    public synchronized int start() {
        if (this.isInitSuccess) {
            if (this.mTotalVideoNums == 0) {
                TXCLog.m2914e(TAG, "quick joiner path empty!!!");
                return -1;
            }
            return start(this.handle);
        }
        return -1;
    }

    public synchronized int verify() {
        if (this.isInitSuccess) {
            return verify(this.handle);
        }
        return -1;
    }

    public synchronized void stop() {
        if (this.isInitSuccess) {
            stop(this.handle);
        }
    }

    public void setOnJoinerCallback(AbstractC3671a abstractC3671a) {
        this.mCallback = abstractC3671a;
    }

    public void callbackFromNative(int i) {
        Log.i(TAG, "callbackFromNative: index = " + i);
        AbstractC3671a abstractC3671a = this.mCallback;
        if (abstractC3671a != null) {
            int i2 = this.mTotalVideoNums;
            abstractC3671a.mo459a(i2 > 0 ? (i + 1) / i2 : 0.0f);
        }
    }
}
