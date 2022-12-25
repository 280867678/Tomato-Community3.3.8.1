package com.tencent.liteav;

import android.content.Context;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import com.tencent.avroom.TXCAVRoomConstants;
import com.tencent.ijk.media.player.IjkBitrateItem;
import com.tencent.ijk.media.player.MediaInfo;
import com.tencent.liteav.audio.impl.TXCAudioRouteMgr;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.txcvodplayer.TXCVodPlayerConfig;
import com.tencent.liteav.txcvodplayer.TXCVodVideoView;
import com.tencent.liteav.txcvodplayer.TXVodPlayerListener;
import com.tencent.liteav.txcvodplayer.TextureRenderView;
import com.tencent.rtmp.TXBitrateItem;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import com.zzz.ipfssdk.callback.exception.CodeState;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.tencent.liteav.o */
/* loaded from: classes3.dex */
public class TXCVodPlayer extends TXIPlayer {

    /* renamed from: a */
    protected boolean f4957a;

    /* renamed from: f */
    private TXCVodVideoView f4958f;

    /* renamed from: g */
    private TXCVodPlayerConfig f4959g;

    /* renamed from: i */
    private boolean f4961i;

    /* renamed from: m */
    private Surface f4965m;

    /* renamed from: h */
    private TXCVodPlayCollection f4960h = null;

    /* renamed from: j */
    private boolean f4962j = true;

    /* renamed from: k */
    private boolean f4963k = true;

    /* renamed from: l */
    private float f4964l = 1.0f;

    /* renamed from: n */
    private TXVodPlayerListener f4966n = new TXVodPlayerListener() { // from class: com.tencent.liteav.o.1
        @Override // com.tencent.liteav.txcvodplayer.TXVodPlayerListener
        /* renamed from: a */
        public void mo625a(int i, Bundle bundle) {
            TXINotifyListener tXINotifyListener;
            String str;
            Bundle bundle2 = new Bundle(bundle);
            int i2 = TXLiveConstants.PLAY_EVT_PLAY_END;
            if (i != 2008) {
                int i3 = 0;
                switch (i) {
                    case -3005:
                        i2 = TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL;
                        if (!TXCVodPlayer.this.f4961i) {
                            TXCVodPlayer.this.f4959g.m634a(false);
                            break;
                        }
                        break;
                    case -3004:
                        i2 = TXLiveConstants.PLAY_ERR_HLS_KEY;
                        break;
                    case -3003:
                        i2 = TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND;
                        break;
                    case -3002:
                    case -3001:
                        i2 = TXLiveConstants.PLAY_ERR_NET_DISCONNECT;
                        break;
                    default:
                        switch (i) {
                            case 3000:
                                i2 = TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED;
                                TXCVodPlayer.this.f4960h.m1195d();
                                break;
                            case 3001:
                                i2 = 2004;
                                TXCVodPlayer.this.f4960h.m1195d();
                                break;
                            case 3002:
                                break;
                            case 3003:
                                i2 = TXLiveConstants.PLAY_EVT_PLAY_LOADING;
                                TXCVodPlayer.this.f4960h.m1190i();
                                break;
                            case 3004:
                                TXCVodPlayer.this.f4960h.m1196c();
                                TXCVodPlayer tXCVodPlayer = TXCVodPlayer.this;
                                if (tXCVodPlayer.f4957a) {
                                    tXCVodPlayer.f4958f.m736b();
                                    TXCLog.m2915d(TXVodPlayer.TAG, "loop play");
                                    return;
                                }
                                break;
                            case 3005:
                                i2 = TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION;
                                break;
                            case CodeState.CODES.CODE_QUERY_RESOURCE_P2P_DISABLE /* 3006 */:
                                i2 = TXLiveConstants.PLAY_WARNING_RECONNECT;
                                break;
                            case CodeState.CODES.CODE_QUERY_RESOURCE_PARAM_ERROR /* 3007 */:
                                i2 = TXLiveConstants.PLAY_EVT_PLAY_PROGRESS;
                                TXCVodPlayer.this.f4960h.m1201a(bundle.getInt(TXLiveConstants.EVT_PLAY_DURATION, 0));
                                break;
                            case CodeState.CODES.CODE_QUERY_RESOURCE_NORMAL /* 3008 */:
                                if (!TXCVodPlayer.this.f4961i) {
                                    int i4 = 1;
                                    TXCVodPlayer.this.f4961i = true;
                                    TXCVodPlayer.this.f4960h.m1194e();
                                    Bundle bundle3 = new Bundle();
                                    bundle3.putInt(TXCAVRoomConstants.EVT_ID, TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER);
                                    bundle3.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                                    MediaInfo mediaInfo = TXCVodPlayer.this.f4958f.getMediaInfo();
                                    if (mediaInfo != null && (str = mediaInfo.mVideoDecoderImpl) != null && str.contains("hevc")) {
                                        i3 = 1;
                                    }
                                    String str2 = "启动硬解";
                                    if (TXCVodPlayer.this.f4958f.getPlayerType() == 0) {
                                        if (i3 == 0) {
                                            if (!TXCVodPlayer.this.f4959g.m639a()) {
                                                str2 = "启动软解";
                                            }
                                            bundle3.putCharSequence("description", str2);
                                        } else {
                                            bundle3.putCharSequence("description", TXCVodPlayer.this.f4959g.m639a() ? "启动硬解265" : "启动软解265");
                                        }
                                        if (!TXCVodPlayer.this.f4959g.m639a()) {
                                            i4 = 2;
                                        }
                                        bundle3.putInt("EVT_PARAM1", i4);
                                        bundle3.putInt("hevc", i3);
                                    } else {
                                        bundle3.putCharSequence("description", str2);
                                        bundle3.putInt("EVT_PARAM1", 2);
                                    }
                                    mo625a(TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER, bundle3);
                                    i2 = 2003;
                                    break;
                                } else {
                                    return;
                                }
                            case CodeState.CODES.CODE_QUERY_RESOURCE_HTTP_REQUEST_ERR /* 3009 */:
                                i2 = TXLiveConstants.PLAY_EVT_CHANGE_ROTATION;
                                bundle2.putInt("EVT_PARAM1", TXCVodPlayer.this.f4958f.getMetaRotationDegree());
                                break;
                            case CodeState.CODES.CODE_QUERY_RESOURCE_NO_HANDLER /* 3010 */:
                                i2 = TXLiveConstants.PLAY_WARNING_HW_ACCELERATION_FAIL;
                                if (!TXCVodPlayer.this.f4961i) {
                                    TXCVodPlayer.this.f4959g.m634a(false);
                                    break;
                                }
                                break;
                            default:
                                switch (i) {
                                    case CodeState.CODES.CODE_QUERY_RESOURCE_UNEXPECTED_CODE_ERR /* 3012 */:
                                        TXCVodPlayer.this.f4960h.m1193f();
                                        return;
                                    case CodeState.CODES.CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR /* 3013 */:
                                        TXCVodPlayer.this.f4960h.m1191h();
                                        return;
                                    case CodeState.CODES.CODE_QUERY_DATA_BROKEN /* 3014 */:
                                        TXCVodPlayer.this.f4960h.m1192g();
                                        return;
                                    case 3015:
                                        return;
                                    case 3016:
                                        i2 = TXLiveConstants.PLAY_EVT_VOD_LOADING_END;
                                        break;
                                    default:
                                        TXCLog.m2915d(TXVodPlayer.TAG, "miss match event " + i);
                                        return;
                                }
                        }
                }
            } else {
                i2 = TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER;
            }
            bundle2.putString("EVT_MSG", bundle.getString("description", ""));
            WeakReference<TXINotifyListener> weakReference = TXCVodPlayer.this.f5205e;
            if (weakReference == null || (tXINotifyListener = weakReference.get()) == null) {
                return;
            }
            tXINotifyListener.onNotifyEvent(i2, bundle2);
        }

        @Override // com.tencent.liteav.txcvodplayer.TXVodPlayerListener
        /* renamed from: a */
        public void mo624a(Bundle bundle) {
            TXINotifyListener tXINotifyListener;
            Bundle bundle2 = new Bundle();
            int[] m2894a = TXCSystemUtil.m2894a();
            bundle2.putCharSequence("CPU_USAGE", (Integer.valueOf(m2894a[0]).intValue() / 10) + "/" + (Integer.valueOf(m2894a[1]).intValue() / 10) + "%");
            bundle2.putInt("VIDEO_FPS", (int) bundle.getFloat("fps"));
            bundle2.putInt("NET_SPEED", ((int) bundle.getLong("tcpSpeed")) / 1000);
            bundle2.putInt("CODEC_CACHE", ((int) bundle.getLong("cachedBytes")) / 1000);
            bundle2.putInt("VIDEO_WIDTH", TXCVodPlayer.this.f4958f.getVideoWidth());
            bundle2.putInt("VIDEO_HEIGHT", TXCVodPlayer.this.f4958f.getVideoHeight());
            bundle2.putString("SERVER_IP", TXCVodPlayer.this.f4958f.getServerIp());
            WeakReference<TXINotifyListener> weakReference = TXCVodPlayer.this.f5205e;
            if (weakReference == null || (tXINotifyListener = weakReference.get()) == null) {
                return;
            }
            tXINotifyListener.onNotifyEvent(15001, bundle2);
        }
    };

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: c */
    public int mo821c(int i) {
        return 0;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: e */
    public int mo818e() {
        return 0;
    }

    public TXCVodPlayer(Context context) {
        super(context);
        this.f4958f = new TXCVodVideoView(context);
        this.f4958f.setListener(this.f4966n);
        TXCAudioRouteMgr.m3371a().m3368a(context);
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo836a(TXCPlayerConfig tXCPlayerConfig) {
        super.mo836a(tXCPlayerConfig);
        if (this.f4959g == null) {
            this.f4959g = new TXCVodPlayerConfig();
        }
        this.f4959g.m638a(this.f5202b.f4343e);
        this.f4959g.m632b(this.f5202b.f4344f);
        this.f4959g.m628c(this.f5202b.f4355q);
        this.f4959g.m634a(this.f5202b.f4347i);
        this.f4959g.m636a(this.f5202b.f4351m);
        this.f4959g.m637a(this.f5202b.f4352n);
        this.f4959g.m631b(this.f5202b.f4353o);
        this.f4959g.m635a(this.f5202b.f4354p);
        this.f4959g.m629b(this.f5202b.f4356r);
        this.f4959g.m626c(this.f5202b.f4358t);
        this.f4959g.m630b(this.f5202b.f4359u);
        this.f4959g.m627c(this.f5202b.f4360v);
        this.f4958f.setConfig(this.f4959g);
        this.f4963k = tXCPlayerConfig.f4357s;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public int mo830a(String str, int i) {
        TXCloudVideoView tXCloudVideoView = this.f5204d;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.setVisibility(0);
            if (this.f5204d.getVideoView() == null) {
                TextureRenderView textureRenderView = new TextureRenderView(this.f5204d.getContext());
                this.f5204d.addVideoView(textureRenderView);
                this.f4958f.setTextureRenderView(textureRenderView);
            }
            this.f5204d.getVideoView().setVisibility(0);
        } else {
            Surface surface = this.f4965m;
            if (surface != null) {
                this.f4958f.setRenderSurface(surface);
            }
        }
        this.f4960h = new TXCVodPlayCollection(this.f5203c);
        this.f4960h.m1200a(str);
        this.f4960h.m1198b();
        this.f4961i = false;
        this.f4958f.setPlayerType(this.f4959g.m633b());
        this.f4958f.setVideoPath(str);
        this.f4958f.setAutoPlay(this.f4962j);
        this.f4958f.setRate(this.f4964l);
        this.f4958f.setAutoRotate(this.f4963k);
        if (this.f4959g != null) {
            this.f4958f.m736b();
            if (this.f4959g.m633b() == 1) {
                this.f4960h.m1197b(3);
            } else {
                this.f4960h.m1197b(1);
            }
        } else {
            this.f4958f.m736b();
            this.f4960h.m1197b(1);
        }
        TXCLog.m2915d(TXVodPlayer.TAG, "startPlay " + str);
        TXCDRApi.txReportDAU(this.f5203c, TXCDRDef.f2498bp);
        try {
            if (Class.forName("com.tencent.liteav.demo.play.SuperPlayerView") != null) {
                TXCDRApi.txReportDAU(this.f5203c, TXCDRDef.f2477bA);
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public int mo829a(boolean z) {
        this.f4958f.m731c();
        TXCloudVideoView tXCloudVideoView = this.f5204d;
        if (tXCloudVideoView != null && tXCloudVideoView.getVideoView() != null && z) {
            this.f5204d.getVideoView().setVisibility(8);
        }
        TXCVodPlayCollection tXCVodPlayCollection = this.f4960h;
        if (tXCVodPlayCollection != null) {
            tXCVodPlayCollection.m1196c();
            return 0;
        }
        return 0;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo838a(Surface surface) {
        this.f4965m = surface;
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            tXCVodVideoView.setRenderSurface(this.f4965m);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo842a() {
        this.f4958f.m728d();
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: b */
    public void mo826b() {
        this.f4958f.m736b();
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a_ */
    public void mo827a_(int i) {
        TXCVodPlayCollection tXCVodPlayCollection;
        this.f4958f.m750a(i * 1000);
        if (!this.f4961i || (tXCVodPlayCollection = this.f4960h) == null) {
            return;
        }
        tXCVodPlayCollection.m1189j();
    }

    /* renamed from: a */
    public void m1070a(float f) {
        TXCVodPlayCollection tXCVodPlayCollection;
        this.f4958f.m750a((int) (f * 1000.0f));
        if (!this.f4961i || (tXCVodPlayCollection = this.f4960h) == null) {
            return;
        }
        tXCVodPlayCollection.m1189j();
    }

    /* renamed from: h */
    public float m1059h() {
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            return tXCVodVideoView.getCurrentPosition() / 1000.0f;
        }
        return 0.0f;
    }

    /* renamed from: i */
    public float m1058i() {
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            return tXCVodVideoView.getBufferDuration() / 1000.0f;
        }
        return 0.0f;
    }

    /* renamed from: j */
    public float m1057j() {
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            return tXCVodVideoView.getDuration() / 1000.0f;
        }
        return 0.0f;
    }

    /* renamed from: k */
    public float m1056k() {
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            return tXCVodVideoView.getBufferDuration() / 1000.0f;
        }
        return 0.0f;
    }

    /* renamed from: l */
    public int m1055l() {
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            return tXCVodVideoView.getVideoWidth();
        }
        return 0;
    }

    /* renamed from: m */
    public int m1054m() {
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            return tXCVodVideoView.getVideoHeight();
        }
        return 0;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: b */
    public void mo823b(boolean z) {
        this.f4958f.setMute(z);
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo841a(int i) {
        if (i == 1) {
            this.f4958f.setRenderMode(0);
        } else {
            this.f4958f.setRenderMode(1);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: b */
    public void mo824b(int i) {
        this.f4958f.setVideoRotationDegree(360 - i);
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: a */
    public void mo833a(TXCloudVideoView tXCloudVideoView) {
        TXCloudVideoView tXCloudVideoView2 = this.f5204d;
        if (tXCloudVideoView != tXCloudVideoView2) {
            if (tXCloudVideoView2 != null) {
                tXCloudVideoView2.removeVideoView();
            }
            if (tXCloudVideoView != 0) {
                tXCloudVideoView.removeVideoView();
            }
        }
        if (tXCloudVideoView != null) {
            tXCloudVideoView.setVisibility(0);
            if (tXCloudVideoView.getVideoView() == null) {
                TextureRenderView textureRenderView = new TextureRenderView(tXCloudVideoView.getContext());
                tXCloudVideoView.addVideoView(textureRenderView);
                this.f4958f.setTextureRenderView(textureRenderView);
            }
            tXCloudVideoView.getVideoView().setVisibility(0);
        }
        super.mo833a(tXCloudVideoView);
    }

    /* renamed from: a */
    public void m1067a(TextureRenderView textureRenderView) {
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            tXCVodVideoView.setRenderView(textureRenderView);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: d */
    public TextureView mo819d() {
        TXCloudVideoView tXCloudVideoView = this.f5204d;
        if (tXCloudVideoView != null) {
            return tXCloudVideoView.getVideoView();
        }
        return null;
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: c */
    public boolean mo822c() {
        return this.f4958f.m725e();
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: c */
    public void mo820c(boolean z) {
        this.f4962j = z;
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            tXCVodVideoView.setAutoPlay(z);
        }
    }

    @Override // com.tencent.liteav.TXIPlayer
    /* renamed from: b */
    public void mo825b(float f) {
        TXCVodPlayCollection tXCVodPlayCollection;
        this.f4964l = f;
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            tXCVodVideoView.setRate(f);
        }
        if (!this.f4961i || (tXCVodPlayCollection = this.f4960h) == null) {
            return;
        }
        tXCVodPlayCollection.m1187l();
    }

    /* renamed from: c */
    public void m1065c(float f) {
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            tXCVodVideoView.setStartTime(f);
        }
    }

    /* renamed from: n */
    public int m1053n() {
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            return tXCVodVideoView.getBitrateIndex();
        }
        return 0;
    }

    /* renamed from: e */
    public void m1061e(int i) {
        TXCVodPlayCollection tXCVodPlayCollection;
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null) {
            tXCVodVideoView.setBitrateIndex(i);
        }
        if (!this.f4961i || (tXCVodPlayCollection = this.f4960h) == null) {
            return;
        }
        tXCVodPlayCollection.m1188k();
    }

    /* renamed from: o */
    public ArrayList<TXBitrateItem> m1052o() {
        ArrayList<IjkBitrateItem> supportedBitrates;
        ArrayList<TXBitrateItem> arrayList = new ArrayList<>();
        TXCVodVideoView tXCVodVideoView = this.f4958f;
        if (tXCVodVideoView != null && (supportedBitrates = tXCVodVideoView.getSupportedBitrates()) != null) {
            Iterator<IjkBitrateItem> it2 = supportedBitrates.iterator();
            while (it2.hasNext()) {
                IjkBitrateItem next = it2.next();
                TXBitrateItem tXBitrateItem = new TXBitrateItem();
                tXBitrateItem.index = next.index;
                tXBitrateItem.width = next.width;
                tXBitrateItem.height = next.height;
                tXBitrateItem.bitrate = next.bitrate;
                arrayList.add(tXBitrateItem);
            }
        }
        return arrayList;
    }

    /* renamed from: d */
    public void m1062d(boolean z) {
        this.f4957a = z;
    }

    /* renamed from: e */
    public void m1060e(boolean z) {
        TextureView mo819d = mo819d();
        if (mo819d != null) {
            mo819d.setScaleX(z ? -1.0f : 1.0f);
        }
        TXCVodPlayCollection tXCVodPlayCollection = this.f4960h;
        if (tXCVodPlayCollection != null) {
            tXCVodPlayCollection.m1199a(z);
        }
    }
}
