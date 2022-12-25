package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import java.nio.Buffer;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.beauty.b.aj */
/* loaded from: classes3.dex */
public class TXCZoomInOutFilter extends TXCGPUFilter {

    /* renamed from: x */
    private static String f3004x = "ZoomInOut";

    /* renamed from: r */
    private int f3005r = -1;

    /* renamed from: s */
    private int f3006s = -1;

    /* renamed from: t */
    private int f3007t = -1;

    /* renamed from: u */
    private int f3008u = -1;

    /* renamed from: v */
    private int f3009v = -1;

    /* renamed from: w */
    private float f3010w = 0.3f;

    /* renamed from: y */
    private final float[] f3011y = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};

    /* renamed from: z */
    private float[] f3012z = (float[]) this.f3011y.clone();

    public TXCZoomInOutFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "precision highp float;\nvarying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\nuniform lowp float alphaLevel;\nuniform vec2 offsetR; \nuniform vec2 offsetG;\nuniform vec2 offsetB;\n\nvoid main()\n{\n\tmediump vec4 fout;\n\tfout.r = texture2D(inputImageTexture, textureCoordinate + offsetR).r; \n\tfout.g = texture2D(inputImageTexture, textureCoordinate + offsetG).g; \n\tfout.b = texture2D(inputImageTexture, textureCoordinate + offsetB).b; \n\tfout.a = alphaLevel;\n\n    gl_FragColor = fout;\n}\n\n");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        if (!super.mo1321a()) {
            TXCLog.m2914e(f3004x, "onInit failed");
            return false;
        }
        this.f3005r = GLES20.glGetUniformLocation(this.f2612a, "textureTransform");
        this.f3009v = GLES20.glGetUniformLocation(this.f2612a, "alphaLevel");
        this.f3006s = GLES20.glGetUniformLocation(this.f2612a, "offsetR");
        this.f3007t = GLES20.glGetUniformLocation(this.f2612a, "offsetG");
        this.f3008u = GLES20.glGetUniformLocation(this.f2612a, "offsetB");
        m2723a(this.f3010w);
        return true;
    }

    /* renamed from: a */
    public void m2721a(float[] fArr, float[] fArr2, float[] fArr3) {
        m3030a(this.f3006s, fArr);
        m3030a(this.f3007t, fArr2);
        m3030a(this.f3008u, fArr3);
    }

    /* renamed from: a */
    public void m2723a(float f) {
        this.f3010w = f;
        m3035a(this.f3009v, this.f3010w);
    }

    /* renamed from: a */
    public void m2722a(float f, int i) {
        if (f <= 0.0f) {
            this.f3012z = (float[]) this.f3011y.clone();
            return;
        }
        this.f3012z = (float[]) this.f3011y.clone();
        for (int i2 = 0; i2 < i; i2++) {
            float[] fArr = new float[16];
            Matrix.setIdentityM(fArr, 0);
            Matrix.scaleM(fArr, 0, f, f, 1.0f);
            float[] fArr2 = this.f3012z;
            Matrix.multiplyMM(fArr2, 0, fArr, 0, fArr2, 0);
            Matrix.setIdentityM(fArr, 0);
            Matrix.translateM(fArr, 0, 0.02f, 0.02f, 1.0f);
            float[] fArr3 = this.f3012z;
            Matrix.multiplyMM(fArr3, 0, fArr, 0, fArr3, 0);
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo2294a(int i) {
        return mo1355a(i, this.f2624m, this.f2625n);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1324a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.f2612a);
        m3017k();
        if (!m3014n() || this.f3012z == null) {
            return;
        }
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(this.f2613b, 2, 5126, false, 0, (Buffer) floatBuffer);
        GLES20.glEnableVertexAttribArray(this.f2613b);
        floatBuffer2.position(0);
        GLES20.glVertexAttribPointer(this.f2615d, 2, 5126, false, 0, (Buffer) floatBuffer2);
        GLES20.glEnableVertexAttribArray(this.f2615d);
        GLES20.glUniformMatrix4fv(this.f3005r, 1, false, this.f3012z, 0);
        if (i != -1) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, i);
            GLES20.glUniform1i(this.f2614c, 0);
        }
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.f2613b);
        GLES20.glDisableVertexAttribArray(this.f2615d);
        GLES20.glBindTexture(3553, 0);
    }
}
