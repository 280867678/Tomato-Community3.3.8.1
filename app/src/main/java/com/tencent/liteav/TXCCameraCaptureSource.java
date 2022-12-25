package com.tencent.liteav;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import com.tencent.liteav.audio.TXCAudioRecorder;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.TXICaptureGLThread;
import com.tencent.liteav.basic.p109e.TXIGLSurfaceTextureListener;
import com.tencent.liteav.basic.p111g.TXSVFrame;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.capturer.TXCCameraCapturer;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.b */
/* loaded from: classes3.dex */
public class TXCCameraCaptureSource implements TXINotifyListener, TXIGLSurfaceTextureListener, TXICaptureSource {

    /* renamed from: a */
    WeakReference<TXINotifyListener> f2160a;

    /* renamed from: b */
    private Context f2161b;

    /* renamed from: c */
    private TXCCameraCapturer f2162c;

    /* renamed from: d */
    private TXICaptureSourceListener f2163d;

    /* renamed from: e */
    private boolean f2164e;

    /* renamed from: f */
    private TXCLivePushConfig f2165f;

    /* renamed from: k */
    private TXICaptureGLThread f2170k;

    /* renamed from: g */
    private int f2166g = 0;

    /* renamed from: h */
    private boolean f2167h = false;

    /* renamed from: i */
    private int f2168i = -1;

    /* renamed from: j */
    private int f2169j = -1;

    /* renamed from: l */
    private boolean f2171l = false;

    public TXCCameraCaptureSource(Context context, TXCLivePushConfig tXCLivePushConfig, TXICaptureGLThread tXICaptureGLThread) {
        this.f2162c = null;
        this.f2170k = null;
        this.f2162c = new TXCCameraCapturer();
        try {
            this.f2165f = (TXCLivePushConfig) tXCLivePushConfig.clone();
        } catch (CloneNotSupportedException e) {
            this.f2165f = new TXCLivePushConfig();
            e.printStackTrace();
        }
        this.f2161b = context;
        this.f2170k = tXICaptureGLThread;
        this.f2170k.setSurfaceTextureListener(this);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1051a() {
        this.f2170k.mo1025a(this.f2165f.f4300h);
        m3335a(this.f2170k.getSurfaceTexture());
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1042a(boolean z) {
        mo1038c();
        this.f2170k.mo1026a();
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: b */
    public void mo1041b() {
        m3335a(this.f2170k.getSurfaceTexture());
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: c */
    public void mo1038c() {
        this.f2162c.m2400b();
        this.f2164e = false;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: b */
    public void mo1039b(boolean z) {
        if (!this.f2164e || this.f2162c == null) {
            return;
        }
        TXCLivePushConfig tXCLivePushConfig = this.f2165f;
        tXCLivePushConfig.f4305m = z ? !tXCLivePushConfig.f4305m : tXCLivePushConfig.f4305m;
        this.f2162c.m2400b();
        this.f2170k.mo1015a(false);
        this.f2162c.m2403a(m3327g());
        this.f2162c.m2402a(this.f2170k.getSurfaceTexture());
        if (this.f2162c.m2395c(this.f2165f.f4305m) == 0) {
            this.f2164e = true;
            m3336a(1003, "打开摄像头成功");
        } else {
            this.f2164e = false;
            m3336a(TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL, "打开摄像头失败，请确认摄像头权限是否打开");
        }
        this.f2171l = false;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: d */
    public boolean mo1035d() {
        return this.f2164e;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: e */
    public int mo1032e() {
        return this.f2162c.m2407a();
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public boolean mo1048a(int i) {
        return this.f2162c.m2396c(i);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1050a(float f) {
        this.f2162c.m2406a(f);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: b */
    public void mo1040b(int i) {
        this.f2168i = i;
        m3326h();
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1044a(TXICaptureSourceListener tXICaptureSourceListener) {
        this.f2163d = tXICaptureSourceListener;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: c */
    public void mo1036c(final boolean z) {
        mo1043a(new Runnable() { // from class: com.tencent.liteav.b.1
            @Override // java.lang.Runnable
            public void run() {
                TXCCameraCaptureSource.this.f2165f.f4290L = z;
            }
        });
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: d */
    public boolean mo1033d(boolean z) {
        return this.f2162c.m2401a(z);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1045a(TXSVFrame tXSVFrame) {
        TXICaptureGLThread tXICaptureGLThread = this.f2170k;
        if (tXICaptureGLThread != null) {
            tXICaptureGLThread.mo1023a(tXSVFrame.f2714a, tXSVFrame.f2721h, this.f2166g, tXSVFrame.f2717d, tXSVFrame.f2718e);
        }
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: e */
    public void mo1031e(boolean z) {
        this.f2167h = z;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1043a(Runnable runnable) {
        this.f2170k.mo1017a(runnable);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: f */
    public EGLContext mo1030f() {
        return this.f2170k.getGLContext();
    }

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(int i, Bundle bundle) {
        TXCSystemUtil.m2886a(this.f2160a, i, bundle);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1046a(TXINotifyListener tXINotifyListener) {
        this.f2160a = new WeakReference<>(tXINotifyListener);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: c */
    public void mo1037c(int i) {
        this.f2169j = i;
        m3326h();
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1047a(int i, int i2) {
        TXCLivePushConfig tXCLivePushConfig = this.f2165f;
        tXCLivePushConfig.f4293a = i;
        tXCLivePushConfig.f4294b = i2;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: d */
    public void mo1034d(int i) {
        TXCLivePushConfig tXCLivePushConfig = this.f2165f;
        tXCLivePushConfig.f4303k = i;
        tXCLivePushConfig.m1465a();
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1049a(float f, float f2) {
        TXCCameraCapturer tXCCameraCapturer = this.f2162c;
        if (tXCCameraCapturer == null || !this.f2165f.f4282D) {
            return;
        }
        tXCCameraCapturer.m2405a(f, f2);
    }

    /* renamed from: a */
    private void m3335a(SurfaceTexture surfaceTexture) {
        TXCCameraCapturer tXCCameraCapturer;
        if (surfaceTexture == null || this.f2164e || (tXCCameraCapturer = this.f2162c) == null) {
            return;
        }
        tXCCameraCapturer.m2402a(surfaceTexture);
        this.f2162c.m2399b(this.f2165f.f4300h);
        this.f2162c.m2393d(this.f2165f.f4304l);
        this.f2162c.m2398b(this.f2165f.f4282D);
        this.f2162c.m2403a(m3327g());
        if (this.f2162c.m2395c(this.f2165f.f4305m) == 0) {
            this.f2164e = true;
            m3336a(1003, "打开摄像头成功");
            this.f2171l = false;
            if (!this.f2167h || TXCAudioRecorder.m3456a().m3444c()) {
                return;
            }
            TXCAudioRecorder.m3456a().m3451a(this.f2161b);
            this.f2167h = false;
            return;
        }
        this.f2164e = false;
        m3336a(TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL, "打开摄像头失败，请确认摄像头权限是否打开");
    }

    /* renamed from: a */
    private void m3336a(int i, String str) {
        TXCSystemUtil.m2885a(this.f2160a, i, str);
    }

    @Override // com.tencent.liteav.basic.p109e.TXIGLSurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture) {
        m3335a(surfaceTexture);
        TXICaptureSourceListener tXICaptureSourceListener = this.f2163d;
        if (tXICaptureSourceListener != null) {
            tXICaptureSourceListener.mo1029a(surfaceTexture);
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXIGLSurfaceTextureListener
    public void onSurfaceTextureDestroy(SurfaceTexture surfaceTexture) {
        TXICaptureSourceListener tXICaptureSourceListener = this.f2163d;
        if (tXICaptureSourceListener != null) {
            tXICaptureSourceListener.mo1027r();
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXIGLSurfaceTextureListener
    public int onTextureProcess(int i, float[] fArr) {
        if (!this.f2164e) {
            return 0;
        }
        boolean z = true;
        if (!this.f2171l) {
            TXCSystemUtil.m2885a(this.f2160a, 1007, "首帧画面采集完成");
            this.f2171l = true;
        }
        if (this.f2163d != null) {
            TXSVFrame tXSVFrame = new TXSVFrame();
            tXSVFrame.f2717d = this.f2162c.m2392e();
            tXSVFrame.f2718e = this.f2162c.m2390f();
            TXCLivePushConfig tXCLivePushConfig = this.f2165f;
            tXSVFrame.f2719f = tXCLivePushConfig.f4293a;
            tXSVFrame.f2720g = tXCLivePushConfig.f4294b;
            tXSVFrame.f2722i = this.f2162c.m2397c();
            if (!this.f2162c.m2394d()) {
                z = this.f2165f.f4290L;
            } else if (this.f2165f.f4290L) {
                z = false;
            }
            tXSVFrame.f2721h = z;
            tXSVFrame.f2714a = i;
            tXSVFrame.f2716c = fArr;
            tXSVFrame.f2723j = this.f2166g;
            tXSVFrame.f2715b = 4;
            int i2 = tXSVFrame.f2722i;
            if (i2 == 0 || i2 == 180) {
                TXCLivePushConfig tXCLivePushConfig2 = this.f2165f;
                tXSVFrame.f2719f = tXCLivePushConfig2.f4294b;
                tXSVFrame.f2720g = tXCLivePushConfig2.f4293a;
            } else {
                TXCLivePushConfig tXCLivePushConfig3 = this.f2165f;
                tXSVFrame.f2719f = tXCLivePushConfig3.f4293a;
                tXSVFrame.f2720g = tXCLivePushConfig3.f4294b;
            }
            int i3 = tXSVFrame.f2717d;
            int i4 = tXSVFrame.f2718e;
            TXCLivePushConfig tXCLivePushConfig4 = this.f2165f;
            tXSVFrame.f2724k = TXCSystemUtil.m2891a(i3, i4, tXCLivePushConfig4.f4294b, tXCLivePushConfig4.f4293a);
            this.f2163d.mo1028b(tXSVFrame);
        }
        return 0;
    }

    /* renamed from: g */
    private int m3327g() {
        TXCLivePushConfig tXCLivePushConfig = this.f2165f;
        if (!tXCLivePushConfig.f4291M) {
            int i = tXCLivePushConfig.f4303k;
            if (i == 0) {
                return 4;
            }
            if (i == 1) {
                return 5;
            }
            if (i == 2) {
                return 6;
            }
            if (i == 6) {
                return 3;
            }
        }
        return 7;
    }

    /* renamed from: h */
    private void m3326h() {
        mo1043a(new Runnable() { // from class: com.tencent.liteav.b.2
            @Override // java.lang.Runnable
            public void run() {
                if (TXCCameraCaptureSource.this.f2168i != -1) {
                    TXCCameraCaptureSource tXCCameraCaptureSource = TXCCameraCaptureSource.this;
                    tXCCameraCaptureSource.f2166g = tXCCameraCaptureSource.f2168i;
                    TXCCameraCaptureSource.this.f2168i = -1;
                }
                if (TXCCameraCaptureSource.this.f2169j != -1) {
                    TXCCameraCaptureSource.this.f2165f.f4304l = TXCCameraCaptureSource.this.f2169j;
                    TXCCameraCaptureSource.this.f2162c.m2393d(TXCCameraCaptureSource.this.f2165f.f4304l);
                    TXCCameraCaptureSource.this.f2169j = -1;
                }
            }
        });
    }
}
