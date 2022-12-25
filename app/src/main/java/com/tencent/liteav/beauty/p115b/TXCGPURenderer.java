package com.tencent.liteav.beauty.p115b;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.TypedValue;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCRotation;
import com.tencent.liteav.basic.p109e.TXCTextureRotationUtil;
import com.tencent.liteav.beauty.p115b.TXCGPUImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* renamed from: com.tencent.liteav.beauty.b.w */
/* loaded from: classes3.dex */
public class TXCGPURenderer implements Camera.PreviewCallback, GLSurfaceView.Renderer {

    /* renamed from: a */
    static final float[] f3156a = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};

    /* renamed from: b */
    public final Object f3157b;

    /* renamed from: c */
    private TXCGPUFilter f3158c;

    /* renamed from: d */
    private SurfaceTexture f3159d;

    /* renamed from: e */
    private final FloatBuffer f3160e;

    /* renamed from: f */
    private final FloatBuffer f3161f;

    /* renamed from: g */
    private IntBuffer f3162g;

    /* renamed from: h */
    private int f3163h;

    /* renamed from: i */
    private int f3164i;

    /* renamed from: j */
    private int f3165j;

    /* renamed from: k */
    private int f3166k;

    /* renamed from: l */
    private final Queue<Runnable> f3167l;

    /* renamed from: m */
    private final Queue<Runnable> f3168m;

    /* renamed from: n */
    private TXCRotation f3169n;

    /* renamed from: o */
    private boolean f3170o;

    /* renamed from: p */
    private boolean f3171p;

    /* renamed from: q */
    private TXCGPUImage.EnumC3389a f3172q;

    /* renamed from: r */
    private float f3173r;

    /* renamed from: s */
    private float f3174s;

    /* renamed from: t */
    private float f3175t;

    /* renamed from: u */
    private Context f3176u;

    /* renamed from: v */
    private int f3177v;

    /* renamed from: w */
    private String f3178w;

    /* renamed from: x */
    private FileOutputStream f3179x;

    /* renamed from: y */
    private int f3180y;

    /* renamed from: z */
    private int f3181z;

    /* renamed from: a */
    private float m2651a(float f, float f2) {
        return f == 0.0f ? f2 : 1.0f - f2;
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        GLES20.glClearColor(this.f3173r, this.f3174s, this.f3175t, 1.0f);
        GLES20.glDisable(2929);
        this.f3158c.mo2653c();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.f3163h = i;
        this.f3164i = i2;
        GLES20.glViewport(0, 0, i, i2);
        GLES20.glUseProgram(this.f3158c.m3011q());
        this.f3158c.mo1333a(i, i2);
        m2652a();
        synchronized (this.f3157b) {
            this.f3157b.notifyAll();
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(16640);
        m2649a(this.f3167l);
        if (this.f3181z == -1) {
            int[] iArr = new int[1];
            GLES20.glGenTextures(1, iArr, 0);
            this.f3181z = iArr[0];
            GLES20.glBindTexture(3553, this.f3181z);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            TypedValue typedValue = new TypedValue();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = typedValue.density;
            Bitmap decodeResource = BitmapFactory.decodeResource(this.f3176u.getResources(), this.f3177v, options);
            GLES20.glBindTexture(3553, this.f3181z);
            GLUtils.texImage2D(3553, 0, decodeResource, 0);
        }
        this.f3158c.mo1324a(this.f3181z, this.f3160e, this.f3161f);
        IntBuffer allocate = IntBuffer.allocate(250000);
        GLES20.glReadPixels(0, 0, 500, 500, 6408, 5121, allocate);
        int i = this.f3180y;
        this.f3180y = i + 1;
        if (i == 50) {
            try {
                if (this.f3179x == null) {
                    this.f3179x = new FileOutputStream(new File("/mnt/sdcard/", "rgbBuffer"));
                }
                String byteOrder = allocate.order().toString();
                int[] array = allocate.array();
                byte[] bArr = new byte[1000000];
                if (byteOrder.compareTo("LITTLE_ENDIAN") != 0) {
                    for (int i2 = 0; i2 < 250000; i2++) {
                        int i3 = i2 * 4;
                        bArr[i3 + 3] = (byte) ((array[i2] >> 24) & 255);
                        bArr[i3 + 2] = (byte) ((array[i2] >> 16) & 255);
                        bArr[i3 + 1] = (byte) ((array[i2] >> 8) & 255);
                        bArr[i3] = (byte) (array[i2] & 255);
                    }
                } else {
                    for (int i4 = 0; i4 < 250000; i4++) {
                        int i5 = i4 * 4;
                        bArr[i5] = (byte) ((array[i4] >> 24) & 255);
                        bArr[i5 + 1] = (byte) ((array[i4] >> 16) & 255);
                        bArr[i5 + 2] = (byte) ((array[i4] >> 8) & 255);
                        bArr[i5 + 3] = (byte) (array[i4] & 255);
                    }
                }
                this.f3179x.write(bArr, 0, bArr.length);
                this.f3179x.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            TXCLog.m2914e("check1", "" + this.f3180y);
        }
        m2649a(this.f3168m);
        SurfaceTexture surfaceTexture = this.f3159d;
        if (surfaceTexture != null) {
            surfaceTexture.updateTexImage();
        }
    }

    /* renamed from: a */
    private void m2649a(Queue<Runnable> queue) {
        if (queue == null) {
            TXCLog.m2914e(this.f3178w, "runAll queue is null!");
            return;
        }
        synchronized (queue) {
            while (!queue.isEmpty()) {
                queue.poll().run();
            }
        }
    }

    @Override // android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        if (this.f3162g == null && previewSize != null) {
            this.f3162g = IntBuffer.allocate(previewSize.width * previewSize.height);
        }
        if (this.f3167l.isEmpty()) {
            m2650a(new Runnable() { // from class: com.tencent.liteav.beauty.b.w.1
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        }
    }

    /* renamed from: a */
    private void m2652a() {
        float f = this.f3163h;
        float f2 = this.f3164i;
        TXCRotation tXCRotation = this.f3169n;
        if (tXCRotation == TXCRotation.ROTATION_270 || tXCRotation == TXCRotation.ROTATION_90) {
            f = this.f3164i;
            f2 = this.f3163h;
        }
        float max = Math.max(f / this.f3165j, f2 / this.f3166k);
        float round = Math.round(this.f3165j * max) / f;
        float round2 = Math.round(this.f3166k * max) / f2;
        float[] fArr = f3156a;
        float[] m2991a = TXCTextureRotationUtil.m2991a(this.f3169n, this.f3170o, this.f3171p);
        if (this.f3172q == TXCGPUImage.EnumC3389a.CENTER_CROP) {
            float f3 = (1.0f - (1.0f / round)) / 2.0f;
            float f4 = (1.0f - (1.0f / round2)) / 2.0f;
            m2991a = new float[]{m2651a(m2991a[0], f3), m2651a(m2991a[1], f4), m2651a(m2991a[2], f3), m2651a(m2991a[3], f4), m2651a(m2991a[4], f3), m2651a(m2991a[5], f4), m2651a(m2991a[6], f3), m2651a(m2991a[7], f4)};
        } else {
            float[] fArr2 = f3156a;
            fArr = new float[]{fArr2[0] / round2, fArr2[1] / round, fArr2[2] / round2, fArr2[3] / round, fArr2[4] / round2, fArr2[5] / round, fArr2[6] / round2, fArr2[7] / round};
        }
        m2991a[0] = 0.0f;
        m2991a[1] = 1.0f;
        m2991a[2] = 1.0f;
        m2991a[3] = 1.0f;
        m2991a[4] = 0.0f;
        m2991a[5] = 0.0f;
        m2991a[6] = 1.0f;
        m2991a[7] = 0.0f;
        this.f3160e.clear();
        this.f3160e.put(fArr).position(0);
        this.f3161f.clear();
        this.f3161f.put(m2991a).position(0);
    }

    /* renamed from: a */
    protected void m2650a(Runnable runnable) {
        synchronized (this.f3167l) {
            this.f3167l.add(runnable);
        }
    }
}
