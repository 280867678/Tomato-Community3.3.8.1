package com.tencent.liteav.audio;

import android.content.Context;
import com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController;
import com.tencent.liteav.audio.impl.Record.TXCAudioSysRecord;
import com.tencent.liteav.audio.impl.Record.TXCAudioSysRecordController;
import com.tencent.liteav.audio.impl.Record.TXCAudioTraeRecordController;
import com.tencent.liteav.audio.impl.TXCAudioRouteMgr;
import com.tencent.liteav.audio.impl.TXCAudioUtil;
import com.tencent.liteav.audio.impl.TXCTelephonyMgr;
import com.tencent.liteav.audio.impl.TXCTraeJNI;
import com.tencent.liteav.audio.impl.TXITelephonyMrgListener;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tomatolive.library.utils.ConstantUtils;
import java.lang.ref.WeakReference;

/* renamed from: com.tencent.liteav.audio.b */
/* loaded from: classes3.dex */
public class TXCAudioRecorder implements TXITelephonyMrgListener {

    /* renamed from: i */
    private WeakReference<TXIAudioRecordListener> f2050i;

    /* renamed from: r */
    private Context f2059r;

    /* renamed from: h */
    private static final String f2049h = "AudioCenter:" + TXCAudioRecorder.class.getSimpleName();

    /* renamed from: a */
    public static final int f2042a = TXEAudioTypeDef.f2318e;

    /* renamed from: b */
    public static final int f2043b = TXEAudioTypeDef.f2319f;

    /* renamed from: c */
    public static final int f2044c = TXEAudioTypeDef.f2321h;

    /* renamed from: d */
    public static final int f2045d = TXEAudioDef.TXE_REVERB_TYPE_0;

    /* renamed from: e */
    public static final int f2046e = TXEAudioDef.TXE_AEC_NONE;

    /* renamed from: f */
    public static final int f2047f = TXEAudioDef.TXE_AUDIO_TYPE_AAC;

    /* renamed from: g */
    static TXCAudioRecorder f2048g = new TXCAudioRecorder();

    /* renamed from: j */
    private int f2051j = f2042a;

    /* renamed from: k */
    private int f2052k = f2043b;

    /* renamed from: l */
    private int f2053l = TXEAudioTypeDef.f2321h;

    /* renamed from: m */
    private int f2054m = f2045d;

    /* renamed from: n */
    private boolean f2055n = false;

    /* renamed from: o */
    private int f2056o = f2046e;

    /* renamed from: p */
    private boolean f2057p = false;

    /* renamed from: q */
    private boolean f2058q = false;

    /* renamed from: s */
    private float f2060s = 1.0f;

    /* renamed from: t */
    private int f2061t = -1;

    /* renamed from: u */
    private int f2062u = -1;

    /* renamed from: v */
    private TXCAudioBaseRecordController f2063v = null;

    /* renamed from: b */
    public void m3445b(boolean z) {
    }

    private TXCAudioRecorder() {
    }

    /* renamed from: a */
    public static TXCAudioRecorder m3456a() {
        return f2048g;
    }

    /* renamed from: a */
    public int m3451a(Context context) {
        if (context == null) {
            TXCLog.m2914e(f2049h, "invalid param, start record failed!");
            return TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
        }
        this.f2059r = context.getApplicationContext();
        TXCAudioRouteMgr.m3371a().m3368a(this.f2059r);
        TXCTelephonyMgr.m3346a().m3344a(this.f2059r);
        TXCTelephonyMgr.m3346a().m3340a(this);
        if (TXCAudioUtil.m3348b(this.f2056o) != TXEAudioDef.TXE_AUDIO_RECORD_ERR_OK) {
            String str = f2049h;
            TXCLog.m2911w(str, "start recorder failed, with aec type " + this.f2056o + ", invalid aec player has started!");
        }
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null && tXCAudioBaseRecordController.isRecording()) {
            TXCLog.m2914e(f2049h, "record has started, can not start again!");
            return TXEAudioDef.TXE_AUDIO_RECORD_ERR_REPEAT_OPTION;
        }
        if (this.f2063v == null) {
            if (this.f2056o == TXEAudioDef.TXE_AEC_TRAE) {
                this.f2063v = new TXCAudioTraeRecordController();
            } else {
                this.f2063v = new TXCAudioSysRecordController();
            }
        }
        if (this.f2063v != null) {
            m3437g();
            return this.f2063v.startRecord(this.f2059r);
        }
        TXCLog.m2914e(f2049h, "start Record failed! controller is null!");
        return TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
    }

    /* renamed from: b */
    public int m3446b() {
        int i = TXEAudioDef.TXE_AUDIO_RECORD_ERR_OK;
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            i = tXCAudioBaseRecordController.stopRecord();
            this.f2063v = null;
        }
        this.f2050i = null;
        this.f2051j = f2042a;
        this.f2052k = f2043b;
        this.f2053l = TXEAudioTypeDef.f2321h;
        this.f2054m = f2045d;
        this.f2055n = false;
        this.f2056o = f2046e;
        this.f2057p = false;
        this.f2058q = false;
        this.f2059r = null;
        this.f2060s = 1.0f;
        this.f2061t = -1;
        this.f2062u = -1;
        m3437g();
        TXCTelephonyMgr.m3346a().m3338b(this);
        return i;
    }

    /* renamed from: c */
    public boolean m3444c() {
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            return tXCAudioBaseRecordController.isRecording();
        }
        return false;
    }

    /* renamed from: a */
    public void m3449a(boolean z) {
        this.f2058q = z;
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            tXCAudioBaseRecordController.setIsCustomRecord(z);
        }
    }

    /* renamed from: a */
    public void m3450a(TXIAudioRecordListener tXIAudioRecordListener) {
        this.f2050i = new WeakReference<>(tXIAudioRecordListener);
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            tXCAudioBaseRecordController.setListener(tXIAudioRecordListener);
        }
    }

    /* renamed from: d */
    public int m3441d() {
        return this.f2052k;
    }

    /* renamed from: e */
    public int m3439e() {
        return this.f2051j;
    }

    /* renamed from: a */
    public void m3454a(int i) {
        String str = f2049h;
        TXCLog.m2913i(str, "setSampleRate: " + i);
        this.f2051j = i;
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            tXCAudioBaseRecordController.setSamplerate(i);
        }
    }

    /* renamed from: c */
    public void m3443c(int i) {
        String str = f2049h;
        TXCLog.m2913i(str, "setChannels: " + i);
        this.f2052k = i;
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            tXCAudioBaseRecordController.setChannels(i);
        }
    }

    /* renamed from: d */
    public void m3440d(int i) {
        String str = f2049h;
        TXCLog.m2913i(str, "setReverbType: " + i);
        this.f2054m = i;
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            tXCAudioBaseRecordController.setReverbType(i);
        }
    }

    /* renamed from: a */
    public void m3448a(boolean z, Context context) {
        if (!z) {
            m3452a(TXEAudioDef.TXE_AEC_NONE, context);
        } else if (TXCConfigCenter.m2988a().m2960g()) {
            m3452a(TXEAudioDef.TXE_AEC_SYSTEM, context);
        } else {
            TXCAudioRouteMgr.m3361a(TXCConfigCenter.m2988a().m2974b());
            m3452a(TXEAudioDef.TXE_AEC_TRAE, context);
        }
    }

    /* renamed from: a */
    private void m3452a(int i, Context context) {
        if (i == TXEAudioDef.TXE_AEC_TRAE && !TXCTraeJNI.nativeCheckTraeEngine(context)) {
            TXCLog.m2914e(f2049h, "set aec type failed, check trae library failed!!");
            return;
        }
        if (this.f2056o != i) {
            TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
            if (tXCAudioBaseRecordController != null && tXCAudioBaseRecordController.isRecording()) {
                this.f2063v.stopRecord();
                this.f2063v = null;
                this.f2056o = i;
                m3451a(this.f2059r);
            }
            this.f2056o = i;
        }
        TXCAudioBaseRecordController tXCAudioBaseRecordController2 = this.f2063v;
        if (tXCAudioBaseRecordController2 == null) {
            return;
        }
        tXCAudioBaseRecordController2.setAECType(i);
    }

    /* renamed from: c */
    public void m3442c(boolean z) {
        String str = f2049h;
        TXCLog.m2913i(str, "setMute: " + z);
        this.f2055n = z;
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            tXCAudioBaseRecordController.setMute(z);
        }
    }

    /* renamed from: a */
    public void m3455a(float f) {
        String str = f2049h;
        TXCLog.m2913i(str, "setVolume: " + f);
        this.f2060s = f;
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            tXCAudioBaseRecordController.setVolume(f);
        }
    }

    /* renamed from: a */
    public void m3453a(int i, int i2) {
        String str = f2049h;
        TXCLog.m2913i(str, "setChangerType: " + i + ConstantUtils.PLACEHOLDER_STR_ONE + i2);
        this.f2061t = i;
        this.f2062u = i2;
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            tXCAudioBaseRecordController.setChangerType(i, i2);
        }
    }

    /* renamed from: a */
    public void m3447a(byte[] bArr) {
        TXCAudioBaseRecordController tXCAudioBaseRecordController = this.f2063v;
        if (tXCAudioBaseRecordController != null) {
            tXCAudioBaseRecordController.sendCustomPCMData(bArr);
        }
    }

    /* renamed from: f */
    public int m3438f() {
        if (TXCAudioSysRecord.m3382a().m3376c()) {
            int i = this.f2056o;
            if (i != TXEAudioDef.TXE_AEC_TRAE) {
                return i;
            }
            String str = f2049h;
            TXCLog.m2914e(str, "audio mic has start, but aec type is trae!!" + this.f2056o);
            return TXEAudioDef.TXE_AEC_NONE;
        } else if (TXCTraeJNI.nativeTraeIsRecording()) {
            int i2 = this.f2056o;
            if (i2 == TXEAudioDef.TXE_AEC_TRAE) {
                return i2;
            }
            String str2 = f2049h;
            TXCLog.m2914e(str2, "trae engine has start, but aec type is not trae!!" + this.f2056o);
            return TXEAudioDef.TXE_AEC_TRAE;
        } else {
            return TXEAudioDef.TXE_AEC_NONE;
        }
    }

    /* renamed from: g */
    private void m3437g() {
        WeakReference<TXIAudioRecordListener> weakReference = this.f2050i;
        if (weakReference != null) {
            m3450a(weakReference.get());
        }
        m3449a(this.f2058q);
        m3454a(this.f2051j);
        m3443c(this.f2052k);
        m3440d(this.f2054m);
        m3452a(this.f2056o, this.f2059r);
        m3445b(this.f2057p);
        m3442c(this.f2055n);
        m3455a(this.f2060s);
        m3453a(this.f2061t, this.f2062u);
    }

    @Override // com.tencent.liteav.audio.impl.TXITelephonyMrgListener
    /* renamed from: b */
    public void mo3337b(int i) {
        TXCAudioBaseRecordController tXCAudioBaseRecordController;
        if (i == 0) {
            TXCAudioBaseRecordController tXCAudioBaseRecordController2 = this.f2063v;
            if (tXCAudioBaseRecordController2 == null) {
                return;
            }
            tXCAudioBaseRecordController2.setMute(this.f2055n);
        } else if (i != 1) {
            if (i != 2 || (tXCAudioBaseRecordController = this.f2063v) == null) {
                return;
            }
            tXCAudioBaseRecordController.setMute(true);
        } else {
            TXCAudioBaseRecordController tXCAudioBaseRecordController3 = this.f2063v;
            if (tXCAudioBaseRecordController3 == null) {
                return;
            }
            tXCAudioBaseRecordController3.setMute(true);
        }
    }
}
