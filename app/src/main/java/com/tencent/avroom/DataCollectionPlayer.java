package com.tencent.avroom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.tencent.liteav.TXCRenderAndDec;
import com.tencent.liteav.audio.TXCAudioPlayer;
import com.tencent.liteav.audio.impl.Play.TXAudioJitterBufferReportInfo;
import com.tencent.liteav.avprotocol.TXCAVProtocol;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p106b.TXCVideoJitterBuffer;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.renderer.TXCVideoRender;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/* renamed from: com.tencent.avroom.a */
/* loaded from: classes3.dex */
public class DataCollectionPlayer {

    /* renamed from: c */
    private static String f1959c = "DataCollectionPlayer";

    /* renamed from: a */
    private long f1960a;

    /* renamed from: n */
    private TXCAVRoomLisenter f1972n;

    /* renamed from: o */
    private WeakReference<TXCVideoRender> f1973o;

    /* renamed from: p */
    private WeakReference<TXCAVProtocol> f1974p;

    /* renamed from: q */
    private WeakReference<TXCVideoJitterBuffer> f1975q;

    /* renamed from: r */
    private WeakReference<TXCAudioPlayer> f1976r;

    /* renamed from: s */
    private WeakReference<TXCRenderAndDec> f1977s;

    /* renamed from: u */
    private Timer f1979u;

    /* renamed from: b */
    private Handler f1961b = new Handler(Looper.getMainLooper());

    /* renamed from: d */
    private HashMap f1962d = new HashMap(100);

    /* renamed from: e */
    private HashMap f1963e = new HashMap(100);

    /* renamed from: f */
    private HashMap f1964f = new HashMap(100);

    /* renamed from: g */
    private boolean f1965g = false;

    /* renamed from: h */
    private long f1966h = 0;

    /* renamed from: i */
    private long f1967i = 0;

    /* renamed from: j */
    private long f1968j = 0;

    /* renamed from: k */
    private long f1969k = 0;

    /* renamed from: l */
    private int f1970l = 0;

    /* renamed from: m */
    private int f1971m = 0;

    /* renamed from: t */
    private TimerTask f1978t = null;

    /* renamed from: f */
    static /* synthetic */ long m3533f(DataCollectionPlayer dataCollectionPlayer) {
        long j = dataCollectionPlayer.f1969k;
        dataCollectionPlayer.f1969k = 1 + j;
        return j;
    }

    public DataCollectionPlayer(long j) {
        this.f1960a = j;
    }

    /* renamed from: a */
    public void m3559a(int i) {
        this.f1971m += i;
    }

    /* renamed from: b */
    public void m3541b(int i) {
        this.f1970l += i;
    }

    /* renamed from: a */
    public void m3545a(TXCVideoRender tXCVideoRender) {
        if (tXCVideoRender != null) {
            tXCVideoRender.m893a(this.f1960a);
        }
        this.f1973o = new WeakReference<>(tXCVideoRender);
    }

    /* renamed from: a */
    public void m3549a(TXCAVProtocol tXCAVProtocol) {
        this.f1974p = new WeakReference<>(tXCAVProtocol);
    }

    /* renamed from: a */
    public void m3548a(TXCVideoJitterBuffer tXCVideoJitterBuffer) {
        this.f1975q = new WeakReference<>(tXCVideoJitterBuffer);
    }

    /* renamed from: a */
    public void m3558a(TXCAVRoomLisenter tXCAVRoomLisenter) {
        this.f1972n = tXCAVRoomLisenter;
    }

    /* renamed from: a */
    public void m3552a(TXCAudioPlayer tXCAudioPlayer) {
        this.f1976r = new WeakReference<>(tXCAudioPlayer);
    }

    /* renamed from: a */
    public void m3547a(TXCRenderAndDec tXCRenderAndDec) {
        this.f1977s = new WeakReference<>(tXCRenderAndDec);
    }

    /* renamed from: a */
    public void m3560a() {
        TimerTask timerTask = this.f1978t;
        if (timerTask != null) {
            timerTask.cancel();
        }
        this.f1979u = new Timer(true);
        this.f1978t = new TimerTask() { // from class: com.tencent.avroom.a.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                TXCAudioPlayer tXCAudioPlayer;
                TXCVideoRender tXCVideoRender;
                TXCAVProtocol tXCAVProtocol;
                TXCLog.m2913i(DataCollectionPlayer.f1959c, "dataCollectingStart run: ");
                if (DataCollectionPlayer.this.f1974p != null && (tXCAVProtocol = (TXCAVProtocol) DataCollectionPlayer.this.f1974p.get()) != null) {
                    DataCollectionPlayer.this.m3550a(tXCAVProtocol.getDownloadStats(), 2000L);
                }
                if (DataCollectionPlayer.this.f1973o != null && (tXCVideoRender = (TXCVideoRender) DataCollectionPlayer.this.f1973o.get()) != null) {
                    DataCollectionPlayer.this.m3546a(tXCVideoRender.m863q(), 2000L);
                }
                if (DataCollectionPlayer.this.f1975q != null && ((TXCVideoJitterBuffer) DataCollectionPlayer.this.f1975q.get()) != null) {
                    long m1393f = ((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1393f();
                    if (DataCollectionPlayer.this.f1965g) {
                        DataCollectionPlayer.m3533f(DataCollectionPlayer.this);
                        DataCollectionPlayer.this.f1968j += m1393f;
                        if (m1393f > DataCollectionPlayer.this.f1967i) {
                            DataCollectionPlayer.this.f1967i = m1393f;
                        }
                    }
                }
                if (DataCollectionPlayer.this.f1977s != null && DataCollectionPlayer.this.f1977s.get() != null) {
                    DataCollectionPlayer.this.f1962d.put("AUDIO_CACHE", Integer.valueOf((int) ((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1396d()));
                    DataCollectionPlayer.this.f1962d.put("VIDEO_CACHE", Integer.valueOf((int) ((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1394e()));
                    DataCollectionPlayer.this.f1962d.put("VIDEO_CACHE_CNT", Integer.valueOf((int) ((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1393f()));
                    DataCollectionPlayer.this.f1962d.put("V_DEC_CACHE_CNT", Integer.valueOf(((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1392g()));
                    DataCollectionPlayer.this.f1962d.put("AV_INTERVAL", Integer.valueOf((int) ((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1391h()));
                    DataCollectionPlayer.this.f1962d.put("AUDIO_INFO", ((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1386m());
                    DataCollectionPlayer.this.f1962d.put("AUDIO_JITTER", Integer.valueOf(((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1390i()));
                    DataCollectionPlayer.this.f1962d.put("AV_NET_RECV_INTERVAL", Long.valueOf(((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1389j()));
                    DataCollectionPlayer.this.f1962d.put("AUDIO_SPEED", Float.valueOf(((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1388k()));
                    DataCollectionPlayer.this.f1962d.put(TXLiveConstants.NET_STATUS_VIDEO_GOP, Integer.valueOf(((TXCRenderAndDec) DataCollectionPlayer.this.f1977s.get()).m1387l()));
                }
                if (DataCollectionPlayer.this.f1976r != null && (tXCAudioPlayer = (TXCAudioPlayer) DataCollectionPlayer.this.f1976r.get()) != null) {
                    DataCollectionPlayer.this.m3551a(tXCAudioPlayer.m3457i());
                }
                int[] m2894a = TXCSystemUtil.m2894a();
                DataCollectionPlayer.this.f1962d.put("u32_app_cpu_usage", Long.valueOf(m2894a[0]));
                DataCollectionPlayer.this.f1962d.put("u32_cpu_usage", Long.valueOf(m2894a[1]));
                final Bundle m3536d = DataCollectionPlayer.this.m3536d();
                DataCollectionPlayer.this.f1961b.post(new Runnable() { // from class: com.tencent.avroom.a.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DataCollectionPlayer.this.f1972n != null) {
                            DataCollectionPlayer.this.f1972n.onAVRoomStatus(DataCollectionPlayer.this.f1960a, m3536d);
                        }
                    }
                });
            }
        };
        this.f1979u.schedule(this.f1978t, 0L, 2000L);
    }

    /* renamed from: b */
    public void m3542b() {
        Timer timer = this.f1979u;
        if (timer != null) {
            timer.cancel();
            this.f1979u = null;
        }
        HashMap hashMap = this.f1962d;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m3546a(TXCVideoRender.C3617a c3617a, long j) {
        if (c3617a == null || j == 0) {
            return;
        }
        this.f1962d.put("u32_fps", Long.valueOf((m3543a("u32_fps", c3617a.f5166c) * 10000) / j));
        this.f1962d.put("u32_first_i_frame", Long.valueOf(c3617a.f5173j));
        this.f1962d.put("u32_avg_block_count", Long.valueOf(c3617a.f5169f));
        this.f1962d.put("u32_avg_block_time", Long.valueOf(c3617a.f5168e));
        this.f1962d.put("VIDEO_WIDTH", Long.valueOf(c3617a.f5174k));
        this.f1962d.put("VIDEO_HEIGHT", Long.valueOf(c3617a.f5175l));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public Bundle m3536d() {
        Bundle bundle = new Bundle();
        bundle.putLong("myid", 16842960L);
        bundle.putCharSequence("CPU_USAGE", (((Long) this.f1962d.get("u32_app_cpu_usage")).intValue() / 10) + "/" + (((Long) this.f1962d.get("u32_cpu_usage")).intValue() / 10) + "%");
        bundle.putInt("VIDEO_WIDTH", m3544a("VIDEO_WIDTH"));
        bundle.putInt("VIDEO_HEIGHT", m3544a("VIDEO_HEIGHT"));
        bundle.putInt("NET_SPEED", m3544a("u32_avg_net_speed"));
        bundle.putInt("VIDEO_FPS", m3544a("u32_fps") / 10);
        bundle.putInt("DROP_SIZE", m3544a("video_drop"));
        bundle.putInt("VIDEO_BITRATE", m3544a("u32_avg_video_bitrate"));
        bundle.putInt("AUDIO_BITRATE", m3544a("u32_avg_audio_bitrate"));
        bundle.putInt("CODEC_CACHE", m3544a("AUDIO_CACHE"));
        bundle.putInt("CACHE_SIZE", m3544a("VIDEO_CACHE"));
        bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE_SIZE, m3544a("VIDEO_CACHE_CNT"));
        bundle.putInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE, m3544a("V_DEC_CACHE_CNT"));
        bundle.putInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL, m3544a("AV_INTERVAL"));
        bundle.putString(TXLiveConstants.NET_STATUS_AUDIO_INFO, (String) this.f1962d.get("AUDIO_INFO"));
        bundle.putCharSequence("SERVER_IP", (String) this.f1962d.get("u32_server_ip"));
        bundle.putInt("NET_JITTER", m3544a("AUDIO_JITTER"));
        bundle.putInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL, m3544a("AV_NET_RECV_INTERVAL"));
        bundle.putFloat(TXLiveConstants.NET_STATUS_AUDIO_PLAY_SPEED, m3544a("AUDIO_SPEED"));
        int m3544a = m3544a("u32_fps") / 10;
        if (m3544a == 0) {
            m3544a = 15;
        }
        bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_GOP, (int) ((((m3544a(TXLiveConstants.NET_STATUS_VIDEO_GOP) * 10) / m3544a) / 10.0f) + 0.5d));
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m3550a(TXCAVProtocol.DownloadStats downloadStats, long j) {
        if (downloadStats == null || j == 0) {
            return;
        }
        String str = f1959c;
        TXCLog.m2913i(str, "updateNetStats: diff_a raw " + downloadStats.afterParseAudioBytes);
        String str2 = f1959c;
        TXCLog.m2913i(str2, "updateNetStats: diff_v raw " + downloadStats.afterParseVideoBytes);
        downloadStats.afterParseAudioBytes = (long) this.f1970l;
        downloadStats.afterParseVideoBytes = (long) this.f1971m;
        long m3543a = m3543a("u32_avg_audio_bitrate", downloadStats.afterParseAudioBytes);
        String str3 = f1959c;
        StringBuilder sb = new StringBuilder();
        sb.append("updateNetStats: kAvgAudioBitrate ");
        long j2 = 1024 * j;
        long j3 = ((m3543a * 8) * 1000) / j2;
        sb.append(Long.valueOf(j3));
        TXCLog.m2913i(str3, sb.toString());
        this.f1962d.put("u32_avg_audio_bitrate", Long.valueOf(j3));
        long m3543a2 = m3543a("u32_avg_video_bitrate", downloadStats.afterParseVideoBytes);
        String str4 = f1959c;
        TXCLog.m2913i(str4, "updateNetStats: diff_v " + m3543a2);
        String str5 = f1959c;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("updateNetStats: kAvgVideoBitrate ");
        long j4 = ((m3543a2 * 8) * 1000) / j2;
        sb2.append(Long.valueOf(j4));
        TXCLog.m2913i(str5, sb2.toString());
        this.f1962d.put("u32_avg_video_bitrate", Long.valueOf(j4));
        String str6 = f1959c;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("updateNetStats: kAvgNetSpeed ");
        long j5 = (((m3543a + m3543a2) * 8) * 1000) / j2;
        sb3.append(Long.valueOf(j5));
        Log.i(str6, sb3.toString());
        this.f1962d.put("u32_avg_net_speed", Long.valueOf(j5));
        this.f1962d.put("u32_server_ip", downloadStats.serverIP);
        this.f1962d.put("u32_dns_time", Long.valueOf(downloadStats.dnsTS));
        this.f1962d.put("u32_connect_server_time", Long.valueOf(downloadStats.connTS));
        this.f1962d.put("u64_timestamp", Long.valueOf(downloadStats.startTS));
        this.f1962d.put("u32_first_frame_down", Long.valueOf(downloadStats.firstVideoTS));
    }

    /* renamed from: a */
    private long m3543a(String str, long j) {
        if (!this.f1964f.containsKey(str)) {
            this.f1964f.put(str, 0L);
        }
        if (!this.f1963e.containsKey(str)) {
            this.f1963e.put(str, 0L);
        }
        if (((Long) this.f1963e.get(str)).longValue() > j) {
            HashMap hashMap = this.f1964f;
            hashMap.put(str, Long.valueOf(((Long) hashMap.get(str)).longValue() + j));
            this.f1963e.put(str, Long.valueOf(j));
            return j;
        }
        long longValue = j - ((Long) this.f1963e.get(str)).longValue();
        this.f1963e.put(str, Long.valueOf(j));
        return longValue;
    }

    /* renamed from: a */
    private int m3544a(String str) {
        Number number = (Number) this.f1962d.get(str);
        if (number != null) {
            return number.intValue();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m3551a(TXAudioJitterBufferReportInfo tXAudioJitterBufferReportInfo) {
        if (tXAudioJitterBufferReportInfo == null) {
            return;
        }
        int i = tXAudioJitterBufferReportInfo.mLoadCnt;
        this.f1962d.put("u32_avg_load", Long.valueOf(i == 0 ? 0L : tXAudioJitterBufferReportInfo.mLoadTime / i));
        this.f1962d.put("u32_load_cnt", Integer.valueOf(tXAudioJitterBufferReportInfo.mLoadCnt));
        this.f1962d.put("u32_max_load", Integer.valueOf(tXAudioJitterBufferReportInfo.mLoadMaxTime));
        this.f1962d.put("u32_speed_cnt", Integer.valueOf(tXAudioJitterBufferReportInfo.mSpeedCnt));
        this.f1962d.put("u32_nodata_cnt", Integer.valueOf(tXAudioJitterBufferReportInfo.mNoDataCnt));
    }
}
