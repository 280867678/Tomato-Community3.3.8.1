package com.tencent.liteav.p121f;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.p118c.ThumbnailConfig;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p118c.VideoPreProcessConfig;
import com.tencent.liteav.p118c.VideoSourceConfig;
import com.tencent.liteav.p119d.AnimatedPaster;
import com.tencent.liteav.p119d.BeautyFilter;
import com.tencent.liteav.p119d.ComStaticFilter;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p119d.WaterMark;
import com.tencent.liteav.p120e.IVideoProcessorListener;
import com.tencent.liteav.p120e.TXIThumbnailListener;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p125j.BitmapUtil;
import com.tencent.liteav.p125j.TestUtils;
import com.tencent.liteav.p125j.TimeProvider;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.f.k */
/* loaded from: classes3.dex */
public class VideoPreprocessChain {

    /* renamed from: a */
    private Context f3981a;

    /* renamed from: b */
    private VideoPreProcessConfig f3982b;

    /* renamed from: c */
    private TXCVideoPreprocessor f3983c;

    /* renamed from: d */
    private TXIThumbnailListener f3984d;

    /* renamed from: e */
    private IVideoProcessorListener f3985e;

    /* renamed from: f */
    private Resolution f3986f;

    /* renamed from: g */
    private TXScaleFilter f3987g;

    /* renamed from: h */
    private VideoScaleFilter f3988h;

    /* renamed from: i */
    private MotionFilterChain f3989i;

    /* renamed from: j */
    private SubtitleFilterChain f3990j;

    /* renamed from: k */
    private PasterFilterChain f3991k;

    /* renamed from: l */
    private AnimatedPasterFilterChain f3992l;

    /* renamed from: m */
    private TailWaterMarkChain f3993m;

    /* renamed from: n */
    private ArrayList<TXCVideoPreprocessor.C3397d> f3994n;

    /* renamed from: o */
    private Frame f3995o;

    /* renamed from: p */
    private boolean f3996p;

    /* renamed from: q */
    private int f3997q;

    /* renamed from: r */
    private VideoScaleFilter f3998r;

    /* renamed from: s */
    private boolean f3999s = false;

    public VideoPreprocessChain(Context context) {
        this.f3981a = context;
    }

    /* renamed from: a */
    public void m1808a() {
        this.f3982b = VideoPreProcessConfig.m2427a();
        this.f3983c = new TXCVideoPreprocessor(this.f3981a, true);
        this.f3989i = new MotionFilterChain(this.f3981a);
        this.f3990j = SubtitleFilterChain.m1841a();
        this.f3991k = PasterFilterChain.m1856a();
        this.f3992l = AnimatedPasterFilterChain.m1935a();
        this.f3993m = TailWaterMarkChain.m1822a();
    }

    /* renamed from: b */
    public void m1799b() {
        if (!ThumbnailConfig.m2474a().m2464e()) {
            this.f3987g = new TXScaleFilter(false);
            this.f3987g.m1834a();
        }
        this.f3988h = new VideoScaleFilter(false);
        this.f3988h.m1783a();
        this.f3998r = new VideoScaleFilter(true);
        this.f3998r.m1783a();
    }

    /* renamed from: c */
    public void m1795c() {
        TXScaleFilter tXScaleFilter = this.f3987g;
        if (tXScaleFilter != null) {
            tXScaleFilter.m1827b();
            this.f3987g = null;
        }
        VideoScaleFilter videoScaleFilter = this.f3988h;
        if (videoScaleFilter != null) {
            videoScaleFilter.m1776b();
            this.f3988h = null;
        }
        VideoScaleFilter videoScaleFilter2 = this.f3998r;
        if (videoScaleFilter2 != null) {
            videoScaleFilter2.m1776b();
            this.f3998r = null;
        }
    }

    /* renamed from: d */
    public void m1792d() {
        MotionFilterChain motionFilterChain = this.f3989i;
        if (motionFilterChain != null) {
            motionFilterChain.m1874a();
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3983c;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2633a();
            this.f3983c = null;
        }
        ArrayList<TXCVideoPreprocessor.C3397d> arrayList = this.f3994n;
        if (arrayList != null) {
            arrayList.clear();
        }
        this.f3995o = null;
    }

    /* renamed from: a */
    public void m1804a(Resolution resolution) {
        this.f3986f = resolution;
    }

    /* renamed from: a */
    public void m1803a(IVideoProcessorListener iVideoProcessorListener) {
        this.f3985e = iVideoProcessorListener;
    }

    /* renamed from: a */
    public void m1802a(TXIThumbnailListener tXIThumbnailListener) {
        this.f3984d = tXIThumbnailListener;
    }

    /* renamed from: a */
    public void m1800a(float[] fArr) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3983c;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2613a(fArr);
        }
    }

    /* renamed from: a */
    public void m1801a(boolean z) {
        this.f3996p = z;
        if (z) {
            m1807a(this.f3997q, this.f3995o);
        }
    }

    /* renamed from: a */
    public void m1807a(int i, Frame frame) {
        int i2;
        if (this.f3983c == null || frame == null) {
            return;
        }
        if (this.f3999s) {
            int m1794c = m1794c(i, frame);
            Frame m1797b = m1797b(frame);
            m1788e(m1794c, m1797b);
            this.f3995o = m1797b;
            this.f3997q = i;
            return;
        }
        this.f3994n = new ArrayList<>();
        if (VideoSourceConfig.m2416a().m2411d() == 1) {
            int m1794c2 = m1794c(i, frame);
            frame = m1797b(frame);
            i2 = m1794c2;
        } else {
            i2 = i;
        }
        this.f3992l.m1881c(frame);
        this.f3991k.m1881c(frame);
        this.f3990j.m1881c(frame);
        m1789e();
        m1784g();
        m1786f();
        if (!this.f3996p) {
            m1787e(frame);
            m1790d(frame);
            m1793c(frame);
        }
        m1805a(frame);
        this.f3983c.m2630a(0);
        this.f3983c.m2616a(this.f3994n);
        this.f3983c.m2611b(frame.m2306s());
        int m1791d = m1791d(this.f3983c.m2627a(i2, frame.m2313m(), frame.m2311n(), 0, 0, 0), frame);
        IVideoProcessorListener iVideoProcessorListener = this.f3985e;
        if (iVideoProcessorListener != null) {
            m1791d = iVideoProcessorListener.mo1487b(m1791d, frame);
        }
        int m1798b = m1798b(m1791d, frame);
        IVideoProcessorListener iVideoProcessorListener2 = this.f3985e;
        if (iVideoProcessorListener2 != null) {
            iVideoProcessorListener2.mo1488a(m1798b, frame);
        }
        m1785f(m1798b, frame);
        this.f3995o = frame;
        this.f3997q = i;
    }

    /* renamed from: a */
    private void m1805a(Frame frame) {
        List<TXCVideoEditConstants.C3520j> m1813h;
        if (!this.f3993m.m1819b() || (m1813h = this.f3993m.m1813h()) == null || m1813h.size() == 0) {
            return;
        }
        long m1427a = TimeProvider.m1427a(frame) / 1000;
        for (TXCVideoEditConstants.C3520j c3520j : m1813h) {
            long j = c3520j.f4391c;
            if (m1427a <= j) {
                return;
            }
            if (m1427a > j && m1427a <= c3520j.f4392d) {
                this.f3994n.add(m1806a(c3520j.f4389a, c3520j.f4390b));
            }
        }
    }

    /* renamed from: b */
    private Frame m1797b(Frame frame) {
        int m2323h = (360 - frame.m2323h()) - VideoPreProcessConfig.m2427a().m2419e();
        if (m2323h == 90 || m2323h == 270) {
            int m2311n = frame.m2311n();
            frame.m2316k(frame.m2313m());
            frame.m2318j(m2311n);
            frame.m2328e(0);
        }
        return frame;
    }

    /* renamed from: b */
    private int m1798b(int i, Frame frame) {
        if (this.f3988h == null || frame.m2313m() == 0 || frame.m2311n() == 0) {
            return i;
        }
        this.f3988h.m1782a(VideoOutputConfig.m2457a().f3381s);
        this.f3988h.m1774b(frame.m2313m(), frame.m2311n());
        VideoScaleFilter videoScaleFilter = this.f3988h;
        Resolution resolution = this.f3986f;
        videoScaleFilter.m1781a(resolution.f3467a, resolution.f3468b);
        return this.f3988h.m1770d(i);
    }

    /* renamed from: c */
    private int m1794c(int i, Frame frame) {
        if (this.f3998r == null || frame.m2313m() == 0 || frame.m2311n() == 0) {
            return i;
        }
        this.f3998r.m1782a(VideoOutputConfig.m2457a().f3381s);
        int m2323h = (360 - frame.m2323h()) - VideoPreProcessConfig.m2427a().m2419e();
        this.f3998r.m1775b(m2323h);
        this.f3998r.m1774b(frame.m2313m(), frame.m2311n());
        if (m2323h == 90 || m2323h == 270) {
            this.f3998r.m1781a(frame.m2311n(), frame.m2313m());
        } else {
            this.f3998r.m1781a(frame.m2313m(), frame.m2311n());
        }
        return this.f3998r.m1770d(i);
    }

    /* renamed from: d */
    private int m1791d(int i, Frame frame) {
        MotionFilterChain motionFilterChain = this.f3989i;
        if (motionFilterChain != null) {
            motionFilterChain.m1871a(frame);
            return this.f3989i.m1870a(frame, i);
        }
        return i;
    }

    /* renamed from: c */
    private void m1793c(Frame frame) {
        Bitmap decodeFile;
        List<AnimatedPaster> m1930b = this.f3992l.m1930b();
        if (m1930b == null || m1930b.size() == 0) {
            this.f3992l.m1884a(this.f3986f);
            this.f3992l.m1934a(frame);
            m1930b = this.f3992l.m1930b();
        }
        for (AnimatedPaster animatedPaster : m1930b) {
            long m2329e = frame.m2329e() / 1000;
            if (m2329e > animatedPaster.f3451c && m2329e <= animatedPaster.f3452d && (decodeFile = BitmapFactory.decodeFile(animatedPaster.f3449a)) != null) {
                float f = animatedPaster.f3453e;
                if (f == 0.0f) {
                    this.f3994n.add(m1806a(decodeFile, animatedPaster.f3450b));
                } else {
                    this.f3994n.add(m1806a(BitmapUtil.m1447a(f, decodeFile), animatedPaster.f3450b));
                }
            }
        }
    }

    /* renamed from: d */
    private void m1790d(Frame frame) {
        List<TXCVideoEditConstants.C3515e> m1852b = this.f3991k.m1852b();
        if (m1852b == null || m1852b.size() == 0) {
            this.f3991k.m1884a(this.f3986f);
            this.f3991k.m1855a(frame);
            m1852b = this.f3991k.m1852b();
        }
        for (TXCVideoEditConstants.C3515e c3515e : m1852b) {
            long m2329e = frame.m2329e() / 1000;
            if (m2329e >= c3515e.f4376c && m2329e <= c3515e.f4377d) {
                this.f3994n.add(m1806a(c3515e.f4374a, c3515e.f4375b));
            }
        }
    }

    /* renamed from: e */
    private void m1787e(Frame frame) {
        List<TXCVideoEditConstants.C3520j> m1837b = this.f3990j.m1837b();
        if (m1837b == null || m1837b.size() == 0) {
            this.f3990j.m1884a(this.f3986f);
            this.f3990j.m1840a(frame);
            m1837b = this.f3990j.m1837b();
        }
        for (TXCVideoEditConstants.C3520j c3520j : m1837b) {
            long m2329e = frame.m2329e() / 1000;
            if (m2329e >= c3520j.f4391c && m2329e <= c3520j.f4392d) {
                this.f3994n.add(m1806a(c3520j.f4389a, c3520j.f4390b));
            }
        }
    }

    /* renamed from: e */
    private void m1788e(int i, Frame frame) {
        if (this.f3984d == null) {
            return;
        }
        ThumbnailConfig m2474a = ThumbnailConfig.m2474a();
        if (m2474a.m2464e()) {
            return;
        }
        if (frame.m2309p()) {
            do {
                int m2461h = m2474a.m2461h();
                Frame frame2 = this.f3995o;
                if (frame2 != null) {
                    long m2329e = frame2.m2329e();
                    Resolution m2465d = m2474a.m2465d();
                    TXScaleFilter tXScaleFilter = this.f3987g;
                    if (tXScaleFilter != null) {
                        tXScaleFilter.m1825b(this.f3995o.m2313m(), this.f3995o.m2311n());
                        this.f3987g.m1832a(m2465d.f3467a, m2465d.f3468b);
                        Bitmap m1430a = TestUtils.m1430a(this.f3987g.m1826b(i), m2465d.f3467a, m2465d.f3468b);
                        TXIThumbnailListener tXIThumbnailListener = this.f3984d;
                        if (tXIThumbnailListener != null) {
                            tXIThumbnailListener.mo2046a(m2461h, m2329e, m1430a);
                        }
                    }
                }
            } while (!m2474a.m2464e());
            return;
        }
        int m2461h2 = m2474a.m2461h();
        long m2462g = m2474a.m2462g();
        Resolution m2465d2 = m2474a.m2465d();
        TXScaleFilter tXScaleFilter2 = this.f3987g;
        if (tXScaleFilter2 == null) {
            return;
        }
        tXScaleFilter2.m1825b(frame.m2313m(), frame.m2311n());
        this.f3987g.m1832a(m2465d2.f3467a, m2465d2.f3468b);
        this.f3984d.mo2046a(m2461h2, m2462g, TestUtils.m1430a(this.f3987g.m1826b(i), m2465d2.f3467a, m2465d2.f3468b));
    }

    /* renamed from: f */
    private void m1785f(int i, Frame frame) {
        if (this.f3984d == null) {
            return;
        }
        ThumbnailConfig m2474a = ThumbnailConfig.m2474a();
        if (m2474a.m2464e()) {
            return;
        }
        if (frame.m2309p()) {
            do {
                int m2461h = m2474a.m2461h();
                m2474a.m2462g();
                Frame frame2 = this.f3995o;
                if (frame2 != null) {
                    long m2329e = frame2.m2329e();
                    Resolution m2465d = m2474a.m2465d();
                    TXScaleFilter tXScaleFilter = this.f3987g;
                    if (tXScaleFilter != null) {
                        tXScaleFilter.m1825b(this.f3995o.m2313m(), this.f3995o.m2311n());
                        this.f3987g.m1832a(m2465d.f3467a, m2465d.f3468b);
                        Bitmap m1430a = TestUtils.m1430a(this.f3987g.m1826b(i), m2465d.f3467a, m2465d.f3468b);
                        TXIThumbnailListener tXIThumbnailListener = this.f3984d;
                        if (tXIThumbnailListener != null) {
                            tXIThumbnailListener.mo2046a(m2461h, m2329e, m1430a);
                        }
                    }
                }
            } while (!m2474a.m2464e());
            return;
        }
        long m2329e2 = frame.m2329e();
        if (!VideoOutputConfig.m2457a().f3380r && !m2474a.m2458k() && m2329e2 < m2474a.m2463f()) {
            return;
        }
        int m2461h2 = m2474a.m2461h();
        long m2462g = m2474a.m2462g();
        Resolution m2465d2 = m2474a.m2465d();
        TXScaleFilter tXScaleFilter2 = this.f3987g;
        if (tXScaleFilter2 == null) {
            return;
        }
        tXScaleFilter2.m1825b(frame.m2313m(), frame.m2311n());
        this.f3987g.m1832a(m2465d2.f3467a, m2465d2.f3468b);
        this.f3984d.mo2046a(m2461h2, m2462g, TestUtils.m1430a(this.f3987g.m1826b(i), m2465d2.f3467a, m2465d2.f3468b));
    }

    /* renamed from: e */
    private void m1789e() {
        BeautyFilter m2421c = this.f3982b.m2421c();
        if (m2421c == null || !m2421c.m2355a()) {
            return;
        }
        this.f3983c.m2608c(m2421c.f3457a);
        this.f3983c.m2607d(m2421c.f3458b);
    }

    /* renamed from: f */
    private void m1786f() {
        ComStaticFilter m2420d = this.f3982b.m2420d();
        if (m2420d != null) {
            float m2349d = m2420d.m2349d();
            Bitmap m2348e = m2420d.m2348e();
            Bitmap m2347f = m2420d.m2347f();
            this.f3983c.m2631a(m2349d, m2348e, m2420d.m2352b(), m2347f, m2420d.m2350c());
        }
    }

    /* renamed from: g */
    private void m1784g() {
        WaterMark m2422b = this.f3982b.m2422b();
        if (m2422b != null) {
            this.f3994n.add(m1806a(m2422b.m2296c(), m2422b.m2295d()));
        }
    }

    /* renamed from: a */
    private TXCVideoPreprocessor.C3397d m1806a(Bitmap bitmap, TXCVideoEditConstants.C3517g c3517g) {
        TXCVideoPreprocessor.C3397d c3397d = new TXCVideoPreprocessor.C3397d();
        c3397d.f3256a = bitmap;
        c3397d.f3257b = c3517g.f4380a;
        c3397d.f3258c = c3517g.f4381b;
        c3397d.f3259d = c3517g.f4382c;
        return c3397d;
    }

    /* renamed from: b */
    public void m1796b(boolean z) {
        this.f3999s = z;
    }
}
