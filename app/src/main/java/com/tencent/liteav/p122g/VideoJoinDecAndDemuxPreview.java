package com.tencent.liteav.p122g;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.tencent.liteav.g.m */
/* loaded from: classes3.dex */
public class VideoJoinDecAndDemuxPreview {

    /* renamed from: c */
    private IVideoJoinDecoderListener f4131c;

    /* renamed from: d */
    private IAudioJoinDecoderListener f4132d;

    /* renamed from: e */
    private List<VideoExtractConfig> f4133e;

    /* renamed from: k */
    private VideoExtractConfig f4139k;

    /* renamed from: l */
    private VideoExtractConfig f4140l;

    /* renamed from: p */
    private long f4144p;

    /* renamed from: q */
    private boolean f4145q;

    /* renamed from: r */
    private Frame f4146r;

    /* renamed from: s */
    private long f4147s;

    /* renamed from: t */
    private long f4148t;

    /* renamed from: v */
    private Frame f4150v;

    /* renamed from: w */
    private long f4151w;

    /* renamed from: x */
    private long f4152x;

    /* renamed from: z */
    private long f4154z;

    /* renamed from: a */
    private final String f4129a = "VideoJoinDecAndDemuxPreview";

    /* renamed from: m */
    private long f4141m = -1;

    /* renamed from: n */
    private long f4142n = -1;

    /* renamed from: o */
    private long f4143o = -1;

    /* renamed from: u */
    private boolean f4149u = true;

    /* renamed from: y */
    private Object f4153y = new Object();

    /* renamed from: f */
    private AtomicInteger f4134f = new AtomicInteger(1);

    /* renamed from: b */
    private VideoSourceListConfig f4130b = VideoSourceListConfig.m1480a();

    /* renamed from: g */
    private HandlerThread f4135g = new HandlerThread("video_handler_thread");

    /* renamed from: i */
    private HandlerC3479b f4137i = new HandlerC3479b(this.f4135g.getLooper());

    /* renamed from: h */
    private HandlerThread f4136h = new HandlerThread("audio_handler_thread");

    /* renamed from: j */
    private HandlerC3478a f4138j = new HandlerC3478a(this.f4136h.getLooper());

    public VideoJoinDecAndDemuxPreview() {
        this.f4135g.start();
        this.f4136h.start();
    }

    /* renamed from: a */
    public void m1636a(List<VideoExtractConfig> list) {
        this.f4133e = list;
    }

    /* renamed from: a */
    public void m1640a(IVideoJoinDecoderListener iVideoJoinDecoderListener) {
        synchronized (this.f4153y) {
            this.f4131c = iVideoJoinDecoderListener;
        }
    }

    /* renamed from: a */
    public void m1641a(IAudioJoinDecoderListener iAudioJoinDecoderListener) {
        synchronized (this.f4153y) {
            this.f4132d = iAudioJoinDecoderListener;
        }
    }

    /* renamed from: a */
    public synchronized void m1644a() {
        TXCLog.m2914e("VideoJoinDecAndDemuxPreview", C2516Ad.TYPE_START);
        if (this.f4134f.get() == 2) {
            TXCLog.m2914e("VideoJoinDecAndDemuxPreview", "start ignore, mCurrentState = " + this.f4134f.get());
            return;
        }
        this.f4149u = true;
        this.f4130b.m1468l();
        this.f4134f.set(2);
        this.f4137i.sendEmptyMessage(101);
        this.f4138j.sendEmptyMessage(201);
    }

    /* renamed from: b */
    public synchronized void m1634b() {
        TXCLog.m2914e("VideoJoinDecAndDemuxPreview", "stop");
        if (this.f4134f.get() == 1) {
            TXCLog.m2914e("VideoJoinDecAndDemuxPreview", "stop(), mCurrentState in stop, ignore");
            return;
        }
        this.f4134f.set(1);
        this.f4137i.sendEmptyMessage(103);
        this.f4138j.sendEmptyMessage(203);
    }

    /* renamed from: c */
    public synchronized void m1629c() {
        int i = this.f4134f.get();
        if (i != 3 && i != 1) {
            this.f4134f.set(3);
            this.f4137i.sendEmptyMessage(104);
            this.f4138j.sendEmptyMessage(204);
            return;
        }
        TXCLog.m2914e("VideoJoinDecAndDemuxPreview", "pause ignore, current state = " + i);
    }

    /* renamed from: d */
    public synchronized void m1626d() {
        int i = this.f4134f.get();
        if (i != 1 && i != 2) {
            this.f4134f.set(2);
            this.f4137i.sendEmptyMessage(102);
            this.f4138j.sendEmptyMessage(202);
            return;
        }
        TXCLog.m2914e("VideoJoinDecAndDemuxPreview", "resume ignore, state = " + i);
    }

    /* renamed from: a */
    public void m1635a(boolean z) {
        this.f4149u = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: VideoJoinDecAndDemuxPreview.java */
    /* renamed from: com.tencent.liteav.g.m$a */
    /* loaded from: classes3.dex */
    public class HandlerC3478a extends Handler {
        public HandlerC3478a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 201:
                    VideoJoinDecAndDemuxPreview.this.m1611k();
                    VideoJoinDecAndDemuxPreview.this.f4138j.sendEmptyMessage(202);
                    return;
                case 202:
                    VideoJoinDecAndDemuxPreview.this.m1623e();
                    return;
                case 203:
                    VideoJoinDecAndDemuxPreview.this.f4138j.removeMessages(202);
                    VideoJoinDecAndDemuxPreview.this.m1610l();
                    VideoJoinDecAndDemuxPreview.this.f4143o = -1L;
                    VideoJoinDecAndDemuxPreview.this.f4150v = null;
                    VideoJoinDecAndDemuxPreview.this.f4148t = 0L;
                    return;
                case 204:
                    VideoJoinDecAndDemuxPreview.this.f4138j.removeMessages(202);
                    VideoJoinDecAndDemuxPreview.this.f4143o = -1L;
                    VideoJoinDecAndDemuxPreview.this.f4150v = null;
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: VideoJoinDecAndDemuxPreview.java */
    /* renamed from: com.tencent.liteav.g.m$b */
    /* loaded from: classes3.dex */
    public class HandlerC3479b extends Handler {
        public HandlerC3479b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 101:
                    VideoJoinDecAndDemuxPreview.this.m1609m();
                    VideoJoinDecAndDemuxPreview.this.f4137i.sendEmptyMessage(102);
                    return;
                case 102:
                    VideoJoinDecAndDemuxPreview.this.m1617g();
                    return;
                case 103:
                    VideoJoinDecAndDemuxPreview.this.f4137i.removeMessages(102);
                    VideoJoinDecAndDemuxPreview.this.m1608n();
                    VideoJoinDecAndDemuxPreview.this.f4146r = null;
                    VideoJoinDecAndDemuxPreview.this.f4141m = -1L;
                    VideoJoinDecAndDemuxPreview.this.f4142n = -1L;
                    VideoJoinDecAndDemuxPreview.this.f4144p = -1L;
                    VideoJoinDecAndDemuxPreview.this.f4147s = 0L;
                    return;
                case 104:
                    VideoJoinDecAndDemuxPreview.this.f4137i.removeMessages(102);
                    VideoJoinDecAndDemuxPreview.this.f4146r = null;
                    VideoJoinDecAndDemuxPreview.this.f4141m = -1L;
                    VideoJoinDecAndDemuxPreview.this.f4142n = -1L;
                    VideoJoinDecAndDemuxPreview.this.f4144p = -1L;
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m1623e() {
        if (!this.f4149u) {
            this.f4138j.sendEmptyMessageDelayed(202, 10L);
            return;
        }
        Frame frame = null;
        if (this.f4140l.m1695f() == null) {
            if (Build.VERSION.SDK_INT >= 16) {
                frame = m1620f();
            }
        } else {
            this.f4140l.m1683r();
            frame = this.f4140l.m1681t();
            if (frame == null) {
                this.f4138j.sendEmptyMessage(202);
                return;
            }
            TXCLog.m2911w("VideoJoinDecAndDemuxPreview", "before:" + frame.m2329e() + ",after:" + (frame.m2329e() + this.f4148t));
            frame.m2343a(frame.m2329e() + this.f4148t);
            if (frame != null) {
                TXCLog.m2913i("lyj", "====:" + frame.m2329e() + ",len:" + frame.m2325g() + ",mEOFAudioFrameUs:" + this.f4148t + ",flag:" + frame.m2327f());
            }
        }
        if (frame.m2309p()) {
            if (this.f4130b.m1469k()) {
                if (this.f4130b.m1470j() && this.f4139k.m1686o()) {
                    TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "throw last audio");
                    m1642a(frame);
                    synchronized (this) {
                        this.f4134f.set(1);
                    }
                }
                this.f4138j.sendEmptyMessage(203);
                return;
            }
            long m2319j = 1024000000 / this.f4150v.m2319j();
            this.f4148t = this.f4150v.m2329e() + m2319j;
            TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "mEOFAudioFrameUs:" + this.f4148t + ",mCurrentAudioDuration:" + this.f4152x);
            long j = this.f4148t;
            long j2 = this.f4152x;
            if (j < j2) {
                int i = (int) ((j2 - j) / m2319j);
                TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "count:" + i);
                for (int i2 = 0; i2 < i; i2++) {
                    m1643a(m2319j);
                }
                this.f4148t = this.f4152x;
            }
            m1613i();
            this.f4138j.sendEmptyMessage(202);
            return;
        }
        if (this.f4150v == null) {
            this.f4143o = System.currentTimeMillis();
        }
        this.f4150v = frame;
        m1642a(frame);
        this.f4138j.sendEmptyMessage(202);
    }

    /* renamed from: f */
    private Frame m1620f() {
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
        Frame frame2 = this.f4150v;
        if (frame2 != null) {
            j2 = frame2.m2329e() + j;
        }
        if (j2 >= this.f4152x) {
            frame.m2334c(4);
        }
        frame.m2343a(j2);
        return frame;
    }

    /* renamed from: a */
    public void m1643a(long j) {
        this.f4150v.m2341a(ByteBuffer.allocate(this.f4150v.m2325g()));
        Frame frame = new Frame(this.f4150v.m2346a(), this.f4150v.m2338b(), this.f4150v.m2310o());
        frame.m2344a(this.f4150v.m2335c());
        frame.m2337b(this.f4150v.m2332d());
        frame.m2324g(this.f4150v.m2319j());
        frame.m2322h(this.f4150v.m2317k());
        frame.m2343a(this.f4150v.m2329e() + j);
        this.f4150v = frame;
        TXCLog.m2915d("VideoJoinDecAndDemuxPreview", "------insertEmptyAudioFrame--------");
        m1642a(frame);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: g */
    public void m1617g() {
        if (this.f4141m >= 0) {
            if (this.f4145q) {
                m1633b(this.f4146r);
            } else if (!m1615h()) {
                this.f4137i.sendEmptyMessageDelayed(102, 5L);
                return;
            } else {
                m1633b(this.f4146r);
            }
        }
        this.f4139k.m1684q();
        Frame m1682s = this.f4139k.m1682s();
        if (m1682s == null) {
            this.f4137i.sendEmptyMessage(102);
            return;
        }
        m1682s.m2343a(m1682s.m2329e() + this.f4147s);
        if (m1682s.m2309p()) {
            if (this.f4130b.m1470j()) {
                if (this.f4130b.m1469k() && this.f4140l.m1685p()) {
                    TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "throw last video");
                    m1633b(m1682s);
                    this.f4134f.set(1);
                    this.f4151w = 0L;
                }
                this.f4137i.sendEmptyMessage(103);
                return;
            }
            this.f4147s = this.f4146r.m2329e();
            TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "mEOFVideoFrameUs:" + this.f4147s + ",mCurrentVideoDuration:" + this.f4151w);
            long j = this.f4147s;
            long j2 = this.f4151w;
            if (j != j2) {
                this.f4147s = j2;
            }
            m1612j();
            this.f4137i.sendEmptyMessage(102);
        } else if (m1682s.m2329e() > this.f4151w) {
            TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "dropOne");
            this.f4137i.sendEmptyMessage(102);
        } else {
            this.f4146r = m1682s;
            this.f4141m = m1682s.m2329e() / 1000;
            if (this.f4142n < 0) {
                this.f4142n = this.f4141m;
                long j3 = this.f4143o;
                if (j3 > 0) {
                    this.f4144p = j3;
                    TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "mTimelineMs get AudioFrame:" + this.f4143o);
                } else {
                    this.f4144p = System.currentTimeMillis();
                    TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "mTimelineMs get SystemTime:" + this.f4144p);
                }
                this.f4145q = true;
                this.f4137i.sendEmptyMessage(102);
                return;
            }
            this.f4145q = false;
            this.f4137i.sendEmptyMessageDelayed(102, 5L);
        }
    }

    /* renamed from: h */
    private boolean m1615h() {
        long currentTimeMillis = System.currentTimeMillis();
        this.f4141m = this.f4146r.m2329e() / 1000;
        return Math.abs(this.f4141m - this.f4142n) < currentTimeMillis - this.f4144p;
    }

    /* renamed from: i */
    private void m1613i() {
        if (!this.f4130b.m1471i()) {
            TXCLog.m2914e("VideoJoinDecAndDemuxPreview", "isAllReadEOF");
            this.f4138j.sendEmptyMessage(203);
            return;
        }
        this.f4140l = this.f4130b.m1475e();
        this.f4152x += this.f4140l.m1691j();
        this.f4138j.sendEmptyMessage(202);
    }

    /* renamed from: j */
    private void m1612j() {
        if (!this.f4130b.m1474f()) {
            TXCLog.m2914e("VideoJoinDecAndDemuxPreview", "isAllReadEOF");
            this.f4137i.sendEmptyMessage(103);
            return;
        }
        this.f4139k = this.f4130b.m1476d();
        this.f4151w += this.f4139k.m1691j();
        TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "Duration :" + this.f4139k.m1691j());
        this.f4137i.sendEmptyMessage(102);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: k */
    public void m1611k() {
        TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "startAudioDecoder");
        List<VideoExtractConfig> m1477c = VideoSourceListConfig.m1480a().m1477c();
        for (int i = 0; i < m1477c.size(); i++) {
            VideoExtractConfig videoExtractConfig = m1477c.get(i);
            videoExtractConfig.m1697d();
            videoExtractConfig.m1688m();
        }
        this.f4140l = this.f4130b.m1475e();
        this.f4152x = this.f4140l.m1691j();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: l */
    public void m1610l() {
        TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "stopAudioDecoder");
        List<VideoExtractConfig> m1477c = VideoSourceListConfig.m1480a().m1477c();
        for (int i = 0; i < m1477c.size(); i++) {
            m1477c.get(i).m1687n();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: m */
    public void m1609m() {
        TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "startVideoDecoder");
        List<VideoExtractConfig> m1477c = VideoSourceListConfig.m1480a().m1477c();
        for (int i = 0; i < m1477c.size(); i++) {
            VideoExtractConfig videoExtractConfig = m1477c.get(i);
            videoExtractConfig.m1698c();
            videoExtractConfig.m1690k();
        }
        this.f4139k = this.f4130b.m1476d();
        this.f4151w = this.f4139k.m1691j();
        TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "Duration :" + this.f4139k.m1691j());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: n */
    public void m1608n() {
        TXCLog.m2913i("VideoJoinDecAndDemuxPreview", "stopVideoDecoder");
        List<VideoExtractConfig> m1477c = VideoSourceListConfig.m1480a().m1477c();
        for (int i = 0; i < m1477c.size(); i++) {
            m1477c.get(i).m1689l();
        }
    }

    /* renamed from: a */
    private void m1642a(Frame frame) {
        synchronized (this.f4153y) {
            if (this.f4132d != null) {
                TXCLog.m2915d("VideoJoinDecAndDemuxPreview", "source:" + this.f4140l.f4089a + ",throwOutAudioFrame: " + frame.m2329e());
                this.f4132d.mo1493a(frame, this.f4140l);
            }
        }
    }

    /* renamed from: b */
    private void m1633b(Frame frame) {
        synchronized (this.f4153y) {
            if (this.f4131c != null && frame.m2329e() != this.f4154z) {
                TXCLog.m2915d("VideoJoinDecAndDemuxPreview", "source:" + this.f4139k.f4089a + ",throwOutVideoFrame: " + frame.m2329e());
                this.f4131c.mo1494a(frame, this.f4139k);
                this.f4154z = frame.m2329e();
            }
        }
    }
}
