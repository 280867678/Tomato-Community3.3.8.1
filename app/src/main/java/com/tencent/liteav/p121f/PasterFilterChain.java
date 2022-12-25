package com.tencent.liteav.p121f;

import android.graphics.Bitmap;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: com.tencent.liteav.f.f */
/* loaded from: classes3.dex */
public class PasterFilterChain extends BasicFilterChain {

    /* renamed from: d */
    private static PasterFilterChain f3938d;

    /* renamed from: e */
    private List<TXCVideoEditConstants.C3515e> f3939e;

    /* renamed from: f */
    private CopyOnWriteArrayList<TXCVideoEditConstants.C3515e> f3940f = new CopyOnWriteArrayList<>();

    /* renamed from: a */
    public static PasterFilterChain m1856a() {
        if (f3938d == null) {
            f3938d = new PasterFilterChain();
        }
        return f3938d;
    }

    private PasterFilterChain() {
    }

    /* renamed from: a */
    public void m1853a(List<TXCVideoEditConstants.C3515e> list) {
        this.f3939e = list;
        m1851b(this.f3940f);
        Frame frame = this.f3844c;
        if (frame != null) {
            m1855a(frame);
        }
    }

    /* renamed from: b */
    public List<TXCVideoEditConstants.C3515e> m1852b() {
        return this.f3940f;
    }

    /* renamed from: a */
    public void m1855a(Frame frame) {
        List<TXCVideoEditConstants.C3515e> list;
        if (this.f3842a == 0 || this.f3843b == 0 || (list = this.f3939e) == null || list.size() == 0) {
            return;
        }
        Resolution m1882b = m1882b(frame);
        for (TXCVideoEditConstants.C3515e c3515e : this.f3939e) {
            if (c3515e != null) {
                this.f3940f.add(m1854a(c3515e, m1883a(c3515e.f4375b, m1882b)));
            }
        }
    }

    /* renamed from: a */
    private TXCVideoEditConstants.C3515e m1854a(TXCVideoEditConstants.C3515e c3515e, TXCVideoEditConstants.C3517g c3517g) {
        TXCVideoEditConstants.C3515e c3515e2 = new TXCVideoEditConstants.C3515e();
        c3515e2.f4375b = c3517g;
        c3515e2.f4374a = c3515e.f4374a;
        c3515e2.f4376c = c3515e.f4376c;
        c3515e2.f4377d = c3515e.f4377d;
        return c3515e2;
    }

    /* renamed from: c */
    public void m1850c() {
        m1851b(this.f3940f);
        m1851b(this.f3939e);
        this.f3939e = null;
    }

    /* renamed from: b */
    protected void m1851b(List<TXCVideoEditConstants.C3515e> list) {
        Bitmap bitmap;
        if (list != null) {
            for (TXCVideoEditConstants.C3515e c3515e : list) {
                if (c3515e != null && (bitmap = c3515e.f4374a) != null && !bitmap.isRecycled()) {
                    c3515e.f4374a.recycle();
                    c3515e.f4374a = null;
                }
            }
            list.clear();
        }
    }
}
