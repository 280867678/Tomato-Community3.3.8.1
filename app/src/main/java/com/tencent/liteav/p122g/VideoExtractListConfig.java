package com.tencent.liteav.p122g;

import com.tencent.liteav.basic.log.TXCLog;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.g.j */
/* loaded from: classes3.dex */
public class VideoExtractListConfig {

    /* renamed from: b */
    private int f4101b = 0;

    /* renamed from: c */
    private int f4102c = 0;

    /* renamed from: a */
    private List<VideoExtractConfig> f4100a = new ArrayList();

    /* renamed from: a */
    public List<VideoExtractConfig> m1680a() {
        TXCLog.m2913i("VideoExtractListConfig", "getAllVideoExtractConfig mVideoExtractConfigList:" + this.f4100a);
        return this.f4100a;
    }

    /* renamed from: b */
    public VideoExtractConfig m1678b() {
        TXCLog.m2913i("VideoExtractListConfig", "getCurrentVideoExtractConfig mCurrentVideoIndex:" + this.f4101b);
        return this.f4100a.get(this.f4101b);
    }

    /* renamed from: c */
    public VideoExtractConfig m1677c() {
        TXCLog.m2913i("VideoExtractListConfig", "getCurrentAudioExtractConfig mCurrentAudioIndex:" + this.f4102c);
        return this.f4100a.get(this.f4102c);
    }

    /* renamed from: d */
    public boolean m1676d() {
        this.f4101b++;
        TXCLog.m2913i("VideoExtractListConfig", "nextVideo mCurrentVideoIndex:" + this.f4101b);
        if (this.f4101b >= this.f4100a.size()) {
            TXCLog.m2913i("VideoExtractListConfig", "nextVideo get fail");
            return false;
        }
        TXCLog.m2913i("VideoExtractListConfig", "nextVideo get succ");
        return true;
    }

    /* renamed from: e */
    public boolean m1675e() {
        this.f4102c++;
        TXCLog.m2913i("VideoExtractListConfig", "nextAudio mCurrentAudioIndex:" + this.f4102c);
        if (this.f4102c >= this.f4100a.size()) {
            TXCLog.m2913i("VideoExtractListConfig", "nextAudio get fail");
            return false;
        }
        TXCLog.m2913i("VideoExtractListConfig", "nextAudio get succ");
        return true;
    }

    /* renamed from: f */
    public boolean m1674f() {
        return this.f4101b == this.f4100a.size() - 1;
    }

    /* renamed from: g */
    public boolean m1673g() {
        return this.f4102c == this.f4100a.size() - 1;
    }

    /* renamed from: h */
    public void m1672h() {
        this.f4101b = 0;
        this.f4102c = 0;
    }

    /* renamed from: a */
    public void m1679a(List<VideoExtractConfig> list) {
        for (int i = 0; i < list.size(); i++) {
            VideoExtractConfig videoExtractConfig = new VideoExtractConfig();
            videoExtractConfig.f4089a = list.get(i).f4089a;
            videoExtractConfig.m1701a(videoExtractConfig.f4089a);
            videoExtractConfig.m1700b();
            this.f4100a.add(videoExtractConfig);
        }
    }
}
