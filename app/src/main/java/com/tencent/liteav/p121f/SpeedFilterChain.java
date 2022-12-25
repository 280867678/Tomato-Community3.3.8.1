package com.tencent.liteav.p121f;

import com.tencent.liteav.p124i.TXCVideoEditConstants;
import java.util.List;

/* renamed from: com.tencent.liteav.f.g */
/* loaded from: classes3.dex */
public class SpeedFilterChain {

    /* renamed from: a */
    private static SpeedFilterChain f3941a;

    /* renamed from: b */
    private List<TXCVideoEditConstants.C3519i> f3942b;

    /* renamed from: a */
    public float m1848a(int i) {
        if (i != 0) {
            if (i == 1) {
                return 0.5f;
            }
            if (i == 2) {
                return 1.0f;
            }
            if (i == 3) {
                return 1.5f;
            }
            return i != 4 ? 1.0f : 2.0f;
        }
        return 0.25f;
    }

    /* renamed from: a */
    public static SpeedFilterChain m1849a() {
        if (f3941a == null) {
            f3941a = new SpeedFilterChain();
        }
        return f3941a;
    }

    private SpeedFilterChain() {
    }

    /* renamed from: a */
    public void m1846a(List<TXCVideoEditConstants.C3519i> list) {
        this.f3942b = list;
    }

    /* renamed from: b */
    public List<TXCVideoEditConstants.C3519i> m1845b() {
        return this.f3942b;
    }

    /* renamed from: c */
    public boolean m1843c() {
        List<TXCVideoEditConstants.C3519i> list = this.f3942b;
        if (list != null && list.size() != 0) {
            for (TXCVideoEditConstants.C3519i c3519i : this.f3942b) {
                if (c3519i.f4386a != 2) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: a */
    public float m1847a(long j) {
        List<TXCVideoEditConstants.C3519i> list = this.f3942b;
        if (list == null || list.size() == 0) {
            return 1.0f;
        }
        for (TXCVideoEditConstants.C3519i c3519i : this.f3942b) {
            if (j > c3519i.f4387b * 1000 && j < c3519i.f4388c * 1000) {
                return m1848a(c3519i.f4386a);
            }
        }
        return 1.0f;
    }

    /* renamed from: d */
    public void m1842d() {
        List<TXCVideoEditConstants.C3519i> list = this.f3942b;
        if (list != null) {
            list.clear();
        }
        this.f3942b = null;
    }

    /* renamed from: b */
    public long m1844b(long j) {
        List<TXCVideoEditConstants.C3519i> m1845b = m1849a().m1845b();
        for (int i = 0; i < m1845b.size(); i++) {
            TXCVideoEditConstants.C3519i c3519i = m1845b.get(i);
            float m1848a = m1848a(c3519i.f4386a);
            long j2 = (c3519i.f4388c - c3519i.f4387b) * 1000;
            j = (j + (((float) j2) / m1848a)) - j2;
        }
        return j;
    }
}
