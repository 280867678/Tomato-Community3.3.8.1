package com.tencent.liteav.beauty.p115b;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.beauty.b.r */
/* loaded from: classes3.dex */
public class TXCGPULookupFilterGroup extends TXCGPUFilter {

    /* renamed from: A */
    private int f3117A;

    /* renamed from: B */
    private float[] f3118B;

    /* renamed from: C */
    private int f3119C;

    /* renamed from: D */
    private float[] f3120D;

    /* renamed from: r */
    private float f3121r;

    /* renamed from: s */
    private Bitmap f3122s;

    /* renamed from: t */
    private int f3123t;

    /* renamed from: u */
    private int f3124u;

    /* renamed from: v */
    private float f3125v;

    /* renamed from: w */
    private Bitmap f3126w;

    /* renamed from: x */
    private int f3127x;

    /* renamed from: y */
    private int f3128y;

    /* renamed from: z */
    private float f3129z;

    public TXCGPULookupFilterGroup(float f, Bitmap bitmap, float f2, Bitmap bitmap2, float f3) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2; // lookup texture 1\n uniform sampler2D inputImageTexture3; // lookup texture 2\n \n \n uniform lowp vec3 v3_params;\n uniform lowp vec2 v2_texs;\n \n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     mediump float blueColor = textureColor.b * 63.0;\n     \n     mediump vec2 quad1;\n     quad1.y = floor(floor(blueColor) / 8.0);\n     quad1.x = floor(blueColor) - (quad1.y * 8.0);\n     \n     mediump vec2 quad2;\n     quad2.y = floor(ceil(blueColor) / 8.0);\n     quad2.x = ceil(blueColor) - (quad2.y * 8.0);\n     \n     highp vec2 texPos1;\n     texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     highp vec2 texPos2;\n     texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     lowp vec4 newColor1;\n     lowp vec4 newColor2;\n     if(textureCoordinate.x <= v3_params.x) { \n       if(v2_texs.x == 1.0) { \n         newColor1 = texture2D(inputImageTexture2, texPos1);\n         newColor2 = texture2D(inputImageTexture2, texPos2);\n         lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n         gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), v3_params.y);\n       } else { \n         gl_FragColor = textureColor;\n       } \n     } else {\n       if(v2_texs.y == 1.0) { \n         newColor1 = texture2D(inputImageTexture3, texPos1);\n         newColor2 = texture2D(inputImageTexture3, texPos2);\n         lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n         gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), v3_params.z);\n       } else { \n         gl_FragColor = textureColor;\n       } \n     }\n }");
        this.f3118B = new float[3];
        this.f3120D = new float[2];
        this.f3121r = f;
        this.f3122s = bitmap;
        this.f3126w = bitmap2;
        this.f3125v = f2;
        this.f3129z = f3;
    }

    public TXCGPULookupFilterGroup(String str, String str2) {
        super(str, str2);
        this.f3124u = -1;
        this.f3128y = -1;
    }

    public TXCGPULookupFilterGroup() {
        this.f3124u = -1;
        this.f3128y = -1;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        this.f3123t = GLES20.glGetUniformLocation(m3011q(), "inputImageTexture2");
        this.f3127x = GLES20.glGetUniformLocation(m3011q(), "inputImageTexture3");
        this.f3117A = GLES20.glGetUniformLocation(m3011q(), "v3_params");
        this.f3119C = GLES20.glGetUniformLocation(m3011q(), "v2_texs");
        return super.mo1321a();
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: d */
    public void mo2643d() {
        super.mo2643d();
        m2667a(this.f3121r, this.f3122s, this.f3125v, this.f3126w, this.f3129z);
    }

    /* renamed from: a */
    public void m2667a(float f, final Bitmap bitmap, float f2, final Bitmap bitmap2, float f3) {
        m2668a(f, f2, f3);
        m3028a(new Runnable() { // from class: com.tencent.liteav.beauty.b.r.1
            @Override // java.lang.Runnable
            public void run() {
                TXCGPULookupFilterGroup.this.f3122s = bitmap;
                TXCGPULookupFilterGroup.this.f3126w = bitmap2;
                Bitmap bitmap3 = bitmap;
                if (bitmap3 == null) {
                    if (TXCGPULookupFilterGroup.this.f3124u != -1) {
                        GLES20.glDeleteTextures(1, new int[]{TXCGPULookupFilterGroup.this.f3124u}, 0);
                    }
                    TXCGPULookupFilterGroup.this.f3124u = -1;
                    TXCGPULookupFilterGroup.this.f3120D[0] = 0.0f;
                } else {
                    TXCGPULookupFilterGroup tXCGPULookupFilterGroup = TXCGPULookupFilterGroup.this;
                    tXCGPULookupFilterGroup.f3124u = TXCOpenGlUtils.m3003a(bitmap3, tXCGPULookupFilterGroup.f3124u, false);
                    TXCGPULookupFilterGroup.this.f3120D[0] = 1.0f;
                }
                Bitmap bitmap4 = bitmap2;
                if (bitmap4 == null) {
                    if (TXCGPULookupFilterGroup.this.f3128y != -1) {
                        GLES20.glDeleteTextures(1, new int[]{TXCGPULookupFilterGroup.this.f3128y}, 0);
                    }
                    TXCGPULookupFilterGroup.this.f3128y = -1;
                    TXCGPULookupFilterGroup.this.f3120D[1] = 0.0f;
                    return;
                }
                TXCGPULookupFilterGroup tXCGPULookupFilterGroup2 = TXCGPULookupFilterGroup.this;
                tXCGPULookupFilterGroup2.f3128y = TXCOpenGlUtils.m3003a(bitmap4, tXCGPULookupFilterGroup2.f3128y, false);
                TXCGPULookupFilterGroup.this.f3120D[1] = 1.0f;
            }
        });
    }

    /* renamed from: a */
    public void m2669a(float f) {
        m2668a(this.f3121r, f, 0.0f);
    }

    /* renamed from: a */
    public void m2668a(float f, float f2, float f3) {
        this.f3121r = f;
        this.f3125v = f2;
        this.f3129z = f3;
        float[] fArr = this.f3118B;
        fArr[0] = this.f3121r;
        fArr[1] = this.f3125v;
        fArr[2] = this.f3129z;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        super.mo2293b();
        GLES20.glDeleteTextures(2, new int[]{this.f3124u, this.f3128y}, 0);
        this.f3124u = -1;
        this.f3128y = -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: i */
    public void mo2657i() {
        if (this.f3124u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.f3124u);
            GLES20.glUniform1i(this.f3123t, 3);
        }
        if (this.f3128y != -1) {
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(3553, this.f3128y);
            GLES20.glUniform1i(this.f3127x, 4);
        }
        GLES20.glUniform2fv(this.f3119C, 1, FloatBuffer.wrap(this.f3120D));
        GLES20.glUniform3fv(this.f3117A, 1, FloatBuffer.wrap(this.f3118B));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: j */
    public void mo2656j() {
        if (this.f3124u != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
        if (this.f3128y != -1) {
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
    }
}
