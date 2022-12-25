package com.tencent.liteav.p122g;

import android.content.Context;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p120e.IVideoProcessorListener;
import com.tencent.liteav.p121f.VideoScaleFilter;

/* renamed from: com.tencent.liteav.g.q */
/* loaded from: classes3.dex */
public class VideoJoinPreprocessChain {

    /* renamed from: a */
    private Context f4240a;

    /* renamed from: b */
    private TXCVideoPreprocessor f4241b;

    /* renamed from: c */
    private VideoScaleFilter f4242c;

    /* renamed from: d */
    private VideoScaleFilter f4243d;

    /* renamed from: e */
    private IVideoProcessorListener f4244e;

    /* renamed from: f */
    private Resolution f4245f;

    public VideoJoinPreprocessChain(Context context) {
        this.f4240a = context;
    }

    /* renamed from: a */
    public void m1526a() {
        this.f4241b = new TXCVideoPreprocessor(this.f4240a, true);
    }

    /* renamed from: b */
    public void m1520b() {
        this.f4242c = new VideoScaleFilter(false);
        this.f4242c.m1783a();
        this.f4243d = new VideoScaleFilter(true);
        this.f4243d.m1783a();
    }

    /* renamed from: c */
    public void m1518c() {
        VideoScaleFilter videoScaleFilter = this.f4242c;
        if (videoScaleFilter != null) {
            videoScaleFilter.m1776b();
            this.f4242c = null;
        }
        VideoScaleFilter videoScaleFilter2 = this.f4243d;
        if (videoScaleFilter2 != null) {
            videoScaleFilter2.m1776b();
            this.f4243d = null;
        }
    }

    /* renamed from: d */
    public void m1516d() {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f4241b;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2633a();
            this.f4241b = null;
        }
    }

    /* renamed from: a */
    public void m1522a(IVideoProcessorListener iVideoProcessorListener) {
        this.f4244e = iVideoProcessorListener;
    }

    /* renamed from: a */
    public void m1523a(Resolution resolution) {
        this.f4245f = resolution;
    }

    /* renamed from: a */
    public void m1521a(float[] fArr) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f4241b;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2613a(fArr);
        }
    }

    /* renamed from: a */
    public void m1525a(int i, Frame frame) {
        if (this.f4241b == null || frame == null) {
            return;
        }
        int m1517c = m1517c(i, frame);
        Frame m1524a = m1524a(frame);
        this.f4241b.m2630a(0);
        int m2627a = this.f4241b.m2627a(m1517c, m1524a.m2313m(), m1524a.m2311n(), 0, 0, 0);
        IVideoProcessorListener iVideoProcessorListener = this.f4244e;
        if (iVideoProcessorListener != null) {
            m2627a = iVideoProcessorListener.mo1487b(m2627a, m1524a);
        }
        int m1519b = m1519b(m2627a, m1524a);
        IVideoProcessorListener iVideoProcessorListener2 = this.f4244e;
        if (iVideoProcessorListener2 == null) {
            return;
        }
        iVideoProcessorListener2.mo1488a(m1519b, m1524a);
    }

    /* renamed from: a */
    private Frame m1524a(Frame frame) {
        int m2323h = 360 - frame.m2323h();
        if (m2323h == 90 || m2323h == 270) {
            int m2311n = frame.m2311n();
            frame.m2316k(frame.m2313m());
            frame.m2318j(m2311n);
        }
        return frame;
    }

    /* renamed from: b */
    private int m1519b(int i, Frame frame) {
        if (this.f4242c == null || frame.m2313m() == 0 || frame.m2311n() == 0) {
            return i;
        }
        this.f4242c.m1782a(VideoOutputListConfig.m1481r().f4272t);
        this.f4242c.m1774b(frame.m2313m(), frame.m2311n());
        VideoScaleFilter videoScaleFilter = this.f4242c;
        Resolution resolution = this.f4245f;
        videoScaleFilter.m1781a(resolution.f3467a, resolution.f3468b);
        return this.f4242c.m1770d(i);
    }

    /* renamed from: c */
    private int m1517c(int i, Frame frame) {
        if (this.f4243d == null || frame.m2313m() == 0 || frame.m2311n() == 0) {
            return i;
        }
        this.f4243d.m1782a(VideoOutputListConfig.m1481r().f4272t);
        int m2323h = 360 - frame.m2323h();
        this.f4243d.m1775b(m2323h);
        this.f4243d.m1774b(frame.m2313m(), frame.m2311n());
        if (m2323h == 90 || m2323h == 270) {
            this.f4243d.m1781a(frame.m2311n(), frame.m2313m());
        } else {
            this.f4243d.m1781a(frame.m2313m(), frame.m2311n());
        }
        return this.f4243d.m1770d(i);
    }
}
