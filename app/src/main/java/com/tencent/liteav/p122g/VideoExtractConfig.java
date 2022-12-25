package com.tencent.liteav.p122g;

import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p120e.VideoMediaCodecDecoder;
import java.io.IOException;

/* renamed from: com.tencent.liteav.g.i */
/* loaded from: classes3.dex */
public class VideoExtractConfig {

    /* renamed from: a */
    public String f4089a;

    /* renamed from: b */
    public VideoGLTextureInfo f4090b;

    /* renamed from: c */
    private MediaExtractorWrapper f4091c;

    /* renamed from: d */
    private VideoMediaCodecDecoder f4092d;

    /* renamed from: e */
    private TXAudioDecoderWrapper f4093e;

    /* renamed from: f */
    private boolean f4094f;

    /* renamed from: g */
    private boolean f4095g;

    /* renamed from: h */
    private boolean f4096h;

    /* renamed from: i */
    private boolean f4097i;

    /* renamed from: j */
    private MediaFormat f4098j;

    /* renamed from: k */
    private MediaFormat f4099k;

    /* renamed from: a */
    public void m1701a(String str) {
        this.f4089a = str;
    }

    /* renamed from: a */
    public void m1703a() {
        MediaExtractorWrapper mediaExtractorWrapper = this.f4091c;
        if (mediaExtractorWrapper != null) {
            mediaExtractorWrapper.m1725o();
        }
    }

    /* renamed from: b */
    public int m1700b() {
        TXCLog.m2913i("VideoExtractConfig", "createMediaExtractor videoSourcePath:" + this.f4089a);
        this.f4091c = new MediaExtractorWrapper();
        try {
            return this.f4091c.m1744a(this.f4089a);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* renamed from: c */
    public void m1698c() {
        TXCLog.m2913i("VideoExtractConfig", "resetVideoMediaExtractor videoSourcePath:" + this.f4089a);
        this.f4091c.m1746a(0L);
    }

    /* renamed from: d */
    public void m1697d() {
        TXCLog.m2913i("VideoExtractConfig", "resetAudioMediaExtractor videoSourcePath:" + this.f4089a);
        this.f4091c.m1739c(0L);
    }

    /* renamed from: e */
    public MediaFormat m1696e() {
        MediaFormat mediaFormat = this.f4098j;
        return mediaFormat == null ? this.f4091c.m1728l() : mediaFormat;
    }

    /* renamed from: f */
    public MediaFormat m1695f() {
        MediaFormat mediaFormat = this.f4099k;
        return mediaFormat == null ? this.f4091c.m1727m() : mediaFormat;
    }

    /* renamed from: g */
    public int m1694g() {
        return this.f4091c.m1733g();
    }

    /* renamed from: h */
    public long m1693h() {
        MediaFormat m1696e;
        if (Build.VERSION.SDK_INT < 16 || (m1696e = m1696e()) == null) {
            return 0L;
        }
        return m1696e.getLong("durationUs");
    }

    /* renamed from: i */
    public long m1692i() {
        MediaFormat m1695f;
        if (Build.VERSION.SDK_INT < 16 || (m1695f = m1695f()) == null) {
            return 0L;
        }
        return m1695f.getLong("durationUs");
    }

    /* renamed from: j */
    public long m1691j() {
        if (m1695f() == null) {
            TXCLog.m2913i("VideoExtractConfig", "getAudioFormat is null");
            return m1693h();
        } else if (m1696e() != null) {
            long m1693h = m1693h();
            long m1692i = m1692i();
            TXCLog.m2913i("VideoExtractConfig", "getDuration vd:" + m1693h + ",ad:" + m1692i);
            return m1693h > m1692i ? m1693h : m1692i;
        } else {
            TXCLog.m2913i("VideoExtractConfig", "getVideoFormat is null");
            return 0L;
        }
    }

    /* renamed from: k */
    public void m1690k() {
        TXCLog.m2913i("VideoExtractConfig", "createVideoDecoder videoSourcePath1111:" + this.f4089a);
        if (this.f4090b.f4105c == null) {
            TXCLog.m2914e("VideoExtractConfig", "createVideoDecoder videoGLTextureInfo.surface is null");
            return;
        }
        this.f4092d = new VideoMediaCodecDecoder();
        this.f4098j = this.f4091c.m1728l();
        this.f4092d.mo468a(this.f4098j);
        this.f4092d.mo467a(this.f4091c.m1728l(), this.f4090b.f4105c);
        this.f4092d.mo469a();
        this.f4094f = false;
        this.f4096h = false;
    }

    /* renamed from: l */
    public void m1689l() {
        TXCLog.m2913i("VideoExtractConfig", "destroyVideoDecoder videoSourcePath:" + this.f4089a);
        VideoMediaCodecDecoder videoMediaCodecDecoder = this.f4092d;
        if (videoMediaCodecDecoder != null) {
            videoMediaCodecDecoder.mo463b();
            this.f4092d = null;
        }
    }

    /* renamed from: m */
    public void m1688m() {
        TXCLog.m2913i("VideoExtractConfig", "createAudioDecoder videoSourcePath:" + this.f4089a);
        this.f4093e = new TXAudioDecoderWrapper();
        this.f4099k = this.f4091c.m1727m();
        this.f4093e.mo468a(this.f4099k);
        this.f4093e.mo467a(this.f4099k, (Surface) null);
        this.f4093e.mo469a();
        if (this.f4099k == null) {
            this.f4095g = true;
            this.f4097i = true;
            return;
        }
        this.f4095g = false;
        this.f4097i = false;
    }

    /* renamed from: n */
    public void m1687n() {
        TXCLog.m2913i("VideoExtractConfig", "destroyAudioDecoder videoSourcePath:" + this.f4089a);
        TXAudioDecoderWrapper tXAudioDecoderWrapper = this.f4093e;
        if (tXAudioDecoderWrapper != null) {
            tXAudioDecoderWrapper.mo463b();
            this.f4093e = null;
        }
    }

    /* renamed from: o */
    public boolean m1686o() {
        return this.f4096h;
    }

    /* renamed from: p */
    public boolean m1685p() {
        return this.f4097i;
    }

    /* renamed from: q */
    public void m1684q() {
        if (this.f4094f) {
            TXCLog.m2913i("VideoExtractConfig", "readVideoFrame source:" + this.f4089a + " readEOF!");
            return;
        }
        Frame mo461c = this.f4092d.mo461c();
        if (mo461c == null) {
            return;
        }
        Frame m1745a = this.f4091c.m1745a(mo461c);
        if (this.f4091c.m1738c(m1745a)) {
            this.f4094f = true;
            TXCLog.m2913i("VideoExtractConfig", "readVideoFrame source:" + this.f4089a + " readEOF!");
        }
        this.f4092d.mo466a(m1745a);
    }

    /* renamed from: r */
    public void m1683r() {
        if (this.f4095g) {
            TXCLog.m2913i("VideoExtractConfig", "readAudioFrame source:" + this.f4089a + " readEOF!");
            return;
        }
        Frame mo461c = this.f4093e.mo461c();
        if (mo461c == null) {
            return;
        }
        Frame m1741b = this.f4091c.m1741b(mo461c);
        if (this.f4091c.m1736d(m1741b)) {
            this.f4095g = true;
            TXCLog.m2913i("VideoExtractConfig", "readAudioFrame source:" + this.f4089a + " readEOF!");
        }
        this.f4093e.mo466a(m1741b);
    }

    /* renamed from: s */
    public Frame m1682s() {
        Frame mo460d = this.f4092d.mo460d();
        if (mo460d == null || mo460d.m2310o() == null) {
            return null;
        }
        m1702a(mo460d);
        if (mo460d.m2309p()) {
            TXCLog.m2913i("VideoExtractConfig", "getDecodeVideoFrame frame.isEndFrame");
            this.f4096h = true;
        }
        return mo460d;
    }

    /* renamed from: t */
    public Frame m1681t() {
        Frame mo460d = this.f4093e.mo460d();
        if (mo460d == null || mo460d.m2310o() == null) {
            return null;
        }
        m1699b(mo460d);
        if (mo460d.m2309p()) {
            TXCLog.m2913i("VideoExtractConfig", "getDecodeAudioFrame frame.isEndFrame");
            this.f4097i = true;
        }
        return mo460d;
    }

    /* renamed from: a */
    private void m1702a(Frame frame) {
        frame.m2318j(this.f4091c.m1743b());
        frame.m2316k(this.f4091c.m1740c());
        frame.m2328e(this.f4091c.m1734f());
        frame.m2326f(this.f4091c.m1735e());
    }

    /* renamed from: b */
    private void m1699b(Frame frame) {
        frame.m2324g(this.f4091c.m1732h());
        frame.m2322h(this.f4091c.m1731i());
    }
}
