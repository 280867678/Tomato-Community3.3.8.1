package com.tencent.liteav.beauty.p115b;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;

/* renamed from: com.tencent.liteav.beauty.b.s */
/* loaded from: classes3.dex */
public class TXCGPULoopupFilter extends TXCGPUFilter {

    /* renamed from: r */
    protected String f3133r;

    /* renamed from: s */
    protected Bitmap f3134s;

    /* renamed from: t */
    public int f3135t;

    /* renamed from: u */
    public int f3136u;

    /* renamed from: v */
    protected int f3137v;

    /* renamed from: w */
    protected float f3138w;

    public TXCGPULoopupFilter(Bitmap bitmap) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2; // lookup texture\n \n \n uniform lowp float intensity;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     mediump float blueColor = textureColor.b * 63.0;\n     \n     mediump vec2 quad1;\n     quad1.y = floor(floor(blueColor) / 8.0);\n     quad1.x = floor(blueColor) - (quad1.y * 8.0);\n     \n     mediump vec2 quad2;\n     quad2.y = floor(ceil(blueColor) / 8.0);\n     quad2.x = ceil(blueColor) - (quad2.y * 8.0);\n     \n     highp vec2 texPos1;\n     texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     highp vec2 texPos2;\n     texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     lowp vec4 newColor1 = texture2D(inputImageTexture2, texPos1);\n     lowp vec4 newColor2 = texture2D(inputImageTexture2, texPos2);\n     \n     lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n     gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), intensity);\n }");
        this.f3134s = bitmap;
    }

    public TXCGPULoopupFilter(String str, String str2) {
        super(str, str2);
        this.f3133r = null;
        this.f3134s = null;
        this.f3136u = -1;
        this.f3137v = -1;
    }

    public TXCGPULoopupFilter() {
        this.f3133r = null;
        this.f3134s = null;
        this.f3136u = -1;
        this.f3137v = -1;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        this.f3135t = GLES20.glGetUniformLocation(m3011q(), "inputImageTexture2");
        this.f3137v = GLES20.glGetUniformLocation(m3011q(), "intensity");
        this.f3138w = 0.5f;
        return super.mo1321a();
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: d */
    public void mo2643d() {
        super.mo2643d();
        m2658a(this.f3134s);
        m2659a(this.f3138w);
    }

    /* renamed from: a */
    public void m2658a(final Bitmap bitmap) {
        m3028a(new Runnable() { // from class: com.tencent.liteav.beauty.b.s.1
            @Override // java.lang.Runnable
            public void run() {
                Bitmap bitmap2 = bitmap;
                if (bitmap2 != null) {
                    TXCGPULoopupFilter tXCGPULoopupFilter = TXCGPULoopupFilter.this;
                    tXCGPULoopupFilter.f3136u = TXCOpenGlUtils.m3003a(bitmap2, tXCGPULoopupFilter.f3136u, false);
                }
            }
        });
    }

    /* renamed from: a */
    public void m2659a(float f) {
        this.f3138w = f;
        m3035a(this.f3137v, this.f3138w);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        super.mo2293b();
        GLES20.glDeleteTextures(1, new int[]{this.f3136u}, 0);
        this.f3136u = -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: j */
    public void mo2656j() {
        if (this.f3136u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: i */
    public void mo2657i() {
        if (this.f3136u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.f3136u);
            GLES20.glUniform1i(this.f3135t, 3);
        }
    }
}
