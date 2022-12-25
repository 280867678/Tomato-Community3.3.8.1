package com.tencent.liteav.p120e;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.LongSparseArray;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p118c.ReverseConfig;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p121f.SpeedFilterChain;
import com.tencent.liteav.p122g.TXAudioDecoderWrapper;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(16)
/* renamed from: com.tencent.liteav.e.x */
/* loaded from: classes3.dex */
public class VideoDecAndDemuxPreview extends BasicVideoDecDemux {

    /* renamed from: A */
    private int f3736A;

    /* renamed from: D */
    private int f3739D;

    /* renamed from: F */
    private long f3741F;

    /* renamed from: G */
    private int f3742G;

    /* renamed from: N */
    private Frame f3749N;

    /* renamed from: Q */
    private long f3752Q;

    /* renamed from: w */
    private Frame f3766w;

    /* renamed from: x */
    private Frame f3767x;

    /* renamed from: k */
    private final String f3754k = "VideoDecAndDemuxPreview";

    /* renamed from: y */
    private volatile boolean f3768y = true;

    /* renamed from: z */
    private long f3769z = -1;

    /* renamed from: B */
    private long f3737B = 0;

    /* renamed from: C */
    private long f3738C = 0;

    /* renamed from: E */
    private boolean f3740E = false;

    /* renamed from: H */
    private long f3743H = -1;

    /* renamed from: I */
    private long f3744I = -1;

    /* renamed from: J */
    private long f3745J = -1;

    /* renamed from: K */
    private long f3746K = -1;

    /* renamed from: L */
    private long f3747L = -1;

    /* renamed from: O */
    private long f3750O = -1;

    /* renamed from: m */
    private LongSparseArray<Frame> f3756m = new LongSparseArray<>();

    /* renamed from: l */
    private LongSparseArray<Frame> f3755l = new LongSparseArray<>();

    /* renamed from: v */
    private AtomicInteger f3765v = new AtomicInteger(1);

    /* renamed from: r */
    private AtomicBoolean f3761r = new AtomicBoolean(false);

    /* renamed from: s */
    private AtomicBoolean f3762s = new AtomicBoolean(false);

    /* renamed from: R */
    private AtomicBoolean f3753R = new AtomicBoolean(false);

    /* renamed from: t */
    private AtomicBoolean f3763t = new AtomicBoolean(false);

    /* renamed from: u */
    private AtomicBoolean f3764u = new AtomicBoolean(false);

    /* renamed from: M */
    private AtomicBoolean f3748M = new AtomicBoolean(false);

    /* renamed from: P */
    private AtomicBoolean f3751P = new AtomicBoolean(false);

    /* renamed from: o */
    private HandlerThread f3758o = new HandlerThread("video_handler_thread");

    /* renamed from: n */
    private HandlerC3456b f3757n = new HandlerC3456b(this.f3758o.getLooper());

    /* renamed from: q */
    private HandlerThread f3760q = new HandlerThread("audio_handler_thread");

    /* renamed from: p */
    private HandlerC3455a f3759p = new HandlerC3455a(this.f3760q.getLooper());

    public VideoDecAndDemuxPreview() {
        this.f3591g = new AtomicLong(0L);
        this.f3592h = new AtomicLong(0L);
        this.f3758o.start();
        this.f3760q.start();
    }

    /* renamed from: l */
    public synchronized void m2015l() {
        TXCLog.m2913i("VideoDecAndDemuxPreview", "start(), mCurrentState = " + this.f3765v);
        if (this.f3765v.get() == 2) {
            TXCLog.m2914e("VideoDecAndDemuxPreview", "start ignore, mCurrentState = " + this.f3765v.get());
            return;
        }
        this.f3755l.clear();
        this.f3748M.set(false);
        this.f3753R.set(true);
        this.f3751P.getAndSet(false);
        this.f3761r.getAndSet(false);
        this.f3762s.getAndSet(false);
        this.f3763t.getAndSet(false);
        this.f3764u.getAndSet(false);
        this.f3749N = null;
        this.f3768y = true;
        this.f3743H = -1L;
        if (this.f3765v.get() == 3) {
            TXCLog.m2913i("VideoDecAndDemuxPreview", "start(), state pause, seek then send MSG_VIDEO_DECODE_START and MSG_AUDIO_DECODE_START");
            this.f3765v.set(2);
            m2035b(this.f3591g.get());
            this.f3757n.sendEmptyMessage(102);
            if (m2164h()) {
                this.f3759p.sendEmptyMessage(202);
            }
        } else if (this.f3765v.get() == 4) {
            TXCLog.m2913i("VideoDecAndDemuxPreview", "start(), state preview at time, stop then start");
            m2013m();
            m2015l();
        } else {
            TXCLog.m2913i("VideoDecAndDemuxPreview", "start(), state init, seek then send MSG_VIDEO_DECODE_CONFIG and MSG_AUDIO_DECODE_CONFIG");
            this.f3765v.set(2);
            m2035b(this.f3591g.get());
            this.f3757n.sendEmptyMessage(101);
            if (m2164h()) {
                this.f3759p.sendEmptyMessage(201);
            }
        }
    }

    /* renamed from: m */
    public void m2013m() {
        if (this.f3765v.get() == 1) {
            TXCLog.m2914e("VideoDecAndDemuxPreview", "stop(), mCurrentState in stop, ignore");
            return;
        }
        this.f3765v.set(1);
        TXCLog.m2913i("VideoDecAndDemuxPreview", "stop(), send MSG_VIDEO_DECODE_STOP");
        this.f3757n.sendEmptyMessage(103);
        if (!m2164h()) {
            return;
        }
        TXCLog.m2913i("VideoDecAndDemuxPreview", "stop(), send MSG_AUDIO_DECODE_STOP");
        this.f3759p.sendEmptyMessage(203);
    }

    /* renamed from: n */
    public synchronized void m2011n() {
        int i = this.f3765v.get();
        if (i != 3 && i != 1) {
            this.f3765v.set(3);
            TXCLog.m2913i("VideoDecAndDemuxPreview", "pause(), send MSG_VIDEO_DECODE_PAUSE");
            this.f3757n.sendEmptyMessage(104);
            if (m2164h()) {
                TXCLog.m2913i("VideoDecAndDemuxPreview", "pause(), send MSG_AUDIO_DECODE_PAUSE");
                this.f3759p.sendEmptyMessage(204);
            }
            return;
        }
        TXCLog.m2914e("VideoDecAndDemuxPreview", "pause ignore, current state = " + i);
    }

    /* renamed from: o */
    public synchronized void m2009o() {
        int i = this.f3765v.get();
        if (i != 1 && i != 2 && i != 4) {
            this.f3765v.set(2);
            TXCLog.m2913i("VideoDecAndDemuxPreview", "resume(), send MSG_VIDEO_DECODE_START");
            this.f3757n.sendEmptyMessage(102);
            if (m2164h()) {
                TXCLog.m2913i("VideoDecAndDemuxPreview", "resume(), send MSG_AUDIO_DECODE_START");
                this.f3759p.sendEmptyMessage(202);
            }
            return;
        }
        TXCLog.m2914e("VideoDecAndDemuxPreview", "resume ignore, state = " + i);
    }

    /* renamed from: a */
    public void m2041a(long j) {
        this.f3752Q = j * 1000;
        if (this.f3765v.get() == 3 || this.f3765v.get() == 4) {
            TXCLog.m2913i("VideoDecAndDemuxPreview", "previewAtTime, state = " + this.f3765v.get() + ", send MSG_VIDEO_DECODE_PREVIEW_START");
            this.f3765v.set(4);
            this.f3757n.sendEmptyMessage(5);
            return;
        }
        TXCLog.m2913i("VideoDecAndDemuxPreview", "previewAtTime, state = " + this.f3765v.get() + ", send MSG_VIDEO_DECODE_PREVIEW_CONFIG");
        this.f3765v.set(4);
        synchronized (this) {
            m2003r();
        }
        this.f3757n.sendEmptyMessage(6);
    }

    /* renamed from: a */
    public void m2036a(boolean z) {
        this.f3768y = z;
    }

    /* renamed from: a */
    public synchronized void m2040a(long j, long j2) {
        this.f3591g.getAndSet(j);
        this.f3592h.getAndSet(j2);
        m2003r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: r */
    public void m2003r() {
        this.f3749N = null;
        this.f3767x = null;
        this.f3746K = -1L;
        this.f3747L = -1L;
        this.f3744I = -1L;
        this.f3745J = -1L;
        this.f3748M.set(false);
        TXCLog.m2915d("VideoDecAndDemuxPreview", "avsync video frame reset first systime " + this.f3745J);
        m2034b(this.f3737B, this.f3738C);
    }

    /* renamed from: b */
    public void m2034b(long j, long j2) {
        if (j == 0 && j2 == 0) {
            this.f3739D = 0;
        } else {
            this.f3739D = 3;
        }
        this.f3737B = j;
        this.f3738C = j2;
        this.f3740E = false;
    }

    /* renamed from: p */
    public int m2007p() {
        return this.f3585a.m1734f();
    }

    @Override // com.tencent.liteav.p120e.BasicVideoDecDemux
    @TargetApi(18)
    /* renamed from: k */
    public void mo2017k() {
        HandlerThread handlerThread = this.f3758o;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        HandlerThread handlerThread2 = this.f3760q;
        if (handlerThread2 != null) {
            handlerThread2.quitSafely();
        }
        LongSparseArray<Frame> longSparseArray = this.f3755l;
        if (longSparseArray != null) {
            longSparseArray.clear();
        }
        LongSparseArray<Frame> longSparseArray2 = this.f3756m;
        if (longSparseArray2 != null) {
            longSparseArray2.clear();
        }
        this.f3766w = null;
        this.f3767x = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: VideoDecAndDemuxPreview.java */
    /* renamed from: com.tencent.liteav.e.x$b */
    /* loaded from: classes3.dex */
    public class HandlerC3456b extends Handler {
        public HandlerC3456b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 5) {
                try {
                    VideoDecAndDemuxPreview.this.f3585a.m1746a(VideoDecAndDemuxPreview.this.f3752Q);
                    Frame mo461c = VideoDecAndDemuxPreview.this.f3586b.mo461c();
                    if (mo461c == null) {
                        return;
                    }
                    VideoDecAndDemuxPreview.this.f3586b.mo466a(VideoDecAndDemuxPreview.this.f3585a.m1745a(mo461c));
                    Frame mo460d = VideoDecAndDemuxPreview.this.f3586b.mo460d();
                    if (mo460d == null) {
                        TXCLog.m2914e("VideoDecAndDemuxPreview", "VideoDecodeHandler, preview at time, frame is null");
                        return;
                    }
                    mo460d.m2318j(VideoDecAndDemuxPreview.this.m2168d());
                    mo460d.m2316k(VideoDecAndDemuxPreview.this.m2167e());
                    mo460d.m2328e(VideoDecAndDemuxPreview.this.m2007p());
                    if (ReverseConfig.m2478a().m2476b() && mo460d.m2329e() <= VideoDecAndDemuxPreview.this.f3591g.get()) {
                        VideoDecAndDemuxPreview.this.f3586b.m2209b(mo460d);
                    }
                    VideoDecAndDemuxPreview.this.m2029c(mo460d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (i != 6) {
                switch (i) {
                    case 101:
                        TXCLog.m2913i("VideoDecAndDemuxPreview", "normal : configureVideo()");
                        VideoDecAndDemuxPreview.this.m2177a();
                        if (ReverseConfig.m2478a().m2476b()) {
                            VideoDecAndDemuxPreview videoDecAndDemuxPreview = VideoDecAndDemuxPreview.this;
                            videoDecAndDemuxPreview.f3585a.m1746a(videoDecAndDemuxPreview.f3592h.get());
                            TXCLog.m2913i("VideoDecAndDemuxPreview", "VideoDecodeHandler, reverse, seekVideo time = " + VideoDecAndDemuxPreview.this.f3592h);
                        }
                        VideoDecAndDemuxPreview.this.f3757n.sendEmptyMessage(102);
                        return;
                    case 102:
                        try {
                            if (VideoDecAndDemuxPreview.this.f3746K >= 0) {
                                if (!VideoDecAndDemuxPreview.this.f3749N.m2309p()) {
                                    if (VideoDecAndDemuxPreview.this.f3748M.get()) {
                                        VideoDecAndDemuxPreview.this.m2029c(VideoDecAndDemuxPreview.this.f3749N);
                                    } else if (!VideoDecAndDemuxPreview.this.m1999t()) {
                                        VideoDecAndDemuxPreview.this.f3757n.sendEmptyMessageDelayed(102, 5L);
                                        return;
                                    } else {
                                        VideoDecAndDemuxPreview.this.m2029c(VideoDecAndDemuxPreview.this.f3749N);
                                    }
                                } else {
                                    TXCLog.m2913i("VideoDecAndDemuxPreview", "is end video frame, to stop decode");
                                    VideoDecAndDemuxPreview.this.m2029c(VideoDecAndDemuxPreview.this.f3749N);
                                    VideoDecAndDemuxPreview.this.f3757n.sendEmptyMessage(103);
                                    return;
                                }
                            }
                            VideoDecAndDemuxPreview.this.m1998u();
                            Frame m2001s = VideoDecAndDemuxPreview.this.m2001s();
                            if (m2001s == null) {
                                VideoDecAndDemuxPreview.this.f3757n.sendEmptyMessage(102);
                                return;
                            }
                            VideoDecAndDemuxPreview.this.f3749N = VideoDecAndDemuxPreview.this.m2033b(m2001s);
                            if (VideoDecAndDemuxPreview.this.f3744I >= 0) {
                                VideoDecAndDemuxPreview.this.f3748M.compareAndSet(true, false);
                                VideoDecAndDemuxPreview.this.f3757n.sendEmptyMessageDelayed(102, 5L);
                                return;
                            }
                            VideoDecAndDemuxPreview.this.f3744I = VideoDecAndDemuxPreview.this.f3746K;
                            if (VideoDecAndDemuxPreview.this.f3750O > 0) {
                                VideoDecAndDemuxPreview.this.f3745J = VideoDecAndDemuxPreview.this.f3750O;
                            } else {
                                VideoDecAndDemuxPreview.this.f3745J = System.currentTimeMillis();
                            }
                            TXCLog.m2915d("VideoDecAndDemuxPreview", "avsync first video frame ts : " + VideoDecAndDemuxPreview.this.f3744I + ", first systime : " + VideoDecAndDemuxPreview.this.f3745J + ", current systime " + System.currentTimeMillis());
                            VideoDecAndDemuxPreview.this.f3748M.set(true);
                            VideoDecAndDemuxPreview.this.f3757n.sendEmptyMessage(102);
                            return;
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    case 103:
                        TXCLog.m2913i("VideoDecAndDemuxPreview", "VideoDecodeHandler, video decode stop!");
                        if (VideoDecAndDemuxPreview.this.f3763t.get() && VideoDecAndDemuxPreview.this.f3764u.get()) {
                            VideoDecAndDemuxPreview.this.f3765v.set(1);
                        }
                        VideoDecAndDemuxPreview.this.f3757n.removeMessages(102);
                        synchronized (VideoDecAndDemuxPreview.this) {
                            VideoDecAndDemuxPreview.this.m2003r();
                        }
                        VideoMediaCodecDecoder videoMediaCodecDecoder = VideoDecAndDemuxPreview.this.f3586b;
                        if (videoMediaCodecDecoder == null) {
                            return;
                        }
                        videoMediaCodecDecoder.mo463b();
                        VideoDecAndDemuxPreview.this.f3586b = null;
                        return;
                    case 104:
                        TXCLog.m2913i("VideoDecAndDemuxPreview", "video decode pause");
                        VideoDecAndDemuxPreview.this.f3757n.removeMessages(102);
                        synchronized (VideoDecAndDemuxPreview.this) {
                            VideoDecAndDemuxPreview.this.m2003r();
                        }
                        return;
                    default:
                        return;
                }
            } else {
                TXCLog.m2913i("VideoDecAndDemuxPreview", "preview at time : configureVideo()");
                VideoDecAndDemuxPreview.this.m2177a();
                VideoDecAndDemuxPreview videoDecAndDemuxPreview2 = VideoDecAndDemuxPreview.this;
                videoDecAndDemuxPreview2.f3585a.m1746a(videoDecAndDemuxPreview2.f3752Q);
                VideoDecAndDemuxPreview.this.f3757n.sendEmptyMessage(5);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public Frame m2033b(Frame frame) {
        if (!SpeedFilterChain.m1849a().m1843c()) {
            frame.m2336b(frame.m2329e());
            return frame;
        } else if (this.f3767x == null) {
            TXCLog.m2913i("VideoDecAndDemuxPreview", "processSpeedFrame, mLastVideoFrame is null, this is first frame, not to speed");
            return frame;
        } else if (frame.m2309p()) {
            TXCLog.m2913i("VideoDecAndDemuxPreview", "processSpeedFrame, this frame is end frame, not to speed");
            return frame;
        } else {
            long m2305t = this.f3767x.m2305t() + (((float) (frame.m2329e() - this.f3767x.m2329e())) / SpeedFilterChain.m1849a().m1847a(frame.m2329e()));
            frame.m2336b(m2305t);
            this.f3746K = m2305t / 1000;
            return frame;
        }
    }

    /* renamed from: b */
    private synchronized void m2035b(long j) {
        if (this.f3751P.get()) {
            TXCLog.m2914e("VideoDecAndDemuxPreview", "seekPosition, had seeked");
            return;
        }
        TXCLog.m2915d("VideoDecAndDemuxPreview", "======================准备开始定位video和audio起点=====================开始时间mStartTime = " + this.f3591g);
        this.f3585a.m1746a(j);
        long m1724p = this.f3585a.m1724p();
        this.f3585a.m1739c(m1724p);
        long m1723q = this.f3585a.m1723q();
        TXCLog.m2915d("VideoDecAndDemuxPreview", "======================定位结束=====================");
        TXCLog.m2915d("VideoDecAndDemuxPreview", "==============seekTime==========" + this.f3591g);
        TXCLog.m2915d("VideoDecAndDemuxPreview", "==============startVdts==========" + m1724p);
        TXCLog.m2915d("VideoDecAndDemuxPreview", "==============startAdts==========" + m1723q);
        this.f3751P.getAndSet(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m2029c(Frame frame) {
        IVideoEditDecoderListener iVideoEditDecoderListener = this.f3590f;
        if (iVideoEditDecoderListener != null) {
            iVideoEditDecoderListener.mo1939a(frame);
        }
        this.f3767x = frame;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    /* renamed from: s */
    public Frame m2001s() {
        Frame mo460d = this.f3586b.mo460d();
        if (mo460d == null || mo460d.m2310o() == null) {
            return null;
        }
        Frame frame = this.f3755l.get(mo460d.m2329e());
        if (frame != null) {
            mo460d = this.f3586b.m2210a(frame, mo460d);
            if (ReverseConfig.m2478a().m2476b()) {
                mo460d.m2343a(frame.m2303v());
            }
        } else {
            mo460d.m2318j(m2168d());
            mo460d.m2316k(m2167e());
        }
        if (mo460d.m2329e() < this.f3591g.get() && !mo460d.m2309p()) {
            TXCLog.m2915d("VideoDecAndDemuxPreview", "VideoFrame pts :" + mo460d.m2329e() + " before  startTime (" + this.f3591g + ")");
            return null;
        }
        if (mo460d.m2329e() > this.f3592h.get()) {
            TXCLog.m2915d("VideoDecAndDemuxPreview", "VideoFrame pts :" + mo460d.m2329e() + " after  duration (" + this.f3592h + ")");
            if (ReverseConfig.m2478a().m2476b()) {
                return null;
            }
            mo460d = this.f3586b.m2209b(mo460d);
        }
        if (mo460d.m2309p()) {
            this.f3763t.getAndSet(true);
            TXCLog.m2915d("VideoDecAndDemuxPreview", "==================preview decode Video END==========================");
            if (!this.f3764u.get()) {
                TXCLog.m2915d("VideoDecAndDemuxPreview", "-------------- preview Audio NOT END ----------------");
                return mo460d;
            }
            TXCLog.m2915d("VideoDecAndDemuxPreview", "================== VIDEO SEND END OF FILE ==========================" + mo460d.toString());
        }
        this.f3749N = mo460d;
        this.f3746K = this.f3749N.m2329e() / 1000;
        return mo460d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: t */
    public boolean m1999t() {
        this.f3747L = System.currentTimeMillis();
        this.f3746K = this.f3749N.m2305t() / 1000;
        return Math.abs(this.f3746K - this.f3744I) < this.f3747L - this.f3745J;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: u */
    public synchronized void m1998u() throws InterruptedException {
        boolean m1738c;
        if (this.f3761r.get()) {
            TXCLog.m2914e("VideoDecAndDemuxPreview", "readVideoFrame, read video end of file, ignore");
            return;
        }
        Frame mo461c = this.f3586b.mo461c();
        if (mo461c == null) {
            return;
        }
        if ((this.f3739D == 3 || this.f3739D == 2) && this.f3591g.get() <= this.f3737B && this.f3585a.m1722r() >= this.f3738C) {
            this.f3585a.m1746a(this.f3737B);
            this.f3739D--;
            this.f3740E = true;
        }
        Frame m1745a = this.f3585a.m1745a(mo461c);
        if (this.f3736A <= 0) {
            this.f3736A = m2162j();
            if (this.f3736A != 0) {
                this.f3742G = (1000 / this.f3736A) * 1000;
            }
        }
        if (this.f3740E) {
            m1745a.m2343a(this.f3741F + this.f3742G);
        }
        this.f3741F = m1745a.m2329e();
        if (this.f3743H < 0) {
            this.f3743H = this.f3741F;
        }
        if (ReverseConfig.m2478a().m2476b()) {
            if (m1745a.m2309p()) {
                this.f3741F = m2174a(m1745a);
                this.f3743H = this.f3741F;
            }
            m1738c = m2176a(this.f3741F, this.f3742G, m1745a);
            if (!m1738c) {
                long abs = Math.abs(this.f3743H - this.f3741F);
                TXCLog.m2913i("VideoDecAndDemuxPreview", "reverse newVPts = " + abs + ", mFirstVideoReadPTS = " + this.f3743H + ", curFixFrame.getSampleTime() = " + this.f3741F);
                m1745a.m2343a(abs);
                m1745a.m2333c(abs);
                m1745a.m2330d(this.f3741F);
            }
        } else {
            m1738c = this.f3585a.m1738c(m1745a);
        }
        if (m1738c) {
            this.f3761r.set(true);
            TXCLog.m2915d("VideoDecAndDemuxPreview", "read video end");
        }
        this.f3755l.put(m1745a.m2329e(), m1745a);
        this.f3586b.mo466a(m1745a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: VideoDecAndDemuxPreview.java */
    /* renamed from: com.tencent.liteav.e.x$a */
    /* loaded from: classes3.dex */
    public class HandlerC3455a extends Handler {
        public HandlerC3455a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 201:
                    VideoDecAndDemuxPreview.this.m2170b();
                    VideoDecAndDemuxPreview.this.f3759p.sendEmptyMessage(202);
                    return;
                case 202:
                    TXCLog.m2913i("VideoDecAndDemuxPreview", "avsync audio frame start AudioDecodeHandler, mCurrentState = " + VideoDecAndDemuxPreview.this.f3765v + ", mAudioDecodeEOF = " + VideoDecAndDemuxPreview.this.f3764u);
                    while (VideoDecAndDemuxPreview.this.f3765v.get() != 1 && !VideoDecAndDemuxPreview.this.f3764u.get()) {
                        try {
                            if (VideoDecAndDemuxPreview.this.f3765v.get() == 3) {
                                VideoDecAndDemuxPreview.this.f3766w = null;
                                VideoDecAndDemuxPreview.this.f3750O = -1L;
                                Thread.sleep(10L);
                            } else if (VideoDecAndDemuxPreview.this.f3768y) {
                                VideoDecAndDemuxPreview.this.m1997v();
                                VideoDecAndDemuxPreview.this.m1996w();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            if (VideoDecAndDemuxPreview.this.f3765v.get() == 1) {
                            }
                        }
                    }
                    VideoDecAndDemuxPreview.this.f3766w = null;
                    VideoDecAndDemuxPreview.this.f3750O = -1L;
                    if (VideoDecAndDemuxPreview.this.f3765v.get() == 1) {
                        TXCLog.m2915d("VideoDecAndDemuxPreview", "AudioDecodeHandler, loop decode end state is init, ignore to stop");
                        return;
                    }
                    TXCLog.m2913i("VideoDecAndDemuxPreview", "AudioDecodeHandler, in MSG_AUDIO_DECODE_START, send MSG_AUDIO_DECODE_STOP");
                    VideoDecAndDemuxPreview.this.f3759p.sendEmptyMessage(203);
                    return;
                case 203:
                    TXCLog.m2913i("VideoDecAndDemuxPreview", "AudioDecodeHandler, audio decode stop!");
                    VideoDecAndDemuxPreview.this.f3759p.removeMessages(202);
                    TXAudioDecoderWrapper tXAudioDecoderWrapper = VideoDecAndDemuxPreview.this.f3587c;
                    if (tXAudioDecoderWrapper == null) {
                        return;
                    }
                    tXAudioDecoderWrapper.mo463b();
                    VideoDecAndDemuxPreview.this.f3587c = null;
                    return;
                case 204:
                    VideoDecAndDemuxPreview.this.f3766w = null;
                    VideoDecAndDemuxPreview.this.f3750O = -1L;
                    VideoDecAndDemuxPreview.this.f3759p.removeMessages(202);
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: v */
    public synchronized void m1997v() {
        if (this.f3762s.get()) {
            return;
        }
        Frame mo461c = this.f3587c.mo461c();
        if (mo461c == null) {
            return;
        }
        Frame m1741b = this.f3585a.m1741b(mo461c);
        if (this.f3585a.m1736d(m1741b)) {
            this.f3762s.set(true);
            TXCLog.m2915d("VideoDecAndDemuxPreview", "audio endOfFile:" + this.f3762s.get());
            TXCLog.m2915d("VideoDecAndDemuxPreview", "read audio end");
        }
        this.f3756m.put(m1741b.m2329e(), m1741b);
        this.f3587c.mo466a(m1741b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: w */
    public synchronized void m1996w() {
        Frame mo460d = this.f3587c.mo460d();
        if (mo460d == null) {
            return;
        }
        if (this.f3587c.m1717e() && this.f3753R.get()) {
            MediaFormat m2166f = m2166f();
            m2166f.setInteger("sample-rate", mo460d.m2319j());
            VideoOutputConfig.m2457a().m2455a(m2166f);
            this.f3753R.set(false);
        }
        if (mo460d.m2310o() == null) {
            return;
        }
        Frame frame = this.f3756m.get(mo460d.m2329e());
        Frame m1720a = frame != null ? this.f3587c.m1720a(frame, mo460d) : mo460d;
        if (m1720a == null) {
            return;
        }
        if (m1720a.m2329e() < this.f3591g.get() && !m1720a.m2309p()) {
            TXCLog.m2915d("VideoDecAndDemuxPreview", "AudioFrame pts :" + m1720a.m2329e() + " before  startTime (" + this.f3591g + ")");
            return;
        }
        if (m1720a.m2329e() > this.f3592h.get()) {
            TXCLog.m2915d("VideoDecAndDemuxPreview", "AudioFrame pts :" + m1720a.m2329e() + " after  duration (" + this.f3592h + ")");
            m1720a = this.f3587c.m1718b(m1720a);
        }
        if (m1720a.m2309p()) {
            this.f3764u.set(true);
            TXCLog.m2915d("VideoDecAndDemuxPreview", "==================preview decode Audio END==========================");
            if (!this.f3763t.get()) {
                TXCLog.m2915d("VideoDecAndDemuxPreview", "-------------- preview VIDEO NOT END ----------------");
                return;
            }
            TXCLog.m2915d("VideoDecAndDemuxPreview", "================== AUDIO SEND END OF FILE ==========================" + m1720a.toString());
        }
        if (this.f3766w == null) {
            this.f3766w = mo460d;
            this.f3750O = System.currentTimeMillis();
            TXCLog.m2915d("VideoDecAndDemuxPreview", "avsync first audio frame ts : " + this.f3766w.m2329e() + ", first systime : " + this.f3750O);
        }
        if (this.f3769z == -1) {
            this.f3769z = System.currentTimeMillis();
        }
        if (this.f3589e != null) {
            this.f3589e.mo1938a(m1720a);
        }
        this.f3766w = m1720a;
        this.f3769z = System.currentTimeMillis();
    }

    /* renamed from: q */
    public boolean m2005q() {
        return this.f3763t.get();
    }
}
