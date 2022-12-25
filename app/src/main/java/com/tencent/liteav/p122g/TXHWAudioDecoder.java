package com.tencent.liteav.p122g;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(19)
/* renamed from: com.tencent.liteav.g.g */
/* loaded from: classes3.dex */
public class TXHWAudioDecoder implements IMediaDecoder {

    /* renamed from: a */
    private MediaCodec f4082a;

    /* renamed from: b */
    private ByteBuffer[] f4083b;

    /* renamed from: c */
    private ByteBuffer[] f4084c;

    /* renamed from: e */
    private long f4086e = 1000;

    /* renamed from: d */
    private AtomicBoolean f4085d = new AtomicBoolean(false);

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo468a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.m2914e("TXHWAudioDecoder", "create AudioDecoder error format:" + mediaFormat);
            return;
        }
        try {
            this.f4082a = MediaCodec.createDecoderByType(mediaFormat.getString("mime"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo467a(MediaFormat mediaFormat, Surface surface) {
        if (mediaFormat == null) {
            TXCLog.m2914e("TXHWAudioDecoder", "configure AudioDecoder error");
        } else {
            this.f4082a.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo469a() {
        MediaCodec mediaCodec = this.f4082a;
        if (mediaCodec == null) {
            TXCLog.m2914e("TXHWAudioDecoder", "start AudioDecoder error");
            return;
        }
        mediaCodec.start();
        this.f4084c = this.f4082a.getInputBuffers();
        this.f4083b = this.f4082a.getOutputBuffers();
        this.f4085d.getAndSet(true);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: b */
    public void mo463b() {
        MediaCodec mediaCodec = this.f4082a;
        if (mediaCodec == null) {
            TXCLog.m2914e("TXHWAudioDecoder", "stop AudioDecoder error");
            return;
        }
        try {
            try {
                mediaCodec.stop();
                this.f4082a.release();
            } catch (IllegalStateException e) {
                TXCLog.m2914e("TXHWAudioDecoder", "audio decoder stop exception: " + e);
            }
        } finally {
            this.f4085d.getAndSet(false);
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: c */
    public Frame mo461c() {
        int i;
        ByteBuffer byteBuffer;
        if (!this.f4085d.get()) {
            return null;
        }
        try {
            i = this.f4082a.dequeueInputBuffer(this.f4086e);
        } catch (Exception e) {
            TXCLog.m2914e("TXHWAudioDecoder", "audio dequeueInputBuffer exception: " + e);
            i = -1;
        }
        if (i < 0) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            byteBuffer = this.f4082a.getInputBuffer(i);
        } else {
            byteBuffer = this.f4084c[i];
        }
        return new Frame(byteBuffer, 0, 0L, i, 0, 0);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo466a(Frame frame) {
        if (!this.f4085d.get()) {
            return;
        }
        this.f4082a.queueInputBuffer(frame.m2332d(), 0, frame.m2325g(), frame.m2329e(), frame.m2327f());
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: d */
    public Frame mo460d() {
        Frame frame = null;
        if (!this.f4085d.get()) {
            return null;
        }
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int dequeueOutputBuffer = this.f4082a.dequeueOutputBuffer(bufferInfo, this.f4086e);
        if (dequeueOutputBuffer != -1 && dequeueOutputBuffer != -3 && dequeueOutputBuffer != -2 && dequeueOutputBuffer >= 0 && dequeueOutputBuffer >= 0) {
            frame = new Frame();
            frame.m2344a(1);
            frame.m2343a(bufferInfo.presentationTimeUs);
            frame.m2334c(bufferInfo.flags);
            frame.m2331d(bufferInfo.size);
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(bufferInfo.size);
            if (Build.VERSION.SDK_INT >= 21) {
                allocateDirect.put(this.f4082a.getOutputBuffer(dequeueOutputBuffer));
            } else {
                ByteBuffer byteBuffer = this.f4082a.getOutputBuffers()[dequeueOutputBuffer];
                byteBuffer.rewind();
                byteBuffer.limit(bufferInfo.size);
                allocateDirect.put(byteBuffer);
            }
            allocateDirect.position(0);
            if (frame.m2325g() >= 0) {
                allocateDirect.limit(frame.m2325g());
            }
            frame.m2341a(allocateDirect);
            this.f4082a.releaseOutputBuffer(dequeueOutputBuffer, false);
        }
        return frame;
    }
}
