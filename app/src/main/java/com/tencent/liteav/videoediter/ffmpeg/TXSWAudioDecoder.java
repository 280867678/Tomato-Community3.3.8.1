package com.tencent.liteav.videoediter.ffmpeg;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p122g.IMediaDecoder;
import com.tencent.liteav.videoediter.ffmpeg.jni.FFDecodedFrame;
import com.tencent.liteav.videoediter.ffmpeg.jni.TXFFAudioDecoderJNI;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(16)
/* renamed from: com.tencent.liteav.videoediter.ffmpeg.c */
/* loaded from: classes3.dex */
public class TXSWAudioDecoder implements IMediaDecoder {

    /* renamed from: a */
    public static String[] f5542a = {"audio/mp4a-latm", "audio/mpeg"};

    /* renamed from: b */
    private ByteBuffer f5543b;

    /* renamed from: c */
    private int f5544c;

    /* renamed from: d */
    private int f5545d;

    /* renamed from: e */
    private int f5546e;

    /* renamed from: f */
    private TXFFAudioDecoderJNI f5547f;

    /* renamed from: g */
    private List<Frame> f5548g;

    /* renamed from: h */
    private AtomicBoolean f5549h = new AtomicBoolean(false);

    /* renamed from: i */
    private FFDecodedFrame f5550i;

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo467a(MediaFormat mediaFormat, Surface surface) {
    }

    /* renamed from: a */
    public static boolean m465a(String str) {
        for (String str2 : f5542a) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private int m462b(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != -53558318) {
            if (hashCode == 1504831518 && str.equals("audio/mpeg")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("audio/mp4a-latm")) {
                c = 0;
            }
            c = 65535;
        }
        if (c != 0) {
            return c != 1 ? -1 : 1;
        }
        return 0;
    }

    public TXSWAudioDecoder() {
        this.f5548g = new LinkedList();
        this.f5548g = Collections.synchronizedList(this.f5548g);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo468a(MediaFormat mediaFormat) {
        mo463b();
        this.f5544c = mediaFormat.getInteger("channel-count");
        this.f5545d = mediaFormat.getInteger("sample-rate");
        if (mediaFormat.containsKey("max-input-size")) {
            this.f5546e = mediaFormat.getInteger("max-input-size");
        }
        ByteBuffer byteBuffer = mediaFormat.getByteBuffer("csd-0");
        if (byteBuffer != null) {
            byteBuffer.position(0);
        }
        String string = mediaFormat.getString("mime");
        this.f5547f = new TXFFAudioDecoderJNI();
        this.f5547f.configureInput(m462b(string), byteBuffer, byteBuffer != null ? byteBuffer.capacity() : 0, this.f5545d, this.f5544c);
        int i = this.f5544c * 1024 * 2;
        int i2 = this.f5546e;
        if (i > i2) {
            i2 = i;
        }
        this.f5543b = ByteBuffer.allocateDirect(i2);
        TXCLog.m2913i("TXSWAudioDecoder", "createDecoderByFormat: type = " + string + ", mediaFormat = " + mediaFormat.toString() + ", calculateBufferSize = " + i + ", mMaxInputSize = " + this.f5546e);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo469a() {
        if (this.f5549h.get()) {
            TXCLog.m2911w("TXSWAudioDecoder", "start error: decoder have been started!");
            return;
        }
        this.f5548g.clear();
        this.f5549h.set(true);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: b */
    public void mo463b() {
        if (!this.f5549h.get()) {
            TXCLog.m2911w("TXSWAudioDecoder", "stop error: decoder isn't starting yet!!");
            return;
        }
        this.f5548g.clear();
        TXFFAudioDecoderJNI tXFFAudioDecoderJNI = this.f5547f;
        if (tXFFAudioDecoderJNI != null) {
            tXFFAudioDecoderJNI.release();
            this.f5547f = null;
        }
        this.f5549h.set(false);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: c */
    public Frame mo461c() {
        if (!this.f5549h.get()) {
            TXCLog.m2911w("TXSWAudioDecoder", "find frame error: decoder isn't starting yet!!");
            return null;
        }
        this.f5543b.position(0);
        Frame frame = new Frame();
        frame.m2341a(this.f5543b);
        frame.m2322h(this.f5544c);
        frame.m2324g(this.f5545d);
        frame.m2331d(this.f5543b.capacity());
        return frame;
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo466a(Frame frame) {
        if (!this.f5549h.get()) {
            TXCLog.m2914e("TXSWAudioDecoder", "decode error: decoder isn't starting yet!!");
            return;
        }
        if (frame.m2327f() == 1) {
            byte[] m464a = m464a(frame.m2338b(), frame.m2325g());
            if (m464a == null) {
                this.f5550i = null;
                return;
            }
            this.f5550i = this.f5547f.decode(m464a, frame.m2329e(), frame.m2327f());
        } else if (frame.m2327f() == 4) {
            this.f5550i = new FFDecodedFrame();
            FFDecodedFrame fFDecodedFrame = this.f5550i;
            fFDecodedFrame.data = new byte[0];
            fFDecodedFrame.flags = 4;
            fFDecodedFrame.pts = frame.m2329e();
        }
        frame.m2341a((ByteBuffer) null);
        frame.m2331d(0);
        this.f5543b.position(0);
    }

    /* renamed from: a */
    private byte[] m464a(ByteBuffer byteBuffer, int i) {
        byte[] bArr = new byte[i];
        try {
            byteBuffer.get(bArr);
            return bArr;
        } catch (BufferUnderflowException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: d */
    public Frame mo460d() {
        byte[] bArr;
        if (!this.f5549h.get()) {
            TXCLog.m2914e("TXSWAudioDecoder", "decode error: decoder isn't starting yet!!");
            return null;
        }
        FFDecodedFrame fFDecodedFrame = this.f5550i;
        if (fFDecodedFrame == null || (bArr = fFDecodedFrame.data) == null) {
            return null;
        }
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(bArr.length);
        allocateDirect.put(this.f5550i.data);
        allocateDirect.position(0);
        Frame frame = new Frame();
        frame.m2341a(allocateDirect);
        frame.m2331d(this.f5550i.data.length);
        frame.m2343a(this.f5550i.pts);
        frame.m2334c(this.f5550i.flags);
        frame.m2322h(this.f5544c);
        frame.m2324g(this.f5550i.sampleRate);
        this.f5550i = null;
        return frame;
    }
}
