package com.tencent.liteav.p122g;

import android.annotation.TargetApi;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.videoediter.ffmpeg.TXFFMediaRetriever;
import com.tencent.ugc.TXVideoEditConstants;
import java.io.IOException;

@TargetApi(16)
/* renamed from: com.tencent.liteav.g.e */
/* loaded from: classes3.dex */
public class MediaExtractorWrapper {

    /* renamed from: g */
    private static int f4060g;

    /* renamed from: h */
    private static int f4061h;

    /* renamed from: a */
    private TXFFMediaRetriever f4062a;

    /* renamed from: b */
    private MediaExtractor f4063b;

    /* renamed from: c */
    private MediaExtractor f4064c;

    /* renamed from: d */
    private MediaFormat f4065d;

    /* renamed from: e */
    private MediaFormat f4066e;

    /* renamed from: f */
    private long f4067f;

    /* renamed from: i */
    private int f4068i;

    /* renamed from: j */
    private long f4069j;

    /* renamed from: k */
    private String f4070k;

    /* renamed from: l */
    private boolean f4071l;

    /* renamed from: m */
    private int f4072m;

    /* renamed from: n */
    private int f4073n;

    /* renamed from: o */
    private int f4074o;

    /* renamed from: p */
    private int f4075p;

    /* renamed from: q */
    private int f4076q;

    /* renamed from: r */
    private int f4077r;

    public MediaExtractorWrapper() {
        this.f4067f = -1L;
        this.f4071l = false;
        this.f4062a = new TXFFMediaRetriever();
    }

    public MediaExtractorWrapper(boolean z) {
        this.f4067f = -1L;
        this.f4071l = z;
        this.f4062a = new TXFFMediaRetriever();
    }

    /* renamed from: a */
    public int m1744a(String str) throws IOException {
        this.f4070k = str;
        MediaExtractor mediaExtractor = this.f4063b;
        if (mediaExtractor != null) {
            mediaExtractor.release();
        }
        MediaExtractor mediaExtractor2 = this.f4064c;
        if (mediaExtractor2 != null) {
            mediaExtractor2.release();
        }
        if (this.f4071l) {
            this.f4064c = new MediaExtractor();
            this.f4064c.setDataSource(str);
        } else {
            this.f4064c = new MediaExtractor();
            this.f4063b = new MediaExtractor();
            this.f4063b.setDataSource(str);
            this.f4064c.setDataSource(str);
        }
        this.f4062a.m498a(str);
        return m1721s();
    }

    /* renamed from: s */
    private int m1721s() {
        int trackCount = this.f4064c.getTrackCount();
        if (trackCount == 0) {
            TXCLog.m2913i("MediaExtractorWrapper", "prepareMediaFileInfo count == 0");
            return TXVideoEditConstants.ERR_UNSUPPORT_VIDEO_FORMAT;
        }
        TXCLog.m2913i("MediaExtractorWrapper", " trackCount = " + trackCount);
        for (int i = 0; i < trackCount; i++) {
            MediaFormat trackFormat = this.f4064c.getTrackFormat(i);
            TXCLog.m2913i("MediaExtractorWrapper", "prepareMediaFileInfo :" + trackFormat.toString());
            String string = trackFormat.getString("mime");
            if (string.startsWith("video")) {
                f4060g = i;
                this.f4065d = trackFormat;
                MediaExtractor mediaExtractor = this.f4063b;
                if (mediaExtractor != null) {
                    mediaExtractor.selectTrack(i);
                }
            } else if (string.startsWith("audio")) {
                f4061h = i;
                this.f4066e = trackFormat;
                this.f4064c.selectTrack(i);
                int integer = trackFormat.getInteger("channel-count");
                if (integer > 2 || integer < 1) {
                    return -1004;
                }
            } else {
                continue;
            }
        }
        this.f4068i = m1733g();
        if (this.f4065d != null) {
            int m1743b = m1743b();
            int m1740c = m1740c();
            if ((m1743b > m1740c ? m1740c : m1743b) > 1080) {
                TXCLog.m2913i("MediaExtractorWrapper", "prepareMediaFileInfo W:" + m1743b + ",H:" + m1740c);
            }
        }
        return 0;
    }

    /* renamed from: a */
    public long m1747a() {
        MediaFormat mediaFormat = this.f4065d;
        if (mediaFormat == null) {
            return 0L;
        }
        if (this.f4066e == null) {
            try {
                if (this.f4069j == 0) {
                    this.f4069j = mediaFormat.getLong("durationUs");
                    TXCLog.m2915d("MediaExtractorWrapper", "mDuration = " + this.f4069j);
                }
                return this.f4069j;
            } catch (NullPointerException unused) {
                TXCLog.m2915d("MediaExtractorWrapper", "空指针异常");
                return 0L;
            }
        }
        try {
            if (this.f4069j == 0) {
                long j = mediaFormat.getLong("durationUs");
                long j2 = this.f4066e.getLong("durationUs");
                if (j <= j2) {
                    j = j2;
                }
                this.f4069j = j;
                TXCLog.m2915d("MediaExtractorWrapper", "mDuration = " + this.f4069j);
            }
            return this.f4069j;
        } catch (NullPointerException unused2) {
            TXCLog.m2915d("MediaExtractorWrapper", "空指针异常");
            return 0L;
        }
    }

    /* renamed from: b */
    public int m1743b() {
        int i = this.f4077r;
        if (i != 0) {
            return i;
        }
        try {
            if (this.f4065d == null) {
                return 0;
            }
            this.f4077r = this.f4065d.getInteger("width");
            return this.f4077r;
        } catch (NullPointerException unused) {
            return 0;
        }
    }

    /* renamed from: c */
    public int m1740c() {
        int i = this.f4076q;
        if (i != 0) {
            return i;
        }
        try {
            if (this.f4065d == null) {
                return 0;
            }
            this.f4076q = this.f4065d.getInteger("height");
            return this.f4076q;
        } catch (NullPointerException unused) {
            return 0;
        }
    }

    /* renamed from: d */
    public int m1737d() {
        int i = this.f4075p;
        if (i != 0) {
            return i;
        }
        try {
            if (this.f4065d == null) {
                return 0;
            }
            this.f4075p = this.f4065d.getInteger("i-frame-interval");
            return this.f4075p;
        } catch (NullPointerException unused) {
            return 0;
        }
    }

    /* renamed from: e */
    public int m1735e() {
        int i = this.f4074o;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        try {
            try {
                if (this.f4065d != null) {
                    i2 = this.f4065d.getInteger("frame-rate");
                }
            } catch (NullPointerException unused) {
                i2 = 20;
            }
        } catch (NullPointerException unused2) {
            i2 = this.f4065d.getInteger("video-framerate");
        }
        this.f4074o = i2;
        return this.f4074o;
    }

    /* renamed from: f */
    public int m1734f() {
        return this.f4068i;
    }

    /* renamed from: g */
    public int m1733g() {
        int parseInt;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this.f4070k);
        String extractMetadata = mediaMetadataRetriever.extractMetadata(24);
        if (TextUtils.isEmpty(extractMetadata)) {
            TXCLog.m2914e("MediaExtractorWrapper", "getRotation error: rotation is empty,rotation have been reset to zero");
            parseInt = 0;
        } else {
            parseInt = Integer.parseInt(extractMetadata);
        }
        mediaMetadataRetriever.release();
        this.f4068i = parseInt;
        TXCLog.m2915d("MediaExtractorWrapper", "mRotation=" + this.f4068i + ",rotation=" + parseInt);
        return parseInt;
    }

    /* renamed from: h */
    public int m1732h() {
        int i = this.f4073n;
        if (i != 0) {
            return i;
        }
        try {
            if (this.f4066e == null) {
                return 0;
            }
            this.f4073n = this.f4066e.getInteger("sample-rate");
            return this.f4073n;
        } catch (NullPointerException unused) {
            return 0;
        }
    }

    /* renamed from: i */
    public int m1731i() {
        int i = this.f4072m;
        if (i != 0) {
            return i;
        }
        try {
            if (this.f4066e == null) {
                return 0;
            }
            this.f4072m = this.f4066e.getInteger("channel-count");
            return this.f4072m;
        } catch (NullPointerException unused) {
            return 0;
        }
    }

    /* renamed from: j */
    public long m1730j() {
        MediaFormat mediaFormat = this.f4065d;
        if (mediaFormat != null) {
            try {
                return mediaFormat.getLong("durationUs");
            } catch (Exception unused) {
            }
        }
        return 0L;
    }

    /* renamed from: k */
    public long m1729k() {
        MediaFormat mediaFormat = this.f4066e;
        if (mediaFormat != null) {
            try {
                return mediaFormat.getLong("durationUs");
            } catch (Exception unused) {
            }
        }
        return 0L;
    }

    /* renamed from: a */
    public Frame m1745a(Frame frame) {
        frame.m2343a(this.f4063b.getSampleTime());
        int sampleTrackIndex = this.f4063b.getSampleTrackIndex();
        frame.m2344a(sampleTrackIndex);
        frame.m2334c(this.f4063b.getSampleFlags());
        frame.m2331d(this.f4063b.readSampleData(frame.m2338b(), 0));
        frame.m2338b().position(0);
        frame.m2326f(m1735e());
        frame.m2328e(m1734f());
        frame.m2324g(m1732h());
        frame.m2322h(m1731i());
        frame.m2318j(m1743b());
        frame.m2316k(m1740c());
        frame.m2339a(false);
        if (this.f4067f == -1 && sampleTrackIndex == m1726n()) {
            this.f4067f = frame.m2329e();
        }
        if (frame.m2325g() <= 0) {
            frame.m2331d(0);
            frame.m2343a(0L);
            frame.m2334c(4);
        }
        return frame;
    }

    /* renamed from: b */
    public Frame m1741b(Frame frame) {
        frame.m2343a(this.f4064c.getSampleTime());
        int sampleTrackIndex = this.f4064c.getSampleTrackIndex();
        frame.m2344a(sampleTrackIndex);
        frame.m2334c(this.f4064c.getSampleFlags());
        frame.m2331d(this.f4064c.readSampleData(frame.m2338b(), 0));
        frame.m2338b().position(0);
        frame.m2328e(m1734f());
        frame.m2324g(m1732h());
        frame.m2322h(m1731i());
        frame.m2318j(m1743b());
        frame.m2316k(m1740c());
        frame.m2339a(false);
        if (this.f4067f == -1 && sampleTrackIndex == m1726n()) {
            this.f4067f = frame.m2329e();
        }
        if (frame.m2325g() <= 0) {
            frame.m2331d(0);
            frame.m2343a(0L);
            frame.m2334c(4);
        }
        return frame;
    }

    /* renamed from: l */
    public MediaFormat m1728l() {
        return this.f4065d;
    }

    /* renamed from: m */
    public MediaFormat m1727m() {
        return this.f4066e;
    }

    /* renamed from: n */
    public int m1726n() {
        return f4060g;
    }

    /* renamed from: c */
    public boolean m1738c(Frame frame) {
        if (frame.m2327f() == 4) {
            return true;
        }
        this.f4063b.advance();
        return false;
    }

    /* renamed from: d */
    public boolean m1736d(Frame frame) {
        if (frame.m2327f() == 4) {
            return true;
        }
        this.f4064c.advance();
        return false;
    }

    /* renamed from: a */
    public void m1746a(long j) {
        this.f4063b.seekTo(j, 0);
    }

    /* renamed from: b */
    public void m1742b(long j) {
        this.f4063b.seekTo(j, 1);
    }

    /* renamed from: c */
    public void m1739c(long j) {
        this.f4064c.seekTo(j, 0);
    }

    /* renamed from: o */
    public void m1725o() {
        MediaExtractor mediaExtractor = this.f4063b;
        if (mediaExtractor != null) {
            mediaExtractor.release();
        }
        MediaExtractor mediaExtractor2 = this.f4064c;
        if (mediaExtractor2 != null) {
            mediaExtractor2.release();
        }
    }

    /* renamed from: p */
    public long m1724p() {
        return this.f4063b.getSampleTime();
    }

    /* renamed from: q */
    public long m1723q() {
        return this.f4064c.getSampleTime();
    }

    /* renamed from: r */
    public long m1722r() {
        return this.f4063b.getSampleTime();
    }
}
