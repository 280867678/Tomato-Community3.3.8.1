package com.tencent.liteav.txcvodplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.p002v4.app.NotificationManagerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.one.tomato.entity.C2516Ad;
import com.tencent.ijk.media.exo.IjkExoMediaPlayer;
import com.tencent.ijk.media.player.AbstractMediaPlayer;
import com.tencent.ijk.media.player.AndroidMediaPlayer;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.tencent.ijk.media.player.IjkBitrateItem;
import com.tencent.ijk.media.player.IjkLibLoader;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.ijk.media.player.IjkMediaPlayer;
import com.tencent.ijk.media.player.IjkTimedText;
import com.tencent.ijk.media.player.MediaInfo;
import com.tencent.ijk.media.player.TextureMediaPlayer;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.txcvodplayer.IRenderView;
import com.tencent.liteav.txcvodplayer.p131a.TXCVodCacheInfo;
import com.tencent.liteav.txcvodplayer.p131a.TXCVodCacheMgr;
import com.tencent.rtmp.TXLiveConstants;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.callback.exception.CodeState;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Locale;
import org.slf4j.Marker;

/* loaded from: classes3.dex */
public class TXCVodVideoView extends FrameLayout {

    /* renamed from: A */
    private String f5287A;

    /* renamed from: C */
    private TXCVodCacheInfo f5289C;

    /* renamed from: E */
    private int f5291E;

    /* renamed from: F */
    private long f5292F;

    /* renamed from: G */
    private int f5293G;

    /* renamed from: H */
    private int f5294H;

    /* renamed from: I */
    private long f5295I;

    /* renamed from: O */
    private int f5301O;

    /* renamed from: P */
    private boolean f5302P;

    /* renamed from: S */
    private int f5305S;

    /* renamed from: af */
    private TXVodPlayerListener f5316af;

    /* renamed from: ag */
    private Handler f5317ag;

    /* renamed from: h */
    private Uri f5325h;

    /* renamed from: m */
    private int f5330m;

    /* renamed from: n */
    private int f5331n;

    /* renamed from: o */
    private int f5332o;

    /* renamed from: p */
    private int f5333p;

    /* renamed from: q */
    private int f5334q;

    /* renamed from: r */
    private int f5335r;

    /* renamed from: s */
    private int f5336s;

    /* renamed from: t */
    private int f5337t;

    /* renamed from: u */
    private int f5338u;

    /* renamed from: v */
    private Context f5339v;

    /* renamed from: w */
    private TXCVodPlayerConfig f5340w;

    /* renamed from: x */
    private IRenderView f5341x;

    /* renamed from: y */
    private int f5342y;

    /* renamed from: z */
    private int f5343z;

    /* renamed from: g */
    private String f5324g = "TXCVodVideoView";

    /* renamed from: i */
    private int f5326i = 0;

    /* renamed from: j */
    private int f5327j = 0;

    /* renamed from: k */
    private IRenderView.AbstractC3656b f5328k = null;

    /* renamed from: l */
    private IMediaPlayer f5329l = null;

    /* renamed from: a */
    protected boolean f5310a = true;

    /* renamed from: B */
    private float f5288B = 1.0f;

    /* renamed from: D */
    private TXCVodCacheMgr f5290D = TXCVodCacheMgr.m671a();

    /* renamed from: b */
    protected boolean f5319b = true;

    /* renamed from: c */
    protected int f5320c = 0;

    /* renamed from: J */
    private boolean f5296J = false;

    /* renamed from: K */
    private int f5297K = -1;

    /* renamed from: L */
    private float f5298L = 1.0f;

    /* renamed from: M */
    private float f5299M = 1.0f;

    /* renamed from: N */
    private boolean f5300N = false;

    /* renamed from: d */
    IMediaPlayer.OnVideoSizeChangedListener f5321d = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.9
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener
        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
            boolean z = (TXCVodVideoView.this.f5331n != i2 && Math.abs(TXCVodVideoView.this.f5331n - i2) > 16) || (TXCVodVideoView.this.f5330m != i && Math.abs(TXCVodVideoView.this.f5330m - i) > 16);
            TXCVodVideoView.this.f5330m = iMediaPlayer.getVideoWidth();
            TXCVodVideoView.this.f5331n = iMediaPlayer.getVideoHeight();
            TXCVodVideoView.this.f5342y = iMediaPlayer.getVideoSarNum();
            TXCVodVideoView.this.f5343z = iMediaPlayer.getVideoSarDen();
            if (TXCVodVideoView.this.f5330m != 0 && TXCVodVideoView.this.f5331n != 0) {
                if (TXCVodVideoView.this.f5341x != null) {
                    TXCVodVideoView.this.f5341x.setVideoSize(TXCVodVideoView.this.f5330m, TXCVodVideoView.this.f5331n);
                    TXCVodVideoView.this.f5341x.setVideoSampleAspectRatio(TXCVodVideoView.this.f5342y, TXCVodVideoView.this.f5343z);
                }
                TXCVodVideoView.this.requestLayout();
            }
            if (z) {
                Message message = new Message();
                message.what = 101;
                message.arg1 = 3005;
                Bundle bundle = new Bundle();
                bundle.putString("description", "分辨率改变:" + TXCVodVideoView.this.f5330m + Marker.ANY_MARKER + TXCVodVideoView.this.f5331n);
                bundle.putInt("EVT_PARAM1", TXCVodVideoView.this.f5330m);
                bundle.putInt("EVT_PARAM2", TXCVodVideoView.this.f5331n);
                message.setData(bundle);
                if (TXCVodVideoView.this.f5317ag == null) {
                    return;
                }
                TXCVodVideoView.this.f5317ag.sendMessage(message);
            }
        }
    };

    /* renamed from: e */
    IMediaPlayer.OnPreparedListener f5322e = new IMediaPlayer.OnPreparedListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.10
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnPreparedListener
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            if (TXCVodVideoView.this.f5326i == 1) {
                TXCVodVideoView.this.m749a(3000, "点播准备完成", "prepared");
                TXCVodVideoView tXCVodVideoView = TXCVodVideoView.this;
                if (!tXCVodVideoView.f5319b) {
                    tXCVodVideoView.f5327j = 4;
                    TXCVodVideoView.this.f5319b = true;
                }
                TXCVodVideoView.this.f5326i = 2;
            }
            TXCVodVideoView.this.f5337t = 0;
            if (TXCVodVideoView.this.f5326i == -1) {
                TXCVodVideoView.this.f5326i = 3;
                TXCVodVideoView.this.f5327j = 3;
            }
            if (TXCVodVideoView.this.f5317ag != null) {
                TXCVodVideoView.this.f5317ag.sendEmptyMessage(100);
                TXCVodVideoView.this.f5317ag.sendEmptyMessage(103);
            }
            TXCVodVideoView.this.f5330m = iMediaPlayer.getVideoWidth();
            TXCVodVideoView.this.f5331n = iMediaPlayer.getVideoHeight();
            if (TXCVodVideoView.this.f5330m == 0 || TXCVodVideoView.this.f5331n == 0) {
                if (TXCVodVideoView.this.f5327j != 3) {
                    return;
                }
                TXCVodVideoView.this.m736b();
            } else if (TXCVodVideoView.this.f5341x == null) {
            } else {
                TXCVodVideoView.this.f5341x.setVideoSize(TXCVodVideoView.this.f5330m, TXCVodVideoView.this.f5331n);
                TXCVodVideoView.this.f5341x.setVideoSampleAspectRatio(TXCVodVideoView.this.f5342y, TXCVodVideoView.this.f5343z);
                if ((TXCVodVideoView.this.f5341x.shouldWaitForResize() && (TXCVodVideoView.this.f5332o != TXCVodVideoView.this.f5330m || TXCVodVideoView.this.f5333p != TXCVodVideoView.this.f5331n)) || TXCVodVideoView.this.f5327j != 3) {
                    return;
                }
                TXCVodVideoView.this.m736b();
            }
        }
    };

    /* renamed from: Q */
    private IMediaPlayer.OnCompletionListener f5303Q = new IMediaPlayer.OnCompletionListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.11
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnCompletionListener
        public void onCompletion(IMediaPlayer iMediaPlayer) {
            TXCVodVideoView tXCVodVideoView = TXCVodVideoView.this;
            if (tXCVodVideoView.f5320c == 1 && tXCVodVideoView.f5327j == -1) {
                return;
            }
            TXCVodVideoView.this.f5326i = 5;
            TXCVodVideoView.this.f5327j = 5;
            TXCVodVideoView.this.m749a(3004, "播放完成", "play end");
        }
    };

    /* renamed from: R */
    private IMediaPlayer.OnInfoListener f5304R = new IMediaPlayer.OnInfoListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.12
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnInfoListener
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
            if (i == 3) {
                TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_VIDEO_RENDERING_START:");
                if (!TXCVodVideoView.this.f5302P) {
                    TXCVodVideoView.this.m749a((int) CodeState.CODES.CODE_QUERY_RESOURCE_NORMAL, "点播显示首帧画面", "render start");
                    if (TXCVodVideoView.this.f5320c == 1) {
                        new Thread(new Runnable() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.12.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    InetAddress byName = InetAddress.getByName(TXCVodVideoView.this.f5325h.getHost());
                                    TXCVodVideoView.this.f5287A = byName.getHostAddress();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
                TXCVodVideoView tXCVodVideoView = TXCVodVideoView.this;
                tXCVodVideoView.setRate(tXCVodVideoView.f5288B);
                TXCVodVideoView tXCVodVideoView2 = TXCVodVideoView.this;
                if (tXCVodVideoView2.f5320c == 1) {
                    tXCVodVideoView2.m749a(3016, "缓冲结束", "loading end");
                    if (TXCVodVideoView.this.f5327j == 3 && TXCVodVideoView.this.f5302P) {
                        TXCVodVideoView.this.m749a(3001, "播放开始", "playing");
                    }
                }
                TXCVodVideoView.this.f5302P = true;
            } else if (i == 10011) {
                TXCVodVideoView.this.m749a((int) CodeState.CODES.CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, "收到视频数据", "first video packet");
            } else if (i == 901) {
                TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
            } else if (i == 902) {
                TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
            } else if (i == 10001) {
                TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + i2);
                TXCVodVideoView.this.f5335r = i2;
                TXCVodVideoView tXCVodVideoView3 = TXCVodVideoView.this;
                if (tXCVodVideoView3.f5310a && tXCVodVideoView3.f5335r > 0) {
                    TXCVodVideoView tXCVodVideoView4 = TXCVodVideoView.this;
                    tXCVodVideoView4.f5334q = tXCVodVideoView4.f5335r;
                    if (TXCVodVideoView.this.f5341x != null) {
                        TXCVodVideoView.this.f5341x.setVideoRotation(TXCVodVideoView.this.f5334q);
                    }
                }
                TXCVodVideoView.this.m749a((int) CodeState.CODES.CODE_QUERY_RESOURCE_HTTP_REQUEST_ERR, "视频角度 " + TXCVodVideoView.this.f5335r, "rotation " + TXCVodVideoView.this.f5335r);
            } else if (i != 10002) {
                switch (i) {
                    case 700:
                        TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        break;
                    case 701:
                        TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_BUFFERING_START:");
                        TXCVodVideoView.this.m749a(3003, "缓冲开始", "loading start");
                        break;
                    case 702:
                        TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_BUFFERING_END: eof " + i2);
                        if (i2 == 0 || TXCVodVideoView.this.f5325h == null || TXCVodVideoView.this.f5325h.getPath() == null || !TXCVodVideoView.this.f5325h.getPath().endsWith(IjkMediaMeta.IJKM_KEY_M3U8)) {
                            TXCVodVideoView.this.m749a(3016, "缓冲结束", "loading end");
                            if (TXCVodVideoView.this.f5327j == 3) {
                                TXCVodVideoView.this.m749a(3001, "播放开始", "playing");
                                break;
                            }
                        }
                        break;
                    case 703:
                        TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_NETWORK_BANDWIDTH: " + i2);
                        break;
                    default:
                        switch (i) {
                            case 800:
                                TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_BAD_INTERLEAVING:");
                                break;
                            case 801:
                                TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_NOT_SEEKABLE:");
                                break;
                            case 802:
                                TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_METADATA_UPDATE:");
                                break;
                        }
                }
            } else {
                TXCLog.m2915d(TXCVodVideoView.this.f5324g, "MEDIA_INFO_AUDIO_RENDERING_START:");
            }
            return true;
        }
    };

    /* renamed from: T */
    private IMediaPlayer.OnErrorListener f5306T = new IMediaPlayer.OnErrorListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.13
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnErrorListener
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
            String str = TXCVodVideoView.this.f5324g;
            TXCLog.m2914e(str, "onError: " + i + "," + i2);
            TXCVodVideoView.this.f5326i = -1;
            TXCVodVideoView.this.f5327j = -1;
            if (i != -1004 || i2 != -3003) {
                if (TXCVodVideoView.this.f5295I != TXCVodVideoView.this.getCurrentPosition()) {
                    TXCVodVideoView.this.f5305S = 0;
                }
                TXCVodVideoView tXCVodVideoView = TXCVodVideoView.this;
                tXCVodVideoView.f5295I = tXCVodVideoView.getCurrentPosition();
                if (TXCVodVideoView.m695r(TXCVodVideoView.this) < TXCVodVideoView.this.f5340w.f5395a) {
                    if (TXCVodVideoView.this.f5317ag != null) {
                        TXCVodVideoView.this.f5317ag.sendEmptyMessageDelayed(102, TXCVodVideoView.this.f5340w.f5396b * 1000.0f);
                    }
                } else {
                    TXCVodVideoView.this.m749a(-3002, "网络断开，播放错误", "disconnect");
                    TXCVodVideoView.this.m731c();
                }
                return true;
            }
            TXCVodVideoView.this.m749a(i2, "文件不存在", "file not exist");
            TXCVodVideoView.this.m731c();
            return true;
        }
    };

    /* renamed from: U */
    private IMediaPlayer.OnHevcVideoDecoderErrorListener f5307U = new IMediaPlayer.OnHevcVideoDecoderErrorListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.14
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnHevcVideoDecoderErrorListener
        public void onHevcVideoDecoderError(IMediaPlayer iMediaPlayer) {
            Log.d(TXCVodVideoView.this.f5324g, "onHevcVideoDecoderError");
            TXCVodVideoView.this.m749a(-3005, "点播H265解码失败", "hevc decode fail");
        }
    };

    /* renamed from: V */
    private IMediaPlayer.OnVideoDecoderErrorListener f5308V = new IMediaPlayer.OnVideoDecoderErrorListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.15
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnVideoDecoderErrorListener
        public void onVideoDecoderError(IMediaPlayer iMediaPlayer) {
            Log.d(TXCVodVideoView.this.f5324g, "onVideoDecoderError");
            if (TXCVodVideoView.this.f5326i != 4) {
                TXCVodVideoView.this.m749a((int) CodeState.CODES.CODE_QUERY_RESOURCE_NO_HANDLER, "点播解码失败", "decode fail");
            }
            if (TXCVodVideoView.this.f5302P || !TXCVodVideoView.this.f5340w.f5398d) {
                return;
            }
            TXCVodVideoView.this.f5340w.f5398d = false;
            TXCVodVideoView.this.m719g();
        }
    };

    /* renamed from: W */
    private IMediaPlayer.OnBufferingUpdateListener f5309W = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.2
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
            TXCVodVideoView.this.f5336s = i;
        }
    };

    /* renamed from: aa */
    private IMediaPlayer.OnSeekCompleteListener f5311aa = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.3
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(IMediaPlayer iMediaPlayer) {
            TXCLog.m2912v(TXCVodVideoView.this.f5324g, "seek complete");
            TXCVodVideoView.this.f5296J = false;
            if (TXCVodVideoView.this.f5297K >= 0) {
                TXCVodVideoView tXCVodVideoView = TXCVodVideoView.this;
                tXCVodVideoView.m750a(tXCVodVideoView.f5297K);
            }
        }
    };

    /* renamed from: ab */
    private IMediaPlayer.OnTimedTextListener f5312ab = new IMediaPlayer.OnTimedTextListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.4
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnTimedTextListener
        public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
        }
    };

    /* renamed from: ac */
    private IjkMediaPlayer.OnNativeInvokeListener f5313ac = new IjkMediaPlayer.OnNativeInvokeListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.5
        @Override // com.tencent.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener
        public boolean onNativeInvoke(int i, Bundle bundle) {
            if (i != 131074) {
                if (i != 131106) {
                    return false;
                }
                TXCVodVideoView.this.m749a((int) CodeState.CODES.CODE_QUERY_DATA_BROKEN, "PLAYER_EVENT_DNS_RESOLVED", "dns resolved");
                return true;
            }
            TXCVodVideoView.this.f5287A = bundle.getString("ip");
            TXCVodVideoView.this.m749a((int) CodeState.CODES.CODE_QUERY_RESOURCE_UNEXPECTED_CODE_ERR, "CTRL_DID_TCP_OPEN", "tcp open");
            return true;
        }
    };

    /* renamed from: ad */
    private IMediaPlayer.OnHLSKeyErrorListener f5314ad = new IMediaPlayer.OnHLSKeyErrorListener() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.6
        @Override // com.tencent.ijk.media.player.IMediaPlayer.OnHLSKeyErrorListener
        public void onHLSKeyError(IMediaPlayer iMediaPlayer) {
            Log.e(TXCVodVideoView.this.f5324g, "onHLSKeyError");
            TXCVodVideoView.this.m749a(-3004, CodeState.MSGS.MSG_HLS_DECRET_ERROR, "hls key error");
            if (TXCVodVideoView.this.f5329l != null) {
                TXCVodVideoView.this.f5329l.stop();
                TXCVodVideoView.this.f5329l.release();
                TXCVodVideoView.this.f5329l = null;
            }
            TXCVodVideoView.this.f5326i = -1;
            TXCVodVideoView.this.f5327j = -1;
        }
    };

    /* renamed from: f */
    IRenderView.AbstractC3655a f5323f = new IRenderView.AbstractC3655a() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.7
        @Override // com.tencent.liteav.txcvodplayer.IRenderView.AbstractC3655a
        /* renamed from: a */
        public void mo675a(@NonNull IRenderView.AbstractC3656b abstractC3656b, int i, int i2, int i3) {
            if (abstractC3656b.mo670a() != TXCVodVideoView.this.f5341x) {
                TXCLog.m2914e(TXCVodVideoView.this.f5324g, "onSurfaceChanged: unmatched render callback\n");
                return;
            }
            TXCLog.m2915d(TXCVodVideoView.this.f5324g, "onSurfaceChanged");
            TXCVodVideoView.this.f5332o = i2;
            TXCVodVideoView.this.f5333p = i3;
            boolean z = true;
            boolean z2 = TXCVodVideoView.this.f5327j == 3;
            if (TXCVodVideoView.this.f5341x.shouldWaitForResize() && (TXCVodVideoView.this.f5330m != i2 || TXCVodVideoView.this.f5331n != i3)) {
                z = false;
            }
            if (TXCVodVideoView.this.f5329l == null || !z2 || !z || TXCVodVideoView.this.f5327j != 3) {
                return;
            }
            TXCVodVideoView.this.m736b();
        }

        @Override // com.tencent.liteav.txcvodplayer.IRenderView.AbstractC3655a
        /* renamed from: a */
        public void mo676a(@NonNull IRenderView.AbstractC3656b abstractC3656b, int i, int i2) {
            if (abstractC3656b.mo670a() != TXCVodVideoView.this.f5341x) {
                TXCLog.m2914e(TXCVodVideoView.this.f5324g, "onSurfaceCreated: unmatched render callback\n");
                return;
            }
            TXCLog.m2915d(TXCVodVideoView.this.f5324g, "onSurfaceCreated");
            TXCVodVideoView.this.f5328k = abstractC3656b;
            if (TXCVodVideoView.this.f5329l == null) {
                TXCVodVideoView.this.m722f();
                return;
            }
            TXCVodVideoView tXCVodVideoView = TXCVodVideoView.this;
            tXCVodVideoView.m747a(tXCVodVideoView.f5329l, abstractC3656b);
        }

        @Override // com.tencent.liteav.txcvodplayer.IRenderView.AbstractC3655a
        /* renamed from: a */
        public void mo677a(@NonNull IRenderView.AbstractC3656b abstractC3656b) {
            if (abstractC3656b.mo670a() != TXCVodVideoView.this.f5341x) {
                TXCLog.m2914e(TXCVodVideoView.this.f5324g, "onSurfaceDestroyed: unmatched render callback\n");
                return;
            }
            TXCLog.m2915d(TXCVodVideoView.this.f5324g, "onSurfaceDestroyed");
            TXCVodVideoView.this.f5328k = null;
            if (TXCVodVideoView.this.f5329l != null) {
                TXCVodVideoView.this.f5329l.setSurface(null);
            }
            TXCVodVideoView.this.m751a();
        }
    };

    /* renamed from: ae */
    private int f5315ae = 0;

    /* renamed from: ah */
    private boolean f5318ah = false;

    /* renamed from: r */
    static /* synthetic */ int m695r(TXCVodVideoView tXCVodVideoView) {
        int i = tXCVodVideoView.f5305S;
        tXCVodVideoView.f5305S = i + 1;
        return i;
    }

    public int getMetaRotationDegree() {
        return this.f5335r;
    }

    public TXCVodVideoView(Context context) {
        super(context);
        m748a(context);
    }

    public TXCVodVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m748a(context);
    }

    public TXCVodVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m748a(context);
    }

    /* renamed from: a */
    private void m748a(Context context) {
        this.f5339v = context.getApplicationContext();
        this.f5340w = new TXCVodPlayerConfig();
        m713i();
        this.f5330m = 0;
        this.f5331n = 0;
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.f5326i = 0;
        this.f5327j = 0;
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper != null) {
            this.f5317ag = new HandlerC3652a(this, mainLooper);
        } else {
            this.f5317ag = null;
        }
    }

    public void setRenderView(IRenderView iRenderView) {
        int i;
        int i2;
        String str = this.f5324g;
        TXCLog.m2915d(str, "setRenderView " + iRenderView);
        if (this.f5341x != null) {
            IMediaPlayer iMediaPlayer = this.f5329l;
            if (iMediaPlayer != null) {
                iMediaPlayer.setDisplay(null);
            }
            View view = this.f5341x.getView();
            this.f5341x.removeRenderCallback(this.f5323f);
            this.f5341x = null;
            if (view.getParent() == this) {
                removeView(view);
            }
        }
        if (iRenderView == null) {
            return;
        }
        this.f5341x = iRenderView;
        iRenderView.setAspectRatio(this.f5315ae);
        int i3 = this.f5330m;
        if (i3 > 0 && (i2 = this.f5331n) > 0) {
            iRenderView.setVideoSize(i3, i2);
        }
        int i4 = this.f5342y;
        if (i4 > 0 && (i = this.f5343z) > 0) {
            iRenderView.setVideoSampleAspectRatio(i4, i);
        }
        View view2 = this.f5341x.getView();
        view2.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        if (view2.getParent() == null) {
            addView(view2);
        }
        this.f5341x.addRenderCallback(this.f5323f);
        this.f5341x.setVideoRotation(this.f5334q);
    }

    public void setRender(int i) {
        if (i == 0) {
            setRenderView(null);
        } else if (i == 1) {
            setRenderView(new SurfaceRenderView(this.f5339v));
        } else if (i == 2) {
            TextureRenderView textureRenderView = new TextureRenderView(this.f5339v);
            if (this.f5329l != null) {
                textureRenderView.getSurfaceHolder().mo668a(this.f5329l);
                textureRenderView.setVideoSize(this.f5329l.getVideoWidth(), this.f5329l.getVideoHeight());
                textureRenderView.setVideoSampleAspectRatio(this.f5329l.getVideoSarNum(), this.f5329l.getVideoSarDen());
                textureRenderView.setAspectRatio(this.f5315ae);
            }
            setRenderView(textureRenderView);
        } else {
            TXCLog.m2914e(this.f5324g, String.format(Locale.getDefault(), "invalid render %d\n", Integer.valueOf(i)));
        }
    }

    public void setTextureRenderView(TextureRenderView textureRenderView) {
        String str = this.f5324g;
        TXCLog.m2915d(str, "setTextureRenderView " + textureRenderView);
        if (this.f5329l != null) {
            textureRenderView.getSurfaceHolder().mo668a(this.f5329l);
            textureRenderView.setVideoSize(this.f5329l.getVideoWidth(), this.f5329l.getVideoHeight());
            textureRenderView.setVideoSampleAspectRatio(this.f5329l.getVideoSarNum(), this.f5329l.getVideoSarDen());
            textureRenderView.setAspectRatio(this.f5315ae);
        }
        setRenderView(textureRenderView);
    }

    public void setRenderSurface(final Surface surface) {
        this.f5328k = new IRenderView.AbstractC3656b() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.1
            @Override // com.tencent.liteav.txcvodplayer.IRenderView.AbstractC3656b
            /* renamed from: a */
            public void mo668a(IMediaPlayer iMediaPlayer) {
                iMediaPlayer.setSurface(surface);
            }

            @Override // com.tencent.liteav.txcvodplayer.IRenderView.AbstractC3656b
            @NonNull
            /* renamed from: a */
            public IRenderView mo670a() {
                return TXCVodVideoView.this.f5341x;
            }
        };
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer != null) {
            m747a(iMediaPlayer, this.f5328k);
        }
    }

    public void setVideoPath(String str) {
        setVideoURI(Uri.parse(str));
    }

    public void setVideoURI(Uri uri) {
        this.f5325h = uri;
        this.f5338u = 0;
        this.f5294H = 0;
        this.f5305S = 0;
        this.f5287A = null;
        String str = this.f5324g;
        TXCLog.m2915d(str, "setVideoURI " + uri);
        m722f();
        requestLayout();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    /* renamed from: f */
    public boolean m722f() {
        IjkMediaPlayer ijkMediaPlayer;
        IjkMediaPlayer ijkMediaPlayer2;
        TXCLog.m2915d(this.f5324g, "openVideo");
        if (this.f5325h == null) {
            return false;
        }
        if (this.f5328k == null && this.f5319b) {
            return false;
        }
        m737a(false);
        ((AudioManager) this.f5339v.getSystemService("audio")).requestAudioFocus(null, 3, 1);
        try {
            String uri = this.f5325h.toString();
            if (uri.startsWith("/") && !new File(uri).exists()) {
                throw new FileNotFoundException();
            }
            int i = this.f5320c;
            if (i == 1) {
                AbstractMediaPlayer ijkExoMediaPlayer = new IjkExoMediaPlayer(this.f5339v);
                TXCLog.m2913i(this.f5324g, "exo media player " + ijkExoMediaPlayer);
                ijkMediaPlayer = ijkExoMediaPlayer;
            } else if (i == 2) {
                AbstractMediaPlayer androidMediaPlayer = new AndroidMediaPlayer();
                TXCLog.m2913i(this.f5324g, "android media player " + androidMediaPlayer);
                ijkMediaPlayer = androidMediaPlayer;
            } else {
                if (this.f5325h != null) {
                    IjkMediaPlayer ijkMediaPlayer3 = new IjkMediaPlayer(new IjkLibLoader() { // from class: com.tencent.liteav.txcvodplayer.TXCVodVideoView.8
                        @Override // com.tencent.ijk.media.player.IjkLibLoader
                        public void loadLibrary(String str) throws UnsatisfiedLinkError, SecurityException {
                            TXCSystemUtil.m2889a(str);
                        }
                    });
                    IjkMediaPlayer.native_setLogLevel(3);
                    ijkMediaPlayer3.setOnNativeInvokeListener(this.f5313ac);
                    long j = 1;
                    if (this.f5340w.f5398d) {
                        ijkMediaPlayer3.setOption(4, "mediacodec", 1L);
                        ijkMediaPlayer3.setOption(4, "mediacodec-hevc", 1L);
                    } else {
                        ijkMediaPlayer3.setOption(4, "mediacodec", 0L);
                    }
                    ijkMediaPlayer3.setOption(4, "mediacodec-auto-rotate", 0L);
                    ijkMediaPlayer3.setOption(4, "mediacodec-handle-resolution-change", 0L);
                    ijkMediaPlayer3.setOption(4, "opensles", 0L);
                    ijkMediaPlayer3.setOption(4, "overlay-format", 842225234L);
                    ijkMediaPlayer3.setOption(4, "framedrop", 1L);
                    ijkMediaPlayer3.setOption(4, "soundtouch", 1L);
                    ijkMediaPlayer3.setOption(4, "max-fps", 30L);
                    if (this.f5319b && this.f5327j != 4) {
                        ijkMediaPlayer3.setOption(4, "start-on-prepared", 1L);
                    } else {
                        ijkMediaPlayer3.setOption(4, "start-on-prepared", 0L);
                    }
                    ijkMediaPlayer3.setOption(4, "load-on-prepared", 1L);
                    ijkMediaPlayer3.setOption(1, "http-detect-range-support", 0L);
                    ijkMediaPlayer3.setOption(2, "skip_loop_filter", 0L);
                    ijkMediaPlayer3.setOption(2, "skip_frame", 0L);
                    ijkMediaPlayer3.setOption(1, "timeout", (int) (this.f5340w.f5397c * 1000.0f * 1000.0f));
                    ijkMediaPlayer3.setOption(1, "reconnect", 1L);
                    ijkMediaPlayer3.setOption(1, "analyzeduration", 90000000L);
                    ijkMediaPlayer3.setOption(4, "enable-accurate-seek", this.f5340w.f5403i ? 1L : 0L);
                    if (this.f5340w.f5404j) {
                        j = 0;
                    }
                    ijkMediaPlayer3.setOption(4, "disable-bitrate-sync", j);
                    ijkMediaPlayer3.setOption(1, "dns_cache_timeout", 0L);
                    ijkMediaPlayer3.setOption(1, "cache_max_capacity", 2147483647L);
                    if (this.f5337t > 0) {
                        ijkMediaPlayer3.setOption(4, "seek-at-start", this.f5337t);
                        TXCLog.m2915d(this.f5324g, "ijk start time " + this.f5337t);
                    }
                    if (this.f5340w.f5402h != null) {
                        String str = null;
                        for (String str2 : this.f5340w.f5402h.keySet()) {
                            str = str == null ? String.format("%s: %s", str2, this.f5340w.f5402h.get(str2)) : str + "\r\n" + String.format("%s: %s", str2, this.f5340w.f5402h.get(str2));
                        }
                        ijkMediaPlayer3.setOption(1, "headers", str);
                    }
                    ijkMediaPlayer3.setBitrateIndex(this.f5301O);
                    IjkMediaPlayer.native_setLogLevel(5);
                    ijkMediaPlayer2 = ijkMediaPlayer3;
                    if (this.f5340w.f5399e != null) {
                        ijkMediaPlayer2 = ijkMediaPlayer3;
                        if (this.f5290D.m657d(uri)) {
                            this.f5290D.m661b(this.f5340w.f5399e);
                            this.f5290D.m669a(this.f5340w.f5400f);
                            this.f5289C = this.f5290D.m659c(uri);
                            ijkMediaPlayer2 = ijkMediaPlayer3;
                            if (this.f5289C != null) {
                                if (this.f5289C.m678a() != null) {
                                    ijkMediaPlayer3.setOption(1, "cache_file_path", this.f5289C.m678a());
                                    uri = "ijkio:cache:ffio:" + this.f5325h.toString();
                                    ijkMediaPlayer2 = ijkMediaPlayer3;
                                } else {
                                    ijkMediaPlayer2 = ijkMediaPlayer3;
                                    if (this.f5289C.m674b() != null) {
                                        ijkMediaPlayer3.setOption(1, "cache_db_path", this.f5289C.m674b());
                                        uri = "ijkhlscache:" + this.f5325h.toString();
                                        ijkMediaPlayer2 = ijkMediaPlayer3;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    ijkMediaPlayer2 = null;
                }
                TXCLog.m2913i(this.f5324g, "ijk media player " + ijkMediaPlayer2);
                ijkMediaPlayer = ijkMediaPlayer2;
            }
            this.f5329l = new TextureMediaPlayer(ijkMediaPlayer);
            this.f5329l.setDataSource(uri);
            this.f5329l.setOnPreparedListener(this.f5322e);
            this.f5329l.setOnVideoSizeChangedListener(this.f5321d);
            this.f5329l.setOnCompletionListener(this.f5303Q);
            this.f5329l.setOnErrorListener(this.f5306T);
            this.f5329l.setOnInfoListener(this.f5304R);
            this.f5329l.setOnBufferingUpdateListener(this.f5309W);
            this.f5329l.setOnSeekCompleteListener(this.f5311aa);
            this.f5329l.setOnTimedTextListener(this.f5312ab);
            this.f5329l.setOnHLSKeyErrorListener(this.f5314ad);
            this.f5329l.setOnHevcVideoDecoderErrorListener(this.f5307U);
            this.f5329l.setOnVideoDecoderErrorListener(this.f5308V);
            this.f5336s = 0;
            m747a(this.f5329l, this.f5328k);
            this.f5329l.setAudioStreamType(3);
            this.f5329l.setScreenOnWhilePlaying(true);
            this.f5329l.prepareAsync();
            setMute(this.f5300N);
            this.f5326i = 1;
        } catch (FileNotFoundException unused) {
            this.f5326i = -1;
            this.f5327j = -1;
            this.f5306T.onError(this.f5329l, -1004, -3003);
        } catch (Exception e) {
            TXCLog.m2911w(this.f5324g, e.toString());
            this.f5326i = -1;
            this.f5327j = -1;
            this.f5306T.onError(this.f5329l, 1, 0);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m747a(IMediaPlayer iMediaPlayer, IRenderView.AbstractC3656b abstractC3656b) {
        if (iMediaPlayer == null) {
            return;
        }
        if (abstractC3656b == null) {
            iMediaPlayer.setDisplay(null);
            return;
        }
        TXCLog.m2915d(this.f5324g, "bindSurfaceHolder");
        abstractC3656b.mo668a(iMediaPlayer);
    }

    /* renamed from: a */
    void m751a() {
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer != null) {
            iMediaPlayer.setDisplay(null);
        }
    }

    /* renamed from: a */
    void m737a(boolean z) {
        if (this.f5329l != null) {
            String str = this.f5324g;
            TXCLog.m2915d(str, "release player " + this.f5329l);
            this.f5329l.reset();
            this.f5329l.release();
            this.f5329l = null;
            this.f5326i = 0;
            if (z) {
                this.f5327j = 0;
                this.f5330m = 0;
                this.f5331n = 0;
            }
            ((AudioManager) this.f5339v.getSystemService("audio")).abandonAudioFocus(null);
        }
    }

    /* renamed from: b */
    public void m736b() {
        TXCLog.m2913i(this.f5324g, C2516Ad.TYPE_START);
        if (m716h()) {
            if (this.f5320c == 1 && this.f5326i == 5) {
                this.f5294H = 0;
                this.f5329l.seekTo(0L);
            }
            this.f5329l.start();
            if (this.f5326i != 3 && !this.f5296J) {
                this.f5326i = 3;
                m749a(3001, "播放开始", "playing");
            }
        }
        this.f5327j = 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: g */
    public void m719g() {
        IMediaPlayer iMediaPlayer;
        TXCLog.m2915d(this.f5324g, "replay");
        int i = this.f5320c;
        if (i != 0) {
            if (i != 1) {
                return;
            }
            m710j();
            return;
        }
        if (this.f5337t == 0 && (iMediaPlayer = this.f5329l) != null && this.f5338u > 0) {
            this.f5337t = (int) iMediaPlayer.getCurrentPosition();
        }
        if (m722f()) {
            return;
        }
        m737a(false);
    }

    /* renamed from: c */
    public void m731c() {
        if (this.f5329l != null) {
            if (this.f5289C != null) {
                if (getDuration() <= 0) {
                    this.f5290D.m664a(this.f5289C.m672d(), true);
                } else {
                    this.f5290D.m664a(this.f5289C.m672d(), false);
                }
                this.f5289C = null;
            }
            this.f5329l.stop();
            this.f5329l.release();
            this.f5329l = null;
            this.f5325h = null;
            this.f5330m = 0;
            this.f5331n = 0;
            this.f5288B = 1.0f;
            this.f5296J = false;
            this.f5297K = -1;
            this.f5326i = 0;
            this.f5327j = 0;
            this.f5302P = false;
            this.f5301O = 0;
            ((AudioManager) this.f5339v.getSystemService("audio")).abandonAudioFocus(null);
        }
        Handler handler = this.f5317ag;
        if (handler != null) {
            handler.removeMessages(102);
        }
        TXCLog.m2913i(this.f5324g, "stop");
    }

    /* renamed from: d */
    public void m728d() {
        this.f5327j = 4;
        TXCLog.m2913i(this.f5324g, "pause");
        if (!m716h() || !this.f5329l.isPlaying()) {
            return;
        }
        this.f5329l.pause();
        this.f5326i = 4;
    }

    public int getDuration() {
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer != null && this.f5338u < 1) {
            this.f5338u = (int) iMediaPlayer.getDuration();
        }
        return this.f5338u;
    }

    public int getCurrentPosition() {
        int i;
        if (this.f5320c == 0) {
            if (this.f5296J && (i = this.f5297K) >= 0) {
                return i;
            }
            int i2 = this.f5337t;
            if (i2 > 0) {
                return i2;
            }
        }
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer != null) {
            int currentPosition = (int) iMediaPlayer.getCurrentPosition();
            if (currentPosition <= 1) {
                return Math.max(currentPosition, this.f5294H);
            }
            this.f5294H = currentPosition;
            return currentPosition;
        }
        return 0;
    }

    /* renamed from: a */
    public void m750a(int i) {
        String str = this.f5324g;
        TXCLog.m2915d(str, "seek to " + i);
        if (getUrlPathExtention().equals(IjkMediaMeta.IJKM_KEY_M3U8)) {
            i = Math.min(i, getDuration() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
        }
        if (i >= 0 && m716h()) {
            if (i > getDuration()) {
                i = getDuration();
            }
            if (this.f5296J) {
                this.f5297K = i;
            } else {
                this.f5297K = -1;
                this.f5329l.seekTo(i);
            }
            if (this.f5320c != 0) {
                return;
            }
            this.f5296J = true;
        }
    }

    public void setMute(boolean z) {
        this.f5300N = z;
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer == null) {
            return;
        }
        if (z) {
            iMediaPlayer.setVolume(0.0f, 0.0f);
        } else {
            iMediaPlayer.setVolume(this.f5298L, this.f5299M);
        }
    }

    /* renamed from: e */
    public boolean m725e() {
        return m716h() && this.f5329l.isPlaying() && this.f5326i != 4;
    }

    public int getBufferDuration() {
        if (this.f5329l != null) {
            IMediaPlayer unwrappedMediaPlayer = getUnwrappedMediaPlayer();
            try {
                if (1 == this.f5320c && (unwrappedMediaPlayer instanceof IjkExoMediaPlayer)) {
                    this.f5336s = ((IjkExoMediaPlayer) unwrappedMediaPlayer).getBufferedPercentage();
                }
            } catch (NoClassDefFoundError unused) {
            }
            int duration = (this.f5336s * getDuration()) / 100;
            if (duration < getCurrentPosition()) {
                duration = getCurrentPosition();
            }
            return Math.abs(getDuration() - duration) < 1000 ? getDuration() : duration;
        }
        return 0;
    }

    /* renamed from: h */
    private boolean m716h() {
        int i;
        return (this.f5329l == null || (i = this.f5326i) == -1 || i == 0 || i == 1) ? false : true;
    }

    public void setConfig(TXCVodPlayerConfig tXCVodPlayerConfig) {
        if (tXCVodPlayerConfig != null) {
            this.f5340w = tXCVodPlayerConfig;
            this.f5290D.m666a(this.f5340w.f5405k);
        }
    }

    public void setRenderMode(int i) {
        this.f5315ae = i;
        IRenderView iRenderView = this.f5341x;
        if (iRenderView != null) {
            iRenderView.setAspectRatio(this.f5315ae);
        }
        IRenderView iRenderView2 = this.f5341x;
        if (iRenderView2 != null) {
            iRenderView2.setVideoRotation(this.f5334q);
        }
    }

    public void setVideoRotationDegree(int i) {
        if (i != 0 && i != 90 && i != 180 && i != 270) {
            if (i != 360) {
                String str = this.f5324g;
                TXCLog.m2914e(str, "not support degree " + i);
                return;
            }
            i = 0;
        }
        this.f5334q = i;
        IRenderView iRenderView = this.f5341x;
        if (iRenderView != null) {
            iRenderView.setVideoRotation(this.f5334q);
        }
        IRenderView iRenderView2 = this.f5341x;
        if (iRenderView2 != null) {
            iRenderView2.setAspectRatio(this.f5315ae);
        }
    }

    /* renamed from: i */
    private void m713i() {
        setRender(0);
    }

    public void setAutoPlay(boolean z) {
        this.f5319b = z;
    }

    public void setRate(float f) {
        String str = this.f5324g;
        TXCLog.m2915d(str, "setRate " + f);
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer != null) {
            iMediaPlayer.setRate(f);
        }
        this.f5288B = f;
    }

    public void setStartTime(float f) {
        this.f5337t = (int) (f * 1000.0f);
    }

    public void setAutoRotate(boolean z) {
        this.f5310a = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tencent.liteav.txcvodplayer.TXCVodVideoView$a */
    /* loaded from: classes3.dex */
    public static class HandlerC3652a extends Handler {

        /* renamed from: a */
        private final WeakReference<TXCVodVideoView> f5361a;

        /* renamed from: b */
        private final int f5362b = 500;

        public HandlerC3652a(TXCVodVideoView tXCVodVideoView, Looper looper) {
            super(looper);
            this.f5361a = new WeakReference<>(tXCVodVideoView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            long j;
            long j2;
            TXCVodVideoView tXCVodVideoView = this.f5361a.get();
            if (tXCVodVideoView == null || tXCVodVideoView.f5316af == null) {
                return;
            }
            switch (message.what) {
                case 100:
                    float f = 0.0f;
                    IMediaPlayer unwrappedMediaPlayer = tXCVodVideoView.getUnwrappedMediaPlayer();
                    if (unwrappedMediaPlayer == null) {
                        return;
                    }
                    long j3 = 0;
                    if (unwrappedMediaPlayer instanceof IjkMediaPlayer) {
                        IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer) unwrappedMediaPlayer;
                        f = ijkMediaPlayer.getVideoOutputFramesPerSecond();
                        j3 = ijkMediaPlayer.getVideoCachedBytes() + ijkMediaPlayer.getAudioCachedBytes();
                        long bitRate = ijkMediaPlayer.getBitRate();
                        long tcpSpeed = ijkMediaPlayer.getTcpSpeed();
                        j = bitRate;
                        j2 = tcpSpeed;
                    } else if (unwrappedMediaPlayer instanceof IjkExoMediaPlayer) {
                        IjkExoMediaPlayer ijkExoMediaPlayer = (IjkExoMediaPlayer) unwrappedMediaPlayer;
                        DecoderCounters videoDecoderCounters = ijkExoMediaPlayer.getVideoDecoderCounters();
                        if (videoDecoderCounters != null) {
                            long currentTimeMillis = System.currentTimeMillis() - tXCVodVideoView.f5292F;
                            int i = videoDecoderCounters.renderedOutputBufferCount - tXCVodVideoView.f5291E;
                            tXCVodVideoView.f5292F = System.currentTimeMillis();
                            tXCVodVideoView.f5291E = videoDecoderCounters.renderedOutputBufferCount;
                            if (currentTimeMillis < 3000 && currentTimeMillis > 0 && i < 120 && i > 0) {
                                tXCVodVideoView.f5293G = (int) Math.ceil((1000.0d / currentTimeMillis) * i);
                            }
                        }
                        f = tXCVodVideoView.f5293G;
                        j = ijkExoMediaPlayer.getObservedBitrate();
                        j2 = j / 8;
                    } else {
                        j = 0;
                        j2 = 0;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putFloat("fps", f);
                    bundle.putLong("cachedBytes", j3);
                    bundle.putLong("bitRate", j);
                    bundle.putLong("tcpSpeed", j2);
                    tXCVodVideoView.f5316af.mo624a(bundle);
                    removeMessages(100);
                    sendEmptyMessageDelayed(100, 500L);
                    return;
                case 101:
                    tXCVodVideoView.f5316af.mo625a(message.arg1, message.getData());
                    return;
                case 102:
                    tXCVodVideoView.m719g();
                    tXCVodVideoView.m749a((int) CodeState.CODES.CODE_QUERY_RESOURCE_P2P_DISABLE, "点播网络重连", "reconnect");
                    return;
                case 103:
                    long currentPosition = tXCVodVideoView.getCurrentPosition();
                    Bundle bundle2 = new Bundle();
                    long bufferDuration = tXCVodVideoView.getBufferDuration();
                    long duration = tXCVodVideoView.getDuration();
                    bundle2.putInt(TXLiveConstants.EVT_PLAY_PROGRESS, (int) (currentPosition / 1000));
                    bundle2.putInt(TXLiveConstants.EVT_PLAY_DURATION, (int) (duration / 1000));
                    bundle2.putInt(TXLiveConstants.NET_STATUS_PLAYABLE_DURATION, (int) (bufferDuration / 1000));
                    bundle2.putInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS, (int) currentPosition);
                    bundle2.putInt(TXLiveConstants.EVT_PLAY_DURATION_MS, (int) duration);
                    bundle2.putInt(TXLiveConstants.EVT_PLAYABLE_DURATION_MS, (int) bufferDuration);
                    tXCVodVideoView.f5316af.mo625a(CodeState.CODES.CODE_QUERY_RESOURCE_PARAM_ERROR, bundle2);
                    if (tXCVodVideoView.f5329l == null) {
                        return;
                    }
                    removeMessages(103);
                    if (tXCVodVideoView.f5340w.f5406l <= 0) {
                        tXCVodVideoView.f5340w.f5406l = 500;
                    }
                    sendEmptyMessageDelayed(103, tXCVodVideoView.f5340w.f5406l);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: j */
    private void m710j() {
        IjkExoMediaPlayer ijkExoMediaPlayer = (IjkExoMediaPlayer) getUnwrappedMediaPlayer();
        if (1 != this.f5320c || !(ijkExoMediaPlayer instanceof IjkExoMediaPlayer)) {
            return;
        }
        ijkExoMediaPlayer.getPlayer().prepare(ijkExoMediaPlayer.buildMediaSource(this.f5325h, null), false, false);
        if (this.f5287A == null) {
            ijkExoMediaPlayer.getPlayer().setPlayWhenReady(this.f5319b);
        } else {
            ijkExoMediaPlayer.getPlayer().setPlayWhenReady(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m749a(int i, String str, String str2) {
        if ((i == -3005 || i == 3010) && this.f5318ah) {
            return;
        }
        Message message = new Message();
        message.what = 101;
        Bundle bundle = new Bundle();
        message.arg1 = i;
        bundle.putString("description", str);
        message.setData(bundle);
        Handler handler = this.f5317ag;
        if (handler != null) {
            handler.sendMessage(message);
        }
        if (i != 3014 && i != 3012) {
            String str3 = this.f5324g;
            TXCLog.m2915d(str3, "sendSimpleEvent " + i + ConstantUtils.PLACEHOLDER_STR_ONE + str2);
        }
        this.f5318ah = i == -3005 || i == 3010;
    }

    public void setListener(TXVodPlayerListener tXVodPlayerListener) {
        this.f5316af = tXVodPlayerListener;
    }

    public int getVideoWidth() {
        return this.f5330m;
    }

    public int getVideoHeight() {
        return this.f5331n;
    }

    public String getServerIp() {
        return this.f5287A;
    }

    public int getPlayerType() {
        return this.f5320c;
    }

    public void setPlayerType(int i) {
        this.f5320c = i;
    }

    @NonNull
    String getUrlPathExtention() {
        Uri uri = this.f5325h;
        if (uri == null || uri.getPath() == null) {
            return "";
        }
        String path = this.f5325h.getPath();
        return path.substring(path.lastIndexOf(".") + 1, path.length());
    }

    public IMediaPlayer getUnwrappedMediaPlayer() {
        IMediaPlayer iMediaPlayer = this.f5329l;
        return iMediaPlayer instanceof TextureMediaPlayer ? ((TextureMediaPlayer) iMediaPlayer).getBackEndMediaPlayer() : iMediaPlayer;
    }

    public int getBitrateIndex() {
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer != null) {
            return iMediaPlayer.getBitrateIndex();
        }
        return this.f5301O;
    }

    public void setBitrateIndex(int i) {
        String str = this.f5324g;
        TXCLog.m2915d(str, "setBitrateIndex " + i);
        if (this.f5301O == i) {
            return;
        }
        this.f5301O = i;
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer == null) {
            return;
        }
        if (this.f5340w.f5404j) {
            iMediaPlayer.setBitrateIndex(i);
        } else {
            m719g();
        }
    }

    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer != null) {
            return iMediaPlayer.getSupportedBitrates();
        }
        return new ArrayList<>();
    }

    public MediaInfo getMediaInfo() {
        IMediaPlayer iMediaPlayer = this.f5329l;
        if (iMediaPlayer == null) {
            return null;
        }
        return iMediaPlayer.getMediaInfo();
    }
}
