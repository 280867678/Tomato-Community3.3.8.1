package com.tencent.liteav.p122g;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.tencent.liteav.g.l */
/* loaded from: classes3.dex */
public class VideoJoinDecAndDemuxGenerate {

    /* renamed from: b */
    private IVideoJoinDecoderListener f4110b;

    /* renamed from: c */
    private IAudioJoinDecoderListener f4111c;

    /* renamed from: d */
    private VideoExtractListConfig f4112d;

    /* renamed from: j */
    private VideoExtractConfig f4118j;

    /* renamed from: k */
    private VideoExtractConfig f4119k;

    /* renamed from: l */
    private Frame f4120l;

    /* renamed from: m */
    private long f4121m;

    /* renamed from: n */
    private long f4122n;

    /* renamed from: p */
    private Frame f4124p;

    /* renamed from: q */
    private long f4125q;

    /* renamed from: r */
    private long f4126r;

    /* renamed from: a */
    private final String f4109a = "VideoJoinDecAndDemuxGenerate";

    /* renamed from: o */
    private boolean f4123o = true;

    /* renamed from: e */
    private AtomicInteger f4113e = new AtomicInteger(1);

    /* renamed from: f */
    private HandlerThread f4114f = new HandlerThread("video_handler_thread");

    /* renamed from: h */
    private HandlerC3477b f4116h = new HandlerC3477b(this.f4114f.getLooper());

    /* renamed from: g */
    private HandlerThread f4115g = new HandlerThread("audio_handler_thread");

    /* renamed from: i */
    private HandlerC3476a f4117i = new HandlerC3476a(this.f4115g.getLooper());

    public VideoJoinDecAndDemuxGenerate() {
        this.f4114f.start();
        this.f4115g.start();
    }

    /* renamed from: a */
    public void m1666a(VideoExtractListConfig videoExtractListConfig) {
        this.f4112d = videoExtractListConfig;
    }

    /* renamed from: a */
    public void m1667a(IVideoJoinDecoderListener iVideoJoinDecoderListener) {
        this.f4110b = iVideoJoinDecoderListener;
    }

    /* renamed from: a */
    public void m1668a(IAudioJoinDecoderListener iAudioJoinDecoderListener) {
        this.f4111c = iAudioJoinDecoderListener;
    }

    /* renamed from: a */
    public synchronized void m1671a() {
        TXCLog.m2914e("VideoJoinDecAndDemuxGenerate", C2516Ad.TYPE_START);
        if (this.f4113e.get() == 2) {
            TXCLog.m2914e("VideoJoinDecAndDemuxGenerate", "start ignore, mCurrentState = " + this.f4113e.get());
            return;
        }
        this.f4125q = 0L;
        this.f4126r = 0L;
        this.f4121m = 0L;
        this.f4122n = 0L;
        this.f4124p = null;
        this.f4120l = null;
        this.f4112d.m1672h();
        this.f4113e.set(2);
        this.f4116h.sendEmptyMessage(101);
        this.f4117i.sendEmptyMessage(201);
    }

    /* renamed from: b */
    public synchronized void m1663b() {
        TXCLog.m2914e("VideoJoinDecAndDemuxGenerate", "stop");
        if (this.f4113e.get() == 1) {
            TXCLog.m2914e("VideoJoinDecAndDemuxGenerate", "stop(), mCurrentState in stop, ignore");
            return;
        }
        this.f4113e.set(1);
        this.f4116h.sendEmptyMessage(103);
        this.f4117i.sendEmptyMessage(203);
    }

    /* renamed from: c */
    public synchronized void m1660c() {
        if (this.f4113e.get() == 1) {
            TXCLog.m2914e("VideoJoinDecAndDemuxGenerate", "getNextVideoFrame, current state is init, ignore");
        } else {
            this.f4116h.sendEmptyMessage(102);
        }
    }

    /* renamed from: a */
    public void m1664a(boolean z) {
        this.f4123o = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: VideoJoinDecAndDemuxGenerate.java */
    /* renamed from: com.tencent.liteav.g.l$a */
    /* loaded from: classes3.dex */
    public class HandlerC3476a extends Handler {
        public HandlerC3476a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 201:
                    VideoJoinDecAndDemuxGenerate.this.m1658d();
                    VideoJoinDecAndDemuxGenerate.this.f4117i.sendEmptyMessage(202);
                    return;
                case 202:
                    VideoJoinDecAndDemuxGenerate.this.m1654f();
                    return;
                case 203:
                    VideoJoinDecAndDemuxGenerate.this.f4117i.removeMessages(202);
                    VideoJoinDecAndDemuxGenerate.this.m1656e();
                    return;
                case 204:
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public void m1658d() {
        TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "startAudioDecoder");
        List<VideoExtractConfig> m1680a = this.f4112d.m1680a();
        for (int i = 0; i < m1680a.size(); i++) {
            VideoExtractConfig videoExtractConfig = m1680a.get(i);
            videoExtractConfig.m1697d();
            videoExtractConfig.m1688m();
        }
        this.f4119k = this.f4112d.m1677c();
        this.f4126r = this.f4119k.m1691j();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m1656e() {
        TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "stopAudioDecoder");
        List<VideoExtractConfig> m1680a = this.f4112d.m1680a();
        for (int i = 0; i < m1680a.size(); i++) {
            m1680a.get(i).m1687n();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: VideoJoinDecAndDemuxGenerate.java */
    /* renamed from: com.tencent.liteav.g.l$b */
    /* loaded from: classes3.dex */
    public class HandlerC3477b extends Handler {
        public HandlerC3477b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 101:
                    VideoJoinDecAndDemuxGenerate.this.m1646k();
                    VideoJoinDecAndDemuxGenerate.this.f4116h.sendEmptyMessage(102);
                    return;
                case 102:
                    VideoJoinDecAndDemuxGenerate.this.m1650h();
                    return;
                case 103:
                    VideoJoinDecAndDemuxGenerate.this.f4116h.removeMessages(102);
                    VideoJoinDecAndDemuxGenerate.this.m1645l();
                    return;
                case 104:
                    VideoJoinDecAndDemuxGenerate.this.f4116h.removeMessages(102);
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m1654f() {
        if (!this.f4123o) {
            this.f4117i.sendEmptyMessageDelayed(202, 10L);
            return;
        }
        Frame frame = null;
        if (this.f4119k.m1695f() == null) {
            if (Build.VERSION.SDK_INT >= 16) {
                frame = m1652g();
            }
        } else {
            this.f4119k.m1683r();
            frame = this.f4119k.m1681t();
            if (frame == null) {
                this.f4117i.sendEmptyMessage(202);
                return;
            }
            frame.m2343a(frame.m2329e() + this.f4122n);
        }
        if (frame.m2309p()) {
            if (this.f4112d.m1673g()) {
                if (this.f4112d.m1674f() && this.f4118j.m1686o()) {
                    TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "throw last audio");
                    m1669a(frame);
                }
                this.f4117i.sendEmptyMessage(203);
                return;
            }
            long m2319j = 1024000000 / this.f4124p.m2319j();
            this.f4122n = this.f4124p.m2329e() + m2319j;
            TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "mEOFAudioFrameUs:" + this.f4122n + ",mCurrentAudioDuration:" + this.f4126r);
            long j = this.f4122n;
            long j2 = this.f4126r;
            if (j < j2) {
                int i = (int) ((j2 - j) / m2319j);
                TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "count:" + i);
                for (int i2 = 0; i2 < i; i2++) {
                    m1670a(m2319j);
                }
                this.f4122n = this.f4126r;
            }
            m1648i();
            this.f4117i.sendEmptyMessage(202);
            return;
        }
        this.f4124p = frame;
        m1669a(frame);
        this.f4117i.sendEmptyMessage(202);
    }

    /* renamed from: g */
    private Frame m1652g() {
        MediaFormat m1473g;
        if (Build.VERSION.SDK_INT < 16 || (m1473g = VideoSourceListConfig.m1480a().m1473g()) == null) {
            return null;
        }
        int integer = m1473g.getInteger("sample-rate");
        int integer2 = m1473g.getInteger("channel-count");
        long j = 1024000000 / integer;
        int i = integer2 * 2048;
        Frame frame = new Frame("audio/mp4a-latm", ByteBuffer.allocate(i), new MediaCodec.BufferInfo());
        frame.m2324g(integer);
        frame.m2322h(integer2);
        frame.m2331d(i);
        long j2 = 0;
        Frame frame2 = this.f4124p;
        if (frame2 != null) {
            j2 = frame2.m2329e() + j;
        }
        if (j2 >= this.f4126r) {
            frame.m2334c(4);
        }
        frame.m2343a(j2);
        return frame;
    }

    /* renamed from: a */
    public void m1670a(long j) {
        ByteBuffer allocate = ByteBuffer.allocate(this.f4124p.m2325g());
        Log.i("lyj", "mCurrentAudioFrame.getLength():" + this.f4124p.m2325g());
        this.f4124p.m2341a(allocate);
        Frame frame = new Frame(this.f4124p.m2346a(), this.f4124p.m2338b(), this.f4124p.m2310o());
        frame.m2344a(this.f4124p.m2335c());
        frame.m2337b(this.f4124p.m2332d());
        frame.m2324g(this.f4124p.m2319j());
        long m2329e = this.f4124p.m2329e() + j;
        frame.m2343a(m2329e);
        frame.m2336b(m2329e);
        frame.m2333c(m2329e);
        frame.m2334c(this.f4124p.m2327f());
        this.f4124p = frame;
        m1669a(frame);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: h */
    public void m1650h() {
        this.f4118j.m1684q();
        Frame m1682s = this.f4118j.m1682s();
        if (m1682s == null) {
            this.f4116h.sendEmptyMessage(102);
            return;
        }
        if (this.f4121m != 0) {
            TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "before:" + m1682s.m2329e() + ",after:" + (m1682s.m2329e() + this.f4121m));
        }
        m1682s.m2343a(m1682s.m2329e() + this.f4121m);
        if (m1682s.m2309p()) {
            if (this.f4112d.m1674f()) {
                if (this.f4112d.m1673g() && this.f4119k.m1685p()) {
                    TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "throw last video");
                    m1662b(m1682s);
                }
                this.f4116h.sendEmptyMessage(103);
                return;
            }
            this.f4121m = this.f4120l.m2329e();
            TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "mEOFVideoFrameUs:" + this.f4121m + ",mCurrentVideoDuration:" + this.f4125q);
            long j = this.f4121m;
            long j2 = this.f4125q;
            if (j != j2) {
                this.f4121m = j2;
            }
            m1647j();
            this.f4116h.sendEmptyMessage(102);
        } else if (m1682s.m2329e() > this.f4125q) {
            TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "dropOne");
            m1660c();
        } else {
            this.f4120l = m1682s;
            m1662b(m1682s);
        }
    }

    /* renamed from: i */
    private void m1648i() {
        if (!this.f4112d.m1675e()) {
            TXCLog.m2914e("VideoJoinDecAndDemuxGenerate", "nextAudioExtractorConfig isAllReadEOF");
            return;
        }
        this.f4119k = this.f4112d.m1677c();
        this.f4126r += this.f4119k.m1691j();
    }

    /* renamed from: j */
    private void m1647j() {
        if (!this.f4112d.m1676d()) {
            TXCLog.m2914e("VideoJoinDecAndDemuxGenerate", "nextVideoExtractorConfig isAllReadEOF");
            return;
        }
        this.f4118j = this.f4112d.m1678b();
        this.f4125q += this.f4118j.m1691j();
        TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "Duration :" + this.f4119k.m1691j());
        TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "AudioDuration :" + this.f4118j.m1692i() + ", VideoDuration:" + this.f4118j.m1693h());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: k */
    public void m1646k() {
        TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "startVideoDecoder");
        List<VideoExtractConfig> m1680a = this.f4112d.m1680a();
        for (int i = 0; i < m1680a.size(); i++) {
            VideoExtractConfig videoExtractConfig = m1680a.get(i);
            videoExtractConfig.m1698c();
            videoExtractConfig.m1690k();
        }
        this.f4118j = this.f4112d.m1678b();
        this.f4125q = this.f4118j.m1691j();
        TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "Duration :" + this.f4118j.m1691j());
        TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "AudioDuration :" + this.f4118j.m1692i() + ", VideoDuration:" + this.f4118j.m1693h());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: l */
    public void m1645l() {
        TXCLog.m2913i("VideoJoinDecAndDemuxGenerate", "stopVideoDecoder");
        List<VideoExtractConfig> m1680a = this.f4112d.m1680a();
        for (int i = 0; i < m1680a.size(); i++) {
            m1680a.get(i).m1689l();
        }
    }

    /* renamed from: a */
    private void m1669a(Frame frame) {
        IAudioJoinDecoderListener iAudioJoinDecoderListener = this.f4111c;
        if (iAudioJoinDecoderListener != null) {
            iAudioJoinDecoderListener.mo1493a(frame, this.f4119k);
        }
    }

    /* renamed from: b */
    private void m1662b(Frame frame) {
        IVideoJoinDecoderListener iVideoJoinDecoderListener = this.f4110b;
        if (iVideoJoinDecoderListener != null) {
            iVideoJoinDecoderListener.mo1494a(frame, this.f4118j);
        }
    }
}
