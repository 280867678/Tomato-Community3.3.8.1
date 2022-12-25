package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.log.TXCLog;
import com.tomatolive.library.utils.ConstantUtils;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.renderer.c */
/* loaded from: classes3.dex */
public class TXCOesTextureRender {

    /* renamed from: c */
    private FloatBuffer f5105c;

    /* renamed from: f */
    private int f5108f;

    /* renamed from: h */
    private int f5110h;

    /* renamed from: i */
    private int f5111i;

    /* renamed from: j */
    private int f5112j;

    /* renamed from: k */
    private int f5113k;

    /* renamed from: m */
    private boolean f5115m;

    /* renamed from: a */
    private final float[] f5103a = {-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};

    /* renamed from: b */
    private final float[] f5104b = {1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f};

    /* renamed from: d */
    private float[] f5106d = new float[16];

    /* renamed from: e */
    private float[] f5107e = new float[16];

    /* renamed from: g */
    private int f5109g = -12345;

    /* renamed from: l */
    private boolean f5114l = false;

    /* renamed from: n */
    private boolean f5116n = false;

    /* renamed from: o */
    private int f5117o = -1;

    /* renamed from: p */
    private int f5118p = 0;

    /* renamed from: q */
    private int f5119q = 0;

    public TXCOesTextureRender(boolean z) {
        this.f5115m = true;
        this.f5115m = z;
        if (this.f5115m) {
            this.f5105c = ByteBuffer.allocateDirect(this.f5103a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.f5105c.put(this.f5103a).position(0);
        } else {
            this.f5105c = ByteBuffer.allocateDirect(this.f5104b.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.f5105c.put(this.f5104b).position(0);
        }
        Matrix.setIdentityM(this.f5107e, 0);
    }

    /* renamed from: a */
    public int m922a() {
        return this.f5109g;
    }

    /* renamed from: a */
    public void m918a(SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            return;
        }
        m917a("onDrawFrame start");
        surfaceTexture.getTransformMatrix(this.f5107e);
        m914b(36197, this.f5109g);
    }

    /* renamed from: a */
    public void m921a(int i, int i2) {
        this.f5118p = i;
        this.f5119q = i2;
    }

    /* renamed from: a */
    public void m919a(int i, boolean z, int i2) {
        if (this.f5116n != z || this.f5117o != i2) {
            this.f5116n = z;
            this.f5117o = i2;
            float[] fArr = new float[20];
            for (int i3 = 0; i3 < 20; i3++) {
                fArr[i3] = this.f5104b[i3];
            }
            if (this.f5116n) {
                fArr[0] = -fArr[0];
                fArr[5] = -fArr[5];
                fArr[10] = -fArr[10];
                fArr[15] = -fArr[15];
            }
            int i4 = i2 / 90;
            for (int i5 = 0; i5 < i4; i5++) {
                float f = fArr[3];
                float f2 = fArr[4];
                fArr[3] = fArr[8];
                fArr[4] = fArr[9];
                fArr[8] = fArr[18];
                fArr[9] = fArr[19];
                fArr[18] = fArr[13];
                fArr[19] = fArr[14];
                fArr[13] = f;
                fArr[14] = f2;
            }
            this.f5105c.clear();
            this.f5105c.put(fArr).position(0);
        }
        m914b(3553, i);
    }

    /* renamed from: b */
    private void m914b(int i, int i2) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        if (this.f5114l) {
            this.f5114l = false;
            return;
        }
        GLES20.glUseProgram(this.f5108f);
        m917a("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(i, i2);
        this.f5105c.position(0);
        GLES20.glVertexAttribPointer(this.f5112j, 3, 5126, false, 20, (Buffer) this.f5105c);
        m917a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.f5112j);
        m917a("glEnableVertexAttribArray maPositionHandle");
        this.f5105c.position(3);
        GLES20.glVertexAttribPointer(this.f5113k, 2, 5126, false, 20, (Buffer) this.f5105c);
        m917a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.f5113k);
        m917a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.f5106d, 0);
        GLES20.glUniformMatrix4fv(this.f5110h, 1, false, this.f5106d, 0);
        int i3 = this.f5118p;
        if (i3 % 8 != 0) {
            Matrix.scaleM(this.f5107e, 0, ((i3 - 1) * 1.0f) / (((i3 + 7) / 8) * 8), 1.0f, 1.0f);
        }
        int i4 = this.f5119q;
        if (i4 % 8 != 0) {
            Matrix.scaleM(this.f5107e, 0, 1.0f, ((i4 - 1) * 1.0f) / (((i4 + 7) / 8) * 8), 1.0f);
        }
        GLES20.glUniformMatrix4fv(this.f5111i, 1, false, this.f5107e, 0);
        GLES20.glDrawArrays(5, 0, 4);
        m917a("glDrawArrays");
        GLES20.glFinish();
    }

    /* renamed from: b */
    public void m915b() {
        if (this.f5115m) {
            this.f5108f = m916a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        } else {
            this.f5108f = m916a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        int i = this.f5108f;
        if (i == 0) {
            throw new RuntimeException("failed creating program");
        }
        this.f5112j = GLES20.glGetAttribLocation(i, "aPosition");
        m917a("glGetAttribLocation aPosition");
        if (this.f5112j == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        this.f5113k = GLES20.glGetAttribLocation(this.f5108f, "aTextureCoord");
        m917a("glGetAttribLocation aTextureCoord");
        if (this.f5113k == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }
        this.f5110h = GLES20.glGetUniformLocation(this.f5108f, "uMVPMatrix");
        m917a("glGetUniformLocation uMVPMatrix");
        if (this.f5110h == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
        this.f5111i = GLES20.glGetUniformLocation(this.f5108f, "uSTMatrix");
        m917a("glGetUniformLocation uSTMatrix");
        if (this.f5111i == -1) {
            throw new RuntimeException("Could not get attrib location for uSTMatrix");
        }
        if (this.f5115m) {
            m912d();
        }
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        m917a("glTexParameter");
    }

    /* renamed from: c */
    public void m913c() {
        int i = this.f5108f;
        if (i != 0) {
            GLES20.glDeleteProgram(i);
        }
        GLES20.glDeleteTextures(1, new int[]{this.f5109g}, 0);
        this.f5109g = -1;
    }

    /* renamed from: d */
    private void m912d() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        this.f5109g = iArr[0];
        GLES20.glBindTexture(36197, this.f5109g);
        m917a("glBindTexture mTextureID");
    }

    /* renamed from: a */
    private int m920a(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        m917a("glCreateShader type=" + i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] == 0) {
            TXCLog.m2914e("TXCOesTextureRender", "Could not compile shader " + i + ":");
            StringBuilder sb = new StringBuilder();
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(GLES20.glGetShaderInfoLog(glCreateShader));
            TXCLog.m2914e("TXCOesTextureRender", sb.toString());
            GLES20.glDeleteShader(glCreateShader);
            return 0;
        }
        return glCreateShader;
    }

    /* renamed from: a */
    private int m916a(String str, String str2) {
        int m920a;
        int m920a2 = m920a(35633, str);
        if (m920a2 == 0 || (m920a = m920a(35632, str2)) == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        m917a("glCreateProgram");
        if (glCreateProgram == 0) {
            TXCLog.m2914e("TXCOesTextureRender", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, m920a2);
        m917a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, m920a);
        m917a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 1) {
            return glCreateProgram;
        }
        TXCLog.m2914e("TXCOesTextureRender", "Could not link program: ");
        TXCLog.m2914e("TXCOesTextureRender", GLES20.glGetProgramInfoLog(glCreateProgram));
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }

    /* renamed from: a */
    public void m917a(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            TXCLog.m2914e("TXCOesTextureRender", str + ": glError " + glGetError);
        }
    }
}
