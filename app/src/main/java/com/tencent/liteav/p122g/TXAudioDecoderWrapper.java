package com.tencent.liteav.p122g;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.videoediter.ffmpeg.TXSWAudioDecoder;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(16)
/* renamed from: com.tencent.liteav.g.f */
/* loaded from: classes3.dex */
public class TXAudioDecoderWrapper implements IMediaDecoder {

    /* renamed from: c */
    private static final String[] f4078c = {"Xiaomi - MI 3"};

    /* renamed from: a */
    private AtomicBoolean f4079a = new AtomicBoolean(false);

    /* renamed from: b */
    private IMediaDecoder f4080b;

    /* renamed from: d */
    private boolean f4081d;

    /* renamed from: b */
    private boolean m1719b(MediaFormat mediaFormat) {
        String string = mediaFormat.getString("mime");
        TXCLog.m2913i("TXAudioDecoderWrapper", " mime type = " + string);
        if (string != null && TXSWAudioDecoder.m465a(string)) {
            TXCLog.m2913i("TXAudioDecoderWrapper", "isUseSw: support mime type! use sw decoder!");
            return true;
        }
        TXCLog.m2913i("TXAudioDecoderWrapper", "isUseSw: use hw decoder!");
        return false;
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo468a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            this.f4079a.set(false);
            return;
        }
        this.f4079a.set(true);
        TXCLog.m2913i("TXAudioDecoderWrapper", "createDecoderByFormat: " + mediaFormat.toString());
        this.f4081d = m1719b(mediaFormat);
        if (this.f4081d) {
            this.f4080b = new TXSWAudioDecoder();
        } else {
            this.f4080b = new TXHWAudioDecoder();
        }
        this.f4080b.mo468a(mediaFormat);
    }

    /* renamed from: e */
    public boolean m1717e() {
        return this.f4081d;
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo467a(MediaFormat mediaFormat, Surface surface) {
        if (mediaFormat == null) {
            this.f4079a.set(false);
            return;
        }
        this.f4079a.set(true);
        this.f4080b.mo467a(mediaFormat, surface);
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo469a() {
        if (this.f4079a.get()) {
            this.f4080b.mo469a();
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: b */
    public void mo463b() {
        if (this.f4079a.get()) {
            this.f4080b.mo463b();
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: c */
    public Frame mo461c() {
        if (this.f4079a.get()) {
            return this.f4080b.mo461c();
        }
        return null;
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: a */
    public void mo466a(Frame frame) {
        if (this.f4079a.get()) {
            this.f4080b.mo466a(frame);
        }
    }

    @Override // com.tencent.liteav.p122g.IMediaDecoder
    /* renamed from: d */
    public Frame mo460d() {
        if (this.f4079a.get()) {
            return this.f4080b.mo460d();
        }
        return null;
    }

    /* renamed from: a */
    public Frame m1720a(Frame frame, Frame frame2) {
        if (this.f4079a.get()) {
            frame2.m2316k(frame.m2311n());
            frame2.m2318j(frame.m2313m());
            frame2.m2326f(frame.m2321i());
            frame2.m2328e(frame.m2323h());
            frame2.m2320i(frame.m2315l());
            frame2.m2322h(frame.m2317k());
            frame2.m2324g(frame.m2319j());
            return frame2;
        }
        return null;
    }

    /* renamed from: b */
    public Frame m1718b(Frame frame) {
        if (this.f4079a.get()) {
            frame.m2334c(4);
            TXCLog.m2915d("TXAudioDecoderWrapper", "------appendEndFrame----------");
            return frame;
        }
        return null;
    }
}
