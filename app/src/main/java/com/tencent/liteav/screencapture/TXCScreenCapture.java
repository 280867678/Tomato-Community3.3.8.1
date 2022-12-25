package com.tencent.liteav.screencapture;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import android.view.WindowManager;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.EGL10Helper;
import com.tencent.liteav.basic.p109e.TXCGPUOESTextureFilter;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import com.tencent.liteav.basic.p109e.TXCRotation;
import com.tencent.liteav.basic.p109e.TXCTextureRotationUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.screencapture.TXCScreenCaptureImplSingleton;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.screencapture.a */
/* loaded from: classes3.dex */
public class TXCScreenCapture {

    /* renamed from: a */
    protected Handler f5206a;

    /* renamed from: k */
    private boolean f5216k;

    /* renamed from: n */
    private Context f5219n;

    /* renamed from: b */
    protected volatile HandlerThread f5207b = null;

    /* renamed from: c */
    protected volatile HandlerC3622a f5208c = null;

    /* renamed from: d */
    protected volatile WeakReference<TXIScreenCaptureListener> f5209d = null;

    /* renamed from: e */
    protected volatile int f5210e = 0;

    /* renamed from: f */
    protected int f5211f = 720;

    /* renamed from: g */
    protected int f5212g = 1280;

    /* renamed from: h */
    protected int f5213h = 20;

    /* renamed from: i */
    protected boolean f5214i = false;

    /* renamed from: j */
    private Object f5215j = null;

    /* renamed from: l */
    private int f5217l = this.f5211f;

    /* renamed from: m */
    private int f5218m = this.f5212g;

    /* renamed from: o */
    private TXCScreenCaptureImplSingleton.AbstractC3632a f5220o = new TXCScreenCaptureImplSingleton.AbstractC3632a() { // from class: com.tencent.liteav.screencapture.a.1
        @Override // com.tencent.liteav.screencapture.TXCScreenCaptureImplSingleton.AbstractC3632a
        /* renamed from: a */
        public void mo759a(int i) {
            TXCScreenCapture.this.m800b(i);
            TXCScreenCapture tXCScreenCapture = TXCScreenCapture.this;
            tXCScreenCapture.m799b(105, tXCScreenCapture.f5217l, TXCScreenCapture.this.f5218m);
        }
    };

    public TXCScreenCapture(Context context, boolean z) {
        this.f5206a = null;
        this.f5216k = false;
        this.f5219n = null;
        this.f5219n = context;
        this.f5206a = new Handler(Looper.getMainLooper());
        this.f5216k = z;
        if (Build.VERSION.SDK_INT >= 21) {
            TXCScreenCaptureImplSingleton.m786a().m784a(context);
        }
    }

    /* renamed from: a */
    public int m812a(int i, int i2, int i3) {
        int i4;
        if (this.f5216k) {
            i4 = ((WindowManager) this.f5219n.getSystemService("window")).getDefaultDisplay().getRotation();
            if (i4 == 0 || i4 == 2) {
                if (i > i2) {
                    this.f5211f = i2;
                    this.f5212g = i;
                } else {
                    this.f5211f = i;
                    this.f5212g = i2;
                }
            } else if (i < i2) {
                this.f5211f = i2;
                this.f5212g = i;
            } else {
                this.f5211f = i;
                this.f5212g = i2;
            }
        } else {
            this.f5211f = i;
            this.f5212g = i2;
            i4 = 0;
        }
        TXCLog.m2915d("ScreenCapture", String.format("start screen capture orientation[%d] input size[%d/%d] output size[%d/%d]", Integer.valueOf(i4), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.f5211f), Integer.valueOf(this.f5212g)));
        this.f5213h = i3;
        if (Build.VERSION.SDK_INT < 21) {
            m808a(20000004, (EGLContext) null);
            return 20000004;
        }
        this.f5217l = this.f5211f;
        this.f5218m = this.f5212g;
        m814a();
        if (this.f5216k) {
            TXCScreenCaptureImplSingleton.m786a().m780a(this.f5220o);
        }
        return 0;
    }

    /* renamed from: a */
    public void m804a(Object obj) {
        TXCScreenCaptureImplSingleton.m786a().m774b(this.f5220o);
        this.f5215j = obj;
        m801b();
    }

    /* renamed from: a */
    public void m802a(final boolean z) {
        synchronized (this) {
            if (this.f5208c != null) {
                this.f5208c.post(new Runnable() { // from class: com.tencent.liteav.screencapture.a.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCScreenCapture.this.f5214i = z;
                    }
                });
            } else {
                this.f5214i = z;
            }
        }
    }

    /* renamed from: a */
    public void m805a(TXIScreenCaptureListener tXIScreenCaptureListener) {
        this.f5209d = new WeakReference<>(tXIScreenCaptureListener);
    }

    /* renamed from: a */
    public void m807a(TXINotifyListener tXINotifyListener) {
        TXCScreenCaptureImplSingleton.m786a().m781a(tXINotifyListener);
    }

    /* renamed from: a */
    public synchronized void m803a(Runnable runnable) {
        if (this.f5208c != null) {
            this.f5208c.post(runnable);
        }
    }

    /* renamed from: a */
    protected void m814a() {
        m801b();
        synchronized (this) {
            this.f5207b = new HandlerThread("ScreenCaptureGLThread");
            this.f5207b.start();
            this.f5208c = new HandlerC3622a(this.f5207b.getLooper(), this);
            int i = 1;
            this.f5210e++;
            this.f5208c.f5228a = this.f5210e;
            this.f5208c.f5232e = this.f5217l;
            this.f5208c.f5233f = this.f5218m;
            HandlerC3622a handlerC3622a = this.f5208c;
            if (this.f5213h >= 1) {
                i = this.f5213h;
            }
            handlerC3622a.f5234g = i;
        }
        m813a(100);
    }

    /* renamed from: b */
    protected void m801b() {
        synchronized (this) {
            this.f5210e++;
            if (this.f5208c != null) {
                final HandlerThread handlerThread = this.f5207b;
                final HandlerC3622a handlerC3622a = this.f5208c;
                m809a(101, new Runnable() { // from class: com.tencent.liteav.screencapture.a.3
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCScreenCapture.this.f5206a.post(new Runnable() { // from class: com.tencent.liteav.screencapture.a.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Handler handler = handlerC3622a;
                                if (handler != null) {
                                    handler.removeCallbacksAndMessages(null);
                                }
                                HandlerThread handlerThread2 = handlerThread;
                                if (handlerThread2 != null) {
                                    if (Build.VERSION.SDK_INT >= 18) {
                                        handlerThread2.quitSafely();
                                    } else {
                                        handlerThread2.quit();
                                    }
                                }
                            }
                        });
                    }
                });
            }
            this.f5208c = null;
            this.f5207b = null;
        }
    }

    /* renamed from: c */
    protected TXIScreenCaptureListener m797c() {
        if (this.f5209d == null) {
            return null;
        }
        return this.f5209d.get();
    }

    /* renamed from: a */
    protected void m810a(int i, long j) {
        synchronized (this) {
            if (this.f5208c != null) {
                this.f5208c.sendEmptyMessageDelayed(i, j);
            }
        }
    }

    /* renamed from: a */
    protected void m813a(int i) {
        synchronized (this) {
            if (this.f5208c != null) {
                this.f5208c.sendEmptyMessage(i);
            }
        }
    }

    /* renamed from: b */
    protected void m799b(int i, int i2, int i3) {
        synchronized (this) {
            if (this.f5208c != null) {
                Message message = new Message();
                message.what = i;
                message.arg1 = i2;
                message.arg2 = i3;
                this.f5208c.sendMessage(message);
            }
        }
    }

    /* renamed from: a */
    protected void m809a(int i, Runnable runnable) {
        synchronized (this) {
            if (this.f5208c != null) {
                Message message = new Message();
                message.what = i;
                message.obj = runnable;
                this.f5208c.sendMessage(message);
            }
        }
    }

    /* renamed from: a */
    protected void m808a(int i, EGLContext eGLContext) {
        TXIScreenCaptureListener m797c = m797c();
        if (m797c != null) {
            m797c.mo757a(i, eGLContext);
        }
    }

    /* renamed from: a */
    protected void m811a(int i, int i2, int i3, int i4, long j) {
        TXIScreenCaptureListener m797c = m797c();
        if (m797c != null) {
            m797c.mo758a(i, i2, i3, i4, j);
        }
    }

    /* renamed from: b */
    protected void m800b(int i) {
        if (i == 0) {
            int i2 = this.f5211f;
            int i3 = this.f5212g;
            if (i2 >= i3) {
                i2 = i3;
            }
            this.f5217l = i2;
            int i4 = this.f5211f;
            int i5 = this.f5212g;
            if (i4 < i5) {
                i4 = i5;
            }
            this.f5218m = i4;
        } else {
            int i6 = this.f5211f;
            int i7 = this.f5212g;
            if (i6 < i7) {
                i6 = i7;
            }
            this.f5217l = i6;
            int i8 = this.f5211f;
            int i9 = this.f5212g;
            if (i8 >= i9) {
                i8 = i9;
            }
            this.f5218m = i8;
        }
        TXCLog.m2915d("ScreenCapture", String.format("reset screen capture angle[%d] output size[%d/%d]", Integer.valueOf(i), Integer.valueOf(this.f5217l), Integer.valueOf(this.f5218m)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* compiled from: TXCScreenCapture.java */
    /* renamed from: com.tencent.liteav.screencapture.a$a */
    /* loaded from: classes3.dex */
    public class HandlerC3622a extends Handler {

        /* renamed from: a */
        public int f5228a = 0;

        /* renamed from: b */
        public int[] f5229b = null;

        /* renamed from: c */
        public Surface f5230c = null;

        /* renamed from: d */
        public SurfaceTexture f5231d = null;

        /* renamed from: e */
        public int f5232e = 720;

        /* renamed from: f */
        public int f5233f = 1280;

        /* renamed from: g */
        public int f5234g = 25;

        /* renamed from: h */
        protected boolean f5235h = false;

        /* renamed from: i */
        protected long f5236i = 0;

        /* renamed from: j */
        protected long f5237j = 0;

        /* renamed from: k */
        protected EGL10Helper f5238k = null;

        /* renamed from: l */
        protected TXCGPUOESTextureFilter f5239l = null;

        /* renamed from: m */
        float[] f5240m = new float[16];

        public HandlerC3622a(Looper looper, TXCScreenCapture tXCScreenCapture) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Object obj;
            if (message == null) {
                return;
            }
            if (this.f5228a != TXCScreenCapture.this.f5210e && 101 != message.what) {
                return;
            }
            switch (message.what) {
                case 100:
                    m794a(message);
                    break;
                case 101:
                    m792b(message);
                    break;
                case 102:
                    try {
                        m790c(message);
                        break;
                    } catch (Exception unused) {
                        break;
                    }
                case 103:
                    m788d(message);
                    break;
                case 105:
                    m787e(message);
                    break;
            }
            if (message == null || (obj = message.obj) == null) {
                return;
            }
            ((Runnable) obj).run();
        }

        /* renamed from: a */
        protected void m794a(Message message) {
            this.f5236i = 0L;
            this.f5237j = 0L;
            if (!m795a()) {
                m793b();
                TXCScreenCapture.this.m801b();
                TXCScreenCapture.this.m808a(20000003, (EGLContext) null);
                return;
            }
            TXCScreenCapture.this.m808a(0, this.f5238k.m3080c());
        }

        /* renamed from: b */
        protected void m792b(Message message) {
            TXIScreenCaptureListener m797c = TXCScreenCapture.this.m797c();
            if (m797c != null) {
                m797c.mo756a(TXCScreenCapture.this.f5215j);
            }
            m793b();
        }

        /* renamed from: c */
        protected void m790c(Message message) {
            TXCScreenCapture.this.m810a(102, 5L);
            if (!TXCScreenCapture.this.f5214i) {
                return;
            }
            if (!this.f5235h) {
                this.f5236i = 0L;
                this.f5237j = System.nanoTime();
                return;
            }
            long nanoTime = System.nanoTime();
            long j = this.f5237j;
            if (nanoTime < ((((this.f5236i * 1000) * 1000) * 1000) / this.f5234g) + j) {
                return;
            }
            if (j == 0) {
                this.f5237j = nanoTime;
            } else if (nanoTime > j + 1000000000) {
                this.f5236i = 0L;
                this.f5237j = nanoTime;
            }
            this.f5236i++;
            SurfaceTexture surfaceTexture = this.f5231d;
            if (surfaceTexture == null || this.f5229b == null) {
                return;
            }
            surfaceTexture.getTransformMatrix(this.f5240m);
            try {
                this.f5231d.updateTexImage();
            } catch (Exception e) {
                TXCLog.m2914e("ScreenCapture", "onMsgRend Exception " + e.getMessage());
                e.printStackTrace();
            }
            this.f5239l.mo3010a(this.f5240m);
            GLES20.glViewport(0, 0, this.f5232e, this.f5233f);
            TXCScreenCapture.this.m811a(0, this.f5239l.mo2294a(this.f5229b[0]), this.f5232e, this.f5233f, TXCTimeUtil.getTimeTick());
        }

        /* renamed from: d */
        protected void m788d(Message message) {
            if (message == null) {
                return;
            }
            int i = message.arg1;
            if (i < 1) {
                i = 1;
            }
            this.f5234g = i;
            this.f5236i = 0L;
            this.f5237j = 0L;
        }

        /* renamed from: e */
        protected void m787e(Message message) {
            if (message == null) {
                return;
            }
            this.f5232e = message.arg1;
            this.f5233f = message.arg2;
            m791c();
            this.f5239l.mo1333a(this.f5232e, this.f5233f);
            m789d();
            TXCLog.m2915d("ScreenCapture", String.format("set screen capture size[%d/%d]", Integer.valueOf(TXCScreenCapture.this.f5217l), Integer.valueOf(TXCScreenCapture.this.f5218m)));
        }

        /* renamed from: a */
        protected boolean m795a() {
            TXCLog.m2915d("ScreenCapture", String.format("init egl size[%d/%d]", Integer.valueOf(this.f5232e), Integer.valueOf(this.f5233f)));
            this.f5238k = EGL10Helper.m3082a(null, null, null, this.f5232e, this.f5233f);
            if (this.f5238k == null) {
                return false;
            }
            this.f5239l = new TXCGPUOESTextureFilter();
            if (!this.f5239l.mo2653c()) {
                return false;
            }
            this.f5239l.mo1353a(true);
            this.f5239l.mo1333a(this.f5232e, this.f5233f);
            this.f5239l.m3026a(TXCTextureRotationUtil.f2684e, TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, false));
            m789d();
            return true;
        }

        /* renamed from: b */
        protected void m793b() {
            m791c();
            TXCGPUOESTextureFilter tXCGPUOESTextureFilter = this.f5239l;
            if (tXCGPUOESTextureFilter != null) {
                tXCGPUOESTextureFilter.mo1351e();
                this.f5239l = null;
            }
            EGL10Helper eGL10Helper = this.f5238k;
            if (eGL10Helper != null) {
                eGL10Helper.m3081b();
                this.f5238k = null;
            }
        }

        /* renamed from: c */
        protected void m791c() {
            SurfaceTexture surfaceTexture = this.f5231d;
            if (surfaceTexture != null) {
                surfaceTexture.setOnFrameAvailableListener(null);
                this.f5231d.release();
                this.f5235h = false;
                this.f5231d = null;
            }
            TXCScreenCaptureImplSingleton.m786a().m783a(this.f5230c);
            Surface surface = this.f5230c;
            if (surface != null) {
                surface.release();
                this.f5230c = null;
            }
            int[] iArr = this.f5229b;
            if (iArr != null) {
                GLES20.glDeleteTextures(1, iArr, 0);
                this.f5229b = null;
            }
        }

        /* renamed from: d */
        protected void m789d() {
            this.f5229b = new int[1];
            this.f5229b[0] = TXCOpenGlUtils.m2994b();
            int[] iArr = this.f5229b;
            if (iArr[0] <= 0) {
                this.f5229b = null;
                return;
            }
            this.f5231d = new SurfaceTexture(iArr[0]);
            this.f5230c = new Surface(this.f5231d);
            this.f5231d.setDefaultBufferSize(this.f5232e, this.f5233f);
            this.f5231d.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.screencapture.a.a.1
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    TXCScreenCapture.this.m809a(104, new Runnable() { // from class: com.tencent.liteav.screencapture.a.a.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            HandlerC3622a handlerC3622a = HandlerC3622a.this;
                            handlerC3622a.f5235h = true;
                            TXCScreenCapture.this.m813a(102);
                        }
                    });
                    surfaceTexture.setOnFrameAvailableListener(null);
                }
            });
            TXCScreenCaptureImplSingleton.m786a().m782a(this.f5230c, this.f5232e, this.f5233f);
        }
    }
}
