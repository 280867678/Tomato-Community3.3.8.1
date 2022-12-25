package com.tencent.liteav.videoencoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import com.tencent.liteav.basic.module.TXCModule;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import java.nio.ByteBuffer;

/* renamed from: com.tencent.liteav.videoencoder.c */
/* loaded from: classes3.dex */
public class TXIVideoEncoder extends TXCModule {
    protected TXCGPUFilter mEncodeFilter;
    protected boolean mInit;
    protected TXCGPUFilter mInputFilter;
    protected TXIVideoEncoderListener mListener = null;
    protected int mInputWidth = 0;
    protected int mInputHeight = 0;
    protected int mOutputWidth = 0;
    protected int mOutputHeight = 0;
    protected int mInputTextureID = -1;
    protected Object mGLContextExternal = null;
    private long mVideoGOPEncode = 0;
    private boolean mEncodeFirstGOP = false;

    public long getRealBitrate() {
        return 0L;
    }

    public long getRealFPS() {
        return 0L;
    }

    public long pushVideoFrame(int i, int i2, int i3, long j) {
        return 10000002L;
    }

    public long pushVideoFrameSync(int i, int i2, int i3, long j) {
        return 10000002L;
    }

    public void setBitrate(int i) {
    }

    public void setFPS(int i) {
    }

    public void signalEOSAndFlush() {
    }

    public void stop() {
    }

    public int start(TXSVideoEncoderParam tXSVideoEncoderParam) {
        if (tXSVideoEncoderParam != null) {
            int i = tXSVideoEncoderParam.width;
            this.mOutputWidth = i;
            int i2 = tXSVideoEncoderParam.height;
            this.mOutputHeight = i2;
            this.mInputWidth = i;
            this.mInputHeight = i2;
            this.mGLContextExternal = tXSVideoEncoderParam.glContext;
        }
        this.mVideoGOPEncode = 0L;
        this.mEncodeFirstGOP = false;
        return 10000002;
    }

    public void setListener(TXIVideoEncoderListener tXIVideoEncoderListener) {
        this.mListener = tXIVideoEncoderListener;
    }

    public int getVideoWidth() {
        return this.mOutputWidth;
    }

    public int getVideoHeight() {
        return this.mOutputHeight;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void callDelegate(int i) {
        callDelegate(null, 0, 0L, 0L, 0L, 0L, 0L, 0L, i, null, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void callDelegate(byte[] bArr, int i, long j, long j2, long j3, long j4, long j5, long j6, int i2, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        MediaCodec.BufferInfo bufferInfo2 = null;
        ByteBuffer asReadOnlyBuffer = byteBuffer == null ? null : byteBuffer.asReadOnlyBuffer();
        if (bufferInfo != null) {
            bufferInfo2 = new MediaCodec.BufferInfo();
        }
        if (bufferInfo2 != null) {
            bufferInfo2.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
        }
        TXIVideoEncoderListener tXIVideoEncoderListener = this.mListener;
        if (tXIVideoEncoderListener != null) {
            TXSNALPacket tXSNALPacket = new TXSNALPacket();
            tXSNALPacket.nalData = bArr;
            tXSNALPacket.nalType = i;
            tXSNALPacket.gopIndex = j;
            tXSNALPacket.gopFrameIndex = j2;
            tXSNALPacket.frameIndex = j3;
            tXSNALPacket.refFremeIndex = j4;
            tXSNALPacket.pts = j5;
            tXSNALPacket.dts = j6;
            tXSNALPacket.buffer = asReadOnlyBuffer;
            if (bufferInfo2 != null) {
                tXSNALPacket.info = bufferInfo2;
            }
            tXIVideoEncoderListener.onEncodeNAL(tXSNALPacket, i2);
            setStatusValue(4002, Long.valueOf(getRealBitrate()));
            setStatusValue(4001, Double.valueOf(getRealFPS()));
            if (i == 0) {
                long j7 = this.mVideoGOPEncode;
                if (j7 != 0) {
                    this.mEncodeFirstGOP = true;
                    setStatusValue(4003, Long.valueOf(j7));
                }
                this.mVideoGOPEncode = 1L;
                return;
            }
            this.mVideoGOPEncode++;
            if (this.mEncodeFirstGOP) {
                return;
            }
            setStatusValue(4003, Long.valueOf(this.mVideoGOPEncode));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void callDelegate(MediaFormat mediaFormat) {
        TXIVideoEncoderListener tXIVideoEncoderListener = this.mListener;
        if (tXIVideoEncoderListener != null) {
            tXIVideoEncoderListener.onEncodeFormat(mediaFormat);
        }
    }
}
