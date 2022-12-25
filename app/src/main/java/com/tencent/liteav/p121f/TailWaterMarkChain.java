package com.tencent.liteav.p121f;

import android.graphics.Bitmap;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p118c.ReverseConfig;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.TailWaterMark;
import com.tencent.liteav.p120e.TailWaterMarkListener;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p125j.BitmapUtil;
import com.tencent.liteav.p125j.TimeProvider;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.f.j */
/* loaded from: classes3.dex */
public class TailWaterMarkChain {

    /* renamed from: c */
    private static TailWaterMarkChain f3966c;

    /* renamed from: a */
    public Frame f3967a;

    /* renamed from: b */
    public Frame f3968b;

    /* renamed from: d */
    private TailWaterMark f3969d;

    /* renamed from: e */
    private float f3970e;

    /* renamed from: f */
    private List<TXCVideoEditConstants.C3520j> f3971f;

    /* renamed from: g */
    private TailWaterMarkListener f3972g;

    /* renamed from: h */
    private int f3973h;

    /* renamed from: i */
    private int f3974i;

    /* renamed from: j */
    private int f3975j;

    /* renamed from: k */
    private int f3976k;

    /* renamed from: l */
    private int f3977l;

    /* renamed from: m */
    private boolean f3978m;

    /* renamed from: n */
    private boolean f3979n;

    /* renamed from: o */
    private boolean f3980o;

    /* renamed from: a */
    public static TailWaterMarkChain m1822a() {
        if (f3966c == null) {
            f3966c = new TailWaterMarkChain();
        }
        return f3966c;
    }

    private TailWaterMarkChain() {
        m1812i();
    }

    /* renamed from: a */
    public void m1821a(TailWaterMark tailWaterMark) {
        this.f3969d = tailWaterMark;
    }

    /* renamed from: b */
    public boolean m1819b() {
        return this.f3969d != null;
    }

    /* renamed from: a */
    public void m1820a(TailWaterMarkListener tailWaterMarkListener) {
        this.f3972g = tailWaterMarkListener;
    }

    /* renamed from: c */
    public long m1818c() {
        return this.f3969d.m2298a() * 1000 * 1000;
    }

    /* renamed from: d */
    public void m1817d() {
        int m2298a;
        this.f3980o = VideoOutputConfig.m2457a().m2433l();
        TailWaterMark tailWaterMark = this.f3969d;
        if (tailWaterMark == null || this.f3967a == null || (m2298a = tailWaterMark.m2298a()) == 0) {
            return;
        }
        this.f3976k = this.f3967a.m2321i() * m2298a;
        this.f3977l = 0;
        this.f3970e = 0.0f;
        m1816e();
        if (this.f3980o) {
            Frame frame = this.f3968b;
            if (frame == null) {
                return;
            }
            this.f3974i = (frame.m2325g() * 1000) / ((this.f3968b.m2317k() * 2) * this.f3968b.m2319j());
            this.f3973h = (m2298a * 1000) / this.f3974i;
            this.f3975j = 0;
            for (int i = 0; i < this.f3973h; i++) {
                m1814g();
            }
        }
        m1815f();
    }

    /* renamed from: e */
    public void m1816e() {
        Bitmap m2296c = this.f3969d.m2296c();
        TXCVideoEditConstants.C3517g m2295d = this.f3969d.m2295d();
        int m2298a = this.f3969d.m2298a();
        if (m2296c == null || m2296c.isRecycled() || m2295d == null || m2298a == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        int m2321i = m2298a * this.f3967a.m2321i();
        long m1427a = TimeProvider.m1427a(this.f3967a) / 1000;
        int i = 255 / m2321i;
        int i2 = 100;
        for (int i3 = 0; i3 < m2321i; i3++) {
            i2 += i;
            if (i2 >= 255) {
                i2 = 255;
            }
            Bitmap m1444a = BitmapUtil.m1444a(m2296c, i2);
            TXCVideoEditConstants.C3520j c3520j = new TXCVideoEditConstants.C3520j();
            c3520j.f4390b = m2295d;
            c3520j.f4389a = m1444a;
            c3520j.f4391c = m1427a;
            c3520j.f4392d = m1427a + (1000 / this.f3967a.m2321i());
            arrayList.add(c3520j);
            m1427a = c3520j.f4392d;
        }
        this.f3971f = arrayList;
    }

    /* renamed from: f */
    public void m1815f() {
        long m2305t;
        int m2321i;
        if (this.f3979n) {
            return;
        }
        if (this.f3977l >= this.f3976k - 1) {
            this.f3979n = true;
            TXCLog.m2915d("TailWaterMarkChain", "===insertTailVideoFrame===mEndAudio:" + this.f3978m + ",mHasAudioTrack:" + this.f3980o);
            if (this.f3980o) {
                if (!this.f3978m) {
                    return;
                }
                m1810k();
                return;
            }
            m1809l();
            return;
        }
        Frame frame = new Frame(this.f3967a.m2346a(), this.f3967a.m2338b(), this.f3967a.m2310o());
        frame.m2344a(this.f3967a.m2335c());
        frame.m2337b(this.f3967a.m2332d());
        frame.m2328e(this.f3967a.m2323h());
        frame.m2326f(this.f3967a.m2321i());
        frame.m2324g(this.f3967a.m2319j());
        if (frame.m2323h() == 90 || frame.m2323h() == 270) {
            frame.m2318j(this.f3967a.m2311n());
            frame.m2316k(this.f3967a.m2313m());
        } else {
            frame.m2318j(this.f3967a.m2313m());
            frame.m2316k(this.f3967a.m2311n());
        }
        if (ReverseConfig.m2478a().m2476b()) {
            m2305t = this.f3967a.m2304u();
            m2321i = ((this.f3977l + 1) * 1000) / this.f3967a.m2321i();
        } else {
            m2305t = this.f3967a.m2305t();
            m2321i = ((this.f3977l + 1) * 1000) / this.f3967a.m2321i();
        }
        long j = m2305t + (m2321i * 1000);
        frame.m2343a(j);
        frame.m2336b(j);
        frame.m2333c(j);
        frame.m2339a(true);
        this.f3970e += 10.0f / this.f3976k;
        frame.m2345a(this.f3970e);
        frame.m2334c(this.f3967a.m2327f());
        frame.m2312m(this.f3967a.m2300y());
        frame.m2340a(this.f3967a.m2302w());
        this.f3977l++;
        TXCLog.m2915d("TailWaterMarkChain", "===insertTailVideoFrame===mVideoIndex:" + this.f3977l + ",time:" + frame.m2305t());
        TailWaterMarkListener tailWaterMarkListener = this.f3972g;
        if (tailWaterMarkListener == null) {
            return;
        }
        tailWaterMarkListener.mo2057b(frame);
    }

    /* renamed from: g */
    public void m1814g() {
        if (this.f3978m) {
            return;
        }
        if (this.f3975j >= this.f3973h - 1) {
            this.f3978m = true;
            if (!this.f3979n) {
                return;
            }
            m1810k();
            return;
        }
        this.f3968b.m2341a(ByteBuffer.allocate(this.f3968b.m2325g()));
        Frame frame = new Frame(this.f3968b.m2346a(), this.f3968b.m2338b(), this.f3968b.m2310o());
        frame.m2344a(this.f3968b.m2335c());
        frame.m2337b(this.f3968b.m2332d());
        frame.m2324g(this.f3968b.m2319j());
        frame.m2322h(this.f3968b.m2317k());
        long m2329e = this.f3968b.m2329e() + (this.f3974i * (this.f3975j + 1) * 1000);
        frame.m2343a(m2329e);
        frame.m2336b(m2329e);
        frame.m2333c(m2329e);
        frame.m2339a(true);
        frame.m2334c(this.f3968b.m2327f());
        this.f3975j++;
        TXCLog.m2915d("TailWaterMarkChain", "===insertTailAudioFrame===mAudioIndex:" + this.f3975j + ",time:" + frame.m2329e());
        TailWaterMarkListener tailWaterMarkListener = this.f3972g;
        if (tailWaterMarkListener == null) {
            return;
        }
        tailWaterMarkListener.mo2058a(frame);
    }

    /* renamed from: k */
    private void m1810k() {
        TXCLog.m2915d("TailWaterMarkChain", "===insertAudioTailFrame===");
        this.f3968b.m2341a(ByteBuffer.allocate(this.f3968b.m2325g()));
        Frame frame = new Frame(this.f3968b.m2346a(), this.f3968b.m2338b(), this.f3968b.m2310o());
        frame.m2344a(this.f3968b.m2335c());
        frame.m2337b(this.f3968b.m2332d());
        frame.m2324g(this.f3968b.m2319j());
        frame.m2322h(this.f3968b.m2317k());
        long m2329e = this.f3968b.m2329e() + (this.f3974i * (this.f3975j + 1) * 1000);
        frame.m2343a(m2329e);
        frame.m2336b(m2329e);
        frame.m2333c(m2329e);
        frame.m2339a(true);
        frame.m2334c(4);
        this.f3975j++;
        TailWaterMarkListener tailWaterMarkListener = this.f3972g;
        if (tailWaterMarkListener != null) {
            tailWaterMarkListener.mo2058a(frame);
        }
    }

    /* renamed from: l */
    private void m1809l() {
        long m2305t;
        int m2321i;
        TXCLog.m2915d("TailWaterMarkChain", "===insertVideoTailVFrame===, lastVideoFrame = " + this.f3967a);
        Frame frame = this.f3967a;
        if (frame == null) {
            return;
        }
        Frame frame2 = new Frame(frame.m2346a(), this.f3967a.m2338b(), this.f3967a.m2310o());
        frame2.m2344a(this.f3967a.m2335c());
        frame2.m2337b(this.f3967a.m2332d());
        frame2.m2328e(this.f3967a.m2323h());
        frame2.m2326f(this.f3967a.m2321i());
        frame2.m2324g(this.f3967a.m2319j());
        if (frame2.m2323h() == 90 || frame2.m2323h() == 270) {
            frame2.m2318j(this.f3967a.m2311n());
            frame2.m2316k(this.f3967a.m2313m());
        } else {
            frame2.m2318j(this.f3967a.m2313m());
            frame2.m2316k(this.f3967a.m2311n());
        }
        if (ReverseConfig.m2478a().m2476b()) {
            m2305t = this.f3967a.m2304u();
            m2321i = ((this.f3977l + 1) * 1000) / this.f3967a.m2321i();
        } else {
            m2305t = this.f3967a.m2305t();
            m2321i = ((this.f3977l + 1) * 1000) / this.f3967a.m2321i();
        }
        long j = m2305t + (m2321i * 1000);
        frame2.m2343a(j);
        frame2.m2336b(j);
        frame2.m2333c(j);
        frame2.m2339a(true);
        frame2.m2334c(4);
        frame2.m2312m(this.f3967a.m2300y());
        this.f3970e += 10.0f / this.f3976k;
        frame2.m2345a(this.f3970e);
        this.f3977l++;
        TXCLog.m2915d("TailWaterMarkChain", "===insertVideoTailVFrame===mVideoIndex:" + this.f3977l + ",time:" + frame2.m2305t() + ",flag:" + frame2.m2327f());
        TailWaterMarkListener tailWaterMarkListener = this.f3972g;
        if (tailWaterMarkListener == null) {
            return;
        }
        tailWaterMarkListener.mo2057b(frame2);
    }

    /* renamed from: h */
    public List<TXCVideoEditConstants.C3520j> m1813h() {
        return this.f3971f;
    }

    /* renamed from: i */
    public void m1812i() {
        Bitmap bitmap;
        List<TXCVideoEditConstants.C3520j> list = this.f3971f;
        if (list != null) {
            for (TXCVideoEditConstants.C3520j c3520j : list) {
                if (c3520j != null && (bitmap = c3520j.f4389a) != null && !bitmap.isRecycled()) {
                    c3520j.f4389a.recycle();
                    c3520j.f4389a = null;
                }
            }
            this.f3971f.clear();
        }
        this.f3971f = null;
        TailWaterMark tailWaterMark = this.f3969d;
        if (tailWaterMark != null) {
            tailWaterMark.mo2297b();
        }
        this.f3969d = null;
        this.f3967a = null;
        this.f3968b = null;
        this.f3970e = 0.0f;
        this.f3975j = 0;
        this.f3977l = 0;
        this.f3973h = 0;
        this.f3976k = 0;
        this.f3978m = false;
        this.f3979n = false;
    }

    /* renamed from: j */
    public boolean m1811j() {
        if (this.f3980o) {
            return this.f3979n && this.f3978m;
        }
        return this.f3979n;
    }
}
