package com.tencent.liteav.p121f;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.log.TXCLog;
import com.tomatolive.library.utils.ConstantUtils;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import org.slf4j.Marker;

/* renamed from: com.tencent.liteav.f.l */
/* loaded from: classes3.dex */
public class VideoScaleFilter {

    /* renamed from: m */
    private boolean f4012m;

    /* renamed from: r */
    private int f4017r;

    /* renamed from: u */
    private int f4020u;

    /* renamed from: v */
    private int f4021v;

    /* renamed from: w */
    private int f4022w;

    /* renamed from: x */
    private int f4023x;

    /* renamed from: a */
    private int f4000a = 0;

    /* renamed from: b */
    private int f4001b = 0;

    /* renamed from: c */
    private int f4002c = 0;

    /* renamed from: d */
    private int f4003d = 0;

    /* renamed from: e */
    private int f4004e = 2;

    /* renamed from: f */
    private int f4005f = 0;

    /* renamed from: g */
    private boolean f4006g = false;

    /* renamed from: h */
    private float[] f4007h = new float[16];

    /* renamed from: i */
    private float[] f4008i = new float[16];

    /* renamed from: j */
    private float f4009j = 1.0f;

    /* renamed from: k */
    private float f4010k = 1.0f;

    /* renamed from: l */
    private boolean f4011l = false;

    /* renamed from: n */
    private final float[] f4013n = {-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};

    /* renamed from: p */
    private float[] f4015p = new float[16];

    /* renamed from: q */
    private float[] f4016q = new float[16];

    /* renamed from: s */
    private int f4018s = -12345;

    /* renamed from: t */
    private int f4019t = -12345;

    /* renamed from: o */
    private FloatBuffer f4014o = ByteBuffer.allocateDirect(this.f4013n.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

    /* renamed from: a */
    public void m1781a(int i, int i2) {
        if (i == this.f4000a && i2 == this.f4001b) {
            return;
        }
        TXCLog.m2915d("VideoScaleFilter", "Output resolution change: " + this.f4000a + Marker.ANY_MARKER + this.f4001b + " -> " + i + Marker.ANY_MARKER + i2);
        this.f4000a = i;
        this.f4001b = i2;
        if (i > i2) {
            Matrix.orthoM(this.f4007h, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
            this.f4009j = 1.0f;
            this.f4010k = 1.0f;
        } else {
            Matrix.orthoM(this.f4007h, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
            this.f4009j = 1.0f;
            this.f4010k = 1.0f;
        }
        this.f4011l = true;
    }

    /* renamed from: b */
    public void m1774b(int i, int i2) {
        if (i == this.f4002c && i2 == this.f4003d) {
            return;
        }
        TXCLog.m2915d("VideoScaleFilter", "Input resolution change: " + this.f4002c + Marker.ANY_MARKER + this.f4003d + " -> " + i + Marker.ANY_MARKER + i2);
        this.f4002c = i;
        this.f4003d = i2;
    }

    /* renamed from: a */
    public void m1782a(int i) {
        this.f4004e = i;
    }

    /* renamed from: b */
    public void m1775b(int i) {
        this.f4005f = i;
    }

    /* renamed from: a */
    private void m1777a(float[] fArr) {
        if (this.f4001b == 0 || this.f4000a == 0) {
            return;
        }
        int i = this.f4002c;
        int i2 = this.f4003d;
        int i3 = this.f4005f;
        if (i3 == 270 || i3 == 90) {
            i = this.f4003d;
            i2 = this.f4002c;
        }
        float f = i;
        float f2 = (this.f4000a * 1.0f) / f;
        int i4 = this.f4001b;
        float f3 = i2;
        float f4 = (i4 * 1.0f) / f3;
        if (this.f4004e != 1 ? f2 * f3 > i4 : f2 * f3 <= i4) {
            f2 = f4;
        }
        Matrix.setIdentityM(this.f4008i, 0);
        if (this.f4006g) {
            if (this.f4005f % 180 == 0) {
                Matrix.scaleM(this.f4008i, 0, -1.0f, 1.0f, 1.0f);
            } else {
                Matrix.scaleM(this.f4008i, 0, 1.0f, -1.0f, 1.0f);
            }
        }
        Matrix.scaleM(this.f4008i, 0, ((f * f2) / this.f4000a) * 1.0f, ((f3 * f2) / this.f4001b) * 1.0f, 1.0f);
        Matrix.rotateM(this.f4008i, 0, this.f4005f, 0.0f, 0.0f, -1.0f);
        Matrix.multiplyMM(fArr, 0, this.f4007h, 0, this.f4008i, 0);
    }

    public VideoScaleFilter(Boolean bool) {
        this.f4012m = true;
        this.f4012m = bool.booleanValue();
        this.f4014o.put(this.f4013n).position(0);
        Matrix.setIdentityM(this.f4016q, 0);
    }

    /* renamed from: c */
    public void m1772c(int i) {
        GLES20.glViewport(0, 0, this.f4000a, this.f4001b);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glUseProgram(this.f4017r);
        m1779a("glUseProgram");
        if (this.f4012m) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i);
        } else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, i);
        }
        this.f4014o.position(0);
        GLES20.glVertexAttribPointer(this.f4022w, 3, 5126, false, 20, (Buffer) this.f4014o);
        m1779a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.f4022w);
        m1779a("glEnableVertexAttribArray maPositionHandle");
        this.f4014o.position(3);
        GLES20.glVertexAttribPointer(this.f4023x, 2, 5126, false, 20, (Buffer) this.f4014o);
        m1779a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.f4023x);
        m1779a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.f4015p, 0);
        m1777a(this.f4015p);
        GLES20.glUniformMatrix4fv(this.f4020u, 1, false, this.f4015p, 0);
        GLES20.glUniformMatrix4fv(this.f4021v, 1, false, this.f4016q, 0);
        m1779a("glDrawArrays");
        GLES20.glDrawArrays(5, 0, 4);
        m1779a("glDrawArrays");
        if (this.f4012m) {
            GLES20.glBindTexture(36197, 0);
        } else {
            GLES20.glBindTexture(3553, 0);
        }
    }

    /* renamed from: d */
    public int m1770d(int i) {
        m1773c();
        int i2 = this.f4019t;
        if (i2 == -12345) {
            TXCLog.m2915d("VideoScaleFilter", "invalid frame buffer id");
            return i;
        }
        GLES20.glBindFramebuffer(36160, i2);
        m1772c(i);
        GLES20.glBindFramebuffer(36160, 0);
        return this.f4018s;
    }

    /* renamed from: a */
    public void m1783a() {
        if (this.f4012m) {
            this.f4017r = m1778a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        } else {
            this.f4017r = m1778a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        int i = this.f4017r;
        if (i == 0) {
            throw new RuntimeException("failed creating program");
        }
        this.f4022w = GLES20.glGetAttribLocation(i, "aPosition");
        m1779a("glGetAttribLocation aPosition");
        if (this.f4022w == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        this.f4023x = GLES20.glGetAttribLocation(this.f4017r, "aTextureCoord");
        m1779a("glGetAttribLocation aTextureCoord");
        if (this.f4023x == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }
        this.f4020u = GLES20.glGetUniformLocation(this.f4017r, "uMVPMatrix");
        m1779a("glGetUniformLocation uMVPMatrix");
        if (this.f4020u == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
        this.f4021v = GLES20.glGetUniformLocation(this.f4017r, "uSTMatrix");
        m1779a("glGetUniformLocation uSTMatrix");
        if (this.f4021v == -1) {
            throw new RuntimeException("Could not get attrib location for uSTMatrix");
        }
    }

    /* renamed from: c */
    private void m1773c() {
        if (!this.f4011l) {
            return;
        }
        TXCLog.m2915d("VideoScaleFilter", "reloadFrameBuffer. size = " + this.f4000a + Marker.ANY_MARKER + this.f4001b);
        m1771d();
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glGenFramebuffers(1, iArr2, 0);
        this.f4018s = iArr[0];
        this.f4019t = iArr2[0];
        TXCLog.m2915d("VideoScaleFilter", "frameBuffer id = " + this.f4019t + ", texture id = " + this.f4018s);
        GLES20.glBindTexture(3553, this.f4018s);
        m1779a("glBindTexture mFrameBufferTextureID");
        GLES20.glTexImage2D(3553, 0, 6408, this.f4000a, this.f4001b, 0, 6408, 5121, null);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        m1779a("glTexParameter");
        GLES20.glBindFramebuffer(36160, this.f4019t);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.f4018s, 0);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        this.f4011l = false;
    }

    /* renamed from: b */
    public void m1776b() {
        GLES20.glDeleteProgram(this.f4017r);
        m1771d();
    }

    /* renamed from: d */
    private void m1771d() {
        int i = this.f4019t;
        if (i != -12345) {
            GLES20.glDeleteFramebuffers(1, new int[]{i}, 0);
            this.f4019t = -12345;
        }
        int i2 = this.f4018s;
        if (i2 != -12345) {
            GLES20.glDeleteTextures(1, new int[]{i2}, 0);
            this.f4018s = -12345;
        }
    }

    /* renamed from: a */
    private int m1780a(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        m1779a("glCreateShader type=" + i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] == 0) {
            TXCLog.m2914e("VideoScaleFilter", "Could not compile shader " + i + ":");
            StringBuilder sb = new StringBuilder();
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(GLES20.glGetShaderInfoLog(glCreateShader));
            TXCLog.m2914e("VideoScaleFilter", sb.toString());
            GLES20.glDeleteShader(glCreateShader);
            return 0;
        }
        return glCreateShader;
    }

    /* renamed from: a */
    private int m1778a(String str, String str2) {
        int m1780a;
        int m1780a2 = m1780a(35633, str);
        if (m1780a2 == 0 || (m1780a = m1780a(35632, str2)) == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        m1779a("glCreateProgram");
        if (glCreateProgram == 0) {
            TXCLog.m2914e("VideoScaleFilter", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, m1780a2);
        m1779a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, m1780a);
        m1779a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 1) {
            return glCreateProgram;
        }
        TXCLog.m2914e("VideoScaleFilter", "Could not link program: ");
        TXCLog.m2914e("VideoScaleFilter", GLES20.glGetProgramInfoLog(glCreateProgram));
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }

    /* renamed from: a */
    private void m1779a(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError == 0) {
            return;
        }
        TXCLog.m2914e("VideoScaleFilter", str + ": glError " + glGetError);
        throw new RuntimeException(str + ": glError " + glGetError);
    }
}
