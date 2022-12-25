package com.tencent.liteav;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.TextureView;
import com.tencent.liteav.TXCRenderAndDec;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXCStreamDownloader;
import com.tencent.liteav.network.TXIStreamDownloaderListener;
import com.tencent.liteav.p123h.TXCStreamRecord;
import com.tencent.liteav.renderer.TXCGLRender;
import com.tencent.liteav.renderer.TXCVideoRender;
import com.tencent.liteav.renderer.TXIVideoRenderTextureListener;
import com.tencent.liteav.renderer.TXTweenFilter;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;

/* renamed from: com.tencent.liteav.g */
/* loaded from: classes3.dex */
public class TXCLivePlayer extends TXIPlayer implements TXINotifyListener, TXCRenderAndDec.AbstractC3529a, TXIStreamDownloaderListener, TXCGLRender.AbstractC3612a, TXIVideoRenderTextureListener {

    /* renamed from: A */
    private TXLivePlayer.ITXAudioRawDataListener f4024A;

    /* renamed from: f */
    private TXCGLRender f4031f;

    /* renamed from: i */
    private TextureView f4034i;

    /* renamed from: p */
    private TXCStreamRecord f4041p;

    /* renamed from: q */
    private TXRecordCommon.ITXVideoRecordListener f4042q;

    /* renamed from: r */
    private TXCDataReport f4043r;

    /* renamed from: z */
    private int f4051z;

    /* renamed from: a */
    private TXCRenderAndDec f4030a = null;

    /* renamed from: g */
    private TXCStreamDownloader f4032g = null;

    /* renamed from: j */
    private boolean f4035j = false;

    /* renamed from: k */
    private boolean f4036k = false;

    /* renamed from: l */
    private int f4037l = 0;

    /* renamed from: m */
    private int f4038m = 0;

    /* renamed from: n */
    private int f4039n = 16;

    /* renamed from: o */
    private boolean f4040o = false;

    /* renamed from: s */
    private int f4044s = 0;

    /* renamed from: t */
    private int f4045t = 0;

    /* renamed from: u */
    private TXTweenFilter f4046u = null;

    /* renamed from: v */
    private TXTweenFilter f4047v = null;

    /* renamed from: w */
    private float[] f4048w = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};

    /* renamed from: x */
    private float[] f4049x = new float[16];

    /* renamed from: y */
    private String f4050y = "";

    /* renamed from: B */
    private String f4025B = "";

    /* renamed from: C */
    private boolean f4026C = false;

    /* renamed from: D */
    private long f4027D = 0;

    /* renamed from: E */
    private long f4028E = 0;

    /* renamed from: F */
    private boolean f4029F = false;

    /* renamed from: h */
    private Handler f4033h = new Handler(Looper.getMainLooper());

    @Override // com.tencent.liteav.TXCRenderAndDec.AbstractC3529a
    /* renamed from: a */
    public void mo1375a(long j) {
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: f */
    public boolean mo817f() {
        return true;
    }

    public TXCLivePlayer(Context context) {
        super(context);
        this.f4031f = null;
        this.f4031f = new TXCGLRender();
        this.f4031f.m887a((TXINotifyListener) this);
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo833a(TXCloudVideoView tXCloudVideoView) {
        TextureView videoView;
        TXCloudVideoView tXCloudVideoView2 = this.f5204d;
        if (tXCloudVideoView2 != null && tXCloudVideoView2 != tXCloudVideoView && (videoView = tXCloudVideoView2.getVideoView()) != null) {
            this.f5204d.removeView(videoView);
        }
        super.mo833a(tXCloudVideoView);
        TXCloudVideoView tXCloudVideoView3 = this.f5204d;
        if (tXCloudVideoView3 != null) {
            this.f4034i = tXCloudVideoView3.getVideoView();
            if (this.f4034i == null) {
                this.f4034i = new TextureView(this.f5204d.getContext());
            }
            this.f5204d.addVideoView(this.f4034i);
        }
        TXCGLRender tXCGLRender = this.f4031f;
        if (tXCGLRender != null) {
            tXCGLRender.m888a(this.f4034i);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo838a(Surface surface) {
        TXCGLRender tXCGLRender = this.f4031f;
        if (tXCGLRender != null) {
            tXCGLRender.m889a(surface);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo840a(int i, int i2) {
        TXCGLRender tXCGLRender = this.f4031f;
        if (tXCGLRender != null) {
            tXCGLRender.m874c(i, i2);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo836a(TXCPlayerConfig tXCPlayerConfig) {
        super.mo836a(tXCPlayerConfig);
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.m1411a(tXCPlayerConfig);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public int mo830a(String str, int i) {
        if (mo822c()) {
            TXCLog.m2911w("TXCLivePlayer", "play: ignore start play when is playing");
            return -2;
        }
        this.f4050y = str;
        this.f4051z = i;
        m1767b(str);
        this.f4036k = true;
        m1763e(i);
        int m1766b = m1766b(str, i);
        if (m1766b != 0) {
            this.f4036k = false;
            m1755l();
            m1756k();
            TextureView textureView = this.f4034i;
            if (textureView != null) {
                textureView.setVisibility(8);
            }
        } else {
            m1754m();
            m1750r();
            TXCDRApi.txReportDAU(this.f5203c, TXCDRDef.f2499bq);
            try {
                if (Class.forName("com.tencent.liteav.demo.play.SuperPlayerView") != null) {
                    TXCDRApi.txReportDAU(this.f5203c, TXCDRDef.f2478bB);
                }
            } catch (Exception unused) {
            }
        }
        return m1766b;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public int mo829a(boolean z) {
        if (!mo822c()) {
            TXCLog.m2911w("TXCLivePlayer", "play: ignore stop play when not started");
            return -2;
        }
        TXCLog.m2912v("TXCLivePlayer", "play: stop");
        this.f4036k = false;
        m1755l();
        m1756k();
        TextureView textureView = this.f4034i;
        if (textureView != null && z) {
            textureView.setVisibility(8);
        }
        m1753n();
        m1749s();
        m1752o();
        return 0;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public int mo831a(String str) {
        TXCStreamDownloader tXCStreamDownloader;
        if (!mo822c() || (tXCStreamDownloader = this.f4032g) == null) {
            return -1;
        }
        boolean switchStream = tXCStreamDownloader.switchStream(str);
        if (this.f4030a != null) {
            TXCLog.m2911w("TXCLivePlayer", " stream_switch video cache " + this.f4030a.m1394e() + " audio cache " + this.f4030a.m1396d());
        }
        if (!switchStream) {
            return -2;
        }
        this.f4050y = str;
        return 0;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo842a() {
        mo829a(false);
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: b */
    public void mo826b() {
        mo830a(this.f4050y, this.f4051z);
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: c */
    public boolean mo822c() {
        return this.f4036k;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo841a(int i) {
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.m1418a(i);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: b */
    public void mo824b(int i) {
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.m1404b(i);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: b */
    public void mo823b(boolean z) {
        this.f4035j = z;
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.m1397c(this.f4035j);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo839a(Context context, int i) {
        TXCRenderAndDec.m1415a(context, i);
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo832a(TXRecordCommon.ITXVideoRecordListener iTXVideoRecordListener) {
        this.f4042q = iTXVideoRecordListener;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: c */
    public int mo821c(int i) {
        if (this.f4040o) {
            TXCLog.m2914e("TXCLivePlayer", "startRecord: there is existing uncompleted record task");
            return -1;
        }
        this.f4040o = true;
        this.f4031f.m944a((TXIVideoRenderTextureListener) this);
        this.f4031f.m945a((TXCGLRender.AbstractC3612a) this);
        TXCDRApi.txReportDAU(this.f5203c, TXCDRDef.f2471av);
        return 0;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: d */
    public TextureView mo819d() {
        return this.f4034i;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo834a(TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener) {
        this.f4024A = iTXAudioRawDataListener;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: e */
    public int mo818e() {
        if (!this.f4040o) {
            TXCLog.m2911w("TXCLivePlayer", "stopRecord: no recording task exist");
            return -1;
        }
        this.f4040o = false;
        TXCStreamRecord tXCStreamRecord = this.f4041p;
        if (tXCStreamRecord != null) {
            tXCStreamRecord.m1464a();
            this.f4041p = null;
        }
        return 0;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public boolean mo828a(byte[] bArr) {
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (tXCRenderAndDec != null) {
            return tXCRenderAndDec.m1406a(bArr);
        }
        return false;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo835a(TXIVideoRawDataListener tXIVideoRawDataListener) {
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.m1408a(tXIVideoRawDataListener);
        }
    }

    /* renamed from: h */
    private void m1759h() {
        if (this.f4041p == null) {
            this.f4044s = this.f4031f.m867m();
            this.f4045t = this.f4031f.m866n();
            TXCStreamRecord.C3509a m1757j = m1757j();
            this.f4041p = new TXCStreamRecord(this.f5203c);
            this.f4041p.m1459a(m1757j);
            this.f4041p.m1458a(new TXCStreamRecord.AbstractC3510b() { // from class: com.tencent.liteav.g.1
                @Override // com.tencent.liteav.p123h.TXCStreamRecord.AbstractC3510b
                /* renamed from: a */
                public void mo1454a(int i, String str, String str2, String str3) {
                    if (TXCLivePlayer.this.f4042q != null) {
                        TXRecordCommon.TXRecordResult tXRecordResult = new TXRecordCommon.TXRecordResult();
                        if (i == 0) {
                            tXRecordResult.retCode = 0;
                        } else {
                            tXRecordResult.retCode = -1;
                        }
                        tXRecordResult.descMsg = str;
                        tXRecordResult.videoPath = str2;
                        tXRecordResult.coverPath = str3;
                        TXCLivePlayer.this.f4042q.onRecordComplete(tXRecordResult);
                    }
                    TXCLivePlayer.this.f4031f.m944a((TXIVideoRenderTextureListener) null);
                    TXCLivePlayer.this.f4031f.m945a((TXCGLRender.AbstractC3612a) null);
                }

                @Override // com.tencent.liteav.p123h.TXCStreamRecord.AbstractC3510b
                /* renamed from: a */
                public void mo1453a(long j) {
                    if (TXCLivePlayer.this.f4042q != null) {
                        TXCLivePlayer.this.f4042q.onRecordProgress(j);
                    }
                }
            });
        }
        if (this.f4046u == null) {
            this.f4046u = new TXTweenFilter(true);
            this.f4046u.m851b();
            this.f4046u.m849b(this.f4044s, this.f4045t);
            this.f4046u.m856a(this.f4044s, this.f4045t);
        }
        if (this.f4047v == null) {
            this.f4047v = new TXTweenFilter(false);
            this.f4047v.m851b();
            this.f4047v.m849b(this.f4031f.m869k(), this.f4031f.m868l());
            this.f4047v.m856a(this.f4031f.m869k(), this.f4031f.m868l());
            Matrix.setIdentityM(this.f4049x, 0);
        }
    }

    /* renamed from: i */
    private void m1758i() {
        TXTweenFilter tXTweenFilter = this.f4046u;
        if (tXTweenFilter != null) {
            tXTweenFilter.m847c();
            this.f4046u = null;
        }
        TXTweenFilter tXTweenFilter2 = this.f4047v;
        if (tXTweenFilter2 != null) {
            tXTweenFilter2.m847c();
            this.f4047v = null;
        }
    }

    /* renamed from: j */
    private TXCStreamRecord.C3509a m1757j() {
        int i;
        int i2 = this.f4044s;
        if (i2 <= 0 || (i = this.f4045t) <= 0) {
            i2 = 480;
            i = 640;
        }
        TXCStreamRecord.C3509a c3509a = new TXCStreamRecord.C3509a();
        c3509a.f4329a = i2;
        c3509a.f4330b = i;
        c3509a.f4331c = 20;
        c3509a.f4332d = (int) (Math.sqrt((i2 * i2 * 1.0d) + (i * i)) * 1.2d);
        c3509a.f4336h = this.f4037l;
        c3509a.f4337i = this.f4038m;
        c3509a.f4338j = this.f4039n;
        c3509a.f4334f = TXCStreamRecord.m1460a(this.f5203c, ".mp4");
        c3509a.f4335g = TXCStreamRecord.m1460a(this.f5203c, ".jpg");
        c3509a.f4333e = this.f4031f.m942b();
        TXCLog.m2915d("TXCLivePlayer", "record config: " + c3509a);
        return c3509a;
    }

    /* renamed from: e */
    private void m1763e(int i) {
        TextureView textureView = this.f4034i;
        boolean z = false;
        if (textureView != null) {
            textureView.setVisibility(0);
        }
        this.f4030a = new TXCRenderAndDec(this.f5203c, 1);
        this.f4030a.m1414a((TXINotifyListener) this);
        this.f4030a.m1409a(this.f4031f);
        this.f4030a.m1410a((TXCRenderAndDec.AbstractC3529a) this);
        this.f4030a.m1411a(this.f5202b);
        this.f4030a.setID(this.f4025B);
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (i == 5) {
            z = true;
        }
        tXCRenderAndDec.m1402b(z);
        this.f4030a.m1397c(this.f4035j);
    }

    /* renamed from: k */
    private void m1756k() {
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.m1401c();
            this.f4030a.m1409a((TXCVideoRender) null);
            this.f4030a.m1410a((TXCRenderAndDec.AbstractC3529a) null);
            this.f4030a.m1414a((TXINotifyListener) null);
            this.f4030a = null;
        }
    }

    /* renamed from: b */
    private int m1766b(String str, int i) {
        boolean z = false;
        if (i == 0) {
            this.f4032g = new TXCStreamDownloader(this.f5203c, 1);
        } else if (i == 5) {
            this.f4032g = new TXCStreamDownloader(this.f5203c, 4);
        } else {
            this.f4032g = new TXCStreamDownloader(this.f5203c, 0);
        }
        this.f4032g.setID(this.f4025B);
        this.f4032g.setListener(this);
        this.f4032g.setNotifyListener(this);
        this.f4032g.setHeaders(this.f5202b.f4354p);
        if (i == 5) {
            z = true;
        }
        if (z) {
            this.f4032g.setRetryTimes(5);
            this.f4032g.setRetryInterval(1);
        } else {
            this.f4032g.setRetryTimes(this.f5202b.f4343e);
            this.f4032g.setRetryInterval(this.f5202b.f4344f);
        }
        TXCStreamDownloader tXCStreamDownloader = this.f4032g;
        TXCPlayerConfig tXCPlayerConfig = this.f5202b;
        return tXCStreamDownloader.start(str, tXCPlayerConfig.f4348j, tXCPlayerConfig.f4350l, tXCPlayerConfig.f4349k);
    }

    /* renamed from: l */
    private void m1755l() {
        TXCStreamDownloader tXCStreamDownloader = this.f4032g;
        if (tXCStreamDownloader != null) {
            tXCStreamDownloader.setListener(null);
            this.f4032g.setNotifyListener(null);
            this.f4032g.stop();
            this.f4032g = null;
        }
    }

    /* renamed from: m */
    private void m1754m() {
        this.f4043r = new TXCDataReport(this.f5203c);
        this.f4043r.m2382a(this.f4050y);
        this.f4043r.m2381a(this.f4051z == 5);
        this.f4043r.m2373d(this.f4025B);
        this.f4043r.m2385a();
    }

    /* renamed from: n */
    private void m1753n() {
        TXCDataReport tXCDataReport = this.f4043r;
        if (tXCDataReport != null) {
            tXCDataReport.m2376c();
            this.f4043r = null;
        }
    }

    /* renamed from: b */
    private void m1767b(String str) {
        this.f4025B = String.format("%s-%d", str, Long.valueOf(TXCTimeUtil.getTimeTick() % 10000));
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.setID(this.f4025B);
        }
        TXCGLRender tXCGLRender = this.f4031f;
        if (tXCGLRender != null) {
            tXCGLRender.setID(this.f4025B);
        }
        TXCStreamDownloader tXCStreamDownloader = this.f4032g;
        if (tXCStreamDownloader != null) {
            tXCStreamDownloader.setID(this.f4025B);
        }
        TXCDataReport tXCDataReport = this.f4043r;
        if (tXCDataReport != null) {
            tXCDataReport.m2373d(this.f4025B);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: g */
    public void mo816g() {
        this.f4028E = 0L;
        if (this.f4026C) {
            return;
        }
        this.f4026C = true;
        Handler handler = this.f4033h;
        if (handler == null) {
            return;
        }
        handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.g.2
            @Override // java.lang.Runnable
            public void run() {
                if (TXCLivePlayer.this.f4026C) {
                    TXCLivePlayer.this.m1751q();
                }
            }
        }, 1000L);
    }

    /* renamed from: o */
    private void m1752o() {
        this.f4026C = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: q */
    public void m1751q() {
        if (this.f4027D > 0) {
            Bundle bundle = new Bundle();
            bundle.putInt(TXLiveConstants.EVT_PLAY_PROGRESS, (int) (this.f4027D / 1000));
            bundle.putInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS, (int) this.f4027D);
            onNotifyEvent(TXLiveConstants.PLAY_EVT_PLAY_PROGRESS, bundle);
        }
        Handler handler = this.f4033h;
        if (handler == null || !this.f4026C) {
            return;
        }
        handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.g.3
            @Override // java.lang.Runnable
            public void run() {
                if (TXCLivePlayer.this.f4026C) {
                    TXCLivePlayer.this.m1751q();
                }
            }
        }, 1000L);
    }

    /* renamed from: r */
    private void m1750r() {
        this.f4029F = true;
        Handler handler = this.f4033h;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.g.4
                @Override // java.lang.Runnable
                public void run() {
                    if (TXCLivePlayer.this.f4029F) {
                        TXCLivePlayer.this.m1748t();
                    }
                }
            }, 2000L);
        }
    }

    /* renamed from: s */
    private void m1749s() {
        this.f4029F = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: t */
    public void m1748t() {
        int[] m2894a = TXCSystemUtil.m2894a();
        String str = (m2894a[0] / 10) + "/" + (m2894a[1] / 10) + "%";
        int m2904d = TXCStatus.m2904d(this.f4025B, 7102);
        int m2904d2 = TXCStatus.m2904d(this.f4025B, 7101);
        String m2905c = TXCStatus.m2905c(this.f4025B, 7110);
        int m2903e = (int) TXCStatus.m2903e(this.f4025B, 6002);
        Bundle bundle = new Bundle();
        TXCGLRender tXCGLRender = this.f4031f;
        if (tXCGLRender != null) {
            bundle.putInt("VIDEO_WIDTH", tXCGLRender.m867m());
            bundle.putInt("VIDEO_HEIGHT", this.f4031f.m866n());
        }
        TXCRenderAndDec tXCRenderAndDec = this.f4030a;
        if (tXCRenderAndDec != null) {
            bundle.putInt("CACHE_SIZE", (int) tXCRenderAndDec.m1394e());
            bundle.putInt("CODEC_CACHE", (int) this.f4030a.m1396d());
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE_SIZE, (int) this.f4030a.m1393f());
            bundle.putInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE, this.f4030a.m1392g());
            bundle.putInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL, (int) this.f4030a.m1391h());
            bundle.putString(TXLiveConstants.NET_STATUS_AUDIO_INFO, this.f4030a.m1386m());
            bundle.putInt("NET_JITTER", this.f4030a.m1390i());
            bundle.putInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL, (int) this.f4030a.m1389j());
            bundle.putFloat(TXLiveConstants.NET_STATUS_AUDIO_PLAY_SPEED, this.f4030a.m1388k());
            bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_GOP, (int) ((((this.f4030a.m1387l() * 10) / (m2903e == 0 ? 15 : m2903e)) / 10.0f) + 0.5d));
        }
        bundle.putInt("NET_SPEED", m2904d2 + m2904d);
        bundle.putInt("VIDEO_FPS", m2903e);
        bundle.putInt("VIDEO_BITRATE", m2904d2);
        bundle.putInt("AUDIO_BITRATE", m2904d);
        bundle.putCharSequence("SERVER_IP", m2905c);
        bundle.putCharSequence("CPU_USAGE", str);
        TXCSystemUtil.m2886a(this.f5205e, 15001, bundle);
        TXCRenderAndDec tXCRenderAndDec2 = this.f4030a;
        if (tXCRenderAndDec2 != null) {
            tXCRenderAndDec2.m1385n();
        }
        TXCDataReport tXCDataReport = this.f4043r;
        if (tXCDataReport != null) {
            tXCDataReport.m2370f();
        }
        Handler handler = this.f4033h;
        if (handler == null || !this.f4029F) {
            return;
        }
        handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.g.5
            @Override // java.lang.Runnable
            public void run() {
                if (TXCLivePlayer.this.f4029F) {
                    TXCLivePlayer.this.m1748t();
                }
            }
        }, 2000L);
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloaderListener
    public void onPullAudio(TXSAudioPacket tXSAudioPacket) {
        TXCRenderAndDec tXCRenderAndDec;
        if (this.f4036k && (tXCRenderAndDec = this.f4030a) != null) {
            tXCRenderAndDec.m1413a(tXSAudioPacket);
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloaderListener
    public void onPullNAL(TXSNALPacket tXSNALPacket) {
        if (!this.f4036k) {
            return;
        }
        try {
            if (this.f4030a == null) {
                return;
            }
            this.f4030a.m1412a(tXSNALPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(final int i, final Bundle bundle) {
        TXCRenderAndDec tXCRenderAndDec;
        if (-2302 == i && (tXCRenderAndDec = this.f4030a) != null) {
            tXCRenderAndDec.m1400c((int) TXCConfigCenter.m2988a().m2979a("Audio", "SmoothModeAdjust"));
        }
        Handler handler = this.f4033h;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.g.6
                @Override // java.lang.Runnable
                public void run() {
                    TXCSystemUtil.m2886a(TXCLivePlayer.this.f5205e, i, bundle);
                    if (i != 2103 || TXCLivePlayer.this.f4030a == null) {
                        return;
                    }
                    TXCLivePlayer.this.f4030a.m1381r();
                }
            });
        }
    }

    @Override // com.tencent.liteav.renderer.TXIVideoRenderTextureListener
    /* renamed from: a */
    public int mo859a(int i, float[] fArr) {
        TXTweenFilter tXTweenFilter;
        TXCStreamRecord tXCStreamRecord = this.f4041p;
        if (this.f4040o && tXCStreamRecord != null && (tXTweenFilter = this.f4046u) != null) {
            int m844d = tXTweenFilter.m844d(i);
            tXCStreamRecord.m1462a(m844d, TXCTimeUtil.getTimeTick());
            this.f4031f.mo894a(m844d, this.f4044s, this.f4045t, false, 0);
        }
        if (this.f4040o) {
            m1759h();
        } else {
            m1758i();
        }
        return i;
    }

    @Override // com.tencent.liteav.renderer.TXCGLRender.AbstractC3612a
    /* renamed from: d */
    public void mo935d(int i) {
        TXTweenFilter tXTweenFilter;
        TXCStreamRecord tXCStreamRecord = this.f4041p;
        if (this.f4040o && tXCStreamRecord != null && (tXTweenFilter = this.f4047v) != null) {
            tXTweenFilter.m852a(this.f4048w);
            tXCStreamRecord.m1462a(this.f4047v.m844d(i), TXCTimeUtil.getTimeTick());
            this.f4047v.m852a(this.f4049x);
            this.f4047v.m846c(i);
        }
        if (this.f4040o) {
            m1759h();
        } else {
            m1758i();
        }
    }

    @Override // com.tencent.liteav.TXCRenderAndDec.AbstractC3529a
    /* renamed from: a */
    public void mo1374a(SurfaceTexture surfaceTexture) {
        m1758i();
        mo818e();
    }

    @Override // com.tencent.liteav.TXCRenderAndDec.AbstractC3529a
    /* renamed from: a */
    public void mo1371a(byte[] bArr, long j) {
        if (this.f4041p != null) {
            if (j <= 0) {
                j = TXCTimeUtil.getTimeTick();
            }
            this.f4041p.m1456a(bArr, j);
        }
        TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener = this.f4024A;
        if (iTXAudioRawDataListener != null) {
            iTXAudioRawDataListener.onPcmDataAvailable(bArr, j);
        }
        long j2 = this.f4028E;
        if (j2 <= 0) {
            this.f4028E = j;
        } else {
            this.f4027D = j - j2;
        }
    }

    @Override // com.tencent.liteav.TXCRenderAndDec.AbstractC3529a
    /* renamed from: a */
    public void mo1373a(TXSAudioPacket tXSAudioPacket) {
        TXCLog.m2915d("TXCLivePlayer", "onPlayAudioInfoChanged, samplerate=" + tXSAudioPacket.sampleRate + ", channels=" + tXSAudioPacket.channelsPerSample + ", bits=" + tXSAudioPacket.bitsPerChannel);
        this.f4037l = tXSAudioPacket.channelsPerSample;
        this.f4038m = tXSAudioPacket.sampleRate;
        int i = tXSAudioPacket.bitsPerChannel;
        if (i > 1) {
            this.f4039n = i;
        }
        TXLivePlayer.ITXAudioRawDataListener iTXAudioRawDataListener = this.f4024A;
        if (iTXAudioRawDataListener != null) {
            iTXAudioRawDataListener.onAudioInfoChanged(tXSAudioPacket.sampleRate, tXSAudioPacket.channelsPerSample, tXSAudioPacket.bitsPerChannel);
        }
    }
}
