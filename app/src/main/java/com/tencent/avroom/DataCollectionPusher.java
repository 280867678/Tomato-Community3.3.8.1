package com.tencent.avroom;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.tencent.liteav.TXCCaptureAndEnc;
import com.tencent.liteav.TXCDataReport;
import com.tencent.liteav.TXCLivePushConfig;
import com.tencent.liteav.avprotocol.TXCAVProtocol;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCModule;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/* renamed from: com.tencent.avroom.b */
/* loaded from: classes3.dex */
public class DataCollectionPusher extends TXCModule {

    /* renamed from: d */
    private static String f1983d = "DataCollectionPusher";

    /* renamed from: a */
    private Context f1984a;

    /* renamed from: b */
    private long f1985b;

    /* renamed from: e */
    private TXCDataReport f1987e;

    /* renamed from: f */
    private TXCAVRoomLisenter f1988f;

    /* renamed from: h */
    private Timer f1990h;

    /* renamed from: j */
    private WeakReference<TXCAVProtocol> f1992j;

    /* renamed from: c */
    private Handler f1986c = new Handler(Looper.getMainLooper());

    /* renamed from: g */
    private TimerTask f1989g = null;

    /* renamed from: i */
    private WeakReference<TXCCaptureAndEnc> f1991i = null;

    /* renamed from: k */
    private HashMap f1993k = new HashMap(100);

    /* renamed from: l */
    private HashMap f1994l = new HashMap(100);

    /* renamed from: m */
    private HashMap f1995m = new HashMap(100);

    public DataCollectionPusher(int i, long j, Context context, TXCLivePushConfig tXCLivePushConfig) {
        this.f1987e = null;
        this.f1984a = context.getApplicationContext();
        this.f1985b = j;
        setID("" + j);
        this.f1987e = new TXCDataReport(this.f1984a);
        this.f1987e.m2384a(tXCLivePushConfig.f4295c);
        this.f1987e.m2379b(tXCLivePushConfig.f4309q);
        this.f1987e.m2383a(tXCLivePushConfig.f4293a, tXCLivePushConfig.f4294b);
        this.f1987e.m2373d("" + j);
        TXCDataReport tXCDataReport = this.f1987e;
        tXCDataReport.m2382a("rtmp://0.livepush.myqcloud.com/live/" + ("" + i + "_" + j));
        TXCLog.m2911w(f1983d, "stream_id = " + j);
    }

    /* renamed from: a */
    public void m3523a(TXCAVRoomLisenter tXCAVRoomLisenter) {
        this.f1988f = tXCAVRoomLisenter;
    }

    /* renamed from: a */
    public void m3518a(TXCCaptureAndEnc tXCCaptureAndEnc) {
        this.f1991i = new WeakReference<>(tXCCaptureAndEnc);
    }

    /* renamed from: a */
    public void m3519a(TXCAVProtocol tXCAVProtocol) {
        this.f1992j = new WeakReference<>(tXCAVProtocol);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: d */
    public Bundle m3511d() {
        Bundle bundle = new Bundle();
        bundle.putLong("myid", this.f1985b);
        bundle.putCharSequence("CPU_USAGE", (((Long) this.f1993k.get("u32_app_cpu_usage")).intValue() / 10) + "/" + (((Long) this.f1993k.get("u32_cpu_usage")).intValue() / 10) + "%");
        bundle.putInt("VIDEO_WIDTH", m3517a("VIDEO_WIDTH"));
        bundle.putInt("VIDEO_HEIGHT", m3517a("VIDEO_HEIGHT"));
        bundle.putInt("NET_SPEED", m3517a("u32_avg_net_speed"));
        bundle.putInt("VIDEO_FPS", m3517a("u32_fps"));
        bundle.putInt("DROP_SIZE", m3517a("video_drop"));
        bundle.putInt("VIDEO_BITRATE", m3517a("u32_avg_video_bitrate"));
        bundle.putInt("AUDIO_BITRATE", m3517a("u32_avg_audio_bitrate"));
        bundle.putInt("CODEC_CACHE", m3517a("u32_avg_cache_size"));
        bundle.putString(TXLiveConstants.NET_STATUS_AUDIO_INFO, (String) this.f1993k.get("AUDIO_INFO"));
        bundle.putCharSequence("SERVER_IP", (String) this.f1993k.get("u32_server_ip"));
        bundle.putInt("qos_video_bitrate", m3517a("qos_video_bitrate"));
        int m3517a = m3517a("u32_fps");
        if (m3517a == 0) {
            m3517a = 15;
        }
        bundle.putInt(TXLiveConstants.NET_STATUS_VIDEO_GOP, (int) ((((m3517a(TXLiveConstants.NET_STATUS_VIDEO_GOP) * 10) / m3517a) / 10.0f) + 0.5d));
        return bundle;
    }

    /* renamed from: a */
    private int m3517a(String str) {
        Number number = (Number) this.f1993k.get(str);
        if (number != null) {
            return number.intValue();
        }
        return 0;
    }

    /* renamed from: a */
    public void m3524a() {
        TimerTask timerTask = this.f1989g;
        if (timerTask != null) {
            timerTask.cancel();
        }
        this.f1987e.m2385a();
        this.f1990h = new Timer(true);
        this.f1989g = new TimerTask() { // from class: com.tencent.avroom.b.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                TXCCaptureAndEnc tXCCaptureAndEnc;
                TXCAVProtocol tXCAVProtocol;
                if (DataCollectionPusher.this.f1992j != null && (tXCAVProtocol = (TXCAVProtocol) DataCollectionPusher.this.f1992j.get()) != null) {
                    TXCAVProtocol.UploadStats uploadStats = tXCAVProtocol.getUploadStats();
                    DataCollectionPusher.this.m3520a(uploadStats, 2000L);
                    DataCollectionPusher.this.setStatusValue(7012, String.valueOf(uploadStats.serverIP));
                }
                if (DataCollectionPusher.this.f1991i != null && (tXCCaptureAndEnc = (TXCCaptureAndEnc) DataCollectionPusher.this.f1991i.get()) != null) {
                    DataCollectionPusher.this.f1993k.put("VIDEO_WIDTH", Long.valueOf(tXCCaptureAndEnc.m2562b()));
                    DataCollectionPusher.this.f1993k.put("VIDEO_HEIGHT", Long.valueOf(tXCCaptureAndEnc.m2552c()));
                }
                if (DataCollectionPusher.this.f1991i != null && DataCollectionPusher.this.f1991i.get() != null) {
                    DataCollectionPusher.this.f1993k.put("AUDIO_INFO", ((TXCCaptureAndEnc) DataCollectionPusher.this.f1991i.get()).m2544d());
                    DataCollectionPusher.this.f1993k.put(TXLiveConstants.NET_STATUS_VIDEO_GOP, Integer.valueOf(TXCStatus.m2904d(((TXCCaptureAndEnc) DataCollectionPusher.this.f1991i.get()).getID(), 4003)));
                }
                int[] m2894a = TXCSystemUtil.m2894a();
                DataCollectionPusher.this.f1993k.put("u32_app_cpu_usage", Long.valueOf(m2894a[0]));
                DataCollectionPusher.this.f1993k.put("u32_cpu_usage", Long.valueOf(m2894a[1]));
                DataCollectionPusher.this.f1993k.put("u32_fps", Long.valueOf((long) TXCStatus.m2903e(DataCollectionPusher.this.getID(), 4001)));
                String str = DataCollectionPusher.f1983d;
                Log.i(str, "run: kAvgVideoBitrate" + Long.valueOf(TXCStatus.m2904d(DataCollectionPusher.this.getID(), 4002)));
                final Bundle m3511d = DataCollectionPusher.this.m3511d();
                DataCollectionPusher.this.f1986c.post(new Runnable() { // from class: com.tencent.avroom.b.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DataCollectionPusher.this.f1988f != null) {
                            DataCollectionPusher.this.f1988f.onAVRoomStatus(DataCollectionPusher.this.f1985b, m3511d);
                        }
                    }
                });
                DataCollectionPusher dataCollectionPusher = DataCollectionPusher.this;
                dataCollectionPusher.setStatusValue(7002, dataCollectionPusher.f1993k.get("u32_avg_audio_bitrate"));
                DataCollectionPusher dataCollectionPusher2 = DataCollectionPusher.this;
                dataCollectionPusher2.setStatusValue(7001, dataCollectionPusher2.f1993k.get("u32_avg_video_bitrate"));
                DataCollectionPusher dataCollectionPusher3 = DataCollectionPusher.this;
                dataCollectionPusher3.setStatusValue(7004, dataCollectionPusher3.f1993k.get("u32_avg_net_speed_audio"));
                DataCollectionPusher dataCollectionPusher4 = DataCollectionPusher.this;
                dataCollectionPusher4.setStatusValue(7003, dataCollectionPusher4.f1993k.get("u32_avg_net_speed_video"));
                DataCollectionPusher dataCollectionPusher5 = DataCollectionPusher.this;
                dataCollectionPusher5.setStatusValue(7005, dataCollectionPusher5.f1993k.get("u32_avg_cache_size"));
                DataCollectionPusher dataCollectionPusher6 = DataCollectionPusher.this;
                dataCollectionPusher6.setStatusValue(7007, dataCollectionPusher6.f1993k.get("video_drop"));
                DataCollectionPusher dataCollectionPusher7 = DataCollectionPusher.this;
                dataCollectionPusher7.setStatusValue(7007, dataCollectionPusher7.f1993k.get("video_drop"));
                if (DataCollectionPusher.this.f1987e != null) {
                    DataCollectionPusher.this.f1987e.m2372e();
                }
            }
        };
        this.f1990h.schedule(this.f1989g, 0L, 2000L);
    }

    /* renamed from: b */
    public void m3515b() {
        Timer timer = this.f1990h;
        if (timer != null) {
            timer.cancel();
            this.f1990h = null;
        }
        TXCDataReport tXCDataReport = this.f1987e;
        if (tXCDataReport != null) {
            tXCDataReport.m2380b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m3520a(TXCAVProtocol.UploadStats uploadStats, long j) {
        if (uploadStats == null || j == 0) {
            return;
        }
        long j2 = j * 1024;
        this.f1993k.put("u32_avg_video_bitrate", Long.valueOf(((m3516a("u32_avg_video_bitrate", uploadStats.inVideoBytes) * 8) * 1000) / j2));
        this.f1993k.put("u32_avg_audio_bitrate", Long.valueOf(((m3516a("u32_avg_audio_bitrate", uploadStats.inAudioBytes) * 8) * 1000) / j2));
        long m3516a = m3516a("VIDEO_BITRATE", uploadStats.outVideoBytes);
        long m3516a2 = m3516a("AUDIO_BITRATE", uploadStats.outAudioBytes);
        this.f1993k.put("u32_avg_net_speed_video", Long.valueOf(((m3516a * 8) * 1000) / j2));
        this.f1993k.put("u32_avg_net_speed_audio", Long.valueOf(((m3516a2 * 8) * 1000) / j2));
        this.f1993k.put("u32_avg_net_speed", Long.valueOf((((m3516a2 + m3516a) * 8) * 1000) / j2));
        this.f1993k.put("u32_avg_cache_size", Long.valueOf(uploadStats.videoCacheLen));
        this.f1993k.put("video_drop", Long.valueOf(uploadStats.videoDropCount));
        this.f1993k.put("u32_server_ip", uploadStats.serverIP);
        long j3 = uploadStats.dnsTS;
        if (j3 != -1) {
            this.f1993k.put("u32_dns_time", Long.valueOf(j3 - uploadStats.startTS));
        } else {
            this.f1993k.put("u32_dns_time", Long.valueOf(j3));
        }
        long j4 = uploadStats.connTS;
        if (j4 != -1) {
            this.f1993k.put("u32_connect_server_time", Long.valueOf(j4 - uploadStats.startTS));
        } else {
            this.f1993k.put("u32_connect_server_time", Long.valueOf(j4));
        }
        this.f1993k.put("u32_channel_type", Long.valueOf(uploadStats.channelType));
        this.f1993k.put("u64_timestamp", Long.valueOf(uploadStats.startTS));
    }

    /* renamed from: a */
    private long m3516a(String str, long j) {
        if (!this.f1995m.containsKey(str)) {
            this.f1995m.put(str, 0L);
        }
        if (!this.f1994l.containsKey(str)) {
            this.f1994l.put(str, 0L);
        }
        if (((Long) this.f1994l.get(str)).longValue() > j) {
            HashMap hashMap = this.f1995m;
            hashMap.put(str, Long.valueOf(((Long) hashMap.get(str)).longValue() + j));
            this.f1994l.put(str, Long.valueOf(j));
            return j;
        }
        long longValue = j - ((Long) this.f1994l.get(str)).longValue();
        this.f1994l.put(str, Long.valueOf(j));
        return longValue;
    }
}
