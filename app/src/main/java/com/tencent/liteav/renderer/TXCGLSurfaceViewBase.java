package com.tencent.liteav.renderer;

import android.content.Context;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes3.dex */
public class TXCGLSurfaceViewBase extends SurfaceView implements SurfaceHolder.Callback {

    /* renamed from: a */
    private static final C3608j f5010a = new C3608j();

    /* renamed from: b */
    protected boolean f5011b = false;

    /* renamed from: c */
    protected boolean f5012c = false;

    /* renamed from: d */
    protected final WeakReference<TXCGLSurfaceViewBase> f5013d = new WeakReference<>(this);

    /* renamed from: e */
    protected boolean f5014e;

    /* renamed from: f */
    protected boolean f5015f;

    /* renamed from: g */
    private C3607i f5016g;

    /* renamed from: h */
    private GLSurfaceView.Renderer f5017h;

    /* renamed from: i */
    private boolean f5018i;

    /* renamed from: j */
    private AbstractC3603e f5019j;

    /* renamed from: k */
    private AbstractC3604f f5020k;

    /* renamed from: l */
    private AbstractC3605g f5021l;

    /* renamed from: m */
    private AbstractC3609k f5022m;

    /* renamed from: n */
    private int f5023n;

    /* renamed from: o */
    private int f5024o;

    /* renamed from: p */
    private boolean f5025p;

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$e */
    /* loaded from: classes3.dex */
    public interface AbstractC3603e {
        /* renamed from: a */
        EGLConfig mo987a(EGL10 egl10, EGLDisplay eGLDisplay);
    }

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$f */
    /* loaded from: classes3.dex */
    public interface AbstractC3604f {
        /* renamed from: a */
        EGLContext mo986a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        /* renamed from: a */
        void mo985a(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$g */
    /* loaded from: classes3.dex */
    public interface AbstractC3605g {
        /* renamed from: a */
        EGLSurface mo984a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

        /* renamed from: a */
        void mo983a(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$k */
    /* loaded from: classes3.dex */
    public interface AbstractC3609k {
        /* renamed from: a */
        GL m947a(GL gl);
    }

    /* renamed from: b */
    protected void mo1003b() {
    }

    /* renamed from: c */
    protected int mo1001c() {
        return 0;
    }

    public TXCGLSurfaceViewBase(Context context) {
        super(context);
        m1006a();
    }

    public TXCGLSurfaceViewBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m1006a();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.f5016g != null) {
                this.f5016g.m960g();
            }
        } finally {
            super.finalize();
        }
    }

    /* renamed from: a */
    private void m1006a() {
        getHolder().addCallback(this);
    }

    public void setGLWrapper(AbstractC3609k abstractC3609k) {
        this.f5022m = abstractC3609k;
    }

    public void setDebugFlags(int i) {
        this.f5023n = i;
    }

    public int getDebugFlags() {
        return this.f5023n;
    }

    public void setPreserveEGLContextOnPause(boolean z) {
        this.f5025p = z;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.f5025p;
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        m994f();
        if (this.f5019j == null) {
            this.f5019j = new C3611m(true);
        }
        if (this.f5020k == null) {
            this.f5020k = new C3601c();
        }
        if (this.f5021l == null) {
            this.f5021l = new C3602d();
        }
        this.f5017h = renderer;
        this.f5016g = new C3607i(this.f5013d);
        this.f5016g.start();
    }

    public void setEGLContextFactory(AbstractC3604f abstractC3604f) {
        m994f();
        this.f5020k = abstractC3604f;
    }

    public void setEGLWindowSurfaceFactory(AbstractC3605g abstractC3605g) {
        m994f();
        this.f5021l = abstractC3605g;
    }

    public void setEGLConfigChooser(AbstractC3603e abstractC3603e) {
        m994f();
        this.f5019j = abstractC3603e;
    }

    public void setEGLConfigChooser(boolean z) {
        setEGLConfigChooser(new C3611m(z));
    }

    /* renamed from: a */
    public void m1005a(int i, int i2, int i3, int i4, int i5, int i6) {
        setEGLConfigChooser(new C3600b(i, i2, i3, i4, i5, i6));
    }

    public void setEGLContextClientVersion(int i) {
        m994f();
        this.f5024o = i;
    }

    public void setRenderMode(int i) {
        this.f5016g.m969a(i);
    }

    public int getRenderMode() {
        return this.f5016g.m963d();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.f5016g.m962e();
        setRunInBackground(false);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        setRunInBackground(true);
        if (!this.f5011b) {
            this.f5016g.m966a(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceViewBase.1
                @Override // java.lang.Runnable
                public void run() {
                    TXCGLSurfaceViewBase.this.mo1003b();
                }
            });
            this.f5016g.m961f();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.f5016g.m968a(i2, i3);
    }

    /* renamed from: c */
    public void m999c(boolean z) {
        this.f5011b = z;
        if (this.f5011b || !this.f5012c || this.f5016g == null) {
            return;
        }
        TXCLog.m2911w("TXCGLSurfaceViewBase", "background capture destroy surface when not enable background run");
        this.f5016g.m966a(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceViewBase.2
            @Override // java.lang.Runnable
            public void run() {
                TXCGLSurfaceViewBase.this.mo1003b();
            }
        });
        this.f5016g.m961f();
    }

    protected void setRunInBackground(boolean z) {
        this.f5012c = z;
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f5018i && this.f5017h != null) {
            C3607i c3607i = this.f5016g;
            int m963d = c3607i != null ? c3607i.m963d() : 1;
            this.f5016g = new C3607i(this.f5013d);
            if (m963d != 1) {
                this.f5016g.m969a(m963d);
            }
            this.f5016g.start();
        }
        this.f5018i = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.SurfaceView, android.view.View
    public void onDetachedFromWindow() {
        C3607i c3607i = this.f5016g;
        if (c3607i != null) {
            c3607i.m960g();
        }
        this.f5018i = true;
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$i */
    /* loaded from: classes3.dex */
    public static class C3607i extends Thread {

        /* renamed from: a */
        private boolean f5046a;

        /* renamed from: b */
        private boolean f5047b;

        /* renamed from: c */
        private boolean f5048c;

        /* renamed from: d */
        private boolean f5049d;

        /* renamed from: e */
        private boolean f5050e;

        /* renamed from: f */
        private boolean f5051f;

        /* renamed from: g */
        private boolean f5052g;

        /* renamed from: h */
        private boolean f5053h;

        /* renamed from: i */
        private boolean f5054i;

        /* renamed from: j */
        private boolean f5055j;

        /* renamed from: k */
        private boolean f5056k;

        /* renamed from: p */
        private boolean f5061p;

        /* renamed from: s */
        private C3606h f5064s;

        /* renamed from: t */
        private WeakReference<TXCGLSurfaceViewBase> f5065t;

        /* renamed from: q */
        private ArrayList<Runnable> f5062q = new ArrayList<>();

        /* renamed from: r */
        private boolean f5063r = true;

        /* renamed from: l */
        private int f5057l = 0;

        /* renamed from: m */
        private int f5058m = 0;

        /* renamed from: o */
        private boolean f5060o = true;

        /* renamed from: n */
        private int f5059n = 1;

        C3607i(WeakReference<TXCGLSurfaceViewBase> weakReference) {
            this.f5065t = weakReference;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setName("GLThread " + getId());
            try {
                m958i();
            } catch (InterruptedException unused) {
            } catch (Throwable th) {
                TXCGLSurfaceViewBase.f5010a.m953a(this);
                throw th;
            }
            TXCGLSurfaceViewBase.f5010a.m953a(this);
        }

        /* renamed from: a */
        public int m970a() {
            return this.f5064s.m976c();
        }

        /* renamed from: b */
        public C3606h m965b() {
            return this.f5064s;
        }

        /* renamed from: i */
        private void m958i() throws InterruptedException {
            int i;
            boolean z;
            boolean z2;
            boolean z3;
            this.f5064s = new C3606h(this.f5065t);
            this.f5053h = false;
            this.f5054i = false;
            boolean z4 = false;
            boolean z5 = false;
            boolean z6 = false;
            GL10 gl10 = null;
            int i2 = 0;
            int i3 = 0;
            boolean z7 = false;
            boolean z8 = false;
            boolean z9 = false;
            boolean z10 = false;
            boolean z11 = false;
            while (true) {
                Runnable runnable = null;
                while (true) {
                    try {
                        synchronized (TXCGLSurfaceViewBase.f5010a) {
                            while (!this.f5046a) {
                                if (!this.f5062q.isEmpty()) {
                                    runnable = this.f5062q.remove(0);
                                } else {
                                    if (this.f5049d != this.f5048c) {
                                        z = this.f5048c;
                                        this.f5049d = this.f5048c;
                                        TXCGLSurfaceViewBase.f5010a.notifyAll();
                                    } else {
                                        z = false;
                                    }
                                    if (this.f5056k) {
                                        m957j();
                                        m956k();
                                        this.f5056k = false;
                                        z6 = true;
                                    }
                                    if (z4) {
                                        m957j();
                                        m956k();
                                        z4 = false;
                                    }
                                    if (z && this.f5054i) {
                                        m957j();
                                    }
                                    if (z && this.f5053h) {
                                        TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f5065t.get();
                                        if (!(tXCGLSurfaceViewBase == null ? false : tXCGLSurfaceViewBase.f5025p) || TXCGLSurfaceViewBase.f5010a.m954a()) {
                                            m956k();
                                        }
                                    }
                                    if (z && TXCGLSurfaceViewBase.f5010a.m951b()) {
                                        this.f5064s.m972g();
                                    }
                                    if (!this.f5050e && !this.f5052g) {
                                        if (this.f5054i) {
                                            m957j();
                                        }
                                        this.f5052g = true;
                                        this.f5051f = false;
                                        TXCGLSurfaceViewBase.f5010a.notifyAll();
                                    }
                                    if (this.f5050e && this.f5052g) {
                                        this.f5052g = false;
                                        TXCGLSurfaceViewBase.f5010a.notifyAll();
                                    }
                                    if (z5) {
                                        this.f5061p = true;
                                        TXCGLSurfaceViewBase.f5010a.notifyAll();
                                        z5 = false;
                                        z11 = false;
                                    }
                                    if (m955l()) {
                                        if (!this.f5053h) {
                                            if (z6) {
                                                z6 = false;
                                            } else if (TXCGLSurfaceViewBase.f5010a.m950b(this)) {
                                                try {
                                                    this.f5064s.m982a();
                                                    this.f5053h = true;
                                                    TXCGLSurfaceViewBase.f5010a.notifyAll();
                                                    z7 = true;
                                                } catch (RuntimeException e) {
                                                    TXCGLSurfaceViewBase.f5010a.m948c(this);
                                                    throw e;
                                                }
                                            }
                                        }
                                        if (!this.f5053h || this.f5054i) {
                                            z2 = z8;
                                        } else {
                                            this.f5054i = true;
                                            z2 = true;
                                            z9 = true;
                                            z10 = true;
                                        }
                                        if (this.f5054i) {
                                            if (this.f5063r) {
                                                i2 = this.f5057l;
                                                i3 = this.f5058m;
                                                z3 = false;
                                                this.f5063r = false;
                                                z2 = true;
                                                z10 = true;
                                                z11 = true;
                                            } else {
                                                z3 = false;
                                            }
                                            this.f5060o = z3;
                                            TXCGLSurfaceViewBase.f5010a.notifyAll();
                                            z8 = z2;
                                        } else {
                                            z8 = z2;
                                        }
                                    }
                                    TXCGLSurfaceViewBase.f5010a.wait();
                                }
                            }
                            synchronized (TXCGLSurfaceViewBase.f5010a) {
                                m957j();
                                m956k();
                            }
                            return;
                        }
                        if (runnable != null) {
                            break;
                        }
                        if (z8) {
                            if (this.f5064s.m978b()) {
                                synchronized (TXCGLSurfaceViewBase.f5010a) {
                                    this.f5055j = true;
                                    TXCGLSurfaceViewBase.f5010a.notifyAll();
                                }
                                z8 = false;
                            } else {
                                synchronized (TXCGLSurfaceViewBase.f5010a) {
                                    this.f5055j = true;
                                    this.f5051f = true;
                                    TXCGLSurfaceViewBase.f5010a.notifyAll();
                                }
                            }
                        }
                        if (z9) {
                            GL10 gl102 = (GL10) this.f5064s.m975d();
                            TXCGLSurfaceViewBase.f5010a.m952a(gl102);
                            gl10 = gl102;
                            z9 = false;
                        }
                        if (z7) {
                            TXCGLSurfaceViewBase tXCGLSurfaceViewBase2 = this.f5065t.get();
                            if (tXCGLSurfaceViewBase2 != null) {
                                tXCGLSurfaceViewBase2.f5017h.onSurfaceCreated(gl10, this.f5064s.f5043d);
                            }
                            z7 = false;
                        }
                        if (z10) {
                            TXCGLSurfaceViewBase tXCGLSurfaceViewBase3 = this.f5065t.get();
                            if (tXCGLSurfaceViewBase3 != null) {
                                tXCGLSurfaceViewBase3.f5017h.onSurfaceChanged(gl10, i2, i3);
                            }
                            z10 = false;
                        }
                        TXCGLSurfaceViewBase tXCGLSurfaceViewBase4 = this.f5065t.get();
                        if (tXCGLSurfaceViewBase4 != null) {
                            tXCGLSurfaceViewBase4.f5017h.onDrawFrame(gl10);
                            i = tXCGLSurfaceViewBase4.mo1001c();
                        } else {
                            i = 12288;
                        }
                        if (i != 12288) {
                            if (i != 12302) {
                                C3606h.m979a("GLThread", "eglSwapBuffers", i);
                                synchronized (TXCGLSurfaceViewBase.f5010a) {
                                    this.f5051f = true;
                                    TXCGLSurfaceViewBase.f5010a.notifyAll();
                                }
                            } else {
                                z4 = true;
                            }
                        }
                        if (z11) {
                            z5 = true;
                        }
                    } catch (Throwable th) {
                        synchronized (TXCGLSurfaceViewBase.f5010a) {
                            m957j();
                            m956k();
                            throw th;
                        }
                    }
                }
                runnable.run();
            }
        }

        /* renamed from: j */
        private void m957j() {
            if (this.f5054i) {
                this.f5054i = false;
                this.f5064s.m973f();
            }
        }

        /* renamed from: k */
        private void m956k() {
            if (this.f5053h) {
                this.f5064s.m972g();
                this.f5053h = false;
                TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f5065t.get();
                if (tXCGLSurfaceViewBase != null) {
                    tXCGLSurfaceViewBase.f5015f = false;
                }
                TXCGLSurfaceViewBase.f5010a.m948c(this);
            }
        }

        /* renamed from: c */
        public boolean m964c() {
            return this.f5053h && this.f5054i && m955l();
        }

        /* renamed from: l */
        private boolean m955l() {
            return !this.f5049d && this.f5050e && !this.f5051f && this.f5057l > 0 && this.f5058m > 0 && (this.f5060o || this.f5059n == 1);
        }

        /* renamed from: a */
        public void m969a(int i) {
            if (i >= 0 && i <= 1) {
                synchronized (TXCGLSurfaceViewBase.f5010a) {
                    this.f5059n = i;
                    TXCGLSurfaceViewBase.f5010a.notifyAll();
                }
                return;
            }
            throw new IllegalArgumentException("renderMode");
        }

        /* renamed from: d */
        public int m963d() {
            int i;
            synchronized (TXCGLSurfaceViewBase.f5010a) {
                i = this.f5059n;
            }
            return i;
        }

        /* renamed from: e */
        public void m962e() {
            synchronized (TXCGLSurfaceViewBase.f5010a) {
                this.f5050e = true;
                this.f5055j = false;
                TXCGLSurfaceViewBase.f5010a.notifyAll();
                while (this.f5052g && !this.f5055j && !this.f5047b) {
                    try {
                        TXCGLSurfaceViewBase.f5010a.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        /* renamed from: f */
        public void m961f() {
            synchronized (TXCGLSurfaceViewBase.f5010a) {
                this.f5050e = false;
                TXCGLSurfaceViewBase.f5010a.notifyAll();
                while (!this.f5052g && !this.f5047b) {
                    try {
                        TXCGLSurfaceViewBase.f5010a.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        /* renamed from: a */
        public void m968a(int i, int i2) {
            synchronized (TXCGLSurfaceViewBase.f5010a) {
                this.f5057l = i;
                this.f5058m = i2;
                this.f5063r = true;
                this.f5060o = true;
                this.f5061p = false;
                TXCGLSurfaceViewBase.f5010a.notifyAll();
                while (!this.f5047b && !this.f5049d && !this.f5061p && m964c()) {
                    try {
                        TXCGLSurfaceViewBase.f5010a.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        /* renamed from: g */
        public void m960g() {
            synchronized (TXCGLSurfaceViewBase.f5010a) {
                this.f5046a = true;
                TXCGLSurfaceViewBase.f5010a.notifyAll();
                while (!this.f5047b) {
                    try {
                        TXCGLSurfaceViewBase.f5010a.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        /* renamed from: h */
        public void m959h() {
            this.f5056k = true;
            TXCGLSurfaceViewBase.f5010a.notifyAll();
        }

        /* renamed from: a */
        public void m966a(Runnable runnable) {
            if (runnable != null) {
                synchronized (TXCGLSurfaceViewBase.f5010a) {
                    this.f5062q.add(runnable);
                    TXCGLSurfaceViewBase.f5010a.notifyAll();
                }
                return;
            }
            throw new IllegalArgumentException("r must not be null");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$l */
    /* loaded from: classes3.dex */
    public static class C3610l extends Writer {

        /* renamed from: a */
        private StringBuilder f5073a = new StringBuilder();

        C3610l() {
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            m946a();
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
            m946a();
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                char c = cArr[i + i3];
                if (c == '\n') {
                    m946a();
                } else {
                    this.f5073a.append(c);
                }
            }
        }

        /* renamed from: a */
        private void m946a() {
            if (this.f5073a.length() > 0) {
                TXCLog.m2912v("TXCGLSurfaceViewBase", this.f5073a.toString());
                StringBuilder sb = this.f5073a;
                sb.delete(0, sb.length());
            }
        }
    }

    /* renamed from: f */
    private void m994f() {
        if (this.f5016g == null) {
            return;
        }
        throw new IllegalStateException("setRenderer has already been called for this instance.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$j */
    /* loaded from: classes3.dex */
    public static class C3608j {

        /* renamed from: a */
        private static String f5066a = "GLThreadManager";

        /* renamed from: b */
        private boolean f5067b;

        /* renamed from: c */
        private int f5068c;

        /* renamed from: d */
        private boolean f5069d;

        /* renamed from: e */
        private boolean f5070e;

        /* renamed from: f */
        private boolean f5071f;

        /* renamed from: g */
        private C3607i f5072g;

        private C3608j() {
        }

        /* renamed from: a */
        public synchronized void m953a(C3607i c3607i) {
            c3607i.f5047b = true;
            if (this.f5072g == c3607i) {
                this.f5072g = null;
            }
            notifyAll();
        }

        /* renamed from: b */
        public boolean m950b(C3607i c3607i) {
            C3607i c3607i2 = this.f5072g;
            if (c3607i2 == c3607i || c3607i2 == null) {
                this.f5072g = c3607i;
                notifyAll();
                return true;
            }
            m949c();
            if (this.f5070e) {
                return true;
            }
            C3607i c3607i3 = this.f5072g;
            if (c3607i3 == null) {
                return false;
            }
            c3607i3.m959h();
            return false;
        }

        /* renamed from: c */
        public void m948c(C3607i c3607i) {
            if (this.f5072g == c3607i) {
                this.f5072g = null;
            }
            notifyAll();
        }

        /* renamed from: a */
        public synchronized boolean m954a() {
            return this.f5071f;
        }

        /* renamed from: b */
        public synchronized boolean m951b() {
            m949c();
            return !this.f5070e;
        }

        /* renamed from: a */
        public synchronized void m952a(GL10 gl10) {
            if (!this.f5069d) {
                m949c();
                String glGetString = gl10.glGetString(7937);
                boolean z = false;
                if (this.f5068c < 131072) {
                    this.f5070e = !glGetString.startsWith("Q3Dimension MSM7500 ");
                    notifyAll();
                }
                if (!this.f5070e) {
                    z = true;
                }
                this.f5071f = z;
                this.f5069d = true;
            }
        }

        /* renamed from: c */
        private void m949c() {
            this.f5068c = 131072;
            this.f5070e = true;
            this.f5067b = true;
        }
    }

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$h */
    /* loaded from: classes3.dex */
    public static class C3606h {

        /* renamed from: a */
        EGL10 f5040a;

        /* renamed from: b */
        EGLDisplay f5041b;

        /* renamed from: c */
        EGLSurface f5042c;

        /* renamed from: d */
        EGLConfig f5043d;

        /* renamed from: e */
        EGLContext f5044e;

        /* renamed from: f */
        private WeakReference<TXCGLSurfaceViewBase> f5045f;

        public C3606h(WeakReference<TXCGLSurfaceViewBase> weakReference) {
            this.f5045f = weakReference;
        }

        /* renamed from: a */
        public void m982a() {
            this.f5040a = (EGL10) EGLContext.getEGL();
            this.f5041b = this.f5040a.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            EGLDisplay eGLDisplay = this.f5041b;
            if (eGLDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (!this.f5040a.eglInitialize(eGLDisplay, new int[2])) {
                throw new RuntimeException("eglInitialize failed");
            }
            TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f5045f.get();
            if (tXCGLSurfaceViewBase != null) {
                this.f5043d = tXCGLSurfaceViewBase.f5019j.mo987a(this.f5040a, this.f5041b);
                this.f5044e = tXCGLSurfaceViewBase.f5020k.mo986a(this.f5040a, this.f5041b, this.f5043d);
            } else {
                this.f5043d = null;
                this.f5044e = null;
            }
            EGLContext eGLContext = this.f5044e;
            if (eGLContext == null || eGLContext == EGL10.EGL_NO_CONTEXT) {
                this.f5044e = null;
                m981a("createContext");
            }
            if (tXCGLSurfaceViewBase != null) {
                tXCGLSurfaceViewBase.f5015f = true;
            }
            this.f5042c = null;
        }

        /* renamed from: b */
        public boolean m978b() {
            if (this.f5040a == null) {
                throw new RuntimeException("egl not initialized");
            }
            if (this.f5041b == null) {
                throw new RuntimeException("eglDisplay not initialized");
            }
            if (this.f5043d == null) {
                throw new RuntimeException("mEglConfig not initialized");
            }
            m971h();
            TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f5045f.get();
            if (tXCGLSurfaceViewBase != null) {
                this.f5042c = tXCGLSurfaceViewBase.f5021l.mo984a(this.f5040a, this.f5041b, this.f5043d, tXCGLSurfaceViewBase.getHolder());
            } else {
                this.f5042c = null;
            }
            EGLSurface eGLSurface = this.f5042c;
            if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
                if (this.f5040a.eglGetError() == 12299) {
                    TXCLog.m2914e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                }
                return false;
            } else if (!this.f5040a.eglMakeCurrent(this.f5041b, eGLSurface, eGLSurface, this.f5044e)) {
                m979a("EGLHelper", "eglMakeCurrent", this.f5040a.eglGetError());
                return false;
            } else {
                if (tXCGLSurfaceViewBase != null) {
                    tXCGLSurfaceViewBase.f5014e = true;
                }
                return true;
            }
        }

        /* renamed from: c */
        public int m976c() {
            return m974e();
        }

        /* renamed from: d */
        GL m975d() {
            GL gl = this.f5044e.getGL();
            TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f5045f.get();
            if (tXCGLSurfaceViewBase != null) {
                if (tXCGLSurfaceViewBase.f5022m != null) {
                    gl = tXCGLSurfaceViewBase.f5022m.m947a(gl);
                }
                if ((tXCGLSurfaceViewBase.f5023n & 3) == 0) {
                    return gl;
                }
                int i = 0;
                C3610l c3610l = null;
                if ((tXCGLSurfaceViewBase.f5023n & 1) != 0) {
                    i = 1;
                }
                if ((tXCGLSurfaceViewBase.f5023n & 2) != 0) {
                    c3610l = new C3610l();
                }
                return GLDebugHelper.wrap(gl, i, c3610l);
            }
            return gl;
        }

        /* renamed from: e */
        public int m974e() {
            if (!this.f5040a.eglSwapBuffers(this.f5041b, this.f5042c)) {
                return this.f5040a.eglGetError();
            }
            return 12288;
        }

        /* renamed from: f */
        public void m973f() {
            m971h();
        }

        /* renamed from: h */
        private void m971h() {
            EGLSurface eGLSurface;
            EGLSurface eGLSurface2 = this.f5042c;
            if (eGLSurface2 == null || eGLSurface2 == (eGLSurface = EGL10.EGL_NO_SURFACE)) {
                return;
            }
            this.f5040a.eglMakeCurrent(this.f5041b, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
            TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f5045f.get();
            if (tXCGLSurfaceViewBase != null) {
                tXCGLSurfaceViewBase.f5021l.mo983a(this.f5040a, this.f5041b, this.f5042c);
                tXCGLSurfaceViewBase.f5014e = false;
            }
            this.f5042c = null;
        }

        /* renamed from: g */
        public void m972g() {
            if (this.f5044e != null) {
                TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f5045f.get();
                if (tXCGLSurfaceViewBase != null) {
                    tXCGLSurfaceViewBase.f5020k.mo985a(this.f5040a, this.f5041b, this.f5044e);
                }
                this.f5044e = null;
            }
            EGLDisplay eGLDisplay = this.f5041b;
            if (eGLDisplay != null) {
                this.f5040a.eglTerminate(eGLDisplay);
                this.f5041b = null;
            }
        }

        /* renamed from: a */
        private void m981a(String str) {
            m980a(str, this.f5040a.eglGetError());
        }

        /* renamed from: a */
        public static void m980a(String str, int i) {
            throw new RuntimeException(m977b(str, i));
        }

        /* renamed from: a */
        public static void m979a(String str, String str2, int i) {
            TXCLog.m2911w(str, m977b(str2, i));
        }

        /* renamed from: b */
        public static String m977b(String str, int i) {
            return str + " failed: " + i;
        }
    }

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$b */
    /* loaded from: classes3.dex */
    private class C3600b extends AbstractC3599a {

        /* renamed from: c */
        protected int f5030c;

        /* renamed from: d */
        protected int f5031d;

        /* renamed from: e */
        protected int f5032e;

        /* renamed from: f */
        protected int f5033f;

        /* renamed from: g */
        protected int f5034g;

        /* renamed from: h */
        protected int f5035h;

        /* renamed from: j */
        private int[] f5037j = new int[1];

        public C3600b(int i, int i2, int i3, int i4, int i5, int i6) {
            super(new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344});
            this.f5030c = i;
            this.f5031d = i2;
            this.f5032e = i3;
            this.f5033f = i4;
            this.f5034g = i5;
            this.f5035h = i6;
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.AbstractC3599a
        /* renamed from: a */
        public EGLConfig mo988a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            for (EGLConfig eGLConfig : eGLConfigArr) {
                int m989a = m989a(egl10, eGLDisplay, eGLConfig, 12325, 0);
                int m989a2 = m989a(egl10, eGLDisplay, eGLConfig, 12326, 0);
                if (m989a >= this.f5034g && m989a2 >= this.f5035h) {
                    int m989a3 = m989a(egl10, eGLDisplay, eGLConfig, 12324, 0);
                    int m989a4 = m989a(egl10, eGLDisplay, eGLConfig, 12323, 0);
                    int m989a5 = m989a(egl10, eGLDisplay, eGLConfig, 12322, 0);
                    int m989a6 = m989a(egl10, eGLDisplay, eGLConfig, 12321, 0);
                    if (m989a3 == this.f5030c && m989a4 == this.f5031d && m989a5 == this.f5032e && m989a6 == this.f5033f) {
                        return eGLConfig;
                    }
                }
            }
            return null;
        }

        /* renamed from: a */
        private int m989a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
            return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.f5037j) ? this.f5037j[0] : i2;
        }
    }

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$m */
    /* loaded from: classes3.dex */
    private class C3611m extends C3600b {
        public C3611m(boolean z) {
            super(8, 8, 8, 0, z ? 16 : 0, 0);
        }
    }

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$c */
    /* loaded from: classes3.dex */
    private class C3601c implements AbstractC3604f {

        /* renamed from: b */
        private int f5039b;

        private C3601c() {
            this.f5039b = 12440;
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.AbstractC3604f
        /* renamed from: a */
        public EGLContext mo986a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = {this.f5039b, TXCGLSurfaceViewBase.this.f5024o, 12344};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (TXCGLSurfaceViewBase.this.f5024o == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.AbstractC3604f
        /* renamed from: a */
        public void mo985a(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            if (!egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                TXCLog.m2914e("DefaultContextFactory", "display:" + eGLDisplay + " context: " + eGLContext);
                C3606h.m980a("eglDestroyContex", egl10.eglGetError());
            }
        }
    }

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$d */
    /* loaded from: classes3.dex */
    private static class C3602d implements AbstractC3605g {
        private C3602d() {
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.AbstractC3605g
        /* renamed from: a */
        public EGLSurface mo984a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) {
            try {
                return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, null);
            } catch (IllegalArgumentException e) {
                TXCLog.m2914e("TXCGLSurfaceViewBase", "eglCreateWindowSurface");
                TXCLog.m2914e("TXCGLSurfaceViewBase", e.toString());
                return null;
            }
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.AbstractC3605g
        /* renamed from: a */
        public void mo983a(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    /* renamed from: com.tencent.liteav.renderer.TXCGLSurfaceViewBase$a */
    /* loaded from: classes3.dex */
    private abstract class AbstractC3599a implements AbstractC3603e {

        /* renamed from: a */
        protected int[] f5028a;

        /* renamed from: a */
        abstract EGLConfig mo988a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public AbstractC3599a(int[] iArr) {
            this.f5028a = m990a(iArr);
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.AbstractC3603e
        /* renamed from: a */
        public EGLConfig mo987a(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.f5028a, null, 0, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }
            int i = iArr[0];
            if (i <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            EGLConfig[] eGLConfigArr = new EGLConfig[i];
            if (!egl10.eglChooseConfig(eGLDisplay, this.f5028a, eGLConfigArr, i, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            EGLConfig mo988a = mo988a(egl10, eGLDisplay, eGLConfigArr);
            if (mo988a == null) {
                throw new IllegalArgumentException("No config chosen");
            }
            return mo988a;
        }

        /* renamed from: a */
        private int[] m990a(int[] iArr) {
            if (TXCGLSurfaceViewBase.this.f5024o != 2) {
                return iArr;
            }
            int length = iArr.length;
            int[] iArr2 = new int[length + 2];
            int i = length - 1;
            System.arraycopy(iArr, 0, iArr2, 0, i);
            iArr2[i] = 12352;
            iArr2[length] = 4;
            iArr2[length + 1] = 12344;
            return iArr2;
        }
    }

    /* renamed from: d */
    public int m998d() {
        return this.f5016g.m970a();
    }

    public C3606h getEGLHelper() {
        return this.f5016g.m965b();
    }
}
