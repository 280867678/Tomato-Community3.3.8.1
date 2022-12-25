package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.p115b.TXCGPUWatermarkFilter;
import com.tencent.liteav.p126k.TXCGPUWatermarkTextureFilter;
import java.nio.Buffer;

/* renamed from: com.tencent.liteav.beauty.b.ag */
/* loaded from: classes3.dex */
public class TXCGPUWatermarkAlphaTextureFilter extends TXCGPUWatermarkTextureFilter {

    /* renamed from: x */
    private static String f2982x = "varying lowp vec2 textureCoordinate;\n   \n  uniform sampler2D inputImageTexture;\n  uniform mediump float alphaBlend;\n  \n  void main()\n  {\n      mediump vec4 color = texture2D(inputImageTexture, textureCoordinate);\n       if (0.0 == color.a){\n            gl_FragColor = color;\n       }else{\n            gl_FragColor = vec4(color.rgb, alphaBlend);\n       } \n  }\n";

    /* renamed from: z */
    private static String f2983z = "AlphaTexture";

    /* renamed from: y */
    private int f2985y = -1;

    /* renamed from: A */
    private boolean f2984A = false;

    public TXCGPUWatermarkAlphaTextureFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        if (!super.mo1321a()) {
            TXCLog.m2914e(f2983z, "onInit failed!");
            return false;
        }
        this.f2985y = GLES20.glGetUniformLocation(this.f2612a, "alphaBlend");
        m2733a(1.0f);
        return true;
    }

    /* renamed from: a */
    public void m2733a(float f) {
        m3035a(this.f2985y, f);
    }

    /* renamed from: c */
    public void m2732c(boolean z) {
        this.f2984A = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.beauty.p115b.TXCGPUWatermarkFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: j */
    public void mo2656j() {
        if (!((TXCGPUWatermarkFilter) this).f2992t || ((TXCGPUWatermarkFilter) this).f2990r == null) {
            return;
        }
        GLES20.glEnable(3042);
        if (true == this.f2984A) {
            GLES20.glBlendFunc(773, 772);
        } else {
            GLES20.glBlendFunc(770, 771);
        }
        GLES20.glActiveTexture(33984);
        int i = 0;
        while (true) {
            TXCGPUWatermarkFilter.C3383a[] c3383aArr = ((TXCGPUWatermarkFilter) this).f2990r;
            if (i < c3383aArr.length) {
                if (c3383aArr[i] != null) {
                    GLES20.glBindTexture(3553, c3383aArr[i].f3000d[0]);
                    GLES20.glUniform1i(this.f2614c, 0);
                    GLES20.glVertexAttribPointer(this.f2613b, 2, 5126, false, 8, (Buffer) ((TXCGPUWatermarkFilter) this).f2990r[i].f2997a);
                    GLES20.glEnableVertexAttribArray(this.f2613b);
                    GLES20.glVertexAttribPointer(this.f2615d, 4, 5126, false, 16, (Buffer) ((TXCGPUWatermarkFilter) this).f2990r[i].f2998b);
                    GLES20.glEnableVertexAttribArray(this.f2615d);
                    GLES20.glDrawElements(4, TXCGPUWatermarkFilter.f2987v.length, 5123, ((TXCGPUWatermarkFilter) this).f2994w);
                    GLES20.glDisableVertexAttribArray(this.f2613b);
                    GLES20.glDisableVertexAttribArray(this.f2615d);
                }
                i++;
            } else {
                GLES20.glDisable(3042);
                return;
            }
        }
    }
}
