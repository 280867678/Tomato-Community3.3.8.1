package com.tencent.liteav.p122g;

import android.media.MediaFormat;
import android.os.Build;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p119d.Resolution;

/* renamed from: com.tencent.liteav.g.s */
/* loaded from: classes3.dex */
public class VideoOutputListConfig extends VideoOutputConfig {

    /* renamed from: v */
    private static VideoOutputListConfig f4271v;

    /* renamed from: t */
    public int f4272t;

    /* renamed from: u */
    public int f4273u;

    /* renamed from: r */
    public static VideoOutputListConfig m1481r() {
        if (f4271v == null) {
            f4271v = new VideoOutputListConfig();
        }
        return f4271v;
    }

    private VideoOutputListConfig() {
    }

    /* renamed from: a */
    public Resolution m1484a(boolean z) {
        Resolution resolution = new Resolution();
        resolution.f3469c = 0;
        int i = this.f4273u;
        if (i == 0) {
            resolution.f3467a = 360;
            resolution.f3468b = 640;
        } else if (i == 1) {
            resolution.f3467a = 480;
            resolution.f3468b = 640;
        } else if (i == 2) {
            resolution.f3467a = 540;
            resolution.f3468b = 960;
        } else if (i == 3) {
            resolution.f3467a = 720;
            resolution.f3468b = 1280;
        }
        return z ? m1482f(resolution) : resolution;
    }

    /* renamed from: f */
    private Resolution m1482f(Resolution resolution) {
        int i = resolution.f3468b;
        resolution.f3468b = resolution.f3467a;
        resolution.f3467a = i;
        return resolution;
    }

    /* renamed from: d */
    public void m1483d(MediaFormat mediaFormat) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.f3363a = mediaFormat.getInteger("sample-rate");
            this.f3364b = mediaFormat.getInteger("channel-count");
        }
    }
}
