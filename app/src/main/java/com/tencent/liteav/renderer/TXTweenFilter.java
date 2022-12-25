package com.tencent.liteav.renderer;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.log.TXCLog;
import com.tomatolive.library.utils.ConstantUtils;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import org.slf4j.Marker;

/* renamed from: com.tencent.liteav.renderer.i */
/* loaded from: classes3.dex */
public class TXTweenFilter {

    /* renamed from: a */
    public static int f5176a = 1;

    /* renamed from: b */
    public static int f5177b = 2;

    /* renamed from: o */
    private boolean f5190o;

    /* renamed from: t */
    private int f5195t;

    /* renamed from: w */
    private int f5198w;

    /* renamed from: x */
    private int f5199x;

    /* renamed from: y */
    private int f5200y;

    /* renamed from: z */
    private int f5201z;

    /* renamed from: c */
    private int f5178c = 0;

    /* renamed from: d */
    private int f5179d = 0;

    /* renamed from: e */
    private int f5180e = 0;

    /* renamed from: f */
    private int f5181f = 0;

    /* renamed from: g */
    private int f5182g = f5177b;

    /* renamed from: h */
    private int f5183h = 0;

    /* renamed from: i */
    private boolean f5184i = false;

    /* renamed from: j */
    private float[] f5185j = new float[16];

    /* renamed from: k */
    private float[] f5186k = new float[16];

    /* renamed from: l */
    private float f5187l = 1.0f;

    /* renamed from: m */
    private float f5188m = 1.0f;

    /* renamed from: n */
    private boolean f5189n = false;

    /* renamed from: p */
    private final float[] f5191p = {-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};

    /* renamed from: r */
    private float[] f5193r = new float[16];

    /* renamed from: s */
    private float[] f5194s = new float[16];

    /* renamed from: u */
    private int f5196u = -12345;

    /* renamed from: v */
    private int f5197v = -12345;

    /* renamed from: q */
    private FloatBuffer f5192q = ByteBuffer.allocateDirect(this.f5191p.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

    /* renamed from: a */
    public void m856a(int i, int i2) {
        if (i == this.f5178c && i2 == this.f5179d) {
            return;
        }
        TXCLog.m2915d("TXTweenFilter", "Output resolution change: " + this.f5178c + Marker.ANY_MARKER + this.f5179d + " -> " + i + Marker.ANY_MARKER + i2);
        this.f5178c = i;
        this.f5179d = i2;
        if (i > i2) {
            Matrix.orthoM(this.f5185j, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
            this.f5187l = 1.0f;
            this.f5188m = 1.0f;
        } else {
            Matrix.orthoM(this.f5185j, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
            this.f5187l = 1.0f;
            this.f5188m = 1.0f;
        }
        this.f5189n = true;
    }

    /* renamed from: b */
    public void m849b(int i, int i2) {
        if (i == this.f5180e && i2 == this.f5181f) {
            return;
        }
        TXCLog.m2915d("TXTweenFilter", "Input resolution change: " + this.f5180e + Marker.ANY_MARKER + this.f5181f + " -> " + i + Marker.ANY_MARKER + i2);
        this.f5180e = i;
        this.f5181f = i2;
    }

    /* renamed from: a */
    public boolean m858a() {
        return this.f5190o;
    }

    /* renamed from: a */
    public void m857a(int i) {
        this.f5182g = i;
    }

    /* renamed from: b */
    public void m850b(int i) {
        this.f5183h = i;
    }

    /* renamed from: b */
    private void m848b(float[] fArr) {
        if (this.f5179d == 0 || this.f5178c == 0) {
            return;
        }
        int i = this.f5180e;
        int i2 = this.f5181f;
        int i3 = this.f5183h;
        if (i3 == 270 || i3 == 90) {
            i = this.f5181f;
            i2 = this.f5180e;
        }
        float f = i;
        float f2 = (this.f5178c * 1.0f) / f;
        int i4 = this.f5179d;
        float f3 = i2;
        float f4 = (i4 * 1.0f) / f3;
        if (this.f5182g != f5176a ? f2 * f3 > i4 : f2 * f3 <= i4) {
            f2 = f4;
        }
        Matrix.setIdentityM(this.f5186k, 0);
        if (this.f5184i) {
            if (this.f5183h % 180 == 0) {
                Matrix.scaleM(this.f5186k, 0, -1.0f, 1.0f, 1.0f);
            } else {
                Matrix.scaleM(this.f5186k, 0, 1.0f, -1.0f, 1.0f);
            }
        }
        Matrix.scaleM(this.f5186k, 0, ((f * f2) / this.f5178c) * 1.0f, ((f3 * f2) / this.f5179d) * 1.0f, 1.0f);
        Matrix.rotateM(this.f5186k, 0, this.f5183h, 0.0f, 0.0f, -1.0f);
        Matrix.multiplyMM(fArr, 0, this.f5185j, 0, this.f5186k, 0);
    }

    public TXTweenFilter(Boolean bool) {
        this.f5190o = true;
        this.f5190o = bool.booleanValue();
        this.f5192q.put(this.f5191p).position(0);
        Matrix.setIdentityM(this.f5194s, 0);
    }

    /* renamed from: a */
    public void m852a(float[] fArr) {
        this.f5194s = fArr;
    }

    /* renamed from: c */
    public void m846c(int i) {
        GLES20.glViewport(0, 0, this.f5178c, this.f5179d);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glUseProgram(this.f5195t);
        m854a("glUseProgram");
        if (this.f5190o) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i);
        } else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, i);
        }
        this.f5192q.position(0);
        GLES20.glVertexAttribPointer(this.f5200y, 3, 5126, false, 20, (Buffer) this.f5192q);
        m854a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.f5200y);
        m854a("glEnableVertexAttribArray maPositionHandle");
        this.f5192q.position(3);
        GLES20.glVertexAttribPointer(this.f5201z, 2, 5126, false, 20, (Buffer) this.f5192q);
        m854a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.f5201z);
        m854a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.f5193r, 0);
        m848b(this.f5193r);
        GLES20.glUniformMatrix4fv(this.f5198w, 1, false, this.f5193r, 0);
        GLES20.glUniformMatrix4fv(this.f5199x, 1, false, this.f5194s, 0);
        m854a("glDrawArrays");
        GLES20.glDrawArrays(5, 0, 4);
        m854a("glDrawArrays");
        if (this.f5190o) {
            GLES20.glBindTexture(36197, 0);
        } else {
            GLES20.glBindTexture(3553, 0);
        }
    }

    /* renamed from: d */
    public int m844d(int i) {
        m845d();
        int i2 = this.f5197v;
        if (i2 == -12345) {
            TXCLog.m2915d("TXTweenFilter", "invalid frame buffer id");
            return i;
        }
        GLES20.glBindFramebuffer(36160, i2);
        m846c(i);
        GLES20.glBindFramebuffer(36160, 0);
        return this.f5196u;
    }

    /* renamed from: b */
    public void m851b() {
        if (this.f5190o) {
            this.f5195t = m853a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        } else {
            this.f5195t = m853a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        int i = this.f5195t;
        if (i == 0) {
            throw new RuntimeException("failed creating program");
        }
        this.f5200y = GLES20.glGetAttribLocation(i, "aPosition");
        m854a("glGetAttribLocation aPosition");
        if (this.f5200y == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        this.f5201z = GLES20.glGetAttribLocation(this.f5195t, "aTextureCoord");
        m854a("glGetAttribLocation aTextureCoord");
        if (this.f5201z == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }
        this.f5198w = GLES20.glGetUniformLocation(this.f5195t, "uMVPMatrix");
        m854a("glGetUniformLocation uMVPMatrix");
        if (this.f5198w == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
        this.f5199x = GLES20.glGetUniformLocation(this.f5195t, "uSTMatrix");
        m854a("glGetUniformLocation uSTMatrix");
        if (this.f5199x == -1) {
            throw new RuntimeException("Could not get attrib location for uSTMatrix");
        }
    }

    /* renamed from: d */
    private void m845d() {
        if (!this.f5189n) {
            return;
        }
        TXCLog.m2915d("TXTweenFilter", "reloadFrameBuffer. size = " + this.f5178c + Marker.ANY_MARKER + this.f5179d);
        m843e();
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glGenFramebuffers(1, iArr2, 0);
        this.f5196u = iArr[0];
        this.f5197v = iArr2[0];
        TXCLog.m2915d("TXTweenFilter", "frameBuffer id = " + this.f5197v + ", texture id = " + this.f5196u);
        GLES20.glBindTexture(3553, this.f5196u);
        m854a("glBindTexture mFrameBufferTextureID");
        GLES20.glTexImage2D(3553, 0, 6408, this.f5178c, this.f5179d, 0, 6408, 5121, null);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        m854a("glTexParameter");
        GLES20.glBindFramebuffer(36160, this.f5197v);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.f5196u, 0);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        this.f5189n = false;
    }

    /* renamed from: c */
    public void m847c() {
        GLES20.glDeleteProgram(this.f5195t);
        m843e();
    }

    /* renamed from: e */
    private void m843e() {
        int i = this.f5197v;
        if (i != -12345) {
            GLES20.glDeleteFramebuffers(1, new int[]{i}, 0);
            this.f5197v = -12345;
        }
        int i2 = this.f5196u;
        if (i2 != -12345) {
            GLES20.glDeleteTextures(1, new int[]{i2}, 0);
            this.f5196u = -12345;
        }
    }

    /* renamed from: a */
    private int m855a(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        m854a("glCreateShader type=" + i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] == 0) {
            TXCLog.m2914e("TXTweenFilter", "Could not compile shader " + i + ":");
            StringBuilder sb = new StringBuilder();
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(GLES20.glGetShaderInfoLog(glCreateShader));
            TXCLog.m2914e("TXTweenFilter", sb.toString());
            GLES20.glDeleteShader(glCreateShader);
            return 0;
        }
        return glCreateShader;
    }

    /* renamed from: a */
    private int m853a(String str, String str2) {
        int m855a;
        int m855a2 = m855a(35633, str);
        if (m855a2 == 0 || (m855a = m855a(35632, str2)) == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        m854a("glCreateProgram");
        if (glCreateProgram == 0) {
            TXCLog.m2914e("TXTweenFilter", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, m855a2);
        m854a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, m855a);
        m854a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 1) {
            return glCreateProgram;
        }
        TXCLog.m2914e("TXTweenFilter", "Could not link program: ");
        TXCLog.m2914e("TXTweenFilter", GLES20.glGetProgramInfoLog(glCreateProgram));
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }

    /* renamed from: a */
    private void m854a(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError == 0) {
            return;
        }
        TXCLog.m2914e("TXTweenFilter", str + ": glError " + glGetError);
        throw new RuntimeException(str + ": glError " + glGetError);
    }
}
