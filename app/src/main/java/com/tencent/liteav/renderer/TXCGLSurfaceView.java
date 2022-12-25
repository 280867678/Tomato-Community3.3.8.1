package com.tencent.liteav.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import com.tencent.liteav.basic.p109e.TXCRotation;
import com.tencent.liteav.basic.p109e.TXCTextureRotationUtil;
import com.tencent.liteav.basic.p109e.TXICaptureGLThread;
import com.tencent.liteav.basic.p109e.TXIGLSurfaceTextureListener;
import com.tencent.liteav.basic.p109e.TXITakePhotoListener;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes3.dex */
public class TXCGLSurfaceView extends TXCGLSurfaceViewBase implements SurfaceTexture.OnFrameAvailableListener, GLSurfaceView.Renderer, TXICaptureGLThread {

    /* renamed from: A */
    private boolean f4970A;

    /* renamed from: B */
    private TXITakePhotoListener f4971B;

    /* renamed from: C */
    private int f4972C;

    /* renamed from: D */
    private int f4973D;

    /* renamed from: E */
    private int f4974E;

    /* renamed from: F */
    private TXIGLSurfaceTextureListener f4975F;

    /* renamed from: G */
    private final Queue<Runnable> f4976G;

    /* renamed from: a */
    WeakReference<TXINotifyListener> f4977a;

    /* renamed from: g */
    private SurfaceTexture f4978g;

    /* renamed from: h */
    private EGLContext f4979h;

    /* renamed from: i */
    private TXCGPUFilter f4980i;

    /* renamed from: j */
    private boolean f4981j;

    /* renamed from: k */
    private int[] f4982k;

    /* renamed from: l */
    private float[] f4983l;

    /* renamed from: m */
    private int f4984m;

    /* renamed from: n */
    private boolean f4985n;

    /* renamed from: o */
    private float f4986o;

    /* renamed from: p */
    private float f4987p;

    /* renamed from: q */
    private int f4988q;

    /* renamed from: r */
    private long f4989r;

    /* renamed from: s */
    private long f4990s;

    /* renamed from: t */
    private int f4991t;

    /* renamed from: u */
    private boolean f4992u;

    /* renamed from: v */
    private boolean f4993v;

    /* renamed from: w */
    private Object f4994w;

    /* renamed from: x */
    private Handler f4995x;

    /* renamed from: y */
    private int f4996y;

    /* renamed from: z */
    private int f4997z;

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
    }

    public TXCGLSurfaceView(Context context) {
        super(context);
        this.f4981j = false;
        this.f4983l = new float[16];
        this.f4984m = 0;
        this.f4985n = false;
        this.f4986o = 1.0f;
        this.f4987p = 1.0f;
        this.f4988q = 20;
        this.f4989r = 0L;
        this.f4990s = 0L;
        this.f4991t = 12288;
        this.f4992u = true;
        this.f4993v = false;
        this.f4994w = new Object();
        this.f4996y = 0;
        this.f4997z = 0;
        this.f4970A = true;
        this.f4971B = null;
        this.f4972C = 0;
        this.f4976G = new LinkedList();
        setEGLContextClientVersion(2);
        m1005a(8, 8, 8, 8, 16, 0);
        setRenderer(this);
    }

    public TXCGLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4981j = false;
        this.f4983l = new float[16];
        this.f4984m = 0;
        this.f4985n = false;
        this.f4986o = 1.0f;
        this.f4987p = 1.0f;
        this.f4988q = 20;
        this.f4989r = 0L;
        this.f4990s = 0L;
        this.f4991t = 12288;
        this.f4992u = true;
        this.f4993v = false;
        this.f4994w = new Object();
        this.f4996y = 0;
        this.f4997z = 0;
        this.f4970A = true;
        this.f4971B = null;
        this.f4972C = 0;
        this.f4976G = new LinkedList();
        setEGLContextClientVersion(2);
        m1005a(8, 8, 8, 8, 16, 0);
        setRenderer(this);
    }

    public void setFPS(final int i) {
        m1011b(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.1
            @Override // java.lang.Runnable
            public void run() {
                TXCGLSurfaceView.this.f4988q = i;
                if (TXCGLSurfaceView.this.f4988q <= 0) {
                    TXCGLSurfaceView.this.f4988q = 1;
                } else if (TXCGLSurfaceView.this.f4988q > 60) {
                    TXCGLSurfaceView.this.f4988q = 60;
                }
                TXCGLSurfaceView.this.f4990s = 0L;
                TXCGLSurfaceView.this.f4989r = 0L;
            }
        });
    }

    public void setRendMode(final int i) {
        m1011b(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.2
            @Override // java.lang.Runnable
            public void run() {
                TXCGLSurfaceView.this.f4972C = i;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(16640);
            }
        });
    }

    @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase
    /* renamed from: b */
    protected void mo1003b() {
        TXIGLSurfaceTextureListener tXIGLSurfaceTextureListener = this.f4975F;
        if (tXIGLSurfaceTextureListener != null) {
            tXIGLSurfaceTextureListener.onSurfaceTextureDestroy(null);
        }
    }

    @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase
    protected void setRunInBackground(boolean z) {
        if (z) {
            synchronized (this) {
                TXCLog.m2915d("TXCGLSurfaceView", "background capture enter background");
                this.f5012c = true;
            }
            return;
        }
        m1011b(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (this) {
                    TXCLog.m2915d("TXCGLSurfaceView", "background capture exit background");
                    TXCGLSurfaceView.this.f5012c = false;
                }
            }
        });
    }

    public void setNotifyListener(TXINotifyListener tXINotifyListener) {
        this.f4977a = new WeakReference<>(tXINotifyListener);
    }

    /* renamed from: a */
    public void m1022a(TXITakePhotoListener tXITakePhotoListener) {
        this.f4971B = tXITakePhotoListener;
        this.f4970A = true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1023a(int i, boolean z, int i2, int i3, int i4) {
        if (this.f4980i == null) {
            return;
        }
        synchronized (this) {
            if (this.f5012c) {
                return;
            }
            int width = getWidth();
            int height = getHeight();
            int i5 = this.f4972C;
            if (i5 == 0) {
                this.f4973D = 0;
                this.f4974E = 0;
            } else if (i5 == 1) {
                int[] m1024a = m1024a(width, height, i3, i4);
                int i6 = m1024a[0];
                int i7 = m1024a[1];
                this.f4973D = m1024a[2];
                this.f4974E = m1024a[3];
                width = i6;
                height = i7;
            }
            this.f4996y = width;
            this.f4997z = height;
            GLES20.glViewport(this.f4973D, this.f4974E, width, height);
            float f = 1.0f;
            float f2 = height != 0 ? width / height : 1.0f;
            if (i4 != 0) {
                f = i3 / i4;
            }
            if (this.f4985n != z || this.f4984m != i2 || this.f4986o != f2 || this.f4987p != f) {
                this.f4985n = z;
                this.f4984m = i2;
                this.f4986o = f2;
                this.f4987p = f;
                int i8 = (720 - this.f4984m) % 360;
                boolean z2 = i8 == 90 || i8 == 270;
                int i9 = z2 ? height : width;
                if (!z2) {
                    width = height;
                }
                this.f4980i.m3034a(i3, i4, i8, TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, true), i9 / width, z2 ? false : this.f4985n, z2 ? this.f4985n : false);
                if (z2) {
                    this.f4980i.m3019g();
                } else {
                    this.f4980i.m3018h();
                }
            }
            GLES20.glBindFramebuffer(36160, 0);
            this.f4980i.m3025b(i);
        }
    }

    /* renamed from: a */
    private int[] m1024a(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int[] iArr = new int[4];
        float f = i2;
        float f2 = i;
        float f3 = i4 / i3;
        if (f / f2 > f3) {
            int i7 = (int) (f2 * f3);
            i5 = (i2 - i7) / 2;
            i2 = i7;
            i6 = 0;
        } else {
            int i8 = (int) (f / f3);
            i5 = 0;
            i6 = (i - i8) / 2;
            i = i8;
        }
        iArr[0] = i;
        iArr[1] = i2;
        iArr[2] = i6;
        iArr[3] = i5;
        return iArr;
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    public SurfaceTexture getSurfaceTexture() {
        return this.f4978g;
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1025a(int i) {
        this.f4988q = i;
        this.f4981j = false;
        this.f4971B = null;
        this.f4970A = false;
        m999c(true);
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1026a() {
        m999c(false);
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    public void setSurfaceTextureListener(TXIGLSurfaceTextureListener tXIGLSurfaceTextureListener) {
        this.f4975F = tXIGLSurfaceTextureListener;
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    public EGLContext getGLContext() {
        return this.f4979h;
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1017a(Runnable runnable) {
        synchronized (this.f4976G) {
            this.f4976G.add(runnable);
        }
    }

    @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase
    /* renamed from: c */
    protected int mo1001c() {
        if (this.f4991t != 12288) {
            TXCLog.m2914e("TXCGLSurfaceView", "background capture swapbuffer error : " + this.f4991t);
        }
        return this.f4991t;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase, android.view.SurfaceView, android.view.View
    public void onDetachedFromWindow() {
        Handler handler;
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT < 21 || (handler = this.f4995x) == null) {
            return;
        }
        handler.getLooper().quitSafely();
    }

    /* renamed from: b */
    public void m1011b(Runnable runnable) {
        synchronized (this.f4976G) {
            this.f4976G.add(runnable);
        }
    }

    /* renamed from: a */
    private boolean m1016a(Queue<Runnable> queue) {
        synchronized (queue) {
            if (queue.isEmpty()) {
                return false;
            }
            Runnable poll = queue.poll();
            if (poll == null) {
                return false;
            }
            poll.run();
            return true;
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.f4979h = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        this.f4982k = new int[1];
        this.f4982k[0] = TXCOpenGlUtils.m2994b();
        int[] iArr = this.f4982k;
        if (iArr[0] <= 0) {
            this.f4982k = null;
            TXCLog.m2914e("TXCGLSurfaceView", "create oes texture error!! at glsurfaceview");
            return;
        }
        this.f4978g = new SurfaceTexture(iArr[0]);
        if (Build.VERSION.SDK_INT >= 21) {
            Handler handler = this.f4995x;
            if (handler != null) {
                handler.getLooper().quitSafely();
            }
            HandlerThread handlerThread = new HandlerThread("VideoCaptureThread");
            handlerThread.start();
            this.f4995x = new Handler(handlerThread.getLooper());
            this.f4978g.setOnFrameAvailableListener(this, this.f4995x);
        } else {
            this.f4978g.setOnFrameAvailableListener(this);
        }
        this.f4980i = new TXCGPUFilter();
        if (!this.f4980i.mo2653c()) {
            return;
        }
        this.f4980i.m3026a(TXCTextureRotationUtil.f2684e, TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, false));
        TXIGLSurfaceTextureListener tXIGLSurfaceTextureListener = this.f4975F;
        if (tXIGLSurfaceTextureListener == null) {
            return;
        }
        tXIGLSurfaceTextureListener.onSurfaceTextureAvailable(this.f4978g);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        long currentTimeMillis;
        long j;
        long j2;
        m1016a(this.f4976G);
        while (true) {
            currentTimeMillis = System.currentTimeMillis();
            if (this.f4990s == 0) {
                this.f4990s = currentTimeMillis;
            }
            j = this.f4990s;
            j2 = this.f4989r;
            if (currentTimeMillis - j >= (1000 * j2) / this.f4988q) {
                break;
            }
            m1007g();
        }
        this.f4989r = j2 + 1;
        if (currentTimeMillis - j > 2000) {
            this.f4989r = 1L;
            this.f4990s = System.currentTimeMillis();
        }
        if (this.f4992u) {
            return;
        }
        try {
            synchronized (this) {
                if (!this.f4993v) {
                    return;
                }
                if (this.f4978g != null) {
                    this.f4978g.updateTexImage();
                    this.f4978g.getTransformMatrix(this.f4983l);
                }
                boolean z = false;
                this.f4993v = false;
                if (this.f4975F != null) {
                    this.f4975F.onTextureProcess(this.f4982k[0], this.f4983l);
                }
                m1008f();
                synchronized (this) {
                    if (!this.f5012c) {
                        z = true;
                    }
                }
                if (!z) {
                    return;
                }
                this.f4991t = m998d();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: f */
    private void m1008f() {
        if (this.f4970A) {
            if (this.f4996y != 0 && this.f4997z != 0) {
                boolean z = getWidth() <= getHeight();
                int i = this.f4997z;
                int i2 = this.f4996y;
                if (i < i2) {
                    i = i2;
                }
                int i3 = this.f4997z;
                int i4 = this.f4996y;
                if (i3 >= i4) {
                    i3 = i4;
                }
                if (z) {
                    int i5 = i3;
                    i3 = i;
                    i = i5;
                }
                final ByteBuffer allocate = ByteBuffer.allocate(i * i3 * 4);
                final Bitmap createBitmap = Bitmap.createBitmap(i, i3, Bitmap.Config.ARGB_8888);
                allocate.position(0);
                GLES20.glReadPixels(this.f4973D, this.f4974E, i, i3, 6408, 5121, allocate);
                final int i6 = i;
                final int i7 = i3;
                new Thread(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.4
                    @Override // java.lang.Runnable
                    public void run() {
                        allocate.position(0);
                        createBitmap.copyPixelsFromBuffer(allocate);
                        Matrix matrix = new Matrix();
                        matrix.setScale(1.0f, -1.0f);
                        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, 0, 0, i6, i7, matrix, false);
                        if (TXCGLSurfaceView.this.f4971B != null) {
                            TXCGLSurfaceView.this.f4971B.onTakePhotoComplete(createBitmap2);
                            TXCGLSurfaceView.this.f4971B = null;
                        }
                        createBitmap.recycle();
                    }
                }).start();
            }
            this.f4970A = false;
        }
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (!this.f4981j) {
            TXCSystemUtil.m2885a(this.f4977a, 1007, "首帧画面采集完成");
            this.f4981j = true;
        }
        this.f4992u = false;
        synchronized (this) {
            this.f4993v = true;
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXICaptureGLThread
    /* renamed from: a */
    public void mo1015a(boolean z) {
        this.f4992u = true;
        if (z) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            this.f4991t = m998d();
        }
        synchronized (this) {
            if (this.f4993v) {
                this.f4993v = false;
                if (this.f4978g != null) {
                    this.f4978g.updateTexImage();
                }
            }
        }
    }

    /* renamed from: b */
    public void m1010b(final boolean z) {
        synchronized (this.f4994w) {
            m1011b(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.5
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (TXCGLSurfaceView.this.f4994w) {
                        TXCGLSurfaceView.this.mo1015a(z);
                        TXCGLSurfaceView.this.f4994w.notifyAll();
                    }
                }
            });
            try {
                this.f4994w.wait(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: g */
    private void m1007g() {
        try {
            Thread.sleep(15L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
