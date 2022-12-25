package com.tencent.liteav.p120e;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p122g.MediaExtractorWrapper;
import com.tencent.liteav.p122g.TXAudioDecoderWrapper;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(16)
/* renamed from: com.tencent.liteav.e.c */
/* loaded from: classes3.dex */
public class BasicVideoDecDemux {

    /* renamed from: a */
    protected MediaExtractorWrapper f3585a;

    /* renamed from: b */
    protected VideoMediaCodecDecoder f3586b;

    /* renamed from: c */
    protected TXAudioDecoderWrapper f3587c;

    /* renamed from: d */
    protected Surface f3588d;

    /* renamed from: e */
    protected IAudioEditDecoderListener f3589e;

    /* renamed from: f */
    protected IVideoEditDecoderListener f3590f;

    /* renamed from: g */
    protected AtomicLong f3591g;

    /* renamed from: h */
    protected AtomicLong f3592h;

    /* renamed from: i */
    protected int f3593i;

    /* renamed from: j */
    protected String f3594j;

    /* renamed from: k */
    private final String f3595k = "BasicVideoDecDemux";

    /* renamed from: l */
    private int f3596l;

    /* renamed from: a */
    public int m2171a(String str) throws IOException {
        this.f3594j = str;
        this.f3585a = new MediaExtractorWrapper();
        int m1744a = this.f3585a.m1744a(str);
        if (m1744a == -1002 || m1744a == 0) {
            this.f3596l = 0;
            return m1744a;
        }
        return m1744a;
    }

    /* renamed from: a */
    public void m2172a(IVideoEditDecoderListener iVideoEditDecoderListener) {
        this.f3590f = iVideoEditDecoderListener;
    }

    /* renamed from: a */
    public void m2173a(IAudioEditDecoderListener iAudioEditDecoderListener) {
        this.f3589e = iAudioEditDecoderListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void m2177a() {
        TXCLog.m2913i("BasicVideoDecDemux", "configureVideo");
        this.f3586b = new VideoMediaCodecDecoder();
        this.f3586b.mo468a(this.f3585a.m1728l());
        this.f3586b.mo467a(this.f3585a.m1728l(), this.f3588d);
        this.f3586b.mo469a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: b */
    public void m2170b() {
        TXCLog.m2913i("BasicVideoDecDemux", "configureAudio");
        this.f3587c = new TXAudioDecoderWrapper();
        MediaFormat m1727m = this.f3585a.m1727m();
        this.f3587c.mo468a(m1727m);
        this.f3587c.mo467a(m1727m, (Surface) null);
        this.f3587c.mo469a();
    }

    /* renamed from: a */
    public synchronized void m2175a(Surface surface) {
        this.f3588d = surface;
    }

    /* renamed from: c */
    public long m2169c() {
        return this.f3585a.m1747a();
    }

    /* renamed from: d */
    public int m2168d() {
        return this.f3585a.m1743b();
    }

    /* renamed from: e */
    public int m2167e() {
        return this.f3585a.m1740c();
    }

    /* renamed from: f */
    public MediaFormat m2166f() {
        return this.f3585a.m1727m();
    }

    /* renamed from: g */
    public MediaFormat m2165g() {
        return this.f3585a.m1728l();
    }

    /* renamed from: h */
    public boolean m2164h() {
        MediaExtractorWrapper mediaExtractorWrapper = this.f3585a;
        return (mediaExtractorWrapper == null || mediaExtractorWrapper.m1727m() == null) ? false : true;
    }

    /* renamed from: i */
    public boolean m2163i() {
        return this.f3585a.m1728l() != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public long m2174a(Frame frame) {
        TXCLog.m2914e("BasicVideoDecDemux", "seekFinalVideo, read is end frame, try to find final video frame(not end frame)");
        long m2162j = (1000 / m2162j()) * 1000;
        long m1730j = this.f3585a.m1730j();
        if (m1730j <= 0) {
            m1730j = this.f3592h.get();
        }
        for (int i = 1; i <= 3; i++) {
            long j = m1730j - (i * m2162j);
            if (j < 0) {
                j = m1730j;
            }
            this.f3585a.m1746a(j);
            this.f3585a.m1745a(frame);
            TXCLog.m2913i("BasicVideoDecDemux", "seekReversePTS, seek End PTS = " + j + ", flags = " + frame.m2327f() + ", seekEndCount = " + i);
            if (!frame.m2309p()) {
                return frame.m2329e();
            }
        }
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public boolean m2176a(long j, long j2, Frame frame) {
        if (j <= this.f3591g.get()) {
            TXCLog.m2913i("BasicVideoDecDemux", "seekReversePTS, lastReadPTS <= mStartTime");
            this.f3585a.m1746a(j);
            this.f3593i++;
            if (this.f3593i < 2) {
                return false;
            }
            this.f3586b.m2209b(frame);
            return true;
        }
        long j3 = 1000;
        long j4 = j - 1000;
        this.f3585a.m1746a(j4);
        long m1724p = this.f3585a.m1724p();
        if (m1724p < j) {
            TXCLog.m2913i("BasicVideoDecDemux", "seekReversePTS, seekPTS = " + j4 + ", find previous pts = " + m1724p);
            return false;
        }
        int i = 1;
        while (true) {
            long j5 = j - ((i * j2) + j3);
            if (j5 < 0) {
                j5 = 0;
            }
            this.f3585a.m1746a(j5);
            long m1724p2 = this.f3585a.m1724p();
            TXCLog.m2913i("BasicVideoDecDemux", "seekReversePTS, may be SEEK_TO_PREVIOUS_SYNC same to NEXT_SYNC, seekPTS = " + j5 + ", find previous pts = " + m1724p2 + ", count = " + i);
            if (m1724p2 < j) {
                return false;
            }
            i++;
            if (i > 10) {
                this.f3586b.m2209b(frame);
                return true;
            }
            j3 = 1000;
        }
    }

    /* renamed from: j */
    public int m2162j() {
        int i = this.f3596l;
        if (i != 0) {
            return i;
        }
        MediaFormat m1728l = this.f3585a.m1728l();
        if (m1728l != null) {
            try {
                this.f3596l = m1728l.getInteger("frame-rate");
            } catch (NullPointerException unused) {
                this.f3596l = 20;
            }
        }
        return this.f3596l;
    }

    /* renamed from: k */
    public void mo2017k() {
        MediaExtractorWrapper mediaExtractorWrapper = this.f3585a;
        if (mediaExtractorWrapper != null) {
            mediaExtractorWrapper.m1725o();
        }
    }
}
