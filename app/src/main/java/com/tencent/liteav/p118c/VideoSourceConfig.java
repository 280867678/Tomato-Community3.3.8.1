package com.tencent.liteav.p118c;

import android.graphics.Bitmap;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.ugc.TXVideoEditConstants;
import java.io.File;
import java.io.IOException;
import java.util.List;

/* renamed from: com.tencent.liteav.c.k */
/* loaded from: classes3.dex */
public class VideoSourceConfig {

    /* renamed from: b */
    private static VideoSourceConfig f3393b;

    /* renamed from: a */
    public String f3394a;

    /* renamed from: c */
    private int f3395c = 1;

    /* renamed from: d */
    private List<Bitmap> f3396d;

    /* renamed from: e */
    private int f3397e;

    /* renamed from: a */
    public static VideoSourceConfig m2416a() {
        if (f3393b == null) {
            f3393b = new VideoSourceConfig();
        }
        return f3393b;
    }

    private VideoSourceConfig() {
    }

    /* renamed from: a */
    public void m2414a(List<Bitmap> list, int i) {
        this.f3396d = list;
        this.f3397e = i;
        this.f3395c = 2;
    }

    /* renamed from: b */
    public List<Bitmap> m2413b() {
        return this.f3396d;
    }

    /* renamed from: c */
    public int m2412c() {
        return this.f3397e;
    }

    /* renamed from: d */
    public int m2411d() {
        return this.f3395c;
    }

    /* renamed from: e */
    public int m2410e() {
        if (!TextUtils.isEmpty(this.f3394a) && new File(this.f3394a).exists()) {
            if (Build.VERSION.SDK_INT >= 16) {
                try {
                    MediaExtractor mediaExtractor = new MediaExtractor();
                    mediaExtractor.setDataSource(this.f3394a);
                    int trackCount = mediaExtractor.getTrackCount();
                    if (trackCount < 1) {
                        return TXVideoEditConstants.ERR_SOURCE_NO_TRACK;
                    }
                    for (int i = 0; i < trackCount; i++) {
                        MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                        TXCLog.m2913i("VideoSourceConfig", "checkLegality :" + trackFormat.toString());
                        if (trackFormat.getString("mime").startsWith("audio") && trackFormat.containsKey("channel-count") && trackFormat.getInteger("channel-count") > 2) {
                            mediaExtractor.release();
                            return -1004;
                        }
                    }
                    mediaExtractor.release();
                } catch (Exception e) {
                    TXCLog.m2914e("VideoSourceConfig", "Exception:" + e.toString());
                    return TXVideoEditConstants.ERR_SOURCE_DAMAGED;
                }
            }
            return 0;
        }
        return TXVideoEditConstants.ERR_SOURCE_NO_FOUND;
    }

    /* renamed from: a */
    public int m2415a(String str) {
        if (!new File(str).exists()) {
            return TXVideoEditConstants.ERR_SOURCE_NO_FOUND;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            MediaExtractor mediaExtractor = new MediaExtractor();
            try {
                mediaExtractor.setDataSource(str);
                int trackCount = mediaExtractor.getTrackCount();
                for (int i = 0; i < trackCount; i++) {
                    MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                    TXCLog.m2913i("VideoSourceConfig", "BGM checkLegality :" + trackFormat.toString());
                    if (trackFormat.getString("mime").startsWith("audio") && trackFormat.containsKey("channel-count") && trackFormat.getInteger("channel-count") > 2) {
                        return -1004;
                    }
                }
            } catch (Exception e) {
                TXCLog.m2914e("VideoSourceConfig", "Exception:" + e.toString());
                return TXVideoEditConstants.ERR_SOURCE_DAMAGED;
            }
        }
        return 0;
    }

    /* renamed from: f */
    public boolean m2409f() {
        int i = Build.VERSION.SDK_INT;
        if (i < 16) {
            TXCLog.m2914e("VideoSourceConfig", "judgeFullIFrame SDK version is less:16");
            return false;
        }
        if (i >= 16) {
            MediaExtractor mediaExtractor = new MediaExtractor();
            try {
                mediaExtractor.setDataSource(this.f3394a);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i2 = 0; i2 < mediaExtractor.getTrackCount(); i2++) {
                if (mediaExtractor.getTrackFormat(i2).getString("mime").startsWith("video/")) {
                    mediaExtractor.selectTrack(i2);
                }
            }
            mediaExtractor.seekTo(0L, 0);
            int sampleFlags = mediaExtractor.getSampleFlags();
            mediaExtractor.advance();
            mediaExtractor.advance();
            mediaExtractor.advance();
            int sampleFlags2 = mediaExtractor.getSampleFlags();
            mediaExtractor.advance();
            mediaExtractor.advance();
            int sampleFlags3 = mediaExtractor.getSampleFlags();
            mediaExtractor.release();
            if (sampleFlags == sampleFlags2 && sampleFlags == sampleFlags3 && sampleFlags == 1) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: g */
    public void m2408g() {
        this.f3395c = 1;
        this.f3394a = null;
        this.f3397e = 0;
        List<Bitmap> list = this.f3396d;
        if (list != null) {
            list.clear();
        }
    }
}
