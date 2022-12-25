package com.tencent.liteav.videoencoder;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.beauty.p115b.TXCGPURGBA2I420Filter;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class TXCSWVideoEncoder extends TXIVideoEncoder {
    private static final boolean DEBUG = false;
    private static final String TAG = "TXCSWVideoEncoder";
    private TXCGPUFilter mRawFrameFilter;
    private TXCGPUFilter mResizeFilter;
    private long mNativeX264Encoder = 0;
    private int mBitrate = 0;
    private long mPTS = 0;
    private int mPushIdx = 0;
    private int mRendIdx = 0;
    private int mPopIdx = 0;

    private static native void nativeClassInit();

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeEncode(long j, int i, int i2, int i3, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeEncodeSync(long j, int i, int i2, int i3, long j2);

    private native long nativeGetRealFPS(long j);

    private native long nativeInit(WeakReference<TXCSWVideoEncoder> weakReference);

    private native void nativeRelease(long j);

    private native void nativeSetBitrate(long j, int i);

    private native void nativeSignalEOSAndFlush(long j);

    private native int nativeStart(long j, TXSVideoEncoderParam tXSVideoEncoderParam);

    private native void nativeStop(long j);

    private native long nativegetRealBitrate(long j);

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public void setFPS(int i) {
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public int start(TXSVideoEncoderParam tXSVideoEncoderParam) {
        super.start(tXSVideoEncoderParam);
        int i = tXSVideoEncoderParam.width;
        int i2 = ((i + 7) / 8) * 8;
        int i3 = tXSVideoEncoderParam.height;
        int i4 = ((i3 + 1) / 2) * 2;
        if (i2 != i || i4 != i3) {
            TXCLog.m2911w(TAG, "Encode Resolution not supportted, transforming...");
            String str = TAG;
            TXCLog.m2911w(str, tXSVideoEncoderParam.width + "x" + tXSVideoEncoderParam.height + "-> " + i2 + "x" + i4);
        }
        tXSVideoEncoderParam.width = i2;
        tXSVideoEncoderParam.height = i4;
        this.mOutputWidth = i2;
        this.mOutputHeight = i4;
        this.mInputWidth = i2;
        this.mInputHeight = i4;
        this.mNativeX264Encoder = nativeInit(new WeakReference<>(this));
        nativeSetBitrate(this.mNativeX264Encoder, this.mBitrate);
        nativeStart(this.mNativeX264Encoder, tXSVideoEncoderParam);
        return 0;
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public void stop() {
        this.mGLContextExternal = null;
        nativeStop(this.mNativeX264Encoder);
        nativeRelease(this.mNativeX264Encoder);
        this.mNativeX264Encoder = 0L;
        TXCGPUFilter tXCGPUFilter = this.mRawFrameFilter;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.mRawFrameFilter = null;
        }
        TXCGPUFilter tXCGPUFilter2 = this.mResizeFilter;
        if (tXCGPUFilter2 != null) {
            tXCGPUFilter2.mo1351e();
            this.mResizeFilter = null;
        }
        super.stop();
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public void setBitrate(int i) {
        this.mBitrate = i;
        nativeSetBitrate(this.mNativeX264Encoder, i);
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public long getRealFPS() {
        return nativeGetRealFPS(this.mNativeX264Encoder);
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public long getRealBitrate() {
        return nativegetRealBitrate(this.mNativeX264Encoder);
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public long pushVideoFrame(int i, int i2, int i3, long j) {
        return pushVideoFrameInternal(i, i2, i3, j, false);
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public long pushVideoFrameSync(int i, int i2, int i3, long j) {
        return pushVideoFrameInternal(i, i2, i3, j, true);
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoder
    public void signalEOSAndFlush() {
        nativeSignalEOSAndFlush(this.mNativeX264Encoder);
    }

    private static void postEventFromNative(WeakReference<TXCSWVideoEncoder> weakReference, byte[] bArr, int i, long j, long j2, long j3, long j4, long j5, long j6, int i2) {
        TXCSWVideoEncoder tXCSWVideoEncoder = weakReference.get();
        if (tXCSWVideoEncoder != null) {
            tXCSWVideoEncoder.callDelegate(bArr, i, j, j2, j3, j4, j5, j6, i2, null, null);
        }
    }

    private long pushVideoFrameInternal(final int i, int i2, int i3, long j, final boolean z) {
        if (this.mGLContextExternal != null) {
            if (this.mInputWidth != i2 || this.mInputHeight != i3) {
                this.mInputWidth = i2;
                this.mInputHeight = i3;
                if (this.mResizeFilter == null) {
                    this.mResizeFilter = new TXCGPUFilter();
                    this.mResizeFilter.mo2653c();
                    this.mResizeFilter.mo1353a(true);
                }
                this.mResizeFilter.mo1333a(this.mOutputWidth, this.mOutputHeight);
            }
            GLES20.glViewport(0, 0, this.mOutputWidth, this.mOutputHeight);
            TXCGPUFilter tXCGPUFilter = this.mResizeFilter;
            if (tXCGPUFilter != null) {
                tXCGPUFilter.mo2294a(i);
            }
            TXCGPUFilter tXCGPUFilter2 = this.mResizeFilter;
            if (tXCGPUFilter2 != null) {
                i = tXCGPUFilter2.m3016l();
            }
            int[] iArr = new int[1];
            this.mPTS = j;
            if (this.mRawFrameFilter == null) {
                this.mRawFrameFilter = new TXCGPURGBA2I420Filter(1);
                this.mRawFrameFilter.mo1353a(true);
                if (!this.mRawFrameFilter.mo2653c()) {
                    this.mRawFrameFilter = null;
                    return 10000004L;
                }
                this.mRawFrameFilter.mo1333a(this.mOutputWidth, this.mOutputHeight);
                this.mRawFrameFilter.m3029a(new TXCGPUFilter.AbstractC3355a() { // from class: com.tencent.liteav.videoencoder.TXCSWVideoEncoder.1
                    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter.AbstractC3355a
                    /* renamed from: a */
                    public void mo458a(int i4) {
                        if (z) {
                            TXCSWVideoEncoder tXCSWVideoEncoder = TXCSWVideoEncoder.this;
                            long j2 = tXCSWVideoEncoder.mNativeX264Encoder;
                            int i5 = i;
                            TXCSWVideoEncoder tXCSWVideoEncoder2 = TXCSWVideoEncoder.this;
                            tXCSWVideoEncoder.nativeEncodeSync(j2, i5, tXCSWVideoEncoder2.mOutputWidth, tXCSWVideoEncoder2.mOutputHeight, tXCSWVideoEncoder2.mPTS);
                            return;
                        }
                        TXCSWVideoEncoder tXCSWVideoEncoder3 = TXCSWVideoEncoder.this;
                        long j3 = tXCSWVideoEncoder3.mNativeX264Encoder;
                        int i6 = i;
                        TXCSWVideoEncoder tXCSWVideoEncoder4 = TXCSWVideoEncoder.this;
                        tXCSWVideoEncoder3.nativeEncode(j3, i6, tXCSWVideoEncoder4.mOutputWidth, tXCSWVideoEncoder4.mOutputHeight, tXCSWVideoEncoder4.mPTS);
                    }
                });
            }
            if (this.mRawFrameFilter == null) {
                return 10000004L;
            }
            GLES20.glViewport(0, 0, this.mOutputWidth, this.mOutputHeight);
            this.mRawFrameFilter.mo2294a(i);
            int i4 = iArr[0];
            if (i4 == 0) {
                return 0L;
            }
            callDelegate(i4);
            return 0L;
        }
        return 0L;
    }

    static {
        TXCSystemUtil.m2873e();
        nativeClassInit();
    }
}
