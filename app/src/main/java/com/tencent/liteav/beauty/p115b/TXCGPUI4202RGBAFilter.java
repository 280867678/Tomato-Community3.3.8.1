package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import com.tencent.liteav.beauty.NativeLoad;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.beauty.b.p */
/* loaded from: classes3.dex */
public class TXCGPUI4202RGBAFilter extends TXCGPUFilter {

    /* renamed from: z */
    private static String f3105z = "YUV420pToRGBFilter";

    /* renamed from: t */
    private int f3108t;

    /* renamed from: r */
    private ByteBuffer f3106r = null;

    /* renamed from: s */
    private byte[] f3107s = null;

    /* renamed from: u */
    private int[] f3109u = null;

    /* renamed from: v */
    private int[] f3110v = null;

    /* renamed from: w */
    private int f3111w = 0;

    /* renamed from: x */
    private int f3112x = 0;

    /* renamed from: y */
    private int[] f3113y = null;

    public TXCGPUI4202RGBAFilter(int i) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f3108t = 1;
        this.f3108t = i;
        String str = f3105z;
        TXCLog.m2913i(str, "yuv Type " + i);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: c */
    public boolean mo2653c() {
        int i = this.f3108t;
        int i2 = 7;
        if (i != 1) {
            if (i == 3) {
                i2 = 9;
            } else if (i == 2) {
                return super.mo2653c();
            } else {
                String str = f3105z;
                TXCLog.m2914e(str, "don't support yuv format " + this.f3108t);
            }
        }
        NativeLoad.getInstance();
        this.f2612a = NativeLoad.nativeLoadGLProgram(i2);
        if (this.f2612a != 0 && mo1321a()) {
            this.f2618g = true;
        } else {
            this.f2618g = false;
        }
        mo2643d();
        return this.f2618g;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (this.f2617f == i2 && this.f2616e == i) {
            return;
        }
        int i3 = this.f3108t;
        if (1 == i3) {
            this.f3109u = new int[1];
            int[] iArr = this.f3109u;
            iArr[0] = TXCOpenGlUtils.m3005a(i, (i2 * 3) / 2, 6409, 6409, iArr);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.f3109u[0]);
            GLES20.glBindTexture(3553, 0);
            GLES20.glUniform1i(this.f2614c, 0);
        } else if (3 == i3) {
            GLES20.glActiveTexture(33984);
            GLES20.glActiveTexture(33985);
            this.f3111w = GLES20.glGetUniformLocation(m3011q(), "yTexture");
            this.f3112x = GLES20.glGetUniformLocation(m3011q(), "uvTexture");
            if (this.f3109u == null) {
                this.f3109u = new int[1];
                int[] iArr2 = this.f3109u;
                iArr2[0] = TXCOpenGlUtils.m3005a(i, i2, 6409, 6409, iArr2);
            }
            if (this.f3110v == null) {
                this.f3110v = new int[1];
                int[] iArr3 = this.f3110v;
                iArr3[0] = TXCOpenGlUtils.m3005a(i / 2, i2 / 2, 6410, 6410, iArr3);
            }
            GLES20.glUniform1i(this.f3111w, 0);
            GLES20.glUniform1i(this.f3112x, 1);
        } else if (2 == i3 && this.f3113y == null) {
            this.f3113y = new int[1];
            int[] iArr4 = this.f3113y;
            iArr4[0] = TXCOpenGlUtils.m3005a(i, i2, 6408, 6408, iArr4);
        }
        super.mo1333a(i, i2);
    }

    /* renamed from: a */
    public void m2672a(byte[] bArr) {
        this.f3107s = bArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: i */
    public void mo2657i() {
        super.mo2657i();
        int i = this.f3108t;
        if (1 == i) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.f3109u[0]);
            GLES20.glUniform1i(this.f2614c, 0);
            NativeLoad.getInstance();
            NativeLoad.nativeglTexImage2D(3553, 0, 6409, this.f2616e, (this.f2617f * 3) / 2, 0, 6409, 5121, this.f3107s, 0);
        } else if (3 != i) {
            if (2 != i) {
                return;
            }
            m2670s();
        } else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.f3109u[0]);
            GLES20.glUniform1i(this.f3111w, 0);
            NativeLoad.getInstance();
            NativeLoad.nativeglTexImage2D(3553, 0, 6409, this.f2616e, this.f2617f, 0, 6409, 5121, this.f3107s, 0);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, this.f3110v[0]);
            GLES20.glUniform1i(this.f3112x, 1);
            NativeLoad.getInstance();
            int i2 = this.f2616e;
            int i3 = this.f2617f;
            NativeLoad.nativeglTexImage2D(3553, 0, 6410, i2 / 2, i3 / 2, 0, 6410, 5121, this.f3107s, i2 * i3);
        }
    }

    /* renamed from: r */
    public int m2671r() {
        if (2 == this.f3108t) {
            int m2670s = m2670s();
            GLES20.glBindTexture(3553, 0);
            return m2670s;
        }
        return super.mo2294a(-1);
    }

    /* renamed from: s */
    private int m2670s() {
        GLES20.glBindTexture(3553, this.f3113y[0]);
        if (this.f3107s != null) {
            NativeLoad.getInstance();
            NativeLoad.nativeglTexImage2D(3553, 0, 6408, this.f2616e, this.f2617f, 0, 6408, 5121, this.f3107s, 0);
        }
        return this.f3113y[0];
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1324a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        super.mo1324a(-1, floatBuffer, floatBuffer2);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        super.mo2293b();
        int[] iArr = this.f3109u;
        if (iArr != null && iArr[0] > 0) {
            GLES20.glDeleteTextures(1, iArr, 0);
            this.f3109u = null;
        }
        int[] iArr2 = this.f3110v;
        if (iArr2 != null && iArr2[0] > 0) {
            GLES20.glDeleteTextures(1, iArr2, 0);
            this.f3110v = null;
        }
        int[] iArr3 = this.f3113y;
        if (iArr3 == null || iArr3[0] <= 0) {
            return;
        }
        GLES20.glDeleteTextures(1, iArr3, 0);
        this.f3113y = null;
    }
}
