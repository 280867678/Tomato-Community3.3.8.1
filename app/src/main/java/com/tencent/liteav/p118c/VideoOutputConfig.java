package com.tencent.liteav.p118c;

import android.media.MediaFormat;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.AudioFormat;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.ugc.TXRecordCommon;
import java.io.File;
import java.io.IOException;

/* renamed from: com.tencent.liteav.c.i */
/* loaded from: classes3.dex */
public class VideoOutputConfig {

    /* renamed from: t */
    private static VideoOutputConfig f3362t;

    /* renamed from: c */
    public int f3365c;

    /* renamed from: h */
    public Resolution f3370h;

    /* renamed from: i */
    public String f3371i;

    /* renamed from: j */
    public int f3372j;

    /* renamed from: k */
    public long f3373k;

    /* renamed from: l */
    public long f3374l;

    /* renamed from: m */
    public boolean f3375m;

    /* renamed from: o */
    public String f3377o;

    /* renamed from: p */
    public int f3378p;

    /* renamed from: q */
    public int f3379q;

    /* renamed from: r */
    public boolean f3380r;

    /* renamed from: s */
    public int f3381s;

    /* renamed from: u */
    private MediaFormat f3382u;

    /* renamed from: v */
    private MediaFormat f3383v;

    /* renamed from: x */
    private MediaFormat f3385x;

    /* renamed from: w */
    private int f3384w = 0;

    /* renamed from: f */
    public int f3368f = 5000;

    /* renamed from: g */
    public int f3369g = 20;

    /* renamed from: e */
    public int f3367e = 3;

    /* renamed from: a */
    public int f3363a = TXRecordCommon.AUDIO_SAMPLERATE_48000;

    /* renamed from: b */
    public int f3364b = 1;

    /* renamed from: d */
    public int f3366d = 98304;

    /* renamed from: n */
    public boolean f3376n = true;

    /* renamed from: a */
    public static VideoOutputConfig m2457a() {
        if (f3362t == null) {
            f3362t = new VideoOutputConfig();
        }
        return f3362t;
    }

    /* renamed from: b */
    public boolean m2453b() {
        return TextUtils.isEmpty(this.f3371i);
    }

    /* renamed from: c */
    public boolean m2450c() {
        return new File(this.f3371i).exists();
    }

    /* renamed from: d */
    public boolean m2447d() {
        return TextUtils.isEmpty(this.f3377o);
    }

    /* renamed from: e */
    public boolean m2445e() {
        return this.f3380r && this.f3375m;
    }

    /* renamed from: f */
    public void m2443f() {
        if (m2447d()) {
            return;
        }
        File file = new File(this.f3377o);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: g */
    public void m2441g() {
        if (m2453b()) {
            return;
        }
        File file = new File(this.f3371i);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: h */
    public int m2439h() {
        int i = this.f3379q;
        return i == 0 ? this.f3366d : i;
    }

    /* renamed from: i */
    public int m2437i() {
        if (this.f3375m) {
            Resolution resolution = this.f3370h;
            if (resolution.f3467a < 1280 && resolution.f3468b < 1280) {
                this.f3368f = 24000;
            } else {
                this.f3368f = 15000;
            }
        } else {
            int i = this.f3378p;
            if (i != 0) {
                this.f3368f = i;
            } else {
                int i2 = this.f3372j;
                if (i2 == 0 || i2 == 1) {
                    this.f3368f = 2400;
                } else if (i2 == 2) {
                    this.f3368f = 6500;
                } else if (i2 == 3) {
                    this.f3368f = 9600;
                }
            }
        }
        return this.f3368f;
    }

    /* renamed from: j */
    public int m2435j() {
        try {
            if (this.f3385x != null && Build.VERSION.SDK_INT >= 16) {
                this.f3369g = this.f3385x.getInteger("frame-rate");
            }
        } catch (NullPointerException unused) {
            this.f3369g = 20;
        }
        return this.f3369g;
    }

    /* renamed from: k */
    public int m2434k() {
        try {
            if (this.f3385x != null && Build.VERSION.SDK_INT >= 16) {
                this.f3367e = this.f3385x.getInteger("i-frame-interval");
            }
        } catch (NullPointerException unused) {
            this.f3367e = 3;
        }
        return this.f3367e;
    }

    /* renamed from: a */
    public Resolution m2454a(Resolution resolution) {
        if (resolution.f3467a == 0 || resolution.f3468b == 0) {
            return resolution;
        }
        if (VideoSourceConfig.m2416a().m2411d() == 2) {
            int i = this.f3372j;
            if (i == 0) {
                resolution = m2436i(resolution);
            } else if (i == 1) {
                resolution = m2438h(resolution);
            } else if (i == 2) {
                resolution = m2440g(resolution);
            } else if (i == 3) {
                resolution = m2442f(resolution);
            }
        } else {
            int i2 = this.f3372j;
            if (i2 == 0) {
                resolution = m2451b(resolution);
            } else if (i2 == 1) {
                resolution = m2448c(resolution);
            } else if (i2 == 2) {
                resolution = m2446d(resolution);
            } else if (i2 == 3) {
                resolution = m2444e(resolution);
            }
        }
        Resolution resolution2 = new Resolution();
        resolution2.f3469c = resolution.f3469c;
        int m2419e = VideoPreProcessConfig.m2427a().m2419e();
        if (m2419e == 90 || m2419e == 270) {
            resolution2.f3467a = ((resolution.f3468b + 15) / 16) * 16;
            resolution2.f3468b = ((resolution.f3467a + 15) / 16) * 16;
        } else {
            resolution2.f3467a = ((resolution.f3467a + 15) / 16) * 16;
            resolution2.f3468b = ((resolution.f3468b + 15) / 16) * 16;
        }
        return resolution2;
    }

    /* renamed from: f */
    private Resolution m2442f(Resolution resolution) {
        int i;
        int i2;
        Resolution resolution2 = new Resolution();
        int i3 = resolution.f3467a;
        int i4 = resolution.f3468b;
        if (i3 / i4 >= 0.5625f) {
            i = 720;
            i2 = (int) ((i4 * 720.0f) / i3);
        } else {
            i = (int) ((i3 * 1280.0f) / i4);
            i2 = 1280;
        }
        resolution2.f3467a = ((i + 15) / 16) * 16;
        resolution2.f3468b = ((i2 + 15) / 16) * 16;
        return resolution2;
    }

    /* renamed from: g */
    private Resolution m2440g(Resolution resolution) {
        int i;
        int i2;
        Resolution resolution2 = new Resolution();
        int i3 = resolution.f3467a;
        int i4 = resolution.f3468b;
        if (i3 / i4 >= 0.5625f) {
            i = 540;
            i2 = (int) ((i4 * 540.0f) / i3);
        } else {
            i = (int) ((i3 * 960.0f) / i4);
            i2 = 960;
        }
        resolution2.f3467a = ((i + 15) / 16) * 16;
        resolution2.f3468b = ((i2 + 15) / 16) * 16;
        return resolution2;
    }

    /* renamed from: h */
    private Resolution m2438h(Resolution resolution) {
        int i;
        int i2;
        Resolution resolution2 = new Resolution();
        int i3 = resolution.f3467a;
        int i4 = resolution.f3468b;
        if (i3 / i4 >= 0.5625f) {
            i = 360;
            i2 = (int) ((i4 * 360.0f) / i3);
        } else {
            i = (int) ((i3 * 640.0f) / i4);
            i2 = 640;
        }
        resolution2.f3467a = ((i + 15) / 16) * 16;
        resolution2.f3468b = ((i2 + 15) / 16) * 16;
        return resolution2;
    }

    /* renamed from: i */
    private Resolution m2436i(Resolution resolution) {
        int i;
        int i2;
        Resolution resolution2 = new Resolution();
        int i3 = resolution.f3467a;
        int i4 = resolution.f3468b;
        if (i3 / i4 >= 0.5625f) {
            i = 720;
            i2 = (int) ((i4 * 720.0f) / i3);
        } else {
            i = (int) ((i3 * 1280.0f) / i4);
            i2 = 1280;
        }
        resolution2.f3467a = ((i + 15) / 16) * 16;
        resolution2.f3468b = ((i2 + 15) / 16) * 16;
        return resolution2;
    }

    /* renamed from: b */
    protected Resolution m2451b(Resolution resolution) {
        int i;
        Resolution resolution2 = new Resolution();
        if ((resolution.f3467a <= 640 && resolution.f3468b <= 360) || (resolution.f3467a <= 360 && resolution.f3468b <= 640)) {
            return m2456a(resolution.f3469c, resolution);
        }
        int i2 = resolution.f3467a;
        int i3 = resolution.f3468b;
        float f = (i2 * 1.0f) / i3;
        if (i2 >= i3) {
            i = (int) (360.0f * f);
            if (i >= 640) {
                i = 640;
            }
        } else {
            i = (int) (640.0f * f);
            if (i >= 360) {
                i = 360;
            }
        }
        resolution2.f3467a = ((i + 1) >> 1) << 1;
        resolution2.f3468b = ((((int) (i / f)) + 1) >> 1) << 1;
        return m2456a(resolution.f3469c, resolution2);
    }

    /* renamed from: c */
    protected Resolution m2448c(Resolution resolution) {
        int i;
        Resolution resolution2 = new Resolution();
        if ((resolution.f3467a <= 640 && resolution.f3468b <= 480) || (resolution.f3467a <= 480 && resolution.f3468b <= 640)) {
            return m2456a(resolution.f3469c, resolution);
        }
        int i2 = resolution.f3467a;
        int i3 = resolution.f3468b;
        float f = (i2 * 1.0f) / i3;
        if (i2 >= i3) {
            i = (int) (480.0f * f);
            if (i >= 640) {
                i = 640;
            }
        } else {
            i = (int) (640.0f * f);
            if (i >= 480) {
                i = 480;
            }
        }
        resolution2.f3467a = ((i + 1) >> 1) << 1;
        resolution2.f3468b = ((((int) (i / f)) + 1) >> 1) << 1;
        return m2456a(resolution.f3469c, resolution2);
    }

    /* renamed from: d */
    protected Resolution m2446d(Resolution resolution) {
        int i;
        Resolution resolution2 = new Resolution();
        if ((resolution.f3467a <= 960 && resolution.f3468b <= 544) || (resolution.f3467a <= 544 && resolution.f3468b <= 960)) {
            return m2456a(resolution.f3469c, resolution);
        }
        int i2 = resolution.f3467a;
        int i3 = resolution.f3468b;
        float f = (i2 * 1.0f) / i3;
        if (i2 >= i3) {
            i = (int) (544.0f * f);
            if (i >= 960) {
                i = 960;
            }
        } else {
            i = (int) (960.0f * f);
            if (i >= 544) {
                i = 544;
            }
        }
        resolution2.f3467a = ((i + 1) >> 1) << 1;
        resolution2.f3468b = ((((int) (i / f)) + 1) >> 1) << 1;
        return m2456a(resolution.f3469c, resolution2);
    }

    /* renamed from: e */
    protected Resolution m2444e(Resolution resolution) {
        int i;
        Resolution resolution2 = new Resolution();
        if ((resolution.f3467a <= 1280 && resolution.f3468b <= 720) || (resolution.f3467a <= 720 && resolution.f3468b <= 1280)) {
            return m2456a(resolution.f3469c, resolution);
        }
        int i2 = resolution.f3467a;
        int i3 = resolution.f3468b;
        float f = (i2 * 1.0f) / i3;
        if (i2 >= i3) {
            i = (int) (720.0f * f);
            if (i >= 1280) {
                i = 1280;
            }
        } else {
            i = (int) (1280.0f * f);
            if (i >= 720) {
                i = 720;
            }
        }
        resolution2.f3467a = ((i + 1) >> 1) << 1;
        resolution2.f3468b = ((((int) (i / f)) + 1) >> 1) << 1;
        return m2456a(resolution.f3469c, resolution2);
    }

    /* renamed from: a */
    private Resolution m2456a(int i, Resolution resolution) {
        if (i == 90 || i == 270) {
            int i2 = resolution.f3467a;
            resolution.f3467a = resolution.f3468b;
            resolution.f3468b = i2;
        }
        return resolution;
    }

    /* renamed from: a */
    public void m2455a(MediaFormat mediaFormat) {
        this.f3382u = mediaFormat;
    }

    /* renamed from: b */
    public void m2452b(MediaFormat mediaFormat) {
        this.f3385x = mediaFormat;
    }

    /* renamed from: c */
    public void m2449c(MediaFormat mediaFormat) {
        this.f3383v = mediaFormat;
    }

    /* renamed from: l */
    public boolean m2433l() {
        return (this.f3382u == null && this.f3383v == null) ? false : true;
    }

    /* renamed from: m */
    public boolean m2432m() {
        return this.f3382u == null && this.f3383v != null;
    }

    /* renamed from: n */
    public MediaFormat m2431n() {
        AudioFormat audioFormat = new AudioFormat();
        MediaFormat mediaFormat = this.f3383v;
        MediaFormat mediaFormat2 = null;
        if (mediaFormat == null) {
            MediaFormat mediaFormat3 = this.f3382u;
            if (mediaFormat3 == null) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                audioFormat.f3455b = mediaFormat3.getInteger("sample-rate");
                audioFormat.f3454a = this.f3382u.getInteger("channel-count");
                if (this.f3382u.containsKey("bitrate")) {
                    audioFormat.f3456c = this.f3382u.getInteger("bitrate");
                }
            }
        } else if (Build.VERSION.SDK_INT >= 16) {
            MediaFormat mediaFormat4 = this.f3382u;
            if (mediaFormat4 == null) {
                int integer = mediaFormat.getInteger("sample-rate");
                int integer2 = this.f3383v.getInteger("channel-count");
                audioFormat.f3455b = integer;
                audioFormat.f3454a = integer2;
                if (this.f3383v.containsKey("bitrate")) {
                    audioFormat.f3456c = this.f3383v.getInteger("bitrate");
                }
            } else {
                mediaFormat4.getInteger("sample-rate");
                audioFormat.f3455b = this.f3383v.getInteger("sample-rate");
                int integer3 = this.f3382u.getInteger("channel-count");
                int integer4 = this.f3383v.getInteger("channel-count");
                if (integer3 < integer4) {
                    integer3 = integer4;
                }
                audioFormat.f3454a = integer3;
                if (this.f3382u.containsKey("bitrate")) {
                    audioFormat.f3456c = this.f3382u.getInteger("bitrate");
                }
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            mediaFormat2 = MediaFormat.createAudioFormat("audio/mp4a-latm", audioFormat.f3455b, audioFormat.f3454a);
            int i = audioFormat.f3456c;
            if (i != 0) {
                mediaFormat2.setInteger("bitrate", i);
            }
        }
        MediaFormat mediaFormat5 = this.f3382u;
        if (mediaFormat5 != null && Build.VERSION.SDK_INT >= 16 && mediaFormat5.containsKey("max-input-size")) {
            this.f3365c = this.f3382u.getInteger("max-input-size");
        }
        this.f3363a = audioFormat.f3455b;
        this.f3364b = audioFormat.f3454a;
        int i2 = audioFormat.f3456c;
        if (i2 != 0) {
            this.f3366d = i2;
        }
        return mediaFormat2;
    }

    /* renamed from: o */
    public void m2430o() {
        if (!TextUtils.isEmpty(this.f3377o)) {
            File file = new File(this.f3377o);
            if (file.exists()) {
                boolean delete = file.delete();
                TXCLog.m2913i("VideoOutputConfig", "clear delete process path:" + delete);
            }
        }
        this.f3373k = 0L;
        this.f3377o = null;
        this.f3371i = null;
        this.f3383v = null;
        this.f3382u = null;
        this.f3378p = 0;
        this.f3379q = 0;
        this.f3376n = true;
    }

    /* renamed from: p */
    public int m2429p() {
        if (TextUtils.isEmpty(this.f3371i)) {
            return 0;
        }
        return (int) (new File(this.f3371i).length() / 1024);
    }

    /* renamed from: q */
    public int m2428q() {
        return Math.round((float) ((this.f3373k / 1000) / 1000));
    }
}
