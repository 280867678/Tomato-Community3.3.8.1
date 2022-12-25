package com.tencent.liteav.p120e;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LongSparseArray;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p118c.ReverseConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p121f.SpeedFilterChain;
import com.tencent.liteav.p122g.MediaExtractorWrapper;
import com.tencent.liteav.p122g.TXAudioDecoderWrapper;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(16)
/* renamed from: com.tencent.liteav.e.d */
/* loaded from: classes3.dex */
public abstract class BasicVideoDecDemuxGenerater extends BasicVideoDecDemux {

    /* renamed from: A */
    protected HandlerC3438a f3597A;

    /* renamed from: B */
    protected HandlerThread f3598B;

    /* renamed from: D */
    protected Frame f3600D;

    /* renamed from: E */
    protected Frame f3601E;

    /* renamed from: F */
    protected int f3602F;

    /* renamed from: I */
    protected int f3605I;

    /* renamed from: K */
    protected long f3607K;

    /* renamed from: L */
    protected int f3608L;

    /* renamed from: R */
    protected List<Long> f3614R;

    /* renamed from: S */
    protected int f3615S;

    /* renamed from: T */
    protected int f3616T;

    /* renamed from: U */
    protected AtomicBoolean f3617U;

    /* renamed from: X */
    private MediaExtractorWrapper f3620X;

    /* renamed from: Y */
    private long f3621Y;

    /* renamed from: Z */
    private long f3622Z;

    /* renamed from: y */
    protected HandlerC3439b f3637y;

    /* renamed from: z */
    protected HandlerThread f3638z;

    /* renamed from: V */
    private final String f3618V = "BasicVideoDecDemuxGenerater";

    /* renamed from: k */
    protected final int f3623k = 5;

    /* renamed from: l */
    protected final int f3624l = 6;

    /* renamed from: m */
    protected final int f3625m = 7;

    /* renamed from: n */
    protected final int f3626n = 8;

    /* renamed from: o */
    protected final int f3627o = 9;

    /* renamed from: p */
    protected final int f3628p = 10;

    /* renamed from: q */
    protected final int f3629q = 11;

    /* renamed from: C */
    protected volatile boolean f3599C = true;

    /* renamed from: G */
    protected long f3603G = 0;

    /* renamed from: H */
    protected long f3604H = 0;

    /* renamed from: J */
    protected boolean f3606J = false;

    /* renamed from: M */
    protected long f3609M = -1;

    /* renamed from: N */
    protected long f3610N = -1;

    /* renamed from: O */
    protected long f3611O = -1;

    /* renamed from: W */
    private long f3619W = -1;

    /* renamed from: s */
    protected LongSparseArray<Frame> f3631s = new LongSparseArray<>();

    /* renamed from: r */
    protected LongSparseArray<Frame> f3630r = new LongSparseArray<>();

    /* renamed from: x */
    protected AtomicInteger f3636x = new AtomicInteger(1);

    /* renamed from: t */
    protected AtomicBoolean f3632t = new AtomicBoolean(false);

    /* renamed from: u */
    protected AtomicBoolean f3633u = new AtomicBoolean(false);

    /* renamed from: v */
    protected AtomicBoolean f3634v = new AtomicBoolean(false);

    /* renamed from: w */
    protected AtomicBoolean f3635w = new AtomicBoolean(false);

    /* renamed from: P */
    protected AtomicBoolean f3612P = new AtomicBoolean(false);

    /* renamed from: Q */
    protected AtomicBoolean f3613Q = new AtomicBoolean(false);

    /* renamed from: a */
    public abstract void mo2045a(boolean z);

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: l */
    public abstract void mo2044l();

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: m */
    public abstract void mo2043m();

    public BasicVideoDecDemuxGenerater() {
        this.f3591g = new AtomicLong(0L);
        this.f3592h = new AtomicLong(0L);
    }

    /* renamed from: n */
    public int m2136n() {
        return this.f3585a.m1734f();
    }

    /* compiled from: BasicVideoDecDemuxGenerater.java */
    /* renamed from: com.tencent.liteav.e.d$b */
    /* loaded from: classes3.dex */
    protected class HandlerC3439b extends Handler {
        public HandlerC3439b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Frame mo461c;
            int i = message.what;
            switch (i) {
                case 5:
                    List<Long> list = BasicVideoDecDemuxGenerater.this.f3614R;
                    if (list == null || list.size() == 0) {
                        return;
                    }
                    BasicVideoDecDemuxGenerater.this.m2177a();
                    BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater = BasicVideoDecDemuxGenerater.this;
                    basicVideoDecDemuxGenerater.f3615S = 0;
                    basicVideoDecDemuxGenerater.f3616T = 0;
                    basicVideoDecDemuxGenerater.f3637y.sendEmptyMessage(6);
                    return;
                case 6:
                    BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater2 = BasicVideoDecDemuxGenerater.this;
                    if (basicVideoDecDemuxGenerater2.f3616T >= basicVideoDecDemuxGenerater2.f3614R.size()) {
                        return;
                    }
                    if (BasicVideoDecDemuxGenerater.this.f3619W < 0) {
                        BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater3 = BasicVideoDecDemuxGenerater.this;
                        if (basicVideoDecDemuxGenerater3.f3615S >= basicVideoDecDemuxGenerater3.f3614R.size()) {
                            BasicVideoDecDemuxGenerater.this.f3619W = 0L;
                        } else {
                            BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater4 = BasicVideoDecDemuxGenerater.this;
                            basicVideoDecDemuxGenerater4.f3619W = basicVideoDecDemuxGenerater4.f3614R.get(basicVideoDecDemuxGenerater4.f3615S).longValue();
                            BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater5 = BasicVideoDecDemuxGenerater.this;
                            basicVideoDecDemuxGenerater5.f3585a.m1746a(basicVideoDecDemuxGenerater5.f3619W);
                            TXCLog.m2913i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, get pts = " + BasicVideoDecDemuxGenerater.this.f3619W + ", mVideoGivenPtsInputIndex = " + BasicVideoDecDemuxGenerater.this.f3615S);
                        }
                    }
                    if (BasicVideoDecDemuxGenerater.this.f3619W >= 0 && (mo461c = BasicVideoDecDemuxGenerater.this.f3586b.mo461c()) != null) {
                        BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater6 = BasicVideoDecDemuxGenerater.this;
                        if (basicVideoDecDemuxGenerater6.f3615S < basicVideoDecDemuxGenerater6.f3614R.size()) {
                            BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater7 = BasicVideoDecDemuxGenerater.this;
                            basicVideoDecDemuxGenerater7.f3615S++;
                            BasicVideoDecDemuxGenerater.this.f3586b.mo466a(basicVideoDecDemuxGenerater7.f3585a.m1745a(mo461c));
                            TXCLog.m2913i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, freeFrame exist");
                        } else {
                            TXCLog.m2913i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, isReadGivenTimeEnd, set end flag");
                            mo461c.m2343a(0L);
                            mo461c.m2334c(4);
                            BasicVideoDecDemuxGenerater.this.f3586b.mo466a(mo461c);
                        }
                        BasicVideoDecDemuxGenerater.this.f3619W = -1L;
                    }
                    if (!BasicVideoDecDemuxGenerater.this.f3617U.get()) {
                        BasicVideoDecDemuxGenerater.this.f3637y.sendEmptyMessageDelayed(6, 5L);
                        return;
                    }
                    Frame mo460d = BasicVideoDecDemuxGenerater.this.f3586b.mo460d();
                    if (mo460d == null) {
                        BasicVideoDecDemuxGenerater.this.f3637y.sendEmptyMessageDelayed(6, 5L);
                        return;
                    }
                    mo460d.m2328e(BasicVideoDecDemuxGenerater.this.m2136n());
                    mo460d.m2318j(BasicVideoDecDemuxGenerater.this.m2168d());
                    mo460d.m2316k(BasicVideoDecDemuxGenerater.this.m2167e());
                    BasicVideoDecDemuxGenerater.this.m2149c(mo460d);
                    BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater8 = BasicVideoDecDemuxGenerater.this;
                    basicVideoDecDemuxGenerater8.f3616T++;
                    basicVideoDecDemuxGenerater8.f3617U.set(false);
                    BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater9 = BasicVideoDecDemuxGenerater.this;
                    if (basicVideoDecDemuxGenerater9.f3616T >= basicVideoDecDemuxGenerater9.f3614R.size()) {
                        TXCLog.m2913i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, decode end");
                        BasicVideoDecDemuxGenerater.this.f3637y.sendEmptyMessage(7);
                        return;
                    }
                    BasicVideoDecDemuxGenerater.this.f3637y.sendEmptyMessageDelayed(6, 5L);
                    return;
                case 7:
                    BasicVideoDecDemuxGenerater.this.f3637y.removeMessages(6);
                    VideoMediaCodecDecoder videoMediaCodecDecoder = BasicVideoDecDemuxGenerater.this.f3586b;
                    if (videoMediaCodecDecoder != null) {
                        videoMediaCodecDecoder.mo463b();
                        BasicVideoDecDemuxGenerater.this.f3586b = null;
                    }
                    BasicVideoDecDemuxGenerater.this.f3636x.set(1);
                    return;
                case 8:
                    List<Long> list2 = BasicVideoDecDemuxGenerater.this.f3614R;
                    if (list2 == null || list2.size() == 0) {
                        return;
                    }
                    BasicVideoDecDemuxGenerater.this.m2134q();
                    BasicVideoDecDemuxGenerater.this.m2177a();
                    BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater10 = BasicVideoDecDemuxGenerater.this;
                    basicVideoDecDemuxGenerater10.f3615S = 0;
                    basicVideoDecDemuxGenerater10.f3616T = 0;
                    basicVideoDecDemuxGenerater10.f3621Y = 0L;
                    BasicVideoDecDemuxGenerater.this.f3622Z = 0L;
                    sendEmptyMessage(9);
                    return;
                case 9:
                    BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater11 = BasicVideoDecDemuxGenerater.this;
                    if (basicVideoDecDemuxGenerater11.f3616T >= basicVideoDecDemuxGenerater11.f3614R.size()) {
                        return;
                    }
                    BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater12 = BasicVideoDecDemuxGenerater.this;
                    if (basicVideoDecDemuxGenerater12.f3615S < basicVideoDecDemuxGenerater12.f3614R.size()) {
                        BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater13 = BasicVideoDecDemuxGenerater.this;
                        basicVideoDecDemuxGenerater13.f3619W = basicVideoDecDemuxGenerater13.f3614R.get(basicVideoDecDemuxGenerater13.f3615S).longValue();
                        if (BasicVideoDecDemuxGenerater.this.f3621Y >= BasicVideoDecDemuxGenerater.this.f3619W) {
                            TXCLog.m2913i("BasicVideoDecDemuxGenerater", "seek lastSyncTime:" + BasicVideoDecDemuxGenerater.this.f3622Z + ",index:" + BasicVideoDecDemuxGenerater.this.f3615S);
                            BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater14 = BasicVideoDecDemuxGenerater.this;
                            basicVideoDecDemuxGenerater14.f3585a.m1742b(basicVideoDecDemuxGenerater14.f3622Z);
                            sendEmptyMessage(10);
                            BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater15 = BasicVideoDecDemuxGenerater.this;
                            basicVideoDecDemuxGenerater15.f3621Y = basicVideoDecDemuxGenerater15.f3622Z;
                            return;
                        }
                        BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater16 = BasicVideoDecDemuxGenerater.this;
                        basicVideoDecDemuxGenerater16.f3622Z = basicVideoDecDemuxGenerater16.f3621Y;
                        BasicVideoDecDemuxGenerater.this.f3620X.m1742b(BasicVideoDecDemuxGenerater.this.f3622Z + 1);
                        BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater17 = BasicVideoDecDemuxGenerater.this;
                        basicVideoDecDemuxGenerater17.f3621Y = basicVideoDecDemuxGenerater17.f3620X.m1724p();
                        TXCLog.m2913i("BasicVideoDecDemuxGenerater", "nextSyncTime:" + BasicVideoDecDemuxGenerater.this.f3621Y + ",lastSyncTime" + BasicVideoDecDemuxGenerater.this.f3622Z + ",mGivenPts:" + BasicVideoDecDemuxGenerater.this.f3619W);
                        if (BasicVideoDecDemuxGenerater.this.f3621Y == -1 || BasicVideoDecDemuxGenerater.this.f3621Y == BasicVideoDecDemuxGenerater.this.f3622Z) {
                            BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater18 = BasicVideoDecDemuxGenerater.this;
                            basicVideoDecDemuxGenerater18.f3621Y = basicVideoDecDemuxGenerater18.f3622Z;
                            TXCLog.m2913i("BasicVideoDecDemuxGenerater", "seek lastSyncTime:" + BasicVideoDecDemuxGenerater.this.f3622Z + ",index:" + BasicVideoDecDemuxGenerater.this.f3615S);
                            sendEmptyMessage(10);
                            return;
                        }
                        sendEmptyMessage(9);
                        return;
                    }
                    sendEmptyMessage(11);
                    return;
                case 10:
                    try {
                        if (BasicVideoDecDemuxGenerater.this.f3586b == null) {
                            return;
                        }
                        BasicVideoDecDemuxGenerater.this.m2132s();
                        Frame m2133r = BasicVideoDecDemuxGenerater.this.m2133r();
                        if (m2133r == null) {
                            sendEmptyMessageDelayed(10, 5L);
                            return;
                        }
                        m2133r.m2318j(BasicVideoDecDemuxGenerater.this.m2168d());
                        m2133r.m2316k(BasicVideoDecDemuxGenerater.this.m2167e());
                        if (m2133r.m2309p()) {
                            BasicVideoDecDemuxGenerater.this.m2149c(m2133r);
                            sendEmptyMessage(11);
                            return;
                        } else if (m2133r.m2329e() >= BasicVideoDecDemuxGenerater.this.f3619W) {
                            BasicVideoDecDemuxGenerater.this.m2149c(m2133r);
                            BasicVideoDecDemuxGenerater.this.f3615S++;
                            sendEmptyMessage(9);
                            return;
                        } else {
                            sendEmptyMessage(10);
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 11:
                    BasicVideoDecDemuxGenerater.this.f3637y.removeMessages(10);
                    VideoMediaCodecDecoder videoMediaCodecDecoder2 = BasicVideoDecDemuxGenerater.this.f3586b;
                    if (videoMediaCodecDecoder2 != null) {
                        videoMediaCodecDecoder2.mo463b();
                        BasicVideoDecDemuxGenerater.this.f3586b = null;
                    }
                    BasicVideoDecDemuxGenerater.this.f3636x.set(1);
                    return;
                default:
                    switch (i) {
                        case 101:
                            BasicVideoDecDemuxGenerater.this.m2177a();
                            if (ReverseConfig.m2478a().m2476b()) {
                                BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater19 = BasicVideoDecDemuxGenerater.this;
                                basicVideoDecDemuxGenerater19.f3585a.m1746a(basicVideoDecDemuxGenerater19.f3592h.get());
                                TXCLog.m2913i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, reverse, seekVideo time = " + BasicVideoDecDemuxGenerater.this.f3592h);
                            }
                            BasicVideoDecDemuxGenerater.this.f3637y.sendEmptyMessage(102);
                            return;
                        case 102:
                            try {
                                if (BasicVideoDecDemuxGenerater.this.f3586b == null) {
                                    return;
                                }
                                BasicVideoDecDemuxGenerater.this.m2132s();
                                Frame m2133r2 = BasicVideoDecDemuxGenerater.this.m2133r();
                                if (m2133r2 != null) {
                                    Frame m2142e = BasicVideoDecDemuxGenerater.this.m2142e(m2133r2);
                                    if (!m2142e.m2309p()) {
                                        BasicVideoDecDemuxGenerater.this.m2149c(m2142e);
                                        return;
                                    }
                                    TXCLog.m2913i("BasicVideoDecDemuxGenerater", "is end video frame, to stop decode, mAudioDecodeEOF = " + BasicVideoDecDemuxGenerater.this.f3635w);
                                    if (!BasicVideoDecDemuxGenerater.this.m2164h()) {
                                        BasicVideoDecDemuxGenerater.this.m2149c(m2142e);
                                    } else if (BasicVideoDecDemuxGenerater.this.f3635w.get()) {
                                        BasicVideoDecDemuxGenerater.this.m2149c(m2142e);
                                    }
                                    BasicVideoDecDemuxGenerater.this.f3637y.sendEmptyMessage(103);
                                    return;
                                }
                                BasicVideoDecDemuxGenerater.this.f3637y.sendEmptyMessage(102);
                                return;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                return;
                            }
                        case 103:
                            TXCLog.m2913i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, video decode stop!");
                            BasicVideoDecDemuxGenerater.this.f3637y.removeMessages(102);
                            BasicVideoDecDemuxGenerater basicVideoDecDemuxGenerater20 = BasicVideoDecDemuxGenerater.this;
                            basicVideoDecDemuxGenerater20.f3601E = null;
                            basicVideoDecDemuxGenerater20.f3610N = -1L;
                            basicVideoDecDemuxGenerater20.f3612P.set(false);
                            VideoMediaCodecDecoder videoMediaCodecDecoder3 = BasicVideoDecDemuxGenerater.this.f3586b;
                            if (videoMediaCodecDecoder3 == null) {
                                return;
                            }
                            videoMediaCodecDecoder3.mo463b();
                            BasicVideoDecDemuxGenerater.this.f3586b = null;
                            return;
                        default:
                            return;
                    }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: q */
    public void m2134q() {
        this.f3620X = new MediaExtractorWrapper();
        try {
            this.f3620X.m1744a(this.f3594j);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* compiled from: BasicVideoDecDemuxGenerater.java */
    /* renamed from: com.tencent.liteav.e.d$a */
    /* loaded from: classes3.dex */
    class HandlerC3438a extends Handler {
        public HandlerC3438a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 201:
                    BasicVideoDecDemuxGenerater.this.m2170b();
                    BasicVideoDecDemuxGenerater.this.f3597A.sendEmptyMessage(202);
                    return;
                case 202:
                    break;
                case 203:
                    TXCLog.m2913i("BasicVideoDecDemuxGenerater", "AudioDecodeHandler, audio decode stop!");
                    BasicVideoDecDemuxGenerater.this.f3597A.removeMessages(202);
                    TXAudioDecoderWrapper tXAudioDecoderWrapper = BasicVideoDecDemuxGenerater.this.f3587c;
                    if (tXAudioDecoderWrapper == null) {
                        return;
                    }
                    tXAudioDecoderWrapper.mo463b();
                    BasicVideoDecDemuxGenerater.this.f3587c = null;
                    return;
                default:
                    return;
            }
            while (BasicVideoDecDemuxGenerater.this.f3636x.get() != 1 && !BasicVideoDecDemuxGenerater.this.f3635w.get()) {
                if (BasicVideoDecDemuxGenerater.this.f3599C) {
                    BasicVideoDecDemuxGenerater.this.m2131t();
                    Frame m2130u = BasicVideoDecDemuxGenerater.this.m2130u();
                    if (m2130u == null) {
                        continue;
                    } else {
                        Frame m2154b = BasicVideoDecDemuxGenerater.this.m2154b(m2130u);
                        if (!m2154b.m2309p()) {
                            BasicVideoDecDemuxGenerater.this.m2145d(m2154b);
                        } else {
                            TXCLog.m2913i("BasicVideoDecDemuxGenerater", "is end audio frame, to stop decode, mVideoDecodeEOF = " + BasicVideoDecDemuxGenerater.this.f3634v);
                            if (BasicVideoDecDemuxGenerater.this.f3634v.get()) {
                                BasicVideoDecDemuxGenerater.this.m2145d(m2154b);
                            }
                            BasicVideoDecDemuxGenerater.this.f3597A.sendEmptyMessage(203);
                            return;
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public Frame m2154b(Frame frame) {
        if (!ReverseConfig.m2478a().m2476b()) {
            return frame;
        }
        if (this.f3611O < 0) {
            this.f3611O = frame.m2329e();
        }
        this.f3609M = frame.m2329e();
        long j = this.f3609M - this.f3611O;
        TXCLog.m2913i("BasicVideoDecDemuxGenerater", "processReverseAudioFrame newVPts = " + j + ", mFirstAudioFramePTS = " + this.f3611O + ", curAudioFrame pts = " + this.f3609M);
        frame.m2343a(j);
        return frame;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m2149c(Frame frame) {
        IVideoEditDecoderListener iVideoEditDecoderListener = this.f3590f;
        if (iVideoEditDecoderListener != null) {
            iVideoEditDecoderListener.mo1939a(frame);
        }
        this.f3601E = frame;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public void m2145d(Frame frame) {
        IAudioEditDecoderListener iAudioEditDecoderListener = this.f3589e;
        if (iAudioEditDecoderListener != null) {
            iAudioEditDecoderListener.mo1938a(frame);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public Frame m2142e(Frame frame) {
        if (this.f3601E == null) {
            TXCLog.m2913i("BasicVideoDecDemuxGenerater", "processSpeedFrame, mLastVideoFrame is null");
            return frame;
        } else if (frame.m2309p() || !SpeedFilterChain.m1849a().m1843c()) {
            return frame;
        } else {
            frame.m2336b(this.f3601E.m2305t() + (((float) (frame.m2329e() - this.f3601E.m2329e())) / SpeedFilterChain.m1849a().m1847a(frame.m2329e())));
            return frame;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void m2161a(long j) {
        if (this.f3613Q.get()) {
            TXCLog.m2914e("BasicVideoDecDemuxGenerater", "seekPosition, had seeked");
            return;
        }
        TXCLog.m2915d("BasicVideoDecDemuxGenerater", "======================准备开始定位video和audio起点=====================开始时间mStartTime = " + this.f3591g);
        this.f3585a.m1746a(j);
        long m1724p = this.f3585a.m1724p();
        this.f3585a.m1739c(m1724p);
        long m1723q = this.f3585a.m1723q();
        TXCLog.m2915d("BasicVideoDecDemuxGenerater", "==============startVdts==========" + m1724p);
        TXCLog.m2915d("BasicVideoDecDemuxGenerater", "==============startAdts==========" + m1723q);
        this.f3613Q.getAndSet(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    /* renamed from: r */
    public Frame m2133r() {
        Frame mo460d = this.f3586b.mo460d();
        if (mo460d == null || mo460d.m2310o() == null) {
            return null;
        }
        if (mo460d.m2309p()) {
            TXCLog.m2913i("BasicVideoDecDemuxGenerater", "getDecodeVideoFrame, is end frame");
            mo460d.m2318j(m2168d());
            mo460d.m2316k(m2167e());
            this.f3634v.getAndSet(true);
            return mo460d;
        }
        long j = 0;
        Frame frame = this.f3630r.get(mo460d.m2329e());
        if (frame != null) {
            mo460d = this.f3586b.m2210a(frame, mo460d);
            if (ReverseConfig.m2478a().m2476b()) {
                mo460d.m2343a(frame.m2303v());
                mo460d.m2333c(frame.m2304u());
                j = frame.m2303v();
            } else {
                j = mo460d.m2329e();
            }
        }
        if (j < this.f3591g.get()) {
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "VideoFrame pts :" + j + " before  startTime (" + this.f3591g + ")");
            return null;
        }
        if (j > this.f3592h.get()) {
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "VideoFrame pts :" + j + " after  duration (" + this.f3592h + ")");
            if (ReverseConfig.m2478a().m2476b()) {
                return null;
            }
            mo460d = this.f3586b.m2209b(mo460d);
        }
        if (mo460d.m2309p()) {
            this.f3634v.getAndSet(true);
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "==================generate decode Video END==========================");
            if (!this.f3635w.get()) {
                TXCLog.m2915d("BasicVideoDecDemuxGenerater", "-------------- generate Audio NOT END ----------------");
                return mo460d;
            }
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "================== VIDEO SEND END OF FILE ==========================" + mo460d.toString());
        }
        return mo460d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: s */
    public void m2132s() {
        boolean m1738c;
        if (this.f3632t.get()) {
            TXCLog.m2914e("BasicVideoDecDemuxGenerater", "readVideoFrame, read video end of file, ignore");
            return;
        }
        Frame mo461c = this.f3586b.mo461c();
        if (mo461c == null) {
            return;
        }
        int i = this.f3605I;
        if ((i == 3 || i == 2) && this.f3585a.m1722r() >= this.f3604H) {
            this.f3585a.m1746a(this.f3603G);
            this.f3605I--;
            this.f3606J = true;
        }
        Frame m1745a = this.f3585a.m1745a(mo461c);
        if (this.f3602F <= 0) {
            this.f3602F = m2162j();
            int i2 = this.f3602F;
            if (i2 != 0) {
                this.f3608L = (1000 / i2) * 1000;
            }
        }
        if (this.f3606J) {
            m1745a.m2343a(this.f3607K + this.f3608L);
        }
        this.f3607K = m1745a.m2329e();
        if (this.f3610N < 0) {
            this.f3610N = this.f3607K;
        }
        if (ReverseConfig.m2478a().m2476b()) {
            if (m1745a.m2309p()) {
                this.f3607K = m2174a(m1745a);
                this.f3610N = this.f3607K;
            }
            m1738c = m2176a(this.f3607K, this.f3608L, m1745a);
            if (!m1738c) {
                long abs = Math.abs(this.f3610N - this.f3607K);
                TXCLog.m2913i("BasicVideoDecDemuxGenerater", "reverse newVPts = " + abs + ", mFirstVideoFramePTS = " + this.f3610N + ", curFixFrame.getSampleTime() = " + this.f3607K);
                m1745a.m2343a(abs);
                m1745a.m2333c(abs);
                m1745a.m2330d(this.f3607K);
            }
        } else {
            m1738c = this.f3585a.m1738c(m1745a);
        }
        if (m1738c) {
            this.f3632t.set(true);
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "read video end");
        }
        this.f3630r.put(m1745a.m2329e(), m1745a);
        this.f3586b.mo466a(m1745a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: t */
    public void m2131t() {
        Frame mo461c;
        if (!this.f3633u.get() && (mo461c = this.f3587c.mo461c()) != null) {
            Frame m1741b = this.f3585a.m1741b(mo461c);
            if (this.f3585a.m1736d(m1741b)) {
                this.f3633u.set(true);
                TXCLog.m2915d("BasicVideoDecDemuxGenerater", "audio endOfFile:" + this.f3633u.get());
                TXCLog.m2915d("BasicVideoDecDemuxGenerater", "read audio end");
            }
            this.f3631s.put(m1741b.m2329e(), m1741b);
            this.f3587c.mo466a(m1741b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: u */
    public Frame m2130u() {
        Frame mo460d = this.f3587c.mo460d();
        if (mo460d == null || mo460d.m2310o() == null) {
            return null;
        }
        Frame frame = this.f3631s.get(mo460d.m2329e());
        Frame m1720a = frame != null ? this.f3587c.m1720a(frame, mo460d) : mo460d;
        if (m1720a == null) {
            return null;
        }
        if (m1720a.m2329e() < this.f3591g.get() && !m1720a.m2309p()) {
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "AudioFrame pts :" + m1720a.m2329e() + " before  startTime (" + this.f3591g + ")");
            return null;
        }
        if (m1720a.m2329e() > this.f3592h.get()) {
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "AudioFrame pts :" + m1720a.m2329e() + " after  duration (" + this.f3592h + ")");
            m1720a = this.f3587c.m1718b(m1720a);
        }
        if (m1720a.m2309p()) {
            this.f3635w.set(true);
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "==================generate decode Audio END==========================");
            if (!this.f3634v.get()) {
                TXCLog.m2915d("BasicVideoDecDemuxGenerater", "-------------- generate VIDEO NOT END ----------------");
                return m1720a;
            }
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "================== AUDIO SEND END OF FILE ==========================" + m1720a.toString());
        }
        if (this.f3600D == null) {
            this.f3600D = mo460d;
            TXCLog.m2915d("BasicVideoDecDemuxGenerater", "first AUDIO pts:" + this.f3600D.m2329e());
        }
        this.f3600D = m1720a;
        return m1720a;
    }

    /* renamed from: a */
    public void m2160a(long j, long j2) {
        this.f3591g.getAndSet(j);
        this.f3592h.getAndSet(j2);
    }

    /* renamed from: b */
    public void m2155b(long j, long j2) {
        if (j == 0 && j2 == 0) {
            this.f3605I = 0;
            this.f3606J = false;
        } else {
            this.f3605I = 3;
        }
        this.f3603G = j;
        this.f3604H = j2;
        this.f3607K = 0L;
    }

    /* renamed from: a */
    public void m2156a(List<Long> list) {
        Log.e("thumbnail", "setVideoGivenPtsList :" + list.size());
        this.f3614R.clear();
        this.f3614R.addAll(list);
    }

    /* renamed from: o */
    public boolean m2135o() {
        return this.f3634v.get();
    }

    /* renamed from: p */
    public void mo2042p() {
        if (this.f3636x.get() == 1) {
            TXCLog.m2914e("BasicVideoDecDemuxGenerater", "getNextVideoFrame, current state is init, ignore");
        } else {
            this.f3637y.sendEmptyMessage(102);
        }
    }

    /* renamed from: b */
    public void m2150b(boolean z) {
        this.f3599C = z;
    }
}
