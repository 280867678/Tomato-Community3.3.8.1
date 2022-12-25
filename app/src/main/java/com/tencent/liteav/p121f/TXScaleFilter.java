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

/* renamed from: com.tencent.liteav.f.i */
/* loaded from: classes3.dex */
public class TXScaleFilter {

    /* renamed from: h */
    private boolean f3953h;

    /* renamed from: n */
    private int f3959n;

    /* renamed from: q */
    private int f3962q;

    /* renamed from: r */
    private int f3963r;

    /* renamed from: s */
    private int f3964s;

    /* renamed from: t */
    private int f3965t;

    /* renamed from: a */
    private int f3946a = 0;

    /* renamed from: b */
    private int f3947b = 0;

    /* renamed from: c */
    private int f3948c = 0;

    /* renamed from: d */
    private int f3949d = 0;

    /* renamed from: e */
    private float[] f3950e = new float[16];

    /* renamed from: f */
    private float[] f3951f = new float[16];

    /* renamed from: g */
    private boolean f3952g = false;

    /* renamed from: i */
    private boolean f3954i = true;

    /* renamed from: j */
    private final float[] f3955j = {-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};

    /* renamed from: l */
    private float[] f3957l = new float[16];

    /* renamed from: m */
    private float[] f3958m = new float[16];

    /* renamed from: o */
    private int f3960o = -12345;

    /* renamed from: p */
    private int f3961p = -12345;

    /* renamed from: k */
    private FloatBuffer f3956k = ByteBuffer.allocateDirect(this.f3955j.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

    /* renamed from: a */
    public void m1832a(int i, int i2) {
        if (i == this.f3946a && i2 == this.f3947b) {
            return;
        }
        TXCLog.m2915d("TXScaleFilter", "Output resolution change: " + this.f3946a + Marker.ANY_MARKER + this.f3947b + " -> " + i + Marker.ANY_MARKER + i2);
        this.f3946a = i;
        this.f3947b = i2;
        if (i > i2) {
            Matrix.orthoM(this.f3950e, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
        } else {
            Matrix.orthoM(this.f3950e, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
        }
        this.f3952g = true;
    }

    /* renamed from: b */
    public void m1825b(int i, int i2) {
        if (i == this.f3948c && i2 == this.f3949d) {
            return;
        }
        TXCLog.m2915d("TXScaleFilter", "Input resolution change: " + this.f3948c + Marker.ANY_MARKER + this.f3949d + " -> " + i + Marker.ANY_MARKER + i2);
        this.f3948c = i;
        this.f3949d = i2;
    }

    /* renamed from: a */
    private void m1828a(float[] fArr) {
        int i;
        int i2 = this.f3947b;
        if (i2 == 0 || (i = this.f3946a) == 0) {
            return;
        }
        float f = this.f3948c;
        float f2 = (i * 1.0f) / f;
        float f3 = this.f3949d;
        float f4 = (i2 * 1.0f) / f3;
        if (f2 * f3 <= i2) {
            f2 = f4;
        }
        Matrix.setIdentityM(this.f3951f, 0);
        if (this.f3954i) {
            Matrix.scaleM(this.f3951f, 0, ((f * f2) / this.f3946a) * 1.0f, ((f3 * f2) / this.f3947b) * 1.0f, 1.0f);
        } else {
            Matrix.scaleM(this.f3951f, 0, ((f * f2) / this.f3946a) * 1.0f, ((f3 * f2) / this.f3947b) * 1.0f, 1.0f);
        }
        Matrix.multiplyMM(fArr, 0, this.f3950e, 0, this.f3951f, 0);
    }

    public TXScaleFilter(Boolean bool) {
        this.f3953h = true;
        this.f3953h = bool.booleanValue();
        this.f3956k.put(this.f3955j).position(0);
        Matrix.setIdentityM(this.f3958m, 0);
    }

    /* renamed from: a */
    public void m1833a(int i) {
        GLES20.glViewport(0, 0, this.f3946a, this.f3947b);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glUseProgram(this.f3959n);
        m1830a("glUseProgram");
        if (this.f3953h) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i);
        } else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, i);
        }
        this.f3956k.position(0);
        GLES20.glVertexAttribPointer(this.f3964s, 3, 5126, false, 20, (Buffer) this.f3956k);
        m1830a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.f3964s);
        m1830a("glEnableVertexAttribArray maPositionHandle");
        this.f3956k.position(3);
        GLES20.glVertexAttribPointer(this.f3965t, 2, 5126, false, 20, (Buffer) this.f3956k);
        m1830a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.f3965t);
        m1830a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.f3957l, 0);
        m1828a(this.f3957l);
        GLES20.glUniformMatrix4fv(this.f3962q, 1, false, this.f3957l, 0);
        GLES20.glUniformMatrix4fv(this.f3963r, 1, false, this.f3958m, 0);
        m1830a("glDrawArrays");
        GLES20.glDrawArrays(5, 0, 4);
        m1830a("glDrawArrays");
        if (this.f3953h) {
            GLES20.glBindTexture(36197, 0);
        } else {
            GLES20.glBindTexture(3553, 0);
        }
    }

    /* renamed from: b */
    public int m1826b(int i) {
        m1824c();
        int i2 = this.f3961p;
        if (i2 == -12345) {
            TXCLog.m2915d("TXScaleFilter", "invalid frame buffer id");
            return i;
        }
        GLES20.glBindFramebuffer(36160, i2);
        m1833a(i);
        GLES20.glBindFramebuffer(36160, 0);
        return this.f3960o;
    }

    /* renamed from: a */
    public void m1834a() {
        if (this.f3953h) {
            this.f3959n = m1829a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        } else {
            this.f3959n = m1829a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        int i = this.f3959n;
        if (i == 0) {
            throw new RuntimeException("failed creating program");
        }
        this.f3964s = GLES20.glGetAttribLocation(i, "aPosition");
        m1830a("glGetAttribLocation aPosition");
        if (this.f3964s == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        this.f3965t = GLES20.glGetAttribLocation(this.f3959n, "aTextureCoord");
        m1830a("glGetAttribLocation aTextureCoord");
        if (this.f3965t == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }
        this.f3962q = GLES20.glGetUniformLocation(this.f3959n, "uMVPMatrix");
        m1830a("glGetUniformLocation uMVPMatrix");
        if (this.f3962q == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
        this.f3963r = GLES20.glGetUniformLocation(this.f3959n, "uSTMatrix");
        m1830a("glGetUniformLocation uSTMatrix");
        if (this.f3963r == -1) {
            throw new RuntimeException("Could not get attrib location for uSTMatrix");
        }
    }

    /* renamed from: c */
    private void m1824c() {
        if (!this.f3952g) {
            return;
        }
        TXCLog.m2915d("TXScaleFilter", "reloadFrameBuffer. size = " + this.f3946a + Marker.ANY_MARKER + this.f3947b);
        m1823d();
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glGenFramebuffers(1, iArr2, 0);
        this.f3960o = iArr[0];
        this.f3961p = iArr2[0];
        TXCLog.m2915d("TXScaleFilter", "frameBuffer id = " + this.f3961p + ", texture id = " + this.f3960o);
        GLES20.glBindTexture(3553, this.f3960o);
        m1830a("glBindTexture mFrameBufferTextureID");
        GLES20.glTexImage2D(3553, 0, 6408, this.f3946a, this.f3947b, 0, 6408, 5121, null);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        m1830a("glTexParameter");
        GLES20.glBindFramebuffer(36160, this.f3961p);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.f3960o, 0);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        this.f3952g = false;
    }

    /* renamed from: b */
    public void m1827b() {
        GLES20.glDeleteProgram(this.f3959n);
        m1823d();
    }

    /* renamed from: d */
    private void m1823d() {
        int i = this.f3961p;
        if (i != -12345) {
            GLES20.glDeleteFramebuffers(1, new int[]{i}, 0);
            this.f3961p = -12345;
        }
        int i2 = this.f3960o;
        if (i2 != -12345) {
            GLES20.glDeleteTextures(1, new int[]{i2}, 0);
            this.f3960o = -12345;
        }
    }

    /* renamed from: a */
    private int m1831a(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        m1830a("glCreateShader type=" + i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] == 0) {
            TXCLog.m2914e("TXScaleFilter", "Could not compile shader " + i + ":");
            StringBuilder sb = new StringBuilder();
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(GLES20.glGetShaderInfoLog(glCreateShader));
            TXCLog.m2914e("TXScaleFilter", sb.toString());
            GLES20.glDeleteShader(glCreateShader);
            return 0;
        }
        return glCreateShader;
    }

    /* renamed from: a */
    private int m1829a(String str, String str2) {
        int m1831a;
        int m1831a2 = m1831a(35633, str);
        if (m1831a2 == 0 || (m1831a = m1831a(35632, str2)) == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        m1830a("glCreateProgram");
        if (glCreateProgram == 0) {
            TXCLog.m2914e("TXScaleFilter", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, m1831a2);
        m1830a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, m1831a);
        m1830a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 1) {
            return glCreateProgram;
        }
        TXCLog.m2914e("TXScaleFilter", "Could not link program: ");
        TXCLog.m2914e("TXScaleFilter", GLES20.glGetProgramInfoLog(glCreateProgram));
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }

    /* renamed from: a */
    private void m1830a(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError == 0) {
            return;
        }
        TXCLog.m2914e("TXScaleFilter", str + ": glError " + glGetError);
        throw new RuntimeException(str + ": glError " + glGetError);
    }
}
