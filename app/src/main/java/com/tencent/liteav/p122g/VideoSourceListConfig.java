package com.tencent.liteav.p122g;

import android.media.MediaFormat;
import android.os.Build;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.ugc.TXRecordCommon;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.g.t */
/* loaded from: classes3.dex */
public class VideoSourceListConfig {

    /* renamed from: a */
    private static VideoSourceListConfig f4274a;

    /* renamed from: d */
    private int f4277d = 0;

    /* renamed from: e */
    private int f4278e = 0;

    /* renamed from: c */
    private ArrayList<String> f4276c = new ArrayList<>();

    /* renamed from: b */
    private final ArrayList<VideoExtractConfig> f4275b = new ArrayList<>();

    /* renamed from: a */
    public static VideoSourceListConfig m1480a() {
        if (f4274a == null) {
            f4274a = new VideoSourceListConfig();
        }
        return f4274a;
    }

    private VideoSourceListConfig() {
    }

    /* renamed from: a */
    public void m1479a(List<String> list) {
        this.f4275b.clear();
        this.f4277d = 0;
        this.f4276c.clear();
        this.f4276c.addAll(list);
    }

    /* renamed from: b */
    public int m1478b() {
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= this.f4276c.size()) {
                break;
            }
            String str = this.f4276c.get(i);
            VideoExtractConfig videoExtractConfig = new VideoExtractConfig();
            videoExtractConfig.m1701a(str);
            int m1700b = videoExtractConfig.m1700b();
            this.f4275b.add(videoExtractConfig);
            if (m1700b != 0) {
                TXCLog.m2913i("VideoSourceListConfig", "checkLegality source:" + str + " is illegal");
                i2 = m1700b;
                break;
            }
            i++;
            i2 = m1700b;
        }
        if (i2 != 0) {
            int size = this.f4275b.size();
            for (int i3 = 0; i3 < size; i3++) {
                this.f4275b.get(i3).m1703a();
            }
        }
        return i2;
    }

    /* renamed from: c */
    public List<VideoExtractConfig> m1477c() {
        return this.f4275b;
    }

    /* renamed from: d */
    public VideoExtractConfig m1476d() {
        TXCLog.m2913i("VideoSourceListConfig", "getCurrentVideoExtractConfig mCurrentVideoIndex:" + this.f4277d);
        return this.f4275b.get(this.f4277d);
    }

    /* renamed from: e */
    public VideoExtractConfig m1475e() {
        TXCLog.m2913i("VideoSourceListConfig", "getCurrentAudioExtractConfig mCurrentAudioIndex:" + this.f4278e);
        return this.f4275b.get(this.f4278e);
    }

    /* renamed from: f */
    public boolean m1474f() {
        this.f4277d++;
        TXCLog.m2913i("VideoSourceListConfig", "nextVideo mCurrentVideoIndex:" + this.f4277d);
        if (this.f4277d >= this.f4275b.size()) {
            TXCLog.m2913i("VideoSourceListConfig", "nextVideo get fail");
            return false;
        }
        TXCLog.m2913i("VideoSourceListConfig", "nextVideo get succ");
        return true;
    }

    /* renamed from: g */
    public MediaFormat m1473g() {
        int i;
        int i2 = 0;
        if (Build.VERSION.SDK_INT >= 16) {
            i = 0;
            int i3 = 0;
            while (i2 < this.f4275b.size()) {
                MediaFormat m1695f = this.f4275b.get(i2).m1695f();
                if (m1695f != null) {
                    int integer = m1695f.getInteger("sample-rate");
                    int integer2 = m1695f.getInteger("channel-count");
                    if (integer > i3) {
                        i3 = integer;
                    }
                    if (integer2 > i) {
                        i = integer2;
                    }
                }
                i2++;
            }
            i2 = i3;
        } else {
            i = 0;
        }
        if (i == 0) {
            i = 2;
        }
        if (i2 == 0) {
            i2 = TXRecordCommon.AUDIO_SAMPLERATE_48000;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return MediaFormat.createAudioFormat("audio/mp4a-latm", i2, i);
        }
        return null;
    }

    /* renamed from: h */
    public boolean m1472h() {
        if (Build.VERSION.SDK_INT >= 16) {
            boolean z = true;
            for (int i = 0; i < this.f4275b.size(); i++) {
                VideoExtractConfig videoExtractConfig = this.f4275b.get(i);
                MediaFormat m1696e = videoExtractConfig.m1696e();
                int integer = m1696e.getInteger("width");
                int integer2 = m1696e.getInteger("height");
                int m1694g = videoExtractConfig.m1694g();
                if (m1694g == 0 || m1694g == 180) {
                    if (integer2 <= integer) {
                    }
                    z = false;
                } else {
                    if (integer <= integer2) {
                    }
                    z = false;
                }
            }
            return z;
        }
        return true;
    }

    /* renamed from: i */
    public boolean m1471i() {
        this.f4278e++;
        TXCLog.m2913i("VideoSourceListConfig", "nextAudio mCurrentAudioIndex:" + this.f4278e);
        if (this.f4278e >= this.f4275b.size()) {
            TXCLog.m2913i("VideoSourceListConfig", "nextAudio get fail");
            return false;
        }
        TXCLog.m2913i("VideoSourceListConfig", "nextAudio get succ");
        return true;
    }

    /* renamed from: j */
    public boolean m1470j() {
        return this.f4277d == this.f4275b.size() - 1;
    }

    /* renamed from: k */
    public boolean m1469k() {
        return this.f4278e == this.f4275b.size() - 1;
    }

    /* renamed from: l */
    public void m1468l() {
        this.f4277d = 0;
        this.f4278e = 0;
    }

    /* renamed from: m */
    public long m1467m() {
        long j = 0;
        if (Build.VERSION.SDK_INT >= 16) {
            for (int i = 0; i < this.f4275b.size(); i++) {
                j += this.f4275b.get(i).m1696e().getLong("durationUs");
            }
        }
        return j;
    }

    /* renamed from: n */
    public long m1466n() {
        if (Build.VERSION.SDK_INT >= 16) {
            long j = 0;
            for (int i = 0; i < this.f4275b.size(); i++) {
                long j2 = this.f4275b.get(i).m1696e().getLong("durationUs");
                if (j == 0) {
                    j = j2;
                }
                if (j > j2) {
                    j = j2;
                }
            }
            return j;
        }
        return 0L;
    }
}
