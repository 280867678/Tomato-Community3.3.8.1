package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.beauty.b.x */
/* loaded from: classes3.dex */
public class TXCGPURotateScaleFilter extends TXCGPUFilter {

    /* renamed from: r */
    private static String f3183r = "precision mediump float;\nvarying mediump vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float scale;\n uniform mediump float alpha;\n\nvoid main(void) {\n    gl_FragColor = vec4(texture2D(inputImageTexture, textureCoordinate).rgb, alpha); \n}\n";

    /* renamed from: B */
    private boolean f3185B;

    /* renamed from: s */
    private int f3186s;

    /* renamed from: t */
    private int f3187t;

    /* renamed from: v */
    private int f3189v = -1;

    /* renamed from: w */
    private int f3190w = -1;

    /* renamed from: x */
    private int f3191x = -1;

    /* renamed from: z */
    private float f3193z = 1.0f;

    /* renamed from: A */
    private float f3184A = 1.0f;

    /* renamed from: u */
    private float[] f3188u = new float[16];

    /* renamed from: y */
    private float[] f3192y = new float[16];

    public TXCGPURotateScaleFilter() {
        super("attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\n \n uniform mat4 transformMatrix;\n uniform mat4 orthographicMatrix;\n \n varying vec2 textureCoordinate;\n void main()\n {\n     gl_Position = transformMatrix * vec4(position.xyz, 1.0) * orthographicMatrix;\n     textureCoordinate = inputTextureCoordinate.xy;\n }", f3183r);
        Matrix.orthoM(this.f3188u, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
        Matrix.setIdentityM(this.f3192y, 0);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f3186s = GLES20.glGetUniformLocation(m3011q(), "transformMatrix");
        this.f3187t = GLES20.glGetUniformLocation(m3011q(), "orthographicMatrix");
        this.f3189v = GLES20.glGetUniformLocation(m3011q(), "scale");
        this.f3190w = GLES20.glGetUniformLocation(m3011q(), "center");
        this.f3191x = GLES20.glGetUniformLocation(m3011q(), "alpha");
        m3020d(this.f3186s, this.f3192y);
        m3020d(this.f3187t, this.f3188u);
        m3035a(this.f3189v, this.f3193z);
        m2645b(this.f3184A);
        m3030a(this.f3190w, new float[]{0.5f, 0.5f});
        return mo1321a;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: d */
    public void mo2643d() {
        super.mo2643d();
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (this.f2617f == i2 && this.f2616e == i) {
            return;
        }
        super.mo1333a(i, i2);
        if (this.f3185B) {
            return;
        }
        float f = i2;
        float f2 = i;
        Matrix.orthoM(this.f3188u, 0, -1.0f, 1.0f, ((-1.0f) * f) / f2, (f * 1.0f) / f2, -1.0f, 1.0f);
        m3020d(this.f3187t, this.f3188u);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo1355a(int i, int i2, int i3) {
        if (!this.f2618g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, i2);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16640);
        mo1324a(i, this.f2619h, this.f2620i);
        TXCGPUFilter.AbstractC3355a abstractC3355a = this.f2623l;
        if (abstractC3355a instanceof TXCGPUFilter.AbstractC3355a) {
            abstractC3355a.mo458a(i3);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return i3;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1324a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (!this.f3185B) {
            floatBuffer.position(0);
            floatBuffer.get(r0);
            float m3012p = m3012p() / m3013o();
            float[] fArr = {0.0f, fArr[1] * m3012p, 0.0f, fArr[3] * m3012p, 0.0f, fArr[5] * m3012p, 0.0f, fArr[7] * m3012p};
            floatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            floatBuffer.put(fArr).position(0);
        }
        super.mo1324a(i, floatBuffer, floatBuffer2);
    }

    /* renamed from: a */
    public void m2648a(float f) {
        m2646a((float[]) null, f);
    }

    /* renamed from: a */
    private float[] m2646a(float[] fArr, float f) {
        if (fArr == null) {
            fArr = new float[16];
            Matrix.setIdentityM(fArr, 0);
        }
        Matrix.setRotateM(fArr, 0, f, 0.0f, 0.0f, 1.0f);
        this.f3192y = fArr;
        m3020d(this.f3186s, this.f3192y);
        return fArr;
    }

    /* renamed from: b */
    private float[] m2644b(float[] fArr, float f) {
        if (fArr == null) {
            fArr = new float[16];
            Matrix.setIdentityM(fArr, 0);
        }
        Matrix.scaleM(fArr, 0, f, f, 1.0f);
        this.f3192y = fArr;
        m3020d(this.f3186s, this.f3192y);
        return fArr;
    }

    /* renamed from: a */
    public float[] m2647a(float f, float f2) {
        return m2644b(m2646a((float[]) null, f), f2);
    }

    /* renamed from: b */
    public void m2645b(float f) {
        m3035a(this.f3191x, f);
    }
}
