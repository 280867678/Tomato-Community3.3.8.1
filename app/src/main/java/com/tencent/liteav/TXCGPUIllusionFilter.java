package com.tencent.liteav;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUTwoInputFilter;

/* renamed from: com.tencent.liteav.e */
/* loaded from: classes3.dex */
public class TXCGPUIllusionFilter extends TXCGPUTwoInputFilter {

    /* renamed from: r */
    private int[] f3475r = null;

    /* renamed from: s */
    private int[] f3476s = null;

    /* renamed from: t */
    private TXCGPUFilter f3477t;

    public TXCGPUIllusionFilter() {
        super("precision mediump float;  \nvarying vec2 textureCoordinate;  \nuniform sampler2D inputImageTexture;  \nuniform sampler2D inputImageTexture2;  \nvoid main() {   \n\tgl_FragColor = vec4(mix(texture2D(inputImageTexture2, textureCoordinate).rgb, texture2D(inputImageTexture, textureCoordinate).rgb, vec3(0.06,0.21,0.6)),1.0);   \n}  \n");
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCGPUTwoInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        this.f2613b = GLES20.glGetAttribLocation(this.f2612a, "position");
        this.f2614c = GLES20.glGetUniformLocation(this.f2612a, "inputImageTexture");
        this.f2615d = GLES20.glGetAttribLocation(this.f2612a, "inputTextureCoordinate");
        ((TXCGPUTwoInputFilter) this).f2961v = GLES20.glGetUniformLocation(m3011q(), "inputImageTexture2");
        return true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo2294a(int i) {
        if (this.f3477t == null) {
            this.f3477t = new TXCGPUFilter();
            this.f3477t.mo1353a(true);
            this.f3477t.mo2653c();
            this.f3477t.mo1333a(this.f2616e, this.f2617f);
            TXCGPUFilter tXCGPUFilter = this.f3477t;
            tXCGPUFilter.mo1355a(i, tXCGPUFilter.m3015m(), this.f3477t.m3016l());
        }
        int m2741c = m2741c(i, this.f3477t.m3016l());
        TXCGPUFilter tXCGPUFilter2 = this.f3477t;
        tXCGPUFilter2.mo1355a(m2741c, tXCGPUFilter2.m3015m(), this.f3477t.m3016l());
        return m2741c;
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCGPUTwoInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        super.mo2293b();
        TXCGPUFilter tXCGPUFilter = this.f3477t;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.f3477t = null;
        }
        int[] iArr = this.f3476s;
        if (iArr != null) {
            GLES20.glDeleteFramebuffers(1, iArr, 0);
            this.f3476s = null;
        }
        int[] iArr2 = this.f3475r;
        if (iArr2 != null) {
            GLES20.glDeleteTextures(1, iArr2, 0);
            this.f3475r = null;
        }
    }
}
