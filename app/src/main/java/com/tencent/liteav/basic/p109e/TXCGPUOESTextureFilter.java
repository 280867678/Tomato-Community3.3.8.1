package com.tencent.liteav.basic.p109e;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.basic.e.h */
/* loaded from: classes3.dex */
public class TXCGPUOESTextureFilter extends TXCGPUFilter {

    /* renamed from: t */
    private int f2659t;

    /* renamed from: s */
    private float[] f2658s = new float[16];

    /* renamed from: r */
    public boolean f2657r = false;

    public TXCGPUOESTextureFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "#extension GL_OES_EGL_image_external : require\n\nvarying lowp vec2 textureCoordinate;\n \nuniform samplerExternalOES inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f2626o = true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f2659t = GLES20.glGetUniformLocation(this.f2612a, "textureTransform");
        return mo1321a && GLES20.glGetError() == 0;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo3010a(float[] fArr) {
        this.f2658s = fArr;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1324a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.f2612a);
        m3017k();
        if (!m3014n() || this.f2658s == null) {
            return;
        }
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(this.f2613b, 2, 5126, false, 0, (Buffer) floatBuffer);
        GLES20.glEnableVertexAttribArray(this.f2613b);
        floatBuffer2.position(0);
        GLES20.glVertexAttribPointer(this.f2615d, 2, 5126, false, 0, (Buffer) floatBuffer2);
        GLES20.glEnableVertexAttribArray(this.f2615d);
        GLES20.glUniformMatrix4fv(this.f2659t, 1, false, this.f2658s, 0);
        if (i != -1) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i);
            GLES20.glUniform1i(this.f2614c, 0);
        }
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.f2613b);
        GLES20.glDisableVertexAttribArray(this.f2615d);
        GLES20.glBindTexture(36197, 0);
    }
}
