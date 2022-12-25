package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCRotation;
import com.tencent.liteav.basic.p109e.TXCTextureRotationUtil;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.beauty.b.j */
/* loaded from: classes3.dex */
public class TXCGPUFilterGroup extends TXCGPUFilter {

    /* renamed from: r */
    protected List<TXCGPUFilter> f3074r;

    /* renamed from: s */
    protected List<TXCGPUFilter> f3075s;

    /* renamed from: t */
    private int[] f3076t;

    /* renamed from: u */
    private int[] f3077u;

    /* renamed from: v */
    private TXCGPUFilter f3078v;

    /* renamed from: w */
    private final FloatBuffer f3079w;

    /* renamed from: x */
    private final FloatBuffer f3080x;

    /* renamed from: y */
    private final FloatBuffer f3081y;

    public TXCGPUFilterGroup() {
        this(null);
        this.f2626o = true;
    }

    public TXCGPUFilterGroup(List<TXCGPUFilter> list) {
        this.f3075s = new ArrayList();
        this.f3078v = new TXCGPUFilter();
        this.f2626o = true;
        this.f3074r = list;
        if (this.f3074r == null) {
            this.f3074r = new ArrayList();
        } else {
            m2689s();
        }
        this.f3079w = ByteBuffer.allocateDirect(TXCGPURenderer.f3156a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f3079w.put(TXCGPURenderer.f3156a).position(0);
        this.f3080x = ByteBuffer.allocateDirect(TXCTextureRotationUtil.f2680a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f3080x.put(TXCTextureRotationUtil.f2680a).position(0);
        float[] m2991a = TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, true);
        this.f3081y = ByteBuffer.allocateDirect(m2991a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f3081y.put(m2991a).position(0);
    }

    /* renamed from: a */
    public void m2692a(TXCGPUFilter tXCGPUFilter) {
        if (tXCGPUFilter == null) {
            return;
        }
        this.f3074r.add(tXCGPUFilter);
        m2689s();
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        if (mo1321a) {
            for (TXCGPUFilter tXCGPUFilter : this.f3074r) {
                tXCGPUFilter.mo2653c();
                if (!tXCGPUFilter.m3014n()) {
                    break;
                }
            }
            mo1321a = this.f3078v.mo2653c();
        }
        return mo1321a && GLES20.glGetError() == 0;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        super.mo2293b();
        for (TXCGPUFilter tXCGPUFilter : this.f3074r) {
            tXCGPUFilter.mo1351e();
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: f */
    public void mo2691f() {
        super.mo2691f();
        int[] iArr = this.f3077u;
        if (iArr != null) {
            GLES20.glDeleteTextures(2, iArr, 0);
            this.f3077u = null;
        }
        int[] iArr2 = this.f3076t;
        if (iArr2 != null) {
            GLES20.glDeleteFramebuffers(2, iArr2, 0);
            this.f3076t = null;
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (this.f2616e == i && this.f2617f == i2) {
            return;
        }
        if (this.f3076t != null) {
            mo2691f();
        }
        super.mo1333a(i, i2);
        int size = this.f3075s.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.f3075s.get(i3).mo1333a(i, i2);
        }
        this.f3078v.mo1333a(i, i2);
        List<TXCGPUFilter> list = this.f3075s;
        if (list == null || list.size() <= 0) {
            return;
        }
        this.f3075s.size();
        this.f3076t = new int[2];
        this.f3077u = new int[2];
        for (int i4 = 0; i4 < 2; i4++) {
            GLES20.glGenFramebuffers(1, this.f3076t, i4);
            GLES20.glGenTextures(1, this.f3077u, i4);
            GLES20.glBindTexture(3553, this.f3077u[i4]);
            GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glBindFramebuffer(36160, this.f3076t[i4]);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.f3077u[i4], 0);
            GLES20.glBindTexture(3553, 0);
            GLES20.glBindFramebuffer(36160, 0);
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo1355a(int i, int i2, int i3) {
        int size = this.f3075s.size();
        m3017k();
        int i4 = i;
        boolean z = false;
        for (int i5 = 0; i5 < size; i5++) {
            TXCGPUFilter tXCGPUFilter = this.f3075s.get(i5);
            if (z) {
                i4 = tXCGPUFilter.mo1355a(i4, i2, i3);
            } else {
                i4 = tXCGPUFilter.mo1355a(i4, this.f3076t[0], this.f3077u[0]);
            }
            z = !z;
        }
        if (z) {
            this.f3078v.mo1355a(i4, i2, i3);
        }
        return i3;
    }

    /* renamed from: r */
    public List<TXCGPUFilter> m2690r() {
        return this.f3075s;
    }

    /* renamed from: s */
    public void m2689s() {
        if (this.f3074r == null) {
            return;
        }
        this.f3075s.clear();
        for (TXCGPUFilter tXCGPUFilter : this.f3074r) {
            if (tXCGPUFilter instanceof TXCGPUFilterGroup) {
                TXCGPUFilterGroup tXCGPUFilterGroup = (TXCGPUFilterGroup) tXCGPUFilter;
                tXCGPUFilterGroup.m2689s();
                List<TXCGPUFilter> m2690r = tXCGPUFilterGroup.m2690r();
                if (m2690r != null && !m2690r.isEmpty()) {
                    this.f3075s.addAll(m2690r);
                }
            } else {
                this.f3075s.add(tXCGPUFilter);
            }
        }
    }
}
