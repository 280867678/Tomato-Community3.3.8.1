package com.tencent.liteav.p126k;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.k.a */
/* loaded from: classes3.dex */
public class TXCGPUAnHeiFilter extends TXCGPUFilter {

    /* renamed from: r */
    private int f4461r = -1;

    /* renamed from: a */
    public void m1372a(TXCVideoEffect.C3533a c3533a) {
    }

    public TXCGPUAnHeiFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "precision mediump float;  \nvarying vec2 textureCoordinate;  \nuniform sampler2D inputImageTexture;  \nuniform vec2 textureSize;  \nconst mat3 xKernal = mat3(-1.0, 0.0, 1.0,  \n-2.0, 0.0, 2.0,  \n-1.0, 0.0, 1.0);  \nconst mat3 yKernal = mat3(1.0, 2.0, 1.0,  \n0.0, 0.0, 0.0,  \n-1.0, -2.0, -1.0);  \nconst vec3 LW = vec3(0.2125, 0.7154, 0.0721);  \nfloat convolution3x3(float invalue[9], mat3 kernal)  \n{  \nfloat v = 0.0;  \nv += invalue[0] * kernal[0][0];  \nv += invalue[1] * kernal[0][1];  \nv += invalue[2] * kernal[0][2];  \nv += invalue[3] * kernal[1][0];  \nv += invalue[4] * kernal[1][1];  \nv += invalue[5] * kernal[1][2];  \nv += invalue[6] * kernal[2][0];  \nv += invalue[7] * kernal[2][1];  \nv += invalue[8] * kernal[2][2];  \nreturn v;  \n}  \nvoid main()  \n{  \nfloat gray[9];  \nvec2 offsets[9];  \noffsets[0] = vec2(-1.0, 1.0);  \noffsets[1] = vec2(0.0, 1.0);  \noffsets[2] = vec2(1.0, 1.0);  \noffsets[3] = vec2(-1.0, 0.0);  \noffsets[4] = vec2(0.0, 0.0);  \noffsets[5] = vec2(0.0, 1.0);  \noffsets[6] = vec2(-1.0, -1.0);  \noffsets[7] = vec2(0.0, -1.0);  \noffsets[8] = vec2(1.0, -1.0);  \nvec2 _step = vec2(1.0 / textureSize.x, 1.0 / textureSize.y);  \nfor (int i = 0; i < 9; ++i)  \n{  \ngray[i] = dot(texture2D(inputImageTexture, textureCoordinate + _step * offsets[i]).xyz, LW);  \n}  \nvec2 G = vec2(convolution3x3(gray, xKernal), convolution3x3(gray, yKernal));  \ngl_FragColor = vec4(vec3(length(G)), 1.0);  \n}  \n");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        super.mo1333a(i, i2);
        m3030a(this.f4461r, new float[]{this.f2616e, this.f2617f});
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        if (!super.mo1321a()) {
            return false;
        }
        this.f4461r = GLES20.glGetUniformLocation(this.f2612a, "textureSize");
        return true;
    }
}
