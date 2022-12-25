package com.tencent.liteav.p104b;

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
/* renamed from: com.tencent.liteav.b.g */
/* loaded from: classes3.dex */
public class TXCombineVideoDecoder implements IMediaDecoder {

    /* renamed from: a */
    private MediaCodec f2267a;

    /* renamed from: b */
    private ByteBuffer[] f2268b;

    /* renamed from: c */
    private ByteBuffer[] f2269c;

    /* renamed from: e */
    private long f2271e = 1000;

    /* renamed from: d */
    private AtomicBoolean f2270d = new AtomicBoolean(false);

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo468a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.m2914e("TXCombineVideoDecoder", "create VideoDecoder error format:" + mediaFormat);
            return;
        }
        try {
            this.f2267a = MediaCodec.createDecoderByType(mediaFormat.getString("mime"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo467a(MediaFormat mediaFormat, Surface surface) {
        if (mediaFormat == null) {
            TXCLog.m2914e("TXCombineVideoDecoder", "configure VideoDecoder error");
            return;
        }
        TXCLog.m2915d("TXCombineVideoDecoder", "format: " + mediaFormat + ", surface: " + surface + ", mMediaCodec: " + this.f2267a);
        mediaFormat.setInteger("rotation-degrees", 0);
        this.f2267a.configure(mediaFormat, surface, (MediaCrypto) null, 0);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo469a() {
        TXCLog.m2915d("TXCombineVideoDecoder", C2516Ad.TYPE_START);
        MediaCodec mediaCodec = this.f2267a;
        if (mediaCodec == null) {
            TXCLog.m2914e("TXCombineVideoDecoder", "start VideoDecoder error");
            return;
        }
        mediaCodec.start();
        this.f2268b = this.f2267a.getInputBuffers();
        this.f2269c = this.f2267a.getOutputBuffers();
        this.f2270d.getAndSet(true);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: b */
    public void mo463b() {
        TXCLog.m2915d("TXCombineVideoDecoder", "stop");
        MediaCodec mediaCodec = this.f2267a;
        if (mediaCodec == null) {
            TXCLog.m2914e("TXCombineVideoDecoder", "stop VideoDecoder error");
            return;
        }
        try {
            try {
                mediaCodec.stop();
                this.f2267a.release();
            } catch (IllegalStateException e) {
                TXCLog.m2914e("TXCombineVideoDecoder", "video decoder stop exception: " + e);
            }
        } finally {
            this.f2270d.getAndSet(false);
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: c */
    public Frame mo461c() {
        int i;
        ByteBuffer byteBuffer;
        if (!this.f2270d.get()) {
            return null;
        }
        try {
            i = this.f2267a.dequeueInputBuffer(this.f2271e);
        } catch (Exception e) {
            TXCLog.m2914e("TXCombineVideoDecoder", "video dequeueInputBuffer exception: " + e);
            i = -1;
        }
        if (i < 0) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            byteBuffer = this.f2267a.getInputBuffer(i);
        } else {
            byteBuffer = this.f2268b[i];
        }
        return new Frame(byteBuffer, 0, 0L, i, 0, 0);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo466a(Frame frame) {
        if (!this.f2270d.get()) {
            return;
        }
        this.f2267a.queueInputBuffer(frame.m2332d(), 0, frame.m2325g(), frame.m2329e(), frame.m2327f());
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: d */
    public Frame mo460d() {
        MediaCodec.BufferInfo bufferInfo;
        int dequeueOutputBuffer;
        if (this.f2270d.get() && (dequeueOutputBuffer = this.f2267a.dequeueOutputBuffer((bufferInfo = new MediaCodec.BufferInfo()), this.f2271e)) != -1) {
            if (dequeueOutputBuffer == -3) {
                TXCLog.m2915d("TXCombineVideoDecoder", "INFO_OUTPUT_BUFFERS_CHANGED info.size :" + bufferInfo.size);
                return null;
            } else if (dequeueOutputBuffer == -2) {
                TXCLog.m2915d("TXCombineVideoDecoder", "INFO_OUTPUT_FORMAT_CHANGED info.size :" + bufferInfo.size);
                return null;
            } else if (dequeueOutputBuffer < 0 || dequeueOutputBuffer < 0) {
                return null;
            } else {
                this.f2267a.releaseOutputBuffer(dequeueOutputBuffer, true);
                return new Frame(null, bufferInfo.size, bufferInfo.presentationTimeUs, dequeueOutputBuffer, bufferInfo.flags, 0);
            }
        }
        return null;
    }

    /* renamed from: a */
    public Frame m3230a(Frame frame, Frame frame2) {
        if (!this.f2270d.get()) {
            return null;
        }
        frame2.m2316k(frame.m2311n());
        frame2.m2318j(frame.m2313m());
        frame2.m2328e(frame.m2323h());
        frame2.m2326f(frame.m2321i());
        frame2.m2320i(frame.m2315l());
        frame2.m2322h(frame.m2317k());
        frame2.m2324g(frame.m2319j());
        return frame2;
    }
}
