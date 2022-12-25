package com.tencent.liteav.p120e;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p122g.IMediaDecoder;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(16)
/* renamed from: com.tencent.liteav.e.ac */
/* loaded from: classes3.dex */
public class VideoMediaCodecDecoder implements IMediaDecoder {

    /* renamed from: a */
    private MediaCodec f3556a;

    /* renamed from: b */
    private ByteBuffer[] f3557b;

    /* renamed from: c */
    private ByteBuffer[] f3558c;

    /* renamed from: e */
    private long f3560e = 1000;

    /* renamed from: d */
    private AtomicBoolean f3559d = new AtomicBoolean(false);

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo468a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.m2914e("VideoMediaCodecDecoder", "create VideoDecoder error format:" + mediaFormat);
            return;
        }
        try {
            this.f3556a = MediaCodec.createDecoderByType(mediaFormat.getString("mime"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo467a(MediaFormat mediaFormat, Surface surface) {
        if (mediaFormat == null) {
            TXCLog.m2914e("VideoMediaCodecDecoder", "configure VideoDecoder error");
            return;
        }
        TXCLog.m2915d("VideoMediaCodecDecoder", "format: " + mediaFormat + ", surface: " + surface + ", mMediaCodec: " + this.f3556a);
        mediaFormat.setInteger("rotation-degrees", 0);
        try {
            this.f3556a.configure(mediaFormat, surface, (MediaCrypto) null, 0);
        } catch (Exception unused) {
            TXCLog.m2911w("VideoMediaCodecDecoder", "mMediaCodec configure error ");
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo469a() {
        TXCLog.m2915d("VideoMediaCodecDecoder", C2516Ad.TYPE_START);
        MediaCodec mediaCodec = this.f3556a;
        if (mediaCodec == null) {
            TXCLog.m2914e("VideoMediaCodecDecoder", "start VideoDecoder error");
            return;
        }
        mediaCodec.start();
        this.f3557b = this.f3556a.getInputBuffers();
        this.f3558c = this.f3556a.getOutputBuffers();
        this.f3559d.getAndSet(true);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: b */
    public void mo463b() {
        TXCLog.m2915d("VideoMediaCodecDecoder", "stop");
        MediaCodec mediaCodec = this.f3556a;
        if (mediaCodec == null) {
            TXCLog.m2914e("VideoMediaCodecDecoder", "stop VideoDecoder error");
            return;
        }
        try {
            try {
                mediaCodec.stop();
                this.f3556a.release();
            } catch (IllegalStateException e) {
                TXCLog.m2914e("VideoMediaCodecDecoder", "video decoder stop exception: " + e);
            }
        } finally {
            this.f3559d.getAndSet(false);
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: c */
    public Frame mo461c() {
        int i;
        ByteBuffer byteBuffer;
        if (!this.f3559d.get()) {
            return null;
        }
        try {
            i = this.f3556a.dequeueInputBuffer(this.f3560e);
        } catch (Exception e) {
            TXCLog.m2914e("VideoMediaCodecDecoder", "video dequeueInputBuffer exception: " + e);
            i = -1;
        }
        if (i < 0) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            byteBuffer = this.f3556a.getInputBuffer(i);
        } else {
            byteBuffer = this.f3557b[i];
        }
        return new Frame(byteBuffer, 0, 0L, i, 0, 0);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo466a(Frame frame) {
        if (!this.f3559d.get()) {
            return;
        }
        this.f3556a.queueInputBuffer(frame.m2332d(), 0, frame.m2325g(), frame.m2329e(), frame.m2327f());
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: d */
    public Frame mo460d() {
        MediaCodec.BufferInfo bufferInfo;
        int dequeueOutputBuffer;
        if (this.f3559d.get() && (dequeueOutputBuffer = this.f3556a.dequeueOutputBuffer((bufferInfo = new MediaCodec.BufferInfo()), this.f3560e)) != -1 && dequeueOutputBuffer != -3 && dequeueOutputBuffer != -2 && dequeueOutputBuffer >= 0 && dequeueOutputBuffer >= 0) {
            Frame frame = new Frame(null, bufferInfo.size, bufferInfo.presentationTimeUs, dequeueOutputBuffer, bufferInfo.flags, 0);
            this.f3556a.releaseOutputBuffer(dequeueOutputBuffer, true);
            return frame;
        }
        return null;
    }

    /* renamed from: a */
    public Frame m2210a(Frame frame, Frame frame2) {
        if (!this.f3559d.get()) {
            return null;
        }
        frame2.m2316k(frame.m2311n());
        frame2.m2318j(frame.m2313m());
        frame2.m2328e(frame.m2323h());
        frame2.m2326f(frame.m2321i());
        frame2.m2320i(frame.m2315l());
        frame2.m2322h(frame.m2317k());
        frame2.m2324g(frame.m2319j());
        frame2.m2336b(frame.m2329e());
        return frame2;
    }

    /* renamed from: b */
    public Frame m2209b(Frame frame) {
        if (!this.f3559d.get()) {
            return null;
        }
        frame.m2331d(0);
        frame.m2343a(0L);
        frame.m2334c(4);
        TXCLog.m2915d("VideoMediaCodecDecoder", "------appendEndFrame----------");
        return frame;
    }
}
