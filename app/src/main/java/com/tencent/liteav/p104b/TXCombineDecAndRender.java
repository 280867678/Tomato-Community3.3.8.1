package com.tencent.liteav.p104b;

import android.content.Context;
import android.opengl.EGLContext;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.p104b.TXCombine;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p120e.IVideoRenderListener;
import com.tencent.liteav.p120e.OnContextListener;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.ugc.TXRecordCommon;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.tencent.liteav.b.c */
/* loaded from: classes3.dex */
public class TXCombineDecAndRender {

    /* renamed from: b */
    private Context f2190b;

    /* renamed from: j */
    private C3320a f2198j;

    /* renamed from: k */
    private boolean f2199k;

    /* renamed from: l */
    private boolean f2200l;

    /* renamed from: m */
    private boolean f2201m;

    /* renamed from: n */
    private boolean f2202n;

    /* renamed from: o */
    private TXCVideoPreprocessor f2203o;

    /* renamed from: p */
    private TXCVideoPreprocessor f2204p;

    /* renamed from: q */
    private TXCombineProcess f2205q;

    /* renamed from: s */
    private int f2207s;

    /* renamed from: t */
    private int f2208t;

    /* renamed from: w */
    private Frame f2211w;

    /* renamed from: x */
    private boolean f2212x;

    /* renamed from: y */
    private TXCombine.AbstractC3314c f2213y;

    /* renamed from: a */
    private final String f2189a = "TXCombineDecAndRender";

    /* renamed from: v */
    private int f2210v = -1;

    /* renamed from: z */
    private final IVideoRenderListener f2214z = new IVideoRenderListener() { // from class: com.tencent.liteav.b.c.1
        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1944a(int i, int i2) {
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1941a(boolean z) {
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1942a(Surface surface) {
            TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback onSurfaceTextureAvailable");
            TXCombineDecAndRender.this.f2191c.m3228a(surface);
            TXCombineDecAndRender tXCombineDecAndRender = TXCombineDecAndRender.this;
            tXCombineDecAndRender.f2203o = new TXCVideoPreprocessor(tXCombineDecAndRender.f2190b, true);
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: b */
        public void mo1940b(Surface surface) {
            TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback onSurfaceTextureDestroy");
            if (TXCombineDecAndRender.this.f2203o != null) {
                TXCombineDecAndRender.this.f2203o.m2633a();
                TXCombineDecAndRender.this.f2203o = null;
            }
            if (TXCombineDecAndRender.this.f2205q != null) {
                TXCombineDecAndRender.this.f2205q.m3251a();
            }
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public int mo1943a(int i, float[] fArr, Frame frame) {
            if (!TXCombineDecAndRender.this.f2200l) {
                if (!TXCombineDecAndRender.this.m3307a(frame, false)) {
                    if (TXCombineDecAndRender.this.f2211w != null) {
                        TXCombineDecAndRender.this.f2203o.m2613a(fArr);
                        int m2627a = TXCombineDecAndRender.this.f2203o.m2627a(i, frame.m2313m(), frame.m2311n(), frame.m2323h(), 4, 0);
                        TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback onTextureProcess, mCurRenderFrame is second pts = " + TXCombineDecAndRender.this.f2211w.m2329e() + ", process frame pts = " + frame.m2329e());
                        TXCombineDecAndRender tXCombineDecAndRender = TXCombineDecAndRender.this;
                        tXCombineDecAndRender.m3315a(m2627a, frame, tXCombineDecAndRender.f2210v, TXCombineDecAndRender.this.f2211w);
                        return 0;
                    }
                    TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback onTextureProcess, mCurRenderFrame is null, frame pts = " + frame.m2329e());
                    TXCombineDecAndRender.this.f2211w = frame;
                    TXCombineDecAndRender.this.f2203o.m2613a(fArr);
                    TXCombineDecAndRender.this.f2210v = TXCombineDecAndRender.this.f2203o.m2627a(i, frame.m2313m(), frame.m2311n(), frame.m2323h(), 4, 0);
                    return 0;
                }
                TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback onTextureProcess, end frame");
                return 0;
            }
            TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback mDecodeVideoEnd, ignore");
            return 0;
        }
    };

    /* renamed from: A */
    private final IVideoRenderListener f2185A = new IVideoRenderListener() { // from class: com.tencent.liteav.b.c.2
        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1944a(int i, int i2) {
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1941a(boolean z) {
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public void mo1942a(Surface surface) {
            TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback2 onSurfaceTextureAvailable");
            TXCombineDecAndRender.this.f2192d.m3228a(surface);
            TXCombineDecAndRender tXCombineDecAndRender = TXCombineDecAndRender.this;
            tXCombineDecAndRender.f2204p = new TXCVideoPreprocessor(tXCombineDecAndRender.f2190b, true);
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: b */
        public void mo1940b(Surface surface) {
            TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback2 onSurfaceTextureDestroy");
            if (TXCombineDecAndRender.this.f2204p != null) {
                TXCombineDecAndRender.this.f2204p.m2633a();
                TXCombineDecAndRender.this.f2204p = null;
            }
        }

        @Override // com.tencent.liteav.p120e.IVideoRenderListener
        /* renamed from: a */
        public int mo1943a(int i, float[] fArr, Frame frame) {
            if (!TXCombineDecAndRender.this.f2200l) {
                if (!TXCombineDecAndRender.this.m3307a(frame, false)) {
                    if (TXCombineDecAndRender.this.f2211w != null) {
                        TXCombineDecAndRender.this.f2204p.m2613a(fArr);
                        int m2627a = TXCombineDecAndRender.this.f2204p.m2627a(i, frame.m2313m(), frame.m2311n(), frame.m2323h(), 4, 0);
                        TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback2 onTextureProcess, mCurRenderFrame is first pts = " + TXCombineDecAndRender.this.f2211w.m2329e() + ", process frame pts = " + frame.m2329e());
                        TXCombineDecAndRender tXCombineDecAndRender = TXCombineDecAndRender.this;
                        tXCombineDecAndRender.m3315a(tXCombineDecAndRender.f2210v, TXCombineDecAndRender.this.f2211w, m2627a, frame);
                        return 0;
                    }
                    TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback2 onTextureProcess, mCurRenderFrame is null, frame pts = " + frame.m2329e());
                    TXCombineDecAndRender.this.f2211w = frame;
                    TXCombineDecAndRender.this.f2204p.m2613a(fArr);
                    TXCombineDecAndRender.this.f2210v = TXCombineDecAndRender.this.f2204p.m2627a(i, frame.m2313m(), frame.m2311n(), frame.m2323h(), 4, 0);
                    return 0;
                }
                TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback2 onTextureProcess, end frame");
                return 0;
            }
            TXCLog.m2913i("TXCombineDecAndRender", "mVideoRenderCallback2 mDecodeVideoEnd, ignore");
            return 0;
        }
    };

    /* renamed from: B */
    private OnContextListener f2186B = new OnContextListener() { // from class: com.tencent.liteav.b.c.3
        @Override // com.tencent.liteav.p120e.OnContextListener
        /* renamed from: a */
        public void mo1532a(EGLContext eGLContext) {
            TXCLog.m2915d("TXCombineDecAndRender", "OnContextListener onContext");
            if (TXCombineDecAndRender.this.f2213y != null) {
                TXCombineDecAndRender.this.f2213y.mo3236a(eGLContext);
            }
            TXCombineDecAndRender.this.f2191c.m3218g();
            TXCombineDecAndRender.this.f2191c.m3227a(TXCombineDecAndRender.this.f2187C);
            TXCombineDecAndRender.this.f2192d.m3218g();
            TXCombineDecAndRender.this.f2192d.m3227a(TXCombineDecAndRender.this.f2188D);
            TXCombineDecAndRender.this.f2198j.start();
        }
    };

    /* renamed from: C */
    private TXCombine.AbstractC3312a f2187C = new TXCombine.AbstractC3312a() { // from class: com.tencent.liteav.b.c.4
        @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3312a
        /* renamed from: b */
        public void mo3278b(Frame frame) {
            if (TXCombineDecAndRender.this.f2202n) {
                return;
            }
            TXCLog.m2913i("TXCombineDecAndRender", "Video1 frame put one:" + frame.m2329e() + ",VideoBlockingQueue size:" + TXCombineDecAndRender.this.f2194f.size());
            try {
                TXCombineDecAndRender.this.f2194f.put(frame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (TXCombineDecAndRender.this.f2193e == null) {
                return;
            }
            TXCombineDecAndRender.this.f2193e.m3208a(frame, 0);
        }

        @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3312a
        /* renamed from: a */
        public void mo3279a(Frame frame) {
            if (TXCombineDecAndRender.this.f2202n) {
                return;
            }
            if (TXCombineDecAndRender.this.f2195g.size() > 3) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            TXCLog.m2913i("TXCombineDecAndRender", "Audio1 frame put one:" + frame.m2329e() + ", flag = " + frame.m2327f() + ", AudioBlockingQueue size:" + TXCombineDecAndRender.this.f2195g.size());
            try {
                TXCombineDecAndRender.this.f2195g.put(frame);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            TXCombineDecAndRender.this.m3297e();
        }
    };

    /* renamed from: D */
    private TXCombine.AbstractC3312a f2188D = new TXCombine.AbstractC3312a() { // from class: com.tencent.liteav.b.c.5
        @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3312a
        /* renamed from: b */
        public void mo3278b(Frame frame) {
            if (TXCombineDecAndRender.this.f2202n) {
                return;
            }
            TXCLog.m2913i("TXCombineDecAndRender", "Video2 frame put one:" + frame.m2329e() + ", flag = " + frame.m2327f() + ",VideoBlockingQueue2 size:" + TXCombineDecAndRender.this.f2196h.size());
            try {
                TXCombineDecAndRender.this.f2196h.put(frame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (TXCombineDecAndRender.this.f2193e == null) {
                return;
            }
            TXCombineDecAndRender.this.f2193e.m3208a(frame, 1);
        }

        @Override // com.tencent.liteav.p104b.TXCombine.AbstractC3312a
        /* renamed from: a */
        public void mo3279a(Frame frame) {
            if (TXCombineDecAndRender.this.f2202n) {
                return;
            }
            if (TXCombineDecAndRender.this.f2197i.size() > 3) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            TXCLog.m2913i("TXCombineDecAndRender", "Audio2 frame put one:" + frame.m2329e() + ", flag = " + frame.m2327f() + ",AudioBlockingQueue2 size:" + TXCombineDecAndRender.this.f2197i.size());
            try {
                TXCombineDecAndRender.this.f2197i.put(frame);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            TXCombineDecAndRender.this.m3297e();
        }
    };

    /* renamed from: c */
    private TXReaderLone f2191c = new TXReaderLone();

    /* renamed from: d */
    private TXReaderLone f2192d = new TXReaderLone();

    /* renamed from: e */
    private VideoGLMultiGenerate f2193e = new VideoGLMultiGenerate(2);

    /* renamed from: r */
    private TXCombineAudioMixer f2206r = new TXCombineAudioMixer();

    /* renamed from: f */
    private ArrayBlockingQueue<Frame> f2194f = new ArrayBlockingQueue<>(1);

    /* renamed from: h */
    private ArrayBlockingQueue<Frame> f2196h = new ArrayBlockingQueue<>(1);

    /* renamed from: g */
    private ArrayBlockingQueue<Frame> f2195g = new ArrayBlockingQueue<>(10);

    /* renamed from: i */
    private ArrayBlockingQueue<Frame> f2197i = new ArrayBlockingQueue<>(10);

    /* renamed from: u */
    private LinkedBlockingQueue<Frame> f2209u = new LinkedBlockingQueue<>();

    public TXCombineDecAndRender(Context context) {
        this.f2190b = context;
        this.f2205q = new TXCombineProcess(this.f2190b);
        this.f2191c.m3225a(this.f2194f);
        this.f2192d.m3225a(this.f2196h);
        this.f2191c.m3223b(this.f2195g);
        this.f2192d.m3223b(this.f2197i);
    }

    /* renamed from: a */
    public void m3314a(TXCombine.AbstractC3314c abstractC3314c) {
        this.f2213y = abstractC3314c;
    }

    /* renamed from: a */
    public int m3306a(List<String> list) {
        if (list == null || list.size() < 2) {
            return -1;
        }
        this.f2191c.m3226a(list.get(0));
        boolean z = true;
        this.f2192d.m3226a(list.get(1));
        if (this.f2191c.m3221d() > this.f2192d.m3221d()) {
            z = false;
        }
        this.f2212x = z;
        this.f2206r.m3323a(this.f2191c.m3229a());
        this.f2206r.m3318b(this.f2192d.m3229a());
        this.f2206r.m3324a(m3316a());
        this.f2206r.m3325a();
        return 0;
    }

    /* renamed from: a */
    public void m3305a(List<TXCVideoEditConstants.C3511a> list, int i, int i2) {
        this.f2205q.m3249a(list, i, i2);
        this.f2207s = i;
        this.f2208t = i2;
    }

    /* renamed from: a */
    public int m3316a() {
        int m3220e = this.f2191c.m3220e();
        int m3220e2 = this.f2192d.m3220e();
        if (m3220e < m3220e2) {
            m3220e = m3220e2;
        }
        return m3220e > 0 ? m3220e : TXRecordCommon.AUDIO_SAMPLERATE_48000;
    }

    /* renamed from: b */
    public int m3304b() {
        int m3219f = this.f2191c.m3219f();
        int m3219f2 = this.f2192d.m3219f();
        if (m3219f < m3219f2) {
            m3219f = m3219f2;
        }
        return m3219f >= 0 ? m3219f : ConstantUtils.MAX_ITEM_NUM;
    }

    /* renamed from: c */
    public void m3301c() {
        Resolution resolution = new Resolution();
        Resolution resolution2 = new Resolution();
        resolution.f3467a = this.f2191c.m3224b();
        resolution.f3468b = this.f2191c.m3222c();
        resolution2.f3467a = this.f2192d.m3224b();
        resolution2.f3468b = this.f2192d.m3222c();
        this.f2193e.m3207a(resolution, 0);
        this.f2193e.m3207a(resolution2, 1);
        this.f2193e.m3206a(this.f2214z, 0);
        this.f2193e.m3206a(this.f2185A, 1);
        this.f2193e.m3205a(this.f2186B);
        this.f2193e.m3211a();
        this.f2202n = false;
        this.f2199k = false;
        this.f2200l = false;
        this.f2201m = false;
        this.f2198j = new C3320a();
    }

    /* renamed from: d */
    public void m3299d() {
        this.f2202n = true;
        C3320a c3320a = this.f2198j;
        if (c3320a != null && c3320a.isAlive()) {
            try {
                this.f2198j.join(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        TXReaderLone tXReaderLone = this.f2191c;
        if (tXReaderLone != null) {
            tXReaderLone.m3217h();
        }
        TXReaderLone tXReaderLone2 = this.f2192d;
        if (tXReaderLone2 != null) {
            tXReaderLone2.m3217h();
        }
        TXCombineAudioMixer tXCombineAudioMixer = this.f2206r;
        if (tXCombineAudioMixer != null) {
            tXCombineAudioMixer.m3319b();
        }
        VideoGLMultiGenerate videoGLMultiGenerate = this.f2193e;
        if (videoGLMultiGenerate != null) {
            videoGLMultiGenerate.m3204b();
        }
        this.f2195g.clear();
        this.f2197i.clear();
        this.f2211w = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m3315a(int i, Frame frame, int i2, Frame frame2) {
        if (this.f2212x) {
            if (frame2.m2329e() < frame.m2329e()) {
                TXCLog.m2913i("TXCombineDecAndRender", "prepareToCombine, mFirstFpsSmall true, secondFrame pts < first pts, drop second, current second queue size = " + this.f2196h.size());
                this.f2211w = frame;
                this.f2210v = i;
                this.f2196h.remove();
                return;
            }
            TXCLog.m2913i("TXCombineDecAndRender", "prepareToCombine, mFirstFpsSmall true, secondFrame pts >= first pts, to combine");
            int m3250a = this.f2205q.m3250a(i, i2, frame, frame2);
            TXCombine.AbstractC3314c abstractC3314c = this.f2213y;
            if (abstractC3314c != null) {
                abstractC3314c.mo3237a(m3250a, this.f2207s, this.f2208t, frame);
            }
            this.f2194f.remove();
            this.f2196h.remove();
            TXCLog.m2913i("TXCombineDecAndRender", "prepareToCombine, after combine, remain queue queue2 size = " + this.f2194f.size() + ", " + this.f2196h.size());
            this.f2210v = -1;
            this.f2211w = null;
        } else if (frame2.m2329e() > frame.m2329e()) {
            TXCLog.m2913i("TXCombineDecAndRender", "mFirstFpsSmall false, secondFrame pts > first pts, drop first, current first queue size = " + this.f2194f.size());
            this.f2211w = frame2;
            this.f2210v = i2;
            this.f2194f.remove();
        } else {
            TXCLog.m2913i("TXCombineDecAndRender", "mFirstFpsSmall false, secondFrame pts <= first pts, to combine");
            int m3250a2 = this.f2205q.m3250a(i, i2, frame, frame2);
            TXCombine.AbstractC3314c abstractC3314c2 = this.f2213y;
            if (abstractC3314c2 != null) {
                abstractC3314c2.mo3237a(m3250a2, this.f2207s, this.f2208t, frame);
            }
            this.f2194f.remove();
            this.f2196h.remove();
            this.f2210v = -1;
            this.f2211w = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TXCombineDecAndRender.java */
    /* renamed from: com.tencent.liteav.b.c$a */
    /* loaded from: classes3.dex */
    public class C3320a extends Thread {
        C3320a() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setName("DecodeThread");
            try {
                TXCLog.m2913i("TXCombineDecAndRender", "===DecodeThread Start===");
                while (!TXCombineDecAndRender.this.f2199k && !TXCombineDecAndRender.this.f2202n) {
                    TXCombineDecAndRender.this.f2191c.m3216i();
                    TXCombineDecAndRender.this.f2192d.m3216i();
                }
                TXCombineDecAndRender.this.f2194f.clear();
                TXCombineDecAndRender.this.f2196h.clear();
                TXCLog.m2913i("TXCombineDecAndRender", "===DecodeThread Exit===");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* renamed from: e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m3297e() {
        Frame frame;
        InterruptedException e;
        Frame frame2;
        TXCombine.AbstractC3314c abstractC3314c;
        if (this.f2195g.isEmpty()) {
            TXCLog.m2913i("TXCombineDecAndRender", "combineAudioFrame, mAudioBlockingQueue is empty, ignore");
            return;
        }
        Frame peek = this.f2195g.peek();
        if (m3307a(peek, true)) {
            TXCLog.m2913i("TXCombineDecAndRender", "combineAudioFrame, frame1 is end");
        } else if (this.f2197i.isEmpty()) {
            TXCLog.m2913i("TXCombineDecAndRender", "combineAudioFrame, mAudioBlockingQueue2 is empty, ignore");
        } else {
            Frame peek2 = this.f2197i.peek();
            if (m3307a(peek2, true)) {
                TXCLog.m2913i("TXCombineDecAndRender", "combineAudioFrame, frame2 is end");
                return;
            }
            try {
                frame = this.f2195g.take();
            } catch (InterruptedException e2) {
                frame = peek;
                e = e2;
            }
            try {
                frame2 = this.f2197i.take();
            } catch (InterruptedException e3) {
                e = e3;
                e.printStackTrace();
                frame2 = peek2;
                TXCLog.m2913i("TXCombineDecAndRender", "===combineAudioFrame===after take, size1:" + this.f2195g.size() + ",size2:" + this.f2197i.size());
                Frame m3322a = this.f2206r.m3322a(frame, frame2);
                abstractC3314c = this.f2213y;
                if (abstractC3314c != null) {
                }
            }
            TXCLog.m2913i("TXCombineDecAndRender", "===combineAudioFrame===after take, size1:" + this.f2195g.size() + ",size2:" + this.f2197i.size());
            Frame m3322a2 = this.f2206r.m3322a(frame, frame2);
            abstractC3314c = this.f2213y;
            if (abstractC3314c != null) {
                return;
            }
            abstractC3314c.mo3235a(m3322a2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public boolean m3307a(Frame frame, boolean z) {
        if (!frame.m2309p()) {
            return false;
        }
        if (this.f2213y != null) {
            if (z) {
                TXCLog.m2913i("TXCombineDecAndRender", "===judgeDecodeComplete=== audio end");
                this.f2201m = true;
                this.f2195g.clear();
                this.f2197i.clear();
                this.f2213y.mo3233c(frame);
            } else {
                TXCLog.m2913i("TXCombineDecAndRender", "===judgeDecodeComplete=== video end");
                this.f2200l = true;
                this.f2213y.mo3234b(frame);
            }
            if (this.f2201m && this.f2200l) {
                TXCLog.m2913i("TXCombineDecAndRender", "judgeDecodeComplete, video and audio both end");
                this.f2199k = true;
                m3299d();
            }
        }
        return true;
    }
}
