package com.tencent.liteav.p121f;

import android.graphics.Bitmap;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: com.tencent.liteav.f.h */
/* loaded from: classes3.dex */
public class SubtitleFilterChain extends BasicFilterChain {

    /* renamed from: d */
    private static SubtitleFilterChain f3943d;

    /* renamed from: e */
    private List<TXCVideoEditConstants.C3520j> f3944e;

    /* renamed from: f */
    private CopyOnWriteArrayList<TXCVideoEditConstants.C3520j> f3945f = new CopyOnWriteArrayList<>();

    /* renamed from: a */
    public static SubtitleFilterChain m1841a() {
        if (f3943d == null) {
            f3943d = new SubtitleFilterChain();
        }
        return f3943d;
    }

    private SubtitleFilterChain() {
    }

    /* renamed from: a */
    public void m1838a(List<TXCVideoEditConstants.C3520j> list) {
        this.f3944e = list;
        this.f3945f.clear();
        Frame frame = this.f3844c;
        if (frame != null) {
            m1840a(frame);
        }
    }

    /* renamed from: b */
    public List<TXCVideoEditConstants.C3520j> m1837b() {
        return this.f3945f;
    }

    /* renamed from: a */
    public void m1840a(Frame frame) {
        List<TXCVideoEditConstants.C3520j> list;
        if (this.f3842a == 0 || this.f3843b == 0 || (list = this.f3944e) == null || list.size() == 0) {
            return;
        }
        Resolution m1882b = m1882b(frame);
        for (TXCVideoEditConstants.C3520j c3520j : this.f3944e) {
            if (c3520j != null) {
                this.f3945f.add(m1839a(c3520j, m1883a(c3520j.f4390b, m1882b)));
            }
        }
    }

    /* renamed from: a */
    private TXCVideoEditConstants.C3520j m1839a(TXCVideoEditConstants.C3520j c3520j, TXCVideoEditConstants.C3517g c3517g) {
        TXCVideoEditConstants.C3520j c3520j2 = new TXCVideoEditConstants.C3520j();
        c3520j2.f4390b = c3517g;
        c3520j2.f4389a = c3520j.f4389a;
        c3520j2.f4391c = c3520j.f4391c;
        c3520j2.f4392d = c3520j.f4392d;
        return c3520j2;
    }

    /* renamed from: c */
    public void m1835c() {
        m1836b(this.f3945f);
        m1836b(this.f3944e);
        this.f3944e = null;
    }

    /* renamed from: b */
    protected void m1836b(List<TXCVideoEditConstants.C3520j> list) {
        Bitmap bitmap;
        if (list != null) {
            for (TXCVideoEditConstants.C3520j c3520j : list) {
                if (c3520j != null && (bitmap = c3520j.f4389a) != null && !bitmap.isRecycled()) {
                    c3520j.f4389a.recycle();
                    c3520j.f4389a = null;
                }
            }
            list.clear();
        }
    }
}
