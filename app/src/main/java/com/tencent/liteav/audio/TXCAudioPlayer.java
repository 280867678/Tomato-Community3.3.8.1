package com.tencent.liteav.audio;

import android.content.Context;
import com.tencent.liteav.audio.impl.Play.TXAudioJitterBufferReportInfo;
import com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController;
import com.tencent.liteav.audio.impl.Play.TXCAudioSysPlayController;
import com.tencent.liteav.audio.impl.Play.TXCAudioTraePlayController;
import com.tencent.liteav.audio.impl.Play.TXCMultAudioTrackPlayer;
import com.tencent.liteav.audio.impl.TXCAudioRouteMgr;
import com.tencent.liteav.audio.impl.TXCAudioUtil;
import com.tencent.liteav.audio.impl.TXCTelephonyMgr;
import com.tencent.liteav.audio.impl.TXCTraeJNI;
import com.tencent.liteav.audio.impl.TXITelephonyMrgListener;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;

/* renamed from: com.tencent.liteav.audio.a */
/* loaded from: classes3.dex */
public class TXCAudioPlayer implements TXITelephonyMrgListener {

    /* renamed from: i */
    private TXIAudioPlayListener f2031i;

    /* renamed from: s */
    private Context f2041s;

    /* renamed from: g */
    private static final String f2029g = "AudioCenter:" + TXCAudioPlayer.class.getSimpleName();

    /* renamed from: a */
    public static final int f2023a = TXEAudioDef.TXE_AEC_NONE;

    /* renamed from: b */
    public static float f2024b = 5.0f;

    /* renamed from: c */
    public static boolean f2025c = true;

    /* renamed from: d */
    public static float f2026d = 5.0f;

    /* renamed from: e */
    public static float f2027e = 1.0f;

    /* renamed from: f */
    public static boolean f2028f = false;

    /* renamed from: h */
    private TXCAudioBasePlayController f2030h = null;

    /* renamed from: j */
    private int f2032j = f2023a;

    /* renamed from: k */
    private float f2033k = f2024b;

    /* renamed from: l */
    private boolean f2034l = f2025c;

    /* renamed from: m */
    private float f2035m = f2026d;

    /* renamed from: n */
    private float f2036n = f2027e;

    /* renamed from: o */
    private boolean f2037o = false;

    /* renamed from: p */
    private boolean f2038p = false;

    /* renamed from: q */
    private boolean f2039q = f2028f;

    /* renamed from: r */
    private int f2040r = 0;

    /* renamed from: a */
    public synchronized int m3475a(Context context) {
        if (context == null) {
            TXCLog.m2914e(f2029g, "invalid param, start play failed!");
            return TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
        }
        if (TXCAudioUtil.m3347c(this.f2032j) != TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK) {
            String str = f2029g;
            TXCLog.m2911w(str, "start player failed, with aec type " + this.f2032j + ", invalid aec recorder has started!");
        }
        if (this.f2030h != null && this.f2030h.isPlaying()) {
            TXCLog.m2914e(f2029g, "play has started, can not start again!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION;
        }
        this.f2041s = context;
        TXCAudioRouteMgr.m3371a().m3368a(context);
        TXCTelephonyMgr.m3346a().m3344a(this.f2041s);
        TXCTelephonyMgr.m3346a().m3340a(this);
        if (this.f2030h == null) {
            if (this.f2032j == TXEAudioDef.TXE_AEC_TRAE) {
                this.f2030h = new TXCAudioTraePlayController(context.getApplicationContext());
            } else {
                this.f2030h = new TXCAudioSysPlayController(context.getApplicationContext());
            }
        }
        if (this.f2030h != null) {
            m3476a(this.f2032j, this.f2041s);
            m3473a(this.f2031i);
            m3478a(this.f2033k);
            m3471a(this.f2034l);
            m3468b(this.f2035m);
            m3465c(this.f2036n);
            m3464c(this.f2037o);
            m3467b(this.f2038p);
            m3477a(this.f2040r);
            m3462d(this.f2039q);
            return this.f2030h.startPlay();
        }
        TXCLog.m2914e(f2029g, "start play failed! controller is null!");
        return TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
    }

    /* renamed from: a */
    public synchronized int m3479a() {
        int i;
        i = TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
        this.f2031i = null;
        this.f2033k = f2024b;
        this.f2034l = f2025c;
        this.f2035m = f2026d;
        this.f2036n = f2027e;
        this.f2037o = false;
        this.f2038p = false;
        this.f2039q = f2028f;
        this.f2040r = 0;
        this.f2041s = null;
        if (this.f2030h != null) {
            i = this.f2030h.stopPlay();
            this.f2030h = null;
        }
        TXCTelephonyMgr.m3346a().m3338b(this);
        return i;
    }

    /* renamed from: a */
    public void m3470a(boolean z, Context context) {
        if (!z) {
            m3476a(TXEAudioDef.TXE_AEC_NONE, context);
        } else if (TXCConfigCenter.m2988a().m2960g()) {
            m3476a(TXEAudioDef.TXE_AEC_SYSTEM, context);
        } else {
            TXCAudioRouteMgr.m3361a(TXCConfigCenter.m2988a().m2974b());
            m3476a(TXEAudioDef.TXE_AEC_TRAE, context);
        }
    }

    /* renamed from: a */
    private void m3476a(int i, Context context) {
        if (i == TXEAudioDef.TXE_AEC_TRAE && !TXCTraeJNI.nativeCheckTraeEngine(context)) {
            TXCLog.m2914e(f2029g, "set aec type failed, check trae library failed!!");
            return;
        }
        String str = f2029g;
        TXCLog.m2913i(str, "set aec type to " + i + ", cur type " + this.f2032j);
        this.f2032j = i;
    }

    /* renamed from: a */
    public synchronized int m3472a(TXSAudioPacket tXSAudioPacket) {
        if (this.f2030h == null) {
            TXCLog.m2914e(f2029g, "play audio failed, controller not created yet!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        }
        return this.f2030h.playData(tXSAudioPacket);
    }

    /* renamed from: a */
    public void m3473a(TXIAudioPlayListener tXIAudioPlayListener) {
        this.f2031i = tXIAudioPlayListener;
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            tXCAudioBasePlayController.setListener(tXIAudioPlayListener);
        }
    }

    /* renamed from: a */
    public void m3478a(float f) {
        this.f2033k = f;
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            tXCAudioBasePlayController.setCacheTime(f);
        }
    }

    /* renamed from: a */
    public void m3471a(boolean z) {
        this.f2034l = z;
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            tXCAudioBasePlayController.enableAutojustCache(z);
        }
    }

    /* renamed from: b */
    public void m3468b(float f) {
        this.f2035m = f;
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            tXCAudioBasePlayController.setAutoAdjustMaxCache(f);
        }
    }

    /* renamed from: c */
    public void m3465c(float f) {
        this.f2036n = f;
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            tXCAudioBasePlayController.setAutoAdjustMinCache(f);
        }
    }

    /* renamed from: b */
    public synchronized long m3469b() {
        if (this.f2030h != null) {
            return this.f2030h.getCacheDuration();
        }
        return 0L;
    }

    /* renamed from: c */
    public synchronized long m3466c() {
        if (this.f2030h != null) {
            return this.f2030h.getCurPts();
        }
        return 0L;
    }

    /* renamed from: d */
    public synchronized int m3463d() {
        if (this.f2030h != null) {
            return this.f2030h.getRecvJitter();
        }
        return 0;
    }

    /* renamed from: e */
    public synchronized long m3461e() {
        if (this.f2030h != null) {
            return this.f2030h.getCurRecvTS();
        }
        return 0L;
    }

    /* renamed from: f */
    public synchronized float m3460f() {
        if (this.f2030h != null) {
            return this.f2030h.getCacheThreshold();
        }
        return 0.0f;
    }

    /* renamed from: b */
    public void m3467b(boolean z) {
        this.f2038p = z;
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            tXCAudioBasePlayController.enableHWAcceleration(z);
        }
    }

    /* renamed from: c */
    public void m3464c(boolean z) {
        this.f2037o = z;
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            tXCAudioBasePlayController.enableRealTimePlay(z);
        }
    }

    /* renamed from: d */
    public void m3462d(boolean z) {
        this.f2039q = z;
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            tXCAudioBasePlayController.setMute(z);
        }
    }

    /* renamed from: a */
    public static void m3474a(Context context, int i) {
        TXCAudioBasePlayController.setAudioMode(context, i);
    }

    /* renamed from: g */
    public boolean m3459g() {
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            return tXCAudioBasePlayController.isPlaying();
        }
        return false;
    }

    /* renamed from: a */
    public void m3477a(int i) {
        this.f2040r = i;
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            tXCAudioBasePlayController.setSmootheMode(this.f2040r);
        }
    }

    /* renamed from: h */
    public int m3458h() {
        if (TXCMultAudioTrackPlayer.m3425a().m3417d()) {
            int i = this.f2032j;
            if (i != TXEAudioDef.TXE_AEC_TRAE) {
                return i;
            }
            String str = f2029g;
            TXCLog.m2914e(str, "audio track has start, but aec type is trae!!" + this.f2032j);
            return TXEAudioDef.TXE_AEC_NONE;
        } else if (TXCTraeJNI.nativeTraeIsPlaying()) {
            int i2 = this.f2032j;
            if (i2 == TXEAudioDef.TXE_AEC_TRAE) {
                return i2;
            }
            String str2 = f2029g;
            TXCLog.m2914e(str2, "trae engine has start, but aec type is not trae!!" + this.f2032j);
            return TXEAudioDef.TXE_AEC_TRAE;
        } else {
            return TXEAudioDef.TXE_AEC_NONE;
        }
    }

    /* renamed from: i */
    public TXAudioJitterBufferReportInfo m3457i() {
        TXCAudioBasePlayController tXCAudioBasePlayController = this.f2030h;
        if (tXCAudioBasePlayController != null) {
            return tXCAudioBasePlayController.getReportInfo();
        }
        return null;
    }

    @Override // com.tencent.liteav.audio.impl.TXITelephonyMrgListener
    /* renamed from: b */
    public void mo3337b(int i) {
        TXCAudioBasePlayController tXCAudioBasePlayController;
        if (i == 0) {
            TXCAudioBasePlayController tXCAudioBasePlayController2 = this.f2030h;
            if (tXCAudioBasePlayController2 == null) {
                return;
            }
            tXCAudioBasePlayController2.setMute(this.f2039q);
        } else if (i != 1) {
            if (i != 2 || (tXCAudioBasePlayController = this.f2030h) == null) {
                return;
            }
            tXCAudioBasePlayController.setMute(true);
        } else {
            TXCAudioBasePlayController tXCAudioBasePlayController3 = this.f2030h;
            if (tXCAudioBasePlayController3 == null) {
                return;
            }
            tXCAudioBasePlayController3.setMute(true);
        }
    }
}
