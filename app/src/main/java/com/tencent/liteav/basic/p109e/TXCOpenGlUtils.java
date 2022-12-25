package com.tencent.liteav.basic.p109e;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import com.tencent.liteav.basic.log.TXCLog;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* renamed from: com.tencent.liteav.basic.e.i */
/* loaded from: classes3.dex */
public class TXCOpenGlUtils {

    /* renamed from: f */
    private static float[] f2665f = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};

    /* renamed from: g */
    private static float[] f2666g = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};

    /* renamed from: h */
    private static float[] f2667h = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f};

    /* renamed from: i */
    private static float[] f2668i = {1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};

    /* renamed from: j */
    private static float[] f2669j = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};

    /* renamed from: a */
    public static FloatBuffer f2660a = m2998a(f2665f);

    /* renamed from: b */
    public static FloatBuffer f2661b = m2998a(f2666g);

    /* renamed from: c */
    public static FloatBuffer f2662c = m2998a(f2667h);

    /* renamed from: d */
    public static FloatBuffer f2663d = m2998a(f2668i);

    /* renamed from: e */
    public static FloatBuffer f2664e = m2998a(f2669j);

    /* renamed from: k */
    private static int f2670k = 2;

    /* compiled from: TXCOpenGlUtils.java */
    /* renamed from: com.tencent.liteav.basic.e.i$a */
    /* loaded from: classes3.dex */
    public static class C3356a {

        /* renamed from: a */
        public int[] f2671a = null;

        /* renamed from: b */
        public int[] f2672b = null;

        /* renamed from: c */
        public int f2673c = -1;

        /* renamed from: d */
        public int f2674d = -1;
    }

    /* compiled from: TXCOpenGlUtils.java */
    /* renamed from: com.tencent.liteav.basic.e.i$b */
    /* loaded from: classes3.dex */
    public interface AbstractC3357b {
    }

    /* renamed from: a */
    public static void m3008a(int i) {
        f2670k = i;
    }

    /* renamed from: a */
    public static final int m3009a() {
        return f2670k;
    }

    /* renamed from: a */
    public static void m2996a(C3356a[] c3356aArr) {
        if (c3356aArr != null) {
            for (C3356a c3356a : c3356aArr) {
                if (c3356a != null) {
                    m3002a(c3356a);
                }
            }
        }
    }

    /* renamed from: a */
    public static C3356a[] m2995a(C3356a[] c3356aArr, int i, int i2, int i3) {
        if (c3356aArr == null) {
            c3356aArr = new C3356a[i];
        }
        for (int i4 = 0; i4 < c3356aArr.length; i4++) {
            c3356aArr[i4] = m3001a(c3356aArr[i4], i2, i3);
        }
        return c3356aArr;
    }

    /* renamed from: a */
    public static C3356a m3001a(C3356a c3356a, int i, int i2) {
        if (c3356a == null) {
            c3356a = new C3356a();
        }
        if (c3356a.f2671a == null) {
            c3356a.f2671a = new int[1];
        }
        if (c3356a.f2672b == null) {
            c3356a.f2672b = new int[1];
        }
        c3356a.f2673c = i;
        c3356a.f2674d = i2;
        m2997a(c3356a.f2671a, c3356a.f2672b, c3356a.f2673c, c3356a.f2674d);
        return c3356a;
    }

    /* renamed from: a */
    public static C3356a m3002a(C3356a c3356a) {
        if (c3356a != null) {
            int[] iArr = c3356a.f2671a;
            if (iArr != null) {
                GLES20.glDeleteFramebuffers(1, iArr, 0);
                c3356a.f2671a = null;
            }
            int[] iArr2 = c3356a.f2672b;
            if (iArr2 != null) {
                GLES20.glDeleteTextures(1, iArr2, 0);
                c3356a.f2672b = null;
            }
            return null;
        }
        return c3356a;
    }

    /* renamed from: a */
    public static int m3005a(int i, int i2, int i3, int i4, int[] iArr) {
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(3553, iArr[0]);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexImage2D(3553, 0, i3, i, i2, 0, i4, 5121, null);
        return iArr[0];
    }

    /* renamed from: a */
    public static FloatBuffer m2998a(float[] fArr) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.put(fArr);
        asFloatBuffer.position(0);
        return asFloatBuffer;
    }

    /* renamed from: a */
    public static int m3006a(int i, int i2, int i3, int i4, IntBuffer intBuffer) {
        int m2993c = m2993c();
        GLES20.glBindTexture(3553, m2993c);
        GLES20.glTexImage2D(3553, 0, i3, i, i2, 0, i4, 5121, intBuffer);
        GLES20.glBindTexture(3553, 0);
        return m2993c;
    }

    /* renamed from: a */
    public static int m3007a(int i, int i2, int i3, int i4) {
        return m3006a(i, i2, i3, i4, (IntBuffer) null);
    }

    /* renamed from: a */
    public static int m3003a(Bitmap bitmap, int i, boolean z) {
        int[] iArr = new int[1];
        if (i == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLUtils.texImage2D(3553, 0, bitmap, 0);
        } else {
            GLES20.glBindTexture(3553, i);
            GLUtils.texSubImage2D(3553, 0, 0, 0, bitmap);
            iArr[0] = i;
        }
        if (z) {
            bitmap.recycle();
        }
        return iArr[0];
    }

    /* renamed from: a */
    public static int m3004a(int i, int i2, int[] iArr) {
        GLES30.glGenBuffers(1, iArr, 0);
        GLES30.glBindBuffer(35051, iArr[0]);
        GLES30.glBufferData(35051, i * i2 * 4, null, 35049);
        GLES30.glBindBuffer(35051, 0);
        return iArr[0];
    }

    /* renamed from: b */
    public static int m2994b() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        return iArr[0];
    }

    /* renamed from: a */
    public static void m2997a(int[] iArr, int[] iArr2, int i, int i2) {
        GLES20.glGenFramebuffers(1, iArr, 0);
        iArr2[0] = m3005a(i, i2, 6408, 6408, iArr2);
        GLES20.glBindFramebuffer(36160, iArr[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, iArr2[0], 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    /* renamed from: c */
    public static int m2993c() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(3553, iArr[0]);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glBindTexture(3553, 0);
        return iArr[0];
    }

    /* renamed from: a */
    public static int m3000a(String str, int i) {
        int[] iArr = new int[1];
        int glCreateShader = GLES20.glCreateShader(i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] == 0) {
            TXCLog.m2915d("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(glCreateShader));
            return 0;
        }
        return glCreateShader;
    }

    /* renamed from: a */
    public static int m2999a(String str, String str2) {
        int[] iArr = new int[1];
        int m3000a = m3000a(str, 35633);
        if (m3000a == 0) {
            TXCLog.m2915d("Load Program", "Vertex Shader Failed");
            return 0;
        }
        int m3000a2 = m3000a(str2, 35632);
        if (m3000a2 == 0) {
            TXCLog.m2915d("Load Program", "Fragment Shader Failed");
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(glCreateProgram, m3000a);
        GLES20.glAttachShader(glCreateProgram, m3000a2);
        GLES20.glLinkProgram(glCreateProgram);
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] <= 0) {
            TXCLog.m2915d("Load Program", "Linking Failed");
            return 0;
        }
        GLES20.glDeleteShader(m3000a);
        GLES20.glDeleteShader(m3000a2);
        return glCreateProgram;
    }
}
