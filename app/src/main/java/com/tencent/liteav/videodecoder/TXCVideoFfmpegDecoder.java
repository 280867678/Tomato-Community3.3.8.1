package com.tencent.liteav.videodecoder;

import android.view.Surface;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class TXCVideoFfmpegDecoder implements IVideoDecoder {
    private boolean mFirstDec;
    private TXIVideoDecoderListener mListener;
    private long mNativeDecoder;
    private long mNativeNotify;
    private ByteBuffer mPps;
    private byte[] mRawData;
    private ByteBuffer mSps;
    private int mVideoHeight;
    private int mVideoWidth;

    private static native void nativeClassInit();

    private native boolean nativeDecode(byte[] bArr, long j, long j2);

    private native void nativeInit(WeakReference<TXCVideoFfmpegDecoder> weakReference, boolean z);

    private native void nativeLoadRawData(byte[] bArr, long j, int i);

    private native void nativeRelease();

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public int config(Surface surface) {
        return 0;
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public boolean isHevc() {
        return false;
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public void setNotifyListener(WeakReference<TXINotifyListener> weakReference) {
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public void setListener(TXIVideoDecoderListener tXIVideoDecoderListener) {
        this.mListener = tXIVideoDecoderListener;
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public void decode(TXSNALPacket tXSNALPacket) {
        if (this.mFirstDec) {
            ByteBuffer byteBuffer = this.mSps;
            if (byteBuffer != null && this.mPps != null) {
                byte[] array = byteBuffer.array();
                byte[] array2 = this.mPps.array();
                byte[] bArr = new byte[array.length + array2.length];
                System.arraycopy(array, 0, bArr, 0, array.length);
                System.arraycopy(array2, 0, bArr, array.length, array2.length);
                nativeDecode(bArr, tXSNALPacket.pts - 1, tXSNALPacket.dts - 1);
            }
            this.mFirstDec = false;
        }
        nativeDecode(tXSNALPacket.nalData, tXSNALPacket.pts, tXSNALPacket.dts);
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public int start(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z, boolean z2) {
        this.mSps = byteBuffer;
        this.mPps = byteBuffer2;
        this.mFirstDec = true;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        nativeInit(new WeakReference<>(this), z);
        return 0;
    }

    @Override // com.tencent.liteav.videodecoder.IVideoDecoder
    public void stop() {
        nativeRelease();
    }

    private static void postEventFromNative(WeakReference<TXCVideoFfmpegDecoder> weakReference, long j, int i, int i2, long j2, long j3) {
        TXIVideoDecoderListener tXIVideoDecoderListener;
        TXCVideoFfmpegDecoder tXCVideoFfmpegDecoder = weakReference.get();
        if (tXCVideoFfmpegDecoder == null || (tXIVideoDecoderListener = tXCVideoFfmpegDecoder.mListener) == null) {
            return;
        }
        tXIVideoDecoderListener.mo589a(j, i, i2, j2, j3);
        if (tXCVideoFfmpegDecoder.mVideoWidth == i && tXCVideoFfmpegDecoder.mVideoHeight == i2) {
            return;
        }
        tXCVideoFfmpegDecoder.mVideoWidth = i;
        tXCVideoFfmpegDecoder.mVideoHeight = i2;
        tXIVideoDecoderListener.mo590a(tXCVideoFfmpegDecoder.mVideoWidth, tXCVideoFfmpegDecoder.mVideoHeight);
    }

    public void loadNativeData(byte[] bArr, long j, int i) {
        nativeLoadRawData(bArr, j, i);
    }

    static {
        TXCSystemUtil.m2873e();
        nativeClassInit();
    }
}
