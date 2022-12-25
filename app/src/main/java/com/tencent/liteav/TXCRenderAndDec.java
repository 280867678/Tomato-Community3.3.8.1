package com.tencent.liteav;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.util.Log;
import com.tencent.avroom.TXCAVRoomConstants;
import com.tencent.liteav.audio.TXCAudioPlayer;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioPlayListener;
import com.tencent.liteav.audio.impl.Play.TXAudioJitterBufferReportInfo;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCModule;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.p106b.TXCVideoJitterBuffer;
import com.tencent.liteav.basic.p106b.TXIVideoJitterBufferListener;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.renderer.TXCVideoRender;
import com.tencent.liteav.renderer.TXIVideoRenderListener;
import com.tencent.liteav.videodecoder.TXCVideoDecoder;
import com.tencent.liteav.videodecoder.TXIVideoDecoderListener;
import com.tencent.rtmp.TXLiveConstants;
import java.nio.ByteBuffer;

/* renamed from: com.tencent.liteav.k */
/* loaded from: classes3.dex */
public class TXCRenderAndDec extends TXCModule implements TXIAudioPlayListener, TXIVideoJitterBufferListener, TXINotifyListener, TXIVideoRenderListener, TXIVideoDecoderListener {

    /* renamed from: a */
    private Context f4440a;

    /* renamed from: m */
    private int f4452m;

    /* renamed from: o */
    private String f4454o;

    /* renamed from: b */
    private TXCPlayerConfig f4441b = null;

    /* renamed from: c */
    private TXCVideoDecoder f4442c = null;

    /* renamed from: d */
    private TXCVideoRender f4443d = null;

    /* renamed from: e */
    private TXCVideoJitterBuffer f4444e = null;

    /* renamed from: f */
    private TXCAudioPlayer f4445f = null;

    /* renamed from: g */
    private TXINotifyListener f4446g = null;

    /* renamed from: h */
    private boolean f4447h = false;

    /* renamed from: i */
    private int f4448i = 0;

    /* renamed from: j */
    private long f4449j = 0;

    /* renamed from: k */
    private byte[] f4450k = null;

    /* renamed from: l */
    private TXIVideoRawDataListener f4451l = null;

    /* renamed from: n */
    private boolean f4453n = false;

    /* renamed from: p */
    private final float f4455p = TXEAudioTypeDef.f2328o;

    /* renamed from: q */
    private final float f4456q = TXEAudioTypeDef.f2329p;

    /* renamed from: r */
    private final float f4457r = TXEAudioTypeDef.f2330q;

    /* renamed from: s */
    private final float f4458s = 0.3f;

    /* renamed from: t */
    private boolean f4459t = false;

    /* renamed from: u */
    private AbstractC3529a f4460u = null;

    /* compiled from: TXCRenderAndDec.java */
    /* renamed from: com.tencent.liteav.k$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3529a {
        /* renamed from: a */
        void mo1375a(long j);

        /* renamed from: a */
        void mo1374a(SurfaceTexture surfaceTexture);

        /* renamed from: a */
        void mo1373a(TXSAudioPacket tXSAudioPacket);

        /* renamed from: a */
        void mo1371a(byte[] bArr, long j);
    }

    @Override // com.tencent.liteav.audio.TXIAudioPlayListener
    public void onPlayError(int i, String str) {
    }

    public TXCRenderAndDec(Context context, int i) {
        this.f4440a = null;
        this.f4440a = context;
        this.f4452m = i;
        TXCConfigCenter.m2988a().m2985a(this.f4440a);
    }

    /* renamed from: a */
    public void m1409a(TXCVideoRender tXCVideoRender) {
        TXCPlayerConfig tXCPlayerConfig;
        this.f4443d = tXCVideoRender;
        TXCVideoRender tXCVideoRender2 = this.f4443d;
        if (tXCVideoRender2 != null && this.f4446g != null) {
            tXCVideoRender2.m887a((TXINotifyListener) this);
        }
        TXCVideoRender tXCVideoRender3 = this.f4443d;
        if (tXCVideoRender3 == null || (tXCPlayerConfig = this.f4441b) == null) {
            return;
        }
        tXCVideoRender3.m897a(tXCPlayerConfig.f4342d);
    }

    /* renamed from: a */
    public void m1414a(TXINotifyListener tXINotifyListener) {
        this.f4446g = tXINotifyListener;
    }

    /* renamed from: a */
    public void m1411a(TXCPlayerConfig tXCPlayerConfig) {
        this.f4441b = tXCPlayerConfig;
        m1377v();
    }

    /* renamed from: a */
    public void m1416a(long j) {
        this.f4449j = j;
    }

    @Override // com.tencent.liteav.basic.module.TXCModule
    public void setID(String str) {
        super.setID(str);
        TXCVideoRender tXCVideoRender = this.f4443d;
        if (tXCVideoRender != null) {
            tXCVideoRender.setID(getID());
        }
    }

    /* renamed from: b */
    public boolean m1405b() {
        return this.f4459t;
    }

    /* renamed from: a */
    public void m1407a(boolean z) {
        this.f4459t = z;
    }

    /* renamed from: a */
    public void m1410a(AbstractC3529a abstractC3529a) {
        this.f4460u = abstractC3529a;
    }

    /* renamed from: b */
    public void m1402b(boolean z) {
        this.f4447h = z;
        this.f4453n = true;
        TXCVideoRender tXCVideoRender = this.f4443d;
        if (tXCVideoRender != null) {
            tXCVideoRender.m885a((TXIVideoRenderListener) this);
            this.f4443d.m871i();
            this.f4443d.setID(getID());
        }
        this.f4442c = new TXCVideoDecoder();
        this.f4442c.m620a(this.f4449j);
        this.f4442c.m615a((TXIVideoDecoderListener) this);
        this.f4442c.m617a((TXINotifyListener) this);
        this.f4445f = new TXCAudioPlayer();
        this.f4445f.m3473a(this);
        m1395d(this.f4447h);
        this.f4445f.m3477a(this.f4448i);
        this.f4445f.m3475a(this.f4440a);
        this.f4444e = new TXCVideoJitterBuffer();
        this.f4444e.m3166a(this);
        this.f4444e.m3174a();
        m1419a();
        m1377v();
    }

    /* renamed from: c */
    public void m1401c() {
        this.f4447h = false;
        this.f4448i = 0;
        TXCVideoDecoder tXCVideoDecoder = this.f4442c;
        if (tXCVideoDecoder != null) {
            tXCVideoDecoder.m615a((TXIVideoDecoderListener) null);
            this.f4442c.m617a((TXINotifyListener) null);
            this.f4442c.m609c();
        }
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer != null) {
            tXCAudioPlayer.m3473a((TXIAudioPlayListener) null);
            this.f4445f.m3479a();
        }
        TXCVideoJitterBuffer tXCVideoJitterBuffer = this.f4444e;
        if (tXCVideoJitterBuffer != null) {
            tXCVideoJitterBuffer.m3166a((TXIVideoJitterBufferListener) null);
            this.f4444e.m3163b();
        }
        TXCVideoRender tXCVideoRender = this.f4443d;
        if (tXCVideoRender != null) {
            tXCVideoRender.m870j();
            this.f4443d.m885a((TXIVideoRenderListener) null);
        }
    }

    /* renamed from: a */
    public void m1413a(TXSAudioPacket tXSAudioPacket) {
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer != null) {
            tXCAudioPlayer.m3472a(tXSAudioPacket);
        } else {
            TXCLog.m2911w("TXCRenderAndDec", "decAudio fail which audio play hasn't been created!");
        }
    }

    /* renamed from: a */
    public void m1412a(TXSNALPacket tXSNALPacket) {
        try {
            if (this.f4444e == null) {
                return;
            }
            this.f4444e.m3165a(tXSNALPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public void m1418a(int i) {
        TXCVideoRender tXCVideoRender = this.f4443d;
        if (tXCVideoRender != null) {
            tXCVideoRender.m882b(i);
        }
    }

    /* renamed from: b */
    public void m1404b(int i) {
        TXCVideoRender tXCVideoRender = this.f4443d;
        if (tXCVideoRender != null) {
            tXCVideoRender.m875c(i);
        }
    }

    /* renamed from: c */
    public void m1397c(boolean z) {
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer != null) {
            tXCAudioPlayer.m3462d(z);
        }
    }

    /* renamed from: c */
    public void m1400c(int i) {
        this.f4448i = i;
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer != null) {
            tXCAudioPlayer.m3477a(this.f4448i);
        }
    }

    /* renamed from: a */
    public static void m1415a(Context context, int i) {
        TXCAudioPlayer.m3474a(context, i);
    }

    /* renamed from: d */
    public long m1396d() {
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer != null) {
            return tXCAudioPlayer.m3469b();
        }
        return 0L;
    }

    /* renamed from: e */
    public long m1394e() {
        TXCVideoJitterBuffer tXCVideoJitterBuffer = this.f4444e;
        if (tXCVideoJitterBuffer != null) {
            return tXCVideoJitterBuffer.m3154d();
        }
        return 0L;
    }

    /* renamed from: f */
    public long m1393f() {
        TXCVideoJitterBuffer tXCVideoJitterBuffer = this.f4444e;
        if (tXCVideoJitterBuffer != null) {
            return tXCVideoJitterBuffer.m3150e();
        }
        return 0L;
    }

    /* renamed from: g */
    public int m1392g() {
        TXCVideoDecoder tXCVideoDecoder = this.f4442c;
        if (tXCVideoDecoder != null) {
            return tXCVideoDecoder.m608d();
        }
        return 0;
    }

    /* renamed from: h */
    public long m1391h() {
        TXCAudioPlayer tXCAudioPlayer;
        if (this.f4444e == null || (tXCAudioPlayer = this.f4445f) == null) {
            return 0L;
        }
        return tXCAudioPlayer.m3466c() - this.f4444e.m3146f();
    }

    /* renamed from: i */
    public int m1390i() {
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer != null) {
            return tXCAudioPlayer.m3463d();
        }
        return 0;
    }

    /* renamed from: j */
    public long m1389j() {
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer == null || this.f4444e == null) {
            return 0L;
        }
        return tXCAudioPlayer.m3461e() - this.f4444e.m3142g();
    }

    /* renamed from: k */
    public float m1388k() {
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer != null) {
            return tXCAudioPlayer.m3460f();
        }
        return 0.0f;
    }

    /* renamed from: l */
    public int m1387l() {
        TXCVideoJitterBuffer tXCVideoJitterBuffer = this.f4444e;
        if (tXCVideoJitterBuffer != null) {
            return tXCVideoJitterBuffer.m3139h();
        }
        return 0;
    }

    /* renamed from: m */
    public String m1386m() {
        int i;
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer != null) {
            i = tXCAudioPlayer.m3458h();
        } else {
            i = TXEAudioDef.TXE_AEC_NONE;
        }
        return i + " | " + this.f4454o;
    }

    /* renamed from: n */
    public void m1385n() {
        TXAudioJitterBufferReportInfo m3457i;
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        long j = 0;
        if (tXCAudioPlayer != null && (m3457i = tXCAudioPlayer.m3457i()) != null) {
            int i = m3457i.mLoadCnt;
            long j2 = i == 0 ? 0L : m3457i.mLoadTime / i;
            long j3 = m3457i.mTimeTotalCacheTimeCnt;
            long j4 = j3 == 0 ? 0L : m3457i.mTimeTotalCacheTime / j3;
            int i2 = m3457i.mTimeTotalJittCnt;
            int i3 = i2 == 0 ? 0 : m3457i.mTimeTotalJitt / i2;
            setStatusValue(2001, Long.valueOf(j2));
            setStatusValue(2002, Long.valueOf(m3457i.mLoadCnt));
            setStatusValue(2003, Long.valueOf(m3457i.mLoadMaxTime));
            setStatusValue(2004, Long.valueOf(m3457i.mSpeedCnt));
            setStatusValue(TXLiveConstants.PLAY_EVT_PLAY_PROGRESS, Long.valueOf(m3457i.mNoDataCnt));
            setStatusValue(TXLiveConstants.PLAY_EVT_PLAY_LOADING, Long.valueOf(m3457i.mAvgCacheTime));
            setStatusValue(TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER, Long.valueOf(m3457i.mIsRealTime));
            setStatusValue(TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC, Long.valueOf(j4));
            setStatusValue(TXLiveConstants.PLAY_EVT_CHANGE_ROTATION, Long.valueOf(i3));
            setStatusValue(TXLiveConstants.PLAY_EVT_VOD_LOADING_END, Long.valueOf(m3457i.mTimeDropCnt));
        }
        if (this.f4444e != null) {
            setStatusValue(TXLiveConstants.PLAY_EVT_PLAY_END, Long.valueOf(m1393f()));
            setStatusValue(6007, Long.valueOf(this.f4444e.m3133k()));
            setStatusValue(6008, Long.valueOf(this.f4444e.m3135j()));
            setStatusValue(6009, Long.valueOf(this.f4444e.m3137i()));
        }
        TXCVideoDecoder tXCVideoDecoder = this.f4442c;
        if (tXCVideoDecoder != null) {
            if (tXCVideoDecoder.m621a()) {
                j = 1;
            }
            setStatusValue(5002, Long.valueOf(j));
        }
    }

    /* renamed from: o */
    public TXCVideoJitterBuffer m1384o() {
        return this.f4444e;
    }

    /* renamed from: p */
    public TXCAudioPlayer m1383p() {
        return this.f4445f;
    }

    /* renamed from: q */
    public TXCVideoRender m1382q() {
        return this.f4443d;
    }

    /* renamed from: a */
    public boolean m1406a(byte[] bArr) {
        synchronized (this) {
            this.f4450k = bArr;
        }
        return true;
    }

    /* renamed from: a */
    public void m1408a(TXIVideoRawDataListener tXIVideoRawDataListener) {
        synchronized (this) {
            this.f4451l = tXIVideoRawDataListener;
        }
    }

    /* renamed from: c */
    private void m1399c(SurfaceTexture surfaceTexture) {
        TXCVideoDecoder tXCVideoDecoder = this.f4442c;
        if (tXCVideoDecoder != null) {
            tXCVideoDecoder.m609c();
            tXCVideoDecoder.m614a(this.f4441b.f4347i);
            if (surfaceTexture == null) {
                return;
            }
            tXCVideoDecoder.m619a(surfaceTexture, (ByteBuffer) null, (ByteBuffer) null, !this.f4447h);
            tXCVideoDecoder.m612b();
        }
    }

    /* renamed from: a */
    private void m1419a() {
        TXCVideoRender tXCVideoRender = this.f4443d;
        m1399c(tXCVideoRender != null ? tXCVideoRender.mo898a() : null);
    }

    /* renamed from: v */
    private void m1377v() {
        m1395d(this.f4447h);
        TXCAudioPlayer tXCAudioPlayer = this.f4445f;
        if (tXCAudioPlayer != null) {
            tXCAudioPlayer.m3478a(this.f4441b.f4339a);
            this.f4445f.m3471a(this.f4441b.f4345g);
            this.f4445f.m3465c(this.f4441b.f4341c);
            this.f4445f.m3468b(this.f4441b.f4340b);
            setStatusValue(TXLiveConstants.PLAY_EVT_GET_MESSAGE, Long.valueOf(this.f4441b.f4341c * 1000.0f));
            setStatusValue(TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED, Long.valueOf(this.f4441b.f4340b * 1000.0f));
            setStatusValue(TXLiveConstants.PLAY_EVT_STREAM_SWITCH_SUCC, 0L);
        }
        TXCVideoJitterBuffer tXCVideoJitterBuffer = this.f4444e;
        if (tXCVideoJitterBuffer != null) {
            tXCVideoJitterBuffer.m3173a(this.f4441b.f4341c);
        }
        TXCVideoDecoder tXCVideoDecoder = this.f4442c;
        if (tXCVideoDecoder != null && tXCVideoDecoder.m621a()) {
            TXCPlayerConfig tXCPlayerConfig = this.f4441b;
            if (tXCPlayerConfig.f4341c < 0.3f && tXCPlayerConfig.f4340b < 0.3f) {
                tXCPlayerConfig.f4347i = false;
                this.f4442c.m609c();
                m1419a();
            }
        }
        TXCVideoRender tXCVideoRender = this.f4443d;
        if (tXCVideoRender != null) {
            tXCVideoRender.m897a(this.f4441b.f4342d);
        }
    }

    /* renamed from: d */
    private void m1395d(boolean z) {
        if (z) {
            TXCPlayerConfig tXCPlayerConfig = this.f4441b;
            float f = tXCPlayerConfig.f4339a;
            float f2 = tXCPlayerConfig.f4341c;
            float f3 = tXCPlayerConfig.f4340b;
            float f4 = this.f4456q;
            if (f2 > f4) {
                f2 = f4;
            }
            float f5 = this.f4457r;
            if (f3 > f5) {
                f3 = f5;
            }
            if (f2 >= f3) {
                f2 = this.f4456q;
                f3 = this.f4457r;
            }
            TXCPlayerConfig tXCPlayerConfig2 = this.f4441b;
            tXCPlayerConfig2.f4345g = true;
            tXCPlayerConfig2.f4339a = f2;
            tXCPlayerConfig2.f4341c = f2;
            tXCPlayerConfig2.f4340b = f3;
            TXCAudioPlayer tXCAudioPlayer = this.f4445f;
            if (tXCAudioPlayer != null) {
                tXCAudioPlayer.m3470a(true, this.f4440a);
                this.f4445f.m3464c(true);
            }
        } else {
            TXCLog.m2914e("TXCRenderAndDec", "setupRealTimePlayParams current cache time : min-cache[" + this.f4441b.f4341c + "], max-cache[" + this.f4441b.f4340b + "], org-cache[" + this.f4441b.f4339a + "]");
            TXCAudioPlayer tXCAudioPlayer2 = this.f4445f;
            if (tXCAudioPlayer2 != null) {
                TXCPlayerConfig tXCPlayerConfig3 = this.f4441b;
                if (tXCPlayerConfig3 != null && tXCPlayerConfig3.f4346h) {
                    tXCAudioPlayer2.m3470a(true, this.f4440a);
                } else {
                    this.f4445f.m3470a(false, this.f4440a);
                }
                this.f4445f.m3464c(false);
            }
            TXCPlayerConfig tXCPlayerConfig4 = this.f4441b;
            float f6 = tXCPlayerConfig4.f4339a;
            if (f6 > tXCPlayerConfig4.f4340b || f6 < tXCPlayerConfig4.f4341c) {
                TXCPlayerConfig tXCPlayerConfig5 = this.f4441b;
                tXCPlayerConfig5.f4339a = tXCPlayerConfig5.f4340b;
            }
        }
        TXCVideoJitterBuffer tXCVideoJitterBuffer = this.f4444e;
        if (tXCVideoJitterBuffer != null) {
            tXCVideoJitterBuffer.m3159b(z);
        }
    }

    /* renamed from: a */
    private void m1417a(int i, String str) {
        TXINotifyListener tXINotifyListener = this.f4446g;
        if (tXINotifyListener != null) {
            Bundle bundle = new Bundle();
            Log.i("TXCRenderAndDec", "TXCRenderAndDec notifyEvent: mUserID  " + this.f4449j);
            bundle.putLong(TXCAVRoomConstants.EVT_USERID, this.f4449j);
            bundle.putInt(TXCAVRoomConstants.EVT_ID, i);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            if (str != null) {
                bundle.putCharSequence("EVT_MSG", str);
            }
            tXINotifyListener.onNotifyEvent(i, bundle);
        }
    }

    /* renamed from: w */
    private void m1376w() {
        TXCVideoDecoder tXCVideoDecoder = this.f4442c;
        if (tXCVideoDecoder != null) {
            TXCLog.m2911w("TXCRenderAndDec", "switch to soft decoder when hw error");
            tXCVideoDecoder.m609c();
            this.f4441b.f4347i = false;
            m1395d(this.f4447h);
            m1419a();
        }
    }

    /* renamed from: r */
    public void m1381r() {
        TXCVideoDecoder tXCVideoDecoder = this.f4442c;
        if (tXCVideoDecoder == null || !tXCVideoDecoder.m607e()) {
            return;
        }
        tXCVideoDecoder.m610b(true);
    }

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(int i, Bundle bundle) {
        if (i == 2106) {
            m1376w();
        } else if (i == 2003 && this.f4453n) {
            m1417a(2004, "视频播放开始");
            this.f4453n = false;
        }
        TXINotifyListener tXINotifyListener = this.f4446g;
        if (tXINotifyListener != null) {
            tXINotifyListener.onNotifyEvent(i, bundle);
        }
    }

    @Override // com.tencent.liteav.renderer.TXIVideoRenderListener
    /* renamed from: a */
    public void mo861a(SurfaceTexture surfaceTexture) {
        m1399c(surfaceTexture);
    }

    @Override // com.tencent.liteav.renderer.TXIVideoRenderListener
    /* renamed from: b */
    public void mo860b(SurfaceTexture surfaceTexture) {
        try {
            TXCLog.m2911w("TXCRenderAndDec", "play:stop decode when surface texture release");
            if (this.f4442c != null) {
                this.f4442c.m609c();
            }
            if (this.f4460u == null) {
                return;
            }
            this.f4460u.mo1374a(surfaceTexture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tencent.liteav.basic.p106b.TXIVideoJitterBufferListener
    /* renamed from: s */
    public long mo1380s() {
        try {
            if (this.f4445f == null) {
                return 0L;
            }
            return this.f4445f.m3469b();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override // com.tencent.liteav.basic.p106b.TXIVideoJitterBufferListener
    /* renamed from: t */
    public long mo1379t() {
        try {
            if (this.f4445f == null) {
                return 0L;
            }
            return this.f4445f.m3466c();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override // com.tencent.liteav.basic.p106b.TXIVideoJitterBufferListener
    /* renamed from: b */
    public void mo1403b(TXSNALPacket tXSNALPacket) {
        try {
            if (this.f4442c != null) {
                this.f4442c.m616a(tXSNALPacket);
            } else if (this.f4444e != null) {
                this.f4444e.m3172a(tXSNALPacket.pts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tencent.liteav.basic.p106b.TXIVideoJitterBufferListener
    /* renamed from: c */
    public void mo1398c(TXSNALPacket tXSNALPacket) {
        Bundle bundle = new Bundle();
        bundle.putByteArray(TXLiveConstants.EVT_GET_MSG, tXSNALPacket.nalData);
        onNotifyEvent(TXLiveConstants.PLAY_EVT_GET_MESSAGE, bundle);
    }

    @Override // com.tencent.liteav.basic.p106b.TXIVideoJitterBufferListener
    /* renamed from: u */
    public int mo1378u() {
        try {
            if (this.f4442c == null) {
                return 0;
            }
            return this.f4442c.m608d();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // com.tencent.liteav.videodecoder.TXIVideoDecoderListener
    /* renamed from: a */
    public void mo588a(SurfaceTexture surfaceTexture, int i, int i2, long j, long j2) {
        TXCVideoRender tXCVideoRender = this.f4443d;
        if (tXCVideoRender != null) {
            tXCVideoRender.m890a(surfaceTexture, i, i2);
            AbstractC3529a abstractC3529a = this.f4460u;
            if (abstractC3529a != null) {
                abstractC3529a.mo1375a(j);
            }
            TXCVideoJitterBuffer tXCVideoJitterBuffer = this.f4444e;
            if (tXCVideoJitterBuffer == null) {
                return;
            }
            tXCVideoJitterBuffer.m3172a(j);
        }
    }

    @Override // com.tencent.liteav.videodecoder.TXIVideoDecoderListener
    /* renamed from: a */
    public void mo589a(long j, int i, int i2, long j2, long j3) {
        TXCVideoRender tXCVideoRender;
        boolean z = false;
        if (this.f4451l != null && this.f4450k != null) {
            synchronized (this) {
                byte[] bArr = this.f4450k;
                this.f4450k = null;
                if (this.f4451l != null && bArr != null && this.f4442c != null) {
                    if (bArr.length <= ((i * i2) * 3) / 2) {
                        this.f4442c.m613a(bArr, j, bArr.length);
                        this.f4451l.onVideoRawDataAvailable(bArr, i, i2, (int) j2);
                        z = true;
                    } else {
                        TXCLog.m2914e("TXCRenderAndDec", "raw data buffer length is too large");
                    }
                }
            }
        }
        if (!z) {
            if (j > 0 && (tXCVideoRender = this.f4443d) != null) {
                tXCVideoRender.mo892a(j, i, i2);
            }
            TXCVideoJitterBuffer tXCVideoJitterBuffer = this.f4444e;
            if (tXCVideoJitterBuffer == null) {
                return;
            }
            tXCVideoJitterBuffer.m3172a(j2);
        }
    }

    @Override // com.tencent.liteav.videodecoder.TXIVideoDecoderListener
    /* renamed from: a */
    public void mo590a(int i, int i2) {
        TXCVideoRender tXCVideoRender = this.f4443d;
        if (tXCVideoRender != null) {
            tXCVideoRender.m881b(i, i2);
        }
        Bundle bundle = new Bundle();
        bundle.putCharSequence("EVT_MSG", "分辨率改变");
        bundle.putInt("EVT_PARAM1", i);
        bundle.putInt("EVT_PARAM2", i2);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        onNotifyEvent(TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION, bundle);
    }

    @Override // com.tencent.liteav.audio.TXIAudioPlayListener
    public void onPlayAudioInfoChanged(TXSAudioPacket tXSAudioPacket, TXSAudioPacket tXSAudioPacket2) {
        AbstractC3529a abstractC3529a = this.f4460u;
        if (abstractC3529a != null) {
            abstractC3529a.mo1373a(tXSAudioPacket2);
        }
        if (tXSAudioPacket == null || tXSAudioPacket2 == null) {
            return;
        }
        this.f4454o = tXSAudioPacket.sampleRate + "," + tXSAudioPacket.channelsPerSample + " | " + tXSAudioPacket2.sampleRate + "," + tXSAudioPacket2.channelsPerSample;
    }

    @Override // com.tencent.liteav.audio.TXIAudioPlayListener
    public void onPlayPcmData(byte[] bArr, long j) {
        AbstractC3529a abstractC3529a = this.f4460u;
        if (abstractC3529a != null) {
            abstractC3529a.mo1371a(bArr, j);
        }
    }

    @Override // com.tencent.liteav.audio.TXIAudioPlayListener
    public void onPlayJitterStateNotify(int i) {
        if (i == TXEAudioDef.TXE_AUDIO_JITTER_STATE_LOADING) {
            TXCVideoJitterBuffer tXCVideoJitterBuffer = this.f4444e;
            if (tXCVideoJitterBuffer != null && !this.f4447h) {
                tXCVideoJitterBuffer.m3164a(true);
            }
            m1417a(TXLiveConstants.PLAY_EVT_PLAY_LOADING, "视频缓冲中...");
        } else if (i == TXEAudioDef.TXE_AUDIO_JITTER_STATE_FIRST_LAODING) {
            m1417a(TXLiveConstants.PLAY_EVT_PLAY_LOADING, "视频缓冲中...");
        } else if (i == TXEAudioDef.TXE_AUDIO_JITTER_STATE_PLAYING) {
            TXCVideoJitterBuffer tXCVideoJitterBuffer2 = this.f4444e;
            if (tXCVideoJitterBuffer2 != null) {
                tXCVideoJitterBuffer2.m3164a(false);
            }
            m1417a(2004, "视频播放开始");
        } else if (i != TXEAudioDef.TXE_AUDIO_JITTER_STATE_FIRST_PLAY) {
        } else {
            TXCVideoJitterBuffer tXCVideoJitterBuffer3 = this.f4444e;
            if (tXCVideoJitterBuffer3 != null) {
                tXCVideoJitterBuffer3.m3164a(false);
            }
            if (!this.f4453n) {
                return;
            }
            m1417a(2004, "视频播放开始");
            this.f4453n = false;
        }
    }
}
