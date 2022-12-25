package com.tencent.liteav.basic.p109e;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.basic.e.f */
/* loaded from: classes3.dex */
public class TXCGLThreadHandler extends Handler {

    /* renamed from: a */
    public int f2603a = 720;

    /* renamed from: b */
    public int f2604b = 1280;

    /* renamed from: c */
    public Surface f2605c = null;

    /* renamed from: d */
    public EGLContext f2606d = null;

    /* renamed from: e */
    protected EGL10Helper f2607e = null;

    /* renamed from: f */
    private AbstractC3347a f2608f = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TXCGLThreadHandler.java */
    /* renamed from: com.tencent.liteav.basic.e.f$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3347a {
        /* renamed from: c */
        void mo3038c();

        /* renamed from: d */
        void mo3037d();

        /* renamed from: e */
        void mo3036e();
    }

    /* renamed from: a */
    public static void m3047a(final Handler handler, final HandlerThread handlerThread) {
        if (handler == null || handlerThread == null) {
            return;
        }
        Message message = new Message();
        message.what = 101;
        message.obj = new Runnable() { // from class: com.tencent.liteav.basic.e.f.1
            @Override // java.lang.Runnable
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.basic.e.f.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Handler handler2 = handler;
                        if (handler2 != null) {
                            handler2.removeCallbacksAndMessages(null);
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
        };
        handler.sendMessage(message);
    }

    public TXCGLThreadHandler(Looper looper) {
        super(looper);
    }

    /* renamed from: a */
    public void m3045a(AbstractC3347a abstractC3347a) {
        this.f2608f = abstractC3347a;
    }

    /* renamed from: a */
    public EGLContext m3048a() {
        EGL10Helper eGL10Helper = this.f2607e;
        if (eGL10Helper != null) {
            return eGL10Helper.m3080c();
        }
        return null;
    }

    /* renamed from: b */
    public Surface m3044b() {
        return this.f2605c;
    }

    /* renamed from: c */
    public void m3042c() {
        EGL10Helper eGL10Helper = this.f2607e;
        if (eGL10Helper != null) {
            eGL10Helper.m3084a();
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        Object obj;
        if (message == null) {
            return;
        }
        switch (message.what) {
            case 100:
                m3046a(message);
                break;
            case 101:
                m3043b(message);
                break;
            case 102:
                try {
                    m3041c(message);
                    break;
                } catch (Exception unused) {
                    break;
                }
        }
        if (message == null || (obj = message.obj) == null) {
            return;
        }
        ((Runnable) obj).run();
    }

    /* renamed from: a */
    private void m3046a(Message message) {
        try {
            m3040d();
        } catch (Exception unused) {
            TXCLog.m2914e("TXGLThreadHandler", "surface-render: init egl context exception " + this.f2605c);
            this.f2605c = null;
        }
    }

    /* renamed from: b */
    private void m3043b(Message message) {
        m3039e();
    }

    /* renamed from: c */
    private void m3041c(Message message) {
        try {
            if (this.f2608f == null) {
                return;
            }
            this.f2608f.mo3037d();
        } catch (Exception e) {
            TXCLog.m2914e("TXGLThreadHandler", "onMsgRend Exception " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* renamed from: d */
    private boolean m3040d() {
        TXCLog.m2915d("TXGLThreadHandler", String.format("init egl size[%d/%d]", Integer.valueOf(this.f2603a), Integer.valueOf(this.f2604b)));
        this.f2607e = EGL10Helper.m3082a(null, this.f2606d, this.f2605c, this.f2603a, this.f2604b);
        if (this.f2607e == null) {
            return false;
        }
        TXCLog.m2911w("TXGLThreadHandler", "surface-render: create egl context " + this.f2605c);
        AbstractC3347a abstractC3347a = this.f2608f;
        if (abstractC3347a != null) {
            abstractC3347a.mo3038c();
        }
        return true;
    }

    /* renamed from: e */
    private void m3039e() {
        TXCLog.m2911w("TXGLThreadHandler", "surface-render: destroy egl context " + this.f2605c);
        AbstractC3347a abstractC3347a = this.f2608f;
        if (abstractC3347a != null) {
            abstractC3347a.mo3036e();
        }
        EGL10Helper eGL10Helper = this.f2607e;
        if (eGL10Helper != null) {
            eGL10Helper.m3081b();
            this.f2607e = null;
        }
        this.f2605c = null;
    }
}
