package com.tencent.liteav;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.p002v4.app.NotificationManagerCompat;
import android.view.Surface;
import com.tencent.avroom.TXCAVRoomConstants;
import com.tencent.liteav.TXCBackgroundPusher;
import com.tencent.liteav.audio.TXCAudioRecorder;
import com.tencent.liteav.audio.TXCLiveBGMPlayer;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioRecordListener;
import com.tencent.liteav.audio.TXIBGMOnPlayListener;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCModule;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.TXCGLSurfaceRenderThread;
import com.tencent.liteav.basic.p109e.TXCGLSurfaceTextureThread;
import com.tencent.liteav.basic.p109e.TXICaptureGLThread;
import com.tencent.liteav.basic.p109e.TXITakePhotoListener;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.p111g.TXSVFrame;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.beauty.TXIVideoPreprocessorListenerEx;
import com.tencent.liteav.renderer.TXCGLSurfaceView;
import com.tencent.liteav.videoencoder.TXCVideoEncoder;
import com.tencent.liteav.videoencoder.TXIVideoEncoderListener;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.c */
/* loaded from: classes3.dex */
public class TXCCaptureAndEnc extends TXCModule implements TXCBackgroundPusher.AbstractC3296b, TXIAudioRecordListener, TXINotifyListener, TXIVideoPreprocessorListenerEx, TXICaptureSourceListener, TXIVideoEncoderListener {

    /* renamed from: d */
    private static final String f3263d = "c";

    /* renamed from: A */
    private WeakReference<TXINotifyListener> f3264A;

    /* renamed from: B */
    private WeakReference<TXICustomPreprocessListenner> f3265B;

    /* renamed from: f */
    private TXCVideoPreprocessor f3273f;

    /* renamed from: h */
    private TXSVideoEncoderParam f3275h;

    /* renamed from: i */
    private TXCVideoEncoder f3276i;

    /* renamed from: j */
    private Context f3277j;

    /* renamed from: k */
    private TXCLivePushConfig f3278k;

    /* renamed from: x */
    private TXSVFrame f3291x;

    /* renamed from: e */
    private TXICaptureSource f3272e = null;

    /* renamed from: g */
    private int f3274g = -1;

    /* renamed from: l */
    private int f3279l = 0;

    /* renamed from: m */
    private int f3280m = 0;

    /* renamed from: n */
    private int f3281n = 0;

    /* renamed from: b */
    TXIBGMOnPlayListener f3270b = null;

    /* renamed from: c */
    TXLivePusher.OnBGMNotify f3271c = null;

    /* renamed from: o */
    private float f3282o = 1.0f;

    /* renamed from: p */
    private int f3283p = 0;

    /* renamed from: q */
    private float f3284q = 0.0f;

    /* renamed from: r */
    private TXCloudVideoView f3285r = null;

    /* renamed from: s */
    private Surface f3286s = null;

    /* renamed from: t */
    private int f3287t = 0;

    /* renamed from: u */
    private int f3288u = 0;

    /* renamed from: v */
    private TXCGLSurfaceRenderThread f3289v = null;

    /* renamed from: w */
    private int f3290w = 0;

    /* renamed from: y */
    private long f3292y = 0;

    /* renamed from: z */
    private AbstractC3410a f3293z = null;

    /* renamed from: C */
    private boolean f3266C = false;

    /* renamed from: D */
    private int f3267D = -1;

    /* renamed from: E */
    private int f3268E = -1;

    /* renamed from: a */
    TXCBackgroundPusher f3269a = new TXCBackgroundPusher(this);

    /* compiled from: TXCCaptureAndEnc.java */
    /* renamed from: com.tencent.liteav.c$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3410a {
        void onEncAudio(byte[] bArr, long j, int i, int i2);

        void onEncVideo(TXSNALPacket tXSNALPacket);

        void onEncVideoFormat(MediaFormat mediaFormat);

        void onRecordPcm(byte[] bArr, long j, int i, int i2, int i3);

        void onRecordRawPcm(byte[] bArr, long j, int i, int i2, int i3, boolean z);
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListenerEx
    /* renamed from: a */
    public void mo2563a(byte[] bArr, int i, int i2, int i3, long j) {
    }

    public TXCCaptureAndEnc(Context context) {
        this.f3273f = null;
        this.f3275h = null;
        this.f3276i = null;
        this.f3277j = null;
        this.f3278k = null;
        this.f3277j = context.getApplicationContext();
        this.f3278k = new TXCLivePushConfig();
        this.f3273f = new TXCVideoPreprocessor(this.f3277j, true);
        this.f3273f.m2619a((TXIVideoPreprocessorListenerEx) this);
        this.f3273f.m2624a((TXINotifyListener) this);
        this.f3275h = new TXSVideoEncoderParam();
        this.f3276i = null;
        TXCConfigCenter.m2988a().m2985a(this.f3277j);
    }

    /* renamed from: a */
    public void m2586a(long j) {
        this.f3292y = j;
        setID("" + this.f3292y);
    }

    /* renamed from: a */
    public void m2577a(AbstractC3410a abstractC3410a) {
        this.f3293z = abstractC3410a;
    }

    /* renamed from: a */
    public void m2572a(TXCLivePushConfig tXCLivePushConfig) {
        TXCLivePushConfig tXCLivePushConfig2 = this.f3278k;
        int i = tXCLivePushConfig2.f4303k;
        boolean z = (tXCLivePushConfig == null || (tXCLivePushConfig2.f4316x == tXCLivePushConfig.f4316x && tXCLivePushConfig2.f4317y == tXCLivePushConfig.f4317y && tXCLivePushConfig2.f4318z == tXCLivePushConfig.f4318z && tXCLivePushConfig2.f4281C == tXCLivePushConfig.f4281C && tXCLivePushConfig2.f4279A == tXCLivePushConfig.f4279A && tXCLivePushConfig2.f4280B == tXCLivePushConfig.f4280B)) ? false : true;
        if (tXCLivePushConfig != null) {
            try {
                this.f3278k = (TXCLivePushConfig) tXCLivePushConfig.clone();
            } catch (CloneNotSupportedException e) {
                this.f3278k = new TXCLivePushConfig();
                e.printStackTrace();
            }
        } else {
            this.f3278k = new TXCLivePushConfig();
        }
        m2517k(i);
        if (m2522i()) {
            m2508u();
            m2506w();
            TXICaptureSource tXICaptureSource = this.f3272e;
            if (tXICaptureSource != null) {
                tXICaptureSource.mo1037c(this.f3278k.f4304l);
            }
            if (!z) {
                return;
            }
            m2507v();
        }
    }

    /* renamed from: a */
    public void m2582a(TXINotifyListener tXINotifyListener) {
        this.f3264A = new WeakReference<>(tXINotifyListener);
    }

    /* renamed from: a */
    public void m2565a(byte[] bArr) {
        TXCAudioRecorder.m3456a().m3447a(bArr);
    }

    /* renamed from: a */
    public void m2571a(TXICustomPreprocessListenner tXICustomPreprocessListenner) {
        this.f3265B = new WeakReference<>(tXICustomPreprocessListenner);
    }

    /* renamed from: b */
    public int m2562b() {
        return this.f3275h.width;
    }

    /* renamed from: c */
    public int m2552c() {
        return this.f3275h.height;
    }

    @Override // com.tencent.liteav.basic.module.TXCModule
    public void setID(String str) {
        super.setID(str);
        TXCVideoEncoder tXCVideoEncoder = this.f3276i;
        if (tXCVideoEncoder != null) {
            tXCVideoEncoder.setID(str);
        }
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.setID(str);
        }
    }

    /* renamed from: d */
    public String m2544d() {
        return TXCAudioRecorder.m3456a().m3438f() + " | " + TXCAudioRecorder.m3456a().m3439e() + "," + TXCAudioRecorder.m3456a().m3441d();
    }

    /* renamed from: e */
    public int m2536e() {
        TXICaptureSource tXICaptureSource;
        if (m2522i()) {
            String str = f3263d;
            TXCLog.m2911w(str, "ignore startPush when pushing, status:" + this.f3280m);
            return -2;
        }
        TXCDRApi.initCrashReport(this.f3277j);
        this.f3280m = 1;
        TXCLog.m2915d(f3263d, "startPusher");
        m2508u();
        TXCAudioRecorder.m3456a().m3450a(this);
        TXCLivePushConfig tXCLivePushConfig = this.f3278k;
        if ((tXCLivePushConfig != null && tXCLivePushConfig.f4284F) || this.f3279l == 1 || (tXICaptureSource = this.f3272e) == null || tXICaptureSource.mo1035d()) {
            TXCAudioRecorder.m3456a().m3451a(this.f3277j);
        } else {
            TXICaptureSource tXICaptureSource2 = this.f3272e;
            if (tXICaptureSource2 != null) {
                tXICaptureSource2.mo1031e(true);
            }
        }
        m2506w();
        TXCDRApi.txReportDAU(this.f3277j, TXCDRDef.f2500br);
        return 0;
    }

    /* renamed from: f */
    public void m2531f() {
        if (!m2522i()) {
            String str = f3263d;
            TXCLog.m2911w(str, "ignore stopPush when not pushing, status:" + this.f3280m);
            return;
        }
        TXCLog.m2915d(f3263d, "stopPusher");
        this.f3280m = 0;
        TXCAudioRecorder.m3456a().m3446b();
        TXCAudioRecorder.m3456a().m3450a((TXIAudioRecordListener) null);
        m2510s();
        this.f3284q = 0.0f;
        this.f3278k.f4287I = false;
        TXCBackgroundPusher tXCBackgroundPusher = this.f3269a;
        if (tXCBackgroundPusher != null) {
            tXCBackgroundPusher.m3492a();
        }
        this.f3291x = null;
    }

    /* renamed from: g */
    public void m2528g() {
        if (this.f3280m != 1) {
            String str = f3263d;
            TXCLog.m2911w(str, "ignore pause push when is not pushing, status:" + this.f3280m);
            return;
        }
        this.f3280m = 2;
        TXCLog.m2915d(f3263d, "pausePusher");
        TXCLivePushConfig tXCLivePushConfig = this.f3278k;
        if ((tXCLivePushConfig.f4315w & 1) == 1) {
            TXCBackgroundPusher tXCBackgroundPusher = this.f3269a;
            if (tXCBackgroundPusher != null && !tXCLivePushConfig.f4284F && this.f3272e != null) {
                int i = tXCLivePushConfig.f4314v;
                int i2 = tXCLivePushConfig.f4313u;
                Bitmap bitmap = tXCLivePushConfig.f4312t;
                TXSVideoEncoderParam tXSVideoEncoderParam = this.f3275h;
                tXCBackgroundPusher.m3490a(i, i2, bitmap, tXSVideoEncoderParam.width, tXSVideoEncoderParam.height);
            }
            TXICaptureSource tXICaptureSource = this.f3272e;
            if (tXICaptureSource != null) {
                tXICaptureSource.mo1038c();
            }
        }
        if ((this.f3278k.f4315w & 2) != 2) {
            return;
        }
        TXCAudioRecorder.m3456a().m3442c(true);
    }

    /* renamed from: h */
    public void m2525h() {
        if (this.f3280m != 2) {
            String str = f3263d;
            TXCLog.m2911w(str, "ignore resume push when is not pause, status:" + this.f3280m);
            return;
        }
        this.f3280m = 1;
        TXCLog.m2915d(f3263d, "resumePusher");
        TXCLivePushConfig tXCLivePushConfig = this.f3278k;
        if ((tXCLivePushConfig.f4315w & 1) == 1) {
            TXCBackgroundPusher tXCBackgroundPusher = this.f3269a;
            if (tXCBackgroundPusher != null && !tXCLivePushConfig.f4284F) {
                tXCBackgroundPusher.m3492a();
            }
            TXICaptureSource tXICaptureSource = this.f3272e;
            if (tXICaptureSource != null) {
                tXICaptureSource.mo1041b();
            }
            m2507v();
        }
        if ((this.f3278k.f4315w & 2) != 2) {
            return;
        }
        TXCAudioRecorder.m3456a().m3442c(this.f3266C);
        if ((1 & this.f3278k.f4289K) != 0) {
            return;
        }
        TXCAudioRecorder.m3456a().m3446b();
        TXCAudioRecorder.m3456a().m3454a(this.f3278k.f4309q);
        TXCAudioRecorder.m3456a().m3448a(this.f3278k.f4311s, this.f3277j);
        TXCAudioRecorder.m3456a().m3440d(this.f3283p);
        TXCAudioRecorder.m3456a().m3453a(this.f3267D, this.f3268E);
        TXCAudioRecorder.m3456a().m3455a(this.f3282o);
        TXCAudioRecorder.m3456a().m3442c(this.f3266C);
        TXCAudioRecorder.m3456a().m3450a(this);
        TXCAudioRecorder.m3456a().m3451a(this.f3277j);
    }

    /* renamed from: a */
    public void m2590a(final int i, final int i2, final int i3) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null) {
            return;
        }
        tXICaptureSource.mo1043a(new Runnable() { // from class: com.tencent.liteav.c.1
            @Override // java.lang.Runnable
            public void run() {
                if (i2 != 0 && i3 != 0) {
                    TXCCaptureAndEnc.this.f3278k.f4293a = i2;
                    TXCCaptureAndEnc.this.f3278k.f4294b = i3;
                    if (TXCCaptureAndEnc.this.f3272e != null) {
                        TXCCaptureAndEnc.this.f3272e.mo1047a(i2, i3);
                    }
                }
                if (i == 0 || TXCCaptureAndEnc.this.f3276i == null) {
                    return;
                }
                TXCCaptureAndEnc.this.f3278k.f4295c = i;
                TXCCaptureAndEnc.this.f3276i.m433a(i);
            }
        });
    }

    /* renamed from: i */
    public boolean m2522i() {
        return this.f3280m != 0;
    }

    /* renamed from: j */
    public void m2520j() {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null) {
            return;
        }
        tXICaptureSource.mo1043a(new Runnable() { // from class: com.tencent.liteav.c.5
            @Override // java.lang.Runnable
            public void run() {
                if (TXCCaptureAndEnc.this.f3272e != null) {
                    TXCCaptureAndEnc.this.f3272e.mo1039b(true);
                }
                TXCCaptureAndEnc tXCCaptureAndEnc = TXCCaptureAndEnc.this;
                tXCCaptureAndEnc.m2549c(tXCCaptureAndEnc.f3275h.width, TXCCaptureAndEnc.this.f3275h.height);
            }
        });
    }

    /* renamed from: a */
    public void m2568a(TXCloudVideoView tXCloudVideoView) {
        TXICaptureGLThread tXICaptureGLThread;
        if (this.f3278k.f4284F) {
            TXCLog.m2914e(f3263d, "enable pure audio push , so can not start preview!");
            return;
        }
        if (tXCloudVideoView != null) {
            TXICaptureGLThread gLSurfaceView = tXCloudVideoView.getGLSurfaceView();
            tXICaptureGLThread = gLSurfaceView;
            if (gLSurfaceView == null) {
                TXCGLSurfaceView tXCGLSurfaceView = new TXCGLSurfaceView(tXCloudVideoView.getContext());
                tXCloudVideoView.addVideoView(tXCGLSurfaceView);
                tXICaptureGLThread = tXCGLSurfaceView;
            }
        } else {
            tXICaptureGLThread = new TXCGLSurfaceTextureThread();
        }
        this.f3279l = 0;
        this.f3272e = new TXCCameraCaptureSource(this.f3277j, this.f3278k, tXICaptureGLThread);
        this.f3272e.mo1044a((TXICaptureSourceListener) this);
        this.f3272e.mo1046a((TXINotifyListener) this);
        this.f3272e.mo1051a();
        this.f3272e.mo1040b(this.f3281n);
        this.f3285r = tXCloudVideoView;
        TXCloudVideoView tXCloudVideoView2 = this.f3285r;
        if (tXCloudVideoView2 == null) {
            return;
        }
        TXCLivePushConfig tXCLivePushConfig = this.f3278k;
        tXCloudVideoView2.start(tXCLivePushConfig.f4282D, tXCLivePushConfig.f4283E, this.f3272e);
    }

    /* renamed from: a */
    public void m2566a(boolean z) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null) {
            return;
        }
        tXICaptureSource.mo1042a(z);
        this.f3272e = null;
        TXCloudVideoView tXCloudVideoView = this.f3285r;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.stop(z);
            this.f3285r = null;
        }
        this.f3286s = null;
        TXCGLSurfaceRenderThread tXCGLSurfaceRenderThread = this.f3289v;
        if (tXCGLSurfaceRenderThread == null) {
            return;
        }
        tXCGLSurfaceRenderThread.m3071a();
        this.f3289v = null;
    }

    /* renamed from: a */
    public void m2583a(Surface surface) {
        if (this.f3285r != null) {
            TXCLog.m2911w(f3263d, "camera preview view is not null, can't set surface");
        } else {
            this.f3286s = surface;
        }
    }

    /* renamed from: a */
    public void m2591a(final int i, final int i2) {
        TXCGLSurfaceRenderThread tXCGLSurfaceRenderThread = this.f3289v;
        if (tXCGLSurfaceRenderThread != null) {
            tXCGLSurfaceRenderThread.m3066a(new Runnable() { // from class: com.tencent.liteav.c.6
                @Override // java.lang.Runnable
                public void run() {
                    TXCCaptureAndEnc.this.f3287t = i;
                    TXCCaptureAndEnc.this.f3288u = i2;
                    if (TXCCaptureAndEnc.this.f3291x == null || TXCCaptureAndEnc.this.f3289v == null) {
                        return;
                    }
                    TXCCaptureAndEnc tXCCaptureAndEnc = TXCCaptureAndEnc.this;
                    tXCCaptureAndEnc.m2578a(tXCCaptureAndEnc.f3291x, true);
                }
            });
            return;
        }
        this.f3287t = i;
        this.f3288u = i2;
    }

    /* renamed from: a */
    public void m2581a(final TXITakePhotoListener tXITakePhotoListener) {
        TXCloudVideoView tXCloudVideoView = this.f3285r;
        if (tXCloudVideoView != null) {
            TXCGLSurfaceView gLSurfaceView = tXCloudVideoView.getGLSurfaceView();
            if (gLSurfaceView == null) {
                return;
            }
            gLSurfaceView.m1022a(new TXITakePhotoListener() { // from class: com.tencent.liteav.c.7
                @Override // com.tencent.liteav.basic.p109e.TXITakePhotoListener
                public void onTakePhotoComplete(Bitmap bitmap) {
                    TXITakePhotoListener tXITakePhotoListener2 = tXITakePhotoListener;
                    if (tXITakePhotoListener2 != null) {
                        tXITakePhotoListener2.onTakePhotoComplete(bitmap);
                    }
                }
            });
            return;
        }
        TXCGLSurfaceRenderThread tXCGLSurfaceRenderThread = this.f3289v;
        if (tXCGLSurfaceRenderThread == null) {
            return;
        }
        tXCGLSurfaceRenderThread.m3067a(new TXITakePhotoListener() { // from class: com.tencent.liteav.c.8
            @Override // com.tencent.liteav.basic.p109e.TXITakePhotoListener
            public void onTakePhotoComplete(Bitmap bitmap) {
                TXITakePhotoListener tXITakePhotoListener2 = tXITakePhotoListener;
                if (tXITakePhotoListener2 != null) {
                    tXITakePhotoListener2.onTakePhotoComplete(bitmap);
                }
            }
        });
    }

    /* renamed from: k */
    public void m2518k() {
        if (Build.VERSION.SDK_INT < 21) {
            Bundle bundle = new Bundle();
            bundle.putString("EVT_MSG", "录屏失败,不支持的Android系统版本,需要5.0以上的系统");
            onNotifyEvent(TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_UNSURPORT, bundle);
            String str = f3263d;
            TXLog.m390e(str, "Screen capture need running on Android Lollipop or higher version, current:" + Build.VERSION.SDK_INT);
            return;
        }
        this.f3279l = 1;
        this.f3272e = new TXCScreenCaptureSource(this.f3277j, this.f3278k);
        this.f3272e.mo1046a((TXINotifyListener) this);
        this.f3272e.mo1044a((TXICaptureSourceListener) this);
        this.f3272e.mo1051a();
        TXCDRApi.txReportDAU(this.f3277j, TXCDRDef.f2430aG);
    }

    /* renamed from: l */
    public void m2516l() {
        if (this.f3272e == null) {
            return;
        }
        m2510s();
        this.f3272e.mo1042a(false);
        this.f3272e = null;
    }

    /* renamed from: a */
    public void m2592a(int i) {
        this.f3281n = i;
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null) {
            return;
        }
        tXICaptureSource.mo1040b(i);
    }

    /* renamed from: b */
    public boolean m2553b(boolean z) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null) {
            return false;
        }
        return tXICaptureSource.mo1033d(z);
    }

    /* renamed from: b */
    public boolean m2558b(int i, int i2, int i3) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2608c(i);
            this.f3273f.m2607d(i2);
            this.f3273f.m2606e(i3);
            return true;
        }
        return true;
    }

    /* renamed from: b */
    public void m2560b(int i) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2610b(i);
        }
    }

    /* renamed from: a */
    public void m2585a(Bitmap bitmap) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2626a(bitmap);
        }
    }

    /* renamed from: a */
    public void m2567a(String str) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2618a(str);
        }
    }

    /* renamed from: c */
    public void m2545c(boolean z) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2609b(z);
        }
    }

    @TargetApi(18)
    /* renamed from: b */
    public boolean m2554b(String str) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            return tXCVideoPreprocessor.m2617a(str, true);
        }
        return false;
    }

    /* renamed from: c */
    public void m2550c(int i) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2604g(i);
        }
    }

    /* renamed from: d */
    public void m2542d(int i) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2603h(i);
        }
    }

    /* renamed from: a */
    public void m2594a(float f) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2632a(f);
        }
    }

    /* renamed from: e */
    public void m2534e(int i) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2602i(i);
        }
    }

    /* renamed from: f */
    public void m2530f(int i) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2601j(i);
        }
    }

    /* renamed from: g */
    public void m2527g(int i) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2600k(i);
        }
    }

    /* renamed from: h */
    public void m2524h(int i) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2599l(i);
        }
    }

    /* renamed from: d */
    public void m2537d(boolean z) {
        this.f3266C = z;
        TXCAudioRecorder.m3456a().m3442c(z);
    }

    /* renamed from: m */
    public boolean m2515m() {
        return this.f3266C;
    }

    /* renamed from: b */
    public void m2559b(int i, int i2) {
        this.f3267D = i;
        this.f3268E = i2;
        TXCAudioRecorder.m3456a().m3453a(i, i2);
    }

    /* renamed from: b */
    public void m2561b(float f) {
        this.f3284q = f;
        TXCLiveBGMPlayer.getInstance().setPitch(f);
    }

    /* renamed from: n */
    public int m2514n() {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null) {
            return 0;
        }
        return tXICaptureSource.mo1032e();
    }

    /* renamed from: i */
    public boolean m2521i(int i) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null) {
            return false;
        }
        return tXICaptureSource.mo1048a(i);
    }

    /* renamed from: e */
    public boolean m2532e(boolean z) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null) {
            return false;
        }
        tXICaptureSource.mo1036c(z);
        return true;
    }

    /* renamed from: c */
    public void m2551c(float f) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null) {
            return;
        }
        tXICaptureSource.mo1050a(f);
    }

    /* renamed from: j */
    public void m2519j(int i) {
        this.f3283p = i;
        TXCAudioRecorder.m3456a().m3440d(i);
        TXCDRApi.txReportDAU(this.f3277j, TXCDRDef.f2475az);
    }

    /* renamed from: a */
    private void m2587a(int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putLong(TXCAVRoomConstants.EVT_USERID, this.f3292y);
        bundle.putInt(TXCAVRoomConstants.EVT_ID, i);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (str != null) {
            bundle.putCharSequence("EVT_MSG", str);
        }
        TXCSystemUtil.m2886a(this.f3264A, i, bundle);
    }

    /* renamed from: c */
    public boolean m2546c(String str) {
        if (!TXCAudioRecorder.m3456a().m3444c()) {
            return false;
        }
        TXCDRApi.txReportDAU(this.f3277j, TXCDRDef.f2424aA);
        return TXCLiveBGMPlayer.getInstance().startPlay(str, TXCAudioRecorder.m3456a().m3438f());
    }

    /* renamed from: o */
    public boolean m2513o() {
        TXCLiveBGMPlayer.getInstance().stopPlay();
        return true;
    }

    /* renamed from: p */
    public boolean m2512p() {
        TXCLiveBGMPlayer.getInstance().pause();
        return true;
    }

    /* renamed from: q */
    public boolean m2511q() {
        TXCLiveBGMPlayer.getInstance().resume();
        return true;
    }

    /* renamed from: d */
    public boolean m2543d(float f) {
        this.f3282o = f;
        TXCAudioRecorder.m3456a().m3455a(f);
        return true;
    }

    /* renamed from: e */
    public boolean m2535e(float f) {
        TXCLiveBGMPlayer.getInstance().setVolume(f);
        return true;
    }

    /* renamed from: d */
    public int m2538d(String str) {
        return (int) TXCLiveBGMPlayer.getInstance().getMusicDuration(str);
    }

    /* renamed from: a */
    public void m2569a(TXLivePusher.OnBGMNotify onBGMNotify) {
        if (onBGMNotify == null) {
            this.f3270b = null;
            this.f3271c = null;
            TXCLiveBGMPlayer.getInstance().setOnPlayListener(null);
            return;
        }
        this.f3271c = onBGMNotify;
        if (this.f3270b == null) {
            this.f3270b = new TXIBGMOnPlayListener() { // from class: com.tencent.liteav.c.9
                @Override // com.tencent.liteav.audio.TXIBGMOnPlayListener
                /* renamed from: a */
                public void mo275a() {
                    TXLivePusher.OnBGMNotify onBGMNotify2 = TXCCaptureAndEnc.this.f3271c;
                    if (onBGMNotify2 != null) {
                        onBGMNotify2.onBGMStart();
                    }
                }

                @Override // com.tencent.liteav.audio.TXIBGMOnPlayListener
                /* renamed from: a */
                public void mo274a(int i) {
                    TXLivePusher.OnBGMNotify onBGMNotify2 = TXCCaptureAndEnc.this.f3271c;
                    if (onBGMNotify2 != null) {
                        onBGMNotify2.onBGMComplete(i);
                    }
                }

                @Override // com.tencent.liteav.audio.TXIBGMOnPlayListener
                /* renamed from: a */
                public void mo273a(long j, long j2) {
                    TXLivePusher.OnBGMNotify onBGMNotify2 = TXCCaptureAndEnc.this.f3271c;
                    if (onBGMNotify2 != null) {
                        onBGMNotify2.onBGMProgress(j, j2);
                    }
                }
            };
        }
        TXCLiveBGMPlayer.getInstance().setOnPlayListener(this.f3270b);
    }

    /* renamed from: a */
    public int m2564a(byte[] bArr, int i, int i2, int i3) {
        int m2588a = m2588a(i2, i3, (EGLContext) null);
        if (m2588a != 0) {
            return m2588a;
        }
        TXCVideoEncoder tXCVideoEncoder = this.f3276i;
        if (tXCVideoEncoder == null) {
            return 0;
        }
        tXCVideoEncoder.m416a(bArr, i, i2, i3, TXCTimeUtil.getTimeTick());
        return 0;
    }

    /* renamed from: c */
    public int m2548c(int i, int i2, int i3) {
        int m2588a = m2588a(i2, i3, ((EGL10) EGLContext.getEGL()).eglGetCurrentContext());
        if (m2588a != 0) {
            return m2588a;
        }
        TXCVideoEncoder tXCVideoEncoder = this.f3276i;
        if (tXCVideoEncoder == null) {
            return 0;
        }
        tXCVideoEncoder.m431a(i, i2, i3, TXCTimeUtil.getTimeTick());
        return 0;
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordRawPcmData(byte[] bArr, long j, int i, int i2, int i3, boolean z) {
        AbstractC3410a abstractC3410a = this.f3293z;
        if (abstractC3410a != null) {
            abstractC3410a.onRecordRawPcm(bArr, j, i, i2, i3, z);
        }
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordPcmData(byte[] bArr, long j, int i, int i2, int i3) {
        AbstractC3410a abstractC3410a = this.f3293z;
        if (abstractC3410a != null) {
            abstractC3410a.onRecordPcm(bArr, j, i, i2, i3);
        }
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordEncData(byte[] bArr, long j, int i, int i2, int i3) {
        AbstractC3410a abstractC3410a = this.f3293z;
        if (abstractC3410a != null) {
            abstractC3410a.onEncAudio(bArr, j, i, i2);
        }
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordError(int i, String str) {
        String str2 = f3263d;
        TXCLog.m2914e(str2, "onRecordError code = " + i + ":" + str);
        if (i == TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT) {
            m2587a(TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL, "打开麦克风失败");
        }
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListenerEx
    /* renamed from: a */
    public int mo2580a(TXSVFrame tXSVFrame) {
        TXICustomPreprocessListenner tXICustomPreprocessListenner;
        WeakReference<TXICustomPreprocessListenner> weakReference = this.f3265B;
        if (weakReference != null && (tXICustomPreprocessListenner = weakReference.get()) != null) {
            tXSVFrame.f2714a = tXICustomPreprocessListenner.onTextureCustomProcess(tXSVFrame.f2714a, tXSVFrame.f2717d, tXSVFrame.f2718e);
        }
        m2578a(tXSVFrame, false);
        return tXSVFrame.f2714a;
    }

    @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListenerEx
    /* renamed from: a */
    public void mo2579a(TXSVFrame tXSVFrame, long j) {
        m2540d(tXSVFrame.f2714a, tXSVFrame.f2717d, tXSVFrame.f2718e);
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
    public void onEncodeNAL(TXSNALPacket tXSNALPacket, int i) {
        if (i == 0) {
            AbstractC3410a abstractC3410a = this.f3293z;
            if (abstractC3410a == null || tXSNALPacket == null) {
                return;
            }
            abstractC3410a.onEncVideo(tXSNALPacket);
        } else if (i != 10000004 || this.f3274g != 1) {
        } else {
            this.f3278k.f4302j = 0;
            m2587a(TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL, "硬编码启动失败,采用软编码");
        }
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
    public void onEncodeFormat(MediaFormat mediaFormat) {
        AbstractC3410a abstractC3410a = this.f3293z;
        if (abstractC3410a != null) {
            abstractC3410a.onEncVideoFormat(mediaFormat);
        }
    }

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(int i, Bundle bundle) {
        if (bundle != null) {
            bundle.putLong(TXCAVRoomConstants.EVT_USERID, this.f3292y);
        }
        TXCSystemUtil.m2886a(this.f3264A, i, bundle);
    }

    @Override // com.tencent.liteav.TXCBackgroundPusher.AbstractC3296b
    /* renamed from: a */
    public void mo2584a(final Bitmap bitmap, final ByteBuffer byteBuffer, final int i, final int i2) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource != null) {
            tXICaptureSource.mo1043a(new Runnable() { // from class: com.tencent.liteav.c.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        TXCCaptureAndEnc.this.f3273f.m2623a(TXCSystemUtil.m2891a(width, height, i, i2));
                        TXCCaptureAndEnc.this.f3273f.m2615a(false);
                        TXCCaptureAndEnc.this.f3273f.m2629a(i, i2);
                        TXCCaptureAndEnc.this.f3273f.m2630a(0);
                        TXCCaptureAndEnc.this.f3273f.m2614a(byteBuffer.array(), width, height, 0, 2, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override // com.tencent.liteav.TXCBackgroundPusher.AbstractC3296b
    /* renamed from: a */
    public void mo2570a(final TXCVideoEncoder tXCVideoEncoder) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource != null) {
            tXICaptureSource.mo1043a(new Runnable() { // from class: com.tencent.liteav.c.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (tXCVideoEncoder == null) {
                            return;
                        }
                        tXCVideoEncoder.m434a();
                        tXCVideoEncoder.m419a((TXIVideoEncoderListener) null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (tXCVideoEncoder == null) {
        } else {
            try {
                tXCVideoEncoder.m434a();
                tXCVideoEncoder.m419a((TXIVideoEncoderListener) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.tencent.liteav.TXCBackgroundPusher.AbstractC3296b
    /* renamed from: a */
    public void mo2595a() {
        TXCAudioRecorder.m3456a().m3446b();
        TXCAudioRecorder.m3456a().m3450a((TXIAudioRecordListener) null);
    }

    @Override // com.tencent.liteav.TXICaptureSourceListener
    /* renamed from: a */
    public void mo1029a(SurfaceTexture surfaceTexture) {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2633a();
        }
    }

    @Override // com.tencent.liteav.TXICaptureSourceListener
    /* renamed from: b */
    public void mo1028b(TXSVFrame tXSVFrame) {
        if (this.f3273f == null || this.f3278k.f4284F || this.f3272e == null) {
            return;
        }
        TXSVideoEncoderParam tXSVideoEncoderParam = this.f3275h;
        if (tXSVideoEncoderParam.height != tXSVFrame.f2720g || tXSVideoEncoderParam.width != tXSVFrame.f2719f) {
            m2541d(tXSVFrame.f2719f, tXSVFrame.f2720g);
        }
        this.f3290w = tXSVFrame.f2723j;
        this.f3273f.m2622a(tXSVFrame, tXSVFrame.f2715b, 0);
    }

    @Override // com.tencent.liteav.TXICaptureSourceListener
    /* renamed from: r */
    public void mo1027r() {
        TXICustomPreprocessListenner tXICustomPreprocessListenner;
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            tXCVideoPreprocessor.m2633a();
        }
        m2509t();
        WeakReference<TXICustomPreprocessListenner> weakReference = this.f3265B;
        if (weakReference == null || (tXICustomPreprocessListenner = weakReference.get()) == null) {
            return;
        }
        tXICustomPreprocessListenner.onTextureDestoryed();
    }

    /* renamed from: a */
    private void m2589a(int i, int i2, int i3, EGLContext eGLContext) {
        String str = f3263d;
        TXCLog.m2915d(str, "New encode size width = " + i + " height = " + i2 + " encType = " + i3);
        m2509t();
        this.f3274g = i3;
        this.f3276i = new TXCVideoEncoder(this.f3274g);
        if ((this.f3278k.f4289K & 2) != 2) {
            TXICaptureSource tXICaptureSource = this.f3272e;
            eGLContext = tXICaptureSource != null ? tXICaptureSource.mo1030f() : null;
        } else if (eGLContext == null) {
            eGLContext = this.f3276i.m432a(i, i2);
        }
        TXSVideoEncoderParam tXSVideoEncoderParam = this.f3275h;
        tXSVideoEncoderParam.width = i;
        tXSVideoEncoderParam.height = i2;
        TXCLivePushConfig tXCLivePushConfig = this.f3278k;
        tXSVideoEncoderParam.fps = tXCLivePushConfig.f4300h;
        tXSVideoEncoderParam.gop = tXCLivePushConfig.f4301i;
        tXSVideoEncoderParam.encoderProfile = tXCLivePushConfig.f4306n ? 3 : 1;
        TXSVideoEncoderParam tXSVideoEncoderParam2 = this.f3275h;
        tXSVideoEncoderParam2.encoderMode = 1;
        tXSVideoEncoderParam2.glContext = eGLContext;
        tXSVideoEncoderParam2.realTime = this.f3278k.f4287I;
        this.f3276i.m419a((TXIVideoEncoderListener) this);
        this.f3276i.m428a((TXINotifyListener) this);
        this.f3276i.m427a(this.f3275h);
        this.f3276i.m433a(this.f3278k.f4295c);
        this.f3276i.setID(getID());
    }

    /* renamed from: d */
    private void m2540d(int i, int i2, int i3) {
        m2557b(i2, i3, (EGLContext) null);
        TXCVideoEncoder tXCVideoEncoder = this.f3276i;
        if (tXCVideoEncoder != null) {
            tXCVideoEncoder.m431a(i, i2, i3, TXCTimeUtil.getTimeTick());
        }
    }

    /* renamed from: a */
    private int m2588a(int i, int i2, EGLContext eGLContext) {
        TXCLivePushConfig tXCLivePushConfig = this.f3278k;
        if (tXCLivePushConfig == null) {
            return -5;
        }
        int i3 = tXCLivePushConfig.f4303k;
        int i4 = 1280;
        int i5 = 720;
        if (i3 == 0) {
            i4 = 368;
            i5 = 640;
        } else if (i3 == 1) {
            i4 = 544;
            i5 = 960;
        } else if (i3 == 2) {
            i4 = 720;
            i5 = 1280;
        } else if (i3 == 3) {
            i4 = 640;
            i5 = 368;
        } else if (i3 == 4) {
            i4 = 960;
            i5 = 544;
        } else if (i3 != 5) {
            TXCLog.m2914e(f3263d, "sendCustomYUVData: invalid video_resolution");
            return -1;
        }
        if (i4 > i || i5 > i2) {
            return -4;
        }
        if (this.f3278k.f4284F) {
            m2509t();
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
        m2557b(i4, i5, eGLContext);
        return 0;
    }

    /* renamed from: b */
    private void m2557b(int i, int i2, EGLContext eGLContext) {
        int i3 = this.f3278k.f4302j;
        int i4 = 2;
        if (i3 != 0) {
            if (i3 == 1) {
                i4 = 1;
            } else if (i3 == 2) {
                i4 = 3;
            }
        }
        if (this.f3279l == 1) {
            i4 = 1;
        }
        int i5 = this.f3278k.f4301i;
        if (this.f3276i != null) {
            TXSVideoEncoderParam tXSVideoEncoderParam = this.f3275h;
            if (tXSVideoEncoderParam.width == i && tXSVideoEncoderParam.height == i2 && this.f3274g == i4 && tXSVideoEncoderParam.gop == i5) {
                return;
            }
        }
        m2589a(i, i2, i4, eGLContext);
    }

    /* renamed from: s */
    public void m2510s() {
        if (this.f3276i == null) {
            return;
        }
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource != null) {
            tXICaptureSource.mo1043a(new Runnable() { // from class: com.tencent.liteav.c.12
                @Override // java.lang.Runnable
                public void run() {
                    TXCCaptureAndEnc.this.m2509t();
                }
            });
        } else {
            m2509t();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: t */
    public void m2509t() {
        try {
            if (this.f3276i != null) {
                this.f3276i.m434a();
                this.f3276i.m419a((TXIVideoEncoderListener) null);
                this.f3276i = null;
            }
            this.f3275h.width = 0;
            this.f3275h.height = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: u */
    private void m2508u() {
        if ((this.f3278k.f4289K & 1) != 0) {
            TXCAudioRecorder.m3456a().m3449a(true);
            TXCAudioRecorder.m3456a().m3443c(this.f3278k.f4310r);
            TXCAudioRecorder.m3456a().m3454a(this.f3278k.f4309q);
        } else {
            TXCAudioRecorder.m3456a().m3443c(1);
            TXCAudioRecorder.m3456a().m3454a(this.f3278k.f4309q);
        }
        TXCAudioRecorder.m3456a().m3448a(this.f3278k.f4311s, this.f3277j);
        TXCAudioRecorder.m3456a().m3442c(this.f3266C);
        TXCLiveBGMPlayer.getInstance().switchAecType(TXCAudioRecorder.m3456a().m3438f());
        TXCLiveBGMPlayer.getInstance().setPitch(this.f3284q);
    }

    /* renamed from: k */
    private void m2517k(int i) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource != null && tXICaptureSource.mo1035d()) {
            TXCLivePushConfig tXCLivePushConfig = this.f3278k;
            if (i != tXCLivePushConfig.f4303k && !tXCLivePushConfig.f4291M && this.f3279l == 0) {
                this.f3272e.mo1043a(new Runnable() { // from class: com.tencent.liteav.c.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCCaptureAndEnc.this.f3278k.m1465a();
                        if (TXCCaptureAndEnc.this.f3272e != null) {
                            TXCCaptureAndEnc.this.f3272e.mo1034d(TXCCaptureAndEnc.this.f3278k.f4303k);
                            TXCCaptureAndEnc.this.f3272e.mo1039b(false);
                        }
                    }
                });
                return;
            }
        }
        this.f3278k.m1465a();
        TXICaptureSource tXICaptureSource2 = this.f3272e;
        if (tXICaptureSource2 == null || !tXICaptureSource2.mo1035d()) {
            return;
        }
        this.f3272e.mo1043a(new Runnable() { // from class: com.tencent.liteav.c.3
            @Override // java.lang.Runnable
            public void run() {
                if (TXCCaptureAndEnc.this.f3272e != null) {
                    TXCCaptureAndEnc.this.f3272e.mo1047a(TXCCaptureAndEnc.this.f3278k.f4293a, TXCCaptureAndEnc.this.f3278k.f4294b);
                    TXCCaptureAndEnc.this.f3272e.mo1034d(TXCCaptureAndEnc.this.f3278k.f4303k);
                }
            }
        });
    }

    /* renamed from: v */
    private void m2507v() {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource != null) {
            tXICaptureSource.mo1043a(new Runnable() { // from class: com.tencent.liteav.c.4
                @Override // java.lang.Runnable
                public void run() {
                    TXCCaptureAndEnc tXCCaptureAndEnc = TXCCaptureAndEnc.this;
                    tXCCaptureAndEnc.m2549c(tXCCaptureAndEnc.f3275h.width, TXCCaptureAndEnc.this.f3275h.height);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m2549c(int i, int i2) {
        TXCLivePushConfig tXCLivePushConfig = this.f3278k;
        float f = tXCLivePushConfig.f4281C;
        if (f != -1.0f) {
            TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
            if (tXCVideoPreprocessor == null) {
                return;
            }
            tXCVideoPreprocessor.m2625a(tXCLivePushConfig.f4316x, tXCLivePushConfig.f4279A, tXCLivePushConfig.f4280B, f);
            return;
        }
        TXCVideoPreprocessor tXCVideoPreprocessor2 = this.f3273f;
        if (tXCVideoPreprocessor2 == null || i == 0 || i2 == 0) {
            return;
        }
        Bitmap bitmap = tXCLivePushConfig.f4316x;
        float f2 = i;
        tXCVideoPreprocessor2.m2625a(bitmap, tXCLivePushConfig.f4317y / f2, tXCLivePushConfig.f4318z / i2, bitmap == null ? 0.0f : bitmap.getWidth() / f2);
    }

    /* renamed from: w */
    private void m2506w() {
        TXCVideoPreprocessor tXCVideoPreprocessor = this.f3273f;
        if (tXCVideoPreprocessor != null) {
            if (this.f3278k.f4287I) {
                tXCVideoPreprocessor.m2605f(0);
            } else {
                tXCVideoPreprocessor.m2605f(3);
            }
        }
    }

    /* renamed from: d */
    private void m2541d(int i, int i2) {
        m2549c(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m2578a(TXSVFrame tXSVFrame, boolean z) {
        TXICaptureSource tXICaptureSource;
        this.f3291x = tXSVFrame;
        if (this.f3285r != null) {
            TXICaptureSource tXICaptureSource2 = this.f3272e;
            if (tXICaptureSource2 == null) {
                return;
            }
            tXICaptureSource2.mo1045a(tXSVFrame);
            return;
        }
        if (this.f3286s != null) {
            TXCGLSurfaceRenderThread tXCGLSurfaceRenderThread = this.f3289v;
            if (tXCGLSurfaceRenderThread != null && tXCGLSurfaceRenderThread.m3064b() != this.f3286s) {
                this.f3289v.m3071a();
                this.f3289v = null;
            }
            if (this.f3289v == null && (tXICaptureSource = this.f3272e) != null && tXICaptureSource.mo1030f() != null) {
                this.f3289v = new TXCGLSurfaceRenderThread();
                this.f3289v.m3065a(this.f3272e.mo1030f(), this.f3286s);
            }
        } else {
            TXCGLSurfaceRenderThread tXCGLSurfaceRenderThread2 = this.f3289v;
            if (tXCGLSurfaceRenderThread2 != null) {
                tXCGLSurfaceRenderThread2.m3071a();
                this.f3289v = null;
            }
        }
        TXCGLSurfaceRenderThread tXCGLSurfaceRenderThread3 = this.f3289v;
        if (tXCGLSurfaceRenderThread3 == null) {
            return;
        }
        tXCGLSurfaceRenderThread3.m3069a(tXSVFrame.f2714a, tXSVFrame.f2721h, this.f3290w, this.f3287t, this.f3288u, tXSVFrame.f2717d, tXSVFrame.f2718e, z);
    }

    /* renamed from: a */
    public void m2593a(float f, float f2) {
        TXICaptureSource tXICaptureSource = this.f3272e;
        if (tXICaptureSource == null || !this.f3278k.f4282D) {
            return;
        }
        tXICaptureSource.mo1049a(f, f2);
    }
}
