package com.tencent.liteav.p104b;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.util.LongSparseArray;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p104b.TXCombine;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p122g.MediaExtractorWrapper;
import com.tencent.liteav.p122g.TXAudioDecoderWrapper;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(16)
/* renamed from: com.tencent.liteav.b.h */
/* loaded from: classes3.dex */
public class TXReaderLone {

    /* renamed from: f */
    private Surface f2277f;

    /* renamed from: g */
    private TXCombine.AbstractC3312a f2278g;

    /* renamed from: i */
    private TXCombineVideoDecoder f2280i;

    /* renamed from: j */
    private TXAudioDecoderWrapper f2281j;

    /* renamed from: k */
    private Frame f2282k;

    /* renamed from: l */
    private Frame f2283l;

    /* renamed from: m */
    private ArrayBlockingQueue<Frame> f2284m;

    /* renamed from: n */
    private ArrayBlockingQueue<Frame> f2285n;

    /* renamed from: h */
    private MediaExtractorWrapper f2279h = new MediaExtractorWrapper();

    /* renamed from: a */
    private LongSparseArray<Frame> f2272a = new LongSparseArray<>();

    /* renamed from: b */
    private LongSparseArray<Frame> f2273b = new LongSparseArray<>();

    /* renamed from: c */
    private AtomicBoolean f2274c = new AtomicBoolean(false);

    /* renamed from: d */
    private AtomicBoolean f2275d = new AtomicBoolean(false);

    /* renamed from: e */
    private LinkedList<Frame> f2276e = new LinkedList<>();

    public TXReaderLone() {
        this.f2276e.clear();
    }

    /* renamed from: a */
    public int m3226a(String str) {
        int i;
        try {
            i = this.f2279h.m1744a(str);
        } catch (IOException e) {
            e.printStackTrace();
            i = 0;
        }
        if (i < 0) {
            return i;
        }
        return 0;
    }

    /* renamed from: a */
    public void m3228a(Surface surface) {
        this.f2277f = surface;
    }

    /* renamed from: a */
    public MediaFormat m3229a() {
        return this.f2279h.m1727m();
    }

    /* renamed from: b */
    public int m3224b() {
        return this.f2279h.m1743b();
    }

    /* renamed from: c */
    public int m3222c() {
        return this.f2279h.m1740c();
    }

    /* renamed from: d */
    public int m3221d() {
        return this.f2279h.m1735e();
    }

    /* renamed from: e */
    public int m3220e() {
        MediaFormat m1727m = this.f2279h.m1727m();
        if (m1727m.containsKey("sample-rate")) {
            return m1727m.getInteger("sample-rate");
        }
        return 0;
    }

    /* renamed from: f */
    public int m3219f() {
        MediaFormat m1727m = this.f2279h.m1727m();
        if (m1727m.containsKey("max-input-size")) {
            return m1727m.getInteger("max-input-size");
        }
        return 0;
    }

    /* renamed from: a */
    public void m3227a(TXCombine.AbstractC3312a abstractC3312a) {
        this.f2278g = abstractC3312a;
    }

    /* renamed from: g */
    public void m3218g() {
        this.f2280i = new TXCombineVideoDecoder();
        this.f2281j = new TXAudioDecoderWrapper();
        MediaFormat m1727m = this.f2279h.m1727m();
        this.f2281j.mo468a(m1727m);
        this.f2281j.mo467a(m1727m, (Surface) null);
        this.f2281j.mo469a();
        this.f2280i.mo468a(this.f2279h.m1728l());
        this.f2280i.mo467a(this.f2279h.m1728l(), this.f2277f);
        this.f2280i.mo469a();
    }

    /* renamed from: h */
    public void m3217h() {
        TXAudioDecoderWrapper tXAudioDecoderWrapper = this.f2281j;
        if (tXAudioDecoderWrapper != null) {
            tXAudioDecoderWrapper.mo463b();
        }
        TXCombineVideoDecoder tXCombineVideoDecoder = this.f2280i;
        if (tXCombineVideoDecoder != null) {
            tXCombineVideoDecoder.mo463b();
        }
        LinkedList<Frame> linkedList = this.f2276e;
        if (linkedList != null) {
            linkedList.clear();
        }
        LongSparseArray<Frame> longSparseArray = this.f2273b;
        if (longSparseArray != null) {
            longSparseArray.clear();
        }
        LongSparseArray<Frame> longSparseArray2 = this.f2272a;
        if (longSparseArray2 != null) {
            longSparseArray2.clear();
        }
        this.f2279h.m1725o();
        this.f2274c.compareAndSet(true, false);
        this.f2275d.compareAndSet(true, false);
    }

    /* renamed from: i */
    public void m3216i() throws InterruptedException {
        m3215j();
        m3214k();
        m3213l();
        m3212m();
    }

    /* renamed from: j */
    private void m3215j() throws InterruptedException {
        if (this.f2274c.get()) {
            TXCLog.m2915d("TXReaderLone", "mReadVideoEOF, ignore");
            return;
        }
        Frame mo461c = this.f2280i.mo461c();
        if (mo461c == null) {
            return;
        }
        Frame m1745a = this.f2279h.m1745a(mo461c);
        if (this.f2279h.m1738c(m1745a)) {
            this.f2274c.compareAndSet(false, true);
            TXCLog.m2913i("TXReaderLone", "==TXReaderLone Read Video End===");
        }
        this.f2273b.put(m1745a.m2329e(), m1745a);
        this.f2280i.mo466a(m1745a);
    }

    /* renamed from: k */
    private void m3214k() throws InterruptedException {
        Frame mo461c;
        if (!this.f2275d.get() && (mo461c = this.f2281j.mo461c()) != null) {
            Frame m1741b = this.f2279h.m1741b(mo461c);
            if (this.f2279h.m1736d(m1741b)) {
                this.f2275d.compareAndSet(false, true);
                TXCLog.m2913i("TXReaderLone", "==TXReaderLone Read Audio End===");
            }
            this.f2272a.put(m1741b.m2329e(), m1741b);
            this.f2281j.mo466a(m1741b);
        }
    }

    /* renamed from: l */
    private void m3213l() {
        if (this.f2276e.size() == 0) {
            ArrayBlockingQueue<Frame> arrayBlockingQueue = this.f2284m;
            if (arrayBlockingQueue != null && arrayBlockingQueue.size() > 0) {
                TXCLog.m2915d("TXReaderLone", "decodeVideoFrame, ignore because mVideoBlockingQueue.size() = " + this.f2284m.size());
                return;
            }
            Frame mo460d = this.f2280i.mo460d();
            if (mo460d == null || mo460d.m2310o() == null) {
                return;
            }
            if (this.f2282k == null) {
                this.f2282k = mo460d;
            }
            Frame frame = this.f2273b.get(mo460d.m2329e());
            if (frame != null) {
                mo460d = this.f2280i.m3230a(frame, mo460d);
            }
            if ((mo460d.m2310o().flags & 4) != 0) {
                TXCLog.m2913i("TXReaderLone", "==TXReaderLone Decode Video End===");
            }
            this.f2276e.add(mo460d);
        }
        if (this.f2276e.size() <= 0) {
            return;
        }
        Frame frame2 = this.f2276e.get(0);
        if (this.f2282k == null) {
            this.f2282k = frame2;
        }
        TXCombine.AbstractC3312a abstractC3312a = this.f2278g;
        if (abstractC3312a != null) {
            abstractC3312a.mo3278b(frame2);
        }
        if (!this.f2276e.isEmpty() && this.f2276e.size() > 0) {
            this.f2276e.remove(0);
        }
        this.f2282k = frame2;
    }

    /* renamed from: m */
    private void m3212m() {
        ArrayBlockingQueue<Frame> arrayBlockingQueue = this.f2285n;
        if (arrayBlockingQueue != null && arrayBlockingQueue.size() > 9) {
            TXCLog.m2915d("TXReaderLone", "decodeAudioFrame, ignore because mAudioBlockingQueue size = " + this.f2285n.size());
            return;
        }
        Frame mo460d = this.f2281j.mo460d();
        if (mo460d == null || mo460d.m2310o() == null) {
            return;
        }
        Frame frame = this.f2272a.get(mo460d.m2329e());
        Frame m1720a = frame != null ? this.f2281j.m1720a(frame, mo460d) : mo460d;
        if (m1720a == null) {
            TXCLog.m2914e("TXReaderLone", "decodeAudioFrame, fixFrame is null, sampleTime = " + mo460d.m2329e());
            return;
        }
        if ((m1720a.m2310o().flags & 4) != 0) {
            TXCLog.m2913i("TXReaderLone", "==TXReaderLone Decode Audio End===");
        }
        if (this.f2283l == null) {
            this.f2283l = mo460d;
        }
        TXCombine.AbstractC3312a abstractC3312a = this.f2278g;
        if (abstractC3312a != null) {
            abstractC3312a.mo3279a(m1720a);
        }
        this.f2283l = m1720a;
    }

    /* renamed from: a */
    public void m3225a(ArrayBlockingQueue<Frame> arrayBlockingQueue) {
        this.f2284m = arrayBlockingQueue;
    }

    /* renamed from: b */
    public void m3223b(ArrayBlockingQueue<Frame> arrayBlockingQueue) {
        this.f2285n = arrayBlockingQueue;
    }
}
